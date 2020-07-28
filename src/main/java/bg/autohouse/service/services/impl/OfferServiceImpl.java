package bg.autohouse.service.services.impl;

import static bg.autohouse.data.specifications.OfferSpecifications.*;
import static org.springframework.data.jpa.domain.Specification.where;

import bg.autohouse.data.models.Filter;
import bg.autohouse.data.models.account.Account;
import bg.autohouse.data.models.geo.Location;
import bg.autohouse.data.models.media.MediaFile;
import bg.autohouse.data.models.media.MediaFunction;
import bg.autohouse.data.models.offer.Offer;
import bg.autohouse.data.models.offer.Vehicle;
import bg.autohouse.data.repositories.AccountRepository;
import bg.autohouse.data.repositories.FilterRepository;
import bg.autohouse.data.repositories.LocationRepository;
import bg.autohouse.data.repositories.OfferRepository;
import bg.autohouse.errors.AccountNotFoundException;
import bg.autohouse.errors.LocationNotFoundException;
import bg.autohouse.errors.NotFoundException;
import bg.autohouse.errors.OfferNotFoundException;
import bg.autohouse.service.models.offer.OfferDetailsServiceModel;
import bg.autohouse.service.models.offer.OfferServiceModel;
import bg.autohouse.service.services.MediaFileService;
import bg.autohouse.service.services.OfferService;
import bg.autohouse.util.Assert;
import bg.autohouse.util.ImageResizer;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.request.offer.OfferCreateRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OfferServiceImpl implements OfferService {

  private final OfferRepository offerRepository;
  private final FilterRepository filterRepository;
  private final ModelMapperWrapper modelMapper;
  private final LocationRepository locationRepository;
  private final AccountRepository accountRepository;
  private final MediaFileService mediaFileService;
  private final ImageResizer imageResizer;

  @Override
  @Transactional(readOnly = true)
  public List<OfferServiceModel> getLatestOffers() {
    Sort sort = Sort.by("createdAt").descending();
    Pageable pageable = PageRequest.of(0, 20, sort);
    List<OfferServiceModel> topOffers =
        offerRepository
            .findLatestOffers(pageable)
            .map(offer -> modelMapper.map(offer, OfferServiceModel.class))
            .collect(Collectors.toUnmodifiableList());
    return topOffers;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<OfferServiceModel> searchOffers(UUID filterId, Pageable pageable) {
    Objects.requireNonNull(filterId);
    Filter filter = filterRepository.findFilterById(filterId).orElseThrow(NotFoundException::new);

    Specification<Offer> specification =
        where(getOffersByFilter(filter)).and(activeUser()).and(activeOffers());

    if (!filter.getFeatures().isEmpty()) {
      List<UUID> offersIds = offerIdsForFilterFeatures(filterId);
      if (offersIds.isEmpty()) {
        return Page.empty();
      }
      specification = specification.and(uuidIn(offersIds));
    }

    return offerRepository
        .findAll(specification, pageable)
        .map(offer -> modelMapper.map(offer, OfferServiceModel.class));
  }

  @Transactional(readOnly = true)
  private List<UUID> offerIdsForFilterFeatures(UUID filterId) {
    return offerRepository.searchOffersIdsWithFeatures(filterId).stream()
        .map(o -> o.getId())
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public OfferDetailsServiceModel getOfferById(UUID id) {
    Offer offer = offerRepository.findOfferById(id).orElseThrow(OfferNotFoundException::new);
    offer.incrementHitCount();
    offerRepository.save(offer);
    return modelMapper.map(offer, OfferDetailsServiceModel.class);
  }

  @Override
  @Transactional
  public boolean toggleActive(UUID creatorId, UUID offerId) {
    Specification<Offer> specification = oneWithIdAndOwnerId(offerId, creatorId);
    Offer offer = offerRepository.findOne(specification).orElseThrow(OfferNotFoundException::new);
    offer.toggleActive();
    return offer.isActive();
  }

  @Override
  @Transactional(readOnly = true)
  public List<String> fetchOfferImages(UUID offerId) {
    boolean isOffer = offerRepository.existsById(offerId);
    if (!isOffer) throw new OfferNotFoundException();
    return mediaFileService.loadForReference(offerId).stream()
        .map(media -> media.getFileKey())
        .collect(Collectors.toList());
  }

  @Override
  // TODO implement main photo choose
  // TODO validate numbers
  // TODO check is primary photo and return default
  // TODO check are images
  // TODO add maker and model ids
  @Transactional(rollbackFor = IOException.class)
  public OfferServiceModel createOffer(OfferCreateRequest request, UUID creatorId)
      throws IOException {
    Assert.notNull(creatorId, "User id is required");
    Assert.notNull(request, "Offer model is required");
    Account account =
        accountRepository.findByUserId(creatorId).orElseThrow(AccountNotFoundException::new);
    Location location =
        locationRepository
            .findByPostalCode(request.getAddressLocationPostalCode())
            .orElseThrow(LocationNotFoundException::new);
    Vehicle vehicle = modelMapper.map(request.getVehicle(), Vehicle.class);
    Assert.notNull(vehicle.getBodyStyle(), RestMessage.INVALID_BODY_STYLE.name());
    Assert.notNull(vehicle.getColor(), RestMessage.INVALID_COLOR.name());
    Assert.notNull(vehicle.getDrive(), RestMessage.INVALID_DRIVE.name());
    Assert.notNull(vehicle.getFuelType(), RestMessage.INVALID_FUEL_TYPE.name());
    Assert.notNull(vehicle.getState(), RestMessage.INVALID_VEHICLE_STATE.name());
    Assert.notNull(vehicle.getTransmission(), RestMessage.INVALID_TRANSMISSION.name());
    Assert.notNulls(vehicle.getFeatures(), RestMessage.INVALID_FEATURE.name());
    request.setVehicle(null);
    Offer offer = modelMapper.map(request, Offer.class);
    offer.setLocation(location);
    offer.setAccount(account);
    offer = offerRepository.save(offer);
    offer.setVehicle(vehicle);
    vehicle.setOffer(offer);
    for (MultipartFile file : request.getImages()) {
      byte[] byteArray = imageResizer.toJpgDownscaleToSize(file.getInputStream());
      String fileName =
          generateFileName(
              file.getContentType(),
              Integer.toString(vehicle.getYear()),
              vehicle.getMakerName(),
              vehicle.getModelName(),
              "pic",
              Long.toString(System.currentTimeMillis()));
      String fileKey = generateFileKey(offer.getId(), fileName);
      MediaFile mediaFile =
          mediaFileService.storeFile(
              byteArray,
              fileKey,
              MediaFunction.OFFER_IMAGE,
              file.getContentType(),
              file.getOriginalFilename(),
              offer.getId());
      if (mediaFile.getOriginalFilename().equals(request.getMainPhoto())) {
        offer.setPrimaryPhotoKey(mediaFile.getFileKey());
      }
    }
    return modelMapper.map(offer, OfferServiceModel.class);
  }

  private String generateFileName(String contentType, String... params) {
    String ext =
        contentType.replace("image/", "").equals("jpeg")
            ? "jpg"
            : contentType.replace("image/", "");
    return String.join("_", params).toLowerCase().replaceAll("\\s+", "_") + "." + ext;
  }

  private String generateFileKey(UUID referenceId, String fileName) {
    LocalDate now = LocalDate.now();
    return String.join(
        "/",
        "offer-images",
        Integer.toString(now.getYear()),
        String.format("%02d", now.getMonthValue()),
        String.format("%02d", now.getDayOfMonth()),
        referenceId.toString(),
        fileName);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<OfferServiceModel> searchOffersByIds(List<UUID> offerIds, Pageable pageable) {
    if (offerIds.isEmpty()) return Page.empty();
    Specification<Offer> specification =
        where(favoriteWithIds(offerIds)).and(activeUser()).and(activeOffers());
    return offerRepository
        .findAll(specification, pageable)
        .map(offer -> modelMapper.map(offer, OfferServiceModel.class));
  }

  @Override
  @Transactional(readOnly = true)
  public Page<OfferServiceModel> findUserOffers(UUID userId, Pageable pageable) {
    UUID accountId =
        accountRepository
            .findByUserId(userId)
            .map(acc -> acc.getId())
            .orElseThrow(AccountNotFoundException::new);
    Specification<Offer> specification = where(withAccountId(accountId));
    Page<OfferServiceModel> accountOffers =
        offerRepository
            .findAll(specification, pageable)
            .map(offer -> modelMapper.map(offer, OfferServiceModel.class));
    return accountOffers;
  }
}

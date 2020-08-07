package bg.autohouse.service.services.impl;

import static bg.autohouse.data.specifications.OfferSpecifications.*;
import static org.springframework.data.jpa.domain.Specification.where;

import bg.autohouse.data.models.BaseUuidEntity;
import bg.autohouse.data.models.EntityConstants;
import bg.autohouse.data.models.Filter;
import bg.autohouse.data.models.Model;
import bg.autohouse.data.models.account.Account;
import bg.autohouse.data.models.geo.Location;
import bg.autohouse.data.models.media.MediaFile;
import bg.autohouse.data.models.media.MediaFunction;
import bg.autohouse.data.models.offer.Offer;
import bg.autohouse.data.models.offer.Vehicle;
import bg.autohouse.data.repositories.AccountRepository;
import bg.autohouse.data.repositories.FilterRepository;
import bg.autohouse.data.repositories.LocationRepository;
import bg.autohouse.data.repositories.ModelRepository;
import bg.autohouse.data.repositories.OfferRepository;
import bg.autohouse.data.repositories.VehicleRepository;
import bg.autohouse.errors.AccountNotFoundException;
import bg.autohouse.errors.InvalidOfferException;
import bg.autohouse.errors.LocationNotFoundException;
import bg.autohouse.errors.ModelNotFoundException;
import bg.autohouse.errors.NotFoundException;
import bg.autohouse.errors.OfferNotFoundException;
import bg.autohouse.service.models.offer.OfferDetailsServiceModel;
import bg.autohouse.service.models.offer.OfferEditServiceModel;
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
import java.util.ArrayList;
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
  private final VehicleRepository vehicleRepository;
  private final ModelRepository modelRepository;
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
    return offerRepository.findPageWithActiveOffers(pageable).stream()
        .map(offer -> modelMapper.map(offer, OfferServiceModel.class))
        .collect(Collectors.toUnmodifiableList());
  }

  @Override
  @Transactional(readOnly = true)
  public Page<OfferServiceModel> searchOffers(UUID filterId, Pageable pageable) {
    Objects.requireNonNull(filterId);
    Filter filter = filterRepository.findFilterById(filterId).orElseThrow(NotFoundException::new);

    Specification<Offer> specification =
        Objects.requireNonNull(where(getOffersByFilter(filter)).and(activeUser())).and(activeOffers());

    if (!filter.getFeatures().isEmpty()) {
      List<UUID> offersIds = offerIdsForFilterFeatures(filterId);
      if (offersIds.isEmpty()) {
        return Page.empty();
      }
      specification = Objects.requireNonNull(specification).and(uuidIn(offersIds));
    }

    return offerRepository
        .findAll(specification, pageable)
        .map(offer -> modelMapper.map(offer, OfferServiceModel.class));
  }

  @Transactional(readOnly = true)
  List<UUID> offerIdsForFilterFeatures(UUID filterId) {
    return offerRepository.searchOffersIdsWithFeatures(filterId).stream()
        .map(BaseUuidEntity::getId)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public OfferDetailsServiceModel loadOfferByIdPublicView(UUID id) {
    Offer offer = offerRepository.findOfferById(id).orElseThrow(OfferNotFoundException::new);
    offer.incrementHitCount();
    offerRepository.save(offer);
    return modelMapper.map(offer, OfferDetailsServiceModel.class);
  }

  @Override
  @Transactional(readOnly = true)
  public OfferDetailsServiceModel loadOfferByIdPrivateView(UUID id, UUID creatorId) {
    Offer offer =
        offerRepository
            .findOneByIdAndCreatorId(id, creatorId)
            .orElseThrow(OfferNotFoundException::new);
    return modelMapper.map(offer, OfferDetailsServiceModel.class);
  }

  @Override
  @Transactional(readOnly = true)
  public OfferEditServiceModel loadOfferForEdit(UUID creatorId, UUID offerId) {
    Offer offer =
        offerRepository
            .findOneByIdAndCreatorId(offerId, creatorId)
            .orElseThrow(OfferNotFoundException::new);
    return modelMapper.map(offer, OfferEditServiceModel.class);
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
        .map(MediaFile::getFileKey)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(rollbackFor = IOException.class)
  public OfferServiceModel createOffer(OfferCreateRequest request, UUID creatorId)
      throws IOException {
    Assert.notNull(creatorId, "User id is required");
    Assert.notNull(request, "Offer model is required");
    Account account =
        accountRepository.findByUserId(creatorId).orElseThrow(AccountNotFoundException::new);
    long currentOffersCount = offerRepository.countByAccountId(account.getId());
    if (currentOffersCount >= account.getMaxOffersCount()) {
      throw new IllegalArgumentException(RestMessage.MAX_OFFER_COUNT_REACHED.name());
    }
    Location location =
        locationRepository
            .findByPostalCode(request.getAddressLocationPostalCode())
            .orElseThrow(LocationNotFoundException::new);
    Vehicle vehicle = modelMapper.map(request.getVehicle(), Vehicle.class);
    request.setVehicle(null);
    Model model =
        modelRepository
            .findByNameAndMakerName(vehicle.getModelName(), vehicle.getMakerName())
            .orElseThrow(ModelNotFoundException::new);
    vehicle.setMakerId(model.getMaker().getId());
    vehicle.setModelId(model.getId());
    Offer offer = modelMapper.map(request, Offer.class);
    offer.setLocation(location);
    offer.setAccount(account);
    offer = offerRepository.save(offer);
    offer.setVehicle(vehicle);
    vehicle.setOffer(offer);
    assertNoNullValuesVehicle(vehicle);
    assertNotNullOfferValues(offer);
    validateNumericValues(offer);
    if (request.getImages().isEmpty())
      throw new InvalidOfferException(RestMessage.HAS_NO_IMAGES.name());
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
  @Transactional
  public OfferServiceModel updateOffer(OfferCreateRequest request, UUID offerId, UUID creatorId) {
    Assert.notNull(creatorId, "User id is required");
    Assert.notNull(offerId, "Offer id is required");
    Assert.notNull(request, "Offer model is required");
    boolean hasAccount = accountRepository.existsByUserId(creatorId);
    if (!hasAccount) throw new AccountNotFoundException();
    Vehicle updatedVehicle = modelMapper.map(request.getVehicle(), Vehicle.class);
    Model model =
        modelRepository
            .findByNameAndMakerName(updatedVehicle.getModelName(), updatedVehicle.getMakerName())
            .orElseThrow(ModelNotFoundException::new);
    Offer updatedOffer = modelMapper.map(request, Offer.class);
    Offer offer =
        offerRepository
            .findOneByIdAndCreatorId(offerId, creatorId)
            .orElseThrow(OfferNotFoundException::new);
    if (Assert.has(request.getAddressLocationPostalCode())
        && !request.getAddressLocationPostalCode().equals(offer.getLocation().getPostalCode())) {
      Location location =
          locationRepository
              .findByPostalCode(request.getAddressLocationPostalCode())
              .orElseThrow(LocationNotFoundException::new);
      offer.setLocation(location);
    }
    offer.getVehicle().setMakerName(updatedVehicle.getMakerName());
    offer.getVehicle().setMakerId(model.getMaker().getId());
    offer.getVehicle().setModelName(updatedVehicle.getModelName());
    offer.getVehicle().setModelId(model.getId());
    offer.getVehicle().setTrim(updatedVehicle.getTrim());
    offer.getVehicle().setYear(updatedVehicle.getYear());
    offer.getVehicle().setMileage(updatedVehicle.getMileage());
    offer.getVehicle().setDoors(updatedVehicle.getDoors());
    offer.getVehicle().setState(updatedVehicle.getState());
    offer.getVehicle().setBodyStyle(updatedVehicle.getBodyStyle());
    offer.getVehicle().setTransmission(updatedVehicle.getTransmission());
    offer.getVehicle().setDrive(updatedVehicle.getDrive());
    offer.getVehicle().setColor(updatedVehicle.getColor());
    offer.getVehicle().setFuelType(updatedVehicle.getFuelType());
    offer.getVehicle().setFeatures(updatedVehicle.getFeatures());
    offer.getVehicle().setHasAccident(updatedVehicle.isHasAccident());
    offer.setPrice(updatedOffer.getPrice());
    offer.setDescription(updatedOffer.getDescription());
    offer.getContactDetails().setPhoneNumber(updatedOffer.getContactDetails().getPhoneNumber());
    offer.getContactDetails().setWebLink(updatedOffer.getContactDetails().getWebLink());
    assertNoNullValuesVehicle(offer.getVehicle());
    assertNotNullOfferValues(offer);
    validateNumericValues(offer);
    return modelMapper.map(offerRepository.save(offer), OfferServiceModel.class);
  }

  private void assertNotNullOfferValues(Offer offer) {
    Assert.notNull(offer.getPrice(), RestMessage.INVALID_OFFER_PRICE.name());
    Assert.notNull(offer.getDescription(), RestMessage.INVALID_OFFER_DESCRIPTION.name());
    Assert.notNull(
        offer.getContactDetails().getPhoneNumber(), RestMessage.INVALID_OFFER_PHONE_NUMBER.name());
    Assert.notNull(offer.getAccount(), RestMessage.ACCOUNT_MISSING.name());
    Assert.notNull(offer.getLocation(), RestMessage.LOCATION_MISSING.name());
    Assert.notNull(offer.getVehicle(), RestMessage.VEHICLE_MISSING.name());
  }

  private void assertNoNullValuesVehicle(Vehicle vehicle) {
    Assert.notNull(vehicle.getBodyStyle(), RestMessage.INVALID_BODY_STYLE.name());
    Assert.notNull(vehicle.getColor(), RestMessage.INVALID_COLOR.name());
    Assert.notNull(vehicle.getDrive(), RestMessage.INVALID_DRIVE.name());
    Assert.notNull(vehicle.getFuelType(), RestMessage.INVALID_FUEL_TYPE.name());
    Assert.notNull(vehicle.getState(), RestMessage.INVALID_VEHICLE_STATE.name());
    Assert.notNull(vehicle.getTransmission(), RestMessage.INVALID_TRANSMISSION.name());
    Assert.notNulls(vehicle.getFeatures(), RestMessage.INVALID_FEATURE.name());
    Assert.notNull(vehicle.getYear(), RestMessage.INVALID_VEHICLE_YEAR.name());
    Assert.notNull(vehicle.getMileage(), RestMessage.INVALID_VEHICLE_MILEAGE.name());
    Assert.notNull(vehicle.getDoors(), RestMessage.INVALID_VEHICLE_DOORS.name());
  }

  private void validateNumericValues(Offer offer) {
    if (offer.getPrice() < EntityConstants.MIN_VALUE
        || offer.getPrice() > EntityConstants.PRICE_TO) {
      throw new InvalidOfferException(RestMessage.INVALID_OFFER_PRICE.name());
    }
    if (offer.getVehicle().getYear() < EntityConstants.YEAR_FROM
        || offer.getVehicle().getYear() > EntityConstants.YEAR_TO) {
      throw new InvalidOfferException(RestMessage.INVALID_VEHICLE_YEAR.name());
    }
    if (offer.getVehicle().getMileage() < EntityConstants.MIN_VALUE
        || offer.getVehicle().getMileage() > EntityConstants.MILEAGE_TO) {
      throw new InvalidOfferException(RestMessage.INVALID_VEHICLE_MILEAGE.name());
    }
    if (offer.getVehicle().getDoors() < EntityConstants.MIN_VALUE
        || offer.getVehicle().getDoors() > EntityConstants.DOORS_TO) {
      throw new InvalidOfferException(RestMessage.INVALID_VEHICLE_DOORS.name());
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Page<OfferServiceModel> searchOffersByIds(List<UUID> offerIds, Pageable pageable) {
    if (offerIds.isEmpty()) return Page.empty();
    Specification<Offer> specification =
        Objects.requireNonNull(where(favoriteWithIds(offerIds)).and(activeUser())).and(activeOffers());
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
            .map(BaseUuidEntity::getId)
            .orElseThrow(AccountNotFoundException::new);
    Specification<Offer> specification = where(withAccountId(accountId));
    return offerRepository
        .findAll(specification, pageable)
        .map(offer -> modelMapper.map(offer, OfferServiceModel.class));
  }

  @Override
  @Transactional
  public void deleteOffer(UUID userId, UUID offerId) {
    Assert.notNull(userId, "User id is required");
    Assert.notNull(offerId, "Offer id is required");
    Offer offer =
        offerRepository
            .findOneByIdAndCreatorId(offerId, userId)
            .orElseThrow(OfferNotFoundException::new);
    mediaFileService.removeAllForReference(offerId);
    if (Assert.has(offer.getVehicle())) {
      offer.getVehicle().setFeatures(new ArrayList<>());
      vehicleRepository.deleteAllById(offer.getVehicle().getId());
    }
    offerRepository.deleteAllById(offer.getId());
  }
}

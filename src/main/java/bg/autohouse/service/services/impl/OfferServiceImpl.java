package bg.autohouse.service.services.impl;

import static org.springframework.data.jpa.domain.Specification.where;

import bg.autohouse.data.models.Filter;
import bg.autohouse.data.models.account.Account;
import bg.autohouse.data.models.geo.Location;
import bg.autohouse.data.models.offer.Offer;
import bg.autohouse.data.models.offer.Vehicle;
import bg.autohouse.data.repositories.AccountRepository;
import bg.autohouse.data.repositories.LocationRepository;
import bg.autohouse.data.repositories.OfferRepository;
import bg.autohouse.data.specifications.OfferSpecifications;
import bg.autohouse.errors.AccountNotFoundException;
import bg.autohouse.errors.LocationNotFoundException;
import bg.autohouse.service.models.offer.OfferServiceModel;
import bg.autohouse.service.services.OfferService;
import bg.autohouse.util.Assert;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.request.FilterRequest;
import bg.autohouse.web.models.request.offer.OfferCreateRequest;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OfferServiceImpl implements OfferService {

  private final OfferRepository offerRepository;
  private final ModelMapperWrapper modelMapper;
  private final LocationRepository locationRepository;
  private final AccountRepository accountRepository;

  @Override
  @Transactional(readOnly = true)
  public List<OfferServiceModel> getTopOffers() {
    Sort sort = Sort.by("createdAt").descending();
    Pageable pageable = PageRequest.of(0, 20, sort);
    List<OfferServiceModel> topOffers =
        offerRepository
            .findLatestOffers(pageable)
            .map(offer -> modelMapper.map(offer, OfferServiceModel.class))
            .collect(Collectors.toUnmodifiableList());
    return topOffers;
  }

  // TODO refactor filter request to add maker and models names
  @Override
  @Transactional(readOnly = true)
  public Page<OfferServiceModel> searchOffers(FilterRequest filterRequest, Pageable pageable) {
    Objects.requireNonNull(filterRequest);
    Filter filter = modelMapper.map(filterRequest, Filter.class);
    if (Assert.has(filterRequest.getMakerId())) {
      filter.setMakerId(filterRequest.getMakerId());
      if (Assert.has(filterRequest.getModelId())) {
        filter.setModelId(filterRequest.getModelId());
      }
    }
    return offerRepository
        .findAll(where(OfferSpecifications.getOffersByFilter(filter)), pageable)
        .map(offer -> modelMapper.map(offer, OfferServiceModel.class));
  }

  @Override
  @Transactional
  public OfferServiceModel createOffer(OfferCreateRequest request, UUID creatorId) {
    Assert.notNull(creatorId, "User id is required");
    Assert.notNull(request, "Offer model is required");
    Account account =
        accountRepository.findByUserId(creatorId).orElseThrow(AccountNotFoundException::new);
    Location location =
        locationRepository
            .findById(request.getLocationId())
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
    // TODO make thumbnail
    offer.setThumbnail("sdadadsa");
    offer = offerRepository.save(offer);
    offer.setVehicle(vehicle);
    vehicle.setOffer(offer);
    return modelMapper.map(offer, OfferServiceModel.class);
  }
  //     VehicleCreateServiceModel vehicleCreateServiceModel = offerCreateServiceModel.getVehicle();
  //     EngineCreateServiceModel engineCreateServiceModel =
  // offerCreateServiceModel.getVehicle().getEngine();

  //     Offer offer = modelMapper.map(offerCreateServiceModel, Offer.class);
  //     offer.setVehicle(null);

  //     User user = userRepository.findByUsername(userUsername)
  //             .orElseThrow(() -> new UsernameNotFoundException(EXCEPTION_USER_NOT_FOUND));
  //     user.addOffer(offer);

  //     Location location = locationRepository.findById(offerCreateServiceModel.getLocation())
  //             .orElseThrow(() -> new NotFoundException(EXCEPTION_LOCATION_NOT_FOUND));
  //     location.addOffer(offer);

  //     offerCreateServiceModel.getFiles()
  //             .stream()
  //             .filter(multipartFile -> multipartFile.getSize() > 0)
  //             .map(multipartFile -> {
  //                 Map<String, Object> params =
  // cloudService.uploadFileAndGetParams(multipartFile);
  //                 return params.get("url").toString();
  //             })
  //             .forEach(url -> {
  //                 Image image = Image.builder()
  //                         .url(url)
  //                         .thumbnailUrl(url)
  //                         .build();

  //                 offer.addImage(image);
  //             });

  //     Offer persistedOffer = offerRepository.save(offer);

  //     Vehicle vehicle = modelMapper.map(offerCreateServiceModel.getVehicle(), Vehicle.class);
  //     vehicle.setVehicleType(VehicleCategory.CAR);

  //     State state = EnumUtils.fromString(vehicleCreateServiceModel.getState(), State.class);
  //     vehicle.setState(state);

  //     Maker maker = makerRepository.findById(offerCreateServiceModel.getVehicle().getMaker())
  //             .orElseThrow(() -> new NotFoundException(EXCEPTION_MAKER_NOT_FOUND));
  //     vehicle.setMaker(maker);

  //     Model model = maker.getModels().stream()
  //             .filter(m -> m.getId().equals(offerCreateServiceModel.getVehicle().getModel()))
  //             .findFirst()
  //             .orElseThrow(() -> new NotFoundException(EXCEPTION_MODEL_NOT_FOUND));
  //     vehicle.setModel(model);

  //     BodyStyle bodyStyle = EnumUtils.fromString(vehicleCreateServiceModel.getBodyStyle(),
  // BodyStyle.class);
  //     vehicle.setBodyStyle(bodyStyle);

  //     Drive drive = EnumUtils.fromString(vehicleCreateServiceModel.getDrive(), Drive.class);
  //     vehicle.setDrive(drive);

  //     Transmission transmission =
  // EnumUtils.fromString(vehicleCreateServiceModel.getTransmission(), Transmission.class);
  //     vehicle.setTransmission(transmission);

  //     List<Color> colors = colorRepository.findAll();

  //     Color interiorColor = colors.stream()
  //             .filter(color ->
  // color.getId().equals(vehicleCreateServiceModel.getInteriorColor()))
  //             .findFirst()
  //             .orElse(null);

  //     vehicle.setInteriorColor(interiorColor);

  //     Color exteriorColor = colors.stream()
  //             .filter(color ->
  // color.getId().equals(vehicleCreateServiceModel.getExteriorColor()))
  //             .findFirst()
  //             .orElse(null);

  //     vehicle.setExteriorColor(exteriorColor);

  //     featureRepository.findAll()
  //             .stream()
  //             .filter(feature ->
  // offerCreateServiceModel.getVehicle().getFeatures().contains(feature.getId()))
  //             .forEach(vehicle::addFeature);

  //     Engine engine = vehicle.getEngine();

  //     FuelType fuelType = EnumUtils.fromString(engineCreateServiceModel.getFuelType(),
  // FuelType.class);
  //     engine.setFuelType(fuelType);

  //     PowerType powerType = EnumUtils.fromString(engineCreateServiceModel.getPowerType(),
  // PowerType.class);
  //     engine.setPowerType(powerType);

  //     EuroStandard euroStandard =
  // EnumUtils.fromString(engineCreateServiceModel.getEuroStandard(), EuroStandard.class);
  //     engine.setEuroStandard(euroStandard);

  //     vehicle.setOffer(persistedOffer);
  //     persistedOffer.setVehicle(vehicle);

  //     vehicle.setEngine(engine);
  //     engine.setVehicle(vehicle);

  //     offerRepository.flush();

  //     return true;
  // }
}

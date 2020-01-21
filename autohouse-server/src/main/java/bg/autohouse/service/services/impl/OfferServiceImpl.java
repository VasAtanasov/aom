package bg.autohouse.service.services.impl;

import bg.autohouse.data.models.*;
import bg.autohouse.data.models.enums.*;
import bg.autohouse.data.repositories.*;
import bg.autohouse.errors.NotFoundException;
import bg.autohouse.service.models.*;
import bg.autohouse.service.services.CloudService;
import bg.autohouse.service.services.OfferService;
import bg.autohouse.service.services.ThumbnailService;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.models.binding.FilterBindingModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static bg.autohouse.common.Constants.*;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final MakerRepository makerRepository;
    private final LocationRepository locationRepository;
    private final ColorRepository colorRepository;
    private final FeatureRepository featureRepository;
    private final ModelMapperWrapper modelMapper;
    private final CloudService cloudService;
    private final ThumbnailService thumbnailService;

    @Override
    public Page<OfferListingServiceModel> findAll(FilterBindingModel filterBindingModel, Pageable pageable) {
        Page<Offer> page = offerRepository.findAll(pageable);
        return page.map(offer -> modelMapper.map(offer, OfferListingServiceModel.class));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "user-offers", sync = true)
    public Page<OfferListingServiceModel> findAll(String username, Pageable pageable) {
        Page<Offer> page = offerRepository.findAllByUserUsername(username, pageable);
        return page.map(offer -> modelMapper.map(offer, OfferListingServiceModel.class));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "offer", sync = true)
    public OfferDetailsServiceModel findById(String id) {
        return offerRepository.findById(id)
                .map(offer -> modelMapper.map(offer, OfferDetailsServiceModel.class))
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Image uploadImage(MultipartFile multipartFile) {
        Map<String, Object> params = cloudService.uploadFileAndGetParams(multipartFile);
        String fileUrl = params.get("url").toString();

        Image image = Image.builder()
                .url(fileUrl)
                .build();

        if (params.get("contentType").toString().contains("image")) {
            image.setThumbnailUrl(fileUrl);
        } else {
            image.setThumbnailUrl(thumbnailService.generateThumbnailUrl(fileUrl));
        }

        return image;
    }

    @Override
    public boolean createOffer(OfferCreateServiceModel offerCreateServiceModel, String userUsername) {
        VehicleCreateServiceModel vehicleCreateServiceModel = offerCreateServiceModel.getVehicle();
        EngineCreateServiceModel engineCreateServiceModel = offerCreateServiceModel.getVehicle().getEngine();

        Offer offer = modelMapper.map(offerCreateServiceModel, Offer.class);
        offer.setVehicle(null);

        User user = userRepository.findByUsername(userUsername)
                .orElseThrow(() -> new UsernameNotFoundException(EXCEPTION_USER_NOT_FOUND));
        user.addOffer(offer);

        Location location = locationRepository.findById(offerCreateServiceModel.getLocation())
                .orElseThrow(() -> new NotFoundException(EXCEPTION_LOCATION_NOT_FOUND));
        location.addOffer(offer);

        offerCreateServiceModel.getFiles()
                .stream()
                .filter(multipartFile -> multipartFile.getSize() > 0)
                .map(multipartFile -> {
                    Map<String, Object> params = cloudService.uploadFileAndGetParams(multipartFile);
                    return params.get("url").toString();
                })
                .forEach(url -> {
                    Image image = Image.builder()
                            .url(url)
                            .thumbnailUrl(url)
                            .build();

                    offer.addImage(image);
                });

        Offer persistedOffer = offerRepository.save(offer);

        Vehicle vehicle = modelMapper.map(offerCreateServiceModel.getVehicle(), Vehicle.class);
        vehicle.setVehicleType(VehicleCategory.CAR);

        State state = EnumUtils.fromString(vehicleCreateServiceModel.getState(), State.class);
        vehicle.setState(state);

        Maker maker = makerRepository.findById(offerCreateServiceModel.getVehicle().getMaker())
                .orElseThrow(() -> new NotFoundException(EXCEPTION_MAKER_NOT_FOUND));
        vehicle.setMaker(maker);

        Model model = maker.getModels().stream()
                .filter(m -> m.getId().equals(offerCreateServiceModel.getVehicle().getModel()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(EXCEPTION_MODEL_NOT_FOUND));
        vehicle.setModel(model);

        BodyStyle bodyStyle = EnumUtils.fromString(vehicleCreateServiceModel.getBodyStyle(), BodyStyle.class);
        vehicle.setBodyStyle(bodyStyle);

        Drive drive = EnumUtils.fromString(vehicleCreateServiceModel.getDrive(), Drive.class);
        vehicle.setDrive(drive);

        Transmission transmission = EnumUtils.fromString(vehicleCreateServiceModel.getTransmission(), Transmission.class);
        vehicle.setTransmission(transmission);

        List<Color> colors = colorRepository.findAll();

        Color interiorColor = colors.stream()
                .filter(color -> color.getId().equals(vehicleCreateServiceModel.getInteriorColor()))
                .findFirst()
                .orElse(null);

        vehicle.setInteriorColor(interiorColor);

        Color exteriorColor = colors.stream()
                .filter(color -> color.getId().equals(vehicleCreateServiceModel.getExteriorColor()))
                .findFirst()
                .orElse(null);

        vehicle.setExteriorColor(exteriorColor);

        featureRepository.findAll()
                .stream()
                .filter(feature -> offerCreateServiceModel.getVehicle().getFeatures().contains(feature.getId()))
                .forEach(vehicle::addFeature);

        Engine engine = vehicle.getEngine();

        FuelType fuelType = EnumUtils.fromString(engineCreateServiceModel.getFuelType(), FuelType.class);
        engine.setFuelType(fuelType);

        PowerType powerType = EnumUtils.fromString(engineCreateServiceModel.getPowerType(), PowerType.class);
        engine.setPowerType(powerType);

        EuroStandard euroStandard = EnumUtils.fromString(engineCreateServiceModel.getEuroStandard(), EuroStandard.class);
        engine.setEuroStandard(euroStandard);

        vehicle.setOffer(persistedOffer);
        persistedOffer.setVehicle(vehicle);

        vehicle.setEngine(engine);
        engine.setVehicle(vehicle);

        offerRepository.flush();

        return true;
    }
}

package bg.autohouse.service.validations;

import bg.autohouse.data.models.enums.*;
import bg.autohouse.service.models.ColorServiceModel;
import bg.autohouse.service.models.FeatureServiceModel;
import bg.autohouse.service.models.MakerServiceModel;
import bg.autohouse.service.services.ColorService;
import bg.autohouse.service.services.FeatureService;
import bg.autohouse.service.services.LocationService;
import bg.autohouse.service.services.MakerService;
import bg.autohouse.web.models.binding.EngineBindingModel;
import bg.autohouse.web.models.binding.OfferBindingModel;
import bg.autohouse.web.models.binding.VehicleBindingModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static bg.autohouse.util.matcher.MatcherAssert.assertThat;
import static bg.autohouse.util.matcher.Matchers.*;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OfferCreateValidator implements Validator {

    private static final Integer TWO_MILLION = 2_000_00;
    private static final List<Integer> DOORS_COUNTS = List.of(2, 4, 6);
    private static final List<Integer> CYLINDERS_COUNTS = List.of(3, 4, 6, 8, 12);
    private static final Integer MAX_SEATS_COUNT = 12;
    private static final Integer MIN_COUNT = 1;
    private static final Integer MAX_DESCRIPTION_LENGTH = 500;
    private static final Integer MAX_MODIFICATION_LENGTH = 30;

    private final MakerService makerService;
    private final LocationService locationService;
    private final ColorService colorService;
    private final FeatureService featureService;

    @Override
    public boolean supports(Class<?> aClass) {
        return OfferBindingModel.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        OfferBindingModel offerBindingModel = (OfferBindingModel) obj;
        VehicleBindingModel vehicle = offerBindingModel.getVehicle();
        EngineBindingModel engine = offerBindingModel.getVehicle().getEngine();

        Long makerId = vehicle.getMaker();
        if (assertThat(makerId, is(notNullValue()))) {
            MakerServiceModel maker = makerService.getModelsForMaker(makerId);
            if (assertThat(maker, is(nullValue()))) {
                errors.rejectValue("vehicle.maker", HttpStatus.BAD_REQUEST.toString(), "Maker does not exist!");
                return;
            }

            Long modelId = vehicle.getModel();
            if (assertThat(modelId, is(notNullValue()))) {
                boolean model = maker.getModels()
                        .stream()
                        .anyMatch(modelServiceModel -> modelServiceModel.getId().equals(modelId));

                if (!model) {
                    errors.rejectValue("vehicle.model", HttpStatus.BAD_REQUEST.toString(), "Model does not exist!");
                    return;
                }
            }
        }

        State state = EnumUtils.fromString(vehicle.getState(), State.class);
        if (assertThat(state, is(nullValue()))) {
            errors.rejectValue("vehicle.state", HttpStatus.BAD_REQUEST.toString(), "Select valid vehicle condition!");
            return;
        }

        BodyStyle bodyStyle = EnumUtils.fromString(vehicle.getBodyStyle(), BodyStyle.class);
        if (assertThat(bodyStyle, is(nullValue()))) {
            errors.rejectValue("vehicle.bodyStyle", HttpStatus.BAD_REQUEST.toString(), "Select valid body style!");
            return;
        }

        FuelType fuelType = EnumUtils.fromString(engine.getFuelType(), FuelType.class);
        if (assertThat(fuelType, is(nullValue()))) {
            errors.rejectValue("vehicle.engine.fuelType", HttpStatus.BAD_REQUEST.toString(), "Select valid fuel type!");
            return;
        }

        PowerType powerType = EnumUtils.fromString(engine.getPowerType(), PowerType.class);
        if (assertThat(powerType, is(nullValue()))) {
            errors.rejectValue("vehicle.engine.powerType", HttpStatus.BAD_REQUEST.toString(), "Select valid power type!");
            return;
        }

        Integer powerOutput = engine.getPower();
        if (assertThat(powerOutput, is(nullValue()))
                || assertThat(powerOutput, lessThanInt(0))
                || assertThat(powerOutput, greaterThanInt(3000))) {

            errors.rejectValue("vehicle.engine.power", HttpStatus.BAD_REQUEST.toString(), "Input valid power!");
        }

        Transmission transmission = EnumUtils.fromString(vehicle.getTransmission(), Transmission.class);
        if (assertThat(transmission, is(nullValue()))) {
            errors.rejectValue("vehicle.transmission", HttpStatus.BAD_REQUEST.toString(), "Select valid transmission!");
            return;
        }

        Long locationId = offerBindingModel.getLocation();
        if (assertThat(locationId, is(nullValue()))) {
            boolean isLocation = locationService.getAllLocations()
                    .stream()
                    .anyMatch(locationServiceModel -> locationServiceModel.getId().equals(locationId));

            if (!isLocation) {
                errors.rejectValue("location", HttpStatus.BAD_REQUEST.toString(), "Select valid location!");
                return;
            }
        }

        Integer month = vehicle.getMonth();
        if (assertThat(month, is(nullValue()))
                || assertThat(month, lessThanInt(1))
                || assertThat(month, greaterThanInt(12))) {

            errors.rejectValue("vehicle.month", HttpStatus.BAD_REQUEST.toString(), "Select valid month!");
            return;
        }

        Integer year = vehicle.getYear();
        if (assertThat(year, is(nullValue()))
                || assertThat(year, lessThanInt(1930))
                || assertThat(year, greaterThanInt(LocalDateTime.now().getYear()))) {

            errors.rejectValue("vehicle.year", HttpStatus.BAD_REQUEST.toString(), "Select valid year!");
            return;
        }

        Integer mileage = vehicle.getMileage();
        if (assertThat(mileage, is(nullValue()))
                || assertThat(mileage, isNegativeInt())
                || assertThat(mileage, greaterThanInt(TWO_MILLION))) {

            errors.rejectValue("vehicle.mileage", HttpStatus.BAD_REQUEST.toString(), "Input valid mileage!");
            return;
        }

        Integer price = offerBindingModel.getPrice();
        if (assertThat(price, is(nullValue()))
                || assertThat(price, isNegativeInt())) {

            errors.rejectValue("price", HttpStatus.BAD_REQUEST.toString(), "Input valid price!");
            return;
        }

        Drive drive = EnumUtils.fromString(vehicle.getDrive(), Drive.class);
        if (assertThat(vehicle.getDrive(), is(notNullValue()))
                && !vehicle.getDrive().isEmpty()
                && assertThat(drive, is(nullValue()))) {

            errors.rejectValue("vehicle.drive", HttpStatus.BAD_REQUEST.toString(), "Select valid drive!");
            return;
        }

        Integer cylinders = engine.getCylinders();
        if (assertThat(engine.getCylinders(), is(notNullValue())) && !CYLINDERS_COUNTS.contains(cylinders)) {
            errors.rejectValue("vehicle.engine.cylinders", HttpStatus.BAD_REQUEST.toString(), "Select valid cylinders count!");
            return;
        }

        Integer doors = vehicle.getDoors();
        if (assertThat(vehicle.getDoors(), is(notNullValue())) && !DOORS_COUNTS.contains(doors)) {
            errors.rejectValue("vehicle.doors", HttpStatus.BAD_REQUEST.toString(), "Select valid doors count!");
            return;
        }

        Integer seats = vehicle.getSeats();
        if (assertThat(vehicle.getState(), is(notNullValue()))
                && (assertThat(seats, lessThanInt(MIN_COUNT)) || assertThat(seats, greaterThanInt(MAX_SEATS_COUNT)))) {
            errors.rejectValue("vehicle.seats", HttpStatus.BAD_REQUEST.toString(), "Select valid seats count!");
            return;
        }

        List<ColorServiceModel> allColors = colorService.getAllColors();

        Long interiorColorId = vehicle.getInteriorColor();
        if (assertThat(interiorColorId, is(notNullValue()))) {
            boolean isColor = allColors.stream()
                    .anyMatch(colorServiceModel -> colorServiceModel.getId().equals(interiorColorId));

            if (!isColor) {
                errors.rejectValue("vehicle.interiorColor", HttpStatus.BAD_REQUEST.toString(), "Select valid interior color!");
                return;
            }
        }

        Long exteriorColorId = vehicle.getInteriorColor();
        if (assertThat(exteriorColorId, is(notNullValue()))) {
            boolean isColor = allColors.stream()
                    .anyMatch(colorServiceModel -> colorServiceModel.getId().equals(exteriorColorId));

            if (!isColor) {
                errors.rejectValue("vehicle.exteriorColor", HttpStatus.BAD_REQUEST.toString(), "Select valid exterior color!");
                return;
            }
        }

        EuroStandard euroStandard = EnumUtils.fromString(engine.getEuroStandard(), EuroStandard.class);
        if (assertThat(engine.getEuroStandard(), is(notNullValue()))
                && !engine.getEuroStandard().isEmpty()
                && assertThat(euroStandard, is(nullValue()))) {

            errors.rejectValue("vehicle.engine.euroStandard", HttpStatus.BAD_REQUEST.toString(), "Select valid euro standard!");
            return;
        }

        Integer owners = vehicle.getOwners();
        if (assertThat(owners, is(notNullValue())) && (assertThat(owners, isNegativeInt()))) {
            errors.rejectValue("vehicle.owners", HttpStatus.BAD_REQUEST.toString(), "Input valid owners count!");
            return;
        }

        List<MultipartFile> photos = offerBindingModel.getFiles();
        if (assertThat(photos, is(notNullValue()))) {
            boolean isNoPhoto = offerBindingModel.getFiles()
                    .stream()
                    .allMatch(file -> file.getSize() <= 0);

            if (isNoPhoto) {
                errors.rejectValue("files", HttpStatus.BAD_REQUEST.toString(), "Upload at least one photo!");
                return;
            }
        }

        String description = offerBindingModel.getDescription();
        if (assertThat(description, is(notNullValue())) && assertThat(description.length(), greaterThanInt(MAX_DESCRIPTION_LENGTH))) {
            errors.rejectValue("description", HttpStatus.BAD_REQUEST.toString(), "Description too long!");
            return;
        }

        String modification = engine.getModification();
        if (assertThat(modification, is(notNullValue())) && assertThat(modification.length(), greaterThanInt(MAX_MODIFICATION_LENGTH))) {
            errors.rejectValue("vehicle.engine.modification", HttpStatus.BAD_REQUEST.toString(), "Modification text too long!");
            return;
        }

        List<Long> featuresIds = vehicle.getFeatures();
        if (assertThat(featuresIds, is(notNullValue()))) {
            List<Long> featureIdsFromDB = featureService.getAllFeatures()
                    .stream()
                    .map(FeatureServiceModel::getId)
                    .collect(Collectors.toList());

            for (Long featuresId : featuresIds) {
                if (!featureIdsFromDB.contains(featuresId)) {
                    errors.rejectValue("vehicle.features", HttpStatus.BAD_REQUEST.toString(), "Invalid feature selected!");
                    return;
                }
            }
        }
    }
}

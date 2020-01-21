package bg.autohouse.web.rest;

import bg.autohouse.data.models.enums.*;
import bg.autohouse.service.models.ColorServiceModel;
import bg.autohouse.service.models.FeatureServiceModel;
import bg.autohouse.service.models.LocationServiceModel;
import bg.autohouse.service.services.ColorService;
import bg.autohouse.service.services.FeatureService;
import bg.autohouse.service.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SearchRestController {

    private final FeatureService featureService;
    private final LocationService locationService;
    private final ColorService colorService;

    @Autowired
    public SearchRestController(FeatureService featureService, LocationService locationService, ColorService colorService) {
        this.featureService = featureService;
        this.locationService = locationService;
        this.colorService = colorService;
    }

    @GetMapping("/features/all")
    @ResponseStatus(HttpStatus.OK)
    public List<FeatureServiceModel> getAllFeatures() {
        return featureService.getAllFeatures();
    }

    @GetMapping("/bodyStyles/all")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, BodyStyle> getAllBodYStyles() {
        return EnumUtils.ENUM_MAP(BodyStyle.class);
    }

    @GetMapping("/drivetrain/all")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Drive> getAllDriveTrains() {
        return EnumUtils.ENUM_MAP(Drive.class);
    }

    @GetMapping("/orderBy/all")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String > getAllSortTypes() {
        return OrderBy.getTextAndValue();
    }

    @GetMapping("/euroStandard/all")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, EuroStandard> getAllEuroStandards() {
        return EnumUtils.ENUM_MAP(EuroStandard.class);
    }

    @GetMapping("/locations/all")
    @ResponseStatus(HttpStatus.OK)
    public List<LocationServiceModel> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/colors/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ColorServiceModel> getAllColors() {
        return colorService.getAllColors();
    }

}

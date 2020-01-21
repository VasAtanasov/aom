package bg.autohouse.web.rest;

import bg.autohouse.data.models.enums.EnumUtils;
import bg.autohouse.data.models.enums.FuelType;
import bg.autohouse.data.models.enums.Transmission;
import bg.autohouse.service.models.MakerNameServiceModel;
import bg.autohouse.service.models.MakerServiceModel;
import bg.autohouse.service.services.MakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
public class HomeRestController {

    private final MakerService makerService;

    @Autowired
    public HomeRestController(MakerService makerService) {
        this.makerService = makerService;
    }

    @GetMapping("/makers/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MakerServiceModel getModelsForMaker(@PathVariable Long id) {
        return makerService.getModelsForMaker(id);
    }

    @GetMapping("/makers/all")
    @ResponseStatus(HttpStatus.OK)
    public List<MakerNameServiceModel> getAllMakers() {
        return makerService.getAllMakers();
    }

    @GetMapping("/fuelTypes/all")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, FuelType> getFuelTypes() {
        return EnumUtils.ENUM_MAP(FuelType.class);
    }

    @GetMapping("/gears/all")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Transmission> getGears() {
        return EnumUtils.ENUM_MAP(Transmission.class);
    }

}

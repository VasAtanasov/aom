package bg.autohouse.service.services;

import bg.autohouse.service.models.MakerNameServiceModel;
import bg.autohouse.service.models.MakerServiceModel;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MakerService {
    MakerServiceModel getModelsForMaker(Long id);

    MakerServiceModel findMakerByName(String name);

    List<MakerNameServiceModel> getAllMakers();

    boolean isMaker(Long id);
}

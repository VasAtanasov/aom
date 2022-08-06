package bg.autohouse.spider.client;

import bg.autohouse.spider.domain.dto.cg.CarsTrimsWrapper;
import bg.autohouse.spider.domain.dto.cg.EngineDTO;
import bg.autohouse.spider.domain.dto.cg.MakersModelsWrapper;
import bg.autohouse.spider.domain.dto.cg.ModelsCarsWrapper;
import bg.autohouse.spider.domain.dto.cg.OptionDTO;
import bg.autohouse.spider.domain.dto.cg.TransmissionWrapper;
import java.util.List;
import java.util.Map;

public interface CGApiClientAdapter {

  MakersModelsWrapper makers();

  ModelsCarsWrapper maker(String id);

  CarsTrimsWrapper modelCars(String id);

  TransmissionWrapper trimTransmissions(String trimId);

  List<EngineDTO> trimEngines(String trimId);

  Map<String, List<OptionDTO>> trimOptions(String trimId);
}

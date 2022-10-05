package bg.autohouse.spider.client;

import bg.autohouse.bg.api.car.CarsTrimsWrapper;
import bg.autohouse.bg.api.engine.EngineDTO;
import bg.autohouse.bg.api.maker.MakersModelsWrapper;
import bg.autohouse.bg.api.model.ModelsCarsWrapper;
import bg.autohouse.bg.api.option.OptionDTO;
import bg.autohouse.bg.api.transmission.TransmissionWrapper;
import bg.autohouse.spider.domain.dto.cg.ListingDTO;

import java.util.List;
import java.util.Map;

public interface CGApiClientAdapter {

  MakersModelsWrapper makers();

  ModelsCarsWrapper maker(String id);

  CarsTrimsWrapper modelCars(String id);

  TransmissionWrapper trimTransmissions(String trimId);

  List<EngineDTO> trimEngines(String trimId);

  Map<String, List<OptionDTO>> trimOptions(String trimId);

  Page<ListingDTO> searchListings(String zip, int distance, String entity, PageRequest pageRequest);
}

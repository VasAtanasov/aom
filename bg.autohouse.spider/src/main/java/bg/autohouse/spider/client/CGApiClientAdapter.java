package bg.autohouse.spider.client;

import bg.autohouse.api.dto.car.CarsTrimsWrapper;
import bg.autohouse.api.dto.engine.EngineDTO;
import bg.autohouse.api.dto.maker.MakersModelsWrapper;
import bg.autohouse.api.dto.model.ModelsCarsWrapper;
import bg.autohouse.api.dto.option.OptionDTO;
import bg.autohouse.api.dto.transmission.TransmissionWrapper;
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

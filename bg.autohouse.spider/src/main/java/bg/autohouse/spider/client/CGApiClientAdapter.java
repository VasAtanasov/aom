package bg.autohouse.spider.client;

import bg.autohouse.spider.domain.dto.cg.*;

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

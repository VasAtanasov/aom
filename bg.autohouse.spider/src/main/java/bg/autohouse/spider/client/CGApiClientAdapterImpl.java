package bg.autohouse.spider.client;

import bg.autohouse.api.dto.car.CarsTrimsWrapper;
import bg.autohouse.api.dto.engine.EngineDTO;
import bg.autohouse.api.dto.maker.MakersModelsWrapper;
import bg.autohouse.api.dto.model.ModelsCarsWrapper;
import bg.autohouse.api.dto.option.OptionDTO;
import bg.autohouse.api.dto.transmission.TransmissionWrapper;
import bg.autohouse.spider.domain.dto.cg.ListingDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class CGApiClientAdapterImpl implements CGApiClientAdapter {
  private static final Logger log = LoggerFactory.getLogger(CGApiClientAdapterImpl.class);
  private final CGApiClientImpl client;

  public CGApiClientAdapterImpl(CGApiClientImpl client) {
    this.client = client;
  }

  @Override
  public MakersModelsWrapper makers() {
    return client.makers().GET().body();
  }

  @Override
  public ModelsCarsWrapper maker(String id) {
    return client.maker(id).GET().body();
  }

  @Override
  public CarsTrimsWrapper modelCars(String id) {
    return client.modelCars(id).GET().body();
  }

  @Override
  public TransmissionWrapper trimTransmissions(String trimId) {
    return client.trimTransmissions(trimId).GET().body();
  }

  @Override
  public List<EngineDTO> trimEngines(String trimId) {
    return client.trimEngines(trimId).GET().body();
  }

  @Override
  public Map<String, List<OptionDTO>> trimOptions(String trimId) {
    var form = FormEntity.form("trim", trimId);
    var body = new RequestBody.FormRequestBody(form);
    return client.trimOptions(trimId).POST(body).body();
  }

  @Override
  public Page<ListingDTO> searchListings(
      String zip, int distance, String entity, PageRequest pageRequest) {
    QueryParameter[] queryParameters =
        new QueryParameter[] {
          QueryParameter.of("zip", zip),
          QueryParameter.of("distance", String.valueOf(distance)),
          QueryParameter.of("offset", String.valueOf(pageRequest.offset())),
          QueryParameter.of("maxResults", String.valueOf(pageRequest.pageSize())),
          QueryParameter.of("entitySelectingHelper.selectedEntity", entity)
        };
    return new Page<>(client.listingsSearch().GET(queryParameters).body(), pageRequest);
  }
}

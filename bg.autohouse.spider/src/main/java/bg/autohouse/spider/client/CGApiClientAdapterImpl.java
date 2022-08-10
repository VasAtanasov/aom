package bg.autohouse.spider.client;

import bg.autohouse.spider.domain.dto.cg.*;

import java.util.List;
import java.util.Map;

public class CGApiClientAdapterImpl implements CGApiClientAdapter {
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
  public List<ListingDTO> searchListings(int offset, int maxResults, String entity) {
    QueryParameter[] queryParameters =
        new QueryParameter[] {
          QueryParameter.of("offset", String.valueOf(offset)),
          QueryParameter.of("maxResults", String.valueOf(maxResults)),
          QueryParameter.of("entitySelectingHelper.selectedEntity", entity)
        };
    return client.listingsSearch().GET(queryParameters).body();
  }
}

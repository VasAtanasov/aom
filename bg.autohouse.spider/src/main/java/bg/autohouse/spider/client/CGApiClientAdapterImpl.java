package bg.autohouse.spider.client;

import bg.autohouse.spider.domain.dto.cg.CarsTrimsWrapper;
import bg.autohouse.spider.domain.dto.cg.EngineDTO;
import bg.autohouse.spider.domain.dto.cg.MakersModelsWrapper;
import bg.autohouse.spider.domain.dto.cg.ModelsCarsWrapper;
import bg.autohouse.spider.domain.dto.cg.OptionDTO;
import bg.autohouse.spider.domain.dto.cg.TransmissionWrapper;
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
}

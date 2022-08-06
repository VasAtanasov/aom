package bg.autohouse.spider.client;

import bg.autohouse.spider.domain.dto.cg.CarsTrimsWrapper;
import bg.autohouse.spider.domain.dto.cg.EngineDTO;
import bg.autohouse.spider.domain.dto.cg.MakersModelsWrapper;
import bg.autohouse.spider.domain.dto.cg.ModelsCarsWrapper;
import bg.autohouse.spider.domain.dto.cg.TransmissionWrapper;
import java.util.List;

public class CGApiClientAdapter {
  private final CGApiClientImpl client;

  public CGApiClientAdapter(CGApiClientImpl client) {
    this.client = client;
  }

  public MakersModelsWrapper makers() {
    return client.makers().GET().body();
  }

  public ModelsCarsWrapper maker(String id) {
    return client.maker(id).GET().body();
  }

  public CarsTrimsWrapper modelCars(String id) {
    return client.modelCars(id).GET().body();
  }

  public TransmissionWrapper trimTransmissions(String trimId) {
    return client.trimTransmissions(trimId).GET().body();
  }

  public List<EngineDTO> trimEngines(String trimId) {
    return client.trimEngines(trimId).GET().body();
  }

  public String trimOptions(String trimId) {
    var form = FormEntity.form("trim", trimId);
    var body = new RequestBody.FormRequestBody(form);
    return client.trimOptions(trimId).POST(body).body();
  }
}

package bg.autohouse.spider.client.endpoint.cg;

import bg.autohouse.spider.client.RequestBuilder;
import bg.autohouse.spider.client.Response;
import bg.autohouse.spider.client.ResponseBodyHandler;
import bg.autohouse.spider.client.api.ApiClient;
import bg.autohouse.spider.client.endpoint.AbstractEndpoint;
import bg.autohouse.spider.domain.dto.cg.CarTrimsDTO;
import bg.autohouse.spider.domain.dto.cg.CarsTrimsWrapper;

import java.util.List;

public class ModelEndpoint extends AbstractEndpoint
{
    public static final String CARS_TRIMS_URL =
            "getSelectedModelCarTrimsAJAX.action?showInactive=true&useInventoryService=false&quotableCarsOnly=false&localCountryCarsOnly=false&outputFormat=REACT&model=";

    public ModelEndpoint(ApiClient client, String modelId)
    {
        super(client, CARS_TRIMS_URL + modelId);
    }

    public List<CarTrimsDTO> cars()
    {
        var metadata = RequestBuilder.get(endpoint()).build();
        ResponseBodyHandler<CarsTrimsWrapper> handler = ResponseBodyHandler.BodyHandlers.ofJson(CarsTrimsWrapper.class);
        Response<CarsTrimsWrapper> response = http().call(metadata, handler);
        return response.body().getCars();
    }
}

package bg.autohouse.spider.client.endpoint.cg;

import bg.autohouse.spider.client.RequestBuilder;
import bg.autohouse.spider.client.Response;
import bg.autohouse.spider.client.ResponseBodyHandler;
import bg.autohouse.spider.api.ApiClient;
import bg.autohouse.spider.client.endpoint.AbstractEndpoint;
import bg.autohouse.spider.domain.dto.cg.TransmissionWrapper;

public class TrimTransmissionEndpoint extends AbstractEndpoint
{
    public static final String TRIM_TRANSMISSION_URL =
            "getTransmissionListTrimFirstJson.action?showInactive=true&useInventoryService=false&quotableCarsOnly=false&localCountryCarsOnly=false&outputFormat=REACT&trim=";

    public TrimTransmissionEndpoint(ApiClient client, String trimId)
    {
        super(client, TRIM_TRANSMISSION_URL + trimId);
    }

    public TransmissionWrapper transmissions()
    {
        var metadata = RequestBuilder.get(endpoint()).build();
        ResponseBodyHandler<TransmissionWrapper> handler = ResponseBodyHandler.BodyHandlers.ofJson(TransmissionWrapper.class);
        Response<TransmissionWrapper> response = http().call(metadata, handler);
        return response.body();
    }
}

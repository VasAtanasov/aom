package bg.autohouse.spider.client.endpoint;

import bg.autohouse.spider.client.JavaHttpClientExecutor;
import bg.autohouse.spider.client.Request;
import bg.autohouse.spider.client.RequestUtil;
import bg.autohouse.spider.client.Response;
import bg.autohouse.spider.client.api.CoreClient;
import bg.autohouse.spider.client.api.Endpoint;
import bg.autohouse.spider.client.api.GetOperation;
import bg.autohouse.spider.domain.dto.cg.MakerDTO;
import com.fasterxml.jackson.core.type.TypeReference;

import java.net.URI;
import java.net.http.HttpResponse;

public class MakerCGEndpoint extends Endpoint implements GetOperation<MakerDTO>
{
    public MakerCGEndpoint(CoreClient client, String url, String makerId)
    {
        super(client, url + "/" + makerId);
    }

    @Override
    public MakerDTO httpGet()
    {
        URI uri = URI.create(getEndpoint());
        HttpResponse<String> response = RequestUtil.getAsString(uri);

        JavaHttpClientExecutor executor = new JavaHttpClientExecutor();
        Response<String> response1 =executor.execute(new Request());

        return RequestUtil.fromJSON(getClient().getMapper(), new TypeReference<>() {}, response.body());
    }
}

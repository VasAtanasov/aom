package bg.autohouse.spider.client.endpoint;

import bg.autohouse.spider.client.RequestUtil;
import bg.autohouse.spider.client.api.CoreClient;
import bg.autohouse.spider.client.api.Endpoint;
import bg.autohouse.spider.client.api.GetCollectionOperation;
import bg.autohouse.spider.domain.dto.cg.MakerDTO;
import bg.autohouse.spider.domain.dto.cg.MakersModelsWrapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.List;

public class MakerCollectionCGEndpoint extends Endpoint implements GetCollectionOperation<MakerDTO>
{
    public MakerCollectionCGEndpoint(CoreClient client, String endpoint)
    {
        super(client, endpoint);
    }

    @Override
    public List<MakerDTO> httpCollectionGet()
    {
        URI uri = URI.create(getEndpoint());
        HttpResponse<String> response = RequestUtil.getAsString(uri);
        MakersModelsWrapper collection = RequestUtil.fromJSON(getClient().getMapper(), new TypeReference<>() {}, response.body());
        return collection.getAllMakerModels().get("makers");

    }
}

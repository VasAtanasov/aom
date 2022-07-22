package bg.autohouse.spider.client;

import bg.autohouse.spider.client.api.CoreClient;
import bg.autohouse.spider.client.endpoint.MakerCGEndpoint;
import bg.autohouse.spider.client.endpoint.MakerCollectionCGEndpoint;
import bg.autohouse.spider.util.json.ObjectMapperFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import static bg.autohouse.spider.constants.Constant.MAKER_MODEL_URL;
import static bg.autohouse.spider.constants.Constant.MODELS_FOR_MAKER_URL;

public class CGClient implements CoreClient
{
    private static final ObjectMapper mapper = ObjectMapperFactory.mapper();

    private final String CORE_ENDPOINT;

    public CGClient(String coreEndpoint)
    {
        this.CORE_ENDPOINT = coreEndpoint;
    }

    public MakerCollectionCGEndpoint makers()
    {
        String endpoint = CORE_ENDPOINT + "/" + MAKER_MODEL_URL;
        return new MakerCollectionCGEndpoint(this, endpoint);
    }

    public MakerCGEndpoint maker(String id)
    {
        String endpoint = CORE_ENDPOINT + "/" + MODELS_FOR_MAKER_URL;
        return new MakerCGEndpoint(this, endpoint, id);
    }

    @Override
    public ObjectMapper getMapper()
    {
        return mapper;
    }
}

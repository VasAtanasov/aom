package bg.autohouse.spider;


import bg.autohouse.spider.client.CGApiClient;
import bg.autohouse.spider.client.HttpStrategy;
import bg.autohouse.spider.client.JavaHttpClientStrategy;
import bg.autohouse.spider.domain.dto.cg.MakerDTO;
import bg.autohouse.spider.util.json.ObjectMapperFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

@Slf4j
public class SpiderApplication
{

    public static void main(String[] args)

    {
        log.info("Running SpiderApplication");
        log.info("Initializing CG Client");
        HttpStrategy httpStrategy = new JavaHttpClientStrategy();
        ObjectMapper objectMapper = ObjectMapperFactory.mapper();
        CGApiClient cg = new CGApiClient(httpStrategy, objectMapper);

        log.info("Getting all makers");
        var makers = cg.makers().makers()
                .stream()
                .filter(MakerDTO::isPopular)
                .collect(Collectors.toSet());

        for (MakerDTO makerDTO : makers)
        {
            log.info("Getting models for maker {}", makerDTO.getName());
            var makerId = makerDTO.getId();
            var models = cg.maker(makerId).models();
            int a = 5;
        }

    }
}

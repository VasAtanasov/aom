package bg.autohouse.spider;


import bg.autohouse.spider.client.CGClient;
import bg.autohouse.spider.constants.Constant;
import bg.autohouse.spider.domain.dto.cg.MakerDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

@Slf4j
public class SpiderApplication
{

    public static void main(String[] args)

    {
        log.info("Running SpiderApplication");
        log.info("Initializing CG Client");
        CGClient client = new CGClient(Constant.CG_CORE_ENDPOINT);
        var makers = client.makers().httpCollectionGet()
                .stream()
                .filter(MakerDTO::isPopular)
                .collect(Collectors.toSet());
        int a = 5;
    }
}

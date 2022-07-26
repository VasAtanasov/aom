package bg.autohouse.spider;


import bg.autohouse.spider.client.CGApiClient;
import bg.autohouse.spider.client.HttpStrategy;
import bg.autohouse.spider.client.JavaHttpClientStrategy;
import bg.autohouse.spider.domain.dto.cg.*;
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

        int b = 10;

        log.info("Getting all makers");
        var makers = cg.makers().makers()
                .stream()
                .filter(MakerDTO::isPopular)
                .collect(Collectors.toSet());

        for (MakerDTO makerDTO : makers)
        {
            log.info("Getting models for maker {}", makerDTO.getName());
            var makerId = makerDTO.getId();
            var makerName = makerDTO.getName();
            var models = cg.maker(makerId).models();
            for (ModelCarsDTO modelDTO : models)
            {
                var modelName = makerDTO.getModels().stream()
                        .filter(m -> m.getId().equals(modelDTO.getId()))
                        .findFirst().map(ModelDTO::getName)
                        .orElse("UNIDENTIFIED");

                log.info("Getting cars for model {}", modelName);
                var modelId = modelDTO.getId();
                var cars = cg.modelCars(modelId).cars();

                for (CarTrimsDTO carTrimsDTO : cars)
                {
                    var carId = carTrimsDTO.getId();
                    var carName = modelDTO.getCars()
                            .stream()
                            .filter(c -> c.getId().equals(carId))
                            .findFirst()
                            .map(CarDTO::getYear)
                            .orElse(9999);

                    for (TrimDTO trim : carTrimsDTO.getTrims())
                    {
                        var trimId = trim.getId();
                        var trimName = trim.getName();
                        var transmission = cg.trimTransmissions(trimId).transmissions();
                        var engines = cg.trimEngines(trimId).engines();
                        var options = cg.trimOptions(trimId).options();
                        int a = 5;
                        break;
                    }


                }
            }
        }

    }
}

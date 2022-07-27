package bg.autohouse.spider;


import bg.autohouse.spider.cache.SpiderCacheManager;
import bg.autohouse.spider.client.CGApiClient;
import bg.autohouse.spider.client.HttpStrategy;
import bg.autohouse.spider.client.JavaHttpClientStrategy;
import bg.autohouse.spider.domain.dto.cg.*;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
public class SpiderApplication
{

    public static void main(String[] args)

    {
        log.info("Running SpiderApplication");
        log.info("Initializing CG Client");
        HttpStrategy httpStrategy = new JavaHttpClientStrategy();
        CGApiClient cg = new CGApiClient(httpStrategy);

        try (SpiderCacheManager cacheManager = new SpiderCacheManager())
        {
            Cache<String, MakersModelsWrapper> makersCache = cacheManager.makersCache();
            Cache<String, ModelsCarsWrapper> makerModelsCache = cacheManager.makerModelsCache();

            long start = System.currentTimeMillis();
            log.info("Getting all makers");

            var makers = Optional.ofNullable(makersCache.get("all_makers"))
                    .map(makersModelsWrapper -> {
                        log.info("Retrieving makers list from cache");
                        return makersModelsWrapper.makers();
                    })
                    .orElseGet(() -> {
                        var wrapper = cg.makers().makers();
                        makersCache.put("all_makers", wrapper);
                        return wrapper.makers()
                                .stream()
                                .filter(MakerDTO::isPopular)
                                .collect(Collectors.toList());
                    });

            var futureMakersModels = makers.stream()
                    .map(m -> {
                        var makerId = m.getId();
                        var modelsCarsWrapper = makerModelsCache.get(makerId);
                        if (modelsCarsWrapper != null)
                        {
                            log.info("Returning from cache: ");
                            return CompletableFuture.supplyAsync(() -> modelsCarsWrapper);
                        }
                        return CompletableFuture.supplyAsync(() -> {
                            var modelsWrapper = cg.maker(makerId).models();
                            makerModelsCache.put(makerId, modelsWrapper);
                            return modelsWrapper;
                        });
                    })
//                    .map(cf -> cf.thenApply(modelsCarsWrapper -> ))
                    .collect(Collectors.toList());

//            for (MakerDTO makerDTO : makers)
            //            {
            //                log.info("Getting models for maker {}", makerDTO.getName());
            //                var makerId = makerDTO.getId();
            //                var makerName = makerDTO.getName();
            //                var models = cg.maker(makerId).models();
            //                for (ModelCarsDTO modelDTO : models)
            //                {
            //                    var modelName = makerDTO.getModels().stream()
            //                            .filter(m -> m.getId().equals(modelDTO.getId()))
            //                            .findFirst().map(ModelDTO::getName)
            //                            .orElse("UNIDENTIFIED");
            //
            //                    log.info("Getting cars for model {}", modelName);
            //                    var modelId = modelDTO.getId();
            //                    var cars = cg.modelCars(modelId).cars();
            //
            //                    for (CarTrimsDTO carTrimsDTO : cars)
            //                    {
            //                        var carId = carTrimsDTO.getId();
            //                        var carName = modelDTO.getCars()
            //                                .stream()
            //                                .filter(c -> c.getId().equals(carId))
            //                                .findFirst()
            //                                .map(CarDTO::getYear)
            //                                .orElse(9999);
            //
            //                        for (TrimDTO trim : carTrimsDTO.getTrims())
            //                        {
            //                            var trimId = trim.getId();
            //                            var trimName = trim.getName();
            //                            var transmission = cg.trimTransmissions(trimId).transmissions();
            //                            var engines = cg.trimEngines(trimId).engines();
            //                            var options = cg.trimOptions(trimId).options();
            //                            int a = 5;
            //                            break;
            //                        }
            //
            //
            //                    }
            //                }
            //            }


            var modelsCars = futureMakersModels.stream()
                    .map(CompletableFuture::join)
                    .parallel()
                    .map(ModelsCarsWrapper::getModels)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());


            long end = System.currentTimeMillis();

            log.info(String.format("The operation took %s ms%n", end - start));

        }

    }
}

package bg.autohouse.spider.service;

import bg.autohouse.spider.client.CGApiClientAdapter;
import bg.autohouse.spider.domain.dto.cg.CarDTO;
import bg.autohouse.spider.domain.dto.cg.CarTrimsDTO;
import bg.autohouse.spider.domain.dto.cg.CarsTrimsWrapper;
import bg.autohouse.spider.domain.dto.cg.EngineDTO;
import bg.autohouse.spider.domain.dto.cg.MakerDTO;
import bg.autohouse.spider.domain.dto.cg.MakersModelsWrapper;
import bg.autohouse.spider.domain.dto.cg.ModelCarsDTO;
import bg.autohouse.spider.domain.dto.cg.OptionDTO;
import bg.autohouse.spider.domain.dto.cg.TransmissionWrapper;
import bg.autohouse.spider.domain.dto.cg.TrimDTO;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CGService {
  private static final Logger log = LoggerFactory.getLogger(CGService.class);

  private final CGApiClientAdapter clientAdapter;

  public CGService(CGApiClientAdapter clientAdapter) {
    this.clientAdapter = clientAdapter;
  }

  public void crawl() {
    MakersModelsWrapper wrapper = clientAdapter.makers();
    List<MakerDTO> makers = wrapper.makers();
    ExecutorService executor = Executors.newFixedThreadPool(10);
    long start = System.currentTimeMillis();
    var futureMakersModels =
        makers.stream()
            .filter(MakerDTO::isPopular)
            .map(MakerDTO::getId)
            .map(id -> CompletableFuture.supplyAsync(() -> clientAdapter.maker(id), executor))
            .toList();

    List<ModelCarsDTO> models =
        futureMakersModels.stream()
            .map(CompletableFuture::join)
            .flatMap(l -> l.getModels().stream())
            .toList();

    for (ModelCarsDTO modelDTO : models) {
      String modelId = modelDTO.getId();
      CarsTrimsWrapper carsTrimsWrapper = clientAdapter.modelCars(modelId);
      List<CarTrimsDTO> cars = carsTrimsWrapper.getCars();
      for (CarTrimsDTO carTrimsDTO : cars) {
        var carId = carTrimsDTO.getId();
        var carName =
            modelDTO.getCars().stream()
                .filter(c -> c.getId().equals(carId))
                .findFirst()
                .map(CarDTO::getYear)
                .orElse(9999);

        log.info("Getting trims for car {}", carName);
        for (TrimDTO trim : carTrimsDTO.getTrims()) {
          var trimId = trim.getId();
          var trimName = trim.getName();
          TransmissionWrapper transmission = clientAdapter.trimTransmissions(trimId);
          List<EngineDTO> engines = clientAdapter.trimEngines(trimId);
          Map<String, List<OptionDTO>> optionsMap = clientAdapter.trimOptions(trimId);
          int a = 5;
        }
//        break;
      }
      break;
    }

    long end = System.currentTimeMillis();
    log.info(String.format("The operation took %s ms%n", end - start));
    executor.shutdown();
  }
}

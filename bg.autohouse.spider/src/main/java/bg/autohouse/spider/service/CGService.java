package bg.autohouse.spider.service;

import bg.autohouse.spider.client.CGApiClientAdapter;
import bg.autohouse.spider.domain.dto.cg.CarDTO;
import bg.autohouse.spider.domain.dto.cg.CarTrimsDTO;
import bg.autohouse.spider.domain.dto.cg.CarsTrimsWrapper;
import bg.autohouse.spider.domain.dto.cg.EngineDTO;
import bg.autohouse.spider.domain.dto.cg.MakerDTO;
import bg.autohouse.spider.domain.dto.cg.MakersModelsWrapper;
import bg.autohouse.spider.domain.dto.cg.ModelCarsDTO;
import bg.autohouse.spider.domain.dto.cg.ModelsCarsWrapper;
import bg.autohouse.spider.domain.dto.cg.OptionDTO;
import bg.autohouse.spider.domain.dto.cg.TransmissionWrapper;
import bg.autohouse.spider.domain.dto.cg.TrimDTO;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CGService implements AutoCloseable {
  private static final Logger log = LoggerFactory.getLogger(CGService.class);

  private final CGApiClientAdapter clientAdapter;
  private final ExecutorService executor;

  public CGService(CGApiClientAdapter clientAdapter, int nThreads) {
    this.clientAdapter = clientAdapter;
    executor = Executors.newFixedThreadPool(nThreads);
  }

  private Supplier<String> taskSupplier(String makerId) {
    return () -> {
      ModelsCarsWrapper makersWrapper = clientAdapter.maker(makerId);
      List<ModelCarsDTO> models = makersWrapper.getModels();

      try {
        List<CompletableFuture<String>> futureModels =
            models.stream()
                .map(m -> CompletableFuture.supplyAsync(modelTaskSupplier(m), executor))
                .toList();

        List<String> completedFuture = futureModels.stream()
            .map(CompletableFuture::join)
            .toList();

        return "DONE";
      } catch (Exception e) {
        return "Error with maker id: " + makerId;
      }
    };
  }

  private Supplier<String> modelTaskSupplier(ModelCarsDTO modelDTO) {
    return () -> {
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
          //            break;
        }
      }
      //        break;
      return "DONE " + modelId;
    };
  }

  public void crawl() {
    long start = System.currentTimeMillis();
    MakersModelsWrapper wrapper = clientAdapter.makers();
    List<MakerDTO> makers =
        wrapper.makers().stream().filter(MakerDTO::isPopular).skip(6).limit(5).toList();
    List<CompletableFuture<String>> futureMakersModels =
        makers.stream()
            .map(MakerDTO::getId)
            //            .map(id -> CompletableFuture.supplyAsync(taskSupplier(id), executor))
            .map(makerId -> CompletableFuture.completedFuture(taskSupplier(makerId).get()))
            .toList();

    List<String> completedFuture =
        futureMakersModels.stream()
            .map(CompletableFuture::join)
            .filter(s -> s.startsWith("Error"))
            .toList();

    completedFuture.forEach(log::error);

    long end = System.currentTimeMillis();
    log.info(String.format("The operation took %s ms%n", end - start));
  }

  @Override
  public void close() {
    executor.shutdown();
  }
}

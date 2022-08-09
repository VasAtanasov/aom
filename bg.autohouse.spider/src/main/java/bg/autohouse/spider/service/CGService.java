package bg.autohouse.spider.service;

import bg.autohouse.spider.client.CGApiClientAdapter;
import bg.autohouse.spider.domain.dto.cg.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CGService implements AutoCloseable {
  private static final Logger log = LoggerFactory.getLogger(CGService.class);

  private final CGApiClientAdapter clientAdapter;
  private final ExecutorService executor;

  public CGService(CGApiClientAdapter clientAdapter, int nThreads) {
    this.clientAdapter = clientAdapter;
    executor = Executors.newFixedThreadPool(nThreads);
  }

  private Supplier<List<TrimFullDTO>> makerTaskSupplier(final MakerDTO maker) {
    return () -> {
      ModelsCarsWrapper makersWrapper = clientAdapter.maker(maker.getId());
      List<ModelCarsDTO> models = makersWrapper.getModels();

      Map<String, String> modelNamesById =
          maker.getModels().stream()
              .collect(
                  Collectors.toMap(ModelDTO::getId, ModelDTO::getName, (a, b) -> a, HashMap::new));

      try {
        List<CompletableFuture<List<TrimFullDTO>>> futureModels =
            models.stream()
                .map(
                    model ->
                        CompletableFuture.supplyAsync(
                            modelTaskSupplier(
                                model,
                                modelNamesById.computeIfAbsent(model.getId(), s -> "UNIDENTIFIED"),
                                maker),
                            executor))
                .toList();

        return futureModels.stream()
            .map(CompletableFuture::join)
            .flatMap(Collection::stream)
            .toList();
      } catch (Exception e) {
        return List.of();
      }
    };
  }

  private Supplier<List<TrimFullDTO>> modelTaskSupplier(
      ModelCarsDTO modelDTO, String modelName, MakerDTO maker) {
    return () -> {
      List<TrimFullDTO> trims = new ArrayList<>();

      final TrimFullDTO.TrimFullBuilder builder =
          TrimFullDTO.builder().makerId(maker.getId()).makerName(maker.getName());

      Map<String, Integer> carNamesById =
          modelDTO.getCars().stream()
              .collect(Collectors.toMap(CarDTO::getId, CarDTO::getYear, (a, b) -> a, HashMap::new));

      String modelId = modelDTO.getId();

      builder.modelName(modelName).modelId(modelId);

      CarsTrimsWrapper carsTrimsWrapper = clientAdapter.modelCars(modelId);
      List<CarTrimsDTO> cars = carsTrimsWrapper.getCars();
      for (CarTrimsDTO carTrimsDTO : cars) {
        var carId = carTrimsDTO.getId();
        var carName = carNamesById.computeIfAbsent(carId, s -> 9999);

        builder.carName(carName).carId(carId);

        log.info("Getting trims for car {}", carName);
        for (TrimDTO trim : carTrimsDTO.getTrims()) {
          var trimId = trim.getId();
          var trimName = trim.getName();
          builder.trimName(trimName).trimId(trimId);

          var transmissionFuture =
              CompletableFuture.supplyAsync(() -> clientAdapter.trimTransmissions(trimId));
          var enginesFuture =
              CompletableFuture.supplyAsync(() -> clientAdapter.trimEngines(trimId));
          var optionsMapFuture =
              CompletableFuture.supplyAsync(() -> clientAdapter.trimOptions(trimId));

          CompletableFuture.allOf(transmissionFuture, enginesFuture, optionsMapFuture);

          try {
            TransmissionWrapper transmission = transmissionFuture.get();
            List<EngineDTO> engines = enginesFuture.get();
            Map<String, List<OptionDTO>> optionsMap = optionsMapFuture.get();
            builder
                .transmissions(transmission.getTrimSpecific())
                .engines(engines)
                .options(optionsMap);

            TrimFullDTO trimFullDTO = builder.build();
            trims.add(trimFullDTO);
          } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
          }

          int a = 5;
        }
      }

      return trims;
    };
  }

  public List<TrimFullDTO> updateMakersModels() {
    long start = System.currentTimeMillis();
    MakersModelsWrapper wrapper = clientAdapter.makers();
    List<MakerDTO> makers =
        wrapper.makers().stream().filter(MakerDTO::isPopular).skip(26).limit(5).toList();
    List<CompletableFuture<List<TrimFullDTO>>> futureMakersModels =
        makers.stream().map(m -> CompletableFuture.completedFuture(makerTaskSupplier(m).get())).toList();

    List<TrimFullDTO> completedFuture =
        futureMakersModels.stream()
            .map(CompletableFuture::join)
            .flatMap(Collection::stream)
            .toList();

    long end = System.currentTimeMillis();
    log.info(String.format("The operation took %s ms%n", end - start));

    return completedFuture;
  }

  @Override
  public void close() {
    executor.shutdown();
  }
}

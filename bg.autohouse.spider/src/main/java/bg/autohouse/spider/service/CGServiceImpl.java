package bg.autohouse.spider.service;

import bg.autohouse.bg.api.car.CarDTO;
import bg.autohouse.bg.api.car.CarTrimsDTO;
import bg.autohouse.bg.api.car.CarsTrimsWrapper;
import bg.autohouse.bg.api.engine.EngineDTO;
import bg.autohouse.bg.api.maker.MakerDTO;
import bg.autohouse.bg.api.model.ModelCarsDTO;
import bg.autohouse.bg.api.model.ModelDTO;
import bg.autohouse.bg.api.option.OptionDTO;
import bg.autohouse.bg.api.transmission.TransmissionWrapper;
import bg.autohouse.bg.api.trim.TrimDTO;
import bg.autohouse.spider.client.CGApiClientAdapter;
import bg.autohouse.spider.client.Page;
import bg.autohouse.spider.client.PageRequest;
import bg.autohouse.spider.domain.dto.cg.*;
import bg.autohouse.spider.util.F;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class CGServiceImpl implements CGService {
  private static final Logger log = LoggerFactory.getLogger(CGServiceImpl.class);

  private final CGApiClientAdapter clientAdapter;
  private final ExecutorService makerExecutor;
  private final ExecutorService modelExecutor;
  private final ExecutorService cachedExecutor;

  public CGServiceImpl(CGApiClientAdapter clientAdapter, int nThreads) {
    this.clientAdapter = clientAdapter;
    int modelsThreads = nThreads * 5;
    makerExecutor = Executors.newFixedThreadPool(nThreads);
    modelExecutor = Executors.newFixedThreadPool(modelsThreads);
    cachedExecutor =
        Executors.newCachedThreadPool(
            new ThreadFactory() {
              private final AtomicLong COUNTER = new AtomicLong(0);

              @Override
              public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "CGService-thread-" + COUNTER.getAndIncrement());
                thread.setDaemon(true);
                return thread;
              }
            });
  }

  @Override
  public void close() {
    makerExecutor.shutdown();
    modelExecutor.shutdown();
    cachedExecutor.shutdown();
  }

  @Override
  public List<MakerDTO> fetchMakers() {
    return clientAdapter.makers().makers();
  }

  @Override
  public List<ModelCarsDTO> fetchMakerModels(String makerId) {
    return clientAdapter.maker(makerId).getModels();
  }

  @Override
  public List<TrimFullDTO> fetchTrims(final MakerDTO maker) {
    final Map<String, String> modelNamesById =
        F.toHashMap(maker.getModels(), ModelDTO::getId, ModelDTO::getName);

    List<ModelCarsDTO> models =
        fetchMakerModels(maker.getId()).stream()
            .peek(
                model -> {
                  model.setModelName(modelNamesById.get(model.getId()));
                  model.setMakerId(maker.getId());
                  model.setMakerName(maker.getName());
                })
            .toList();

    return models.stream()
        .map(m -> CompletableFuture.supplyAsync(() -> fetchTrims(m), modelExecutor))
        .map(CompletableFuture::join)
        .flatMap(Collection::stream)
        .toList();
  }

  @Override
  public List<TrimFullDTO> fetchTrims(ModelCarsDTO model) {
    Map<String, Integer> carNamesById =
        F.toHashMap(model.getCars(), CarDTO::getId, CarDTO::getYear);

    final TrimBuilder builder =
        TrimBuilder.builder()
            .makerId(model.getMakerId())
            .makerName(model.getMakerName())
            .modelId(model.getId())
            .modelName(model.getModelName());

    List<TrimFullDTO> trims = new ArrayList<>();
    CarsTrimsWrapper carsTrimsWrapper = clientAdapter.modelCars(model.getId());
    List<CarTrimsDTO> cars = carsTrimsWrapper.getCars();

    if (cars.isEmpty()) trims.add(builder.build());

    for (CarTrimsDTO carTrimsDTO : cars) {
      var carId = carTrimsDTO.getId();
      var carName = carNamesById.computeIfAbsent(carId, s -> 9999);

      builder.carName(carName).carId(carId);

      for (TrimDTO trim : carTrimsDTO.getTrims()) {
        var trimId = trim.getId();
        var trimName = trim.getName();
        builder.trimName(trimName).trimId(trimId);

        log.info(
            "======================================= {} =======================================",
            builder.buildTitle());

        var transmissionFuture =
            CompletableFuture.supplyAsync(
                () -> clientAdapter.trimTransmissions(trimId), cachedExecutor);
        var enginesFuture =
            CompletableFuture.supplyAsync(() -> clientAdapter.trimEngines(trimId), cachedExecutor);
        var optionsMapFuture =
            CompletableFuture.supplyAsync(() -> clientAdapter.trimOptions(trimId), cachedExecutor);

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
      }
    }

    return trims;
  }

  @Override
  public List<TrimFullDTO> fetchTrims(List<MakerDTO> makers) {
    return makers.stream()
        .map(m -> CompletableFuture.supplyAsync(() -> fetchTrims(m), makerExecutor))
        .map(CompletableFuture::join)
        .flatMap(Collection::stream)
        .toList();
  }

  @Override
  public Page<ListingDTO> fetchListings(String zip, int distance, String entity, PageRequest page) {
    return clientAdapter.searchListings(zip, distance, entity, page);
  }

  @Override
  public List<ListingDTO> fetchListings(String zip, int distance, List<MakerDTO> makers) {
    return makers.stream()
        .map(MakerDTO::getId)
        .map(makerId -> listingsTaskSupplier(zip, distance, makerId, 5))
        .map(supplier -> CompletableFuture.supplyAsync(supplier, makerExecutor))
        .map(CompletableFuture::join)
        .flatMap(Collection::stream)
        .toList();
  }

  @Override
  public List<ListingDTO> fetchListings(String zip, int distance, MakerDTO maker) {
    return listingsTaskSupplier(zip, distance, maker.getId(), 5).get();
  }

  private Supplier<List<ListingDTO>> listingsTaskSupplier(
      String zip, int distance, String entity, int totalPages) {
    return () ->
        IntStream.iterate(0, pageNumber -> pageNumber + 1)
            .limit(totalPages)
            .mapToObj(PageRequest::of)
            .map(
                page ->
                    CompletableFuture.supplyAsync(
                        () -> fetchListings(zip, distance, entity, page), cachedExecutor))
            .map(CompletableFuture::join)
            .flatMap(Page::stream)
            .toList();
  }
}

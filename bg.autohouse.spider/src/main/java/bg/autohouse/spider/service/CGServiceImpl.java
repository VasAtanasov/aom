package bg.autohouse.spider.service;

import bg.autohouse.spider.client.CGApiClientAdapter;
import bg.autohouse.spider.client.Page;
import bg.autohouse.spider.client.PageRequest;
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
import java.util.stream.IntStream;

public class CGServiceImpl implements CGService, AutoCloseable {
  private static final Logger log = LoggerFactory.getLogger(CGServiceImpl.class);

  private final CGApiClientAdapter clientAdapter;
  private final ExecutorService makerExecutor;
  private final ExecutorService modelExecutor;
  private final ExecutorService cachedExecutor;

  private final int makerThreads;

  public CGServiceImpl(CGApiClientAdapter clientAdapter, int nThreads) {
    this.clientAdapter = clientAdapter;
    this.makerThreads = nThreads;
    int modelsThreads = makerThreads * 5;
    makerExecutor = Executors.newFixedThreadPool(makerThreads);
    modelExecutor = Executors.newFixedThreadPool(modelsThreads);
    cachedExecutor = Executors.newCachedThreadPool();
  }

  public List<MakerDTO> fetchMakers() {
    return clientAdapter.makers().makers();
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
                            modelExecutor))
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
              CompletableFuture.supplyAsync(
                  () -> clientAdapter.trimTransmissions(trimId), cachedExecutor);
          var enginesFuture =
              CompletableFuture.supplyAsync(
                  () -> clientAdapter.trimEngines(trimId), cachedExecutor);
          var optionsMapFuture =
              CompletableFuture.supplyAsync(
                  () -> clientAdapter.trimOptions(trimId), cachedExecutor);

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
    };
  }

  public List<TrimFullDTO> updateMakersModels() {
    long start = System.currentTimeMillis();
    MakersModelsWrapper wrapper = clientAdapter.makers();

    List<MakerDTO> makers =
        wrapper.makers().stream().filter(MakerDTO::isPopular).skip(26).limit(makerThreads).toList();

    List<CompletableFuture<List<TrimFullDTO>>> futureMakersModels =
        makers.stream()
            .map(this::makerTaskSupplier)
            .map(supplier -> CompletableFuture.supplyAsync(supplier, makerExecutor))
            .toList();

    List<TrimFullDTO> completedFuture =
        futureMakersModels.stream()
            .map(CompletableFuture::join)
            .flatMap(Collection::stream)
            .toList();

    long end = System.currentTimeMillis();
    log.info(String.format("The operation took %s ms%n", end - start));

    return completedFuture;
  }

  private Supplier<List<ListingDTO>> listingsTaskSupplier(
      String zip, int distance, String entity, int totalPages) {
    return () ->
        IntStream.iterate(0, pageNumber -> pageNumber + 1)
            .limit(totalPages)
            .mapToObj(PageRequest::of)
            .map(page -> fetchListingsPageAsync(zip, distance, entity, page))
            .map(CompletableFuture::join)
            .flatMap(Page::stream)
            .toList();
  }

  private CompletableFuture<Page<ListingDTO>> fetchListingsPageAsync(
      String zip, int distance, String entity, PageRequest page) {
    return CompletableFuture.supplyAsync(
        () -> fetchListingsPage(zip, distance, entity, page), cachedExecutor);
  }

  public Page<ListingDTO> fetchListingsPage(
      String zip, int distance, String entity, PageRequest page) {
    return clientAdapter.searchListings(zip, distance, entity, page);
  }

  public List<ListingDTO> fetchListings(List<MakerDTO> makers) {

    final String zip = "10001";
    final int distance = 150;

    var listingsFuture =
        makers.stream()
            .map(MakerDTO::getId)
            .map(makerId -> listingsTaskSupplier(zip, distance, makerId, 5))
            .map(supplier -> CompletableFuture.supplyAsync(supplier, makerExecutor))
            .toList();

    List<ListingDTO> listings =
        listingsFuture.stream().map(CompletableFuture::join).flatMap(Collection::stream).toList();

    int a = 5;

    return listings;
  }

  @Override
  public void close() {
    makerExecutor.shutdown();
    modelExecutor.shutdown();
    cachedExecutor.shutdown();
  }
}

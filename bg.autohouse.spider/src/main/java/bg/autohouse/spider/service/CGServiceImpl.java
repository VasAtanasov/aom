package bg.autohouse.spider.service;

import bg.autohouse.spider.client.CGApiClientAdapter;
import bg.autohouse.spider.client.Page;
import bg.autohouse.spider.client.PageRequest;
import bg.autohouse.spider.domain.dto.cg.*;
import bg.autohouse.spider.util.F;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class CGServiceImpl implements CGService {
  private static final Logger log = LoggerFactory.getLogger(CGServiceImpl.class);

  private final CGApiClientAdapter clientAdapter;
  private final ExecutorService makerExecutor;
  private final ExecutorService modelExecutor;
  private final ExecutorService cachedExecutor;
  private final Map<String, TrimBuilder> builders = new HashMap<>();

  private final TrimTree trimTree = new TrimTree();

  @Override
  public void close() {
    makerExecutor.shutdown();
    modelExecutor.shutdown();
    cachedExecutor.shutdown();
  }

  private enum NodeType {
    MAKER,
    MODEL,
    CAR,
    TRIM
  }

  private static class TrimTree {
    private static Map<String, TrimNode> nodes;
  }

  private static class TrimNode {
    private final String id;
    private final NodeType nodeType;
    private final TrimFullDTO trim;
    private final Map<String, TrimFullDTO> children = new HashMap<>();

    public TrimNode(String id, NodeType nodeType, TrimFullDTO trim) {
      this.id = id;
      this.nodeType = nodeType;
      this.trim = trim;
    }
  }

  private static class Model {
    String id;
    ModelCarsDTO cars;
    String name;
    MakerDTO maker;

    static Model of(String id, String name, ModelCarsDTO cars, MakerDTO maker) {
      Model model = new Model();
      model.id = id;
      model.cars = cars;
      model.name = name;
      model.maker = maker;
      return model;
    }
  }

  public CGServiceImpl(CGApiClientAdapter clientAdapter, int nThreads) {
    this.clientAdapter = clientAdapter;
    int modelsThreads = nThreads * 5;
    makerExecutor = Executors.newFixedThreadPool(nThreads);
    modelExecutor = Executors.newFixedThreadPool(modelsThreads);
    cachedExecutor = Executors.newCachedThreadPool();
  }

  public List<MakerDTO> fetchMakers() {
    return clientAdapter.makers().makers();
  }

  public List<ModelCarsDTO> fetchMakerModels(String makerId) {
    return clientAdapter.maker(makerId).getModels();
  }

  public CompletableFuture<List<ModelCarsDTO>> fetchMakerModelsAsync(String makerId) {
    return CompletableFuture.supplyAsync(() -> fetchMakerModels(makerId), cachedExecutor);
  }

  public Page<ListingDTO> fetchListingsPage(
      String zip, int distance, String entity, PageRequest page) {
    return clientAdapter.searchListings(zip, distance, entity, page);
  }

  public CompletableFuture<Page<ListingDTO>> fetchListingsPageAsync(
      String zip, int distance, String entity, PageRequest page) {
    return CompletableFuture.supplyAsync(
        () -> fetchListingsPage(zip, distance, entity, page), cachedExecutor);
  }

  private String getModelName(Map<String, String> modelNamesById, String modelId) {
    return modelNamesById.computeIfAbsent(modelId, s -> "UNIDENTIFIED");
  }

  private Supplier<List<TrimFullDTO>> makerTaskSupplier(final MakerDTO maker) {
    return () -> {
      final Map<String, String> modelNamesById =
          F.toHashMap(maker.getModels(), ModelDTO::getId, ModelDTO::getName);

      List<ModelCarsDTO> models = fetchMakerModels(maker.getId());

      try {

        return models.stream()
            .peek(
                model -> {
                  var makerBuilder =
                      builders.computeIfAbsent(
                          maker.getId(),
                          makerId ->
                              TrimBuilder.toBuilder(TrimBuilder::makerId, TrimBuilder::makerName)
                                  .apply(makerId, maker.getName()));

                  builders.computeIfAbsent(
                      model.getId(),
                      modelId ->
                          TrimBuilder.toBuilder(
                                  TrimBuilder::modelId,
                                  TrimBuilder::modelName,
                                  () -> new TrimBuilder(makerBuilder))
                              .apply(modelId, getModelName(modelNamesById, modelId)));
                })
            .map(this::modelTrimsAsync)
            .map(CompletableFuture::join)
            .flatMap(Collection::stream)
            .toList();

      } catch (Exception e) {
        return List.of();
      }
    };
  }

  private CompletableFuture<List<TrimFullDTO>> modelTrimsAsync(ModelCarsDTO model) {
    return CompletableFuture.supplyAsync(modelTrimsTaskSupplier(model), modelExecutor);
  }

  private Supplier<List<TrimFullDTO>> modelTrimsTaskSupplier(ModelCarsDTO model) {
    return () -> {
      Map<String, Integer> carNamesById =
          F.toHashMap(model.getCars(), CarDTO::getId, CarDTO::getYear);

      final TrimBuilder builder = builders.get(model.getId());

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

  public List<TrimFullDTO> updateAllTrims(List<MakerDTO> makers) {
    long start = System.currentTimeMillis();

    List<CompletableFuture<List<TrimFullDTO>>> futureMakersModels =
        makers.stream()
            //            .peek(
            //                maker ->
            //                    builders.computeIfAbsent(
            //                        maker.getId(),
            //                        makerId ->
            //                            TrimBuilder.toBuilder(TrimBuilder::makerId,
            // TrimBuilder::makerName)
            //                                .apply(makerId, maker.getName())))
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
}

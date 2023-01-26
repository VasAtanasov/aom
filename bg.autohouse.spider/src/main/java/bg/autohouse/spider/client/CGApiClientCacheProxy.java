package bg.autohouse.spider.client;

import bg.autohouse.api.dto.engine.EnginesList;
import bg.autohouse.api.dto.option.OptionsWrapper;
import bg.autohouse.spider.cache.SpiderCacheManager;
import bg.autohouse.api.dto.car.CarsTrimsWrapper;
import bg.autohouse.api.dto.engine.EngineDTO;
import bg.autohouse.api.dto.maker.MakersModelsWrapper;
import bg.autohouse.api.dto.model.ModelsCarsWrapper;
import bg.autohouse.api.dto.option.OptionDTO;
import bg.autohouse.api.dto.transmission.TransmissionWrapper;
import bg.autohouse.spider.domain.dto.cg.ListingDTO;
import org.ehcache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class CGApiClientCacheProxy implements CGApiClientAdapter {
  private static final Logger log = LoggerFactory.getLogger(CGApiClientCacheProxy.class);
  private static final String MAKERS_KEY = "all_makers";

  private final CGApiClientAdapterImpl client;
  private final SpiderCacheManager cacheManager;

  public CGApiClientCacheProxy(CGApiClientAdapterImpl client, SpiderCacheManager cacheManager) {
    this.client = client;
    this.cacheManager = cacheManager;
  }

  @Override
  public MakersModelsWrapper makers() {
    Cache<String, MakersModelsWrapper> makersCache = cacheManager.makersCache();
    MakersModelsWrapper makers = makersCache.get(MAKERS_KEY);
    if (makers != null) {
      log.info("Retrieving makers list from cache");
      return makers;
    }
    makers = client.makers();
    makersCache.put("all_makers", makers);
    log.info("Fetching makers");
    return makers;
  }

  @Override
  public ModelsCarsWrapper maker(String makerId) {
    Cache<String, ModelsCarsWrapper> makerModelsCache = cacheManager.makerModelsCache();
    var modelsCarsWrapper = makerModelsCache.get(makerId);
    if (modelsCarsWrapper != null) {
      log.info("Returning from cache: " + makerId);
      return modelsCarsWrapper;
    }
    log.info("Fetching model: " + makerId);
    modelsCarsWrapper = client.maker(makerId);
    makerModelsCache.put(makerId, modelsCarsWrapper);
    return modelsCarsWrapper;
  }

  @Override
  public CarsTrimsWrapper modelCars(String modelId) {
    Cache<String, CarsTrimsWrapper> carsTrimsWrapperCache = cacheManager.carsTrimsCache();
    CarsTrimsWrapper wrapper = carsTrimsWrapperCache.get(modelId);
    if (wrapper != null) {
      log.info("Returning model from cache: " + modelId);
      return wrapper;
    }
    wrapper = client.modelCars(modelId);
    carsTrimsWrapperCache.put(modelId, wrapper);
    return wrapper;
  }

  @Override
  public TransmissionWrapper trimTransmissions(String trimId) {
    Cache<String, TransmissionWrapper> transmissionWrapperCache = cacheManager.transmissionsCache();
    TransmissionWrapper wrapper = transmissionWrapperCache.get(trimId);
    if (wrapper != null) return wrapper;
    wrapper = client.trimTransmissions(trimId);
    transmissionWrapperCache.put(trimId, wrapper);
    return wrapper;
  }

  @Override
  public List<EngineDTO> trimEngines(String trimId) {
    Cache<String, EnginesList> enginesListCache = cacheManager.enginesCache();
    EnginesList enginesList = enginesListCache.get(trimId);
    if (enginesList != null) return enginesList.getEngines();
    List<EngineDTO> engines = client.trimEngines(trimId);
    enginesListCache.put(trimId, new EnginesList(engines));
    return engines;
  }

  @Override
  public Map<String, List<OptionDTO>> trimOptions(String trimId) {
    Cache<String, OptionsWrapper> optionsCache = cacheManager.optionsCache();
    OptionsWrapper wrapper = optionsCache.get(trimId);
    if (wrapper != null) {
      return wrapper.getOptionsMap();
    }
    Map<String, List<OptionDTO>> options = client.trimOptions(trimId);
    optionsCache.put(trimId, new OptionsWrapper(options));
    return options;
  }

  @Override
  public Page<ListingDTO> searchListings(
      String zip, int distance, String entity, PageRequest pageRequest) {
    return client.searchListings(zip, distance, entity, pageRequest);
  }
}

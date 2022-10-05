package bg.autohouse.spider.service;

import bg.autohouse.bg.api.maker.MakerDTO;
import bg.autohouse.bg.api.model.ModelCarsDTO;
import bg.autohouse.spider.client.Page;
import bg.autohouse.spider.client.PageRequest;
import bg.autohouse.spider.domain.dto.cg.ListingDTO;
import bg.autohouse.spider.domain.dto.cg.TrimFullDTO;

import java.util.List;

public interface CGService extends AutoCloseable {

  @Override
  default void close() {}

  List<MakerDTO> fetchMakers();

  List<ModelCarsDTO> fetchMakerModels(String makerId);

  List<TrimFullDTO> fetchTrims(MakerDTO maker);

  List<TrimFullDTO> fetchTrims(ModelCarsDTO model);

  List<TrimFullDTO> fetchTrims(List<MakerDTO> makers);

  Page<ListingDTO> fetchListings(String zip, int distance, String entity, PageRequest page);

  List<ListingDTO> fetchListings(String zip, int distance, List<MakerDTO> makers);

  List<ListingDTO> fetchListings(String zip, int distance, MakerDTO maker);
}

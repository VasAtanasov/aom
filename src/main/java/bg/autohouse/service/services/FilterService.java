package bg.autohouse.service.services;

import bg.autohouse.service.models.FilterServiceModel;
import bg.autohouse.web.models.request.FilterRequest;
import java.util.List;
import java.util.UUID;

public interface FilterService {
  UUID createFilter(FilterRequest filterRequest);

  void saveSearch(FilterRequest filterRequest, UUID id);

  List<FilterServiceModel> listSavedSearches(UUID userId);

  boolean deleteSavedSearch(UUID filterId, UUID userId);
}

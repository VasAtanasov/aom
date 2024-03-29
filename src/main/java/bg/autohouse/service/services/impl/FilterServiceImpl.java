package bg.autohouse.service.services.impl;

import bg.autohouse.data.models.Filter;
import bg.autohouse.data.models.User;
import bg.autohouse.data.repositories.FilterRepository;
import bg.autohouse.data.repositories.UserRepository;
import bg.autohouse.errors.NotFoundException;
import bg.autohouse.service.models.FilterServiceModel;
import bg.autohouse.service.services.FilterService;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.models.request.FilterRequest;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class FilterServiceImpl implements FilterService {

  private final UserRepository userRepository;
  private final FilterRepository filterRepository;
  private final ModelMapperWrapper modelMapper;

  @Override
  @Transactional
  public UUID createFilter(FilterRequest filterRequest) {
    Objects.requireNonNull(filterRequest);
    Filter filter = modelMapper.map(filterRequest, Filter.class);
    filterRepository.save(filter);
    return filter.getId();
  }

  @Override
  @Transactional
  public void saveSearch(FilterRequest filterRequest, UUID id) {
    Objects.requireNonNull(filterRequest);
    User user = userRepository.findUserById(id).orElseThrow(NotFoundException::new);
    Filter filter = modelMapper.map(filterRequest, Filter.class);
    user.getFilters().add(filter);
    filter.setUser(user);
  }

  @Override
  @Transactional(readOnly = true)
  public List<FilterServiceModel> listSavedSearches(UUID userId) {
    List<Filter> savedSearches = filterRepository.findAllByUserId(userId);
    return modelMapper.mapAll(savedSearches, FilterServiceModel.class);
  }

  @Override
  @Transactional
  public boolean deleteSavedSearch(UUID filterId, UUID userId) {
    User user = userRepository.findUserById(userId).orElseThrow(NotFoundException::new);
    return user.getFilters().removeIf(f -> Objects.requireNonNull(f.getId()).equals(filterId));
  }
}

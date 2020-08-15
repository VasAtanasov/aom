package bg.autohouse.util;

import bg.autohouse.data.repositories.FilterRepository;
import bg.autohouse.errors.NotFoundException;
import bg.autohouse.service.models.FilterServiceModel;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SearchOffersKeyGenerator implements KeyGenerator {

  private final FilterRepository filterRepository;
  private final ModelMapperWrapper modelMapper;

  @Override
  @Transactional(readOnly = true)
  public Object generate(Object target, Method method, Object... params) {
    UUID filterId = (UUID) params[0];
    Pageable pageable = (Pageable) params[1];
    List<Object> objects =
        new ArrayList<>(
            List.of(
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().toString()));
    FilterServiceModel filter =
        modelMapper.map(
            filterRepository.findFilterById(filterId).orElseThrow(NotFoundException::new),
            FilterServiceModel.class);
    filter.setId(null);
    filter.setUserId(null);
    objects.add(filter.toString());
    return new SimpleKey(objects);
  }
}

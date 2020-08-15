package bg.autohouse.util;

import bg.autohouse.data.models.BaseUuidEntity;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class PageableKeyGenerator implements KeyGenerator {

  @Override
  public Object generate(Object target, Method method, Object... params) {
    List<Object> objects = new ArrayList<>();
    if (params[0] instanceof BaseUuidEntity) {
      objects.add(Objects.requireNonNull(((BaseUuidEntity) params[0]).getId()).toString());
    } else {
      objects.add(params[0].toString());
    }
    for (Object param : params) {
      if (param instanceof Pageable) {
        Pageable pageable = (Pageable) param;
        objects.addAll(
            Arrays.asList(
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().toString()));
      }
    }
    return new SimpleKey(objects);
  }
}

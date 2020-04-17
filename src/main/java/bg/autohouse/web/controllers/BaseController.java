package bg.autohouse.web.controllers;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseController {

  protected static final String USER_PROFILE_IMAGE_FOLDER = "user-profile-images";
  protected static final int DEFAULT_PAGE_SIZE = 20;
  protected static final int DEFAULT_PAGE_NUMBER = 0;
  protected static final String DEFAULT_SORT = "createdAt,desc";
  protected static final String SORT = "createdAt";

  protected <T> ImmutableMap<String, List<T>> toMap(final String key, final List<T> objects) {
    return ImmutableMap.of(key, objects);
  }

  protected ImmutableMap<String, Object> toMap(final String key, final Object objects) {
    return ImmutableMap.of(key, objects);
  }
}

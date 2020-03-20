package bg.autohouse.web.controllers;

import bg.autohouse.util.Assert;
import bg.autohouse.web.models.response.ApiResponseModel;
import com.google.common.collect.ImmutableMap;
import java.net.URI;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseController {

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

  protected ResponseEntity<?> handleCreateSuccess(Object payload, String message, String path) {

    final HttpStatus status = HttpStatus.CREATED;

    URI location =
        ServletUriComponentsBuilder.fromCurrentContextPath().path(path).buildAndExpand().toUri();

    return ResponseEntity.created(location)
        .body(
            ApiResponseModel.builder()
                .success(Boolean.TRUE)
                .message(Assert.has(message) ? message : status.getReasonPhrase())
                .data(payload)
                .status(status.value())
                .build());
  }

  protected ResponseEntity<?> handleRequestSuccess(Object payload, String message) {

    final HttpStatus status = HttpStatus.OK;

    return ResponseEntity.ok()
        .body(
            ApiResponseModel.builder()
                .success(Boolean.TRUE)
                .message(Assert.has(message) ? message : status.getReasonPhrase())
                .data(payload)
                .status(status.value())
                .build());
  }
}

package bg.autohouse.web.controllers;

import bg.autohouse.web.models.response.ApiResponseModel;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public abstract class BaseController {

  static final String APP_V1_MEDIA_TYPE_JSON = "application/bg.autohouse.api-v1+json";

  protected ResponseEntity<?> handleCreateSuccess(Object payload, String message, String path) {

    URI location =
        ServletUriComponentsBuilder.fromCurrentContextPath().path(path).buildAndExpand().toUri();

    return ResponseEntity.created(location)
        .body(
            ApiResponseModel.builder()
                .success(Boolean.TRUE)
                .message(message)
                .payload(payload)
                .status(HttpStatus.OK.getReasonPhrase())
                .build());
  }

  protected ResponseEntity<?> handleRequestSuccess(Object payload, String message) {
    return ResponseEntity.ok()
        .body(
            ApiResponseModel.builder()
                .success(Boolean.TRUE)
                .message(message)
                .payload(payload)
                .status(HttpStatus.OK.getReasonPhrase())
                .build());
  }
}

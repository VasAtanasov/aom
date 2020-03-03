package bg.autohouse.web.controllers;

import bg.autohouse.web.models.response.ApiResponseModel;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

  protected ResponseEntity<?> handleRequestError(BindingResult result, String message) {
    Map<String, String> errorMap = new HashMap<>();

    for (FieldError error : result.getFieldErrors()) {
      errorMap.put(error.getField(), error.getDefaultMessage());
    }

    ApiResponseModel response =
        ApiResponseModel.builder()
            .success(Boolean.FALSE)
            .message(message)
            .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .payload(errorMap)
            .build();

    return ResponseEntity.badRequest().body(response);
  }
}

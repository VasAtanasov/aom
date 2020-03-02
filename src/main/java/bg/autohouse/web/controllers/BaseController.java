package bg.autohouse.web.controllers;

import bg.autohouse.web.models.response.ApiResponseModel;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public abstract class BaseController {

  protected ResponseEntity<?> handleSuccessRequest(Object payload, String message) {
    return ResponseEntity.ok()
        .body(
            ApiResponseModel.builder()
                .success(Boolean.TRUE)
                .timestamp(LocalDateTime.now())
                .message(message)
                .payload(payload)
                .status(HttpStatus.OK.getReasonPhrase())
                .build());
  }

  protected ResponseEntity<?> handleBadRequest(BindingResult result, String message) {
    Map<String, String> errorMap = new HashMap<>();

    for (FieldError error : result.getFieldErrors()) {
      errorMap.put(error.getField(), error.getDefaultMessage());
    }

    ApiResponseModel response =
        ApiResponseModel.builder()
            .success(Boolean.FALSE)
            .timestamp(LocalDateTime.now())
            .message(message)
            .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .payload(errorMap)
            .build();

    return ResponseEntity.badRequest().body(response);
  }
}

package bg.autohouse.web.controllers;

import bg.autohouse.errors.models.ValidationErrorModel;
import bg.autohouse.web.models.response.ApiResponseModel;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

    Set<ValidationErrorModel> errors =
        Stream.concat(
                result.getFieldErrors().stream()
                    .map(
                        err ->
                            ValidationErrorModel.builder()
                                .errorCode(err.getCode())
                                .fieldName(err.getField())
                                .rejectedValue(err.getRejectedValue())
                                .params(collectArguments(err.getArguments()))
                                .message(err.getDefaultMessage())
                                .build()),
                result.getGlobalErrors().stream()
                    .map(
                        err ->
                            ValidationErrorModel.builder()
                                .errorCode(err.getCode())
                                .params(collectArguments(err.getArguments()))
                                .message(err.getDefaultMessage())
                                .build()))
            .collect(Collectors.toSet());

    ApiResponseModel response =
        ApiResponseModel.builder()
            .success(Boolean.FALSE)
            .message(message)
            .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .payload(errors)
            .build();

    return ResponseEntity.badRequest().body(response);
  }

  private List<String> collectArguments(Object[] arguments) {
    return arguments == null
        ? Collections.emptyList()
        : Stream.of(arguments).skip(1).map(Object::toString).collect(Collectors.toList());
  }
}

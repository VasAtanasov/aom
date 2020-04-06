package bg.autohouse.web.util;

import static org.springframework.http.HttpStatus.*;

import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.response.ResponseWrapper;
import java.net.URI;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@UtilityClass
public class RestUtil {

  public static ResponseEntity<ResponseWrapper> errorResponse(
      HttpStatus httpCode, RestMessage message) {

    ResponseWrapper response =
        ResponseWrapper.builder()
            .success(Boolean.FALSE)
            .message(String.valueOf(message))
            .status(httpCode.value())
            .build();

    return new ResponseEntity<>(response, httpCode);
  }

  public static ResponseEntity<ResponseWrapper> errorResponse(RestMessage restMessage) {
    return errorResponse(BAD_REQUEST, restMessage);
  }

  public static ResponseEntity<ResponseWrapper> errorResponseWithData(
      RestMessage message, Object data) {

    ResponseWrapper response =
        ResponseWrapper.builder()
            .success(Boolean.FALSE)
            .message(String.valueOf(message))
            .status(BAD_REQUEST.value())
            .errors(data)
            .build();

    return new ResponseEntity<>(response, BAD_REQUEST);
  }

  // public static ResponseEntity<ResponseWrapper> accessDeniedResponse() {
  //   return new ResponseEntity<>(
  //       new ResponseWrapperImpl(FORBIDDEN, RestMessage.PERMISSION_DENIED, RestStatus.FAILURE),
  //       FORBIDDEN);
  // }

  // public static ResponseEntity<ResponseWrapper> lackPermissionResponse(Permission permission) {
  //   return new ResponseEntity<>(
  //       new PermissionLackingWrapper(FORBIDDEN, permission, RestStatus.FAILURE), FORBIDDEN);
  // }

  public static ResponseEntity<ResponseWrapper> messageOkayResponse(RestMessage message) {

    ResponseWrapper response =
        ResponseWrapper.builder()
            .success(Boolean.TRUE)
            .message(String.valueOf(message))
            .status(OK.value())
            .build();

    return new ResponseEntity<>(response, OK);
  }

  public static ResponseEntity<ResponseWrapper> okayResponseWithData(
      RestMessage message, Object data) {

    ResponseWrapper response =
        ResponseWrapper.builder()
            .success(Boolean.TRUE)
            .message(String.valueOf(message))
            .data(data)
            .status(OK.value())
            .build();

    return new ResponseEntity<>(response, OK);
  }

  public static ResponseEntity<ResponseWrapper> createSuccessResponse(
      Object data, RestMessage message, String path) {

    URI location =
        ServletUriComponentsBuilder.fromCurrentContextPath().path(path).buildAndExpand().toUri();

    ResponseWrapper response =
        ResponseWrapper.builder()
            .success(Boolean.TRUE)
            .message(String.valueOf(message))
            .status(CREATED.value())
            .build();

    return ResponseEntity.created(location).body(response);
  }
}

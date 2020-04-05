package bg.autohouse.web.util;

import static org.springframework.http.HttpStatus.*;

import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.enums.RestStatus;
import bg.autohouse.web.models.wrappers.GenericResponseWrapper;
import bg.autohouse.web.models.wrappers.ResponseWrapper;
import bg.autohouse.web.models.wrappers.ResponseWrapperImpl;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@UtilityClass
public class RestUtil {

  public static ResponseEntity<ResponseWrapper> errorResponse(
      HttpStatus httpCode, RestMessage message) {
    return new ResponseEntity<>(
        new ResponseWrapperImpl(httpCode, message, RestStatus.FAILURE), httpCode);
  }

  public static ResponseEntity<ResponseWrapper> errorResponse(RestMessage restMessage) {
    return errorResponse(HttpStatus.BAD_REQUEST, restMessage);
  }

  public static ResponseEntity<ResponseWrapper> accessDeniedResponse() {
    return new ResponseEntity<>(
        new ResponseWrapperImpl(FORBIDDEN, RestMessage.PERMISSION_DENIED, RestStatus.FAILURE),
        FORBIDDEN);
  }

  // public static ResponseEntity<ResponseWrapper> lackPermissionResponse(Permission permission) {
  //   return new ResponseEntity<>(
  //       new PermissionLackingWrapper(FORBIDDEN, permission, RestStatus.FAILURE), FORBIDDEN);
  // }

  public static ResponseEntity<ResponseWrapper> messageOkayResponse(RestMessage message) {
    return new ResponseEntity<>(new ResponseWrapperImpl(OK, message, RestStatus.SUCCESS), OK);
  }

  public static ResponseEntity<ResponseWrapper> okayResponseWithData(
      RestMessage message, Object data) {
    return new ResponseEntity<>(
        new GenericResponseWrapper(OK, message, RestStatus.SUCCESS, data), OK);
  }

  public static ResponseEntity<ResponseWrapper> errorResponseWithData(
      RestMessage message, Object data) {
    GenericResponseWrapper error =
        new GenericResponseWrapper(BAD_REQUEST, message, RestStatus.FAILURE, data);
    return new ResponseEntity<>(error, BAD_REQUEST);
  }
}

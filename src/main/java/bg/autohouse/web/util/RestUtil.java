package bg.autohouse.web.util;

import static org.springframework.http.HttpStatus.*;

import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.response.ResponseWrapper;
import bg.autohouse.web.models.response.ResponseWrapper.ResponseWrapperBuilder;
import java.net.URI;
import java.util.function.Function;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@UtilityClass
public class RestUtil {

  public static ResponseEntity<ResponseWrapper> errResponse(HttpStatus code, RestMessage msg) {
    ResponseWrapper response = response(b -> b.message(msg));
    return new ResponseEntity<>(response, code);
  }

  public static ResponseEntity<ResponseWrapper> errorResponse(RestMessage restMessage) {
    return errResponse(BAD_REQUEST, restMessage);
  }

  public static ResponseEntity<ResponseWrapper> errResponse(RestMessage message, Object data) {
    ResponseWrapper response = response(b -> b.message(message).data(data));
    return new ResponseEntity<>(response, BAD_REQUEST);
  }

  public static ResponseEntity<ResponseWrapper> accessDeniedResponse() {
    ResponseWrapper response = response(b -> b.message(RestMessage.PERMISSION_DENIED));
    return new ResponseEntity<>(response, FORBIDDEN);
  }

  public static ResponseEntity<ResponseWrapper> okResponse(RestMessage message) {
    ResponseWrapper response = response(b -> b.message(message));
    return new ResponseEntity<>(response, OK);
  }

  public static ResponseEntity<ResponseWrapper> okResponse(RestMessage message, Object data) {
    ResponseWrapper response = response(b -> b.message(message).data(data));
    return new ResponseEntity<>(response, OK);
  }

  public static ResponseEntity<ResponseWrapper> okResponse(Object data) {
    ResponseWrapper response = response(b -> b.data(data));
    return new ResponseEntity<>(response, OK);
  }

  public static ResponseEntity<ResponseWrapper> createSuccessResponse(
      Object data, RestMessage message, String path) {
    URI location =
        ServletUriComponentsBuilder.fromCurrentContextPath().path(path).buildAndExpand().toUri();
    ResponseWrapper response = response(b -> b.message(message).data(data));
    return ResponseEntity.created(location).body(response);
  }

  public static ResponseWrapper response(
      Function<ResponseWrapperBuilder, ResponseWrapperBuilder> func) {
    return func.apply(ResponseWrapper.builder()).build();
  }
}

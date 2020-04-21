package bg.autohouse.web.controllers;

import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;

import bg.autohouse.config.WebConfiguration;
import javax.validation.constraints.Email;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WebConfiguration.URL_VALIDATION)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ValidationController {
  private static final String BODY_VALID_VALUE = "ok";
  private static final String BODY_INVALID_VALUE = "invalid";

  @GetMapping(
      value = "/email",
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<String> validateUsername(@RequestParam final String username) {
    final ValidationDTO dto = ValidationDTO.builder().username(username).build();
    return validate(dto);
  }

  // TODO validatePhoneNumber

  private static ResponseEntity<String> createResponse(final boolean result) {
    return ResponseEntity.ok(result ? BODY_VALID_VALUE : BODY_INVALID_VALUE);
  }

  private ResponseEntity<String> validate(final Object dto) {
    final DataBinder binder = new DataBinder(dto);
    return createResponse(!binder.getBindingResult().hasErrors());
  }

  @Builder
  private static class ValidationDTO {
    @Email private String username;
  }
}

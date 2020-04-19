package bg.autohouse.service.models.error;

import bg.autohouse.web.enums.RestMessage;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@AllArgsConstructor
public class RestError {
  @Builder.Default private final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
  @Builder.Default private final RestMessage message = RestMessage.SOMETHING_WENT_WRONG;
  @Builder.Default private final List<RestValidationError> errors = new ArrayList<>();
}

package bg.autohouse.service.models.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
  @Builder.Default @JsonIgnore
  private final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

  @Builder.Default private final List<RestValidationError> errors = new ArrayList<>();
}

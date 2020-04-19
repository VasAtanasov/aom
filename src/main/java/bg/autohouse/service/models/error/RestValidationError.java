package bg.autohouse.service.models.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class RestValidationError {
  private final String field;
  private final String fieldErrorCode;
  private final String errorMessage;
}

package bg.autohouse.errors.models;

import io.swagger.annotations.ApiModel;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

@ApiModel("Error")
@Getter
@Setter
@Builder
@AllArgsConstructor(staticName = "of")
public class ErrorModel {
  private final Integer errorCode;

  @Singular private final Map<String, Object> errors;

  public static PayloadBuilder builder() {
    return new PayloadBuilder();
  }

  public static class PayloadBuilder {
    private Map<String, Object> object = new LinkedHashMap<>();
    private Integer errorCode;

    public PayloadBuilder field(String key, Object value) {
      this.object.putIfAbsent(key, value);
      return (this);
    }

    public PayloadBuilder errorCode(Integer errorCode) {
      this.errorCode = errorCode;
      return (this);
    }

    public ErrorModel build() {
      return ErrorModel.of(errorCode, object);
    }
  }
}

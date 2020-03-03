package bg.autohouse.errors.models;

import io.swagger.annotations.ApiModel;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

@ApiModel("ValidationError")
@Getter
@Setter
@Builder
public class ValidationErrorModel {

  private String fieldName;

  private String errorCode;

  private Object rejectedValue;

  @Singular private List<Object> params;

  private String message;
}

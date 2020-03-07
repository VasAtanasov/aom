package bg.autohouse.errors.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ValidationErrorModel implements Serializable {

  private static final long serialVersionUID = 5246526371043071934L;

  private String fieldName;

  private String errorCode;

  private Object rejectedValue;

  @Singular private List<Object> params;

  private String message;
}

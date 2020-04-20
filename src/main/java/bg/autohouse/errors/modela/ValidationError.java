package bg.autohouse.errors.modela;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ValidationError implements Serializable {

  private static final long serialVersionUID = 5246526371043071934L;

  private String fieldName;
  private Object rejectedValue;
  private String message;
}

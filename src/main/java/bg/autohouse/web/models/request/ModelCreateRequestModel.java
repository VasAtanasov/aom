package bg.autohouse.web.models.request;

import bg.autohouse.web.validation.annotations.maker.ModelName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ModelCreateRequestModel {
  @ModelName private String name;
}

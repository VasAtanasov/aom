package bg.autohouse.web.models.request;

import bg.autohouse.validation.ValidationMessages;
import bg.autohouse.validation.maker.ModelName;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ModelCreateRequestModel {
  @ModelName private String name;

  @NotNull(message = ValidationMessages.MAKER_ID_NULL)
  private Long makerId;
}

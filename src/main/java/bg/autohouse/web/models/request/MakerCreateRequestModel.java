package bg.autohouse.web.models.request;

import bg.autohouse.web.validation.annotations.maker.MakerName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MakerCreateRequestModel {
  @MakerName private String name;
}

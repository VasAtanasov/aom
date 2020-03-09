package bg.autohouse.web.models.request;

import bg.autohouse.web.validation.annotations.maker.MakerName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class MakerCreateRequestModel {
  @MakerName private String name;
}

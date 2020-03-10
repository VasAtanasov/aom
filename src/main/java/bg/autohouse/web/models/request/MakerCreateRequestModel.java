package bg.autohouse.web.models.request;

import bg.autohouse.validation.maker.MakerName;
import bg.autohouse.validation.maker.UniqueMakerName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class MakerCreateRequestModel {
  @MakerName @UniqueMakerName private String name;
}

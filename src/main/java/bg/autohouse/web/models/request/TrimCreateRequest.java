package bg.autohouse.web.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class TrimCreateRequest {
  @NotNull private Integer year;
  @NotNull private String trim;
}

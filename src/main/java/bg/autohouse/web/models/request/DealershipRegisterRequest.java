package bg.autohouse.web.models.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DealershipRegisterRequest {

  @NotBlank(message = "Dealer name is required")
  private String name;

  private String description;

  @NotBlank(message = "Address is required")
  private String address;

  @NotBlank(message = "Location is required")
  private Long locationId;
}

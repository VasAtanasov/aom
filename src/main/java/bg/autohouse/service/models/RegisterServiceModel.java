package bg.autohouse.service.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterServiceModel {
  private UserRegisterServiceModel user;
  private DealershipServiceModel dealership;
}

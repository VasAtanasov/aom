package bg.autohouse.service.models;

import bg.autohouse.service.validations.annotations.Required;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterServiceModel {
  @Required private String username;
  @Required private String password;
}

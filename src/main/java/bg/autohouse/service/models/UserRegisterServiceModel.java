package bg.autohouse.service.models;

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
  private String username;
  private String password;
  private String phoneNumber;
  // private String email;
  private String firstName;
  private String lastName;
  private String seller;
}
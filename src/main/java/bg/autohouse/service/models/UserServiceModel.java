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
public class UserServiceModel {
  private String id;
  private String username;
  private String phoneNumber;
  private String seller;
}

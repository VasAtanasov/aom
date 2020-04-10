package bg.autohouse.data.models;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = EntityConstants.USERS_CREATE_REQUEST)
public class UserCreateRequest extends BaseUuidEntity {
  private static final long serialVersionUID = -4417558961136176201L;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "is_verified", nullable = false)
  @Builder.Default
  private boolean isVerified = false;

  @Transient
  public void verify() {
    isVerified = true;
  }
}

package bg.autohouse.data.models;

import bg.autohouse.data.models.enums.Role;
import bg.autohouse.data.models.enums.Seller;
import bg.autohouse.util.F;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = EntityConstants.USERS)
public class User extends BaseUuidEntity implements UserDetails {

  // TODO add user data to database
  // TODO user service and user controller

  private static final long serialVersionUID = -4468841758676373460L;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "phone_number", nullable = true, length = 20, unique = true)
  private String phoneNumber;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  // TODO change to false when email confirmation applied.
  @Column private boolean enabled = true;

  @Column(name = "seller")
  @Enumerated(EnumType.STRING)
  private Seller seller;

  @ElementCollection(fetch = FetchType.LAZY, targetClass = Role.class)
  @JoinTable(
      name = "user_role",
      joinColumns =
          @JoinColumn(
              name = "user_id",
              referencedColumnName = "id",
              foreignKey = @ForeignKey(name = EntityConstants.PREFIX + "fk_role_user_id")))
  @Column(name = "role")
  @Enumerated(value = EnumType.STRING)
  private Set<Role> roles = new HashSet<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }

  @Override
  @Transient
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  @Transient
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  @Transient
  public boolean isCredentialsNonExpired() {
    return true;
  }

  public boolean isNormalUser() {
    return F.containsAll(roles, Role.USER);
  }

  public boolean isAdmin() {
    return F.containsAll(roles, Role.ADMIN);
  }

  public boolean isModerator() {
    return F.containsAll(roles, Role.MODERATOR);
  }

  public boolean isRoot() {
    return F.containsAll(roles, Role.ROOT);
  }

  public boolean isModeratorOrAdmin() {
    return isModerator() || isAdmin();
  }

  public boolean canAcceptEmailToken() {
    return isModeratorOrAdmin() || isNormalUser() || isRoot();
  }
}

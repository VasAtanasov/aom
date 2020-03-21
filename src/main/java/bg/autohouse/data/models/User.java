package bg.autohouse.data.models;

import bg.autohouse.data.models.enums.Seller;
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
  public enum Role implements GrantedAuthority {
    ROOT,
    ADMIN,
    MODERATOR,
    USER,
    REST;

    public String includes(Role that) {
      return this + " > " + that;
    }

    public boolean isNormalUser() {
      return this == USER;
    }

    public boolean isAdmin() {
      return this == ADMIN;
    }

    public boolean isModerator() {
      return this == MODERATOR;
    }

    public boolean isRoot() {
      return this == ROOT;
    }

    public boolean isModeratorOrAdmin() {
      return isModerator() || isAdmin();
    }

    public boolean canAcceptEmailToken() {
      return isModeratorOrAdmin() || isNormalUser() || isRoot();
    }

    @Override
    public String getAuthority() {
      return name();
    }
  }

  private static final long serialVersionUID = -4468841758676373460L;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "phone_number", nullable = true, length = 20, unique = true)
  private String phoneNumber;

  @Column(
      name = "email_address",
      nullable = false,
      unique = true) // enforcing one user per email add.
  private String emailAddress;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(
      name = "display_name",
      nullable = true,
      length = 70) // allowing this to be nullable as might not be set
  private String displayName;

  @Column private boolean enabled = true;

  @Column(name = "seller")
  @Enumerated(EnumType.STRING)
  private Seller seller;

  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  private Role role;

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
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
}

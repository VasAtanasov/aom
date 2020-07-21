package bg.autohouse.data.models;

import bg.autohouse.data.models.enums.Role;
import bg.autohouse.data.models.offer.Offer;
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
@NamedQueries({
  @NamedQuery(
      name = User.QUERY_BY_ID_WITH_ROLES,
      query = "SELECT u from User u JOIN FETCH u.roles ur where u.id = :id"),
  @NamedQuery(
      name = User.UPDATE_HAS_ACCOUNT,
      query = "UPDATE User u set u.hasAccount = 1 where u.id in :ids"),
  @NamedQuery(
      name = User.QUERY_ALL_ACTIVE_WITH_ROLES,
      query = "SELECT DISTINCT u FROM User u JOIN FETCH u.roles r WHERE u.enabled = true"),
  @NamedQuery(
      name = User.QUERY_BY_USERNAME,
      query =
          "SELECT u from User u JOIN FETCH u.roles ur where lower(u.username) = lower(:username)")
})
public class User extends BaseUuidEntity implements UserDetails {
  public static final String QUERY_BY_ID_WITH_ROLES = "User.findByIdWithRoles";;
  public static final String UPDATE_HAS_ACCOUNT = "User.updateHasAccount";
  public static final String QUERY_ALL_ACTIVE_WITH_ROLES = "User.findAllActiveWithRoles";
  public static final String QUERY_BY_USERNAME = "User.findByUsernameIgnoreCase";

  private static final long serialVersionUID = -4468841758676373460L;
  // username == email
  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "enabled")
  private boolean enabled = true;

  @Column(name = "has_account")
  private boolean hasAccount = false;

  @ElementCollection(fetch = FetchType.LAZY, targetClass = Role.class)
  @JoinTable(
      name = EntityConstants.PREFIX + "user_role",
      joinColumns =
          @JoinColumn(
              name = "user_id",
              referencedColumnName = "id",
              foreignKey = @ForeignKey(name = EntityConstants.PREFIX + "fk_role_user_id")))
  @Column(name = "role")
  @Enumerated(value = EnumType.STRING)
  private Set<Role> roles = new HashSet<>();

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "auto_user_saved_offer",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "offer_id"))
  private Set<Offer> favorites = new HashSet<>();

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

  @Override
  @Transient
  public boolean isEnabled() {
    return enabled;
  }

  private User(String username, String password) {
    this.username = username;
    this.password = password;
    this.roles.add(Role.USER);
  }

  public static User createNormalUser(String username, String password) {
    return new User(username, password);
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

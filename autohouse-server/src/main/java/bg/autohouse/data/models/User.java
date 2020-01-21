package bg.autohouse.data.models;

import bg.autohouse.data.listeners.AuditListener;
import bg.autohouse.data.models.converters.LocalDateTimeAttributeConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@EntityListeners(AuditListener.class)
public class User extends BaseUuidEntity implements UserDetails, Auditable {

    @Column(nullable = false, unique = true, updatable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Embedded
    private Audit audit;

    @Column(name = "last_login")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime lastLogin;

    @Column(name = "is_active", columnDefinition = "boolean NOT NULL DEFAULT 1")
    private boolean isActive;

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id"
            )
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Offer> offers;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return isActive;
    }

    @PrePersist
    public void setActive() {
        isActive = true;
    }

    public void addOffer(Offer offer) {
        if (offers == null) {
            offers = new ArrayList<>();
        }
        offers.add(offer);
        offer.setUser(this);
    }


}


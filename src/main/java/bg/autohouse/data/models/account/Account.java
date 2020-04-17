package bg.autohouse.data.models.account;

import bg.autohouse.data.models.BaseUuidEntity;
import bg.autohouse.data.models.EntityConstants;
import bg.autohouse.data.models.User;
import bg.autohouse.data.models.enums.AccountType;
import bg.autohouse.data.models.geo.Address;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = EntityConstants.ACCOUNTS)
public class Account extends BaseUuidEntity {

  private static final long serialVersionUID = 1L;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @MapsId
  @JoinColumn(name = "id", foreignKey = @ForeignKey(name = "fk_account_user_id"))
  private User owner;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "max_offers_count")
  private int maxOffersCount;

  @Column(name = "description")
  private String description;

  @Embedded private ContactDetails contact;

  @Column(name = "display_name")
  private String displayName;

  @Column(name = "enabled", nullable = false)
  private boolean enabled = true;

  @Column(name = "closed", nullable = false)
  private boolean closed = false;

  @Column(name = "has_image")
  private boolean hasImage = false;

  @Column(name = "account_type")
  @Enumerated(EnumType.STRING)
  private AccountType accountType = null;

  @OneToOne(
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      mappedBy = "resident")
  private Address address;
}

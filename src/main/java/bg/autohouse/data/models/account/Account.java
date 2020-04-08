package bg.autohouse.data.models.account;

import bg.autohouse.data.models.BaseUuidEntity;
import bg.autohouse.data.models.EntityConstants;
import bg.autohouse.data.models.User;
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name = "ACCOUNT_TYPE",
    discriminatorType = DiscriminatorType.INTEGER,
    columnDefinition = "TINYINT(1)")
@Table(name = EntityConstants.ACCOUNTS)
public abstract class Account extends BaseUuidEntity {

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

  @Embedded private ContactDetails contactDetails;
}

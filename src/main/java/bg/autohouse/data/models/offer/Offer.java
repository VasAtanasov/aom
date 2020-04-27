package bg.autohouse.data.models.offer;

import bg.autohouse.data.models.BaseUuidEntity;
import bg.autohouse.data.models.EntityConstants;
import bg.autohouse.data.models.account.Account;
import bg.autohouse.data.models.account.ContactDetails;
import bg.autohouse.data.models.geo.Location;
import javax.persistence.*;
import lombok.*;

// TODO add named queries
// TODO add validation annotations to entities
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = EntityConstants.OFFERS,
    indexes = {
      @Index(name = "idx_" + EntityConstants.OFFERS + "_account_id", columnList = "account_id")
    })
public class Offer extends BaseUuidEntity {
  // TODO list of images
  // TODO list of price changes

  private static final long serialVersionUID = -2840866963962522737L;

  @ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
  @JoinColumn(
      name = "account_id",
      nullable = false,
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_offer_account_id"))
  private Account account;

  @OneToOne(
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      mappedBy = "offer")
  private Vehicle vehicle;

  @Column(name = "thumbnail", nullable = false)
  private String thumbnail;

  @Column(name = "price", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT(0)")
  private Integer price = 0;

  @Column(name = "hit_count", columnDefinition = "INT UNSIGNED DEFAULT(0)")
  private Integer hitCount = 0;

  @Column(name = "saved_count", columnDefinition = "INT UNSIGNED DEFAULT(0)")
  private Integer savedCount = 0;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "is_active")
  private boolean isActive = true;

  @ManyToOne
  @JoinColumn(
      name = "location_id",
      nullable = false,
      updatable = true,
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_offer_location_id"))
  private Location location;

  @Embedded private ContactDetails contactDetails;

  public void incrementHitCount() {
    hitCount += 1;
  }

  public void incrementSavedCount() {
    savedCount += 1;
  }

  public void decrementSaveCount() {
    savedCount -= 1;
    if (savedCount < 0) savedCount = 0;
  }
}

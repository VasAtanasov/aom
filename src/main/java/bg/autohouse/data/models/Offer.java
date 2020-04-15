package bg.autohouse.data.models;

import bg.autohouse.data.models.account.ContactDetails;
import bg.autohouse.data.models.geo.Location;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

// TODO add named queries
// TODO add validation annotations to entities
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = EntityConstants.OFFERS)
public class Offer extends BaseUuidEntity implements EntityDetails {

  private static final long serialVersionUID = -2840866963962522737L;

  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private User seller;

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

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "price_modified_on")
  private Date priceModifiedOn;

  @Column(name = "hit_count", columnDefinition = "INT UNSIGNED DEFAULT(0)")
  private Integer hitCount = 0;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "is_active")
  private boolean isActive = true;

  @Column(name = "is_deleted")
  private boolean isDeleted = false;

  @Column(name = "is_expired")
  private boolean isExpired = false;

  @ManyToOne
  @JoinColumn(name = "location_id", nullable = false, updatable = true)
  private Location location;

  @Embedded private ContactDetails contactDetails;

  @Transient private Integer previousPrice;

  @PreUpdate
  private void doBeforeUpdate() {
    int areEqualPrices = Integer.compare(previousPrice, price);
    if (areEqualPrices != 0) {
      priceModifiedOn = new Date();
    }
  }

  @PostLoad
  private void storePriceState() {
    previousPrice = price;
  }
}

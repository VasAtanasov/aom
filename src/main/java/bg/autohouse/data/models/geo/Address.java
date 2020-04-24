package bg.autohouse.data.models.geo;

import bg.autohouse.data.models.BaseUuidEntity;
import bg.autohouse.data.models.EntityConstants;
import bg.autohouse.data.models.account.Account;
import javax.persistence.*;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = EntityConstants.ADDRESSES)
public class Address extends BaseUuidEntity {

  private static final long serialVersionUID = -2377398736911388136L;

  @ManyToOne
  @JoinColumn(name = "location_id", nullable = false, updatable = true)
  private Location location;

  @Column(name = "street_name")
  private String street;

  @MapsId
  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id")
  private Account resident;

  public static Address createAddress(Location location, String street, Account resident) {
    return new Address(location, street, resident);
  }
}

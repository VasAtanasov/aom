package bg.autohouse.data.models.account;

import bg.autohouse.data.models.EntityConstants;
import bg.autohouse.data.models.geo.Address;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = EntityConstants.DEALER)
public class Dealership extends Account {

  private static final long serialVersionUID = 5897929406117962363L;
  private static final int DEFAULT_DEALER_OFFERS_COUNT = 50;

  @Column(name = "max_offers_count")
  private int maxOffersCount = DEFAULT_DEALER_OFFERS_COUNT;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Column(name = "description")
  private String description;

  @OneToOne(
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      mappedBy = "resident")
  private Address address;
}

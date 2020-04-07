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
@DiscriminatorValue("1")
@Table(name = EntityConstants.DEALER)
public class Dealer extends Account {

  private static final long serialVersionUID = 5897929406117962363L;

  @Column(name = "dealership_name", unique = true)
  private String dealershipName;

  @Column(name = "description")
  private String description;

  @OneToOne(
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      mappedBy = "resident")
  private Address address;
}

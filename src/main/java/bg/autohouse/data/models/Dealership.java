package bg.autohouse.data.models;

import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = EntityConstants.DEALERSHIPS)
public class Dealership extends BaseUuidEntity {

  private static final long serialVersionUID = 5897929406117962363L;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @MapsId
  @JoinColumn(name = "id")
  private User dealer;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Column(name = "description")
  private String description;
}

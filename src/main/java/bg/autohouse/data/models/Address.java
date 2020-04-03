package bg.autohouse.data.models;

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
public class Address extends BaseLongEntity {

  private static final long serialVersionUID = -2377398736911388136L;

  @ManyToOne
  @JoinColumn(name = "location_id", nullable = true, updatable = true)
  private Location location;

  @Column(name = "street_name")
  private String street;

  @ManyToOne
  @JoinColumn(name = "resident_user_id", nullable = true, updatable = true)
  private User resident;
}

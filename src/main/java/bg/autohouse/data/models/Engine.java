package bg.autohouse.data.models;

import bg.autohouse.data.models.enums.EuroStandard;
import bg.autohouse.data.models.enums.FuelType;
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
@Table(name = EntityConstants.ENGINES)
public class Engine extends BaseUuidEntity {

  private static final long serialVersionUID = 3158862397025624400L;

  @MapsId
  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id")
  private Vehicle vehicle;

  @Column(name = "fuel_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private FuelType fuelType;

  @Column(name = "power", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT(0)")
  private Integer power = 0;

  @Column(name = "euro_standard")
  @Enumerated(EnumType.STRING)
  private EuroStandard euroStandard;
}

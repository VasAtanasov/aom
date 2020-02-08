package bg.autohouse.data.models;

import bg.autohouse.data.models.enums.EuroStandard;
import bg.autohouse.data.models.enums.FuelType;
import bg.autohouse.data.models.enums.PowerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "engines")
public class Engine extends BaseUuidEntity {

    @Column(name = "modification")
    private String modification;

    @Column(name = "fuel_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @Column(name = "cylinders", columnDefinition = "INT UNSIGNED DEFAULT '0'")
    private Integer cylinders;

    @Column(name = "power", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT '0'")
    private Integer power;

    @Column(name = "power_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PowerType powerType;

    @Column(name = "euro_standard")
    @Enumerated(EnumType.STRING)
    private EuroStandard euroStandard;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id")
    private Vehicle vehicle;
}

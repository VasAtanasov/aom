package bg.autohouse.data.models;

import bg.autohouse.data.models.enums.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle extends BaseUuidEntity {

    @Column(name = "vehicle_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleCategory vehicleType;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Offer  offer;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToOne(targetEntity = Maker.class, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "maker_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Maker maker;

    @ManyToOne(targetEntity = Model.class, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "model_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Model model;

    @Column(name = "month", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT '0'")
    private Integer month;

    @Column(name = "year", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT '0'")
    private Integer year;

    @Column(name = "body_style", nullable = false)
    @Enumerated(EnumType.STRING)
    private BodyStyle bodyStyle;

    @Column(name = "mileage", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT '0'")
    private Integer mileage;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "vehicle")
    private Engine engine;

    @Column(name = "transmission", nullable = false)
    @Enumerated(EnumType.STRING)
    private Transmission transmission;

    @Column(name = "drive")
    @Enumerated(EnumType.STRING)
    private Drive drive;

    @ManyToOne(targetEntity = Color.class, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "interior_color_id",
            referencedColumnName = "id"
    )
    private Color interiorColor;

    @ManyToOne(targetEntity = Color.class, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "exterior_color_id",
            referencedColumnName = "id"
    )
    private Color exteriorColor;

    @Column(name = "seats", columnDefinition = "INT UNSIGNED DEFAULT '0'")
    private Integer seats;

    @Column(name = "doors", columnDefinition = "INT UNSIGNED DEFAULT '0'")
    private Integer doors;

    @Column(name = "owners", columnDefinition = "INT UNSIGNED DEFAULT '0'")
    private Integer owners;

    @ManyToMany(
            targetEntity = Feature.class,
            cascade = {
                    CascadeType.ALL
            },
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "vehicle_feature",
            joinColumns = @JoinColumn(
                    name = "vehicle_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "feature_id",
                    referencedColumnName = "id"
            )
    )
    private List<Feature> features = new ArrayList<>();

    @Column(name = "has_accident", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean hasAccident;

    @Transient
    public void addFeature(Feature feature) {
        if (features == null) {
            features = new ArrayList<>();
        }
        features.add(feature);
    }
}
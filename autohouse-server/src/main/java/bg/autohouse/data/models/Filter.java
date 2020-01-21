package bg.autohouse.data.models;

import bg.autohouse.data.listeners.AuditListener;
import bg.autohouse.data.models.enums.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "filters")
@EntityListeners(AuditListener.class)
public class Filter extends BaseUuidEntity implements Auditable {

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            nullable = false
    )
    private User user;

    @Embedded
    private Audit audit;

    @ManyToOne(targetEntity = Maker.class)
    @JoinColumn(
            name = "maker_id",
            referencedColumnName = "id"
    )
    private Maker maker;

    @ManyToOne(targetEntity = Model.class)
    @JoinColumn(
            name = "model_id",
            referencedColumnName = "id"
    )
    private Model model;

    @Column(name = "modification")
    private String modification;

    @Column(name = "body_style")
    @Enumerated(EnumType.STRING)
    private BodyStyle bodyStyle;

    @Column(name = "first_registration_from", columnDefinition = "INT UNSIGNED DEFAULT '0'")
    private Integer firstRegistrationFrom;

    @Column(name = "first_registration_to", columnDefinition = "INT UNSIGNED DEFAULT '0'")
    private Integer firstRegistrationTo;

    @Column(name = "price_from", columnDefinition = "INT UNSIGNED DEFAULT '0'")
    private Integer priceFrom;

    @Column(name = "price_to", columnDefinition = "INT UNSIGNED DEFAULT '0'")
    private Integer priceTo;

    @Column(name = "fuel_type")
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @Column(name = "mileage_from", columnDefinition = "INT UNSIGNED DEFAULT '0'")
    private Integer mileageFrom;

    @Column(name = "mileage_to", columnDefinition = "INT UNSIGNED DEFAULT '0'")
    private Integer mileageTo;

    @Column(name = "power")
    @Enumerated(EnumType.STRING)
    private PowerType powerType;

    @Column(name = "power_from", columnDefinition = "INT UNSIGNED DEFAULT '0'")
    private Integer powerFrom;

    @Column(name = "power_to", columnDefinition = "INT UNSIGNED DEFAULT '0'")
    private Integer powerTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "transmission")
    private Transmission transmission;

    @Column(name = "cylinders", columnDefinition = "INT UNSIGNED DEFAULT '0'")
    private Integer cylinders;

    @Column(name = "drive", nullable = false)
    @Enumerated(EnumType.STRING)
    private Drive drive;

    @Column(name = "doors", columnDefinition = "INT UNSIGNED DEFAULT '0'")
    private Integer doors;

    @Column(name = "seats_from", columnDefinition = "INT UNSIGNED DEFAULT '0'")
    private Integer seatsFrom;

    @Column(name = "seats_to", columnDefinition = "INT UNSIGNED DEFAULT '0'")
    private Integer seatsTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;

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

    @Column(name = "previous_owners", columnDefinition = "INT UNSIGNED DEFAULT '0'")
    private Integer previousOwners;

    @Column(name = "has_accident", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean hasAccident;

    @Enumerated(EnumType.STRING)
    @Column(name = "euro_standard")
    private EuroStandard euroStandard;

    @Column(name = "online_since", columnDefinition = "INT UNSIGNED DEFAULT '0'")
    private Integer onlineSince;

    @Column(name = "location")
    private String location;

    @Column(name = "is_saved", nullable = false, columnDefinition = "TINYINT(1) DEFAULT '0'")
    private Integer isSaved;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "TINYINT(1) DEFAULT '0'")
    private Integer isDeleted;

    @Column(name = "sort", columnDefinition = "VARCHAR(255) DEFAULT 'auditCreatedOn,desc'")
    private String sort;

    @ManyToMany(
            targetEntity = Feature.class,
            cascade = {
                    CascadeType.ALL
            },
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "filter_feature",
            joinColumns = @JoinColumn(
                    name = "filter_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "feature_id",
                    referencedColumnName = "id"
            )
    )
    private List<Feature> features;

}

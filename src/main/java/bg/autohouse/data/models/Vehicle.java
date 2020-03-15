package bg.autohouse.data.models;

import bg.autohouse.data.models.enums.*;
import java.util.ArrayList;
import java.util.List;
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
@Table(
    name = EntityConstants.VEHICLES,
    indexes = {
      @Index(name = "idx_" + EntityConstants.VEHICLES, columnList = "id, maker_id, model_id")
    })
public class Vehicle extends BaseUuidEntity {

  private static final long serialVersionUID = -797198687403481966L;

  @OneToOne
  @MapsId
  @JoinColumn(name = "id")
  private Offer offer;

  @ManyToOne(targetEntity = Maker.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "maker_id", referencedColumnName = "id", nullable = false)
  private Maker maker;

  @ManyToOne(targetEntity = Model.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "model_id", referencedColumnName = "id", nullable = false)
  private Model model;

  @OneToOne(
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      mappedBy = "vehicle")
  private Engine engine;

  @Embedded private ManufactureDate manufactureDate;

  @Column(name = "mileage", columnDefinition = "INT UNSIGNED DEFAULT(0)")
  private Integer mileage = 0;

  @Column(name = "seats", columnDefinition = "INT UNSIGNED DEFAULT(0)")
  private Integer seats;

  @Column(name = "doors", columnDefinition = "INT UNSIGNED DEFAULT(0)")
  private Integer doors;

  @Column(name = "state", nullable = false)
  @Enumerated(EnumType.STRING)
  private State state;

  @Column(name = "body_style", nullable = false)
  @Enumerated(EnumType.STRING)
  private BodyStyle bodyStyle;

  @Column(name = "transmission", nullable = false)
  @Enumerated(EnumType.STRING)
  private Transmission transmission;

  @Column(name = "drive")
  @Enumerated(EnumType.STRING)
  private Drive drive;

  @Column(name = "color")
  @Enumerated(EnumType.STRING)
  private Color color;

  @Column(name = "upholstery")
  @Enumerated(EnumType.STRING)
  private Upholstery upholstery;

  @ElementCollection(fetch = FetchType.LAZY, targetClass = Feature.class)
  @JoinTable(
      name = "vehicle_feature",
      joinColumns =
          @JoinColumn(
              name = "vehicle_id",
              referencedColumnName = "id",
              foreignKey = @ForeignKey(name = EntityConstants.PREFIX + "fk_feature_vehicle_id")))
  @Column(name = "feature")
  @Enumerated(value = EnumType.STRING)
  private List<Feature> feature = new ArrayList<>();

  @Column(name = "has_accident")
  private boolean hasAccident = false;
}

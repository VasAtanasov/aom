package bg.autohouse.data.models;

import bg.autohouse.data.models.enums.BodyStyle;
import bg.autohouse.data.models.enums.Color;
import bg.autohouse.data.models.enums.Drive;
import bg.autohouse.data.models.enums.EuroStandard;
import bg.autohouse.data.models.enums.Feature;
import bg.autohouse.data.models.enums.FuelType;
import bg.autohouse.data.models.enums.Seller;
import bg.autohouse.data.models.enums.SortOption;
import bg.autohouse.data.models.enums.State;
import bg.autohouse.data.models.enums.Transmission;
import bg.autohouse.data.models.enums.Upholstery;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = EntityConstants.FILTERS)
public class Filter extends BaseUuidEntity implements EntityDetails {

  private static final long serialVersionUID = 1915410703216504289L;

  @ManyToOne(targetEntity = Maker.class, fetch = FetchType.LAZY)
  @JoinColumn(
      name = "maker_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = EntityConstants.PREFIX + "fk_filter_maker_id"))
  private Maker maker;

  @ManyToOne(targetEntity = Model.class, fetch = FetchType.LAZY)
  @JoinColumn(
      name = "model_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = EntityConstants.PREFIX + "fk_filter_model_id"))
  private Model model;

  @Column(name = "fuel_type")
  @Enumerated(EnumType.STRING)
  private FuelType fuelType;

  @Column(name = "transmission")
  @Enumerated(EnumType.STRING)
  private Transmission transmission;

  @Column(name = "body_style")
  @Enumerated(EnumType.STRING)
  private BodyStyle bodyStyle;

  @Column(name = "color")
  @Enumerated(EnumType.STRING)
  private Color color;

  @Enumerated(EnumType.STRING)
  @Column(name = "euro_standard")
  private EuroStandard euroStandard;

  @Column(name = "drive", nullable = false)
  @Enumerated(EnumType.STRING)
  private Drive drive;

  @AttributeOverrides({
    @AttributeOverride(name = "from", column = @Column(name = "price_from")),
    @AttributeOverride(name = "to", column = @Column(name = "price_to"))
  })
  @Embedded
  private RangeCriteria price;

  @AttributeOverrides({
    @AttributeOverride(name = "from", column = @Column(name = "mileage_from")),
    @AttributeOverride(name = "to", column = @Column(name = "mileage_to"))
  })
  @Embedded
  private RangeCriteria mileage;

  @AttributeOverrides({
    @AttributeOverride(name = "from", column = @Column(name = "doors_from")),
    @AttributeOverride(name = "to", column = @Column(name = "doors_to"))
  })
  @Embedded
  private RangeCriteria doors;

  @AttributeOverrides({
    @AttributeOverride(name = "from", column = @Column(name = "seats_from")),
    @AttributeOverride(name = "to", column = @Column(name = "seats_to"))
  })
  @Embedded
  private RangeCriteria seats;

  @AttributeOverrides({
    @AttributeOverride(name = "from", column = @Column(name = "registrationYear_from")),
    @AttributeOverride(name = "to", column = @Column(name = "registrationYear_to"))
  })
  @Embedded
  private RangeCriteria registrationYear;

  @AttributeOverrides({
    @AttributeOverride(name = "from", column = @Column(name = "horsePower_from")),
    @AttributeOverride(name = "to", column = @Column(name = "horsePower_to"))
  })
  @Embedded
  private RangeCriteria horsePower;

  @ElementCollection(fetch = FetchType.LAZY, targetClass = Feature.class)
  @JoinTable(
      name = "filter_feature",
      joinColumns =
          @JoinColumn(
              name = "filter_id",
              referencedColumnName = "id",
              foreignKey = @ForeignKey(name = EntityConstants.PREFIX + "fk_feature_filter_id")))
  @Column(name = "feature")
  @Enumerated(value = EnumType.STRING)
  private List<Feature> feature = new ArrayList<>();

  @ElementCollection(fetch = FetchType.LAZY, targetClass = Seller.class)
  @JoinTable(
      name = "filter_seller",
      joinColumns =
          @JoinColumn(
              name = "filter_id",
              referencedColumnName = "id",
              foreignKey = @ForeignKey(name = EntityConstants.PREFIX + "fk_seller_filter_id")))
  @Column(name = "seller")
  @Enumerated(value = EnumType.STRING)
  private List<Seller> seller = new ArrayList<>();

  @ElementCollection(fetch = FetchType.LAZY, targetClass = State.class)
  @JoinTable(
      name = "filter_state",
      joinColumns =
          @JoinColumn(
              name = "filter_id",
              referencedColumnName = "id",
              foreignKey = @ForeignKey(name = EntityConstants.PREFIX + "fk_state_filter_id")))
  @Column(name = "state")
  @Enumerated(value = EnumType.STRING)
  private List<State> state = new ArrayList<>();

  @ElementCollection(fetch = FetchType.LAZY, targetClass = Upholstery.class)
  @JoinTable(
      name = "filter_upholstery",
      joinColumns =
          @JoinColumn(
              name = "filter_id",
              referencedColumnName = "id",
              foreignKey = @ForeignKey(name = EntityConstants.PREFIX + "fk_upholstery_filter_id")))
  @Column(name = "upholstery")
  @Enumerated(value = EnumType.STRING)
  private List<Upholstery> upholstery = new ArrayList<>();

  @Column(name = "is_deleted")
  private boolean isDeleted = false;

  @Column(name = "is_expired")
  private boolean isExpired = false;

  @Column(name = "is_active")
  private boolean isActive = true;

  @Column(name = "has_accident")
  private boolean hasAccident = false;

  @Column(name = "sort_option", nullable = false)
  @Enumerated(EnumType.STRING)
  private SortOption sortOption = SortOption.LATEST;
}

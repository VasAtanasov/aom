package bg.autohouse.data.models;

import static bg.autohouse.data.models.EntityConstants.*;

import bg.autohouse.data.models.enums.AccountType;
import bg.autohouse.data.models.enums.BodyStyle;
import bg.autohouse.data.models.enums.Color;
import bg.autohouse.data.models.enums.Drive;
import bg.autohouse.data.models.enums.EuroStandard;
import bg.autohouse.data.models.enums.Feature;
import bg.autohouse.data.models.enums.FuelType;
import bg.autohouse.data.models.enums.State;
import bg.autohouse.data.models.enums.Transmission;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// TODO add location
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
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

  @Column(name = "drive")
  @Enumerated(EnumType.STRING)
  private Drive drive;

  @AttributeOverrides({
    @AttributeOverride(name = "from", column = @Column(name = "price_from")),
    @AttributeOverride(name = "to", column = @Column(name = "price_to"))
  })
  @Embedded
  @Builder.Default
  private RangeCriteria price = RangeCriteria.of(MIN_VALUE, PRICE_TO);

  @AttributeOverrides({
    @AttributeOverride(name = "from", column = @Column(name = "mileage_from")),
    @AttributeOverride(name = "to", column = @Column(name = "mileage_to"))
  })
  @Embedded
  @Builder.Default
  private RangeCriteria mileage = RangeCriteria.of(MIN_VALUE, MILEAGE_TO);

  @AttributeOverrides({
    @AttributeOverride(name = "from", column = @Column(name = "doors_from")),
    @AttributeOverride(name = "to", column = @Column(name = "doors_to"))
  })
  @Embedded
  @Builder.Default
  private RangeCriteria doors = RangeCriteria.of(MIN_VALUE, DOORS_TO);

  @AttributeOverrides({
    @AttributeOverride(name = "from", column = @Column(name = "seats_from")),
    @AttributeOverride(name = "to", column = @Column(name = "seats_to"))
  })
  @Embedded
  @Builder.Default
  private RangeCriteria seats = RangeCriteria.of(MIN_VALUE, SEATS_TO);

  @AttributeOverrides({
    @AttributeOverride(name = "from", column = @Column(name = "registrationYear_from")),
    @AttributeOverride(name = "to", column = @Column(name = "registrationYear_to"))
  })
  @Embedded
  @Builder.Default
  private RangeCriteria registrationYear = RangeCriteria.of(YEAR_FROM, YEAR_TO);

  @AttributeOverrides({
    @AttributeOverride(name = "from", column = @Column(name = "horsePower_from")),
    @AttributeOverride(name = "to", column = @Column(name = "horsePower_to"))
  })
  @Embedded
  @Builder.Default
  private RangeCriteria horsePower = RangeCriteria.of(MIN_VALUE, POWER_TO);

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
  @Builder.Default
  private List<Feature> feature = new ArrayList<>();

  @ElementCollection(fetch = FetchType.LAZY, targetClass = AccountType.class)
  @JoinTable(
      name = "filter_account_type",
      joinColumns =
          @JoinColumn(
              name = "filter_id",
              referencedColumnName = "id",
              foreignKey =
                  @ForeignKey(name = EntityConstants.PREFIX + "fk_account_type_filter_id")))
  @Column(name = "account_type")
  @Enumerated(value = EnumType.STRING)
  @Builder.Default
  private List<AccountType> accountTypes = new ArrayList<>();

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
  @Builder.Default
  private List<State> state = new ArrayList<>();

  @Column(name = "is_deleted")
  @Builder.Default
  private boolean isDeleted = false;

  @Builder.Default
  @Column(name = "is_expired")
  private boolean isExpired = false;

  @Column(name = "is_active")
  @Builder.Default
  private boolean isActive = true;

  @Column(name = "has_accident")
  @Builder.Default
  private boolean hasAccident = false;
}

package bg.autohouse.backend.feature.pub.model.dao;

import bg.autohouse.backend.feature.pub.car.dao.Car;
import bg.autohouse.backend.feature.pub.maker.dao.Maker;
import bg.autohouse.backend.validation.ModelName;
import bg.autohouse.util.common.persistance.BaseEntity;
import bg.autohouse.util.common.persistance.ColumnConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static bg.autohouse.backend.feature.pub.model.dao.Model.ENTITY_NAME;
import static bg.autohouse.backend.validation.ValidationMessages.MAKER_NULL;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
    name = ENTITY_NAME,
    indexes = {@Index(name = "MODEL_NAME", columnList = "name")})
public class Model implements BaseEntity<String> {

  public static final String ENTITY_NAME = "model";

  @Id
  @Column(
      name = ColumnConstants.ID,
      updatable = false,
      unique = true,
      nullable = false,
      length = 12)
  private String id;

  @Column(name = "name")
  @ModelName
  private String name;

  @NotNull(message = MAKER_NULL)
  @ManyToOne(targetEntity = Maker.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "maker_id",
      referencedColumnName = ColumnConstants.ID,
      foreignKey = @ForeignKey(name = "FK_MODEL_MAKE_ID"))
  private Maker maker;

  @OneToMany(
      mappedBy = "model",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Car> cars = new ArrayList<>();
}

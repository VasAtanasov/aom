package bg.autohouse.backend.feature.common.car.dao;

import bg.autohouse.backend.feature.common.trim.dao.Trim;
import bg.autohouse.backend.feature.common.model.dao.Model;
import bg.autohouse.util.common.persistance.BaseEntity;
import bg.autohouse.util.common.persistance.ColumnConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static bg.autohouse.backend.feature.common.car.dao.Car.ENTITY_NAME;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = ENTITY_NAME)
public class Car implements BaseEntity<String> {

  public static final String ENTITY_NAME = "car";

  @Id
  @Column(
      name = ColumnConstants.ID,
      updatable = false,
      unique = true,
      nullable = false,
      length = 12)
  private String id;

  @Column(name = "year")
  private Integer year;

  @ManyToOne(targetEntity = Model.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "model_id",
      referencedColumnName = ColumnConstants.ID,
      foreignKey = @ForeignKey(name = "FK_CARS_MODELS_ID"))
  private Model model;

  @OneToMany(
      mappedBy = "car",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Trim> trims = new ArrayList<>();
}

package com.github.vaatech.aom.core.model.vehicle;

import com.github.vaatech.aom.commons.basic.persistence.model.BaseEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * For a vehicle model, the calendar year designation assigned by the manufacturer to the annual
 * version of that model
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
    name = ModelYear.Persistence.TABLE_NAME,
    indexes = {
      @Index(
          name = ModelYear.Persistence.INDEX_MODEL_YEAR_YEAR,
          columnList = ModelYear.Persistence.COLUMN_YEAR)
    })
public class ModelYear implements BaseEntity<Integer> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = Persistence.COLUMN_ID, updatable = false, nullable = false)
  private Integer id;

  @Column(name = Persistence.COLUMN_YEAR, nullable = false)
  private Integer year;

  @ManyToOne(targetEntity = Model.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = Persistence.COLUMN_MODEL_ID,
      referencedColumnName = Model.Persistence.COLUMN_ID,
      foreignKey = @ForeignKey(name = Persistence.FK_CARS_TO_MODELS_ID))
  private Model model;

  @OneToMany(
      mappedBy = Trim.Properties.MODEL_YEAR,
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Trim> trims = new ArrayList<>();

  public interface Persistence {
    String TABLE_NAME = "model_year";
    String COLUMN_ID = "id";
    String COLUMN_YEAR = "year";
    String COLUMN_MODEL_ID = "model_id";
    String FK_CARS_TO_MODELS_ID = "FK_CARS_TO_MODELS_ID";
    String INDEX_MODEL_YEAR_YEAR = "INDEX_MODEL_YEAR_YEAR";
  }

  public interface Properties {
    String MODEL = "model";
  }
}

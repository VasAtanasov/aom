package com.github.vaatech.aom.core.model.vehicle;

import com.github.vaatech.aom.core.model.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Trim is a version of a particular model with a particular configuration. The different trim
 * levels offer varieties to the exterior and interior elements of a particular model, in addition
 * to performance options, technologies, and even safety options. While many manufacturers today
 * offer customers a wide array of trims, some models are still offered in a single configuration.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
    name = Trim.Persistence.TABLE_NAME,
    indexes = {
      @Index(name = Trim.Persistence.INDEX_TRIM_NAME, columnList = Trim.Persistence.COLUMN_TRIM)
    })
public class Trim implements BaseEntity<Integer> {
  public interface Persistence {
    String TABLE_NAME = "trim";
    String COLUMN_ID = "id";
    String COLUMN_TRIM = "trim";
    String COLUMN_MODEL_YEAR_ID = "model_year_id";
    String FK_TRIMS_TO_CARS_ID = "FK_TRIMS_TO_CARS_ID";
    String INDEX_TRIM_NAME = "INDEX_TRIM_NAME";
    String JOIN_TABLE_NAME_TRIM_TRANSMISSION = "trim_transmission";
    String JOIN_TABLE_COLUMN_TRIM_ID = "trim_id";
    String JOIN_TABLE_COLUMN_TRANSMISSION_ID = "transmission_id";
  }

  public interface Properties {
    String MODEL_YEAR = "modelYear";
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = Persistence.COLUMN_ID, updatable = false, nullable = false)
  private Integer id;

  @Column(name = Persistence.COLUMN_TRIM, nullable = false)
  private String trim;

  @ManyToOne(targetEntity = ModelYear.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = Persistence.COLUMN_MODEL_YEAR_ID,
      referencedColumnName = ModelYear.Persistence.COLUMN_ID,
      foreignKey = @ForeignKey(name = Persistence.FK_TRIMS_TO_CARS_ID))
  private ModelYear modelYear;

  @OneToMany(mappedBy = Engine.Properties.TRIM, fetch = FetchType.LAZY)
  private Set<Engine> engines = new HashSet<>();

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
  @JoinTable(
      name = Persistence.JOIN_TABLE_NAME_TRIM_TRANSMISSION,
      joinColumns = @JoinColumn(name = Persistence.JOIN_TABLE_COLUMN_TRIM_ID),
      inverseJoinColumns = @JoinColumn(name = Persistence.JOIN_TABLE_COLUMN_TRANSMISSION_ID))
  private Set<Transmission> transmissions = new HashSet<>();

  @OneToMany(mappedBy = Option.Properties.TRIM, fetch = FetchType.LAZY)
  private Set<Option> options = new HashSet<>();
}

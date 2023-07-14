package com.github.vaatech.aom.core.model.vehicle;

import com.github.vaatech.aom.commons.basic.persistence.model.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.github.vaatech.aom.core.model.vehicle.Engine.Persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
    name = Engine.Persistence.TABLE_NAME,
    indexes = {
      @Index(name = INDEX_ENGINE_NAME, columnList = COLUMN_NAME),
      @Index(name = INDEX_ENGINE_IS_STANDARD, columnList = COLUMN_IS_STANDARD)
    })
public class Engine implements BaseEntity<Integer> {

  public interface Persistence {
    String TABLE_NAME = "engine";
    String COLUMN_ID = "id";
    String COLUMN_NAME = "name";
    String COLUMN_IS_STANDARD = "is_standard";
    String COLUMN_TRIM_ID = "trim_id";
    String INDEX_ENGINE_NAME = "INDEX_ENGINE_NAME";
    String INDEX_ENGINE_IS_STANDARD = "INDEX_ENGINE_IS_STANDARD";
    String FK_ENGINES_TO_TRIMS_ID = "FK_ENGINES_TO_TRIMS_ID";
  }

  public interface Properties {
    String TRIM = "trim";
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = Persistence.COLUMN_ID, updatable = false, nullable = false)
  private Integer id;

  @Column(name = COLUMN_NAME)
  private String name;

  @Column(name = Persistence.COLUMN_IS_STANDARD)
  private boolean isStandard;

  @ManyToOne(targetEntity = Trim.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = Persistence.COLUMN_TRIM_ID,
      referencedColumnName = Trim.Persistence.COLUMN_ID,
      foreignKey = @ForeignKey(name = Persistence.FK_ENGINES_TO_TRIMS_ID))
  private Trim trim;
}

package bg.autohouse.backend.feature.common.engine.dao;

import bg.autohouse.backend.feature.common.trim.dao.Trim;
import bg.autohouse.util.common.persistance.BaseEntity;
import bg.autohouse.util.common.persistance.ColumnConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static bg.autohouse.backend.feature.common.engine.dao.Engine.ENTITY_NAME;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = ENTITY_NAME)
public class Engine implements BaseEntity<String> {
  public static final String ENTITY_NAME = "engine";

  @Id
  @Column(
      name = ColumnConstants.ID,
      updatable = false,
      unique = true,
      nullable = false,
      length = 12)
  private String id;

  @Column(name = "name")
  private String name;

  @Column(name = "is_standard")
  private boolean isStandard;

  @ManyToOne(targetEntity = Trim.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "trim_id",
      referencedColumnName = ColumnConstants.ID,
      foreignKey = @ForeignKey(name = "FK_ENGINES_TRIMS_ID"))
  private Trim trim;
}

package bg.autohouse.backend.feature.pub.engine.dao;

import bg.autohouse.backend.feature.pub.trim.dao.Trim;
import bg.autohouse.backend.util.common.persistance.entity.BaseEntity;
import bg.autohouse.backend.util.common.persistance.entity.ColumnConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import static bg.autohouse.backend.feature.pub.engine.dao.Engine.ENTITY_NAME;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
    name = ENTITY_NAME,
    indexes = {
      @Index(name = "ENGINE_NAME", columnList = "name"),
      @Index(name = "ENGINE_IS_STANDARD", columnList = "is_standard")
    })
public class Engine implements BaseEntity<Long> {
  public static final String ENTITY_NAME = "engine";

  @Id
  @Column(name = ColumnConstants.ID, updatable = false, unique = true, nullable = false)
  private Long id;

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

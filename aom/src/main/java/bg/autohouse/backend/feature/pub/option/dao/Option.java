package bg.autohouse.backend.feature.pub.option.dao;

import bg.autohouse.backend.feature.pub.trim.dao.Trim;
import bg.autohouse.backend.util.common.persistance.entity.BaseEntity;
import bg.autohouse.backend.util.common.persistance.entity.ColumnConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import static bg.autohouse.backend.feature.pub.option.dao.Option.ENTITY_NAME;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
    name = ENTITY_NAME,
    indexes = {@Index(name = "OPTION_NAME", columnList = "name")})
public class Option implements BaseEntity<Long> {

  public static final String ENTITY_NAME = "option";

  @Id
  @Column(name = ColumnConstants.ID, updatable = false, unique = true, nullable = false)
  private Long id;

  @Column(name = "name")
  private String label;

  @Column(name = "important")
  private boolean important;

  @Column(name = "active")
  private boolean active;

  @ManyToOne(targetEntity = Trim.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "trim_id",
      referencedColumnName = ColumnConstants.ID,
      foreignKey = @ForeignKey(name = "FK_OPTIONS_TRIMS_ID"))
  private Trim trim;
}

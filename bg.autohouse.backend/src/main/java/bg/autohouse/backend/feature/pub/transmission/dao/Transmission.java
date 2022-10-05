package bg.autohouse.backend.feature.pub.transmission.dao;

import bg.autohouse.backend.feature.pub.trim.dao.Trim;
import bg.autohouse.util.common.persistance.BaseEntity;
import bg.autohouse.util.common.persistance.ColumnConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static bg.autohouse.backend.feature.pub.transmission.dao.Transmission.ENTITY_NAME;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = ENTITY_NAME)
public class Transmission implements BaseEntity<Long> {

  public static final String ENTITY_NAME = "transmission";

  @Id
  @Column(name = ColumnConstants.ID, updatable = false, unique = true, nullable = false)
  private Long id;

  @Column(name = "name")
  private String name;

  @ManyToOne(targetEntity = Trim.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
          name = "trim_id",
          referencedColumnName = ColumnConstants.ID,
          foreignKey = @ForeignKey(name = "FK_TRANSMISSIONS_TRIMS_ID"))
  private Trim trim;
}

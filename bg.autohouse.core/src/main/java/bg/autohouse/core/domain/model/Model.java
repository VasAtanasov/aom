package bg.autohouse.core.domain.model;

import bg.autohouse.core.domain.model.common.BaseEntity;
import bg.autohouse.core.domain.model.common.ColumnConstants;
import bg.autohouse.core.domain.validation.maker.ModelName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static bg.autohouse.core.domain.model.Model.ENTITY_NAME;
import static bg.autohouse.core.domain.validation.ValidationMessages.MAKER_NULL;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = ENTITY_NAME)
public class Model implements BaseEntity<Long> {

  public static final String ENTITY_NAME = "model";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = ColumnConstants.ID, updatable = false, unique = true, nullable = false)
  private Long id;

  @Column(name = ColumnConstants.UID, nullable = false)
  private UUID uid;

  @Column(name = "external_id")
  private String externalId;

  @Column(name = "name")
  @ModelName
  private String name;

  @NotNull(message = MAKER_NULL)
  @ManyToOne(targetEntity = Maker.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "maker_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = EntityConstants.PREFIX + "FK_MODEL_MAKE_ID"))
  private Maker maker;

  @OneToMany(
      mappedBy = "model",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Trim> trims = new ArrayList<>();
}

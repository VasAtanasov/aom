package bg.autohouse.core.domain.model;

import bg.autohouse.core.domain.model.common.BaseEntity;
import bg.autohouse.core.domain.model.common.ColumnConstants;
import bg.autohouse.core.domain.validation.maker.MakerName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static bg.autohouse.core.domain.model.Maker.ENTITY_NAME;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = ENTITY_NAME)
public class Maker implements BaseEntity<Long> {

  public static final String ENTITY_NAME = "maker";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = ColumnConstants.ID, updatable = false, unique = true, nullable = false)
  private Long id;

  @Column(name = ColumnConstants.UID, nullable = false)
  private UUID uid;

  @Column(name = "external_id")
  private String externalId;

  @MakerName
  @Column(name = "name")
  private String name;

  @OneToMany(
      mappedBy = "maker",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Model> models = new ArrayList<>();
}

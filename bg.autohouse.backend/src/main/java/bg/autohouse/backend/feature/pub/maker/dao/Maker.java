package bg.autohouse.backend.feature.pub.maker.dao;

import bg.autohouse.backend.feature.pub.model.dao.Model;
import bg.autohouse.backend.validation.MakerName;
import bg.autohouse.util.common.persistance.entity.BaseEntity;
import bg.autohouse.util.common.persistance.entity.ColumnConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static bg.autohouse.backend.feature.pub.maker.dao.Maker.ENTITY_NAME;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
    name = ENTITY_NAME,
    indexes = {@Index(name = "MAKER_NAME", columnList = "name")})
public class Maker implements BaseEntity<String> {

  public static final String ENTITY_NAME = "maker";

  @Id
  @Column(
      name = ColumnConstants.ID,
      updatable = false,
      unique = true,
      nullable = false,
      length = 12)
  private String id;

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

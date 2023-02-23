package com.github.vaatech.aom.core.model.vehicle;

import com.github.vaatech.aom.core.model.common.BaseEntity;
import com.github.vaatech.aom.validation.MakerName;
import com.github.vaatech.aom.core.model.common.ColumnConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.github.vaatech.aom.core.model.vehicle.Maker.ENTITY_NAME;

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

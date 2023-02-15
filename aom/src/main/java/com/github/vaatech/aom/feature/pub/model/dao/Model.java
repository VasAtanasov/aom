package com.github.vaatech.aom.feature.pub.model.dao;

import com.github.vaatech.aom.feature.pub.car.dao.Car;
import com.github.vaatech.aom.util.common.persistance.entity.BaseEntity;
import com.github.vaatech.aom.validation.ModelName;
import com.github.vaatech.aom.validation.ValidationMessages;
import com.github.vaatech.aom.feature.pub.maker.dao.Maker;
import com.github.vaatech.aom.util.common.persistance.entity.ColumnConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static com.github.vaatech.aom.feature.pub.model.dao.Model.ENTITY_NAME;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
    name = ENTITY_NAME,
    indexes = {@Index(name = "MODEL_NAME", columnList = "name")})
public class Model implements BaseEntity<String> {

  public static final String ENTITY_NAME = "model";

  @Id
  @Column(
      name = ColumnConstants.ID,
      updatable = false,
      unique = true,
      nullable = false,
      length = 12)
  private String id;

  @Column(name = "name")
  @ModelName
  private String name;

  @NotNull(message = ValidationMessages.MAKER_NULL)
  @ManyToOne(targetEntity = Maker.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "maker_id",
      referencedColumnName = ColumnConstants.ID,
      foreignKey = @ForeignKey(name = "FK_MODEL_MAKE_ID"))
  private Maker maker;

  @OneToMany(
      mappedBy = "model",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Car> cars = new ArrayList<>();
}

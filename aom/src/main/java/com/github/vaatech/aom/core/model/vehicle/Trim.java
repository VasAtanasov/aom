package com.github.vaatech.aom.core.model.vehicle;

import com.github.vaatech.aom.core.model.common.BaseEntity;
import com.github.vaatech.aom.core.model.common.ColumnConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.github.vaatech.aom.core.model.vehicle.Trim.ENTITY_NAME;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
    name = ENTITY_NAME,
    indexes = {@Index(name = "TRIM_NAME", columnList = "trim")})
public class Trim implements BaseEntity<String> {

  public static final String ENTITY_NAME = "trim";

  @Id
  @Column(
      name = ColumnConstants.ID,
      updatable = false,
      unique = true,
      nullable = false,
      length = 12)
  private String id;

  @Column(name = "trim", nullable = false)
  private String trim;

  @ManyToOne(targetEntity = Car.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "car_id",
      referencedColumnName = ColumnConstants.ID,
      foreignKey = @ForeignKey(name = "FK_TRIMS_CARS_ID"))
  private Car car;

  @OneToMany(
      mappedBy = "trim",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Engine> engines = new ArrayList<>();

  @OneToMany(
      mappedBy = "trim",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Transmission> transmissions = new ArrayList<>();

  @OneToMany(
      mappedBy = "trim",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Option> options = new ArrayList<>();
}

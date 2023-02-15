package com.github.vaatech.aom.feature.pub.transmission.dao;

import com.github.vaatech.aom.feature.pub.trim.dao.Trim;
import com.github.vaatech.aom.util.common.persistance.entity.BaseEntity;
import com.github.vaatech.aom.util.common.persistance.entity.ColumnConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import static com.github.vaatech.aom.feature.pub.transmission.dao.Transmission.ENTITY_NAME;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
    name = ENTITY_NAME,
    indexes = {@Index(name = "TRANSMISSION_NAME", columnList = "name")})
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

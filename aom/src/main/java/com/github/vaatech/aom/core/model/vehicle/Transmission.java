package com.github.vaatech.aom.core.model.vehicle;

import com.github.vaatech.aom.core.model.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
    name = Transmission.Persistence.TABLE_NAME,
    indexes = {
      @Index(
          name = Transmission.Persistence.INDEX_TRANSMISSION_NAME,
          columnList = Transmission.Persistence.COLUMN_NAME)
    })
public class Transmission implements BaseEntity<Integer> {

  public interface Persistence {
    String TABLE_NAME = "transmission";
    String COLUMN_ID = "id";
    String COLUMN_NAME = "name";
    String COLUMN_TRIM_ID = "trim_id";
    String INDEX_TRANSMISSION_NAME = "INDEX_TRANSMISSION_NAME";
    String FK_TRANSMISSIONS_TO_TRIMS_ID = "FK_TRANSMISSIONS_TO_TRIMS_ID";
  }

  public interface Properties {
    String TRIM = "trim";
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = Persistence.COLUMN_ID, updatable = false, nullable = false)
  private Integer id;

  @Column(name = Persistence.COLUMN_NAME)
  private String name;

  @ManyToOne(targetEntity = Trim.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = Persistence.COLUMN_TRIM_ID,
      referencedColumnName = Trim.Persistence.COLUMN_ID,
      foreignKey = @ForeignKey(name = Persistence.FK_TRANSMISSIONS_TO_TRIMS_ID))
  private Trim trim;
}

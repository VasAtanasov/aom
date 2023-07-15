package com.github.vaatech.aom.core.model.vehicle;

import com.github.vaatech.aom.commons.basic.persistence.model.BaseEntity;
import jakarta.persistence.*;
import java.util.Set;
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

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = Persistence.COLUMN_ID, updatable = false, nullable = false)
  private Integer id;

  @Column(name = Persistence.COLUMN_NAME)
  private String name;

  @ManyToMany(mappedBy = Persistence.MAPPED_BY_TRANSMISSIONS, fetch = FetchType.LAZY)
  private Set<Trim> trim;

  public interface Persistence {
    String TABLE_NAME = "transmission";
    String COLUMN_ID = "id";
    String COLUMN_NAME = "name";
    String INDEX_TRANSMISSION_NAME = "INDEX_TRANSMISSION_NAME";
    String MAPPED_BY_TRANSMISSIONS = "transmissions";
  }

  public interface Properties {
    String TRIM = "trim";
  }
}

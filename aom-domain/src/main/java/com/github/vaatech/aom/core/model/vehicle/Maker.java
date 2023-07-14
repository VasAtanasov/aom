package com.github.vaatech.aom.core.model.vehicle;

import com.github.vaatech.aom.commons.basic.persistence.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
    name = Maker.Persistence.TABLE_NAME,
    indexes = {
      @Index(name = Maker.Persistence.INDEX_MAKER_NAME, columnList = Maker.Persistence.COLUMN_NAME)
    })
public class Maker implements BaseEntity<Integer> {

  public interface Persistence {
    String TABLE_NAME = "maker";
    String COLUMN_ID = "id";
    String COLUMN_NAME = "name";
    String INDEX_MAKER_NAME = "INDEX_MAKER_NAME";
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = Persistence.COLUMN_ID, updatable = false, nullable = false)
  private Integer id;

  @Column(name = Persistence.COLUMN_NAME)
  private String name;

  @OneToMany(
      mappedBy = Model.Properties.MAKER,
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private Set<Model> models = new HashSet<>();
}

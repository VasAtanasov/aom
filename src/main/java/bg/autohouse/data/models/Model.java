package bg.autohouse.data.models;

import javax.persistence.*;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = false)
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(of = "name", callSuper = true)
@Entity
@Table(name = EntityConstants.MODELS)
public class Model extends BaseLongEntity {

  private static final long serialVersionUID = 3257573416154758517L;

  @Column(name = "name", nullable = false)
  private String name;

  @ManyToOne(targetEntity = Maker.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "maker_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = EntityConstants.PREFIX + "fk_models_makers_id"))
  private Maker maker;
}

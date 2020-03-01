package bg.autohouse.data.models;

import javax.persistence.*;
import javax.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "models")
public class Model extends BaseLongEntity {

  private static final long serialVersionUID = 3257573416154758517L;

  @Column(name = "name", nullable = false)
  private String name;

  @ManyToOne(targetEntity = Maker.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "maker_id", referencedColumnName = "id", nullable = false)
  private Maker maker;

  @Builder
  public Model(final Long id, final String name, final Maker maker) {
    super(id);
    this.name = name;
    this.maker = maker;
  }
}

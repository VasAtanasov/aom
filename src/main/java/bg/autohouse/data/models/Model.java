package bg.autohouse.data.models;

import static bg.autohouse.validation.ValidationMessages.MAKER_NULL;

import bg.autohouse.validation.maker.ModelName;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Entity
@Table(name = EntityConstants.MODELS)
public class Model extends BaseLongEntity {

  private static final long serialVersionUID = 3257573416154758517L;

  @Column(name = "name", nullable = false)
  @ModelName
  private String name;

  @NotNull(message = MAKER_NULL)
  @ManyToOne(targetEntity = Maker.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "maker_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = EntityConstants.PREFIX + "fk_models_makers_id"))
  private Maker maker;

  @OneToMany(
      mappedBy = "model",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Trim> trims = new ArrayList<>();

  @Builder
  public Model(Long id, String name) {
    super(id);
    this.name = name;
  }
}

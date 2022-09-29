package bg.autohouse.core.domain.model;

import bg.autohouse.core.domain.model.common.AbstractLongEntity;
import bg.autohouse.core.domain.validation.maker.ModelName;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static bg.autohouse.core.domain.validation.ValidationMessages.MAKER_NULL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Entity
@Table(name = EntityConstants.MODELS)
public class Model extends AbstractLongEntity {
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

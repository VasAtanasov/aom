package bg.autohouse.core.domain.model;

import bg.autohouse.core.domain.model.common.AbstractLongEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = EntityConstants.TRIMS)
public class Trim extends AbstractLongEntity
{

  private static final long serialVersionUID = 2140536691508177039L;

  @ManyToOne(targetEntity = Model.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "model_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = EntityConstants.PREFIX + "fk_trims_models_id"))
  private Model model;

  @Column(name = "year", nullable = false)
  private Integer year;

  @Column(name = "trim", nullable = false)
  private String trim;
}

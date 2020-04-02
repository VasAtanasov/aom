package bg.autohouse.data.models;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = EntityConstants.TRIMS)
public class Trim extends BaseLongEntity {

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

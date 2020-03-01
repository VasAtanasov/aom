package bg.autohouse.data.models;

import java.util.ArrayList;
import java.util.List;
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
@Table(name = "makers")
public class Maker extends BaseLongEntity {

  private static final long serialVersionUID = -2811586455073721689L;

  @Column(name = "name", unique = true, nullable = false)
  private String name;

  @OneToMany(
      mappedBy = "maker",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  private List<Model> models = new ArrayList<>();

  @Transient
  public void addModel(Model model) {
    if (models == null) {
      models = new ArrayList<>();
    }
    models.add(model);
    model.setMaker(this);
  }

  @Builder
  public Maker(final Long id, final String name) {
    super(id);
    this.name = name;
  }
}

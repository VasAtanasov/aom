package bg.autohouse.core.domain.model;

import bg.autohouse.core.domain.model.common.AbstractLongEntity;
import bg.autohouse.core.domain.validation.maker.MakerName;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = EntityConstants.MAKERS)
public class Maker extends AbstractLongEntity {
  private static final long serialVersionUID = -2811586455073721689L;

  @MakerName
  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @OneToMany(
      mappedBy = "maker",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Model> models = new ArrayList<>();

  @Builder
  public Maker(Long id, String name) {
    super(id);
    this.name = name;
  }
}

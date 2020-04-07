package bg.autohouse.data.models;

import bg.autohouse.validation.maker.MakerName;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.persistence.Entity;
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
@ToString(callSuper = false)
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(of = "name", callSuper = true)
@Entity
@Table(name = EntityConstants.MAKERS)
public class Maker extends BaseLongEntity {

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

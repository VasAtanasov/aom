package bg.autohouse.data.models.geo;

import bg.autohouse.data.models.BaseLongEntity;
import bg.autohouse.data.models.EntityConstants;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = EntityConstants.PROVINCES)
public class Province extends BaseLongEntity {
  private static final long serialVersionUID = 343405233728014739L;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "ekatte", nullable = false)
  private Integer ekatte;

  @OneToMany(
      mappedBy = "province",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Location> models = new ArrayList<>();
}

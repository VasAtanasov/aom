package bg.autohouse.data.models.account;

import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("2")
public class Private extends Account {
  private static final long serialVersionUID = 7637953173577794588L;

  @Column(name = "displayed_name")
  private String displayedName;
}

package bg.autohouse.data.models.account;

import bg.autohouse.data.models.EntityConstants;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = EntityConstants.PRIVATE)
@DiscriminatorValue("2")
public class Private extends Account {
  private static final long serialVersionUID = 7637953173577794588L;

  @Column(name = "displayed_name")
  private String displayedName;
}

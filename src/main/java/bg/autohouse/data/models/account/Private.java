package bg.autohouse.data.models.account;

import bg.autohouse.data.models.EntityConstants;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = EntityConstants.PRIVATE)
public class Private extends Account {

  private static final long serialVersionUID = 7637953173577794588L;
  private static final int DEFAULT_PRIVATE_USER_OFFERS_COUNT = 5;

  @Column(name = "max_offers_count")
  private int maxOffersCount = DEFAULT_PRIVATE_USER_OFFERS_COUNT;
}

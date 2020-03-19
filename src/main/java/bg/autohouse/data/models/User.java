package bg.autohouse.data.models;

import bg.autohouse.data.models.enums.Seller;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = EntityConstants.USERS)
public class User extends BaseUuidEntity {

  private static final long serialVersionUID = -4468841758676373460L;

  @Column(name = "seller")
  @Enumerated(EnumType.STRING)
  private Seller seller;
}

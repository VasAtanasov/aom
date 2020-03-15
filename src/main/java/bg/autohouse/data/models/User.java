package bg.autohouse.data.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = EntityConstants.USERS)
public class User extends BaseUuidEntity {

  private static final long serialVersionUID = -4468841758676373460L;
}

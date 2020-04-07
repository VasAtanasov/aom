package bg.autohouse.data.models.account;

import bg.autohouse.data.models.BaseUuidEntity;
import bg.autohouse.data.models.User;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Account extends BaseUuidEntity {

  private static final long serialVersionUID = 1L;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @MapsId
  @JoinColumn(name = "id")
  private User owner;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  public abstract int getMaxOffersCount();
}

package bg.autohouse.security.jwt;

import bg.autohouse.data.models.BaseUuidEntity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Entity
@Table(name = "tokens")
@NamedQuery(
    name = JwtAuthenticationToken.DELETE_TOKEN,
    query = "DELETE FROM JwtAuthenticationToken t where t.expirationTime <= :now")
public class JwtAuthenticationToken extends BaseUuidEntity {

  public static final String DELETE_TOKEN = "JwtAuthenticationToken.deleteAllExpiredSince";
  private static final long serialVersionUID = -3553731653107716142L;

  @Column(name = "value", nullable = false, columnDefinition = "TEXT")
  private String value;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private JwtAuthenticationTokenType type;

  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "expiration_time")
  private Date expirationTime;
}

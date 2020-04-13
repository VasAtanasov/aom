package bg.autohouse.security.jwt;

import bg.autohouse.data.models.BaseLongEntity;
import bg.autohouse.data.models.EntityConstants;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = EntityConstants.TOKENS,
    indexes = {
      @Index(name = "idx_token_username_type", columnList = "username, type", unique = false)
    })
@NamedQuery(
    name = JwtToken.DELETE_TOKEN,
    query = "DELETE FROM JwtToken t where t.expirationTime <= :now")
public class JwtToken extends BaseLongEntity {

  public static final String DELETE_TOKEN = "JwtToken.deleteAllExpiredSince";
  private static final long serialVersionUID = -3553731653107716142L;

  @Column(name = "value", nullable = false, updatable = false, columnDefinition = "TEXT")
  private String value;

  @Column(name = "token_uid", nullable = false, updatable = false)
  private String tokenUid;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false, updatable = false)
  private JwtTokenType type;

  @Column(name = "username", nullable = false, updatable = false)
  private String username;

  @Column(name = "user_id", updatable = false)
  private String userId;

  @Column(name = "expiration_time", nullable = false, updatable = false)
  private Date expirationTime;

  @Column(name = "revoked")
  private boolean revoked = false;
}

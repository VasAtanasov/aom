package bg.autohouse.security.jwt;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class JwtAuthenticationToken {
  private String value;
  private JwtAuthenticationTokenType type;
  private String username;
  private Date expirationTime;
}

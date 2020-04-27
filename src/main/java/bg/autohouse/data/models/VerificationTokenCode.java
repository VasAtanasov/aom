package bg.autohouse.data.models;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
    name = EntityConstants.VERIFICATION_TOKEN_CODES,
    indexes = {@Index(name = "idx_code_username", columnList = "username, code", unique = false)})
public class VerificationTokenCode extends BaseLongEntity {

  private static final long serialVersionUID = -1263466037844427469L;

  private static final int MAX_TOKEN_ACCESS_ATTEMPTS = 5;

  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "user_id", columnDefinition = "BINARY(16)")
  private UUID userId;

  @Column(name = "code", nullable = false)
  protected String code;

  @Column(name = "expiry_date")
  private Date expiryDateTime;

  @Column(name = "token_access_attempts")
  private int tokenAccessAttempts = 0;

  public VerificationTokenCode(String username, String code, UUID userId) {
    Objects.requireNonNull(username);
    Objects.requireNonNull(code);
    this.code = code;
    this.username = username;
    this.userId = userId;
  }

  @PreUpdate
  @PrePersist
  public void addTimeStamps() {
    if (expiryDateTime == null) {
      Date createdAt = super.getCreatedAt();
      long defaultExpiration = Duration.ofMinutes(5L).toMillis();
      expiryDateTime = new Date(createdAt.getTime() + defaultExpiration);
    }
  }

  public void incrementTokenAttempts() {
    this.tokenAccessAttempts = this.tokenAccessAttempts + 1;
    if (this.tokenAccessAttempts > MAX_TOKEN_ACCESS_ATTEMPTS) {
      this.setExpiryDateTime(new Date());
    }
    log.info("token code {}, access attempts now = {}", this.code, this.tokenAccessAttempts);
  }
}

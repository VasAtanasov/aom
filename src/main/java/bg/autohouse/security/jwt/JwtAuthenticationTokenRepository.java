package bg.autohouse.security.jwt;

import java.util.Date;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtAuthenticationTokenRepository
    extends JpaRepository<JwtAuthenticationToken, String>,
        JpaSpecificationExecutor<JwtAuthenticationToken> {

  @Modifying
  void deleteAllExpiredSince(Date now);

  Optional<JwtAuthenticationToken> findByValue(String value);
}

package bg.autohouse.security.jwt;

import java.util.Date;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtTokenRepository
    extends JpaRepository<JwtToken, String>, JpaSpecificationExecutor<JwtToken> {

  @Modifying
  void deleteAllExpiredSince(Date now);

  Optional<JwtToken> findByValue(String value);

  Optional<JwtToken> findByTokenUid(String tokenUid);
}

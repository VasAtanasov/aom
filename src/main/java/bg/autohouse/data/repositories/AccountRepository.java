package bg.autohouse.data.repositories;

import bg.autohouse.data.models.account.Account;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository
    extends JpaRepository<Account, UUID>, JpaSpecificationExecutor<Account> {
  boolean existsByUserId(UUID userId);

  Optional<Account> findByUserId(UUID userId);
}

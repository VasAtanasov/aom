package bg.autohouse.data.repositories;

import bg.autohouse.data.models.account.Account;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository
    extends JpaRepository<Account, UUID>, JpaSpecificationExecutor<Account> {
  boolean existsByUserId(UUID userId);

  @Query(
      "SELECT acc "
          + "FROM Account acc "
          + "JOIN FETCH acc.user usr "
          + "JOIN FETCH acc.address adr "
          + "JOIN FETCH adr.location loc "
          + "WHERE usr.id = :userId")
  Optional<Account> findByUserId(UUID userId);
}

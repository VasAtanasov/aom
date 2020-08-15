package bg.autohouse.data.repositories;

import bg.autohouse.data.models.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository
    extends JpaRepository<Account, UUID>, JpaSpecificationExecutor<Account> {

  boolean existsByUserId(UUID userId);

  boolean existsByUserUsername(String userUsername);

  @Query(
      "SELECT acc "
          + "FROM Account acc "
          + "LEFT JOIN FETCH acc.user usr "
          + "LEFT JOIN FETCH acc.address adr "
          + "LEFT JOIN FETCH adr.location loc "
          + "WHERE usr.id = :userId")
  Optional<Account> findByUserId(UUID userId);
}

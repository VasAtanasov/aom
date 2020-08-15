package bg.autohouse.data.repositories;

import bg.autohouse.data.models.account.AccountLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountLogRepository extends JpaRepository<AccountLog, Long> {}

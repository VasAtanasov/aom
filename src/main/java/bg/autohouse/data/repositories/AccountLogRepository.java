package bg.autohouse.data.repositories;

import bg.autohouse.data.models.account.AccountLog;
import bg.autohouse.data.models.enums.AccountLogType;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountLogRepository extends JpaRepository<AccountLog, Long> {
  List<AccountLog> findByAccountLogType(AccountLogType type, Pageable pageable);
}

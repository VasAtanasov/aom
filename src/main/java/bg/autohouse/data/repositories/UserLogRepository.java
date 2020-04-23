package bg.autohouse.data.repositories;

import bg.autohouse.data.models.UserLog;
import bg.autohouse.data.models.enums.UserLogType;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLogRepository extends JpaRepository<UserLog, UUID> {

  void deleteAllByUserIdAndUserLogTypeIn(UUID userId, List<UserLogType> userLogTypeList);

  List<UserLog> findByUserIdInAndUserLogType(Collection<String> userIds, UserLogType userLogType);
}

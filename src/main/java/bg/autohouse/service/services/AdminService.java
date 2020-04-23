package bg.autohouse.service.services;

import bg.autohouse.data.projections.user.UserIdUsername;
import java.util.List;
import java.util.UUID;

public interface AdminService {
  void bulkRegisterUsers(UUID adminId, List<String> usernames);

  List<UserIdUsername> loadAllUsers();
}

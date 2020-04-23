package bg.autohouse.service.services;

import bg.autohouse.service.models.UserServiceModel;
import java.util.List;
import java.util.UUID;

public interface AdminService {
  void bulkRegisterUsers(UUID adminId, List<String> usernames);

  List<UserServiceModel> loadAllUsers();
}

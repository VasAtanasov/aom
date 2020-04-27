package bg.autohouse.service.services;

import bg.autohouse.data.projections.user.UserIdUsername;
import bg.autohouse.service.models.UserServiceModel;
import bg.autohouse.service.models.account.AccountCreateServiceModel;
import java.util.List;
import java.util.UUID;

public interface AdminService {
  List<UserServiceModel> bulkRegisterUsers(UUID adminId, List<String> usernames);

  List<UserIdUsername> loadAllUsers();

  int bulkCreateAccounts(UUID id, List<AccountCreateServiceModel> mapAll);
}

package bg.autohouse.service.services;

import bg.autohouse.service.models.UserServiceModel;
import bg.autohouse.service.models.account.AccountCreateServiceModel;
import bg.autohouse.service.models.user.UserRowServiceModel;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {
  List<UserServiceModel> bulkRegisterUsers(UUID adminId, List<String> usernames);

  Page<UserRowServiceModel> loadUsersPage(Pageable pageable);

  int bulkCreateAccounts(UUID id, List<AccountCreateServiceModel> mapAll);
}

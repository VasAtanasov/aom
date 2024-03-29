package bg.autohouse.service.services;

import bg.autohouse.data.models.User;
import bg.autohouse.service.models.UserServiceModel;
import bg.autohouse.service.models.account.AccountCreateServiceModel;
import bg.autohouse.service.models.user.ChangeRoleServiceModel;
import bg.autohouse.service.models.user.UserAdminDetailsServiceModel;
import bg.autohouse.service.models.user.UserRowServiceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface AdminService {
  String getRevision();

  UserAdminDetailsServiceModel loadUserDetails(UUID userId, UUID adminId);

  UserRowServiceModel changeRole(ChangeRoleServiceModel request, User user);

  List<UserServiceModel> bulkRegisterUsers(UUID adminId, List<String> usernames);

  Page<UserRowServiceModel> loadUsersPage(Pageable pageable);

  int bulkCreateAccounts(UUID id, List<AccountCreateServiceModel> mapAll);

  boolean toggleActive(UUID userId, UUID adminId);
}

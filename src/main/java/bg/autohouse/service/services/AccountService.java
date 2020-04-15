package bg.autohouse.service.services;

import bg.autohouse.service.models.AccountServiceModel;

public interface AccountService {
  boolean isHasAccount(String id);

  AccountServiceModel loadAccountForUser(String userId);

  void createPrivateSellerAccount(AccountServiceModel model, String ownerId);
}

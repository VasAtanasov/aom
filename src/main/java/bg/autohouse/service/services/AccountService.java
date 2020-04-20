package bg.autohouse.service.services;

import bg.autohouse.service.models.account.*;

public interface AccountService {
  boolean hasAccount(String id);

  AccountServiceModel loadAccountForUser(String userId);

  AccountServiceModel createPrivateSellerAccount(AccountServiceModel model, String ownerId);

  AccountServiceModel createDealerAccount(AccountServiceModel model, String id);
}

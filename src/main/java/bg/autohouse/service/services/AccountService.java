package bg.autohouse.service.services;

import bg.autohouse.service.models.account.*;

public interface AccountService {
  boolean hasAccount(String id);

  AccountServiceModel loadAccountForUser(String userId);

  PrivateAccountServiceModel createPrivateSellerAccount(
      PrivateAccountServiceModel model, String ownerId);

  DealerAccountServiceModel createDealerAccount(DealerAccountServiceModel model, String id);
}

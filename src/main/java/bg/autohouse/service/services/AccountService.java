package bg.autohouse.service.services;

import bg.autohouse.service.models.PrivateSellerAccountServiceModel;

public interface AccountService {
  void createPrivateSellerAccount(PrivateSellerAccountServiceModel model, String ownerId);

  boolean isHasAccount(String id);
}

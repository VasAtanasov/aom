package bg.autohouse.service.services;

import bg.autohouse.data.models.User;
import bg.autohouse.service.models.PrivateSellerAccountServiceModel;

public interface AccountService {
  void createPrivateSellerAccount(PrivateSellerAccountServiceModel model, User owner);
}

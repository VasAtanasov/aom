package bg.autohouse.service.services;

import bg.autohouse.service.models.account.*;
import java.util.UUID;

public interface AccountService {
  boolean hasAccount(UUID id);

  AccountServiceModel loadAccountForUser(UUID userId);

  AccountServiceModel createPrivateSellerAccount(AccountServiceModel model, UUID ownerId);

  AccountServiceModel createDealerAccount(AccountServiceModel model, UUID ownerId);
}

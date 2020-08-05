package bg.autohouse.service.services;

import bg.autohouse.service.models.account.*;
import java.util.UUID;

public interface AccountService {
  boolean hasAccount(UUID id);

  boolean hasAccount(String username);

  AccountServiceModel loadAccountForUser(UUID userId);

  AccountServiceModel createOrUpdatePrivateSellerAccount(AccountServiceModel model, UUID ownerId);

  AccountServiceModel createOrUpdateDealerAccount(AccountServiceModel model, UUID ownerId);
}

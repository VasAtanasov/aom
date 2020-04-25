package bg.autohouse.service.models.account;

import java.util.UUID;
import lombok.Getter;

@Getter
public class AccountCreateServiceModel {
  private UUID id;
  private String username;
  private AccountServiceModel account;
}

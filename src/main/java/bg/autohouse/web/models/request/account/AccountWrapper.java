package bg.autohouse.web.models.request.account;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AccountWrapper {
  @NotNull @NotNull private String username;
  @NotNull private UUID id;
  @NotNull private DealerAccountCreateUpdateRequest account;
}

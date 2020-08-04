package bg.autohouse.web.models.request.account;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class AccountWrapper {
  @NotNull private UUID id;
  @NotNull @NotNull private String username;
  @NotNull private AccountCreateRequest account;
}

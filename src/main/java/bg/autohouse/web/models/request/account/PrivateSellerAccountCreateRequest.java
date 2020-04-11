package bg.autohouse.web.models.request.account;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrivateSellerAccountCreateRequest {
  private String firstName;
  private String lastName;
}

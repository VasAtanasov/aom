package bg.autohouse.config;

import bg.autohouse.web.models.request.account.AccountCreateRequest;

public class TestData {
  public static final AccountCreateRequest accountRequest1 =
      AccountCreateRequest.builder().firstName("").lastName("").build();
}

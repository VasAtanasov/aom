package bg.autohouse.web.controllers;

import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;

import bg.autohouse.config.WebConfiguration;
import bg.autohouse.data.models.User;
import bg.autohouse.data.models.enums.AccountType;
import bg.autohouse.security.authentication.LoggedUser;
import bg.autohouse.service.models.account.AccountServiceModel;
import bg.autohouse.service.services.AccountService;
import bg.autohouse.util.Assert;
import bg.autohouse.util.EnumUtils;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.request.account.DealerAccountCreateUpdateRequest;
import bg.autohouse.web.models.request.account.PrivateAccountCreateUpdateRequest;
import bg.autohouse.web.util.RestUtil;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(WebConfiguration.URL_ACCOUNT_BASE)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AccountController extends BaseController {

  private final AccountService accountService;
  private final ModelMapperWrapper modelMapper;

  @PostMapping(
      value = "/private-create",
      produces = {APP_V1_MEDIA_TYPE_JSON},
      consumes = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> createPrivateSellerAccount(
      @Valid @RequestBody PrivateAccountCreateUpdateRequest request, @LoggedUser User user) {
    AccountServiceModel model = modelMapper.map(request, AccountServiceModel.class);
    return createAccount(model, user.getId());
  }

  @PostMapping(
      value = "/dealer-request",
      produces = {APP_V1_MEDIA_TYPE_JSON},
      consumes = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> requestDealerAccount(
      @Valid @RequestBody DealerAccountCreateUpdateRequest request, @LoggedUser User user) {
    AccountServiceModel model = modelMapper.map(request, AccountServiceModel.class);
    return createAccount(model, user.getId());
  }

  private ResponseEntity<?> createAccount(AccountServiceModel model, String ownerId) {
    if (Assert.isEmpty(model) || Assert.isEmpty(ownerId)) {
      log.error("Missing account data");
      return RestUtil.errorResponse(RestMessage.INVALID_ACCOUNT_DATA);
    }

    if (accountService.hasAccount(ownerId)) {
      log.error("User already has set account");
      return RestUtil.errorResponse(RestMessage.USER_ALREADY_HAS_ACCOUNT);
    }

    AccountType accountType =
        EnumUtils.fromString(model.getAccountType(), AccountType.class)
            .orElseThrow(() -> new IllegalStateException(RestMessage.INVALID_ACCOUNT_TYPE.name()));

    if (AccountType.DEALER.equals(accountType)) {
      AccountServiceModel account = accountService.createDealerAccount(model, ownerId);
      return RestUtil.okResponse(RestMessage.DEALER_ACCOUNT_REQUEST_CREATED, account);
    }

    AccountServiceModel account = accountService.createPrivateSellerAccount(model, ownerId);
    return RestUtil.okResponse(RestMessage.PRIVATE_SELLER_ACCOUNT_CREATED, account);
  }
}

package bg.autohouse.web.controllers;

import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;

import bg.autohouse.config.WebConfiguration;
import bg.autohouse.data.models.User;
import bg.autohouse.data.models.enums.AccountType;
import bg.autohouse.security.authentication.LoggedUser;
import bg.autohouse.service.models.account.AccountServiceModel;
import bg.autohouse.service.services.AccountService;
import bg.autohouse.util.EnumUtils;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.request.account.DealerAccountCreateUpdateRequest;
import bg.autohouse.web.models.request.account.PrivateAccountCreateUpdateRequest;
import bg.autohouse.web.util.RestUtil;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WebConfiguration.URL_ACCOUNT_BASE)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AccountController extends BaseController {

  private final AccountService accountService;
  private final ModelMapperWrapper modelMapper;

  @GetMapping(
      value = "/user-account",
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> fetchUserAccount(@LoggedUser User user) {
    AccountServiceModel model =
        modelMapper.map(accountService.loadAccountForUser(user.getId()), AccountServiceModel.class);
    return RestUtil.okResponse(model);
  }

  @PostMapping(
      value = "/private-create",
      produces = {APP_V1_MEDIA_TYPE_JSON},
      consumes = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> createOrUpdatePrivateSellerAccount(
      @Valid @RequestBody PrivateAccountCreateUpdateRequest request, @LoggedUser User user) {
    AccountServiceModel model = modelMapper.map(request, AccountServiceModel.class);
    return createAccount(model, user.getId(), AccountType.PRIVATE);
  }

  @PostMapping(
      value = "/dealer-create",
      produces = {APP_V1_MEDIA_TYPE_JSON},
      consumes = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> createOrUpdateDealerAccount(
      @Valid @RequestBody DealerAccountCreateUpdateRequest request, @LoggedUser User user) {
    AccountServiceModel model = modelMapper.map(request, AccountServiceModel.class);
    return createAccount(model, user.getId(), AccountType.DEALER);
  }

  private ResponseEntity<?> createAccount(
      AccountServiceModel model, UUID ownerId, AccountType requiredAccountType) {
    AccountType accountType =
        EnumUtils.fromString(model.getAccountType(), AccountType.class)
            .orElseThrow(() -> new IllegalStateException(RestMessage.INVALID_ACCOUNT_TYPE.name()));
    if (AccountType.DEALER.equals(accountType) && requiredAccountType.equals(AccountType.DEALER)) {
      AccountServiceModel account = accountService.createOrUpdateDealerAccount(model, ownerId);
      return RestUtil.okResponse(RestMessage.DEALER_ACCOUNT_OPERATION_SUCCESSFUL, account);
    }
    if (AccountType.PRIVATE.equals(accountType)
        && requiredAccountType.equals(AccountType.PRIVATE)) {
      AccountServiceModel account =
          accountService.createOrUpdatePrivateSellerAccount(model, ownerId);
      return RestUtil.okResponse(RestMessage.PRIVATE_SELLER_ACCOUNT_OPERATION_SUCCESSFUL, account);
    }
    return RestUtil.errorResponse(RestMessage.INVALID_ACCOUNT_DATA);
  }
}

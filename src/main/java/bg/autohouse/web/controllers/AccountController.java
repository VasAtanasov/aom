package bg.autohouse.web.controllers;

import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;

import bg.autohouse.config.WebConfiguration;
import bg.autohouse.data.models.User;
import bg.autohouse.security.authentication.LoggedUser;
import bg.autohouse.service.models.account.DealerAccountServiceModel;
import bg.autohouse.service.models.account.PrivateAccountServiceModel;
import bg.autohouse.service.services.AccountService;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.request.account.DealerAccountCreateUpdateRequest;
import bg.autohouse.web.models.request.account.PrivateAccountCreateUpdateRequest;
import bg.autohouse.web.util.RestUtil;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    if (accountService.isHasAccount(user.getId())) {
      log.error("User already has set account");
      return RestUtil.errorResponse(HttpStatus.BAD_REQUEST, RestMessage.USER_ALREADY_HAS_ACCOUNT);
    }

    PrivateAccountServiceModel model = modelMapper.map(request, PrivateAccountServiceModel.class);

    PrivateAccountServiceModel account =
        accountService.createPrivateSellerAccount(model, user.getId());

    return RestUtil.okayResponseWithData(RestMessage.PRIVATE_SELLER_ACCOUNT_CREATED, account);
  }

  @PostMapping(
      value = "/dealer-request",
      produces = {APP_V1_MEDIA_TYPE_JSON},
      consumes = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> requestDealerAccount(
      @Valid @RequestBody DealerAccountCreateUpdateRequest request, @LoggedUser User user) {

    if (accountService.isHasAccount(user.getId())) {
      log.error("User already has set account");
      return RestUtil.errorResponse(HttpStatus.BAD_REQUEST, RestMessage.USER_ALREADY_HAS_ACCOUNT);
    }

    DealerAccountServiceModel model = modelMapper.map(request, DealerAccountServiceModel.class);
    DealerAccountServiceModel account = accountService.createDealerAccount(model, user.getId());

    return RestUtil.okayResponseWithData(RestMessage.DEALER_ACCOUNT_REQUEST_CREATED, account);
  }
}

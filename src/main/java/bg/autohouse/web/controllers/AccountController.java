package bg.autohouse.web.controllers;

import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;

import bg.autohouse.config.WebConfiguration;
import bg.autohouse.data.models.User;
import bg.autohouse.security.authentication.LoggedUser;
import bg.autohouse.service.models.AccountServiceModel;
import bg.autohouse.service.services.AccountService;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.request.account.AccountCreateRequest;
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
      @Valid @RequestBody AccountCreateRequest request, @LoggedUser User user) {

    if (accountService.isHasAccount(user.getId())) {
      log.error("User already has set account");
      return RestUtil.errorResponse(HttpStatus.BAD_REQUEST, RestMessage.USER_ALREADY_HAS_ACCOUNT);
    }

    AccountServiceModel model = modelMapper.map(request, AccountServiceModel.class);

    accountService.createPrivateSellerAccount(model, user.getId());
    return RestUtil.messageOkayResponse(RestMessage.PRIVATE_SELLER_ACCOUNT_CREATED);
  }

  @PostMapping(
      value = "/dealer-request",
      produces = {APP_V1_MEDIA_TYPE_JSON},
      consumes = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> requestDealerAccount(@LoggedUser User user) {

    if (accountService.isHasAccount(user.getId())) {
      log.error("User already has set account");
      return RestUtil.errorResponse(HttpStatus.BAD_REQUEST, RestMessage.USER_ALREADY_HAS_ACCOUNT);
    }

    // TODO implement dealer account request
    // TODO only admins can finish dealer's account creation
    return RestUtil.messageOkayResponse(RestMessage.DEALER_ACCOUNT_REQUEST_CREATED);
  }
}

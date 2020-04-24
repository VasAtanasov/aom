package bg.autohouse.web.controllers;

import bg.autohouse.config.WebConfiguration;
import bg.autohouse.data.models.User;
import bg.autohouse.security.authentication.LoggedUser;
import bg.autohouse.service.models.UserServiceModel;
import bg.autohouse.service.services.AdminService;
import bg.autohouse.web.models.request.account.AccountWrapper;
import bg.autohouse.web.models.wrappers.ListWrapper;
import bg.autohouse.web.util.RestUtil;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(WebConfiguration.URL_ADMIN_BASE)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AdminController {

  private final AdminService adminService;

  @PostMapping(value = "/users/bulk")
  public ResponseEntity<?> bulkInsert(@Valid @RequestBody ListWrapper list, @LoggedUser User user) {
    log.info("Inserting bulk emails");
    List<UserServiceModel> users = adminService.bulkRegisterUsers(user.getId(), list.getValues());
    return RestUtil.okResponse(users);
  }

  @GetMapping(value = "/users/list")
  public ResponseEntity<?> getUsersList() {
    return RestUtil.okResponse(adminService.loadAllUsers());
  }

  @GetMapping(value = "/locations/list")
  public ResponseEntity<?> getLocationsList() {
    return RestUtil.okResponse(adminService.loadAllLocations());
  }

  @PostMapping(value = "/accounts/bulk")
  public ResponseEntity<?> createAccountsForUsers(
      @Valid @RequestBody List<AccountWrapper> account, @LoggedUser User user) {

    int a = 5;

    return ResponseEntity.ok().build();
  }
}
package bg.autohouse.web.controllers;

import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;

import bg.autohouse.config.WebConfiguration;
import bg.autohouse.data.models.User;
import bg.autohouse.errors.RoleChangeException;
import bg.autohouse.security.authentication.LoggedUser;
import bg.autohouse.service.models.UserServiceModel;
import bg.autohouse.service.models.account.AccountCreateServiceModel;
import bg.autohouse.service.models.user.ChangeRoleServiceModel;
import bg.autohouse.service.models.user.UserAdminDetailsServiceModel;
import bg.autohouse.service.models.user.UserRowServiceModel;
import bg.autohouse.service.services.AdminService;
import bg.autohouse.service.services.LocationService;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.request.ChangeRoleRequest;
import bg.autohouse.web.models.request.ProvinceLocationsCreateRequest;
import bg.autohouse.web.models.request.account.AccountWrapper;
import bg.autohouse.web.models.wrappers.ListWrapper;
import bg.autohouse.web.util.RestUtil;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(WebConfiguration.URL_ADMIN_BASE)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AdminController extends BaseController {

  private final AdminService adminService;
  private final ModelMapperWrapper modelMapper;
  private final LocationService locationService;

  @GetMapping(
      value = "/users/list",
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> getUsersList(
      @PageableDefault(size = DEFAULT_PAGE_SIZE, sort = SORT, direction = Sort.Direction.DESC)
          Pageable pageable) {
    Page<UserRowServiceModel> usersPage = adminService.loadUsersPage(pageable);
    return ResponseEntity.ok(usersPage);
  }

  @GetMapping(
      value = "/user/details/{userId}",
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> fetchUserDetails(@PathVariable UUID userId, @LoggedUser User admin) {
    UserAdminDetailsServiceModel model = adminService.loadUserDetails(userId, admin.getId());
    return ResponseEntity.ok(model);
  }

  @GetMapping(
      value = "/user/toggle-active/{userId}",
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> toggleActive(@PathVariable UUID userId, @LoggedUser User admin) {
    return ResponseEntity.ok(adminService.toggleActive(userId, admin.getId()));
  }

  @PostMapping(
      value = "/user/update-roles",
      produces = {APP_V1_MEDIA_TYPE_JSON},
      consumes = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> updateUserRoles(
      @Valid @RequestBody ChangeRoleRequest request, @LoggedUser User user) {
    ChangeRoleServiceModel serviceModel = modelMapper.map(request, ChangeRoleServiceModel.class);
    UserRowServiceModel updatedUser = adminService.changeRole(serviceModel, user);
    return ResponseEntity.ok(updatedUser);
  }

  @ExceptionHandler(RoleChangeException.class)
  public ResponseEntity<?> notAllowedRoleChange(RoleChangeException e) {
    log.error("Role change not allowed!", e);
    return RestUtil.errorResponse(RestMessage.ROLE_CHANGE_NOT_ALLOWED);
  }

  @PostMapping(value = "/users/bulk")
  public ResponseEntity<?> bulkInsert(@Valid @RequestBody ListWrapper list, @LoggedUser User user) {
    log.info("Inserting bulk emails");
    List<UserServiceModel> users = adminService.bulkRegisterUsers(user.getId(), list.getValues());
    return ResponseEntity.ok(users);
  }

  @PostMapping(value = "/accounts/bulk")
  public ResponseEntity<?> createAccountsForUsers(
      @Valid @RequestBody List<AccountWrapper> accounts, @LoggedUser User user) {
    int created =
        adminService.bulkCreateAccounts(
            user.getId(), modelMapper.mapAll(accounts, AccountCreateServiceModel.class));
    log.info("Update: {} users", created);
    return ResponseEntity.ok(created);
  }

  @PostMapping(value = "/provinces/bulk")
  public ResponseEntity<?> createProvinceLocations(
      @Valid @RequestBody List<ProvinceLocationsCreateRequest> provinces) {
    int created = locationService.createProvincesBulk(provinces);
    return ResponseEntity.ok(created);
  }
}

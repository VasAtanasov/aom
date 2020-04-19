package bg.autohouse.service.services.impl;

import static bg.autohouse.errors.ExceptionSupplier.*;

import bg.autohouse.data.models.User;
import bg.autohouse.data.models.UserCreateRequest;
import bg.autohouse.data.models.VerificationTokenCode;
import bg.autohouse.data.models.enums.Role;
import bg.autohouse.data.models.enums.UserLogType;
import bg.autohouse.data.repositories.UserRepository;
import bg.autohouse.data.repositories.UserRequestRepository;
import bg.autohouse.errors.ExceptionsMessages;
import bg.autohouse.errors.NotFoundException;
import bg.autohouse.errors.ResourceAlreadyExistsException;
import bg.autohouse.errors.UsernamePasswordLoginFailedException;
import bg.autohouse.security.jwt.JwtTokenCreateRequest;
import bg.autohouse.security.jwt.JwtTokenService;
import bg.autohouse.security.jwt.JwtTokenType;
import bg.autohouse.service.models.UserRegisterServiceModel;
import bg.autohouse.service.models.UserServiceModel;
import bg.autohouse.service.models.user.AuthorizedUserServiceModel;
import bg.autohouse.service.services.AsyncUserLogger;
import bg.autohouse.service.services.PasswordService;
import bg.autohouse.service.services.UserService;
import bg.autohouse.util.Assert;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.request.UserDetailsUpdateRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserRequestRepository userRequestRepository;
  private final PasswordService passwordService;
  private final ModelMapperWrapper modelMapper;
  private final JwtTokenService jwtService;
  private final PasswordEncoder encoder;
  private final AsyncUserLogger asyncUserService;

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
  public UserDetails loadUserById(String id) {
    return userRepository.findById(id).orElseThrow(noSuchUser);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
  public UserDetails loadUserByUsername(String username) {
    if (Assert.isEmpty(username)) noSuchUser.get();
    return userRepository.findByUsernameIgnoreCase(username).orElseThrow(noSuchUser);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean userExist(String username) {
    return userRepository.existsByUsernameIgnoreCase(username);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean requestExists(String username) {
    return userRequestRepository.existsByUsernameIgnoreCase(username);
  }

  @Override
  public AuthorizedUserServiceModel tryLogin(String username, String password) {
    User user = userRepository.findByUsernameIgnoreCase(username).orElseThrow(loginFailed);

    boolean isValidCredentials = passwordService.validateCredentials(username, password);

    if (!isValidCredentials) {
      throw new UsernamePasswordLoginFailedException();
    }

    JwtTokenCreateRequest tokenRequest = new JwtTokenCreateRequest(JwtTokenType.API_CLIENT, user);
    String token = jwtService.createJwt(tokenRequest);
    log.info("Generated a jwt token, on server is: {}", token);

    return AuthorizedUserServiceModel.builder().user(user).token(token).build();
  }

  @Override
  public String generateUserRegistrationVerifier(UserRegisterServiceModel model) {
    Assert.notNull(model, "Model is required");

    if (userExist(model.getUsername())) {
      throw new ResourceAlreadyExistsException(ExceptionsMessages.USER_ALREADY_EXISTS);
    }

    UserCreateRequest request =
        userRequestRepository.findByUsernameAndIsVerifiedIsFalse(model.getUsername()).orElse(null);

    if (request == null) {
      request = modelMapper.map(model, UserCreateRequest.class);
      log.info("Hashing password...");
      request.setPassword(encoder.encode(model.getPassword()));
      userRequestRepository.save(request);
    }

    VerificationTokenCode token = passwordService.generateShortLivedOTP(model.getUsername());
    return token.getCode();
  }

  // TODO test if user request not present
  // TODO test if user already completed registration
  @Override
  @Transactional
  public UserServiceModel completeRegistration(String username) {
    Assert.notNull(username, "User email address is required");
    log.info("about to try to use email : {}", username);

    long start = System.nanoTime();
    boolean userExists = Assert.has(username) && userExist(username);
    long time = System.nanoTime() - start;
    log.info("User exists check took {} nano seconds", time);

    if (userExists) {
      throw new ResourceAlreadyExistsException(RestMessage.USER_ALREADY_EXISTS);
    }
    // TODO throw generic error
    UserCreateRequest request =
        userRequestRepository.findByUsernameAndIsVerifiedIsFalse(username).orElseThrow(noSuchUser);

    User user = modelMapper.map(request, User.class);
    user.setId(null);
    log.info("Saving user entity...");
    user.setRoles(getInheritedRolesFromRole(Role.USER));
    userRepository.saveAndFlush(user);
    asyncUserService.recordUserLog(
        user.getId(), UserLogType.CREATED_IN_DB, "User registration created");
    request.verify();
    userRequestRepository.save(request);
    return modelMapper.map(user, UserServiceModel.class);
  }

  @Override
  @Transactional
  public String regenerateUserVerifier(String username) {
    Assert.notNull(username, "Username is required");
    // TODO Change exception and message
    if (!userExist(username)) {
      throw new UsernameNotFoundException(ExceptionsMessages.USER_NOT_FOUND_USERNAME);
    }

    VerificationTokenCode newTokenCode = passwordService.generateShortLivedOTP(username);
    return newTokenCode.getCode();
  }

  @Override
  public UserServiceModel updateUser(
      String userId, UserDetailsUpdateRequest user, User loggedUser) {
    // TODO Change exception and message

    User userEntity =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException(ExceptionsMessages.NO_RECORD_FOUND));
    // TODO Change exception and message

    if (!userEntity.getUsername().equals(loggedUser.getUsername())) {
      throw new IllegalStateException(ExceptionsMessages.INVALID_UPDATE_OPERATION);
    }
    // TODO update User profile
    // userEntity.setFirstName(user.getFirstName());
    // userEntity.setLastName(user.getLastName());

    userRepository.save(userEntity);

    return modelMapper.map(userEntity, UserServiceModel.class);
  }

  private Set<Role> getInheritedRolesFromRole(Role role) {
    List<Role> allRoles = Arrays.stream(Role.values()).collect(Collectors.toList());
    int index = allRoles.indexOf(role);
    return new HashSet<>(allRoles.subList(index, allRoles.size()));
  }

  @Override
  @Transactional
  public void updateHasImage(String userId, boolean hasImage) {
    // TODO Change exception and message

    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException(ExceptionsMessages.USER_NOT_FOUND_ID));
    // TODO set account has image
    // user.setHasImage(hasImage);
    userRepository.save(user);
  }
}

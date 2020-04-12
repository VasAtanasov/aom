package bg.autohouse.service.services.impl;

import bg.autohouse.data.models.User;
import bg.autohouse.data.models.UserCreateRequest;
import bg.autohouse.data.models.enums.Role;
import bg.autohouse.data.models.enums.UserLogType;
import bg.autohouse.data.repositories.UserRepository;
import bg.autohouse.data.repositories.UserRequestRepository;
import bg.autohouse.errors.ExceptionsMessages;
import bg.autohouse.errors.ResourceAlreadyExistsException;
import bg.autohouse.security.jwt.JwtToken;
import bg.autohouse.service.models.UserRegisterServiceModel;
import bg.autohouse.service.models.UserServiceModel;
import bg.autohouse.service.services.AsyncUserLogger;
import bg.autohouse.service.services.PasswordService;
import bg.autohouse.service.services.UserService;
import bg.autohouse.util.Assert;
import bg.autohouse.util.ModelMapperWrapper;
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
  private final PasswordEncoder encoder;
  private final AsyncUserLogger asyncUserService;

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
  public UserDetails loadUserById(String id) {
    return userRepository
        .findById(id)
        .orElseThrow(
            () -> new UsernameNotFoundException(ExceptionsMessages.EXCEPTION_USER_NOT_FOUND_ID));
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
  public UserDetails loadUserByUsername(String username) {
    if (username == null) {
      throw new UsernameNotFoundException(ExceptionsMessages.INVALID_USER_LOGIN);
    }
    return userRepository
        .findByUsernameIgnoreCase(username)
        .orElseThrow(() -> new UsernameNotFoundException(ExceptionsMessages.NO_SUCH_USERNAME));
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsByUsername(String username) {
    return userRepository.existsByUsernameIgnoreCase(username);
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
  public String generateUserRegistrationVerifier(UserRegisterServiceModel model) {
    Assert.notNull(model, "Model is required");

    if (existsByUsername(model.getUsername())) {
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

    JwtToken token = passwordService.generateRegistrationToken(model.getUsername());
    return token.getValue();
  }

  @Override
  @Transactional
  public UserServiceModel completeRegistration(String username) {
    Assert.notNull(username, "User email address is required");

    UserCreateRequest request =
        userRequestRepository
            .findByUsernameAndIsVerifiedIsFalse(username)
            .orElseThrow(() -> new UsernameNotFoundException("No registration request found"));

    log.info("about to try to use email : {}", request.getUsername());

    String email = request.getUsername();
    long start = System.nanoTime();
    boolean userExists = Assert.has(email) && existsByUsername(email);
    long time = System.nanoTime() - start;
    log.info("User exists check took {} nano seconds", time);

    if (userExists) {
      throw new ResourceAlreadyExistsException(ExceptionsMessages.USER_ALREADY_EXISTS);
    }

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
  public String generatePasswordResetVerifier(String username) {
    Assert.notNull(username, "Username is required");

    if (!existsByUsername(username)) {
      throw new UsernameNotFoundException(ExceptionsMessages.EXCEPTION_USER_NOT_FOUND_ID);
    }

    JwtToken token = passwordService.generatePasswordResetToken(username);
    return token.getValue();
  }

  @Override
  public UserServiceModel updateUser(
      String userId, UserDetailsUpdateRequest user, User loggedUser) {

    User userEntity =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException(ExceptionsMessages.NO_RECORD_FOUND));

    if (!userEntity.getUsername().equals(loggedUser.getUsername())) {
      throw new IllegalStateException(ExceptionsMessages.INVALID_UPDATE_OPERATION);
    }

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
}

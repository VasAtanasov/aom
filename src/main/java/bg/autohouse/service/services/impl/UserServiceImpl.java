package bg.autohouse.service.services.impl;

import bg.autohouse.data.models.User;
import bg.autohouse.data.models.enums.Role;
import bg.autohouse.data.repositories.UserRepository;
import bg.autohouse.errors.ExceptionsMessages;
import bg.autohouse.errors.ResourceAlreadyExistsException;
import bg.autohouse.security.jwt.JwtAuthenticationToken;
import bg.autohouse.security.jwt.JwtAuthenticationTokenProvider;
import bg.autohouse.security.jwt.JwtAuthenticationTokenRepository;
import bg.autohouse.security.jwt.JwtAuthenticationTokenSpecifications;
import bg.autohouse.security.jwt.JwtAuthenticationTokenType;
import bg.autohouse.service.models.UserRegisterServiceModel;
import bg.autohouse.service.models.UserServiceModel;
import bg.autohouse.service.services.EmailService;
import bg.autohouse.service.services.UserService;
import bg.autohouse.util.Assert;
import bg.autohouse.util.EnumUtils;
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
  private final ModelMapperWrapper modelMapper;
  private final PasswordEncoder encoder;
  private final JwtAuthenticationTokenProvider tokenProvider;
  private final JwtAuthenticationTokenRepository tokenRepository;
  private final EmailService emailService;

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
  public Boolean existsByUsername(String username) {
    return userRepository.existsByUsernameIgnoreCase(username);
  }

  @Override
  public Boolean existsByEmail(String email) {
    return userRepository.existsByEmailIgnoreCase(email);
  }

  @Override
  public UserServiceModel register(UserRegisterServiceModel model) {

    if (existsByUsername(model.getUsername())) {
      throw new ResourceAlreadyExistsException(
          String.format(ExceptionsMessages.USERNAME_ALREADY_EXISTS, model.getUsername()));
    }

    if (existsByEmail(model.getEmail())) {
      throw new ResourceAlreadyExistsException(
          String.format(ExceptionsMessages.EMAIL_ALREADY_EXISTS, model.getEmail()));
    }

    User user = modelMapper.map(model, User.class);

    log.info("Hashing password...");
    user.setPassword(encoder.encode(model.getPassword()));
    log.info("Saving user...");
    user.setRoles(getInheritedRolesFromRole(Role.USER));
    userRepository.save(user);

    JwtAuthenticationToken token =
        tokenProvider.generateTokenEntity(user, JwtAuthenticationTokenType.REGISTRATION);

    log.info("Saving token...");
    tokenRepository.save(token);
    log.info("Token value: " + token.getValue());
    log.info("User id: " + user.getId());

    // TODO uncomment to when email enabled
    // emailService.verifyEmail(user.getEmail(), token.getValue());

    return modelMapper.map(user, UserServiceModel.class);
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

    userEntity.setFirstName(user.getFirstName());
    userEntity.setLastName(user.getLastName());

    userRepository.save(userEntity);

    return modelMapper.map(userEntity, UserServiceModel.class);
  }

  @Override
  public boolean verifyEmailToken(String token) {

    if (!Assert.has(token)) {
      return false;
    }

    if (tokenProvider.hasTokenExpired(token)) {
      return false;
    }

    if (!tokenProvider.validateToken(token)) {
      return false;
    }

    String userId = tokenProvider.getUserIdFromJWT(token);

    User user =
        userRepository
            .findById(userId)
            .orElseThrow(
                () ->
                    new UsernameNotFoundException(ExceptionsMessages.EXCEPTION_USER_NOT_FOUND_ID));

    JwtAuthenticationToken tokenEntity =
        tokenRepository
            .findOne(
                JwtAuthenticationTokenSpecifications.forUser(user.getUsername())
                    .and(JwtAuthenticationTokenSpecifications.withValue(token)))
            .orElseThrow(() -> new UsernameNotFoundException(ExceptionsMessages.INVALID_TOKEN));

    // JwtAuthenticationToken tokenEntity =
    //     tokenRepository
    //         .findByValue(token)
    //         .orElseThrow(() -> new UsernameNotFoundException(ExceptionsMessages.INVALID_TOKEN));

    if (!EnumUtils.has(tokenEntity.getType(), JwtAuthenticationTokenType.class)
        && tokenEntity.getType().equals(JwtAuthenticationTokenType.REGISTRATION)) {
      return false;
    }

    user.setEnabled(true);
    userRepository.save(user);
    tokenRepository.delete(tokenEntity);

    return true;
  }

  @Override
  public boolean requestPasswordReset(String email) {

    if (!Assert.has(email)) {
      return false;
    }

    User user = userRepository.findByEmail(email).orElse(null);

    if (user == null) return false;

    if (!user.isEnabled()) {
      log.error("User registration not verified.");
      return false;
    }

    JwtAuthenticationToken passwordResetToken =
        tokenProvider.generateTokenEntity(user, JwtAuthenticationTokenType.RESET);
    log.info("Password reset token: " + passwordResetToken.getValue());
    tokenRepository.save(passwordResetToken);

    // TODO uncomment to when email enabled
    // emailService.sendPasswordResetRequest(
    //     user.getFirstName(), user.getEmail(), passwordResetToken.getValue());

    return true;
  }

  @Override
  public boolean resetPassword(String token, String password) {

    if (!Assert.has(token) && !Assert.has(password)) {
      return false;
    }

    if (tokenProvider.hasTokenExpired(token)) {
      return false;
    }

    if (!tokenProvider.validateToken(token)) {
      return false;
    }

    String userId = tokenProvider.getUserIdFromJWT(token);

    User user =
        userRepository
            .findById(userId)
            .orElseThrow(
                () ->
                    new UsernameNotFoundException(ExceptionsMessages.EXCEPTION_USER_NOT_FOUND_ID));

    JwtAuthenticationToken tokenEntity =
        tokenRepository
            .findByValue(token)
            .orElseThrow(() -> new UsernameNotFoundException(ExceptionsMessages.INVALID_TOKEN));

    if (!EnumUtils.has(tokenEntity.getType(), JwtAuthenticationTokenType.class)
        && tokenEntity.getType().equals(JwtAuthenticationTokenType.RESET)) {
      return false;
    }

    user.setPassword(encoder.encode(password));
    userRepository.save(user);
    tokenRepository.delete(tokenEntity);

    return true;
  }

  private Set<Role> getInheritedRolesFromRole(Role role) {
    List<Role> allRoles = Arrays.stream(Role.values()).collect(Collectors.toList());
    int index = allRoles.indexOf(role);
    return new HashSet<>(allRoles.subList(index, allRoles.size()));
  }
}

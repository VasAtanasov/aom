package bg.autohouse.service.services.impl;

import bg.autohouse.data.models.User;
import bg.autohouse.data.models.enums.Role;
import bg.autohouse.data.repositories.UserRepository;
import bg.autohouse.errors.ExceptionsMessages;
import bg.autohouse.errors.ResourceAlreadyExistsException;
import bg.autohouse.service.models.UserRegisterServiceModel;
import bg.autohouse.service.models.UserServiceModel;
import bg.autohouse.service.services.UserService;
import bg.autohouse.util.ModelMapperWrapper;
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

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
  public UserDetails loadUserById(String id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new UsernameNotFoundException("No user exists under provided id."));
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
  public UserDetails loadUserByUsername(String username) {
    if (username == null) {
      throw new UsernameNotFoundException("Invalid empty login username");
    }
    return userRepository
        .findByUsernameIgnoreCase(username)
        .orElseThrow(
            () -> new UsernameNotFoundException("Could not lookup user, no such username."));
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

    return modelMapper.map(user, UserServiceModel.class);
  }

  private Set<Role> getInheritedRolesFromRole(Role role) {
    List<Role> allRoles = Arrays.stream(Role.values()).collect(Collectors.toList());
    int index = allRoles.indexOf(role);
    return new HashSet<>(allRoles.subList(index, allRoles.size()));
  }
}

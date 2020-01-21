package bg.autohouse.service.services.impl;

import bg.autohouse.common.Constants;
import bg.autohouse.data.models.Role;
import bg.autohouse.data.models.User;
import bg.autohouse.data.repositories.RoleRepository;
import bg.autohouse.data.repositories.UserRepository;
import bg.autohouse.service.models.UserServiceModel;
import bg.autohouse.service.services.UserService;
import bg.autohouse.service.services.UserValidationService;
import bg.autohouse.util.ModelMapperWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static bg.autohouse.common.Constants.*;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceImpl implements UserService {

    private static final String ROLE_PREFIX = "ROLE_";

    private final UserRepository userRepository;
    private final UserValidationService userValidationService;
    private final RoleRepository roleRepository;
    private final ModelMapperWrapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .orElseThrow(() -> new UsernameNotFoundException(EXCEPTION_USERNAME_NOT_FOUND + username));
    }

    @Override
    public UserServiceModel findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .orElseThrow(() -> new UsernameNotFoundException(EXCEPTION_USERNAME_NOT_FOUND + username));
    }

    @Override
    public UserServiceModel findUserByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .orElseThrow(() -> new UsernameNotFoundException(EXCEPTION_EMAIL_NOT_FOUND + email));
    }

    @Override
    public void registerUser(UserServiceModel serviceModel) {

        List<Boolean> validations = List.of(
                userValidationService.isUsernameTaken(serviceModel.getUsername()),
                userValidationService.isEmailTaken(serviceModel.getEmail())
        );

        User user = modelMapper.map(serviceModel, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(serviceModel.getPassword()));

        if (userRepository.count() == 0) {
            user.setRoles(this.getInheritedRolesFromRole("ROOT"));
        } else {
            user.setRoles(this.getInheritedRolesFromRole("USER"));
        }

        userRepository.saveAndFlush(user);
    }

    private Set<Role> getInheritedRolesFromRole(String role) {
        Set<Role> roles = new HashSet<>();
        List<String> allRoles = roleRepository.findAll()
                .stream()
                .map(r -> r.getAuthority().substring(ROLE_PREFIX.length()))
                .collect(Collectors.toUnmodifiableList());

        for (int i = allRoles.indexOf(role.toUpperCase()); i < allRoles.size(); i++) {
            roles.add(roleRepository.findById(i + 1L).orElseThrow());
        }

        return roles;
    }
}

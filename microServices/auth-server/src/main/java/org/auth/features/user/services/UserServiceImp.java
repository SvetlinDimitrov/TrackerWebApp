package org.auth.features.user.services;

import static org.auth.infrastructure.exceptions.ExceptionMessages.USER_NOT_FOUND;
import static org.auth.infrastructure.exceptions.ExceptionMessages.USER_NOT_FOUND_EMAIL;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.auth.features.user.dto.UserFilter;
import org.auth.features.user.entity.User;
import org.auth.features.user.repository.UserRepository;
import org.auth.features.user.repository.UserSpecification;
import org.auth.infrastructure.config.security.services.UserDetailsServiceImpl;
import org.auth.infrastructure.mappers.UserMapper;
import org.example.domain.user.dto.UserEditRequest;
import org.example.domain.user.dto.UserView;
import org.example.exceptions.throwable.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

  private final UserRepository repository;
  private final UserMapper userMapper;
  private final UserDetailsServiceImpl userDetailsService;

  public Page<UserView> getAll(UserFilter filter, Pageable pageable) {
    return repository.findAll(new UserSpecification(filter), pageable).map(userMapper::toView);
  }

  public UserView getById(UUID userId) {
    return userMapper.toView(findById(userId));
  }

  public UserView edit(UserEditRequest userDto, UUID userId) {
    var user = findById(userId);

    userMapper.update(user, userDto);

    return userMapper.toView(repository.save(user));
  }

  public User findByEmail(String email) {
    return repository
        .findByEmail(email)
        .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_EMAIL, email));
  }

  public UserView me() {
    return userMapper.toView(userDetailsService.extractUserPrincipal().user());
  }

  private User findById(UUID userId) {
    return repository
        .findById(userId)
        .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND, userId));
  }
}

package org.auth.infrastructure.rabbitmq;

import static org.auth.infrastructure.exceptions.ExceptionMessages.FORBIDDEN;
import static org.auth.infrastructure.exceptions.ExceptionMessages.USER_NOT_FOUND;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.auth.features.user.dto.UserCreateRequest;
import org.auth.features.user.repository.UserRepository;
import org.auth.infrastructure.mappers.UserMapper;
import org.auth.infrastructure.config.security.services.UserDetailsServiceImpl;
import org.example.domain.user.dto.UserView;
import org.example.domain.user.enums.UserRole;
import org.example.exceptions.throwable.ForbiddenException;
import org.example.exceptions.throwable.NotFoundException;
import org.example.rabbitmq.RabbitMqUserQueues;
import org.example.util.GsonWrapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMqProducer {

  private static final GsonWrapper GSON_WRAPPER = new GsonWrapper();
  private final AmqpTemplate amqpTemplate;
  private final UserMapper userMapper;
  private final UserRepository repository;
  private final UserDetailsServiceImpl userDetailsService;

  public UserView create(UserCreateRequest userDto) {
    var userToSave = userMapper.toEntity(userDto);
    var savedUser = repository.save(userToSave);

    String token = GSON_WRAPPER.toJson(userMapper.toView(repository.save(savedUser)));

    RabbitMqUserQueues.getAllQueuesUserCreate()
        .forEach(queue -> amqpTemplate.convertAndSend(queue, token));

    return userMapper.toView(savedUser);
  }

  public void delete(UUID userId) {
    checkIfUserIsOwner(userId);

    var user =
        repository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND, userId));

    String token = userId.toString();

    RabbitMqUserQueues.getAllQueuesUserDeletion()
        .forEach(queue -> amqpTemplate.convertAndSend(queue, token));

    repository.delete(user);
  }

  private void checkIfUserIsOwner(UUID userId) {
    var userDetails = userDetailsService.extractUserPrincipal();

    if (!userDetails.getId().equals(userId) && UserRole.USER == userDetails.user().getRole()) {
      throw new ForbiddenException(FORBIDDEN);
    }
  }
}

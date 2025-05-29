package org.auth.features.user.services;

import java.util.UUID;
import org.auth.features.user.dto.UserCreateRequest;
import org.auth.features.user.dto.UserFilter;
import org.auth.features.user.entity.User;
import org.example.domain.user.dto.UserEditRequest;
import org.example.domain.user.dto.UserView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  Page<UserView> getAll(UserFilter filter, Pageable pageable);

  UserView getById(UUID userId);

  UserView edit(UserEditRequest userDto, UUID userId);

  User findByEmail(String email);

  UserView me();
}

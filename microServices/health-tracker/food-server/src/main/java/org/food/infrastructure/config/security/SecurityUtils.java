package org.food.infrastructure.config.security;

import org.example.domain.user.dto.UserView;
import org.example.exceptions.throwable.ForbiddenException;
import org.food.infrastructure.exception.ExceptionMessages;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

  private SecurityUtils() {}

  public static UserView getCurrentLoggedInUser() {
    if (SecurityContextHolder.getContext().getAuthentication()
        instanceof CustomAuthenticationToken authentication) {
      return authentication.getPrincipal();
    }
    throw new ForbiddenException(ExceptionMessages.INVALID_USER_TOKEN);
  }
}

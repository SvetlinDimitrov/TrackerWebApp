package org.auth.infrastructure.config.security.evaluator;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.auth.infrastructure.config.security.services.UserDetailsServiceImpl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEvaluator {
  private final UserDetailsServiceImpl userDetailsService;

  public boolean isOwner(UUID userId) {
    var user = userDetailsService.extractUserPrincipal();
    return user.getId().equals(userId);
  }
}

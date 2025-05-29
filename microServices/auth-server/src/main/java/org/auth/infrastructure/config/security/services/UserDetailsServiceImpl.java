package org.auth.infrastructure.config.security.services;

import lombok.RequiredArgsConstructor;
import org.auth.features.user.repository.UserRepository;
import org.auth.infrastructure.config.security.dto.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String email) {
    return new CustomUserDetails(
        repository
            .findByEmail(email)
            .orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with email: " + email)));
  }

  public CustomUserDetails extractUserPrincipal() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
      return (CustomUserDetails) authentication.getPrincipal();
    } else {
      throw new IllegalStateException(
          "No user is currently authenticated or the principal is not of type CustomUserDetails.");
    }
  }
}

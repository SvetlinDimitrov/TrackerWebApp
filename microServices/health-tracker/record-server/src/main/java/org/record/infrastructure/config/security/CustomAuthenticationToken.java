package org.record.infrastructure.config.security;

import java.util.Collection;
import org.example.domain.user.dto.UserView;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {

  private final UserView principal;

  public CustomAuthenticationToken(
      UserView principal, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public UserView getPrincipal() {
    return principal;
  }
}

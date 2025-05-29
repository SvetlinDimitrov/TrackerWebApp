package org.gateway.filter.user;

import java.util.List;
import org.example.domain.shared.EndpointPermission;
import org.example.domain.shared.HttpMethod;
import org.example.domain.shared.UserAccess;
import org.example.domain.user.paths.AuthenticationControllerPaths;
import org.gateway.filter.MainFilter;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationControllerFilter extends MainFilter {

  @Override
  protected List<EndpointPermission> getPermissions() {
    return List.of(
        new EndpointPermission(
            AuthenticationControllerPaths.LOGIN, UserAccess.NO_AUTH, HttpMethod.POST));
  }

  @Override
  protected String getBasePath() {
    return AuthenticationControllerPaths.BASE;
  }
}

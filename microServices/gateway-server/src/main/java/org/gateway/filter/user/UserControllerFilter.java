package org.gateway.filter.user;

import java.util.List;
import org.example.domain.shared.EndpointPermission;
import org.example.domain.shared.HttpMethod;
import org.example.domain.shared.UserAccess;
import org.example.domain.user.paths.UserControllerPaths;
import org.gateway.filter.MainFilter;
import org.springframework.stereotype.Component;

@Component
public class UserControllerFilter extends MainFilter {

  @Override
  protected List<EndpointPermission> getPermissions() {
    return List.of(
        new EndpointPermission(UserControllerPaths.CREATE, UserAccess.NO_AUTH, HttpMethod.POST),
        new EndpointPermission(UserControllerPaths.ME, UserAccess.AUTH, HttpMethod.GET),
        new EndpointPermission(UserControllerPaths.GET_BY_ID, UserAccess.AUTH, HttpMethod.GET),
        new EndpointPermission(UserControllerPaths.EDIT, UserAccess.AUTH, HttpMethod.PATCH),
        new EndpointPermission(UserControllerPaths.DELETE, UserAccess.AUTH, HttpMethod.DELETE),
        new EndpointPermission(UserControllerPaths.GET_ALL, UserAccess.AUTH, HttpMethod.GET));
  }

  @Override
  protected String getBasePath() {
    return UserControllerPaths.BASE;
  }
}

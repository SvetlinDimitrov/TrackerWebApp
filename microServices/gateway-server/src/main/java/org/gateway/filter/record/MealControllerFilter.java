package org.gateway.filter.record;

import java.util.List;
import org.example.domain.record.paths.MealControllerPaths;
import org.example.domain.shared.EndpointPermission;
import org.example.domain.shared.HttpMethod;
import org.example.domain.shared.UserAccess;
import org.gateway.filter.MainFilter;
import org.springframework.stereotype.Component;

@Component
public class MealControllerFilter extends MainFilter {

  @Override
  protected List<EndpointPermission> getPermissions() {
    return List.of(
        new EndpointPermission(MealControllerPaths.GET_ALL, UserAccess.AUTH, HttpMethod.GET),
        new EndpointPermission(MealControllerPaths.GET_BY_ID, UserAccess.AUTH, HttpMethod.GET),
        new EndpointPermission(MealControllerPaths.CREATE, UserAccess.AUTH, HttpMethod.POST),
        new EndpointPermission(MealControllerPaths.UPDATE, UserAccess.AUTH, HttpMethod.PATCH),
        new EndpointPermission(MealControllerPaths.DELETE, UserAccess.AUTH, HttpMethod.DELETE));
  }

  @Override
  protected String getBasePath() {
    return MealControllerPaths.BASE;
  }
}

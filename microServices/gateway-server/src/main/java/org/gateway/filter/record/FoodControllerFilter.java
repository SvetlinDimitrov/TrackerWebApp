package org.gateway.filter.record;

import java.util.List;
import org.example.domain.record.paths.FoodControllerPaths;
import org.example.domain.shared.EndpointPermission;
import org.example.domain.shared.HttpMethod;
import org.example.domain.shared.UserAccess;
import org.gateway.filter.MainFilter;
import org.springframework.stereotype.Component;

@Component
public class FoodControllerFilter extends MainFilter {

  @Override
  protected List<EndpointPermission> getPermissions() {
    return List.of(
        new EndpointPermission(FoodControllerPaths.GET_ALL, UserAccess.AUTH, HttpMethod.GET),
        new EndpointPermission(FoodControllerPaths.GET_BY_ID, UserAccess.AUTH, HttpMethod.GET),
        new EndpointPermission(FoodControllerPaths.CREATE, UserAccess.AUTH, HttpMethod.POST),
        new EndpointPermission(FoodControllerPaths.UPDATE, UserAccess.AUTH, HttpMethod.PUT),
        new EndpointPermission(FoodControllerPaths.DELETE, UserAccess.AUTH, HttpMethod.DELETE));
  }

  @Override
  protected String getBasePath() {
    return FoodControllerPaths.BASE;
  }
}

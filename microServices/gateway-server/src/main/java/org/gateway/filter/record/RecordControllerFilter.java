package org.gateway.filter.record;

import java.util.List;
import org.example.domain.record.paths.RecordControllerPaths;
import org.example.domain.shared.EndpointPermission;
import org.example.domain.shared.HttpMethod;
import org.example.domain.shared.UserAccess;
import org.gateway.filter.MainFilter;
import org.springframework.stereotype.Component;

@Component
public class RecordControllerFilter extends MainFilter {

  @Override
  protected List<EndpointPermission> getPermissions() {
    return List.of(
        new EndpointPermission(RecordControllerPaths.GET_ALL, UserAccess.AUTH, HttpMethod.GET),
        new EndpointPermission(RecordControllerPaths.GET_BY_ID, UserAccess.AUTH, HttpMethod.GET),
        new EndpointPermission(RecordControllerPaths.CREATE, UserAccess.AUTH, HttpMethod.POST),
        new EndpointPermission(RecordControllerPaths.UPDATE, UserAccess.AUTH, HttpMethod.PATCH),
        new EndpointPermission(RecordControllerPaths.DELETE, UserAccess.AUTH, HttpMethod.DELETE));
  }

  @Override
  protected String getBasePath() {
    return RecordControllerPaths.BASE;
  }
}

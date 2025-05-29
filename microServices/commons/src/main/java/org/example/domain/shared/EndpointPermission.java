package org.example.domain.shared;

public record EndpointPermission(String path, UserAccess access, HttpMethod method) {

  public EndpointPermission {
    if (path == null || access == null || method == null) {
      throw new RuntimeException("Parameters cannot be null");
    }
  }
}

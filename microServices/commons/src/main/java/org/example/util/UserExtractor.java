package org.example.util;

import org.example.domain.user.dto.UserView;

public class UserExtractor {

  private static final GsonWrapper GSON_WRAPPER = new GsonWrapper();

  public static UserView get(String token) {
    try {
      return GSON_WRAPPER.fromJson(token, UserView.class);
    } catch (Exception e) {
      throw new RuntimeException("Invalid token");
    }
  }
}

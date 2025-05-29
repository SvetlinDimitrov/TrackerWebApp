package org.example.utils;

import com.google.gson.Gson;

public class GsonWrapper {
  private final Gson gson = new Gson();

  public String toJson(Object src) {
    return gson.toJson(src);
  }

  public <T> T fromJson(String json, Class<T> classOfT) {
    return gson.fromJson(json, classOfT);
  }
}

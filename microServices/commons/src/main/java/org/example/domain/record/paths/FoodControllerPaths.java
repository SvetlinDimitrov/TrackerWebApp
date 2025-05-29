package org.example.domain.record.paths;

public final class FoodControllerPaths {

  public static final String BASE = "/api/v1/meal/{mealId}/food";
  public static final String GET_ALL = "";
  public static final String GET_BY_ID = "/{foodId}";
  public static final String CREATE = "";
  public static final String UPDATE = "/{foodId}";
  public static final String DELETE = "/{foodId}";

  private FoodControllerPaths() {}
}

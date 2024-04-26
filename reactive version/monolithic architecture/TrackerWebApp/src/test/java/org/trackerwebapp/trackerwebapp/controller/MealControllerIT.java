package org.trackerwebapp.trackerwebapp.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.header.Header;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.trackerwebapp.trackerwebapp.domain.dto.meal.*;
import org.trackerwebapp.trackerwebapp.domain.dto.user.UserCreate;
import org.trackerwebapp.trackerwebapp.domain.dto.user.UserDetailsDto;
import org.trackerwebapp.trackerwebapp.domain.dto.user.UserDetailsView;
import org.trackerwebapp.trackerwebapp.domain.dto.user.UserView;
import org.trackerwebapp.trackerwebapp.domain.enums.*;
import org.trackerwebapp.trackerwebapp.enums.Credentials;
import org.trackerwebapp.trackerwebapp.repository.UserRepository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@Testcontainers
class MealControllerIT {

  @Autowired
  private WebTestClient webTestClient;
  @Autowired
  private UserRepository userRepository;

  @Container
  public static GenericContainer<?> mysqlContainer = new GenericContainer<>("mysql:latest")
      .withExposedPorts(3306)
      .withEnv("MYSQL_ROOT_PASSWORD", "12345")
      .withEnv("MYSQL_DATABASE", "reactiveDB");

  @BeforeAll
  static void beforeAll() {
    mysqlContainer.start();
  }

  @AfterEach
  void afterEach() {
    cleanupDatabase().block();
  }

  private Mono<Void> cleanupDatabase() {
    return userRepository.findAll()
        .flatMap(user -> userRepository.deleteById(user.getId()))
        .then();
  }

  @DynamicPropertySource
  static void setDatasourceProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.r2dbc.url", () -> "r2dbc:mysql://" + mysqlContainer.getHost() + ":" + mysqlContainer.getFirstMappedPort() + "/reactiveDB");
    registry.add("spring.liquibase.url", () -> "jdbc:mysql://" + mysqlContainer.getHost() + ":" + mysqlContainer.getFirstMappedPort() + "/reactiveDB");
  }

  @Test
  void givenNoAuth_whenTestingGetAllMealsByUserId_thenServerShouldReturnUnauthorized() {

    webTestClient
        .get()
        .uri("/api/meals")
        .exchange()
        .expectStatus().isUnauthorized();
  }

  @Test
  void givenNoAuth_whenTestingGetMealById_thenServerShouldReturnUnauthorized() {

    String INVALID_ID = UUID.randomUUID().toString();

    webTestClient
        .get()
        .uri("/api/meals/" + INVALID_ID)
        .exchange()
        .expectStatus().isUnauthorized();
  }

  @Test
  void givenNoAuth_whenTestingCreateMeal_thenServerShouldReturnUnauthorized() {

    webTestClient
        .post()
        .uri("/api/meals")
        .bodyValue(new CreateMeal(null))
        .exchange()
        .expectStatus().isUnauthorized();
  }

  @Test
  void givenNoAuth_whenTestingChangeMeal_thenServerShouldReturnUnauthorized() {

    String INVALID_ID = UUID.randomUUID().toString();

    webTestClient
        .patch()
        .uri("/api/meals/" + INVALID_ID)
        .bodyValue(new CreateMeal(null))
        .exchange()
        .expectStatus().isUnauthorized();
  }

  @Test
  void givenNoAuth_whenTestingDeleteMealById_thenServerShouldReturnUnauthorized() {

    String INVALID_ID = UUID.randomUUID().toString();

    webTestClient
        .delete()
        .uri("/api/meals/" + INVALID_ID)
        .exchange()
        .expectStatus().isUnauthorized();
  }

  @Test
  void givenValidAuthButNotFullUserDetails_whenTestingGetAllMealsByUserId_thenServerShouldReturnForbidden() {

    Header authHeader = setUpUserAndReturnAuthHeader();

    webTestClient
        .get()
        .uri("/api/meals")
        .header(authHeader.getName(), authHeader.getValues().getFirst())
        .exchange()
        .expectStatus().isForbidden();
  }

  @Test
  void givenValidAuthButNotFullUserDetails_whenTestingGetMealById_thenServerShouldReturnForbidden() {

    Header authHeader = setUpUserAndReturnAuthHeader();
    String INVALID_ID = UUID.randomUUID().toString();

    webTestClient
        .get()
        .uri("/api/meals/" + INVALID_ID)
        .header(authHeader.getName(), authHeader.getValues().getFirst())
        .exchange()
        .expectStatus().isForbidden();
  }

  @Test
  void givenValidAuthButNotFullUserDetails_whenTestingCreateMeal_thenServerShouldReturnForbidden() {

    Header authHeader = setUpUserAndReturnAuthHeader();

    webTestClient
        .post()
        .uri("/api/meals")
        .header(authHeader.getName(), authHeader.getValues().getFirst())
        .bodyValue(new CreateMeal(null))
        .exchange()
        .expectStatus().isForbidden();
  }

  @Test
  void givenValidAuthButNotFullUserDetails_whenTestingChangeMeal_thenServerShouldReturnForbidden() {

    Header authHeader = setUpUserAndReturnAuthHeader();
    String INVALID_ID = UUID.randomUUID().toString();

    webTestClient
        .patch()
        .uri("/api/meals/" + INVALID_ID)
        .header(authHeader.getName(), authHeader.getValues().getFirst())
        .bodyValue(new CreateMeal(null))
        .exchange()
        .expectStatus().isForbidden();
  }

  @Test
  void givenValidAuthButNotFullUserDetails_whenTestingDeleteMealById_thenServerShouldReturnForbidden() {

    Header authHeader = setUpUserAndReturnAuthHeader();
    String INVALID_ID = UUID.randomUUID().toString();

    webTestClient
        .delete()
        .uri("/api/meals/" + INVALID_ID)
        .header(authHeader.getName(), authHeader.getValues().getFirst())
        .exchange()
        .expectStatus().isForbidden();
  }

  @Test
  void givenValidAuthAndFullUserDetails_whenTestingGetAllMealsByUserId_thenServerShouldReturnOkWithEmptyList() {

    Header authHeader = setUpUserWithFullUserDetailsAndReturnAuthHeader();

    webTestClient
        .get()
        .uri("/api/meals")
        .header(authHeader.getName(), authHeader.getValues().getFirst())
        .exchange()
        .expectStatus().isOk()
        .expectBody(ArrayList.class)
        .value(list -> assertEquals(list.size(), 0));
  }

  @Test
  void givenValidAuthAndFullUserDetailsAndMultipleMeals_whenTestingGetAllMealsByUserId_thenServerShouldReturnOkWithCorrectAmountOfMeals() {

    Header authHeader = setUpUserWithFullUserDetailsAndReturnAuthHeader();
    Integer VALID_NUMBER_OF_MEALS = 20;

    for (int i = 0; i < VALID_NUMBER_OF_MEALS; i++) {
      webTestClient
          .post()
          .uri("/api/meals")
          .header(authHeader.getName(), authHeader.getValues().getFirst())
          .bodyValue(new CreateMeal(null))
          .exchange()
          .expectStatus().isCreated();
    }

    webTestClient
        .get()
        .uri("/api/meals")
        .header(authHeader.getName(), authHeader.getValues().getFirst())
        .exchange()
        .expectStatus().isOk()
        .expectBody(ArrayList.class)
        .value(list -> assertEquals(list.size(), VALID_NUMBER_OF_MEALS));
  }

  @Test
  void givenValidAuthAndFullUserDetailsAndMultipleMeals_whenTestingGetAllMealsByUserIdAndDeleteMealById_thenServerShouldReturnOkWithCorrectAmountOfMeals() {

    Header authHeader = setUpUserWithFullUserDetailsAndReturnAuthHeader();
    int VALID_NUMBER_OF_MEALS = 20;
    List<String> VALID_IDS = new ArrayList<>(List.of());

    for (int i = 0; i < VALID_NUMBER_OF_MEALS; i++) {

      String VALID_ID = obtainValidMealId(authHeader);

      VALID_IDS.add(VALID_ID);
    }

    for (int i = 0; i < VALID_IDS.size() / 2; i++) {

      webTestClient
          .delete()
          .uri("/api/meals/" + VALID_IDS.get(i))
          .header(authHeader.getName(), authHeader.getValues().getFirst())
          .exchange()
          .expectStatus().isOk();
    }

    webTestClient
        .get()
        .uri("/api/meals")
        .header(authHeader.getName(), authHeader.getValues().getFirst())
        .exchange()
        .expectStatus().isOk()
        .expectBody(ArrayList.class)
        .value(list -> assertEquals(list.size(), VALID_IDS.size() / 2));
  }

  @Test
  void givenValidAuthAndFullUserDetailsAndInvalidMealId_whenTestingGetMealById_thenServerShouldReturnOkWithEmptyList() {

    Header authHeader = setUpUserWithFullUserDetailsAndReturnAuthHeader();
    String INVALID_ID = UUID.randomUUID().toString();

    webTestClient
        .get()
        .uri("/api/meals/" + INVALID_ID)
        .header(authHeader.getName(), authHeader.getValues().getFirst())
        .exchange()
        .expectStatus().isBadRequest();
  }

  @Test
  void givenValidAuthAndFullUserDetailsAndValidMealId_whenTestingGetMealById_thenServerShouldReturnOk() {

    Header authHeader = setUpUserWithFullUserDetailsAndReturnAuthHeader();

    String VALID_MEAL_ID = obtainValidMealId(authHeader);

    webTestClient
        .get()
        .uri("/api/meals/" + VALID_MEAL_ID)
        .header(authHeader.getName(), authHeader.getValues().getFirst())
        .exchange()
        .expectStatus().isOk()
        .expectBody(MealView.class)
        .value(meal -> assertEquals(meal.id(), VALID_MEAL_ID))
        .value(meal -> assertEquals(meal.consumedCalories(), BigDecimal.ZERO))
        .value(meal -> assertEquals(meal.foods().size(), 0));
  }

  @Test
  void givenValidAuthAndFullUserDetails_whenCreateMealWithNoGivenName_thenServerShouldReturnCreated() {

    Header authHeader = setUpUserWithFullUserDetailsAndReturnAuthHeader();

    webTestClient
        .post()
        .uri("/api/meals")
        .header(authHeader.getName(), authHeader.getValues().getFirst())
        .bodyValue(new CreateMeal(null))
        .exchange()
        .expectStatus().isCreated()
        .expectBody(MealView.class)
        .value(meal -> assertNotNull(meal.id()))
        .value(meal -> assertEquals(meal.consumedCalories(), BigDecimal.ZERO))
        .value(meal -> assertEquals(meal.foods().size(), 0));
  }

  @Test
  void givenValidAuthAndFullUserDetails_whenCreateMealWithInvalidNames_thenServerShouldReturnBadRequest() {

    Header authHeader = setUpUserWithFullUserDetailsAndReturnAuthHeader();

    getInvalidNames()
        .forEach(INVALID_NAME -> {
              webTestClient
                  .post()
                  .uri("/api/meals")
                  .header(authHeader.getName(), authHeader.getValues().getFirst())
                  .bodyValue(new CreateMeal(INVALID_NAME))
                  .exchange()
                  .expectStatus().isBadRequest();
            }
        );
  }

  @Test
  void givenValidAuthAndFullUserDetails_whenCreateMealWithValidName_thenServerShouldReturnCreatedWithTheProvidedName() {

    Header authHeader = setUpUserWithFullUserDetailsAndReturnAuthHeader();

    webTestClient
        .post()
        .uri("/api/meals")
        .header(authHeader.getName(), authHeader.getValues().getFirst())
        .bodyValue(new CreateMeal(Credentials.VALID_MEAL_NAME.getValue()))
        .exchange()
        .expectStatus().isCreated()
        .expectBody(MealView.class)
        .value(meal -> assertNotNull(meal.id()))
        .value(meal -> assertEquals(meal.consumedCalories(), BigDecimal.ZERO))
        .value(meal -> assertEquals(meal.name(), Credentials.VALID_MEAL_NAME.getValue()))
        .value(meal -> assertEquals(meal.foods().size(), 0));
  }

  @Test
  void givenValidAuthAndFullUserDetails_whenChangeMealWithNoGivenBody_thenServerShouldReturnOk() {

    Header authHeader = setUpUserWithFullUserDetailsAndReturnAuthHeader();
    String VALID_MEAL_ID = obtainValidMealId(authHeader);

    webTestClient
        .patch()
        .uri("/api/meals/" + VALID_MEAL_ID)
        .header(authHeader.getName(), authHeader.getValues().getFirst())
        .bodyValue(new CreateMeal(null))
        .exchange()
        .expectStatus().isOk()
        .expectBody(MealView.class);
  }

  @Test
  void givenValidAuthAndFullUserDetails_whenChangeMealWithInvalidBody_thenServerShouldReturnBadRequest() {

    Header authHeader = setUpUserWithFullUserDetailsAndReturnAuthHeader();
    String VALID_MEAL_ID = obtainValidMealId(authHeader);

    getInvalidNames()
        .forEach(INVALID_NAME -> {
          webTestClient
              .patch()
              .uri("/api/meals/" + VALID_MEAL_ID)
              .header(authHeader.getName(), authHeader.getValues().getFirst())
              .bodyValue(new CreateMeal(INVALID_NAME))
              .exchange()
              .expectStatus().isBadRequest()
              .expectBody(MealView.class);
        });
  }

  @Test
  void givenValidAuthAndFullUserDetails_whenChangeMealWithValidBody_thenServerShouldReturnOk() {

    Header authHeader = setUpUserWithFullUserDetailsAndReturnAuthHeader();
    String VALID_MEAL_ID = obtainValidMealId(authHeader);

    webTestClient
        .patch()
        .uri("/api/meals/" + VALID_MEAL_ID)
        .header(authHeader.getName(), authHeader.getValues().getFirst())
        .bodyValue(new CreateMeal(Credentials.VALID_MEAL_NAME.getValue()))
        .exchange()
        .expectStatus().isOk()
        .expectBody(MealView.class)
        .value(mealView -> assertEquals(mealView.name(), Credentials.VALID_MEAL_NAME.getValue()));
  }

  @Test
  void givenValidAuthAndFullUserDetailsAndValidMealId_whenTestingDeleteMealById_thenServerShouldReturnOk() {

    Header authHeader = setUpUserWithFullUserDetailsAndReturnAuthHeader();
    String VALID_MEAL_ID = obtainValidMealId(authHeader);

    webTestClient
        .delete()
        .uri("/api/meals/" + VALID_MEAL_ID)
        .header(authHeader.getName(), authHeader.getValues().getFirst())
        .exchange()
        .expectStatus().isOk();

    webTestClient
        .get()
        .uri("/api/meals/" + VALID_MEAL_ID)
        .header(authHeader.getName(), authHeader.getValues().getFirst())
        .exchange()
        .expectStatus().isBadRequest();
  }

  @Test
  void givenValidAuthAndFullUserDetailsAndValidMealId_whenTestingDeleteMealByIdMultipleTimesWithTheSameId_thenServerShouldReturnBadRequest() {

    Header authHeader = setUpUserWithFullUserDetailsAndReturnAuthHeader();
    String VALID_MEAL_ID = obtainValidMealId(authHeader);

    webTestClient
        .delete()
        .uri("/api/meals/" + VALID_MEAL_ID)
        .header(authHeader.getName(), authHeader.getValues().getFirst())
        .exchange()
        .expectStatus().isOk();

    for (int i = 0; i < 3; i++) {
      webTestClient
          .delete()
          .uri("/api/meals/" + VALID_MEAL_ID)
          .header(authHeader.getName(), authHeader.getValues().getFirst())
          .exchange()
          .expectStatus().isBadRequest();
    }

    webTestClient
        .get()
        .uri("/api/meals/" + VALID_MEAL_ID)
        .header(authHeader.getName(), authHeader.getValues().getFirst())
        .exchange()
        .expectStatus().isBadRequest();
  }

  @Test
  void givenValidAuthAndFullUserDetails_whenTestingGetMealByIdWhenSingleFoodIsInserted_thenServerShouldReturnOkWithSingleFood() {

    Header authHeader = setUpUserWithFullUserDetailsAndReturnAuthHeader();
    String VALID_MEAL_ID = obtainValidMealId(authHeader);

    webTestClient
        .post()
        .uri("/api/meals/" + VALID_MEAL_ID + "/insertFood")
        .header(authHeader.getName(), authHeader.getValues().getFirst())
        .bodyValue(createValidInsertedFoodWithEveryPossibleNutrientView2())
        .exchange()
        .expectStatus().isOk();

    webTestClient
        .get()
        .uri("/api/meals/" + VALID_MEAL_ID)
        .header(authHeader.getName(), authHeader.getValues().getFirst())
        .exchange()
        .expectStatus().isOk()
        .expectBody(MealView.class)
        .value(meal -> assertEquals(meal.foods().size(), 1))
        .value(meal -> assertEquals(0, meal.consumedCalories().compareTo(meal.foods().getFirst().calorie().amount())));
  }

  @Test
  void givenValidAuthAndFullUserDetails_whenTestingGetAllMealsByUserIdWhenMultipleFoodsAreInserted_thenServerShouldReturnOkWithListOfFoods() {

    Header authHeader = setUpUserWithFullUserDetailsAndReturnAuthHeader();
    String VALID_MEAL_ID = obtainValidMealId(authHeader);
    int TIME_TO_INSERT_FOOD = 30;

    for (int i = 0; i < TIME_TO_INSERT_FOOD; i++) {
      webTestClient
          .post()
          .uri("/api/meals/" + VALID_MEAL_ID + "/insertFood")
          .header(authHeader.getName(), authHeader.getValues().getFirst())
          .bodyValue(createValidInsertedFoodWithEveryPossibleNutrientView2())
          .exchange()
          .expectStatus().isOk()
          .expectBody()
          .returnResult();
    }

    webTestClient
        .get()
        .uri("/api/meals/" + VALID_MEAL_ID)
        .header(authHeader.getName(), authHeader.getValues().getFirst())
        .exchange()
        .expectStatus().isOk()
        .expectBody(MealView.class)
        .value(meal -> assertEquals(TIME_TO_INSERT_FOOD, meal.foods().size()))
        .value(meal -> assertEquals(
                0, meal.consumedCalories()
                    .compareTo(meal.foods()
                        .stream()
                        .map(FoodView::calorie)
                        .map(CalorieView::amount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                    )
            )
        );
  }

  @Test
  void givenValidAuthAndFullUserDetails_whenTestingChangeFoodWhenSingleFoodIsInserted_thenServerShouldReturnOkWithNoChangedFoodValues() {

    Header authHeader = setUpUserWithFullUserDetailsAndReturnAuthHeader();
    String VALID_MEAL_ID = obtainValidMealId(authHeader);

    webTestClient
        .post()
        .uri("/api/meals/" + VALID_MEAL_ID + "/insertFood")
        .header(authHeader.getName(), authHeader.getValues().getFirst())
        .bodyValue(createValidInsertedFoodWithEveryPossibleNutrientView2())
        .exchange()
        .expectStatus().isOk();

    webTestClient
        .patch()
        .uri("/api/meals/" + VALID_MEAL_ID)
        .header(authHeader.getName(), authHeader.getValues().getFirst())
        .bodyValue(new CreateMeal(Credentials.VALID_MEAL_NAME.getValue()))
        .exchange()
        .expectStatus().isOk()
        .expectBody(MealView.class)
        .value(mealView -> assertEquals(mealView.name(), Credentials.VALID_MEAL_NAME.getValue()));

    webTestClient
        .get()
        .uri("/api/meals/" + VALID_MEAL_ID)
        .header(authHeader.getName(), authHeader.getValues().getFirst())
        .exchange()
        .expectStatus().isOk()
        .expectBody(MealView.class)
        .value(meal -> assertEquals(meal.foods().size(), 1))
        .value(meal -> assertEquals(0, meal.consumedCalories().compareTo(meal.foods().getFirst().calorie().amount())));
  }

  @Test
  void givenValidAuthAndFullUserDetails_whenTestingChangeFoodWhenWhenMultipleFoodsAreInserted_thenServerShouldReturnOkWithNoChangedFoodValues() {

    Header authHeader = setUpUserWithFullUserDetailsAndReturnAuthHeader();
    String VALID_MEAL_ID = obtainValidMealId(authHeader);
    int TIME_TO_INSERT_FOOD = 30;

    for (int i = 0; i < TIME_TO_INSERT_FOOD; i++) {
      webTestClient
          .post()
          .uri("/api/meals/" + VALID_MEAL_ID + "/insertFood")
          .header(authHeader.getName(), authHeader.getValues().getFirst())
          .bodyValue(createValidInsertedFoodWithEveryPossibleNutrientView2())
          .exchange()
          .expectStatus().isOk()
          .expectBody()
          .returnResult();
    }

    webTestClient
        .patch()
        .uri("/api/meals/" + VALID_MEAL_ID)
        .header(authHeader.getName(), authHeader.getValues().getFirst())
        .bodyValue(new CreateMeal(Credentials.VALID_MEAL_NAME.getValue()))
        .exchange()
        .expectStatus().isOk()
        .expectBody(MealView.class)
        .value(mealView -> assertEquals(mealView.name(), Credentials.VALID_MEAL_NAME.getValue()));

    webTestClient
        .get()
        .uri("/api/meals/" + VALID_MEAL_ID)
        .header(authHeader.getName(), authHeader.getValues().getFirst())
        .exchange()
        .expectStatus().isOk()
        .expectBody(MealView.class)
        .value(meal -> assertEquals(TIME_TO_INSERT_FOOD, meal.foods().size()))
        .value(meal -> assertEquals(
                0, meal.consumedCalories()
                    .compareTo(meal.foods()
                        .stream()
                        .map(FoodView::calorie)
                        .map(CalorieView::amount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                    )
            )
        );
  }


  private UserCreate createUser(String username, String email, String password) {
    return new UserCreate(username, email, password);
  }

  private UserDetailsDto createDetails(BigDecimal kilograms, BigDecimal height, Integer age, WorkoutState workoutState, Gender gender) {
    return new UserDetailsDto(kilograms, height, age, workoutState, gender);
  }

  private CalorieView createValidCalorieView() {
    return new CalorieView(BigDecimal.valueOf(366), AllowedCalorieUnits.CALORIE.getSymbol());
  }

  private InsertFoodDto createInsertedFood(String name, CalorieView calorieView, String measurement, BigDecimal size, List<NutritionView> nutritionViewList) {
    return new InsertFoodDto(name, calorieView, measurement, size, nutritionViewList);
  }

  private InsertFoodDto createValidInsertedFoodWithEveryPossibleNutrientView2() {

    List<NutritionView> nutritionViews = Arrays.stream(AllowedNutrients.values())
        .map(data -> new NutritionView(data.getNutrientName(), data.getNutrientUnit(), BigDecimal.valueOf(150)))
        .toList();

    return createInsertedFood(
        Credentials.VALID_MEAL_NAME.getValue(),
        createValidCalorieView(),
        AllowedFoodUnits.GRAM.getSymbol(),
        BigDecimal.valueOf(450),
        nutritionViews
    );
  }

  private Header setUpUserAndReturnAuthHeader() {

    UserCreate newUser = createUser(
        Credentials.VALID_USERNAME.getValue(),
        Credentials.VALID_EMAIL.getValue(),
        Credentials.VALID_PASSWORD.getValue()
    );

    webTestClient.post()
        .uri("/api/user")
        .bodyValue(newUser)
        .exchange()
        .expectStatus().isCreated()
        .expectBody(UserView.class)
        .returnResult();

    return new Header("Authorization", "Basic " + Base64.getEncoder().encodeToString((newUser.email() + ":" + newUser.password()).getBytes()));
  }

  private Header setUpUserWithFullUserDetailsAndReturnAuthHeader() {

    UserCreate newUser = createUser(
        Credentials.VALID_USERNAME.getValue(),
        Credentials.VALID_EMAIL.getValue(),
        Credentials.VALID_PASSWORD.getValue()
    );

    UserDetailsDto validDetails = createDetails(
        new BigDecimal(Credentials.VALID_DETAIL_KILOGRAMS.getValue()),
        new BigDecimal(Credentials.VALID_DETAIL_HEIGHT.getValue()),
        Integer.valueOf(Credentials.VALID_DETAIL_AGE.getValue()),
        WorkoutState.valueOf(Credentials.VALID_DETAIL_WORKOUT.getValue()),
        Gender.valueOf(Credentials.VALID_DETAIL_GENDER.getValue())
    );

    webTestClient.post()
        .uri("/api/user")
        .bodyValue(newUser)
        .exchange()
        .expectStatus().isCreated()
        .expectBody(UserView.class)
        .returnResult();

    webTestClient.patch()
        .uri("/api/user/details")
        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((newUser.email() + ":" + newUser.password()).getBytes()))
        .bodyValue(validDetails)
        .exchange()
        .expectStatus().isOk()
        .expectBody(UserDetailsView.class)
        .returnResult();

    return new Header("Authorization", "Basic " + Base64.getEncoder().encodeToString((newUser.email() + ":" + newUser.password()).getBytes()));
  }

  private String obtainValidMealId(Header authHeader) {
    return Objects.requireNonNull(
        webTestClient
            .post()
            .uri("/api/meals")
            .header(authHeader.getName(), authHeader.getValues().getFirst())
            .bodyValue(new CreateMeal(null))
            .exchange()
            .expectStatus().isCreated()
            .expectBody(MealView.class)
            .returnResult()
            .getResponseBody()
    ).id();
  }

  private List<String> getInvalidNames() {
    return List.of(
        "",
        "h",
        "                     ",
        "         .  "
    );
  }


}
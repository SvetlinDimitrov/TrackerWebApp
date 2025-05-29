// package org.food;
//
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
// import java.math.BigDecimal;
//
// import org.food.clients.dto.User;
// import org.food.domain.dtos.CreateCustomFood;
// import org.food.features.custom.repository.CustomFoodRepository;
// import org.food.utils.GsonWrapper;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.web.servlet.MockMvc;
//
// import com.fasterxml.jackson.databind.ObjectMapper;
//
// @SpringBootTest
// @ActiveProfiles("test")
// @AutoConfigureMockMvc
// class CustomFoodControllerImpTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private GsonWrapper gsonWrapper;
//
//    @Autowired
//    private CustomFoodRepository customFoodRepository;
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//
//    @AfterEach
//    void setUp() {
//        customFoodRepository.deleteAll();
//    }
//
//    @Test
//    void addFood_ValidCreateFoodDto_StatusCreated() throws Exception {
//        CreateCustomFood createCustomFood = generateCreateCustomFoodDto("name", new
// BigDecimal("1"), new BigDecimal("1"));
//
//        String userToken = generateUserToken(1L);
//
//        mockMvc.perform(post("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(createCustomFood))
//                .header("X-ViewUser", userToken))
//            .andExpect(status().isCreated());
//    }
//
//    @Test
//    void addFood_InvalidCreateFoodDto_StatusBadRequest() throws Exception {
//        CreateCustomFood createCustomFood = generateCreateCustomFoodDto("name", null, new
// BigDecimal("1"));
//        String userToken = generateUserToken(2L);
//
//        mockMvc.perform(post("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(createCustomFood))
//                .header("X-ViewUser", userToken))
//            .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void addFood_FoodAlreadyExists_StatusBadRequest() throws Exception {
//        CreateCustomFood createCustomFood = generateCreateCustomFoodDto("name", new
// BigDecimal("1"), new BigDecimal("1"));
//        String userToken = generateUserToken(3L);
//
//        mockMvc.perform(post("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(createCustomFood))
//                .header("X-ViewUser", userToken))
//            .andExpect(status().isCreated());
//
//        mockMvc.perform(post("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(createCustomFood))
//                .header("X-ViewUser", userToken))
//            .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void getAllCustomFoods_ValidUserToken_StatusOk() throws Exception {
//        String userToken = generateUserToken(4L);
//        mockMvc.perform(post("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(generateCreateCustomFoodDto("name", new
// BigDecimal("1"), new BigDecimal("1"))))
//                .header("X-ViewUser", userToken))
//            .andExpect(status().isCreated());
//
//        mockMvc.perform(post("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(generateCreateCustomFoodDto("name2", new
// BigDecimal("1"), new BigDecimal("1"))))
//                .header("X-ViewUser", userToken))
//            .andExpect(status().isCreated());
//
//        mockMvc.perform(post("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(generateCreateCustomFoodDto("name3", new
// BigDecimal("1"), new BigDecimal("1"))))
//                .header("X-ViewUser", userToken))
//            .andExpect(status().isCreated());
//
//        assertEquals(3, customFoodRepository.findAll()
//            .stream().filter(f -> f.getUserId().equals(4L)).toList().size());
//    }
//
//    @Test
//    void getAllCustomFoods_EmptyList_StatusOk() throws Exception {
//        String userToken = generateUserToken(5L);
//        mockMvc.perform(get("/api/food/all")
//                .contentType("application/json")
//                .header("X-ViewUser", userToken))
//            .andExpect(status().isOk());
//
//        assertEquals(0, customFoodRepository.findAll()
//            .stream().filter(f -> f.getUserId().equals(5L)).toList().size());
//    }
//
//    @Test
//    void getCustomFood_ValidUserTokenAndFoodName_StatusOk() throws Exception {
//        String userToken = generateUserToken(6L);
//        mockMvc.perform(post("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(generateCreateCustomFoodDto("name", new
// BigDecimal("1"), new BigDecimal("1"))))
//                .header("X-ViewUser", userToken))
//            .andExpect(status().isCreated());
//
//        mockMvc.perform(get("/api/food")
//                .contentType("application/json")
//                .param("foodName", "name")
//                .param("amount", "1")
//                .header("X-ViewUser", userToken))
//            .andExpect(status().isOk());
//
//        assertEquals(1, customFoodRepository.findAll()
//            .stream().filter(f -> f.getUserId().equals(6L)).toList().size());
//    }
//
//    @Test
//    void getCustomFood_ValidUserTokenAndFoodNameAndLowerThen100gramAmount_StatusOk() throws
// Exception {
//        String userToken = generateUserToken(7L);
//        mockMvc.perform(post("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(generateCreateCustomFoodDto("name", new
// BigDecimal("1"), new BigDecimal("1"))))
//                .header("X-ViewUser", userToken))
//            .andExpect(status().isCreated());
//
//        mockMvc.perform(get("/api/food")
//                .contentType("application/json")
//                .param("foodName", "name")
//                .param("amount", "10")
//                .header("X-ViewUser", userToken))
//            .andExpect(jsonPath("$.calories").value("10.0"))
//            .andExpect(status().isOk());
//    }
//
//    @Test
//    void getCustomFood_ValidUserTokenAndFoodNameWith100gramAmount_StatusOk() throws Exception {
//        String userToken = generateUserToken(8L);
//        mockMvc.perform(post("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(generateCreateCustomFoodDto("name", new
// BigDecimal("1"), new BigDecimal("1"))))
//                .header("X-ViewUser", userToken))
//            .andExpect(status().isCreated());
//
//        mockMvc.perform(get("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(generateCreateCustomFoodDto("name", new
// BigDecimal("1"), new BigDecimal("1"))))
//                .param("foodName", "name")
//                .param("amount", "100")
//                .header("X-ViewUser", userToken))
//            .andExpect(jsonPath("$.calories").value("100.0"))
//            .andExpect(status().isOk());
//    }
//
//    @Test
//    void getCustomFood_ValidUserTokenAndFoodNameWithMoreThen100gramAmount_StatusOk() throws
// Exception {
//        String userToken = generateUserToken(9L);
//        mockMvc.perform(post("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(generateCreateCustomFoodDto("name", new
// BigDecimal("1"), new BigDecimal("1"))))
//                .header("X-ViewUser", userToken))
//            .andExpect(status().isCreated());
//
//        mockMvc.perform(get("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(generateCreateCustomFoodDto("name", new
// BigDecimal("1"), new BigDecimal("1"))))
//                .param("foodName", "name")
//                .param("amount", "101")
//                .header("X-ViewUser", userToken))
//            .andExpect(jsonPath("$.calories").value("101.0"))
//            .andExpect(status().isOk());
//    }
//
//    @Test
//    void getCustomFood_ValidUserTokenAndFoodNameWithZeroAmount_StatusOk() throws Exception {
//        String userToken = generateUserToken(10L);
//        mockMvc.perform(post("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(generateCreateCustomFoodDto("name", new
// BigDecimal("1"), new BigDecimal("1"))))
//                .header("X-ViewUser", userToken))
//            .andExpect(status().isCreated());
//
//        mockMvc.perform(get("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(generateCreateCustomFoodDto("name", new
// BigDecimal("1"), new BigDecimal("1"))))
//                .param("foodName", "name")
//                .param("amount", "0")
//                .header("X-ViewUser", userToken))
//            .andExpect(jsonPath("$.calories").value("0"))
//            .andExpect(status().isOk());
//    }
//
//    @Test
//    void getCustomFood_ValidUserTokenAndFoodNameWithNegativeAmount_StatusBadRequest() throws
// Exception {
//        String userToken = generateUserToken(11L);
//        mockMvc.perform(post("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(generateCreateCustomFoodDto("name", new
// BigDecimal("1"), new BigDecimal("1"))))
//                .header("X-ViewUser", userToken))
//            .andExpect(status().isCreated());
//
//        mockMvc.perform(get("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(generateCreateCustomFoodDto("name", new
// BigDecimal("1"), new BigDecimal("1"))))
//                .param("foodName", "name")
//                .param("amount", "-1")
//                .header("X-ViewUser", userToken))
//            .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void getCustomFood_ValidUserTokenAndInvalidFoodNameWith_StatusBadRequest() throws Exception {
//        String userToken = generateUserToken(12L);
//
//        mockMvc.perform(get("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(generateCreateCustomFoodDto("invalid",
// new BigDecimal("1"), new BigDecimal("1"))))
//                .param("foodName", "name")
//                .header("X-ViewUser", userToken))
//            .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void getCustomFood_ValidUserTokenAndFoodNameWithNullAmount_ReturnsAmount100AndStatusOk()
// throws Exception {
//        String userToken = generateUserToken(13L);
//        mockMvc.perform(post("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(generateCreateCustomFoodDto("name", new
// BigDecimal("1"), new BigDecimal("1"))))
//                .header("X-ViewUser", userToken))
//            .andExpect(status().isCreated());
//
//        mockMvc.perform(get("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(generateCreateCustomFoodDto("name", new
// BigDecimal("1"), new BigDecimal("1"))))
//                .param("foodName", "name")
//                .header("X-ViewUser", userToken))
//            .andExpect(jsonPath("$.calories").value("100.0"))
//            .andExpect(status().isOk());
//    }
//
//    @Test
//    void getCustomFood_FirstUserTryToTakeNotHisOwnFood_StatusBadRequest() throws Exception {
//        String userToken = generateUserToken(14L);
//        String userToken2 = generateUserToken(15L);
//        CreateCustomFood createCustomFood = generateCreateCustomFoodDto("name", new
// BigDecimal("1"), new BigDecimal("1"));
//
//
//        mockMvc.perform(post("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(createCustomFood))
//                .header("X-ViewUser", userToken))
//            .andExpect(status().isCreated());
//
//        mockMvc.perform(get("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(createCustomFood))
//                .param("foodName", "name")
//                .header("X-ViewUser", userToken2))
//            .andExpect(status().isBadRequest());
//
//    }
//
//    @Test
//    void deleteCustomFood_ValidUserTokenAndFoodName_StatusOk() throws Exception {
//        String userToken = generateUserToken(16L);
//        CreateCustomFood createCustomFood = generateCreateCustomFoodDto("name", new
// BigDecimal("1"), new BigDecimal("1"));
//
//        mockMvc.perform(post("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(createCustomFood))
//                .header("X-ViewUser", userToken))
//            .andExpect(status().isCreated());
//
//        mockMvc.perform(get("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(createCustomFood))
//                .param("foodName", "name")
//                .header("X-ViewUser", userToken))
//            .andExpect(status().isOk());
//
//        mockMvc.perform(delete("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(createCustomFood))
//                .param("foodName", "name")
//                .header("X-ViewUser", userToken))
//            .andExpect(status().isOk());
//
//        mockMvc.perform(get("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(createCustomFood))
//                .param("foodName", "name")
//                .header("X-ViewUser", userToken))
//            .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void deleteCustomFood_ValidUserTokenAndInvalidFoodName_StatusBadRequest() throws Exception {
//        String userToken = generateUserToken(17L);
//        CreateCustomFood createCustomFood = generateCreateCustomFoodDto("name", new
// BigDecimal("1"), new BigDecimal("1"));
//
//        mockMvc.perform(delete("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(createCustomFood))
//                .param("foodName", "name")
//                .header("X-ViewUser", userToken))
//            .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void deleteCustomFood_FirstUserTryToDeleteNotHisOwnFood_StatusBadRequest() throws Exception {
//        String userToken = generateUserToken(18L);
//        String userToken2 = generateUserToken(19L);
//        CreateCustomFood createCustomFood = generateCreateCustomFoodDto("name", new
// BigDecimal("1"), new BigDecimal("1"));
//
//        mockMvc.perform(post("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(createCustomFood))
//                .header("X-ViewUser", userToken))
//            .andExpect(status().isCreated());
//
//        mockMvc.perform(delete("/api/food")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(createCustomFood))
//                .param("foodName", "name")
//                .header("X-ViewUser", userToken2))
//            .andExpect(status().isBadRequest());
//    }
//
//    private CreateCustomFood generateCreateCustomFoodDto(String name, BigDecimal amount,
// BigDecimal calories) {
//        CreateCustomFood customFood = new CreateCustomFood();
//        customFood.setName(name);
//        customFood.setSize(amount);
//        customFood.setCalories(calories);
//        customFood.setA(new BigDecimal("1"));
//        customFood.setD(new BigDecimal("1"));
//        customFood.setE(new BigDecimal("1"));
//        customFood.setK(new BigDecimal("1"));
//        customFood.setC(new BigDecimal("1"));
//        customFood.setB1(new BigDecimal("1"));
//        customFood.setB2(new BigDecimal("1"));
//        customFood.setB3(new BigDecimal("1"));
//        customFood.setB5(new BigDecimal("1"));
//        customFood.setB6(new BigDecimal("1"));
//        customFood.setB7(new BigDecimal("1"));
//        customFood.setB9(new BigDecimal("1"));
//        customFood.setB12(new BigDecimal("1"));
//        customFood.setCalcium(new BigDecimal("1"));
//        customFood.setPhosphorus(new BigDecimal("1"));
//        customFood.setMagnesium(new BigDecimal("1"));
//        customFood.setSodium(new BigDecimal("1"));
//        customFood.setPotassium(new BigDecimal("1"));
//        customFood.setChloride(new BigDecimal("1"));
//        customFood.setIron(new BigDecimal("1"));
//        customFood.setZinc(new BigDecimal("1"));
//        customFood.setCopper(new BigDecimal("1"));
//        customFood.setManganese(new BigDecimal("1"));
//        customFood.setIodine(new BigDecimal("1"));
//        customFood.setSelenium(new BigDecimal("1"));
//        customFood.setFluoride(new BigDecimal("1"));
//        customFood.setChromium(new BigDecimal("1"));
//        customFood.setMolybdenum(new BigDecimal("1"));
//        customFood.setCarbohydrates(new BigDecimal("1"));
//        customFood.setProtein(new BigDecimal("1"));
//        customFood.setFat(new BigDecimal("1"));
//        customFood.setFiber(new BigDecimal("1"));
//        customFood.setTransFat(new BigDecimal("1"));
//        customFood.setSaturatedFat(new BigDecimal("1"));
//        customFood.setSugar(new BigDecimal("1"));
//        customFood.setPolyunsaturatedFat(new BigDecimal("1"));
//        customFood.setMonounsaturatedFat(new BigDecimal("1"));
//
//        return customFood;
//    }
//
//    private String generateUserToken(Long userId) {
//        User user = new User();
//        user.setId(userId);
//        return gsonWrapper.toJson(user);
//    }
// }

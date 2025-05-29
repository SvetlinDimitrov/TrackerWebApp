// package org.record.services;
//
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.mockito.Mockito.when;
//
// import java.math.BigDecimal;
// import java.util.Collections;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.Optional;
//
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.record.features.repository.RecordRepository;
// import org.record.client.StorageClient;
// import org.example.domain.food.Food;
// import org.example.domain.food.StorageView;
// import org.example.domain.food.User;
// import org.record.exceptions.InvalidJsonTokenException;
// import org.record.exceptions.RecordNotFoundException;
// import org.record.model.dtos.RecordView;
// import org.record.model.entity.NutritionIntake;
// import org.record.model.entity.Record;
// import org.record.model.enums.Gender;
// import org.record.model.enums.WorkoutState;
// import org.record.utils.GsonWrapper;
// import org.record.utils.NutrientIntakeCreator;
// import org.springframework.http.ResponseEntity;
//
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.ObjectMapper;
//
// @ExtendWith(MockitoExtension.class)
// public class RecordServiceImpTest {
//
//    @Mock
//    private StorageClient storageClient;
//
//    @Mock
//    private RecordRepository recordRepository;
//
//    @Mock
//    private NutrientIntakeCreator nutrientIntakeCreator;
//
//    @Mock
//    private GsonWrapper gsonWrapper;
//
//    @InjectMocks
//    private RecordServiceImp recordServiceImp;
//
//    private User userView;
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @BeforeEach
//    public void setUp() {
//
//        userView = new User();
//        userView.setAge(20);
//        userView.setEmail("test@abv.bg");
//        userView.setGender(Gender.FEMALE);
//        userView.setHeight(new BigDecimal("170"));
//        userView.setKilograms(new BigDecimal("60"));
//        userView.setUsername("test");
//        userView.setWorkoutState(WorkoutState.MODERATELY_ACTIVE);
//
//    }
//
//    @Test
//    public void testGetAllViewsByUserId_ValidUserCredential_ReturnRecordViews()
//            throws JsonProcessingException, InvalidJsonTokenException {
//        userView.setId(1L);
//        Record record = getRecord(userView.getId(), 1L);
//        String token = objectMapper.writeValueAsString(userView);
//
//        StorageView storage = new StorageView();
//        storage.setFoods(List.of(getApple()));
//        storage.setConsumedCalories(getApple().getCalories());
//
//        when(nutrientIntakeCreator.create(userView.getGender(),
//                record.getDailyCalories(),
//                userView.getWorkoutState()))
//                .thenReturn(getNutritionMap());
//        when(gsonWrapper.fromJson(token, User.class)).thenReturn(userView);
//
// when(recordRepository.findAllByUserId(userView.getId())).thenReturn(Collections.singletonList(record));
//
//        when(storageClient.getAllStorages(record.getId(), token))
//                .thenReturn(ResponseEntity.ok(List.of(storage)));
//
//        List<RecordView> result = recordServiceImp.getAllViewsByUserId(token);
//
//        assertEquals(record.getName(), result.get(0).getName());
//        assertEquals(record.getId(), result.get(0).getId());
//        assertEquals(getApple().getCalories(), result.get(0).getDailyConsumedCalories());
//    }
//
//    @Test
//    public void testGetAllViewsByUserId_EmptyRecordList_ReturnEmptyArray()
//            throws JsonProcessingException, InvalidJsonTokenException {
//        userView.setId(1L);
//        String token = objectMapper.writeValueAsString(userView);
//
//
// when(recordRepository.findAllByUserId(userView.getId())).thenReturn(Collections.emptyList());
//        when(gsonWrapper.fromJson(token, User.class)).thenReturn(userView);
//
//        List<RecordView> result = recordServiceImp.getAllViewsByUserId(token);
//
//        assertTrue(result.isEmpty());
//    }
//
//    @Test
//    public void testGetViewByRecordIdAndUserId_ValidUserCredential_ReturnRecordView()
//            throws JsonProcessingException, RecordNotFoundException, InvalidJsonTokenException {
//        userView.setId(2L);
//        Record record = getRecord(userView.getId(), 2L);
//        String token = objectMapper.writeValueAsString(userView);
//        when(gsonWrapper.fromJson(token, User.class)).thenReturn(userView);
//        when(recordRepository.findByIdAndUserId(record.getId(),
// userView.getId())).thenReturn(Optional.of(record));
//
//        when(storageClient.getAllStorages(record.getId(), token))
//                .thenReturn(ResponseEntity.ok(Collections.emptyList()));
//
//        RecordView result = recordServiceImp.getViewByRecordIdAndUserId(record.getId(), token);
//        assertEquals(record.getName(), result.getName());
//        assertEquals(record.getId(), result.getId());
//    }
//
//    @Test
//    public void testGetViewByRecordIdAndUserId_InvalidRecordId_ThrowsRecordNotFoundException()
//            throws JsonProcessingException {
//        userView.setId(3L);
//        Record record = getRecord(userView.getId(), 3L);
//        String token = objectMapper.writeValueAsString(userView);
//        when(gsonWrapper.fromJson(token, User.class)).thenReturn(userView);
//        when(recordRepository.findByIdAndUserId(record.getId(),
// userView.getId())).thenReturn(Optional.empty());
//
//        assertThrows(RecordNotFoundException.class,
//                () -> recordServiceImp.getViewByRecordIdAndUserId(record.getId(), token));
//    }
//
//    public Record getRecord(Long userId, Long recordId) {
//        Record record = new Record();
//        record.setUserId(userId);
//        record.setDailyCalories(BigDecimal.ZERO);
//        record.setId(recordId);
//        record.setName("test");
//        record.setDate(java.time.LocalDate.now());
//        return record;
//    }
//
//    private Food getApple() {
//        Food food = new Food();
//        food.setSize(new BigDecimal("100"));
//        food.setA(new BigDecimal("0.0"));
//        food.setB1(new BigDecimal("0.017"));
//        food.setB12(new BigDecimal("0.0"));
//        food.setB2(new BigDecimal("0.026"));
//        food.setB3(new BigDecimal("0.091"));
//        food.setC(new BigDecimal("4.6"));
//        food.setB5(new BigDecimal("0.043"));
//        food.setB6(new BigDecimal("0.041"));
//        food.setB7(new BigDecimal("0.009"));
//        food.setB9(new BigDecimal("0.003"));
//        food.setCalcium(new BigDecimal("6"));
//        food.setCalories(new BigDecimal("52"));
//        food.setCarbohydrates(new BigDecimal("14"));
//        food.setChloride(new BigDecimal("0.0"));
//        food.setChromium(new BigDecimal("0.0"));
//        food.setMolybdenum(new BigDecimal("0.0"));
//        food.setCopper(new BigDecimal("0.05"));
//        food.setD(new BigDecimal("0.0"));
//        food.setE(new BigDecimal("0.18"));
//        food.setFat(new BigDecimal("0.2"));
//        food.setFiber(new BigDecimal("2.4"));
//        food.setFluoride(new BigDecimal("0.0"));
//        food.setIodine(new BigDecimal("0.0"));
//        food.setIron(new BigDecimal("0.12"));
//        food.setK(new BigDecimal("0.5"));
//        food.setMagnesium(new BigDecimal("5"));
//        food.setManganese(new BigDecimal("0.035"));
//        food.setMonounsaturatedFat(new BigDecimal("0.01"));
//        food.setName("Apple");
//        food.setPhosphorus(new BigDecimal("11"));
//        food.setPolyunsaturatedFat(new BigDecimal("0.03"));
//        food.setPotassium(new BigDecimal("107"));
//        food.setProtein(new BigDecimal("0.3"));
//        food.setSaturatedFat(new BigDecimal("0.02"));
//        food.setSelenium(new BigDecimal("0.0"));
//        food.setSodium(new BigDecimal("1"));
//        food.setSugar(new BigDecimal("10"));
//        food.setTransFat(new BigDecimal("0.0"));
//        food.setZinc(new BigDecimal("0.05"));
//        return food;
//    }
//
//    private Map<String, NutritionIntake> getNutritionMap() {
//        Map<String, NutritionIntake> nutritionMap = new HashMap<>();
//        fillMap(nutritionMap, "A");
//        fillMap(nutritionMap, "D");
//        fillMap(nutritionMap, "E");
//        fillMap(nutritionMap, "K");
//        fillMap(nutritionMap, "C");
//        fillMap(nutritionMap, "B1");
//        fillMap(nutritionMap, "B2");
//        fillMap(nutritionMap, "B3");
//        fillMap(nutritionMap, "B5");
//        fillMap(nutritionMap, "B6");
//        fillMap(nutritionMap, "B7");
//        fillMap(nutritionMap, "B9");
//        fillMap(nutritionMap, "B12");
//
//        fillMap(nutritionMap, "Calcium");
//        fillMap(nutritionMap, "Phosphorus");
//        fillMap(nutritionMap, "Magnesium");
//        fillMap(nutritionMap, "Molybdenum");
//        fillMap(nutritionMap, "Sodium");
//        fillMap(nutritionMap, "Potassium");
//        fillMap(nutritionMap, "Chloride");
//        fillMap(nutritionMap, "Iron");
//        fillMap(nutritionMap, "Zinc");
//        fillMap(nutritionMap, "Copper");
//        fillMap(nutritionMap, "Manganese");
//        fillMap(nutritionMap, "Selenium");
//        fillMap(nutritionMap, "Chromium");
//        fillMap(nutritionMap, "Iodine");
//        fillMap(nutritionMap, "Fluoride");
//
//        fillMap(nutritionMap, "Protein");
//        fillMap(nutritionMap, "Carbohydrates");
//        fillMap(nutritionMap, "Fat");
//        fillMap(nutritionMap, "Fiber");
//        fillMap(nutritionMap, "Sugar");
//        fillMap(nutritionMap, "TransFat");
//        fillMap(nutritionMap, "SaturatedFat");
//        fillMap(nutritionMap, "MonounsaturatedFat");
//        fillMap(nutritionMap, "PolyunsaturatedFat");
//        return nutritionMap;
//    }
//
//    private void fillMap(Map<String, NutritionIntake> map, String nutritionName) {
//        NutritionIntake nutritionIntake = new NutritionIntake();
//        nutritionIntake.setNutrientName(nutritionName);
//        nutritionIntake.setDailyConsumed(BigDecimal.ZERO);
//        map.put(nutritionIntake.getNutrientName(), nutritionIntake);
//    }
// }

// package org.example;
//
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.google.gson.Gson;
// import com.google.gson.GsonBuilder;
// import jakarta.transaction.Transactional;
// import org.example.config.LocalDateAdapter;
// import org.example.config.YearMonthAdapter;
// import org.example.domain.dto.AchievementHolderCreateDto;
// import org.example.domain.dto.AchievementTrackerEditDto;
// import org.example.domain.dto.User;
// import org.example.domain.entity.Achievement;
// import org.example.domain.entity.AchievementTracker;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.web.servlet.MockMvc;
//
// import java.math.BigDecimal;
// import java.time.LocalDate;
// import java.time.YearMonth;
// import java.util.SortedMap;
//
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
// @ActiveProfiles("test")
// @AutoConfigureMockMvc
// @SpringBootTest
// class AchievementControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private AchievementRepository achievementRepository;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    Gson gson = new GsonBuilder()
//        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
//        .registerTypeAdapter(YearMonth.class, new YearMonthAdapter())
//        .create();
//
//    @BeforeEach
//    void setUp() {
//    }
//
//    @Test
//    void createAchievementHolder_validUserDataAndDtoInvalidMeasurementData_StatusCreated() throws
// Exception {
//        String userToken = generatedUserToken(1L);
//        String achievementHolderCreateDto =
// objectMapper.writeValueAsString(achievementHolderCreateDto());
//
//        mockMvc.perform(post("/api/achievement")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievementHolderCreateDto))
//            .andExpect(status().isCreated());
//    }
//
//    @Test
//    void createAchievementHolder_validUserDataAndInvalidNameDto_StatusBadRequest() throws
// Exception {
//        String userToken = generatedUserToken(2L);
//        String achievementHolderCreateDto =
// objectMapper.writeValueAsString(achievementHolderCreateDto());
//        achievementHolderCreateDto = achievementHolderCreateDto.replace("test", "");
//
//        mockMvc.perform(post("/api/achievement")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievementHolderCreateDto))
//            .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void createAchievementHolder_validUserDataAndInvalidDescriptionDto_StatusBadRequest() throws
// Exception {
//        String userToken = generatedUserToken(3L);
//        String achievementHolderCreateDto =
// objectMapper.writeValueAsString(achievementHolderCreateDto());
//        achievementHolderCreateDto = achievementHolderCreateDto.replace("test", "");
//
//        mockMvc.perform(post("/api/achievement")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievementHolderCreateDto))
//            .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void createAchievementHolder_validUserDataAndInvalidGoalDto_StatusBadRequest() throws
// Exception {
//        String userToken = generatedUserToken(4L);
//        String achievementHolderCreateDto =
// objectMapper.writeValueAsString(achievementHolderCreateDto());
//        achievementHolderCreateDto = achievementHolderCreateDto.replace("1", "");
//
//        mockMvc.perform(post("/api/achievement")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievementHolderCreateDto))
//            .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void createAchievementHolder_validUserDataAndTwoDtoWithTheSameName_StatusBadRequest() throws
// Exception {
//        String userToken = generatedUserToken(5L);
//        String achievementHolderCreateDto =
// objectMapper.writeValueAsString(achievementHolderCreateDto());
//
//        mockMvc.perform(post("/api/achievement")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievementHolderCreateDto))
//            .andExpect(status().isCreated());
//        mockMvc.perform(post("/api/achievement")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievementHolderCreateDto))
//            .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void getAllAchievementHoldersById_validUserDataEmptyList_StatusOk() throws Exception {
//        String userToken = generatedUserToken(6L);
//
//        mockMvc.perform(get("/api/achievement/all")
//                .contentType("application/json")
//                .header("X-ViewUser", userToken))
//            .andExpect(status().isOk());
//    }
//
//    @Test
//    void getAllAchievementHoldersById_validUserDataWithOneAchievement_StatusOk() throws Exception
// {
//        String userToken = generatedUserToken(7L);
//        String achievementHolderCreateDto =
// objectMapper.writeValueAsString(achievementHolderCreateDto());
//
//        mockMvc.perform(post("/api/achievement")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievementHolderCreateDto))
//            .andExpect(status().isCreated());
//
//        mockMvc.perform(get("/api/achievement/all")
//                .contentType("application/json")
//                .header("X-ViewUser", userToken))
//            .andExpect(status().isOk());
//    }
//
//    @Test
//    void updateAchievement_validAchievementDto_StatusNotContent() throws Exception {
//        String userToken = generatedUserToken(8L);
//        String achievementHolderCreateDto = gson.toJson(achievementHolderCreateDto());
//        String achievement = gson.toJson(generatedAchievement(LocalDate.now()));
//
//        mockMvc.perform(post("/api/achievement")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievementHolderCreateDto))
//            .andExpect(status().isCreated());
//
//        AchievementTracker achievementTracker = achievementRepository.findAllByUserId(8L).get(0);
//        assertNotNull(achievementTracker);
//
//        mockMvc.perform(patch("/api/achievement/" + achievementTracker.getId() + "/addProgress")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievement))
//            .andExpect(status().isOk());
//    }
//
//    @Test
//    void updateAchievement_validAchievementDtoWithNullDate_StatusBadRequest() throws Exception {
//        String userToken = generatedUserToken(9L);
//        String achievementHolderCreateDto = gson.toJson(achievementHolderCreateDto());
//        String achievement = gson.toJson(generatedAchievement(null));
//
//        mockMvc.perform(post("/api/achievement")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievementHolderCreateDto))
//            .andExpect(status().isCreated());
//
//        AchievementTracker achievementTracker = achievementRepository.findAllByUserId(9L).get(0);
//        assertNotNull(achievementTracker);
//
//        mockMvc.perform(patch("/api/achievement/" + achievementTracker.getId() + "/addProgress")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievement))
//            .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void updateAchievement_validAchievementDtoWithNullProgress_StatusBadRequest() throws Exception
// {
//        String userToken = generatedUserToken(10L);
//        String achievementHolderCreateDto = gson.toJson(achievementHolderCreateDto());
//        String achievement = gson.toJson(generatedAchievement(LocalDate.now()));
//        achievement = achievement.replace("1", "");
//
//        mockMvc.perform(post("/api/achievement")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievementHolderCreateDto))
//            .andExpect(status().isCreated());
//
//        AchievementTracker achievementTracker = achievementRepository.findAllByUserId(10L).get(0);
//        assertNotNull(achievementTracker);
//
//        mockMvc.perform(patch("/api/achievement/" + achievementTracker.getId() + "/addProgress")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievement))
//            .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void updateAchievement_validAchievementDtoWithNegativeProgress_StatusBadRequest() throws
// Exception {
//        String userToken = generatedUserToken(11L);
//        String achievementHolderCreateDto = gson.toJson(achievementHolderCreateDto());
//        String achievement = gson.toJson(generatedAchievement(LocalDate.now()));
//        achievement = achievement.replace("1", "-1");
//
//        mockMvc.perform(post("/api/achievement")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievementHolderCreateDto))
//            .andExpect(status().isCreated());
//
//        AchievementTracker achievementTracker = achievementRepository.findAllByUserId(11L).get(0);
//        assertNotNull(achievementTracker);
//
//        mockMvc.perform(patch("/api/achievement/" + achievementTracker.getId() + "/addProgress")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievement))
//            .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void updateAchievement_notExistingAchievementId_StatusBadRequest() throws Exception {
//        String userToken = generatedUserToken(12L);
//        String invalidAchId = "99";
//        String achievement = gson.toJson(generatedAchievement(LocalDate.now()));
//
//        mockMvc.perform(patch("/api/achievement/" + invalidAchId + "/addProgress")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievement))
//            .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    @Transactional
//    void
// updateAchievement_twoAchievementDtoWithTheSameDateReplaceDailyProgressTrue_TotalAchievementProgressReplacedStatusOk() throws Exception {
//        String userToken = generatedUserToken(13L);
//        LocalDate date = LocalDate.now();
//        String achievementHolderCreateDto = gson.toJson(achievementHolderCreateDto());
//        String achievement = gson.toJson(generatedAchievement(date));
//
//        mockMvc.perform(post("/api/achievement")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievementHolderCreateDto))
//            .andExpect(status().isCreated());
//
//        AchievementTracker achievementTracker = achievementRepository.findAllByUserId(13L).get(0);
//        assertNotNull(achievementTracker);
//
//        mockMvc.perform(patch("/api/achievement/" + achievementTracker.getId() +
// "/addProgress?replaceDailyProgress=true")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievement))
//            .andExpect(status().isOk());
//
//        Achievement achievement2 = generatedAchievement(date);
//        achievement2.setProgress(new BigDecimal(20));
//
//        mockMvc.perform(patch("/api/achievement/" + achievementTracker.getId() +
// "/addProgress?replaceDailyProgress=true")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(gson.toJson(achievement2)))
//            .andExpect(status().isOk());
//
//        achievementTracker = achievementRepository.findAllByUserId(13L).get(0);
//        SortedMap<LocalDate, Achievement> dailyProgress = achievementTracker.getDailyProgress();
//        Achievement result = dailyProgress.get(achievement2.getDate());
//
//
//        assertEquals(0, result.getProgress().compareTo(new BigDecimal(20)));
//
//    }
//
//    @Test
//    @Transactional
//    void
// updateAchievement_twoAchievementDtoWithTheSameDateReplaceDailyProgressFalse_TotalAchievementProgressAddedStatusOk() throws Exception {
//        String userToken = generatedUserToken(14L);
//        LocalDate date = LocalDate.now();
//        String achievementHolderCreateDto = gson.toJson(achievementHolderCreateDto());
//        String achievement = gson.toJson(generatedAchievement(date));
//
//        mockMvc.perform(post("/api/achievement")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievementHolderCreateDto))
//            .andExpect(status().isCreated());
//
//        AchievementTracker achievementTracker = achievementRepository.findAllByUserId(14L).get(0);
//        assertNotNull(achievementTracker);
//
//        mockMvc.perform(patch("/api/achievement/" + achievementTracker.getId() +
// "/addProgress?replaceDailyProgress=false")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievement))
//            .andExpect(status().isOk());
//
//        Achievement achievement2 = generatedAchievement(date);
//        achievement2.setProgress(new BigDecimal(20));
//
//        mockMvc.perform(patch("/api/achievement/" + achievementTracker.getId() +
// "/addProgress?replaceDailyProgress=false")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(gson.toJson(achievement2)))
//            .andExpect(status().isOk());
//
//        achievementTracker = achievementRepository.findAllByUserId(14L).get(0);
//        SortedMap<LocalDate, Achievement> dailyProgress = achievementTracker.getDailyProgress();
//        Achievement result = dailyProgress.get(achievement2.getDate());
//
//
//        assertEquals(0, result.getProgress().compareTo(new BigDecimal(21)));
//    }
//
//    @Test
//    void getAchievementHolderById_validUserDataAndId_StatusOk() throws Exception {
//        String userToken = generatedUserToken(15L);
//        String achievementHolderCreateDto = gson.toJson(achievementHolderCreateDto());
//
//        mockMvc.perform(post("/api/achievement")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievementHolderCreateDto))
//            .andExpect(status().isCreated());
//
//        AchievementTracker achievementTracker = achievementRepository.findAllByUserId(15L).get(0);
//        assertNotNull(achievementTracker);
//
//        mockMvc.perform(get("/api/achievement?id=" + achievementTracker.getId())
//                .header("X-ViewUser", userToken)
//                .contentType("application/json"))
//            .andExpect(status().isOk());
//    }
//
//    @Test
//    void getAchievementHolderById_validUserDataAndInvalidId_StatusBadRequest() throws Exception {
//        String userToken = generatedUserToken(16L);
//        String invalidId = "99";
//
//        mockMvc.perform(get("/api/achievement?id=" + invalidId)
//                .header("X-ViewUser", userToken)
//                .contentType("application/json"))
//            .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void getAchievementHolderById_validDataWith1AchievementInsertion_validViewStatusOk() throws
// Exception {
//        String userToken = generatedUserToken(17L);
//        LocalDate date = LocalDate.now();
//        String achievementHolderCreateDto = gson.toJson(achievementHolderCreateDto());
//        String achievement = gson.toJson(generatedAchievement(date));
//
//        mockMvc.perform(post("/api/achievement")
//            .header("X-ViewUser", userToken)
//            .contentType("application/json")
//            .content(achievementHolderCreateDto));
//
//        AchievementTracker achievementTracker = achievementRepository.findAllByUserId(17L).get(0);
//        assertNotNull(achievementTracker);
//
//        mockMvc.perform(patch("/api/achievement/" + achievementTracker.getId() +
// "/addProgress?replaceDailyProgress=true")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievement))
//            .andExpect(status().isOk());
//
//        mockMvc.perform(get("/api/achievement?id=" + achievementTracker.getId())
//                .header("X-ViewUser", userToken)
//                .contentType("application/json"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.dailyProgress.length()").value(1))
//            .andExpect(jsonPath("$.dailyProgress[0].date").value(date.toString()))
//            .andExpect(jsonPath("$.weeklyProgress.length()").value(1))
//            .andExpect(jsonPath("$.monthlyProgress.length()").value(1))
//            .andExpect(jsonPath("$.yearlyProgress.length()").value(1))
//            .andReturn();
//    }
//
//    @Test
//    void getAchievementHolderById_validDataWith2AchievementInsertion_validViewStatusOk() throws
// Exception {
//        String userToken = generatedUserToken(18L);
//        LocalDate date = LocalDate.parse("2023-01-01");
//        LocalDate date2 = LocalDate.parse("2023-02-01");
//        String achievementHolderCreateDto = gson.toJson(achievementHolderCreateDto());
//        String achievement = gson.toJson(generatedAchievement(date));
//        String achievement2 = gson.toJson(generatedAchievement(date2));
//
//        mockMvc.perform(post("/api/achievement")
//            .header("X-ViewUser", userToken)
//            .contentType("application/json")
//            .content(achievementHolderCreateDto));
//
//        AchievementTracker achievementTracker = achievementRepository.findAllByUserId(18L).get(0);
//        assertNotNull(achievementTracker);
//
//        mockMvc.perform(patch("/api/achievement/" + achievementTracker.getId() +
// "/addProgress?replaceDailyProgress=true")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievement))
//            .andExpect(status().isOk());
//
//        mockMvc.perform(patch("/api/achievement/" + achievementTracker.getId() +
// "/addProgress?replaceDailyProgress=true")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievement2))
//            .andExpect(status().isOk());
//
//        mockMvc.perform(get("/api/achievement?id=" + achievementTracker.getId())
//                .header("X-ViewUser", userToken)
//                .contentType("application/json"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.dailyProgress.length()").value(2))
//            .andExpect(jsonPath("$.weeklyProgress.length()").value(5))
//            .andExpect(jsonPath("$.monthlyProgress.length()").value(2))
//            .andExpect(jsonPath("$.yearlyProgress.length()").value(1))
//            .andReturn();
//    }
//
//    @Test
//    void
// getAchievementHolderById_validDataWith2AchievementInsertionAnd1AchievementUpdate_validViewStatusOk() throws Exception {
//        String userToken = generatedUserToken(19L);
//        LocalDate date = LocalDate.parse("2022-01-01");
//        LocalDate date2 = LocalDate.parse("2022-12-31");
//        String achievementHolderCreateDto = gson.toJson(achievementHolderCreateDto());
//        String achievement = gson.toJson(generatedAchievement(date));
//        String achievement2 = gson.toJson(generatedAchievement(date2));
//
//        mockMvc.perform(post("/api/achievement")
//            .header("X-ViewUser", userToken)
//            .contentType("application/json")
//            .content(achievementHolderCreateDto));
//
//        AchievementTracker achievementTracker = achievementRepository.findAllByUserId(19L).get(0);
//        assertNotNull(achievementTracker);
//
//        mockMvc.perform(patch("/api/achievement/" + achievementTracker.getId() +
// "/addProgress?replaceDailyProgress=true")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievement))
//            .andExpect(status().isOk());
//
//        mockMvc.perform(patch("/api/achievement/" + achievementTracker.getId() +
// "/addProgress?replaceDailyProgress=true")
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievement2))
//            .andExpect(status().isOk());
//
//
//        mockMvc.perform(get("/api/achievement?id=" + achievementTracker.getId())
//                .header("X-ViewUser", userToken)
//                .contentType("application/json"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.dailyProgress.length()").value(2))
//            .andExpect(jsonPath("$.weeklyProgress.length()").value(53))
//            .andExpect(jsonPath("$.monthlyProgress.length()").value(12))
//            .andExpect(jsonPath("$.yearlyProgress.length()").value(1))
//            .andReturn();
//    }
//
//    @Test
//    void changeAchievementTracker_validUserDataAndDto_StatusNoContent() throws Exception {
//        String userToken = generatedUserToken(20L);
//        String achievementHolderCreateDto = gson.toJson(achievementHolderCreateDto());
//        AchievementTrackerEditDto trackerEditDto = achievementTrackerEditDto();
//        String achievementTrackerEditDto = gson.toJson(trackerEditDto);
//
//        mockMvc.perform(post("/api/achievement")
//            .header("X-ViewUser", userToken)
//            .contentType("application/json")
//            .content(achievementHolderCreateDto));
//
//        AchievementTracker achievementTracker = achievementRepository.findAllByUserId(20L).get(0);
//        assertNotNull(achievementTracker);
//
//        mockMvc.perform(patch("/api/achievement/" + achievementTracker.getId())
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievementTrackerEditDto))
//            .andExpect(status().isNoContent());
//
//        mockMvc.perform(get("/api/achievement?id=" + achievementTracker.getId())
//                .header("X-ViewUser", userToken)
//                .contentType("application/json"))
//            .andExpect(jsonPath("$.name").value(trackerEditDto.getName()))
//            .andExpect(jsonPath("$.description").value(trackerEditDto.getDescription()))
//            .andExpect(jsonPath("$.goal").value(trackerEditDto.getGoal().intValue()))
//            .andExpect(jsonPath("$.measurement").value(trackerEditDto.getMeasurement()))
//            .andExpect(status().isOk());
//    }
//
//    @Test
//    void changeAchievementTracker_validUserDataAndDtoWithEmptyName_StatusNoContent() throws
// Exception {
//        String userToken = generatedUserToken(21L);
//        String achievementHolderCreateDto = gson.toJson(achievementHolderCreateDto());
//        AchievementTrackerEditDto trackerEditDto = achievementTrackerEditDto();
//        trackerEditDto.setName("");
//        String achievementTrackerEditDto = gson.toJson(trackerEditDto);
//
//        mockMvc.perform(post("/api/achievement")
//            .header("X-ViewUser", userToken)
//            .contentType("application/json")
//            .content(achievementHolderCreateDto));
//
//        AchievementTracker achievementTracker = achievementRepository.findAllByUserId(21L).get(0);
//        assertNotNull(achievementTracker);
//
//        mockMvc.perform(patch("/api/achievement/" + achievementTracker.getId())
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievementTrackerEditDto))
//            .andExpect(status().isNoContent());
//
//        mockMvc.perform(get("/api/achievement?id=" + achievementTracker.getId())
//                .header("X-ViewUser", userToken)
//                .contentType("application/json"))
//            .andExpect(jsonPath("$.name").value(achievementTracker.getName()))
//            .andExpect(jsonPath("$.description").value(trackerEditDto.getDescription()))
//            .andExpect(jsonPath("$.goal").value(trackerEditDto.getGoal().intValue()))
//            .andExpect(jsonPath("$.measurement").value(trackerEditDto.getMeasurement()))
//            .andExpect(status().isOk());
//    }
//
//    @Test
//    void changeAchievementTracker_validUserDataAndDtoWithNonValidData_StatusNoContent() throws
// Exception {
//        String userToken = generatedUserToken(22L);
//        String achievementHolderCreateDto = gson.toJson(achievementHolderCreateDto());
//        AchievementTrackerEditDto trackerEditDto = achievementTrackerEditDto();
//        trackerEditDto.setDescription("");
//        trackerEditDto.setName("");
//        trackerEditDto.setGoal(null);
//        trackerEditDto.setMeasurement("");
//        String achievementTrackerEditDto = gson.toJson(trackerEditDto);
//
//        mockMvc.perform(post("/api/achievement")
//            .header("X-ViewUser", userToken)
//            .contentType("application/json")
//            .content(achievementHolderCreateDto));
//
//        AchievementTracker achievementTracker = achievementRepository.findAllByUserId(22L).get(0);
//        assertNotNull(achievementTracker);
//
//        mockMvc.perform(patch("/api/achievement/" + achievementTracker.getId())
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievementTrackerEditDto))
//            .andExpect(status().isNoContent());
//
//        mockMvc.perform(get("/api/achievement?id=" + achievementTracker.getId())
//                .header("X-ViewUser", userToken)
//                .contentType("application/json"))
//            .andExpect(jsonPath("$.name").value(achievementTracker.getName()))
//            .andExpect(jsonPath("$.description").value(achievementTracker.getDescription()))
//            .andExpect(jsonPath("$.goal").value(achievementTracker.getGoal().intValue()))
//            .andExpect(jsonPath("$.measurement").value(achievementTracker.getMeasurement()))
//            .andExpect(status().isOk());
//    }
//
//    @Test
//    void changeAchievementTracker_invalidAchievementId_StatusBadRequest() throws Exception {
//        String userToken = generatedUserToken(23L);
//        String invalidId = "99";
//        String achievementTrackerEditDto = gson.toJson(achievementTrackerEditDto());
//
//        mockMvc.perform(patch("/api/achievement/" + invalidId)
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievementTrackerEditDto))
//            .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void changeAchievementTracker_validUserDataAndDtoWithAlreadyExistingName_StatusBadRequest()
// throws Exception {
//        String userToken = generatedUserToken(24L);
//        String achievementHolderCreateDto = gson.toJson(achievementHolderCreateDto());
//        AchievementTrackerEditDto trackerEditDto = achievementTrackerEditDto();
//        String achievementTrackerEditDto = gson.toJson(trackerEditDto);
//
//        mockMvc.perform(post("/api/achievement")
//            .header("X-ViewUser", userToken)
//            .contentType("application/json")
//            .content(achievementHolderCreateDto));
//
//        AchievementTracker achievementTracker = achievementRepository.findAllByUserId(24L).get(0);
//        achievementHolderCreateDto = achievementHolderCreateDto.replace("test", "test2");
//        assertNotNull(achievementTracker);
//
//        mockMvc.perform(post("/api/achievement")
//            .header("X-ViewUser", userToken)
//            .contentType("application/json")
//            .content(achievementHolderCreateDto));
//
//        AchievementTracker achievementTracker2 =
// achievementRepository.findAllByUserId(24L).get(1);
//        assertNotNull(achievementTracker2);
//
//        mockMvc.perform(patch("/api/achievement/" + achievementTracker2.getId())
//                .header("X-ViewUser", userToken)
//                .contentType("application/json")
//                .content(achievementTrackerEditDto))
//            .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void deleteAchievement_validUserDataAndId_StatusNoContent() throws Exception {
//        String userToken = generatedUserToken(25L);
//        String achievementHolderCreateDto = gson.toJson(achievementHolderCreateDto());
//
//        mockMvc.perform(post("/api/achievement")
//            .header("X-ViewUser", userToken)
//            .contentType("application/json")
//            .content(achievementHolderCreateDto));
//
//        AchievementTracker achievementTracker = achievementRepository.findAllByUserId(25L).get(0);
//        assertNotNull(achievementTracker);
//
//        mockMvc.perform(delete("/api/achievement?id=" + achievementTracker.getId())
//                .header("X-ViewUser", userToken)
//                .contentType("application/json"))
//            .andExpect(status().isNoContent());
//    }
//
//    @Test
//    void deleteAchievement_invalidAchievementId_StatusBadRequest() throws Exception {
//        String userToken = generatedUserToken(26L);
//        String invalidId = "99";
//
//        mockMvc.perform(delete("/api/achievement?id=" + invalidId)
//                .header("X-ViewUser", userToken)
//                .contentType("application/json"))
//            .andExpect(status().isBadRequest());
//    }
//    private AchievementHolderCreateDto achievementHolderCreateDto() {
//        AchievementHolderCreateDto achievementHolderCreateDto = new AchievementHolderCreateDto();
//        achievementHolderCreateDto.setName("test");
//        achievementHolderCreateDto.setDescription("test");
//        achievementHolderCreateDto.setGoal(new BigDecimal(1));
//        return achievementHolderCreateDto;
//    }
//
//    private String generatedUserToken(Long userId) throws JsonProcessingException {
//        User user = new User();
//        user.setUsername("test");
//        user.setId(userId);
//        return objectMapper.writeValueAsString(user);
//    }
//
//    private Achievement generatedAchievement(LocalDate date) {
//        Achievement achievement = new Achievement();
//        achievement.setProgress(new BigDecimal(1));
//        achievement.setDate(date);
//        return achievement;
//    }
//
//    private AchievementTrackerEditDto achievementTrackerEditDto() {
//        AchievementTrackerEditDto achievementTrackerEditDto = new AchievementTrackerEditDto();
//        achievementTrackerEditDto.setName("test2");
//        achievementTrackerEditDto.setDescription("test2");
//        achievementTrackerEditDto.setGoal(new BigDecimal(2.0));
//        achievementTrackerEditDto.setMeasurement("test2");
//        return achievementTrackerEditDto;
//    }
// }

package org.record;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.record.client.StorageClient;
import org.record.client.dto.StorageView;
import org.record.client.dto.User;
import org.record.model.entity.Record;
import org.record.model.enums.Gender;
import org.record.model.enums.WorkoutState;
import org.record.services.RecordServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.google.gson.Gson;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class RecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecordServiceImp recordService;

    @Autowired
    private RecordRepository recordRepository;

    private Gson gson = new Gson();

    private User userView;

    private StorageClient storageClient;

    @BeforeEach
    public void setUp() {
        userView = new User();
        userView.setId(1L);
        userView.setAge(20);
        userView.setEmail("test@abv.bg");
        userView.setGender(Gender.FEMALE);
        userView.setHeight(new BigDecimal(170));
        userView.setKilograms(new BigDecimal("60"));
        userView.setUsername("test");
        userView.setWorkoutState(WorkoutState.MODERATELY_ACTIVE);

        storageClient = Mockito.mock(StorageClient.class);

        ReflectionTestUtils.setField(recordService, "storageClient", storageClient);
    }

    @AfterEach
    public void tearDown() {
        recordRepository.deleteAll();
    }

    @Test
    public void getAllRecords_ValidInputHeader_StatusOk() throws Exception {

        Record record = new Record();
        record.setId(1L);
        record.setUserId(userView.getId());
        recordRepository.save(record);

        when(storageClient.getAllStorages(record.getId(), gson.toJson(userView)))
                .thenReturn(new ResponseEntity<>(List.of(getStorage()), HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/record/all")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-ViewUser", gson.toJson(userView)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getAllRecords_InvalidInputHeader_StatusBadRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/record/all")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-ViewUser", gson.toJson(new User())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void getById_ValidInputHeader_StatusOk() throws Exception {

        Record record = new Record();
        userView.setId(2L);
        record.setId(2L);
        record.setUserId(userView.getId());
        recordRepository.save(record);

        when(storageClient.getAllStorages(record.getId(), gson.toJson(userView)))
                .thenReturn(new ResponseEntity<>(List.of(getStorage()), HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/record/" + record.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-ViewUser", gson.toJson(userView)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(record.getId()));
    }

    @Test
    public void getById_InvalidInputHeader_StatusBadRequest() throws Exception {

        Long invalidRecordId = 3L;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/record/" + invalidRecordId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-ViewUser", gson.toJson(new User())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void createNewRecord_ValidInputNameEmpty_StatusOk() throws Exception {

        userView.setId(3L);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/record")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-ViewUser", gson.toJson(userView)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        List<Record> result = recordRepository.findAllByUserId(userView.getId());

        assertTrue(result.size() == 1);
    }

    @Test
    public void createNewRecord_ValidInputNameTest_StatusOk() throws Exception {

        String name = "test";
        userView.setId(4L);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/record")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-ViewUser", gson.toJson(userView))
                .param("name", name))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        List<Record> result = recordRepository.findAllByUserId(userView.getId());

        assertTrue(result.size() == 1);
        assertTrue(result.get(0).getName().equals(name));
    }

    @Test
    public void createNewRecord_MissingOrNegativeAge_StatusBadRequest() throws Exception {

        userView.setAge(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/record")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-ViewUser", gson.toJson(userView)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        userView.setAge(-1);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/record")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-ViewUser", gson.toJson(userView)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void createNewRecord_MissingGender_StatusBadRequest() throws Exception {

        userView.setGender(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/record")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-ViewUser", gson.toJson(userView)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void createNewRecord_MissingOrNegativeHeight_StatusBadRequest() throws Exception {

        userView.setHeight(new BigDecimal(-1));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/record")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-ViewUser", gson.toJson(userView)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        userView.setHeight(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/record")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-ViewUser", gson.toJson(userView)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void createNewRecord_NegativeOrMissingKilograms_StatusBadRequest() throws Exception {

        userView.setKilograms(new BigDecimal("-1"));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/record")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-ViewUser", gson.toJson(userView)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        userView.setKilograms(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/record")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-ViewUser", gson.toJson(userView)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void createNewRecord_MissingWorkoutState_StatusBadRequest() throws Exception {

        userView.setWorkoutState(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/record")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-ViewUser", gson.toJson(userView)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void createNewRecord_MissingOrInvalidEmail_StatusBadRequest() throws Exception {

        userView.setEmail(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/record")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-ViewUser", gson.toJson(userView)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        userView.setEmail("test");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/record")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-ViewUser", gson.toJson(userView))).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void createNewRecord_MissingOrInvalidUsername_StatusBadRequest() throws Exception {

        userView.setUsername(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/record")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-ViewUser", gson.toJson(userView))).andExpect(MockMvcResultMatchers.status().isBadRequest());

        userView.setUsername("");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/record")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-ViewUser", gson.toJson(userView))).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deleteById_ValidInputHeader_StatusNoContent() throws Exception {

        Record record = new Record();
        userView.setId(5L);
        record.setId(5L);
        record.setUserId(userView.getId());
        record = recordRepository.save(record);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/record/" + record.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-ViewUser", gson.toJson(userView)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        assertTrue(recordRepository.findById(record.getId()).isEmpty());
    }

    @Test
    public void deleteById_InvalidRecordId_StatusNotFound() throws Exception {
        Long invalidRecordId = 666L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/record/" + invalidRecordId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-ViewUser", gson.toJson(userView)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void createStorage_ValidInputEmptyName_StatusNoContent() throws Exception {

        Record record = new Record();
        record.setId(7L);
        userView.setId(7L);
        record.setUserId(userView.getId());
        record = recordRepository.save(record);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/record/" + record.getId() + "/storage")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-ViewUser", gson.toJson(userView))
                .param("storageName", ""))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        assertFalse(recordRepository.findById(record.getId()).isEmpty());
    }

    @Test
    public void createStorage_InvalidRecordId_StatusBadRequest() throws Exception {

        Long invalidRecordId = 666L;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/record/" + invalidRecordId + "/storage")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-ViewUser", gson.toJson(userView))
                .param("storageName", "test"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deleteStorage_ValidInput_StatusNoContent() throws Exception {

        Record record = new Record();
        Long validStorageId = 1L;
        record.setId(8L);
        userView.setId(8L);
        record.setUserId(userView.getId());
        record = recordRepository.save(record);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/record/" + record.getId() + "/storage/" + validStorageId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-ViewUser", gson.toJson(userView)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        assertFalse(recordRepository.findById(record.getId()).isEmpty());
    }

    @Test
    public void deleteStorage_InvalidRecordIdInput_StatusBadRequest() throws Exception {
        Long invalidRecordId = 666L;
        Long invalidStorageId = 666L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/record/" + invalidRecordId + "/storage/" + invalidStorageId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-ViewUser", gson.toJson(userView))
                .param("storageId", "1"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deleteStorage_InvalidStorageIdInput_StatusBadRequest() throws Exception {
        Long invalidStorageId = 666L;

        Record record = new Record();
        record.setId(9L);
        userView.setId(9L);
        record.setUserId(userView.getId());
        record = recordRepository.save(record);

        when(storageClient.deleteStorage(invalidStorageId, record.getId(), gson.toJson(userView)))
                .thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/record/" + record.getId() + "/storage/" + invalidStorageId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-ViewUser", gson.toJson(userView)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private StorageView getStorage() {
        StorageView storage = new StorageView();
        storage.setId(1L);
        storage.setFoods(List.of());
        storage.setName("test");
        return storage;
    }
}

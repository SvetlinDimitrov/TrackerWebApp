// package org.auth;
//
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
//
// import java.math.BigDecimal;
// import java.util.Collections;
// import java.util.Map;
//
// import org.apache.kafka.clients.consumer.Consumer;
// import org.apache.kafka.clients.consumer.ConsumerConfig;
// import org.apache.kafka.clients.consumer.ConsumerRecords;
// import org.apache.kafka.common.serialization.StringDeserializer;
// import org.auth.config.security.JwtUtil;
// import org.auth.model.dto.EditUserDto;
// import org.auth.model.dto.JwtTokenView;
// import org.auth.model.dto.LoginUserDto;
// import org.auth.model.dto.RegisterUserDto;
// import org.auth.user.dto.UserView;
// import org.auth.user.entity.User;
// import org.auth.model.enums.Gender;
// import org.auth.model.enums.UserDetails;
// import org.auth.model.enums.WorkoutState;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
// import org.springframework.kafka.test.EmbeddedKafkaBroker;
// import org.springframework.kafka.test.context.EmbeddedKafka;
// import org.springframework.kafka.test.utils.KafkaTestUtils;
// import org.springframework.mock.web.MockHttpServletResponse;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.MvcResult;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
// import com.fasterxml.jackson.databind.ObjectMapper;
//
// @SpringBootTest
// @ActiveProfiles("test")
// @AutoConfigureMockMvc
// @EmbeddedKafka(brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" },
// partitions = 1)
// public class UserControllerImpTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private EmbeddedKafkaBroker embeddedKafkaBroker;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private RegisterUserDto validRegisterUserDto;
//
//    @BeforeEach
//    public void setUp() {
//
//        userRepository.deleteAll();
//
//        validRegisterUserDto = new RegisterUserDto();
//        validRegisterUserDto.setEmail("test@abv.bg");
//        validRegisterUserDto.setPassword("12345");
//        validRegisterUserDto.setUsername("test");
//    }
//
//    @Test
//    public void createUserAccount_ValidInput_ReturnsHttp204() throws Exception {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(validRegisterUserDto)))
//                .andExpect(MockMvcResultMatchers.status().isCreated());
//
//    }
//
//    @Test
//    public void createUserAccount_InvalidInput_ReturnsHttp400() throws Exception {
//        RegisterUserDto registerUserDto = new RegisterUserDto();
//        registerUserDto.setEmail("testabv.bg");
//        registerUserDto.setPassword("1345");
//        registerUserDto.setUsername("tet");
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(registerUserDto)))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest());
//
//    }
//
//    @Test
//    public void createUserAccount_FullValidInput_ReturnsHttp204SendMessageIntoKafka() throws
// Exception {
//
//        Map<String, Object> consumerProps1 = KafkaTestUtils.consumerProps("test-group-1", "true",
//                embeddedKafkaBroker);
//        consumerProps1.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
// StringDeserializer.class);
//        consumerProps1.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
// StringDeserializer.class);
//        DefaultKafkaConsumerFactory<String, String> cf1 = new
// DefaultKafkaConsumerFactory<>(consumerProps1);
//        Consumer<String, String> consumer1 = cf1.createConsumer();
//
//        validRegisterUserDto.setKg(new BigDecimal(80));
//        validRegisterUserDto.setWorkoutState(WorkoutState.LIGHTLY_ACTIVE);
//        validRegisterUserDto.setAge(80);
//        validRegisterUserDto.setHeight(new BigDecimal(180));
//        validRegisterUserDto.setGender(Gender.FEMALE);
//
//        User user = validRegisterUserDto.toUser();
//        user.setUserDetails(UserDetails.COMPLETED);
//        user.setFirstRecord(true);
//        user.setId(1L);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(validRegisterUserDto)))
//                .andExpect(MockMvcResultMatchers.status().isCreated());
//
//        consumer1.subscribe(Collections.singletonList("USER_FIRST_CREATION"));
//
//        ConsumerRecords<String, String> records = KafkaTestUtils.getRecords(consumer1);
//
//        assertFalse(records.isEmpty());
//    }
//
//    @Test
//    public void login_ValidInput_ReturnsHttp200() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(validRegisterUserDto)))
//                .andExpect(MockMvcResultMatchers.status().isCreated());
//
//        LoginUserDto loginUserDto = new LoginUserDto();
//        loginUserDto.setEmail(validRegisterUserDto.getEmail());
//        loginUserDto.setPassword(validRegisterUserDto.getPassword());
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(loginUserDto)))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//    @Test
//    public void login_WrongInput_ReturnsHttp400() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(validRegisterUserDto)))
//                .andExpect(MockMvcResultMatchers.status().isCreated());
//
//        LoginUserDto loginUserDto = new LoginUserDto();
//        loginUserDto.setEmail("invalid@abv.bg");
//        loginUserDto.setPassword("12345");
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(loginUserDto)))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest());
//    }
//
//    @Test
//    public void getUserDetails_ValidAuthToken_ReturnsHttp200() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(validRegisterUserDto)))
//                .andExpect(MockMvcResultMatchers.status().isCreated());
//
//        LoginUserDto loginUserDto = new LoginUserDto();
//        loginUserDto.setEmail(validRegisterUserDto.getEmail());
//        loginUserDto.setPassword(validRegisterUserDto.getPassword());
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(loginUserDto)))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        UserView userView = new
// UserView(userRepository.findByEmail(validRegisterUserDto.getEmail()).get());
//        String authToken = jwtUtil.createJwtToken(userView).getToken();
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/details")
//                .header("Authorization", "Bearer " + authToken))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//    @Test
//    public void getUserDetails_InvalidAuthToken_ReturnHttp403() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(validRegisterUserDto)))
//                .andExpect(MockMvcResultMatchers.status().isCreated());
//
//        LoginUserDto loginUserDto = new LoginUserDto();
//        loginUserDto.setEmail(validRegisterUserDto.getEmail());
//        loginUserDto.setPassword(validRegisterUserDto.getPassword());
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(loginUserDto)))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        String authToken = "invalidToken";
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/details")
//                .header("Authorization", "Bearer " + authToken))
//                .andExpect(MockMvcResultMatchers.status().isForbidden());
//    }
//
//    @Test
//    public void getUserDetails_NotExistingAuthToken_ReturnHttp403() throws Exception {
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/details"))
//                .andExpect(MockMvcResultMatchers.status().isForbidden());
//    }
//
//    @Test
//    public void editUserProfile_ValidInput_ReturnsHttp200() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(validRegisterUserDto)))
//                .andExpect(MockMvcResultMatchers.status().isCreated());
//
//        LoginUserDto loginUserDto = new LoginUserDto();
//        loginUserDto.setEmail(validRegisterUserDto.getEmail());
//        loginUserDto.setPassword(validRegisterUserDto.getPassword());
//
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(loginUserDto)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn();
//
//        MockHttpServletResponse response = mvcResult.getResponse();
//
//        JwtTokenView jwtTokenView = objectMapper.readValue(response.getContentAsString(),
// JwtTokenView.class);
//
//        EditUserDto editUserDto = new EditUserDto();
//        editUserDto.setAge(20);
//
//        mockMvc.perform(MockMvcRequestBuilders.patch("/api/user/edit")
//                .header("Authorization", "Bearer " + jwtTokenView.getToken())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(editUserDto)))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        User user = userRepository.findByEmail(validRegisterUserDto.getEmail()).get();
//        assertEquals(user.getAge(), editUserDto.getAge());
//
//    }
//
//    @Test
//    public void editUserProfile_FullyValidInput_ReturnsHttp200SendsMessageToBroker() throws
// Exception {
//
//        Map<String, Object> consumerProps2 = KafkaTestUtils.consumerProps("test-group-2", "true",
//                embeddedKafkaBroker);
//        consumerProps2.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
// StringDeserializer.class);
//        consumerProps2.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
// StringDeserializer.class);
//        DefaultKafkaConsumerFactory<String, String> cf2 = new
// DefaultKafkaConsumerFactory<>(consumerProps2);
//        Consumer<String, String> consumer2 = cf2.createConsumer();
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        RegisterUserDto registerUserDto = new RegisterUserDto();
//        registerUserDto.setEmail("test5@abv.bg");
//        registerUserDto.setPassword("12345");
//        registerUserDto.setUsername("test5");
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(registerUserDto)))
//                .andExpect(MockMvcResultMatchers.status().isCreated());
//
//        LoginUserDto loginUserDto = new LoginUserDto();
//        loginUserDto.setEmail(registerUserDto.getEmail());
//        loginUserDto.setPassword(registerUserDto.getPassword());
//
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(loginUserDto)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn();
//
//        MockHttpServletResponse response = mvcResult.getResponse();
//
//        JwtTokenView jwtTokenView = objectMapper.readValue(response.getContentAsString(),
// JwtTokenView.class);
//
//        EditUserDto editUserDto = new EditUserDto();
//        editUserDto.setAge(20);
//        editUserDto.setKilograms(new BigDecimal(80.00));
//        editUserDto.setWorkoutState(WorkoutState.LIGHTLY_ACTIVE);
//        editUserDto.setHeight(new BigDecimal(180.00));
//        editUserDto.setGender(Gender.FEMALE);
//
//        mockMvc.perform(MockMvcRequestBuilders.patch("/api/user/edit")
//                .header("Authorization", "Bearer " + jwtTokenView.getToken())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(editUserDto)))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        User user = userRepository.findByEmail(registerUserDto.getEmail()).get();
//        assertEquals(user.getUserDetails(), UserDetails.COMPLETED);
//
//        consumer2.subscribe(Collections.singletonList("USER_FIRST_CREATION"));
//
//        ConsumerRecords<String, String> records = KafkaTestUtils.getRecords(consumer2);
//
//        assertFalse(records.isEmpty());
//    }
//
//    @Test
//    public void editUserProfile_InvalidInput_ReturnsHttp403() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        EditUserDto editUserDto = new EditUserDto();
//        editUserDto.setAge(20);
//        editUserDto.setKilograms(new BigDecimal(80.00));
//        editUserDto.setWorkoutState(WorkoutState.LIGHTLY_ACTIVE);
//
//        String invalidToken = "invalidToken";
//        mockMvc.perform(MockMvcRequestBuilders.patch("/api/user/edit")
//                .header("Authorization", "Bearer " + invalidToken)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(editUserDto)))
//                .andExpect(MockMvcResultMatchers.status().isForbidden());
//    }
//
//    @Test
//    public void deleteUser_ValidAuthToken_ReturnsHttp200() throws Exception {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        RegisterUserDto registerUserDto = new RegisterUserDto();
//        registerUserDto.setEmail("test6@abv.bg");
//        registerUserDto.setPassword("12345");
//        registerUserDto.setUsername("test6");
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(registerUserDto)))
//                .andExpect(MockMvcResultMatchers.status().isCreated());
//
//        LoginUserDto loginUserDto = new LoginUserDto();
//        loginUserDto.setEmail(registerUserDto.getEmail());
//        loginUserDto.setPassword(registerUserDto.getPassword());
//
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(loginUserDto)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn();
//
//        MockHttpServletResponse response = mvcResult.getResponse();
//
//        JwtTokenView jwtTokenView = objectMapper.readValue(response.getContentAsString(),
// JwtTokenView.class);
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user")
//                .header("Authorization", "Bearer " + jwtTokenView.getToken()))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//    @Test
//    public void deleteUser_InvalidAuthToken_ReturnsHttp403() throws Exception {
//        // Mock the authorization header
//        String authToken = "invalidAuthToken";
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user")
//                .header("Authorization", "Bearer " + authToken))
//                .andExpect(MockMvcResultMatchers.status().isForbidden());
//    }
// }

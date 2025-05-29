// package org.auth.services;
//
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.never;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;
//
// import java.math.BigDecimal;
// import java.util.Optional;
//
// import org.auth.features.user.repository.UserRepository;
// import org.auth.exceptions.UserNotFoundException;
// import org.auth.model.dto.EditUserDto;
// import org.auth.model.dto.RegisterUserDto;
// import org.auth.user.dto.UserView;
// import org.auth.user.entity.User;
// import org.auth.model.enums.Gender;
// import org.auth.model.enums.UserDetails;
// import org.auth.model.enums.WorkoutState;
// import org.auth.util.GsonWrapper;
// import org.auth.util.UserValidator;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.ArgumentCaptor;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.kafka.core.KafkaTemplate;
// import org.springframework.security.crypto.password.PasswordEncoder;
//
// @ExtendWith(MockitoExtension.class)
// public class UserServiceKafkaImpTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private UserValidator validator;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @Mock
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    @Mock
//    private GsonWrapper gson;
//
//    @InjectMocks
//    private UserServiceKafkaImp userServiceKafkaImp;
//
//    private User user;
//    private RegisterUserDto registerUserDto;
//    private ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
//
//    @BeforeEach
//    public void setUp() {
//        user = new User();
//        user.setEmail("email@abv.bg");
//        user.setUsername("username");
//        user.setPassword("password");
//        new UserView(user);
//        registerUserDto = new RegisterUserDto();
//        registerUserDto.setEmail("email@abv.bg");
//        registerUserDto.setUsername("username");
//        registerUserDto.setPassword("password");
//    }
//
//    @Test
//    public void register_ValidInput_SuccessfulRegister() {
//
//        when(passwordEncoder.encode(user.getPassword())).thenReturn("password");
//        when(validator.validateFullRegistration(any(User.class))).thenReturn(false);
//
//        userServiceKafkaImp.register(registerUserDto);
//
//        verify(userRepository).saveAndFlush(userCaptor.capture());
//
//        User savedUser = userCaptor.getValue();
//
//        assertEquals(savedUser.getEmail(), user.getEmail());
//        assertEquals(savedUser.getUsername(), user.getUsername());
//
//    }
//
//    @Test
//    public void testEditUserEntity_shouldUpdateUserEntityWithValidInput_Successful() throws
// UserNotFoundException {
//
//        EditUserDto userDto = getEditUserDto();
//
//        Long userId = 1L;
//
//        User user = new User();
//        user.setId(userId);
//
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//        when(validator.validUsernameChange(userDto)).thenReturn(true);
//        when(validator.validKilogramsChange(userDto)).thenReturn(true);
//        when(validator.validWorkoutStateChange(userDto)).thenReturn(true);
//        when(validator.validHeightChange(userDto)).thenReturn(true);
//        when(validator.validGenderChange(userDto)).thenReturn(true);
//        when(validator.validAgeChange(userDto)).thenReturn(true);
//
//        // Act
//        UserView result = userServiceKafkaImp.editUserEntity(userDto, userId);
//
//        // Assert
//        verify(userRepository).saveAndFlush(user);
//        assertEquals(user.getUsername(), result.getUsername());
//        assertEquals(user.getKilograms(), result.getKilograms());
//        assertEquals(user.getWorkoutState(), result.getWorkoutState());
//        assertEquals(user.getHeight(), result.getHeight());
//        assertEquals(user.getGender(), result.getGender());
//        assertEquals(user.getAge(), result.getAge());
//    }
//
//    @Test
//    public void testEditUserEntity_shouldUpdateUserEntityToCompletedUserDetails_Successful()
//            throws UserNotFoundException {
//
//        EditUserDto userDto = getEditUserDto();
//        Long userId = 1L;
//        String token = gson.toJson(new UserView(user));
//
//        User user = new User();
//        user.setId(userId);
//        user.setFirstRecord(false);
//
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//        when(validator.validUsernameChange(userDto)).thenReturn(true);
//        when(validator.validKilogramsChange(userDto)).thenReturn(true);
//        when(validator.validWorkoutStateChange(userDto)).thenReturn(true);
//        when(validator.validHeightChange(userDto)).thenReturn(true);
//        when(validator.validGenderChange(userDto)).thenReturn(true);
//        when(validator.validAgeChange(userDto)).thenReturn(true);
//        when(validator.validateFullRegistration(user)).thenReturn(true);
//
//        UserView result = userServiceKafkaImp.editUserEntity(userDto, userId);
//
//        verify(userRepository).saveAndFlush(user);
//        verify(kafkaTemplate).send("USER_FIRST_CREATION", token);
//        assertEquals(result.getUserDetails(), UserDetails.COMPLETED);
//        assertTrue(user.getFirstRecord());
//    }
//
//    @Test
//    public void testEditUserEntity_shouldNotUpdateUserEntityIfUserIdIsInvalid_throwsException() {
//        EditUserDto userDto = getEditUserDto();
//
//        Long userId = 1L;
//
//        when(userRepository.findById(userId)).thenReturn(Optional.empty());
//
//        assertThrows(UserNotFoundException.class, () ->
// userServiceKafkaImp.editUserEntity(userDto, userId));
//
//        verify(userRepository, never()).saveAndFlush(any(User.class));
//    }
//
//    @Test
//    public void testEditUserEntity_allFieldsAreInvalid_NothingChanges() throws
// UserNotFoundException {
//        EditUserDto userDto = getEditUserDto();
//        Long userId = 1L;
//
//        User existingUser = new User();
//        existingUser.setId(userId);
//        existingUser.setUsername("oldUsername");
//        existingUser.setKilograms(new BigDecimal(60));
//        existingUser.setWorkoutState(WorkoutState.SEDENTARY);
//        existingUser.setHeight(new BigDecimal(160));
//        existingUser.setGender(Gender.FEMALE);
//        existingUser.setAge(30);
//
//        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
//        Mockito.when(validator.validUsernameChange(userDto)).thenReturn(false);
//        Mockito.when(validator.validKilogramsChange(userDto)).thenReturn(false);
//        Mockito.when(validator.validWorkoutStateChange(userDto)).thenReturn(false);
//        Mockito.when(validator.validHeightChange(userDto)).thenReturn(false);
//        Mockito.when(validator.validGenderChange(userDto)).thenReturn(false);
//        Mockito.when(validator.validAgeChange(userDto)).thenReturn(false);
//
//        // Act
//        UserView result = userServiceKafkaImp.editUserEntity(userDto, userId);
//
//        // Assert
//        assertEquals(existingUser.getUsername(), result.getUsername());
//        assertEquals(existingUser.getKilograms(), result.getKilograms());
//        assertEquals(existingUser.getWorkoutState(), result.getWorkoutState());
//        assertEquals(existingUser.getHeight(), result.getHeight());
//        assertEquals(existingUser.getGender(), result.getGender());
//        assertEquals(existingUser.getAge(), result.getAge());
//    }
//
//    @Test
//    public void testDeleteUserById_ValidUserId_UserDeleted() throws UserNotFoundException {
//        Long userId = 2L;
//        User user = new User();
//        user.setId(userId);
//        String token =gson.toJson(new UserView(user));
//
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//
//        userServiceKafkaImp.deleteUserById(userId);
//
//        verify(userRepository).deleteById(userId);
//        verify(kafkaTemplate).send("USER_DELETION", token);
//    }
//
//    @Test
//    public void testDeleteUserById_InvalidUserId_ThrowsUserNotFoundException() {
//        Long userId = 1L;
//
//        when(userRepository.findById(userId)).thenReturn(Optional.empty());
//
//        assertThrows(UserNotFoundException.class, () ->
// userServiceKafkaImp.deleteUserById(userId));
//    }
//
//    public EditUserDto getEditUserDto() {
//        EditUserDto userDto = new EditUserDto();
//        userDto.setUsername("newUsername");
//        userDto.setKilograms(new BigDecimal(70));
//        userDto.setWorkoutState(WorkoutState.LIGHTLY_ACTIVE);
//        userDto.setHeight(new BigDecimal(170));
//        userDto.setGender(Gender.MALE);
//        userDto.setAge(25);
//        return userDto;
//    }
// }

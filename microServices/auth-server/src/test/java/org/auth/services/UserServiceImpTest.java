// package org.auth.services;
//
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.Mockito.when;
//
// import java.util.Optional;
//
// import org.auth.features.user.repository.UserRepository;
// import org.auth.exceptions.WrongUserCredentialsException;
// import org.auth.model.dto.LoginUserDto;
// import org.auth.user.dto.UserView;
// import org.auth.user.entity.User;
// import org.auth.util.UserValidator;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.security.crypto.password.PasswordEncoder;
//
// @ExtendWith(MockitoExtension.class)
// public class UserServiceImpTest {
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
//    @InjectMocks
//    private UserServiceImp userServiceImp;
//
//    private User user;
//    private UserView userView;
//    private LoginUserDto loginUserDto;
//
//    @BeforeEach
//    public void setUp() {
//        user = new User();
//        user.setEmail("email@abv.bg");
//        user.setUsername("username");
//        user.setPassword("password");
//        userView = new UserView(user);
//        loginUserDto = new LoginUserDto();
//        loginUserDto.setEmail("email@abv.bg");
//        loginUserDto.setPassword("password");
//    }
//
//    @Test
//    public void login_ValidInput_ReturnSuccessfulView() throws WrongUserCredentialsException {
//
//        when(validator.validateTheLoginCredentials(loginUserDto)).thenReturn(true);
//        when(userRepository.findByEmail(loginUserDto.getEmail())).thenReturn(Optional.of(user));
//        when(passwordEncoder.matches(loginUserDto.getPassword(),
// user.getPassword())).thenReturn(true);
//
//        UserView result = userServiceImp.login(loginUserDto);
//
//        assertEquals(userView, result);
//    }
//
//    @Test
//    public void login_InvalidInput_ThrowsException1(){
//        when(validator.validateTheLoginCredentials(loginUserDto)).thenReturn(false);
//
//        assertThrows(
//            WrongUserCredentialsException.class,
//             () -> userServiceImp.login(loginUserDto));
//    }
//
//    @Test
//    public void login_InvalidInput_ThrowsException2(){
//        when(validator.validateTheLoginCredentials(loginUserDto)).thenReturn(true);
//        when(userRepository.findByEmail(loginUserDto.getEmail())).thenReturn(Optional.empty());
//
//        assertThrows(
//            WrongUserCredentialsException.class,
//             () -> userServiceImp.login(loginUserDto));
//    }
//
// }

package org.auth.util;

import org.auth.model.dto.EditUserDto;
import org.auth.model.dto.LoginUserDto;
import org.auth.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.function.BiPredicate;
import java.util.function.Predicate;


@Component
public class UserValidator {

    public  Predicate<EditUserDto> validUsernameChange = (userDto -> userDto.getUsername() != null && userDto.getUsername().length() > 4 && !userDto.getUsername().isBlank());
    public  Predicate<EditUserDto> validKilogramsChange = (userDto -> userDto.getKilograms() != null);
    public  Predicate<EditUserDto> validWorkoutStateChange = (userDto -> userDto.getWorkoutState() != null);
    public  Predicate<EditUserDto> validHeightChange = (userDto -> userDto.getHeight() != null);
    public  Predicate<EditUserDto> validGenderChange = (userDto -> userDto.getGender() != null);
    public  Predicate<EditUserDto> validAgeChange = (userDto -> userDto.getAge() != null);
    public  Predicate<User> validateFullRegistration = (entity ->
            entity.getKilograms() != null &&
            entity.getWorkoutState() != null &&
            entity.getGender() != null &&
            entity.getHeight() != null &&
            entity.getAge() != null);

    public  Predicate<LoginUserDto> validateTheLoginCredentials = (loginUserDto ->
            loginUserDto.getEmail() != null && loginUserDto.getPassword() != null);

}

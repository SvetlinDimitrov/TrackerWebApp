package org.auth.infrastructure.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

  private static final PasswordValidator PASSWORD_VALIDATOR = createPasswordValidator();

  private static PasswordValidator createPasswordValidator() {
    var specialCharacterData =
        new CharacterData() {
          @Override
          public String getErrorCode() {
            return "INSUFFICIENT_SPECIAL";
          }

          @Override
          public String getCharacters() {
            return "!@#$%^&*()_+";
          }
        };

    return new PasswordValidator(
        Arrays.asList(
            new LengthRule(8, 30),
            new CharacterRule(EnglishCharacterData.UpperCase, 1),
            new CharacterRule(EnglishCharacterData.Digit, 1),
            new CharacterRule(specialCharacterData, 1),
            new WhitespaceRule()));
  }

  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {
    if (password == null) {
      return false;
    }

    RuleResult result = PASSWORD_VALIDATOR.validate(new PasswordData(password));

    if (result.isValid()) {
      return true;
    }

    context.disableDefaultConstraintViolation();

    PASSWORD_VALIDATOR
        .getMessages(result)
        .forEach(
            message ->
                context.buildConstraintViolationWithTemplate(message).addConstraintViolation());

    return false;
  }
}

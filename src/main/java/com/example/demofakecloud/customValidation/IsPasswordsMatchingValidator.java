package com.example.demofakecloud.customValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsPasswordsMatchingValidator
    implements ConstraintValidator<IsPasswordsMatching, Object> {
  @Override
  public void initialize(IsPasswordsMatching isPasswordsMatching) {}

  @Override
  public boolean isValid(Object userClass,
      ConstraintValidatorContext constraintValidatorContext) {
    return userClass instanceof PasswordConfirmable
        && ((PasswordConfirmable) userClass).getPassword()
            .equals(((PasswordConfirmable) userClass).getConfirmPassword());
  }
}



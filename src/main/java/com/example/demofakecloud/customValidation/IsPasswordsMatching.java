package com.example.demofakecloud.customValidation;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = IsPasswordsMatchingValidator.class)
public @interface IsPasswordsMatching {

  String message() default "Passwords not matching";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}

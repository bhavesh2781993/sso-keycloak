package com.ezbytz.keycloak.configs.validators;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@NoArgsConstructor(access = PRIVATE)
public class ValidationUtils {

    public static <T> Set<ConstraintViolation<T>> getViolations(final T object) {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        return validator.validate(object);
    }

    public static void validateFieldConstraints(final Object object) {
        final Set<ConstraintViolation<Object>> violations = getViolations(object);
        if (!CollectionUtils.isEmpty(violations)) {
            throw new ConstraintViolationException(violations);
        }
    }

}

package com.ezbytz.keycloak.configs.validators;

import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumSetValidator implements ConstraintValidator<ValidateEnumSet, Set<String>> {

    private static final String MESSAGE_PREFIX = "%s values must be any of ";
    private List<String> acceptedValues;
    private boolean isNullable;
    private String field;

    @Override
    public void initialize(final ValidateEnumSet constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.acceptedValues = Stream.of(constraintAnnotation.type().getEnumConstants()).map(Enum::name).toList();
        this.isNullable = constraintAnnotation.isNullable();
        this.field = constraintAnnotation.field();
    }

    @Override
    public boolean isValid(final Set<String> values, final ConstraintValidatorContext context) {
        final String messageTemplate = String.format(MESSAGE_PREFIX, field) + acceptedValues.toString();

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation();

        if (isNullable) {
            return CollectionUtils.isEmpty(values) || new HashSet<>(acceptedValues).containsAll(values);
        } else {
            return !CollectionUtils.isEmpty(values) && new HashSet<>(acceptedValues).containsAll(values);
        }
    }

}

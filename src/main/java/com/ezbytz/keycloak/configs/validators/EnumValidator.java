package com.ezbytz.keycloak.configs.validators;

import java.util.List;
import java.util.stream.Stream;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidateEnum, CharSequence> {

    private static final String MESSAGE_PREFIX = "%s values must be any of ";

    private List<String> acceptedValues;
    private boolean isNullable;
    private String field;

    @Override
    public void initialize(final ValidateEnum constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.acceptedValues = Stream.of(constraintAnnotation.type().getEnumConstants()).map(Enum::name).toList();
        this.isNullable = constraintAnnotation.isNullable();
        this.field = constraintAnnotation.field();
    }

    @Override
    public boolean isValid(final CharSequence value, final ConstraintValidatorContext context) {
        final String messageTemplate = String.format(MESSAGE_PREFIX, field) + acceptedValues.toString();

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation();

        if (isNullable) {
            return value == null || acceptedValues.contains(value.toString());
        } else {
            return value != null && acceptedValues.contains(value.toString());
        }
    }

}

package com.project.mall.demo.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 状态标记校验器
 */
public class FlagValidatorClass implements ConstraintValidator<FlagValidator, Integer> {
    private String[] values;

    @Override
    public void initialize(FlagValidator flagValidator) {
        this.values = flagValidator.value();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        for (String v : values) {
            if (v.equals(String.valueOf(value))) {
                return true;
            }
        }
        return false;
    }
}

package com.project.mall.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 鐘舵?佺害鏉熸牎楠屽櫒
 * Created by macro on 2018/4/26.
 */
public class FlagValidatorClass implements ConstraintValidator<FlagValidator,Integer> {
    private String[] values;
    @Override
    public void initialize(FlagValidator flagValidator) {
        this.values = flagValidator.value();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = false;
        if(value==null){
            //褰撶姸鎬佷负绌烘椂浣跨敤榛樿鍊?
            return true;
        }
        for(int i=0;i<values.length;i++){
            if(values[i].equals(String.valueOf(value))){
                isValid = true;
                break;
            }
        }
        return isValid;
    }
}

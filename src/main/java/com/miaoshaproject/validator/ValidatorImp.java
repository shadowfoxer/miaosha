package com.miaoshaproject.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * imp
 *
 * @author
 * @create 2019-07-14 下午6:31
 **/

@Component
public class ValidatorImp implements InitializingBean{

    private Validator validator;


    public ValidationResult validate(Object bean){
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<Object>> constraintViolationSet =  validator.validate(bean);

        if(constraintViolationSet.size()>0){
            result.setHasErrors(true);
            constraintViolationSet.forEach(constraintViolation->{
                String errMsg = constraintViolation.getMessage();
                String propertyName = constraintViolation.getPropertyPath().toString();

                result.getErrorMsgMap().put(propertyName,errMsg);
            });
        }

        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        this.validator =  Validation.buildDefaultValidatorFactory().getValidator();
    }
}

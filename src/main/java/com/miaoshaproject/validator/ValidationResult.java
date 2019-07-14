package com.miaoshaproject.validator;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装validator
 *
 * @author
 * @create 2019-07-14 下午6:21
 **/
public class ValidationResult {

    /**
     * 加盐结果是否错误
     */
    private boolean hasErrors = false;


    /**
     * 存放错误信息的map
     */
    private Map<String,String> errorMsgMap = new HashMap<>();


    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getErrorMsgMap() {
        return errorMsgMap;
    }

    public void setErrorMsgMap(Map<String, String> errorMsgMap) {
        this.errorMsgMap = errorMsgMap;
    }

    public String getErrorMsg(){
        return String.join(",",errorMsgMap.values());
    }
}

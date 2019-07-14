package com.miaoshaproject.controller;

import com.miaoshaproject.error.BusinessExcepition;
import com.miaoshaproject.error.EnBusinessError;
import com.miaoshaproject.response.CommonResponseType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 基类
 *
 * @author
 * @create 2019-04-30 下午11:10
 **/
public class BaseController {

    public static final String CONTENT_TYPE_FORMED="application/x-www-form-urlencoded";

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object exceptHandler(HttpServletRequest request, Exception ex){

        Map<String,Object> resultMap = new HashMap<>();

        if(ex instanceof BusinessExcepition){
            BusinessExcepition businessExcepition = (BusinessExcepition)ex;

            resultMap.put("errCode",businessExcepition.getErrCode());
            resultMap.put("errMsg",businessExcepition.getErrMsg());


        }else {
            resultMap.put("errCode", EnBusinessError.UNKNOWN_ERROR.getErrCode());
            resultMap.put("errMsg",EnBusinessError.UNKNOWN_ERROR.getErrMsg());

        }


        return CommonResponseType.create(resultMap,"fail");
    }
}

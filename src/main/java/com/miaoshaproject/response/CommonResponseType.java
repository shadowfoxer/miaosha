package com.miaoshaproject.response;

import java.util.Stack;

/**
 * 统一返回类型
 *
 * @author
 * @create 2019-04-30 下午8:31
 **/
public class CommonResponseType {

    private String status;
    private Object data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static CommonResponseType create(Object object){
        return CommonResponseType.create(object,"success");
    }

    public static CommonResponseType create(Object object,String status){
        CommonResponseType commonResponseType = new CommonResponseType();
        commonResponseType.status = status;
        commonResponseType.data = object;
        return commonResponseType;
    }
}

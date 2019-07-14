package com.miaoshaproject.error;

/**
 * 自定义excepition
 *
 * @author
 * @create 2019-04-30 下午10:30
 **/
public class BusinessExcepition extends Exception implements CommonError {


    private CommonError commonError;

    public BusinessExcepition( CommonError commonError) {
        super();
        this.commonError = commonError;
    }

    public BusinessExcepition(String message, CommonError commonError) {
        super();
        this.commonError = commonError;
        setErrMsg(message);
    }

    @Override
    public int getErrCode() {
        return commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        commonError.setErrMsg(errMsg);
        return this;
    }
}

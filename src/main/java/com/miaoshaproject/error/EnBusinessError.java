package com.miaoshaproject.error;

/**
 *
 * 自定义error
 *
 * @author
 * @create 2019-04-30 下午10:30
 *
 */
public enum EnBusinessError implements CommonError {
    USER_NOT_EXIT(10001,"用户不存在"),


    PARAMETER_VALIDATION_ERROR(20001,"参数不合法"),
    UNKNOWN_ERROR(20002,"未知错误")
    ;

    private int errCode;
    private String errMsg;

    EnBusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}

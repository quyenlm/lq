package com.garena.pay.android.data;

public class ValidationResult {
    public String errorMessage;
    public ResultCode resultCode;

    public enum ResultCode {
        SUCCESS,
        ERROR
    }

    public static ValidationResult createError(String errorMessage2) {
        ValidationResult result = new ValidationResult();
        result.errorMessage = errorMessage2;
        result.resultCode = ResultCode.ERROR;
        return result;
    }
}

package com.beetalk.sdk.data;

import android.text.TextUtils;
import com.garena.pay.android.GGErrorCode;
import com.garena.pay.android.GGPayRequest;
import java.io.Serializable;
import java.util.Map;

public class Result implements Serializable {
    private static final long serialVersionUID = 1;
    final Integer errorCode;
    final String errorMessage;
    private final Map<String, String> extras;
    final GGPayRequest request;
    final ResultCode resultCode;

    public Result(GGPayRequest request2, ResultCode resultCode2, String errorMessage2, Integer errorCode2, Map<String, String> extras2) {
        this.resultCode = resultCode2;
        this.errorMessage = errorMessage2;
        this.errorCode = errorCode2;
        this.request = request2;
        this.extras = extras2;
    }

    public static Result createSuccessResult(GGPayRequest request2, Map<String, String> extras2) {
        return new Result(request2, ResultCode.SUCCESS, (String) null, (Integer) null, extras2);
    }

    public static Result createCancelResult(GGPayRequest request2, String message) {
        return new Result(request2, ResultCode.CANCEL, message, (Integer) null, (Map<String, String>) null);
    }

    public static Result createErrorResult(GGPayRequest request2, GGErrorCode errorType, String errorDescription) {
        return createErrorResult(request2, errorType, errorDescription, (String) null);
    }

    public static Result createErrorResult(GGPayRequest request2, GGErrorCode errorType, String errorDescription, String errorCode2) {
        return new Result(request2, ResultCode.ERROR, TextUtils.join(": ", new String[]{errorType.getCode().toString(), errorDescription}), errorType.getCode(), (Map<String, String>) null);
    }

    public static boolean isError(ResultCode resultCode2) {
        return resultCode2 != ResultCode.SUCCESS;
    }

    public Map<String, String> getData() {
        return this.extras;
    }

    public ResultCode getResultCode() {
        return this.resultCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public Integer getErrorCode() {
        return this.errorCode;
    }

    public GGPayRequest getRequest() {
        return this.request;
    }

    public enum ResultCode {
        SUCCESS(0),
        CANCEL(1),
        ERROR(2);
        
        private final Integer value;

        private ResultCode(Integer value2) {
            this.value = value2;
        }

        public Integer getValue() {
            return this.value;
        }
    }
}

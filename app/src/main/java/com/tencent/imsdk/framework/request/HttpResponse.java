package com.tencent.imsdk.framework.request;

import com.amazonaws.services.s3.internal.Constants;
import com.tencent.imsdk.framework.consts.InnerErrorCode;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.SafeJSONObject;
import java.nio.charset.Charset;
import org.apache.http.Header;
import org.json.JSONException;

public abstract class HttpResponse {
    public static final String HTTP_RESP_PARAM_ERRCODE = "error_code";
    public static final String HTTP_RESP_PARAM_MSG = "msg";
    public static final String HTTP_RESP_PARAM_RET = "ret";
    public int error_code;
    public String msg;
    public int ret = -1;

    public abstract void parseJson(SafeJSONObject safeJSONObject);

    public HttpResponse() {
    }

    public HttpResponse(int ret2, int errCode, String msg2) {
        this.ret = ret2;
        this.error_code = errCode;
        this.msg = msg2;
    }

    public void parseSuccessResponse(int statusCode, Header[] headers, byte[] response) {
        if (response == null || response.length == 0) {
            this.ret = -1;
            this.error_code = InnerErrorCode.NET_RESP_PARAMS_NULL_ERROR;
            this.msg = "msg body is null, statusCode:" + statusCode;
            return;
        }
        String netContent = new String(response, Charset.forName("UTF-8"));
        try {
            parseJson(new SafeJSONObject(netContent));
        } catch (JSONException e) {
            IMLogger.d("JSONException json: " + netContent);
            this.ret = -1;
            this.error_code = InnerErrorCode.NET_RESP_PARAMS_PARSE_ERROR;
            this.msg = "JsonException:" + e.getMessage() + " statusCode:" + statusCode;
        }
    }

    public void parseFailureResponse(int statusCode, Header[] headers, byte[] errorResponse, Throwable error) {
        this.ret = -1;
        this.error_code = processNetErrorCode(statusCode, error);
        this.msg = processNetErrorMsg(statusCode, headers, errorResponse, error);
    }

    public void parseBaseJson(SafeJSONObject json) {
        try {
            if (json.getInt("ret") == 0) {
                this.ret = 1;
                this.error_code = 0;
            } else {
                this.ret = -1;
                this.error_code = InnerErrorCode.NET_DEFAULT_ERROR;
            }
            this.msg = json.getString("msg");
        } catch (JSONException e) {
            IMLogger.d("LogoutResponse JSONException : " + json.toString());
            this.ret = -1;
            this.error_code = InnerErrorCode.NET_RESP_PARAMS_PARSE_ERROR;
            this.msg = "LogoutResponse JsonException:" + e.getMessage();
        }
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ret=" + this.ret);
        builder.append("&error_code=" + this.error_code);
        builder.append("&msg=" + this.msg);
        return builder.toString();
    }

    private int processNetErrorCode(int statusCode, Throwable error) {
        if (statusCode == 0) {
            return InnerErrorCode.NET_REQ_THROWABLE_DEFAULT;
        }
        if (statusCode > 300) {
            return InnerErrorCode.NET_REQ_HTTP_STATUS_ERROR;
        }
        return InnerErrorCode.NET_DEFAULT_ERROR;
    }

    private String processNetErrorMsg(int statusCode, Header[] headers, byte[] errorResponse, Throwable error) {
        String message;
        String str;
        StringBuilder append = new StringBuilder().append("statusCode:").append(statusCode).append(", throwable:");
        if (error == null) {
            message = Constants.NULL_VERSION_ID;
        } else {
            message = error.getMessage();
        }
        StringBuilder append2 = append.append(message).append(",response:");
        if (errorResponse == null) {
            str = Constants.NULL_VERSION_ID;
        } else {
            str = new String(errorResponse, Charset.forName("UTF-8"));
        }
        return append2.append(str).toString();
    }
}

package com.tencent.imsdk.unity.webview;

import com.tencent.imsdk.IMErrorDef;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.tool.json.JsonProp;
import org.json.JSONException;
import org.json.JSONObject;

public class IMWebViewStatusResult extends IMResult {
    @JsonProp(name = "canGoBack")
    public boolean canGoBack;
    @JsonProp(name = "canGoForward")
    public boolean canGoForward;
    @JsonProp(name = "isOpen")
    public boolean isOpen;

    public IMWebViewStatusResult() {
    }

    public IMWebViewStatusResult(int errCode) {
        this.retCode = errCode;
        this.retMsg = IMErrorDef.getErrorString(errCode);
    }

    public IMWebViewStatusResult(int errCode, String message) {
        this.retCode = errCode;
        this.retMsg = IMErrorDef.getErrorString(errCode) + ":" + message;
    }

    public IMWebViewStatusResult(JSONObject object) throws JSONException {
        super(object);
    }

    public IMWebViewStatusResult(String json) throws JSONException {
        super(json);
    }
}

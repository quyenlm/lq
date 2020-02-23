package com.tencent.imsdk.unity.webview;

import com.tencent.imsdk.IMErrorDef;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.tool.json.JsonProp;
import org.json.JSONException;
import org.json.JSONObject;

public class IMWebViewTicketResult extends IMResult {
    @JsonProp(name = "ticket")
    public String ticket;

    public IMWebViewTicketResult() {
    }

    public IMWebViewTicketResult(int errCode) {
        this.retCode = errCode;
        this.retMsg = IMErrorDef.getErrorString(errCode);
    }

    public IMWebViewTicketResult(int errCode, String message) {
        this.retCode = errCode;
        this.retMsg = IMErrorDef.getErrorString(errCode) + ":" + message;
    }

    public IMWebViewTicketResult(JSONObject object) throws JSONException {
        super(object);
    }

    public IMWebViewTicketResult(String json) throws JSONException {
        super(json);
    }
}

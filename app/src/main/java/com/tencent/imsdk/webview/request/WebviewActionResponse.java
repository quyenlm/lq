package com.tencent.imsdk.webview.request;

import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.tool.json.JsonProp;
import org.json.JSONException;
import org.json.JSONObject;

public class WebviewActionResponse extends IMResult {
    @JsonProp(name = "stateCode")
    public int stateCode;

    public WebviewActionResponse() {
    }

    public WebviewActionResponse(int retCode, String retMsg) {
        super(retCode, retMsg);
    }

    public WebviewActionResponse(int errCode, int imsdkCode, int thirdCode) {
        super(errCode, imsdkCode, thirdCode);
    }

    public WebviewActionResponse(JSONObject object) throws JSONException {
        super(object);
    }

    public WebviewActionResponse(String json) throws JSONException {
        super(json);
    }
}

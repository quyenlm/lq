package com.tencent.imsdk.webview.request;

import com.tencent.imsdk.framework.request.HttpRequestParams;
import com.tencent.imsdk.framework.request.HttpResponse;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.SafeJSONObject;
import org.json.JSONException;
import org.json.JSONObject;

public class WebviewGetTicketResponse extends HttpResponse {
    private int code;
    private String desc;
    public String ticket;

    public void parseJson(SafeJSONObject json) {
        if (json.has("code")) {
            processPushMsgRsp(json);
        } else {
            IMLogger.w(json.toString());
        }
    }

    public void processPushMsgRsp(JSONObject json) {
        decodepushJson(json);
    }

    public void decodepushJson(JSONObject pushJson) {
        if (pushJson == null) {
            IMLogger.w("pushJson is null!");
            return;
        }
        try {
            this.desc = pushJson.getString("desc");
            this.code = pushJson.getInt("code");
            this.ticket = pushJson.getString(HttpRequestParams.WEBVIEW_TICKET);
            if (this.code != 1) {
                IMLogger.e("ret fail,desc is = " + pushJson.getString("desc") + "!");
            }
        } catch (JSONException e) {
            IMLogger.e("decodepushJson JSONException");
            e.printStackTrace();
        }
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("code=" + this.code + ",desc=" + this.desc + ",ticket=" + this.ticket);
        return builder.toString();
    }
}

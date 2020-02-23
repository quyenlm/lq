package com.garena.network;

import com.beetalk.sdk.helper.BBLogger;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONObjectParser implements Parser<AsyncHttpResponse, JSONObject> {
    public JSONObject parse(AsyncHttpResponse source) {
        try {
            return new JSONObject(source.getResponse());
        } catch (JSONException e) {
            BBLogger.e(e);
            return null;
        }
    }
}

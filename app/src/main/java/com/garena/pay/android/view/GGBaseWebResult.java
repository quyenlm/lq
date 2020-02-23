package com.garena.pay.android.view;

import org.json.JSONException;
import org.json.JSONObject;

public class GGBaseWebResult {
    protected static final String KEY_RESULT = "result";
    private final JSONObject object = new JSONObject();
    private final int resultCode;

    public GGBaseWebResult(int resultCode2) {
        this.resultCode = resultCode2;
        try {
            this.object.put("result", resultCode2);
        } catch (JSONException e) {
        }
    }

    public void addData(String key, Object value) {
        try {
            this.object.put(key, value);
        } catch (JSONException e) {
        }
    }

    public String toDataString() {
        return this.object.toString();
    }
}

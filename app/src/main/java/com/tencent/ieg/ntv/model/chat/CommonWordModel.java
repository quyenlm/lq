package com.tencent.ieg.ntv.model.chat;

import com.tencent.ieg.ntv.utils.Logger;
import com.tencent.midas.oversea.network.http.APNetworkManager2;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class CommonWordModel {
    private static final String TAG = CommonWordModel.class.getSimpleName();
    private final String TEXTKEY = APNetworkManager2.HTTP_KEY_OVERSEAINFO;
    private final String VIEWTEXTKEY = "cText";
    private String _text;

    public String get_text() {
        return this._text;
    }

    public void set_text(String _text2) {
        this._text = _text2;
    }

    public CommonWordModel() {
    }

    public CommonWordModel(String text) {
        this._text = text;
    }

    public CommonWordModel convertStringToModel(String fString) {
        return new CommonWordModel(fString);
    }

    public CommonWordModel convertJsonToModel(JSONObject fJson) {
        String value = "";
        try {
            value = fJson.getString(APNetworkManager2.HTTP_KEY_OVERSEAINFO);
        } catch (JSONException e) {
            Logger.w(TAG, (Exception) e);
        }
        if (!value.equals("")) {
            return new CommonWordModel(value);
        }
        return null;
    }

    public HashMap<String, Object> convertModelToMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("cText", get_text());
        return map;
    }
}

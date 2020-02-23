package com.tencent.ieg.ntv.ctrl.chat;

import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class ChatMsg {
    public static final String KEY_HEAD_IMG_URI = "headImg";
    public static final String KEY_MSG = "";
    public static final String KEY_NICK_NAME = "nickname";
    public String admin;
    public String headImgUri;
    public String msg;
    public String nickName;
    public String uid;

    public static ChatMsg parseMsg(String jsonStr) {
        try {
            JSONObject obj = new JSONObject(jsonStr);
            ChatMsg msg2 = new ChatMsg();
            msg2.nickName = obj.getString(KEY_NICK_NAME);
            msg2.headImgUri = obj.getString(KEY_HEAD_IMG_URI);
            msg2.msg = obj.getString("msg");
            msg2.uid = obj.getString(GGLiveConstants.PARAM.UID);
            msg2.admin = obj.getString("admin");
            return msg2;
        } catch (JSONException e) {
            return null;
        }
    }

    public String toSendString() {
        try {
            JSONObject obj = new JSONObject();
            obj.put(GGLiveConstants.PARAM.UID, this.uid);
            obj.put("msg", this.msg);
            return obj.toString();
        } catch (JSONException e) {
            return "";
        }
    }

    public Map<String, Object> toMapData() {
        Map<String, Object> map = new HashMap<>();
        map.put("nickName", this.nickName);
        map.put(KEY_HEAD_IMG_URI, this.headImgUri);
        map.put("msg", (Object) null);
        return map;
    }
}

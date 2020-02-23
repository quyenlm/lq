package com.beetalk.sdk.data;

import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.Helper;
import com.tencent.midas.oversea.api.UnityPayHelper;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class AuthToken implements Serializable {
    private static final long serialVersionUID = 1;
    private String authToken;
    private Integer expiryTimestamp;
    private Integer lastInspectTime = 0;
    private String openId;
    private String refreshToken;
    private TokenProvider tokenProvider;

    public AuthToken(String authToken2, TokenProvider tokenProvider2) {
        this.authToken = authToken2;
        this.tokenProvider = tokenProvider2;
    }

    public static String toJsonString(AuthToken token) {
        JSONObject object = new JSONObject();
        try {
            object.put("authToken", token.authToken);
            object.put("tokenProvider", token.tokenProvider.getValue());
            object.put("expiryTimestamp", token.expiryTimestamp);
            object.put("refreshToken", token.refreshToken != null ? token.refreshToken : "");
            object.put(UnityPayHelper.OPENID, token.openId != null ? token.openId : "");
            object.put("lastInspectTime", token.lastInspectTime);
        } catch (JSONException e) {
            BBLogger.e(e);
        }
        return object.toString();
    }

    public static AuthToken fromJson(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            AuthToken token = new AuthToken(jsonObject.optString("authToken"), TokenProvider.valueOf(jsonObject.optInt("tokenProvider")));
            token.setRefreshToken(jsonObject.optString("refreshToken"));
            token.setOpenId(jsonObject.optString(UnityPayHelper.OPENID));
            token.setExpiryTimestamp(jsonObject.optInt("expiryTimestamp"));
            token.setLastInspectTime(jsonObject.optInt("lastInspectTime"));
            return token;
        } catch (Exception e) {
            BBLogger.e(e);
            return null;
        }
    }

    public String getOpenId() {
        return this.openId;
    }

    public void setOpenId(String openId2) {
        this.openId = openId2;
    }

    public String getAuthToken() {
        return this.authToken;
    }

    public void setAuthToken(String authToken2) {
        this.authToken = authToken2;
    }

    public int getExpiryTimestamp() {
        return this.expiryTimestamp.intValue();
    }

    public void setExpiryTimestamp(int expiryTimestamp2) {
        this.expiryTimestamp = Integer.valueOf(expiryTimestamp2);
    }

    public int getLastInspectTime() {
        return this.lastInspectTime.intValue();
    }

    public void setLastInspectTime(int time) {
        this.lastInspectTime = Integer.valueOf(time);
    }

    public TokenProvider getTokenProvider() {
        return this.tokenProvider;
    }

    public void setTokenProvider(TokenProvider tokenProvider2) {
        this.tokenProvider = tokenProvider2;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public void setRefreshToken(String refreshToken2) {
        this.refreshToken = refreshToken2;
    }

    public boolean hasAllFields() {
        return !Helper.isNullOrEmpty(this.authToken) && !Helper.isNullOrEmpty(this.openId) && this.expiryTimestamp != null;
    }
}

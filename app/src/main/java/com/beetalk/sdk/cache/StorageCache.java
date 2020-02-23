package com.beetalk.sdk.cache;

import android.text.TextUtils;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.data.AuthToken;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveAuthToken;
import java.util.HashMap;
import java.util.Map;

public abstract class StorageCache {
    private static final String GARENA_LIVE_REFRESH_TOKEN = "com.garena.msdk.glive.refresh_token";
    private static final String GARENA_LIVE_REFRESH_TOKEN_EXPIRY_TIME = "com.garena.msdk.glive.refresh_token_expiry_time";
    private static final String GARENA_LIVE_REFRESH_TOKEN_ID = "com.garena.msdk.glive.refresh_token_id";
    private static final String GARENA_LIVE_SESSION_KEY = "com.garena.msdk.glive.session_key";
    private static final String GARENA_LIVE_SESSION_KEY_EXPIRY_TIME = "com.garena.msdk.glive.session_key_expiry_time";
    private static final String GARENA_LIVE_UID = "com.garena.msdk.glive.uid";
    public static final String GUEST_PASSWORD_KEY = "com.garena.msdk.guest_password";
    public static final String GUEST_PASSWORD_KEY_SANDBOX = "com.garena.msdk.guest_password_sandbox";
    public static final String GUEST_TOKEN_KEY = "com.garena.msdk.guest_token";
    public static final String GUEST_TOKEN_KEY_SANDBOX = "com.garena.msdk.guest_token_sandbox";
    public static final String GUEST_UID_KEY = "com.garena.msdk.guest_uid";
    public static final String GUEST_UID_KEY_SANDBOX = "com.garena.msdk.guest_uid_sandbox";
    public static final String TOKEN_KEY = "com.garena.msdk.token";

    public abstract void clear();

    public abstract Map<String, String> load();

    public abstract void remove(String str);

    public abstract void save(Map<String, String> map);

    public void clearSession() {
        remove(TOKEN_KEY);
        remove(GUEST_TOKEN_KEY);
        remove(GUEST_TOKEN_KEY_SANDBOX);
        clearGGLiveSession();
    }

    public void clearGGLiveSession() {
        remove(GARENA_LIVE_UID);
        remove(GARENA_LIVE_SESSION_KEY);
        remove(GARENA_LIVE_SESSION_KEY_EXPIRY_TIME);
        remove(GARENA_LIVE_REFRESH_TOKEN);
        remove(GARENA_LIVE_REFRESH_TOKEN_ID);
        remove(GARENA_LIVE_REFRESH_TOKEN_EXPIRY_TIME);
    }

    public String deleteGuestInfo() {
        String uid = getGuestUID();
        remove(getGuestUidKey());
        remove(getGuestPasswordKey());
        return uid;
    }

    public AuthToken getToken() {
        String jsonString = load().get(TOKEN_KEY);
        if (jsonString == null) {
            return null;
        }
        return AuthToken.fromJson(jsonString);
    }

    public void putToken(AuthToken token) {
        if (token == null) {
            remove(TOKEN_KEY);
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put(TOKEN_KEY, AuthToken.toJsonString(token));
        save(map);
    }

    private String getGuestTokenKey() {
        if (SDKConstants.getEnvironment() == SDKConstants.GGEnvironment.TEST) {
            return GUEST_TOKEN_KEY_SANDBOX;
        }
        if (SDKConstants.getEnvironment() == SDKConstants.GGEnvironment.PRODUCTION) {
            return GUEST_TOKEN_KEY;
        }
        return GUEST_TOKEN_KEY;
    }

    private String getGuestUidKey() {
        if (SDKConstants.getEnvironment() == SDKConstants.GGEnvironment.TEST) {
            return GUEST_UID_KEY_SANDBOX;
        }
        if (SDKConstants.getEnvironment() == SDKConstants.GGEnvironment.PRODUCTION) {
            return GUEST_UID_KEY;
        }
        return GUEST_UID_KEY;
    }

    private String getGuestPasswordKey() {
        if (SDKConstants.getEnvironment() == SDKConstants.GGEnvironment.TEST) {
            return GUEST_PASSWORD_KEY_SANDBOX;
        }
        if (SDKConstants.getEnvironment() == SDKConstants.GGEnvironment.PRODUCTION) {
            return GUEST_PASSWORD_KEY;
        }
        return GUEST_PASSWORD_KEY;
    }

    public AuthToken getGuestToken() {
        String jsonString = load().get(getGuestTokenKey());
        if (jsonString == null) {
            return null;
        }
        return AuthToken.fromJson(jsonString);
    }

    public void putGuestToken(AuthToken token) {
        Map<String, String> map = new HashMap<>();
        map.put(getGuestTokenKey(), AuthToken.toJsonString(token));
        save(map);
    }

    public String getGuestUID() {
        return load().get(getGuestUidKey());
    }

    public String getGuestPassword() {
        return load().get(getGuestPasswordKey());
    }

    public void putGuestUID(long uid) {
        Map<String, String> map = new HashMap<>();
        map.put(getGuestUidKey(), String.valueOf(uid));
        save(map);
    }

    public void putGuestPassword(String password) {
        Map<String, String> map = new HashMap<>();
        map.put(getGuestPasswordKey(), password);
        save(map);
    }

    public void removeGuestToken() {
        remove(getGuestTokenKey());
    }

    public GGLiveAuthToken getGGLiveAuthToken() {
        BBLogger.d("retrieving garena live auth token", new Object[0]);
        Map<String, String> map = load();
        String sessionKey = map.get(GARENA_LIVE_SESSION_KEY);
        String sessionKeyExpiryTimeStr = map.get(GARENA_LIVE_SESSION_KEY_EXPIRY_TIME);
        String uidStr = map.get(GARENA_LIVE_UID);
        String refreshTokenIdStr = map.get(GARENA_LIVE_REFRESH_TOKEN_ID);
        String refreshToken = map.get(GARENA_LIVE_REFRESH_TOKEN);
        String refreshTokenExpiryTimeStr = map.get(GARENA_LIVE_REFRESH_TOKEN_EXPIRY_TIME);
        if (TextUtils.isEmpty(sessionKey)) {
            BBLogger.d("garena live not logged in", new Object[0]);
            return null;
        }
        int sessionKeyExpiryTime = 0;
        if (!TextUtils.isEmpty(sessionKeyExpiryTimeStr)) {
            try {
                sessionKeyExpiryTime = Integer.parseInt(sessionKeyExpiryTimeStr);
            } catch (NumberFormatException e) {
                BBLogger.e("garena live session key expiry time malformatted: %s", sessionKeyExpiryTimeStr);
                BBLogger.e(e);
            }
        }
        long refreshTokenId = 0;
        if (!TextUtils.isEmpty(refreshTokenIdStr)) {
            try {
                refreshTokenId = Long.parseLong(refreshTokenIdStr);
            } catch (NumberFormatException e2) {
                BBLogger.e("garena live refresh token id malformatted: %s", refreshTokenIdStr);
                BBLogger.e(e2);
            }
        }
        int refreshTokenExpiryTime = 0;
        if (!TextUtils.isEmpty(refreshTokenExpiryTimeStr)) {
            try {
                refreshTokenExpiryTime = Integer.parseInt(refreshTokenExpiryTimeStr);
            } catch (NumberFormatException e3) {
                BBLogger.e("garena live refresh token expiry time malformatted: %s", refreshTokenExpiryTimeStr);
                BBLogger.e(e3);
            }
        }
        long uid = 0;
        if (!TextUtils.isEmpty(uidStr)) {
            try {
                uid = Long.parseLong(uidStr);
            } catch (NumberFormatException e4) {
                BBLogger.e(e4);
            }
        }
        int now = (int) (System.currentTimeMillis() / 1000);
        if (sessionKeyExpiryTime <= now) {
            if (refreshTokenId <= 0 || TextUtils.isEmpty(refreshToken)) {
                BBLogger.d("garena live session expired but no refresh token, required to relogin", new Object[0]);
                return null;
            } else if (refreshTokenExpiryTime <= now) {
                BBLogger.d("garena live session expired but refresh token also expired, required to relogin", new Object[0]);
                return null;
            } else if (uid <= 0) {
                BBLogger.d("garena live session expired but no uid, required to relogin", new Object[0]);
                return null;
            }
        }
        return new GGLiveAuthToken(sessionKey, sessionKeyExpiryTime, refreshTokenId, refreshToken, refreshTokenExpiryTime, uid);
    }

    public void putGGLiveSessionTokenInfo(String sessionKey, int expiryTime) {
        Map<String, String> map = new HashMap<>(2);
        map.put(GARENA_LIVE_SESSION_KEY, sessionKey);
        map.put(GARENA_LIVE_SESSION_KEY_EXPIRY_TIME, String.valueOf(expiryTime));
        save(map);
    }

    public void putGGLiveRefreshTokenInfo(long tokenId, String token, int expiryTime) {
        Map<String, String> map = new HashMap<>(3);
        map.put(GARENA_LIVE_REFRESH_TOKEN_ID, String.valueOf(tokenId));
        map.put(GARENA_LIVE_REFRESH_TOKEN, token);
        map.put(GARENA_LIVE_REFRESH_TOKEN_EXPIRY_TIME, String.valueOf(expiryTime));
        save(map);
    }

    public void putGGLiveUid(long uid) {
        Map<String, String> map = new HashMap<>(1);
        map.put(GARENA_LIVE_UID, String.valueOf(uid));
        save(map);
    }
}

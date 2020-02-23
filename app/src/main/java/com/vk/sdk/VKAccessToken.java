package com.vk.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.vk.sdk.util.VKStringJoiner;
import com.vk.sdk.util.VKUtil;
import java.util.HashMap;
import java.util.Map;

public class VKAccessToken {
    public static final String ACCESS_TOKEN = "access_token";
    public static final String CREATED = "created";
    public static final String EMAIL = "email";
    public static final String EXPIRES_IN = "expires_in";
    public static final String HTTPS_REQUIRED = "https_required";
    public static final String SCOPE = "scope";
    public static final String SECRET = "secret";
    public static final String SUCCESS = "success";
    public static final String USER_ID = "user_id";
    private static final String VK_SDK_ACCESS_TOKEN_PREF_KEY = "VK_SDK_ACCESS_TOKEN_PLEASE_DONT_TOUCH";
    private static volatile VKAccessToken sCurrentToken;
    public String accessToken = null;
    public long created = 0;
    public String email = null;
    public int expiresIn = 0;
    public boolean httpsRequired = false;
    private Map<String, Boolean> scope = null;
    public String secret = null;
    public String userId = null;

    public void saveTokenToFile(String filePath) {
        VKUtil.stringToFile(filePath, serialize());
    }

    public void saveTokenToSharedPreferences(Context ctx, String tokenKey) {
        if (ctx != null) {
            SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(ctx).edit();
            edit.putString(tokenKey, serialize());
            edit.apply();
        }
    }

    public static void removeTokenAtKey(Context ctx, String tokenKey) {
        if (ctx != null) {
            SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(ctx).edit();
            edit.remove(tokenKey);
            edit.apply();
        }
    }

    private VKAccessToken() {
    }

    /* access modifiers changed from: protected */
    public Map<String, String> tokenParams() {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", this.accessToken);
        params.put("expires_in", "" + this.expiresIn);
        params.put("user_id", this.userId);
        params.put(CREATED, "" + this.created);
        if (this.scope != null) {
            params.put("scope", TextUtils.join(",", this.scope.keySet()));
        }
        if (this.secret != null) {
            params.put(SECRET, this.secret);
        }
        if (this.httpsRequired) {
            params.put(HTTPS_REQUIRED, "1");
        }
        if (this.email != null) {
            params.put("email", this.email);
        }
        return params;
    }

    /* access modifiers changed from: protected */
    public String serialize() {
        return VKStringJoiner.joinParams(tokenParams());
    }

    public static VKAccessToken tokenFromUrlString(String urlString) {
        if (urlString == null) {
            return null;
        }
        return tokenFromParameters(VKUtil.explodeQueryString(urlString));
    }

    public static VKAccessToken tokenFromParameters(@Nullable Map<String, String> parameters) {
        if (parameters == null || parameters.size() == 0) {
            return null;
        }
        VKAccessToken token = new VKAccessToken();
        try {
            token.accessToken = parameters.get("access_token");
            token.userId = parameters.get("user_id");
            token.secret = parameters.get(SECRET);
            token.email = parameters.get("email");
            token.httpsRequired = false;
            if (parameters.get("expires_in") != null) {
                token.expiresIn = Integer.parseInt(parameters.get("expires_in"));
            }
            String scope2 = parameters.get("scope");
            if (scope2 != null) {
                HashMap<String, Boolean> scopeMap = new HashMap<>();
                for (String s : scope2.split(",")) {
                    scopeMap.put(s, true);
                }
                token.scope = scopeMap;
            }
            if (parameters.containsKey(HTTPS_REQUIRED)) {
                token.httpsRequired = parameters.get(HTTPS_REQUIRED).equals("1");
            } else if (token.secret == null) {
                token.httpsRequired = true;
            }
            if (parameters.containsKey(CREATED)) {
                token.created = Long.parseLong(parameters.get(CREATED));
            } else {
                token.created = System.currentTimeMillis();
            }
            if (token.accessToken == null) {
                token = null;
            }
            return token;
        } catch (Exception e) {
            return null;
        }
    }

    public static VKAccessToken tokenFromSharedPreferences(Context ctx, String key) {
        return tokenFromUrlString(PreferenceManager.getDefaultSharedPreferences(ctx).getString(key, (String) null));
    }

    public static VKAccessToken tokenFromFile(String filePath) {
        try {
            return tokenFromUrlString(VKUtil.fileToString(filePath));
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isExpired() {
        return this.expiresIn > 0 && ((long) (this.expiresIn * 1000)) + this.created < System.currentTimeMillis();
    }

    public static VKAccessToken currentToken() {
        if (sCurrentToken == null) {
            synchronized (VKAccessToken.class) {
                if (sCurrentToken == null) {
                    sCurrentToken = tokenFromSharedPreferences(VKUIHelper.getApplicationContext(), VK_SDK_ACCESS_TOKEN_PREF_KEY);
                }
            }
        }
        return sCurrentToken;
    }

    static VKAccessToken replaceToken(@NonNull Context ctx, @Nullable VKAccessToken newToken) {
        VKAccessToken oldToken = sCurrentToken;
        sCurrentToken = newToken;
        if (sCurrentToken != null) {
            sCurrentToken.save();
        } else {
            removeTokenAtKey(ctx, VK_SDK_ACCESS_TOKEN_PREF_KEY);
        }
        return oldToken;
    }

    public void save() {
        saveTokenToSharedPreferences(VKUIHelper.getApplicationContext(), VK_SDK_ACCESS_TOKEN_PREF_KEY);
    }

    public boolean hasScope(String... scopes) {
        for (String scopeStr : scopes) {
            if (this.scope.get(scopeStr) == null) {
                return false;
            }
        }
        return true;
    }

    public VKAccessToken copyWithToken(@NonNull VKAccessToken token) {
        Map<String, String> newTokenParams = tokenParams();
        newTokenParams.putAll(token.tokenParams());
        return tokenFromParameters(newTokenParams);
    }
}

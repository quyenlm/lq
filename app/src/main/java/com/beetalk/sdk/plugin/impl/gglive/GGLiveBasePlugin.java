package com.beetalk.sdk.plugin.impl.gglive;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.cache.SharedPrefStorage;
import com.beetalk.sdk.cache.StorageCache;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.networking.HttpRequestTask;
import com.beetalk.sdk.plugin.GGPlugin;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveAuthToken;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.beetalk.sdk.plugin.impl.gglive.network.GGLiveCallback;
import org.json.JSONException;
import org.json.JSONObject;

abstract class GGLiveBasePlugin<S, T> extends GGPlugin<S, T> {
    /* access modifiers changed from: protected */
    public abstract void onError(Activity activity, S s, String str);

    /* access modifiers changed from: protected */
    public abstract void onTokenReady(Activity activity, S s, String str);

    GGLiveBasePlugin() {
    }

    /* access modifiers changed from: protected */
    public final void executeAction(Activity activity, S data) {
        StorageCache cache = new SharedPrefStorage(activity);
        GGLiveAuthToken token = cache.getGGLiveAuthToken();
        if (token == null) {
            BBLogger.d("garena live user not logged in", new Object[0]);
            onError(activity, data, GGLiveConstants.ERROR_CODE.ERROR_INVALID_SESSION);
            return;
        }
        GGLiveAuthToken.TokenData sessionToken = token.sessionToken;
        if (!sessionToken.isExpired()) {
            BBLogger.d("garena live token not expired", new Object[0]);
            onTokenReady(activity, data, sessionToken.token);
            return;
        }
        refreshSessionToken(activity, data, cache, token);
    }

    private void refreshSessionToken(Activity activity, S data, StorageCache cache, GGLiveAuthToken token) {
        BBLogger.d("refreshing garena live session token", new Object[0]);
        final GGLiveAuthToken.TokenData refreshToken = token.refreshToken;
        if (refreshToken.isExpired()) {
            BBLogger.d("failed to exchange garena live session token: refresh token already expired", new Object[0]);
            onError(activity, data, GGLiveConstants.ERROR_CODE.ERROR_SESSION_EXPIRED);
        } else if (token.uid <= 0 || refreshToken.id <= 0 || TextUtils.isEmpty(refreshToken.token)) {
            BBLogger.d("failed to exchange garena live session token: parameters incomplete", new Object[0]);
            onError(activity, data, GGLiveConstants.ERROR_CODE.ERROR_SESSION_EXPIRED);
        } else {
            JSONObject req = new JSONObject();
            try {
                req.put(GGLiveConstants.PARAM.TOKEN_ID, refreshToken.id);
                req.put("token", refreshToken.token);
                req.put(GGLiveConstants.PARAM.UID, token.uid);
                final StorageCache storageCache = cache;
                final Activity activity2 = activity;
                final S s = data;
                new HttpRequestTask(req, (HttpRequestTask.BaseCallback) new GGLiveCallback() {
                    /* access modifiers changed from: protected */
                    public void onSuccess(JSONObject replyData) {
                        String sessionKey = replyData != null ? replyData.optString(GGLiveConstants.PARAM.SESSION_TOKEN) : null;
                        if (!TextUtils.isEmpty(sessionKey)) {
                            GGLiveBasePlugin.this.saveSessionToken(storageCache, sessionKey);
                            if (refreshToken.expiryTime - ((int) (System.currentTimeMillis() / 1000)) < 604800) {
                                GGLiveBasePlugin.this.requestRefreshToken(storageCache, sessionKey);
                            }
                            GGLiveBasePlugin.this.onTokenReady(activity2, s, sessionKey);
                            return;
                        }
                        BBLogger.d("invalid session key returned", new Object[0]);
                        GGLiveBasePlugin.this.onError(activity2, s, GGLiveConstants.ERROR_CODE.ERROR_SESSION_EXPIRED);
                    }

                    /* access modifiers changed from: protected */
                    public void onError(String error) {
                        BBLogger.d("failed to exchange session token: %s", error);
                        GGLiveBasePlugin.this.onError(activity2, s, error);
                    }

                    public void onTimeout() {
                        BBLogger.d("failed to exchange session token: timeout", new Object[0]);
                        GGLiveBasePlugin.this.onError(activity2, s, GGLiveConstants.ERROR_CODE.ERROR_SESSION_EXPIRED);
                    }
                }).executeParallel(SDKConstants.getGGLiveGetSessionTokenUrl());
            } catch (JSONException e) {
                BBLogger.e(e);
                BBLogger.d("failed to exchange session token", new Object[0]);
                onError(activity, data, GGLiveConstants.ERROR_CODE.ERROR_SESSION_EXPIRED);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void saveSessionToken(StorageCache cache, String sessionKey) {
        int expire = ((int) (System.currentTimeMillis() / 1000)) + 3000;
        cache.putGGLiveSessionTokenInfo(sessionKey, expire);
        BBLogger.d("garena live session token saved: %s %d", sessionKey, Integer.valueOf(expire));
    }

    /* access modifiers changed from: package-private */
    public void requestRefreshToken(final StorageCache cache, String sessionKey) {
        BBLogger.d("requesting garena live refresh token", new Object[0]);
        JSONObject req = new JSONObject();
        try {
            req.put(GGLiveConstants.PARAM.SESSION_TOKEN, sessionKey);
            new HttpRequestTask(req, (HttpRequestTask.BaseCallback) new GGLiveCallback() {
                /* access modifiers changed from: protected */
                public void onSuccess(JSONObject replyData) {
                    if (replyData != null) {
                        long tokenId = replyData.optLong(GGLiveConstants.PARAM.TOKEN_ID);
                        String token = replyData.optString("token");
                        int expiryTime = replyData.optInt(GGLiveConstants.PARAM.EXPIRY_TIME);
                        cache.putGGLiveRefreshTokenInfo(tokenId, token, expiryTime);
                        BBLogger.d("saved garena live refresh token: %d %s %d", Long.valueOf(tokenId), token, Integer.valueOf(expiryTime));
                        return;
                    }
                    BBLogger.d("failed to request garena live refresh token: empty reply", new Object[0]);
                }

                /* access modifiers changed from: protected */
                public void onError(String error) {
                    BBLogger.d("unable to request garena live refresh token: %s", error);
                }

                public void onTimeout() {
                    BBLogger.d("unable to request garena live refresh token: timeout", new Object[0]);
                }
            }).executeParallel(SDKConstants.getGGLiveGetRefreshTokenUrl());
        } catch (JSONException e) {
            BBLogger.e(e);
            BBLogger.d("unable to request garena live refresh token", new Object[0]);
        }
    }

    public Integer getRequestCode() {
        return null;
    }

    public boolean onActivityResult(Activity ggPluginActivity, int resultCode, Intent data) {
        return false;
    }

    public boolean embedInActivity() {
        return false;
    }
}

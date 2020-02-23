package com.facebook;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.share.internal.ShareConstants;
import com.tencent.qqgamemi.util.TimeUtils;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONObject;

public final class AccessTokenManager {
    public static final String ACTION_CURRENT_ACCESS_TOKEN_CHANGED = "com.facebook.sdk.ACTION_CURRENT_ACCESS_TOKEN_CHANGED";
    public static final String EXTRA_NEW_ACCESS_TOKEN = "com.facebook.sdk.EXTRA_NEW_ACCESS_TOKEN";
    public static final String EXTRA_OLD_ACCESS_TOKEN = "com.facebook.sdk.EXTRA_OLD_ACCESS_TOKEN";
    private static final String ME_PERMISSIONS_GRAPH_PATH = "me/permissions";
    public static final String SHARED_PREFERENCES_NAME = "com.facebook.AccessTokenManager.SharedPreferences";
    public static final String TAG = "AccessTokenManager";
    private static final String TOKEN_EXTEND_GRAPH_PATH = "oauth/access_token";
    private static final int TOKEN_EXTEND_RETRY_SECONDS = 3600;
    private static final int TOKEN_EXTEND_THRESHOLD_SECONDS = 86400;
    private static volatile AccessTokenManager instance;
    private final AccessTokenCache accessTokenCache;
    private AccessToken currentAccessToken;
    private Date lastAttemptedTokenExtendDate = new Date(0);
    private final LocalBroadcastManager localBroadcastManager;
    /* access modifiers changed from: private */
    public AtomicBoolean tokenRefreshInProgress = new AtomicBoolean(false);

    AccessTokenManager(LocalBroadcastManager localBroadcastManager2, AccessTokenCache accessTokenCache2) {
        Validate.notNull(localBroadcastManager2, "localBroadcastManager");
        Validate.notNull(accessTokenCache2, "accessTokenCache");
        this.localBroadcastManager = localBroadcastManager2;
        this.accessTokenCache = accessTokenCache2;
    }

    static AccessTokenManager getInstance() {
        if (instance == null) {
            synchronized (AccessTokenManager.class) {
                if (instance == null) {
                    instance = new AccessTokenManager(LocalBroadcastManager.getInstance(FacebookSdk.getApplicationContext()), new AccessTokenCache());
                }
            }
        }
        return instance;
    }

    /* access modifiers changed from: package-private */
    public AccessToken getCurrentAccessToken() {
        return this.currentAccessToken;
    }

    /* access modifiers changed from: package-private */
    public boolean loadCurrentAccessToken() {
        AccessToken accessToken = this.accessTokenCache.load();
        if (accessToken == null) {
            return false;
        }
        setCurrentAccessToken(accessToken, false);
        return true;
    }

    /* access modifiers changed from: package-private */
    public void setCurrentAccessToken(AccessToken currentAccessToken2) {
        setCurrentAccessToken(currentAccessToken2, true);
    }

    private void setCurrentAccessToken(AccessToken currentAccessToken2, boolean saveToCache) {
        AccessToken oldAccessToken = this.currentAccessToken;
        this.currentAccessToken = currentAccessToken2;
        this.tokenRefreshInProgress.set(false);
        this.lastAttemptedTokenExtendDate = new Date(0);
        if (saveToCache) {
            if (currentAccessToken2 != null) {
                this.accessTokenCache.save(currentAccessToken2);
            } else {
                this.accessTokenCache.clear();
                Utility.clearFacebookCookies(FacebookSdk.getApplicationContext());
            }
        }
        if (!Utility.areObjectsEqual(oldAccessToken, currentAccessToken2)) {
            sendCurrentAccessTokenChangedBroadcastIntent(oldAccessToken, currentAccessToken2);
            setTokenExpirationBroadcastAlarm();
        }
    }

    /* access modifiers changed from: package-private */
    public void currentAccessTokenChanged() {
        sendCurrentAccessTokenChangedBroadcastIntent(this.currentAccessToken, this.currentAccessToken);
    }

    private void sendCurrentAccessTokenChangedBroadcastIntent(AccessToken oldAccessToken, AccessToken currentAccessToken2) {
        Intent intent = new Intent(FacebookSdk.getApplicationContext(), CurrentAccessTokenExpirationBroadcastReceiver.class);
        intent.setAction(ACTION_CURRENT_ACCESS_TOKEN_CHANGED);
        intent.putExtra(EXTRA_OLD_ACCESS_TOKEN, oldAccessToken);
        intent.putExtra(EXTRA_NEW_ACCESS_TOKEN, currentAccessToken2);
        this.localBroadcastManager.sendBroadcast(intent);
    }

    private void setTokenExpirationBroadcastAlarm() {
        Context context = FacebookSdk.getApplicationContext();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        if (AccessToken.isCurrentAccessTokenActive() && accessToken.getExpires() != null && alarmManager != null) {
            Intent intent = new Intent(context, CurrentAccessTokenExpirationBroadcastReceiver.class);
            intent.setAction(ACTION_CURRENT_ACCESS_TOKEN_CHANGED);
            alarmManager.set(1, accessToken.getExpires().getTime(), PendingIntent.getBroadcast(context, 0, intent, 0));
        }
    }

    /* access modifiers changed from: package-private */
    public void extendAccessTokenIfNeeded() {
        if (shouldExtendAccessToken()) {
            refreshCurrentAccessToken((AccessToken.AccessTokenRefreshCallback) null);
        }
    }

    private boolean shouldExtendAccessToken() {
        if (this.currentAccessToken == null) {
            return false;
        }
        Long now = Long.valueOf(new Date().getTime());
        if (!this.currentAccessToken.getSource().canExtendToken() || now.longValue() - this.lastAttemptedTokenExtendDate.getTime() <= 3600000 || now.longValue() - this.currentAccessToken.getLastRefresh().getTime() <= TimeUtils.MILLIS_IN_DAY) {
            return false;
        }
        return true;
    }

    private static GraphRequest createGrantedPermissionsRequest(AccessToken accessToken, GraphRequest.Callback callback) {
        return new GraphRequest(accessToken, ME_PERMISSIONS_GRAPH_PATH, new Bundle(), HttpMethod.GET, callback);
    }

    private static GraphRequest createExtendAccessTokenRequest(AccessToken accessToken, GraphRequest.Callback callback) {
        Bundle parameters = new Bundle();
        parameters.putString("grant_type", "fb_extend_sso_token");
        return new GraphRequest(accessToken, TOKEN_EXTEND_GRAPH_PATH, parameters, HttpMethod.GET, callback);
    }

    private static class RefreshResult {
        public String accessToken;
        public int expiresAt;

        private RefreshResult() {
        }
    }

    /* access modifiers changed from: package-private */
    public void refreshCurrentAccessToken(final AccessToken.AccessTokenRefreshCallback callback) {
        if (Looper.getMainLooper().equals(Looper.myLooper())) {
            refreshCurrentAccessTokenImpl(callback);
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    AccessTokenManager.this.refreshCurrentAccessTokenImpl(callback);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void refreshCurrentAccessTokenImpl(AccessToken.AccessTokenRefreshCallback callback) {
        final AccessToken accessToken = this.currentAccessToken;
        if (accessToken == null) {
            if (callback != null) {
                callback.OnTokenRefreshFailed(new FacebookException("No current access token to refresh"));
            }
        } else if (this.tokenRefreshInProgress.compareAndSet(false, true)) {
            this.lastAttemptedTokenExtendDate = new Date();
            final Set<String> permissions = new HashSet<>();
            final Set<String> declinedPermissions = new HashSet<>();
            final AtomicBoolean permissionsCallSucceeded = new AtomicBoolean(false);
            final RefreshResult refreshResult = new RefreshResult();
            GraphRequestBatch batch = new GraphRequestBatch(createGrantedPermissionsRequest(accessToken, new GraphRequest.Callback() {
                public void onCompleted(GraphResponse response) {
                    JSONArray permissionsArray;
                    JSONObject result = response.getJSONObject();
                    if (result != null && (permissionsArray = result.optJSONArray(ShareConstants.WEB_DIALOG_PARAM_DATA)) != null) {
                        permissionsCallSucceeded.set(true);
                        for (int i = 0; i < permissionsArray.length(); i++) {
                            JSONObject permissionEntry = permissionsArray.optJSONObject(i);
                            if (permissionEntry != null) {
                                String permission = permissionEntry.optString("permission");
                                String status = permissionEntry.optString("status");
                                if (!Utility.isNullOrEmpty(permission) && !Utility.isNullOrEmpty(status)) {
                                    String status2 = status.toLowerCase(Locale.US);
                                    if (status2.equals("granted")) {
                                        permissions.add(permission);
                                    } else if (status2.equals("declined")) {
                                        declinedPermissions.add(permission);
                                    } else {
                                        Log.w(AccessTokenManager.TAG, "Unexpected status: " + status2);
                                    }
                                }
                            }
                        }
                    }
                }
            }), createExtendAccessTokenRequest(accessToken, new GraphRequest.Callback() {
                public void onCompleted(GraphResponse response) {
                    JSONObject data = response.getJSONObject();
                    if (data != null) {
                        refreshResult.accessToken = data.optString("access_token");
                        refreshResult.expiresAt = data.optInt("expires_at");
                    }
                }
            }));
            final AccessToken.AccessTokenRefreshCallback accessTokenRefreshCallback = callback;
            batch.addCallback(new GraphRequestBatch.Callback() {
                public void onBatchCompleted(GraphRequestBatch batch) {
                    AccessToken newAccessToken;
                    String token;
                    Set<String> permissions;
                    Set<String> declinedPermissions;
                    Date expires;
                    try {
                        if (AccessTokenManager.getInstance().getCurrentAccessToken() == null || AccessTokenManager.getInstance().getCurrentAccessToken().getUserId() != accessToken.getUserId()) {
                            if (accessTokenRefreshCallback != null) {
                                accessTokenRefreshCallback.OnTokenRefreshFailed(new FacebookException("No current access token to refresh"));
                            }
                            AccessTokenManager.this.tokenRefreshInProgress.set(false);
                            if (!(accessTokenRefreshCallback == null || 0 == 0)) {
                                accessTokenRefreshCallback.OnTokenRefreshed((AccessToken) null);
                            }
                        } else if (!permissionsCallSucceeded.get() && refreshResult.accessToken == null && refreshResult.expiresAt == 0) {
                            if (accessTokenRefreshCallback != null) {
                                accessTokenRefreshCallback.OnTokenRefreshFailed(new FacebookException("Failed to refresh access token"));
                            }
                            AccessTokenManager.this.tokenRefreshInProgress.set(false);
                            if (!(accessTokenRefreshCallback == null || 0 == 0)) {
                                accessTokenRefreshCallback.OnTokenRefreshed((AccessToken) null);
                            }
                        } else {
                            if (refreshResult.accessToken != null) {
                                token = refreshResult.accessToken;
                            } else {
                                token = accessToken.getToken();
                            }
                            String applicationId = accessToken.getApplicationId();
                            String userId = accessToken.getUserId();
                            if (permissionsCallSucceeded.get()) {
                                permissions = permissions;
                            } else {
                                permissions = accessToken.getPermissions();
                            }
                            if (permissionsCallSucceeded.get()) {
                                declinedPermissions = declinedPermissions;
                            } else {
                                declinedPermissions = accessToken.getDeclinedPermissions();
                            }
                            AccessTokenSource source = accessToken.getSource();
                            if (refreshResult.expiresAt != 0) {
                                expires = new Date(((long) refreshResult.expiresAt) * 1000);
                            } else {
                                expires = accessToken.getExpires();
                            }
                            newAccessToken = new AccessToken(token, applicationId, userId, permissions, declinedPermissions, source, expires, new Date());
                            try {
                                AccessTokenManager.getInstance().setCurrentAccessToken(newAccessToken);
                                AccessTokenManager.this.tokenRefreshInProgress.set(false);
                                if (accessTokenRefreshCallback != null && newAccessToken != null) {
                                    accessTokenRefreshCallback.OnTokenRefreshed(newAccessToken);
                                }
                            } catch (Throwable th) {
                                th = th;
                                AccessTokenManager.this.tokenRefreshInProgress.set(false);
                                if (!(accessTokenRefreshCallback == null || newAccessToken == null)) {
                                    accessTokenRefreshCallback.OnTokenRefreshed(newAccessToken);
                                }
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        newAccessToken = null;
                        AccessTokenManager.this.tokenRefreshInProgress.set(false);
                        accessTokenRefreshCallback.OnTokenRefreshed(newAccessToken);
                        throw th;
                    }
                }
            });
            batch.executeAsync();
        } else if (callback != null) {
            callback.OnTokenRefreshFailed(new FacebookException("Refresh already in progress"));
        }
    }
}

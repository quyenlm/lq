package com.beetalk.sdk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import com.beetalk.sdk.GGLoginSession;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.cache.SharedPrefStorage;
import com.beetalk.sdk.cache.StorageCache;
import com.beetalk.sdk.data.AuthToken;
import com.beetalk.sdk.data.TokenProvider;
import com.beetalk.sdk.facebook.FBClient;
import com.beetalk.sdk.helper.Helper;

public abstract class GarenaAutoLoginActivity extends Activity implements GGLoginSession.SessionCallback {
    public static final String EXTRA_AUTHTOKEN = "authtoken";
    public static final String EXTRA_EXPIRY = "expiry";
    public static final String EXTRA_LOGIN_STATUS = "login_status";
    public static final String EXTRA_OPENID = "openid";
    public static final String EXTRA_REFRESHTOKEN = "refreshtoken";

    /* access modifiers changed from: protected */
    public abstract String getAppKey();

    /* access modifiers changed from: protected */
    public abstract Class<? extends Activity> getFailActivityClass();

    /* access modifiers changed from: protected */
    public abstract Class<? extends Activity> getSuccessActivityClass();

    /* access modifiers changed from: protected */
    public int getLayoutResourceId() {
        return -1;
    }

    /* access modifiers changed from: protected */
    public int getStayPeriod() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public SDKConstants.GGEnvironment getEnvironment() {
        return SDKConstants.GGEnvironment.PRODUCTION;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        GGPlatform.initialize((Activity) this);
        super.onCreate(savedInstanceState);
        if (getLayoutResourceId() > 0) {
            setContentView(getLayoutResourceId());
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                GarenaAutoLoginActivity.this.autoLogin();
            }
        }, (long) getStayPeriod());
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    /* access modifiers changed from: private */
    public void autoLogin() {
        StorageCache cache = new SharedPrefStorage(this);
        AuthToken cachedAutoToken = cache.getToken();
        if (cachedAutoToken == null || cachedAutoToken.getTokenProvider() != TokenProvider.FACEBOOK) {
            Intent intent = getIntent();
            String authTokenString = intent.getStringExtra(EXTRA_AUTHTOKEN);
            String refreshToken = intent.getStringExtra(EXTRA_REFRESHTOKEN);
            AuthToken authToken = new AuthToken(authTokenString, TokenProvider.GARENA_WEB_ANDROID);
            authToken.setRefreshToken(refreshToken);
            authToken.setOpenId(intent.getStringExtra("openid"));
            authToken.setExpiryTimestamp(intent.getIntExtra(EXTRA_EXPIRY, Helper.getTimeNow() + 2592000));
            if (TextUtils.isEmpty(authTokenString)) {
                gotoHomePage();
                return;
            }
            GGLoginSession.clearSession();
            cache.putToken(authToken);
            GGPlatform.GGSetEnvironment(getEnvironment());
            GGPlatform.initialize(new GGLoginSession.Builder(this).setApplicationId(Helper.getMetaDataAppId(this)).setApplicationKey(getAppKey()).setAuthMode(AuthMode.LEGACY_ENABLED).setSessionProvider(GGLoginSession.SessionProvider.GARENA).build());
            GGPlatform.login(this, this);
            return;
        }
        cache.clearSession();
        FBClient.clearSession(this);
        GGPlatform.initialize(new GGLoginSession.Builder(this).setApplicationId(Helper.getMetaDataAppId(this)).setApplicationKey(getAppKey()).setAuthMode(AuthMode.LEGACY_ENABLED).setSessionProvider(GGLoginSession.SessionProvider.FACEBOOK).setRequestCode(SDKConstants.DEFAULT_ACTIVITY_FACEBOOK_REQUEST_CODE.intValue()).build());
        GGPlatform.login(this, this);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        GGPlatform.handleActivityResult(this, requestCode, resultCode, data);
    }

    public void onSessionProcessed(GGLoginSession session, Exception exception) {
        if (exception != null) {
            gotoHomePage();
        } else if (session.getSessionStatus() == SessionStatus.TOKEN_AVAILABLE) {
            GGLoginSession.getCurrentSession().setApplicationKey(getAppKey());
            Intent intent = new Intent(this, getSuccessActivityClass());
            intent.putExtra(EXTRA_LOGIN_STATUS, 0);
            intent.putExtra(EXTRA_AUTHTOKEN, session.getTokenValue().getAuthToken());
            intent.putExtra("openid", session.getTokenValue().getOpenId());
            startActivity(intent);
            finish();
        } else if (session.getSessionStatus() == SessionStatus.CLOSED_WITH_ERROR || session.getSessionStatus() == SessionStatus.CLOSED) {
            gotoHomePage();
        } else if (session.getSessionStatus() == SessionStatus.INSPECTION_WITH_ERROR) {
            gotoHomePage();
        }
    }

    private void gotoHomePage() {
        new SharedPrefStorage(this).clearSession();
        FBClient.clearSession(this);
        GGLoginSession.setCurrentSession((GGLoginSession) null);
        Intent intent2 = new Intent(this, getFailActivityClass());
        intent2.putExtra(EXTRA_LOGIN_STATUS, 1);
        startActivity(intent2);
        finish();
    }
}

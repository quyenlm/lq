package com.beetalk.sdk;

import android.app.Activity;
import android.content.Intent;
import com.beetalk.sdk.AuthClient;
import com.beetalk.sdk.GGLoginSession;
import com.beetalk.sdk.helper.BBLogger;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class AuthRequest implements Serializable {
    private static final long serialVersionUID = 1;
    /* access modifiers changed from: private */
    public final ActivityLauncher activityLauncher;
    private String applicationId;
    private String applicationKey;
    private final String authId;
    private boolean isLegacy;
    private GGLoginSession mSession;
    private ArrayList<String> permissions;
    private String redirectURI;
    private int requestCode;
    private GGLoginSession.SessionCallback statusCallback;

    public AuthRequest(final Activity activity, GGLoginSession.SessionCallback statusCallback2, int requestCode2, boolean isLegacy2, String applicationId2) {
        this.authId = UUID.randomUUID().toString();
        this.requestCode = SDKConstants.DEFAULT_ACTIVITY_LAUNCH_REQUEST_CODE.intValue();
        this.isLegacy = false;
        this.requestCode = requestCode2;
        this.statusCallback = statusCallback2;
        this.isLegacy = isLegacy2;
        this.applicationId = applicationId2;
        this.activityLauncher = new ActivityLauncher() {
            public void startActivityForResult(Intent intent, int requestCode) {
                BBLogger.d(activity.getClass().getName(), new Object[0]);
                activity.startActivityForResult(intent, requestCode);
            }

            public Activity getContext() {
                return activity;
            }
        };
    }

    public AuthRequest(Activity activity, GGLoginSession.SessionCallback callback) {
        this(activity, callback, SDKConstants.DEFAULT_ACTIVITY_LAUNCH_REQUEST_CODE.intValue(), false, "");
    }

    public GGLoginSession getSession() {
        return this.mSession;
    }

    public void setSession(GGLoginSession session) {
        this.mSession = session;
    }

    public ActivityLauncher getActivityLauncher() {
        return this.activityLauncher;
    }

    public String getAuthId() {
        return this.authId;
    }

    public int getRequestCode() {
        return this.requestCode;
    }

    public void setRequestCode(int requestCode2) {
        this.requestCode = requestCode2;
    }

    public GGLoginSession.SessionCallback getStatusCallback() {
        return this.statusCallback;
    }

    public void setStatusCallback(GGLoginSession.SessionCallback statusCallback2) {
        this.statusCallback = statusCallback2;
    }

    public boolean isLegacy() {
        return this.isLegacy;
    }

    public void setLegacy(boolean isLegacy2) {
        this.isLegacy = isLegacy2;
    }

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String applicationId2) {
        this.applicationId = applicationId2;
    }

    public String getApplicationKey() {
        return this.applicationKey;
    }

    public void setApplicationKey(String applicationKey2) {
        this.applicationKey = applicationKey2;
    }

    public void setRedirectURI(String uri) {
        this.redirectURI = uri;
    }

    public AuthClient.AuthClientRequest getAuthClientRequest() {
        AuthClient.AuthClientRequest request = new AuthClient.AuthClientRequest(new ActivityLauncher() {
            public void startActivityForResult(Intent intent, int requestCode) {
                AuthRequest.this.activityLauncher.startActivityForResult(intent, requestCode);
            }

            public Activity getContext() {
                return AuthRequest.this.activityLauncher.getContext();
            }
        }, this.authId, this.requestCode, this.isLegacy, this.applicationId, this.applicationKey);
        request.setRedirectURI(this.redirectURI);
        request.setPermissions(this.permissions);
        request.setAuthToken(this.mSession.getTokenValue());
        request.setSessionProvider(this.mSession.getSessionProvider());
        return request;
    }
}

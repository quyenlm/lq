package com.beetalk.sdk.plugin.impl.fb;

import android.app.Activity;
import android.content.Intent;
import com.beetalk.sdk.exception.InvalidOperationException;
import com.beetalk.sdk.plugin.GGPlugin;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public abstract class BaseFBPlugin<S, T> extends GGPlugin<S, T> {
    protected CallbackManager callbackManager;
    protected S mData;

    public abstract void onError(Exception exc, Activity activity);

    public abstract void onSuccess(Activity activity);

    /* access modifiers changed from: protected */
    public void executeAction(Activity activity, S data) {
        this.mData = data;
        this.callbackManager = CallbackManager.Factory.create();
        startAuth(activity);
    }

    private boolean startAuth(final Activity activity) {
        AccessToken token = AccessToken.getCurrentAccessToken();
        if (token == null || token.isExpired()) {
            LoginManager.getInstance().registerCallback(this.callbackManager, new FacebookCallback<LoginResult>() {
                public void onSuccess(LoginResult loginResult) {
                    if (loginResult.getAccessToken() != null) {
                        BaseFBPlugin.this.onSuccess(activity);
                    }
                }

                public void onCancel() {
                    BaseFBPlugin.this.onError(new InvalidOperationException("Login Cancelled"), activity);
                }

                public void onError(FacebookException error) {
                    if (error == null) {
                        BaseFBPlugin.this.onError(new InvalidOperationException("Login Failed for some reason"), activity);
                        return;
                    }
                    BaseFBPlugin.this.onError(error, activity);
                }
            });
            LoginManager.getInstance().logInWithReadPermissions(activity, (Collection<String>) Arrays.asList(new String[]{"public_profile", "user_friends", "email"}));
        } else {
            onSuccess(activity);
        }
        return true;
    }

    private boolean hasPermission(Set<String> permissions, String permission) {
        return permissions.contains(permission);
    }

    /* access modifiers changed from: protected */
    public boolean needsPublishPermission() {
        return false;
    }

    public boolean onActivityResult(Activity ggPluginActivity, int resultCode, Intent data) {
        return false;
    }

    public boolean onFBActivityResult(int requestCode, int resultCode, Intent data) {
        return this.callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

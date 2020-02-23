package com.beetalk.sdk;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import com.beetalk.sdk.AuthClient;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.data.AuthToken;
import com.beetalk.sdk.data.TokenProvider;
import com.beetalk.sdk.exception.InvalidOperationException;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.networking.SimpleNetworkClient;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.garena.pay.android.GGErrorCode;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class FBAuthRequestHandler extends AuthRequestHandler {
    private final CallbackManager callbackManager = CallbackManager.Factory.create();

    public FBAuthRequestHandler(AuthClient client) {
        super(client);
        final Activity activity = client.getActivityLauncher().getContext();
        LoginManager.getInstance().registerCallback(this.callbackManager, new FacebookCallback<LoginResult>() {
            public void onSuccess(LoginResult loginResult) {
                if (loginResult.getAccessToken() != null) {
                    FBAuthRequestHandler.this.onSuccess();
                }
            }

            public void onCancel() {
                FBAuthRequestHandler.this.onError(new InvalidOperationException(SDKConstants.USER_CANCELLED), activity);
            }

            public void onError(FacebookException error) {
                if (error == null) {
                    FBAuthRequestHandler.this.onError(new InvalidOperationException("Login Failed for some reason"), activity);
                } else {
                    FBAuthRequestHandler.this.onError(error, activity);
                }
                Object[] objArr = new Object[1];
                objArr[0] = error != null ? error.getLocalizedMessage() : "exception is null";
                BBLogger.e("FB login fail CLOSED_LOGIN_FAILED, exception: ", objArr);
            }
        });
    }

    /* access modifiers changed from: package-private */
    public boolean startAuth(AuthClient.AuthClientRequest request) {
        BBLogger.d("start FB Auth", new Object[0]);
        if (request == null) {
            return false;
        }
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null || accessToken.isExpired()) {
            Activity activity = this.client.getActivityLauncher().getContext();
            LoginManager.getInstance().logInWithReadPermissions(activity, (Collection<String>) Arrays.asList(new String[]{"public_profile", "user_friends", "email"}));
            return true;
        }
        onSuccess();
        return true;
    }

    /* access modifiers changed from: private */
    public void onResult(AuthClient.Result result) {
        GGLoginSession session;
        if (!(result == null || result.token == null || (session = GGLoginSession.getCurrentSession()) == null)) {
            session.getCache().putToken(result.token);
        }
        this.client.notifyListener(result);
    }

    /* access modifiers changed from: private */
    public AuthClient.Result exchangeTokenFromGOP(String userToken, AuthClient.AuthClientRequest pendingRequest) {
        HashMap<String, String> data = new HashMap<>(3);
        data.put("grant_type", "authorization_code");
        data.put("facebook_access_token", userToken);
        data.put("redirect_uri", SDKConstants.REDIRECT_URL_PREFIX + pendingRequest.getApplicationId() + "://auth/");
        data.put("source", GGPlatform.getChannelSource().toString());
        data.put("client_id", pendingRequest.getApplicationId());
        data.put("client_secret", pendingRequest.getApplicationKey());
        JSONObject result = SimpleNetworkClient.getInstance().makePostRequest(SDKConstants.getAuthFacebookTokenExchangeUrl(), (Map<String, String>) data);
        BBLogger.d("FB login exchange token, result: ", result);
        AuthClient.Result outcome = null;
        int errorCode = GGErrorCode.UNKNOWN_ERROR.getCode().intValue();
        if (result == null) {
            errorCode = GGErrorCode.NETWORK_EXCEPTION.getCode().intValue();
        } else if (result.has("open_id")) {
            try {
                String openId = result.getString("open_id");
                String accessToken = result.getString("access_token");
                int expireTime = result.getInt(GGLiveConstants.PARAM.EXPIRY_TIME);
                AuthToken authToken = new AuthToken(accessToken, TokenProvider.FACEBOOK);
                if (result.optInt("platform") == 1) {
                    authToken.setTokenProvider(TokenProvider.GARENA_NATIVE_ANDROID);
                }
                authToken.setExpiryTimestamp(expireTime);
                outcome = AuthClient.Result.createTokenResult(pendingRequest, authToken, openId);
            } catch (JSONException e) {
                BBLogger.e(e);
            }
        } else {
            String error = result.optString("error");
            if (SDKConstants.ErrorFlags.ERROR_USER_BAN.equals(error)) {
                errorCode = GGErrorCode.ERROR_USER_BANNED.getCode().intValue();
            } else if (SDKConstants.ErrorFlags.INVALID_TOKEN.equals(error)) {
                AccessToken.setCurrentAccessToken((AccessToken) null);
                errorCode = GGErrorCode.ACCESS_TOKEN_INVALID_GRANT.getCode().intValue();
            } else {
                errorCode = GGErrorCode.ACCESS_TOKEN_EXCHANGE_FAILED.getCode().intValue();
            }
        }
        if (outcome == null) {
            return AuthClient.Result.createErrorResult(pendingRequest, errorCode);
        }
        return outcome;
    }

    /* access modifiers changed from: private */
    public void onSuccess() {
        BBLogger.i("Starting Token Exchange for Facebook", new Object[0]);
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            new RequestSessionTokenTask().execute(new String[]{accessToken.getToken()});
            return;
        }
        this.client.notifyListener(AuthClient.Result.createErrorResult(this.client.getPendingRequest(), GGErrorCode.SESSION_NOT_INITIALIZED.getCode().intValue()));
    }

    /* access modifiers changed from: private */
    public void onError(Exception exception, Activity activity) {
        AuthClient.Result result;
        BBLogger.e("FB login failed, exception: ", exception.getLocalizedMessage());
        AuthClient.AuthClientRequest mPendingRequest = this.client.getPendingRequest();
        if (!(exception instanceof InvalidOperationException)) {
            result = AuthClient.Result.createErrorResult(mPendingRequest, GGErrorCode.UNKNOWN_ERROR.getCode().intValue());
        } else if (SDKConstants.USER_CANCELLED.equalsIgnoreCase(exception.getMessage())) {
            result = AuthClient.Result.createErrorResult(mPendingRequest, GGErrorCode.USER_CANCELLED.getCode().intValue());
        } else {
            result = AuthClient.Result.createErrorResult(mPendingRequest, GGErrorCode.LOGIN_FAILED.getCode().intValue());
        }
        this.client.notifyListener(result);
    }

    private boolean hasPermission(List<String> permissions, String permission) {
        for (String knownPerm : permissions) {
            if (knownPerm.equals(permission)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean onActivityResult(int requestCode, int resultCode, Intent data, AuthClient.AuthClientRequest pendingRequest) {
        if (this.callbackManager != null) {
            this.callbackManager.onActivityResult(requestCode, resultCode, data);
            return true;
        }
        onResult(AuthClient.Result.createErrorResult(pendingRequest, GGErrorCode.SESSION_NOT_INITIALIZED.getCode().intValue()));
        return true;
    }

    private class RequestSessionTokenTask extends AsyncTask<String, Void, AuthClient.Result> {
        private RequestSessionTokenTask() {
        }

        /* access modifiers changed from: protected */
        public AuthClient.Result doInBackground(String... tokens) {
            return FBAuthRequestHandler.this.exchangeTokenFromGOP(tokens[0], FBAuthRequestHandler.this.client.getPendingRequest());
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(AuthClient.Result result) {
            FBAuthRequestHandler.this.onResult(result);
        }
    }
}

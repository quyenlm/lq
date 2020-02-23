package com.beetalk.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.beetalk.sdk.AuthClient;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.data.AuthToken;
import com.beetalk.sdk.data.TokenProvider;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.networking.SimpleNetworkClient;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.garena.pay.android.GGErrorCode;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

class GoogleAuthRequestHandler extends AuthRequestHandler {
    private static final int RC_SIGN_IN = 29554;
    private GoogleSignInClient mGoogleSignInClient;

    GoogleAuthRequestHandler(AuthClient client) {
        super(client);
    }

    /* access modifiers changed from: package-private */
    public boolean startAuth(AuthClient.AuthClientRequest request) {
        BBLogger.d("start Google Auth", new Object[0]);
        if (request == null) {
            return false;
        }
        this.mGoogleSignInClient = getGoogleSignInClient(this.client.getContext());
        if (this.mGoogleSignInClient == null) {
            return false;
        }
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this.client.getContext());
        String idToken = null;
        if (account != null && !account.isExpired()) {
            idToken = account.getIdToken();
            if (idToken != null) {
                BBLogger.d("already login", new Object[0]);
                onSuccess(idToken);
            } else {
                BBLogger.d("got token failed", new Object[0]);
            }
        }
        if (idToken == null) {
            BBLogger.d("starting google sign in page", new Object[0]);
            this.client.getActivityLauncher().startActivityForResult(this.mGoogleSignInClient.getSignInIntent(), RC_SIGN_IN);
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean onActivityResult(int requestCode, int resultCode, Intent data, AuthClient.AuthClientRequest pendingRequest) {
        if (requestCode != RC_SIGN_IN) {
            return false;
        }
        if (resultCode == -1) {
            try {
                GoogleSignInAccount account = GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException.class);
                if (account == null || account.isExpired()) {
                    BBLogger.e("Google login failed", new Object[0]);
                } else {
                    BBLogger.d("Google login success", new Object[0]);
                    onSuccess(account.getIdToken());
                }
            } catch (ApiException e) {
                BBLogger.w("signInResult:failed code=" + e.getStatusCode(), new Object[0]);
                if (e.getStatusCode() == 12501) {
                    onUserCanceled();
                }
            }
        } else {
            onUserCanceled();
        }
        return true;
    }

    private void onSuccess(String idToken) {
        BBLogger.i("Starting Token Exchange for Google", new Object[0]);
        if (!TextUtils.isEmpty(idToken)) {
            new RequestSessionTokenTask().execute(new String[]{idToken});
            return;
        }
        this.client.notifyListener(AuthClient.Result.createErrorResult(this.client.getPendingRequest(), GGErrorCode.SESSION_NOT_INITIALIZED.getCode().intValue()));
    }

    private void onUserCanceled() {
        this.client.notifyListener(AuthClient.Result.createErrorResult(this.client.getPendingRequest(), GGErrorCode.USER_CANCELLED.getCode().intValue()));
    }

    /* access modifiers changed from: private */
    public AuthClient.Result exchangeTokenFromGOP(String idToken, AuthClient.AuthClientRequest pendingRequest) {
        HashMap<String, String> data = new HashMap<>();
        data.put("google_access_token", idToken);
        data.put("source", GGPlatform.getChannelSource().toString());
        data.put("client_id", pendingRequest.getApplicationId());
        data.put("client_secret", pendingRequest.getApplicationKey());
        JSONObject result = SimpleNetworkClient.getInstance().makePostRequest(SDKConstants.getAuthGoogleTokenExchangeUrl(), (Map<String, String>) data);
        BBLogger.d("Google login exchange token, result: ", result);
        AuthClient.Result outcome = null;
        int errorCode = GGErrorCode.UNKNOWN_ERROR.getCode().intValue();
        if (result == null) {
            errorCode = GGErrorCode.NETWORK_EXCEPTION.getCode().intValue();
        } else if (result.has("open_id")) {
            try {
                String openId = result.getString("open_id");
                String accessToken = result.getString("access_token");
                int expireTime = result.getInt(GGLiveConstants.PARAM.EXPIRY_TIME);
                AuthToken authToken = new AuthToken(accessToken, TokenProvider.GOOGLE);
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
                this.mGoogleSignInClient.signOut();
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

    private class RequestSessionTokenTask extends AsyncTask<String, Void, AuthClient.Result> {
        private RequestSessionTokenTask() {
        }

        /* access modifiers changed from: protected */
        public AuthClient.Result doInBackground(String... tokens) {
            return GoogleAuthRequestHandler.this.exchangeTokenFromGOP(tokens[0], GoogleAuthRequestHandler.this.client.getPendingRequest());
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(AuthClient.Result result) {
            GoogleAuthRequestHandler.this.onResult(result);
        }
    }

    /* access modifiers changed from: private */
    public void onResult(AuthClient.Result result) {
        GGLoginSession session;
        if (!(result == null || result.token == null || (session = GGLoginSession.getCurrentSession()) == null)) {
            session.getCache().putToken(result.token);
        }
        this.client.notifyListener(result);
    }

    private static String getGoogleClientId(Context context) {
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (info.metaData != null) {
                return info.metaData.getString(SDKConstants.CLIENT_ID_PROPERTY_GOOGLE, "");
            }
        } catch (Exception e) {
            BBLogger.e(e);
        }
        return "";
    }

    public static GoogleSignInClient getGoogleSignInClient(Context context) {
        String clientId = getGoogleClientId(context);
        if (!TextUtils.isEmpty(clientId)) {
            return GoogleSignIn.getClient(context, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(clientId).build());
        }
        BBLogger.e("Google client ID is not configured.", new Object[0]);
        return null;
    }
}

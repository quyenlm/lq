package com.beetalk.sdk;

import android.os.AsyncTask;
import com.beetalk.sdk.AuthClient;
import com.beetalk.sdk.data.AuthToken;
import com.beetalk.sdk.data.TokenProvider;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.StringUtils;
import com.beetalk.sdk.networking.SimpleNetworkClient;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.garena.pay.android.GGErrorCode;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class RefreshTokenHandler extends AuthRequestHandler {
    public RefreshTokenHandler(AuthClient authClient) {
        super(authClient);
    }

    /* access modifiers changed from: package-private */
    public boolean startAuth(AuthClient.AuthClientRequest request) {
        new RequestRefreshTokenTask().execute(new String[]{request.getAuthToken().getRefreshToken()});
        return true;
    }

    /* access modifiers changed from: private */
    public void onResult(AuthClient.Result result) {
        if (result != null) {
            this.client.notifyListener(result);
        } else {
            this.client.tryHandler();
        }
    }

    /* access modifiers changed from: private */
    public AuthClient.Result requestUserToken(String userToken, AuthClient.AuthClientRequest pendingRequest) {
        HashMap<String, String> data = new HashMap<>(3);
        data.put("grant_type", "refresh_token");
        data.put("refresh_token", userToken);
        JSONObject result = SimpleNetworkClient.getInstance().makePostRequest(SDKConstants.getAuthRefreshTokenUrl(), (Map<String, String>) data);
        AuthClient.Result outcome = null;
        if (result != null && result.has("open_id")) {
            try {
                String openId = result.getString("open_id");
                String accessToken = result.getString("access_token");
                String refreshToken = result.getString("refresh_token");
                int expireTime = result.getInt(GGLiveConstants.PARAM.EXPIRY_TIME);
                AuthToken authToken = new AuthToken(accessToken, TokenProvider.GARENA_WEB_ANDROID);
                authToken.setRefreshToken(refreshToken);
                authToken.setExpiryTimestamp(expireTime);
                authToken.setOpenId(openId);
                outcome = AuthClient.Result.createTokenResult(pendingRequest, authToken, openId);
            } catch (JSONException e) {
                BBLogger.e(e);
            }
        }
        if (outcome == null) {
            return AuthClient.Result.createErrorResult(pendingRequest, GGErrorCode.ACCESS_TOKEN_EXCHANGE_FAILED.getCode().intValue());
        }
        return outcome;
    }

    private class RequestRefreshTokenTask extends AsyncTask<String, Void, AuthClient.Result> {
        private RequestRefreshTokenTask() {
        }

        /* access modifiers changed from: protected */
        public AuthClient.Result doInBackground(String... tokens) {
            String token = tokens[0];
            if (token == null || StringUtils.isEmpty(token)) {
                return AuthClient.Result.createErrorResult(RefreshTokenHandler.this.client.getPendingRequest(), GGErrorCode.REFRESH_TOKEN_FAILED.getCode().intValue());
            }
            return RefreshTokenHandler.this.requestUserToken(token, RefreshTokenHandler.this.client.getPendingRequest());
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(AuthClient.Result result) {
            RefreshTokenHandler.this.onResult(result);
        }
    }
}

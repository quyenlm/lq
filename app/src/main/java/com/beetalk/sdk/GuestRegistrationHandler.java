package com.beetalk.sdk;

import com.beetalk.sdk.AuthClient;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.cache.PersistentCache;
import com.beetalk.sdk.data.AuthToken;
import com.beetalk.sdk.data.GuestAccountRegInfo;
import com.beetalk.sdk.data.TokenProvider;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.Security;
import com.beetalk.sdk.helper.StringUtils;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.beetalk.sdk.request.GuestAccountRegRequest;
import com.beetalk.sdk.request.GuestGrandTokenRequest;
import com.beetalk.sdk.request.ResponseType;
import com.garena.network.AsyncHttpClient;
import com.garena.network.AsyncHttpResponse;
import com.garena.pay.android.GGErrorCode;
import org.json.JSONException;
import org.json.JSONObject;

public class GuestRegistrationHandler extends AuthRequestHandler {
    public GuestRegistrationHandler(AuthClient authClient) {
        super(authClient);
    }

    /* access modifiers changed from: package-private */
    public boolean startAuth(AuthClient.AuthClientRequest request) {
        String uidStr = PersistentCache.getInstance().getGuestUID();
        if (StringUtils.isEmpty(uidStr)) {
            final GuestAccountRegInfo regInfo = new GuestAccountRegInfo();
            regInfo.password = Security.generate64charpassword();
            regInfo.nickname = "";
            regInfo.gender = 0;
            regInfo.app_id = request.getApplicationId();
            regInfo.source = GGLoginSession.getCurrentSession().getSourceId().intValue();
            new GuestAccountRegRequest(regInfo).send(new AsyncHttpClient.JSONObjectCallback() {
                public void onCompleted(Exception e, AsyncHttpResponse source, JSONObject result) {
                    if (e != null || GuestRegistrationHandler.this.responseHasError(result)) {
                        GuestRegistrationHandler.this.client.notifyListener((AuthClient.Result) null);
                        return;
                    }
                    try {
                        long uid = result.getLong(GGLiveConstants.PARAM.UID);
                        PersistentCache.getInstance().putGuestUID(uid);
                        PersistentCache.getInstance().putGuestPassword(regInfo.password);
                        GuestRegistrationHandler.this.grantTokenForGuest(uid, regInfo.password);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            });
        } else {
            grantTokenForGuest(Long.valueOf(uidStr).longValue(), PersistentCache.getInstance().getGuestPassword());
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void grantTokenForGuest(long uid, String password) {
        new GuestGrandTokenRequest(uid, password, ResponseType.TOKEN, this.client.getPendingRequest().getApplicationId()).send(new AsyncHttpClient.JSONObjectCallback() {
            public void onCompleted(Exception e, AsyncHttpResponse source, JSONObject result) {
                if (e != null) {
                    BBLogger.e(e);
                    GuestRegistrationHandler.this.client.notifyListener((AuthClient.Result) null);
                    return;
                }
                GuestRegistrationHandler.this.client.notifyListener(GuestRegistrationHandler.this.getAuthResult(result));
            }
        });
    }

    /* access modifiers changed from: private */
    public AuthClient.Result getAuthResult(JSONObject result) {
        AuthClient.Result outcome = null;
        int errorCode = GGErrorCode.UNKNOWN_ERROR.getCode().intValue();
        if (result == null) {
            try {
                errorCode = GGErrorCode.NETWORK_EXCEPTION.getCode().intValue();
            } catch (JSONException e) {
                BBLogger.e(e);
            }
        } else if (result.has("open_id")) {
            String openId = result.getString("open_id");
            String accessToken = result.getString("access_token");
            String refreshToken = result.getString("refresh_token");
            int expireTime = result.getInt(GGLiveConstants.PARAM.EXPIRY_TIME);
            AuthToken authToken = new AuthToken(accessToken, TokenProvider.GUEST);
            authToken.setRefreshToken(refreshToken);
            authToken.setExpiryTimestamp(expireTime);
            outcome = AuthClient.Result.createTokenResult(this.client.getPendingRequest(), authToken, openId);
        } else if (result.has("error")) {
            String error = result.getString("error");
            errorCode = SDKConstants.ErrorFlags.INVALID_TOKEN.equals(error) ? GGErrorCode.ACCESS_TOKEN_INVALID_GRANT.getCode().intValue() : SDKConstants.ErrorFlags.ERROR_USER_BAN.equals(error) ? GGErrorCode.ERROR_USER_BANNED.getCode().intValue() : GGErrorCode.ERROR_GUEST_LOGIN.getCode().intValue();
        } else {
            errorCode = GGErrorCode.ACCESS_TOKEN_EXCHANGE_FAILED.getCode().intValue();
        }
        if (outcome == null) {
            return AuthClient.Result.createErrorResult(this.client.getPendingRequest(), errorCode);
        }
        return outcome;
    }

    /* access modifiers changed from: private */
    public boolean responseHasError(JSONObject result) {
        return result == null || result.has("error");
    }
}

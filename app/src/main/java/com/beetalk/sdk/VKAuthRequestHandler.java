package com.beetalk.sdk;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import bolts.Continuation;
import bolts.Task;
import com.beetalk.sdk.AuthClient;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.data.AuthToken;
import com.beetalk.sdk.data.TokenProvider;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.networking.SimpleNetworkClient;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.beetalk.sdk.vk.VKAuthHelper;
import com.beetalk.sdk.vk.VKUtils;
import com.facebook.AccessToken;
import com.garena.msdk.R;
import com.garena.pay.android.GGErrorCode;
import com.tencent.tp.a.h;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKError;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import org.json.JSONException;
import org.json.JSONObject;

public class VKAuthRequestHandler extends AuthRequestHandler implements VKAuthHelper.OnAuthResultListener {
    private final VKAuthHelper mAuthHelper;

    public VKAuthRequestHandler(AuthClient client) {
        super(client);
        this.mAuthHelper = new VKAuthHelper(client.getActivityLauncher().getContext());
        this.mAuthHelper.setOnAuthResultListener(this);
    }

    /* access modifiers changed from: package-private */
    public boolean startAuth(AuthClient.AuthClientRequest request) {
        BBLogger.d("start VK Auth", new Object[0]);
        if (request == null) {
            return false;
        }
        if (VKUtils.lookUpAppId(this.client.getContext()) == -1) {
            throw new IllegalStateException("Forget add <integer name=\"com_vk_sdk_AppId\">your_app_id</integer> in your values dir?");
        }
        this.mAuthHelper.startLogin();
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean onActivityResult(int requestCode, int resultCode, Intent data, AuthClient.AuthClientRequest pendingRequest) {
        return this.mAuthHelper.onActivityResult(resultCode, data);
    }

    public void onLoggedIn() {
        VKAccessToken token = VKAccessToken.currentToken();
        if (token == null || TextUtils.isEmpty(token.accessToken)) {
            this.client.notifyListener(AuthClient.Result.createErrorResult(this.client.getPendingRequest(), "error", "vk access token is null", GGErrorCode.LOGIN_FAILED.getCode().intValue()));
            return;
        }
        BBLogger.d("vk auth success: %s", token.accessToken);
        final Dialog progress = new Dialog(this.client.getContext(), R.style.ProgressDialogTheme);
        progress.setContentView(R.layout.msdk_progress_dialog);
        progress.setCanceledOnTouchOutside(false);
        progress.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialogInterface) {
                VKAuthRequestHandler.this.client.notifyListener(AuthClient.Result.createErrorResult(VKAuthRequestHandler.this.client.getPendingRequest(), GGErrorCode.USER_CANCELLED.getCode().intValue()));
            }
        });
        try {
            progress.show();
        } catch (Exception e) {
        }
        exchangeToken(token.accessToken).continueWith(new Continuation<AuthClient.Result, Void>() {
            public Void then(Task<AuthClient.Result> task) throws Exception {
                try {
                    progress.dismiss();
                } catch (Exception e) {
                }
                if (task.isFaulted()) {
                    VKAuthRequestHandler.this.client.notifyListener(AuthClient.Result.createErrorResult(VKAuthRequestHandler.this.client.getPendingRequest(), "error", task.getError().getMessage(), GGErrorCode.UNKNOWN_ERROR.getCode().intValue()));
                    return null;
                }
                VKAuthRequestHandler.this.client.notifyListener(task.getResult());
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    private Task<AuthClient.Result> exchangeToken(final String token) {
        final AuthClient.AuthClientRequest pendingRequest = this.client.getPendingRequest();
        return Task.callInBackground(new Callable<AuthClient.Result>() {
            public AuthClient.Result call() throws Exception {
                AuthClient.Result result = null;
                int errorCode = GGErrorCode.UNKNOWN_ERROR.getCode().intValue();
                HashMap<String, String> data = new HashMap<>(3);
                data.put("vk_access_token", token);
                data.put("client_id", pendingRequest.getApplicationId());
                JSONObject response = SimpleNetworkClient.getInstance().makePostRequest(SDKConstants.getAuthVkTokenExchangeUrl(), (Map<String, String>) data);
                if (response == null) {
                    errorCode = GGErrorCode.NETWORK_EXCEPTION.getCode().intValue();
                } else if (response.has("open_id")) {
                    try {
                        String openId = response.getString("open_id");
                        String accessToken = response.getString("access_token");
                        int expireTime = response.getInt(GGLiveConstants.PARAM.EXPIRY_TIME);
                        AuthToken authToken = new AuthToken(accessToken, TokenProvider.VK);
                        authToken.setExpiryTimestamp(expireTime);
                        result = AuthClient.Result.createTokenResult(pendingRequest, authToken, openId);
                    } catch (JSONException e) {
                        BBLogger.e(e);
                    }
                } else {
                    String error = response.optString("error");
                    if (SDKConstants.ErrorFlags.ERROR_USER_BAN.equals(error)) {
                        errorCode = GGErrorCode.ERROR_USER_BANNED.getCode().intValue();
                    } else if (SDKConstants.ErrorFlags.INVALID_TOKEN.equals(error)) {
                        AccessToken.setCurrentAccessToken((AccessToken) null);
                        errorCode = GGErrorCode.ACCESS_TOKEN_INVALID_GRANT.getCode().intValue();
                    } else {
                        errorCode = GGErrorCode.ACCESS_TOKEN_EXCHANGE_FAILED.getCode().intValue();
                    }
                }
                if (result == null) {
                    return AuthClient.Result.createErrorResult(pendingRequest, errorCode);
                }
                return result;
            }
        });
    }

    public void onError(VKError error) {
        BBLogger.d("vk auth err: %s", error);
        AuthClient.AuthClientRequest pendingRequest = this.client.getPendingRequest();
        if (error == null || error.errorCode != -102) {
            this.client.notifyListener(AuthClient.Result.createErrorResult(pendingRequest, "error", error != null ? h.a + error.errorCode + ") " + error.errorMessage : "", GGErrorCode.LOGIN_FAILED.getCode().intValue()));
        } else {
            this.client.notifyListener(AuthClient.Result.createErrorResult(pendingRequest, GGErrorCode.USER_CANCELLED.getCode().intValue()));
        }
    }
}

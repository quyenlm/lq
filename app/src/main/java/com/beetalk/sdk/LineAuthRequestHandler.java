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
import com.beetalk.sdk.line.LineUtils;
import com.beetalk.sdk.networking.SimpleNetworkClient;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.facebook.AccessToken;
import com.garena.msdk.R;
import com.garena.pay.android.GGErrorCode;
import com.linecorp.linesdk.LineApiResponseCode;
import com.linecorp.linesdk.LineCredential;
import com.linecorp.linesdk.auth.LineLoginApi;
import com.linecorp.linesdk.auth.LineLoginResult;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import org.json.JSONException;
import org.json.JSONObject;

public class LineAuthRequestHandler extends AuthRequestHandler {
    private static final int REQUEST_CODE = 33058;

    public LineAuthRequestHandler(AuthClient client) {
        super(client);
    }

    /* access modifiers changed from: package-private */
    public boolean startAuth(AuthClient.AuthClientRequest request) {
        BBLogger.d("start Line Auth", new Object[0]);
        if (request == null) {
            return false;
        }
        try {
            this.client.getActivityLauncher().startActivityForResult(LineLoginApi.getLoginIntent(this.client.getContext(), String.valueOf(LineUtils.lookUpChannelId(this.client.getContext()))), REQUEST_CODE);
            return true;
        } catch (Exception e) {
            BBLogger.e(e);
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean onActivityResult(int requestCode, int resultCode, Intent data, AuthClient.AuthClientRequest pendingRequest) {
        if (requestCode != REQUEST_CODE) {
            return false;
        }
        LineLoginResult result = LineLoginApi.getLoginResultFromIntent(data);
        switch (AnonymousClass4.$SwitchMap$com$linecorp$linesdk$LineApiResponseCode[result.getResponseCode().ordinal()]) {
            case 1:
                LineCredential credential = result.getLineCredential();
                if (credential != null) {
                    String accessToken = credential.getAccessToken().getAccessToken();
                    if (!TextUtils.isEmpty(accessToken)) {
                        BBLogger.d("line login success, token: %s", accessToken);
                        onLoggedIn(accessToken);
                        return true;
                    }
                }
                BBLogger.e("line access token is null", new Object[0]);
                this.client.notifyListener(AuthClient.Result.createErrorResult(this.client.getPendingRequest(), GGErrorCode.LOGIN_FAILED.getCode().intValue()));
                return true;
            case 2:
                BBLogger.d("line login canceled", new Object[0]);
                this.client.notifyListener(AuthClient.Result.createErrorResult(this.client.getPendingRequest(), GGErrorCode.USER_CANCELLED.getCode().intValue()));
                return true;
            default:
                String errorMsg = result.getErrorData().toString();
                BBLogger.d("line login failed: %s", errorMsg);
                this.client.notifyListener(AuthClient.Result.createErrorResult(this.client.getPendingRequest(), "error", errorMsg, GGErrorCode.LOGIN_FAILED.getCode().intValue()));
                return true;
        }
    }

    /* renamed from: com.beetalk.sdk.LineAuthRequestHandler$4  reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$linecorp$linesdk$LineApiResponseCode = new int[LineApiResponseCode.values().length];

        static {
            try {
                $SwitchMap$com$linecorp$linesdk$LineApiResponseCode[LineApiResponseCode.SUCCESS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$linecorp$linesdk$LineApiResponseCode[LineApiResponseCode.CANCEL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    private void onLoggedIn(String token) {
        final Dialog progress = new Dialog(this.client.getContext(), R.style.ProgressDialogTheme);
        progress.setContentView(R.layout.msdk_progress_dialog);
        progress.setCanceledOnTouchOutside(false);
        progress.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialogInterface) {
                LineAuthRequestHandler.this.client.notifyListener(AuthClient.Result.createErrorResult(LineAuthRequestHandler.this.client.getPendingRequest(), GGErrorCode.USER_CANCELLED.getCode().intValue()));
            }
        });
        try {
            progress.show();
        } catch (Exception e) {
        }
        exchangeToken(token).continueWith(new Continuation<AuthClient.Result, Void>() {
            public Void then(Task<AuthClient.Result> task) throws Exception {
                try {
                    progress.dismiss();
                } catch (Exception e) {
                }
                if (task.isFaulted()) {
                    BBLogger.e(task.getError());
                    LineAuthRequestHandler.this.client.notifyListener(AuthClient.Result.createErrorResult(LineAuthRequestHandler.this.client.getPendingRequest(), "error", task.getError().getMessage(), GGErrorCode.LOGIN_FAILED.getCode().intValue()));
                    return null;
                }
                LineAuthRequestHandler.this.client.notifyListener(task.getResult());
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
                data.put("line_access_token", token);
                data.put("client_id", pendingRequest.getApplicationId());
                JSONObject response = SimpleNetworkClient.getInstance().makePostRequest(SDKConstants.getAuthLineTokenExchangeUrl(), (Map<String, String>) data);
                BBLogger.d("response: %s", response);
                if (response == null) {
                    errorCode = GGErrorCode.NETWORK_EXCEPTION.getCode().intValue();
                } else if (response.has("open_id")) {
                    try {
                        String openId = response.getString("open_id");
                        String accessToken = response.getString("access_token");
                        int expireTime = response.getInt(GGLiveConstants.PARAM.EXPIRY_TIME);
                        AuthToken authToken = new AuthToken(accessToken, TokenProvider.LINE);
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
}

package com.beetalk.sdk;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;
import com.beetalk.sdk.AuthClient;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.ShareConstants;
import com.beetalk.sdk.data.AuthToken;
import com.beetalk.sdk.data.TokenProvider;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.Helper;
import com.beetalk.sdk.helper.Validate;
import com.beetalk.sdk.networking.SimpleNetworkClient;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.garena.pay.android.GGErrorCode;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class GasAuthRequestHandler extends AuthRequestHandler {
    protected GasAuthRequestHandler(AuthClient client) {
        super(client);
    }

    public static boolean validateVersion(Context context, String packageName, int minVersion) {
        try {
            PackageManager manager = context.getPackageManager();
            if (manager == null) {
                return false;
            }
            if (manager.getPackageInfo(packageName, 0).versionCode >= minVersion || SDKConstants.RELEASE_VERSION) {
                return true;
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    protected static boolean validateSignature(Context context, String packageName) {
        String brand = Build.BRAND;
        int applicationFlags = context.getApplicationInfo().flags;
        if (brand.startsWith(MessengerShareContentUtility.TEMPLATE_GENERIC_TYPE) && (applicationFlags & 2) != 0) {
            return true;
        }
        try {
            PackageManager manager = context.getPackageManager();
            if (manager == null) {
                return false;
            }
            for (Signature signature : manager.getPackageInfo(packageName, 64).signatures) {
                String val = signature.toCharsString();
                if (SDKConstants.GAS_DEBUG_SIGNATURE.equals(val)) {
                    return true;
                }
                if ((SDKConstants.GAS_RELEASE_SIGNATURE.equals(val) || SDKConstants.GAS_GOOGLE_PLAY_RELEASE_SIGNATURE.equals(val)) && SDKConstants.RELEASE_VERSION) {
                    return true;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean startAuth(AuthClient.AuthClientRequest request) {
        if (request == null) {
            return false;
        }
        BBLogger.d("start gas auth", new Object[0]);
        Activity authActivity = this.client.getActivityLauncher().getContext();
        Validate.notNull(authActivity, "Auth Activity");
        String gasPackageName = SDKConstants.GAS_PACKAGE;
        Intent intent = new Intent().setClassName(gasPackageName, SDKConstants.GAS_AUTH_ACTIVITY_FQ_NAME);
        intent.putExtra(ShareConstants.IntentFlag.GG_FIELD_REQUEST_CODE, request.getRequestCode());
        intent.putExtra(ShareConstants.IntentFlag.GG_FIELD_APPLICATION_ID, request.getApplicationId());
        intent.putExtra(ShareConstants.IntentFlag.GG_FIELD_APPLICATION_KEY, request.getApplicationKey());
        intent.putExtra(ShareConstants.IntentFlag.GG_FIELD_REDIRECT_URL, SDKConstants.REDIRECT_URL_PREFIX + request.getApplicationId() + "://auth/");
        intent.putExtra(ShareConstants.IntentFlag.GG_FIELD_AUTH_ID, request.getAuthId());
        intent.putExtra(ShareConstants.IntentFlag.GG_FIELD_KEY_HASH, Helper.getSignatureFingerprint(authActivity));
        intent.putExtra(ShareConstants.IntentFlag.GG_FIELD_SDK_ENV, SDKConstants.getEnvironment().toString());
        PackageManager manager = this.client.getActivityLauncher().getContext().getPackageManager();
        if ((manager == null ? null : manager.resolveActivity(intent, 0)) == null) {
            gasPackageName = SDKConstants.GAS_LITE_PACKAGE;
            intent.setClassName(gasPackageName, SDKConstants.GAS_AUTH_ACTIVITY_FQ_NAME);
            if ((manager == null ? null : manager.resolveActivity(intent, 0)) == null) {
                startWebAuth(request);
                return true;
            }
        }
        if (!validateSignature(authActivity, gasPackageName)) {
            Toast.makeText(authActivity, "No valid Gas Signature Found", 0).show();
            return false;
        } else if (!validateVersion(authActivity, gasPackageName, SDKConstants.MIN_GAS_VERSION_SUPPORT)) {
            Toast.makeText(authActivity, "Please upgrade the Gas app", 0).show();
            return false;
        } else {
            try {
                authActivity.startActivityForResult(intent, request.getRequestCode());
                return true;
            } catch (ActivityNotFoundException e) {
                return false;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean onActivityResult(int requestCode, int resultCode, Intent data, AuthClient.AuthClientRequest thisRequest) {
        AuthClient.Result outcome;
        if (requestCode != thisRequest.getRequestCode()) {
            return false;
        }
        AuthClient.AuthClientRequest pendingRequest = this.client.getPendingRequest();
        if (!pendingRequest.getAuthId().equals(thisRequest.getAuthId())) {
            onResult(AuthClient.Result.createErrorResult(pendingRequest, GGErrorCode.REQUEST_ID_MISMATCH.getCode().intValue()));
            return true;
        }
        String error = "";
        if (data != null && data.hasExtra("error")) {
            error = data.getStringExtra("error");
        }
        switch (resultCode) {
            case 0:
            case ShareConstants.ERROR_CODE.GG_RESULT_CANCELLED /*184*/:
                outcome = AuthClient.Result.createErrorResult(pendingRequest, GGErrorCode.USER_CANCELLED.getCode().intValue());
                break;
            case ShareConstants.ERROR_CODE.GG_RESULT_NETWORK_ERROR /*178*/:
                outcome = AuthClient.Result.createErrorResult(pendingRequest, GGErrorCode.NETWORK_EXCEPTION.getCode().intValue());
                break;
            case ShareConstants.ERROR_CODE.GG_RESULT_AUTH_ERROR /*179*/:
                if (!SDKConstants.ErrorFlags.ERROR_USER_BAN.equals(error)) {
                    outcome = AuthClient.Result.createErrorResult(pendingRequest, GGErrorCode.ERROR.getCode().intValue());
                    break;
                } else {
                    outcome = AuthClient.Result.createErrorResult(pendingRequest, GGErrorCode.ERROR_USER_BANNED.getCode().intValue());
                    break;
                }
            case ShareConstants.ERROR_CODE.GG_RESULT_UNKNOWN_ERROR /*180*/:
                outcome = AuthClient.Result.createErrorResult(pendingRequest, GGErrorCode.UNKNOWN_ERROR.getCode().intValue());
                break;
            case ShareConstants.ERROR_CODE.GG_RESULT_AUTH_EXCHANGE_ERROR /*182*/:
                outcome = AuthClient.Result.createErrorResult(pendingRequest, GGErrorCode.ACCESS_TOKEN_EXCHANGE_FAILED.getCode().intValue());
                break;
            case ShareConstants.ERROR_CODE.GG_RESULT_REFRESH_TOKEN_EMPTY /*183*/:
                outcome = AuthClient.Result.createErrorResult(pendingRequest, GGErrorCode.REFRESH_TOKEN_FAILED.getCode().intValue());
                break;
            case ShareConstants.ERROR_CODE.GG_RESULT_INSPECTION_ERROR /*185*/:
                outcome = AuthClient.Result.createErrorResult(pendingRequest, GGErrorCode.ERROR_IN_PARAMS.getCode().intValue());
                break;
            case ShareConstants.ERROR_CODE.GG_RESULT_REFRESH_FAIL_ERROR /*186*/:
                outcome = AuthClient.Result.createErrorResult(pendingRequest, GGErrorCode.REFRESH_TOKEN_FAILED.getCode().intValue());
                break;
            case ShareConstants.ERROR_CODE.GG_RESULT_NOT_SUPPORTED /*187*/:
                outcome = AuthClient.Result.createErrorResult(pendingRequest, GGErrorCode.UNSUPPORTED_API.getCode().intValue());
                break;
            case ShareConstants.ERROR_CODE.GG_RESULT_TCP_FAILED /*189*/:
                startWebAuth(thisRequest);
                return true;
            default:
                outcome = AuthClient.Result.createErrorResult(pendingRequest, GGErrorCode.UNKNOWN_ERROR.getCode().intValue());
                break;
        }
        if (resultCode == -1) {
            if (data.hasExtra("garena_auth_result")) {
                outcome = (AuthClient.Result) data.getSerializableExtra("garena_auth_result");
            } else {
                try {
                    String token = data.getExtras().getString(ShareConstants.IntentFlag.GG_FIELD_TOKEN_VALUE);
                    new RequestSessionTokenTask().execute(new String[]{token});
                    return true;
                } catch (NullPointerException e) {
                    BBLogger.e(e);
                }
            }
        }
        onResult(outcome);
        return true;
    }

    private void startWebAuth(AuthClient.AuthClientRequest request) {
        Intent startGarenaAuthEvent = new Intent();
        startGarenaAuthEvent.setClass(this.client.getActivityLauncher().getContext(), GarenaAuthActivity.class);
        startGarenaAuthEvent.putExtra("garena_request_info", request);
        this.client.getActivityLauncher().startActivityForResult(startGarenaAuthEvent, request.getRequestCode());
    }

    /* access modifiers changed from: private */
    public void onResult(AuthClient.Result result) {
        if (result != null) {
            BBLogger.d(result.toString(), new Object[0]);
            if (result.token != null) {
                GGLoginSession session = GGLoginSession.getCurrentSession();
                if (session != null) {
                    session.getCache().putToken(result.token);
                }
                GGLoginSession.setCurrentSession(session);
            }
            this.client.notifyListener(result);
            return;
        }
        this.client.tryHandler();
    }

    /* access modifiers changed from: private */
    public AuthClient.Result requestUserToken(String userToken) {
        AuthClient.AuthClientRequest pendingRequest = this.client.getPendingRequest();
        HashMap<String, String> data = new HashMap<>(3);
        data.put("grant_type", "authorization_code");
        data.put("code", userToken);
        data.put("redirect_uri", SDKConstants.REDIRECT_URL_PREFIX + pendingRequest.getApplicationId() + "://auth/");
        data.put("source", GGPlatform.getChannelSource().toString());
        data.put("client_id", pendingRequest.getApplicationId());
        data.put("client_secret", pendingRequest.getApplicationKey());
        JSONObject result = SimpleNetworkClient.getInstance().makePostRequest(SDKConstants.getAuthServerExchangeTokenUrl(), (Map<String, String>) data);
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
            AuthToken authToken = new AuthToken(accessToken, TokenProvider.GARENA_NATIVE_ANDROID);
            if (result.optInt("platform") == 3) {
                authToken.setTokenProvider(TokenProvider.FACEBOOK);
            }
            authToken.setRefreshToken(refreshToken);
            authToken.setExpiryTimestamp(expireTime);
            outcome = AuthClient.Result.createTokenResult(pendingRequest, authToken, openId);
        } else {
            String error = result.optString("error");
            errorCode = SDKConstants.ErrorFlags.INVALID_TOKEN.equals(error) ? GGErrorCode.ACCESS_TOKEN_INVALID_GRANT.getCode().intValue() : SDKConstants.ErrorFlags.ERROR_USER_BAN.equals(error) ? GGErrorCode.ERROR_USER_BANNED.getCode().intValue() : GGErrorCode.ACCESS_TOKEN_EXCHANGE_FAILED.getCode().intValue();
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
            return GasAuthRequestHandler.this.requestUserToken(tokens[0]);
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(AuthClient.Result result) {
            GasAuthRequestHandler.this.onResult(result);
        }
    }
}

package com.facebook.login;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.PlatformServiceClient;
import com.facebook.internal.Utility;
import com.facebook.login.LoginClient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

class GetTokenLoginMethodHandler extends LoginMethodHandler {
    public static final Parcelable.Creator<GetTokenLoginMethodHandler> CREATOR = new Parcelable.Creator() {
        public GetTokenLoginMethodHandler createFromParcel(Parcel source) {
            return new GetTokenLoginMethodHandler(source);
        }

        public GetTokenLoginMethodHandler[] newArray(int size) {
            return new GetTokenLoginMethodHandler[size];
        }
    };
    private GetTokenClient getTokenClient;

    GetTokenLoginMethodHandler(LoginClient loginClient) {
        super(loginClient);
    }

    /* access modifiers changed from: package-private */
    public String getNameForLogging() {
        return "get_token";
    }

    /* access modifiers changed from: package-private */
    public void cancel() {
        if (this.getTokenClient != null) {
            this.getTokenClient.cancel();
            this.getTokenClient.setCompletedListener((PlatformServiceClient.CompletedListener) null);
            this.getTokenClient = null;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean tryAuthorize(final LoginClient.Request request) {
        this.getTokenClient = new GetTokenClient(this.loginClient.getActivity(), request.getApplicationId());
        if (!this.getTokenClient.start()) {
            return false;
        }
        this.loginClient.notifyBackgroundProcessingStart();
        this.getTokenClient.setCompletedListener(new PlatformServiceClient.CompletedListener() {
            public void completed(Bundle result) {
                GetTokenLoginMethodHandler.this.getTokenCompleted(request, result);
            }
        });
        return true;
    }

    /* access modifiers changed from: package-private */
    public void getTokenCompleted(LoginClient.Request request, Bundle result) {
        if (this.getTokenClient != null) {
            this.getTokenClient.setCompletedListener((PlatformServiceClient.CompletedListener) null);
        }
        this.getTokenClient = null;
        this.loginClient.notifyBackgroundProcessingStop();
        if (result != null) {
            ArrayList<String> currentPermissions = result.getStringArrayList(NativeProtocol.EXTRA_PERMISSIONS);
            Set<String> permissions = request.getPermissions();
            if (currentPermissions == null || (permissions != null && !currentPermissions.containsAll(permissions))) {
                Set<String> newPermissions = new HashSet<>();
                for (String permission : permissions) {
                    if (!currentPermissions.contains(permission)) {
                        newPermissions.add(permission);
                    }
                }
                if (!newPermissions.isEmpty()) {
                    addLoggingExtra("new_permissions", TextUtils.join(",", newPermissions));
                }
                request.setPermissions(newPermissions);
            } else {
                complete(request, result);
                return;
            }
        }
        this.loginClient.tryNextHandler();
    }

    /* access modifiers changed from: package-private */
    public void onComplete(LoginClient.Request request, Bundle result) {
        this.loginClient.completeAndValidate(LoginClient.Result.createTokenResult(this.loginClient.getPendingRequest(), createAccessTokenFromNativeLogin(result, AccessTokenSource.FACEBOOK_APPLICATION_SERVICE, request.getApplicationId())));
    }

    /* access modifiers changed from: package-private */
    public void complete(final LoginClient.Request request, final Bundle result) {
        String userId = result.getString(NativeProtocol.EXTRA_USER_ID);
        if (userId == null || userId.isEmpty()) {
            this.loginClient.notifyBackgroundProcessingStart();
            Utility.getGraphMeRequestWithCacheAsync(result.getString(NativeProtocol.EXTRA_ACCESS_TOKEN), new Utility.GraphMeRequestWithCacheCallback() {
                public void onSuccess(JSONObject userInfo) {
                    try {
                        result.putString(NativeProtocol.EXTRA_USER_ID, userInfo.getString("id"));
                        GetTokenLoginMethodHandler.this.onComplete(request, result);
                    } catch (JSONException ex) {
                        GetTokenLoginMethodHandler.this.loginClient.complete(LoginClient.Result.createErrorResult(GetTokenLoginMethodHandler.this.loginClient.getPendingRequest(), "Caught exception", ex.getMessage()));
                    }
                }

                public void onFailure(FacebookException error) {
                    GetTokenLoginMethodHandler.this.loginClient.complete(LoginClient.Result.createErrorResult(GetTokenLoginMethodHandler.this.loginClient.getPendingRequest(), "Caught exception", error.getMessage()));
                }
            });
            return;
        }
        onComplete(request, result);
    }

    GetTokenLoginMethodHandler(Parcel source) {
        super(source);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }
}

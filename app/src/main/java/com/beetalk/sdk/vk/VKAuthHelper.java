package com.beetalk.sdk.vk;

import android.app.Activity;
import android.content.Intent;
import com.beetalk.sdk.helper.BBLogger;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKServiceActivity;
import com.vk.sdk.api.VKError;

public class VKAuthHelper {
    private static final String[] SCOPE = {"wall", "photos", "offline"};
    /* access modifiers changed from: private */
    public final Activity mActivity;
    /* access modifiers changed from: private */
    public OnAuthResultListener mAuthListener;

    public interface OnAuthResultListener {
        void onError(VKError vKError);

        void onLoggedIn();
    }

    public VKAuthHelper(Activity activity) {
        this.mActivity = activity;
    }

    public void setOnAuthResultListener(OnAuthResultListener listener) {
        this.mAuthListener = listener;
    }

    public void startLogin() {
        VKSdk.wakeUpSession(this.mActivity, new VKCallback<VKSdk.LoginState>() {
            public void onResult(VKSdk.LoginState res) {
                BBLogger.d("vk session state: %s", res);
                switch (AnonymousClass3.$SwitchMap$com$vk$sdk$VKSdk$LoginState[res.ordinal()]) {
                    case 2:
                        BBLogger.d("vk already logged in", new Object[0]);
                        VKAuthHelper.this.verifyScopes(VKAuthHelper.this.mActivity);
                        return;
                    case 3:
                    case 4:
                        VKAuthHelper.this.startLogin(VKAuthHelper.this.mActivity);
                        return;
                    default:
                        return;
                }
            }

            public void onError(VKError error) {
                BBLogger.d("vk session check error: %s", error);
                if (VKAuthHelper.this.mAuthListener != null) {
                    VKAuthHelper.this.mAuthListener.onError(error);
                }
            }
        });
    }

    /* renamed from: com.beetalk.sdk.vk.VKAuthHelper$3  reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$vk$sdk$VKSdk$LoginState = new int[VKSdk.LoginState.values().length];

        static {
            try {
                $SwitchMap$com$vk$sdk$VKSdk$LoginState[VKSdk.LoginState.Pending.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$vk$sdk$VKSdk$LoginState[VKSdk.LoginState.LoggedIn.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$vk$sdk$VKSdk$LoginState[VKSdk.LoginState.LoggedOut.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$vk$sdk$VKSdk$LoginState[VKSdk.LoginState.Unknown.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:4:0x001d, code lost:
        if (r2.hasScope(SCOPE) != false) goto L_0x001f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void verifyScopes(android.app.Activity r8) {
        /*
            r7 = this;
            r1 = 1
            r3 = 0
            java.lang.String r4 = "vk verifying scopes: %s"
            java.lang.Object[] r5 = new java.lang.Object[r1]
            java.lang.String[] r6 = SCOPE
            java.lang.String r6 = java.util.Arrays.toString(r6)
            r5[r3] = r6
            com.beetalk.sdk.helper.BBLogger.d(r4, r5)
            com.vk.sdk.VKAccessToken r2 = com.vk.sdk.VKAccessToken.currentToken()
            if (r2 == 0) goto L_0x0032
            java.lang.String[] r4 = SCOPE     // Catch:{ NullPointerException -> 0x0034 }
            boolean r4 = r2.hasScope(r4)     // Catch:{ NullPointerException -> 0x0034 }
            if (r4 == 0) goto L_0x0032
        L_0x001f:
            if (r1 == 0) goto L_0x0037
            java.lang.String r4 = "vk scopes verified"
            java.lang.Object[] r3 = new java.lang.Object[r3]
            com.beetalk.sdk.helper.BBLogger.d(r4, r3)
            com.beetalk.sdk.vk.VKAuthHelper$OnAuthResultListener r3 = r7.mAuthListener
            if (r3 == 0) goto L_0x0031
            com.beetalk.sdk.vk.VKAuthHelper$OnAuthResultListener r3 = r7.mAuthListener
            r3.onLoggedIn()
        L_0x0031:
            return
        L_0x0032:
            r1 = r3
            goto L_0x001f
        L_0x0034:
            r0 = move-exception
            r1 = 0
            goto L_0x001f
        L_0x0037:
            java.lang.String r4 = "vk scopes verification failed"
            java.lang.Object[] r3 = new java.lang.Object[r3]
            com.beetalk.sdk.helper.BBLogger.d(r4, r3)
            r7.startLogin(r8)
            goto L_0x0031
        */
        throw new UnsupportedOperationException("Method not decompiled: com.beetalk.sdk.vk.VKAuthHelper.verifyScopes(android.app.Activity):void");
    }

    /* access modifiers changed from: private */
    public void startLogin(Activity activity) {
        BBLogger.d("vk login required, start login", new Object[0]);
        VKSdk.login(activity, SCOPE);
    }

    public boolean onActivityResult(int resultCode, Intent data) {
        return VKSdk.onActivityResult(VKServiceActivity.VKServiceType.Authorization.getOuterCode(), resultCode, data, new VKCallback<VKAccessToken>() {
            public void onResult(VKAccessToken res) {
                BBLogger.d("vk login success: %s", res);
                if (VKAuthHelper.this.mAuthListener != null) {
                    VKAuthHelper.this.mAuthListener.onLoggedIn();
                }
            }

            public void onError(VKError error) {
                BBLogger.d("vk login error: %s", error);
                if (VKAuthHelper.this.mAuthListener != null) {
                    VKAuthHelper.this.mAuthListener.onError(error);
                }
            }
        });
    }
}

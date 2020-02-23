package com.vk.sdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.drive.DriveFile;
import com.vk.sdk.api.VKError;
import com.vk.sdk.dialogs.VKCaptchaDialog;
import com.vk.sdk.dialogs.VKOpenAuthDialog;
import com.vk.sdk.util.VKStringJoiner;
import com.vk.sdk.util.VKUtil;
import java.util.ArrayList;
import java.util.Collection;

public class VKServiceActivity extends Activity implements DialogInterface.OnDismissListener {
    private static final String KEY_REQUEST = "arg3";
    private static final String KEY_SCOPE_LIST = "arg2";
    private static final String KEY_SDK_CUSTOM_INITIALIZE = "arg4";
    private static final String KEY_TYPE = "arg1";
    private static final String VK_APP_AUTH_ACTION = "com.vkontakte.android.action.SDK_AUTH";
    private static final String VK_APP_FINGERPRINT = "48761EEF50EE53AFC4CC9C5F10E6BDE7F8F5B82F";
    private static final String VK_APP_PACKAGE_ID = "com.vkontakte.android";

    public enum VKServiceType {
        Authorization(10485),
        Captcha(14079),
        Validation(11477);
        
        private int outerCode;

        private VKServiceType(int outerCode2) {
            this.outerCode = outerCode2;
        }

        public int getOuterCode() {
            return this.outerCode;
        }
    }

    public static void interruptWithError(Context ctx, VKError apiError, VKServiceType type) {
        Intent intent = createIntent(ctx, type);
        intent.setFlags(DriveFile.MODE_READ_ONLY);
        intent.putExtra(KEY_REQUEST, apiError.registerObject());
        if (ctx != null) {
            ctx.startActivity(intent);
        }
    }

    static void startLoginActivity(@NonNull Activity act, @NonNull ArrayList<String> scopeList) {
        Intent intent = createIntent(act.getApplicationContext(), VKServiceType.Authorization);
        intent.putStringArrayListExtra(KEY_SCOPE_LIST, scopeList);
        act.startActivityForResult(intent, VKServiceType.Authorization.getOuterCode());
    }

    @TargetApi(11)
    static void startLoginActivity(@NonNull Fragment fr, ArrayList<String> scopeList) {
        Intent intent = createIntent(fr.getActivity().getApplication(), VKServiceType.Authorization);
        intent.putStringArrayListExtra(KEY_SCOPE_LIST, scopeList);
        fr.startActivityForResult(intent, VKServiceType.Authorization.getOuterCode());
    }

    @NonNull
    private static Intent createIntent(Context appCtx, VKServiceType type) {
        Intent intent = new Intent(appCtx, VKServiceActivity.class);
        intent.putExtra(KEY_TYPE, type.name());
        intent.putExtra(KEY_SDK_CUSTOM_INITIALIZE, VKSdk.isCustomInitialize());
        return intent;
    }

    @NonNull
    private VKServiceType getType() {
        return VKServiceType.valueOf(getIntent().getStringExtra(KEY_TYPE));
    }

    @Nullable
    private ArrayList<String> getScopeList() {
        return getIntent().getStringArrayListExtra(KEY_SCOPE_LIST);
    }

    /* access modifiers changed from: private */
    public long getRequestId() {
        return getIntent().getLongExtra(KEY_REQUEST, 0);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra(KEY_SDK_CUSTOM_INITIALIZE, false)) {
            VKSdk.customInitialize(this, 0, (String) null);
        }
        VKSdk.wakeUpSession(getApplicationContext());
        switch (getType()) {
            case Authorization:
                Context ctx = getApplicationContext();
                Bundle bundle = new Bundle();
                bundle.putString("version", VKSdk.getApiVersion());
                bundle.putInt("client_id", VKSdk.getsCurrentAppId());
                bundle.putBoolean(VKOpenAuthDialog.VK_EXTRA_REVOKE, true);
                bundle.putString("scope", VKStringJoiner.join((Collection<?>) getScopeList(), ","));
                String[] fingerprints = VKUtil.getCertificateFingerprint(ctx, "com.vkontakte.android");
                if (!VKUtil.isAppInstalled(ctx, "com.vkontakte.android") || !VKUtil.isIntentAvailable(ctx, VK_APP_AUTH_ACTION) || fingerprints.length <= 0 || !fingerprints[0].equals(VK_APP_FINGERPRINT)) {
                    new VKOpenAuthDialog().show(this, bundle, VKServiceType.Authorization.getOuterCode(), (VKError) null);
                    return;
                } else if (savedInstanceState == null) {
                    Intent intent = new Intent(VK_APP_AUTH_ACTION, (Uri) null);
                    intent.setPackage("com.vkontakte.android");
                    intent.putExtras(bundle);
                    startActivityForResult(intent, VKServiceType.Authorization.getOuterCode());
                    return;
                } else {
                    return;
                }
            case Captcha:
                VKError vkError = (VKError) VKObject.getRegisteredObject(getRequestId());
                if (vkError != null) {
                    new VKCaptchaDialog(vkError).show(this, this);
                    return;
                } else {
                    finish();
                    return;
                }
            case Validation:
                VKError vkError2 = (VKError) VKObject.getRegisteredObject(getRequestId());
                if (vkError2 != null) {
                    if (!TextUtils.isEmpty(vkError2.redirectUri) && !vkError2.redirectUri.contains("&ui=vk_sdk") && !vkError2.redirectUri.contains("?ui=vk_sdk")) {
                        if (vkError2.redirectUri.indexOf(63) > 0) {
                            vkError2.redirectUri += "&ui=vk_sdk";
                        } else {
                            vkError2.redirectUri += "?ui=vk_sdk";
                        }
                    }
                    new VKOpenAuthDialog().show(this, new Bundle(), VKServiceType.Validation.getOuterCode(), vkError2);
                    return;
                }
                finish();
                return;
            default:
                return;
        }
    }

    public void onActivityResultPublic(int requestCode, int resultCode, Intent data) {
        onActivityResult(requestCode, resultCode, data);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VKServiceType.Authorization.getOuterCode() || requestCode == VKServiceType.Validation.getOuterCode()) {
            VKSdk.processActivityResult(this, resultCode, data, new VKCallback<VKAccessToken>() {
                public void onResult(VKAccessToken res) {
                    VKServiceActivity.this.setResult(-1);
                    VKServiceActivity.this.finish();
                }

                public void onError(VKError error) {
                    VKObject o = VKObject.getRegisteredObject(VKServiceActivity.this.getRequestId());
                    if (o instanceof VKError) {
                        VKError vkError = (VKError) o;
                        if (vkError.request != null) {
                            vkError.request.cancel();
                            if (vkError.request.requestListener != null) {
                                vkError.request.requestListener.onError(error);
                            }
                        }
                    }
                    if (error != null) {
                        VKServiceActivity.this.setResult(0, VKServiceActivity.this.getIntent().putExtra("vk_extra_error_id", error.registerObject()));
                    } else {
                        VKServiceActivity.this.setResult(0);
                    }
                    VKServiceActivity.this.finish();
                }
            });
        }
    }

    public void onDismiss(DialogInterface dialog) {
        finish();
    }
}

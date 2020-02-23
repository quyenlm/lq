package com.beetalk.sdk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.CookieSyncManager;
import com.beetalk.sdk.AuthClient;
import com.beetalk.sdk.GGLoginSession;
import com.beetalk.sdk.helper.BBLogger;

public class BTLoginActivity extends Activity {
    static final /* synthetic */ boolean $assertionsDisabled = (!BTLoginActivity.class.desiredAssertionStatus());
    private static final String SAVED_AUTH_CLIENT = "saved_auth_client";
    private AuthClient authClient;
    private String callingPackage;
    private AuthClient.AuthClientRequest request;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        BBLogger.d("BTLoginActivity onCreate", new Object[0]);
        super.onCreate(savedInstanceState);
        CookieSyncManager.createInstance(this);
        GGPlatform.initialize((Activity) this);
        if (GGLoginSession.getCurrentSession() == null) {
            BBLogger.d("recreate session", new Object[0]);
            GGLoginSession.setCurrentSession(new GGLoginSession.Builder(this).build());
        }
        if (savedInstanceState != null) {
            this.authClient = (AuthClient) savedInstanceState.getSerializable(SAVED_AUTH_CLIENT);
            BBLogger.d("BTLoginActivity onCreate, get authClient from savedInstanceState", new Object[0]);
        } else {
            this.authClient = new AuthClient();
            this.request = (AuthClient.AuthClientRequest) getIntent().getSerializableExtra(SDKConstants.BUNDLE_REQUEST_KEY);
            BBLogger.d("BTLoginActivity onCreate, savedInstanceState is null, create authClient", new Object[0]);
        }
        this.callingPackage = getCallingPackage();
        if ($assertionsDisabled || this.authClient != null) {
            this.authClient.setContext(this);
            this.authClient.setOnAuthCompleted(new AuthClient.OnAuthCompleted() {
                public void onAuthComplete(AuthClient.Result result) {
                    BBLogger.d("Auth complete", new Object[0]);
                    BTLoginActivity.this.onAuthClientCompletion(result);
                }
            });
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: private */
    public void onAuthClientCompletion(AuthClient.Result result) {
        int resultCode;
        this.request = null;
        if (result != null) {
            resultCode = AuthClient.Result.isError(result.resultCode) ? 0 : -1;
        } else {
            resultCode = 0;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(SDKConstants.BUNDLE_RESULT_KEY, result);
        Intent resultIntent = new Intent();
        resultIntent.putExtras(bundle);
        setResult(resultCode, resultIntent);
        finish();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BBLogger.d("onActivityResult", new Object[0]);
        if (this.authClient != null) {
            this.authClient.onActivityResult(requestCode, resultCode, data);
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        BBLogger.d("destroy", new Object[0]);
        this.authClient.cancelCurrentHandler();
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        BBLogger.d("save bundle", new Object[0]);
        outState.putSerializable(SAVED_AUTH_CLIENT, this.authClient);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        CookieSyncManager.getInstance().startSync();
        if (this.callingPackage == null) {
            BBLogger.e("Error Calling Package must be set", new Object[0]);
            finish();
            return;
        }
        this.authClient.startOrResumeAuth(this.request);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        CookieSyncManager.getInstance().stopSync();
    }
}

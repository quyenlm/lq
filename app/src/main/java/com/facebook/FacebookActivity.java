package com.facebook;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.facebook.common.R;
import com.facebook.internal.FacebookDialogFragment;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.login.LoginFragment;
import com.facebook.share.internal.DeviceShareDialogFragment;
import com.facebook.share.model.ShareContent;

public class FacebookActivity extends FragmentActivity {
    private static String FRAGMENT_TAG = "SingleFragment";
    public static String PASS_THROUGH_CANCEL_ACTION = "PassThrough";
    private static final String TAG = FacebookActivity.class.getName();
    private Fragment singleFragment;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (!FacebookSdk.isInitialized()) {
            Utility.logd(TAG, "Facebook SDK not initialized. Make sure you call sdkInitialize inside your Application's onCreate method.");
            FacebookSdk.sdkInitialize(getApplicationContext());
        }
        setContentView(R.layout.com_facebook_activity_layout);
        if (PASS_THROUGH_CANCEL_ACTION.equals(intent.getAction())) {
            handlePassThroughError();
        } else {
            this.singleFragment = getFragment();
        }
    }

    /* access modifiers changed from: protected */
    public Fragment getFragment() {
        Intent intent = getIntent();
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(FRAGMENT_TAG);
        if (fragment != null) {
            return fragment;
        }
        if (FacebookDialogFragment.TAG.equals(intent.getAction())) {
            FacebookDialogFragment dialogFragment = new FacebookDialogFragment();
            dialogFragment.setRetainInstance(true);
            dialogFragment.show(manager, FRAGMENT_TAG);
            return dialogFragment;
        } else if (DeviceShareDialogFragment.TAG.equals(intent.getAction())) {
            DeviceShareDialogFragment dialogFragment2 = new DeviceShareDialogFragment();
            dialogFragment2.setRetainInstance(true);
            dialogFragment2.setShareContent((ShareContent) intent.getParcelableExtra("content"));
            dialogFragment2.show(manager, FRAGMENT_TAG);
            return dialogFragment2;
        } else {
            Fragment fragment2 = new LoginFragment();
            fragment2.setRetainInstance(true);
            manager.beginTransaction().add(R.id.com_facebook_fragment_container, fragment2, FRAGMENT_TAG).commit();
            return fragment2;
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.singleFragment != null) {
            this.singleFragment.onConfigurationChanged(newConfig);
        }
    }

    public Fragment getCurrentFragment() {
        return this.singleFragment;
    }

    private void handlePassThroughError() {
        setResult(0, NativeProtocol.createProtocolResultIntent(getIntent(), (Bundle) null, NativeProtocol.getExceptionFromErrorData(NativeProtocol.getMethodArgumentsFromIntent(getIntent()))));
        finish();
    }
}

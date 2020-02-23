package com.facebook;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import com.facebook.internal.CustomTab;

public class CustomTabMainActivity extends Activity {
    public static final String EXTRA_CHROME_PACKAGE = (CustomTabMainActivity.class.getSimpleName() + ".extra_chromePackage");
    public static final String EXTRA_PARAMS = (CustomTabMainActivity.class.getSimpleName() + ".extra_params");
    public static final String EXTRA_URL = (CustomTabMainActivity.class.getSimpleName() + ".extra_url");
    private static final String OAUTH_DIALOG = "oauth";
    public static final String REFRESH_ACTION = (CustomTabMainActivity.class.getSimpleName() + ".action_refresh");
    private BroadcastReceiver redirectReceiver;
    private boolean shouldCloseCustomTab = true;

    public static final String getRedirectUrl() {
        return "fb" + FacebookSdk.getApplicationId() + "://authorize";
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (CustomTabActivity.CUSTOM_TAB_REDIRECT_ACTION.equals(getIntent().getAction())) {
            setResult(0);
            finish();
        } else if (savedInstanceState == null) {
            Bundle parameters = getIntent().getBundleExtra(EXTRA_PARAMS);
            new CustomTab(OAUTH_DIALOG, parameters).openCustomTab(this, getIntent().getStringExtra(EXTRA_CHROME_PACKAGE));
            this.shouldCloseCustomTab = false;
            this.redirectReceiver = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    Intent newIntent = new Intent(CustomTabMainActivity.this, CustomTabMainActivity.class);
                    newIntent.setAction(CustomTabMainActivity.REFRESH_ACTION);
                    newIntent.putExtra(CustomTabMainActivity.EXTRA_URL, intent.getStringExtra(CustomTabMainActivity.EXTRA_URL));
                    newIntent.addFlags(603979776);
                    CustomTabMainActivity.this.startActivity(newIntent);
                }
            };
            LocalBroadcastManager.getInstance(this).registerReceiver(this.redirectReceiver, new IntentFilter(CustomTabActivity.CUSTOM_TAB_REDIRECT_ACTION));
        }
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (REFRESH_ACTION.equals(intent.getAction())) {
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(CustomTabActivity.DESTROY_ACTION));
            sendResult(-1, intent);
        } else if (CustomTabActivity.CUSTOM_TAB_REDIRECT_ACTION.equals(intent.getAction())) {
            sendResult(-1, intent);
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (this.shouldCloseCustomTab) {
            sendResult(0, (Intent) null);
        }
        this.shouldCloseCustomTab = true;
    }

    private void sendResult(int resultCode, Intent resultIntent) {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.redirectReceiver);
        if (resultIntent != null) {
            setResult(resultCode, resultIntent);
        } else {
            setResult(resultCode);
        }
        finish();
    }
}

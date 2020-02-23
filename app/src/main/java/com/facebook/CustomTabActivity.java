package com.facebook;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

public class CustomTabActivity extends Activity {
    public static final String CUSTOM_TAB_REDIRECT_ACTION = (CustomTabActivity.class.getSimpleName() + ".action_customTabRedirect");
    private static final int CUSTOM_TAB_REDIRECT_REQUEST_CODE = 2;
    public static final String DESTROY_ACTION = (CustomTabActivity.class.getSimpleName() + ".action_destroy");
    private BroadcastReceiver closeReceiver;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, CustomTabMainActivity.class);
        intent.setAction(CUSTOM_TAB_REDIRECT_ACTION);
        intent.putExtra(CustomTabMainActivity.EXTRA_URL, getIntent().getDataString());
        intent.addFlags(603979776);
        startActivityForResult(intent, 2);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            Intent broadcast = new Intent(CUSTOM_TAB_REDIRECT_ACTION);
            broadcast.putExtra(CustomTabMainActivity.EXTRA_URL, getIntent().getDataString());
            LocalBroadcastManager.getInstance(this).sendBroadcast(broadcast);
            this.closeReceiver = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    CustomTabActivity.this.finish();
                }
            };
            LocalBroadcastManager.getInstance(this).registerReceiver(this.closeReceiver, new IntentFilter(DESTROY_ACTION));
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.closeReceiver);
        super.onDestroy();
    }
}

package com.beetalk.sdk.plugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.beetalk.sdk.GGLoginSession;
import com.beetalk.sdk.GGPlatform;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.plugin.impl.fb.BaseFBPlugin;

public class GGPluginActivity extends Activity {
    /* access modifiers changed from: private */
    public GGPlugin mPlugin;
    private String mPluginId;

    public static void startWithPlugin(Activity activity, String key, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity, GGPluginActivity.class);
        intent.putExtra("plugin_id", key);
        activity.startActivityForResult(intent, requestCode);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (GGLoginSession.getCurrentSession() == null) {
            GGPlatform.getLastLoginSession(this);
        }
        this.mPluginId = getIntent().getStringExtra("plugin_id");
        this.mPlugin = GGPluginManager.getInstance().getPlugin(this.mPluginId);
        if (savedInstanceState == null) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                public void run() {
                    GGPluginManager.getInstance().realExecute(GGPluginActivity.this.mPlugin, GGPluginActivity.this);
                }
            }, 140);
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!TextUtils.isEmpty(this.mPluginId) && this.mPluginId.startsWith("facebook")) {
            try {
                if (((BaseFBPlugin) this.mPlugin).onFBActivityResult(requestCode, resultCode, data)) {
                    return;
                }
            } catch (Exception e) {
                BBLogger.e(e);
            }
        }
        if (this.mPlugin.getRequestCode() != null && requestCode == this.mPlugin.getRequestCode().intValue() && this.mPlugin.onActivityResult(this, resultCode, data)) {
            finish();
        }
    }
}

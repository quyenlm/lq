package com.tencent.imsdk;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import com.tencent.imsdk.tool.etc.IMLogger;

public class IMProxyActivity extends Activity {
    private IMProxyRunner proxyRunnerInstance;
    private IMProxyTask task = null;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        this.proxyRunnerInstance = IMProxyRunner.getInstance();
        this.task = this.proxyRunnerInstance.getTask();
        if (this.task == null) {
            this.proxyRunnerInstance.setProxyRunning(false);
            finish();
            return;
        }
        this.task.onPostProxy(this);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.task != null) {
            this.task.onActivityResult(requestCode, resultCode, data);
        }
        IMLogger.d("finish() is execute");
        finish();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        IMLogger.d("Should execute the left proxy task");
        if (this.task != null) {
            this.task.onDestroy();
        }
        this.proxyRunnerInstance.setProxyRunning(false);
        this.proxyRunnerInstance.startProxyTask(this.proxyRunnerInstance.getTask());
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        if (this.task != null) {
            this.task.onPause(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (this.task != null) {
            this.task.onResume(this);
        }
    }
}

package com.tencent.imsdk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import com.tencent.imsdk.tool.etc.IMLogger;

public class IMPermissionProxyActivity extends Activity {
    private IMPermissionProxyRunner proxyRunnerInstance;
    private IMPermissionProxyTask task = null;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        this.proxyRunnerInstance = IMPermissionProxyRunner.getInstance();
        this.task = this.proxyRunnerInstance.getTask();
        if (this.task == null) {
            this.proxyRunnerInstance.setProxyRunning(false);
            finish();
            return;
        }
        this.task.onPostProxy(this);
    }

    @SuppressLint({"Override"})
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (this.task != null) {
            this.task.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        IMLogger.d("finish() is execute");
        finish();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.proxyRunnerInstance.setProxyRunning(false);
        this.proxyRunnerInstance.startProxyTask(this.proxyRunnerInstance.getTask());
    }
}

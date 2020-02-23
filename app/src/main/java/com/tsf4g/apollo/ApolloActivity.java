package com.tsf4g.apollo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import com.tencent.tp.r;
import com.tsf4g.tx.TXLog;

public class ApolloActivity extends Activity {
    private static final int REQUEST_PERMISSION = 100;
    private static final String tag = "ApolloActivity";
    private boolean bEnableRequestPermission = true;
    private Object info;

    static {
        System.loadLibrary("apollo");
    }

    protected ApolloActivity() {
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        TXLog.i(tag, "onConfigurationChanged");
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        TXLog.i(tag, "onCreate");
        if (this.bEnableRequestPermission) {
            int checkSelfPermission = ContextCompat.checkSelfPermission(this, r.a);
            int checkSelfPermission2 = ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
            if (checkSelfPermission == 0 && checkSelfPermission2 == 0) {
                TXLog.i(tag, "READ_PHONE_STATE and WRITE_EXTERNAL_STORAGE permission granted");
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, r.a)) {
                    TXLog.i(tag, "User denied last request for READ_PHONE_STATE Permission");
                }
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                    TXLog.i(tag, "User denied last request for WRITE_EXTERNAL_STORAGE Permission");
                }
                TXLog.i(tag, "ApolloPlayerActivity onCreate, requestPermissions: READ_PHONE_STATE && WRITE_EXTERNAL_STORAGE");
                ActivityCompat.requestPermissions(this, new String[]{r.a, "android.permission.WRITE_EXTERNAL_STORAGE"}, 100);
            }
        }
        if (!Apollo.Instance.Initialize(this, this.info)) {
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        TXLog.i(tag, "onDestroy");
        Apollo.Instance.OnDestroy(this);
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        TXLog.i(tag, "onNewIntent");
        Apollo.Instance.HandleCallback(intent);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        Apollo.Instance.OnPause();
    }

    @TargetApi(23)
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        TXLog.i(tag, "onRequestPermissionsResult: " + i);
        switch (i) {
            case 100:
                if (strArr.length <= 0 || iArr.length <= 0) {
                    TXLog.e(tag, "onRequestPermissionsResult: permissions or grantResults is empty");
                    return;
                }
                for (int i2 = 0; i2 < strArr.length; i2++) {
                    if (iArr[0] == 0) {
                        TXLog.i(tag, "onRequestPermissionsResult: " + strArr[i2] + " Granted!");
                    } else {
                        TXLog.i(tag, "onRequestPermissionsResult: " + strArr[i2] + " Denied!");
                    }
                }
                return;
            default:
                super.onRequestPermissionsResult(i, strArr, iArr);
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Apollo.Instance.OnResume();
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        TXLog.i(tag, "onSaveInstanceState");
    }

    public void setAppInfo(Object obj) {
        this.info = obj;
    }

    public void setEnableRequestPermission(boolean z) {
        this.bEnableRequestPermission = z;
    }
}

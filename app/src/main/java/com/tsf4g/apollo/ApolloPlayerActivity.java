package com.tsf4g.apollo;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.tencent.tp.r;
import com.tsf4g.tx.TXLog;
import com.unity3d.player.UnityPlayerActivity;

public class ApolloPlayerActivity extends UnityPlayerActivity {
    private static final int REQUEST_PERMISSION = 100;
    private static final String tag = "ApolloPlayerActivity";
    private boolean bEnableApolloInit = true;
    private boolean bEnableRequestPermission = true;
    private boolean bEnableTouch = true;
    private Object info;

    static {
        System.loadLibrary("TDataMaster");
        System.loadLibrary("apollo");
    }

    protected ApolloPlayerActivity() {
    }

    public void SetEnableTouch(boolean z) {
        this.bEnableTouch = z;
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (!this.bEnableTouch) {
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (!this.bEnableTouch) {
            return true;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        try {
            super.onActivityResult(i, i2, intent);
            if (this.bEnableApolloInit) {
                Apollo.Instance.OnActivityResult(i, i2, intent);
            }
        } catch (Exception e) {
            TXLog.w("Exception", "OnActivityResult Exception:" + e.toString());
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        try {
            super.onConfigurationChanged(configuration);
        } catch (Exception e) {
            TXLog.w("Exception", "onConfigurationChanged Exception:" + e.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        TXLog.i(tag, "ApolloPlayerActivity onCreate, bEnableApolloInit:" + this.bEnableApolloInit);
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
        if (this.bEnableApolloInit && !Apollo.Instance.Initialize(this, this.info)) {
            TXLog.i(tag, "Warning!Reduplicate game activity was detected.Activity will finish immediately.");
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        try {
            super.onDestroy();
            if (this.bEnableApolloInit) {
                Apollo.Instance.OnDestroy(this);
            }
        } catch (Exception e) {
            TXLog.w("Exception", "onDestroy Exception:" + e.toString());
        }
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if (!this.bEnableTouch) {
            return true;
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (!this.bEnableTouch) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (!this.bEnableTouch) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        try {
            super.onNewIntent(intent);
            if (this.bEnableApolloInit) {
                Apollo.Instance.HandleCallback(intent);
            }
        } catch (Exception e) {
            TXLog.w("Exception", "onNewIntent Exception:" + e.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        try {
            super.onPause();
            if (this.bEnableApolloInit) {
                Apollo.Instance.OnPause();
            }
        } catch (Exception e) {
            TXLog.w("Exception", "onPause Exception:" + e.toString());
        }
    }

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
    public void onRestart() {
        try {
            super.onRestart();
            if (this.bEnableApolloInit) {
                Apollo.Instance.OnRestart(this);
            }
        } catch (Exception e) {
            TXLog.w("Exception", "onRestart Exception:" + e.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        try {
            super.onResume();
            if (this.bEnableApolloInit) {
                Apollo.Instance.OnResume();
            }
        } catch (Exception e) {
            TXLog.w("Exception", "onResume Exception:" + e.toString());
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        try {
            super.onSaveInstanceState(bundle);
        } catch (Exception e) {
            TXLog.w("Exception", "onSaveInstanceState Exception:" + e.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        try {
            super.onStart();
            if (this.bEnableApolloInit) {
                Apollo.Instance.OnStart(this);
            }
        } catch (Exception e) {
            TXLog.w("Exception", "onStart Exception:" + e.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        try {
            super.onStop();
            if (this.bEnableApolloInit) {
                Apollo.Instance.OnStop(this);
            }
        } catch (Exception e) {
            TXLog.w("Exception", "onStop Exception:" + e.toString());
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.bEnableTouch) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setApolloLog(int i) {
        TXLog.setLogLevel(i);
    }

    public void setAppInfo(Object obj) {
        this.info = obj;
    }

    public void setEnableApolloInit(boolean z) {
        this.bEnableApolloInit = z;
    }

    public void setEnableRequestPermission(boolean z) {
        this.bEnableRequestPermission = z;
    }
}

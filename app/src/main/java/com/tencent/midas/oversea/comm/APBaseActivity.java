package com.tencent.midas.oversea.comm;

import android.support.v4.app.FragmentActivity;

public class APBaseActivity extends FragmentActivity {
    /* access modifiers changed from: protected */
    public void onDestroy() {
        APUICommonMethod.dissMessageDialog(this);
        APUICommonMethod.dismissWaitDialog();
        APLog.i("APBaseActivity", "onDestroy");
        super.onDestroy();
    }
}

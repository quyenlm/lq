package com.tencent.midas.oversea.business;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import com.tencent.midas.oversea.comm.APActivityResult;
import com.tencent.midas.oversea.comm.APBaseActivity;

public class APProxyMallActivity extends APBaseActivity {
    public void onActivityResult(int i, int i2, Intent intent) {
        Log.d("", "onActivityResult(" + i + "," + i2 + "," + intent);
        Message obtainMessage = APPayMananger.singleton().getCurHandler().obtainMessage();
        obtainMessage.obj = new APActivityResult(i, i2, intent);
        obtainMessage.what = 57;
        APPayMananger.singleton().getCurHandler().sendMessage(obtainMessage);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (APPayMananger.singleton().getCurHandler() == null) {
            finish();
            return;
        }
        Message obtainMessage = APPayMananger.singleton().getCurHandler().obtainMessage();
        obtainMessage.obj = this;
        obtainMessage.what = 22;
        APPayMananger.singleton().getCurHandler().sendMessage(obtainMessage);
    }
}

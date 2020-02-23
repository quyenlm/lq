package com.tencent.qqgamemi;

import android.content.Context;
import android.content.Intent;
import com.tencent.component.plugin.PluginProxyReceiver;

public class QmiPluginTreeReceiver extends PluginProxyReceiver {
    public void onReceive(Context context, Intent intent) {
        QmiCorePluginManager.init(context.getApplicationContext());
        super.onReceive(context, intent);
    }

    /* access modifiers changed from: protected */
    public boolean startPlatform(Context context) {
        SDKApiHelper.getInstance().invokeQmiWriteCmd("qmi.simulateStartQmi", context);
        return true;
    }
}

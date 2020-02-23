package com.tencent.imsdk.webview.qq;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;
import android.support.annotation.Nullable;
import com.tencent.imsdk.tool.etc.IMLogger;

public class WebViewService extends Service {
    private static Messenger sMessenger = null;

    protected static void setMessenger(Messenger messenger) {
        sMessenger = messenger;
    }

    public void onCreate() {
        super.onCreate();
        IMLogger.d("onCreate");
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        if (sMessenger == null) {
            return null;
        }
        IMLogger.d("onBind");
        return sMessenger.getBinder();
    }

    public boolean onUnbind(Intent intent) {
        IMLogger.d("onUnbind");
        return super.onUnbind(intent);
    }

    public void onDestroy() {
        super.onDestroy();
        IMLogger.d("onDestroy");
    }
}

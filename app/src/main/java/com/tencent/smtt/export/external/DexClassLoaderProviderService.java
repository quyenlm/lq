package com.tencent.smtt.export.external;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import java.util.ArrayList;

public class DexClassLoaderProviderService extends Service {
    private static final String LOGTAG = "dexloader";

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        DexClassLoaderProvider.setForceLoadDexFlag(true, this);
    }

    public void onDestroy() {
        System.exit(0);
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        ArrayList<String> stringArrayListExtra;
        if (!(intent == null || (stringArrayListExtra = intent.getStringArrayListExtra("dex2oat")) == null)) {
            String str = stringArrayListExtra.get(0);
            DexClassLoaderProvider.createDexClassLoader(stringArrayListExtra.get(1), stringArrayListExtra.get(2), stringArrayListExtra.get(3), getClassLoader(), getApplicationContext());
        }
        return 1;
    }
}

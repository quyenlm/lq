package android.support.multidex;

import android.content.Context;
import com.tencent.tpshell.TPShellApplication;

public class MultiDexApplication extends TPShellApplication {
    /* access modifiers changed from: protected */
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}

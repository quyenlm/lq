package com.tsf4g.apollo;

import android.app.Activity;
import android.content.Intent;
import com.tsf4g.tx.TXLog;

public abstract class ApolloPlugin {
    public abstract void HandleCallback(Intent intent);

    public boolean Install() {
        TXLog.i("ApolloPlugin", "Install()");
        ApolloPluginManager.Instance.AddPlugin(this);
        TXLog.i("ApolloPlugin", "Install() end");
        return true;
    }

    public abstract void OnActivityResult(int i, int i2, Intent intent);

    public abstract void OnDestroy(Activity activity);

    public abstract boolean OnInitialize(Activity activity, Object obj);

    public abstract void OnPause();

    public abstract void OnRestart(Activity activity);

    public abstract void OnResume();

    public abstract void OnStart(Activity activity);

    public abstract void OnStop(Activity activity);
}

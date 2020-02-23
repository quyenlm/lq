package com.tsf4g.apollo;

import android.app.Activity;
import android.content.Intent;
import com.tsf4g.tx.TXLog;
import java.util.ArrayList;
import java.util.List;

public class ApolloPluginManager {
    public static final ApolloPluginManager Instance = new ApolloPluginManager();
    List<ApolloPlugin> list = new ArrayList();
    ApolloPlugin plugin = null;

    private ApolloPluginManager() {
    }

    public void AddPlugin(ApolloPlugin apolloPlugin) {
        TXLog.i("ApolloPlugin", "AddPlugin():" + (apolloPlugin != null));
        this.plugin = apolloPlugin;
        if (apolloPlugin != null) {
            this.list.add(apolloPlugin);
            TXLog.i("ApolloPlugin", "list size: " + this.list.size());
        }
    }

    public void HandleCallback(Intent intent) {
        if (this.plugin != null) {
            this.plugin.HandleCallback(intent);
        }
    }

    public boolean InitializePlugin(Activity activity, Object obj) {
        if (this.plugin != null) {
            TXLog.e("InitializePlugin", "Plugin != null");
        }
        if (this.list.size() != 0) {
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= this.list.size()) {
                    break;
                }
                ApolloPlugin apolloPlugin = this.list.get(i2);
                if (apolloPlugin != null) {
                    apolloPlugin.OnInitialize(activity, obj);
                }
                i = i2 + 1;
            }
        } else {
            TXLog.i("InitializePlugin", "Plugin list is empty");
        }
        return true;
    }

    public void OnActivityResult(int i, int i2, Intent intent) {
        if (this.plugin != null) {
            this.plugin.OnActivityResult(i, i2, intent);
        }
    }

    public void OnDestroy(Activity activity) {
        TXLog.i("", "Apollo onDestroy");
        if (this.plugin != null) {
            this.plugin.OnDestroy(activity);
        }
    }

    public void OnPause() {
        TXLog.i("", "Apollo onPause");
        if (this.plugin != null) {
            this.plugin.OnPause();
        }
    }

    public void OnRestart(Activity activity) {
        TXLog.i("", "Apollo onRestart");
        if (this.plugin != null) {
            this.plugin.OnRestart(activity);
        }
    }

    public void OnResume() {
        TXLog.i("", "Apollo onResume");
        if (this.plugin != null) {
            this.plugin.OnResume();
        }
    }

    public void OnStart(Activity activity) {
        TXLog.i("", "Apollo OnStart");
        if (this.plugin != null) {
            this.plugin.OnStart(activity);
        }
    }

    public void OnStop(Activity activity) {
        TXLog.i("", "Apollo OnStop");
        if (this.plugin != null) {
            this.plugin.OnStop(activity);
        }
    }
}

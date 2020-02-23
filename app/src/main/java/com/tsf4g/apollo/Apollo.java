package com.tsf4g.apollo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.tencent.tdm.TDataMaster;
import com.tsf4g.tx.ConnectionChangeReceiver;
import com.tsf4g.tx.TX;
import com.tsf4g.tx.TXLog;

public class Apollo {
    public static final Apollo Instance = new Apollo();
    private static final String NetTag = "checkNetworkState";
    private static Context currentContext = null;
    private static final String tag = "Apollo";
    String strJsonConfig = null;

    static {
        System.loadLibrary("TDataMaster");
        System.loadLibrary("apollo");
    }

    private Apollo() {
        TXLog.i(tag, "Apollo()");
    }

    public static int GetResID(String str, String str2) {
        try {
            return currentContext.getResources().getIdentifier(str, str2, currentContext.getPackageName());
        } catch (Exception e) {
            TXLog.e(tag, "GetResID " + str + " Error");
            return 0;
        }
    }

    private native void apolloInit(Object obj, Activity activity, Context context, String str);

    private native void apolloPause();

    private native void apolloResume();

    private int checkNetworkState() {
        int i;
        try {
            if (currentContext == null) {
                return 0;
            }
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) currentContext.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                TXLog.i(NetTag, "ApolloNetInfo : not null");
                switch (activeNetworkInfo.getType()) {
                    case 0:
                        TXLog.i(NetTag, "Network Type : MOBILE");
                        i = 1;
                        break;
                    case 1:
                        TXLog.i(NetTag, "Network Type : WIFI");
                        i = 2;
                        break;
                    default:
                        TXLog.i(NetTag, "Network Type : Other Network Type");
                        i = 0;
                        break;
                }
            } else {
                TXLog.i(NetTag, "ApolloNetInfo : null. All Networks are disabled");
                i = 0;
            }
            return i;
        } catch (Exception e) {
            TXLog.i("Exception", "Apollo check" + e.toString());
            i = 0;
        }
    }

    public void HandleCallback(Intent intent) {
        try {
            ApolloPluginManager.Instance.HandleCallback(intent);
        } catch (Exception e) {
            TXLog.i(tag, "HandleCallback exception:" + e);
        }
    }

    public boolean Initialize(Activity activity, Object obj) {
        TXLog.i(tag, "TX Init");
        currentContext = activity.getApplicationContext();
        TX.Instance.Initialize(activity);
        TXLog.i(tag, "TDM Init");
        TDataMaster.getInstance().initialize(currentContext);
        TXLog.i(tag, "Apollo Init");
        apolloInit(obj, activity, activity.getBaseContext(), activity.getBaseContext().getDir("tomb", 0).getAbsolutePath());
        ConnectionChangeReceiver connectionChangeReceiver = new ConnectionChangeReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        activity.registerReceiver(connectionChangeReceiver, intentFilter);
        boolean InitializePlugin = ApolloPluginManager.Instance.InitializePlugin(activity, obj);
        Instance.HandleCallback(activity.getIntent());
        return InitializePlugin;
    }

    public void OnActivityResult(int i, int i2, Intent intent) {
        TXLog.i(tag, "OnActivityResult requestCode:" + i + " resultCode:" + i2);
        try {
            ApolloPluginManager.Instance.OnActivityResult(i, i2, intent);
            TDataMaster.getInstance().onActivityResult(i, i2, intent);
        } catch (Exception e) {
            TXLog.i(tag, "OnActivityResult exception:" + e);
        }
    }

    public void OnDestroy(Activity activity) {
        TXLog.i(tag, "onDestroy");
        try {
            ApolloPluginManager.Instance.OnDestroy(activity);
            TDataMaster.getInstance().onDestroy();
        } catch (Exception e) {
            TXLog.i(tag, "OnDestroy exception:" + e);
        }
    }

    public void OnPause() {
        TXLog.i(tag, "onPause");
        try {
            ApolloPluginManager.Instance.OnPause();
            TDataMaster.getInstance().onPause();
            apolloPause();
        } catch (Exception e) {
            TXLog.i(tag, "onPause exception:" + e);
        }
    }

    public void OnRestart(Activity activity) {
        TXLog.i(tag, "OnRestart");
        try {
            ApolloPluginManager.Instance.OnRestart(activity);
            TDataMaster.getInstance().onRestart();
        } catch (Exception e) {
            TXLog.i(tag, "OnRestart exception:" + e);
        }
    }

    public void OnResume() {
        TXLog.i(tag, "onResume");
        try {
            ApolloPluginManager.Instance.OnResume();
            TDataMaster.getInstance().onResume();
            apolloResume();
        } catch (Exception e) {
            TXLog.i(tag, "onResume exception:" + e);
        }
    }

    public void OnStart(Activity activity) {
        TXLog.i(tag, "OnStart");
        try {
            ApolloPluginManager.Instance.OnStart(activity);
            TDataMaster.getInstance().onStart();
        } catch (Exception e) {
            TXLog.i(tag, "OnStart exception:" + e);
        }
    }

    public void OnStop(Activity activity) {
        TXLog.i(tag, "OnStop");
        try {
            ApolloPluginManager.Instance.OnStop(activity);
            TDataMaster.getInstance().onStop();
        } catch (Exception e) {
            TXLog.i(tag, "OnStop exception:" + e);
        }
    }
}

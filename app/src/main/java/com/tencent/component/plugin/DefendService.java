package com.tencent.component.plugin;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;
import com.tencent.component.UtilitiesInitial;
import com.tencent.component.utils.log.LogUtil;

public class DefendService extends Service {
    public static final String DEFEND_PLUGIN_DELAY_TIME = "_defend_service_delay_time";
    public static final String DEFEND_PLUGIN_ID = "_defend_service_plugin_id";
    public static final String DEFEND_STARTGAME_PKGNAME = "_defend_service_startgame_pkgname";
    public static final String DELAY_STARTGAME_ACTION = "com.tencent.component.platform.startgame";
    private static final int MSG_START_GAME = 1;
    private static final String TAG = "DefendService";
    /* access modifiers changed from: private */
    public Context cachedContext = null;
    private Handler mDelayStartGameHandler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle extras;
            if (msg != null) {
                switch (msg.what) {
                    case 1:
                        LogUtil.d(DefendService.TAG, "receive msg:1");
                        if (msg.obj != null && (extras = (Bundle) msg.obj) != null) {
                            String string = extras.getString(DefendService.DEFEND_PLUGIN_ID);
                            String pkgName = extras.getString(DefendService.DEFEND_STARTGAME_PKGNAME);
                            extras.remove(DefendService.DEFEND_PLUGIN_ID);
                            extras.remove(DefendService.DEFEND_STARTGAME_PKGNAME);
                            DefendService.this.startGame(pkgName, DefendService.this.cachedContext, extras);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    };

    /* access modifiers changed from: protected */
    public void onReceiveStartGameAction(Context context, Intent intent) {
        LogUtil.d(TAG, "onReceiveStartGameAction");
        Bundle extras = intent.getExtras();
        if (extras != null) {
            long delayMillis = extras.getLong(DEFEND_PLUGIN_DELAY_TIME);
            if (delayMillis < 0) {
                delayMillis = 0;
            }
            extras.remove(DEFEND_PLUGIN_DELAY_TIME);
            this.cachedContext = context;
            Message msg = Message.obtain();
            msg.what = 1;
            msg.obj = extras;
            LogUtil.d(TAG, "onReceiveStartGameAction delayMillis:" + delayMillis);
            Toast.makeText(context.getApplicationContext(), "获取账号信息成功,将在3秒内重启游戏,请耐心等待", 1).show();
            this.mDelayStartGameHandler.sendMessageDelayed(msg, delayMillis);
        }
    }

    /* access modifiers changed from: private */
    public void startGame(String pkgName, Context context, Bundle extras) {
        if (!TextUtils.isEmpty(pkgName) && context != null) {
            try {
                PackageManager pm = context.getPackageManager();
                if (pm != null) {
                    Intent mainIntent = pm.getLaunchIntentForPackage(pkgName);
                    if (mainIntent == null && pm.getPackageInfo(pkgName, 1) != null) {
                        mainIntent = new Intent(pkgName);
                    }
                    if (mainIntent != null) {
                        mainIntent.setFlags(270532608);
                        mainIntent.putExtras(extras);
                        context.startActivity(mainIntent);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        LogUtil.d(TAG, "DefendService onCreate");
        UtilitiesInitial.init(getApplicationContext());
        super.onCreate();
    }

    @Deprecated
    public void onStart(Intent intent, int startId) {
        LogUtil.d(TAG, "DefendService onStart");
        super.onStart(intent, startId);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d(TAG, "DefendService onStartCommand");
        if (intent != null) {
            String action = intent.getAction();
            LogUtil.d(TAG, "onStartCommand" + action);
            if (DELAY_STARTGAME_ACTION.equals(action)) {
                onReceiveStartGameAction(getApplicationContext(), intent);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }
}

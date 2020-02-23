package com.garena.android.gpns.strategy;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import com.garena.android.gpns.GNotificationService;
import com.garena.android.gpns.utility.AppLogger;

public class RemoteQueryReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        int action = intent.getIntExtra(CompetitiveBootStrategy.INTENT_ACTION_KEY, 0);
        AppLogger.d(context.getPackageName() + " === remote query received:" + action);
        if (action == 1) {
            String senderPackageName = intent.getStringExtra(CompetitiveBootStrategy.INTENT_COMPONENT_NAME);
            ComponentName serviceComponent = new ComponentName(context, GNotificationService.class.getCanonicalName());
            Intent info = new Intent(CompetitiveBootStrategy.INTENT_QUERY_RESPONSE);
            info.putExtra(CompetitiveBootStrategy.INTENT_COMPONENT_NAME, serviceComponent);
            info.putExtra(CompetitiveBootStrategy.INTENT_STATUS, GNotificationService.IS_RUNNING);
            info.putExtra(CompetitiveBootStrategy.INTENT_QUERY_VERSION, 4);
            info.putExtra(CompetitiveBootStrategy.INTENT_PROCESS_ID, Process.myPid());
            info.setPackage(senderPackageName);
            AppLogger.d(context.getPackageName() + " === remote response deliver to " + senderPackageName);
            context.sendBroadcast(info);
        } else if (action == 2) {
            Intent killing = new Intent(GNotificationService.INTENT_LOCAL_ACTION);
            killing.putExtra("command", 1);
            killing.setPackage(context.getPackageName());
            context.sendBroadcast(killing);
        }
    }
}

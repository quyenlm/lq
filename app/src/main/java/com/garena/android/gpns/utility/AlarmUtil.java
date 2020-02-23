package com.garena.android.gpns.utility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import com.garena.android.gpns.utility.CONSTANT;

public final class AlarmUtil {
    public static void scheduleShortPing(Context context) {
        ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).setInexactRepeating(3, SystemClock.elapsedRealtime() + ((long) 60000), (long) 60000, initIntent(context, 0));
    }

    public static void scheduleLongPing(Context context) {
        ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).setInexactRepeating(2, SystemClock.elapsedRealtime() + ((long) CONSTANT.TIME.MIN_5), (long) CONSTANT.TIME.MIN_5, initIntent(context, 1));
    }

    public static void scheduleWakeConnect(Context context, int delayMs) {
        ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).set(2, SystemClock.elapsedRealtime() + ((long) delayMs), initIntent(context, 2));
    }

    public static void cancelShortPing(Context context) {
        ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).cancel(initIntent(context, 0));
    }

    public static void cancelLongPing(Context context) {
        ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).cancel(initIntent(context, 1));
    }

    private static PendingIntent initIntent(Context context, int alarmId) {
        Bundle data = new Bundle();
        data.putInt(CONSTANT.ALARM.KEY_ALARM_TYPE, alarmId);
        Intent intent = new Intent(CONSTANT.ACTION.ACTION_ALARM + ServiceUtils.getAppId(context));
        AppLogger.d("AppId: com.garena.android.gpns.ALARM_ACTION" + ServiceUtils.getAppId(context));
        intent.putExtra(CONSTANT.ALARM.DATA_BUNDLE, data);
        return PendingIntent.getBroadcast(context, alarmId, intent, 134217728);
    }

    private AlarmUtil() {
    }

    public static void cancelWakeConnect(Context context) {
        ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).cancel(initIntent(context, 2));
    }

    public static void scheduleLogAlarm(Context context) {
        ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).setInexactRepeating(2, SystemClock.elapsedRealtime() + 180000, 3600000, PendingIntent.getBroadcast(context, 5, new Intent("com.garena.android.gpns.LOGGING_ACTION"), 134217728));
    }
}

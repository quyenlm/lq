package com.garena.android.push;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.helper.BBLogger;
import com.garena.android.DefaultNotificationReceiver;
import com.garena.msdk.R;
import com.google.android.gms.drive.DriveFile;
import org.json.JSONObject;

public class NotificationData {
    public static final String CHANNEL_ID = "com.garena.sdk.CHANNEL_ID_DEFAULT";
    private static final float LARGE_ICON_SIZE = 32.0f;
    private final String message;
    private final int notificationId;
    private final String packageName;
    private final String title;

    public NotificationData(String message2, String packageName2, int notificationId2, String title2) {
        this.message = message2;
        this.packageName = packageName2;
        this.notificationId = notificationId2;
        this.title = title2;
    }

    private Bitmap convertToBitmap(Drawable drawable, int widthPixels, int heightPixels) {
        Bitmap mutableBitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        drawable.setBounds(0, 0, widthPixels, heightPixels);
        drawable.draw(canvas);
        return mutableBitmap;
    }

    public void queueNotification(Context context) {
        if (context != null) {
            createChannel(context);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
            builder.setAutoCancel(true);
            builder.setContentTitle(this.title);
            builder.setContentText(this.message);
            Drawable largeIcon = loadLargeLogo(context);
            if (largeIcon != null) {
                int defaultSize = (int) TypedValue.applyDimension(1, LARGE_ICON_SIZE, context.getResources().getDisplayMetrics());
                int w = largeIcon.getIntrinsicWidth();
                int h = largeIcon.getIntrinsicHeight();
                if (w == 0) {
                    w = defaultSize;
                }
                if (h == 0) {
                    h = defaultSize;
                }
                builder.setLargeIcon(convertToBitmap(largeIcon, w, h));
            }
            int smallIconRes = context.getResources().getIdentifier("ic_launcher", "drawable", context.getPackageName());
            if (smallIconRes == 0) {
                smallIconRes = R.drawable.ic_launcher;
            }
            builder.setSmallIcon(smallIconRes);
            Intent resultIntent = context.getPackageManager().getLaunchIntentForPackage(context.getApplicationContext().getPackageName());
            resultIntent.setAction("android.intent.action.MAIN");
            resultIntent.addCategory("android.intent.category.LAUNCHER");
            resultIntent.addFlags(DriveFile.MODE_WRITE_ONLY);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent, 134217728);
            if (Build.VERSION.SDK_INT >= 19) {
                pendingIntent.cancel();
            }
            builder.setContentIntent(PendingIntent.getActivity(context, 0, resultIntent, 134217728));
            NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
            if (notificationManager != null) {
                notificationManager.notify(this.notificationId, builder.build());
            }
        }
    }

    public static void createChannel(Context context) {
        String name;
        if (Build.VERSION.SDK_INT >= 26) {
            int channelNameRes = context.getResources().getIdentifier("com_garena_sdk_default_notification_channel_name", "string", context.getPackageName());
            if (channelNameRes > 0) {
                name = context.getString(channelNameRes);
            } else {
                name = getApplicationName(context);
            }
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, 3);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    public void scheduleNotification(Context context, long milis) {
        PendingIntent pendingIntent = initIntent(context, this.notificationId);
        BBLogger.d("Alarm %d %d", Long.valueOf(System.currentTimeMillis()), Long.valueOf(milis));
        ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).set(0, milis, pendingIntent);
    }

    private PendingIntent initIntent(Context context, int alarmId) {
        Intent intent = new Intent(context, DefaultNotificationReceiver.class);
        Bundle bundle = new Bundle();
        try {
            JSONObject object = new JSONObject();
            object.put("content", this.message);
            object.put(DefaultNotificationReceiver.KEY_PARAMETER, this.title);
            bundle.putString("DATA", object.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        bundle.putInt(DefaultNotificationReceiver.NOTIFY_ID, this.notificationId);
        intent.putExtras(bundle);
        return PendingIntent.getBroadcast(context, alarmId, intent, DriveFile.MODE_READ_ONLY);
    }

    private Drawable loadLargeLogo(Context context) {
        String drawableName = null;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (info.metaData != null) {
                drawableName = info.metaData.getString(SDKConstants.NOTIFICATION_LARGE_LOGO_PROPERTY);
            }
        } catch (Exception e) {
            BBLogger.e(e);
        }
        if (TextUtils.isEmpty(drawableName)) {
            try {
                return context.getPackageManager().getApplicationIcon(this.packageName);
            } catch (PackageManager.NameNotFoundException e2) {
                BBLogger.e(e2);
            }
        } else {
            try {
                return context.getResources().getDrawable(context.getResources().getIdentifier(drawableName, "drawable", context.getPackageName()));
            } catch (Resources.NotFoundException e3) {
                BBLogger.e(e3);
            }
        }
        return null;
    }

    private static String getApplicationName(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
    }

    public static final class NotificationBuilder {
        private String message;
        private int notificationId;
        private String packageName;
        private String title;

        public NotificationBuilder setMessage(String message2) {
            this.message = message2;
            return this;
        }

        public NotificationBuilder setPackageName(String packageName2) {
            this.packageName = packageName2;
            return this;
        }

        public NotificationBuilder setNotificationId(int notificationId2) {
            this.notificationId = notificationId2;
            return this;
        }

        public NotificationBuilder setTitle(String title2) {
            this.title = title2;
            return this;
        }

        public NotificationData build() {
            return new NotificationData(this.message, this.packageName, this.notificationId, this.title);
        }
    }
}

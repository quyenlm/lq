package com.tencent.component.plugin;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.google.android.gms.drive.DriveFile;
import com.tencent.component.annotation.PluginApi;
import com.tencent.component.utils.ParcelUtil;
import com.tencent.component.utils.log.LogUtil;
import java.util.concurrent.ConcurrentHashMap;

public class PluginNotificationManager {
    private static final String TAG = "PluginNotificationManager";
    private static ConcurrentHashMap<String, PluginNotificationManager> sMaps = new ConcurrentHashMap<>();
    private Class pluginProxyReceiver;

    private PluginNotificationManager(Class pluginProxyReceiver2) {
        this.pluginProxyReceiver = pluginProxyReceiver2;
    }

    @PluginApi(since = 300)
    public static PluginNotificationManager getInstance(Plugin plugin) {
        PluginPlatformConfig pluginPlatformConfig;
        PluginNotificationManager manager = null;
        if (!(plugin == null || (pluginPlatformConfig = plugin.getPluginManager().pluginPlatformConfig) == null)) {
            Class pluginProxyReceiver2 = pluginPlatformConfig.pluginProxyReceiver;
            if (pluginProxyReceiver2 == null) {
                pluginProxyReceiver2 = PluginProxyReceiver.class;
            }
            String key = pluginProxyReceiver2.getName();
            manager = sMaps.get(key);
            if (manager == null) {
                synchronized (PluginNotificationManager.class) {
                    if (manager == null) {
                        try {
                            PluginNotificationManager manager2 = new PluginNotificationManager(pluginProxyReceiver2);
                            try {
                                sMaps.put(key, manager2);
                                manager = manager2;
                            } catch (Throwable th) {
                                th = th;
                                PluginNotificationManager pluginNotificationManager = manager2;
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            throw th;
                        }
                    }
                }
            }
        }
        return manager;
    }

    @PluginApi(since = 300)
    public void cancelNotification(Context context, String notificationId) {
        PluginInfo pluginInfo;
        if (context != null && !TextUtils.isEmpty(notificationId) && (context instanceof PluginContextWrapper) && (pluginInfo = ((PluginContextWrapper) context).getPluginInfo()) != null) {
            Context baseContext = context.getApplicationContext();
            ((NotificationManager) baseContext.getSystemService("notification")).cancel((pluginInfo.pluginId + "_" + notificationId).hashCode());
        }
    }

    private static Notification buildNotification(Context context, PluginNotification pluginNotification) {
        Bitmap bitmap;
        Context baseContext = context.getApplicationContext();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(baseContext);
        int icon = baseContext.getApplicationInfo().icon;
        builder.setSmallIcon(icon);
        if (!TextUtils.isEmpty(pluginNotification.tickerText)) {
            builder.setTicker(pluginNotification.tickerText);
        }
        if (!TextUtils.isEmpty(pluginNotification.contentTitle)) {
            builder.setContentTitle(pluginNotification.contentTitle);
        }
        if (!TextUtils.isEmpty(pluginNotification.contentText)) {
            builder.setContentText(pluginNotification.contentText);
        }
        if (!TextUtils.isEmpty(pluginNotification.subText)) {
            builder.setSubText(pluginNotification.subText);
        }
        if (pluginNotification.progressMax > 0 && pluginNotification.progress >= 0) {
            builder.setProgress(pluginNotification.progressMax, pluginNotification.progress, pluginNotification.progressIndeterminate);
        }
        if (pluginNotification.largeIcon == null) {
            pluginNotification.largeIcon = baseContext.getResources().getDrawable(icon);
        }
        if (!(pluginNotification.largeIcon == null || (bitmap = createBitmapFromDrawable(pluginNotification.largeIcon)) == null)) {
            builder.setLargeIcon(bitmap);
        }
        builder.setOngoing(pluginNotification.ongoing);
        builder.setOnlyAlertOnce(pluginNotification.onlyAlertOnce);
        builder.setAutoCancel(pluginNotification.autoCancel);
        if (pluginNotification.vibratePattern != null) {
            builder.setVibrate(pluginNotification.vibratePattern);
        }
        if (pluginNotification.number != -1) {
            builder.setNumber(pluginNotification.number);
        }
        if (pluginNotification.priority != -1) {
            builder.setPriority(pluginNotification.priority);
        }
        if (pluginNotification.when != -1) {
            builder.setWhen(pluginNotification.when);
        }
        if (pluginNotification.contentIntent != null) {
            builder.setContentIntent(pluginNotification.contentIntent);
        }
        builder.setUsesChronometer(pluginNotification.useChronometer);
        if (pluginNotification.setLigths) {
            builder.setLights(pluginNotification.ledARGB, pluginNotification.ledOnMS, pluginNotification.ledOffMS);
        }
        return builder.build();
    }

    private static Bitmap createBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        Bitmap bitmap = null;
        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            try {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.RGB_565);
                drawable.draw(new Canvas(bitmap));
            } catch (Throwable e) {
                LogUtil.i(TAG, e.getMessage(), e);
            }
        }
        if (bitmap == null || bitmap.isRecycled()) {
            bitmap = null;
        }
        return bitmap;
    }

    @PluginApi(since = 401)
    public void shoNotification(Context context, PluginNotification pluginNotification, String notificationId, Bundle args) {
        shoNotification(context, pluginNotification, notificationId, args, true);
    }

    @PluginApi(since = 300)
    public void shoNotification(Context context, PluginNotification pluginNotification, String notificationId, Bundle args, boolean handleByPlugin) {
        Plugin plugin;
        if (context != null && !TextUtils.isEmpty(notificationId) && pluginNotification != null && (context instanceof PluginContextWrapper) && (plugin = ((PluginContextWrapper) context).getPlugin()) != null) {
            PluginInfo pluginInfo = plugin.getPluginInfo();
            Context baseContext = context.getApplicationContext();
            Notification notification = buildNotification(context, pluginNotification);
            if (handleByPlugin) {
                if (args == null) {
                    args = new Bundle();
                }
                args.setClassLoader(getClass().getClassLoader());
                args.putByteArray(PluginProxyReceiver.PARAMS_PLATFORM_CONFIG, ParcelUtil.writeParcelable(plugin.getPluginManager().pluginPlatformConfig));
                args.putString(PluginProxyReceiver.PARAMS_PLUGIN_ID, pluginInfo.pluginId);
                if (this.pluginProxyReceiver == null) {
                    this.pluginProxyReceiver = PluginProxyReceiver.class;
                }
                Intent intent = new Intent(baseContext, this.pluginProxyReceiver);
                intent.setExtrasClassLoader(getClass().getClassLoader());
                intent.setAction(PluginProxyReceiver.NOTIFICATION_ACTION);
                intent.putExtras(args);
                notification.contentIntent = PendingIntent.getBroadcast(baseContext, 0, intent, DriveFile.MODE_READ_ONLY);
            }
            showNotificationInner(baseContext, notification, pluginInfo.pluginId, notificationId);
        }
    }

    private void showNotificationInner(Context context, Notification notification, String pluginId, String notificationId) {
        ((NotificationManager) context.getSystemService("notification")).notify((pluginId + "_" + notificationId).hashCode(), notification);
    }

    @PluginApi(since = 300)
    public static class PluginNotification {
        @PluginApi(since = 300)
        public boolean autoCancel;
        @PluginApi(since = 300)
        public PendingIntent contentIntent;
        @PluginApi(since = 300)
        public CharSequence contentText;
        @PluginApi(since = 300)
        public CharSequence contentTitle;
        @PluginApi(since = 300)
        public Drawable largeIcon;
        public int ledARGB;
        public int ledOffMS;
        public int ledOnMS;
        @PluginApi(since = 300)
        public int number = -1;
        @PluginApi(since = 300)
        public boolean ongoing;
        @PluginApi(since = 300)
        public boolean onlyAlertOnce;
        @PluginApi(since = 300)
        public int priority = -1;
        @PluginApi(since = 300)
        public int progress = -1;
        @PluginApi(since = 300)
        public boolean progressIndeterminate;
        @PluginApi(since = 300)
        public int progressMax = -1;
        public boolean setLigths;
        @PluginApi(since = 300)
        public CharSequence subText;
        @PluginApi(since = 300)
        public CharSequence tickerText;
        @PluginApi(since = 300)
        public boolean useChronometer;
        @PluginApi(since = 300)
        public long[] vibratePattern;
        @PluginApi(since = 300)
        public long when = -1;

        @PluginApi(since = 300)
        public void setLights(int argb, int onMs, int offMs) {
            this.ledARGB = argb;
            this.ledOnMS = onMs;
            this.ledOffMS = offMs;
            this.setLigths = true;
        }
    }
}

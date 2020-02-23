package com.tencent.component.plugin;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import com.tencent.component.utils.log.LogUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TreeServiceHelper {
    private static final String TAG = "TreeServiceHelper";
    private static final Class<?>[] mSetForegroundSignature = {Boolean.TYPE};
    private static final Class<?>[] mStartForegroundSignature = {Integer.TYPE, Notification.class};
    private static final Class<?>[] mStopForegroundSignature = {Boolean.TYPE};
    private NotificationCompat.Builder builder;
    private boolean isForeground = false;
    private int mContentResId;
    private Service mContext;
    private boolean mHasInit;
    private int mIconResId;
    private NotificationManager mNM;
    private Class mServiceClazz;
    private Method mSetForeground;
    private Object[] mSetForegroundArgs = new Object[1];
    private Method mStartForeground;
    private Object[] mStartForegroundArgs = new Object[2];
    private Method mStopForeground;
    private Object[] mStopForegroundArgs = new Object[1];
    private int mTitleResId;

    public TreeServiceHelper(Service context, int iconResId, int titleResId, int contentResId, Class clazz) {
        this.mContext = context;
        this.mIconResId = iconResId;
        this.mTitleResId = titleResId;
        this.mContentResId = contentResId;
        this.mServiceClazz = clazz;
        this.mNM = (NotificationManager) context.getSystemService("notification");
    }

    private synchronized void init(Context context) {
        if (!this.mHasInit) {
            this.mHasInit = true;
            int iconResId = getIconResId();
            int titleResId = getTitleResId();
            int contentResId = getTitleResId();
            if (iconResId <= 0 || titleResId <= 0 || contentResId <= 0) {
                LogUtil.e(TAG, "invalid resource id , ignore init request...");
            } else {
                this.builder = new NotificationCompat.Builder(context);
                this.builder.setSmallIcon(getIconResId());
                String title = this.mContext.getResources().getString(getTitleResId());
                String content = this.mContext.getResources().getString(getContentResId());
                this.builder.setContentTitle(title);
                this.builder.setContentText(content);
                this.builder.setContentIntent(PendingIntent.getService(context, 0, new Intent(context, this.mServiceClazz), 0));
                this.builder.setTicker(title);
                this.builder.setAutoCancel(true);
                try {
                    this.mStartForeground = this.mServiceClazz.getMethod("startForeground", mStartForegroundSignature);
                    this.mStopForeground = this.mServiceClazz.getMethod("stopForeground", mStopForegroundSignature);
                } catch (NoSuchMethodException e) {
                    LogUtil.w(TAG, "no suchmethod exception", e.fillInStackTrace());
                    this.mStopForeground = null;
                    this.mStartForeground = null;
                    try {
                        this.mSetForeground = this.mServiceClazz.getMethod("setForeground", mSetForegroundSignature);
                    } catch (NoSuchMethodException e2) {
                        LogUtil.w(TAG, "no suchmethod exception2", e2.fillInStackTrace());
                        this.mSetForeground = null;
                    }
                }
            }
        }
        return;
    }

    private int getIconResId() {
        if (this.mIconResId <= 0) {
            try {
                return this.mContext.getApplicationInfo().icon;
            } catch (Exception e) {
            }
        }
        return this.mIconResId;
    }

    private int getTitleResId() {
        if (this.mTitleResId <= 0) {
            try {
                return this.mContext.getApplicationInfo().labelRes;
            } catch (Exception e) {
            }
        }
        return this.mTitleResId;
    }

    private int getContentResId() {
        if (this.mContentResId <= 0) {
            try {
                return this.mContext.getApplicationInfo().labelRes;
            } catch (Exception e) {
            }
        }
        return this.mContentResId;
    }

    public void setForeground() {
        int contentResId;
        if (!this.isForeground) {
            this.isForeground = true;
            init(this.mContext);
            if (this.builder != null && (contentResId = getContentResId()) > 0) {
                startForegroundCompat(contentResId, this.builder.build());
            }
        }
    }

    public void setBackground() {
        if (this.isForeground) {
            this.isForeground = false;
            int contentResId = getContentResId();
            if (contentResId > 0) {
                stopForegroundCompat(contentResId);
            }
        }
    }

    private void startForegroundCompat(int id, Notification notification) {
        if (this.mStartForeground != null) {
            this.mStartForegroundArgs[0] = Integer.valueOf(id);
            this.mStartForegroundArgs[1] = notification;
            invokeMethod(this.mStartForeground, this.mStartForegroundArgs);
        } else if (this.mSetForeground != null) {
            this.mSetForegroundArgs[0] = Boolean.TRUE;
            invokeMethod(this.mSetForeground, this.mSetForegroundArgs);
            this.mNM.notify(id, notification);
        }
    }

    private void stopForegroundCompat(int id) {
        if (this.mStopForeground != null) {
            this.mStopForegroundArgs[0] = Boolean.TRUE;
            invokeMethod(this.mStopForeground, this.mStopForegroundArgs);
        } else if (this.mSetForeground != null) {
            this.mNM.cancel(id);
            this.mSetForegroundArgs[0] = Boolean.FALSE;
            invokeMethod(this.mSetForeground, this.mSetForegroundArgs);
        }
    }

    private void invokeMethod(Method method, Object[] args) {
        try {
            method.invoke(this.mContext, args);
        } catch (InvocationTargetException e) {
            LogUtil.w(TAG, "Unable to invoke method", e);
        } catch (IllegalAccessException e2) {
            LogUtil.w(TAG, "Unable to invoke method", e2);
        }
    }
}

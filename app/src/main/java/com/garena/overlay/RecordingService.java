package com.garena.overlay;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.beetalk.sdk.helper.BBLogger;
import com.garena.android.push.NotificationData;
import com.garena.msdk.R;

public class RecordingService extends Service {
    private static final String EXTRA_DATA = "data";
    private static final String EXTRA_RESULT_CODE = "result-code";
    private static final int NOTIFICATION_ID = 143922;
    public static final String SERVICE_ACTION_STOP = "service_stop";
    public static final String SERVICE_ACTION_VIDEO_END = "video_end";
    public static final String SERVICE_ACTION_VIDEO_START = "video_start";
    public static final String SERVICE_COMMAND = "command";
    private static volatile RecordingService mInstance;
    private RecordingSession recordingSession;

    public static RecordingService getInstance() {
        if (mInstance == null) {
            synchronized (RecordingService.class) {
                if (mInstance == null) {
                    mInstance = new RecordingService();
                }
            }
        }
        return mInstance;
    }

    public static Intent getStartVideoIntent(Context context, int resultCode, Intent data) {
        Intent intent = new Intent(context, RecordingService.class);
        intent.putExtra("command", SERVICE_ACTION_VIDEO_START);
        intent.putExtra(EXTRA_RESULT_CODE, resultCode);
        intent.putExtra("data", data);
        return intent;
    }

    public void onCreate() {
        super.onCreate();
    }

    public void onConfigurationChanged(Configuration newConfig) {
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return super.onStartCommand(intent, flags, startId);
        }
        String command = intent.getStringExtra("command");
        BBLogger.i("RecordingService: startId=" + startId + ", command:" + command, new Object[0]);
        if (!TextUtils.isEmpty(command)) {
            if (SERVICE_ACTION_VIDEO_START.equals(command) && !FloatDotViewUtil.isRecording) {
                int resultCode = intent.getIntExtra(EXTRA_RESULT_CODE, 0);
                Intent data = (Intent) intent.getParcelableExtra("data");
                if (resultCode == 0 || data == null) {
                    throw new IllegalStateException("Result code or data missing.");
                }
                startForeground(NOTIFICATION_ID, getNotification());
                this.recordingSession = new RecordingSession(this, resultCode, data);
                this.recordingSession.showCountDownView();
                FloatDotViewUtil.isRecording = true;
            }
            if (SERVICE_ACTION_VIDEO_END.equals(command) && FloatDotViewUtil.isRecording) {
                this.recordingSession = null;
                FloatDotViewUtil.isRecording = false;
                stopForeground(true);
                stopSelf();
            }
            if (SERVICE_ACTION_STOP.equals(command)) {
                stopForeground(true);
                stopSelf();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        FloatDotViewUtil.isRecording = false;
        FloatDotViewUtil.restoreFloatDot();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    private Notification getNotification() {
        NotificationData.createChannel(this);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationData.CHANNEL_ID);
        builder.setContentTitle(getString(getApplicationInfo().labelRes)).setContentText(getString(getApplicationInfo().labelRes)).setSmallIcon(R.drawable.msdk_record_notification).setAutoCancel(true).setPriority(-2);
        return builder.build();
    }
}

package com.tencent.imsdk.expansion.downloader.impl;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.os.Messenger;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import com.tencent.imsdk.expansion.downloader.Constants;
import com.tencent.imsdk.expansion.downloader.DownloadProgressInfo;
import com.tencent.imsdk.expansion.downloader.DownloaderServiceMarshaller;
import com.tencent.imsdk.expansion.downloader.Helpers;
import com.tencent.imsdk.expansion.downloader.IDownloaderService;
import com.tencent.imsdk.expansion.downloader.IMLogger;
import com.tencent.imsdk.expansion.downloader.IStub;
import com.tencent.imsdk.expansion.downloader.LicenseVerificationBase;
import java.io.File;

public class DownloaderService extends CustomIntentService implements IDownloaderService {
    public static final String ACTION_DOWNLOADS_CHANGED = "downloadsChanged";
    public static final String ACTION_DOWNLOAD_COMPLETE = "lvldownloader.intent.action.DOWNLOAD_COMPLETE";
    public static final String ACTION_DOWNLOAD_STATUS = "lvldownloader.intent.action.DOWNLOAD_STATUS";
    public static final int CONTROL_PAUSED = 1;
    public static final int CONTROL_RUN = 0;
    public static final int DOWNLOAD_REQUIRED = 2;
    public static final String EXTRA_FILE_NAME = "downloadId";
    public static final String EXTRA_IS_WIFI_REQUIRED = "isWifiRequired";
    public static final String EXTRA_MESSAGE_HANDLER = "EMH";
    public static final String EXTRA_PACKAGE_NAME = "EPN";
    public static final String EXTRA_PENDING_INTENT = "EPI";
    public static final String EXTRA_STATUS_CURRENT_FILE_SIZE = "CFS";
    public static final String EXTRA_STATUS_CURRENT_PROGRESS = "CFP";
    public static final String EXTRA_STATUS_STATE = "ESS";
    public static final String EXTRA_STATUS_TOTAL_PROGRESS = "TFP";
    public static final String EXTRA_STATUS_TOTAL_SIZE = "ETS";
    public static final String LOG_TAG = "LVLDL";
    public static final int LVL_CHECK_REQUIRED = 1;
    public static final int NETWORK_CANNOT_USE_ROAMING = 5;
    public static final int NETWORK_MOBILE = 1;
    public static final int NETWORK_NO_CONNECTION = 2;
    public static final int NETWORK_OK = 1;
    public static final int NETWORK_RECOMMENDED_UNUSABLE_DUE_TO_SIZE = 4;
    public static final int NETWORK_TYPE_DISALLOWED_BY_REQUESTOR = 6;
    public static final int NETWORK_UNUSABLE_DUE_TO_SIZE = 3;
    public static final int NETWORK_WIFI = 2;
    public static final int NO_DOWNLOAD_REQUIRED = 0;
    private static final float SMOOTHING_FACTOR = 0.005f;
    public static final int STATUS_CANCELED = 490;
    public static final int STATUS_CANNOT_RESUME = 489;
    public static final int STATUS_DEVICE_NOT_FOUND_ERROR = 499;
    public static final int STATUS_FILE_ALREADY_EXISTS_ERROR = 488;
    public static final int STATUS_FILE_DELIVERED_INCORRECTLY = 487;
    public static final int STATUS_FILE_ERROR = 492;
    public static final int STATUS_FORBIDDEN = 403;
    public static final int STATUS_HTTP_DATA_ERROR = 495;
    public static final int STATUS_HTTP_EXCEPTION = 496;
    public static final int STATUS_INSUFFICIENT_SPACE_ERROR = 498;
    public static final int STATUS_PAUSED_BY_APP = 193;
    public static final int STATUS_PENDING = 190;
    public static final int STATUS_QUEUED_FOR_WIFI = 197;
    public static final int STATUS_QUEUED_FOR_WIFI_OR_CELLULAR_PERMISSION = 196;
    public static final int STATUS_RUNNING = 192;
    public static final int STATUS_SUCCESS = 200;
    public static final int STATUS_TOO_MANY_REDIRECTS = 497;
    public static final int STATUS_UNHANDLED_HTTP_CODE = 494;
    public static final int STATUS_UNHANDLED_REDIRECT = 493;
    public static final int STATUS_UNKNOWN_ERROR = 491;
    public static final int STATUS_WAITING_FOR_NETWORK = 195;
    public static final int STATUS_WAITING_TO_RETRY = 194;
    private static final String TEMP_EXT = ".tmp";
    public static final int VISIBILITY_HIDDEN = 2;
    public static final int VISIBILITY_VISIBLE = 0;
    public static final int VISIBILITY_VISIBLE_NOTIFY_COMPLETED = 1;
    private static LicenseVerificationBase lvl = null;
    private static boolean sIsRunning;
    private PendingIntent mAlarmIntent;
    float mAverageDownloadSpeed;
    long mBytesAtSample;
    long mBytesSoFar;
    private Messenger mClientMessenger;
    private BroadcastReceiver mConnReceiver;
    private ConnectivityManager mConnectivityManager;
    private int mControl;
    private BroadcastReceiver mCustomAlarmReceiver;
    int mFileCount;
    private boolean mIsAtLeast3G;
    private boolean mIsAtLeast4G;
    private boolean mIsCellularConnection;
    private boolean mIsConnected;
    private boolean mIsFailover;
    private boolean mIsRoaming;
    long mMillisecondsAtSample;
    private DownloadNotification mNotification;
    private PackageInfo mPackageInfo;
    /* access modifiers changed from: private */
    public PendingIntent mPendingIntent;
    private final Messenger mServiceMessenger = this.mServiceStub.getMessenger();
    private final IStub mServiceStub = DownloaderServiceMarshaller.CreateStub(this);
    /* access modifiers changed from: private */
    public boolean mStateChanged;
    private int mStatus;
    long mTotalLength;
    private WifiManager mWifiManager;

    public DownloaderService() {
        super("LVLDownloadService");
    }

    public static boolean isStatusInformational(int status) {
        return status >= 100 && status < 200;
    }

    public static boolean isStatusSuccess(int status) {
        return status >= 200 && status < 300;
    }

    public static boolean isStatusError(int status) {
        return status >= 400 && status < 600;
    }

    public static boolean isStatusClientError(int status) {
        return status >= 400 && status < 500;
    }

    public static boolean isStatusServerError(int status) {
        return status >= 500 && status < 600;
    }

    public static boolean isStatusCompleted(int status) {
        return (status >= 200 && status < 300) || (status >= 400 && status < 600);
    }

    public IBinder onBind(Intent paramIntent) {
        IMLogger.d("Service Bound");
        return this.mServiceMessenger.getBinder();
    }

    public boolean isWiFi() {
        return this.mIsConnected && !this.mIsCellularConnection;
    }

    private void updateNetworkType(int type, int subType) {
        switch (type) {
            case 0:
                this.mIsCellularConnection = true;
                switch (subType) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case 11:
                        this.mIsAtLeast3G = false;
                        this.mIsAtLeast4G = false;
                        return;
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                        this.mIsAtLeast3G = true;
                        this.mIsAtLeast4G = false;
                        return;
                    case 13:
                    case 14:
                    case 15:
                        this.mIsAtLeast3G = true;
                        this.mIsAtLeast4G = true;
                        return;
                    default:
                        this.mIsCellularConnection = false;
                        this.mIsAtLeast3G = false;
                        this.mIsAtLeast4G = false;
                        return;
                }
            case 1:
            case 7:
            case 9:
                this.mIsCellularConnection = false;
                this.mIsAtLeast3G = false;
                this.mIsAtLeast4G = false;
                return;
            case 6:
                this.mIsCellularConnection = true;
                this.mIsAtLeast3G = true;
                this.mIsAtLeast4G = true;
                return;
            default:
                return;
        }
    }

    private void updateNetworkState(NetworkInfo info) {
        boolean z = false;
        boolean isConnected = this.mIsConnected;
        boolean isFailover = this.mIsFailover;
        boolean isCellularConnection = this.mIsCellularConnection;
        boolean isRoaming = this.mIsRoaming;
        boolean isAtLeast3G = this.mIsAtLeast3G;
        if (info != null) {
            this.mIsRoaming = info.isRoaming();
            this.mIsFailover = info.isFailover();
            this.mIsConnected = info.isConnected();
            updateNetworkType(info.getType(), info.getSubtype());
        } else {
            this.mIsRoaming = false;
            this.mIsFailover = false;
            this.mIsConnected = false;
            updateNetworkType(-1, -1);
        }
        if (!(!this.mStateChanged && isConnected == this.mIsConnected && isFailover == this.mIsFailover && isCellularConnection == this.mIsCellularConnection && isRoaming == this.mIsRoaming && isAtLeast3G == this.mIsAtLeast3G)) {
            z = true;
        }
        this.mStateChanged = z;
        if (this.mStateChanged) {
            IMLogger.v("Network state changed: ");
            IMLogger.v("Starting State: " + (isConnected ? "Connected " : "Not Connected ") + (isCellularConnection ? "Cellular " : "WiFi ") + (isRoaming ? "Roaming " : "Local ") + (isAtLeast3G ? "3G+ " : "<3G "));
            IMLogger.v("Ending State: " + (this.mIsConnected ? "Connected " : "Not Connected ") + (this.mIsCellularConnection ? "Cellular " : "WiFi ") + (this.mIsRoaming ? "Roaming " : "Local ") + (this.mIsAtLeast3G ? "3G+ " : "<3G "));
            if (!isServiceRunning()) {
                return;
            }
            if (this.mIsRoaming) {
                this.mStatus = STATUS_WAITING_FOR_NETWORK;
                this.mControl = 1;
            } else if (this.mIsCellularConnection && (DownloadsDB.getDB(this).getFlags() & 1) == 0) {
                this.mStatus = STATUS_QUEUED_FOR_WIFI;
                this.mControl = 1;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void pollNetworkState() {
        if (this.mConnectivityManager == null) {
            this.mConnectivityManager = (ConnectivityManager) getSystemService("connectivity");
        }
        if (this.mWifiManager == null) {
            this.mWifiManager = (WifiManager) getSystemService("wifi");
        }
        if (this.mConnectivityManager == null) {
            IMLogger.w("couldn't get connectivity manager to poll network state");
        } else {
            updateNetworkState(this.mConnectivityManager.getActiveNetworkInfo());
        }
    }

    private static boolean isLVLCheckRequired(DownloadsDB db, PackageInfo pi) {
        if (db.mVersionCode != pi.versionCode) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public static synchronized boolean isServiceRunning() {
        boolean z;
        synchronized (DownloaderService.class) {
            z = sIsRunning;
        }
        return z;
    }

    public static synchronized void setServiceRunning(boolean isRunning) {
        synchronized (DownloaderService.class) {
            sIsRunning = isRunning;
        }
    }

    public int startDownloadServiceIfRequired() throws PackageManager.NameNotFoundException {
        return startDownloadServiceIfRequired(getApplicationContext(), this.mPendingIntent, getClass());
    }

    public static int startDownloadServiceIfRequired(Context context, Intent intent, Class<?> serviceClass) throws PackageManager.NameNotFoundException {
        return startDownloadServiceIfRequired(context, (PendingIntent) intent.getParcelableExtra(EXTRA_PENDING_INTENT), serviceClass);
    }

    public static int startDownloadServiceIfRequired(Context context, PendingIntent pendingIntent, Class<?> serviceClass) throws PackageManager.NameNotFoundException {
        return startDownloadServiceIfRequired(context, pendingIntent, context.getPackageName(), serviceClass.getName());
    }

    public static int startDownloadServiceIfRequired(Context context, PendingIntent pendingIntent, String classPackage, String className) throws PackageManager.NameNotFoundException {
        int i = 0;
        PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        int status = 0;
        DownloadsDB db = DownloadsDB.getDB(context);
        if (isLVLCheckRequired(db, pi)) {
            status = 1;
        }
        if (db.mStatus == 0) {
            DownloadInfo[] infos = db.getDownloads();
            if (infos != null) {
                int length = infos.length;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    DownloadInfo info = infos[i];
                    if (!Helpers.doesFileExist(context, info.mFileName, info.mTotalBytes, true)) {
                        status = 2;
                        db.updateStatus(-1);
                        break;
                    }
                    i++;
                }
            }
        } else {
            status = 2;
        }
        switch (status) {
            case 1:
            case 2:
                Intent fileIntent = new Intent();
                fileIntent.setClassName(classPackage, className);
                fileIntent.putExtra(EXTRA_PENDING_INTENT, pendingIntent);
                context.startService(fileIntent);
                break;
        }
        return status;
    }

    public void requestAbortDownload() {
        this.mControl = 1;
        this.mStatus = 490;
    }

    public void requestPauseDownload() {
        this.mControl = 1;
        this.mStatus = STATUS_PAUSED_BY_APP;
    }

    public void setDownloadFlags(int flags) {
        DownloadsDB.getDB(this).updateFlags(flags);
    }

    public void requestContinueDownload() {
        if (this.mControl == 1) {
            this.mControl = 0;
        }
        Intent fileIntent = new Intent(this, getClass());
        fileIntent.putExtra(EXTRA_PENDING_INTENT, this.mPendingIntent);
        startService(fileIntent);
    }

    private class CustomAlarmReceiver extends BroadcastReceiver {
        private CustomAlarmReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            IMLogger.v("DownloaderService -> CustomAlarmReceiver start ...");
            if (!DownloaderService.isServiceRunning()) {
                try {
                    DownloaderService.startDownloadServiceIfRequired(context, intent, DownloaderService.this.getClass());
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void scheduleAlarm(long wakeUp) {
        AlarmManager alarms = (AlarmManager) getSystemService(NotificationCompat.CATEGORY_ALARM);
        if (alarms == null) {
            IMLogger.e("couldn't get alarm manager");
            return;
        }
        IMLogger.v("scheduling retry in " + wakeUp + "ms");
        String className = CustomAlarmReceiver.class.getName();
        Intent intent = new Intent(Constants.ACTION_RETRY);
        intent.putExtra(EXTRA_PENDING_INTENT, this.mPendingIntent);
        intent.setClassName(getPackageName(), className);
        this.mAlarmIntent = PendingIntent.getBroadcast(this, 0, intent, 1073741824);
        alarms.set(0, System.currentTimeMillis() + wakeUp, this.mAlarmIntent);
    }

    private void cancelAlarms() {
        if (this.mAlarmIntent != null) {
            AlarmManager alarms = (AlarmManager) getSystemService(NotificationCompat.CATEGORY_ALARM);
            if (alarms == null) {
                IMLogger.e("couldn't get alarm manager");
                return;
            }
            alarms.cancel(this.mAlarmIntent);
            this.mAlarmIntent = null;
        }
    }

    private class InnerBroadcastReceiver extends BroadcastReceiver {
        final Service mService;

        InnerBroadcastReceiver(Service service) {
            this.mService = service;
        }

        public void onReceive(Context context, Intent intent) {
            DownloaderService.this.pollNetworkState();
            if (DownloaderService.this.mStateChanged && !DownloaderService.isServiceRunning()) {
                IMLogger.d("InnerBroadcastReceiver Called");
                Intent fileIntent = new Intent(context, this.mService.getClass());
                fileIntent.putExtra(DownloaderService.EXTRA_PENDING_INTENT, DownloaderService.this.mPendingIntent);
                context.startService(fileIntent);
            }
        }
    }

    private void syncDownloadInfoByLocalFile(DownloadInfo di) {
        if (di.mFileName != null) {
            long fileLength = 0;
            File f = new File(generateTempSaveFileName(di.mFileName));
            if (f.exists()) {
                fileLength = f.length();
                if (fileLength == 0) {
                    f.delete();
                }
            }
            IMLogger.d("tFileName fileLength = " + fileLength);
            di.mCurrentBytes = fileLength;
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onHandleIntent(android.content.Intent r23) {
        /*
            r22 = this;
            r16 = 1
            setServiceRunning(r16)
            com.tencent.imsdk.expansion.downloader.impl.DownloadsDB r6 = com.tencent.imsdk.expansion.downloader.impl.DownloadsDB.getDB(r22)     // Catch:{ all -> 0x005f }
            java.lang.String r16 = "EPI"
            r0 = r23
            r1 = r16
            android.os.Parcelable r12 = r0.getParcelableExtra(r1)     // Catch:{ all -> 0x005f }
            android.app.PendingIntent r12 = (android.app.PendingIntent) r12     // Catch:{ all -> 0x005f }
            if (r12 == 0) goto L_0x0047
            r0 = r22
            com.tencent.imsdk.expansion.downloader.impl.DownloadNotification r0 = r0.mNotification     // Catch:{ all -> 0x005f }
            r16 = r0
            r0 = r16
            r0.setClientIntent(r12)     // Catch:{ all -> 0x005f }
            r0 = r22
            r0.mPendingIntent = r12     // Catch:{ all -> 0x005f }
        L_0x0026:
            com.tencent.imsdk.expansion.downloader.LicenseVerificationBase r16 = lvl     // Catch:{ all -> 0x005f }
            if (r16 == 0) goto L_0x0071
            r0 = r22
            android.content.pm.PackageInfo r0 = r0.mPackageInfo     // Catch:{ all -> 0x005f }
            r16 = r0
            r0 = r16
            boolean r16 = isLVLCheckRequired(r6, r0)     // Catch:{ all -> 0x005f }
            if (r16 == 0) goto L_0x0071
            com.tencent.imsdk.expansion.downloader.LicenseVerificationBase r16 = lvl     // Catch:{ all -> 0x005f }
            r0 = r16
            r1 = r22
            r0.updateLVL(r1)     // Catch:{ all -> 0x005f }
            r16 = 0
            setServiceRunning(r16)
        L_0x0046:
            return
        L_0x0047:
            r0 = r22
            android.app.PendingIntent r0 = r0.mPendingIntent     // Catch:{ all -> 0x005f }
            r16 = r0
            if (r16 == 0) goto L_0x0066
            r0 = r22
            com.tencent.imsdk.expansion.downloader.impl.DownloadNotification r0 = r0.mNotification     // Catch:{ all -> 0x005f }
            r16 = r0
            r0 = r22
            android.app.PendingIntent r0 = r0.mPendingIntent     // Catch:{ all -> 0x005f }
            r17 = r0
            r16.setClientIntent(r17)     // Catch:{ all -> 0x005f }
            goto L_0x0026
        L_0x005f:
            r16 = move-exception
            r17 = 0
            setServiceRunning(r17)
            throw r16
        L_0x0066:
            java.lang.String r16 = "Downloader started in bad state without notification intent."
            com.tencent.imsdk.expansion.downloader.IMLogger.e(r16)     // Catch:{ all -> 0x005f }
            r16 = 0
            setServiceRunning(r16)
            goto L_0x0046
        L_0x0071:
            com.tencent.imsdk.expansion.downloader.impl.DownloadInfo[] r9 = r6.getDownloads()     // Catch:{ all -> 0x005f }
            r16 = 0
            r0 = r16
            r2 = r22
            r2.mBytesSoFar = r0     // Catch:{ all -> 0x005f }
            r16 = 0
            r0 = r16
            r2 = r22
            r2.mTotalLength = r0     // Catch:{ all -> 0x005f }
            int r0 = r9.length     // Catch:{ all -> 0x005f }
            r16 = r0
            r0 = r16
            r1 = r22
            r1.mFileCount = r0     // Catch:{ all -> 0x005f }
            int r0 = r9.length     // Catch:{ all -> 0x005f }
            r17 = r0
            r16 = 0
        L_0x0093:
            r0 = r16
            r1 = r17
            if (r0 >= r1) goto L_0x0116
            r8 = r9[r16]     // Catch:{ all -> 0x005f }
            int r0 = r8.mStatus     // Catch:{ all -> 0x005f }
            r18 = r0
            r19 = 200(0xc8, float:2.8E-43)
            r0 = r18
            r1 = r19
            if (r0 != r1) goto L_0x00f2
            java.lang.String r0 = r8.mFileName     // Catch:{ all -> 0x005f }
            r18 = r0
            long r0 = r8.mTotalBytes     // Catch:{ all -> 0x005f }
            r20 = r0
            r19 = 1
            r0 = r22
            r1 = r18
            r2 = r20
            r4 = r19
            boolean r18 = com.tencent.imsdk.expansion.downloader.Helpers.doesFileExist(r0, r1, r2, r4)     // Catch:{ all -> 0x005f }
            if (r18 != 0) goto L_0x00cb
            r18 = 0
            r0 = r18
            r8.mStatus = r0     // Catch:{ all -> 0x005f }
            r18 = 0
            r0 = r18
            r8.mCurrentBytes = r0     // Catch:{ all -> 0x005f }
        L_0x00cb:
            r0 = r22
            long r0 = r0.mTotalLength     // Catch:{ all -> 0x005f }
            r18 = r0
            long r0 = r8.mTotalBytes     // Catch:{ all -> 0x005f }
            r20 = r0
            long r18 = r18 + r20
            r0 = r18
            r2 = r22
            r2.mTotalLength = r0     // Catch:{ all -> 0x005f }
            r0 = r22
            long r0 = r0.mBytesSoFar     // Catch:{ all -> 0x005f }
            r18 = r0
            long r0 = r8.mCurrentBytes     // Catch:{ all -> 0x005f }
            r20 = r0
            long r18 = r18 + r20
            r0 = r18
            r2 = r22
            r2.mBytesSoFar = r0     // Catch:{ all -> 0x005f }
            int r16 = r16 + 1
            goto L_0x0093
        L_0x00f2:
            r0 = r22
            r0.syncDownloadInfoByLocalFile(r8)     // Catch:{ all -> 0x005f }
            java.lang.StringBuilder r18 = new java.lang.StringBuilder     // Catch:{ all -> 0x005f }
            r18.<init>()     // Catch:{ all -> 0x005f }
            java.lang.String r19 = "After syncLocal info.mCurrentBytes "
            java.lang.StringBuilder r18 = r18.append(r19)     // Catch:{ all -> 0x005f }
            long r0 = r8.mCurrentBytes     // Catch:{ all -> 0x005f }
            r20 = r0
            r0 = r18
            r1 = r20
            java.lang.StringBuilder r18 = r0.append(r1)     // Catch:{ all -> 0x005f }
            java.lang.String r18 = r18.toString()     // Catch:{ all -> 0x005f }
            com.tencent.imsdk.expansion.downloader.IMLogger.e(r18)     // Catch:{ all -> 0x005f }
            goto L_0x00cb
        L_0x0116:
            r22.pollNetworkState()     // Catch:{ all -> 0x005f }
            r0 = r22
            android.content.BroadcastReceiver r0 = r0.mConnReceiver     // Catch:{ all -> 0x005f }
            r16 = r0
            if (r16 != 0) goto L_0x014f
            com.tencent.imsdk.expansion.downloader.impl.DownloaderService$InnerBroadcastReceiver r16 = new com.tencent.imsdk.expansion.downloader.impl.DownloaderService$InnerBroadcastReceiver     // Catch:{ all -> 0x005f }
            r0 = r16
            r1 = r22
            r2 = r22
            r0.<init>(r2)     // Catch:{ all -> 0x005f }
            r0 = r16
            r1 = r22
            r1.mConnReceiver = r0     // Catch:{ all -> 0x005f }
            android.content.IntentFilter r10 = new android.content.IntentFilter     // Catch:{ all -> 0x005f }
            java.lang.String r16 = "android.net.conn.CONNECTIVITY_CHANGE"
            r0 = r16
            r10.<init>(r0)     // Catch:{ all -> 0x005f }
            java.lang.String r16 = "android.net.wifi.WIFI_STATE_CHANGED"
            r0 = r16
            r10.addAction(r0)     // Catch:{ all -> 0x005f }
            r0 = r22
            android.content.BroadcastReceiver r0 = r0.mConnReceiver     // Catch:{ all -> 0x005f }
            r16 = r0
            r0 = r22
            r1 = r16
            r0.registerReceiver(r1, r10)     // Catch:{ all -> 0x005f }
        L_0x014f:
            r0 = r22
            android.content.BroadcastReceiver r0 = r0.mCustomAlarmReceiver     // Catch:{ all -> 0x005f }
            r16 = r0
            if (r16 != 0) goto L_0x0180
            com.tencent.imsdk.expansion.downloader.impl.DownloaderService$CustomAlarmReceiver r16 = new com.tencent.imsdk.expansion.downloader.impl.DownloaderService$CustomAlarmReceiver     // Catch:{ all -> 0x005f }
            r17 = 0
            r0 = r16
            r1 = r22
            r2 = r17
            r0.<init>()     // Catch:{ all -> 0x005f }
            r0 = r16
            r1 = r22
            r1.mCustomAlarmReceiver = r0     // Catch:{ all -> 0x005f }
            android.content.IntentFilter r10 = new android.content.IntentFilter     // Catch:{ all -> 0x005f }
            java.lang.String r16 = "android.intent.action.DOWNLOAD_WAKEUP"
            r0 = r16
            r10.<init>(r0)     // Catch:{ all -> 0x005f }
            r0 = r22
            android.content.BroadcastReceiver r0 = r0.mCustomAlarmReceiver     // Catch:{ all -> 0x005f }
            r16 = r0
            r0 = r22
            r1 = r16
            r0.registerReceiver(r1, r10)     // Catch:{ all -> 0x005f }
        L_0x0180:
            int r0 = r9.length     // Catch:{ all -> 0x005f }
            r17 = r0
            r16 = 0
        L_0x0185:
            r0 = r16
            r1 = r17
            if (r0 >= r1) goto L_0x026c
            r8 = r9[r16]     // Catch:{ all -> 0x005f }
            long r14 = r8.mCurrentBytes     // Catch:{ all -> 0x005f }
            int r0 = r8.mStatus     // Catch:{ all -> 0x005f }
            r18 = r0
            r19 = 200(0xc8, float:2.8E-43)
            r0 = r18
            r1 = r19
            if (r0 == r1) goto L_0x01bc
            com.tencent.imsdk.expansion.downloader.impl.DownloadThread r7 = new com.tencent.imsdk.expansion.downloader.impl.DownloadThread     // Catch:{ all -> 0x005f }
            r0 = r22
            com.tencent.imsdk.expansion.downloader.impl.DownloadNotification r0 = r0.mNotification     // Catch:{ all -> 0x005f }
            r18 = r0
            r0 = r22
            r1 = r18
            r7.<init>(r8, r0, r1)     // Catch:{ all -> 0x005f }
            r22.cancelAlarms()     // Catch:{ all -> 0x005f }
            r18 = 5000(0x1388, double:2.4703E-320)
            r0 = r22
            r1 = r18
            r0.scheduleAlarm(r1)     // Catch:{ all -> 0x005f }
            r7.run()     // Catch:{ all -> 0x005f }
            r22.cancelAlarms()     // Catch:{ all -> 0x005f }
        L_0x01bc:
            r6.updateFromDb(r8)     // Catch:{ all -> 0x005f }
            r13 = 0
            int r0 = r8.mStatus     // Catch:{ all -> 0x005f }
            r18 = r0
            switch(r18) {
                case 193: goto L_0x0235;
                case 194: goto L_0x0237;
                case 195: goto L_0x0237;
                case 196: goto L_0x023a;
                case 197: goto L_0x023a;
                case 200: goto L_0x01fb;
                case 403: goto L_0x01e7;
                case 487: goto L_0x0228;
                case 490: goto L_0x0258;
                case 498: goto L_0x025d;
                case 499: goto L_0x0262;
                default: goto L_0x01c7;
            }     // Catch:{ all -> 0x005f }
        L_0x01c7:
            r11 = 19
        L_0x01c9:
            if (r13 == 0) goto L_0x0267
            r16 = 60000(0xea60, double:2.9644E-319)
            r0 = r22
            r1 = r16
            r0.scheduleAlarm(r1)     // Catch:{ all -> 0x005f }
        L_0x01d5:
            r0 = r22
            com.tencent.imsdk.expansion.downloader.impl.DownloadNotification r0 = r0.mNotification     // Catch:{ all -> 0x005f }
            r16 = r0
            r0 = r16
            r0.onDownloadStateChanged(r11)     // Catch:{ all -> 0x005f }
            r16 = 0
            setServiceRunning(r16)
            goto L_0x0046
        L_0x01e7:
            com.tencent.imsdk.expansion.downloader.LicenseVerificationBase r16 = lvl     // Catch:{ all -> 0x005f }
            if (r16 == 0) goto L_0x01f4
            com.tencent.imsdk.expansion.downloader.LicenseVerificationBase r16 = lvl     // Catch:{ all -> 0x005f }
            r0 = r16
            r1 = r22
            r0.updateLVL(r1)     // Catch:{ all -> 0x005f }
        L_0x01f4:
            r16 = 0
            setServiceRunning(r16)
            goto L_0x0046
        L_0x01fb:
            r0 = r22
            long r0 = r0.mBytesSoFar     // Catch:{ all -> 0x005f }
            r18 = r0
            long r0 = r8.mCurrentBytes     // Catch:{ all -> 0x005f }
            r20 = r0
            long r20 = r20 - r14
            long r18 = r18 + r20
            r0 = r18
            r2 = r22
            r2.mBytesSoFar = r0     // Catch:{ all -> 0x005f }
            r0 = r22
            android.content.pm.PackageInfo r0 = r0.mPackageInfo     // Catch:{ all -> 0x005f }
            r18 = r0
            r0 = r18
            int r0 = r0.versionCode     // Catch:{ all -> 0x005f }
            r18 = r0
            r19 = 0
            r0 = r18
            r1 = r19
            r6.updateMetadata(r0, r1)     // Catch:{ all -> 0x005f }
            int r16 = r16 + 1
            goto L_0x0185
        L_0x0228:
            r11 = 13
            r16 = 0
            r0 = r16
            r8.mCurrentBytes = r0     // Catch:{ all -> 0x005f }
            r6.updateDownload(r8)     // Catch:{ all -> 0x005f }
            r13 = 1
            goto L_0x01c9
        L_0x0235:
            r11 = 7
            goto L_0x01c9
        L_0x0237:
            r11 = 6
            r13 = 1
            goto L_0x01c9
        L_0x023a:
            r0 = r22
            android.net.wifi.WifiManager r0 = r0.mWifiManager     // Catch:{ all -> 0x005f }
            r16 = r0
            if (r16 == 0) goto L_0x0253
            r0 = r22
            android.net.wifi.WifiManager r0 = r0.mWifiManager     // Catch:{ all -> 0x005f }
            r16 = r0
            boolean r16 = r16.isWifiEnabled()     // Catch:{ all -> 0x005f }
            if (r16 != 0) goto L_0x0253
            r11 = 8
            r13 = 1
            goto L_0x01c9
        L_0x0253:
            r11 = 9
            r13 = 1
            goto L_0x01c9
        L_0x0258:
            r11 = 18
            r13 = 1
            goto L_0x01c9
        L_0x025d:
            r11 = 17
            r13 = 1
            goto L_0x01c9
        L_0x0262:
            r11 = 14
            r13 = 1
            goto L_0x01c9
        L_0x0267:
            r22.cancelAlarms()     // Catch:{ all -> 0x005f }
            goto L_0x01d5
        L_0x026c:
            r0 = r22
            com.tencent.imsdk.expansion.downloader.impl.DownloadNotification r0 = r0.mNotification     // Catch:{ all -> 0x005f }
            r16 = r0
            r17 = 5
            r16.onDownloadStateChanged(r17)     // Catch:{ all -> 0x005f }
            r16 = 0
            setServiceRunning(r16)
            goto L_0x0046
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.expansion.downloader.impl.DownloaderService.onHandleIntent(android.content.Intent):void");
    }

    public void onDestroy() {
        if (this.mConnReceiver != null) {
            unregisterReceiver(this.mConnReceiver);
            this.mConnReceiver = null;
        }
        if (this.mCustomAlarmReceiver != null) {
            unregisterReceiver(this.mCustomAlarmReceiver);
            this.mCustomAlarmReceiver = null;
        }
        this.mServiceStub.disconnect(this);
        super.onDestroy();
    }

    public int getNetworkAvailabilityState(DownloadsDB db) {
        if (!this.mIsConnected) {
            return 2;
        }
        if (!this.mIsCellularConnection) {
            return 1;
        }
        int flags = db.mFlags;
        if (this.mIsRoaming) {
            return 5;
        }
        if ((flags & 1) == 0) {
            return 6;
        }
        return 1;
    }

    public void onCreate() {
        super.onCreate();
        try {
            this.mPackageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            this.mNotification = new DownloadNotification(this, getPackageManager().getApplicationLabel(getApplicationInfo()));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int onStartCommand(Intent paramIntent, int flags, int startId) {
        IMLogger.d("DownloaderService -> onStartCommand ...");
        if (lvl == null) {
            lvl = CustomLicenseVerificationFactory.CreateCustomLV(this);
        }
        return super.onStartCommand(paramIntent, flags, startId);
    }

    public static class GenerateSaveFileError extends Exception {
        private static final long serialVersionUID = 3465966015408936540L;
        String mMessage;
        int mStatus;

        public GenerateSaveFileError(int status, String message) {
            this.mStatus = status;
            this.mMessage = message;
        }
    }

    public String generateTempSaveFileName(String fileName) {
        return Helpers.getSaveFilePath(this) + File.separator + fileName + TEMP_EXT;
    }

    public String generateSaveFile(String filename, long filesize) throws GenerateSaveFileError {
        String path = generateTempSaveFileName(filename);
        File expPath = new File(path);
        if (!Helpers.isExternalMediaMounted()) {
            IMLogger.d("External media not mounted: " + path);
            throw new GenerateSaveFileError(499, "external media is not yet mounted");
        } else if (expPath.exists()) {
            IMLogger.d("File already exists: " + path);
            throw new GenerateSaveFileError(488, "requested destination file already exists");
        } else if (Helpers.getAvailableBytes(Helpers.getFilesystemRoot(path)) >= filesize) {
            return path;
        } else {
            throw new GenerateSaveFileError(498, "insufficient space on external storage");
        }
    }

    public String getLogMessageForNetworkError(int networkError) {
        switch (networkError) {
            case 2:
                return "no network connection available";
            case 3:
                return "download size exceeds limit for mobile network";
            case 4:
                return "download size exceeds recommended limit for mobile network";
            case 5:
                return "download cannot use the current network connection because it is roaming";
            case 6:
                return "download was requested to not use the current network type";
            default:
                return "unknown error with network connectivity";
        }
    }

    public int getControl() {
        return this.mControl;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public void notifyUpdateBytes(long totalBytesSoFar) {
        long timeRemaining;
        long currentTime = SystemClock.uptimeMillis();
        if (0 != this.mMillisecondsAtSample) {
            float currentSpeedSample = ((float) (totalBytesSoFar - this.mBytesAtSample)) / ((float) (currentTime - this.mMillisecondsAtSample));
            if (0.0f != this.mAverageDownloadSpeed) {
                this.mAverageDownloadSpeed = (SMOOTHING_FACTOR * currentSpeedSample) + (0.995f * this.mAverageDownloadSpeed);
            } else {
                this.mAverageDownloadSpeed = currentSpeedSample;
            }
            timeRemaining = (long) (((float) (this.mTotalLength - totalBytesSoFar)) / this.mAverageDownloadSpeed);
        } else {
            timeRemaining = -1;
        }
        this.mMillisecondsAtSample = currentTime;
        this.mBytesAtSample = totalBytesSoFar;
        this.mNotification.onDownloadProgress(new DownloadProgressInfo(this.mTotalLength, totalBytesSoFar, timeRemaining, this.mAverageDownloadSpeed));
    }

    /* access modifiers changed from: protected */
    public boolean shouldStop() {
        if (DownloadsDB.getDB(this).mStatus == 0) {
            return true;
        }
        return false;
    }

    public DownloadNotification getmNotification() {
        return this.mNotification;
    }

    public void requestDownloadStatus() {
        this.mNotification.resendState();
    }

    public void onClientUpdated(Messenger clientMessenger) {
        this.mClientMessenger = clientMessenger;
        this.mNotification.setMessenger(this.mClientMessenger);
    }
}

package com.amazonaws.mobileconnectors.s3.transferutility;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.amazonaws.services.s3.AmazonS3;
import com.tencent.imsdk.expansion.downloader.Constants;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TransferService extends Service {
    static final String INTENT_ACTION_TRANSFER_ADD = "add_transfer";
    static final String INTENT_ACTION_TRANSFER_CANCEL = "cancel_transfer";
    static final String INTENT_ACTION_TRANSFER_PAUSE = "pause_transfer";
    static final String INTENT_ACTION_TRANSFER_RESUME = "resume_transfer";
    static final String INTENT_BUNDLE_S3_REFERENCE_KEY = "s3_reference_key";
    static final String INTENT_BUNDLE_TRANSFER_ID = "id";
    private static final int MINUTE_IN_MILLIS = 60000;
    static final int MSG_CHECK = 200;
    static final int MSG_DISCONNECT = 300;
    static final int MSG_EXEC = 100;
    private static final String TAG = "TransferService";
    private TransferDBUtil dbUtil;
    private HandlerThread handlerThread;
    private boolean isFirst = true;
    private volatile long lastActiveTime;
    private NetworkInfoReceiver networkInfoReceiver;
    private AmazonS3 s3;
    private boolean shouldScan = true;
    private volatile int startId;
    /* access modifiers changed from: private */
    public Handler updateHandler;
    TransferStatusUpdater updater;

    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Can't bind to TransferService");
    }

    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Starting Transfer Service");
        this.dbUtil = new TransferDBUtil(getApplicationContext());
        this.updater = new TransferStatusUpdater(this.dbUtil);
        this.handlerThread = new HandlerThread("TransferService-AWSTransferUpdateHandlerThread");
        this.handlerThread.start();
        setHandlerLooper(this.handlerThread.getLooper());
    }

    static class NetworkInfoReceiver extends BroadcastReceiver {
        private final ConnectivityManager connManager;
        private final Handler handler;

        public NetworkInfoReceiver(Context context, Handler handler2) {
            this.handler = handler2;
            this.connManager = (ConnectivityManager) context.getSystemService("connectivity");
        }

        public void onReceive(Context context, Intent intent) {
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                boolean networkConnected = isNetworkConnected();
                Log.d(TransferService.TAG, "Network connected: " + networkConnected);
                this.handler.sendEmptyMessage(networkConnected ? 200 : 300);
            }
        }

        /* access modifiers changed from: package-private */
        public boolean isNetworkConnected() {
            NetworkInfo info = this.connManager.getActiveNetworkInfo();
            return info != null && info.isConnected();
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId2) {
        this.startId = startId2;
        this.s3 = S3ClientReference.get(intent.getStringExtra(INTENT_BUNDLE_S3_REFERENCE_KEY));
        if (this.s3 == null) {
            Log.w(TAG, "TransferService can't get s3 client, and it will stop.");
            stopSelf(startId2);
        } else {
            this.updateHandler.sendMessage(this.updateHandler.obtainMessage(100, intent));
            if (this.isFirst) {
                registerReceiver(this.networkInfoReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                this.isFirst = false;
            }
        }
        return 2;
    }

    public void onDestroy() {
        try {
            unregisterReceiver(this.networkInfoReceiver);
        } catch (IllegalArgumentException e) {
        }
        this.handlerThread.quit();
        TransferThreadPool.closeThreadPool();
        S3ClientReference.clear();
        super.onDestroy();
    }

    class UpdateHandler extends Handler {
        public UpdateHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            if (msg.what == 200) {
                TransferService.this.updateHandler.removeMessages(200);
                TransferService.this.checkTransfers();
            } else if (msg.what == 100) {
                TransferService.this.execCommand((Intent) msg.obj);
            } else if (msg.what == 300) {
                TransferService.this.pauseAllForNetwork();
            } else {
                Log.e(TransferService.TAG, "Unknown command: " + msg.what);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void checkTransfers() {
        if (this.shouldScan && this.networkInfoReceiver.isNetworkConnected() && this.s3 != null) {
            loadTransfersFromDB();
            this.shouldScan = false;
        }
        removeCompletedTransfers();
        if (isActive()) {
            this.lastActiveTime = System.currentTimeMillis();
            this.updateHandler.sendEmptyMessageDelayed(200, Constants.WATCHDOG_WAKE_TIMER);
            return;
        }
        Log.d(TAG, "Stop self");
        stopSelf(this.startId);
    }

    /* access modifiers changed from: package-private */
    public void execCommand(Intent intent) {
        this.lastActiveTime = System.currentTimeMillis();
        String action = intent.getAction();
        int id = intent.getIntExtra("id", 0);
        if (id == 0) {
            Log.e(TAG, "Invalid id: " + id);
        } else if (INTENT_ACTION_TRANSFER_ADD.equals(action)) {
            if (this.updater.getTransfer(id) != null) {
                Log.w(TAG, "Transfer has already been added: " + id);
                return;
            }
            TransferRecord transfer = this.dbUtil.getTransferById(id);
            if (transfer != null) {
                this.updater.addTransfer(transfer);
                transfer.start(this.s3, this.dbUtil, this.updater, this.networkInfoReceiver);
                return;
            }
            Log.e(TAG, "Can't find transfer: " + id);
        } else if (INTENT_ACTION_TRANSFER_PAUSE.equals(action)) {
            TransferRecord transfer2 = this.updater.getTransfer(id);
            if (transfer2 == null) {
                transfer2 = this.dbUtil.getTransferById(id);
            }
            if (transfer2 != null) {
                transfer2.pause(this.s3, this.updater);
            }
        } else if (INTENT_ACTION_TRANSFER_RESUME.equals(action)) {
            TransferRecord transfer3 = this.updater.getTransfer(id);
            if (transfer3 == null) {
                transfer3 = this.dbUtil.getTransferById(id);
                if (transfer3 != null) {
                    this.updater.addTransfer(transfer3);
                } else {
                    Log.e(TAG, "Can't find transfer: " + id);
                }
            }
            transfer3.start(this.s3, this.dbUtil, this.updater, this.networkInfoReceiver);
        } else if (INTENT_ACTION_TRANSFER_CANCEL.equals(action)) {
            TransferRecord transfer4 = this.updater.getTransfer(id);
            if (transfer4 == null) {
                transfer4 = this.dbUtil.getTransferById(id);
            }
            if (transfer4 != null) {
                transfer4.cancel(this.s3, this.updater);
            }
        } else {
            Log.e(TAG, "Unknown action: " + action);
        }
    }

    private boolean isActive() {
        if (this.shouldScan) {
            return true;
        }
        for (TransferRecord transfer : this.updater.getTransfers().values()) {
            if (transfer.isRunning()) {
                return true;
            }
        }
        if (System.currentTimeMillis() - this.lastActiveTime >= Constants.WATCHDOG_WAKE_TIMER) {
            return false;
        }
        return true;
    }

    private void removeCompletedTransfers() {
        List<Integer> ids = new ArrayList<>();
        for (TransferRecord transfer : this.updater.getTransfers().values()) {
            if (TransferState.COMPLETED.equals(transfer.state)) {
                ids.add(Integer.valueOf(transfer.id));
            }
        }
        for (Integer id : ids) {
            this.updater.removeTransfer(id.intValue());
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    public void loadTransfersFromDB() {
        Log.d(TAG, "Loading transfers from database");
        Cursor c = this.dbUtil.queryAllTransfersWithType(TransferType.ANY);
        int count = 0;
        while (c.moveToNext()) {
            try {
                int id = c.getInt(c.getColumnIndexOrThrow("_id"));
                TransferState state = TransferState.getState(c.getString(c.getColumnIndexOrThrow("state")));
                if ((c.getInt(c.getColumnIndexOrThrow(TransferTable.COLUMN_PART_NUM)) == 0 && (TransferState.WAITING.equals(state) || TransferState.WAITING_FOR_NETWORK.equals(state) || TransferState.RESUMED_WAITING.equals(state))) || TransferState.IN_PROGRESS.equals(state)) {
                    if (this.updater.getTransfer(id) == null) {
                        TransferRecord transfer = new TransferRecord(id);
                        transfer.updateFromDB(c);
                        if (transfer.start(this.s3, this.dbUtil, this.updater, this.networkInfoReceiver)) {
                            this.updater.addTransfer(transfer);
                            count++;
                        }
                    } else {
                        TransferRecord transfer2 = this.updater.getTransfer(id);
                        if (!transfer2.isRunning()) {
                            transfer2.start(this.s3, this.dbUtil, this.updater, this.networkInfoReceiver);
                        }
                    }
                }
            } catch (Throwable th) {
                c.close();
                throw th;
            }
        }
        c.close();
        Log.d(TAG, count + " transfers are loaded from database");
    }

    /* access modifiers changed from: package-private */
    public void pauseAllForNetwork() {
        for (TransferRecord transfer : this.updater.getTransfers().values()) {
            if (!(this.s3 == null || transfer == null || !transfer.pause(this.s3, this.updater))) {
                this.updater.updateState(transfer.id, TransferState.WAITING_FOR_NETWORK);
            }
        }
        this.shouldScan = true;
    }

    /* access modifiers changed from: package-private */
    public void setHandlerLooper(Looper looper) {
        this.updateHandler = new UpdateHandler(looper);
        this.networkInfoReceiver = new NetworkInfoReceiver(getApplicationContext(), this.updateHandler);
    }

    /* access modifiers changed from: protected */
    public void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        if ((getApplicationInfo().flags & 2) != 0) {
            writer.printf("start id: %d\n", new Object[]{Integer.valueOf(this.startId)});
            writer.printf("network status: %s\n", new Object[]{Boolean.valueOf(this.networkInfoReceiver.isNetworkConnected())});
            writer.printf("lastActiveTime: %s, shouldScan: %s\n", new Object[]{new Date(this.lastActiveTime), Boolean.valueOf(this.shouldScan)});
            Map<Integer, TransferRecord> transfers = this.updater.getTransfers();
            writer.printf("# of active transfers: %d\n", new Object[]{Integer.valueOf(transfers.size())});
            for (TransferRecord transfer : transfers.values()) {
                writer.printf("bucket: %s, key: %s, status: %s, total size: %d, current: %d\n", new Object[]{transfer.bucketName, transfer.key, transfer.state, Long.valueOf(transfer.bytesTotal), Long.valueOf(transfer.bytesCurrent)});
            }
            writer.flush();
        }
    }
}

package com.amazonaws.mobileconnectors.s3.transferutility;

import android.database.Cursor;
import java.io.File;

public class TransferObserver {
    private String bucket;
    /* access modifiers changed from: private */
    public long bytesTotal;
    /* access modifiers changed from: private */
    public long bytesTransferred;
    private final TransferDBUtil dbUtil;
    private String filePath;
    private final int id;
    private String key;
    private TransferStatusListener statusListener;
    private TransferListener transferListener;
    /* access modifiers changed from: private */
    public TransferState transferState;

    TransferObserver(int id2, TransferDBUtil dbUtil2, String bucket2, String key2, File file) {
        this.id = id2;
        this.dbUtil = dbUtil2;
        this.bucket = bucket2;
        this.key = key2;
        this.filePath = file.getAbsolutePath();
        this.bytesTotal = file.length();
        this.transferState = TransferState.WAITING;
    }

    TransferObserver(int id2, TransferDBUtil dbUtil2, Cursor c) {
        this.id = id2;
        this.dbUtil = dbUtil2;
        updateFromDB(c);
    }

    public void refresh() {
        Cursor c = this.dbUtil.queryTransferById(this.id);
        try {
            if (c.moveToFirst()) {
                updateFromDB(c);
            }
        } finally {
            c.close();
        }
    }

    private void updateFromDB(Cursor c) {
        this.bucket = c.getString(c.getColumnIndexOrThrow(TransferTable.COLUMN_BUCKET_NAME));
        this.key = c.getString(c.getColumnIndexOrThrow("key"));
        this.bytesTotal = c.getLong(c.getColumnIndexOrThrow(TransferTable.COLUMN_BYTES_TOTAL));
        this.bytesTransferred = c.getLong(c.getColumnIndexOrThrow(TransferTable.COLUMN_BYTES_CURRENT));
        this.transferState = TransferState.getState(c.getString(c.getColumnIndexOrThrow("state")));
        this.filePath = c.getString(c.getColumnIndexOrThrow(TransferTable.COLUMN_FILE));
    }

    public void setTransferListener(TransferListener listener) {
        synchronized (this) {
            cleanTransferListener();
            this.statusListener = new TransferStatusListener();
            TransferStatusUpdater.registerListener(this.id, this.statusListener);
            this.transferListener = listener;
            TransferStatusUpdater.registerListener(this.id, this.transferListener);
        }
    }

    public int getId() {
        return this.id;
    }

    public String getBucket() {
        return this.bucket;
    }

    public String getKey() {
        return this.key;
    }

    public long getBytesTotal() {
        return this.bytesTotal;
    }

    public String getAbsoluteFilePath() {
        return this.filePath;
    }

    public long getBytesTransferred() {
        return this.bytesTransferred;
    }

    public TransferState getState() {
        return this.transferState;
    }

    public void cleanTransferListener() {
        synchronized (this) {
            if (this.transferListener != null) {
                TransferStatusUpdater.unregisterListener(this.id, this.transferListener);
                this.transferListener = null;
            }
            if (this.statusListener != null) {
                TransferStatusUpdater.unregisterListener(this.id, this.statusListener);
                this.statusListener = null;
            }
        }
    }

    private class TransferStatusListener implements TransferListener {
        private TransferStatusListener() {
        }

        public void onStateChanged(int id, TransferState state) {
            TransferState unused = TransferObserver.this.transferState = state;
        }

        public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
            long unused = TransferObserver.this.bytesTransferred = bytesCurrent;
            long unused2 = TransferObserver.this.bytesTotal = bytesTotal;
        }

        public void onError(int id, Exception ex) {
        }
    }
}

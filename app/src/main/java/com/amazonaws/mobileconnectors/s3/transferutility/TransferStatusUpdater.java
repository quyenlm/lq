package com.amazonaws.mobileconnectors.s3.transferutility;

import android.os.Handler;
import android.os.Looper;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

class TransferStatusUpdater {
    private static HashSet<TransferState> STATES_NOT_TO_NOTIFY = new HashSet<>(Arrays.asList(new TransferState[]{TransferState.PART_COMPLETED, TransferState.PENDING_CANCEL, TransferState.PENDING_PAUSE, TransferState.PENDING_NETWORK_DISCONNECT}));
    private static final String TAG = "TransferStatusUpdater";
    private static final int UPDATE_THRESHOLD_MS = 1000;
    static final Map<Integer, List<TransferListener>> listeners = new HashMap();
    private final TransferDBUtil dbUtil;
    private final Map<Integer, Long> lastUpdateTime = new HashMap();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final Map<Integer, TransferRecord> transfers = new HashMap();

    TransferStatusUpdater(TransferDBUtil dbUtil2) {
        this.dbUtil = dbUtil2;
    }

    /* access modifiers changed from: package-private */
    public Map<Integer, TransferRecord> getTransfers() {
        return Collections.unmodifiableMap(this.transfers);
    }

    /* access modifiers changed from: package-private */
    public void addTransfer(TransferRecord transfer) {
        this.transfers.put(Integer.valueOf(transfer.id), transfer);
    }

    /* access modifiers changed from: package-private */
    public TransferRecord getTransfer(int id) {
        return this.transfers.get(Integer.valueOf(id));
    }

    /* access modifiers changed from: package-private */
    public void removeTransfer(int id) {
        this.transfers.remove(Integer.valueOf(id));
        listeners.remove(Integer.valueOf(id));
        this.lastUpdateTime.remove(Integer.valueOf(id));
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0061, code lost:
        r0 = listeners.get(java.lang.Integer.valueOf(r7));
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateState(final int r7, final com.amazonaws.mobileconnectors.s3.transferutility.TransferState r8) {
        /*
            r6 = this;
            java.util.HashSet<com.amazonaws.mobileconnectors.s3.transferutility.TransferState> r3 = STATES_NOT_TO_NOTIFY
            boolean r1 = r3.contains(r8)
            java.util.Map<java.lang.Integer, com.amazonaws.mobileconnectors.s3.transferutility.TransferRecord> r3 = r6.transfers
            java.lang.Integer r4 = java.lang.Integer.valueOf(r7)
            java.lang.Object r2 = r3.get(r4)
            com.amazonaws.mobileconnectors.s3.transferutility.TransferRecord r2 = (com.amazonaws.mobileconnectors.s3.transferutility.TransferRecord) r2
            if (r2 != 0) goto L_0x0037
            com.amazonaws.mobileconnectors.s3.transferutility.TransferDBUtil r3 = r6.dbUtil
            int r3 = r3.updateState(r7, r8)
            if (r3 != 0) goto L_0x0034
            java.lang.String r3 = "TransferStatusUpdater"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Failed to update the status of transfer "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r7)
            java.lang.String r4 = r4.toString()
            android.util.Log.w(r3, r4)
        L_0x0034:
            if (r1 == 0) goto L_0x0061
        L_0x0036:
            return
        L_0x0037:
            com.amazonaws.mobileconnectors.s3.transferutility.TransferState r3 = r2.state
            boolean r3 = r8.equals(r3)
            r1 = r1 | r3
            r2.state = r8
            com.amazonaws.mobileconnectors.s3.transferutility.TransferDBUtil r3 = r6.dbUtil
            int r3 = r3.updateTransferRecord(r2)
            if (r3 != 0) goto L_0x0034
            java.lang.String r3 = "TransferStatusUpdater"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Failed to update the status of transfer "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r7)
            java.lang.String r4 = r4.toString()
            android.util.Log.w(r3, r4)
            goto L_0x0034
        L_0x0061:
            java.util.Map<java.lang.Integer, java.util.List<com.amazonaws.mobileconnectors.s3.transferutility.TransferListener>> r3 = listeners
            java.lang.Integer r4 = java.lang.Integer.valueOf(r7)
            java.lang.Object r0 = r3.get(r4)
            java.util.List r0 = (java.util.List) r0
            if (r0 == 0) goto L_0x0036
            boolean r3 = r0.isEmpty()
            if (r3 != 0) goto L_0x0036
            android.os.Handler r3 = r6.mainHandler
            com.amazonaws.mobileconnectors.s3.transferutility.TransferStatusUpdater$1 r4 = new com.amazonaws.mobileconnectors.s3.transferutility.TransferStatusUpdater$1
            r4.<init>(r0, r7, r8)
            r3.post(r4)
            goto L_0x0036
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amazonaws.mobileconnectors.s3.transferutility.TransferStatusUpdater.updateState(int, com.amazonaws.mobileconnectors.s3.transferutility.TransferState):void");
    }

    /* access modifiers changed from: package-private */
    public void updateProgress(int id, long bytesCurrent, long bytesTotal) {
        TransferRecord transfer = this.transfers.get(Integer.valueOf(id));
        if (transfer != null) {
            transfer.bytesCurrent = bytesCurrent;
            transfer.bytesTotal = bytesTotal;
        }
        final List<TransferListener> list = listeners.get(Integer.valueOf(id));
        if (list != null && !list.isEmpty()) {
            long timeInMillis = System.currentTimeMillis();
            if (!this.lastUpdateTime.containsKey(Integer.valueOf(id)) || timeInMillis - this.lastUpdateTime.get(Integer.valueOf(id)).longValue() > 1000 || bytesCurrent == bytesTotal) {
                this.lastUpdateTime.put(Integer.valueOf(id), Long.valueOf(timeInMillis));
                final int i = id;
                final long j = bytesCurrent;
                final long j2 = bytesTotal;
                this.mainHandler.post(new Runnable() {
                    public void run() {
                        for (TransferListener l : list) {
                            l.onProgressChanged(i, j, j2);
                        }
                    }
                });
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void throwError(final int id, final Exception e) {
        final List<TransferListener> list = listeners.get(Integer.valueOf(id));
        if (list != null && !list.isEmpty()) {
            this.mainHandler.post(new Runnable() {
                public void run() {
                    for (TransferListener l : list) {
                        l.onError(id, e);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        listeners.clear();
        this.transfers.clear();
        this.lastUpdateTime.clear();
    }

    static void registerListener(int id, TransferListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener can't be null");
        }
        synchronized (listeners) {
            List<TransferListener> list = listeners.get(Integer.valueOf(id));
            if (list == null) {
                List<TransferListener> list2 = new CopyOnWriteArrayList<>();
                list2.add(listener);
                listeners.put(Integer.valueOf(id), list2);
            } else if (!list.contains(listener)) {
                list.add(listener);
            }
        }
    }

    static void unregisterListener(int id, TransferListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener can't be null");
        }
        List<TransferListener> list = listeners.get(Integer.valueOf(id));
        if (list != null && !list.isEmpty()) {
            list.remove(listener);
        }
    }

    private class TransferProgressListener implements ProgressListener {
        private long bytesCurrent;
        private final TransferRecord transfer;

        public TransferProgressListener(TransferRecord transfer2) {
            this.transfer = transfer2;
        }

        public synchronized void progressChanged(ProgressEvent progressEvent) {
            if (progressEvent.getEventCode() == 32) {
                this.transfer.bytesCurrent -= this.bytesCurrent;
                this.bytesCurrent = 0;
            } else {
                this.bytesCurrent += progressEvent.getBytesTransferred();
                this.transfer.bytesCurrent += progressEvent.getBytesTransferred();
            }
            TransferStatusUpdater.this.updateProgress(this.transfer.id, this.transfer.bytesCurrent, this.transfer.bytesTotal);
        }
    }

    /* access modifiers changed from: package-private */
    public ProgressListener newProgressListener(int id) {
        TransferRecord transfer = getTransfer(id);
        if (transfer != null) {
            return new TransferProgressListener(transfer);
        }
        throw new IllegalArgumentException("transfer " + id + " doesn't exist");
    }
}

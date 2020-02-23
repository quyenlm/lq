package com.amazonaws.mobileconnectors.s3.transfermanager;

import com.amazonaws.mobileconnectors.s3.transfermanager.internal.MultipleFileTransfer;
import com.amazonaws.mobileconnectors.s3.transfermanager.internal.TransferStateChangeListener;
import java.util.concurrent.CountDownLatch;

/* compiled from: MultipleFileTransferChangeStateListener */
final class MultipleFileTransferStateChangeListener implements TransferStateChangeListener {
    private final CountDownLatch latch;
    private final MultipleFileTransfer<?> multipleFileTransfer;

    public MultipleFileTransferStateChangeListener(CountDownLatch latch2, MultipleFileTransfer<?> multipleFileTransfer2) {
        this.latch = latch2;
        this.multipleFileTransfer = multipleFileTransfer2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void transferStateChanged(com.amazonaws.mobileconnectors.s3.transfermanager.Transfer r5, com.amazonaws.mobileconnectors.s3.transfermanager.Transfer.TransferState r6) {
        /*
            r4 = this;
            java.util.concurrent.CountDownLatch r1 = r4.latch     // Catch:{ InterruptedException -> 0x001a }
            r1.await()     // Catch:{ InterruptedException -> 0x001a }
            com.amazonaws.mobileconnectors.s3.transfermanager.internal.MultipleFileTransfer<?> r2 = r4.multipleFileTransfer
            monitor-enter(r2)
            com.amazonaws.mobileconnectors.s3.transfermanager.internal.MultipleFileTransfer<?> r1 = r4.multipleFileTransfer     // Catch:{ all -> 0x002e }
            com.amazonaws.mobileconnectors.s3.transfermanager.Transfer$TransferState r1 = r1.getState()     // Catch:{ all -> 0x002e }
            if (r1 == r6) goto L_0x0018
            com.amazonaws.mobileconnectors.s3.transfermanager.internal.MultipleFileTransfer<?> r1 = r4.multipleFileTransfer     // Catch:{ all -> 0x002e }
            boolean r1 = r1.isDone()     // Catch:{ all -> 0x002e }
            if (r1 == 0) goto L_0x0023
        L_0x0018:
            monitor-exit(r2)     // Catch:{ all -> 0x002e }
        L_0x0019:
            return
        L_0x001a:
            r0 = move-exception
            com.amazonaws.AmazonClientException r1 = new com.amazonaws.AmazonClientException
            java.lang.String r2 = "Couldn't wait for all downloads to be queued"
            r1.<init>(r2)
            throw r1
        L_0x0023:
            com.amazonaws.mobileconnectors.s3.transfermanager.Transfer$TransferState r1 = com.amazonaws.mobileconnectors.s3.transfermanager.Transfer.TransferState.InProgress     // Catch:{ all -> 0x002e }
            if (r6 != r1) goto L_0x0031
            com.amazonaws.mobileconnectors.s3.transfermanager.internal.MultipleFileTransfer<?> r1 = r4.multipleFileTransfer     // Catch:{ all -> 0x002e }
            r1.setState(r6)     // Catch:{ all -> 0x002e }
        L_0x002c:
            monitor-exit(r2)     // Catch:{ all -> 0x002e }
            goto L_0x0019
        L_0x002e:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x002e }
            throw r1
        L_0x0031:
            com.amazonaws.mobileconnectors.s3.transfermanager.internal.MultipleFileTransfer<?> r1 = r4.multipleFileTransfer     // Catch:{ all -> 0x002e }
            com.amazonaws.mobileconnectors.s3.transfermanager.internal.TransferMonitor r1 = r1.getMonitor()     // Catch:{ all -> 0x002e }
            boolean r1 = r1.isDone()     // Catch:{ all -> 0x002e }
            if (r1 == 0) goto L_0x0043
            com.amazonaws.mobileconnectors.s3.transfermanager.internal.MultipleFileTransfer<?> r1 = r4.multipleFileTransfer     // Catch:{ all -> 0x002e }
            r1.collateFinalState()     // Catch:{ all -> 0x002e }
            goto L_0x002c
        L_0x0043:
            com.amazonaws.mobileconnectors.s3.transfermanager.internal.MultipleFileTransfer<?> r1 = r4.multipleFileTransfer     // Catch:{ all -> 0x002e }
            com.amazonaws.mobileconnectors.s3.transfermanager.Transfer$TransferState r3 = com.amazonaws.mobileconnectors.s3.transfermanager.Transfer.TransferState.InProgress     // Catch:{ all -> 0x002e }
            r1.setState(r3)     // Catch:{ all -> 0x002e }
            goto L_0x002c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amazonaws.mobileconnectors.s3.transfermanager.MultipleFileTransferStateChangeListener.transferStateChanged(com.amazonaws.mobileconnectors.s3.transfermanager.Transfer, com.amazonaws.mobileconnectors.s3.transfermanager.Transfer$TransferState):void");
    }
}

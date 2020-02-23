package com.amazonaws.mobileconnectors.s3.transferutility;

import android.util.Log;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferService;
import com.amazonaws.retry.RetryUtils;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

class DownloadTask implements Callable<Boolean> {
    private static final String TAG = "DownloadTask";
    private final TransferRecord download;
    private final TransferService.NetworkInfoReceiver networkInfo;
    private final AmazonS3 s3;
    private final TransferStatusUpdater updater;

    public DownloadTask(TransferRecord download2, AmazonS3 s32, TransferStatusUpdater updater2, TransferService.NetworkInfoReceiver networkInfo2) {
        this.download = download2;
        this.s3 = s32;
        this.updater = updater2;
        this.networkInfo = networkInfo2;
    }

    public Boolean call() throws Exception {
        if (!this.networkInfo.isNetworkConnected()) {
            this.updater.updateState(this.download.id, TransferState.WAITING_FOR_NETWORK);
            return false;
        }
        this.updater.updateState(this.download.id, TransferState.IN_PROGRESS);
        GetObjectRequest getObjectRequest = new GetObjectRequest(this.download.bucketName, this.download.key);
        TransferUtility.appendTransferServiceUserAgentString(getObjectRequest);
        File file = new File(this.download.file);
        long bytesCurrent = file.length();
        if (bytesCurrent > 0) {
            Log.d(TAG, String.format("Resume transfer %d from %d bytes", new Object[]{Integer.valueOf(this.download.id), Long.valueOf(bytesCurrent)}));
            getObjectRequest.setRange(bytesCurrent, -1);
        }
        getObjectRequest.setGeneralProgressListener(this.updater.newProgressListener(this.download.id));
        try {
            S3Object object = this.s3.getObject(getObjectRequest);
            if (object == null) {
                this.updater.throwError(this.download.id, new IllegalStateException("AmazonS3.getObject returns null"));
                this.updater.updateState(this.download.id, TransferState.FAILED);
                return false;
            }
            long bytesTotal = object.getObjectMetadata().getInstanceLength();
            this.updater.updateProgress(this.download.id, bytesCurrent, bytesTotal);
            saveToFile(object.getObjectContent(), file);
            this.updater.updateProgress(this.download.id, bytesTotal, bytesTotal);
            this.updater.updateState(this.download.id, TransferState.COMPLETED);
            return true;
        } catch (Exception e) {
            if (RetryUtils.isInterrupted(e)) {
                Log.d(TAG, "Transfer " + this.download.id + " is interrupted by user");
            } else if (e.getCause() == null || !(e.getCause() instanceof IOException) || this.networkInfo.isNetworkConnected()) {
                Log.e(TAG, "Failed to download: " + this.download.id + " due to " + e.getMessage());
                this.updater.throwError(this.download.id, e);
                this.updater.updateState(this.download.id, TransferState.FAILED);
            } else {
                Log.d(TAG, "Transfer " + this.download.id + " waits for network");
                this.updater.updateState(this.download.id, TransferState.WAITING_FOR_NETWORK);
            }
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0058 A[SYNTHETIC, Splitter:B:24:0x0058] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void saveToFile(java.io.InputStream r13, java.io.File r14) {
        /*
            r12 = this;
            r0 = 0
            java.io.File r6 = r14.getParentFile()
            if (r6 == 0) goto L_0x0010
            boolean r7 = r6.exists()
            if (r7 != 0) goto L_0x0010
            r6.mkdirs()
        L_0x0010:
            long r8 = r14.length()
            r10 = 0
            int r7 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r7 <= 0) goto L_0x001b
            r0 = 1
        L_0x001b:
            r4 = 0
            java.io.BufferedOutputStream r5 = new java.io.BufferedOutputStream     // Catch:{ IOException -> 0x0073 }
            java.io.FileOutputStream r7 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0073 }
            r7.<init>(r14, r0)     // Catch:{ IOException -> 0x0073 }
            r5.<init>(r7)     // Catch:{ IOException -> 0x0073 }
            r7 = 16384(0x4000, float:2.2959E-41)
            byte[] r1 = new byte[r7]     // Catch:{ IOException -> 0x0036, all -> 0x0070 }
        L_0x002a:
            int r2 = r13.read(r1)     // Catch:{ IOException -> 0x0036, all -> 0x0070 }
            r7 = -1
            if (r2 == r7) goto L_0x005f
            r7 = 0
            r5.write(r1, r7, r2)     // Catch:{ IOException -> 0x0036, all -> 0x0070 }
            goto L_0x002a
        L_0x0036:
            r3 = move-exception
            r4 = r5
        L_0x0038:
            com.amazonaws.AmazonClientException r7 = new com.amazonaws.AmazonClientException     // Catch:{ all -> 0x0055 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0055 }
            r8.<init>()     // Catch:{ all -> 0x0055 }
            java.lang.String r9 = "Unable to store object contents to disk: "
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ all -> 0x0055 }
            java.lang.String r9 = r3.getMessage()     // Catch:{ all -> 0x0055 }
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ all -> 0x0055 }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x0055 }
            r7.<init>(r8, r3)     // Catch:{ all -> 0x0055 }
            throw r7     // Catch:{ all -> 0x0055 }
        L_0x0055:
            r7 = move-exception
        L_0x0056:
            if (r4 == 0) goto L_0x005b
            r4.close()     // Catch:{ IOException -> 0x006c }
        L_0x005b:
            r13.close()     // Catch:{ IOException -> 0x006e }
        L_0x005e:
            throw r7
        L_0x005f:
            if (r5 == 0) goto L_0x0064
            r5.close()     // Catch:{ IOException -> 0x0068 }
        L_0x0064:
            r13.close()     // Catch:{ IOException -> 0x006a }
        L_0x0067:
            return
        L_0x0068:
            r7 = move-exception
            goto L_0x0064
        L_0x006a:
            r7 = move-exception
            goto L_0x0067
        L_0x006c:
            r8 = move-exception
            goto L_0x005b
        L_0x006e:
            r8 = move-exception
            goto L_0x005e
        L_0x0070:
            r7 = move-exception
            r4 = r5
            goto L_0x0056
        L_0x0073:
            r3 = move-exception
            goto L_0x0038
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amazonaws.mobileconnectors.s3.transferutility.DownloadTask.saveToFile(java.io.InputStream, java.io.File):void");
    }
}

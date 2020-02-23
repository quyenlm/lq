package com.amazonaws.mobileconnectors.s3.transferutility;

import android.database.Cursor;
import android.util.Log;
import com.amazonaws.AmazonClientException;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.util.json.JsonUtils;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class TransferRecord {
    private static final String TAG = "TransferRecord";
    public String bucketName;
    public long bytesCurrent;
    public long bytesTotal;
    public String cannedAcl;
    public String eTag;
    public String expirationTimeRuleId;
    public String file;
    public long fileOffset;
    public String headerCacheControl;
    public String headerContentDisposition;
    public String headerContentEncoding;
    public String headerContentLanguage;
    public String headerContentType;
    public String headerExpire;
    public String httpExpires;
    public int id;
    public int isEncrypted;
    public int isLastPart;
    public int isMultipart;
    public int isRequesterPays;
    public String key;
    public int mainUploadId;
    public String md5;
    public String multipartId;
    public int partNumber;
    public long rangeLast;
    public long rangeStart;
    public long speed;
    public String sseAlgorithm;
    public String sseKMSKey;
    public TransferState state;
    private Future<?> submittedTask;
    public TransferType type;
    public Map<String, String> userMetadata;
    public String versionId;

    public TransferRecord(int id2) {
        this.id = id2;
    }

    public void updateFromDB(Cursor c) {
        this.id = c.getInt(c.getColumnIndexOrThrow("_id"));
        this.mainUploadId = c.getInt(c.getColumnIndexOrThrow(TransferTable.COLUMN_MAIN_UPLOAD_ID));
        this.type = TransferType.getType(c.getString(c.getColumnIndexOrThrow("type")));
        this.state = TransferState.getState(c.getString(c.getColumnIndexOrThrow("state")));
        this.bucketName = c.getString(c.getColumnIndexOrThrow(TransferTable.COLUMN_BUCKET_NAME));
        this.key = c.getString(c.getColumnIndexOrThrow("key"));
        this.versionId = c.getString(c.getColumnIndexOrThrow(TransferTable.COLUMN_VERSION_ID));
        this.bytesTotal = c.getLong(c.getColumnIndexOrThrow(TransferTable.COLUMN_BYTES_TOTAL));
        this.bytesCurrent = c.getLong(c.getColumnIndexOrThrow(TransferTable.COLUMN_BYTES_CURRENT));
        this.speed = c.getLong(c.getColumnIndexOrThrow(TransferTable.COLUMN_SPEED));
        this.isRequesterPays = c.getInt(c.getColumnIndexOrThrow(TransferTable.COLUMN_IS_REQUESTER_PAYS));
        this.isMultipart = c.getInt(c.getColumnIndexOrThrow(TransferTable.COLUMN_IS_MULTIPART));
        this.isLastPart = c.getInt(c.getColumnIndexOrThrow(TransferTable.COLUMN_IS_LAST_PART));
        this.isEncrypted = c.getInt(c.getColumnIndexOrThrow(TransferTable.COLUMN_IS_ENCRYPTED));
        this.partNumber = c.getInt(c.getColumnIndexOrThrow(TransferTable.COLUMN_PART_NUM));
        this.eTag = c.getString(c.getColumnIndexOrThrow("etag"));
        this.file = c.getString(c.getColumnIndexOrThrow(TransferTable.COLUMN_FILE));
        this.multipartId = c.getString(c.getColumnIndexOrThrow(TransferTable.COLUMN_MULTIPART_ID));
        this.rangeStart = c.getLong(c.getColumnIndexOrThrow(TransferTable.COLUMN_DATA_RANGE_START));
        this.rangeLast = c.getLong(c.getColumnIndexOrThrow(TransferTable.COLUMN_DATA_RANGE_LAST));
        this.fileOffset = c.getLong(c.getColumnIndexOrThrow(TransferTable.COLUMN_FILE_OFFSET));
        this.headerContentType = c.getString(c.getColumnIndexOrThrow(TransferTable.COLUMN_HEADER_CONTENT_TYPE));
        this.headerContentLanguage = c.getString(c.getColumnIndexOrThrow(TransferTable.COLUMN_HEADER_CONTENT_LANGUAGE));
        this.headerContentDisposition = c.getString(c.getColumnIndexOrThrow(TransferTable.COLUMN_HEADER_CONTENT_DISPOSITION));
        this.headerContentEncoding = c.getString(c.getColumnIndexOrThrow(TransferTable.COLUMN_HEADER_CONTENT_ENCODING));
        this.headerCacheControl = c.getString(c.getColumnIndexOrThrow(TransferTable.COLUMN_HEADER_CACHE_CONTROL));
        this.headerExpire = c.getString(c.getColumnIndexOrThrow(TransferTable.COLUMN_HEADER_EXPIRE));
        this.userMetadata = JsonUtils.jsonToMap(c.getString(c.getColumnIndexOrThrow(TransferTable.COLUMN_USER_METADATA)));
        this.expirationTimeRuleId = c.getString(c.getColumnIndexOrThrow(TransferTable.COLUMN_EXPIRATION_TIME_RULE_ID));
        this.httpExpires = c.getString(c.getColumnIndexOrThrow(TransferTable.COLUMN_HTTP_EXPIRES_DATE));
        this.sseAlgorithm = c.getString(c.getColumnIndexOrThrow(TransferTable.COLUMN_SSE_ALGORITHM));
        this.sseKMSKey = c.getString(c.getColumnIndexOrThrow(TransferTable.COLUMN_SSE_KMS_KEY));
        this.md5 = c.getString(c.getColumnIndexOrThrow(TransferTable.COLUMN_CONTENT_MD5));
        this.cannedAcl = c.getString(c.getColumnIndexOrThrow(TransferTable.COLUMN_CANNED_ACL));
    }

    public boolean start(AmazonS3 s3, TransferDBUtil dbUtil, TransferStatusUpdater updater, TransferService.NetworkInfoReceiver networkInfo) {
        if (isRunning() || !checkIsReadyToRun()) {
            return false;
        }
        if (this.type.equals(TransferType.DOWNLOAD)) {
            this.submittedTask = TransferThreadPool.submitTask(new DownloadTask(this, s3, updater, networkInfo));
        } else {
            this.submittedTask = TransferThreadPool.submitTask(new UploadTask(this, s3, dbUtil, updater, networkInfo));
        }
        return true;
    }

    public boolean pause(AmazonS3 s3, TransferStatusUpdater updater) {
        if (isFinalState(this.state) || TransferState.PAUSED.equals(this.state)) {
            return false;
        }
        updater.updateState(this.id, TransferState.PAUSED);
        if (!isRunning()) {
            return true;
        }
        this.submittedTask.cancel(true);
        return true;
    }

    public boolean cancel(final AmazonS3 s3, TransferStatusUpdater updater) {
        if (isFinalState(this.state)) {
            return false;
        }
        updater.updateState(this.id, TransferState.CANCELED);
        if (isRunning()) {
            this.submittedTask.cancel(true);
        }
        if (this.isMultipart == 1) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        s3.abortMultipartUpload(new AbortMultipartUploadRequest(TransferRecord.this.bucketName, TransferRecord.this.key, TransferRecord.this.multipartId));
                        Log.d(TransferRecord.TAG, "Successfully clean up multipart upload: " + TransferRecord.this.id);
                    } catch (AmazonClientException e) {
                        Log.d(TransferRecord.TAG, "Failed to abort multiplart upload: " + TransferRecord.this.id, e);
                    }
                }
            }).start();
            return true;
        } else if (!TransferType.DOWNLOAD.equals(this.type)) {
            return true;
        } else {
            new File(this.file).delete();
            return true;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isRunning() {
        return this.submittedTask != null && !this.submittedTask.isDone();
    }

    /* access modifiers changed from: package-private */
    public void waitTillFinish(long timeout) throws InterruptedException, ExecutionException, TimeoutException {
        if (isRunning()) {
            this.submittedTask.get(timeout, TimeUnit.MILLISECONDS);
        }
    }

    private boolean isFinalState(TransferState state2) {
        return TransferState.COMPLETED.equals(state2) || TransferState.FAILED.equals(state2) || TransferState.CANCELED.equals(state2);
    }

    private boolean checkIsReadyToRun() {
        return this.partNumber == 0 && !TransferState.COMPLETED.equals(this.state);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append("id:").append(this.id).append(",").append("bucketName:").append(this.bucketName).append(",").append("key:").append(this.key).append(",").append("file:").append(this.file).append(",").append("type:").append(this.type).append(",").append("bytesTotal:").append(this.bytesTotal).append(",").append("bytesCurrent:").append(this.bytesCurrent).append(",").append("fileOffset:").append(this.fileOffset).append(",").append("state:").append(this.state).append(",").append("cannedAcl:").append(this.cannedAcl).append(",").append("mainUploadId:").append(this.mainUploadId).append(",").append("isMultipart:").append(this.isMultipart).append(",").append("isLastPart:").append(this.isLastPart).append(",").append("partNumber:").append(this.partNumber).append(",").append("multipartId:").append(this.multipartId).append(",").append("eTag:").append(this.eTag).append("]");
        return sb.toString();
    }
}

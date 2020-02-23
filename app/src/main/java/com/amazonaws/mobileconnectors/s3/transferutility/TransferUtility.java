package com.amazonaws.mobileconnectors.s3.transferutility;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.util.VersionInfoUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransferUtility {
    static final int MINIMUM_UPLOAD_PART_SIZE = 5242880;
    private static final String TAG = "TransferUtility";
    private final Context appContext;
    private final TransferDBUtil dbUtil = new TransferDBUtil(this.appContext);
    private final AmazonS3 s3;

    public TransferUtility(AmazonS3 s32, Context context) {
        this.s3 = s32;
        this.appContext = context.getApplicationContext();
    }

    public TransferObserver download(String bucket, String key, File file) {
        if (file == null || file.isDirectory()) {
            throw new IllegalArgumentException("Invalid file: " + file);
        }
        int recordId = Integer.parseInt(this.dbUtil.insertSingleTransferRecord(TransferType.DOWNLOAD, bucket, key, file).getLastPathSegment());
        if (file.isFile()) {
            Log.w(TAG, "Overwrite existing file: " + file);
            file.delete();
        }
        sendIntent("add_transfer", recordId);
        return new TransferObserver(recordId, this.dbUtil, bucket, key, file);
    }

    public TransferObserver upload(String bucket, String key, File file) {
        return upload(bucket, key, file, new ObjectMetadata());
    }

    public TransferObserver upload(String bucket, String key, File file, CannedAccessControlList cannedAcl) {
        return upload(bucket, key, file, new ObjectMetadata(), cannedAcl);
    }

    public TransferObserver upload(String bucket, String key, File file, ObjectMetadata metadata) {
        return upload(bucket, key, file, metadata, (CannedAccessControlList) null);
    }

    public TransferObserver upload(String bucket, String key, File file, ObjectMetadata metadata, CannedAccessControlList cannedAcl) {
        int recordId;
        if (file == null || file.isDirectory()) {
            throw new IllegalArgumentException("Invalid file: " + file);
        }
        if (shouldUploadInMultipart(file)) {
            recordId = createMultipartUploadRecords(bucket, key, file, metadata, cannedAcl);
        } else {
            recordId = Integer.parseInt(this.dbUtil.insertSingleTransferRecord(TransferType.UPLOAD, bucket, key, file, metadata, cannedAcl).getLastPathSegment());
        }
        sendIntent("add_transfer", recordId);
        return new TransferObserver(recordId, this.dbUtil, bucket, key, file);
    }

    public TransferObserver getTransferById(int id) {
        Cursor c = this.dbUtil.queryTransferById(id);
        try {
            if (c.moveToFirst()) {
                return new TransferObserver(id, this.dbUtil, c);
            }
            c.close();
            return null;
        } finally {
            c.close();
        }
    }

    public List<TransferObserver> getTransfersWithType(TransferType type) {
        List<TransferObserver> transferObservers = new ArrayList<>();
        Cursor c = this.dbUtil.queryAllTransfersWithType(type);
        while (c.moveToNext()) {
            try {
                transferObservers.add(new TransferObserver(c.getInt(c.getColumnIndexOrThrow("_id")), this.dbUtil, c));
            } finally {
                c.close();
            }
        }
        return transferObservers;
    }

    public List<TransferObserver> getTransfersWithTypeAndState(TransferType type, TransferState state) {
        List<TransferObserver> transferObservers = new ArrayList<>();
        Cursor c = this.dbUtil.queryTransfersWithTypeAndState(type, state);
        while (c.moveToNext()) {
            try {
                if (c.getInt(c.getColumnIndexOrThrow(TransferTable.COLUMN_PART_NUM)) == 0) {
                    transferObservers.add(new TransferObserver(c.getInt(c.getColumnIndexOrThrow("_id")), this.dbUtil, c));
                }
            } finally {
                c.close();
            }
        }
        return transferObservers;
    }

    private int createMultipartUploadRecords(String bucket, String key, File file, ObjectMetadata metadata, CannedAccessControlList cannedAcl) {
        long remainingLenth = file.length();
        long optimalPartSize = (long) Math.max(Math.ceil(((double) remainingLenth) / 10000.0d), 5242880.0d);
        long fileOffset = 0;
        int partCount = (int) Math.ceil(((double) remainingLenth) / ((double) optimalPartSize));
        ContentValues[] valuesArray = new ContentValues[(partCount + 1)];
        valuesArray[0] = this.dbUtil.generateContentValuesForMultiPartUpload(bucket, key, file, 0, 0, "", file.length(), 0, metadata, cannedAcl);
        int partNumber = 1;
        for (int i = 1; i < partCount + 1; i++) {
            valuesArray[i] = this.dbUtil.generateContentValuesForMultiPartUpload(bucket, key, file, fileOffset, partNumber, "", Math.min(optimalPartSize, remainingLenth), remainingLenth - optimalPartSize <= 0 ? 1 : 0, metadata, cannedAcl);
            fileOffset += optimalPartSize;
            remainingLenth -= optimalPartSize;
            partNumber++;
        }
        return this.dbUtil.bulkInsertTransferRecords(valuesArray);
    }

    public boolean pause(int id) {
        sendIntent("pause_transfer", id);
        return true;
    }

    public void pauseAllWithType(TransferType type) {
        Cursor c = this.dbUtil.queryAllTransfersWithType(type);
        while (c.moveToNext()) {
            try {
                pause(c.getInt(c.getColumnIndexOrThrow("_id")));
            } finally {
                c.close();
            }
        }
    }

    public TransferObserver resume(int id) {
        sendIntent("resume_transfer", id);
        return getTransferById(id);
    }

    public boolean cancel(int id) {
        sendIntent("cancel_transfer", id);
        return true;
    }

    public void cancelAllWithType(TransferType type) {
        Cursor c = this.dbUtil.queryAllTransfersWithType(type);
        while (c.moveToNext()) {
            try {
                cancel(c.getInt(c.getColumnIndexOrThrow("_id")));
            } finally {
                c.close();
            }
        }
    }

    public boolean deleteTransferRecord(int id) {
        cancel(id);
        return this.dbUtil.deleteTransferRecords(id) > 0;
    }

    private void sendIntent(String action, int id) {
        String s3Key = UUID.randomUUID().toString();
        S3ClientReference.put(s3Key, this.s3);
        Intent intent = new Intent(this.appContext, TransferService.class);
        intent.setAction(action);
        intent.putExtra("id", id);
        intent.putExtra("s3_reference_key", s3Key);
        this.appContext.startService(intent);
    }

    private boolean shouldUploadInMultipart(File file) {
        if (file == null || file.length() <= 5242880) {
            return false;
        }
        return true;
    }

    static <X extends AmazonWebServiceRequest> X appendTransferServiceUserAgentString(X request) {
        request.getRequestClientOptions().appendUserAgent("TransferService/" + VersionInfoUtils.getVersion());
        return request;
    }

    static <X extends AmazonWebServiceRequest> X appendMultipartTransferServiceUserAgentString(X request) {
        request.getRequestClientOptions().appendUserAgent("TransferService_multipart/" + VersionInfoUtils.getVersion());
        return request;
    }
}

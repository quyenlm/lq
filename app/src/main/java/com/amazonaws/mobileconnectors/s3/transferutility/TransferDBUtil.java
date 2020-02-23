package com.amazonaws.mobileconnectors.s3.transferutility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.util.json.JsonUtils;
import com.appsflyer.share.Constants;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

class TransferDBUtil {
    private static TransferDBBase transferDBBase;

    public TransferDBUtil(Context context) {
        if (transferDBBase == null) {
            transferDBBase = new TransferDBBase(context);
        }
    }

    public void closeDB() {
        if (transferDBBase != null) {
            transferDBBase.closeDBHelper();
        }
    }

    public Uri insertMultipartUploadRecord(String bucket, String key, File file, long fileOffset, int partNumber, String uploadId, long bytesTotal, int isLastPart) {
        return transferDBBase.insert(transferDBBase.getContentUri(), generateContentValuesForMultiPartUpload(bucket, key, file, fileOffset, partNumber, uploadId, bytesTotal, isLastPart, new ObjectMetadata(), (CannedAccessControlList) null));
    }

    public Uri insertSingleTransferRecord(TransferType type, String bucket, String key, File file, ObjectMetadata metadata) {
        return insertSingleTransferRecord(type, bucket, key, file, metadata, (CannedAccessControlList) null);
    }

    public Uri insertSingleTransferRecord(TransferType type, String bucket, String key, File file, ObjectMetadata metadata, CannedAccessControlList cannedAcl) {
        return transferDBBase.insert(transferDBBase.getContentUri(), generateContentValuesForSinglePartTransfer(type, bucket, key, file, metadata, cannedAcl));
    }

    public Uri insertSingleTransferRecord(TransferType type, String bucket, String key, File file) {
        return insertSingleTransferRecord(type, bucket, key, file, new ObjectMetadata());
    }

    public int bulkInsertTransferRecords(ContentValues[] valuesArray) {
        return transferDBBase.bulkInsert(transferDBBase.getContentUri(), valuesArray);
    }

    public int updateTransferRecord(TransferRecord transfer) {
        ContentValues cv = new ContentValues();
        cv.put("_id", Integer.valueOf(transfer.id));
        cv.put("state", transfer.state.toString());
        cv.put(TransferTable.COLUMN_BYTES_TOTAL, Long.valueOf(transfer.bytesTotal));
        cv.put(TransferTable.COLUMN_BYTES_CURRENT, Long.valueOf(transfer.bytesCurrent));
        return transferDBBase.update(getRecordUri(transfer.id), cv, (String) null, (String[]) null);
    }

    public int updateBytesTransferred(int id, long bytes) {
        ContentValues values = new ContentValues();
        values.put(TransferTable.COLUMN_BYTES_CURRENT, Long.valueOf(bytes));
        return transferDBBase.update(getRecordUri(id), values, (String) null, (String[]) null);
    }

    public int updateBytesTotalForDownload(int id, long bytes) {
        ContentValues values = new ContentValues();
        values.put(TransferTable.COLUMN_BYTES_TOTAL, Long.valueOf(bytes));
        return transferDBBase.update(getRecordUri(id), values, (String) null, (String[]) null);
    }

    public int updateState(int id, TransferState state) {
        ContentValues values = new ContentValues();
        values.put("state", state.toString());
        if (!TransferState.FAILED.equals(state)) {
            return transferDBBase.update(getRecordUri(id), values, (String) null, (String[]) null);
        }
        return transferDBBase.update(getRecordUri(id), values, "state not in (?,?,?,?,?) ", new String[]{TransferState.COMPLETED.toString(), TransferState.PENDING_NETWORK_DISCONNECT.toString(), TransferState.PAUSED.toString(), TransferState.CANCELED.toString(), TransferState.WAITING_FOR_NETWORK.toString()});
    }

    public int updateStateAndNotifyUpdate(int id, TransferState state) {
        ContentValues values = new ContentValues();
        values.put("state", state.toString());
        return transferDBBase.update(transferDBBase.getContentUri(), values, "_id=" + id, (String[]) null);
    }

    public int updateMultipartId(int id, String multipartId) {
        ContentValues values = new ContentValues();
        values.put(TransferTable.COLUMN_MULTIPART_ID, multipartId);
        return transferDBBase.update(getRecordUri(id), values, (String) null, (String[]) null);
    }

    public int updateETag(int id, String etag) {
        ContentValues values = new ContentValues();
        values.put("etag", etag);
        return transferDBBase.update(getRecordUri(id), values, (String) null, (String[]) null);
    }

    public int updateNetworkDisconnected() {
        ContentValues values = new ContentValues();
        values.put("state", TransferState.PENDING_NETWORK_DISCONNECT.toString());
        return transferDBBase.update(transferDBBase.getContentUri(), values, "state in (?,?,?)", new String[]{TransferState.IN_PROGRESS.toString(), TransferState.RESUMED_WAITING.toString(), TransferState.WAITING.toString()});
    }

    public int updateNetworkConnected() {
        ContentValues values = new ContentValues();
        values.put("state", TransferState.RESUMED_WAITING.toString());
        return transferDBBase.update(transferDBBase.getContentUri(), values, "state in (?,?)", new String[]{TransferState.PENDING_NETWORK_DISCONNECT.toString(), TransferState.WAITING_FOR_NETWORK.toString()});
    }

    public int setAllRunningRecordsToPausedBeforeShutdownService() {
        ContentValues values = new ContentValues();
        values.put("state", TransferState.PAUSED.toString());
        return transferDBBase.update(transferDBBase.getContentUri(), values, "state in (?,?,?,?)", new String[]{TransferState.IN_PROGRESS.toString(), TransferState.PENDING_PAUSE.toString(), TransferState.RESUMED_WAITING.toString(), TransferState.WAITING.toString()});
    }

    public int pauseAllWithType(TransferType type) {
        String selection;
        String[] selectionArgs;
        ContentValues values = new ContentValues();
        values.put("state", TransferState.PENDING_PAUSE.toString());
        if (type == TransferType.ANY) {
            selection = "state in (?,?,?)";
            selectionArgs = new String[]{TransferState.IN_PROGRESS.toString(), TransferState.RESUMED_WAITING.toString(), TransferState.WAITING.toString()};
        } else {
            selection = "state in (?,?,?) and type=?";
            selectionArgs = new String[]{TransferState.IN_PROGRESS.toString(), TransferState.RESUMED_WAITING.toString(), TransferState.WAITING.toString(), type.toString()};
        }
        return transferDBBase.update(transferDBBase.getContentUri(), values, selection, selectionArgs);
    }

    public int cancelAllWithType(TransferType type) {
        String selection;
        String[] selectionArgs;
        ContentValues values = new ContentValues();
        values.put("state", TransferState.PENDING_CANCEL.toString());
        if (type == TransferType.ANY) {
            selection = "state in (?,?,?,?,?)";
            selectionArgs = new String[]{TransferState.IN_PROGRESS.toString(), TransferState.RESUMED_WAITING.toString(), TransferState.WAITING.toString(), TransferState.PAUSED.toString(), TransferState.WAITING_FOR_NETWORK.toString()};
        } else {
            selection = "state in (?,?,?,?,?) and type=?";
            selectionArgs = new String[]{TransferState.IN_PROGRESS.toString(), TransferState.RESUMED_WAITING.toString(), TransferState.WAITING.toString(), TransferState.PAUSED.toString(), TransferState.WAITING_FOR_NETWORK.toString(), type.toString()};
        }
        return transferDBBase.update(transferDBBase.getContentUri(), values, selection, selectionArgs);
    }

    public Cursor queryAllTransfersWithType(TransferType type) {
        if (type == TransferType.ANY) {
            return transferDBBase.query(transferDBBase.getContentUri(), (String[]) null, (String) null, (String[]) null, (String) null);
        }
        return transferDBBase.query(transferDBBase.getContentUri(), (String[]) null, "type=?", new String[]{type.toString()}, (String) null);
    }

    public Cursor queryTransfersWithTypeAndState(TransferType type, TransferState state) {
        if (type == TransferType.ANY) {
            return transferDBBase.query(getStateUri(state), (String[]) null, (String) null, (String[]) null, (String) null);
        }
        return transferDBBase.query(getStateUri(state), (String[]) null, "type=?", new String[]{type.toString()}, (String) null);
    }

    public Cursor queryTransferById(int id) {
        return transferDBBase.query(getRecordUri(id), (String[]) null, (String) null, (String[]) null, (String) null);
    }

    public long queryBytesTransferredByMainUploadId(int mainUploadId) {
        Cursor c = transferDBBase.query(getPartUri(mainUploadId), (String[]) null, (String) null, (String[]) null, (String) null);
        long bytesTotal = 0;
        while (c.moveToNext()) {
            try {
                if (TransferState.PART_COMPLETED.equals(TransferState.getState(c.getString(c.getColumnIndexOrThrow("state"))))) {
                    bytesTotal += c.getLong(c.getColumnIndexOrThrow(TransferTable.COLUMN_BYTES_TOTAL));
                }
            } finally {
                c.close();
            }
        }
        return bytesTotal;
    }

    public int deleteTransferRecords(int id) {
        return transferDBBase.delete(getRecordUri(id), (String) null, (String[]) null);
    }

    public List<PartETag> queryPartETagsOfUpload(int mainUploadId) {
        List<PartETag> partETags = new ArrayList<>();
        Cursor c = transferDBBase.query(getPartUri(mainUploadId), (String[]) null, (String) null, (String[]) null, (String) null);
        while (c.moveToNext()) {
            try {
                partETags.add(new PartETag(c.getInt(c.getColumnIndexOrThrow(TransferTable.COLUMN_PART_NUM)), c.getString(c.getColumnIndexOrThrow("etag"))));
            } finally {
                c.close();
            }
        }
        return partETags;
    }

    public List<UploadPartRequest> getNonCompletedPartRequestsFromDB(int mainUploadId, String multipartId) {
        ArrayList<UploadPartRequest> list = new ArrayList<>();
        Cursor c = transferDBBase.query(getPartUri(mainUploadId), (String[]) null, (String) null, (String[]) null, (String) null);
        while (c.moveToNext()) {
            try {
                if (!TransferState.PART_COMPLETED.equals(TransferState.getState(c.getString(c.getColumnIndexOrThrow("state"))))) {
                    list.add(new UploadPartRequest().withId(c.getInt(c.getColumnIndexOrThrow("_id"))).withMainUploadId(c.getInt(c.getColumnIndexOrThrow(TransferTable.COLUMN_MAIN_UPLOAD_ID))).withBucketName(c.getString(c.getColumnIndexOrThrow(TransferTable.COLUMN_BUCKET_NAME))).withKey(c.getString(c.getColumnIndexOrThrow("key"))).withUploadId(multipartId).withFile(new File(c.getString(c.getColumnIndexOrThrow(TransferTable.COLUMN_FILE)))).withFileOffset(c.getLong(c.getColumnIndexOrThrow(TransferTable.COLUMN_FILE_OFFSET))).withPartNumber(c.getInt(c.getColumnIndexOrThrow(TransferTable.COLUMN_PART_NUM))).withPartSize(c.getLong(c.getColumnIndexOrThrow(TransferTable.COLUMN_BYTES_TOTAL))).withLastPart(1 == c.getInt(c.getColumnIndexOrThrow(TransferTable.COLUMN_IS_LAST_PART))));
                }
            } finally {
                c.close();
            }
        }
        return list;
    }

    public ContentValues generateContentValuesForMultiPartUpload(String bucket, String key, File file, long fileOffset, int partNumber, String uploadId, long bytesTotal, int isLastPart, ObjectMetadata metadata, CannedAccessControlList cannedAcl) {
        ContentValues values = new ContentValues();
        values.put("type", TransferType.UPLOAD.toString());
        values.put("state", TransferState.WAITING.toString());
        values.put(TransferTable.COLUMN_BUCKET_NAME, bucket);
        values.put("key", key);
        values.put(TransferTable.COLUMN_FILE, file.getAbsolutePath());
        values.put(TransferTable.COLUMN_BYTES_CURRENT, 0L);
        values.put(TransferTable.COLUMN_BYTES_TOTAL, Long.valueOf(bytesTotal));
        values.put(TransferTable.COLUMN_IS_MULTIPART, 1);
        values.put(TransferTable.COLUMN_PART_NUM, Integer.valueOf(partNumber));
        values.put(TransferTable.COLUMN_FILE_OFFSET, Long.valueOf(fileOffset));
        values.put(TransferTable.COLUMN_MULTIPART_ID, uploadId);
        values.put(TransferTable.COLUMN_IS_LAST_PART, Integer.valueOf(isLastPart));
        values.put(TransferTable.COLUMN_IS_ENCRYPTED, 0);
        values.putAll(generateContentValuesForObjectMetadata(metadata));
        if (cannedAcl != null) {
            values.put(TransferTable.COLUMN_CANNED_ACL, cannedAcl.toString());
        }
        return values;
    }

    private ContentValues generateContentValuesForObjectMetadata(ObjectMetadata metadata) {
        ContentValues values = new ContentValues();
        values.put(TransferTable.COLUMN_USER_METADATA, JsonUtils.mapToString(metadata.getUserMetadata()));
        values.put(TransferTable.COLUMN_HEADER_CONTENT_TYPE, metadata.getContentType());
        values.put(TransferTable.COLUMN_HEADER_CONTENT_ENCODING, metadata.getContentEncoding());
        values.put(TransferTable.COLUMN_HEADER_CACHE_CONTROL, metadata.getCacheControl());
        values.put(TransferTable.COLUMN_CONTENT_MD5, metadata.getContentMD5());
        values.put(TransferTable.COLUMN_HEADER_CONTENT_DISPOSITION, metadata.getContentDisposition());
        values.put(TransferTable.COLUMN_SSE_ALGORITHM, metadata.getSSEAlgorithm());
        values.put(TransferTable.COLUMN_SSE_KMS_KEY, metadata.getSSEKMSKeyId());
        values.put(TransferTable.COLUMN_EXPIRATION_TIME_RULE_ID, metadata.getExpirationTimeRuleId());
        if (metadata.getHttpExpiresDate() != null) {
            values.put(TransferTable.COLUMN_HTTP_EXPIRES_DATE, String.valueOf(metadata.getHttpExpiresDate().getTime()));
        }
        return values;
    }

    private ContentValues generateContentValuesForSinglePartTransfer(TransferType type, String bucket, String key, File file, ObjectMetadata metadata, CannedAccessControlList cannedAcl) {
        long j = 0;
        ContentValues values = new ContentValues();
        values.put("type", type.toString());
        values.put("state", TransferState.WAITING.toString());
        values.put(TransferTable.COLUMN_BUCKET_NAME, bucket);
        values.put("key", key);
        values.put(TransferTable.COLUMN_FILE, file.getAbsolutePath());
        values.put(TransferTable.COLUMN_BYTES_CURRENT, 0L);
        if (type.equals(TransferType.UPLOAD)) {
            if (file != null) {
                j = file.length();
            }
            values.put(TransferTable.COLUMN_BYTES_TOTAL, Long.valueOf(j));
        }
        values.put(TransferTable.COLUMN_IS_MULTIPART, 0);
        values.put(TransferTable.COLUMN_PART_NUM, 0);
        values.put(TransferTable.COLUMN_IS_ENCRYPTED, 0);
        values.putAll(generateContentValuesForObjectMetadata(metadata));
        if (cannedAcl != null) {
            values.put(TransferTable.COLUMN_CANNED_ACL, cannedAcl.toString());
        }
        return values;
    }

    public Uri getContentUri() {
        return transferDBBase.getContentUri();
    }

    public Uri getRecordUri(int id) {
        return Uri.parse(transferDBBase.getContentUri() + Constants.URL_PATH_DELIMITER + id);
    }

    public Uri getPartUri(int mainUploadId) {
        return Uri.parse(transferDBBase.getContentUri() + "/part/" + mainUploadId);
    }

    public Uri getStateUri(TransferState state) {
        return Uri.parse(transferDBBase.getContentUri() + "/state/" + state.toString());
    }

    /* access modifiers changed from: package-private */
    public TransferRecord getTransferById(int id) {
        TransferRecord transfer = null;
        Cursor c = queryTransferById(id);
        try {
            if (c.moveToFirst()) {
                TransferRecord transfer2 = new TransferRecord(0);
                try {
                    transfer2.updateFromDB(c);
                    transfer = transfer2;
                } catch (Throwable th) {
                    th = th;
                    TransferRecord transferRecord = transfer2;
                    c.close();
                    throw th;
                }
            }
            c.close();
            return transfer;
        } catch (Throwable th2) {
            th = th2;
            c.close();
            throw th;
        }
    }
}

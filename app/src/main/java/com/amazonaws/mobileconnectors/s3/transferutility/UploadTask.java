package com.amazonaws.mobileconnectors.s3.transferutility;

import android.util.Log;
import com.amazonaws.AmazonClientException;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferService;
import com.amazonaws.retry.RetryUtils;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.services.s3.util.Mimetypes;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

class UploadTask implements Callable<Boolean> {
    private static final String TAG = "UploadTask";
    private static final Map<String, CannedAccessControlList> cannedAclMap = new HashMap();
    private final TransferDBUtil dbUtil;
    private final TransferService.NetworkInfoReceiver networkInfo;
    private final AmazonS3 s3;
    private final TransferStatusUpdater updater;
    private final TransferRecord upload;

    public UploadTask(TransferRecord uploadInfo, AmazonS3 s32, TransferDBUtil dbUtil2, TransferStatusUpdater updater2, TransferService.NetworkInfoReceiver networkInfo2) {
        this.upload = uploadInfo;
        this.s3 = s32;
        this.dbUtil = dbUtil2;
        this.updater = updater2;
        this.networkInfo = networkInfo2;
    }

    public Boolean call() throws Exception {
        if (!this.networkInfo.isNetworkConnected()) {
            this.updater.updateState(this.upload.id, TransferState.WAITING_FOR_NETWORK);
            return false;
        }
        this.updater.updateState(this.upload.id, TransferState.IN_PROGRESS);
        if (this.upload.isMultipart == 1 && this.upload.partNumber == 0) {
            return uploadMultipartAndWaitForCompletion();
        }
        if (this.upload.isMultipart == 0) {
            return uploadSinglePartAndWaitForCompletion();
        }
        return false;
    }

    private Boolean uploadMultipartAndWaitForCompletion() throws ExecutionException {
        long bytesAlreadyTransferrd = 0;
        if (this.upload.multipartId == null || this.upload.multipartId.isEmpty()) {
            PutObjectRequest putObjectRequest = createPutObjectRequest(this.upload);
            TransferUtility.appendMultipartTransferServiceUserAgentString(putObjectRequest);
            try {
                this.upload.multipartId = initiateMultipartUpload(putObjectRequest);
                this.dbUtil.updateMultipartId(this.upload.id, this.upload.multipartId);
            } catch (AmazonClientException ace) {
                Log.e(TAG, "Error initiating multipart upload: " + this.upload.id + " due to " + ace.getMessage());
                this.updater.throwError(this.upload.id, ace);
                this.updater.updateState(this.upload.id, TransferState.FAILED);
                return false;
            }
        } else {
            bytesAlreadyTransferrd = this.dbUtil.queryBytesTransferredByMainUploadId(this.upload.id);
            if (bytesAlreadyTransferrd > 0) {
                Log.d(TAG, String.format("Resume transfer %d from %d bytes", new Object[]{Integer.valueOf(this.upload.id), Long.valueOf(bytesAlreadyTransferrd)}));
            }
        }
        this.updater.updateProgress(this.upload.id, bytesAlreadyTransferrd, this.upload.bytesTotal);
        List<UploadPartRequest> requestList = this.dbUtil.getNonCompletedPartRequestsFromDB(this.upload.id, this.upload.multipartId);
        Log.d(TAG, "multipart upload " + this.upload.id + " in " + requestList.size() + " parts.");
        ArrayList<Future<Boolean>> futures = new ArrayList<>();
        for (UploadPartRequest request : requestList) {
            TransferUtility.appendMultipartTransferServiceUserAgentString(request);
            request.setGeneralProgressListener(this.updater.newProgressListener(this.upload.id));
            ArrayList<Future<Boolean>> arrayList = futures;
            arrayList.add(TransferThreadPool.submitTask(new UploadPartTask(request, this.s3, this.dbUtil)));
        }
        boolean isSuccess = true;
        try {
            Iterator<Future<Boolean>> it = futures.iterator();
            while (it.hasNext()) {
                isSuccess &= it.next().get().booleanValue();
            }
            if (!isSuccess) {
                return false;
            }
            try {
                completeMultiPartUpload(this.upload.id, this.upload.bucketName, this.upload.key, this.upload.multipartId);
                this.updater.updateProgress(this.upload.id, this.upload.bytesTotal, this.upload.bytesTotal);
                this.updater.updateState(this.upload.id, TransferState.COMPLETED);
                return true;
            } catch (AmazonClientException ace2) {
                Log.e(TAG, "Failed to complete multipart: " + this.upload.id + " due to " + ace2.getMessage());
                this.updater.throwError(this.upload.id, ace2);
                this.updater.updateState(this.upload.id, TransferState.FAILED);
                return false;
            }
        } catch (InterruptedException e) {
            Iterator<Future<Boolean>> it2 = futures.iterator();
            while (it2.hasNext()) {
                it2.next().cancel(true);
            }
            Log.d(TAG, "Transfer " + this.upload.id + " is interrupted by user");
            return false;
        } catch (ExecutionException ee) {
            if (ee.getCause() != null && (ee.getCause() instanceof Exception)) {
                Exception e2 = (Exception) ee.getCause();
                if (RetryUtils.isInterrupted(e2)) {
                    Log.d(TAG, "Transfer " + this.upload.id + " is interrupted by user");
                    return false;
                }
                if (e2.getCause() != null && (e2.getCause() instanceof IOException) && !this.networkInfo.isNetworkConnected()) {
                    Log.d(TAG, "Transfer " + this.upload.id + " waits for network");
                    this.updater.updateState(this.upload.id, TransferState.WAITING_FOR_NETWORK);
                }
                this.updater.throwError(this.upload.id, e2);
            }
            this.updater.updateState(this.upload.id, TransferState.FAILED);
            return false;
        }
    }

    private Boolean uploadSinglePartAndWaitForCompletion() {
        PutObjectRequest putObjectRequest = createPutObjectRequest(this.upload);
        long length = putObjectRequest.getFile().length();
        TransferUtility.appendTransferServiceUserAgentString(putObjectRequest);
        this.updater.updateProgress(this.upload.id, 0, length);
        putObjectRequest.setGeneralProgressListener(this.updater.newProgressListener(this.upload.id));
        try {
            this.s3.putObject(putObjectRequest);
            this.updater.updateProgress(this.upload.id, length, length);
            this.updater.updateState(this.upload.id, TransferState.COMPLETED);
            return true;
        } catch (Exception e) {
            if (RetryUtils.isInterrupted(e)) {
                Log.d(TAG, "Transfer " + this.upload.id + " is interrupted by user");
                return false;
            }
            if (e.getCause() != null && (e.getCause() instanceof IOException) && !this.networkInfo.isNetworkConnected()) {
                Log.d(TAG, "Transfer " + this.upload.id + " waits for network");
                this.updater.updateState(this.upload.id, TransferState.WAITING_FOR_NETWORK);
            }
            Log.e(TAG, "Failed to upload: " + this.upload.id + " due to " + e.getMessage());
            this.updater.throwError(this.upload.id, e);
            this.updater.updateState(this.upload.id, TransferState.FAILED);
            return false;
        }
    }

    private void completeMultiPartUpload(int mainUploadId, String bucket, String key, String multipartId) throws AmazonClientException {
        CompleteMultipartUploadRequest completeRequest = new CompleteMultipartUploadRequest(bucket, key, multipartId, this.dbUtil.queryPartETagsOfUpload(mainUploadId));
        TransferUtility.appendMultipartTransferServiceUserAgentString(completeRequest);
        this.s3.completeMultipartUpload(completeRequest);
    }

    private String initiateMultipartUpload(PutObjectRequest putObjectRequest) throws AmazonClientException {
        InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(putObjectRequest.getBucketName(), putObjectRequest.getKey()).withCannedACL(putObjectRequest.getCannedAcl()).withObjectMetadata(putObjectRequest.getMetadata());
        TransferUtility.appendMultipartTransferServiceUserAgentString(initiateMultipartUploadRequest);
        return this.s3.initiateMultipartUpload(initiateMultipartUploadRequest).getUploadId();
    }

    private PutObjectRequest createPutObjectRequest(TransferRecord upload2) {
        File file = new File(upload2.file);
        PutObjectRequest putObjectRequest = new PutObjectRequest(upload2.bucketName, upload2.key, file);
        ObjectMetadata om = new ObjectMetadata();
        om.setContentLength(file.length());
        if (upload2.headerCacheControl != null) {
            om.setCacheControl(upload2.headerCacheControl);
        }
        if (upload2.headerContentDisposition != null) {
            om.setContentDisposition(upload2.headerContentDisposition);
        }
        if (upload2.headerContentEncoding != null) {
            om.setContentEncoding(upload2.headerContentEncoding);
        }
        if (upload2.headerContentType != null) {
            om.setContentType(upload2.headerContentType);
        } else {
            om.setContentType(Mimetypes.getInstance().getMimetype(file));
        }
        if (upload2.expirationTimeRuleId != null) {
            om.setExpirationTimeRuleId(upload2.expirationTimeRuleId);
        }
        if (upload2.httpExpires != null) {
            om.setHttpExpiresDate(new Date(Long.valueOf(upload2.httpExpires).longValue()));
        }
        if (upload2.sseAlgorithm != null) {
            om.setSSEAlgorithm(upload2.sseAlgorithm);
        }
        if (upload2.userMetadata != null) {
            om.setUserMetadata(upload2.userMetadata);
        }
        if (upload2.md5 != null) {
            om.setContentMD5(upload2.md5);
        }
        putObjectRequest.setMetadata(om);
        putObjectRequest.setCannedAcl(getCannedAclFromString(upload2.cannedAcl));
        return putObjectRequest;
    }

    static {
        for (CannedAccessControlList cannedAcl : CannedAccessControlList.values()) {
            cannedAclMap.put(cannedAcl.toString(), cannedAcl);
        }
    }

    private static CannedAccessControlList getCannedAclFromString(String cannedAcl) {
        if (cannedAcl == null) {
            return null;
        }
        return cannedAclMap.get(cannedAcl);
    }
}

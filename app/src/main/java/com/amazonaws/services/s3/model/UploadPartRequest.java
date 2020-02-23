package com.amazonaws.services.s3.model;

import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.event.ProgressListener;
import java.io.File;
import java.io.InputStream;

public class UploadPartRequest extends AmazonWebServiceRequest {
    private String bucketName;
    private File file;
    private long fileOffset;
    private ProgressListener generalProgressListener;
    private int id;
    private InputStream inputStream;
    private boolean isLastPart;
    private String key;
    private int mainUploadId;
    private String md5Digest;
    private int partNumber;
    private long partSize;
    private SSECustomerKey sseCustomerKey;
    private String uploadId;

    public void setId(int id2) {
        this.id = id2;
    }

    public int getId() {
        return this.id;
    }

    public UploadPartRequest withId(int id2) {
        this.id = id2;
        return this;
    }

    public void setMainUploadId(int id2) {
        this.mainUploadId = id2;
    }

    public int getMainUploadId() {
        return this.mainUploadId;
    }

    public UploadPartRequest withMainUploadId(int id2) {
        this.mainUploadId = id2;
        return this;
    }

    public void setInputStream(InputStream inputStream2) {
        this.inputStream = inputStream2;
    }

    public InputStream getInputStream() {
        return this.inputStream;
    }

    public UploadPartRequest withInputStream(InputStream inputStream2) {
        setInputStream(inputStream2);
        return this;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName2) {
        this.bucketName = bucketName2;
    }

    public UploadPartRequest withBucketName(String bucketName2) {
        this.bucketName = bucketName2;
        return this;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key2) {
        this.key = key2;
    }

    public UploadPartRequest withKey(String key2) {
        this.key = key2;
        return this;
    }

    public String getUploadId() {
        return this.uploadId;
    }

    public void setUploadId(String uploadId2) {
        this.uploadId = uploadId2;
    }

    public UploadPartRequest withUploadId(String uploadId2) {
        this.uploadId = uploadId2;
        return this;
    }

    public int getPartNumber() {
        return this.partNumber;
    }

    public void setPartNumber(int partNumber2) {
        this.partNumber = partNumber2;
    }

    public UploadPartRequest withPartNumber(int partNumber2) {
        this.partNumber = partNumber2;
        return this;
    }

    public long getPartSize() {
        return this.partSize;
    }

    public void setPartSize(long partSize2) {
        this.partSize = partSize2;
    }

    public UploadPartRequest withPartSize(long partSize2) {
        this.partSize = partSize2;
        return this;
    }

    public String getMd5Digest() {
        return this.md5Digest;
    }

    public void setMd5Digest(String md5Digest2) {
        this.md5Digest = md5Digest2;
    }

    public UploadPartRequest withMD5Digest(String md5Digest2) {
        this.md5Digest = md5Digest2;
        return this;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file2) {
        this.file = file2;
    }

    public UploadPartRequest withFile(File file2) {
        setFile(file2);
        return this;
    }

    public long getFileOffset() {
        return this.fileOffset;
    }

    public void setFileOffset(long fileOffset2) {
        this.fileOffset = fileOffset2;
    }

    public UploadPartRequest withFileOffset(long fileOffset2) {
        setFileOffset(fileOffset2);
        return this;
    }

    @Deprecated
    public void setProgressListener(ProgressListener progressListener) {
        this.generalProgressListener = new LegacyS3ProgressListener(progressListener);
    }

    @Deprecated
    public ProgressListener getProgressListener() {
        if (this.generalProgressListener instanceof LegacyS3ProgressListener) {
            return ((LegacyS3ProgressListener) this.generalProgressListener).unwrap();
        }
        return null;
    }

    @Deprecated
    public UploadPartRequest withProgressListener(ProgressListener progressListener) {
        setProgressListener(progressListener);
        return this;
    }

    public boolean isLastPart() {
        return this.isLastPart;
    }

    public void setLastPart(boolean isLastPart2) {
        this.isLastPart = isLastPart2;
    }

    public UploadPartRequest withLastPart(boolean isLastPart2) {
        setLastPart(isLastPart2);
        return this;
    }

    public SSECustomerKey getSSECustomerKey() {
        return this.sseCustomerKey;
    }

    public void setSSECustomerKey(SSECustomerKey sseKey) {
        this.sseCustomerKey = sseKey;
    }

    public UploadPartRequest withSSECustomerKey(SSECustomerKey sseKey) {
        setSSECustomerKey(sseKey);
        return this;
    }

    public void setGeneralProgressListener(ProgressListener generalProgressListener2) {
        this.generalProgressListener = generalProgressListener2;
    }

    public ProgressListener getGeneralProgressListener() {
        return this.generalProgressListener;
    }

    public UploadPartRequest withGeneralProgressListener(ProgressListener progressListener) {
        setGeneralProgressListener(progressListener);
        return this;
    }
}

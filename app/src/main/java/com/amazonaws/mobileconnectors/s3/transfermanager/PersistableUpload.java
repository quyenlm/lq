package com.amazonaws.mobileconnectors.s3.transfermanager;

import com.amazonaws.util.json.JsonUtils;
import java.io.IOException;
import java.io.StringWriter;

@Deprecated
public final class PersistableUpload extends PersistableTransfer {
    static final String TYPE = "upload";
    private final String bucketName;
    private final String file;
    private final String key;
    private final String multipartUploadId;
    private final long mutlipartUploadThreshold;
    private final long partSize;
    private final String pauseType;

    @Deprecated
    public PersistableUpload() {
        this((String) null, (String) null, (String) null, (String) null, -1, -1);
    }

    public PersistableUpload(String bucketName2, String key2, String file2, String multipartUploadId2, long partSize2, long mutlipartUploadThreshold2) {
        this.pauseType = TYPE;
        this.bucketName = bucketName2;
        this.key = key2;
        this.file = file2;
        this.multipartUploadId = multipartUploadId2;
        this.partSize = partSize2;
        this.mutlipartUploadThreshold = mutlipartUploadThreshold2;
    }

    /* access modifiers changed from: package-private */
    public String getBucketName() {
        return this.bucketName;
    }

    /* access modifiers changed from: package-private */
    public String getKey() {
        return this.key;
    }

    /* access modifiers changed from: package-private */
    public String getMultipartUploadId() {
        return this.multipartUploadId;
    }

    /* access modifiers changed from: package-private */
    public long getPartSize() {
        return this.partSize;
    }

    /* access modifiers changed from: package-private */
    public long getMutlipartUploadThreshold() {
        return this.mutlipartUploadThreshold;
    }

    /* access modifiers changed from: package-private */
    public String getFile() {
        return this.file;
    }

    /* access modifiers changed from: package-private */
    public String getPauseType() {
        return TYPE;
    }

    public String serialize() {
        StringWriter out = new StringWriter();
        try {
            JsonUtils.getJsonWriter(out).beginObject().name("pauseType").value(TYPE).name("bucketName").value(this.bucketName).name("key").value(this.key).name(TransferTable.COLUMN_FILE).value(this.file).name("multipartUploadId").value(this.multipartUploadId).name("partSize").value(this.partSize).name("mutlipartUploadThreshold").value(this.mutlipartUploadThreshold).endObject().close();
            return out.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}

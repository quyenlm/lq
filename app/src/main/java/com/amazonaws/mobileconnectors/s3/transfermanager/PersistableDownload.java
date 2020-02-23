package com.amazonaws.mobileconnectors.s3.transfermanager;

import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import java.io.IOException;
import java.io.StringWriter;

@Deprecated
public final class PersistableDownload extends PersistableTransfer {
    static final String TYPE = "download";
    private final String bucketName;
    private final String file;
    private final boolean isRequesterPays;
    private final String key;
    private final String pauseType;
    private final long[] range;
    private final ResponseHeaderOverrides responseHeaders;
    private final String versionId;

    @Deprecated
    public PersistableDownload() {
        this((String) null, (String) null, (String) null, (long[]) null, (ResponseHeaderOverrides) null, false, (String) null);
    }

    public PersistableDownload(String bucketName2, String key2, String versionId2, long[] range2, ResponseHeaderOverrides responseHeaders2, boolean isRequesterPays2, String file2) {
        this.pauseType = "download";
        this.bucketName = bucketName2;
        this.key = key2;
        this.versionId = versionId2;
        this.range = range2 == null ? null : (long[]) range2.clone();
        this.responseHeaders = responseHeaders2;
        this.isRequesterPays = isRequesterPays2;
        this.file = file2;
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
    public String getVersionId() {
        return this.versionId;
    }

    /* access modifiers changed from: package-private */
    public long[] getRange() {
        if (this.range == null) {
            return null;
        }
        return (long[]) this.range.clone();
    }

    /* access modifiers changed from: package-private */
    public ResponseHeaderOverrides getResponseHeaders() {
        return this.responseHeaders;
    }

    /* access modifiers changed from: package-private */
    public boolean isRequesterPays() {
        return this.isRequesterPays;
    }

    /* access modifiers changed from: package-private */
    public String getFile() {
        return this.file;
    }

    /* access modifiers changed from: package-private */
    public String getPauseType() {
        return "download";
    }

    public String serialize() {
        StringWriter out = new StringWriter();
        AwsJsonWriter writer = JsonUtils.getJsonWriter(out);
        try {
            writer.beginObject().name("pauseType").value("download").name("bucketName").value(this.bucketName).name("key").value(this.key).name(TransferTable.COLUMN_FILE).value(this.file).name("versionId").value(this.versionId).name("isRequesterPays").value(this.isRequesterPays);
            if (this.range != null) {
                writer.name("range").beginArray();
                for (long r : this.range) {
                    writer.value(r);
                }
                writer.endArray();
            }
            if (this.responseHeaders != null) {
                writer.name("responseHeaders").beginObject().name(HttpRequestParams.NOTICE_CONTENT_TYPE).value(this.responseHeaders.getContentType()).name("contentLanguage").value(this.responseHeaders.getContentLanguage()).name("expires").value(this.responseHeaders.getExpires()).name("cacheControl").value(this.responseHeaders.getCacheControl()).name("contentDisposition").value(this.responseHeaders.getContentDisposition()).name("contentEncoding").value(this.responseHeaders.getContentEncoding()).endObject();
            }
            writer.endObject().close();
            return out.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}

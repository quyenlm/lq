package com.tencent.component.net.download.multiplex.http;

import com.tencent.component.net.download.multiplex.DownloaderLog;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public class MttResponse {
    private static final String TAG = "MttResponse";
    private String mByteRanges;
    private String mCacheControl;
    private String mCharset;
    private String mConnection;
    private String mContentDisposition;
    private String mContentEncoding;
    private long mContentLength;
    private String mContentRange;
    private ContentType mContentType;
    private String mETag;
    private int mHeadFlow = 0;
    private MttInputStream mInputStream;
    private String mLastModify;
    private String mLocation;
    private String mQEncrypt;
    private String mQNeed;
    private String mQNkey;
    private String mQSZip;
    private String mQTip;
    private String mServer;
    private Integer mStatusCode = new Integer(-1);
    private String mTransferEncoding;
    private String mVersion;

    public int getFlow() {
        if (this.mInputStream != null) {
            return this.mInputStream.getFlow() + this.mHeadFlow;
        }
        return this.mHeadFlow;
    }

    public MttInputStream getInputStream() {
        return this.mInputStream;
    }

    public void setInputStream(MttInputStream inputStream) {
        this.mInputStream = inputStream;
    }

    public void setStatusCode(Integer statusCode) {
        this.mStatusCode = statusCode;
    }

    public Integer getStatusCode() {
        return this.mStatusCode;
    }

    public String getVersion() {
        return this.mVersion;
    }

    public void setVersion(String version) {
        this.mVersion = version;
    }

    public String getLocation() {
        return this.mLocation;
    }

    public void setLocation(String location) {
        this.mLocation = location;
    }

    public ContentType getContentType() {
        return this.mContentType;
    }

    public void setContentType(ContentType contentType) {
        this.mContentType = contentType;
    }

    public long getContentLength() {
        return this.mContentLength;
    }

    public void setContentLength(long contentLength) {
        this.mContentLength = contentLength;
    }

    public String getETag() {
        return this.mETag;
    }

    public void setETag(String etag) {
        this.mETag = etag;
    }

    public String getCharset() {
        return this.mCharset;
    }

    public void setCharset(String charset) {
        this.mCharset = charset;
    }

    public String getTransferEncoding() {
        return this.mTransferEncoding;
    }

    public void setTransferEncoding(String transferEncoding) {
        this.mTransferEncoding = transferEncoding;
    }

    public String getLastModify() {
        return this.mLastModify;
    }

    public void setLastModify(String lastModify) {
        this.mLastModify = lastModify;
    }

    public String getCacheControl() {
        return this.mCacheControl;
    }

    public void setCacheControl(String cacheControl) {
        this.mCacheControl = cacheControl;
    }

    public String getServer() {
        return this.mServer;
    }

    public void setServer(String server) {
        this.mServer = server;
    }

    public String getContentEncoding() {
        return this.mContentEncoding;
    }

    public void setContentEncoding(String contentEncoding) {
        this.mContentEncoding = contentEncoding;
    }

    public String getAcceptRanges() {
        return this.mByteRanges;
    }

    public void setByteRanges(String byteRanges) {
        this.mByteRanges = byteRanges;
    }

    public String getConnection() {
        return this.mConnection;
    }

    public void setConnection(String connection) {
        this.mConnection = connection;
    }

    public String getContentRange() {
        return this.mContentRange;
    }

    public void setContentRange(String contentRange) {
        this.mContentRange = contentRange;
    }

    public String getContentDisposition() {
        return this.mContentDisposition;
    }

    public void setContentDisposition(String contentDisposition) {
        this.mContentDisposition = contentDisposition;
    }

    public String getQNeed() {
        return this.mQNeed;
    }

    public void setQNeed(String mQNeed2) {
        this.mQNeed = mQNeed2;
    }

    public String getQNkey() {
        return this.mQNkey;
    }

    public void setQNkey(String mQNkey2) {
        this.mQNkey = mQNkey2;
    }

    public String getQTip() {
        return this.mQTip;
    }

    public void setQTip(String mQTip2) {
        this.mQTip = mQTip2;
    }

    public String getQSZip() {
        return this.mQSZip;
    }

    public void setQSZip(String qSZip) {
        this.mQSZip = qSZip;
    }

    public String getQEncrypt() {
        return this.mQEncrypt;
    }

    public void setQEncrypt(String qEncrypt) {
        this.mQEncrypt = qEncrypt;
    }

    public void setFlow(Map<String, List<String>> headerFields) {
        if (headerFields == null) {
            DownloaderLog.d(TAG, "headerFields == null");
            return;
        }
        DownloaderLog.d(TAG, "start to add flow");
        for (Map.Entry<String, List<String>> entry : headerFields.entrySet()) {
            for (String tempString : entry.getValue()) {
                try {
                    this.mHeadFlow += tempString.getBytes("UTF-8").length;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

package com.tencent.component.net.download.multiplex.http;

public abstract class Requester {
    public static final int BROKER_READ_TIMEOUT = 30000;
    public static final int REQUEST_LOCAL = 2;
    public static final int REQUEST_REMOTE = 1;
    protected int mApn;
    protected int mConnectTimeout = 20000;
    protected boolean mCookieEnable = true;
    public boolean mIsNeedBackWriteCookie = false;
    protected boolean mIsRemoveHeader = false;
    protected int mReadTimeout = 30000;
    public boolean mRequestFromWWW = false;
    protected String mUserAgent;

    public abstract void abort();

    public abstract void close();

    public abstract MttResponse execute(MttRequest mttRequest) throws Exception;

    public void setCookieEnable(boolean enable) {
        this.mCookieEnable = enable;
    }

    public String getUserAgent() {
        return this.mUserAgent;
    }

    public void setUserAgent(String userAgent) {
        this.mUserAgent = userAgent;
    }

    public boolean isRequestFromWWW() {
        return this.mRequestFromWWW;
    }

    public void setIsWWWRequest(boolean isWWW) {
        this.mRequestFromWWW = isWWW;
    }

    public int getApn() {
        return this.mApn;
    }

    public void setReadTimeout(int time) {
        this.mReadTimeout = time;
    }

    public void setConnectTimeout(int time) {
        this.mConnectTimeout = time;
    }

    public void setApn(int apn) {
        this.mApn = apn;
    }

    public MttResponse getResponse() {
        return null;
    }

    public void enableServiceRequest() {
    }

    public void setIsRemoveHeader(boolean is) {
        this.mIsRemoveHeader = is;
    }
}

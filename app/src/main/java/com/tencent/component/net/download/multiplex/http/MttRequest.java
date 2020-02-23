package com.tencent.component.net.download.multiplex.http;

import android.os.Build;
import android.text.TextUtils;
import com.tencent.component.net.download.multiplex.http.HttpHeader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.http.protocol.HTTP;

public class MttRequest {
    private static final String ACCEPT = "application/vnd.wap.xhtml+xml,application/xml,text/vnd.wap.wml,text/html,application/xhtml+xml,image/jpeg;q=0.5,image/png;q=0.5,image/gif;q=0.5,image/*;q=0.6,video/*,audio/*,*/*;q=0.6";
    private static final String ACCEPT_ENCODING = "gzip";
    public static final String CONNECTION = "Close";
    public static final String METHOD_GET_NAME = "GET";
    public static final String METHOD_POST_NAME = "POST";
    public static final byte REQUEST_BROKER = 103;
    public static final byte REQUEST_DIRECT = 102;
    public static final byte REQUEST_FILE_DOWNLOAD = 104;
    public static final byte REQUEST_NORMAL = 101;
    private static String USER_AGENT = null;
    private boolean mCheckCache = false;
    private int mFlow = 0;
    public boolean mForceNoReferer = false;
    private HashMap<String, String> mHeaderMap = new HashMap<>();
    private byte mMethod;
    private boolean mNeedCache = true;
    private boolean mNeedRefresh = false;
    public byte mNetworkStatus;
    private String mReferer;
    private byte mRequestType = REQUEST_NORMAL;
    private String mUrl;
    private String mUserAgent;

    public MttRequest() {
        initUserAgent();
        addHeader("Accept", ACCEPT);
        addHeader("Accept-Encoding", "gzip");
        if (USER_AGENT != null) {
            addHeader("User-Agent", USER_AGENT);
        } else {
            System.err.println("user_agent is null!");
        }
    }

    private void initUserAgent() {
        if (USER_AGENT == null) {
            Locale locale = Locale.getDefault();
            StringBuffer buffer = new StringBuffer();
            String version = Build.VERSION.RELEASE;
            if (version.length() > 0) {
                buffer.append(version);
            } else {
                buffer.append("1.0");
            }
            buffer.append("; ");
            String language = locale.getLanguage();
            if (language != null) {
                buffer.append(language.toLowerCase());
                String country = locale.getCountry();
                if (country != null) {
                    buffer.append("-");
                    buffer.append(country.toLowerCase());
                }
            } else {
                buffer.append("en");
            }
            if (Integer.parseInt(Build.VERSION.SDK) > 3) {
                String model = Build.MODEL;
                if (model.length() > 0) {
                    buffer.append("; ");
                    buffer.append(model);
                }
            }
            String id = Build.ID;
            if (id.length() > 0) {
                buffer.append(" Build/");
                buffer.append(id);
            }
            USER_AGENT = String.format("Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/533.1 (KHTML, like Gecko) Mobile Safari/533.1", new Object[]{buffer});
        }
    }

    public String getUrl() {
        return this.mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public byte getRequestType() {
        return this.mRequestType;
    }

    public void setRequestType(byte requestType) {
        this.mRequestType = requestType;
    }

    public boolean isCheckCache() {
        return this.mCheckCache;
    }

    public void setCheckCache(boolean checkCache) {
        this.mCheckCache = checkCache;
    }

    public boolean isNeedRefresh() {
        return this.mNeedRefresh;
    }

    public void setNeedRefresh(boolean needRefresh) {
        this.mNeedRefresh = needRefresh;
    }

    public boolean isNeedCache() {
        return this.mNeedCache;
    }

    public void setNeedCache(boolean needCache) {
        this.mNeedCache = needCache;
    }

    public void setMethod(byte method) {
        this.mMethod = method;
    }

    public byte getMethod() {
        return this.mMethod;
    }

    public String getMethodName() {
        if (this.mMethod == 1) {
            return "POST";
        }
        return "GET";
    }

    public void addHeader(String name, String value) {
        this.mHeaderMap.put(name, value);
    }

    public String getHeader(String name) {
        return this.mHeaderMap.get(name);
    }

    public void removeHeader(String name) {
        this.mHeaderMap.remove(name);
    }

    public void replaceHeader(String name, String value) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(value)) {
            this.mHeaderMap.remove(name);
            this.mHeaderMap.put(name, value);
        }
    }

    public Map<String, String> getHeaders() {
        return this.mHeaderMap;
    }

    public boolean isBroker() {
        return this.mRequestType == 103;
    }

    public String getReferer() {
        return this.mReferer;
    }

    public void setReferer(String referer) {
        this.mReferer = referer;
    }

    public String key() {
        return this.mUrl + hashCode();
    }

    public int getFlow() {
        return this.mFlow;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Method : " + getMethodName() + "\n");
        sb.append("NUrl : " + getUrl() + "\n");
        sb.append("RequestType : " + getRequestType() + "\n");
        return sb.toString();
    }

    public String getUserAgent() {
        if (this.mUserAgent == null) {
            return USER_AGENT;
        }
        return this.mUserAgent;
    }

    public void setUserAgent(String ua) {
        this.mUserAgent = ua;
    }

    public static String getDefaultUserAgent() {
        return USER_AGENT;
    }

    public void addHeaders(boolean mIsRemoveHeader, boolean mCookieEnable, boolean Q_DEBUG, boolean isRequestFromWWW) {
        if (getRequestType() == 104) {
            addHeader("Accept-Encoding", HTTP.IDENTITY_CODING);
        }
        if (!TextUtils.isEmpty(getReferer()) && !mIsRemoveHeader) {
            addHeader(HttpHeader.REQ.REFERER, getReferer());
        } else if (this.mForceNoReferer) {
            addHeader(HttpHeader.REQ.REFERER, this.mUrl.toString());
        }
    }
}

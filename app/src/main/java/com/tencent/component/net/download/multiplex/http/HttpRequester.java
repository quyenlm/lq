package com.tencent.component.net.download.multiplex.http;

import com.tencent.component.net.download.multiplex.DownloaderLog;
import com.tencent.component.net.download.multiplex.download.extension.UrlUtility;
import com.tencent.component.net.download.multiplex.http.Apn;
import com.tencent.component.net.download.multiplex.http.HttpHeader;
import com.tencent.imsdk.tool.net.AsyncHttpClient;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HttpsURLConnection;
import org.apache.http.conn.ssl.SSLSocketFactory;

public class HttpRequester extends Requester {
    public static boolean Q_DEBUG = true;
    private static final String TAG = "HttpRequester";
    private HttpURLConnection mHttpConnection;
    private MttInputStream mInputStream;
    private MttRequest mMttRequest;
    private MttResponse mMttResponse;
    private OutputStream mOutputStream;
    private URL mUrl;

    HttpRequester() {
        this.mCookieEnable = true;
    }

    public void enableServiceRequest() {
        this.mCookieEnable = false;
        Q_DEBUG = false;
    }

    public MttResponse execute(MttRequest mttRequest) throws Exception {
        InputStream inputStream;
        InputStream inputStream2;
        String host;
        String path;
        if (mttRequest == null) {
            return null;
        }
        mttRequest.mNetworkStatus = 1;
        this.mMttRequest = mttRequest;
        setApn(Apn.getApnTypeS());
        String strTargetUrl = this.mMttRequest.getUrl();
        this.mUrl = UrlUtility.toURL(strTargetUrl);
        if (UrlUtility.isHttpsUrl(strTargetUrl)) {
            HttpsURLConnection.setDefaultHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
        }
        DownloaderLog.d(TAG, "[HttpRequester] APN_TYPE : " + Apn.getApnType());
        Apn.ApnProxyInfo info = Apn.getApnProxyInfo();
        if (info.apnUseProxy) {
            String strTargetUrl2 = this.mUrl.toString();
            int hostIndex = strTargetUrl2.indexOf("://") + 3;
            int pathIndex = strTargetUrl2.indexOf(47, hostIndex);
            if (pathIndex < 0) {
                host = strTargetUrl2.substring(hostIndex);
                path = "";
            } else {
                host = strTargetUrl2.substring(hostIndex, pathIndex);
                path = strTargetUrl2.substring(pathIndex);
            }
            if (info.apnProxyType == 1) {
                DownloaderLog.d(TAG, "[HttpRequester] PROXY_TYPE : CT");
                this.mHttpConnection = (HttpURLConnection) this.mUrl.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(info.apnProxy, 80)));
            } else {
                this.mHttpConnection = (HttpURLConnection) new URL("http://" + info.apnProxy + path).openConnection();
                this.mHttpConnection.setRequestProperty("X-Online-Host", host);
            }
        } else {
            this.mHttpConnection = (HttpURLConnection) this.mUrl.openConnection();
        }
        this.mHttpConnection.setRequestMethod(this.mMttRequest.getMethodName());
        this.mHttpConnection.setInstanceFollowRedirects(false);
        this.mHttpConnection.setConnectTimeout(this.mConnectTimeout);
        this.mHttpConnection.setReadTimeout(this.mReadTimeout);
        if (this.mMttRequest.getMethodName().equalsIgnoreCase("POST")) {
            this.mHttpConnection.setDoOutput(true);
        }
        this.mHttpConnection.setDoInput(true);
        if (UrlUtility.isAlipaySite(this.mUrl.getHost())) {
            DownloaderLog.d("UA", "mtt UA=" + MttRequest.getDefaultUserAgent() + "/(alipay)");
            if (MttRequest.getDefaultUserAgent() != null) {
                mttRequest.addHeader("User-Agent", MttRequest.getDefaultUserAgent() + "/(alipay/un)");
            }
        } else if (mttRequest.getUserAgent() != null && !this.mIsRemoveHeader) {
            mttRequest.addHeader("User-Agent", mttRequest.getUserAgent());
        }
        if (mttRequest.getRequestType() == 104) {
        }
        mttRequest.mNetworkStatus = 2;
        fillHeader(mttRequest);
        try {
            fillPostBody(mttRequest);
            mttRequest.mNetworkStatus = 3;
            this.mMttResponse = new MttResponse();
            parseHeaders(this.mHttpConnection, this.mMttResponse);
            try {
                inputStream = this.mHttpConnection.getInputStream();
            } catch (IOException ie) {
                DownloaderLog.e(TAG, "[HttpRequester] open url stream error", ie);
                try {
                    inputStream = this.mHttpConnection.getErrorStream();
                } catch (Exception e) {
                    e.printStackTrace();
                    inputStream = null;
                }
            }
            if (inputStream != null) {
                if (mttRequest.getRequestType() != 104) {
                    String contentEncoding = this.mHttpConnection.getContentEncoding();
                    if (contentEncoding != null && contentEncoding.toLowerCase().indexOf(AsyncHttpClient.ENCODING_GZIP) != -1) {
                        try {
                            inputStream2 = new GZIPInputStream(inputStream);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            inputStream2 = inputStream;
                        }
                        this.mInputStream = new MttInputStream(inputStream2);
                        this.mMttResponse.setInputStream(this.mInputStream);
                    } else if (!(contentEncoding == null || contentEncoding.toLowerCase().indexOf("deflate") == -1)) {
                        inputStream2 = new GZIPInputStream(inputStream, 0);
                        this.mInputStream = new MttInputStream(inputStream2);
                        this.mMttResponse.setInputStream(this.mInputStream);
                    }
                }
                inputStream2 = inputStream;
                this.mInputStream = new MttInputStream(inputStream2);
                this.mMttResponse.setInputStream(this.mInputStream);
            }
            mttRequest.mNetworkStatus = 4;
            return this.mMttResponse;
        } catch (OutOfMemoryError e3) {
            throw e3;
        }
    }

    private void fillPostBody(MttRequest mttRequest) throws IOException, InterruptedException {
    }

    private void fillHeader(MttRequest mttRequest) {
        this.mMttRequest.addHeaders(this.mIsRemoveHeader, this.mCookieEnable, Q_DEBUG, isRequestFromWWW());
        for (Map.Entry<String, String> entry : mttRequest.getHeaders().entrySet()) {
            this.mHttpConnection.setRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    public MttResponse getResponse() {
        return this.mMttResponse;
    }

    private void parseHeaders(HttpURLConnection httpConn, MttResponse mttResponse) throws Exception {
        String strSemiPre;
        int equalIdx;
        if (httpConn != null) {
            mttResponse.setFlow(httpConn.getHeaderFields());
            mttResponse.setStatusCode(Integer.valueOf(httpConn.getResponseCode()));
            mttResponse.setLocation(httpConn.getHeaderField("Location"));
            mttResponse.setServer(httpConn.getHeaderField("Server"));
            mttResponse.setContentLength(getLongValue(httpConn.getHeaderField("Content-Length")));
            mttResponse.setContentEncoding(httpConn.getHeaderField("Content-Encoding"));
            mttResponse.setCharset(httpConn.getHeaderField(HttpHeader.RSP.CHARSET));
            mttResponse.setTransferEncoding(httpConn.getHeaderField("Transfer-Encoding"));
            mttResponse.setLastModify(httpConn.getHeaderField("Last-Modified"));
            mttResponse.setByteRanges(httpConn.getHeaderField(HttpHeader.RSP.BYTE_RNAGES));
            mttResponse.setCacheControl(httpConn.getHeaderField("Cache-Control"));
            mttResponse.setConnection(httpConn.getHeaderField("Connection"));
            mttResponse.setContentRange(httpConn.getHeaderField("Content-Range"));
            mttResponse.setContentDisposition(httpConn.getHeaderField("Content-Disposition"));
            mttResponse.setETag(httpConn.getHeaderField("etag"));
            mttResponse.setQNeed(httpConn.getHeaderField(HttpHeader.RSP.QNeed));
            mttResponse.setQNkey(httpConn.getHeaderField(HttpHeader.RSP.QNkey));
            mttResponse.setQTip(httpConn.getHeaderField(HttpHeader.RSP.QTip));
            mttResponse.setQSZip(httpConn.getHeaderField("QQ-S-ZIP"));
            mttResponse.setQEncrypt(httpConn.getHeaderField("QQ-S-Encrypt"));
            ContentType contentType = new ContentType(ContentType.TYPE_TEXT, ContentType.SUBTYPE_HTML, (String) null);
            String strContentType = httpConn.getHeaderField("Content-Type");
            if (strContentType == null) {
            }
            if (strContentType != null) {
                String strContentType2 = strContentType.trim();
                if (!"".equals(strContentType2)) {
                    int semiIdx = strContentType2.indexOf(59);
                    String strSemiAfter = null;
                    if (semiIdx != -1) {
                        strSemiPre = strContentType2.substring(0, semiIdx);
                        strSemiAfter = strContentType2.substring(semiIdx + 1);
                    } else {
                        strSemiPre = strContentType2;
                    }
                    if (strSemiPre != null) {
                        int slashIdx = strSemiPre.indexOf(47);
                        if (slashIdx != -1) {
                            contentType.setType(strSemiPre.substring(0, slashIdx));
                            contentType.setTypeValue(strSemiPre.substring(slashIdx + 1));
                        } else {
                            contentType.setType(strSemiPre);
                        }
                    }
                    if (!(strSemiAfter == null || (equalIdx = strSemiAfter.indexOf(61)) == -1)) {
                        contentType.setEncoding(strSemiAfter.substring(equalIdx + 1));
                    }
                }
            }
            mttResponse.setContentType(contentType);
        }
    }

    public void parseCookie(HttpURLConnection httpConn) {
    }

    private Integer getIntValue(String value) {
        if (value != null) {
            try {
                return Integer.valueOf(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private long getLongValue(String value) {
        if (value != null) {
            return Long.parseLong(value);
        }
        return 0;
    }

    public void close() {
        if (this.mInputStream != null) {
            try {
                this.mInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.mOutputStream != null) {
            try {
                this.mOutputStream.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (this.mHttpConnection != null) {
            this.mHttpConnection.disconnect();
        }
    }

    public void abort() {
        close();
    }

    public boolean getCookieEnable() {
        return this.mCookieEnable;
    }

    public void setCookieEnable(boolean cookieEnable) {
        this.mCookieEnable = cookieEnable;
    }
}

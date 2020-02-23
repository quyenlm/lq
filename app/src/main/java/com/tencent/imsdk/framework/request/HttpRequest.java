package com.tencent.imsdk.framework.request;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;
import com.tencent.imsdk.api.IMSystem;
import com.tencent.imsdk.framework.config.ConfigManager;
import com.tencent.imsdk.tool.encrypt.DesUtils;
import com.tencent.imsdk.tool.encrypt.TEACoding;
import com.tencent.imsdk.tool.etc.HexUtil;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.SafeJSONObject;
import com.tencent.imsdk.tool.etc.T;
import com.tencent.imsdk.tool.net.AsyncHttpClient;
import com.tencent.imsdk.tool.net.AsyncHttpResponseHandler;
import com.tencent.imsdk.tool.net.ResponseHandlerInterface;
import com.tencent.midas.oversea.comm.APGlobalInfo;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

public abstract class HttpRequest {
    public static String HTTP_DOMAIN = "";
    public static String HTTP_PUSH_DOMAIN = "";
    public static final String HTTP_PUSH_RRQ_DOMAIN = "IMSDK_PUSH_SERVER";
    public static final String HTTP_REQ_DOMAIN = "MSDK_URL";
    public static final String HTTP_REQ_ENTITY_JOIN = "&";
    public static final String HTTP_REQ_ENTITY_MERGE = "=";
    public static String HTTP_WEBVIEW_DOMAIN = "";
    public static final String HTTP_WEBVIEW_RRQ_DOMAIN = "IMSDK_WEBVIEW_TICKET_SERVER";
    protected boolean isEncode = true;
    private AsyncHttpResponseHandler mAsyncHttpResponseHandler = new AsyncHttpResponseHandler() {
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            byte[] body;
            if (responseBody == null) {
                IMLogger.d("responseBody is null");
                HttpRequest.this.onRequestSuccess(statusCode, headers, (byte[]) null);
                return;
            }
            if (HttpRequest.this.isEncode) {
                body = HttpRequest.this.teaCode.decode(responseBody);
            } else {
                body = responseBody;
            }
            HttpRequest.this.onRequestSuccess(statusCode, headers, body);
        }

        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            byte[] body;
            if (responseBody == null) {
                IMLogger.d("responseBody is null");
                HttpRequest.this.onRequestFailure(statusCode, headers, (byte[]) null, (Throwable) null);
                return;
            }
            if (HttpRequest.this.isEncode) {
                body = HttpRequest.this.teaCode.decode(responseBody);
            } else {
                body = responseBody;
            }
            HttpRequest.this.onRequestFailure(statusCode, headers, body, error);
        }
    };
    /* access modifiers changed from: private */
    public TEACoding teaCode = new TEACoding(DesUtils.REQUEST_KEY);

    /* access modifiers changed from: protected */
    public abstract SafeJSONObject getBody();

    /* access modifiers changed from: protected */
    public abstract List<NameValuePair> getBodyForPost();

    /* access modifiers changed from: protected */
    public abstract String getClientSecret();

    /* access modifiers changed from: protected */
    public abstract String getReqValue(String str);

    /* access modifiers changed from: protected */
    public abstract String getUrl();

    /* access modifiers changed from: protected */
    public abstract void onRequestFailure(int i, Header[] headerArr, byte[] bArr, Throwable th);

    /* access modifiers changed from: protected */
    public abstract void onRequestSuccess(int i, Header[] headerArr, byte[] bArr);

    /* access modifiers changed from: protected */
    public String getContentType() {
        return "application/x-www-form-urlencoded";
    }

    /* access modifiers changed from: protected */
    public String getDomain() {
        if (!T.ckIsEmpty(HTTP_DOMAIN)) {
            return HTTP_DOMAIN;
        }
        HTTP_DOMAIN = ConfigManager.readValueByKey(IMSystem.getInstance().getApplicationContext(), HTTP_REQ_DOMAIN);
        if (!T.ckIsEmpty(HTTP_DOMAIN)) {
            if (HTTP_DOMAIN.contains(APGlobalInfo.TestEnv) || HTTP_DOMAIN.contains(APGlobalInfo.DevEnv)) {
                Toast.makeText(IMSystem.getInstance().getActivity(), "You are using: " + HTTP_DOMAIN, 1).show();
                IMLogger.e("You are using: " + HTTP_DOMAIN);
            }
            return HTTP_DOMAIN;
        }
        IMLogger.e("Domain is not found in config file");
        return "";
    }

    /* access modifiers changed from: protected */
    public String getPushDomain() {
        if (!T.ckIsEmpty(HTTP_PUSH_DOMAIN)) {
            return HTTP_PUSH_DOMAIN;
        }
        HTTP_PUSH_DOMAIN = ConfigManager.readValueByKey(IMSystem.getInstance().getApplicationContext(), HTTP_PUSH_RRQ_DOMAIN);
        if (!T.ckIsEmpty(HTTP_PUSH_DOMAIN)) {
            if (HTTP_PUSH_DOMAIN.contains(APGlobalInfo.TestEnv) || HTTP_PUSH_DOMAIN.contains(APGlobalInfo.DevEnv)) {
                Toast.makeText(IMSystem.getInstance().getActivity(), "You are using: " + HTTP_PUSH_DOMAIN, 1).show();
                IMLogger.e("You are using: " + HTTP_PUSH_DOMAIN);
            }
            return HTTP_PUSH_DOMAIN;
        }
        IMLogger.e("Domain is not found in config file");
        return "";
    }

    /* access modifiers changed from: protected */
    public String getWebviewDomain() {
        if (!T.ckIsEmpty(HTTP_WEBVIEW_DOMAIN)) {
            return HTTP_WEBVIEW_DOMAIN;
        }
        HTTP_WEBVIEW_DOMAIN = ConfigManager.readValueByKey(IMSystem.getInstance().getApplicationContext(), HTTP_WEBVIEW_RRQ_DOMAIN);
        if (!T.ckIsEmpty(HTTP_WEBVIEW_DOMAIN)) {
            if (HTTP_WEBVIEW_DOMAIN.contains(APGlobalInfo.TestEnv) || HTTP_WEBVIEW_DOMAIN.contains(APGlobalInfo.DevEnv)) {
                Toast.makeText(IMSystem.getInstance().getActivity(), "You are using: " + HTTP_WEBVIEW_DOMAIN, 1).show();
                IMLogger.e("You are using: " + HTTP_WEBVIEW_DOMAIN);
            }
            return HTTP_WEBVIEW_DOMAIN;
        }
        IMLogger.e("Domain is not found in config file");
        return "";
    }

    /* access modifiers changed from: protected */
    public String getBaseUrl(int loginPlatform, String appId, String appKey) {
        String sig = "";
        try {
            String timestamp = "" + System.currentTimeMillis();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((appKey + timestamp).getBytes(Charset.forName("UTF-8")));
            String hexStr = HexUtil.bytes2HexStr(md.digest());
            if (hexStr != null) {
                sig = hexStr.toLowerCase(Locale.US);
            }
            return (((("" + "?appid=" + appId) + "&version=" + IMSystem.getInstance().getVersion()) + "&timestamp=" + timestamp) + "&sig=" + sig) + "&encode=1";
        } catch (NoSuchAlgorithmException e) {
            IMLogger.e(e.getMessage());
            return "";
        } catch (NumberFormatException e2) {
            IMLogger.e(e2.getMessage());
            return "";
        }
    }

    /* access modifiers changed from: protected */
    public SafeJSONObject getBaseBody() {
        return new SafeJSONObject();
    }

    private static String getUrlEncodeValue(String origValue) {
        if (origValue == null) {
            origValue = "";
        }
        try {
            return URLEncoder.encode(origValue, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return URLEncoder.encode(origValue);
        }
    }

    public final void execute(Context context, AsyncHttpClient client) {
        HttpEntity entity = null;
        try {
            SafeJSONObject jsonBody = getBody();
            List<NameValuePair> postList = getBodyForPost();
            int position = -1;
            for (int i = 0; i < postList.size(); i++) {
                NameValuePair pair = postList.get(i);
                if (postList.get(i).getName().equalsIgnoreCase("iChannel")) {
                    String tempChannel = pair.getValue();
                    if (!TextUtils.isEmpty(tempChannel) && (tempChannel.equalsIgnoreCase("601") || tempChannel.equalsIgnoreCase("602") || tempChannel.equalsIgnoreCase("603"))) {
                        position = i;
                    }
                }
            }
            if (position > -1) {
                postList.remove(position);
                postList.add(new BasicNameValuePair("iChannel", "6"));
            }
            if (jsonBody != null) {
                try {
                    byte[] body = jsonBody.toString().getBytes("UTF-8");
                    if (body != null) {
                        entity = this.isEncode ? new ByteArrayEntity(this.teaCode.encode(body)) : new ByteArrayEntity(body);
                    } else {
                        IMLogger.d("execute body is null");
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else if (postList != null) {
                entity = new UrlEncodedFormEntity(postList);
            }
            if (entity == null) {
                IMLogger.d("execute entity is null");
            }
            String url = getUrl();
            IMLogger.d("req url:" + url);
            client.post(context, url, new Header[]{new BasicHeader("Content-Type", "application/x-www-form-urlencoded"), new BasicHeader("Content-Encrypt", "msdktea"), new BasicHeader("Accept-Encrypt", "msdktea")}, entity, (String) null, (ResponseHandlerInterface) this.mAsyncHttpResponseHandler);
        } catch (Exception e2) {
            IMLogger.e(e2.getMessage());
            e2.printStackTrace();
        }
    }
}

package com.tencent.imsdk.sns.base;

import android.content.Context;
import android.net.Uri;
import com.tencent.imsdk.BuildConfig;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.IMErrorMsg;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.IMHandlerThread;
import com.tencent.imsdk.sns.innerapi.IMLoginStat;
import com.tencent.imsdk.stat.innerapi.InnerStat;
import com.tencent.imsdk.tool.etc.DeviceInfoUtils;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.InstallationInfoUtil;
import com.tencent.imsdk.tool.etc.MD5;
import com.tencent.imsdk.tool.net.AsyncHttpClient;
import com.tencent.imsdk.tool.net.AsyncHttpResponseHandler;
import com.tencent.imsdk.tool.net.RequestParams;
import com.tencent.imsdk.tool.net.ResponseHandlerInterface;
import com.tencent.imsdk.tool.net.SyncHttpClient;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.apache.http.Header;
import org.json.JSONObject;

public class IMHttpClient {
    private static final int ARGUMENT_ERROR = 11;
    private static final String HTTP_SCHEME = "http";
    private static final int NETWORK_ERROR = 4;
    private static String mLastValidString;
    private static InnerStat.Builder mSTBuilder;
    /* access modifiers changed from: private */
    public AsyncHttpClient asyncHttpClient = null;
    private SyncHttpClient syncHttpClient = null;
    private boolean syncMode = false;

    public static class RequestType {
        public static final int GET = 0;
        public static final int POST = 1;
    }

    public void initialize() {
        if (this.asyncHttpClient == null) {
            this.asyncHttpClient = new AsyncHttpClient(true, 80, 443);
            this.syncHttpClient = new SyncHttpClient(true, 80, 443);
        }
    }

    public AsyncHttpClient getAsyncHttpClient() {
        if (this.asyncHttpClient == null) {
            initialize();
        }
        return this.asyncHttpClient;
    }

    public SyncHttpClient getSyncHttpClient() {
        if (this.syncHttpClient == null) {
            initialize();
        }
        return this.syncHttpClient;
    }

    public boolean setSyncMode(boolean mode) {
        this.syncMode = mode;
        return this.syncMode;
    }

    public void get(String url, Map<String, String> params, IMCallback<String> callback, boolean needCheckParams) {
        request(url, params, 0, callback, needCheckParams);
    }

    public void post(String url, Map<String, String> params, IMCallback<String> callback, boolean needCheckParams) {
        request(url, params, 1, callback, needCheckParams);
    }

    public void get(String url, Map<String, String> params, IMCallback<String> callback) {
        request(url, params, 0, callback, true);
    }

    public void post(String url, Map<String, String> params, IMCallback<String> callback) {
        request(url, params, 1, callback, true);
    }

    public static String getLastValidString() {
        if (mLastValidString != null) {
            return mLastValidString;
        }
        return "";
    }

    /* access modifiers changed from: private */
    public AsyncHttpResponseHandler newAsyncHttpResponseHandler(final IMCallback<String> callback) {
        return new AsyncHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                IMLogger.d("get server result : " + new String(responseBody, Charset.forName("UTF-8")));
                if (statusCode == 200) {
                    String result = new String(responseBody, Charset.forName("UTF-8"));
                    try {
                        if (new IMLoginResult(result).retCode == 1 && new JSONObject(result).getInt("firstLoginTag") == 1) {
                            IMLoginStat.setFirstLogin(true);
                        }
                    } catch (Exception e) {
                        IMLogger.d("try to first login flag error : " + e.getMessage());
                    }
                    callback.onSuccess(new String(responseBody, Charset.forName("UTF-8")));
                    return;
                }
                IMException imException = new IMException(4, "code : " + statusCode + ", response : " + new String(responseBody, Charset.forName("UTF-8")));
                imException.imsdkRetCode = 4;
                imException.imsdkRetMsg = IMErrorMsg.getMessageByCode(imException.imsdkRetCode);
                imException.thirdRetCode = statusCode;
                imException.thirdRetMsg = new String(responseBody, Charset.forName("UTF-8"));
                callback.onError(imException);
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                IMLogger.d("get server result error message : " + error.getMessage());
                if (responseBody != null) {
                    IMLogger.d("get server result error result : " + new String(responseBody, Charset.forName("UTF-8")));
                }
                IMException imException = new IMException(4, error.getMessage());
                imException.imsdkRetCode = 4;
                imException.imsdkRetMsg = IMErrorMsg.getMessageByCode(imException.imsdkRetCode);
                imException.thirdRetCode = statusCode;
                if (responseBody != null) {
                    imException.thirdRetMsg = new String(responseBody, Charset.forName("UTF-8"));
                }
                callback.onError(imException);
            }
        };
    }

    private void request(String url, Map<String, String> params, int type, IMCallback<String> callback, boolean needCheckParams) {
        final IMCallback<String> proxyCallback;
        String errorString;
        Map<String, String> allParams = getAllParams(url, params);
        if (params == null || !needCheckParams || (errorString = checkParams(allParams)) == null) {
            if (allParams.containsKey("iChannel")) {
                String channel = allParams.get("iChannel");
                if (channel.equalsIgnoreCase("601") || channel.equalsIgnoreCase("602") || channel.equalsIgnoreCase("603")) {
                    IMLogger.d("zalo channel : " + channel + ", need fix to 6");
                    allParams.remove("iChannel");
                    allParams.put("iChannel", "6");
                }
            }
            if (!allParams.containsKey("iGameId")) {
                allParams.put("iGameId", String.valueOf(IMConfig.getGameId()));
            }
            if (!allParams.containsKey("iPlatform")) {
                allParams.put("iPlatform", IMConfig.getPlatform());
            }
            if (!allParams.containsKey("sRefer")) {
                allParams.put("sRefer", IMConfig.getInstallSource());
            }
            if (!allParams.containsKey("sValidKey")) {
                allParams.put("sValidKey", getMd5(allParams));
            }
            IMLogger.d("request url : " + url);
            initialize();
            RequestParams requestParams = new RequestParams();
            if (allParams != null) {
                for (Map.Entry<String, String> entry : allParams.entrySet()) {
                    requestParams.put(entry.getKey(), entry.getValue());
                }
            }
            if (mSTBuilder != null) {
                proxyCallback = mSTBuilder.create().proxyHttpListener4EventReport("http-" + getServerPath(url), callback);
            } else {
                mSTBuilder = new InnerStat.Builder(IMConfig.getCurContext(), BuildConfig.VERSION_NAME);
                proxyCallback = mSTBuilder.create().proxyHttpListener4EventReport("http-" + getServerPath(url), callback);
            }
            AsyncHttpResponseHandler asyncHttpResponseHandler = newAsyncHttpResponseHandler(proxyCallback);
            IMLogger.d("begin http request ... ");
            if (type == 0) {
                IMLogger.d("use GET method ...");
                if (this.syncMode) {
                    IMLogger.d("use sync mode, sending request ...");
                    this.syncHttpClient.get(url, requestParams, (ResponseHandlerInterface) asyncHttpResponseHandler);
                    return;
                }
                IMLogger.d("use async mode, try sending request ...");
                if (asyncHttpResponseHandler.getUseSynchronousMode()) {
                    IMLogger.d("current thread being forced to use sync mode, try fix it ... ");
                    final String finalUrl = url;
                    final RequestParams finalRequestParams = requestParams;
                    IMHandlerThread.getHandler().post(new Runnable() {
                        public void run() {
                            IMLogger.d("current thread do NOT have a looper, run in handler thread !");
                            AsyncHttpResponseHandler fixedAsyncHttpResponseHandler = IMHttpClient.this.newAsyncHttpResponseHandler(proxyCallback);
                            if (fixedAsyncHttpResponseHandler.getUseSynchronousMode()) {
                                IMLogger.e("handler thread can NOT fix looper problem, just send the request ...");
                            }
                            IMLogger.d("start sending request ... ");
                            IMHttpClient.this.asyncHttpClient.get(finalUrl, finalRequestParams, (ResponseHandlerInterface) fixedAsyncHttpResponseHandler);
                        }
                    });
                    IMLogger.d("request handled to handler thread ... ");
                    return;
                }
                IMLogger.d("current thread seems fine to call async mode, sending request ... ");
                this.asyncHttpClient.get(url, requestParams, (ResponseHandlerInterface) asyncHttpResponseHandler);
                return;
            }
            IMLogger.d("use POST method ...");
            if (this.syncMode) {
                IMLogger.d("use sync mode, sending request ...");
                this.syncHttpClient.post(url, requestParams, asyncHttpResponseHandler);
                return;
            }
            IMLogger.d("use async mode, try sending request ...");
            if (asyncHttpResponseHandler.getUseSynchronousMode()) {
                IMLogger.d("current thread being forced to use sync mode, try fix it ... ");
                final String finalUrl2 = url;
                final RequestParams finalRequestParams2 = requestParams;
                IMHandlerThread.getHandler().post(new Runnable() {
                    public void run() {
                        IMLogger.d("current thread do NOT have a looper, run in handler thread !");
                        AsyncHttpResponseHandler fixedAsyncHttpResponseHandler = IMHttpClient.this.newAsyncHttpResponseHandler(proxyCallback);
                        if (fixedAsyncHttpResponseHandler.getUseSynchronousMode()) {
                            IMLogger.e("handler thread can NOT fix looper problem, just send the request ...");
                        }
                        IMLogger.d("start sending request ... ");
                        IMHttpClient.this.asyncHttpClient.post(finalUrl2, finalRequestParams2, fixedAsyncHttpResponseHandler);
                    }
                });
                IMLogger.d("request handled to handler thread ... ");
                return;
            }
            IMLogger.d("current thread seems fine to call async mode, sending request ... ");
            this.asyncHttpClient.post(url, requestParams, asyncHttpResponseHandler);
            return;
        }
        IMException imException = new IMException(4, "need param : " + errorString);
        imException.imsdkRetCode = 11;
        imException.imsdkRetMsg = IMErrorMsg.getMessageByCode(imException.imsdkRetCode);
        imException.thirdRetMsg = "need param : " + errorString;
        callback.onError(imException);
    }

    private Set<String> getQueryParameterNames(Uri uri) {
        int end;
        if (uri.isOpaque()) {
            throw new UnsupportedOperationException("This isn't a hierarchical URI.");
        }
        String query = uri.getEncodedQuery();
        if (query == null) {
            return Collections.emptySet();
        }
        Set<String> names = new LinkedHashSet<>();
        int start = 0;
        do {
            int next = query.indexOf(38, start);
            if (next == -1) {
                end = query.length();
            } else {
                end = next;
            }
            int separator = query.indexOf(61, start);
            if (separator > end || separator == -1) {
                separator = end;
            }
            names.add(Uri.decode(query.substring(start, separator)));
            start = end + 1;
        } while (start < query.length());
        return Collections.unmodifiableSet(names);
    }

    private Map<String, String> getAllParams(String url, Map<String, String> params) {
        Uri uri = Uri.parse(url);
        if (params == null) {
            params = new HashMap<>();
        } else {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (entry.getValue() == null) {
                    entry.setValue("");
                }
            }
        }
        for (String key : getQueryParameterNames(uri)) {
            params.put(key, uri.getQueryParameter(key));
        }
        return params;
    }

    private String checkParams(Map<String, String> params) {
        if (!params.containsKey("iChannel")) {
            return "iChannel";
        }
        return null;
    }

    private String getMd5(Map<String, String> params) {
        if (!params.containsKey("sGuestId")) {
            try {
                Context context = IMConfig.getCurContext();
                if (context != null) {
                    params.put("sGuestId", DeviceInfoUtils.getGuestId(context));
                }
            } catch (Exception e) {
                IMLogger.w("get uuid failed : " + e.getMessage());
            }
        }
        if (!params.containsKey("did")) {
            try {
                Context context2 = IMConfig.getCurContext();
                if (context2 != null) {
                    String installationId = InstallationInfoUtil.getInstallationID(context2);
                    params.put("did", installationId != null ? installationId : "");
                }
            } catch (Exception e2) {
                IMLogger.w("get did failed : " + e2.getMessage());
            }
        }
        String md5Key = IMConfig.getMD5Key();
        if (!params.containsKey("sHttpVerifyCode") && md5Key != null) {
            params.put("sHttpVerifyCode", md5Key);
        }
        ArrayList<String> sorter = new ArrayList<>();
        for (String key : params.keySet()) {
            sorter.add(key);
        }
        Collections.sort(sorter);
        StringBuilder valueSB = new StringBuilder();
        Iterator<String> it = sorter.iterator();
        while (it.hasNext()) {
            valueSB.append(params.get(it.next()));
        }
        String md5String = MD5.getMD5(valueSB.toString() + md5Key);
        IMLogger.d("md5 key : " + md5Key + ", value string : " + valueSB.toString() + ", md5 string : " + md5String);
        mLastValidString = md5Key + "|" + valueSB.toString() + "|" + md5String;
        return md5String;
    }

    private String getServerPath(String url) {
        Uri uri;
        if (url == null || url.length() <= 0 || (uri = Uri.parse(url)) == null) {
            return "";
        }
        String path = uri.getPath();
        String version = IMConfig.getSdkServerVersion();
        if (path == null || !path.startsWith(version)) {
            return path;
        }
        return path.replaceFirst(version, "");
    }
}

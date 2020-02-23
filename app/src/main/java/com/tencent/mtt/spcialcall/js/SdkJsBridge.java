package com.tencent.mtt.spcialcall.js;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.mtt.spcialcall.ExtraInfo;
import com.tencent.mtt.spcialcall.WebViewSp;
import org.json.JSONException;
import org.json.JSONObject;

public class SdkJsBridge {
    private static final String FALSE = "false";
    private static final String TAG = "SdkJsBridge";
    private static final String TRUE = "true";
    private static final String UNDEFINED = "undefined";
    private static String[] sDefaultWhiteLists = {"*.qq.com", "*.soso.com", "*.qzone.com", "*.myapp.com", "*.wenwen.com"};
    private String[] mCustomWhiteLists;
    private WebViewSp mWebView;

    public SdkJsBridge(WebViewSp webview) {
        this.mWebView = webview;
        String pattern = ExtraInfo.getWhiteListPattern();
        if (!TextUtils.isEmpty(pattern)) {
            this.mCustomWhiteLists = new String[]{pattern};
        }
    }

    public String nativeExec(String serviceName, String action, String callbackId, String args) {
        JSONObject argsJson = null;
        if (!TextUtils.isEmpty(args)) {
            try {
                argsJson = new JSONObject(args);
            } catch (JSONException e) {
                Log.w(TAG, e);
                return UNDEFINED;
            }
        }
        if (!"qb_sdk".equalsIgnoreCase(serviceName) || !"isApkInstalled".equals(action)) {
            return null;
        }
        return isApkInstalled(argsJson);
    }

    private String isApkInstalled(JSONObject argsJson) {
        if (argsJson == null) {
            return UNDEFINED;
        }
        String pkg = argsJson.optString("packagename");
        if (TextUtils.isEmpty(pkg)) {
            return UNDEFINED;
        }
        if (!checkDomain()) {
            return "false";
        }
        try {
            return this.mWebView.getContext().getPackageManager().getApplicationInfo(pkg, 0) == null ? "false" : "true";
        } catch (Exception e) {
            Log.w(TAG, e);
            return "false";
        }
    }

    private boolean checkDomain() {
        String url = this.mWebView.getUrl();
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        String host = Uri.parse(url).getHost();
        for (String pattern : sDefaultWhiteLists) {
            if (isDomainMatch(pattern, host)) {
                return true;
            }
        }
        if (this.mCustomWhiteLists == null) {
            return false;
        }
        for (String pattern2 : this.mCustomWhiteLists) {
            if (isDomainMatch(pattern2, host)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDomainMatch(String pattern, String domain) {
        if (TextUtils.isEmpty(pattern) || TextUtils.isEmpty(domain)) {
            return false;
        }
        return domain.matches("^" + pattern.replaceAll("\\.", "\\\\.").replaceAll("\\*", ".+") + "$");
    }
}

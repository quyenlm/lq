package com.garena.pay.android.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.StringUtils;
import com.garena.pay.android.helper.UILoop;
import org.json.JSONException;
import org.json.JSONObject;

public class WebViewJSInterface {
    private static final String BRIDGE_RESULT_BACK_TEMPLATE = "javascript: window.__native_bridge.onReceive('%1$s', '%2$s');";
    public static final String KEY_NAME = "__native_bridge";
    /* access modifiers changed from: private */
    @Nullable
    public CmdHandler mHandler;
    /* access modifiers changed from: private */
    public WebView mWebView;

    public interface CmdHandler {
        boolean handleCmd(WebViewJSInterface webViewJSInterface, String str, JSONObject jSONObject, String str2);

        boolean isCmdHandlable(String str);
    }

    @SuppressLint({"AddJavascriptInterface"})
    public WebViewJSInterface(WebView webView, @Nullable CmdHandler handler) {
        this.mWebView = webView;
        this.mWebView.addJavascriptInterface(this, KEY_NAME);
        this.mHandler = handler;
    }

    public void unregisterHandler() {
        this.mHandler = null;
    }

    @JavascriptInterface
    public boolean hasHandler(String cmd) {
        BBLogger.i("has handler: %1$s", cmd);
        if (this.mHandler == null || !this.mHandler.isCmdHandlable(cmd)) {
            return false;
        }
        return true;
    }

    @JavascriptInterface
    public void send(final String cmd, String data, final String callback) {
        BBLogger.i("received cmd: %1$s, data: %2$s, callback: %3$s", cmd, data, callback);
        if (this.mHandler != null && this.mHandler.isCmdHandlable(cmd)) {
            try {
                final JSONObject dataObj = new JSONObject(data);
                UILoop.getInstance().post(new Runnable() {
                    public void run() {
                        if (WebViewJSInterface.this.mHandler == null || WebViewJSInterface.this.mHandler.handleCmd(WebViewJSInterface.this, cmd, dataObj, callback)) {
                        }
                    }
                });
            } catch (JSONException e) {
                BBLogger.e(e);
            }
        }
    }

    public void sendResultBack(final String callback, final GGBaseWebResult result) {
        Handler webViewHandler = this.mWebView.getHandler();
        if (webViewHandler != null) {
            webViewHandler.post(new Runnable() {
                public void run() {
                    String js = String.format(WebViewJSInterface.BRIDGE_RESULT_BACK_TEMPLATE, new Object[]{callback, result.toDataString()});
                    BBLogger.i("send result back: %1$s", js);
                    WebViewJSInterface.this.mWebView.loadUrl(js);
                }
            });
        }
    }

    @JavascriptInterface
    public void sendSms(String phoneNum, String text) {
        BBLogger.i("local sendSms function called", new Object[0]);
        if (!StringUtils.isEmpty(phoneNum) && !StringUtils.isEmpty(text)) {
            Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + phoneNum));
            intent.putExtra("sms_body", text);
            this.mWebView.getContext().startActivity(intent);
        }
    }

    @JavascriptInterface
    public void sendSmsInApp(String phoneNum, String text) {
        sendSms(phoneNum, text);
    }
}

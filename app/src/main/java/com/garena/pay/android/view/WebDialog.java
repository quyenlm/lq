package com.garena.pay.android.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.networking.GarenaUserAgent;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.garena.msdk.R;
import com.garena.pay.android.GGErrorCode;
import com.garena.pay.android.exception.ErrorException;
import com.garena.pay.android.helper.Utils;
import com.google.android.gms.drive.DriveFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WebDialog extends Dialog {
    static final /* synthetic */ boolean $assertionsDisabled = (!WebDialog.class.desiredAssertionStatus());
    private static final int BACKGROUND_GRAY = -872415232;
    private static final String CANCEL_URI = "pay/channel/cancel";
    private static final int MAX_PADDING_SCREEN_HEIGHT = 1920;
    private static final int MAX_PADDING_SCREEN_WIDTH = 1080;
    private static final double MIN_SCALE_FACTOR = 0.5d;
    private static final int NO_PADDING_SCREEN_HEIGHT = 800;
    private static final int NO_PADDING_SCREEN_WIDTH = 480;
    private static final String REDIRECT_URI = "pay/result?";
    protected ViewGroup contentLayout;
    /* access modifiers changed from: private */
    public boolean isDetached;
    private boolean listenerCalled;
    /* access modifiers changed from: private */
    public Context mContext;
    protected boolean needMargin = true;
    private OnCompleteListener onCompleteListener;
    /* access modifiers changed from: private */
    public Dialog spinner;
    private String url;
    /* access modifiers changed from: protected */
    public WebView webView;
    private List<String> whiteListedDomains = new ArrayList<String>() {
        {
            add("garena.com");
            add("garenanow.com");
            add("molsolutions.com");
        }
    };

    public interface OnCompleteListener {
        void onComplete(Bundle bundle, ErrorException errorException);
    }

    public WebDialog(Context context) {
        super(context);
        init(context);
    }

    public WebDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    public WebDialog(Context context, String url2, int theme) {
        super(context, theme);
        this.url = url2;
        init(context);
    }

    protected WebDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.webView = new WebView(context);
    }

    public void setOnCompleteListener(OnCompleteListener onCompleteListener2) {
        this.onCompleteListener = onCompleteListener2;
    }

    /* access modifiers changed from: private */
    public void sendSuccessToListener(Bundle values) {
        if (this.onCompleteListener != null && !this.listenerCalled) {
            this.listenerCalled = true;
            this.onCompleteListener.onComplete(values, (ErrorException) null);
        }
    }

    public WebView getWebView() {
        return this.webView;
    }

    public void onDetachedFromWindow() {
        this.isDetached = true;
        super.onDetachedFromWindow();
    }

    public void onAttachedToWindow() {
        this.isDetached = false;
        super.onAttachedToWindow();
    }

    public void dismiss() {
        try {
            if (this.webView != null) {
                this.webView.destroy();
                this.contentLayout.removeAllViews();
            }
        } catch (Exception e) {
            BBLogger.e(e);
        }
        if (!this.isDetached) {
            if (this.spinner.isShowing()) {
                this.spinner.dismiss();
            }
            super.dismiss();
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        int i = 0;
        requestWindowFeature(1);
        super.onCreate(savedInstanceState);
        setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                WebDialog.this.sendCancelToListener();
            }
        });
        this.spinner = new Dialog(getContext(), R.style.ProgressDialogTheme);
        this.spinner.setContentView(R.layout.msdk_progress_dialog);
        this.spinner.setCanceledOnTouchOutside(false);
        this.spinner.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialogInterface) {
                WebDialog.this.sendCancelToListener();
                WebDialog.this.dismiss();
            }
        });
        initContentLayout();
        calculateSize();
        getWindow().setGravity(17);
        getWindow().setSoftInputMode(16);
        if (this.needMargin) {
            i = 20;
        }
        setUpWebView(i);
        setContentView(this.contentLayout);
    }

    /* access modifiers changed from: protected */
    public void initContentLayout() {
        this.contentLayout = new FrameLayout(getContext());
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void setUpWebView(int margin) {
        LinearLayout webViewContainer = new LinearLayout(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
        webViewContainer.setLayoutParams(params);
        onConfigWebView(this.webView);
        this.webView.loadUrl(this.url);
        this.webView.setLayoutParams(params);
        this.webView.setVisibility(4);
        this.webView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                if (WebDialog.this.mContext != null) {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setFlags(DriveFile.MODE_READ_ONLY);
                    intent.setData(Uri.parse(url));
                    WebDialog.this.mContext.startActivity(intent);
                }
            }
        });
        webViewContainer.setPadding(margin, margin, margin, margin);
        webViewContainer.addView(this.webView);
        this.contentLayout.setBackgroundColor(BACKGROUND_GRAY);
        this.contentLayout.addView(webViewContainer);
    }

    /* access modifiers changed from: protected */
    public void onConfigWebView(WebView webView2) {
        webView2.setVerticalScrollBarEnabled(false);
        webView2.setHorizontalScrollBarEnabled(false);
        webView2.setWebViewClient(createWebViewClient());
        WebSettings webSettings = webView2.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView2, true);
        }
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (!SDKConstants.RELEASE_VERSION) {
            if (Build.VERSION.SDK_INT >= 19) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
            webView2.clearCache(true);
        }
        webSettings.setSavePassword(false);
        webSettings.setUserAgentString(webSettings.getUserAgentString() + ";" + new GarenaUserAgent().toString());
    }

    /* access modifiers changed from: protected */
    public WebViewClient createWebViewClient() {
        return new DialogWebViewClient();
    }

    /* access modifiers changed from: protected */
    public void calculateSize() {
        Display display = ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        getWindow().setLayout(Math.min(getScaledSize(metrics.widthPixels < metrics.heightPixels ? metrics.widthPixels : metrics.heightPixels, metrics.density, NO_PADDING_SCREEN_WIDTH, MAX_PADDING_SCREEN_WIDTH), metrics.widthPixels), Math.min(getScaledSize(metrics.widthPixels < metrics.heightPixels ? metrics.heightPixels : metrics.widthPixels, metrics.density, 800, MAX_PADDING_SCREEN_HEIGHT), metrics.heightPixels));
    }

    private int getScaledSize(int screenSize, float density, int noPaddingSize, int maxPaddingSize) {
        double scaleFactor;
        int scaledSize = (int) (((float) screenSize) / density);
        if (scaledSize <= noPaddingSize) {
            scaleFactor = 1.0d;
        } else if (scaledSize >= maxPaddingSize) {
            scaleFactor = MIN_SCALE_FACTOR;
        } else {
            scaleFactor = MIN_SCALE_FACTOR + ((((double) (maxPaddingSize - scaledSize)) / ((double) (maxPaddingSize - noPaddingSize))) * MIN_SCALE_FACTOR);
        }
        return (int) (((double) screenSize) * scaleFactor);
    }

    /* access modifiers changed from: private */
    public void sendCancelToListener() {
        sendErrorToListener(new ErrorException(GGErrorCode.PAYMENT_USER_CANCELLED.getStringValue(), GGErrorCode.PAYMENT_USER_CANCELLED.getCode()));
    }

    /* access modifiers changed from: protected */
    public void sendErrorToListener(ErrorException error) {
        Bundle val = new Bundle();
        if ($assertionsDisabled || error != null) {
            val.putInt(SDKConstants.WEB_PAY.EXTRA_ERROR_CODE, error.getErrorCode().intValue());
            val.putString("error", error.getMessage());
            if (this.onCompleteListener != null && !this.listenerCalled) {
                this.listenerCalled = true;
                this.onCompleteListener.onComplete(val, error);
                return;
            }
            return;
        }
        throw new AssertionError();
    }

    public void setUrl(String url2) {
        this.url = url2;
    }

    public WebSettings getWebViewSettings() {
        if (this.webView != null) {
            return this.webView.getSettings();
        }
        return null;
    }

    /* access modifiers changed from: private */
    public boolean isWhitelisted(String url2) {
        return true;
    }

    /* access modifiers changed from: protected */
    public ErrorException parseServerErrors(String error) {
        GGErrorCode finalErrorCode = GGErrorCode.UNKNOWN_ERROR;
        if (error != null) {
            if (error.equals("error_params")) {
                finalErrorCode = GGErrorCode.ERROR_IN_PARAMS;
            } else if (error.equals(SDKConstants.SERVER_ERRORS.ERROR_SCOPE)) {
                finalErrorCode = GGErrorCode.GOP_ERROR_SCOPE;
            } else if (error.equals(SDKConstants.SERVER_ERRORS.ERROR_TOKEN)) {
                finalErrorCode = GGErrorCode.GOP_ERROR_TOKEN;
            } else if (error.equals("server_error")) {
                finalErrorCode = GGErrorCode.PAYMENT_INVALID_SERVER_RESPONSE;
            }
        }
        return new ErrorException(finalErrorCode.getStringValue(), finalErrorCode.getCode());
    }

    protected class DialogWebViewClient extends WebViewClient {
        protected DialogWebViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            BBLogger.d("Redirect URL: %s", url);
            if (url.endsWith(WebDialog.REDIRECT_URI)) {
                parseAndNotifyClient(url);
                WebDialog.this.dismiss();
                return true;
            } else if (url.endsWith(WebDialog.CANCEL_URI)) {
                WebDialog.this.sendCancelToListener();
                WebDialog.this.dismiss();
                return true;
            } else if (url.startsWith(com.tencent.smtt.sdk.WebView.SCHEME_TEL) || url.startsWith("sms:") || url.startsWith(com.tencent.smtt.sdk.WebView.SCHEME_MAILTO) || url.startsWith(com.tencent.smtt.sdk.WebView.SCHEME_GEO) || url.startsWith("maps:")) {
                WebDialog.this.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                return true;
            } else if (WebDialog.this.isWhitelisted(url)) {
                return false;
            } else {
                WebDialog.this.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                return true;
            }
        }

        private void parseAndNotifyClient(String url) {
            Map<String, String> queryMap = Utils.parseQueryArgs(url);
            if (!queryMap.containsKey(GGLiveConstants.PARAM.RESULT) || !queryMap.get(GGLiveConstants.PARAM.RESULT).equals("0")) {
                WebDialog.this.sendErrorToListener(WebDialog.this.parseServerErrors(queryMap.get("error")));
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString(SDKConstants.WEB_PAY.EXTRA_TXN_ID, queryMap.get(SDKConstants.WEB_PAY.EXTRA_TXN_ID));
            bundle.putString(SDKConstants.WEB_PAY.EXTRA_AMOUNT, queryMap.get(SDKConstants.WEB_PAY.EXTRA_AMOUNT));
            bundle.putString("item_name", queryMap.get("item_name"));
            bundle.putString(SDKConstants.WEB_PAY.EXTRA_ICON, queryMap.get(SDKConstants.WEB_PAY.EXTRA_ICON));
            bundle.putString(SDKConstants.WEB_PAY.EXTRA_REBATE_CARD_ID, queryMap.get(SDKConstants.WEB_PAY.EXTRA_REBATE_CARD_ID));
            bundle.putString(SDKConstants.WEB_PAY.EXTRA_REMAINING_DAYS, queryMap.get(SDKConstants.WEB_PAY.EXTRA_REMAINING_DAYS));
            WebDialog.this.sendSuccessToListener(bundle);
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            int realErrorCode = GGErrorCode.UNKNOWN_ERROR.getCode().intValue();
            switch (errorCode) {
                case -15:
                case -11:
                case -10:
                case -9:
                case -8:
                case -7:
                case -6:
                case -5:
                case -4:
                case -3:
                case -2:
                    realErrorCode = GGErrorCode.PAYMENT_NETWORK_CONNECTION_EXCEPTION.getCode().intValue();
                    break;
                case -14:
                case -12:
                    realErrorCode = GGErrorCode.PAYMENT_ERROR_IN_PARAMS.getCode().intValue();
                    break;
            }
            WebDialog.this.sendErrorToListener(new ErrorException(description, Integer.valueOf(realErrorCode)));
            WebDialog.this.dismiss();
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            BBLogger.d("Webview loading URL: %s", url);
            super.onPageStarted(view, url, favicon);
            if (!WebDialog.this.isDetached) {
                WebDialog.this.spinner.show();
            }
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (!WebDialog.this.isDetached) {
                WebDialog.this.spinner.dismiss();
            }
            if (url.contains(WebDialog.REDIRECT_URI) && !url.contains("#")) {
                parseAndNotifyClient(url);
            }
            WebDialog.this.contentLayout.setBackgroundColor(0);
            WebDialog.this.webView.setVisibility(0);
        }
    }
}

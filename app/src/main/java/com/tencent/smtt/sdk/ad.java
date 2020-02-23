package com.tencent.smtt.sdk;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslCertificate;
import android.os.Build;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.WebResourceError;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.tencent.smtt.export.external.interfaces.ClientCertRequest;
import com.tencent.smtt.export.external.interfaces.HttpAuthHandler;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.utils.TbsLog;
import com.tencent.smtt.utils.s;
import com.tencent.smtt.utils.v;
import com.tencent.smtt.utils.y;
import java.io.InputStream;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Map;

@SuppressLint({"NewApi", "Override"})
class ad extends WebViewClient {
    private static String c = null;
    private WebViewClient a;
    /* access modifiers changed from: private */
    public WebView b;

    private static class a extends ClientCertRequest {
        private android.webkit.ClientCertRequest a;

        public a(android.webkit.ClientCertRequest clientCertRequest) {
            this.a = clientCertRequest;
        }

        public void cancel() {
            this.a.cancel();
        }

        public String getHost() {
            return this.a.getHost();
        }

        public String[] getKeyTypes() {
            return this.a.getKeyTypes();
        }

        public int getPort() {
            return this.a.getPort();
        }

        public Principal[] getPrincipals() {
            return this.a.getPrincipals();
        }

        public void ignore() {
            this.a.ignore();
        }

        public void proceed(PrivateKey privateKey, X509Certificate[] x509CertificateArr) {
            this.a.proceed(privateKey, x509CertificateArr);
        }
    }

    private static class b implements HttpAuthHandler {
        private android.webkit.HttpAuthHandler a;

        b(android.webkit.HttpAuthHandler httpAuthHandler) {
            this.a = httpAuthHandler;
        }

        public void cancel() {
            this.a.cancel();
        }

        public void proceed(String str, String str2) {
            this.a.proceed(str, str2);
        }

        public boolean useHttpAuthUsernamePassword() {
            return this.a.useHttpAuthUsernamePassword();
        }
    }

    private static class c implements SslErrorHandler {
        android.webkit.SslErrorHandler a;

        c(android.webkit.SslErrorHandler sslErrorHandler) {
            this.a = sslErrorHandler;
        }

        public void cancel() {
            this.a.cancel();
        }

        public void proceed() {
            this.a.proceed();
        }
    }

    private static class d implements SslError {
        android.net.http.SslError a;

        d(android.net.http.SslError sslError) {
            this.a = sslError;
        }

        public boolean addError(int i) {
            return this.a.addError(i);
        }

        public SslCertificate getCertificate() {
            return this.a.getCertificate();
        }

        public int getPrimaryError() {
            return this.a.getPrimaryError();
        }

        public boolean hasError(int i) {
            return this.a.hasError(i);
        }
    }

    private class e implements WebResourceRequest {
        private String b;
        private boolean c;
        private boolean d;
        private boolean e;
        private String f;
        private Map<String, String> g;

        public e(String str, boolean z, boolean z2, boolean z3, String str2, Map<String, String> map) {
            this.b = str;
            this.c = z;
            this.d = z2;
            this.e = z3;
            this.f = str2;
            this.g = map;
        }

        public String getMethod() {
            return this.f;
        }

        public Map<String, String> getRequestHeaders() {
            return this.g;
        }

        public Uri getUrl() {
            return Uri.parse(this.b);
        }

        public boolean hasGesture() {
            return this.e;
        }

        public boolean isForMainFrame() {
            return this.c;
        }

        public boolean isRedirect() {
            return this.d;
        }
    }

    private static class f implements WebResourceRequest {
        android.webkit.WebResourceRequest a;

        f(android.webkit.WebResourceRequest webResourceRequest) {
            this.a = webResourceRequest;
        }

        public String getMethod() {
            return this.a.getMethod();
        }

        public Map<String, String> getRequestHeaders() {
            return this.a.getRequestHeaders();
        }

        public Uri getUrl() {
            return this.a.getUrl();
        }

        public boolean hasGesture() {
            return this.a.hasGesture();
        }

        public boolean isForMainFrame() {
            return this.a.isForMainFrame();
        }

        public boolean isRedirect() {
            if (Build.VERSION.SDK_INT >= 24) {
                Object a2 = v.a((Object) this.a, "isRedirect");
                if (a2 instanceof Boolean) {
                    return ((Boolean) a2).booleanValue();
                }
            }
            return false;
        }
    }

    private static class g extends WebResourceResponse {
        android.webkit.WebResourceResponse a;

        public g(android.webkit.WebResourceResponse webResourceResponse) {
            this.a = webResourceResponse;
        }

        public InputStream getData() {
            return this.a.getData();
        }

        public String getEncoding() {
            return this.a.getEncoding();
        }

        public String getMimeType() {
            return this.a.getMimeType();
        }

        public String getReasonPhrase() {
            return this.a.getReasonPhrase();
        }

        public Map<String, String> getResponseHeaders() {
            return this.a.getResponseHeaders();
        }

        public int getStatusCode() {
            return this.a.getStatusCode();
        }

        public void setData(InputStream inputStream) {
            this.a.setData(inputStream);
        }

        public void setEncoding(String str) {
            this.a.setEncoding(str);
        }

        public void setMimeType(String str) {
            this.a.setMimeType(str);
        }

        public void setResponseHeaders(Map<String, String> map) {
            this.a.setResponseHeaders(map);
        }

        public void setStatusCodeAndReasonPhrase(int i, String str) {
            this.a.setStatusCodeAndReasonPhrase(i, str);
        }
    }

    ad(WebView webView, WebViewClient webViewClient) {
        this.b = webView;
        this.a = webViewClient;
    }

    public void doUpdateVisitedHistory(WebView webView, String str, boolean z) {
        this.b.a(webView);
        this.a.doUpdateVisitedHistory(this.b, str, z);
    }

    public void onFormResubmission(WebView webView, Message message, Message message2) {
        this.b.a(webView);
        this.a.onFormResubmission(this.b, message, message2);
    }

    public void onLoadResource(WebView webView, String str) {
        this.b.a(webView);
        this.a.onLoadResource(this.b, str);
    }

    public void onPageFinished(WebView webView, String str) {
        y a2;
        if (c == null && (a2 = y.a()) != null) {
            a2.a(true);
            c = Boolean.toString(true);
        }
        this.b.a(webView);
        this.b.a++;
        this.a.onPageFinished(this.b, str);
        if (TbsConfig.APP_QZONE.equals(webView.getContext().getApplicationInfo().packageName)) {
            this.b.a(webView.getContext());
        }
        TbsLog.app_extra("SystemWebViewClient", webView.getContext());
        WebView.c();
        if (!TbsShareManager.mHasQueryed && this.b.getContext() != null && TbsShareManager.isThirdPartyApp(this.b.getContext())) {
            TbsShareManager.mHasQueryed = true;
            new Thread(new ae(this)).start();
        }
        if (this.b.getContext() != null && !TbsLogReport.a(this.b.getContext()).e()) {
            TbsLogReport.a(this.b.getContext()).a(true);
            TbsLogReport.a(this.b.getContext()).b();
        }
    }

    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        this.b.a(webView);
        this.a.onPageStarted(this.b, str, bitmap);
    }

    public void onReceivedClientCertRequest(WebView webView, android.webkit.ClientCertRequest clientCertRequest) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.b.a(webView);
            this.a.onReceivedClientCertRequest(this.b, new a(clientCertRequest));
        }
    }

    public void onReceivedError(WebView webView, int i, String str, String str2) {
        this.b.a(webView);
        this.a.onReceivedError(this.b, i, str, str2);
    }

    public void onReceivedError(WebView webView, android.webkit.WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
        af afVar = null;
        this.b.a(webView);
        f fVar = webResourceRequest != null ? new f(webResourceRequest) : null;
        if (webResourceError != null) {
            afVar = new af(this, webResourceError);
        }
        this.a.onReceivedError(this.b, fVar, afVar);
    }

    public void onReceivedHttpAuthRequest(WebView webView, android.webkit.HttpAuthHandler httpAuthHandler, String str, String str2) {
        this.b.a(webView);
        this.a.onReceivedHttpAuthRequest(this.b, new b(httpAuthHandler), str, str2);
    }

    public void onReceivedHttpError(WebView webView, android.webkit.WebResourceRequest webResourceRequest, android.webkit.WebResourceResponse webResourceResponse) {
        this.b.a(webView);
        this.a.onReceivedHttpError(this.b, new f(webResourceRequest), new g(webResourceResponse));
    }

    @TargetApi(12)
    public void onReceivedLoginRequest(WebView webView, String str, String str2, String str3) {
        if (Build.VERSION.SDK_INT >= 12) {
            this.b.a(webView);
            this.a.onReceivedLoginRequest(this.b, str, str2, str3);
        }
    }

    @TargetApi(8)
    public void onReceivedSslError(WebView webView, android.webkit.SslErrorHandler sslErrorHandler, android.net.http.SslError sslError) {
        if (Build.VERSION.SDK_INT >= 8) {
            this.b.a(webView);
            this.a.onReceivedSslError(this.b, new c(sslErrorHandler), new d(sslError));
        }
    }

    public void onScaleChanged(WebView webView, float f2, float f3) {
        this.b.a(webView);
        this.a.onScaleChanged(this.b, f2, f3);
    }

    public void onTooManyRedirects(WebView webView, Message message, Message message2) {
        this.b.a(webView);
        this.a.onTooManyRedirects(this.b, message, message2);
    }

    public void onUnhandledKeyEvent(WebView webView, KeyEvent keyEvent) {
        this.b.a(webView);
        this.a.onUnhandledKeyEvent(this.b, keyEvent);
    }

    public android.webkit.WebResourceResponse shouldInterceptRequest(WebView webView, android.webkit.WebResourceRequest webResourceRequest) {
        if (Build.VERSION.SDK_INT < 21) {
            return null;
        }
        if (webResourceRequest == null) {
            return null;
        }
        boolean z = false;
        if (Build.VERSION.SDK_INT >= 24) {
            Object a2 = v.a((Object) webResourceRequest, "isRedirect");
            if (a2 instanceof Boolean) {
                z = ((Boolean) a2).booleanValue();
            }
        }
        WebResourceResponse shouldInterceptRequest = this.a.shouldInterceptRequest(this.b, (WebResourceRequest) new e(webResourceRequest.getUrl().toString(), webResourceRequest.isForMainFrame(), z, webResourceRequest.hasGesture(), webResourceRequest.getMethod(), webResourceRequest.getRequestHeaders()));
        if (shouldInterceptRequest == null) {
            return null;
        }
        android.webkit.WebResourceResponse webResourceResponse = new android.webkit.WebResourceResponse(shouldInterceptRequest.getMimeType(), shouldInterceptRequest.getEncoding(), shouldInterceptRequest.getData());
        webResourceResponse.setResponseHeaders(shouldInterceptRequest.getResponseHeaders());
        int statusCode = shouldInterceptRequest.getStatusCode();
        String reasonPhrase = shouldInterceptRequest.getReasonPhrase();
        if (statusCode == webResourceResponse.getStatusCode() && (reasonPhrase == null || reasonPhrase.equals(webResourceResponse.getReasonPhrase()))) {
            return webResourceResponse;
        }
        webResourceResponse.setStatusCodeAndReasonPhrase(statusCode, reasonPhrase);
        return webResourceResponse;
    }

    @TargetApi(11)
    public android.webkit.WebResourceResponse shouldInterceptRequest(WebView webView, String str) {
        WebResourceResponse shouldInterceptRequest;
        if (Build.VERSION.SDK_INT >= 11 && (shouldInterceptRequest = this.a.shouldInterceptRequest(this.b, str)) != null) {
            return new android.webkit.WebResourceResponse(shouldInterceptRequest.getMimeType(), shouldInterceptRequest.getEncoding(), shouldInterceptRequest.getData());
        }
        return null;
    }

    public boolean shouldOverrideKeyEvent(WebView webView, KeyEvent keyEvent) {
        this.b.a(webView);
        return this.a.shouldOverrideKeyEvent(this.b, keyEvent);
    }

    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        if (str == null || this.b.showDebugView(str)) {
            return true;
        }
        this.b.a(webView);
        if (!s.a().a(this.b.getContext().getApplicationContext(), str)) {
            return this.a.shouldOverrideUrlLoading(this.b, str);
        }
        return true;
    }
}

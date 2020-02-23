package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Message;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.View;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.ads.internal.overlay.zzm;
import com.google.android.gms.ads.internal.zzbs;
import com.tencent.tp.a.h;

@zzzn
@TargetApi(11)
public class zzakw extends WebChromeClient {
    private final zzaka zzJH;

    public zzakw(zzaka zzaka) {
        this.zzJH = zzaka;
    }

    private static Context zza(WebView webView) {
        if (!(webView instanceof zzaka)) {
            return webView.getContext();
        }
        zzaka zzaka = (zzaka) webView;
        Activity zzis = zzaka.zzis();
        return zzis == null ? zzaka.getContext() : zzis;
    }

    private static boolean zza(Context context, String str, String str2, String str3, JsResult jsResult, JsPromptResult jsPromptResult, boolean z) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(str);
            if (z) {
                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(1);
                TextView textView = new TextView(context);
                textView.setText(str2);
                EditText editText = new EditText(context);
                editText.setText(str3);
                linearLayout.addView(textView);
                linearLayout.addView(editText);
                builder.setView(linearLayout).setPositiveButton(17039370, new zzalc(jsPromptResult, editText)).setNegativeButton(17039360, new zzalb(jsPromptResult)).setOnCancelListener(new zzala(jsPromptResult)).create().show();
            } else {
                builder.setMessage(str2).setPositiveButton(17039370, new zzakz(jsResult)).setNegativeButton(17039360, new zzaky(jsResult)).setOnCancelListener(new zzakx(jsResult)).create().show();
            }
        } catch (WindowManager.BadTokenException e) {
            zzafr.zzc("Fail to display Dialog.", e);
        }
        return true;
    }

    public final void onCloseWindow(WebView webView) {
        if (!(webView instanceof zzaka)) {
            zzafr.zzaT("Tried to close a WebView that wasn't an AdWebView.");
            return;
        }
        zzm zziu = ((zzaka) webView).zziu();
        if (zziu == null) {
            zzafr.zzaT("Tried to close an AdWebView not associated with an overlay.");
        } else {
            zziu.close();
        }
    }

    public final boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        String valueOf = String.valueOf(consoleMessage.message());
        String valueOf2 = String.valueOf(consoleMessage.sourceId());
        String sb = new StringBuilder(String.valueOf(valueOf).length() + 19 + String.valueOf(valueOf2).length()).append("JS: ").append(valueOf).append(" (").append(valueOf2).append(":").append(consoleMessage.lineNumber()).append(h.b).toString();
        if (sb.contains("Application Cache")) {
            return super.onConsoleMessage(consoleMessage);
        }
        switch (zzald.zzacK[consoleMessage.messageLevel().ordinal()]) {
            case 1:
                zzafr.e(sb);
                break;
            case 2:
                zzafr.zzaT(sb);
                break;
            case 3:
            case 4:
                zzafr.zzaS(sb);
                break;
            case 5:
                zzafr.zzaC(sb);
                break;
            default:
                zzafr.zzaS(sb);
                break;
        }
        return super.onConsoleMessage(consoleMessage);
    }

    public final boolean onCreateWindow(WebView webView, boolean z, boolean z2, Message message) {
        WebView webView2 = new WebView(webView.getContext());
        webView2.setWebViewClient(this.zzJH.zziw());
        ((WebView.WebViewTransport) message.obj).setWebView(webView2);
        message.sendToTarget();
        return true;
    }

    public final void onExceededDatabaseQuota(String str, String str2, long j, long j2, long j3, WebStorage.QuotaUpdater quotaUpdater) {
        long j4 = 5242880 - j3;
        if (j4 <= 0) {
            quotaUpdater.updateQuota(j);
            return;
        }
        if (j != 0) {
            if (j2 == 0) {
                j = Math.min(Math.min(PlaybackStateCompat.ACTION_PREPARE_FROM_URI, j4) + j, PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED);
            } else if (j2 <= Math.min(PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED - j, j4)) {
                j += j2;
            }
            j2 = j;
        } else if (j2 > j4 || j2 > PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED) {
            j2 = 0;
        }
        quotaUpdater.updateQuota(j2);
    }

    public final void onGeolocationPermissionsShowPrompt(String str, GeolocationPermissions.Callback callback) {
        boolean z;
        if (callback != null) {
            zzbs.zzbz();
            if (!zzagz.zzc(this.zzJH.getContext(), this.zzJH.getContext().getPackageName(), "android.permission.ACCESS_FINE_LOCATION")) {
                zzbs.zzbz();
                if (!zzagz.zzc(this.zzJH.getContext(), this.zzJH.getContext().getPackageName(), "android.permission.ACCESS_COARSE_LOCATION")) {
                    z = false;
                    callback.invoke(str, z, true);
                }
            }
            z = true;
            callback.invoke(str, z, true);
        }
    }

    public final void onHideCustomView() {
        zzm zziu = this.zzJH.zziu();
        if (zziu == null) {
            zzafr.zzaT("Could not get ad overlay when hiding custom view.");
        } else {
            zziu.zzfI();
        }
    }

    public final boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
        return zza(zza(webView), str, str2, (String) null, jsResult, (JsPromptResult) null, false);
    }

    public final boolean onJsBeforeUnload(WebView webView, String str, String str2, JsResult jsResult) {
        return zza(zza(webView), str, str2, (String) null, jsResult, (JsPromptResult) null, false);
    }

    public final boolean onJsConfirm(WebView webView, String str, String str2, JsResult jsResult) {
        return zza(zza(webView), str, str2, (String) null, jsResult, (JsPromptResult) null, false);
    }

    public final boolean onJsPrompt(WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) {
        return zza(zza(webView), str, str2, str3, (JsResult) null, jsPromptResult, true);
    }

    public final void onReachedMaxAppCacheSize(long j, long j2, WebStorage.QuotaUpdater quotaUpdater) {
        long j3 = PlaybackStateCompat.ACTION_PREPARE_FROM_URI + j;
        if (5242880 - j2 < j3) {
            quotaUpdater.updateQuota(0);
        } else {
            quotaUpdater.updateQuota(j3);
        }
    }

    public final void onShowCustomView(View view, WebChromeClient.CustomViewCallback customViewCallback) {
        zza(view, -1, customViewCallback);
    }

    /* access modifiers changed from: protected */
    public final void zza(View view, int i, WebChromeClient.CustomViewCallback customViewCallback) {
        zzm zziu = this.zzJH.zziu();
        if (zziu == null) {
            zzafr.zzaT("Could not get ad overlay when showing custom view.");
            customViewCallback.onCustomViewHidden();
            return;
        }
        zziu.zza(view, customViewCallback);
        zziu.setRequestedOrientation(i);
    }
}

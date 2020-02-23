package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.gms.ads.internal.overlay.zzm;
import com.google.android.gms.ads.internal.zzbl;
import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.ads.internal.zzv;
import com.google.android.gms.common.util.zzq;
import com.tencent.tp.a.h;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

@zzzn
final class zzako extends WebView implements ViewTreeObserver.OnGlobalLayoutListener, DownloadListener, zzaka {
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    @Nullable
    private final zzcu zzIc;
    private int zzNY = -1;
    private int zzNZ = -1;
    private int zzOb = -1;
    private int zzOc = -1;
    private String zzQx = "";
    private zzmz zzQy;
    private Boolean zzYA;
    private final zzakr zzabS;
    private final zzbl zzabT;
    private zzakb zzabU;
    private zzm zzabV;
    private boolean zzabW;
    private boolean zzabX;
    private boolean zzabY;
    private boolean zzabZ;
    private int zzaca;
    private boolean zzacb = true;
    private boolean zzacc = false;
    private zzaks zzacd;
    private boolean zzace;
    private boolean zzacf;
    private zznw zzacg;
    private int zzach;
    /* access modifiers changed from: private */
    public int zzaci;
    private zzmz zzacj;
    private zzmz zzack;
    private zzna zzacl;
    private WeakReference<View.OnClickListener> zzacm;
    private zzm zzacn;
    private boolean zzaco;
    private Map<String, zzsb> zzacp;
    private final zzig zzacq;
    private final zzv zzsS;
    private final zzaje zztW;
    private zziv zzuZ;
    private zzaix zzwC;
    private final WindowManager zzwR;

    private zzako(zzakr zzakr, zziv zziv, boolean z, boolean z2, @Nullable zzcu zzcu, zzaje zzaje, zznb zznb, zzbl zzbl, zzv zzv, zzig zzig) {
        super(zzakr);
        this.zzabS = zzakr;
        this.zzuZ = zziv;
        this.zzabY = z;
        this.zzaca = -1;
        this.zzIc = zzcu;
        this.zztW = zzaje;
        this.zzabT = zzbl;
        this.zzsS = zzv;
        this.zzwR = (WindowManager) getContext().getSystemService("window");
        this.zzacq = zzig;
        setBackgroundColor(0);
        WebSettings settings = getSettings();
        settings.setAllowFileAccess(false);
        try {
            settings.setJavaScriptEnabled(true);
        } catch (NullPointerException e) {
            zzafr.zzb("Unable to enable Javascript.", e);
        }
        settings.setSavePassword(false);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= 21) {
            settings.setMixedContentMode(2);
        }
        settings.setUserAgentString(zzbs.zzbz().zzs(zzakr, zzaje.zzaP));
        zzbs.zzbB().zza(getContext(), settings);
        setDownloadListener(this);
        zzjd();
        if (zzq.zzsa()) {
            addJavascriptInterface(new zzakv(this), "googleAdsJsInterface");
        }
        removeJavascriptInterface("accessibility");
        removeJavascriptInterface("accessibilityTraversal");
        this.zzwC = new zzaix(this.zzabS.zzis(), this, this, (ViewTreeObserver.OnScrollChangedListener) null);
        zzd(zznb);
        zzbs.zzbB().zzR(zzakr);
    }

    private final void zzF(boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put("isVisible", z ? "1" : "0");
        zza("onAdVisibilityChanged", (Map<String, ?>) hashMap);
    }

    private final void zza(Boolean bool) {
        synchronized (this.mLock) {
            this.zzYA = bool;
        }
        zzbs.zzbD().zza(bool);
    }

    private final void zzaW(String str) {
        synchronized (this.mLock) {
            if (!isDestroyed()) {
                loadUrl(str);
            } else {
                zzafr.zzaT("The webview is destroyed. Ignoring action.");
            }
        }
    }

    private final void zzaX(String str) {
        if (zzq.zzsc()) {
            if (zzhw() == null) {
                synchronized (this.mLock) {
                    this.zzYA = zzbs.zzbD().zzhw();
                    if (this.zzYA == null) {
                        try {
                            evaluateJavascript("(function(){})()", (ValueCallback<String>) null);
                            zza((Boolean) true);
                        } catch (IllegalStateException e) {
                            zza((Boolean) false);
                        }
                    }
                }
            }
            if (zzhw().booleanValue()) {
                synchronized (this.mLock) {
                    if (!isDestroyed()) {
                        evaluateJavascript(str, (ValueCallback<String>) null);
                    } else {
                        zzafr.zzaT("The webview is destroyed. Ignoring action.");
                    }
                }
                return;
            }
            String valueOf = String.valueOf(str);
            zzaW(valueOf.length() != 0 ? "javascript:".concat(valueOf) : new String("javascript:"));
            return;
        }
        String valueOf2 = String.valueOf(str);
        zzaW(valueOf2.length() != 0 ? "javascript:".concat(valueOf2) : new String("javascript:"));
    }

    static zzako zzb(Context context, zziv zziv, boolean z, boolean z2, @Nullable zzcu zzcu, zzaje zzaje, zznb zznb, zzbl zzbl, zzv zzv, zzig zzig) {
        return new zzako(new zzakr(context), zziv, z, z2, zzcu, zzaje, zznb, zzbl, zzv, zzig);
    }

    private final void zzd(zznb zznb) {
        zzjg();
        this.zzacl = new zzna(new zznb(true, "make_wv", this.zzuZ.zzAs));
        this.zzacl.zzdR().zzc(zznb);
        this.zzQy = zzmu.zzb(this.zzacl.zzdR());
        this.zzacl.zza("native:view_create", this.zzQy);
        this.zzack = null;
        this.zzacj = null;
    }

    private final void zzhI() {
        synchronized (this.mLock) {
            if (!this.zzaco) {
                this.zzaco = true;
                zzbs.zzbD().zzhI();
            }
        }
    }

    private final Boolean zzhw() {
        Boolean bool;
        synchronized (this.mLock) {
            bool = this.zzYA;
        }
        return bool;
    }

    private final boolean zzjb() {
        int i;
        int i2;
        if (!this.zzabU.zzcn() && !this.zzabU.zziP()) {
            return false;
        }
        zzbs.zzbz();
        DisplayMetrics zza = zzagz.zza(this.zzwR);
        zzji.zzds();
        int zzb = zzaiy.zzb(zza, zza.widthPixels);
        zzji.zzds();
        int zzb2 = zzaiy.zzb(zza, zza.heightPixels);
        Activity zzis = this.zzabS.zzis();
        if (zzis == null || zzis.getWindow() == null) {
            i = zzb2;
            i2 = zzb;
        } else {
            zzbs.zzbz();
            int[] zzf = zzagz.zzf(zzis);
            zzji.zzds();
            i2 = zzaiy.zzb(zza, zzf[0]);
            zzji.zzds();
            i = zzaiy.zzb(zza, zzf[1]);
        }
        if (this.zzNY == zzb && this.zzNZ == zzb2 && this.zzOb == i2 && this.zzOc == i) {
            return false;
        }
        boolean z = (this.zzNY == zzb && this.zzNZ == zzb2) ? false : true;
        this.zzNY = zzb;
        this.zzNZ = zzb2;
        this.zzOb = i2;
        this.zzOc = i;
        new zzwu(this).zza(zzb, zzb2, i2, i, zza.density, this.zzwR.getDefaultDisplay().getRotation());
        return z;
    }

    private final void zzjc() {
        zzmu.zza(this.zzacl.zzdR(), this.zzQy, "aeh2");
    }

    private final void zzjd() {
        synchronized (this.mLock) {
            if (this.zzabY || this.zzuZ.zzAt) {
                zzafr.zzaC("Enabling hardware acceleration on an overlay.");
                zzje();
            } else if (Build.VERSION.SDK_INT < 18) {
                zzafr.zzaC("Disabling hardware acceleration on an AdView.");
                synchronized (this.mLock) {
                    if (!this.zzabZ) {
                        zzbs.zzbB().zzr(this);
                    }
                    this.zzabZ = true;
                }
            } else {
                zzafr.zzaC("Enabling hardware acceleration on an AdView.");
                zzje();
            }
        }
    }

    private final void zzje() {
        synchronized (this.mLock) {
            if (this.zzabZ) {
                zzbs.zzbB().zzq(this);
            }
            this.zzabZ = false;
        }
    }

    private final void zzjf() {
        synchronized (this.mLock) {
            this.zzacp = null;
        }
    }

    private final void zzjg() {
        zznb zzdR;
        if (this.zzacl != null && (zzdR = this.zzacl.zzdR()) != null && zzbs.zzbD().zzhr() != null) {
            zzbs.zzbD().zzhr().zza(zzdR);
        }
    }

    public final void destroy() {
        synchronized (this.mLock) {
            zzjg();
            this.zzwC.zzih();
            if (this.zzabV != null) {
                this.zzabV.close();
                this.zzabV.onDestroy();
                this.zzabV = null;
            }
            this.zzabU.reset();
            if (!this.zzabX) {
                zzbs.zzbW();
                zzsa.zze(this);
                zzjf();
                this.zzabX = true;
                zzafr.v("Initiating WebView self destruct sequence in 3...");
                this.zzabU.zziT();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        return;
     */
    @android.annotation.TargetApi(19)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void evaluateJavascript(java.lang.String r3, android.webkit.ValueCallback<java.lang.String> r4) {
        /*
            r2 = this;
            java.lang.Object r1 = r2.mLock
            monitor-enter(r1)
            boolean r0 = r2.isDestroyed()     // Catch:{ all -> 0x001b }
            if (r0 == 0) goto L_0x0016
            java.lang.String r0 = "The webview is destroyed. Ignoring action."
            com.google.android.gms.internal.zzafr.zzaT(r0)     // Catch:{ all -> 0x001b }
            if (r4 == 0) goto L_0x0014
            r0 = 0
            r4.onReceiveValue(r0)     // Catch:{ all -> 0x001b }
        L_0x0014:
            monitor-exit(r1)     // Catch:{ all -> 0x001b }
        L_0x0015:
            return
        L_0x0016:
            super.evaluateJavascript(r3, r4)     // Catch:{ all -> 0x001b }
            monitor-exit(r1)     // Catch:{ all -> 0x001b }
            goto L_0x0015
        L_0x001b:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x001b }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzako.evaluateJavascript(java.lang.String, android.webkit.ValueCallback):void");
    }

    /* access modifiers changed from: protected */
    public final void finalize() throws Throwable {
        try {
            if (this.mLock != null) {
                synchronized (this.mLock) {
                    if (!this.zzabX) {
                        this.zzabU.reset();
                        zzbs.zzbW();
                        zzsa.zze(this);
                        zzjf();
                        zzhI();
                    }
                }
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
            throw th;
        }
    }

    public final String getRequestId() {
        String str;
        synchronized (this.mLock) {
            str = this.zzQx;
        }
        return str;
    }

    public final int getRequestedOrientation() {
        int i;
        synchronized (this.mLock) {
            i = this.zzaca;
        }
        return i;
    }

    public final View getView() {
        return this;
    }

    public final WebView getWebView() {
        return this;
    }

    public final boolean isDestroyed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzabX;
        }
        return z;
    }

    public final void loadData(String str, String str2, String str3) {
        synchronized (this.mLock) {
            if (!isDestroyed()) {
                super.loadData(str, str2, str3);
            } else {
                zzafr.zzaT("The webview is destroyed. Ignoring action.");
            }
        }
    }

    public final void loadDataWithBaseURL(String str, String str2, String str3, String str4, String str5) {
        synchronized (this.mLock) {
            if (!isDestroyed()) {
                super.loadDataWithBaseURL(str, str2, str3, str4, str5);
            } else {
                zzafr.zzaT("The webview is destroyed. Ignoring action.");
            }
        }
    }

    public final void loadUrl(String str) {
        synchronized (this.mLock) {
            if (!isDestroyed()) {
                try {
                    super.loadUrl(str);
                } catch (Throwable th) {
                    zzbs.zzbD().zza(th, "AdWebViewImpl.loadUrl");
                    zzafr.zzc("Could not call loadUrl. ", th);
                }
            } else {
                zzafr.zzaT("The webview is destroyed. Ignoring action.");
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void onAttachedToWindow() {
        boolean z = true;
        synchronized (this.mLock) {
            super.onAttachedToWindow();
            if (!isDestroyed()) {
                this.zzwC.onAttachedToWindow();
            }
            boolean z2 = this.zzace;
            if (this.zzabU == null || !this.zzabU.zziP()) {
                z = z2;
            } else {
                if (!this.zzacf) {
                    ViewTreeObserver.OnGlobalLayoutListener zziQ = this.zzabU.zziQ();
                    if (zziQ != null) {
                        zzbs.zzbX();
                        zzajv.zza((View) this, zziQ);
                    }
                    ViewTreeObserver.OnScrollChangedListener zziR = this.zzabU.zziR();
                    if (zziR != null) {
                        zzbs.zzbX();
                        zzajv.zza((View) this, zziR);
                    }
                    this.zzacf = true;
                }
                zzjb();
            }
            zzF(z);
        }
    }

    /* access modifiers changed from: protected */
    public final void onDetachedFromWindow() {
        synchronized (this.mLock) {
            if (!isDestroyed()) {
                this.zzwC.onDetachedFromWindow();
            }
            super.onDetachedFromWindow();
            if (this.zzacf && this.zzabU != null && this.zzabU.zziP() && getViewTreeObserver() != null && getViewTreeObserver().isAlive()) {
                ViewTreeObserver.OnGlobalLayoutListener zziQ = this.zzabU.zziQ();
                if (zziQ != null) {
                    zzbs.zzbB().zza(getViewTreeObserver(), zziQ);
                }
                ViewTreeObserver.OnScrollChangedListener zziR = this.zzabU.zziR();
                if (zziR != null) {
                    getViewTreeObserver().removeOnScrollChangedListener(zziR);
                }
                this.zzacf = false;
            }
        }
        zzF(false);
    }

    public final void onDownloadStart(String str, String str2, String str3, String str4, long j) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(Uri.parse(str), str4);
            zzbs.zzbz();
            zzagz.zzb(getContext(), intent);
        } catch (ActivityNotFoundException e) {
            zzafr.zzaC(new StringBuilder(String.valueOf(str).length() + 51 + String.valueOf(str4).length()).append("Couldn't find an Activity to view url/mimetype: ").append(str).append(" / ").append(str4).toString());
        }
    }

    /* access modifiers changed from: protected */
    @TargetApi(21)
    public final void onDraw(Canvas canvas) {
        if (!isDestroyed()) {
            if (Build.VERSION.SDK_INT != 21 || !canvas.isHardwareAccelerated() || isAttachedToWindow()) {
                super.onDraw(canvas);
                if (this.zzabU != null && this.zzabU.zzja() != null) {
                    this.zzabU.zzja().zzaS();
                }
            }
        }
    }

    public final boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if (((Boolean) zzbs.zzbL().zzd(zzmo.zzDF)).booleanValue()) {
            float axisValue = motionEvent.getAxisValue(9);
            float axisValue2 = motionEvent.getAxisValue(10);
            if (motionEvent.getActionMasked() == 8 && ((axisValue > 0.0f && !canScrollVertically(-1)) || ((axisValue < 0.0f && !canScrollVertically(1)) || ((axisValue2 > 0.0f && !canScrollHorizontally(-1)) || (axisValue2 < 0.0f && !canScrollHorizontally(1)))))) {
                return false;
            }
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    public final void onGlobalLayout() {
        boolean zzjb = zzjb();
        zzm zziu = zziu();
        if (zziu != null && zzjb) {
            zziu.zzfO();
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:68:?, code lost:
        return;
     */
    @android.annotation.SuppressLint({"DrawAllocation"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onMeasure(int r10, int r11) {
        /*
            r9 = this;
            r0 = 2147483647(0x7fffffff, float:NaN)
            r8 = 1073741824(0x40000000, float:2.0)
            r7 = 8
            r6 = -2147483648(0xffffffff80000000, float:-0.0)
            java.lang.Object r4 = r9.mLock
            monitor-enter(r4)
            boolean r1 = r9.isDestroyed()     // Catch:{ all -> 0x002e }
            if (r1 == 0) goto L_0x0019
            r0 = 0
            r1 = 0
            r9.setMeasuredDimension(r0, r1)     // Catch:{ all -> 0x002e }
            monitor-exit(r4)     // Catch:{ all -> 0x002e }
        L_0x0018:
            return
        L_0x0019:
            boolean r1 = r9.isInEditMode()     // Catch:{ all -> 0x002e }
            if (r1 != 0) goto L_0x0029
            boolean r1 = r9.zzabY     // Catch:{ all -> 0x002e }
            if (r1 != 0) goto L_0x0029
            com.google.android.gms.internal.zziv r1 = r9.zzuZ     // Catch:{ all -> 0x002e }
            boolean r1 = r1.zzAv     // Catch:{ all -> 0x002e }
            if (r1 == 0) goto L_0x0031
        L_0x0029:
            super.onMeasure(r10, r11)     // Catch:{ all -> 0x002e }
            monitor-exit(r4)     // Catch:{ all -> 0x002e }
            goto L_0x0018
        L_0x002e:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x002e }
            throw r0
        L_0x0031:
            com.google.android.gms.internal.zziv r1 = r9.zzuZ     // Catch:{ all -> 0x002e }
            boolean r1 = r1.zzAw     // Catch:{ all -> 0x002e }
            if (r1 == 0) goto L_0x0087
            com.google.android.gms.internal.zzme<java.lang.Boolean> r0 = com.google.android.gms.internal.zzmo.zzFD     // Catch:{ all -> 0x002e }
            com.google.android.gms.internal.zzmm r1 = com.google.android.gms.ads.internal.zzbs.zzbL()     // Catch:{ all -> 0x002e }
            java.lang.Object r0 = r1.zzd(r0)     // Catch:{ all -> 0x002e }
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ all -> 0x002e }
            boolean r0 = r0.booleanValue()     // Catch:{ all -> 0x002e }
            if (r0 != 0) goto L_0x004f
            boolean r0 = com.google.android.gms.common.util.zzq.zzsa()     // Catch:{ all -> 0x002e }
            if (r0 != 0) goto L_0x0054
        L_0x004f:
            super.onMeasure(r10, r11)     // Catch:{ all -> 0x002e }
            monitor-exit(r4)     // Catch:{ all -> 0x002e }
            goto L_0x0018
        L_0x0054:
            java.lang.String r0 = "/contentHeight"
            com.google.android.gms.internal.zzakp r1 = new com.google.android.gms.internal.zzakp     // Catch:{ all -> 0x002e }
            r1.<init>(r9)     // Catch:{ all -> 0x002e }
            r9.zza((java.lang.String) r0, (com.google.android.gms.internal.zzrd) r1)     // Catch:{ all -> 0x002e }
            java.lang.String r0 = "(function() {  var height = -1;  if (document.body) {    height = document.body.offsetHeight;  } else if (document.documentElement) {    height = document.documentElement.offsetHeight;  }  var url = 'gmsg://mobileads.google.com/contentHeight?';  url += 'height=' + height;  try {    window.googleAdsJsInterface.notify(url);  } catch (e) {    var frame = document.getElementById('afma-notify-fluid');    if (!frame) {      frame = document.createElement('IFRAME');      frame.id = 'afma-notify-fluid';      frame.style.display = 'none';      var body = document.body || document.documentElement;      body.appendChild(frame);    }    frame.src = url;  }})();"
            r9.zzaX(r0)     // Catch:{ all -> 0x002e }
            com.google.android.gms.internal.zzakr r0 = r9.zzabS     // Catch:{ all -> 0x002e }
            android.content.res.Resources r0 = r0.getResources()     // Catch:{ all -> 0x002e }
            android.util.DisplayMetrics r0 = r0.getDisplayMetrics()     // Catch:{ all -> 0x002e }
            float r0 = r0.density     // Catch:{ all -> 0x002e }
            int r1 = android.view.View.MeasureSpec.getSize(r10)     // Catch:{ all -> 0x002e }
            int r2 = r9.zzaci     // Catch:{ all -> 0x002e }
            switch(r2) {
                case -1: goto L_0x0082;
                default: goto L_0x0078;
            }     // Catch:{ all -> 0x002e }
        L_0x0078:
            int r2 = r9.zzaci     // Catch:{ all -> 0x002e }
            float r2 = (float) r2     // Catch:{ all -> 0x002e }
            float r0 = r0 * r2
            int r0 = (int) r0     // Catch:{ all -> 0x002e }
        L_0x007d:
            r9.setMeasuredDimension(r1, r0)     // Catch:{ all -> 0x002e }
            monitor-exit(r4)     // Catch:{ all -> 0x002e }
            goto L_0x0018
        L_0x0082:
            int r0 = android.view.View.MeasureSpec.getSize(r11)     // Catch:{ all -> 0x002e }
            goto L_0x007d
        L_0x0087:
            com.google.android.gms.internal.zziv r1 = r9.zzuZ     // Catch:{ all -> 0x002e }
            boolean r1 = r1.zzAt     // Catch:{ all -> 0x002e }
            if (r1 == 0) goto L_0x00a5
            android.util.DisplayMetrics r0 = new android.util.DisplayMetrics     // Catch:{ all -> 0x002e }
            r0.<init>()     // Catch:{ all -> 0x002e }
            android.view.WindowManager r1 = r9.zzwR     // Catch:{ all -> 0x002e }
            android.view.Display r1 = r1.getDefaultDisplay()     // Catch:{ all -> 0x002e }
            r1.getMetrics(r0)     // Catch:{ all -> 0x002e }
            int r1 = r0.widthPixels     // Catch:{ all -> 0x002e }
            int r0 = r0.heightPixels     // Catch:{ all -> 0x002e }
            r9.setMeasuredDimension(r1, r0)     // Catch:{ all -> 0x002e }
            monitor-exit(r4)     // Catch:{ all -> 0x002e }
            goto L_0x0018
        L_0x00a5:
            int r2 = android.view.View.MeasureSpec.getMode(r10)     // Catch:{ all -> 0x002e }
            int r3 = android.view.View.MeasureSpec.getSize(r10)     // Catch:{ all -> 0x002e }
            int r5 = android.view.View.MeasureSpec.getMode(r11)     // Catch:{ all -> 0x002e }
            int r1 = android.view.View.MeasureSpec.getSize(r11)     // Catch:{ all -> 0x002e }
            if (r2 == r6) goto L_0x00b9
            if (r2 != r8) goto L_0x0150
        L_0x00b9:
            r2 = r3
        L_0x00ba:
            if (r5 == r6) goto L_0x00be
            if (r5 != r8) goto L_0x00bf
        L_0x00be:
            r0 = r1
        L_0x00bf:
            com.google.android.gms.internal.zziv r5 = r9.zzuZ     // Catch:{ all -> 0x002e }
            int r5 = r5.widthPixels     // Catch:{ all -> 0x002e }
            if (r5 > r2) goto L_0x00cb
            com.google.android.gms.internal.zziv r2 = r9.zzuZ     // Catch:{ all -> 0x002e }
            int r2 = r2.heightPixels     // Catch:{ all -> 0x002e }
            if (r2 <= r0) goto L_0x013a
        L_0x00cb:
            com.google.android.gms.internal.zzakr r0 = r9.zzabS     // Catch:{ all -> 0x002e }
            android.content.res.Resources r0 = r0.getResources()     // Catch:{ all -> 0x002e }
            android.util.DisplayMetrics r0 = r0.getDisplayMetrics()     // Catch:{ all -> 0x002e }
            float r0 = r0.density     // Catch:{ all -> 0x002e }
            com.google.android.gms.internal.zziv r2 = r9.zzuZ     // Catch:{ all -> 0x002e }
            int r2 = r2.widthPixels     // Catch:{ all -> 0x002e }
            float r2 = (float) r2     // Catch:{ all -> 0x002e }
            float r2 = r2 / r0
            int r2 = (int) r2     // Catch:{ all -> 0x002e }
            com.google.android.gms.internal.zziv r5 = r9.zzuZ     // Catch:{ all -> 0x002e }
            int r5 = r5.heightPixels     // Catch:{ all -> 0x002e }
            float r5 = (float) r5     // Catch:{ all -> 0x002e }
            float r5 = r5 / r0
            int r5 = (int) r5     // Catch:{ all -> 0x002e }
            float r3 = (float) r3     // Catch:{ all -> 0x002e }
            float r3 = r3 / r0
            int r3 = (int) r3     // Catch:{ all -> 0x002e }
            float r1 = (float) r1     // Catch:{ all -> 0x002e }
            float r0 = r1 / r0
            int r0 = (int) r0     // Catch:{ all -> 0x002e }
            r1 = 103(0x67, float:1.44E-43)
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x002e }
            r6.<init>(r1)     // Catch:{ all -> 0x002e }
            java.lang.String r1 = "Not enough space to show ad. Needs "
            java.lang.StringBuilder r1 = r6.append(r1)     // Catch:{ all -> 0x002e }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ all -> 0x002e }
            java.lang.String r2 = "x"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ all -> 0x002e }
            java.lang.StringBuilder r1 = r1.append(r5)     // Catch:{ all -> 0x002e }
            java.lang.String r2 = " dp, but only has "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ all -> 0x002e }
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ all -> 0x002e }
            java.lang.String r2 = "x"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ all -> 0x002e }
            java.lang.StringBuilder r0 = r1.append(r0)     // Catch:{ all -> 0x002e }
            java.lang.String r1 = " dp."
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ all -> 0x002e }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x002e }
            com.google.android.gms.internal.zzafr.zzaT(r0)     // Catch:{ all -> 0x002e }
            int r0 = r9.getVisibility()     // Catch:{ all -> 0x002e }
            if (r0 == r7) goto L_0x0132
            r0 = 4
            r9.setVisibility(r0)     // Catch:{ all -> 0x002e }
        L_0x0132:
            r0 = 0
            r1 = 0
            r9.setMeasuredDimension(r0, r1)     // Catch:{ all -> 0x002e }
        L_0x0137:
            monitor-exit(r4)     // Catch:{ all -> 0x002e }
            goto L_0x0018
        L_0x013a:
            int r0 = r9.getVisibility()     // Catch:{ all -> 0x002e }
            if (r0 == r7) goto L_0x0144
            r0 = 0
            r9.setVisibility(r0)     // Catch:{ all -> 0x002e }
        L_0x0144:
            com.google.android.gms.internal.zziv r0 = r9.zzuZ     // Catch:{ all -> 0x002e }
            int r0 = r0.widthPixels     // Catch:{ all -> 0x002e }
            com.google.android.gms.internal.zziv r1 = r9.zzuZ     // Catch:{ all -> 0x002e }
            int r1 = r1.heightPixels     // Catch:{ all -> 0x002e }
            r9.setMeasuredDimension(r0, r1)     // Catch:{ all -> 0x002e }
            goto L_0x0137
        L_0x0150:
            r2 = r0
            goto L_0x00ba
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzako.onMeasure(int, int):void");
    }

    public final void onPause() {
        if (!isDestroyed()) {
            try {
                super.onPause();
            } catch (Exception e) {
                zzafr.zzb("Could not pause webview.", e);
            }
        }
    }

    public final void onResume() {
        if (!isDestroyed()) {
            try {
                super.onResume();
            } catch (Exception e) {
                zzafr.zzb("Could not resume webview.", e);
            }
        }
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.zzabU.zziP()) {
            synchronized (this.mLock) {
                if (this.zzacg != null) {
                    this.zzacg.zzc(motionEvent);
                }
            }
        } else if (this.zzIc != null) {
            this.zzIc.zza(motionEvent);
        }
        if (isDestroyed()) {
            return false;
        }
        return super.onTouchEvent(motionEvent);
    }

    public final void setContext(Context context) {
        this.zzabS.setBaseContext(context);
        this.zzwC.zzi(this.zzabS.zzis());
    }

    public final void setOnClickListener(View.OnClickListener onClickListener) {
        this.zzacm = new WeakReference<>(onClickListener);
        super.setOnClickListener(onClickListener);
    }

    public final void setRequestedOrientation(int i) {
        synchronized (this.mLock) {
            this.zzaca = i;
            if (this.zzabV != null) {
                this.zzabV.setRequestedOrientation(this.zzaca);
            }
        }
    }

    public final void setWebViewClient(WebViewClient webViewClient) {
        super.setWebViewClient(webViewClient);
        if (webViewClient instanceof zzakb) {
            this.zzabU = (zzakb) webViewClient;
        }
    }

    public final void stopLoading() {
        if (!isDestroyed()) {
            try {
                super.stopLoading();
            } catch (Exception e) {
                zzafr.zzb("Could not stop loading webview.", e);
            }
        }
    }

    public final void zzA(int i) {
        if (i == 0) {
            zzmu.zza(this.zzacl.zzdR(), this.zzQy, "aebb2");
        }
        zzjc();
        if (this.zzacl.zzdR() != null) {
            this.zzacl.zzdR().zzh("close_type", String.valueOf(i));
        }
        HashMap hashMap = new HashMap(2);
        hashMap.put("closetype", String.valueOf(i));
        hashMap.put("version", this.zztW.zzaP);
        zza("onhide", (Map<String, ?>) hashMap);
    }

    public final void zzA(boolean z) {
        synchronized (this.mLock) {
            boolean z2 = z != this.zzabY;
            this.zzabY = z;
            zzjd();
            if (z2) {
                new zzwu(this).zzap(z ? "expanded" : "default");
            }
        }
    }

    public final void zzB(boolean z) {
        synchronized (this.mLock) {
            if (this.zzabV != null) {
                this.zzabV.zza(this.zzabU.zzcn(), z);
            } else {
                this.zzabW = z;
            }
        }
    }

    public final void zzC(boolean z) {
        synchronized (this.mLock) {
            this.zzacb = z;
        }
    }

    public final void zzD(boolean z) {
        synchronized (this.mLock) {
            this.zzach = (z ? 1 : -1) + this.zzach;
            if (this.zzach <= 0 && this.zzabV != null) {
                this.zzabV.zzfR();
            }
        }
    }

    public final void zza(Context context, zziv zziv, zznb zznb) {
        synchronized (this.mLock) {
            this.zzwC.zzih();
            setContext(context);
            this.zzabV = null;
            this.zzuZ = zziv;
            this.zzabY = false;
            this.zzabW = false;
            this.zzQx = "";
            this.zzaca = -1;
            zzbs.zzbB();
            zzahe.zzl(this);
            loadUrl("about:blank");
            this.zzabU.reset();
            setOnTouchListener((View.OnTouchListener) null);
            setOnClickListener((View.OnClickListener) null);
            this.zzacb = true;
            this.zzacc = false;
            this.zzacd = null;
            zzd(zznb);
            this.zzace = false;
            this.zzach = 0;
            zzbs.zzbW();
            zzsa.zze(this);
            zzjf();
        }
    }

    public final void zza(zzaks zzaks) {
        synchronized (this.mLock) {
            if (this.zzacd != null) {
                zzafr.e("Attempt to create multiple AdWebViewVideoControllers.");
            } else {
                this.zzacd = zzaks;
            }
        }
    }

    public final void zza(zzgh zzgh) {
        synchronized (this.mLock) {
            this.zzace = zzgh.zzxS;
        }
        zzF(zzgh.zzxS);
    }

    public final void zza(zziv zziv) {
        synchronized (this.mLock) {
            this.zzuZ = zziv;
            requestLayout();
        }
    }

    public final void zza(String str, zzrd zzrd) {
        if (this.zzabU != null) {
            this.zzabU.zza(str, zzrd);
        }
    }

    public final void zza(String str, Map<String, ?> map) {
        try {
            zzb(str, zzbs.zzbz().zzj(map));
        } catch (JSONException e) {
            zzafr.zzaT("Could not convert parameters to JSON.");
        }
    }

    public final void zza(String str, JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        zzi(str, jSONObject.toString());
    }

    public final void zzaJ() {
        synchronized (this.mLock) {
            this.zzacc = true;
            if (this.zzabT != null) {
                this.zzabT.zzaJ();
            }
        }
    }

    public final void zzaK() {
        synchronized (this.mLock) {
            this.zzacc = false;
            if (this.zzabT != null) {
                this.zzabT.zzaK();
            }
        }
    }

    public final void zzaU(String str) {
        synchronized (this.mLock) {
            try {
                super.loadUrl(str);
            } catch (Throwable th) {
                zzbs.zzbD().zza(th, "AdWebViewImpl.loadUrlUnsafe");
                zzafr.zzc("Could not call loadUrl. ", th);
            }
        }
    }

    public final void zzaV(String str) {
        synchronized (this.mLock) {
            if (str == null) {
                str = "";
            }
            this.zzQx = str;
        }
    }

    public final zzv zzak() {
        return this.zzsS;
    }

    public final zziv zzam() {
        zziv zziv;
        synchronized (this.mLock) {
            zziv = this.zzuZ;
        }
        return zziv;
    }

    public final void zzb(zzm zzm) {
        synchronized (this.mLock) {
            this.zzabV = zzm;
        }
    }

    public final void zzb(zznw zznw) {
        synchronized (this.mLock) {
            this.zzacg = zznw;
        }
    }

    public final void zzb(String str, zzrd zzrd) {
        if (this.zzabU != null) {
            this.zzabU.zzb(str, zzrd);
        }
    }

    public final void zzb(String str, JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        String jSONObject2 = jSONObject.toString();
        StringBuilder sb = new StringBuilder();
        sb.append("(window.AFMA_ReceiveMessage || function() {})('");
        sb.append(str);
        sb.append("'");
        sb.append(",");
        sb.append(jSONObject2);
        sb.append(");");
        String valueOf = String.valueOf(sb.toString());
        zzafr.zzaC(valueOf.length() != 0 ? "Dispatching AFMA event: ".concat(valueOf) : new String("Dispatching AFMA event: "));
        zzaX(sb.toString());
    }

    public final void zzc(zzm zzm) {
        synchronized (this.mLock) {
            this.zzacn = zzm;
        }
    }

    public final void zzfP() {
        if (this.zzacj == null) {
            zzmu.zza(this.zzacl.zzdR(), this.zzQy, "aes2");
            this.zzacj = zzmu.zzb(this.zzacl.zzdR());
            this.zzacl.zza("native:view_show", this.zzacj);
        }
        HashMap hashMap = new HashMap(1);
        hashMap.put("version", this.zztW.zzaP);
        zza("onshow", (Map<String, ?>) hashMap);
    }

    public final void zzi(String str, String str2) {
        zzaX(new StringBuilder(String.valueOf(str).length() + 3 + String.valueOf(str2).length()).append(str).append(h.a).append(str2).append(");").toString());
    }

    public final boolean zziA() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzabY;
        }
        return z;
    }

    public final void zziB() {
        synchronized (this.mLock) {
            zzafr.v("Destroying WebView!");
            zzhI();
            zzagz.zzZr.post(new zzakq(this));
        }
    }

    public final boolean zziC() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzacb;
        }
        return z;
    }

    public final boolean zziD() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzacc;
        }
        return z;
    }

    public final zzajz zziE() {
        return null;
    }

    public final zzmz zziF() {
        return this.zzQy;
    }

    public final zzna zziG() {
        return this.zzacl;
    }

    public final zzaks zziH() {
        zzaks zzaks;
        synchronized (this.mLock) {
            zzaks = this.zzacd;
        }
        return zzaks;
    }

    public final boolean zziI() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzach > 0;
        }
        return z;
    }

    public final void zziJ() {
        this.zzwC.zzig();
    }

    public final void zziK() {
        if (this.zzack == null) {
            this.zzack = zzmu.zzb(this.zzacl.zzdR());
            this.zzacl.zza("native:view_load", this.zzack);
        }
    }

    public final View.OnClickListener zziL() {
        return (View.OnClickListener) this.zzacm.get();
    }

    public final zznw zziM() {
        zznw zznw;
        synchronized (this.mLock) {
            zznw = this.zzacg;
        }
        return zznw;
    }

    public final void zziN() {
        setBackgroundColor(0);
    }

    public final void zziq() {
        zzjc();
        HashMap hashMap = new HashMap(1);
        hashMap.put("version", this.zztW.zzaP);
        zza("onhide", (Map<String, ?>) hashMap);
    }

    public final void zzir() {
        HashMap hashMap = new HashMap(3);
        zzbs.zzbz();
        hashMap.put("app_muted", String.valueOf(zzagz.zzbh()));
        zzbs.zzbz();
        hashMap.put("app_volume", String.valueOf(zzagz.zzbf()));
        zzbs.zzbz();
        hashMap.put("device_volume", String.valueOf(zzagz.zzM(getContext())));
        zza(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, (Map<String, ?>) hashMap);
    }

    public final Activity zzis() {
        return this.zzabS.zzis();
    }

    public final Context zzit() {
        return this.zzabS.zzit();
    }

    public final zzm zziu() {
        zzm zzm;
        synchronized (this.mLock) {
            zzm = this.zzabV;
        }
        return zzm;
    }

    public final zzm zziv() {
        zzm zzm;
        synchronized (this.mLock) {
            zzm = this.zzacn;
        }
        return zzm;
    }

    public final zzakb zziw() {
        return this.zzabU;
    }

    public final boolean zzix() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzabW;
        }
        return z;
    }

    public final zzcu zziy() {
        return this.zzIc;
    }

    public final zzaje zziz() {
        return this.zztW;
    }
}

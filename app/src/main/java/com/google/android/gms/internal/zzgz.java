package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.graphics.Rect;
import android.os.PowerManager;
import android.os.Process;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.common.util.zzq;
import com.tencent.component.net.download.multiplex.http.ContentType;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

@zzzn
@TargetApi(14)
public final class zzgz extends Thread {
    private final Object mLock;
    private boolean mStarted = false;
    private boolean zzak = false;
    private final int zzxW;
    private final int zzxY;
    private final int zzyA;
    private final int zzyB;
    private final int zzyC;
    private final int zzyD;
    private final int zzyE;
    private final int zzyF;
    private final String zzyG;
    private final boolean zzyH;
    private boolean zzyx = false;
    private final zzgu zzyy;
    private final zzzl zzyz;

    public zzgz(zzgu zzgu, zzzl zzzl) {
        this.zzyy = zzgu;
        this.zzyz = zzzl;
        this.mLock = new Object();
        this.zzxW = ((Integer) zzbs.zzbL().zzd(zzmo.zzCU)).intValue();
        this.zzyB = ((Integer) zzbs.zzbL().zzd(zzmo.zzCV)).intValue();
        this.zzxY = ((Integer) zzbs.zzbL().zzd(zzmo.zzCW)).intValue();
        this.zzyC = ((Integer) zzbs.zzbL().zzd(zzmo.zzCX)).intValue();
        this.zzyD = ((Integer) zzbs.zzbL().zzd(zzmo.zzDa)).intValue();
        this.zzyE = ((Integer) zzbs.zzbL().zzd(zzmo.zzDc)).intValue();
        this.zzyF = ((Integer) zzbs.zzbL().zzd(zzmo.zzDd)).intValue();
        this.zzyA = ((Integer) zzbs.zzbL().zzd(zzmo.zzCY)).intValue();
        this.zzyG = (String) zzbs.zzbL().zzd(zzmo.zzDf);
        this.zzyH = ((Boolean) zzbs.zzbL().zzd(zzmo.zzDh)).booleanValue();
        setName("ContentFetchTask");
    }

    private final zzhd zza(@Nullable View view, zzgt zzgt) {
        boolean z;
        if (view == null) {
            return new zzhd(this, 0, 0);
        }
        Context context = zzbs.zzbC().getContext();
        if (context != null) {
            String str = (String) view.getTag(context.getResources().getIdentifier((String) zzbs.zzbL().zzd(zzmo.zzDe), "id", context.getPackageName()));
            if (!TextUtils.isEmpty(this.zzyG) && str != null && str.equals(this.zzyG)) {
                return new zzhd(this, 0, 0);
            }
        }
        boolean globalVisibleRect = view.getGlobalVisibleRect(new Rect());
        if ((view instanceof TextView) && !(view instanceof EditText)) {
            CharSequence text = ((TextView) view).getText();
            if (TextUtils.isEmpty(text)) {
                return new zzhd(this, 0, 0);
            }
            zzgt.zzb(text.toString(), globalVisibleRect, view.getX(), view.getY(), (float) view.getWidth(), (float) view.getHeight());
            return new zzhd(this, 1, 0);
        } else if ((view instanceof WebView) && !(view instanceof zzaka)) {
            zzgt.zzcI();
            WebView webView = (WebView) view;
            if (!zzq.zzsc()) {
                z = false;
            } else {
                zzgt.zzcI();
                webView.post(new zzhb(this, zzgt, webView, globalVisibleRect));
                z = true;
            }
            return z ? new zzhd(this, 0, 1) : new zzhd(this, 0, 0);
        } else if (!(view instanceof ViewGroup)) {
            return new zzhd(this, 0, 0);
        } else {
            ViewGroup viewGroup = (ViewGroup) view;
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            while (i < viewGroup.getChildCount()) {
                zzhd zza = zza(viewGroup.getChildAt(i), zzgt);
                i3 += zza.zzyP;
                i++;
                i2 += zza.zzyQ;
            }
            return new zzhd(this, i3, i2);
        }
    }

    private static boolean zzcN() {
        try {
            Context context = zzbs.zzbC().getContext();
            if (context == null) {
                return false;
            }
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService("keyguard");
            if (activityManager == null || keyguardManager == null) {
                return false;
            }
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
            if (runningAppProcesses == null) {
                return false;
            }
            Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ActivityManager.RunningAppProcessInfo next = it.next();
                if (Process.myPid() == next.pid) {
                    if (next.importance == 100 && !keyguardManager.inKeyguardRestrictedInputMode()) {
                        PowerManager powerManager = (PowerManager) context.getSystemService("power");
                        if (powerManager == null ? false : powerManager.isScreenOn()) {
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (Throwable th) {
            zzbs.zzbD().zza(th, "ContentFetchTask.isInForeground");
            return false;
        }
    }

    private final void zzcP() {
        synchronized (this.mLock) {
            this.zzyx = true;
            zzafr.zzaC(new StringBuilder(42).append("ContentFetchThread: paused, mPause = ").append(this.zzyx).toString());
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0063, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0064, code lost:
        com.google.android.gms.internal.zzafr.zzb("Error in ContentFetchTask", r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x007a, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x007b, code lost:
        com.google.android.gms.internal.zzafr.zzb("Error in ContentFetchTask", r0);
        r4.zzyz.zza(r0, "ContentFetchTask.run");
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0063 A[ExcHandler: InterruptedException (r0v1 'e' java.lang.InterruptedException A[CUSTOM_DECLARE]), Splitter:B:0:0x0000] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r4 = this;
        L_0x0000:
            boolean r0 = zzcN()     // Catch:{ InterruptedException -> 0x0063, Throwable -> 0x007a }
            if (r0 == 0) goto L_0x0088
            com.google.android.gms.internal.zzgv r0 = com.google.android.gms.ads.internal.zzbs.zzbC()     // Catch:{ InterruptedException -> 0x0063, Throwable -> 0x007a }
            android.app.Activity r1 = r0.getActivity()     // Catch:{ InterruptedException -> 0x0063, Throwable -> 0x007a }
            if (r1 != 0) goto L_0x0034
            java.lang.String r0 = "ContentFetchThread: no activity. Sleeping."
            com.google.android.gms.internal.zzafr.zzaC(r0)     // Catch:{ InterruptedException -> 0x0063, Throwable -> 0x007a }
            r4.zzcP()     // Catch:{ InterruptedException -> 0x0063, Throwable -> 0x007a }
        L_0x0018:
            int r0 = r4.zzyA     // Catch:{ InterruptedException -> 0x0063, Throwable -> 0x007a }
            int r0 = r0 * 1000
            long r0 = (long) r0     // Catch:{ InterruptedException -> 0x0063, Throwable -> 0x007a }
            java.lang.Thread.sleep(r0)     // Catch:{ InterruptedException -> 0x0063, Throwable -> 0x007a }
        L_0x0020:
            java.lang.Object r1 = r4.mLock
            monitor-enter(r1)
        L_0x0023:
            boolean r0 = r4.zzyx     // Catch:{ all -> 0x0094 }
            if (r0 == 0) goto L_0x0091
            java.lang.String r0 = "ContentFetchTask: waiting"
            com.google.android.gms.internal.zzafr.zzaC(r0)     // Catch:{ InterruptedException -> 0x0032 }
            java.lang.Object r0 = r4.mLock     // Catch:{ InterruptedException -> 0x0032 }
            r0.wait()     // Catch:{ InterruptedException -> 0x0032 }
            goto L_0x0023
        L_0x0032:
            r0 = move-exception
            goto L_0x0023
        L_0x0034:
            if (r1 == 0) goto L_0x0018
            r0 = 0
            android.view.Window r2 = r1.getWindow()     // Catch:{ Throwable -> 0x006a, InterruptedException -> 0x0063 }
            if (r2 == 0) goto L_0x0056
            android.view.Window r2 = r1.getWindow()     // Catch:{ Throwable -> 0x006a, InterruptedException -> 0x0063 }
            android.view.View r2 = r2.getDecorView()     // Catch:{ Throwable -> 0x006a, InterruptedException -> 0x0063 }
            if (r2 == 0) goto L_0x0056
            android.view.Window r1 = r1.getWindow()     // Catch:{ Throwable -> 0x006a, InterruptedException -> 0x0063 }
            android.view.View r1 = r1.getDecorView()     // Catch:{ Throwable -> 0x006a, InterruptedException -> 0x0063 }
            r2 = 16908290(0x1020002, float:2.3877235E-38)
            android.view.View r0 = r1.findViewById(r2)     // Catch:{ Throwable -> 0x006a, InterruptedException -> 0x0063 }
        L_0x0056:
            if (r0 == 0) goto L_0x0018
            if (r0 == 0) goto L_0x0018
            com.google.android.gms.internal.zzha r1 = new com.google.android.gms.internal.zzha     // Catch:{ InterruptedException -> 0x0063, Throwable -> 0x007a }
            r1.<init>(r4, r0)     // Catch:{ InterruptedException -> 0x0063, Throwable -> 0x007a }
            r0.post(r1)     // Catch:{ InterruptedException -> 0x0063, Throwable -> 0x007a }
            goto L_0x0018
        L_0x0063:
            r0 = move-exception
            java.lang.String r1 = "Error in ContentFetchTask"
            com.google.android.gms.internal.zzafr.zzb(r1, r0)
            goto L_0x0020
        L_0x006a:
            r1 = move-exception
            com.google.android.gms.internal.zzafk r2 = com.google.android.gms.ads.internal.zzbs.zzbD()     // Catch:{ InterruptedException -> 0x0063, Throwable -> 0x007a }
            java.lang.String r3 = "ContentFetchTask.extractContent"
            r2.zza((java.lang.Throwable) r1, (java.lang.String) r3)     // Catch:{ InterruptedException -> 0x0063, Throwable -> 0x007a }
            java.lang.String r1 = "Failed getting root view of activity. Content not extracted."
            com.google.android.gms.internal.zzafr.zzaC(r1)     // Catch:{ InterruptedException -> 0x0063, Throwable -> 0x007a }
            goto L_0x0056
        L_0x007a:
            r0 = move-exception
            java.lang.String r1 = "Error in ContentFetchTask"
            com.google.android.gms.internal.zzafr.zzb(r1, r0)
            com.google.android.gms.internal.zzzl r1 = r4.zzyz
            java.lang.String r2 = "ContentFetchTask.run"
            r1.zza(r0, r2)
            goto L_0x0020
        L_0x0088:
            java.lang.String r0 = "ContentFetchTask: sleeping"
            com.google.android.gms.internal.zzafr.zzaC(r0)     // Catch:{ InterruptedException -> 0x0063, Throwable -> 0x007a }
            r4.zzcP()     // Catch:{ InterruptedException -> 0x0063, Throwable -> 0x007a }
            goto L_0x0018
        L_0x0091:
            monitor-exit(r1)     // Catch:{ all -> 0x0094 }
            goto L_0x0000
        L_0x0094:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0094 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgz.run():void");
    }

    public final void wakeup() {
        synchronized (this.mLock) {
            this.zzyx = false;
            this.mLock.notifyAll();
            zzafr.zzaC("ContentFetchThread: wakeup");
        }
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzgt zzgt, WebView webView, String str, boolean z) {
        zzgt.zzcH();
        try {
            if (!TextUtils.isEmpty(str)) {
                String optString = new JSONObject(str).optString(ContentType.TYPE_TEXT);
                if (this.zzyH || TextUtils.isEmpty(webView.getTitle())) {
                    zzgt.zza(optString, z, webView.getX(), webView.getY(), (float) webView.getWidth(), (float) webView.getHeight());
                } else {
                    String valueOf = String.valueOf(webView.getTitle());
                    zzgt.zza(new StringBuilder(String.valueOf(valueOf).length() + 1 + String.valueOf(optString).length()).append(valueOf).append("\n").append(optString).toString(), z, webView.getX(), webView.getY(), (float) webView.getWidth(), (float) webView.getHeight());
                }
            }
            if (zzgt.zzcC()) {
                this.zzyy.zzb(zzgt);
            }
        } catch (JSONException e) {
            zzafr.zzaC("Json string may be malformed.");
        } catch (Throwable th) {
            zzafr.zza("Failed to get webview content.", th);
            this.zzyz.zza(th, "ContentFetchTask.processWebViewContent");
        }
    }

    public final void zzcM() {
        synchronized (this.mLock) {
            if (this.mStarted) {
                zzafr.zzaC("Content hash thread already started, quiting...");
                return;
            }
            this.mStarted = true;
            start();
        }
    }

    public final zzgt zzcO() {
        return this.zzyy.zzcL();
    }

    public final boolean zzcQ() {
        return this.zzyx;
    }

    /* access modifiers changed from: package-private */
    public final void zzf(View view) {
        try {
            zzgt zzgt = new zzgt(this.zzxW, this.zzyB, this.zzxY, this.zzyC, this.zzyD, this.zzyE, this.zzyF);
            zzhd zza = zza(view, zzgt);
            zzgt.zzcJ();
            if (zza.zzyP != 0 || zza.zzyQ != 0) {
                if (zza.zzyQ != 0 || zzgt.zzcK() != 0) {
                    if (zza.zzyQ != 0 || !this.zzyy.zza(zzgt)) {
                        this.zzyy.zzc(zzgt);
                    }
                }
            }
        } catch (Exception e) {
            zzafr.zzb("Exception in fetchContentOnUIThread", e);
            this.zzyz.zza(e, "ContentFetchTask.fetchContent");
        }
    }
}

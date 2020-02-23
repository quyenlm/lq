package com.google.android.gms.internal;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import com.google.android.gms.ads.internal.zzbs;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzzn
public final class zzfi implements ViewTreeObserver.OnGlobalLayoutListener, ViewTreeObserver.OnScrollChangedListener {
    private final Context mApplicationContext;
    private Object mLock = new Object();
    private boolean zzuV = false;
    private final WeakReference<zzaff> zzwN;
    private WeakReference<ViewTreeObserver> zzwO;
    private final zzgs zzwP;
    protected final zzfg zzwQ;
    private final WindowManager zzwR;
    private final PowerManager zzwS;
    private final KeyguardManager zzwT;
    private final DisplayMetrics zzwU;
    @Nullable
    private zzfp zzwV;
    private boolean zzwW;
    private boolean zzwX = false;
    private boolean zzwY;
    private boolean zzwZ;
    private zzair zzwx;
    private boolean zzxa;
    @Nullable
    private BroadcastReceiver zzxb;
    private final HashSet<Object> zzxc = new HashSet<>();
    private final HashSet<zzgd> zzxd = new HashSet<>();
    private final Rect zzxe = new Rect();
    private final zzfl zzxf;
    private float zzxg;

    public zzfi(Context context, zziv zziv, zzaff zzaff, zzaje zzaje, zzgs zzgs) {
        this.zzwN = new WeakReference<>(zzaff);
        this.zzwP = zzgs;
        this.zzwO = new WeakReference<>((Object) null);
        this.zzwY = true;
        this.zzxa = false;
        this.zzwx = new zzair(200);
        this.zzwQ = new zzfg(UUID.randomUUID().toString(), zzaje, zziv.zzAs, zzaff.zzXL, zzaff.zzcn(), zziv.zzAv);
        this.zzwR = (WindowManager) context.getSystemService("window");
        this.zzwS = (PowerManager) context.getApplicationContext().getSystemService("power");
        this.zzwT = (KeyguardManager) context.getSystemService("keyguard");
        this.mApplicationContext = context;
        this.zzxf = new zzfl(this, new Handler());
        this.mApplicationContext.getContentResolver().registerContentObserver(Settings.System.CONTENT_URI, true, this.zzxf);
        this.zzwU = context.getResources().getDisplayMetrics();
        Display defaultDisplay = this.zzwR.getDefaultDisplay();
        this.zzxe.right = defaultDisplay.getWidth();
        this.zzxe.bottom = defaultDisplay.getHeight();
        zzcp();
    }

    private final boolean isScreenOn() {
        return Build.VERSION.SDK_INT >= 20 ? this.zzwS.isInteractive() : this.zzwS.isScreenOn();
    }

    private static int zza(int i, DisplayMetrics displayMetrics) {
        return (int) (((float) i) / displayMetrics.density);
    }

    private final JSONObject zza(@Nullable View view, @Nullable Boolean bool) throws JSONException {
        if (view == null) {
            return zzcu().put("isAttachedToWindow", false).put("isScreenOn", isScreenOn()).put("isVisible", false);
        }
        boolean isAttachedToWindow = zzbs.zzbB().isAttachedToWindow(view);
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        try {
            view.getLocationOnScreen(iArr);
            view.getLocationInWindow(iArr2);
        } catch (Exception e) {
            zzafr.zzb("Failure getting view location.", e);
        }
        Rect rect = new Rect();
        rect.left = iArr[0];
        rect.top = iArr[1];
        rect.right = rect.left + view.getWidth();
        rect.bottom = rect.top + view.getHeight();
        Rect rect2 = new Rect();
        boolean globalVisibleRect = view.getGlobalVisibleRect(rect2, (Point) null);
        Rect rect3 = new Rect();
        boolean localVisibleRect = view.getLocalVisibleRect(rect3);
        Rect rect4 = new Rect();
        view.getHitRect(rect4);
        JSONObject zzcu = zzcu();
        zzcu.put("windowVisibility", view.getWindowVisibility()).put("isAttachedToWindow", isAttachedToWindow).put("viewBox", new JSONObject().put("top", zza(this.zzxe.top, this.zzwU)).put("bottom", zza(this.zzxe.bottom, this.zzwU)).put("left", zza(this.zzxe.left, this.zzwU)).put("right", zza(this.zzxe.right, this.zzwU))).put("adBox", new JSONObject().put("top", zza(rect.top, this.zzwU)).put("bottom", zza(rect.bottom, this.zzwU)).put("left", zza(rect.left, this.zzwU)).put("right", zza(rect.right, this.zzwU))).put("globalVisibleBox", new JSONObject().put("top", zza(rect2.top, this.zzwU)).put("bottom", zza(rect2.bottom, this.zzwU)).put("left", zza(rect2.left, this.zzwU)).put("right", zza(rect2.right, this.zzwU))).put("globalVisibleBoxVisible", globalVisibleRect).put("localVisibleBox", new JSONObject().put("top", zza(rect3.top, this.zzwU)).put("bottom", zza(rect3.bottom, this.zzwU)).put("left", zza(rect3.left, this.zzwU)).put("right", zza(rect3.right, this.zzwU))).put("localVisibleBoxVisible", localVisibleRect).put("hitBox", new JSONObject().put("top", zza(rect4.top, this.zzwU)).put("bottom", zza(rect4.bottom, this.zzwU)).put("left", zza(rect4.left, this.zzwU)).put("right", zza(rect4.right, this.zzwU))).put("screenDensity", (double) this.zzwU.density);
        if (bool == null) {
            bool = Boolean.valueOf(zzbs.zzbz().zza(view, this.zzwS, this.zzwT));
        }
        zzcu.put("isVisible", bool.booleanValue());
        return zzcu;
    }

    private static JSONObject zza(JSONObject jSONObject) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject2 = new JSONObject();
        jSONArray.put(jSONObject);
        jSONObject2.put("units", jSONArray);
        return jSONObject2;
    }

    private final void zza(JSONObject jSONObject, boolean z) {
        try {
            JSONObject zza = zza(jSONObject);
            ArrayList arrayList = new ArrayList(this.zzxd);
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((zzgd) obj).zzb(zza, z);
            }
        } catch (Throwable th) {
            zzafr.zzb("Skipping active view message.", th);
        }
    }

    private final void zzcr() {
        if (this.zzwV != null) {
            this.zzwV.zza(this);
        }
    }

    private final void zzct() {
        ViewTreeObserver viewTreeObserver = (ViewTreeObserver) this.zzwO.get();
        if (viewTreeObserver != null && viewTreeObserver.isAlive()) {
            viewTreeObserver.removeOnScrollChangedListener(this);
            viewTreeObserver.removeGlobalOnLayoutListener(this);
        }
    }

    private final JSONObject zzcu() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject put = jSONObject.put("afmaVersion", this.zzwQ.zzck()).put("activeViewJSON", this.zzwQ.zzcl()).put("timestamp", zzbs.zzbF().elapsedRealtime()).put("adFormat", this.zzwQ.zzcj()).put("hashCode", this.zzwQ.zzcm()).put("isMraid", this.zzwQ.zzcn()).put("isStopped", this.zzwX).put("isPaused", this.zzuV).put("isNative", this.zzwQ.zzco()).put("isScreenOn", isScreenOn());
        zzbs.zzbz();
        JSONObject put2 = put.put("appMuted", zzagz.zzbh());
        zzbs.zzbz();
        put2.put("appVolume", (double) zzagz.zzbf()).put("deviceVolume", (double) this.zzxg);
        return jSONObject;
    }

    public final void onGlobalLayout() {
        zzg(2);
    }

    public final void onScrollChanged() {
        zzg(1);
    }

    public final void pause() {
        synchronized (this.mLock) {
            this.zzuV = true;
            zzg(3);
        }
    }

    public final void resume() {
        synchronized (this.mLock) {
            this.zzuV = false;
            zzg(3);
        }
    }

    public final void stop() {
        synchronized (this.mLock) {
            this.zzwX = true;
            zzg(3);
        }
    }

    public final void zza(zzfp zzfp) {
        synchronized (this.mLock) {
            this.zzwV = zzfp;
        }
    }

    public final void zza(zzgd zzgd) {
        if (this.zzxd.isEmpty()) {
            synchronized (this.mLock) {
                if (this.zzxb == null) {
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("android.intent.action.SCREEN_ON");
                    intentFilter.addAction("android.intent.action.SCREEN_OFF");
                    this.zzxb = new zzfj(this);
                    this.mApplicationContext.registerReceiver(this.zzxb, intentFilter);
                }
            }
            zzg(3);
        }
        this.zzxd.add(zzgd);
        try {
            zzgd.zzb(zza(zza(this.zzwP.zzcv(), (Boolean) null)), false);
        } catch (JSONException e) {
            zzafr.zzb("Skipping measurement update for new client.", e);
        }
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzgd zzgd, Map<String, String> map) {
        String valueOf = String.valueOf(this.zzwQ.zzcm());
        zzafr.zzaC(valueOf.length() != 0 ? "Received request to untrack: ".concat(valueOf) : new String("Received request to untrack: "));
        zzb(zzgd);
    }

    /* access modifiers changed from: package-private */
    public final void zzb(zzaka zzaka, Map<String, String> map) {
        zzg(3);
    }

    public final void zzb(zzgd zzgd) {
        this.zzxd.remove(zzgd);
        zzgd.zzcz();
        if (this.zzxd.isEmpty()) {
            synchronized (this.mLock) {
                zzct();
                synchronized (this.mLock) {
                    if (this.zzxb != null) {
                        try {
                            this.mApplicationContext.unregisterReceiver(this.zzxb);
                        } catch (IllegalStateException e) {
                            zzafr.zzb("Failed trying to unregister the receiver", e);
                        } catch (Exception e2) {
                            zzbs.zzbD().zza((Throwable) e2, "ActiveViewUnit.stopScreenStatusMonitoring");
                        }
                        this.zzxb = null;
                    }
                }
                this.mApplicationContext.getContentResolver().unregisterContentObserver(this.zzxf);
                this.zzwY = false;
                zzcr();
                ArrayList arrayList = new ArrayList(this.zzxd);
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    zzb((zzgd) obj);
                }
            }
            return;
        }
        return;
    }

    /* access modifiers changed from: package-private */
    public final boolean zzb(@Nullable Map<String, String> map) {
        if (map == null) {
            return false;
        }
        String str = map.get("hashCode");
        return !TextUtils.isEmpty(str) && str.equals(this.zzwQ.zzcm());
    }

    /* access modifiers changed from: package-private */
    public final void zzc(Map<String, String> map) {
        if (map.containsKey("isVisible")) {
            if (!"1".equals(map.get("isVisible"))) {
                "true".equals(map.get("isVisible"));
            }
            Iterator<Object> it = this.zzxc.iterator();
            while (it.hasNext()) {
                it.next();
            }
        }
    }

    public final void zzcp() {
        zzbs.zzbz();
        this.zzxg = zzagz.zzM(this.mApplicationContext);
    }

    public final void zzcq() {
        synchronized (this.mLock) {
            if (this.zzwY) {
                this.zzwZ = true;
                try {
                    JSONObject zzcu = zzcu();
                    zzcu.put("doneReasonCode", "u");
                    zza(zzcu, true);
                } catch (JSONException e) {
                    zzafr.zzb("JSON failure while processing active view data.", e);
                } catch (RuntimeException e2) {
                    zzafr.zzb("Failure while processing active view data.", e2);
                }
                String valueOf = String.valueOf(this.zzwQ.zzcm());
                zzafr.zzaC(valueOf.length() != 0 ? "Untracking ad unit: ".concat(valueOf) : new String("Untracking ad unit: "));
            }
        }
    }

    public final boolean zzcs() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzwY;
        }
        return z;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x00cb, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00cc, code lost:
        com.google.android.gms.internal.zzafr.zza("Active view update failed.", r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:?, code lost:
        return;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzg(int r8) {
        /*
            r7 = this;
            r2 = 0
            r1 = 1
            java.lang.Object r4 = r7.mLock
            monitor-enter(r4)
            java.util.HashSet<com.google.android.gms.internal.zzgd> r0 = r7.zzxd     // Catch:{ all -> 0x005d }
            java.util.Iterator r3 = r0.iterator()     // Catch:{ all -> 0x005d }
        L_0x000b:
            boolean r0 = r3.hasNext()     // Catch:{ all -> 0x005d }
            if (r0 == 0) goto L_0x0026
            java.lang.Object r0 = r3.next()     // Catch:{ all -> 0x005d }
            com.google.android.gms.internal.zzgd r0 = (com.google.android.gms.internal.zzgd) r0     // Catch:{ all -> 0x005d }
            boolean r0 = r0.zzcy()     // Catch:{ all -> 0x005d }
            if (r0 == 0) goto L_0x000b
            r0 = r1
        L_0x001e:
            if (r0 == 0) goto L_0x0024
            boolean r0 = r7.zzwY     // Catch:{ all -> 0x005d }
            if (r0 != 0) goto L_0x0028
        L_0x0024:
            monitor-exit(r4)     // Catch:{ all -> 0x005d }
        L_0x0025:
            return
        L_0x0026:
            r0 = r2
            goto L_0x001e
        L_0x0028:
            com.google.android.gms.internal.zzgs r0 = r7.zzwP     // Catch:{ all -> 0x005d }
            android.view.View r5 = r0.zzcv()     // Catch:{ all -> 0x005d }
            if (r5 == 0) goto L_0x0060
            com.google.android.gms.internal.zzagz r0 = com.google.android.gms.ads.internal.zzbs.zzbz()     // Catch:{ all -> 0x005d }
            android.os.PowerManager r3 = r7.zzwS     // Catch:{ all -> 0x005d }
            android.app.KeyguardManager r6 = r7.zzwT     // Catch:{ all -> 0x005d }
            boolean r0 = r0.zza((android.view.View) r5, (android.os.PowerManager) r3, (android.app.KeyguardManager) r6)     // Catch:{ all -> 0x005d }
            if (r0 == 0) goto L_0x0060
            r3 = r1
        L_0x003f:
            if (r5 == 0) goto L_0x0062
            if (r3 == 0) goto L_0x0062
            android.graphics.Rect r0 = new android.graphics.Rect     // Catch:{ all -> 0x005d }
            r0.<init>()     // Catch:{ all -> 0x005d }
            r6 = 0
            boolean r0 = r5.getGlobalVisibleRect(r0, r6)     // Catch:{ all -> 0x005d }
            if (r0 == 0) goto L_0x0062
            r0 = r1
        L_0x0050:
            com.google.android.gms.internal.zzgs r2 = r7.zzwP     // Catch:{ all -> 0x005d }
            boolean r2 = r2.zzcw()     // Catch:{ all -> 0x005d }
            if (r2 == 0) goto L_0x0064
            r7.zzcq()     // Catch:{ all -> 0x005d }
            monitor-exit(r4)     // Catch:{ all -> 0x005d }
            goto L_0x0025
        L_0x005d:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x005d }
            throw r0
        L_0x0060:
            r3 = r2
            goto L_0x003f
        L_0x0062:
            r0 = r2
            goto L_0x0050
        L_0x0064:
            if (r8 != r1) goto L_0x0074
            com.google.android.gms.internal.zzair r2 = r7.zzwx     // Catch:{ all -> 0x005d }
            boolean r2 = r2.tryAcquire()     // Catch:{ all -> 0x005d }
            if (r2 != 0) goto L_0x0074
            boolean r2 = r7.zzxa     // Catch:{ all -> 0x005d }
            if (r0 != r2) goto L_0x0074
            monitor-exit(r4)     // Catch:{ all -> 0x005d }
            goto L_0x0025
        L_0x0074:
            if (r0 != 0) goto L_0x007e
            boolean r2 = r7.zzxa     // Catch:{ all -> 0x005d }
            if (r2 != 0) goto L_0x007e
            if (r8 != r1) goto L_0x007e
            monitor-exit(r4)     // Catch:{ all -> 0x005d }
            goto L_0x0025
        L_0x007e:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r3)     // Catch:{ JSONException -> 0x00d2, RuntimeException -> 0x00cb }
            org.json.JSONObject r1 = r7.zza((android.view.View) r5, (java.lang.Boolean) r1)     // Catch:{ JSONException -> 0x00d2, RuntimeException -> 0x00cb }
            r2 = 0
            r7.zza((org.json.JSONObject) r1, (boolean) r2)     // Catch:{ JSONException -> 0x00d2, RuntimeException -> 0x00cb }
            r7.zzxa = r0     // Catch:{ JSONException -> 0x00d2, RuntimeException -> 0x00cb }
        L_0x008c:
            com.google.android.gms.internal.zzgs r0 = r7.zzwP     // Catch:{ all -> 0x005d }
            com.google.android.gms.internal.zzgs r0 = r0.zzcx()     // Catch:{ all -> 0x005d }
            android.view.View r1 = r0.zzcv()     // Catch:{ all -> 0x005d }
            if (r1 == 0) goto L_0x00c5
            java.lang.ref.WeakReference<android.view.ViewTreeObserver> r0 = r7.zzwO     // Catch:{ all -> 0x005d }
            java.lang.Object r0 = r0.get()     // Catch:{ all -> 0x005d }
            android.view.ViewTreeObserver r0 = (android.view.ViewTreeObserver) r0     // Catch:{ all -> 0x005d }
            android.view.ViewTreeObserver r1 = r1.getViewTreeObserver()     // Catch:{ all -> 0x005d }
            if (r1 == r0) goto L_0x00c5
            r7.zzct()     // Catch:{ all -> 0x005d }
            boolean r2 = r7.zzwW     // Catch:{ all -> 0x005d }
            if (r2 == 0) goto L_0x00b5
            if (r0 == 0) goto L_0x00be
            boolean r0 = r0.isAlive()     // Catch:{ all -> 0x005d }
            if (r0 == 0) goto L_0x00be
        L_0x00b5:
            r0 = 1
            r7.zzwW = r0     // Catch:{ all -> 0x005d }
            r1.addOnScrollChangedListener(r7)     // Catch:{ all -> 0x005d }
            r1.addOnGlobalLayoutListener(r7)     // Catch:{ all -> 0x005d }
        L_0x00be:
            java.lang.ref.WeakReference r0 = new java.lang.ref.WeakReference     // Catch:{ all -> 0x005d }
            r0.<init>(r1)     // Catch:{ all -> 0x005d }
            r7.zzwO = r0     // Catch:{ all -> 0x005d }
        L_0x00c5:
            r7.zzcr()     // Catch:{ all -> 0x005d }
            monitor-exit(r4)     // Catch:{ all -> 0x005d }
            goto L_0x0025
        L_0x00cb:
            r0 = move-exception
        L_0x00cc:
            java.lang.String r1 = "Active view update failed."
            com.google.android.gms.internal.zzafr.zza(r1, r0)     // Catch:{ all -> 0x005d }
            goto L_0x008c
        L_0x00d2:
            r0 = move-exception
            goto L_0x00cc
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzfi.zzg(int):void");
    }
}

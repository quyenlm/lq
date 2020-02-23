package com.google.android.gms.internal;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import com.google.android.gms.ads.internal.zzbs;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class zzcq implements zzcp {
    protected MotionEvent zzpP;
    protected LinkedList<MotionEvent> zzpQ = new LinkedList<>();
    protected long zzpR = 0;
    protected long zzpS = 0;
    protected long zzpT = 0;
    protected long zzpU = 0;
    protected long zzpV = 0;
    protected long zzpW = 0;
    protected long zzpX = 0;
    protected double zzpY;
    private double zzpZ;
    private double zzqa;
    protected float zzqb;
    protected float zzqc;
    protected float zzqd;
    protected float zzqe;
    private boolean zzqf = false;
    protected boolean zzqg = false;
    protected DisplayMetrics zzqh;

    protected zzcq(Context context) {
        try {
            zzbv.zzw();
            this.zzqh = context.getResources().getDisplayMetrics();
        } catch (Throwable th) {
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0025 A[Catch:{ UnsupportedEncodingException | NoSuchAlgorithmException -> 0x0055, Throwable -> 0x005c }] */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x002e A[Catch:{ UnsupportedEncodingException | NoSuchAlgorithmException -> 0x0055, Throwable -> 0x005c }] */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0034 A[Catch:{ UnsupportedEncodingException | NoSuchAlgorithmException -> 0x0055, Throwable -> 0x005c }] */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0015 A[SYNTHETIC, Splitter:B:7:0x0015] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.String zza(android.content.Context r6, java.lang.String r7, boolean r8, android.view.View r9, byte[] r10) {
        /*
            r5 = this;
            r2 = 0
            r1 = 1
            r3 = 0
            if (r10 == 0) goto L_0x002c
            int r0 = r10.length
            if (r0 <= 0) goto L_0x002c
            com.google.android.gms.internal.zzau r0 = new com.google.android.gms.internal.zzau     // Catch:{ ado -> 0x002b }
            r0.<init>()     // Catch:{ ado -> 0x002b }
            com.google.android.gms.internal.adp r0 = com.google.android.gms.internal.adp.zza(r0, r10)     // Catch:{ ado -> 0x002b }
            com.google.android.gms.internal.zzau r0 = (com.google.android.gms.internal.zzau) r0     // Catch:{ ado -> 0x002b }
        L_0x0013:
            if (r8 == 0) goto L_0x002e
            com.google.android.gms.internal.zzax r0 = r5.zza((android.content.Context) r6, (android.view.View) r9)     // Catch:{ NoSuchAlgorithmException -> 0x0055, UnsupportedEncodingException -> 0x0063, Throwable -> 0x005c }
            r3 = 1
            r5.zzqf = r3     // Catch:{ NoSuchAlgorithmException -> 0x0055, UnsupportedEncodingException -> 0x0063, Throwable -> 0x005c }
            r3 = r0
        L_0x001d:
            if (r3 == 0) goto L_0x0025
            int r0 = r3.zzLV()     // Catch:{ NoSuchAlgorithmException -> 0x0055, UnsupportedEncodingException -> 0x0063, Throwable -> 0x005c }
            if (r0 != 0) goto L_0x0034
        L_0x0025:
            r0 = 5
            java.lang.String r0 = java.lang.Integer.toString(r0)     // Catch:{ NoSuchAlgorithmException -> 0x0055, UnsupportedEncodingException -> 0x0063, Throwable -> 0x005c }
        L_0x002a:
            return r0
        L_0x002b:
            r0 = move-exception
        L_0x002c:
            r0 = r3
            goto L_0x0013
        L_0x002e:
            com.google.android.gms.internal.zzax r0 = r5.zza((android.content.Context) r6, (com.google.android.gms.internal.zzau) r0)     // Catch:{ NoSuchAlgorithmException -> 0x0055, UnsupportedEncodingException -> 0x0063, Throwable -> 0x005c }
            r3 = r0
            goto L_0x001d
        L_0x0034:
            com.google.android.gms.internal.zzme<java.lang.Boolean> r0 = com.google.android.gms.internal.zzmo.zzEO     // Catch:{ NoSuchAlgorithmException -> 0x0055, UnsupportedEncodingException -> 0x0063, Throwable -> 0x005c }
            com.google.android.gms.internal.zzmm r4 = com.google.android.gms.ads.internal.zzbs.zzbL()     // Catch:{ NoSuchAlgorithmException -> 0x0055, UnsupportedEncodingException -> 0x0063, Throwable -> 0x005c }
            java.lang.Object r0 = r4.zzd(r0)     // Catch:{ NoSuchAlgorithmException -> 0x0055, UnsupportedEncodingException -> 0x0063, Throwable -> 0x005c }
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ NoSuchAlgorithmException -> 0x0055, UnsupportedEncodingException -> 0x0063, Throwable -> 0x005c }
            boolean r0 = r0.booleanValue()     // Catch:{ NoSuchAlgorithmException -> 0x0055, UnsupportedEncodingException -> 0x0063, Throwable -> 0x005c }
            if (r0 == 0) goto L_0x0048
            if (r8 == 0) goto L_0x0051
        L_0x0048:
            r0 = r1
        L_0x0049:
            if (r0 != 0) goto L_0x0053
            r0 = r1
        L_0x004c:
            java.lang.String r0 = com.google.android.gms.internal.zzbv.zza((com.google.android.gms.internal.zzax) r3, (java.lang.String) r7, (boolean) r0)     // Catch:{ NoSuchAlgorithmException -> 0x0055, UnsupportedEncodingException -> 0x0063, Throwable -> 0x005c }
            goto L_0x002a
        L_0x0051:
            r0 = r2
            goto L_0x0049
        L_0x0053:
            r0 = r2
            goto L_0x004c
        L_0x0055:
            r0 = move-exception
        L_0x0056:
            r0 = 7
            java.lang.String r0 = java.lang.Integer.toString(r0)
            goto L_0x002a
        L_0x005c:
            r0 = move-exception
            r0 = 3
            java.lang.String r0 = java.lang.Integer.toString(r0)
            goto L_0x002a
        L_0x0063:
            r0 = move-exception
            goto L_0x0056
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcq.zza(android.content.Context, java.lang.String, boolean, android.view.View, byte[]):java.lang.String");
    }

    /* access modifiers changed from: protected */
    public abstract long zza(StackTraceElement[] stackTraceElementArr) throws zzcy;

    /* access modifiers changed from: protected */
    public abstract zzax zza(Context context, View view);

    /* access modifiers changed from: protected */
    public abstract zzax zza(Context context, zzau zzau);

    public final String zza(Context context) {
        if (zzdg.zzS()) {
            if (((Boolean) zzbs.zzbL().zzd(zzmo.zzFb)).booleanValue()) {
                throw new IllegalStateException("The caller must not be called from the UI thread.");
            }
        }
        return zza(context, (String) null, false, (View) null, (byte[]) null);
    }

    public final String zza(Context context, String str) {
        return zza(context, str, (View) null);
    }

    public final String zza(Context context, String str, View view) {
        return zza(context, str, true, view, (byte[]) null);
    }

    public final String zza(Context context, byte[] bArr) {
        if (zzdg.zzS()) {
            if (((Boolean) zzbs.zzbL().zzd(zzmo.zzFb)).booleanValue()) {
                throw new IllegalStateException("The caller must not be called from the UI thread.");
            }
        }
        return zza(context, (String) null, false, (View) null, bArr);
    }

    public final void zza(int i, int i2, int i3) {
        if (this.zzpP != null) {
            this.zzpP.recycle();
        }
        if (this.zzqh != null) {
            this.zzpP = MotionEvent.obtain(0, (long) i3, 1, ((float) i) * this.zzqh.density, ((float) i2) * this.zzqh.density, 0.0f, 0.0f, 0, 0.0f, 0.0f, 0, 0);
        } else {
            this.zzpP = null;
        }
        this.zzqg = false;
    }

    public final void zza(MotionEvent motionEvent) {
        if (this.zzqf) {
            this.zzpU = 0;
            this.zzpT = 0;
            this.zzpS = 0;
            this.zzpR = 0;
            this.zzpV = 0;
            this.zzpX = 0;
            this.zzpW = 0;
            Iterator it = this.zzpQ.iterator();
            while (it.hasNext()) {
                ((MotionEvent) it.next()).recycle();
            }
            this.zzpQ.clear();
            this.zzpP = null;
            this.zzqf = false;
        }
        if (((Boolean) zzbs.zzbL().zzd(zzmo.zzEU)).booleanValue()) {
            switch (motionEvent.getAction()) {
                case 0:
                    this.zzpY = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
                    this.zzpZ = (double) motionEvent.getRawX();
                    this.zzqa = (double) motionEvent.getRawY();
                    break;
                case 1:
                case 2:
                    double rawX = (double) motionEvent.getRawX();
                    double rawY = (double) motionEvent.getRawY();
                    double d = rawX - this.zzpZ;
                    double d2 = rawY - this.zzqa;
                    this.zzpY = Math.sqrt((d * d) + (d2 * d2)) + this.zzpY;
                    this.zzpZ = rawX;
                    this.zzqa = rawY;
                    break;
            }
        }
        switch (motionEvent.getAction()) {
            case 0:
                if (((Boolean) zzbs.zzbL().zzd(zzmo.zzEV)).booleanValue()) {
                    this.zzqb = motionEvent.getX();
                    this.zzqc = motionEvent.getY();
                    this.zzqd = motionEvent.getRawX();
                    this.zzqe = motionEvent.getRawY();
                }
                this.zzpR++;
                break;
            case 1:
                this.zzpP = MotionEvent.obtain(motionEvent);
                this.zzpQ.add(this.zzpP);
                if (this.zzpQ.size() > 6) {
                    this.zzpQ.remove().recycle();
                }
                this.zzpT++;
                try {
                    this.zzpV = zza(new Throwable().getStackTrace());
                    break;
                } catch (zzcy e) {
                    break;
                }
            case 2:
                this.zzpS += (long) (motionEvent.getHistorySize() + 1);
                try {
                    zzdf zzb = zzb(motionEvent);
                    if ((zzb == null || zzb.zzcd == null || zzb.zzrd == null) ? false : true) {
                        this.zzpW += zzb.zzcd.longValue() + zzb.zzrd.longValue();
                    }
                    if ((this.zzqh == null || zzb == null || zzb.zzcb == null || zzb.zzre == null) ? false : true) {
                        this.zzpX = zzb.zzre.longValue() + zzb.zzcb.longValue() + this.zzpX;
                        break;
                    }
                } catch (zzcy e2) {
                    break;
                }
                break;
            case 3:
                this.zzpU++;
                break;
        }
        this.zzqg = true;
    }

    /* access modifiers changed from: protected */
    public abstract zzdf zzb(MotionEvent motionEvent) throws zzcy;
}

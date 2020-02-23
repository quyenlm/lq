package com.google.android.gms.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.view.View;
import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.common.zze;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzzn
public final class zzaeg implements zzaet {
    private final Context mContext;
    /* access modifiers changed from: private */
    public final Object mLock;
    private final zzaeq zzVu;
    /* access modifiers changed from: private */
    public final adt zzWX;
    private final LinkedHashMap<String, aeb> zzWY;
    private final zzael zzWZ;
    @VisibleForTesting
    boolean zzXa;
    private HashSet<String> zzXb;
    private boolean zzXc;
    private boolean zzXd;
    private boolean zzXe;

    public zzaeg(Context context, zzaje zzaje, zzaai zzaai) {
        this(context, zzaje, zzaai, new zzael());
    }

    @VisibleForTesting
    private zzaeg(Context context, zzaje zzaje, zzaai zzaai, zzael zzael) {
        this.mLock = new Object();
        this.zzXb = new HashSet<>();
        this.zzXc = false;
        this.zzXd = false;
        this.zzXe = false;
        zzbo.zzb(zzaai.zzTJ, (Object) "SafeBrowsing config is not present.");
        this.mContext = context.getApplicationContext() != null ? context.getApplicationContext() : context;
        this.zzWY = new LinkedHashMap<>();
        this.zzWZ = zzael;
        this.zzVu = zzaai.zzTJ;
        for (String lowerCase : this.zzVu.zzXv) {
            this.zzXb.add(lowerCase.toLowerCase(Locale.ENGLISH));
        }
        this.zzXb.remove("cookie".toLowerCase(Locale.ENGLISH));
        adt adt = new adt();
        adt.zzcsJ = 8;
        adt.url = zzaai.zzPi;
        adt.zzcsL = zzaai.zzPi;
        adt.zzcsN = new adu();
        adt.zzcsN.zzXr = this.zzVu.zzXr;
        aec aec = new aec();
        aec.zzctu = zzaje.zzaP;
        zze.zzoW();
        long zzau = (long) zze.zzau(this.mContext);
        if (zzau > 0) {
            aec.zzctv = Long.valueOf(zzau);
        }
        adt.zzcsX = aec;
        this.zzWX = adt;
    }

    @Nullable
    private final aeb zzaB(String str) {
        aeb aeb;
        synchronized (this.mLock) {
            aeb = this.zzWY.get(str);
        }
        return aeb;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public final void send() {
        boolean z = true;
        if ((!this.zzXa || !this.zzVu.zzXx) && ((!this.zzXe || !this.zzVu.zzXw) && (this.zzXa || !this.zzVu.zzXu))) {
            z = false;
        }
        if (z) {
            synchronized (this.mLock) {
                this.zzWX.zzcsO = new aeb[this.zzWY.size()];
                this.zzWY.values().toArray(this.zzWX.zzcsO);
                if (zzaes.isEnabled()) {
                    String valueOf = String.valueOf(this.zzWX.url);
                    String valueOf2 = String.valueOf(this.zzWX.zzcsP);
                    StringBuilder sb = new StringBuilder(new StringBuilder(String.valueOf(valueOf).length() + 53 + String.valueOf(valueOf2).length()).append("Sending SB report\n  url: ").append(valueOf).append("\n  clickUrl: ").append(valueOf2).append("\n  resources: \n").toString());
                    for (aeb aeb : this.zzWX.zzcsO) {
                        sb.append("    [");
                        sb.append(aeb.zzctt.length);
                        sb.append("] ");
                        sb.append(aeb.url);
                    }
                    zzaes.zzaC(sb.toString());
                }
                zzajm<String> zza = new zzaie(this.mContext).zza(1, this.zzVu.zzXs, (Map<String, String>) null, adp.zzc(this.zzWX));
                if (zzaes.isEnabled()) {
                    zza.zzc(new zzaej(this));
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        return;
     */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(java.lang.String r9, java.util.Map<java.lang.String, java.lang.String> r10, int r11) {
        /*
            r8 = this;
            r1 = 3
            java.lang.Object r2 = r8.mLock
            monitor-enter(r2)
            if (r11 != r1) goto L_0x0009
            r0 = 1
            r8.zzXe = r0     // Catch:{ all -> 0x00ac }
        L_0x0009:
            java.util.LinkedHashMap<java.lang.String, com.google.android.gms.internal.aeb> r0 = r8.zzWY     // Catch:{ all -> 0x00ac }
            boolean r0 = r0.containsKey(r9)     // Catch:{ all -> 0x00ac }
            if (r0 == 0) goto L_0x0023
            if (r11 != r1) goto L_0x0021
            java.util.LinkedHashMap<java.lang.String, com.google.android.gms.internal.aeb> r0 = r8.zzWY     // Catch:{ all -> 0x00ac }
            java.lang.Object r0 = r0.get(r9)     // Catch:{ all -> 0x00ac }
            com.google.android.gms.internal.aeb r0 = (com.google.android.gms.internal.aeb) r0     // Catch:{ all -> 0x00ac }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r11)     // Catch:{ all -> 0x00ac }
            r0.zzcts = r1     // Catch:{ all -> 0x00ac }
        L_0x0021:
            monitor-exit(r2)     // Catch:{ all -> 0x00ac }
        L_0x0022:
            return
        L_0x0023:
            com.google.android.gms.internal.aeb r3 = new com.google.android.gms.internal.aeb     // Catch:{ all -> 0x00ac }
            r3.<init>()     // Catch:{ all -> 0x00ac }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r11)     // Catch:{ all -> 0x00ac }
            r3.zzcts = r0     // Catch:{ all -> 0x00ac }
            java.util.LinkedHashMap<java.lang.String, com.google.android.gms.internal.aeb> r0 = r8.zzWY     // Catch:{ all -> 0x00ac }
            int r0 = r0.size()     // Catch:{ all -> 0x00ac }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ all -> 0x00ac }
            r3.zzbuM = r0     // Catch:{ all -> 0x00ac }
            r3.url = r9     // Catch:{ all -> 0x00ac }
            com.google.android.gms.internal.adw r0 = new com.google.android.gms.internal.adw     // Catch:{ all -> 0x00ac }
            r0.<init>()     // Catch:{ all -> 0x00ac }
            r3.zzctn = r0     // Catch:{ all -> 0x00ac }
            java.util.HashSet<java.lang.String> r0 = r8.zzXb     // Catch:{ all -> 0x00ac }
            int r0 = r0.size()     // Catch:{ all -> 0x00ac }
            if (r0 <= 0) goto L_0x00c2
            if (r10 == 0) goto L_0x00c2
            java.util.LinkedList r4 = new java.util.LinkedList     // Catch:{ all -> 0x00ac }
            r4.<init>()     // Catch:{ all -> 0x00ac }
            java.util.Set r0 = r10.entrySet()     // Catch:{ all -> 0x00ac }
            java.util.Iterator r5 = r0.iterator()     // Catch:{ all -> 0x00ac }
        L_0x005a:
            boolean r0 = r5.hasNext()     // Catch:{ all -> 0x00ac }
            if (r0 == 0) goto L_0x00b5
            java.lang.Object r0 = r5.next()     // Catch:{ all -> 0x00ac }
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch:{ all -> 0x00ac }
            java.lang.Object r1 = r0.getKey()     // Catch:{ UnsupportedEncodingException -> 0x00a5 }
            if (r1 == 0) goto L_0x00af
            java.lang.Object r1 = r0.getKey()     // Catch:{ UnsupportedEncodingException -> 0x00a5 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ UnsupportedEncodingException -> 0x00a5 }
        L_0x0072:
            java.lang.Object r6 = r0.getValue()     // Catch:{ UnsupportedEncodingException -> 0x00a5 }
            if (r6 == 0) goto L_0x00b2
            java.lang.Object r0 = r0.getValue()     // Catch:{ UnsupportedEncodingException -> 0x00a5 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ UnsupportedEncodingException -> 0x00a5 }
        L_0x007e:
            java.util.Locale r6 = java.util.Locale.ENGLISH     // Catch:{ UnsupportedEncodingException -> 0x00a5 }
            java.lang.String r6 = r1.toLowerCase(r6)     // Catch:{ UnsupportedEncodingException -> 0x00a5 }
            java.util.HashSet<java.lang.String> r7 = r8.zzXb     // Catch:{ UnsupportedEncodingException -> 0x00a5 }
            boolean r6 = r7.contains(r6)     // Catch:{ UnsupportedEncodingException -> 0x00a5 }
            if (r6 == 0) goto L_0x005a
            com.google.android.gms.internal.adv r6 = new com.google.android.gms.internal.adv     // Catch:{ UnsupportedEncodingException -> 0x00a5 }
            r6.<init>()     // Catch:{ UnsupportedEncodingException -> 0x00a5 }
            java.lang.String r7 = "UTF-8"
            byte[] r1 = r1.getBytes(r7)     // Catch:{ UnsupportedEncodingException -> 0x00a5 }
            r6.zzcsZ = r1     // Catch:{ UnsupportedEncodingException -> 0x00a5 }
            java.lang.String r1 = "UTF-8"
            byte[] r0 = r0.getBytes(r1)     // Catch:{ UnsupportedEncodingException -> 0x00a5 }
            r6.zzcnR = r0     // Catch:{ UnsupportedEncodingException -> 0x00a5 }
            r4.add(r6)     // Catch:{ UnsupportedEncodingException -> 0x00a5 }
            goto L_0x005a
        L_0x00a5:
            r0 = move-exception
            java.lang.String r0 = "Cannot convert string to bytes, skip header."
            com.google.android.gms.internal.zzaes.zzaC(r0)     // Catch:{ all -> 0x00ac }
            goto L_0x005a
        L_0x00ac:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x00ac }
            throw r0
        L_0x00af:
            java.lang.String r1 = ""
            goto L_0x0072
        L_0x00b2:
            java.lang.String r0 = ""
            goto L_0x007e
        L_0x00b5:
            int r0 = r4.size()     // Catch:{ all -> 0x00ac }
            com.google.android.gms.internal.adv[] r0 = new com.google.android.gms.internal.adv[r0]     // Catch:{ all -> 0x00ac }
            r4.toArray(r0)     // Catch:{ all -> 0x00ac }
            com.google.android.gms.internal.adw r1 = r3.zzctn     // Catch:{ all -> 0x00ac }
            r1.zzctb = r0     // Catch:{ all -> 0x00ac }
        L_0x00c2:
            java.util.LinkedHashMap<java.lang.String, com.google.android.gms.internal.aeb> r0 = r8.zzWY     // Catch:{ all -> 0x00ac }
            r0.put(r9, r3)     // Catch:{ all -> 0x00ac }
            monitor-exit(r2)     // Catch:{ all -> 0x00ac }
            goto L_0x0022
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzaeg.zza(java.lang.String, java.util.Map, int):void");
    }

    public final void zzaA(String str) {
        synchronized (this.mLock) {
            this.zzWX.zzcsP = str;
        }
    }

    public final zzaeq zzgY() {
        return this.zzVu;
    }

    public final boolean zzgZ() {
        return zzq.zzsc() && this.zzVu.zzXt && !this.zzXd;
    }

    public final void zzha() {
        this.zzXc = true;
    }

    public final void zzhb() {
        synchronized (this.mLock) {
            zzajm<Map<String, String>> zza = this.zzWZ.zza(this.mContext, this.zzWY.keySet());
            zza.zzc(new zzaei(this, zza));
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public final void zzi(@Nullable Map<String, String> map) throws JSONException {
        if (map != null) {
            for (String next : map.keySet()) {
                JSONArray optJSONArray = new JSONObject(map.get(next)).optJSONArray("matches");
                if (optJSONArray != null) {
                    synchronized (this.mLock) {
                        int length = optJSONArray.length();
                        aeb zzaB = zzaB(next);
                        if (zzaB == null) {
                            String valueOf = String.valueOf(next);
                            zzaes.zzaC(valueOf.length() != 0 ? "Cannot find the corresponding resource object for ".concat(valueOf) : new String("Cannot find the corresponding resource object for "));
                        } else {
                            zzaB.zzctt = new String[length];
                            for (int i = 0; i < length; i++) {
                                zzaB.zzctt[i] = optJSONArray.getJSONObject(i).getString("threat_type");
                            }
                            this.zzXa = (length > 0) | this.zzXa;
                        }
                    }
                }
            }
        }
    }

    public final void zzk(View view) {
        if (this.zzVu.zzXt && !this.zzXd) {
            zzbs.zzbz();
            Bitmap zzm = zzagz.zzm(view);
            if (zzm == null) {
                zzaes.zzaC("Failed to capture the webview bitmap.");
                return;
            }
            this.zzXd = true;
            zzagz.zzb(new zzaeh(this, zzm));
        }
    }
}

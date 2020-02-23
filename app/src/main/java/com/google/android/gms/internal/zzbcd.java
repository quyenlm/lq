package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzal;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.zze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;

public final class zzbcd implements zzbcw {
    /* access modifiers changed from: private */
    public final Context mContext;
    private final Api.zza<? extends zzctk, zzctl> zzaBe;
    private final zzq zzaCA;
    private final Map<Api<?>, Boolean> zzaCD;
    /* access modifiers changed from: private */
    public final zze zzaCF;
    private ConnectionResult zzaCO;
    /* access modifiers changed from: private */
    public final zzbcx zzaCZ;
    /* access modifiers changed from: private */
    public final Lock zzaCv;
    private int zzaDc;
    private int zzaDd = 0;
    private int zzaDe;
    private final Bundle zzaDf = new Bundle();
    private final Set<Api.zzc> zzaDg = new HashSet();
    /* access modifiers changed from: private */
    public zzctk zzaDh;
    private boolean zzaDi;
    /* access modifiers changed from: private */
    public boolean zzaDj;
    private boolean zzaDk;
    /* access modifiers changed from: private */
    public zzal zzaDl;
    private boolean zzaDm;
    private boolean zzaDn;
    private ArrayList<Future<?>> zzaDo = new ArrayList<>();

    public zzbcd(zzbcx zzbcx, zzq zzq, Map<Api<?>, Boolean> map, zze zze, Api.zza<? extends zzctk, zzctl> zza, Lock lock, Context context) {
        this.zzaCZ = zzbcx;
        this.zzaCA = zzq;
        this.zzaCD = map;
        this.zzaCF = zze;
        this.zzaBe = zza;
        this.zzaCv = lock;
        this.mContext = context;
    }

    /* access modifiers changed from: private */
    public final void zza(zzctx zzctx) {
        if (zzan(0)) {
            ConnectionResult zzpz = zzctx.zzpz();
            if (zzpz.isSuccess()) {
                zzbr zzAx = zzctx.zzAx();
                ConnectionResult zzpz2 = zzAx.zzpz();
                if (!zzpz2.isSuccess()) {
                    String valueOf = String.valueOf(zzpz2);
                    Log.wtf("GoogleApiClientConnecting", new StringBuilder(String.valueOf(valueOf).length() + 48).append("Sign-in succeeded with resolve account failure: ").append(valueOf).toString(), new Exception());
                    zze(zzpz2);
                    return;
                }
                this.zzaDk = true;
                this.zzaDl = zzAx.zzrH();
                this.zzaDm = zzAx.zzrI();
                this.zzaDn = zzAx.zzrJ();
                zzpX();
            } else if (zzd(zzpz)) {
                zzpZ();
                zzpX();
            } else {
                zze(zzpz);
            }
        }
    }

    private final void zzad(boolean z) {
        if (this.zzaDh != null) {
            if (this.zzaDh.isConnected() && z) {
                this.zzaDh.zzAq();
            }
            this.zzaDh.disconnect();
            this.zzaDl = null;
        }
    }

    /* access modifiers changed from: private */
    public final boolean zzan(int i) {
        if (this.zzaDd == i) {
            return true;
        }
        Log.w("GoogleApiClientConnecting", this.zzaCZ.zzaCl.zzqg());
        String valueOf = String.valueOf(this);
        Log.w("GoogleApiClientConnecting", new StringBuilder(String.valueOf(valueOf).length() + 23).append("Unexpected callback in ").append(valueOf).toString());
        Log.w("GoogleApiClientConnecting", new StringBuilder(33).append("mRemainingConnections=").append(this.zzaDe).toString());
        String valueOf2 = String.valueOf(zzao(this.zzaDd));
        String valueOf3 = String.valueOf(zzao(i));
        Log.wtf("GoogleApiClientConnecting", new StringBuilder(String.valueOf(valueOf2).length() + 70 + String.valueOf(valueOf3).length()).append("GoogleApiClient connecting is in step ").append(valueOf2).append(" but received callback for step ").append(valueOf3).toString(), new Exception());
        zze(new ConnectionResult(8, (PendingIntent) null));
        return false;
    }

    private static String zzao(int i) {
        switch (i) {
            case 0:
                return "STEP_SERVICE_BINDINGS_AND_SIGN_IN";
            case 1:
                return "STEP_GETTING_REMOTE_SERVICE";
            default:
                return "UNKNOWN";
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003f, code lost:
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0013, code lost:
        if ((r6.hasResolution() ? true : r5.zzaCF.zzak(r6.getErrorCode()) != null) != false) goto L_0x0015;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001b, code lost:
        if (r3 >= r5.zzaDc) goto L_0x003f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzb(com.google.android.gms.common.ConnectionResult r6, com.google.android.gms.common.api.Api<?> r7, boolean r8) {
        /*
            r5 = this;
            r1 = 0
            r0 = 1
            com.google.android.gms.common.api.Api$zzd r2 = r7.zzpb()
            int r3 = r2.getPriority()
            if (r8 == 0) goto L_0x0015
            boolean r2 = r6.hasResolution()
            if (r2 == 0) goto L_0x002f
            r2 = r0
        L_0x0013:
            if (r2 == 0) goto L_0x003f
        L_0x0015:
            com.google.android.gms.common.ConnectionResult r2 = r5.zzaCO
            if (r2 == 0) goto L_0x001d
            int r2 = r5.zzaDc
            if (r3 >= r2) goto L_0x003f
        L_0x001d:
            if (r0 == 0) goto L_0x0023
            r5.zzaCO = r6
            r5.zzaDc = r3
        L_0x0023:
            com.google.android.gms.internal.zzbcx r0 = r5.zzaCZ
            java.util.Map<com.google.android.gms.common.api.Api$zzc<?>, com.google.android.gms.common.ConnectionResult> r0 = r0.zzaDU
            com.google.android.gms.common.api.Api$zzc r1 = r7.zzpd()
            r0.put(r1, r6)
            return
        L_0x002f:
            com.google.android.gms.common.zze r2 = r5.zzaCF
            int r4 = r6.getErrorCode()
            android.content.Intent r2 = r2.zzak(r4)
            if (r2 == 0) goto L_0x003d
            r2 = r0
            goto L_0x0013
        L_0x003d:
            r2 = r1
            goto L_0x0013
        L_0x003f:
            r0 = r1
            goto L_0x001d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzbcd.zzb(com.google.android.gms.common.ConnectionResult, com.google.android.gms.common.api.Api, boolean):void");
    }

    /* access modifiers changed from: private */
    public final boolean zzd(ConnectionResult connectionResult) {
        return this.zzaDi && !connectionResult.hasResolution();
    }

    /* access modifiers changed from: private */
    public final void zze(ConnectionResult connectionResult) {
        zzqa();
        zzad(!connectionResult.hasResolution());
        this.zzaCZ.zzg(connectionResult);
        this.zzaCZ.zzaDY.zzc(connectionResult);
    }

    /* access modifiers changed from: private */
    public final boolean zzpW() {
        this.zzaDe--;
        if (this.zzaDe > 0) {
            return false;
        }
        if (this.zzaDe < 0) {
            Log.w("GoogleApiClientConnecting", this.zzaCZ.zzaCl.zzqg());
            Log.wtf("GoogleApiClientConnecting", "GoogleApiClient received too many callbacks for the given step. Clients may be in an unexpected state; GoogleApiClient will now disconnect.", new Exception());
            zze(new ConnectionResult(8, (PendingIntent) null));
            return false;
        } else if (this.zzaCO == null) {
            return true;
        } else {
            this.zzaCZ.zzaDX = this.zzaDc;
            zze(this.zzaCO);
            return false;
        }
    }

    /* access modifiers changed from: private */
    public final void zzpX() {
        if (this.zzaDe == 0) {
            if (!this.zzaDj || this.zzaDk) {
                ArrayList arrayList = new ArrayList();
                this.zzaDd = 1;
                this.zzaDe = this.zzaCZ.zzaDF.size();
                for (Api.zzc next : this.zzaCZ.zzaDF.keySet()) {
                    if (!this.zzaCZ.zzaDU.containsKey(next)) {
                        arrayList.add(this.zzaCZ.zzaDF.get(next));
                    } else if (zzpW()) {
                        zzpY();
                    }
                }
                if (!arrayList.isEmpty()) {
                    this.zzaDo.add(zzbda.zzqj().submit(new zzbcj(this, arrayList)));
                }
            }
        }
    }

    private final void zzpY() {
        this.zzaCZ.zzqi();
        zzbda.zzqj().execute(new zzbce(this));
        if (this.zzaDh != null) {
            if (this.zzaDm) {
                this.zzaDh.zza(this.zzaDl, this.zzaDn);
            }
            zzad(false);
        }
        for (Api.zzc<?> zzc : this.zzaCZ.zzaDU.keySet()) {
            this.zzaCZ.zzaDF.get(zzc).disconnect();
        }
        this.zzaCZ.zzaDY.zzm(this.zzaDf.isEmpty() ? null : this.zzaDf);
    }

    /* access modifiers changed from: private */
    public final void zzpZ() {
        this.zzaDj = false;
        this.zzaCZ.zzaCl.zzaDG = Collections.emptySet();
        for (Api.zzc next : this.zzaDg) {
            if (!this.zzaCZ.zzaDU.containsKey(next)) {
                this.zzaCZ.zzaDU.put(next, new ConnectionResult(17, (PendingIntent) null));
            }
        }
    }

    private final void zzqa() {
        ArrayList arrayList = this.zzaDo;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Future) obj).cancel(true);
        }
        this.zzaDo.clear();
    }

    /* access modifiers changed from: private */
    public final Set<Scope> zzqb() {
        if (this.zzaCA == null) {
            return Collections.emptySet();
        }
        HashSet hashSet = new HashSet(this.zzaCA.zzrn());
        Map<Api<?>, zzr> zzrp = this.zzaCA.zzrp();
        for (Api next : zzrp.keySet()) {
            if (!this.zzaCZ.zzaDU.containsKey(next.zzpd())) {
                hashSet.addAll(zzrp.get(next).zzame);
            }
        }
        return hashSet;
    }

    public final void begin() {
        this.zzaCZ.zzaDU.clear();
        this.zzaDj = false;
        this.zzaCO = null;
        this.zzaDd = 0;
        this.zzaDi = true;
        this.zzaDk = false;
        this.zzaDm = false;
        HashMap hashMap = new HashMap();
        boolean z = false;
        for (Api next : this.zzaCD.keySet()) {
            Api.zze zze = this.zzaCZ.zzaDF.get(next.zzpd());
            boolean z2 = (next.zzpb().getPriority() == 1) | z;
            boolean booleanValue = this.zzaCD.get(next).booleanValue();
            if (zze.zzmv()) {
                this.zzaDj = true;
                if (booleanValue) {
                    this.zzaDg.add(next.zzpd());
                } else {
                    this.zzaDi = false;
                }
            }
            hashMap.put(zze, new zzbcf(this, next, booleanValue));
            z = z2;
        }
        if (z) {
            this.zzaDj = false;
        }
        if (this.zzaDj) {
            this.zzaCA.zzc(Integer.valueOf(System.identityHashCode(this.zzaCZ.zzaCl)));
            zzbcm zzbcm = new zzbcm(this, (zzbce) null);
            this.zzaDh = (zzctk) this.zzaBe.zza(this.mContext, this.zzaCZ.zzaCl.getLooper(), this.zzaCA, this.zzaCA.zzrt(), zzbcm, zzbcm);
        }
        this.zzaDe = this.zzaCZ.zzaDF.size();
        this.zzaDo.add(zzbda.zzqj().submit(new zzbcg(this, hashMap)));
    }

    public final void connect() {
    }

    public final boolean disconnect() {
        zzqa();
        zzad(true);
        this.zzaCZ.zzg((ConnectionResult) null);
        return true;
    }

    public final void onConnected(Bundle bundle) {
        if (zzan(1)) {
            if (bundle != null) {
                this.zzaDf.putAll(bundle);
            }
            if (zzpW()) {
                zzpY();
            }
        }
    }

    public final void onConnectionSuspended(int i) {
        zze(new ConnectionResult(8, (PendingIntent) null));
    }

    public final void zza(ConnectionResult connectionResult, Api<?> api, boolean z) {
        if (zzan(1)) {
            zzb(connectionResult, api, z);
            if (zzpW()) {
                zzpY();
            }
        }
    }

    public final <A extends Api.zzb, R extends Result, T extends zzbay<R, A>> T zzd(T t) {
        this.zzaCZ.zzaCl.zzaCJ.add(t);
        return t;
    }

    public final <A extends Api.zzb, T extends zzbay<? extends Result, A>> T zze(T t) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }
}

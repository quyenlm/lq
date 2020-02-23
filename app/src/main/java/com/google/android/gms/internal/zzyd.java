package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.internal.zzbs;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@zzzn
public final class zzyd extends zzxt {
    /* access modifiers changed from: private */
    public final zzaka zzJH;
    private zzub zzMu;
    private zztz zzRc;
    protected zzuh zzRd;
    /* access modifiers changed from: private */
    public boolean zzRe;
    private final zznb zzsK;
    private zzuq zzsX;

    zzyd(Context context, zzafg zzafg, zzuq zzuq, zzxy zzxy, zznb zznb, zzaka zzaka) {
        super(context, zzafg, zzxy);
        this.zzsX = zzuq;
        this.zzMu = zzafg.zzXN;
        this.zzsK = zznb;
        this.zzJH = zzaka;
    }

    private static String zzi(List<zzuh> list) {
        int i;
        if (list == null) {
            return "".toString();
        }
        String str = "";
        for (zzuh next : list) {
            if (!(next == null || next.zzMG == null || TextUtils.isEmpty(next.zzMG.zzLK))) {
                String valueOf = String.valueOf(str);
                String str2 = next.zzMG.zzLK;
                switch (next.zzMF) {
                    case -1:
                        i = 4;
                        break;
                    case 0:
                        i = 0;
                        break;
                    case 1:
                        i = 1;
                        break;
                    case 3:
                        i = 2;
                        break;
                    case 4:
                        i = 3;
                        break;
                    case 5:
                        i = 5;
                        break;
                    default:
                        i = 6;
                        break;
                }
                String valueOf2 = String.valueOf(new StringBuilder(String.valueOf(str2).length() + 33).append(str2).append(".").append(i).append(".").append(next.zzML).toString());
                str = new StringBuilder(String.valueOf(valueOf).length() + 1 + String.valueOf(valueOf2).length()).append(valueOf).append(valueOf2).append("_").toString();
            }
        }
        return str.substring(0, Math.max(0, str.length() - 1));
    }

    public final void onStop() {
        synchronized (this.zzQT) {
            super.onStop();
            if (this.zzRc != null) {
                this.zzRc.cancel();
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void zzd(long j) throws zzxw {
        zztz zzun;
        Bundle bundle;
        synchronized (this.zzQT) {
            if (this.zzMu.zzMj != -1) {
                zzun = new zzuk(this.mContext, this.zzQQ.zzUj, this.zzsX, this.zzMu, this.zzQR.zzAv, this.zzQR.zzAx, this.zzQR.zzTI, j, ((Long) zzbs.zzbL().zzd(zzmo.zzEL)).longValue(), 2);
            } else {
                long j2 = j;
                zzun = new zzun(this.mContext, this.zzQQ.zzUj, this.zzsX, this.zzMu, this.zzQR.zzAv, this.zzQR.zzAx, this.zzQR.zzTI, j2, ((Long) zzbs.zzbL().zzd(zzmo.zzEL)).longValue(), this.zzsK);
            }
            this.zzRc = zzun;
        }
        ArrayList arrayList = new ArrayList(this.zzMu.zzLY);
        boolean z = false;
        Bundle bundle2 = this.zzQQ.zzUj.zzSz.zzzX;
        if (!(bundle2 == null || (bundle = bundle2.getBundle("com.google.ads.mediation.admob.AdMobAdapter")) == null)) {
            z = bundle.getBoolean("_skipMediation");
        }
        if (z) {
            ListIterator listIterator = arrayList.listIterator();
            while (listIterator.hasNext()) {
                if (!((zzua) listIterator.next()).zzLJ.contains("com.google.ads.mediation.admob.AdMobAdapter")) {
                    listIterator.remove();
                }
            }
        }
        this.zzRd = this.zzRc.zzf(arrayList);
        switch (this.zzRd.zzMF) {
            case 0:
                if (this.zzRd.zzMG != null && this.zzRd.zzMG.zzLS != null) {
                    CountDownLatch countDownLatch = new CountDownLatch(1);
                    zzagz.zzZr.post(new zzye(this, countDownLatch));
                    try {
                        countDownLatch.await(10, TimeUnit.SECONDS);
                        synchronized (this.zzQT) {
                            if (!this.zzRe) {
                                throw new zzxw("View could not be prepared", 0);
                            } else if (this.zzJH.isDestroyed()) {
                                throw new zzxw("Assets not loaded, web view is destroyed", 0);
                            }
                        }
                        return;
                    } catch (InterruptedException e) {
                        String valueOf = String.valueOf(e);
                        throw new zzxw(new StringBuilder(String.valueOf(valueOf).length() + 38).append("Interrupted while waiting for latch : ").append(valueOf).toString(), 0);
                    }
                } else {
                    return;
                }
            case 1:
                throw new zzxw("No fill from any mediation ad networks.", 3);
            default:
                throw new zzxw(new StringBuilder(40).append("Unexpected mediation result: ").append(this.zzRd.zzMF).toString(), 0);
        }
    }

    /* access modifiers changed from: protected */
    public final zzaff zzs(int i) {
        zzaae zzaae = this.zzQQ.zzUj;
        return new zzaff(zzaae.zzSz, this.zzJH, this.zzQR.zzMa, i, this.zzQR.zzMb, this.zzQR.zzTq, this.zzQR.orientation, this.zzQR.zzMg, zzaae.zzSC, this.zzQR.zzTo, this.zzRd != null ? this.zzRd.zzMG : null, this.zzRd != null ? this.zzRd.zzMH : null, this.zzRd != null ? this.zzRd.zzMI : AdMobAdapter.class.getName(), this.zzMu, this.zzRd != null ? this.zzRd.zzMJ : null, this.zzQR.zzTp, this.zzQQ.zzvX, this.zzQR.zzTn, this.zzQQ.zzXR, this.zzQR.zzTs, this.zzQR.zzTt, this.zzQQ.zzXL, (zzoa) null, this.zzQR.zzTD, this.zzQR.zzTE, this.zzQR.zzTF, this.zzMu != null ? this.zzMu.zzMl : false, this.zzQR.zzTH, this.zzRc != null ? zzi(this.zzRc.zzfg()) : null, this.zzQR.zzMd, this.zzQR.zzTK, this.zzQQ.zzXX);
    }
}

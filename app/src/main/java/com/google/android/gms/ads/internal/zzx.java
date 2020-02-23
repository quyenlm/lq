package com.google.android.gms.ads.internal;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewTreeObserver;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.internal.zzaet;
import com.google.android.gms.internal.zzaev;
import com.google.android.gms.internal.zzaff;
import com.google.android.gms.internal.zzafg;
import com.google.android.gms.internal.zzafr;
import com.google.android.gms.internal.zzagz;
import com.google.android.gms.internal.zzaje;
import com.google.android.gms.internal.zzaka;
import com.google.android.gms.internal.zzakj;
import com.google.android.gms.internal.zzakm;
import com.google.android.gms.internal.zzge;
import com.google.android.gms.internal.zzgi;
import com.google.android.gms.internal.zzir;
import com.google.android.gms.internal.zziv;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzks;
import com.google.android.gms.internal.zzuq;
import com.google.android.gms.internal.zzvc;
import com.google.android.gms.internal.zzvf;
import com.google.android.gms.internal.zzzn;
import java.lang.ref.WeakReference;
import java.util.List;

@zzzn
public final class zzx extends zzi implements ViewTreeObserver.OnGlobalLayoutListener, ViewTreeObserver.OnScrollChangedListener {
    private boolean zzsu;
    private WeakReference<Object> zzts = new WeakReference<>((Object) null);

    public zzx(Context context, zziv zziv, String str, zzuq zzuq, zzaje zzaje, zzv zzv) {
        super(context, zziv, str, zzuq, zzaje, zzv);
    }

    private final boolean zzd(@Nullable zzaff zzaff, zzaff zzaff2) {
        if (zzaff2.zzTo) {
            View zzd = zzar.zzd(zzaff2);
            if (zzd == null) {
                zzafr.zzaT("Could not get mediation view");
                return false;
            }
            View nextView = this.zzsP.zzvU.getNextView();
            if (nextView != null) {
                if (nextView instanceof zzaka) {
                    ((zzaka) nextView).destroy();
                }
                this.zzsP.zzvU.removeView(nextView);
            }
            if (!zzar.zze(zzaff2)) {
                try {
                    if (zzbs.zzbY().zzq(this.zzsP.zzqD)) {
                        new zzge(this.zzsP.zzqD, zzd).zza((zzgi) new zzaev(this.zzsP.zzqD, this.zzsP.zzvR));
                    }
                    zzb(zzd);
                } catch (Throwable th) {
                    zzbs.zzbD().zza(th, "BannerAdManager.swapViews");
                    zzafr.zzc("Could not add mediation view to view hierarchy.", th);
                    return false;
                }
            }
        } else if (!(zzaff2.zzXP == null || zzaff2.zzPg == null)) {
            zzaff2.zzPg.zza(zzaff2.zzXP);
            this.zzsP.zzvU.removeAllViews();
            this.zzsP.zzvU.setMinimumWidth(zzaff2.zzXP.widthPixels);
            this.zzsP.zzvU.setMinimumHeight(zzaff2.zzXP.heightPixels);
            zzb(zzaff2.zzPg.getView());
        }
        if (this.zzsP.zzvU.getChildCount() > 1) {
            this.zzsP.zzvU.showNext();
        }
        if (zzaff != null) {
            View nextView2 = this.zzsP.zzvU.getNextView();
            if (nextView2 instanceof zzaka) {
                ((zzaka) nextView2).zza(this.zzsP.zzqD, this.zzsP.zzvX, this.zzsK);
            } else if (nextView2 != null) {
                this.zzsP.zzvU.removeView(nextView2);
            }
            this.zzsP.zzcb();
        }
        this.zzsP.zzvU.setVisibility(0);
        return true;
    }

    @Nullable
    public final zzks getVideoController() {
        zzbo.zzcz("getVideoController must be called from the main thread.");
        if (this.zzsP.zzvY == null || this.zzsP.zzvY.zzPg == null) {
            return null;
        }
        return this.zzsP.zzvY.zzPg.zziH();
    }

    public final void onGlobalLayout() {
        zzc(this.zzsP.zzvY);
    }

    public final void onScrollChanged() {
        zzc(this.zzsP.zzvY);
    }

    public final void setManualImpressionsEnabled(boolean z) {
        zzbo.zzcz("setManualImpressionsEnabled must be called from the main thread.");
        this.zzsu = z;
    }

    public final void showInterstitial() {
        throw new IllegalStateException("Interstitial is NOT supported by BannerAdManager.");
    }

    /* access modifiers changed from: protected */
    public final zzaka zza(zzafg zzafg, @Nullable zzw zzw, @Nullable zzaet zzaet) throws zzakm {
        AdSize zzdl;
        zziv zziv;
        if (this.zzsP.zzvX.zzAu == null && this.zzsP.zzvX.zzAw) {
            zzbt zzbt = this.zzsP;
            if (zzafg.zzXY.zzAw) {
                zziv = this.zzsP.zzvX;
            } else {
                String str = zzafg.zzXY.zzTr;
                if (str != null) {
                    String[] split = str.split("[xX]");
                    split[0] = split[0].trim();
                    split[1] = split[1].trim();
                    zzdl = new AdSize(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                } else {
                    zzdl = this.zzsP.zzvX.zzdl();
                }
                zziv = new zziv(this.zzsP.zzqD, zzdl);
            }
            zzbt.zzvX = zziv;
        }
        return super.zza(zzafg, zzw, zzaet);
    }

    /* access modifiers changed from: protected */
    public final void zza(@Nullable zzaff zzaff, boolean z) {
        zzvf zzvf = null;
        super.zza(zzaff, z);
        if (zzar.zze(zzaff)) {
            zzab zzab = new zzab(this);
            if (zzaff != null && zzar.zze(zzaff)) {
                zzaka zzaka = zzaff.zzPg;
                View view = zzaka != null ? zzaka.getView() : null;
                if (view == null) {
                    zzafr.zzaT("AdWebView is null");
                    return;
                }
                try {
                    List<String> list = zzaff.zzMG != null ? zzaff.zzMG.zzLV : null;
                    if (list == null || list.isEmpty()) {
                        zzafr.zzaT("No template ids present in mediation response");
                        return;
                    }
                    zzvc zzfq = zzaff.zzMH != null ? zzaff.zzMH.zzfq() : null;
                    if (zzaff.zzMH != null) {
                        zzvf = zzaff.zzMH.zzfr();
                    }
                    if (list.contains("2") && zzfq != null) {
                        zzfq.zzm(zzn.zzw(view));
                        if (!zzfq.getOverrideImpressionRecording()) {
                            zzfq.recordImpression();
                        }
                        zzaka.zziw().zza("/nativeExpressViewClicked", zzar.zza(zzfq, (zzvf) null, zzab));
                    } else if (!list.contains("1") || zzvf == null) {
                        zzafr.zzaT("No matching template id and mapper");
                    } else {
                        zzvf.zzm(zzn.zzw(view));
                        if (!zzvf.getOverrideImpressionRecording()) {
                            zzvf.recordImpression();
                        }
                        zzaka.zziw().zza("/nativeExpressViewClicked", zzar.zza((zzvc) null, zzvf, zzab));
                    }
                } catch (RemoteException e) {
                    zzafr.zzc("Error occurred while recording impression and registering for clicks", e);
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00e7, code lost:
        if (((java.lang.Boolean) com.google.android.gms.ads.internal.zzbs.zzbL().zzd(com.google.android.gms.internal.zzmo.zzFo)).booleanValue() != false) goto L_0x00e9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean zza(@android.support.annotation.Nullable com.google.android.gms.internal.zzaff r5, com.google.android.gms.internal.zzaff r6) {
        /*
            r4 = this;
            r1 = 0
            boolean r0 = super.zza((com.google.android.gms.internal.zzaff) r5, (com.google.android.gms.internal.zzaff) r6)
            if (r0 != 0) goto L_0x0009
            r0 = r1
        L_0x0008:
            return r0
        L_0x0009:
            com.google.android.gms.ads.internal.zzbt r0 = r4.zzsP
            boolean r0 = r0.zzcc()
            if (r0 == 0) goto L_0x001c
            boolean r0 = r4.zzd(r5, r6)
            if (r0 != 0) goto L_0x001c
            r4.zze(r1)
            r0 = r1
            goto L_0x0008
        L_0x001c:
            boolean r0 = r6.zzTG
            if (r0 == 0) goto L_0x00cf
            r4.zzc(r6)
            com.google.android.gms.ads.internal.zzbs.zzbX()
            com.google.android.gms.ads.internal.zzbt r0 = r4.zzsP
            com.google.android.gms.ads.internal.zzbu r0 = r0.zzvU
            com.google.android.gms.internal.zzajv.zza((android.view.View) r0, (android.view.ViewTreeObserver.OnGlobalLayoutListener) r4)
            com.google.android.gms.ads.internal.zzbs.zzbX()
            com.google.android.gms.ads.internal.zzbt r0 = r4.zzsP
            com.google.android.gms.ads.internal.zzbu r0 = r0.zzvU
            com.google.android.gms.internal.zzajv.zza((android.view.View) r0, (android.view.ViewTreeObserver.OnScrollChangedListener) r4)
            boolean r0 = r6.zzXM
            if (r0 != 0) goto L_0x0054
            com.google.android.gms.ads.internal.zzy r1 = new com.google.android.gms.ads.internal.zzy
            r1.<init>(r4)
            com.google.android.gms.internal.zzaka r0 = r6.zzPg
            if (r0 == 0) goto L_0x00cc
            com.google.android.gms.internal.zzaka r0 = r6.zzPg
            com.google.android.gms.internal.zzakb r0 = r0.zziw()
        L_0x004a:
            if (r0 == 0) goto L_0x0054
            com.google.android.gms.ads.internal.zzz r2 = new com.google.android.gms.ads.internal.zzz
            r2.<init>(r4, r6, r1)
            r0.zza((com.google.android.gms.internal.zzakj) r2)
        L_0x0054:
            com.google.android.gms.internal.zzaka r0 = r6.zzPg
            if (r0 == 0) goto L_0x0078
            com.google.android.gms.internal.zzaka r0 = r6.zzPg
            com.google.android.gms.internal.zzaks r0 = r0.zziH()
            com.google.android.gms.internal.zzaka r1 = r6.zzPg
            com.google.android.gms.internal.zzakb r1 = r1.zziw()
            if (r1 == 0) goto L_0x0069
            r1.zziV()
        L_0x0069:
            com.google.android.gms.ads.internal.zzbt r1 = r4.zzsP
            com.google.android.gms.internal.zzlx r1 = r1.zzwk
            if (r1 == 0) goto L_0x0078
            if (r0 == 0) goto L_0x0078
            com.google.android.gms.ads.internal.zzbt r1 = r4.zzsP
            com.google.android.gms.internal.zzlx r1 = r1.zzwk
            r0.zzb((com.google.android.gms.internal.zzlx) r1)
        L_0x0078:
            com.google.android.gms.ads.internal.zzbt r0 = r4.zzsP
            boolean r0 = r0.zzcc()
            if (r0 == 0) goto L_0x00fd
            com.google.android.gms.internal.zzaka r0 = r6.zzPg
            if (r0 == 0) goto L_0x00c9
            org.json.JSONObject r0 = r6.zzXL
            if (r0 == 0) goto L_0x0091
            com.google.android.gms.internal.zzfh r0 = r4.zzsR
            com.google.android.gms.ads.internal.zzbt r1 = r4.zzsP
            com.google.android.gms.internal.zziv r1 = r1.zzvX
            r0.zza(r1, r6)
        L_0x0091:
            com.google.android.gms.internal.zzge r0 = new com.google.android.gms.internal.zzge
            com.google.android.gms.ads.internal.zzbt r1 = r4.zzsP
            android.content.Context r1 = r1.zzqD
            com.google.android.gms.internal.zzaka r2 = r6.zzPg
            android.view.View r2 = r2.getView()
            r0.<init>(r1, r2)
            com.google.android.gms.internal.zzaew r1 = com.google.android.gms.ads.internal.zzbs.zzbY()
            com.google.android.gms.ads.internal.zzbt r2 = r4.zzsP
            android.content.Context r2 = r2.zzqD
            boolean r1 = r1.zzq(r2)
            if (r1 == 0) goto L_0x00be
            com.google.android.gms.internal.zzaev r1 = new com.google.android.gms.internal.zzaev
            com.google.android.gms.ads.internal.zzbt r2 = r4.zzsP
            android.content.Context r2 = r2.zzqD
            com.google.android.gms.ads.internal.zzbt r3 = r4.zzsP
            java.lang.String r3 = r3.zzvR
            r1.<init>(r2, r3)
            r0.zza((com.google.android.gms.internal.zzgi) r1)
        L_0x00be:
            boolean r1 = r6.zzcn()
            if (r1 == 0) goto L_0x00ee
            com.google.android.gms.internal.zzaka r1 = r6.zzPg
            r0.zza((com.google.android.gms.internal.zzgi) r1)
        L_0x00c9:
            r0 = 1
            goto L_0x0008
        L_0x00cc:
            r0 = 0
            goto L_0x004a
        L_0x00cf:
            com.google.android.gms.ads.internal.zzbt r0 = r4.zzsP
            boolean r0 = r0.zzcd()
            if (r0 == 0) goto L_0x00e9
            com.google.android.gms.internal.zzme<java.lang.Boolean> r0 = com.google.android.gms.internal.zzmo.zzFo
            com.google.android.gms.internal.zzmm r2 = com.google.android.gms.ads.internal.zzbs.zzbL()
            java.lang.Object r0 = r2.zzd(r0)
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L_0x0054
        L_0x00e9:
            r4.zza((com.google.android.gms.internal.zzaff) r6, (boolean) r1)
            goto L_0x0054
        L_0x00ee:
            com.google.android.gms.internal.zzaka r1 = r6.zzPg
            com.google.android.gms.internal.zzakb r1 = r1.zziw()
            com.google.android.gms.ads.internal.zzaa r2 = new com.google.android.gms.ads.internal.zzaa
            r2.<init>(r4, r0, r6)
            r1.zza((com.google.android.gms.internal.zzakh) r2)
            goto L_0x00c9
        L_0x00fd:
            com.google.android.gms.ads.internal.zzbt r0 = r4.zzsP
            android.view.View r0 = r0.zzws
            if (r0 == 0) goto L_0x00c9
            org.json.JSONObject r0 = r6.zzXL
            if (r0 == 0) goto L_0x00c9
            com.google.android.gms.internal.zzfh r0 = r4.zzsR
            com.google.android.gms.ads.internal.zzbt r1 = r4.zzsP
            com.google.android.gms.internal.zziv r1 = r1.zzvX
            com.google.android.gms.ads.internal.zzbt r2 = r4.zzsP
            android.view.View r2 = r2.zzws
            r0.zza(r1, r6, r2)
            goto L_0x00c9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.ads.internal.zzx.zza(com.google.android.gms.internal.zzaff, com.google.android.gms.internal.zzaff):boolean");
    }

    public final boolean zza(zzir zzir) {
        if (zzir.zzzS != this.zzsu) {
            zzir = new zzir(zzir.versionCode, zzir.zzzN, zzir.extras, zzir.zzzO, zzir.zzzP, zzir.zzzQ, zzir.zzzR, zzir.zzzS || this.zzsu, zzir.zzzT, zzir.zzzU, zzir.zzzV, zzir.zzzW, zzir.zzzX, zzir.zzzY, zzir.zzzZ, zzir.zzAa, zzir.zzAb, zzir.zzAc);
        }
        return super.zza(zzir);
    }

    /* access modifiers changed from: protected */
    public final boolean zzaz() {
        boolean z = true;
        zzbs.zzbz();
        if (!zzagz.zzc(this.zzsP.zzqD, this.zzsP.zzqD.getPackageName(), "android.permission.INTERNET")) {
            zzji.zzds().zza(this.zzsP.zzvU, this.zzsP.zzvX, "Missing internet permission in AndroidManifest.xml.", "Missing internet permission in AndroidManifest.xml. You must have the following declaration: <uses-permission android:name=\"android.permission.INTERNET\" />");
            z = false;
        }
        zzbs.zzbz();
        if (!zzagz.zzD(this.zzsP.zzqD)) {
            zzji.zzds().zza(this.zzsP.zzvU, this.zzsP.zzvX, "Missing AdActivity with android:configChanges in AndroidManifest.xml.", "Missing AdActivity with android:configChanges in AndroidManifest.xml. You must have the following declaration within the <application> element: <activity android:name=\"com.google.android.gms.ads.AdActivity\" android:configChanges=\"keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize\" />");
            z = false;
        }
        if (!z && this.zzsP.zzvU != null) {
            this.zzsP.zzvU.setVisibility(0);
        }
        return z;
    }

    /* access modifiers changed from: package-private */
    public final void zzc(@Nullable zzaff zzaff) {
        if (zzaff != null && !zzaff.zzXM && this.zzsP.zzvU != null && zzbs.zzbz().zza((View) this.zzsP.zzvU, this.zzsP.zzqD) && this.zzsP.zzvU.getGlobalVisibleRect(new Rect(), (Point) null)) {
            if (!(zzaff == null || zzaff.zzPg == null || zzaff.zzPg.zziw() == null)) {
                zzaff.zzPg.zziw().zza((zzakj) null);
            }
            zza(zzaff, false);
            zzaff.zzXM = true;
        }
    }
}

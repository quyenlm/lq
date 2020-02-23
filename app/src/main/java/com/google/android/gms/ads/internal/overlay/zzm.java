package com.google.android.gms.ads.internal.overlay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.internal.zzafr;
import com.google.android.gms.internal.zzagz;
import com.google.android.gms.internal.zzahe;
import com.google.android.gms.internal.zzaka;
import com.google.android.gms.internal.zzmo;
import com.google.android.gms.internal.zzwy;
import com.google.android.gms.internal.zzzn;
import java.util.Collections;
import java.util.Map;

@zzzn
public final class zzm extends zzwy implements zzaj {
    private static int zzOF = Color.argb(0, 0, 0, 0);
    /* access modifiers changed from: private */
    public final Activity mActivity;
    private zzaka zzJH;
    AdOverlayInfoParcel zzOG;
    private zzr zzOH;
    private zzae zzOI;
    private boolean zzOJ = false;
    private FrameLayout zzOK;
    private WebChromeClient.CustomViewCallback zzOL;
    private boolean zzOM = false;
    private boolean zzON = false;
    private zzq zzOO;
    private boolean zzOP = false;
    private int zzOQ = 0;
    private final Object zzOR = new Object();
    private Runnable zzOS;
    private boolean zzOT;
    private boolean zzOU;
    private boolean zzOV = false;
    private boolean zzOW = false;
    private boolean zzOX = true;

    public zzm(Activity activity) {
        this.mActivity = activity;
    }

    private final void zzfM() {
        if (this.mActivity.isFinishing() && !this.zzOV) {
            this.zzOV = true;
            if (this.zzJH != null) {
                this.zzJH.zzA(this.zzOQ);
                synchronized (this.zzOR) {
                    if (!this.zzOT && this.zzJH.zziI()) {
                        this.zzOS = new zzo(this);
                        zzagz.zzZr.postDelayed(this.zzOS, ((Long) zzbs.zzbL().zzd(zzmo.zzDS)).longValue());
                        return;
                    }
                }
            }
            zzfN();
        }
    }

    private final void zzfP() {
        this.zzJH.zzfP();
    }

    private final void zzr(boolean z) {
        int intValue = ((Integer) zzbs.zzbL().zzd(zzmo.zzGB)).intValue();
        zzaf zzaf = new zzaf();
        zzaf.size = 50;
        zzaf.paddingLeft = z ? intValue : 0;
        zzaf.paddingRight = z ? 0 : intValue;
        zzaf.paddingTop = 0;
        zzaf.paddingBottom = intValue;
        this.zzOI = new zzae(this.mActivity, zzaf, this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(10);
        layoutParams.addRule(z ? 11 : 9);
        this.zzOI.zza(z, this.zzOG.zzPj);
        this.zzOO.addView(this.zzOI, layoutParams);
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x009d  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00f1  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00fa  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x010d A[SYNTHETIC, Splitter:B:49:0x010d] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01a2  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x01c8  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x01cd  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x01d0  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x01fa  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x022e  */
    /* JADX WARNING: Removed duplicated region for block: B:95:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zzs(boolean r16) throws com.google.android.gms.ads.internal.overlay.zzp {
        /*
            r15 = this;
            boolean r0 = r15.zzOU
            if (r0 != 0) goto L_0x000a
            android.app.Activity r0 = r15.mActivity
            r1 = 1
            r0.requestWindowFeature(r1)
        L_0x000a:
            android.app.Activity r0 = r15.mActivity
            android.view.Window r2 = r0.getWindow()
            if (r2 != 0) goto L_0x001a
            com.google.android.gms.ads.internal.overlay.zzp r0 = new com.google.android.gms.ads.internal.overlay.zzp
            java.lang.String r1 = "Invalid activity, no window available."
            r0.<init>(r1)
            throw r0
        L_0x001a:
            r1 = 1
            boolean r0 = com.google.android.gms.common.util.zzq.isAtLeastN()
            if (r0 == 0) goto L_0x023d
            com.google.android.gms.internal.zzme<java.lang.Boolean> r0 = com.google.android.gms.internal.zzmo.zzGz
            com.google.android.gms.internal.zzmm r3 = com.google.android.gms.ads.internal.zzbs.zzbL()
            java.lang.Object r0 = r3.zzd(r0)
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L_0x023d
            com.google.android.gms.ads.internal.zzbs.zzbz()
            android.app.Activity r0 = r15.mActivity
            android.app.Activity r1 = r15.mActivity
            android.content.res.Resources r1 = r1.getResources()
            android.content.res.Configuration r1 = r1.getConfiguration()
            boolean r0 = com.google.android.gms.internal.zzagz.zza((android.app.Activity) r0, (android.content.res.Configuration) r1)
        L_0x0046:
            com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel r1 = r15.zzOG
            com.google.android.gms.ads.internal.zzap r1 = r1.zzPo
            if (r1 == 0) goto L_0x01cd
            com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel r1 = r15.zzOG
            com.google.android.gms.ads.internal.zzap r1 = r1.zzPo
            boolean r1 = r1.zzus
            if (r1 == 0) goto L_0x01cd
            r1 = 1
        L_0x0055:
            boolean r3 = r15.zzON
            if (r3 == 0) goto L_0x005b
            if (r1 == 0) goto L_0x0093
        L_0x005b:
            if (r0 == 0) goto L_0x0093
            r0 = 1024(0x400, float:1.435E-42)
            r1 = 1024(0x400, float:1.435E-42)
            r2.setFlags(r0, r1)
            com.google.android.gms.internal.zzme<java.lang.Boolean> r0 = com.google.android.gms.internal.zzmo.zzDT
            com.google.android.gms.internal.zzmm r1 = com.google.android.gms.ads.internal.zzbs.zzbL()
            java.lang.Object r0 = r1.zzd(r0)
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L_0x0093
            boolean r0 = com.google.android.gms.common.util.zzq.zzsc()
            if (r0 == 0) goto L_0x0093
            com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel r0 = r15.zzOG
            com.google.android.gms.ads.internal.zzap r0 = r0.zzPo
            if (r0 == 0) goto L_0x0093
            com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel r0 = r15.zzOG
            com.google.android.gms.ads.internal.zzap r0 = r0.zzPo
            boolean r0 = r0.zzux
            if (r0 == 0) goto L_0x0093
            android.view.View r0 = r2.getDecorView()
            r1 = 4098(0x1002, float:5.743E-42)
            r0.setSystemUiVisibility(r1)
        L_0x0093:
            com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel r0 = r15.zzOG
            com.google.android.gms.internal.zzaka r0 = r0.zzPg
            com.google.android.gms.internal.zzakb r0 = r0.zziw()
            if (r0 == 0) goto L_0x01d0
            boolean r4 = r0.zzcn()
        L_0x00a1:
            r0 = 0
            r15.zzOP = r0
            if (r4 == 0) goto L_0x00c6
            com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel r0 = r15.zzOG
            int r0 = r0.orientation
            com.google.android.gms.internal.zzahe r1 = com.google.android.gms.ads.internal.zzbs.zzbB()
            int r1 = r1.zzhT()
            if (r0 != r1) goto L_0x01d6
            android.app.Activity r0 = r15.mActivity
            android.content.res.Resources r0 = r0.getResources()
            android.content.res.Configuration r0 = r0.getConfiguration()
            int r0 = r0.orientation
            r1 = 1
            if (r0 != r1) goto L_0x01d3
            r0 = 1
        L_0x00c4:
            r15.zzOP = r0
        L_0x00c6:
            boolean r0 = r15.zzOP
            r1 = 46
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>(r1)
            java.lang.String r1 = "Delay onShow to next orientation change: "
            java.lang.StringBuilder r1 = r3.append(r1)
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r0 = r0.toString()
            com.google.android.gms.internal.zzafr.zzaC(r0)
            com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel r0 = r15.zzOG
            int r0 = r0.orientation
            r15.setRequestedOrientation(r0)
            com.google.android.gms.internal.zzahe r0 = com.google.android.gms.ads.internal.zzbs.zzbB()
            boolean r0 = r0.zza((android.view.Window) r2)
            if (r0 == 0) goto L_0x00f6
            java.lang.String r0 = "Hardware acceleration on the AdActivity window enabled."
            com.google.android.gms.internal.zzafr.zzaC(r0)
        L_0x00f6:
            boolean r0 = r15.zzON
            if (r0 != 0) goto L_0x01fa
            com.google.android.gms.ads.internal.overlay.zzq r0 = r15.zzOO
            r1 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r0.setBackgroundColor(r1)
        L_0x0101:
            android.app.Activity r0 = r15.mActivity
            com.google.android.gms.ads.internal.overlay.zzq r1 = r15.zzOO
            r0.setContentView(r1)
            r0 = 1
            r15.zzOU = r0
            if (r16 == 0) goto L_0x022e
            com.google.android.gms.internal.zzakk r0 = com.google.android.gms.ads.internal.zzbs.zzbA()     // Catch:{ Exception -> 0x0203 }
            android.app.Activity r1 = r15.mActivity     // Catch:{ Exception -> 0x0203 }
            com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel r2 = r15.zzOG     // Catch:{ Exception -> 0x0203 }
            com.google.android.gms.internal.zzaka r2 = r2.zzPg     // Catch:{ Exception -> 0x0203 }
            com.google.android.gms.internal.zziv r2 = r2.zzam()     // Catch:{ Exception -> 0x0203 }
            r3 = 1
            r5 = 0
            com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel r6 = r15.zzOG     // Catch:{ Exception -> 0x0203 }
            com.google.android.gms.internal.zzaje r6 = r6.zzvT     // Catch:{ Exception -> 0x0203 }
            r7 = 0
            r8 = 0
            com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel r9 = r15.zzOG     // Catch:{ Exception -> 0x0203 }
            com.google.android.gms.internal.zzaka r9 = r9.zzPg     // Catch:{ Exception -> 0x0203 }
            com.google.android.gms.ads.internal.zzv r9 = r9.zzak()     // Catch:{ Exception -> 0x0203 }
            com.google.android.gms.internal.zzig r10 = com.google.android.gms.internal.zzig.zzde()     // Catch:{ Exception -> 0x0203 }
            com.google.android.gms.internal.zzaka r0 = r0.zza(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ Exception -> 0x0203 }
            r15.zzJH = r0     // Catch:{ Exception -> 0x0203 }
            com.google.android.gms.internal.zzaka r0 = r15.zzJH
            com.google.android.gms.internal.zzakb r5 = r0.zziw()
            r6 = 0
            r7 = 0
            com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel r0 = r15.zzOG
            com.google.android.gms.internal.zzqk r8 = r0.zzPh
            com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel r0 = r15.zzOG
            com.google.android.gms.ads.internal.overlay.zzag r9 = r0.zzPl
            r10 = 1
            r11 = 0
            com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel r0 = r15.zzOG
            com.google.android.gms.internal.zzaka r0 = r0.zzPg
            com.google.android.gms.internal.zzakb r0 = r0.zziw()
            com.google.android.gms.ads.internal.zzw r12 = r0.zziO()
            r13 = 0
            r14 = 0
            r5.zza(r6, r7, r8, r9, r10, r11, r12, r13, r14)
            com.google.android.gms.internal.zzaka r0 = r15.zzJH
            com.google.android.gms.internal.zzakb r0 = r0.zziw()
            com.google.android.gms.ads.internal.overlay.zzn r1 = new com.google.android.gms.ads.internal.overlay.zzn
            r1.<init>(r15)
            r0.zza((com.google.android.gms.internal.zzakf) r1)
            com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel r0 = r15.zzOG
            java.lang.String r0 = r0.url
            if (r0 == 0) goto L_0x020c
            com.google.android.gms.internal.zzaka r0 = r15.zzJH
            com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel r1 = r15.zzOG
            java.lang.String r1 = r1.url
            r0.loadUrl(r1)
        L_0x0175:
            com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel r0 = r15.zzOG
            com.google.android.gms.internal.zzaka r0 = r0.zzPg
            if (r0 == 0) goto L_0x0182
            com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel r0 = r15.zzOG
            com.google.android.gms.internal.zzaka r0 = r0.zzPg
            r0.zzc(r15)
        L_0x0182:
            com.google.android.gms.internal.zzaka r0 = r15.zzJH
            r0.zzb((com.google.android.gms.ads.internal.overlay.zzm) r15)
            com.google.android.gms.internal.zzaka r0 = r15.zzJH
            android.view.ViewParent r0 = r0.getParent()
            if (r0 == 0) goto L_0x019e
            boolean r1 = r0 instanceof android.view.ViewGroup
            if (r1 == 0) goto L_0x019e
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            com.google.android.gms.internal.zzaka r1 = r15.zzJH
            android.view.View r1 = r1.getView()
            r0.removeView(r1)
        L_0x019e:
            boolean r0 = r15.zzON
            if (r0 == 0) goto L_0x01a7
            com.google.android.gms.internal.zzaka r0 = r15.zzJH
            r0.zziN()
        L_0x01a7:
            com.google.android.gms.ads.internal.overlay.zzq r0 = r15.zzOO
            com.google.android.gms.internal.zzaka r1 = r15.zzJH
            android.view.View r1 = r1.getView()
            r2 = -1
            r3 = -1
            r0.addView(r1, r2, r3)
            if (r16 != 0) goto L_0x01bd
            boolean r0 = r15.zzOP
            if (r0 != 0) goto L_0x01bd
            r15.zzfP()
        L_0x01bd:
            r15.zzr(r4)
            com.google.android.gms.internal.zzaka r0 = r15.zzJH
            boolean r0 = r0.zzix()
            if (r0 == 0) goto L_0x01cc
            r0 = 1
            r15.zza((boolean) r4, (boolean) r0)
        L_0x01cc:
            return
        L_0x01cd:
            r1 = 0
            goto L_0x0055
        L_0x01d0:
            r4 = 0
            goto L_0x00a1
        L_0x01d3:
            r0 = 0
            goto L_0x00c4
        L_0x01d6:
            com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel r0 = r15.zzOG
            int r0 = r0.orientation
            com.google.android.gms.internal.zzahe r1 = com.google.android.gms.ads.internal.zzbs.zzbB()
            int r1 = r1.zzhU()
            if (r0 != r1) goto L_0x00c6
            android.app.Activity r0 = r15.mActivity
            android.content.res.Resources r0 = r0.getResources()
            android.content.res.Configuration r0 = r0.getConfiguration()
            int r0 = r0.orientation
            r1 = 2
            if (r0 != r1) goto L_0x01f8
            r0 = 1
        L_0x01f4:
            r15.zzOP = r0
            goto L_0x00c6
        L_0x01f8:
            r0 = 0
            goto L_0x01f4
        L_0x01fa:
            com.google.android.gms.ads.internal.overlay.zzq r0 = r15.zzOO
            int r1 = zzOF
            r0.setBackgroundColor(r1)
            goto L_0x0101
        L_0x0203:
            r0 = move-exception
            com.google.android.gms.ads.internal.overlay.zzp r0 = new com.google.android.gms.ads.internal.overlay.zzp
            java.lang.String r1 = "Could not obtain webview for the overlay."
            r0.<init>(r1)
            throw r0
        L_0x020c:
            com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel r0 = r15.zzOG
            java.lang.String r0 = r0.zzPk
            if (r0 == 0) goto L_0x0226
            com.google.android.gms.internal.zzaka r5 = r15.zzJH
            com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel r0 = r15.zzOG
            java.lang.String r6 = r0.zzPi
            com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel r0 = r15.zzOG
            java.lang.String r7 = r0.zzPk
            java.lang.String r8 = "text/html"
            java.lang.String r9 = "UTF-8"
            r10 = 0
            r5.loadDataWithBaseURL(r6, r7, r8, r9, r10)
            goto L_0x0175
        L_0x0226:
            com.google.android.gms.ads.internal.overlay.zzp r0 = new com.google.android.gms.ads.internal.overlay.zzp
            java.lang.String r1 = "No URL or HTML to display in ad overlay."
            r0.<init>(r1)
            throw r0
        L_0x022e:
            com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel r0 = r15.zzOG
            com.google.android.gms.internal.zzaka r0 = r0.zzPg
            r15.zzJH = r0
            com.google.android.gms.internal.zzaka r0 = r15.zzJH
            android.app.Activity r1 = r15.mActivity
            r0.setContext(r1)
            goto L_0x0182
        L_0x023d:
            r0 = r1
            goto L_0x0046
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.ads.internal.overlay.zzm.zzs(boolean):void");
    }

    public final void close() {
        this.zzOQ = 2;
        this.mActivity.finish();
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
    }

    public final void onBackPressed() {
        this.zzOQ = 0;
    }

    public final void onCreate(Bundle bundle) {
        boolean z = false;
        this.mActivity.requestWindowFeature(1);
        if (bundle != null) {
            z = bundle.getBoolean("com.google.android.gms.ads.internal.overlay.hasResumed", false);
        }
        this.zzOM = z;
        try {
            this.zzOG = AdOverlayInfoParcel.zzb(this.mActivity.getIntent());
            if (this.zzOG == null) {
                throw new zzp("Could not get info for ad overlay.");
            }
            if (this.zzOG.zzvT.zzaaP > 7500000) {
                this.zzOQ = 3;
            }
            if (this.mActivity.getIntent() != null) {
                this.zzOX = this.mActivity.getIntent().getBooleanExtra("shouldCallOnOverlayOpened", true);
            }
            if (this.zzOG.zzPo != null) {
                this.zzON = this.zzOG.zzPo.zzur;
            } else {
                this.zzON = false;
            }
            if (((Boolean) zzbs.zzbL().zzd(zzmo.zzFh)).booleanValue() && this.zzON && this.zzOG.zzPo.zzuw != -1) {
                new zzs(this, (zzn) null).zzhL();
            }
            if (bundle == null) {
                if (this.zzOG.zzPf != null && this.zzOX) {
                    this.zzOG.zzPf.zzaB();
                }
                if (!(this.zzOG.zzPm == 1 || this.zzOG.zzPe == null)) {
                    this.zzOG.zzPe.onAdClicked();
                }
            }
            this.zzOO = new zzq(this.mActivity, this.zzOG.zzPn, this.zzOG.zzvT.zzaP);
            this.zzOO.setId(1000);
            switch (this.zzOG.zzPm) {
                case 1:
                    zzs(false);
                    return;
                case 2:
                    this.zzOH = new zzr(this.zzOG.zzPg);
                    zzs(false);
                    return;
                case 3:
                    zzs(true);
                    return;
                case 4:
                    if (this.zzOM) {
                        this.zzOQ = 3;
                        this.mActivity.finish();
                        return;
                    }
                    zzbs.zzbw();
                    if (!zza.zza((Context) this.mActivity, this.zzOG.zzPd, this.zzOG.zzPl)) {
                        this.zzOQ = 3;
                        this.mActivity.finish();
                        return;
                    }
                    return;
                default:
                    throw new zzp("Could not determine ad overlay type.");
            }
        } catch (zzp e) {
            zzafr.zzaT(e.getMessage());
            this.zzOQ = 3;
            this.mActivity.finish();
        }
    }

    public final void onDestroy() {
        if (this.zzJH != null) {
            this.zzOO.removeView(this.zzJH.getView());
        }
        zzfM();
    }

    public final void onPause() {
        zzfI();
        if (this.zzOG.zzPf != null) {
            this.zzOG.zzPf.onPause();
        }
        if (!((Boolean) zzbs.zzbL().zzd(zzmo.zzGA)).booleanValue() && this.zzJH != null && (!this.mActivity.isFinishing() || this.zzOH == null)) {
            zzbs.zzbB();
            zzahe.zzk(this.zzJH);
        }
        zzfM();
    }

    public final void onRestart() {
    }

    public final void onResume() {
        if (this.zzOG != null && this.zzOG.zzPm == 4) {
            if (this.zzOM) {
                this.zzOQ = 3;
                this.mActivity.finish();
            } else {
                this.zzOM = true;
            }
        }
        if (this.zzOG.zzPf != null) {
            this.zzOG.zzPf.onResume();
        }
        if (((Boolean) zzbs.zzbL().zzd(zzmo.zzGA)).booleanValue()) {
            return;
        }
        if (this.zzJH == null || this.zzJH.isDestroyed()) {
            zzafr.zzaT("The webview does not exist. Ignoring action.");
            return;
        }
        zzbs.zzbB();
        zzahe.zzl(this.zzJH);
    }

    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putBoolean("com.google.android.gms.ads.internal.overlay.hasResumed", this.zzOM);
    }

    public final void onStart() {
        if (!((Boolean) zzbs.zzbL().zzd(zzmo.zzGA)).booleanValue()) {
            return;
        }
        if (this.zzJH == null || this.zzJH.isDestroyed()) {
            zzafr.zzaT("The webview does not exist. Ignoring action.");
            return;
        }
        zzbs.zzbB();
        zzahe.zzl(this.zzJH);
    }

    public final void onStop() {
        if (((Boolean) zzbs.zzbL().zzd(zzmo.zzGA)).booleanValue() && this.zzJH != null && (!this.mActivity.isFinishing() || this.zzOH == null)) {
            zzbs.zzbB();
            zzahe.zzk(this.zzJH);
        }
        zzfM();
    }

    public final void setRequestedOrientation(int i) {
        this.mActivity.setRequestedOrientation(i);
    }

    public final void zza(View view, WebChromeClient.CustomViewCallback customViewCallback) {
        this.zzOK = new FrameLayout(this.mActivity);
        this.zzOK.setBackgroundColor(-16777216);
        this.zzOK.addView(view, -1, -1);
        this.mActivity.setContentView(this.zzOK);
        this.zzOU = true;
        this.zzOL = customViewCallback;
        this.zzOJ = true;
    }

    public final void zza(boolean z, boolean z2) {
        if (this.zzOI != null) {
            this.zzOI.zza(z, z2);
        }
    }

    public final void zzaa() {
        this.zzOU = true;
    }

    public final void zzfI() {
        if (this.zzOG != null && this.zzOJ) {
            setRequestedOrientation(this.zzOG.orientation);
        }
        if (this.zzOK != null) {
            this.mActivity.setContentView(this.zzOO);
            this.zzOU = true;
            this.zzOK.removeAllViews();
            this.zzOK = null;
        }
        if (this.zzOL != null) {
            this.zzOL.onCustomViewHidden();
            this.zzOL = null;
        }
        this.zzOJ = false;
    }

    public final void zzfJ() {
        this.zzOQ = 1;
        this.mActivity.finish();
    }

    public final boolean zzfK() {
        this.zzOQ = 0;
        if (this.zzJH == null) {
            return true;
        }
        boolean zziC = this.zzJH.zziC();
        if (zziC) {
            return zziC;
        }
        this.zzJH.zza("onbackblocked", (Map<String, ?>) Collections.emptyMap());
        return zziC;
    }

    public final void zzfL() {
        this.zzOO.removeView(this.zzOI);
        zzr(true);
    }

    /* access modifiers changed from: package-private */
    public final void zzfN() {
        if (!this.zzOW) {
            this.zzOW = true;
            if (this.zzJH != null) {
                this.zzOO.removeView(this.zzJH.getView());
                if (this.zzOH != null) {
                    this.zzJH.setContext(this.zzOH.zzqD);
                    this.zzJH.zzA(false);
                    this.zzOH.parent.addView(this.zzJH.getView(), this.zzOH.index, this.zzOH.zzPa);
                    this.zzOH = null;
                } else if (this.mActivity.getApplicationContext() != null) {
                    this.zzJH.setContext(this.mActivity.getApplicationContext());
                }
                this.zzJH = null;
            }
            if (this.zzOG != null && this.zzOG.zzPf != null) {
                this.zzOG.zzPf.zzaA();
            }
        }
    }

    public final void zzfO() {
        if (this.zzOP) {
            this.zzOP = false;
            zzfP();
        }
    }

    public final void zzfQ() {
        this.zzOO.zzOZ = true;
    }

    public final void zzfR() {
        synchronized (this.zzOR) {
            this.zzOT = true;
            if (this.zzOS != null) {
                zzagz.zzZr.removeCallbacks(this.zzOS);
                zzagz.zzZr.post(this.zzOS);
            }
        }
    }

    public final void zzo(IObjectWrapper iObjectWrapper) {
        if (((Boolean) zzbs.zzbL().zzd(zzmo.zzGz)).booleanValue() && zzq.isAtLeastN()) {
            zzbs.zzbz();
            if (zzagz.zza(this.mActivity, (Configuration) zzn.zzE(iObjectWrapper))) {
                this.mActivity.getWindow().addFlags(1024);
                this.mActivity.getWindow().clearFlags(2048);
                return;
            }
            this.mActivity.getWindow().addFlags(2048);
            this.mActivity.getWindow().clearFlags(1024);
        }
    }
}

package com.google.android.gms.internal;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.common.util.zzf;
import java.util.Set;

@zzzn
public final class zzwk extends zzwu {
    private static Set<String> zzNy = zzf.zzb("top-left", "top-right", "top-center", "center", "bottom-left", "bottom-right", "bottom-center");
    private final Object mLock = new Object();
    private final zzaka zzJH;
    private boolean zzNA = true;
    private int zzNB = 0;
    private int zzNC = 0;
    private int zzND = 0;
    private int zzNE = 0;
    private ImageView zzNF;
    private LinearLayout zzNG;
    private zzwv zzNH;
    private PopupWindow zzNI;
    private RelativeLayout zzNJ;
    private ViewGroup zzNK;
    private final Activity zzNo;
    private String zzNz = "top-right";
    private int zzrW = -1;
    private int zzrX = -1;
    private zziv zzuZ;

    public zzwk(zzaka zzaka, zzwv zzwv) {
        super(zzaka, "resize");
        this.zzJH = zzaka;
        this.zzNo = zzaka.zzis();
        this.zzNH = zzwv;
    }

    private final void zza(int i, int i2) {
        zzb(i, i2 - zzbs.zzbz().zzh(this.zzNo)[0], this.zzrW, this.zzrX);
    }

    private final int[] zzfB() {
        boolean z;
        int i;
        int i2;
        int[] zzg = zzbs.zzbz().zzg(this.zzNo);
        int[] zzh = zzbs.zzbz().zzh(this.zzNo);
        int i3 = zzg[0];
        int i4 = zzg[1];
        if (this.zzrW < 50 || this.zzrW > i3) {
            zzafr.zzaT("Width is too small or too large.");
            z = false;
        } else if (this.zzrX < 50 || this.zzrX > i4) {
            zzafr.zzaT("Height is too small or too large.");
            z = false;
        } else if (this.zzrX == i4 && this.zzrW == i3) {
            zzafr.zzaT("Cannot resize to a full-screen ad.");
            z = false;
        } else {
            if (this.zzNA) {
                String str = this.zzNz;
                char c = 65535;
                switch (str.hashCode()) {
                    case -1364013995:
                        if (str.equals("center")) {
                            c = 2;
                            break;
                        }
                        break;
                    case -1012429441:
                        if (str.equals("top-left")) {
                            c = 0;
                            break;
                        }
                        break;
                    case -655373719:
                        if (str.equals("bottom-left")) {
                            c = 3;
                            break;
                        }
                        break;
                    case 1163912186:
                        if (str.equals("bottom-right")) {
                            c = 5;
                            break;
                        }
                        break;
                    case 1288627767:
                        if (str.equals("bottom-center")) {
                            c = 4;
                            break;
                        }
                        break;
                    case 1755462605:
                        if (str.equals("top-center")) {
                            c = 1;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        i = this.zzND + this.zzNB;
                        i2 = this.zzNC + this.zzNE;
                        break;
                    case 1:
                        i = ((this.zzNB + this.zzND) + (this.zzrW / 2)) - 25;
                        i2 = this.zzNC + this.zzNE;
                        break;
                    case 2:
                        i = ((this.zzNB + this.zzND) + (this.zzrW / 2)) - 25;
                        i2 = ((this.zzNC + this.zzNE) + (this.zzrX / 2)) - 25;
                        break;
                    case 3:
                        i = this.zzND + this.zzNB;
                        i2 = ((this.zzNC + this.zzNE) + this.zzrX) - 50;
                        break;
                    case 4:
                        i = ((this.zzNB + this.zzND) + (this.zzrW / 2)) - 25;
                        i2 = ((this.zzNC + this.zzNE) + this.zzrX) - 50;
                        break;
                    case 5:
                        i = ((this.zzNB + this.zzND) + this.zzrW) - 50;
                        i2 = ((this.zzNC + this.zzNE) + this.zzrX) - 50;
                        break;
                    default:
                        i = ((this.zzNB + this.zzND) + this.zzrW) - 50;
                        i2 = this.zzNC + this.zzNE;
                        break;
                }
                if (i < 0 || i + 50 > i3 || i2 < zzh[0] || i2 + 50 > zzh[1]) {
                    z = false;
                }
            }
            z = true;
        }
        if (!z) {
            return null;
        }
        if (this.zzNA) {
            return new int[]{this.zzNB + this.zzND, this.zzNC + this.zzNE};
        }
        int[] zzg2 = zzbs.zzbz().zzg(this.zzNo);
        int[] zzh2 = zzbs.zzbz().zzh(this.zzNo);
        int i5 = zzg2[0];
        int i6 = this.zzNB + this.zzND;
        int i7 = this.zzNC + this.zzNE;
        if (i6 < 0) {
            i6 = 0;
        } else if (this.zzrW + i6 > i5) {
            i6 = i5 - this.zzrW;
        }
        if (i7 < zzh2[0]) {
            i7 = zzh2[0];
        } else if (this.zzrX + i7 > zzh2[1]) {
            i7 = zzh2[1] - this.zzrX;
        }
        return new int[]{i6, i7};
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void execute(java.util.Map<java.lang.String, java.lang.String> r13) {
        /*
            r12 = this;
            r4 = -1
            r5 = 1
            r3 = 0
            java.lang.Object r6 = r12.mLock
            monitor-enter(r6)
            android.app.Activity r1 = r12.zzNo     // Catch:{ all -> 0x0020 }
            if (r1 != 0) goto L_0x0011
            java.lang.String r1 = "Not an activity context. Cannot resize."
            r12.zzan(r1)     // Catch:{ all -> 0x0020 }
            monitor-exit(r6)     // Catch:{ all -> 0x0020 }
        L_0x0010:
            return
        L_0x0011:
            com.google.android.gms.internal.zzaka r1 = r12.zzJH     // Catch:{ all -> 0x0020 }
            com.google.android.gms.internal.zziv r1 = r1.zzam()     // Catch:{ all -> 0x0020 }
            if (r1 != 0) goto L_0x0023
            java.lang.String r1 = "Webview is not yet available, size is not set."
            r12.zzan(r1)     // Catch:{ all -> 0x0020 }
            monitor-exit(r6)     // Catch:{ all -> 0x0020 }
            goto L_0x0010
        L_0x0020:
            r1 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0020 }
            throw r1
        L_0x0023:
            com.google.android.gms.internal.zzaka r1 = r12.zzJH     // Catch:{ all -> 0x0020 }
            com.google.android.gms.internal.zziv r1 = r1.zzam()     // Catch:{ all -> 0x0020 }
            boolean r1 = r1.zzAt     // Catch:{ all -> 0x0020 }
            if (r1 == 0) goto L_0x0034
            java.lang.String r1 = "Is interstitial. Cannot resize an interstitial."
            r12.zzan(r1)     // Catch:{ all -> 0x0020 }
            monitor-exit(r6)     // Catch:{ all -> 0x0020 }
            goto L_0x0010
        L_0x0034:
            com.google.android.gms.internal.zzaka r1 = r12.zzJH     // Catch:{ all -> 0x0020 }
            boolean r1 = r1.zziA()     // Catch:{ all -> 0x0020 }
            if (r1 == 0) goto L_0x0043
            java.lang.String r1 = "Cannot resize an expanded banner."
            r12.zzan(r1)     // Catch:{ all -> 0x0020 }
            monitor-exit(r6)     // Catch:{ all -> 0x0020 }
            goto L_0x0010
        L_0x0043:
            java.lang.String r1 = "width"
            java.lang.Object r1 = r13.get(r1)     // Catch:{ all -> 0x0020 }
            java.lang.CharSequence r1 = (java.lang.CharSequence) r1     // Catch:{ all -> 0x0020 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x0020 }
            if (r1 != 0) goto L_0x0062
            com.google.android.gms.ads.internal.zzbs.zzbz()     // Catch:{ all -> 0x0020 }
            java.lang.String r1 = "width"
            java.lang.Object r1 = r13.get(r1)     // Catch:{ all -> 0x0020 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x0020 }
            int r1 = com.google.android.gms.internal.zzagz.zzaJ(r1)     // Catch:{ all -> 0x0020 }
            r12.zzrW = r1     // Catch:{ all -> 0x0020 }
        L_0x0062:
            java.lang.String r1 = "height"
            java.lang.Object r1 = r13.get(r1)     // Catch:{ all -> 0x0020 }
            java.lang.CharSequence r1 = (java.lang.CharSequence) r1     // Catch:{ all -> 0x0020 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x0020 }
            if (r1 != 0) goto L_0x0081
            com.google.android.gms.ads.internal.zzbs.zzbz()     // Catch:{ all -> 0x0020 }
            java.lang.String r1 = "height"
            java.lang.Object r1 = r13.get(r1)     // Catch:{ all -> 0x0020 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x0020 }
            int r1 = com.google.android.gms.internal.zzagz.zzaJ(r1)     // Catch:{ all -> 0x0020 }
            r12.zzrX = r1     // Catch:{ all -> 0x0020 }
        L_0x0081:
            java.lang.String r1 = "offsetX"
            java.lang.Object r1 = r13.get(r1)     // Catch:{ all -> 0x0020 }
            java.lang.CharSequence r1 = (java.lang.CharSequence) r1     // Catch:{ all -> 0x0020 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x0020 }
            if (r1 != 0) goto L_0x00a0
            com.google.android.gms.ads.internal.zzbs.zzbz()     // Catch:{ all -> 0x0020 }
            java.lang.String r1 = "offsetX"
            java.lang.Object r1 = r13.get(r1)     // Catch:{ all -> 0x0020 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x0020 }
            int r1 = com.google.android.gms.internal.zzagz.zzaJ(r1)     // Catch:{ all -> 0x0020 }
            r12.zzND = r1     // Catch:{ all -> 0x0020 }
        L_0x00a0:
            java.lang.String r1 = "offsetY"
            java.lang.Object r1 = r13.get(r1)     // Catch:{ all -> 0x0020 }
            java.lang.CharSequence r1 = (java.lang.CharSequence) r1     // Catch:{ all -> 0x0020 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x0020 }
            if (r1 != 0) goto L_0x00bf
            com.google.android.gms.ads.internal.zzbs.zzbz()     // Catch:{ all -> 0x0020 }
            java.lang.String r1 = "offsetY"
            java.lang.Object r1 = r13.get(r1)     // Catch:{ all -> 0x0020 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x0020 }
            int r1 = com.google.android.gms.internal.zzagz.zzaJ(r1)     // Catch:{ all -> 0x0020 }
            r12.zzNE = r1     // Catch:{ all -> 0x0020 }
        L_0x00bf:
            java.lang.String r1 = "allowOffscreen"
            java.lang.Object r1 = r13.get(r1)     // Catch:{ all -> 0x0020 }
            java.lang.CharSequence r1 = (java.lang.CharSequence) r1     // Catch:{ all -> 0x0020 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x0020 }
            if (r1 != 0) goto L_0x00db
            java.lang.String r1 = "allowOffscreen"
            java.lang.Object r1 = r13.get(r1)     // Catch:{ all -> 0x0020 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x0020 }
            boolean r1 = java.lang.Boolean.parseBoolean(r1)     // Catch:{ all -> 0x0020 }
            r12.zzNA = r1     // Catch:{ all -> 0x0020 }
        L_0x00db:
            java.lang.String r1 = "customClosePosition"
            java.lang.Object r1 = r13.get(r1)     // Catch:{ all -> 0x0020 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x0020 }
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x0020 }
            if (r2 != 0) goto L_0x00eb
            r12.zzNz = r1     // Catch:{ all -> 0x0020 }
        L_0x00eb:
            int r1 = r12.zzrW     // Catch:{ all -> 0x0020 }
            if (r1 < 0) goto L_0x00fe
            int r1 = r12.zzrX     // Catch:{ all -> 0x0020 }
            if (r1 < 0) goto L_0x00fe
            r1 = r5
        L_0x00f4:
            if (r1 != 0) goto L_0x0100
            java.lang.String r1 = "Invalid width and height options. Cannot resize."
            r12.zzan(r1)     // Catch:{ all -> 0x0020 }
            monitor-exit(r6)     // Catch:{ all -> 0x0020 }
            goto L_0x0010
        L_0x00fe:
            r1 = r3
            goto L_0x00f4
        L_0x0100:
            android.app.Activity r1 = r12.zzNo     // Catch:{ all -> 0x0020 }
            android.view.Window r7 = r1.getWindow()     // Catch:{ all -> 0x0020 }
            if (r7 == 0) goto L_0x010e
            android.view.View r1 = r7.getDecorView()     // Catch:{ all -> 0x0020 }
            if (r1 != 0) goto L_0x0116
        L_0x010e:
            java.lang.String r1 = "Activity context is not ready, cannot get window or decor view."
            r12.zzan(r1)     // Catch:{ all -> 0x0020 }
            monitor-exit(r6)     // Catch:{ all -> 0x0020 }
            goto L_0x0010
        L_0x0116:
            int[] r8 = r12.zzfB()     // Catch:{ all -> 0x0020 }
            if (r8 != 0) goto L_0x0124
            java.lang.String r1 = "Resize location out of screen or close button is not visible."
            r12.zzan(r1)     // Catch:{ all -> 0x0020 }
            monitor-exit(r6)     // Catch:{ all -> 0x0020 }
            goto L_0x0010
        L_0x0124:
            com.google.android.gms.internal.zzji.zzds()     // Catch:{ all -> 0x0020 }
            android.app.Activity r1 = r12.zzNo     // Catch:{ all -> 0x0020 }
            int r2 = r12.zzrW     // Catch:{ all -> 0x0020 }
            int r9 = com.google.android.gms.internal.zzaiy.zzc(r1, r2)     // Catch:{ all -> 0x0020 }
            com.google.android.gms.internal.zzji.zzds()     // Catch:{ all -> 0x0020 }
            android.app.Activity r1 = r12.zzNo     // Catch:{ all -> 0x0020 }
            int r2 = r12.zzrX     // Catch:{ all -> 0x0020 }
            int r10 = com.google.android.gms.internal.zzaiy.zzc(r1, r2)     // Catch:{ all -> 0x0020 }
            com.google.android.gms.internal.zzaka r1 = r12.zzJH     // Catch:{ all -> 0x0020 }
            android.view.View r1 = r1.getView()     // Catch:{ all -> 0x0020 }
            android.view.ViewParent r2 = r1.getParent()     // Catch:{ all -> 0x0020 }
            if (r2 == 0) goto L_0x0286
            boolean r1 = r2 instanceof android.view.ViewGroup     // Catch:{ all -> 0x0020 }
            if (r1 == 0) goto L_0x0286
            r0 = r2
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0     // Catch:{ all -> 0x0020 }
            r1 = r0
            com.google.android.gms.internal.zzaka r11 = r12.zzJH     // Catch:{ all -> 0x0020 }
            android.view.View r11 = r11.getView()     // Catch:{ all -> 0x0020 }
            r1.removeView(r11)     // Catch:{ all -> 0x0020 }
            android.widget.PopupWindow r1 = r12.zzNI     // Catch:{ all -> 0x0020 }
            if (r1 != 0) goto L_0x027f
            android.view.ViewGroup r2 = (android.view.ViewGroup) r2     // Catch:{ all -> 0x0020 }
            r12.zzNK = r2     // Catch:{ all -> 0x0020 }
            com.google.android.gms.ads.internal.zzbs.zzbz()     // Catch:{ all -> 0x0020 }
            com.google.android.gms.internal.zzaka r1 = r12.zzJH     // Catch:{ all -> 0x0020 }
            android.view.View r1 = r1.getView()     // Catch:{ all -> 0x0020 }
            android.graphics.Bitmap r1 = com.google.android.gms.internal.zzagz.zzl(r1)     // Catch:{ all -> 0x0020 }
            android.widget.ImageView r2 = new android.widget.ImageView     // Catch:{ all -> 0x0020 }
            android.app.Activity r11 = r12.zzNo     // Catch:{ all -> 0x0020 }
            r2.<init>(r11)     // Catch:{ all -> 0x0020 }
            r12.zzNF = r2     // Catch:{ all -> 0x0020 }
            android.widget.ImageView r2 = r12.zzNF     // Catch:{ all -> 0x0020 }
            r2.setImageBitmap(r1)     // Catch:{ all -> 0x0020 }
            com.google.android.gms.internal.zzaka r1 = r12.zzJH     // Catch:{ all -> 0x0020 }
            com.google.android.gms.internal.zziv r1 = r1.zzam()     // Catch:{ all -> 0x0020 }
            r12.zzuZ = r1     // Catch:{ all -> 0x0020 }
            android.view.ViewGroup r1 = r12.zzNK     // Catch:{ all -> 0x0020 }
            android.widget.ImageView r2 = r12.zzNF     // Catch:{ all -> 0x0020 }
            r1.addView(r2)     // Catch:{ all -> 0x0020 }
        L_0x0189:
            android.widget.RelativeLayout r1 = new android.widget.RelativeLayout     // Catch:{ all -> 0x0020 }
            android.app.Activity r2 = r12.zzNo     // Catch:{ all -> 0x0020 }
            r1.<init>(r2)     // Catch:{ all -> 0x0020 }
            r12.zzNJ = r1     // Catch:{ all -> 0x0020 }
            android.widget.RelativeLayout r1 = r12.zzNJ     // Catch:{ all -> 0x0020 }
            r2 = 0
            r1.setBackgroundColor(r2)     // Catch:{ all -> 0x0020 }
            android.widget.RelativeLayout r1 = r12.zzNJ     // Catch:{ all -> 0x0020 }
            android.view.ViewGroup$LayoutParams r2 = new android.view.ViewGroup$LayoutParams     // Catch:{ all -> 0x0020 }
            r2.<init>(r9, r10)     // Catch:{ all -> 0x0020 }
            r1.setLayoutParams(r2)     // Catch:{ all -> 0x0020 }
            com.google.android.gms.ads.internal.zzbs.zzbz()     // Catch:{ all -> 0x0020 }
            android.widget.RelativeLayout r1 = r12.zzNJ     // Catch:{ all -> 0x0020 }
            r2 = 0
            android.widget.PopupWindow r1 = com.google.android.gms.internal.zzagz.zza((android.view.View) r1, (int) r9, (int) r10, (boolean) r2)     // Catch:{ all -> 0x0020 }
            r12.zzNI = r1     // Catch:{ all -> 0x0020 }
            android.widget.PopupWindow r1 = r12.zzNI     // Catch:{ all -> 0x0020 }
            r2 = 1
            r1.setOutsideTouchable(r2)     // Catch:{ all -> 0x0020 }
            android.widget.PopupWindow r1 = r12.zzNI     // Catch:{ all -> 0x0020 }
            r2 = 1
            r1.setTouchable(r2)     // Catch:{ all -> 0x0020 }
            android.widget.PopupWindow r2 = r12.zzNI     // Catch:{ all -> 0x0020 }
            boolean r1 = r12.zzNA     // Catch:{ all -> 0x0020 }
            if (r1 != 0) goto L_0x028e
            r1 = r5
        L_0x01c1:
            r2.setClippingEnabled(r1)     // Catch:{ all -> 0x0020 }
            android.widget.RelativeLayout r1 = r12.zzNJ     // Catch:{ all -> 0x0020 }
            com.google.android.gms.internal.zzaka r2 = r12.zzJH     // Catch:{ all -> 0x0020 }
            android.view.View r2 = r2.getView()     // Catch:{ all -> 0x0020 }
            r9 = -1
            r10 = -1
            r1.addView(r2, r9, r10)     // Catch:{ all -> 0x0020 }
            android.widget.LinearLayout r1 = new android.widget.LinearLayout     // Catch:{ all -> 0x0020 }
            android.app.Activity r2 = r12.zzNo     // Catch:{ all -> 0x0020 }
            r1.<init>(r2)     // Catch:{ all -> 0x0020 }
            r12.zzNG = r1     // Catch:{ all -> 0x0020 }
            android.widget.RelativeLayout$LayoutParams r2 = new android.widget.RelativeLayout$LayoutParams     // Catch:{ all -> 0x0020 }
            com.google.android.gms.internal.zzji.zzds()     // Catch:{ all -> 0x0020 }
            android.app.Activity r1 = r12.zzNo     // Catch:{ all -> 0x0020 }
            r9 = 50
            int r1 = com.google.android.gms.internal.zzaiy.zzc(r1, r9)     // Catch:{ all -> 0x0020 }
            com.google.android.gms.internal.zzji.zzds()     // Catch:{ all -> 0x0020 }
            android.app.Activity r9 = r12.zzNo     // Catch:{ all -> 0x0020 }
            r10 = 50
            int r9 = com.google.android.gms.internal.zzaiy.zzc(r9, r10)     // Catch:{ all -> 0x0020 }
            r2.<init>(r1, r9)     // Catch:{ all -> 0x0020 }
            java.lang.String r1 = r12.zzNz     // Catch:{ all -> 0x0020 }
            int r9 = r1.hashCode()     // Catch:{ all -> 0x0020 }
            switch(r9) {
                case -1364013995: goto L_0x02a7;
                case -1012429441: goto L_0x0291;
                case -655373719: goto L_0x02b2;
                case 1163912186: goto L_0x02c8;
                case 1288627767: goto L_0x02bd;
                case 1755462605: goto L_0x029c;
                default: goto L_0x01fe;
            }     // Catch:{ all -> 0x0020 }
        L_0x01fe:
            r1 = r4
        L_0x01ff:
            switch(r1) {
                case 0: goto L_0x02d3;
                case 1: goto L_0x02df;
                case 2: goto L_0x02eb;
                case 3: goto L_0x02f2;
                case 4: goto L_0x02fe;
                case 5: goto L_0x030a;
                default: goto L_0x0202;
            }     // Catch:{ all -> 0x0020 }
        L_0x0202:
            r1 = 10
            r2.addRule(r1)     // Catch:{ all -> 0x0020 }
            r1 = 11
            r2.addRule(r1)     // Catch:{ all -> 0x0020 }
        L_0x020c:
            android.widget.LinearLayout r1 = r12.zzNG     // Catch:{ all -> 0x0020 }
            com.google.android.gms.internal.zzwl r3 = new com.google.android.gms.internal.zzwl     // Catch:{ all -> 0x0020 }
            r3.<init>(r12)     // Catch:{ all -> 0x0020 }
            r1.setOnClickListener(r3)     // Catch:{ all -> 0x0020 }
            android.widget.LinearLayout r1 = r12.zzNG     // Catch:{ all -> 0x0020 }
            java.lang.String r3 = "Close button"
            r1.setContentDescription(r3)     // Catch:{ all -> 0x0020 }
            android.widget.RelativeLayout r1 = r12.zzNJ     // Catch:{ all -> 0x0020 }
            android.widget.LinearLayout r3 = r12.zzNG     // Catch:{ all -> 0x0020 }
            r1.addView(r3, r2)     // Catch:{ all -> 0x0020 }
            android.widget.PopupWindow r1 = r12.zzNI     // Catch:{ RuntimeException -> 0x0316 }
            android.view.View r2 = r7.getDecorView()     // Catch:{ RuntimeException -> 0x0316 }
            r3 = 0
            com.google.android.gms.internal.zzji.zzds()     // Catch:{ RuntimeException -> 0x0316 }
            android.app.Activity r4 = r12.zzNo     // Catch:{ RuntimeException -> 0x0316 }
            r5 = 0
            r5 = r8[r5]     // Catch:{ RuntimeException -> 0x0316 }
            int r4 = com.google.android.gms.internal.zzaiy.zzc(r4, r5)     // Catch:{ RuntimeException -> 0x0316 }
            com.google.android.gms.internal.zzji.zzds()     // Catch:{ RuntimeException -> 0x0316 }
            android.app.Activity r5 = r12.zzNo     // Catch:{ RuntimeException -> 0x0316 }
            r7 = 1
            r7 = r8[r7]     // Catch:{ RuntimeException -> 0x0316 }
            int r5 = com.google.android.gms.internal.zzaiy.zzc(r5, r7)     // Catch:{ RuntimeException -> 0x0316 }
            r1.showAtLocation(r2, r3, r4, r5)     // Catch:{ RuntimeException -> 0x0316 }
            r1 = 0
            r1 = r8[r1]     // Catch:{ all -> 0x0020 }
            r2 = 1
            r2 = r8[r2]     // Catch:{ all -> 0x0020 }
            com.google.android.gms.internal.zzwv r3 = r12.zzNH     // Catch:{ all -> 0x0020 }
            if (r3 == 0) goto L_0x0259
            com.google.android.gms.internal.zzwv r3 = r12.zzNH     // Catch:{ all -> 0x0020 }
            int r4 = r12.zzrW     // Catch:{ all -> 0x0020 }
            int r5 = r12.zzrX     // Catch:{ all -> 0x0020 }
            r3.zza(r1, r2, r4, r5)     // Catch:{ all -> 0x0020 }
        L_0x0259:
            com.google.android.gms.internal.zzaka r1 = r12.zzJH     // Catch:{ all -> 0x0020 }
            com.google.android.gms.internal.zziv r2 = new com.google.android.gms.internal.zziv     // Catch:{ all -> 0x0020 }
            android.app.Activity r3 = r12.zzNo     // Catch:{ all -> 0x0020 }
            com.google.android.gms.ads.AdSize r4 = new com.google.android.gms.ads.AdSize     // Catch:{ all -> 0x0020 }
            int r5 = r12.zzrW     // Catch:{ all -> 0x0020 }
            int r7 = r12.zzrX     // Catch:{ all -> 0x0020 }
            r4.<init>(r5, r7)     // Catch:{ all -> 0x0020 }
            r2.<init>(r3, r4)     // Catch:{ all -> 0x0020 }
            r1.zza((com.google.android.gms.internal.zziv) r2)     // Catch:{ all -> 0x0020 }
            r1 = 0
            r1 = r8[r1]     // Catch:{ all -> 0x0020 }
            r2 = 1
            r2 = r8[r2]     // Catch:{ all -> 0x0020 }
            r12.zza(r1, r2)     // Catch:{ all -> 0x0020 }
            java.lang.String r1 = "resized"
            r12.zzap(r1)     // Catch:{ all -> 0x0020 }
            monitor-exit(r6)     // Catch:{ all -> 0x0020 }
            goto L_0x0010
        L_0x027f:
            android.widget.PopupWindow r1 = r12.zzNI     // Catch:{ all -> 0x0020 }
            r1.dismiss()     // Catch:{ all -> 0x0020 }
            goto L_0x0189
        L_0x0286:
            java.lang.String r1 = "Webview is detached, probably in the middle of a resize or expand."
            r12.zzan(r1)     // Catch:{ all -> 0x0020 }
            monitor-exit(r6)     // Catch:{ all -> 0x0020 }
            goto L_0x0010
        L_0x028e:
            r1 = r3
            goto L_0x01c1
        L_0x0291:
            java.lang.String r5 = "top-left"
            boolean r1 = r1.equals(r5)     // Catch:{ all -> 0x0020 }
            if (r1 == 0) goto L_0x01fe
            r1 = r3
            goto L_0x01ff
        L_0x029c:
            java.lang.String r3 = "top-center"
            boolean r1 = r1.equals(r3)     // Catch:{ all -> 0x0020 }
            if (r1 == 0) goto L_0x01fe
            r1 = r5
            goto L_0x01ff
        L_0x02a7:
            java.lang.String r3 = "center"
            boolean r1 = r1.equals(r3)     // Catch:{ all -> 0x0020 }
            if (r1 == 0) goto L_0x01fe
            r1 = 2
            goto L_0x01ff
        L_0x02b2:
            java.lang.String r3 = "bottom-left"
            boolean r1 = r1.equals(r3)     // Catch:{ all -> 0x0020 }
            if (r1 == 0) goto L_0x01fe
            r1 = 3
            goto L_0x01ff
        L_0x02bd:
            java.lang.String r3 = "bottom-center"
            boolean r1 = r1.equals(r3)     // Catch:{ all -> 0x0020 }
            if (r1 == 0) goto L_0x01fe
            r1 = 4
            goto L_0x01ff
        L_0x02c8:
            java.lang.String r3 = "bottom-right"
            boolean r1 = r1.equals(r3)     // Catch:{ all -> 0x0020 }
            if (r1 == 0) goto L_0x01fe
            r1 = 5
            goto L_0x01ff
        L_0x02d3:
            r1 = 10
            r2.addRule(r1)     // Catch:{ all -> 0x0020 }
            r1 = 9
            r2.addRule(r1)     // Catch:{ all -> 0x0020 }
            goto L_0x020c
        L_0x02df:
            r1 = 10
            r2.addRule(r1)     // Catch:{ all -> 0x0020 }
            r1 = 14
            r2.addRule(r1)     // Catch:{ all -> 0x0020 }
            goto L_0x020c
        L_0x02eb:
            r1 = 13
            r2.addRule(r1)     // Catch:{ all -> 0x0020 }
            goto L_0x020c
        L_0x02f2:
            r1 = 12
            r2.addRule(r1)     // Catch:{ all -> 0x0020 }
            r1 = 9
            r2.addRule(r1)     // Catch:{ all -> 0x0020 }
            goto L_0x020c
        L_0x02fe:
            r1 = 12
            r2.addRule(r1)     // Catch:{ all -> 0x0020 }
            r1 = 14
            r2.addRule(r1)     // Catch:{ all -> 0x0020 }
            goto L_0x020c
        L_0x030a:
            r1 = 12
            r2.addRule(r1)     // Catch:{ all -> 0x0020 }
            r1 = 11
            r2.addRule(r1)     // Catch:{ all -> 0x0020 }
            goto L_0x020c
        L_0x0316:
            r1 = move-exception
            java.lang.String r2 = "Cannot show popup window: "
            java.lang.String r1 = r1.getMessage()     // Catch:{ all -> 0x0020 }
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ all -> 0x0020 }
            int r3 = r1.length()     // Catch:{ all -> 0x0020 }
            if (r3 == 0) goto L_0x0359
            java.lang.String r1 = r2.concat(r1)     // Catch:{ all -> 0x0020 }
        L_0x032b:
            r12.zzan(r1)     // Catch:{ all -> 0x0020 }
            android.widget.RelativeLayout r1 = r12.zzNJ     // Catch:{ all -> 0x0020 }
            com.google.android.gms.internal.zzaka r2 = r12.zzJH     // Catch:{ all -> 0x0020 }
            android.view.View r2 = r2.getView()     // Catch:{ all -> 0x0020 }
            r1.removeView(r2)     // Catch:{ all -> 0x0020 }
            android.view.ViewGroup r1 = r12.zzNK     // Catch:{ all -> 0x0020 }
            if (r1 == 0) goto L_0x0356
            android.view.ViewGroup r1 = r12.zzNK     // Catch:{ all -> 0x0020 }
            android.widget.ImageView r2 = r12.zzNF     // Catch:{ all -> 0x0020 }
            r1.removeView(r2)     // Catch:{ all -> 0x0020 }
            android.view.ViewGroup r1 = r12.zzNK     // Catch:{ all -> 0x0020 }
            com.google.android.gms.internal.zzaka r2 = r12.zzJH     // Catch:{ all -> 0x0020 }
            android.view.View r2 = r2.getView()     // Catch:{ all -> 0x0020 }
            r1.addView(r2)     // Catch:{ all -> 0x0020 }
            com.google.android.gms.internal.zzaka r1 = r12.zzJH     // Catch:{ all -> 0x0020 }
            com.google.android.gms.internal.zziv r2 = r12.zzuZ     // Catch:{ all -> 0x0020 }
            r1.zza((com.google.android.gms.internal.zziv) r2)     // Catch:{ all -> 0x0020 }
        L_0x0356:
            monitor-exit(r6)     // Catch:{ all -> 0x0020 }
            goto L_0x0010
        L_0x0359:
            java.lang.String r1 = new java.lang.String     // Catch:{ all -> 0x0020 }
            r1.<init>(r2)     // Catch:{ all -> 0x0020 }
            goto L_0x032b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzwk.execute(java.util.Map):void");
    }

    public final void zza(int i, int i2, boolean z) {
        synchronized (this.mLock) {
            this.zzNB = i;
            this.zzNC = i2;
            if (this.zzNI != null && z) {
                int[] zzfB = zzfB();
                if (zzfB != null) {
                    PopupWindow popupWindow = this.zzNI;
                    zzji.zzds();
                    int zzc = zzaiy.zzc(this.zzNo, zzfB[0]);
                    zzji.zzds();
                    popupWindow.update(zzc, zzaiy.zzc(this.zzNo, zzfB[1]), this.zzNI.getWidth(), this.zzNI.getHeight());
                    zza(zzfB[0], zzfB[1]);
                } else {
                    zzk(true);
                }
            }
        }
    }

    public final void zzb(int i, int i2) {
        this.zzNB = i;
        this.zzNC = i2;
    }

    public final boolean zzfC() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzNI != null;
        }
        return z;
    }

    public final void zzk(boolean z) {
        synchronized (this.mLock) {
            if (this.zzNI != null) {
                this.zzNI.dismiss();
                this.zzNJ.removeView(this.zzJH.getView());
                if (this.zzNK != null) {
                    this.zzNK.removeView(this.zzNF);
                    this.zzNK.addView(this.zzJH.getView());
                    this.zzJH.zza(this.zzuZ);
                }
                if (z) {
                    zzap("default");
                    if (this.zzNH != null) {
                        this.zzNH.zzaN();
                    }
                }
                this.zzNI = null;
                this.zzNJ = null;
                this.zzNK = null;
                this.zzNG = null;
            }
        }
    }
}

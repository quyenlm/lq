package com.google.android.gms.ads.internal;

import android.app.Activity;
import android.content.Context;
import android.os.RemoteException;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import com.google.android.gms.ads.internal.overlay.zzm;
import com.google.android.gms.common.annotation.KeepForSdkWithMembers;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.internal.zzacr;
import com.google.android.gms.internal.zzacy;
import com.google.android.gms.internal.zzagz;
import com.google.android.gms.internal.zzaje;
import com.google.android.gms.internal.zziv;
import com.google.android.gms.internal.zzju;
import com.google.android.gms.internal.zzjz;
import com.google.android.gms.internal.zzki;
import com.google.android.gms.internal.zzkn;
import com.google.android.gms.internal.zzop;
import com.google.android.gms.internal.zzow;
import com.google.android.gms.internal.zzuq;
import com.google.android.gms.internal.zzwx;
import com.google.android.gms.internal.zzxj;
import com.google.android.gms.internal.zzzn;

@Keep
@KeepForSdkWithMembers
@DynamiteApi
@zzzn
public class ClientApi extends zzki {
    public zzju createAdLoaderBuilder(IObjectWrapper iObjectWrapper, String str, zzuq zzuq, int i) {
        Context context = (Context) zzn.zzE(iObjectWrapper);
        zzbs.zzbz();
        return new zzak(context, str, zzuq, new zzaje(11020000, i, true, zzagz.zzO(context)), zzv.zzaQ());
    }

    public zzwx createAdOverlay(IObjectWrapper iObjectWrapper) {
        return new zzm((Activity) zzn.zzE(iObjectWrapper));
    }

    public zzjz createBannerAdManager(IObjectWrapper iObjectWrapper, zziv zziv, String str, zzuq zzuq, int i) throws RemoteException {
        Context context = (Context) zzn.zzE(iObjectWrapper);
        zzbs.zzbz();
        return new zzx(context, zziv, str, zzuq, new zzaje(11020000, i, true, zzagz.zzO(context)), zzv.zzaQ());
    }

    public zzxj createInAppPurchaseManager(IObjectWrapper iObjectWrapper) {
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0035, code lost:
        if (((java.lang.Boolean) com.google.android.gms.ads.internal.zzbs.zzbL().zzd(com.google.android.gms.internal.zzmo.zzDY)).booleanValue() == false) goto L_0x0037;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0049, code lost:
        if (((java.lang.Boolean) com.google.android.gms.ads.internal.zzbs.zzbL().zzd(com.google.android.gms.internal.zzmo.zzDZ)).booleanValue() != false) goto L_0x004b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x004b, code lost:
        r1 = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.gms.internal.zzjz createInterstitialAdManager(com.google.android.gms.dynamic.IObjectWrapper r14, com.google.android.gms.internal.zziv r15, java.lang.String r16, com.google.android.gms.internal.zzuq r17, int r18) throws android.os.RemoteException {
        /*
            r13 = this;
            java.lang.Object r2 = com.google.android.gms.dynamic.zzn.zzE(r14)
            android.content.Context r2 = (android.content.Context) r2
            com.google.android.gms.internal.zzmo.initialize(r2)
            com.google.android.gms.internal.zzaje r5 = new com.google.android.gms.internal.zzaje
            r1 = 11020000(0xa826e0, float:1.5442309E-38)
            r3 = 1
            com.google.android.gms.ads.internal.zzbs.zzbz()
            boolean r4 = com.google.android.gms.internal.zzagz.zzO(r2)
            r0 = r18
            r5.<init>(r1, r0, r3, r4)
            java.lang.String r1 = "reward_mb"
            java.lang.String r3 = r15.zzAs
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x0037
            com.google.android.gms.internal.zzme<java.lang.Boolean> r1 = com.google.android.gms.internal.zzmo.zzDY
            com.google.android.gms.internal.zzmm r4 = com.google.android.gms.ads.internal.zzbs.zzbL()
            java.lang.Object r1 = r4.zzd(r1)
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            if (r1 != 0) goto L_0x004b
        L_0x0037:
            if (r3 == 0) goto L_0x005c
            com.google.android.gms.internal.zzme<java.lang.Boolean> r1 = com.google.android.gms.internal.zzmo.zzDZ
            com.google.android.gms.internal.zzmm r3 = com.google.android.gms.ads.internal.zzbs.zzbL()
            java.lang.Object r1 = r3.zzd(r1)
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            if (r1 == 0) goto L_0x005c
        L_0x004b:
            r1 = 1
        L_0x004c:
            if (r1 == 0) goto L_0x005e
            com.google.android.gms.internal.zztq r1 = new com.google.android.gms.internal.zztq
            com.google.android.gms.ads.internal.zzv r6 = com.google.android.gms.ads.internal.zzv.zzaQ()
            r3 = r16
            r4 = r17
            r1.<init>(r2, r3, r4, r5, r6)
        L_0x005b:
            return r1
        L_0x005c:
            r1 = 0
            goto L_0x004c
        L_0x005e:
            com.google.android.gms.ads.internal.zzal r6 = new com.google.android.gms.ads.internal.zzal
            com.google.android.gms.ads.internal.zzv r12 = com.google.android.gms.ads.internal.zzv.zzaQ()
            r7 = r2
            r8 = r15
            r9 = r16
            r10 = r17
            r11 = r5
            r6.<init>(r7, r8, r9, r10, r11, r12)
            r1 = r6
            goto L_0x005b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.ads.internal.ClientApi.createInterstitialAdManager(com.google.android.gms.dynamic.IObjectWrapper, com.google.android.gms.internal.zziv, java.lang.String, com.google.android.gms.internal.zzuq, int):com.google.android.gms.internal.zzjz");
    }

    public zzow createNativeAdViewDelegate(IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2) {
        return new zzop((FrameLayout) zzn.zzE(iObjectWrapper), (FrameLayout) zzn.zzE(iObjectWrapper2));
    }

    public zzacy createRewardedVideoAd(IObjectWrapper iObjectWrapper, zzuq zzuq, int i) {
        Context context = (Context) zzn.zzE(iObjectWrapper);
        zzbs.zzbz();
        return new zzacr(context, zzv.zzaQ(), zzuq, new zzaje(11020000, i, true, zzagz.zzO(context)));
    }

    public zzjz createSearchAdManager(IObjectWrapper iObjectWrapper, zziv zziv, String str, int i) throws RemoteException {
        Context context = (Context) zzn.zzE(iObjectWrapper);
        zzbs.zzbz();
        return new zzbm(context, zziv, str, new zzaje(11020000, i, true, zzagz.zzO(context)));
    }

    @Nullable
    public zzkn getMobileAdsSettingsManager(IObjectWrapper iObjectWrapper) {
        return null;
    }

    public zzkn getMobileAdsSettingsManagerWithClientJarVersion(IObjectWrapper iObjectWrapper, int i) {
        Context context = (Context) zzn.zzE(iObjectWrapper);
        zzbs.zzbz();
        return zzax.zza(context, new zzaje(11020000, i, true, zzagz.zzO(context)));
    }
}

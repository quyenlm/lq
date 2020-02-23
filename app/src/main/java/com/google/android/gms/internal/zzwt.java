package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.google.android.gms.ads.internal.zzbs;
import java.util.Map;

@zzzn
public final class zzwt extends zzwu implements zzrd {
    private final Context mContext;
    private final zzaka zzJH;
    private final zzlz zzNW;
    private float zzNX;
    private int zzNY = -1;
    private int zzNZ = -1;
    private int zzOa;
    private int zzOb = -1;
    private int zzOc = -1;
    private int zzOd = -1;
    private int zzOe = -1;
    private final WindowManager zzwR;
    private DisplayMetrics zzxF;

    public zzwt(zzaka zzaka, Context context, zzlz zzlz) {
        super(zzaka);
        this.zzJH = zzaka;
        this.mContext = context;
        this.zzNW = zzlz;
        this.zzwR = (WindowManager) context.getSystemService("window");
    }

    public final void zza(zzaka zzaka, Map<String, String> map) {
        this.zzxF = new DisplayMetrics();
        Display defaultDisplay = this.zzwR.getDefaultDisplay();
        defaultDisplay.getMetrics(this.zzxF);
        this.zzNX = this.zzxF.density;
        this.zzOa = defaultDisplay.getRotation();
        zzji.zzds();
        this.zzNY = zzaiy.zzb(this.zzxF, this.zzxF.widthPixels);
        zzji.zzds();
        this.zzNZ = zzaiy.zzb(this.zzxF, this.zzxF.heightPixels);
        Activity zzis = this.zzJH.zzis();
        if (zzis == null || zzis.getWindow() == null) {
            this.zzOb = this.zzNY;
            this.zzOc = this.zzNZ;
        } else {
            zzbs.zzbz();
            int[] zzf = zzagz.zzf(zzis);
            zzji.zzds();
            this.zzOb = zzaiy.zzb(this.zzxF, zzf[0]);
            zzji.zzds();
            this.zzOc = zzaiy.zzb(this.zzxF, zzf[1]);
        }
        if (this.zzJH.zzam().zzAt) {
            this.zzOd = this.zzNY;
            this.zzOe = this.zzNZ;
        } else {
            this.zzJH.measure(0, 0);
        }
        zza(this.zzNY, this.zzNZ, this.zzOb, this.zzOc, this.zzNX, this.zzOa);
        this.zzJH.zzb("onDeviceFeaturesReceived", new zzwq(new zzws().zzm(this.zzNW.zzdE()).zzl(this.zzNW.zzdF()).zzn(this.zzNW.zzdH()).zzo(this.zzNW.zzdG()).zzp(true)).toJson());
        int[] iArr = new int[2];
        this.zzJH.getLocationOnScreen(iArr);
        zzji.zzds();
        int zzd = zzaiy.zzd(this.mContext, iArr[0]);
        zzji.zzds();
        zzc(zzd, zzaiy.zzd(this.mContext, iArr[1]));
        if (zzafr.zzz(2)) {
            zzafr.zzaS("Dispatching Ready Event.");
        }
        zzao(this.zzJH.zziz().zzaP);
    }

    public final void zzc(int i, int i2) {
        int i3 = this.mContext instanceof Activity ? zzbs.zzbz().zzh((Activity) this.mContext)[0] : 0;
        if (this.zzJH.zzam() == null || !this.zzJH.zzam().zzAt) {
            zzji.zzds();
            this.zzOd = zzaiy.zzd(this.mContext, this.zzJH.getWidth());
            zzji.zzds();
            this.zzOe = zzaiy.zzd(this.mContext, this.zzJH.getHeight());
        }
        zzc(i, i2 - i3, this.zzOd, this.zzOe);
        this.zzJH.zziw().zzb(i, i2);
    }
}

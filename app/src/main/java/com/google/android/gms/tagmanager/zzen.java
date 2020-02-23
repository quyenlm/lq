package com.google.android.gms.tagmanager;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.google.android.gms.internal.zzbf;
import com.google.android.gms.internal.zzbr;
import java.util.Map;

final class zzen extends zzbr {
    private static final String ID = zzbf.RESOLUTION.toString();
    private final Context mContext;

    public zzen(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    public final boolean zzAE() {
        return true;
    }

    public final zzbr zzo(Map<String, zzbr> map) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        return zzgk.zzI(new StringBuilder(23).append(i).append("x").append(displayMetrics.heightPixels).toString());
    }
}

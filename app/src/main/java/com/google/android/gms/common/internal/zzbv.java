package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.view.View;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamic.zzp;
import com.google.android.gms.dynamic.zzq;

public final class zzbv extends zzp<zzbb> {
    private static final zzbv zzaIv = new zzbv();

    private zzbv() {
        super("com.google.android.gms.common.ui.SignInButtonCreatorImpl");
    }

    public static View zzc(Context context, int i, int i2) throws zzq {
        return zzaIv.zzd(context, i, i2);
    }

    private final View zzd(Context context, int i, int i2) throws zzq {
        try {
            zzbt zzbt = new zzbt(i, i2, (Scope[]) null);
            return (View) zzn.zzE(((zzbb) zzaS(context)).zza(zzn.zzw(context), zzbt));
        } catch (Exception e) {
            throw new zzq(new StringBuilder(64).append("Could not get button with size ").append(i).append(" and color ").append(i2).toString(), e);
        }
    }

    public final /* synthetic */ Object zzb(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.ISignInButtonCreator");
        return queryLocalInterface instanceof zzbb ? (zzbb) queryLocalInterface : new zzbc(iBinder);
    }
}

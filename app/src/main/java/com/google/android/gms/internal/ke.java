package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.common.api.Api;

public final class ke {
    private static Api.zzf<jx> zzajR = new Api.zzf<>();
    private static final Api.zza<jx, kg> zzbWH = new kf();
    public static final Api<kg> zzbWI = new Api<>("InternalFirebaseAuth.FIREBASE_AUTH_API", zzbWH, zzajR);

    public static iq zza(Context context, kg kgVar) {
        return new iq(context, kgVar);
    }
}

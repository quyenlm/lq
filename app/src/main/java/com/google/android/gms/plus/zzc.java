package com.google.android.gms.plus;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.internal.PlusCommonExtras;
import com.google.android.gms.plus.internal.zzh;
import com.google.android.gms.plus.internal.zzn;

final class zzc extends Api.zza<zzh, Plus.PlusOptions> {
    zzc() {
    }

    public final int getPriority() {
        return 2;
    }

    public final /* synthetic */ Api.zze zza(Context context, Looper looper, zzq zzq, Object obj, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        Plus.PlusOptions plusOptions = (Plus.PlusOptions) obj;
        if (plusOptions == null) {
            plusOptions = new Plus.PlusOptions((zzc) null);
        }
        return new zzh(context, looper, zzq, new zzn(zzq.zzrl().name, zzs.zzc(zzq.zzro()), (String[]) plusOptions.zzbAs.toArray(new String[0]), new String[0], context.getPackageName(), context.getPackageName(), (String) null, new PlusCommonExtras()), connectionCallbacks, onConnectionFailedListener);
    }
}

package com.google.android.gms.wallet;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.internal.gu;
import com.google.android.gms.wallet.Wallet;

final class zzaa extends Api.zza<gu, Wallet.WalletOptions> {
    zzaa() {
    }

    public final /* synthetic */ Api.zze zza(Context context, Looper looper, zzq zzq, Object obj, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        Wallet.WalletOptions walletOptions = (Wallet.WalletOptions) obj;
        if (walletOptions == null) {
            walletOptions = new Wallet.WalletOptions((zzaa) null);
        }
        return new gu(context, looper, zzq, connectionCallbacks, onConnectionFailedListener, walletOptions.environment, walletOptions.theme, walletOptions.zzbPS);
    }
}

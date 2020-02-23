package com.google.android.gms.internal;

import android.accounts.Account;
import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzz;
import com.google.android.gms.identity.intents.AddressConstants;
import com.google.android.gms.identity.intents.UserAddressRequest;

public final class zzcbe extends zzz<zzcbi> {
    private Activity mActivity;
    private final int mTheme;
    private final String zzakh;
    private zzcbf zzbgD;

    public zzcbe(Activity activity, Looper looper, zzq zzq, int i, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(activity, looper, 12, zzq, connectionCallbacks, onConnectionFailedListener);
        this.zzakh = zzq.getAccountName();
        this.mActivity = activity;
        this.mTheme = i;
    }

    public final void disconnect() {
        super.disconnect();
        if (this.zzbgD != null) {
            this.zzbgD.setActivity((Activity) null);
            this.zzbgD = null;
        }
    }

    public final void zza(UserAddressRequest userAddressRequest, int i) {
        super.zzre();
        this.zzbgD = new zzcbf(i, this.mActivity);
        try {
            Bundle bundle = new Bundle();
            bundle.putString("com.google.android.gms.identity.intents.EXTRA_CALLING_PACKAGE_NAME", getContext().getPackageName());
            if (!TextUtils.isEmpty(this.zzakh)) {
                bundle.putParcelable("com.google.android.gms.identity.intents.EXTRA_ACCOUNT", new Account(this.zzakh, "com.google"));
            }
            bundle.putInt("com.google.android.gms.identity.intents.EXTRA_THEME", this.mTheme);
            ((zzcbi) super.zzrf()).zza(this.zzbgD, userAddressRequest, bundle);
        } catch (RemoteException e) {
            Log.e("AddressClientImpl", "Exception requesting user address", e);
            Bundle bundle2 = new Bundle();
            bundle2.putInt(AddressConstants.Extras.EXTRA_ERROR_CODE, AddressConstants.ErrorCodes.ERROR_CODE_NO_APPLICABLE_ADDRESSES);
            this.zzbgD.zze(1, bundle2);
        }
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.identity.intents.internal.IAddressService");
        return queryLocalInterface instanceof zzcbi ? (zzcbi) queryLocalInterface : new zzcbj(iBinder);
    }

    /* access modifiers changed from: protected */
    public final String zzdb() {
        return "com.google.android.gms.identity.service.BIND";
    }

    /* access modifiers changed from: protected */
    public final String zzdc() {
        return "com.google.android.gms.identity.intents.internal.IAddressService";
    }

    public final boolean zzrg() {
        return true;
    }
}

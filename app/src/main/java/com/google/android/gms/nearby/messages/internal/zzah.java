package com.google.android.gms.nearby.messages.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzz;
import com.google.android.gms.internal.zzbaz;
import com.google.android.gms.internal.zzbdw;
import com.google.android.gms.internal.zzcpn;
import com.google.android.gms.internal.zzcpq;
import com.google.android.gms.internal.zzcpx;
import com.google.android.gms.internal.zzcpz;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.MessagesOptions;
import com.google.android.gms.nearby.messages.StatusCallback;
import com.google.android.gms.nearby.messages.SubscribeCallback;
import com.google.android.gms.nearby.messages.SubscribeOptions;

final class zzah extends zzz<zzs> {
    private final int zzbyi;
    private final zzcpz zzbze = new zzcpz();
    private final ClientAppContext zzbzf;

    @TargetApi(14)
    zzah(Context context, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, zzq zzq, MessagesOptions messagesOptions) {
        super(context, looper, 62, zzq, connectionCallbacks, onConnectionFailedListener);
        String zzrq = zzq.zzrq();
        int i = context instanceof Activity ? 1 : context instanceof Application ? 2 : context instanceof Service ? 3 : 0;
        if (messagesOptions != null) {
            this.zzbzf = new ClientAppContext(zzrq, (String) null, false, (String) null, i);
            this.zzbyi = messagesOptions.zzbyg;
        } else {
            this.zzbzf = new ClientAppContext(zzrq, (String) null, false, (String) null, i);
            this.zzbyi = -1;
        }
        if (i == 1) {
            Activity activity = (Activity) context;
            Log.v("NearbyMessagesClient", String.format("Registering ClientLifecycleSafetyNet's ActivityLifecycleCallbacks for %s", new Object[]{activity.getPackageName()}));
            activity.getApplication().registerActivityLifecycleCallbacks(new zzaj(activity, this));
        }
    }

    public final void disconnect() {
        try {
            zzbs(2);
        } catch (RemoteException e) {
            Log.v("NearbyMessagesClient", String.format("Failed to emit CLIENT_DISCONNECTED from override of GmsClient#disconnect(): %s", new Object[]{e}));
        }
        this.zzbze.clear();
        super.disconnect();
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final zzbdw<MessageListener> zza(GoogleApiClient googleApiClient, @Nullable MessageListener messageListener) {
        return this.zzbze.zzb(googleApiClient, messageListener).zzzX();
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final zzbdw<StatusCallback> zza(GoogleApiClient googleApiClient, @Nullable StatusCallback statusCallback) {
        return this.zzbze.zzb(googleApiClient, statusCallback).zzzX();
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final zzbdw<MessageListener> zza(@Nullable MessageListener messageListener) {
        zzcpn zzE = this.zzbze.zzE(messageListener);
        if (zzE == null) {
            return null;
        }
        return zzE.zzzX();
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final zzbdw<StatusCallback> zza(@Nullable StatusCallback statusCallback) {
        zzcpn zzE = this.zzbze.zzE(statusCallback);
        if (zzE == null) {
            return null;
        }
        return zzE.zzzX();
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzbdw<zzbaz<Status>> zzbdw, @Nullable zzbdw<MessageListener> zzbdw2) throws RemoteException {
        if (zzbdw2 != null) {
            ((zzs) zzrf()).zza(new zzbe(this.zzbze.zzh(zzbdw2), new zzcpq(zzbdw), (PendingIntent) null));
            this.zzbze.zzi(zzbdw2);
        }
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzbdw<zzbaz<Status>> zzbdw, zzbdw<MessageListener> zzbdw2, @Nullable zzbdw<SubscribeCallback> zzbdw3, SubscribeOptions subscribeOptions, @Nullable byte[] bArr) throws RemoteException {
        ((zzs) zzrf()).zza(new SubscribeRequest(this.zzbze.zzh(zzbdw2), subscribeOptions.getStrategy(), new zzcpq(zzbdw), subscribeOptions.getFilter(), (PendingIntent) null, (byte[]) null, zzbdw3 == null ? null : new zzcpx(zzbdw3), subscribeOptions.zzbyA));
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzbdw<zzbaz<Status>> zzbdw, zzaf zzaf) throws RemoteException {
        ((zzs) zzrf()).zza(new zzbc(zzaf, new zzcpq(zzbdw), this.zzbzf));
    }

    /* access modifiers changed from: package-private */
    public final void zzb(zzbdw<zzbaz<Status>> zzbdw, zzbdw<StatusCallback> zzbdw2) throws RemoteException {
        zzaz zzaz = new zzaz(new zzcpq(zzbdw), this.zzbze.zzh(zzbdw2));
        zzaz.zzbzu = true;
        ((zzs) zzrf()).zza(zzaz);
    }

    /* access modifiers changed from: package-private */
    public final void zzbs(int i) throws RemoteException {
        String str;
        switch (i) {
            case 1:
                str = "ACTIVITY_STOPPED";
                break;
            case 2:
                str = "CLIENT_DISCONNECTED";
                break;
            default:
                Log.w("NearbyMessagesClient", String.format("Received unknown/unforeseen client lifecycle event %d, can't do anything with it.", new Object[]{Integer.valueOf(i)}));
                return;
        }
        if (isConnected()) {
            zzj zzj = new zzj(i);
            Log.d("NearbyMessagesClient", String.format("Emitting client lifecycle event %s", new Object[]{str}));
            ((zzs) zzrf()).zza(zzj);
            return;
        }
        Log.d("NearbyMessagesClient", String.format("Failed to emit client lifecycle event %s due to GmsClient being disconnected", new Object[]{str}));
    }

    /* access modifiers changed from: package-private */
    public final void zzc(zzbdw<zzbaz<Status>> zzbdw, @Nullable zzbdw<StatusCallback> zzbdw2) throws RemoteException {
        if (zzbdw2 != null) {
            zzaz zzaz = new zzaz(new zzcpq(zzbdw), this.zzbze.zzh(zzbdw2));
            zzaz.zzbzu = false;
            ((zzs) zzrf()).zza(zzaz);
            this.zzbze.zzi(zzbdw2);
        }
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.nearby.messages.internal.INearbyMessagesService");
        return queryLocalInterface instanceof zzs ? (zzs) queryLocalInterface : new zzt(iBinder);
    }

    /* access modifiers changed from: protected */
    @NonNull
    public final String zzdb() {
        return "com.google.android.gms.nearby.messages.service.NearbyMessagesService.START";
    }

    /* access modifiers changed from: protected */
    @NonNull
    public final String zzdc() {
        return "com.google.android.gms.nearby.messages.internal.INearbyMessagesService";
    }

    /* access modifiers changed from: protected */
    @NonNull
    public final Bundle zzmo() {
        Bundle zzmo = super.zzmo();
        zzmo.putInt("NearbyPermissions", this.zzbyi);
        zzmo.putParcelable("ClientAppContext", this.zzbzf);
        return zzmo;
    }
}

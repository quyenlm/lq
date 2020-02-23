package com.google.android.gms.wearable.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzz;
import com.google.android.gms.common.zze;
import com.google.android.gms.internal.zzbaz;
import com.google.android.gms.internal.zzbdw;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public final class zzfw extends zzz<zzdn> {
    private final zzdp<Object> zzbTg;
    private final zzdp<Object> zzbTh;
    private final zzdp<ChannelApi.ChannelListener> zzbTi;
    private final zzdp<DataApi.DataListener> zzbTj;
    private final zzdp<MessageApi.MessageListener> zzbTk;
    private final zzdp<NodeApi.NodeListener> zzbTl;
    private final zzdp<Object> zzbTm;
    private final zzdp<CapabilityApi.CapabilityListener> zzbTn;
    private final zzgh zzbTo;
    private final ExecutorService zzbrV;

    public zzfw(Context context, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, zzq zzq) {
        this(context, looper, connectionCallbacks, onConnectionFailedListener, zzq, Executors.newCachedThreadPool(), zzgh.zzbz(context));
    }

    private zzfw(Context context, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, zzq zzq, ExecutorService executorService, zzgh zzgh) {
        super(context, looper, 14, zzq, connectionCallbacks, onConnectionFailedListener);
        this.zzbTg = new zzdp<>();
        this.zzbTh = new zzdp<>();
        this.zzbTi = new zzdp<>();
        this.zzbTj = new zzdp<>();
        this.zzbTk = new zzdp<>();
        this.zzbTl = new zzdp<>();
        this.zzbTm = new zzdp<>();
        this.zzbTn = new zzdp<>();
        this.zzbrV = (ExecutorService) zzbo.zzu(executorService);
        this.zzbTo = zzgh;
    }

    /* access modifiers changed from: protected */
    public final void zza(int i, IBinder iBinder, Bundle bundle, int i2) {
        if (Log.isLoggable("WearableClient", 2)) {
            Log.d("WearableClient", new StringBuilder(41).append("onPostInitHandler: statusCode ").append(i).toString());
        }
        if (i == 0) {
            this.zzbTg.zzam(iBinder);
            this.zzbTh.zzam(iBinder);
            this.zzbTi.zzam(iBinder);
            this.zzbTj.zzam(iBinder);
            this.zzbTk.zzam(iBinder);
            this.zzbTl.zzam(iBinder);
            this.zzbTm.zzam(iBinder);
            this.zzbTn.zzam(iBinder);
        }
        super.zza(i, iBinder, bundle, i2);
    }

    public final void zza(@NonNull zzj zzj) {
        int i = 0;
        if (!zzpe()) {
            try {
                Bundle bundle = getContext().getPackageManager().getApplicationInfo("com.google.android.wearable.app.cn", 128).metaData;
                if (bundle != null) {
                    i = bundle.getInt("com.google.android.wearable.api.version", 0);
                }
                if (i < zze.GOOGLE_PLAY_SERVICES_VERSION_CODE) {
                    Log.w("WearableClient", new StringBuilder(80).append("Android Wear out of date. Requires API version ").append(zze.GOOGLE_PLAY_SERVICES_VERSION_CODE).append(" but found ").append(i).toString());
                    Context context = getContext();
                    Context context2 = getContext();
                    Intent intent = new Intent("com.google.android.wearable.app.cn.UPDATE_ANDROID_WEAR").setPackage("com.google.android.wearable.app.cn");
                    if (context2.getPackageManager().resolveActivity(intent, 65536) == null) {
                        intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details").buildUpon().appendQueryParameter("id", "com.google.android.wearable.app.cn").build());
                    }
                    zza(zzj, 6, PendingIntent.getActivity(context, 0, intent, 0));
                    return;
                }
            } catch (PackageManager.NameNotFoundException e) {
                zza(zzj, 16, (PendingIntent) null);
                return;
            }
        }
        super.zza(zzj);
    }

    public final void zza(zzbaz<DataApi.GetFdForAssetResult> zzbaz, Asset asset) throws RemoteException {
        ((zzdn) zzrf()).zza((zzdi) new zzfn(zzbaz), asset);
    }

    public final void zza(zzbaz<Status> zzbaz, CapabilityApi.CapabilityListener capabilityListener) throws RemoteException {
        this.zzbTn.zza(this, zzbaz, capabilityListener);
    }

    public final void zza(zzbaz<Status> zzbaz, CapabilityApi.CapabilityListener capabilityListener, zzbdw<CapabilityApi.CapabilityListener> zzbdw, IntentFilter[] intentFilterArr) throws RemoteException {
        this.zzbTn.zza(this, zzbaz, capabilityListener, zzga.zze(zzbdw, intentFilterArr));
    }

    public final void zza(zzbaz<Status> zzbaz, ChannelApi.ChannelListener channelListener, zzbdw<ChannelApi.ChannelListener> zzbdw, String str, IntentFilter[] intentFilterArr) throws RemoteException {
        if (str == null) {
            this.zzbTi.zza(this, zzbaz, channelListener, zzga.zzd(zzbdw, intentFilterArr));
            return;
        }
        this.zzbTi.zza(this, zzbaz, new zzeu(str, channelListener), zzga.zza(zzbdw, str, intentFilterArr));
    }

    public final void zza(zzbaz<Status> zzbaz, ChannelApi.ChannelListener channelListener, String str) throws RemoteException {
        if (str == null) {
            this.zzbTi.zza(this, zzbaz, channelListener);
            return;
        }
        this.zzbTi.zza(this, zzbaz, new zzeu(str, channelListener));
    }

    public final void zza(zzbaz<Status> zzbaz, DataApi.DataListener dataListener) throws RemoteException {
        this.zzbTj.zza(this, zzbaz, dataListener);
    }

    public final void zza(zzbaz<Status> zzbaz, DataApi.DataListener dataListener, zzbdw<DataApi.DataListener> zzbdw, IntentFilter[] intentFilterArr) throws RemoteException {
        this.zzbTj.zza(this, zzbaz, dataListener, zzga.zza(zzbdw, intentFilterArr));
    }

    public final void zza(zzbaz<Status> zzbaz, MessageApi.MessageListener messageListener) throws RemoteException {
        this.zzbTk.zza(this, zzbaz, messageListener);
    }

    public final void zza(zzbaz<Status> zzbaz, MessageApi.MessageListener messageListener, zzbdw<MessageApi.MessageListener> zzbdw, IntentFilter[] intentFilterArr) throws RemoteException {
        this.zzbTk.zza(this, zzbaz, messageListener, zzga.zzb(zzbdw, intentFilterArr));
    }

    public final void zza(zzbaz<Status> zzbaz, NodeApi.NodeListener nodeListener) throws RemoteException {
        this.zzbTl.zza(this, zzbaz, nodeListener);
    }

    public final void zza(zzbaz<Status> zzbaz, NodeApi.NodeListener nodeListener, zzbdw<NodeApi.NodeListener> zzbdw, IntentFilter[] intentFilterArr) throws RemoteException {
        this.zzbTl.zza(this, zzbaz, nodeListener, zzga.zzc(zzbdw, intentFilterArr));
    }

    public final void zza(zzbaz<DataApi.DataItemResult> zzbaz, PutDataRequest putDataRequest) throws RemoteException {
        for (Map.Entry<String, Asset> value : putDataRequest.getAssets().entrySet()) {
            Asset asset = (Asset) value.getValue();
            if (asset.getData() == null && asset.getDigest() == null && asset.getFd() == null && asset.getUri() == null) {
                String valueOf = String.valueOf(putDataRequest.getUri());
                String valueOf2 = String.valueOf(asset);
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 33 + String.valueOf(valueOf2).length()).append("Put for ").append(valueOf).append(" contains invalid asset: ").append(valueOf2).toString());
            }
        }
        PutDataRequest zzt = PutDataRequest.zzt(putDataRequest.getUri());
        zzt.setData(putDataRequest.getData());
        if (putDataRequest.isUrgent()) {
            zzt.setUrgent();
        }
        ArrayList arrayList = new ArrayList();
        for (Map.Entry next : putDataRequest.getAssets().entrySet()) {
            Asset asset2 = (Asset) next.getValue();
            if (asset2.getData() != null) {
                try {
                    ParcelFileDescriptor[] createPipe = ParcelFileDescriptor.createPipe();
                    if (Log.isLoggable("WearableClient", 3)) {
                        String valueOf3 = String.valueOf(asset2);
                        String valueOf4 = String.valueOf(createPipe[0]);
                        String valueOf5 = String.valueOf(createPipe[1]);
                        Log.d("WearableClient", new StringBuilder(String.valueOf(valueOf3).length() + 61 + String.valueOf(valueOf4).length() + String.valueOf(valueOf5).length()).append("processAssets: replacing data with FD in asset: ").append(valueOf3).append(" read:").append(valueOf4).append(" write:").append(valueOf5).toString());
                    }
                    zzt.putAsset((String) next.getKey(), Asset.createFromFd(createPipe[0]));
                    FutureTask futureTask = new FutureTask(new zzfx(this, createPipe[1], asset2.getData()));
                    arrayList.add(futureTask);
                    this.zzbrV.submit(futureTask);
                } catch (IOException e) {
                    String valueOf6 = String.valueOf(putDataRequest);
                    throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf6).length() + 60).append("Unable to create ParcelFileDescriptor for asset in request: ").append(valueOf6).toString(), e);
                }
            } else if (asset2.getUri() != null) {
                try {
                    zzt.putAsset((String) next.getKey(), Asset.createFromFd(getContext().getContentResolver().openFileDescriptor(asset2.getUri(), "r")));
                } catch (FileNotFoundException e2) {
                    new zzfr(zzbaz, arrayList).zza(new zzem(4005, (zzcb) null));
                    String valueOf7 = String.valueOf(asset2.getUri());
                    Log.w("WearableClient", new StringBuilder(String.valueOf(valueOf7).length() + 28).append("Couldn't resolve asset URI: ").append(valueOf7).toString());
                    return;
                }
            } else {
                zzt.putAsset((String) next.getKey(), asset2);
            }
        }
        ((zzdn) zzrf()).zza((zzdi) new zzfr(zzbaz, arrayList), zzt);
    }

    public final void zza(zzbaz<Status> zzbaz, String str, Uri uri, long j, long j2) {
        try {
            ExecutorService executorService = this.zzbrV;
            zzbo.zzu(zzbaz);
            zzbo.zzu(str);
            zzbo.zzu(uri);
            zzbo.zzb(j >= 0, "startOffset is negative: %s", Long.valueOf(j));
            zzbo.zzb(j2 >= -1, "invalid length: %s", Long.valueOf(j2));
            executorService.execute(new zzfz(this, uri, zzbaz, str, j, j2));
        } catch (RuntimeException e) {
            zzbaz.zzr(new Status(8));
            throw e;
        }
    }

    public final void zza(zzbaz<Status> zzbaz, String str, Uri uri, boolean z) {
        try {
            ExecutorService executorService = this.zzbrV;
            zzbo.zzu(zzbaz);
            zzbo.zzu(str);
            zzbo.zzu(uri);
            executorService.execute(new zzfy(this, uri, zzbaz, z, str));
        } catch (RuntimeException e) {
            zzbaz.zzr(new Status(8));
            throw e;
        }
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.wearable.internal.IWearableService");
        return queryLocalInterface instanceof zzdn ? (zzdn) queryLocalInterface : new zzdo(iBinder);
    }

    /* access modifiers changed from: protected */
    public final String zzdb() {
        return "com.google.android.gms.wearable.BIND";
    }

    /* access modifiers changed from: protected */
    public final String zzdc() {
        return "com.google.android.gms.wearable.internal.IWearableService";
    }

    public final boolean zzpe() {
        return !this.zzbTo.zzgm("com.google.android.wearable.app.cn");
    }

    /* access modifiers changed from: protected */
    public final String zzqZ() {
        return this.zzbTo.zzgm("com.google.android.wearable.app.cn") ? "com.google.android.wearable.app.cn" : "com.google.android.gms";
    }
}

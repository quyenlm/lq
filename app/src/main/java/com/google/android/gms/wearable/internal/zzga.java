package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.zzbdw;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.ChannelApi;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.NodeApi;
import java.util.List;

public final class zzga<T> extends zzdl {
    private final IntentFilter[] zzbSW;
    private final String zzbTA;
    private zzbdw<Object> zzbTs;
    private zzbdw<Object> zzbTt;
    private zzbdw<DataApi.DataListener> zzbTu;
    private zzbdw<MessageApi.MessageListener> zzbTv;
    private zzbdw<NodeApi.NodeListener> zzbTw;
    private zzbdw<Object> zzbTx;
    private zzbdw<ChannelApi.ChannelListener> zzbTy;
    private zzbdw<CapabilityApi.CapabilityListener> zzbTz;

    private zzga(IntentFilter[] intentFilterArr, String str) {
        this.zzbSW = (IntentFilter[]) zzbo.zzu(intentFilterArr);
        this.zzbTA = str;
    }

    public static zzga<ChannelApi.ChannelListener> zza(zzbdw<ChannelApi.ChannelListener> zzbdw, String str, IntentFilter[] intentFilterArr) {
        zzga<ChannelApi.ChannelListener> zzga = new zzga<>(intentFilterArr, (String) zzbo.zzu(str));
        zzga.zzbTy = (zzbdw) zzbo.zzu(zzbdw);
        return zzga;
    }

    public static zzga<DataApi.DataListener> zza(zzbdw<DataApi.DataListener> zzbdw, IntentFilter[] intentFilterArr) {
        zzga<DataApi.DataListener> zzga = new zzga<>(intentFilterArr, (String) null);
        zzga.zzbTu = (zzbdw) zzbo.zzu(zzbdw);
        return zzga;
    }

    public static zzga<MessageApi.MessageListener> zzb(zzbdw<MessageApi.MessageListener> zzbdw, IntentFilter[] intentFilterArr) {
        zzga<MessageApi.MessageListener> zzga = new zzga<>(intentFilterArr, (String) null);
        zzga.zzbTv = (zzbdw) zzbo.zzu(zzbdw);
        return zzga;
    }

    public static zzga<NodeApi.NodeListener> zzc(zzbdw<NodeApi.NodeListener> zzbdw, IntentFilter[] intentFilterArr) {
        zzga<NodeApi.NodeListener> zzga = new zzga<>(intentFilterArr, (String) null);
        zzga.zzbTw = (zzbdw) zzbo.zzu(zzbdw);
        return zzga;
    }

    public static zzga<ChannelApi.ChannelListener> zzd(zzbdw<ChannelApi.ChannelListener> zzbdw, IntentFilter[] intentFilterArr) {
        zzga<ChannelApi.ChannelListener> zzga = new zzga<>(intentFilterArr, (String) null);
        zzga.zzbTy = (zzbdw) zzbo.zzu(zzbdw);
        return zzga;
    }

    public static zzga<CapabilityApi.CapabilityListener> zze(zzbdw<CapabilityApi.CapabilityListener> zzbdw, IntentFilter[] intentFilterArr) {
        zzga<CapabilityApi.CapabilityListener> zzga = new zzga<>(intentFilterArr, (String) null);
        zzga.zzbTz = (zzbdw) zzbo.zzu(zzbdw);
        return zzga;
    }

    private static void zzk(zzbdw<?> zzbdw) {
        if (zzbdw != null) {
            zzbdw.clear();
        }
    }

    public final void clear() {
        zzk((zzbdw<?>) null);
        this.zzbTs = null;
        zzk((zzbdw<?>) null);
        this.zzbTt = null;
        zzk(this.zzbTu);
        this.zzbTu = null;
        zzk(this.zzbTv);
        this.zzbTv = null;
        zzk(this.zzbTw);
        this.zzbTw = null;
        zzk((zzbdw<?>) null);
        this.zzbTx = null;
        zzk(this.zzbTy);
        this.zzbTy = null;
        zzk(this.zzbTz);
        this.zzbTz = null;
    }

    public final void onConnectedNodes(List<zzeg> list) {
    }

    public final IntentFilter[] zzDY() {
        return this.zzbSW;
    }

    public final String zzDZ() {
        return this.zzbTA;
    }

    public final void zzS(DataHolder dataHolder) {
        if (this.zzbTu != null) {
            this.zzbTu.zza(new zzgb(dataHolder));
        } else {
            dataHolder.close();
        }
    }

    public final void zza(zzaa zzaa) {
        if (this.zzbTz != null) {
            this.zzbTz.zza(new zzgg(zzaa));
        }
    }

    public final void zza(zzai zzai) {
        if (this.zzbTy != null) {
            this.zzbTy.zza(new zzgf(zzai));
        }
    }

    public final void zza(zzdx zzdx) {
        if (this.zzbTv != null) {
            this.zzbTv.zza(new zzgc(zzdx));
        }
    }

    public final void zza(zzeg zzeg) {
        if (this.zzbTw != null) {
            this.zzbTw.zza(new zzgd(zzeg));
        }
    }

    public final void zza(zzi zzi) {
    }

    public final void zza(zzl zzl) {
    }

    public final void zzb(zzeg zzeg) {
        if (this.zzbTw != null) {
            this.zzbTw.zza(new zzge(zzeg));
        }
    }
}

package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RawRes;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.tagmanager.DataLayer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TagManager {
    private static TagManager zzbGW;
    private final Context mContext;
    private final DataLayer zzbDx;
    private final zzal zzbFW;
    private final zza zzbGT;
    private final zzfn zzbGU;
    private final ConcurrentMap<String, zzv> zzbGV;

    public interface zza {
        zzy zza(Context context, TagManager tagManager, Looper looper, String str, int i, zzal zzal);
    }

    private TagManager(Context context, zza zza2, DataLayer dataLayer, zzfn zzfn) {
        if (context == null) {
            throw new NullPointerException("context cannot be null");
        }
        this.mContext = context.getApplicationContext();
        this.zzbGU = zzfn;
        this.zzbGT = zza2;
        this.zzbGV = new ConcurrentHashMap();
        this.zzbDx = dataLayer;
        this.zzbDx.zza((DataLayer.zzb) new zzgb(this));
        this.zzbDx.zza((DataLayer.zzb) new zzg(this.mContext));
        this.zzbFW = new zzal();
        this.mContext.registerComponentCallbacks(new zzgd(this));
        zza.zzbl(this.mContext);
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public static TagManager getInstance(Context context) {
        TagManager tagManager;
        synchronized (TagManager.class) {
            if (zzbGW == null) {
                if (context == null) {
                    zzdj.e("TagManager.getInstance requires non-null context.");
                    throw new NullPointerException();
                }
                zzbGW = new TagManager(context, new zzgc(), new DataLayer(new zzat(context)), zzfo.zzBV());
            }
            tagManager = zzbGW;
        }
        return tagManager;
    }

    /* access modifiers changed from: private */
    public final void zzfu(String str) {
        for (zzv zzeZ : this.zzbGV.values()) {
            zzeZ.zzeZ(str);
        }
    }

    public void dispatch() {
        this.zzbGU.dispatch();
    }

    public DataLayer getDataLayer() {
        return this.zzbDx;
    }

    public PendingResult<ContainerHolder> loadContainerDefaultOnly(String str, @RawRes int i) {
        zzy zza2 = this.zzbGT.zza(this.mContext, this, (Looper) null, str, i, this.zzbFW);
        zza2.zzAN();
        return zza2;
    }

    public PendingResult<ContainerHolder> loadContainerDefaultOnly(String str, @RawRes int i, Handler handler) {
        zzy zza2 = this.zzbGT.zza(this.mContext, this, handler.getLooper(), str, i, this.zzbFW);
        zza2.zzAN();
        return zza2;
    }

    public PendingResult<ContainerHolder> loadContainerPreferFresh(String str, @RawRes int i) {
        zzy zza2 = this.zzbGT.zza(this.mContext, this, (Looper) null, str, i, this.zzbFW);
        zza2.zzAP();
        return zza2;
    }

    public PendingResult<ContainerHolder> loadContainerPreferFresh(String str, @RawRes int i, Handler handler) {
        zzy zza2 = this.zzbGT.zza(this.mContext, this, handler.getLooper(), str, i, this.zzbFW);
        zza2.zzAP();
        return zza2;
    }

    public PendingResult<ContainerHolder> loadContainerPreferNonDefault(String str, @RawRes int i) {
        zzy zza2 = this.zzbGT.zza(this.mContext, this, (Looper) null, str, i, this.zzbFW);
        zza2.zzAO();
        return zza2;
    }

    public PendingResult<ContainerHolder> loadContainerPreferNonDefault(String str, @RawRes int i, Handler handler) {
        zzy zza2 = this.zzbGT.zza(this.mContext, this, handler.getLooper(), str, i, this.zzbFW);
        zza2.zzAO();
        return zza2;
    }

    public void setVerboseLoggingEnabled(boolean z) {
        zzdj.setLogLevel(z ? 2 : 5);
    }

    public final int zza(zzv zzv) {
        this.zzbGV.put(zzv.getContainerId(), zzv);
        return this.zzbGV.size();
    }

    public final boolean zzb(zzv zzv) {
        return this.zzbGV.remove(zzv.getContainerId()) != null;
    }

    /* access modifiers changed from: package-private */
    public final synchronized boolean zzr(Uri uri) {
        boolean z;
        zzei zzBD = zzei.zzBD();
        if (zzBD.zzr(uri)) {
            String containerId = zzBD.getContainerId();
            switch (zzge.zzbGY[zzBD.zzBE().ordinal()]) {
                case 1:
                    zzv zzv = (zzv) this.zzbGV.get(containerId);
                    if (zzv != null) {
                        zzv.zzfa((String) null);
                        zzv.refresh();
                        break;
                    }
                    break;
                case 2:
                case 3:
                    for (String str : this.zzbGV.keySet()) {
                        zzv zzv2 = (zzv) this.zzbGV.get(str);
                        if (str.equals(containerId)) {
                            zzv2.zzfa(zzBD.zzBF());
                            zzv2.refresh();
                        } else if (zzv2.zzAK() != null) {
                            zzv2.zzfa((String) null);
                            zzv2.refresh();
                        }
                    }
                    break;
            }
            z = true;
        } else {
            z = false;
        }
        return z;
    }
}

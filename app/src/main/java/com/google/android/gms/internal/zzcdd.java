package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.ContentProviderClient;
import android.content.Context;
import android.location.Location;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.zzj;
import com.google.android.gms.location.zzm;
import java.util.HashMap;
import java.util.Map;

public final class zzcdd {
    private final Context mContext;
    private final Map<zzbdy<LocationListener>, zzcdh> zzaWU = new HashMap();
    private final zzcdt<zzccz> zzbiB;
    private ContentProviderClient zzbiM = null;
    private boolean zzbiN = false;
    private final Map<zzbdy<LocationCallback>, zzcde> zzbiO = new HashMap();

    public zzcdd(Context context, zzcdt<zzccz> zzcdt) {
        this.mContext = context;
        this.zzbiB = zzcdt;
    }

    private final zzcdh zzf(zzbdw<LocationListener> zzbdw) {
        zzcdh zzcdh;
        synchronized (this.zzaWU) {
            zzcdh = this.zzaWU.get(zzbdw.zzqG());
            if (zzcdh == null) {
                zzcdh = new zzcdh(zzbdw);
            }
            this.zzaWU.put(zzbdw.zzqG(), zzcdh);
        }
        return zzcdh;
    }

    private final zzcde zzg(zzbdw<LocationCallback> zzbdw) {
        zzcde zzcde;
        synchronized (this.zzbiO) {
            zzcde = this.zzbiO.get(zzbdw.zzqG());
            if (zzcde == null) {
                zzcde = new zzcde(zzbdw);
            }
            this.zzbiO.put(zzbdw.zzqG(), zzcde);
        }
        return zzcde;
    }

    public final Location getLastLocation() {
        this.zzbiB.zzre();
        try {
            return this.zzbiB.zzrf().zzdv(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void removeAllListeners() {
        try {
            synchronized (this.zzaWU) {
                for (zzcdh next : this.zzaWU.values()) {
                    if (next != null) {
                        this.zzbiB.zzrf().zza(zzcdp.zza((zzm) next, (zzccu) null));
                    }
                }
                this.zzaWU.clear();
            }
            synchronized (this.zzbiO) {
                for (zzcde next2 : this.zzbiO.values()) {
                    if (next2 != null) {
                        this.zzbiB.zzrf().zza(zzcdp.zza((zzj) next2, (zzccu) null));
                    }
                }
                this.zzbiO.clear();
            }
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void zza(PendingIntent pendingIntent, zzccu zzccu) throws RemoteException {
        this.zzbiB.zzre();
        this.zzbiB.zzrf().zza(new zzcdp(2, (zzcdn) null, (IBinder) null, pendingIntent, (IBinder) null, zzccu != null ? zzccu.asBinder() : null));
    }

    public final void zza(zzbdy<LocationListener> zzbdy, zzccu zzccu) throws RemoteException {
        this.zzbiB.zzre();
        zzbo.zzb(zzbdy, (Object) "Invalid null listener key");
        synchronized (this.zzaWU) {
            zzcdh remove = this.zzaWU.remove(zzbdy);
            if (remove != null) {
                remove.release();
                this.zzbiB.zzrf().zza(zzcdp.zza((zzm) remove, zzccu));
            }
        }
    }

    public final void zza(zzccu zzccu) throws RemoteException {
        this.zzbiB.zzre();
        this.zzbiB.zzrf().zza(zzccu);
    }

    public final void zza(zzcdn zzcdn, zzbdw<LocationCallback> zzbdw, zzccu zzccu) throws RemoteException {
        this.zzbiB.zzre();
        this.zzbiB.zzrf().zza(new zzcdp(1, zzcdn, (IBinder) null, (PendingIntent) null, zzg(zzbdw).asBinder(), zzccu != null ? zzccu.asBinder() : null));
    }

    public final void zza(LocationRequest locationRequest, PendingIntent pendingIntent, zzccu zzccu) throws RemoteException {
        this.zzbiB.zzre();
        this.zzbiB.zzrf().zza(new zzcdp(1, zzcdn.zza(locationRequest), (IBinder) null, pendingIntent, (IBinder) null, zzccu != null ? zzccu.asBinder() : null));
    }

    public final void zza(LocationRequest locationRequest, zzbdw<LocationListener> zzbdw, zzccu zzccu) throws RemoteException {
        this.zzbiB.zzre();
        this.zzbiB.zzrf().zza(new zzcdp(1, zzcdn.zza(locationRequest), zzf(zzbdw).asBinder(), (PendingIntent) null, (IBinder) null, zzccu != null ? zzccu.asBinder() : null));
    }

    public final void zzai(boolean z) throws RemoteException {
        this.zzbiB.zzre();
        this.zzbiB.zzrf().zzai(z);
        this.zzbiN = z;
    }

    public final void zzb(zzbdy<LocationCallback> zzbdy, zzccu zzccu) throws RemoteException {
        this.zzbiB.zzre();
        zzbo.zzb(zzbdy, (Object) "Invalid null listener key");
        synchronized (this.zzbiO) {
            zzcde remove = this.zzbiO.remove(zzbdy);
            if (remove != null) {
                remove.release();
                this.zzbiB.zzrf().zza(zzcdp.zza((zzj) remove, zzccu));
            }
        }
    }

    public final void zzc(Location location) throws RemoteException {
        this.zzbiB.zzre();
        this.zzbiB.zzrf().zzc(location);
    }

    public final LocationAvailability zzvQ() {
        this.zzbiB.zzre();
        try {
            return this.zzbiB.zzrf().zzdw(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public final void zzvR() {
        if (this.zzbiN) {
            try {
                zzai(false);
            } catch (RemoteException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}

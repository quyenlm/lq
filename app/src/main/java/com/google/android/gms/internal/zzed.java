package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public class zzed implements IInterface {
    private final IBinder zzrD;
    private final String zzrE;

    protected zzed(IBinder iBinder, String str) {
        this.zzrD = iBinder;
        this.zzrE = str;
    }

    public IBinder asBinder() {
        return this.zzrD;
    }

    /* access modifiers changed from: protected */
    public final Parcel zzZ() {
        Parcel obtain = Parcel.obtain();
        obtain.writeInterfaceToken(this.zzrE);
        return obtain;
    }

    /* access modifiers changed from: protected */
    public final Parcel zza(int i, Parcel parcel) throws RemoteException {
        parcel = Parcel.obtain();
        try {
            this.zzrD.transact(i, parcel, parcel, 0);
            parcel.readException();
            return parcel;
        } catch (RuntimeException e) {
            throw e;
        } finally {
            parcel.recycle();
        }
    }

    /* access modifiers changed from: protected */
    public final void zzb(int i, Parcel parcel) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        try {
            this.zzrD.transact(i, parcel, obtain, 0);
            obtain.readException();
        } finally {
            parcel.recycle();
            obtain.recycle();
        }
    }

    /* access modifiers changed from: protected */
    public final void zzc(int i, Parcel parcel) throws RemoteException {
        try {
            this.zzrD.transact(i, parcel, (Parcel) null, 1);
        } finally {
            parcel.recycle();
        }
    }
}

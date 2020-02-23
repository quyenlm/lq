package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.dynamite.DynamiteModule;

public abstract class fb<T> {
    private final Context mContext;
    private final Object mLock = new Object();
    private final String mTag;
    private boolean zzbNL = false;
    private T zzbNM;

    public fb(Context context, String str) {
        this.mContext = context;
        this.mTag = str;
    }

    public final boolean isOperational() {
        return zzDR() != null;
    }

    /* access modifiers changed from: protected */
    public abstract void zzDO() throws RemoteException;

    public final void zzDQ() {
        synchronized (this.mLock) {
            if (this.zzbNM != null) {
                try {
                    zzDO();
                } catch (RemoteException e) {
                    Log.e(this.mTag, "Could not finalize native handle", e);
                }
                return;
            }
            return;
        }
    }

    /* access modifiers changed from: protected */
    public final T zzDR() {
        T t;
        synchronized (this.mLock) {
            if (this.zzbNM != null) {
                t = this.zzbNM;
            } else {
                try {
                    this.zzbNM = zza(DynamiteModule.zza(this.mContext, DynamiteModule.zzaSO, "com.google.android.gms.vision.dynamite"), this.mContext);
                } catch (RemoteException | DynamiteModule.zzc e) {
                    Log.e(this.mTag, "Error creating remote native handle", e);
                }
                if (!this.zzbNL && this.zzbNM == null) {
                    Log.w(this.mTag, "Native handle not yet available. Reverting to no-op handle.");
                    this.zzbNL = true;
                } else if (this.zzbNL && this.zzbNM != null) {
                    Log.w(this.mTag, "Native handle is now available.");
                }
                t = this.zzbNM;
            }
        }
        return t;
    }

    /* access modifiers changed from: protected */
    public abstract T zza(DynamiteModule dynamiteModule, Context context) throws RemoteException, DynamiteModule.zzc;
}

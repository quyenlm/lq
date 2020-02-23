package com.google.android.gms.internal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.storage.StorageException;
import java.io.InputStream;
import java.net.SocketException;
import org.json.JSONObject;

public final class acf {
    private Exception zzbMj;
    private aca zzcqk;
    private int zzcql;
    private Exception zzcqm;

    public acf(@NonNull aca aca) {
        this.zzcqk = aca;
    }

    @Nullable
    public final Exception getException() {
        try {
            return this.zzcqm != null ? this.zzcqm : this.zzbMj != null ? this.zzbMj : (Exception) zzn.zzE(this.zzcqk.zzLj());
        } catch (RemoteException e) {
            this.zzbMj = e;
            Log.e("NetworkRequestProxy", "getException failed with a RemoteException:", e);
            return null;
        }
    }

    public final int getResultCode() {
        try {
            return this.zzcql != 0 ? this.zzcql : this.zzcqk.getResultCode();
        } catch (RemoteException e) {
            this.zzbMj = e;
            Log.e("NetworkRequestProxy", "getResultCode failed with a RemoteException:", e);
            return 0;
        }
    }

    @Nullable
    public final InputStream getStream() {
        try {
            return (InputStream) zzn.zzE(this.zzcqk.zzLg());
        } catch (RemoteException e) {
            this.zzbMj = e;
            Log.e("NetworkRequestProxy", "getStream failed with a RemoteException:", e);
            return null;
        }
    }

    public final void reset() {
        try {
            this.zzcql = 0;
            this.zzcqm = null;
            this.zzcqk.reset();
        } catch (RemoteException e) {
            this.zzbMj = e;
            Log.e("NetworkRequestProxy", "reset failed with a RemoteException:", e);
        }
    }

    public final void zzLf() {
        try {
            if (this.zzcqk != null) {
                this.zzcqk.zzLf();
            }
        } catch (RemoteException e) {
            this.zzbMj = e;
            Log.e("NetworkRequestProxy", "performRequestEnd failed with a RemoteException:", e);
        }
    }

    @Nullable
    public final String zzLi() {
        try {
            this.zzcqk.zzLi();
            return null;
        } catch (RemoteException e) {
            this.zzbMj = e;
            Log.e("NetworkRequestProxy", "getRawResult failed with a RemoteException:", e);
            return null;
        }
    }

    public final boolean zzLk() {
        try {
            if (this.zzcql == -2 || this.zzcqm != null) {
                return false;
            }
            return this.zzcqk.zzLk();
        } catch (RemoteException e) {
            this.zzbMj = e;
            Log.e("NetworkRequestProxy", "isResultSuccess failed with a RemoteException:", e);
            return false;
        }
    }

    public final int zzLl() {
        try {
            return this.zzcqk.zzLl();
        } catch (RemoteException e) {
            this.zzbMj = e;
            Log.e("NetworkRequestProxy", "getResultingContentLength failed with a RemoteException:", e);
            return 0;
        }
    }

    @NonNull
    public final JSONObject zzLn() throws RemoteException {
        return (JSONObject) zzn.zzE(this.zzcqk.zzLh());
    }

    public final <TResult> void zza(TaskCompletionSource<TResult> taskCompletionSource, TResult tresult) {
        Exception exception = getException();
        if (!zzLk() || exception != null) {
            taskCompletionSource.setException(StorageException.fromExceptionAndHttpCode(exception, getResultCode()));
        } else {
            taskCompletionSource.setResult(tresult);
        }
    }

    public final void zzam(String str, String str2) {
        try {
            this.zzcqk.zzam(str, str2);
        } catch (RemoteException e) {
            RemoteException remoteException = e;
            String valueOf = String.valueOf(str);
            Log.e("NetworkRequestProxy", valueOf.length() != 0 ? "Caught remote exception setting custom header:".concat(valueOf) : new String("Caught remote exception setting custom header:"), remoteException);
        }
    }

    public final void zze(@Nullable String str, @NonNull Context context) {
        boolean z;
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                this.zzcql = -2;
                this.zzcqm = new SocketException("Network subsystem is unavailable");
                z = false;
            } else {
                z = true;
            }
            if (z) {
                this.zzcqk.zzhM(str);
            }
        } catch (RemoteException e) {
            this.zzbMj = e;
            Log.e("NetworkRequestProxy", "performRequest failed with a RemoteException:", e);
        }
    }

    public final void zzhN(@Nullable String str) {
        try {
            this.zzcqk.zzhN(str);
        } catch (RemoteException e) {
            this.zzbMj = e;
            Log.e("NetworkRequestProxy", "performRequestStart failed with a RemoteException:", e);
        }
    }

    @Nullable
    public final String zzhO(String str) {
        try {
            return this.zzcqk.zzhO(str);
        } catch (RemoteException e) {
            RemoteException remoteException = e;
            String valueOf = String.valueOf(str);
            Log.e("NetworkRequestProxy", valueOf.length() != 0 ? "getResultString failed with a RemoteException:".concat(valueOf) : new String("getResultString failed with a RemoteException:"), remoteException);
            return null;
        }
    }
}

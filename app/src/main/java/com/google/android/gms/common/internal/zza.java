package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.zzo;

public final class zza extends zzam {
    private int zzaGG;

    public static Account zza(zzal zzal) {
        Account account = null;
        if (zzal != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                account = zzal.getAccount();
            } catch (RemoteException e) {
                Log.w("AccountAccessor", "Remote account accessor probably died");
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return account;
    }

    public final boolean equals(Object obj) {
        Account account = null;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zza)) {
            return false;
        }
        return account.equals(account);
    }

    public final Account getAccount() {
        int callingUid = Binder.getCallingUid();
        if (callingUid != this.zzaGG) {
            if (zzo.zzf((Context) null, callingUid)) {
                this.zzaGG = callingUid;
            } else {
                throw new SecurityException("Caller is not GooglePlayServices");
            }
        }
        return null;
    }
}

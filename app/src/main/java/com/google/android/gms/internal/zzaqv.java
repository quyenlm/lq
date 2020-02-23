package com.google.android.gms.internal;

import android.accounts.Account;
import com.google.android.gms.auth.account.WorkAccountApi;
import com.google.android.gms.common.api.Status;

final class zzaqv implements WorkAccountApi.AddAccountResult {
    private final Status mStatus;
    private final Account zzajb;

    public zzaqv(Status status, Account account) {
        this.mStatus = status;
        this.zzajb = account;
    }

    public final Account getAccount() {
        return this.zzajb;
    }

    public final Status getStatus() {
        return this.mStatus;
    }
}

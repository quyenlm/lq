package com.google.android.gms.games.internal.api;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.request.Requests;
import java.util.Set;

final class zzca implements Requests.UpdateRequestsResult {
    private /* synthetic */ Status zzakB;

    zzca(zzbz zzbz, Status status) {
        this.zzakB = status;
    }

    public final Set<String> getRequestIds() {
        return null;
    }

    public final int getRequestOutcome(String str) {
        String valueOf = String.valueOf(str);
        throw new IllegalArgumentException(valueOf.length() != 0 ? "Unknown request ID ".concat(valueOf) : new String("Unknown request ID "));
    }

    public final Status getStatus() {
        return this.zzakB;
    }

    public final void release() {
    }
}

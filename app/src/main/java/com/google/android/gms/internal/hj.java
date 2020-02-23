package com.google.android.gms.internal;

import android.database.ContentObserver;
import android.os.Handler;

final class hj extends ContentObserver {
    hj(Handler handler) {
        super((Handler) null);
    }

    public final void onChange(boolean z) {
        hi.zzbUd.set(true);
    }
}

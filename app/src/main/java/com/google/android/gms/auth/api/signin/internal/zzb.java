package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.zzbei;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public final class zzb extends AsyncTaskLoader<Void> implements zzbei {
    private Semaphore zzami = new Semaphore(0);
    private Set<GoogleApiClient> zzamj;

    public zzb(Context context, Set<GoogleApiClient> set) {
        super(context);
        this.zzamj = set;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzmE */
    public final Void loadInBackground() {
        int i = 0;
        Iterator<GoogleApiClient> it = this.zzamj.iterator();
        while (true) {
            int i2 = i;
            if (it.hasNext()) {
                i = it.next().zza((zzbei) this) ? i2 + 1 : i2;
            } else {
                try {
                    this.zzami.tryAcquire(i2, 5, TimeUnit.SECONDS);
                    return null;
                } catch (InterruptedException e) {
                    Log.i("GACSignInLoader", "Unexpected InterruptedException", e);
                    Thread.currentThread().interrupt();
                    return null;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void onStartLoading() {
        this.zzami.drainPermits();
        forceLoad();
    }

    public final void zzmF() {
        this.zzami.release();
    }
}

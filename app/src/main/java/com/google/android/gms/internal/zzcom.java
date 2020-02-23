package com.google.android.gms.internal;

import android.util.Log;
import com.google.android.gms.common.util.zzn;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class zzcom {
    private final ExecutorService zzbxv = Executors.newSingleThreadExecutor();
    /* access modifiers changed from: private */
    public volatile InputStream zzbxw = null;
    /* access modifiers changed from: private */
    public volatile boolean zzbxx = false;

    /* access modifiers changed from: private */
    public static void zza(OutputStream outputStream, boolean z, long j) {
        int i = 1;
        if (!z) {
            i = 0;
        }
        try {
            outputStream.write(i);
        } catch (IOException e) {
            Log.w("NearbyConnections", String.format("Unable to deliver status for Payload %d", new Object[]{Long.valueOf(j)}), e);
        } finally {
            zzn.closeQuietly(outputStream);
        }
    }

    /* access modifiers changed from: package-private */
    public final void shutdown() {
        this.zzbxx = true;
        this.zzbxv.shutdownNow();
        zzn.closeQuietly(this.zzbxw);
    }

    /* access modifiers changed from: package-private */
    public final void zza(InputStream inputStream, OutputStream outputStream, OutputStream outputStream2, long j) {
        this.zzbxv.execute(new zzcon(this, inputStream, outputStream, j, outputStream2));
    }
}

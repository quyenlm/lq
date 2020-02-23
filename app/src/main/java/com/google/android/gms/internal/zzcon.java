package com.google.android.gms.internal;

import android.util.Log;
import com.google.android.gms.common.util.zzn;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

final class zzcon implements Runnable {
    private /* synthetic */ long zzbxA;
    private /* synthetic */ OutputStream zzbxB;
    private /* synthetic */ zzcom zzbxC;
    private /* synthetic */ InputStream zzbxy;
    private /* synthetic */ OutputStream zzbxz;

    zzcon(zzcom zzcom, InputStream inputStream, OutputStream outputStream, long j, OutputStream outputStream2) {
        this.zzbxC = zzcom;
        this.zzbxy = inputStream;
        this.zzbxz = outputStream;
        this.zzbxA = j;
        this.zzbxB = outputStream2;
    }

    public final void run() {
        boolean z = false;
        InputStream unused = this.zzbxC.zzbxw = this.zzbxy;
        try {
            zzn.zza(this.zzbxy, this.zzbxz, false, 65536);
            zzn.closeQuietly(this.zzbxy);
            zzcom.zza(this.zzbxB, false, this.zzbxA);
            zzn.closeQuietly(this.zzbxz);
            InputStream unused2 = this.zzbxC.zzbxw = null;
            return;
        } catch (IOException e) {
            if (!this.zzbxC.zzbxx) {
                Log.w("NearbyConnections", String.format("Exception copying stream for Payload %d", new Object[]{Long.valueOf(this.zzbxA)}), e);
            } else {
                Log.d("NearbyConnections", String.format("Terminating copying stream for Payload %d due to shutdown of OutgoingPayloadStreamer.", new Object[]{Long.valueOf(this.zzbxA)}));
            }
            zzn.closeQuietly(this.zzbxy);
            zzcom.zza(this.zzbxB, true, this.zzbxA);
            zzn.closeQuietly(this.zzbxz);
            InputStream unused3 = this.zzbxC.zzbxw = null;
            return;
        } catch (Throwable th) {
            th = th;
            z = true;
        }
        zzn.closeQuietly(this.zzbxy);
        zzcom.zza(this.zzbxB, z, this.zzbxA);
        zzn.closeQuietly(this.zzbxz);
        InputStream unused4 = this.zzbxC.zzbxw = null;
        throw th;
    }
}

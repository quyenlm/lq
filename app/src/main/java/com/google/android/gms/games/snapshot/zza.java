package com.google.android.gms.games.snapshot;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.util.zzn;
import com.google.android.gms.games.internal.zzc;
import com.google.android.gms.games.internal.zze;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public final class zza extends zzc implements SnapshotContents {
    public static final Parcelable.Creator<zza> CREATOR = new zzb();
    private static final Object zzbew = new Object();
    private com.google.android.gms.drive.zzc zzaOg;

    public zza(com.google.android.gms.drive.zzc zzc) {
        this.zzaOg = zzc;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean zza(int r9, byte[] r10, int r11, int r12, boolean r13) {
        /*
            r8 = this;
            r1 = 1
            r2 = 0
            boolean r0 = r8.isClosed()
            if (r0 != 0) goto L_0x003c
            r0 = r1
        L_0x0009:
            java.lang.String r3 = "Must provide a previously opened SnapshotContents"
            com.google.android.gms.common.internal.zzbo.zza((boolean) r0, (java.lang.Object) r3)
            java.lang.Object r3 = zzbew
            monitor-enter(r3)
            com.google.android.gms.drive.zzc r0 = r8.zzaOg     // Catch:{ all -> 0x0049 }
            android.os.ParcelFileDescriptor r0 = r0.getParcelFileDescriptor()     // Catch:{ all -> 0x0049 }
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ all -> 0x0049 }
            java.io.FileDescriptor r0 = r0.getFileDescriptor()     // Catch:{ all -> 0x0049 }
            r4.<init>(r0)     // Catch:{ all -> 0x0049 }
            java.io.BufferedOutputStream r0 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x0049 }
            r0.<init>(r4)     // Catch:{ all -> 0x0049 }
            java.nio.channels.FileChannel r4 = r4.getChannel()     // Catch:{ IOException -> 0x003e }
            long r6 = (long) r9     // Catch:{ IOException -> 0x003e }
            r4.position(r6)     // Catch:{ IOException -> 0x003e }
            r0.write(r10, r11, r12)     // Catch:{ IOException -> 0x003e }
            if (r13 == 0) goto L_0x0037
            int r5 = r10.length     // Catch:{ IOException -> 0x003e }
            long r6 = (long) r5     // Catch:{ IOException -> 0x003e }
            r4.truncate(r6)     // Catch:{ IOException -> 0x003e }
        L_0x0037:
            r0.flush()     // Catch:{ IOException -> 0x003e }
            monitor-exit(r3)     // Catch:{ all -> 0x0049 }
        L_0x003b:
            return r1
        L_0x003c:
            r0 = r2
            goto L_0x0009
        L_0x003e:
            r0 = move-exception
            java.lang.String r1 = "SnapshotContentsEntity"
            java.lang.String r4 = "Failed to write snapshot data"
            com.google.android.gms.games.internal.zze.zzb(r1, r4, r0)     // Catch:{ all -> 0x0049 }
            monitor-exit(r3)     // Catch:{ all -> 0x0049 }
            r1 = r2
            goto L_0x003b
        L_0x0049:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0049 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.games.snapshot.zza.zza(int, byte[], int, int, boolean):boolean");
    }

    public final void close() {
        this.zzaOg = null;
    }

    public final ParcelFileDescriptor getParcelFileDescriptor() {
        zzbo.zza(!isClosed(), (Object) "Cannot mutate closed contents!");
        return this.zzaOg.getParcelFileDescriptor();
    }

    public final boolean isClosed() {
        return this.zzaOg == null;
    }

    public final boolean modifyBytes(int i, byte[] bArr, int i2, int i3) {
        return zza(i, bArr, i2, bArr.length, false);
    }

    public final byte[] readFully() throws IOException {
        byte[] zza;
        boolean z = false;
        if (!isClosed()) {
            z = true;
        }
        zzbo.zza(z, (Object) "Must provide a previously opened Snapshot");
        synchronized (zzbew) {
            FileInputStream fileInputStream = new FileInputStream(this.zzaOg.getParcelFileDescriptor().getFileDescriptor());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            try {
                fileInputStream.getChannel().position(0);
                zza = zzn.zza(bufferedInputStream, false);
                fileInputStream.getChannel().position(0);
            } catch (IOException e) {
                zze.zzc("SnapshotContentsEntity", "Failed to read snapshot data", e);
                throw e;
            }
        }
        return zza;
    }

    public final boolean writeBytes(byte[] bArr) {
        return zza(0, bArr, 0, bArr.length, true);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, (Parcelable) this.zzaOg, i, false);
        zzd.zzI(parcel, zze);
    }

    public final com.google.android.gms.drive.zzc zzsM() {
        return this.zzaOg;
    }
}

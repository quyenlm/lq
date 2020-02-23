package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbaz;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

final class zzfy implements Runnable {
    private /* synthetic */ String zzakq;
    private /* synthetic */ boolean zzbSl;
    private /* synthetic */ zzbaz zzbTq;
    private /* synthetic */ zzfw zzbTr;
    private /* synthetic */ Uri zzbzR;

    zzfy(zzfw zzfw, Uri uri, zzbaz zzbaz, boolean z, String str) {
        this.zzbTr = zzfw;
        this.zzbzR = uri;
        this.zzbTq = zzbaz;
        this.zzbSl = z;
        this.zzakq = str;
    }

    public final void run() {
        if (Log.isLoggable("WearableClient", 2)) {
            Log.v("WearableClient", "Executing receiveFileFromChannelTask");
        }
        if (!TransferTable.COLUMN_FILE.equals(this.zzbzR.getScheme())) {
            Log.w("WearableClient", "Channel.receiveFile used with non-file URI");
            this.zzbTq.zzr(new Status(10, "Channel.receiveFile used with non-file URI"));
            return;
        }
        File file = new File(this.zzbzR.getPath());
        try {
            ParcelFileDescriptor open = ParcelFileDescriptor.open(file, (this.zzbSl ? 33554432 : 0) | 671088640);
            try {
                ((zzdn) this.zzbTr.zzrf()).zza((zzdi) new zzfv(this.zzbTq), this.zzakq, open);
                try {
                    open.close();
                } catch (IOException e) {
                    Log.w("WearableClient", "Failed to close targetFd", e);
                }
            } catch (RemoteException e2) {
                Log.w("WearableClient", "Channel.receiveFile failed.", e2);
                this.zzbTq.zzr(new Status(8));
                try {
                    open.close();
                } catch (IOException e3) {
                    Log.w("WearableClient", "Failed to close targetFd", e3);
                }
            } catch (Throwable th) {
                try {
                    open.close();
                } catch (IOException e4) {
                    Log.w("WearableClient", "Failed to close targetFd", e4);
                }
                throw th;
            }
        } catch (FileNotFoundException e5) {
            String valueOf = String.valueOf(file);
            Log.w("WearableClient", new StringBuilder(String.valueOf(valueOf).length() + 49).append("File couldn't be opened for Channel.receiveFile: ").append(valueOf).toString());
            this.zzbTq.zzr(new Status(13));
        }
    }
}

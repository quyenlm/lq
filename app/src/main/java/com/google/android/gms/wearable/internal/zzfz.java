package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.internal.zzbaz;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

final class zzfz implements Runnable {
    private /* synthetic */ String zzakq;
    private /* synthetic */ long zzbSm;
    private /* synthetic */ long zzbSn;
    private /* synthetic */ zzbaz zzbTq;
    private /* synthetic */ zzfw zzbTr;
    private /* synthetic */ Uri zzbzR;

    zzfz(zzfw zzfw, Uri uri, zzbaz zzbaz, String str, long j, long j2) {
        this.zzbTr = zzfw;
        this.zzbzR = uri;
        this.zzbTq = zzbaz;
        this.zzakq = str;
        this.zzbSm = j;
        this.zzbSn = j2;
    }

    public final void run() {
        if (Log.isLoggable("WearableClient", 2)) {
            Log.v("WearableClient", "Executing sendFileToChannelTask");
        }
        if (!TransferTable.COLUMN_FILE.equals(this.zzbzR.getScheme())) {
            Log.w("WearableClient", "Channel.sendFile used with non-file URI");
            this.zzbTq.zzr(new Status(10, "Channel.sendFile used with non-file URI"));
            return;
        }
        File file = new File(this.zzbzR.getPath());
        try {
            ParcelFileDescriptor open = ParcelFileDescriptor.open(file, DriveFile.MODE_READ_ONLY);
            try {
                ((zzdn) this.zzbTr.zzrf()).zza(new zzfs(this.zzbTq), this.zzakq, open, this.zzbSm, this.zzbSn);
                try {
                    open.close();
                } catch (IOException e) {
                    Log.w("WearableClient", "Failed to close sourceFd", e);
                }
            } catch (RemoteException e2) {
                Log.w("WearableClient", "Channel.sendFile failed.", e2);
                this.zzbTq.zzr(new Status(8));
                try {
                    open.close();
                } catch (IOException e3) {
                    Log.w("WearableClient", "Failed to close sourceFd", e3);
                }
            } catch (Throwable th) {
                try {
                    open.close();
                } catch (IOException e4) {
                    Log.w("WearableClient", "Failed to close sourceFd", e4);
                }
                throw th;
            }
        } catch (FileNotFoundException e5) {
            String valueOf = String.valueOf(file);
            Log.w("WearableClient", new StringBuilder(String.valueOf(valueOf).length() + 46).append("File couldn't be opened for Channel.sendFile: ").append(valueOf).toString());
            this.zzbTq.zzr(new Status(13));
        }
    }
}

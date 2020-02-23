package com.google.android.gms.wearable.internal;

import android.os.ParcelFileDescriptor;
import android.util.Log;
import java.io.IOException;
import java.util.concurrent.Callable;

final class zzfx implements Callable<Boolean> {
    private /* synthetic */ byte[] zzbKQ;
    private /* synthetic */ ParcelFileDescriptor zzbTp;

    zzfx(zzfw zzfw, ParcelFileDescriptor parcelFileDescriptor, byte[] bArr) {
        this.zzbTp = parcelFileDescriptor;
        this.zzbKQ = bArr;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzDX */
    public final Boolean call() {
        if (Log.isLoggable("WearableClient", 3)) {
            String valueOf = String.valueOf(this.zzbTp);
            Log.d("WearableClient", new StringBuilder(String.valueOf(valueOf).length() + 36).append("processAssets: writing data to FD : ").append(valueOf).toString());
        }
        ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(this.zzbTp);
        try {
            autoCloseOutputStream.write(this.zzbKQ);
            autoCloseOutputStream.flush();
            if (Log.isLoggable("WearableClient", 3)) {
                String valueOf2 = String.valueOf(this.zzbTp);
                Log.d("WearableClient", new StringBuilder(String.valueOf(valueOf2).length() + 27).append("processAssets: wrote data: ").append(valueOf2).toString());
            }
            try {
                if (Log.isLoggable("WearableClient", 3)) {
                    String valueOf3 = String.valueOf(this.zzbTp);
                    Log.d("WearableClient", new StringBuilder(String.valueOf(valueOf3).length() + 24).append("processAssets: closing: ").append(valueOf3).toString());
                }
                autoCloseOutputStream.close();
                return true;
            } catch (IOException e) {
                return true;
            }
        } catch (IOException e2) {
            String valueOf4 = String.valueOf(this.zzbTp);
            Log.w("WearableClient", new StringBuilder(String.valueOf(valueOf4).length() + 36).append("processAssets: writing data failed: ").append(valueOf4).toString());
            try {
                if (Log.isLoggable("WearableClient", 3)) {
                    String valueOf5 = String.valueOf(this.zzbTp);
                    Log.d("WearableClient", new StringBuilder(String.valueOf(valueOf5).length() + 24).append("processAssets: closing: ").append(valueOf5).toString());
                }
                autoCloseOutputStream.close();
            } catch (IOException e3) {
            }
            return false;
        } catch (Throwable th) {
            try {
                if (Log.isLoggable("WearableClient", 3)) {
                    String valueOf6 = String.valueOf(this.zzbTp);
                    Log.d("WearableClient", new StringBuilder(String.valueOf(valueOf6).length() + 24).append("processAssets: closing: ").append(valueOf6).toString());
                }
                autoCloseOutputStream.close();
            } catch (IOException e4) {
            }
            throw th;
        }
    }
}

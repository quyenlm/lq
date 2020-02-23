package com.google.firebase.storage;

import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.abu;
import com.google.android.gms.internal.ace;
import com.google.android.gms.internal.acf;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.storage.StorageMetadata;
import org.json.JSONException;

final class zzb implements Runnable {
    private StorageReference zzcoe;
    private TaskCompletionSource<StorageMetadata> zzcof;
    private abu zzcog = new abu(this.zzcoe.getStorage().getApp(), this.zzcoe.getStorage().getMaxOperationRetryTimeMillis());
    private StorageMetadata zzcor;

    public zzb(@NonNull StorageReference storageReference, @NonNull TaskCompletionSource<StorageMetadata> taskCompletionSource) {
        zzbo.zzu(storageReference);
        zzbo.zzu(taskCompletionSource);
        this.zzcoe = storageReference;
        this.zzcof = taskCompletionSource;
    }

    public final void run() {
        try {
            acf zzx = ace.zzg(this.zzcoe.getStorage().getApp()).zzx(this.zzcoe.zzKP());
            this.zzcog.zza(zzx, true);
            if (zzx.zzLk()) {
                try {
                    this.zzcor = new StorageMetadata.Builder(zzx.zzLn(), this.zzcoe).build();
                } catch (RemoteException | JSONException e) {
                    String valueOf = String.valueOf(zzx.zzLi());
                    Log.e("GetMetadataTask", valueOf.length() != 0 ? "Unable to parse resulting metadata. ".concat(valueOf) : new String("Unable to parse resulting metadata. "), e);
                    this.zzcof.setException(StorageException.fromException(e));
                    return;
                }
            }
            if (this.zzcof != null) {
                zzx.zza(this.zzcof, this.zzcor);
            }
        } catch (RemoteException e2) {
            Log.e("GetMetadataTask", "Unable to create firebase storage network request.", e2);
            this.zzcof.setException(StorageException.fromException(e2));
        }
    }
}

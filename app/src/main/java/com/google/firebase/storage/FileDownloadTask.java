package com.google.firebase.storage;

import android.net.Uri;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import com.amazonaws.services.s3.Headers;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.abu;
import com.google.android.gms.internal.ace;
import com.google.android.gms.internal.acf;
import com.google.firebase.storage.StorageTask;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileDownloadTask extends StorageTask<TaskSnapshot> {
    private int mResultCode;
    private long zzaNq = -1;
    private volatile Exception zzbMj = null;
    private StorageReference zzcoe;
    private abu zzcog;
    private final Uri zzcoh;
    private long zzcoi;
    private String zzcoj = null;
    private long zzcok = 0;

    public class TaskSnapshot extends StorageTask<TaskSnapshot>.SnapshotBase {
        private final long zzcoi;

        TaskSnapshot(Exception exc, long j) {
            super(exc);
            this.zzcoi = j;
        }

        public long getBytesTransferred() {
            return this.zzcoi;
        }

        public long getTotalByteCount() {
            return FileDownloadTask.this.getTotalBytes();
        }
    }

    FileDownloadTask(@NonNull StorageReference storageReference, @NonNull Uri uri) {
        this.zzcoe = storageReference;
        this.zzcoh = uri;
        this.zzcog = new abu(this.zzcoe.getStorage().getApp(), this.zzcoe.getStorage().getMaxDownloadRetryTimeMillis());
    }

    private final int zza(InputStream inputStream, byte[] bArr) {
        boolean z;
        int read;
        boolean z2 = false;
        int i = 0;
        while (true) {
            try {
                z = z2;
                if (i == bArr.length || (read = inputStream.read(bArr, i, bArr.length - i)) == -1) {
                    break;
                }
                z2 = true;
                i += read;
            } catch (IOException e) {
                this.zzbMj = e;
            }
        }
        if (z) {
            return i;
        }
        return -1;
    }

    private final boolean zza(acf acf) throws IOException {
        FileOutputStream fileOutputStream;
        InputStream stream = acf.getStream();
        if (stream != null) {
            File file = new File(this.zzcoh.getPath());
            if (!file.exists()) {
                if (this.zzcok > 0) {
                    String valueOf = String.valueOf(file.getAbsolutePath());
                    Log.e("FileDownloadTask", valueOf.length() != 0 ? "The file downloading to has been deleted:".concat(valueOf) : new String("The file downloading to has been deleted:"));
                    throw new IllegalStateException("expected a file to resume from.");
                } else if (!file.createNewFile()) {
                    String valueOf2 = String.valueOf(file.getAbsolutePath());
                    Log.w("FileDownloadTask", valueOf2.length() != 0 ? "unable to create file:".concat(valueOf2) : new String("unable to create file:"));
                }
            }
            if (this.zzcok > 0) {
                String valueOf3 = String.valueOf(file.getAbsolutePath());
                Log.d("FileDownloadTask", new StringBuilder(String.valueOf(valueOf3).length() + 47).append("Resuming download file ").append(valueOf3).append(" at ").append(this.zzcok).toString());
                fileOutputStream = new FileOutputStream(file, true);
            } else {
                fileOutputStream = new FileOutputStream(file);
            }
            try {
                byte[] bArr = new byte[262144];
                boolean z = true;
                while (z) {
                    int zza = zza(stream, bArr);
                    if (zza == -1) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, zza);
                    this.zzcoi += (long) zza;
                    if (this.zzbMj != null) {
                        Log.d("FileDownloadTask", "Exception occurred during file download. Retrying.", this.zzbMj);
                        this.zzbMj = null;
                        z = false;
                    }
                    if (!zzj(4, false)) {
                        z = false;
                    }
                }
                return z;
            } finally {
                fileOutputStream.flush();
                fileOutputStream.close();
                stream.close();
            }
        } else {
            this.zzbMj = new IllegalStateException("Unable to open Firebase Storage stream.");
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public final StorageReference getStorage() {
        return this.zzcoe;
    }

    /* access modifiers changed from: package-private */
    public final long getTotalBytes() {
        return this.zzaNq;
    }

    /* access modifiers changed from: protected */
    public void onCanceled() {
        this.zzcog.cancel();
        this.zzbMj = StorageException.fromErrorStatus(Status.zzaBq);
    }

    /* access modifiers changed from: package-private */
    public final void run() {
        if (this.zzbMj != null) {
            zzj(64, false);
        } else if (zzj(4, false)) {
            do {
                this.zzcoi = 0;
                this.zzbMj = null;
                this.zzcog.reset();
                try {
                    acf zza = ace.zzg(this.zzcoe.getStorage().getApp()).zza(this.zzcoe.zzKP(), this.zzcok);
                    this.zzcog.zza(zza, false);
                    this.mResultCode = zza.getResultCode();
                    this.zzbMj = zza.getException() != null ? zza.getException() : this.zzbMj;
                    int i = this.mResultCode;
                    boolean z = (i == 308 || (i >= 200 && i < 300)) && this.zzbMj == null && zzKR() == 4;
                    if (z) {
                        this.zzaNq = (long) zza.zzLl();
                        String zzhO = zza.zzhO(Headers.ETAG);
                        if (TextUtils.isEmpty(zzhO) || this.zzcoj == null || this.zzcoj.equals(zzhO)) {
                            this.zzcoj = zzhO;
                            try {
                                z = zza(zza);
                            } catch (IOException e) {
                                Log.e("FileDownloadTask", "Exception occurred during file write.  Aborting.", e);
                                this.zzbMj = e;
                            }
                        } else {
                            Log.w("FileDownloadTask", "The file at the server has changed.  Restarting from the beginning.");
                            this.zzcok = 0;
                            this.zzcoj = null;
                            zza.zzLf();
                            schedule();
                            return;
                        }
                    }
                    zza.zzLf();
                    if (z && this.zzbMj == null && zzKR() == 4) {
                        zzj(128, false);
                        return;
                    }
                    File file = new File(this.zzcoh.getPath());
                    if (file.exists()) {
                        this.zzcok = file.length();
                    } else {
                        this.zzcok = 0;
                    }
                    if (zzKR() == 8) {
                        zzj(16, false);
                        return;
                    } else if (zzKR() == 32) {
                        if (!zzj(256, false)) {
                            Log.w("FileDownloadTask", new StringBuilder(62).append("Unable to change download task to final state from ").append(zzKR()).toString());
                            return;
                        }
                        return;
                    }
                } catch (RemoteException e2) {
                    Log.e("FileDownloadTask", "Unable to create firebase storage network request.", e2);
                    this.zzbMj = e2;
                    zzj(64, false);
                    return;
                }
            } while (this.zzcoi > 0);
            zzj(64, false);
        }
    }

    /* access modifiers changed from: protected */
    public void schedule() {
        zzt.zzu(zzEf());
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public final /* synthetic */ StorageTask.ProvideError zzKM() {
        return new TaskSnapshot(StorageException.fromExceptionAndHttpCode(this.zzbMj, this.mResultCode), this.zzcoi + this.zzcok);
    }
}

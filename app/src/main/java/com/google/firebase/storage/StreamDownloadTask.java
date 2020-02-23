package com.google.firebase.storage;

import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import com.amazonaws.services.s3.Headers;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.abu;
import com.google.android.gms.internal.acf;
import com.google.firebase.storage.StorageTask;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;

public class StreamDownloadTask extends StorageTask<TaskSnapshot> {
    private volatile int mResultCode = 0;
    private long zzaNq = -1;
    private volatile Exception zzbMj = null;
    /* access modifiers changed from: private */
    public InputStream zzbSo;
    private StorageReference zzcoe;
    private abu zzcog;
    private long zzcoi;
    private String zzcoj;
    private StreamProcessor zzcpp;
    private long zzcpq;
    /* access modifiers changed from: private */
    public acf zzcpr;

    public interface StreamProcessor {
        void doInBackground(TaskSnapshot taskSnapshot, InputStream inputStream) throws IOException;
    }

    public class TaskSnapshot extends StorageTask<TaskSnapshot>.SnapshotBase {
        private final long zzcoi;

        TaskSnapshot(Exception exc, long j) {
            super(exc);
            this.zzcoi = j;
        }

        public long getBytesTransferred() {
            return this.zzcoi;
        }

        public InputStream getStream() {
            return StreamDownloadTask.this.zzbSo;
        }

        public long getTotalByteCount() {
            return StreamDownloadTask.this.getTotalBytes();
        }
    }

    static class zza extends InputStream {
        @Nullable
        private StreamDownloadTask zzcpt;
        @Nullable
        private InputStream zzcpu;
        private Callable<InputStream> zzcpv;
        private IOException zzcpw;
        private int zzcpx;
        private int zzcpy;
        private boolean zzcpz;

        zza(@NonNull Callable<InputStream> callable, @Nullable StreamDownloadTask streamDownloadTask) {
            this.zzcpt = streamDownloadTask;
            this.zzcpv = callable;
        }

        private final void zzKX() throws IOException {
            if (this.zzcpt != null && this.zzcpt.zzKR() == 32) {
                throw StorageException.zzcos;
            }
        }

        /* access modifiers changed from: private */
        public final boolean zzKY() throws IOException {
            zzKX();
            if (this.zzcpw != null) {
                try {
                    if (this.zzcpu != null) {
                        this.zzcpu.close();
                    }
                } catch (IOException e) {
                }
                this.zzcpu = null;
                if (this.zzcpy == this.zzcpx) {
                    Log.i("StreamDownloadTask", "Encountered exception during stream operation. Aborting.", this.zzcpw);
                    return false;
                }
                Log.i("StreamDownloadTask", new StringBuilder(70).append("Encountered exception during stream operation. Retrying at ").append(this.zzcpx).toString(), this.zzcpw);
                this.zzcpy = this.zzcpx;
                this.zzcpw = null;
            }
            if (this.zzcpz) {
                throw new IOException("Can't perform operation on closed stream");
            }
            if (this.zzcpu == null) {
                try {
                    this.zzcpu = this.zzcpv.call();
                } catch (Exception e2) {
                    if (e2 instanceof IOException) {
                        throw ((IOException) e2);
                    }
                    throw new IOException("Unable to open stream", e2);
                }
            }
            return true;
        }

        private final void zzaN(long j) {
            if (this.zzcpt != null) {
                this.zzcpt.zzaN(j);
            }
            this.zzcpx = (int) (((long) this.zzcpx) + j);
        }

        public final int available() throws IOException {
            while (zzKY()) {
                try {
                    return this.zzcpu.available();
                } catch (IOException e) {
                    this.zzcpw = e;
                }
            }
            throw this.zzcpw;
        }

        public final void close() throws IOException {
            if (this.zzcpu != null) {
                this.zzcpu.close();
            }
            this.zzcpz = true;
            if (!(this.zzcpt == null || this.zzcpt.zzcpr == null)) {
                this.zzcpt.zzcpr.zzLf();
                acf unused = this.zzcpt.zzcpr = null;
            }
            zzKX();
        }

        public final void mark(int i) {
        }

        public final boolean markSupported() {
            return false;
        }

        public final int read() throws IOException {
            while (zzKY()) {
                try {
                    int read = this.zzcpu.read();
                    if (read != -1) {
                        zzaN(1);
                    }
                    return read;
                } catch (IOException e) {
                    this.zzcpw = e;
                }
            }
            throw this.zzcpw;
        }

        public final int read(@NonNull byte[] bArr, int i, int i2) throws IOException {
            int i3 = 0;
            while (zzKY()) {
                while (((long) i2) > PlaybackStateCompat.ACTION_SET_REPEAT_MODE) {
                    try {
                        int read = this.zzcpu.read(bArr, i, 262144);
                        if (read != -1) {
                            i3 += read;
                            i += read;
                            i2 -= read;
                            zzaN((long) read);
                            zzKX();
                        } else if (i3 == 0) {
                            return -1;
                        } else {
                            return i3;
                        }
                    } catch (IOException e) {
                        this.zzcpw = e;
                    }
                }
                if (i2 > 0) {
                    int read2 = this.zzcpu.read(bArr, i, i2);
                    if (read2 != -1) {
                        i += read2;
                        i3 += read2;
                        i2 -= read2;
                        zzaN((long) read2);
                    } else if (i3 == 0) {
                        return -1;
                    } else {
                        return i3;
                    }
                }
                if (i2 == 0) {
                    return i3;
                }
            }
            throw this.zzcpw;
        }

        public final long skip(long j) throws IOException {
            int i = 0;
            while (zzKY()) {
                while (j > PlaybackStateCompat.ACTION_SET_REPEAT_MODE) {
                    try {
                        long skip = this.zzcpu.skip(PlaybackStateCompat.ACTION_SET_REPEAT_MODE);
                        if (skip >= 0) {
                            i = (int) (((long) i) + skip);
                            j -= skip;
                            zzaN(skip);
                            zzKX();
                        } else if (i == 0) {
                            return -1;
                        } else {
                            return (long) i;
                        }
                    } catch (IOException e) {
                        this.zzcpw = e;
                    }
                }
                if (j > 0) {
                    long skip2 = this.zzcpu.skip(j);
                    if (skip2 >= 0) {
                        i = (int) (((long) i) + skip2);
                        j -= skip2;
                        zzaN(skip2);
                    } else if (i == 0) {
                        return -1;
                    } else {
                        return (long) i;
                    }
                }
                if (j == 0) {
                    return (long) i;
                }
            }
            throw this.zzcpw;
        }
    }

    StreamDownloadTask(@NonNull StorageReference storageReference) {
        this.zzcoe = storageReference;
        this.zzcog = new abu(this.zzcoe.getStorage().getApp(), this.zzcoe.getStorage().getMaxDownloadRetryTimeMillis());
    }

    /* access modifiers changed from: private */
    public final InputStream zzKW() throws Exception {
        this.zzcog.reset();
        if (this.zzcpr != null) {
            this.zzcpr.zzLf();
        }
        try {
            this.zzcpr = this.zzcoe.zzKO().zza(this.zzcoe.zzKP(), this.zzcoi);
            this.zzcog.zza(this.zzcpr, false);
            this.mResultCode = this.zzcpr.getResultCode();
            this.zzbMj = this.zzcpr.getException() != null ? this.zzcpr.getException() : this.zzbMj;
            int i = this.mResultCode;
            if ((i == 308 || (i >= 200 && i < 300)) && this.zzbMj == null && zzKR() == 4) {
                String zzhO = this.zzcpr.zzhO(Headers.ETAG);
                if (TextUtils.isEmpty(zzhO) || this.zzcoj == null || this.zzcoj.equals(zzhO)) {
                    this.zzcoj = zzhO;
                    if (this.zzaNq == -1) {
                        this.zzaNq = (long) this.zzcpr.zzLl();
                    }
                    return this.zzcpr.getStream();
                }
                this.mResultCode = 409;
                throw new IOException("The ETag on the server changed.");
            }
            throw new IOException("Could not open resulting stream.");
        } catch (RemoteException e) {
            Log.e("StreamDownloadTask", "Unable to create firebase storage network request.", e);
            throw e;
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

    /* access modifiers changed from: protected */
    public void onProgress() {
        this.zzcpq = this.zzcoi;
    }

    public boolean pause() {
        throw new UnsupportedOperationException("this operation is not supported on StreamDownloadTask.");
    }

    public boolean resume() {
        throw new UnsupportedOperationException("this operation is not supported on StreamDownloadTask.");
    }

    /* access modifiers changed from: package-private */
    public final void run() {
        if (this.zzbMj != null) {
            zzj(64, false);
        } else if (zzj(4, false)) {
            zza zza2 = new zza(new zzv(this), this);
            this.zzbSo = new BufferedInputStream(zza2);
            try {
                boolean unused = zza2.zzKY();
                if (this.zzcpp != null) {
                    try {
                        this.zzcpp.doInBackground((TaskSnapshot) zzKS(), this.zzbSo);
                    } catch (Exception e) {
                        Log.w("StreamDownloadTask", "Exception occurred calling doInBackground.", e);
                        this.zzbMj = e;
                    }
                }
            } catch (IOException e2) {
                Log.d("StreamDownloadTask", "Initial opening of Stream failed", e2);
                this.zzbMj = e2;
            }
            if (this.zzbSo == null) {
                this.zzcpr.zzLf();
                this.zzcpr = null;
            }
            if (this.zzbMj == null && zzKR() == 4) {
                zzj(4, false);
                zzj(128, false);
                return;
            }
            if (!zzj(zzKR() == 32 ? 256 : 64, false)) {
                Log.w("StreamDownloadTask", new StringBuilder(62).append("Unable to change download task to final state from ").append(zzKR()).toString());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void schedule() {
        zzt.zzu(zzEf());
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public final /* synthetic */ StorageTask.ProvideError zzKM() {
        return new TaskSnapshot(StorageException.fromExceptionAndHttpCode(this.zzbMj, this.mResultCode), this.zzcpq);
    }

    /* access modifiers changed from: package-private */
    public final StreamDownloadTask zza(@NonNull StreamProcessor streamProcessor) {
        zzbo.zzu(streamProcessor);
        zzbo.zzae(this.zzcpp == null);
        this.zzcpp = streamProcessor;
        return this;
    }

    /* access modifiers changed from: package-private */
    public final void zzaN(long j) {
        this.zzcoi += j;
        if (this.zzcpq + PlaybackStateCompat.ACTION_SET_REPEAT_MODE > this.zzcoi) {
            return;
        }
        if (zzKR() == 4) {
            zzj(4, false);
        } else {
            this.zzcpq = this.zzcoi;
        }
    }
}

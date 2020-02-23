package com.google.firebase.storage;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.abt;
import com.google.android.gms.internal.abu;
import com.google.android.gms.internal.abz;
import com.google.android.gms.internal.acf;
import com.google.firebase.storage.StorageTask;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicLong;

public class UploadTask extends StorageTask<TaskSnapshot> {
    private volatile int mResultCode = 0;
    private final Uri mUri;
    private volatile Exception zzbMj = null;
    private volatile StorageMetadata zzcoK;
    /* access modifiers changed from: private */
    public final StorageReference zzcoe;
    private abu zzcog;
    private final long zzcpK;
    private final abt zzcpL;
    private final AtomicLong zzcpM = new AtomicLong(0);
    private int zzcpN = 262144;
    private boolean zzcpO;
    private volatile Uri zzcpP = null;
    private volatile Exception zzcpQ = null;
    private volatile String zzcpR;

    public class TaskSnapshot extends StorageTask<TaskSnapshot>.SnapshotBase {
        private final StorageMetadata zzcoK;
        private final Uri zzcpP;
        private final long zzcpU;

        TaskSnapshot(Exception exc, long j, Uri uri, StorageMetadata storageMetadata) {
            super(exc);
            this.zzcpU = j;
            this.zzcpP = uri;
            this.zzcoK = storageMetadata;
        }

        public long getBytesTransferred() {
            return this.zzcpU;
        }

        @Nullable
        public Uri getDownloadUrl() {
            StorageMetadata metadata = getMetadata();
            if (metadata != null) {
                return metadata.getDownloadUrl();
            }
            return null;
        }

        @Nullable
        public StorageMetadata getMetadata() {
            return this.zzcoK;
        }

        public long getTotalByteCount() {
            return UploadTask.this.getTotalByteCount();
        }

        @Nullable
        public Uri getUploadSessionUri() {
            return this.zzcpP;
        }
    }

    UploadTask(StorageReference storageReference, StorageMetadata storageMetadata, Uri uri, Uri uri2) {
        FileNotFoundException e;
        long j;
        InputStream inputStream;
        zzbo.zzu(storageReference);
        zzbo.zzu(uri);
        this.zzcoe = storageReference;
        this.zzcoK = storageMetadata;
        this.mUri = uri;
        this.zzcog = new abu(this.zzcoe.getStorage().getApp(), this.zzcoe.getStorage().getMaxUploadRetryTimeMillis());
        long j2 = -1;
        try {
            ContentResolver contentResolver = this.zzcoe.getStorage().getApp().getApplicationContext().getContentResolver();
            try {
                ParcelFileDescriptor openFileDescriptor = contentResolver.openFileDescriptor(this.mUri, "r");
                if (openFileDescriptor != null) {
                    j2 = openFileDescriptor.getStatSize();
                    openFileDescriptor.close();
                }
            } catch (NullPointerException e2) {
                Log.w("UploadTask", "NullPointerException during file size calculation.", e2);
                j2 = -1;
            } catch (IOException e3) {
                String valueOf = String.valueOf(this.mUri.toString());
                Log.w("UploadTask", valueOf.length() != 0 ? "could not retrieve file size for upload ".concat(valueOf) : new String("could not retrieve file size for upload "), e3);
            }
            InputStream openInputStream = contentResolver.openInputStream(this.mUri);
            if (openInputStream != null) {
                if (j2 == -1) {
                    try {
                        int available = openInputStream.available();
                        if (available >= 0) {
                            j2 = (long) available;
                        }
                    } catch (IOException e4) {
                    }
                }
                try {
                    inputStream = new BufferedInputStream(openInputStream);
                } catch (FileNotFoundException e5) {
                    e = e5;
                    j = j2;
                    inputStream = openInputStream;
                    String valueOf2 = String.valueOf(this.mUri.toString());
                    Log.e("UploadTask", valueOf2.length() != 0 ? "could not locate file for uploading:".concat(valueOf2) : new String("could not locate file for uploading:"));
                    this.zzbMj = e;
                    j2 = j;
                    this.zzcpK = j2;
                    this.zzcpL = new abt(inputStream, 262144);
                    this.zzcpO = true;
                    this.zzcpP = uri2;
                }
            } else {
                inputStream = openInputStream;
            }
        } catch (FileNotFoundException e6) {
            e = e6;
            j = j2;
            inputStream = null;
        }
        this.zzcpK = j2;
        this.zzcpL = new abt(inputStream, 262144);
        this.zzcpO = true;
        this.zzcpP = uri2;
    }

    UploadTask(StorageReference storageReference, StorageMetadata storageMetadata, InputStream inputStream) {
        zzbo.zzu(storageReference);
        zzbo.zzu(inputStream);
        this.zzcpK = -1;
        this.zzcoe = storageReference;
        this.zzcoK = storageMetadata;
        this.zzcpL = new abt(inputStream, 262144);
        this.zzcpO = false;
        this.mUri = null;
        this.zzcog = new abu(this.zzcoe.getStorage().getApp(), this.zzcoe.getStorage().getMaxUploadRetryTimeMillis());
    }

    UploadTask(StorageReference storageReference, StorageMetadata storageMetadata, byte[] bArr) {
        zzbo.zzu(storageReference);
        zzbo.zzu(bArr);
        this.zzcpK = (long) bArr.length;
        this.zzcoe = storageReference;
        this.zzcoK = storageMetadata;
        this.mUri = null;
        this.zzcpL = new abt(new ByteArrayInputStream(bArr), 262144);
        this.zzcpO = true;
        this.zzcog = new abu(this.zzcoe.getStorage().getApp(), this.zzcoe.getStorage().getMaxUploadRetryTimeMillis());
    }

    private final boolean zzLa() {
        if (zzKR() == 128) {
            return false;
        }
        if (Thread.interrupted()) {
            this.zzbMj = new InterruptedException();
            zzj(64, false);
            return false;
        } else if (zzKR() == 32) {
            zzj(256, false);
            return false;
        } else if (zzKR() == 8) {
            zzj(16, false);
            return false;
        } else if (!zzLb()) {
            return false;
        } else {
            if (this.zzcpP == null) {
                if (this.zzbMj == null) {
                    this.zzbMj = new IllegalStateException("Unable to obtain an upload URL.");
                }
                zzj(64, false);
                return false;
            } else if (this.zzbMj != null) {
                zzj(64, false);
                return false;
            } else {
                if (!(this.zzcpQ != null || this.mResultCode < 200 || this.mResultCode >= 300) || zzaK(true)) {
                    return true;
                }
                if (!zzLb()) {
                    return false;
                }
                zzj(64, false);
                return false;
            }
        }
    }

    private final boolean zzLb() {
        if (!"final".equals(this.zzcpR)) {
            return true;
        }
        if (this.zzbMj == null) {
            this.zzbMj = new IOException("The server has terminated the upload session");
        }
        zzj(64, false);
        return false;
    }

    private final boolean zzaK(boolean z) {
        try {
            acf zzb = this.zzcoe.zzKO().zzb(this.zzcoe.zzKP(), this.zzcpP.toString());
            if ("final".equals(this.zzcpR)) {
                return false;
            }
            if (z) {
                if (!zzc(zzb)) {
                    return false;
                }
            } else if (!zzb(zzb)) {
                return false;
            }
            if ("final".equals(zzb.zzhO("X-Goog-Upload-Status"))) {
                this.zzbMj = new IOException("The server has terminated the upload session");
                return false;
            }
            String zzhO = zzb.zzhO("X-Goog-Upload-Size-Received");
            long parseLong = !TextUtils.isEmpty(zzhO) ? Long.parseLong(zzhO) : 0;
            long j = this.zzcpM.get();
            if (j > parseLong) {
                this.zzbMj = new IOException("Unexpected error. The server lost a chunk update.");
                return false;
            }
            if (j < parseLong) {
                try {
                    if (((long) this.zzcpL.zzcg((int) (parseLong - j))) != parseLong - j) {
                        this.zzbMj = new IOException("Unexpected end of stream encountered.");
                        return false;
                    } else if (!this.zzcpM.compareAndSet(j, parseLong)) {
                        Log.e("UploadTask", "Somehow, the uploaded bytes changed during an uploaded.  This should nothappen");
                        this.zzbMj = new IllegalStateException("uploaded bytes changed unexpectedly.");
                        return false;
                    }
                } catch (IOException e) {
                    Log.e("UploadTask", "Unable to recover position in Stream during resumable upload", e);
                    this.zzbMj = e;
                    return false;
                }
            }
            return true;
        } catch (RemoteException e2) {
            Log.e("UploadTask", "Unable to recover status during resumable upload", e2);
            this.zzbMj = e2;
            return false;
        }
    }

    private final boolean zzb(acf acf) {
        acf.zze(abz.zzf(this.zzcoe.getStorage().getApp()), this.zzcoe.getStorage().getApp().getApplicationContext());
        return zzd(acf);
    }

    private final boolean zzc(acf acf) {
        this.zzcog.zza(acf, true);
        return zzd(acf);
    }

    private final boolean zzd(acf acf) {
        int resultCode = acf.getResultCode();
        if (abu.zzcj(resultCode)) {
            resultCode = -2;
        }
        this.mResultCode = resultCode;
        this.zzcpQ = acf.getException();
        this.zzcpR = acf.zzhO("X-Goog-Upload-Status");
        int i = this.mResultCode;
        return (i == 308 || (i >= 200 && i < 300)) && this.zzcpQ == null;
    }

    /* access modifiers changed from: package-private */
    public final StorageReference getStorage() {
        return this.zzcoe;
    }

    /* access modifiers changed from: package-private */
    public final long getTotalByteCount() {
        return this.zzcpK;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:5:0x0022  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onCanceled() {
        /*
            r4 = this;
            com.google.android.gms.internal.abu r0 = r4.zzcog
            r0.cancel()
            r1 = 0
            android.net.Uri r0 = r4.zzcpP
            if (r0 == 0) goto L_0x003e
            com.google.firebase.storage.StorageReference r0 = r4.zzcoe     // Catch:{ RemoteException -> 0x0036 }
            com.google.android.gms.internal.ace r0 = r0.zzKO()     // Catch:{ RemoteException -> 0x0036 }
            com.google.firebase.storage.StorageReference r2 = r4.zzcoe     // Catch:{ RemoteException -> 0x0036 }
            android.net.Uri r2 = r2.zzKP()     // Catch:{ RemoteException -> 0x0036 }
            android.net.Uri r3 = r4.zzcpP     // Catch:{ RemoteException -> 0x0036 }
            java.lang.String r3 = r3.toString()     // Catch:{ RemoteException -> 0x0036 }
            com.google.android.gms.internal.acf r0 = r0.zza((android.net.Uri) r2, (java.lang.String) r3)     // Catch:{ RemoteException -> 0x0036 }
        L_0x0020:
            if (r0 == 0) goto L_0x002a
            com.google.firebase.storage.zzac r1 = new com.google.firebase.storage.zzac
            r1.<init>(r4, r0)
            com.google.firebase.storage.zzt.zzs(r1)
        L_0x002a:
            com.google.android.gms.common.api.Status r0 = com.google.android.gms.common.api.Status.zzaBq
            com.google.firebase.storage.StorageException r0 = com.google.firebase.storage.StorageException.fromErrorStatus(r0)
            r4.zzbMj = r0
            super.onCanceled()
            return
        L_0x0036:
            r0 = move-exception
            java.lang.String r2 = "UploadTask"
            java.lang.String r3 = "Unable to create chunk upload request"
            android.util.Log.e(r2, r3, r0)
        L_0x003e:
            r0 = r1
            goto L_0x0020
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.storage.UploadTask.onCanceled():void");
    }

    /* access modifiers changed from: protected */
    public void resetState() {
        this.zzbMj = null;
        this.zzcpQ = null;
        this.mResultCode = 0;
        this.zzcpR = null;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0102  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0199 A[Catch:{ IOException -> 0x0120 }] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x01a4 A[Catch:{ IOException -> 0x0120 }] */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0099 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r10 = this;
            r1 = 0
            r9 = 4
            r8 = 0
            com.google.android.gms.internal.abu r0 = r10.zzcog
            r0.reset()
            boolean r0 = r10.zzj(r9, r8)
            if (r0 != 0) goto L_0x0016
            java.lang.String r0 = "UploadTask"
            java.lang.String r1 = "The upload cannot continue as it is not in a valid state."
            android.util.Log.d(r0, r1)
        L_0x0015:
            return
        L_0x0016:
            com.google.firebase.storage.StorageReference r0 = r10.zzcoe
            com.google.firebase.storage.StorageReference r0 = r0.getParent()
            if (r0 != 0) goto L_0x0027
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "Cannot upload to getRoot. You should upload to a storage location such as .getReference('image.png').putFile..."
            r0.<init>(r2)
            r10.zzbMj = r0
        L_0x0027:
            java.lang.Exception r0 = r10.zzbMj
            if (r0 != 0) goto L_0x0015
            android.net.Uri r0 = r10.zzcpP
            if (r0 != 0) goto L_0x0111
            com.google.firebase.storage.StorageMetadata r0 = r10.zzcoK
            if (r0 == 0) goto L_0x01cd
            com.google.firebase.storage.StorageMetadata r0 = r10.zzcoK
            java.lang.String r0 = r0.getContentType()
        L_0x0039:
            android.net.Uri r2 = r10.mUri
            if (r2 == 0) goto L_0x005b
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 == 0) goto L_0x005b
            com.google.firebase.storage.StorageReference r0 = r10.zzcoe
            com.google.firebase.storage.FirebaseStorage r0 = r0.getStorage()
            com.google.firebase.FirebaseApp r0 = r0.getApp()
            android.content.Context r0 = r0.getApplicationContext()
            android.content.ContentResolver r0 = r0.getContentResolver()
            android.net.Uri r2 = r10.mUri
            java.lang.String r0 = r0.getType(r2)
        L_0x005b:
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 == 0) goto L_0x0063
            java.lang.String r0 = "application/octet-stream"
        L_0x0063:
            com.google.firebase.storage.StorageReference r2 = r10.zzcoe     // Catch:{ JSONException -> 0x01ca, RemoteException -> 0x0106 }
            com.google.android.gms.internal.ace r2 = r2.zzKO()     // Catch:{ JSONException -> 0x01ca, RemoteException -> 0x0106 }
            com.google.firebase.storage.StorageReference r3 = r10.zzcoe     // Catch:{ JSONException -> 0x01ca, RemoteException -> 0x0106 }
            android.net.Uri r3 = r3.zzKP()     // Catch:{ JSONException -> 0x01ca, RemoteException -> 0x0106 }
            com.google.firebase.storage.StorageMetadata r4 = r10.zzcoK     // Catch:{ JSONException -> 0x01ca, RemoteException -> 0x0106 }
            if (r4 == 0) goto L_0x0079
            com.google.firebase.storage.StorageMetadata r1 = r10.zzcoK     // Catch:{ JSONException -> 0x01ca, RemoteException -> 0x0106 }
            org.json.JSONObject r1 = r1.zzKN()     // Catch:{ JSONException -> 0x01ca, RemoteException -> 0x0106 }
        L_0x0079:
            com.google.android.gms.internal.acf r0 = r2.zza(r3, r1, r0)     // Catch:{ JSONException -> 0x01ca, RemoteException -> 0x0106 }
            boolean r1 = r10.zzc(r0)
            if (r1 == 0) goto L_0x0095
            java.lang.String r1 = "X-Goog-Upload-URL"
            java.lang.String r0 = r0.zzhO(r1)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x0095
            android.net.Uri r0 = android.net.Uri.parse(r0)
            r10.zzcpP = r0
        L_0x0095:
            boolean r0 = r10.zzLa()
        L_0x0099:
            if (r0 == 0) goto L_0x01aa
            com.google.android.gms.internal.abt r0 = r10.zzcpL     // Catch:{ IOException -> 0x0120 }
            int r1 = r10.zzcpN     // Catch:{ IOException -> 0x0120 }
            r0.zzch(r1)     // Catch:{ IOException -> 0x0120 }
            int r0 = r10.zzcpN     // Catch:{ IOException -> 0x0120 }
            com.google.android.gms.internal.abt r1 = r10.zzcpL     // Catch:{ IOException -> 0x0120 }
            int r1 = r1.available()     // Catch:{ IOException -> 0x0120 }
            int r6 = java.lang.Math.min(r0, r1)     // Catch:{ IOException -> 0x0120 }
            com.google.firebase.storage.StorageReference r0 = r10.zzcoe     // Catch:{ RemoteException -> 0x0115 }
            com.google.android.gms.internal.ace r0 = r0.zzKO()     // Catch:{ RemoteException -> 0x0115 }
            com.google.firebase.storage.StorageReference r1 = r10.zzcoe     // Catch:{ RemoteException -> 0x0115 }
            android.net.Uri r1 = r1.zzKP()     // Catch:{ RemoteException -> 0x0115 }
            android.net.Uri r2 = r10.zzcpP     // Catch:{ RemoteException -> 0x0115 }
            java.lang.String r2 = r2.toString()     // Catch:{ RemoteException -> 0x0115 }
            com.google.android.gms.internal.abt r3 = r10.zzcpL     // Catch:{ RemoteException -> 0x0115 }
            byte[] r3 = r3.zzLe()     // Catch:{ RemoteException -> 0x0115 }
            java.util.concurrent.atomic.AtomicLong r4 = r10.zzcpM     // Catch:{ RemoteException -> 0x0115 }
            long r4 = r4.get()     // Catch:{ RemoteException -> 0x0115 }
            com.google.android.gms.internal.abt r7 = r10.zzcpL     // Catch:{ RemoteException -> 0x0115 }
            boolean r7 = r7.isFinished()     // Catch:{ RemoteException -> 0x0115 }
            com.google.android.gms.internal.acf r2 = r0.zza(r1, r2, r3, r4, r6, r7)     // Catch:{ RemoteException -> 0x0115 }
            boolean r0 = r10.zzb(r2)     // Catch:{ IOException -> 0x0120 }
            if (r0 != 0) goto L_0x012b
            r0 = 262144(0x40000, float:3.67342E-40)
            r10.zzcpN = r0     // Catch:{ IOException -> 0x0120 }
            java.lang.String r0 = "UploadTask"
            int r1 = r10.zzcpN     // Catch:{ IOException -> 0x0120 }
            r2 = 35
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0120 }
            r3.<init>(r2)     // Catch:{ IOException -> 0x0120 }
            java.lang.String r2 = "Resetting chunk size to "
            java.lang.StringBuilder r2 = r3.append(r2)     // Catch:{ IOException -> 0x0120 }
            java.lang.StringBuilder r1 = r2.append(r1)     // Catch:{ IOException -> 0x0120 }
            java.lang.String r1 = r1.toString()     // Catch:{ IOException -> 0x0120 }
            android.util.Log.d(r0, r1)     // Catch:{ IOException -> 0x0120 }
        L_0x00fc:
            boolean r0 = r10.zzLa()
            if (r0 == 0) goto L_0x0099
            r10.zzj(r9, r8)
            goto L_0x0099
        L_0x0106:
            r0 = move-exception
        L_0x0107:
            java.lang.String r1 = "UploadTask"
            java.lang.String r2 = "Unable to create a network request from metadata"
            android.util.Log.e(r1, r2, r0)
            r10.zzbMj = r0
            goto L_0x0095
        L_0x0111:
            r10.zzaK(r8)
            goto L_0x0095
        L_0x0115:
            r0 = move-exception
            java.lang.String r1 = "UploadTask"
            java.lang.String r2 = "Unable to create chunk upload request"
            android.util.Log.e(r1, r2, r0)     // Catch:{ IOException -> 0x0120 }
            r10.zzbMj = r0     // Catch:{ IOException -> 0x0120 }
            goto L_0x00fc
        L_0x0120:
            r0 = move-exception
            java.lang.String r1 = "UploadTask"
            java.lang.String r2 = "Unable to read bytes for uploading"
            android.util.Log.e(r1, r2, r0)
            r10.zzbMj = r0
            goto L_0x00fc
        L_0x012b:
            java.util.concurrent.atomic.AtomicLong r0 = r10.zzcpM     // Catch:{ IOException -> 0x0120 }
            long r4 = (long) r6     // Catch:{ IOException -> 0x0120 }
            r0.getAndAdd(r4)     // Catch:{ IOException -> 0x0120 }
            com.google.android.gms.internal.abt r0 = r10.zzcpL     // Catch:{ IOException -> 0x0120 }
            boolean r0 = r0.isFinished()     // Catch:{ IOException -> 0x0120 }
            if (r0 != 0) goto L_0x0167
            com.google.android.gms.internal.abt r0 = r10.zzcpL     // Catch:{ IOException -> 0x0120 }
            r0.zzcg(r6)     // Catch:{ IOException -> 0x0120 }
            int r0 = r10.zzcpN     // Catch:{ IOException -> 0x0120 }
            r1 = 33554432(0x2000000, float:9.403955E-38)
            if (r0 >= r1) goto L_0x00fc
            int r0 = r10.zzcpN     // Catch:{ IOException -> 0x0120 }
            int r0 = r0 << 1
            r10.zzcpN = r0     // Catch:{ IOException -> 0x0120 }
            java.lang.String r0 = "UploadTask"
            int r1 = r10.zzcpN     // Catch:{ IOException -> 0x0120 }
            r2 = 36
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0120 }
            r3.<init>(r2)     // Catch:{ IOException -> 0x0120 }
            java.lang.String r2 = "Increasing chunk size to "
            java.lang.StringBuilder r2 = r3.append(r2)     // Catch:{ IOException -> 0x0120 }
            java.lang.StringBuilder r1 = r2.append(r1)     // Catch:{ IOException -> 0x0120 }
            java.lang.String r1 = r1.toString()     // Catch:{ IOException -> 0x0120 }
            android.util.Log.d(r0, r1)     // Catch:{ IOException -> 0x0120 }
            goto L_0x00fc
        L_0x0167:
            com.google.firebase.storage.StorageMetadata$Builder r0 = new com.google.firebase.storage.StorageMetadata$Builder     // Catch:{ JSONException -> 0x0185, RemoteException -> 0x01c7 }
            org.json.JSONObject r1 = r2.zzLn()     // Catch:{ JSONException -> 0x0185, RemoteException -> 0x01c7 }
            com.google.firebase.storage.StorageReference r3 = r10.zzcoe     // Catch:{ JSONException -> 0x0185, RemoteException -> 0x01c7 }
            r0.<init>(r1, r3)     // Catch:{ JSONException -> 0x0185, RemoteException -> 0x01c7 }
            com.google.firebase.storage.StorageMetadata r0 = r0.build()     // Catch:{ JSONException -> 0x0185, RemoteException -> 0x01c7 }
            r10.zzcoK = r0     // Catch:{ JSONException -> 0x0185, RemoteException -> 0x01c7 }
            r0 = 4
            r1 = 0
            r10.zzj(r0, r1)     // Catch:{ IOException -> 0x0120 }
            r0 = 128(0x80, float:1.794E-43)
            r1 = 0
            r10.zzj(r0, r1)     // Catch:{ IOException -> 0x0120 }
            goto L_0x00fc
        L_0x0185:
            r0 = move-exception
            r1 = r0
        L_0x0187:
            java.lang.String r3 = "UploadTask"
            java.lang.String r4 = "Unable to parse resulting metadata from upload:"
            java.lang.String r0 = r2.zzLi()     // Catch:{ IOException -> 0x0120 }
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ IOException -> 0x0120 }
            int r2 = r0.length()     // Catch:{ IOException -> 0x0120 }
            if (r2 == 0) goto L_0x01a4
            java.lang.String r0 = r4.concat(r0)     // Catch:{ IOException -> 0x0120 }
        L_0x019d:
            android.util.Log.e(r3, r0, r1)     // Catch:{ IOException -> 0x0120 }
            r10.zzbMj = r1     // Catch:{ IOException -> 0x0120 }
            goto L_0x00fc
        L_0x01a4:
            java.lang.String r0 = new java.lang.String     // Catch:{ IOException -> 0x0120 }
            r0.<init>(r4)     // Catch:{ IOException -> 0x0120 }
            goto L_0x019d
        L_0x01aa:
            boolean r0 = r10.zzcpO
            if (r0 == 0) goto L_0x0015
            int r0 = r10.zzKR()
            r1 = 16
            if (r0 == r1) goto L_0x0015
            com.google.android.gms.internal.abt r0 = r10.zzcpL     // Catch:{ IOException -> 0x01bd }
            r0.close()     // Catch:{ IOException -> 0x01bd }
            goto L_0x0015
        L_0x01bd:
            r0 = move-exception
            java.lang.String r1 = "UploadTask"
            java.lang.String r2 = "Unable to close stream."
            android.util.Log.e(r1, r2, r0)
            goto L_0x0015
        L_0x01c7:
            r0 = move-exception
            r1 = r0
            goto L_0x0187
        L_0x01ca:
            r0 = move-exception
            goto L_0x0107
        L_0x01cd:
            r0 = r1
            goto L_0x0039
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.storage.UploadTask.run():void");
    }

    /* access modifiers changed from: protected */
    public void schedule() {
        zzt.zzt(zzEf());
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public final /* synthetic */ StorageTask.ProvideError zzKM() {
        return new TaskSnapshot(StorageException.fromExceptionAndHttpCode(this.zzbMj != null ? this.zzbMj : this.zzcpQ, this.mResultCode), this.zzcpM.get(), this.zzcpP, this.zzcoK);
    }
}

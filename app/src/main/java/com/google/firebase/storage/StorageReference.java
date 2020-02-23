package com.google.firebase.storage;

import android.net.Uri;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.appsflyer.share.Constants;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.abv;
import com.google.android.gms.internal.ace;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.storage.StreamDownloadTask;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class StorageReference {
    private final Uri zzcoM;
    private final FirebaseStorage zzcoN;

    StorageReference(@NonNull Uri uri, @NonNull FirebaseStorage firebaseStorage) {
        boolean z = true;
        zzbo.zzb(uri != null, (Object) "storageUri cannot be null");
        zzbo.zzb(firebaseStorage == null ? false : z, (Object) "FirebaseApp cannot be null");
        this.zzcoM = uri;
        this.zzcoN = firebaseStorage;
    }

    @NonNull
    public StorageReference child(@NonNull String str) {
        zzbo.zzb(!TextUtils.isEmpty(str), (Object) "childName cannot be null or empty");
        String zzhK = abv.zzhK(str);
        try {
            return new StorageReference(this.zzcoM.buildUpon().appendEncodedPath(abv.zzhI(zzhK)).build(), this.zzcoN);
        } catch (UnsupportedEncodingException e) {
            UnsupportedEncodingException unsupportedEncodingException = e;
            String valueOf = String.valueOf(zzhK);
            Log.e("StorageReference", valueOf.length() != 0 ? "Unable to create a valid default Uri. ".concat(valueOf) : new String("Unable to create a valid default Uri. "), unsupportedEncodingException);
            throw new IllegalArgumentException("childName");
        }
    }

    public Task<Void> delete() {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        zzt.zzs(new zza(this, taskCompletionSource));
        return taskCompletionSource.getTask();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof StorageReference)) {
            return false;
        }
        return ((StorageReference) obj).toString().equals(toString());
    }

    @NonNull
    public List<FileDownloadTask> getActiveDownloadTasks() {
        return zzs.zzKV().zzb(this);
    }

    @NonNull
    public List<UploadTask> getActiveUploadTasks() {
        return zzs.zzKV().zza(this);
    }

    @NonNull
    public String getBucket() {
        return this.zzcoM.getAuthority();
    }

    @NonNull
    public Task<byte[]> getBytes(long j) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        StreamDownloadTask streamDownloadTask = new StreamDownloadTask(this);
        ((StorageTask) streamDownloadTask.zza((StreamDownloadTask.StreamProcessor) new zzh(this, j, taskCompletionSource)).addOnSuccessListener((OnSuccessListener) new zzg(this, taskCompletionSource))).addOnFailureListener((OnFailureListener) new zzf(this, taskCompletionSource));
        streamDownloadTask.zzKQ();
        return taskCompletionSource.getTask();
    }

    @NonNull
    public Task<Uri> getDownloadUrl() {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        Task<StorageMetadata> metadata = getMetadata();
        metadata.addOnSuccessListener(new zzd(this, taskCompletionSource));
        metadata.addOnFailureListener(new zze(this, taskCompletionSource));
        return taskCompletionSource.getTask();
    }

    @NonNull
    public FileDownloadTask getFile(@NonNull Uri uri) {
        FileDownloadTask fileDownloadTask = new FileDownloadTask(this, uri);
        fileDownloadTask.zzKQ();
        return fileDownloadTask;
    }

    @NonNull
    public FileDownloadTask getFile(@NonNull File file) {
        return getFile(Uri.fromFile(file));
    }

    @NonNull
    public Task<StorageMetadata> getMetadata() {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        zzt.zzs(new zzb(this, taskCompletionSource));
        return taskCompletionSource.getTask();
    }

    @NonNull
    public String getName() {
        String path = this.zzcoM.getPath();
        int lastIndexOf = path.lastIndexOf(47);
        return lastIndexOf != -1 ? path.substring(lastIndexOf + 1) : path;
    }

    @Nullable
    public StorageReference getParent() {
        String path = this.zzcoM.getPath();
        if (path == null || path.equals(Constants.URL_PATH_DELIMITER)) {
            return null;
        }
        int lastIndexOf = path.lastIndexOf(47);
        return new StorageReference(this.zzcoM.buildUpon().path(lastIndexOf == -1 ? Constants.URL_PATH_DELIMITER : path.substring(0, lastIndexOf)).build(), this.zzcoN);
    }

    @NonNull
    public String getPath() {
        return this.zzcoM.getPath();
    }

    @NonNull
    public StorageReference getRoot() {
        return new StorageReference(this.zzcoM.buildUpon().path("").build(), this.zzcoN);
    }

    @NonNull
    public FirebaseStorage getStorage() {
        return this.zzcoN;
    }

    @NonNull
    public StreamDownloadTask getStream() {
        StreamDownloadTask streamDownloadTask = new StreamDownloadTask(this);
        streamDownloadTask.zzKQ();
        return streamDownloadTask;
    }

    @NonNull
    public StreamDownloadTask getStream(@NonNull StreamDownloadTask.StreamProcessor streamProcessor) {
        StreamDownloadTask streamDownloadTask = new StreamDownloadTask(this);
        streamDownloadTask.zza(streamProcessor);
        streamDownloadTask.zzKQ();
        return streamDownloadTask;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    @NonNull
    public UploadTask putBytes(@NonNull byte[] bArr) {
        zzbo.zzb(bArr != null, (Object) "bytes cannot be null");
        UploadTask uploadTask = new UploadTask(this, (StorageMetadata) null, bArr);
        uploadTask.zzKQ();
        return uploadTask;
    }

    @NonNull
    public UploadTask putBytes(@NonNull byte[] bArr, @NonNull StorageMetadata storageMetadata) {
        boolean z = true;
        zzbo.zzb(bArr != null, (Object) "bytes cannot be null");
        if (storageMetadata == null) {
            z = false;
        }
        zzbo.zzb(z, (Object) "metadata cannot be null");
        UploadTask uploadTask = new UploadTask(this, storageMetadata, bArr);
        uploadTask.zzKQ();
        return uploadTask;
    }

    @NonNull
    public UploadTask putFile(@NonNull Uri uri) {
        zzbo.zzb(uri != null, (Object) "uri cannot be null");
        UploadTask uploadTask = new UploadTask(this, (StorageMetadata) null, uri, (Uri) null);
        uploadTask.zzKQ();
        return uploadTask;
    }

    @NonNull
    public UploadTask putFile(@NonNull Uri uri, @NonNull StorageMetadata storageMetadata) {
        boolean z = true;
        zzbo.zzb(uri != null, (Object) "uri cannot be null");
        if (storageMetadata == null) {
            z = false;
        }
        zzbo.zzb(z, (Object) "metadata cannot be null");
        UploadTask uploadTask = new UploadTask(this, storageMetadata, uri, (Uri) null);
        uploadTask.zzKQ();
        return uploadTask;
    }

    @NonNull
    public UploadTask putFile(@NonNull Uri uri, @Nullable StorageMetadata storageMetadata, @Nullable Uri uri2) {
        boolean z = true;
        zzbo.zzb(uri != null, (Object) "uri cannot be null");
        if (storageMetadata == null) {
            z = false;
        }
        zzbo.zzb(z, (Object) "metadata cannot be null");
        UploadTask uploadTask = new UploadTask(this, storageMetadata, uri, uri2);
        uploadTask.zzKQ();
        return uploadTask;
    }

    @NonNull
    public UploadTask putStream(@NonNull InputStream inputStream) {
        zzbo.zzb(inputStream != null, (Object) "stream cannot be null");
        UploadTask uploadTask = new UploadTask(this, (StorageMetadata) null, inputStream);
        uploadTask.zzKQ();
        return uploadTask;
    }

    @NonNull
    public UploadTask putStream(@NonNull InputStream inputStream, @NonNull StorageMetadata storageMetadata) {
        boolean z = true;
        zzbo.zzb(inputStream != null, (Object) "stream cannot be null");
        if (storageMetadata == null) {
            z = false;
        }
        zzbo.zzb(z, (Object) "metadata cannot be null");
        UploadTask uploadTask = new UploadTask(this, storageMetadata, inputStream);
        uploadTask.zzKQ();
        return uploadTask;
    }

    public String toString() {
        String valueOf = String.valueOf(this.zzcoM.getAuthority());
        String valueOf2 = String.valueOf(this.zzcoM.getEncodedPath());
        return new StringBuilder(String.valueOf(valueOf).length() + 5 + String.valueOf(valueOf2).length()).append("gs://").append(valueOf).append(valueOf2).toString();
    }

    @NonNull
    public Task<StorageMetadata> updateMetadata(@NonNull StorageMetadata storageMetadata) {
        zzbo.zzu(storageMetadata);
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        zzt.zzs(new zzab(this, taskCompletionSource, storageMetadata));
        return taskCompletionSource.getTask();
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public final ace zzKO() throws RemoteException {
        return ace.zzg(getStorage().getApp());
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public final Uri zzKP() {
        return this.zzcoM;
    }
}

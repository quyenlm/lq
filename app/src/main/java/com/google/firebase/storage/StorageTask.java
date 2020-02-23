package com.google.firebase.storage;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.storage.StorageTask.ProvideError;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.Executor;

public abstract class StorageTask<TResult extends ProvideError> extends ControllableTask<TResult> {
    private static final HashMap<Integer, HashSet<Integer>> zzcoR = new HashMap<>();
    private static final HashMap<Integer, HashSet<Integer>> zzcoS = new HashMap<>();
    protected final Object mSyncObject = new Object();
    private volatile int zzOn = 1;
    @VisibleForTesting
    private zzw<OnSuccessListener<? super TResult>, TResult> zzcoT = new zzw<>(this, 128, new zzi(this));
    @VisibleForTesting
    private zzw<OnFailureListener, TResult> zzcoU = new zzw<>(this, 320, new zzj(this));
    @VisibleForTesting
    private zzw<OnCompleteListener<TResult>, TResult> zzcoV = new zzw<>(this, 448, new zzk(this));
    @VisibleForTesting
    private zzw<OnProgressListener<? super TResult>, TResult> zzcoW = new zzw<>(this, -465, new zzl(this));
    @VisibleForTesting
    private zzw<OnPausedListener<? super TResult>, TResult> zzcoX = new zzw<>(this, 16, new zzm(this));
    private TResult zzcoY;

    public interface ProvideError {
        Exception getError();
    }

    public class SnapshotBase implements ProvideError {
        private final Exception zzcpc;

        public SnapshotBase(Exception exc) {
            if (exc != null) {
                this.zzcpc = exc;
            } else if (StorageTask.this.isCanceled()) {
                this.zzcpc = StorageException.fromErrorStatus(Status.zzaBq);
            } else if (StorageTask.this.zzKR() == 64) {
                this.zzcpc = StorageException.fromErrorStatus(Status.zzaBo);
            } else {
                this.zzcpc = null;
            }
        }

        @Nullable
        public Exception getError() {
            return this.zzcpc;
        }

        @NonNull
        public StorageReference getStorage() {
            return getTask().getStorage();
        }

        @NonNull
        public StorageTask<TResult> getTask() {
            return StorageTask.this;
        }
    }

    static {
        zzcoR.put(1, new HashSet(Arrays.asList(new Integer[]{16, 256})));
        zzcoR.put(2, new HashSet(Arrays.asList(new Integer[]{8, 32})));
        zzcoR.put(4, new HashSet(Arrays.asList(new Integer[]{8, 32})));
        zzcoR.put(16, new HashSet(Arrays.asList(new Integer[]{2, 256})));
        zzcoR.put(64, new HashSet(Arrays.asList(new Integer[]{2, 256})));
        zzcoS.put(1, new HashSet(Arrays.asList(new Integer[]{2, 64})));
        zzcoS.put(2, new HashSet(Arrays.asList(new Integer[]{4, 64, 128})));
        zzcoS.put(4, new HashSet(Arrays.asList(new Integer[]{4, 64, 128})));
        zzcoS.put(8, new HashSet(Arrays.asList(new Integer[]{16, 64, 128})));
        zzcoS.put(32, new HashSet(Arrays.asList(new Integer[]{256, 64, 128})));
    }

    protected StorageTask() {
    }

    private final TResult zzKT() {
        if (this.zzcoY != null) {
            return this.zzcoY;
        }
        if (!isComplete()) {
            return null;
        }
        if (this.zzcoY == null) {
            this.zzcoY = zzKS();
        }
        return this.zzcoY;
    }

    /* access modifiers changed from: private */
    public final void zzKU() {
        if (!isComplete() && !isPaused() && this.zzOn != 2 && !zzj(256, false)) {
            zzj(64, false);
        }
    }

    @NonNull
    private final <TContinuationResult> Task<TContinuationResult> zza(@Nullable Executor executor, @NonNull Continuation<TResult, TContinuationResult> continuation) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.zzcoV.zza((Activity) null, executor, new zzn(this, continuation, taskCompletionSource));
        return taskCompletionSource.getTask();
    }

    @VisibleForTesting
    private final boolean zza(int[] iArr, boolean z) {
        HashMap<Integer, HashSet<Integer>> hashMap = z ? zzcoR : zzcoS;
        synchronized (this.mSyncObject) {
            int length = iArr.length;
            int i = 0;
            while (i < length) {
                int i2 = iArr[i];
                HashSet hashSet = hashMap.get(Integer.valueOf(this.zzOn));
                if (hashSet == null || !hashSet.contains(Integer.valueOf(i2))) {
                    i++;
                } else {
                    this.zzOn = i2;
                    switch (this.zzOn) {
                        case 2:
                            zzs.zzKV().zzb(this);
                            onQueued();
                            break;
                        case 4:
                            onProgress();
                            break;
                        case 16:
                            onPaused();
                            break;
                        case 64:
                            onFailure();
                            break;
                        case 128:
                            onSuccess();
                            break;
                        case 256:
                            onCanceled();
                            break;
                    }
                    this.zzcoT.zzKZ();
                    this.zzcoU.zzKZ();
                    this.zzcoV.zzKZ();
                    this.zzcoX.zzKZ();
                    this.zzcoW.zzKZ();
                    if (Log.isLoggable("StorageTask", 3)) {
                        String valueOf = String.valueOf(zzcf(i2));
                        String valueOf2 = String.valueOf(zzcf(this.zzOn));
                        Log.d("StorageTask", new StringBuilder(String.valueOf(valueOf).length() + 53 + String.valueOf(valueOf2).length()).append("changed internal state to: ").append(valueOf).append(" isUser: ").append(z).append(" from state:").append(valueOf2).toString());
                    }
                    return true;
                }
            }
            String valueOf3 = String.valueOf(zzd(iArr));
            String valueOf4 = String.valueOf(zzcf(this.zzOn));
            Log.w("StorageTask", new StringBuilder(String.valueOf(valueOf3).length() + 62 + String.valueOf(valueOf4).length()).append("unable to change internal state to: ").append(valueOf3).append(" isUser: ").append(z).append(" from state:").append(valueOf4).toString());
            return false;
        }
    }

    @NonNull
    private final <TContinuationResult> Task<TContinuationResult> zzb(@Nullable Executor executor, @NonNull Continuation<TResult, Task<TContinuationResult>> continuation) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.zzcoV.zza((Activity) null, executor, new zzo(this, continuation, taskCompletionSource));
        return taskCompletionSource.getTask();
    }

    private static String zzcf(int i) {
        switch (i) {
            case 1:
                return "INTERNAL_STATE_NOT_STARTED";
            case 2:
                return "INTERNAL_STATE_QUEUED";
            case 4:
                return "INTERNAL_STATE_IN_PROGRESS";
            case 8:
                return "INTERNAL_STATE_PAUSING";
            case 16:
                return "INTERNAL_STATE_PAUSED";
            case 32:
                return "INTERNAL_STATE_CANCELING";
            case 64:
                return "INTERNAL_STATE_FAILURE";
            case 128:
                return "INTERNAL_STATE_SUCCESS";
            case 256:
                return "INTERNAL_STATE_CANCELED";
            default:
                return "Unknown Internal State!";
        }
    }

    private static String zzd(int[] iArr) {
        if (iArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int zzcf : iArr) {
            sb.append(zzcf(zzcf)).append(", ");
        }
        return sb.substring(0, sb.length() - 2);
    }

    @NonNull
    public StorageTask<TResult> addOnCompleteListener(@NonNull Activity activity, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        zzbo.zzu(onCompleteListener);
        zzbo.zzu(activity);
        this.zzcoV.zza(activity, (Executor) null, onCompleteListener);
        return this;
    }

    @NonNull
    public StorageTask<TResult> addOnCompleteListener(@NonNull OnCompleteListener<TResult> onCompleteListener) {
        zzbo.zzu(onCompleteListener);
        this.zzcoV.zza((Activity) null, (Executor) null, onCompleteListener);
        return this;
    }

    @NonNull
    public StorageTask<TResult> addOnCompleteListener(@NonNull Executor executor, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        zzbo.zzu(onCompleteListener);
        zzbo.zzu(executor);
        this.zzcoV.zza((Activity) null, executor, onCompleteListener);
        return this;
    }

    @NonNull
    public StorageTask<TResult> addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
        zzbo.zzu(onFailureListener);
        zzbo.zzu(activity);
        this.zzcoU.zza(activity, (Executor) null, onFailureListener);
        return this;
    }

    @NonNull
    public StorageTask<TResult> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
        zzbo.zzu(onFailureListener);
        this.zzcoU.zza((Activity) null, (Executor) null, onFailureListener);
        return this;
    }

    @NonNull
    public StorageTask<TResult> addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        zzbo.zzu(onFailureListener);
        zzbo.zzu(executor);
        this.zzcoU.zza((Activity) null, executor, onFailureListener);
        return this;
    }

    public StorageTask<TResult> addOnPausedListener(@NonNull Activity activity, @NonNull OnPausedListener<? super TResult> onPausedListener) {
        zzbo.zzu(onPausedListener);
        zzbo.zzu(activity);
        this.zzcoX.zza(activity, (Executor) null, onPausedListener);
        return this;
    }

    public StorageTask<TResult> addOnPausedListener(@NonNull OnPausedListener<? super TResult> onPausedListener) {
        zzbo.zzu(onPausedListener);
        this.zzcoX.zza((Activity) null, (Executor) null, onPausedListener);
        return this;
    }

    public StorageTask<TResult> addOnPausedListener(@NonNull Executor executor, @NonNull OnPausedListener<? super TResult> onPausedListener) {
        zzbo.zzu(onPausedListener);
        zzbo.zzu(executor);
        this.zzcoX.zza((Activity) null, executor, onPausedListener);
        return this;
    }

    public StorageTask<TResult> addOnProgressListener(@NonNull Activity activity, @NonNull OnProgressListener<? super TResult> onProgressListener) {
        zzbo.zzu(onProgressListener);
        zzbo.zzu(activity);
        this.zzcoW.zza(activity, (Executor) null, onProgressListener);
        return this;
    }

    public StorageTask<TResult> addOnProgressListener(@NonNull OnProgressListener<? super TResult> onProgressListener) {
        zzbo.zzu(onProgressListener);
        this.zzcoW.zza((Activity) null, (Executor) null, onProgressListener);
        return this;
    }

    public StorageTask<TResult> addOnProgressListener(@NonNull Executor executor, @NonNull OnProgressListener<? super TResult> onProgressListener) {
        zzbo.zzu(onProgressListener);
        zzbo.zzu(executor);
        this.zzcoW.zza((Activity) null, executor, onProgressListener);
        return this;
    }

    @NonNull
    public StorageTask<TResult> addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        zzbo.zzu(activity);
        zzbo.zzu(onSuccessListener);
        this.zzcoT.zza(activity, (Executor) null, onSuccessListener);
        return this;
    }

    @NonNull
    public StorageTask<TResult> addOnSuccessListener(@NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        zzbo.zzu(onSuccessListener);
        this.zzcoT.zza((Activity) null, (Executor) null, onSuccessListener);
        return this;
    }

    @NonNull
    public StorageTask<TResult> addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        zzbo.zzu(executor);
        zzbo.zzu(onSuccessListener);
        this.zzcoT.zza((Activity) null, executor, onSuccessListener);
        return this;
    }

    public boolean cancel() {
        return zza(new int[]{256, 32}, true);
    }

    @NonNull
    public <TContinuationResult> Task<TContinuationResult> continueWith(@NonNull Continuation<TResult, TContinuationResult> continuation) {
        return zza((Executor) null, continuation);
    }

    @NonNull
    public <TContinuationResult> Task<TContinuationResult> continueWith(@NonNull Executor executor, @NonNull Continuation<TResult, TContinuationResult> continuation) {
        return zza(executor, continuation);
    }

    @NonNull
    public <TContinuationResult> Task<TContinuationResult> continueWithTask(@NonNull Continuation<TResult, Task<TContinuationResult>> continuation) {
        return zzb((Executor) null, continuation);
    }

    @NonNull
    public <TContinuationResult> Task<TContinuationResult> continueWithTask(@NonNull Executor executor, @NonNull Continuation<TResult, Task<TContinuationResult>> continuation) {
        return zzb(executor, continuation);
    }

    @Nullable
    public Exception getException() {
        if (zzKT() == null) {
            return null;
        }
        return zzKT().getError();
    }

    public TResult getResult() {
        if (zzKT() == null) {
            throw new IllegalStateException();
        }
        Exception error = zzKT().getError();
        if (error == null) {
            return zzKT();
        }
        throw new RuntimeExecutionException(error);
    }

    public <X extends Throwable> TResult getResult(@NonNull Class<X> cls) throws Throwable {
        if (zzKT() == null) {
            throw new IllegalStateException();
        } else if (cls.isInstance(zzKT().getError())) {
            throw ((Throwable) cls.cast(zzKT().getError()));
        } else {
            Exception error = zzKT().getError();
            if (error == null) {
                return zzKT();
            }
            throw new RuntimeExecutionException(error);
        }
    }

    public TResult getSnapshot() {
        return zzKS();
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public abstract StorageReference getStorage();

    public boolean isCanceled() {
        return this.zzOn == 256;
    }

    public boolean isComplete() {
        return ((this.zzOn & 128) == 0 && (this.zzOn & 320) == 0) ? false : true;
    }

    public boolean isInProgress() {
        return (this.zzOn & -465) != 0;
    }

    public boolean isPaused() {
        return (this.zzOn & 16) != 0;
    }

    public boolean isSuccessful() {
        return (this.zzOn & 128) != 0;
    }

    /* access modifiers changed from: protected */
    public void onCanceled() {
    }

    /* access modifiers changed from: protected */
    public void onFailure() {
    }

    /* access modifiers changed from: protected */
    public void onPaused() {
    }

    /* access modifiers changed from: protected */
    public void onProgress() {
    }

    /* access modifiers changed from: protected */
    public void onQueued() {
    }

    /* access modifiers changed from: protected */
    public void onSuccess() {
    }

    public boolean pause() {
        return zza(new int[]{16, 8}, true);
    }

    public StorageTask<TResult> removeOnCompleteListener(@NonNull OnCompleteListener<TResult> onCompleteListener) {
        zzbo.zzu(onCompleteListener);
        this.zzcoV.zzat(onCompleteListener);
        return this;
    }

    public StorageTask<TResult> removeOnFailureListener(@NonNull OnFailureListener onFailureListener) {
        zzbo.zzu(onFailureListener);
        this.zzcoU.zzat(onFailureListener);
        return this;
    }

    public StorageTask<TResult> removeOnPausedListener(@NonNull OnPausedListener<? super TResult> onPausedListener) {
        zzbo.zzu(onPausedListener);
        this.zzcoX.zzat(onPausedListener);
        return this;
    }

    public StorageTask<TResult> removeOnProgressListener(@NonNull OnProgressListener<? super TResult> onProgressListener) {
        zzbo.zzu(onProgressListener);
        this.zzcoW.zzat(onProgressListener);
        return this;
    }

    public StorageTask<TResult> removeOnSuccessListener(@NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        zzbo.zzu(onSuccessListener);
        this.zzcoT.zzat(onSuccessListener);
        return this;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void resetState() {
    }

    public boolean resume() {
        if (!zzj(2, true)) {
            return false;
        }
        resetState();
        schedule();
        return true;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public abstract void run();

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public abstract void schedule();

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public final Runnable zzEf() {
        return new zzr(this);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    @NonNull
    public abstract TResult zzKM();

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public final boolean zzKQ() {
        if (!zzj(2, false)) {
            return false;
        }
        schedule();
        return true;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public final int zzKR() {
        return this.zzOn;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    @NonNull
    public final TResult zzKS() {
        TResult zzKM;
        synchronized (this.mSyncObject) {
            zzKM = zzKM();
        }
        return zzKM;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public final boolean zzj(int i, boolean z) {
        return zza(new int[]{i}, z);
    }
}

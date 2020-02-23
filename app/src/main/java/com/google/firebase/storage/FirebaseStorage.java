package com.google.firebase.storage;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.appsflyer.share.Constants;
import com.banalytics.BATrackerConst;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.abz;
import com.google.firebase.FirebaseApp;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class FirebaseStorage {
    private static final Map<String, Map<String, FirebaseStorage>> zzcom = new HashMap();
    @NonNull
    private final FirebaseApp zzckw;
    @Nullable
    private final String zzcon;
    private long zzcoo = BATrackerConst.TRACKER_WAKE_UP_INTERVAL;
    private long zzcop = BATrackerConst.TRACKER_WAKE_UP_INTERVAL;
    private long zzcoq = 120000;

    private FirebaseStorage(@Nullable String str, @NonNull FirebaseApp firebaseApp) {
        this.zzcon = str;
        this.zzckw = firebaseApp;
    }

    @NonNull
    public static FirebaseStorage getInstance() {
        FirebaseApp instance = FirebaseApp.getInstance();
        zzbo.zzb(instance != null, (Object) "You must call FirebaseApp.initialize() first.");
        return getInstance(instance);
    }

    @NonNull
    public static FirebaseStorage getInstance(@NonNull FirebaseApp firebaseApp) {
        zzbo.zzb(firebaseApp != null, (Object) "Null is not a valid value for the FirebaseApp.");
        String storageBucket = firebaseApp.getOptions().getStorageBucket();
        if (storageBucket == null) {
            return zza(firebaseApp, (Uri) null);
        }
        try {
            String valueOf = String.valueOf(firebaseApp.getOptions().getStorageBucket());
            return zza(firebaseApp, abz.zzg(firebaseApp, valueOf.length() != 0 ? "gs://".concat(valueOf) : new String("gs://")));
        } catch (UnsupportedEncodingException e) {
            UnsupportedEncodingException unsupportedEncodingException = e;
            String valueOf2 = String.valueOf(storageBucket);
            Log.e("FirebaseStorage", valueOf2.length() != 0 ? "Unable to parse bucket:".concat(valueOf2) : new String("Unable to parse bucket:"), unsupportedEncodingException);
            throw new IllegalArgumentException("The storage Uri could not be parsed.");
        }
    }

    @NonNull
    public static FirebaseStorage getInstance(@NonNull FirebaseApp firebaseApp, @NonNull String str) {
        zzbo.zzb(firebaseApp != null, (Object) "Null is not a valid value for the FirebaseApp.");
        try {
            return zza(firebaseApp, abz.zzg(firebaseApp, str));
        } catch (UnsupportedEncodingException e) {
            UnsupportedEncodingException unsupportedEncodingException = e;
            String valueOf = String.valueOf(str);
            Log.e("FirebaseStorage", valueOf.length() != 0 ? "Unable to parse url:".concat(valueOf) : new String("Unable to parse url:"), unsupportedEncodingException);
            throw new IllegalArgumentException("The storage Uri could not be parsed.");
        }
    }

    @NonNull
    public static FirebaseStorage getInstance(@NonNull String str) {
        FirebaseApp instance = FirebaseApp.getInstance();
        zzbo.zzb(instance != null, (Object) "You must call FirebaseApp.initialize() first.");
        return getInstance(instance, str);
    }

    private static FirebaseStorage zza(@NonNull FirebaseApp firebaseApp, @Nullable Uri uri) {
        HashMap hashMap;
        FirebaseStorage firebaseStorage;
        String host = uri != null ? uri.getHost() : null;
        if (uri == null || TextUtils.isEmpty(uri.getPath())) {
            synchronized (zzcom) {
                Map map = zzcom.get(firebaseApp.getName());
                if (map == null) {
                    HashMap hashMap2 = new HashMap();
                    zzcom.put(firebaseApp.getName(), hashMap2);
                    hashMap = hashMap2;
                } else {
                    hashMap = map;
                }
                firebaseStorage = (FirebaseStorage) hashMap.get(host);
                if (firebaseStorage == null) {
                    firebaseStorage = new FirebaseStorage(host, firebaseApp);
                    hashMap.put(host, firebaseStorage);
                }
            }
            return firebaseStorage;
        }
        throw new IllegalArgumentException("The storage Uri cannot contain a path element.");
    }

    @NonNull
    private final StorageReference zzu(@NonNull Uri uri) {
        zzbo.zzb(uri, (Object) "uri must not be null");
        String str = this.zzcon;
        zzbo.zzb(TextUtils.isEmpty(str) || uri.getAuthority().equalsIgnoreCase(str), (Object) "The supplied bucketname does not match the storage bucket of the current instance.");
        return new StorageReference(uri, this);
    }

    @NonNull
    public FirebaseApp getApp() {
        return this.zzckw;
    }

    public long getMaxDownloadRetryTimeMillis() {
        return this.zzcop;
    }

    public long getMaxOperationRetryTimeMillis() {
        return this.zzcoq;
    }

    public long getMaxUploadRetryTimeMillis() {
        return this.zzcoo;
    }

    @NonNull
    public StorageReference getReference() {
        if (!TextUtils.isEmpty(this.zzcon)) {
            return zzu(new Uri.Builder().scheme("gs").authority(this.zzcon).path(Constants.URL_PATH_DELIMITER).build());
        }
        throw new IllegalStateException("FirebaseApp was not initialized with a bucket name.");
    }

    @NonNull
    public StorageReference getReference(@NonNull String str) {
        zzbo.zzb(!TextUtils.isEmpty(str), (Object) "location must not be null or empty");
        String lowerCase = str.toLowerCase();
        if (!lowerCase.startsWith("gs://") && !lowerCase.startsWith("https://") && !lowerCase.startsWith("http://")) {
            return getReference().child(str);
        }
        throw new IllegalArgumentException("location should not be a full URL.");
    }

    @NonNull
    public StorageReference getReferenceFromUrl(@NonNull String str) {
        zzbo.zzb(!TextUtils.isEmpty(str), (Object) "location must not be null or empty");
        String lowerCase = str.toLowerCase();
        if (lowerCase.startsWith("gs://") || lowerCase.startsWith("https://") || lowerCase.startsWith("http://")) {
            try {
                Uri zzg = abz.zzg(this.zzckw, str);
                if (zzg != null) {
                    return zzu(zzg);
                }
                throw new IllegalArgumentException("The storage Uri could not be parsed.");
            } catch (UnsupportedEncodingException e) {
                UnsupportedEncodingException unsupportedEncodingException = e;
                String valueOf = String.valueOf(str);
                Log.e("FirebaseStorage", valueOf.length() != 0 ? "Unable to parse location:".concat(valueOf) : new String("Unable to parse location:"), unsupportedEncodingException);
                throw new IllegalArgumentException("The storage Uri could not be parsed.");
            }
        } else {
            throw new IllegalArgumentException("The storage Uri could not be parsed.");
        }
    }

    public void setMaxDownloadRetryTimeMillis(long j) {
        this.zzcop = j;
    }

    public void setMaxOperationRetryTimeMillis(long j) {
        this.zzcoq = j;
    }

    public void setMaxUploadRetryTimeMillis(long j) {
        this.zzcoo = j;
    }
}

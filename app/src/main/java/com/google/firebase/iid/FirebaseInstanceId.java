package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.util.Base64;
import android.util.Log;
import com.google.firebase.FirebaseApp;
import java.io.IOException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class FirebaseInstanceId {
    private static Map<String, FirebaseInstanceId> zzbgQ = new ArrayMap();
    private static zzk zzckv;
    private final FirebaseApp zzckw;
    private final zzj zzckx;
    private final String zzcky;

    private FirebaseInstanceId(FirebaseApp firebaseApp, zzj zzj) {
        this.zzckw = firebaseApp;
        this.zzckx = zzj;
        String gcmSenderId = this.zzckw.getOptions().getGcmSenderId();
        if (gcmSenderId == null) {
            gcmSenderId = this.zzckw.getOptions().getApplicationId();
            if (gcmSenderId.startsWith("1:")) {
                String[] split = gcmSenderId.split(":");
                if (split.length < 2) {
                    gcmSenderId = null;
                } else {
                    gcmSenderId = split[1];
                    if (gcmSenderId.isEmpty()) {
                        gcmSenderId = null;
                    }
                }
            }
        }
        this.zzcky = gcmSenderId;
        if (this.zzcky == null) {
            throw new IllegalStateException("IID failing to initialize, FirebaseApp is missing project ID");
        }
        FirebaseInstanceIdService.zza(this.zzckw.getApplicationContext(), this);
    }

    public static FirebaseInstanceId getInstance() {
        return getInstance(FirebaseApp.getInstance());
    }

    @Keep
    public static synchronized FirebaseInstanceId getInstance(@NonNull FirebaseApp firebaseApp) {
        FirebaseInstanceId firebaseInstanceId;
        synchronized (FirebaseInstanceId.class) {
            firebaseInstanceId = zzbgQ.get(firebaseApp.getOptions().getApplicationId());
            if (firebaseInstanceId == null) {
                zzj zzb = zzj.zzb(firebaseApp.getApplicationContext(), (Bundle) null);
                if (zzckv == null) {
                    zzckv = new zzk(zzj.zzJT());
                }
                firebaseInstanceId = new FirebaseInstanceId(firebaseApp, zzb);
                zzbgQ.put(firebaseApp.getOptions().getApplicationId(), firebaseInstanceId);
            }
        }
        return firebaseInstanceId;
    }

    private final void zzF(Bundle bundle) {
        bundle.putString("gmp_app_id", this.zzckw.getOptions().getApplicationId());
    }

    static zzk zzJS() {
        return zzckv;
    }

    static int zzO(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            String valueOf = String.valueOf(e);
            Log.w("FirebaseInstanceId", new StringBuilder(String.valueOf(valueOf).length() + 23).append("Failed to find package ").append(valueOf).toString());
            return 0;
        }
    }

    static String zza(KeyPair keyPair) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA1").digest(keyPair.getPublic().getEncoded());
            digest[0] = (byte) ((digest[0] & 15) + 112);
            return Base64.encodeToString(digest, 0, 8, 11);
        } catch (NoSuchAlgorithmException e) {
            Log.w("FirebaseInstanceId", "Unexpected error, device missing required alghorithms");
            return null;
        }
    }

    static void zza(Context context, zzr zzr) {
        zzr.zzvP();
        Intent intent = new Intent();
        intent.putExtra("CMD", "RST");
        zzq.zzJX().zze(context, intent);
    }

    static int zzbF(Context context) {
        return zzO(context, context.getPackageName());
    }

    static void zzbG(Context context) {
        Intent intent = new Intent();
        intent.putExtra("CMD", "SYNC");
        zzq.zzJX().zze(context, intent);
    }

    static String zzbb(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            String valueOf = String.valueOf(e);
            Log.w("FirebaseInstanceId", new StringBuilder(String.valueOf(valueOf).length() + 38).append("Never happens: can't find own package ").append(valueOf).toString());
            return null;
        }
    }

    static String zzj(byte[] bArr) {
        return Base64.encodeToString(bArr, 11);
    }

    public void deleteInstanceId() throws IOException {
        this.zzckx.zzb("*", "*", (Bundle) null);
        this.zzckx.zzvL();
    }

    @WorkerThread
    public void deleteToken(String str, String str2) throws IOException {
        Bundle bundle = new Bundle();
        zzF(bundle);
        this.zzckx.zzb(str, str2, bundle);
    }

    public long getCreationTime() {
        return this.zzckx.getCreationTime();
    }

    public String getId() {
        return zza(this.zzckx.zzvK());
    }

    @Nullable
    public String getToken() {
        zzs zzJQ = zzJQ();
        if (zzJQ == null || zzJQ.zzhp(zzj.zzbgW)) {
            FirebaseInstanceIdService.zzbI(this.zzckw.getApplicationContext());
        }
        if (zzJQ != null) {
            return zzJQ.zzbPJ;
        }
        return null;
    }

    @WorkerThread
    public String getToken(String str, String str2) throws IOException {
        Bundle bundle = new Bundle();
        zzF(bundle);
        return this.zzckx.getToken(str, str2, bundle);
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public final zzs zzJQ() {
        return zzj.zzJT().zzp("", this.zzcky, "*");
    }

    /* access modifiers changed from: package-private */
    public final String zzJR() throws IOException {
        return getToken(this.zzcky, "*");
    }

    public final void zzhf(String str) {
        zzckv.zzhf(str);
        FirebaseInstanceIdService.zzbI(this.zzckw.getApplicationContext());
    }

    /* access modifiers changed from: package-private */
    public final void zzhg(String str) throws IOException {
        zzs zzJQ = zzJQ();
        if (zzJQ == null || zzJQ.zzhp(zzj.zzbgW)) {
            throw new IOException("token not available");
        }
        Bundle bundle = new Bundle();
        String valueOf = String.valueOf("/topics/");
        String valueOf2 = String.valueOf(str);
        bundle.putString("gcm.topic", valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        String str2 = zzJQ.zzbPJ;
        String valueOf3 = String.valueOf("/topics/");
        String valueOf4 = String.valueOf(str);
        String concat = valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3);
        zzF(bundle);
        this.zzckx.zzc(str2, concat, bundle);
    }

    /* access modifiers changed from: package-private */
    public final void zzhh(String str) throws IOException {
        zzs zzJQ = zzJQ();
        if (zzJQ == null || zzJQ.zzhp(zzj.zzbgW)) {
            throw new IOException("token not available");
        }
        Bundle bundle = new Bundle();
        String valueOf = String.valueOf("/topics/");
        String valueOf2 = String.valueOf(str);
        bundle.putString("gcm.topic", valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        zzj zzj = this.zzckx;
        String str2 = zzJQ.zzbPJ;
        String valueOf3 = String.valueOf("/topics/");
        String valueOf4 = String.valueOf(str);
        zzj.zzb(str2, valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3), bundle);
    }
}

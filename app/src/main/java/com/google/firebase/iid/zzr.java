package com.google.firebase.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.util.zzu;
import java.io.File;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

final class zzr {
    SharedPreferences zzbho;
    private Context zzqD;

    public zzr(Context context) {
        this(context, "com.google.android.gms.appid");
    }

    private zzr(Context context, String str) {
        this.zzqD = context;
        this.zzbho = context.getSharedPreferences(str, 0);
        String valueOf = String.valueOf(str);
        String valueOf2 = String.valueOf("-no-backup");
        File file = new File(zzu.getNoBackupFilesDir(this.zzqD), valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        if (!file.exists()) {
            try {
                if (file.createNewFile() && !isEmpty()) {
                    Log.i("InstanceID/Store", "App restored, clearing state");
                    FirebaseInstanceId.zza(this.zzqD, this);
                }
            } catch (IOException e) {
                if (Log.isLoggable("InstanceID/Store", 3)) {
                    String valueOf3 = String.valueOf(e.getMessage());
                    Log.d("InstanceID/Store", valueOf3.length() != 0 ? "Error creating file in no backup dir: ".concat(valueOf3) : new String("Error creating file in no backup dir: "));
                }
            }
        }
    }

    private static String zzai(String str, String str2) {
        String valueOf = String.valueOf("|S|");
        return new StringBuilder(String.valueOf(str).length() + String.valueOf(valueOf).length() + String.valueOf(str2).length()).append(str).append(valueOf).append(str2).toString();
    }

    private final void zzdq(String str) {
        SharedPreferences.Editor edit = this.zzbho.edit();
        for (String next : this.zzbho.getAll().keySet()) {
            if (next.startsWith(str)) {
                edit.remove(next);
            }
        }
        edit.commit();
    }

    private static String zzo(String str, String str2, String str3) {
        String valueOf = String.valueOf("|T|");
        return new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(valueOf).length() + String.valueOf(str2).length() + String.valueOf(str3).length()).append(str).append(valueOf).append(str2).append("|").append(str3).toString();
    }

    public final synchronized boolean isEmpty() {
        return this.zzbho.getAll().isEmpty();
    }

    public final synchronized void zza(String str, String str2, String str3, String str4, String str5) {
        String zzc = zzs.zzc(str4, str5, System.currentTimeMillis());
        if (zzc != null) {
            SharedPreferences.Editor edit = this.zzbho.edit();
            edit.putString(zzo(str, str2, str3), zzc);
            edit.commit();
        }
    }

    public final synchronized void zzdr(String str) {
        zzdq(String.valueOf(str).concat("|T|"));
    }

    public final synchronized void zzg(String str, String str2, String str3) {
        String zzo = zzo(str, str2, str3);
        SharedPreferences.Editor edit = this.zzbho.edit();
        edit.remove(zzo);
        edit.commit();
    }

    public final synchronized long zzhk(String str) {
        long j;
        String string = this.zzbho.getString(zzai(str, "cre"), (String) null);
        if (string != null) {
            try {
                j = Long.parseLong(string);
            } catch (NumberFormatException e) {
            }
        }
        j = 0;
        return j;
    }

    /* access modifiers changed from: package-private */
    public final synchronized KeyPair zzhl(String str) {
        KeyPair zzvJ;
        zzvJ = zza.zzvJ();
        long currentTimeMillis = System.currentTimeMillis();
        SharedPreferences.Editor edit = this.zzbho.edit();
        edit.putString(zzai(str, "|P|"), FirebaseInstanceId.zzj(zzvJ.getPublic().getEncoded()));
        edit.putString(zzai(str, "|K|"), FirebaseInstanceId.zzj(zzvJ.getPrivate().getEncoded()));
        edit.putString(zzai(str, "cre"), Long.toString(currentTimeMillis));
        edit.commit();
        return zzvJ;
    }

    /* access modifiers changed from: package-private */
    public final synchronized void zzhm(String str) {
        zzdq(String.valueOf(str).concat("|"));
    }

    public final synchronized KeyPair zzhn(String str) {
        KeyPair keyPair;
        String string = this.zzbho.getString(zzai(str, "|P|"), (String) null);
        String string2 = this.zzbho.getString(zzai(str, "|K|"), (String) null);
        if (string == null || string2 == null) {
            keyPair = null;
        } else {
            try {
                byte[] decode = Base64.decode(string, 8);
                byte[] decode2 = Base64.decode(string2, 8);
                KeyFactory instance = KeyFactory.getInstance("RSA");
                keyPair = new KeyPair(instance.generatePublic(new X509EncodedKeySpec(decode)), instance.generatePrivate(new PKCS8EncodedKeySpec(decode2)));
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                String valueOf = String.valueOf(e);
                Log.w("InstanceID/Store", new StringBuilder(String.valueOf(valueOf).length() + 19).append("Invalid key stored ").append(valueOf).toString());
                FirebaseInstanceId.zza(this.zzqD, this);
                keyPair = null;
            }
        }
        return keyPair;
    }

    public final synchronized zzs zzp(String str, String str2, String str3) {
        return zzs.zzho(this.zzbho.getString(zzo(str, str2, str3), (String) null));
    }

    public final synchronized void zzvP() {
        this.zzbho.edit().clear().commit();
    }
}

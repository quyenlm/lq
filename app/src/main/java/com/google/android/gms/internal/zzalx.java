package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Locale;

public final class zzalx extends zzamh {
    private static boolean zzafv;
    private Object zzafA = new Object();
    private AdvertisingIdClient.Info zzafw;
    private final zzaoo zzafx;
    private String zzafy;
    private boolean zzafz = false;

    zzalx(zzamj zzamj) {
        super(zzamj);
        this.zzafx = new zzaoo(zzamj.zzkq());
    }

    private final boolean zza(AdvertisingIdClient.Info info, AdvertisingIdClient.Info info2) {
        String str;
        String str2 = null;
        String id = info2 == null ? null : info2.getId();
        if (TextUtils.isEmpty(id)) {
            return true;
        }
        String zzli = zzkz().zzli();
        synchronized (this.zzafA) {
            if (!this.zzafz) {
                this.zzafy = zzkj();
                this.zzafz = true;
            } else if (TextUtils.isEmpty(this.zzafy)) {
                if (info != null) {
                    str2 = info.getId();
                }
                if (str2 == null) {
                    String valueOf = String.valueOf(id);
                    String valueOf2 = String.valueOf(zzli);
                    boolean zzbn = zzbn(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
                    return zzbn;
                }
                String valueOf3 = String.valueOf(str2);
                String valueOf4 = String.valueOf(zzli);
                this.zzafy = zzbm(valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3));
            }
            String valueOf5 = String.valueOf(id);
            String valueOf6 = String.valueOf(zzli);
            String zzbm = zzbm(valueOf6.length() != 0 ? valueOf5.concat(valueOf6) : new String(valueOf5));
            if (TextUtils.isEmpty(zzbm)) {
                return false;
            }
            if (zzbm.equals(this.zzafy)) {
                return true;
            }
            if (!TextUtils.isEmpty(this.zzafy)) {
                zzbo("Resetting the client id because Advertising Id changed.");
                str = zzkz().zzlj();
                zza("New client Id", str);
            } else {
                str = zzli;
            }
            String valueOf7 = String.valueOf(id);
            String valueOf8 = String.valueOf(str);
            boolean zzbn2 = zzbn(valueOf8.length() != 0 ? valueOf7.concat(valueOf8) : new String(valueOf7));
            return zzbn2;
        }
    }

    private static String zzbm(String str) {
        MessageDigest zzbE = zzaos.zzbE("MD5");
        if (zzbE == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new Object[]{new BigInteger(1, zzbE.digest(str.getBytes()))});
    }

    private final boolean zzbn(String str) {
        try {
            String zzbm = zzbm(str);
            zzbo("Storing hashed adid.");
            FileOutputStream openFileOutput = getContext().openFileOutput("gaClientIdData", 0);
            openFileOutput.write(zzbm.getBytes());
            openFileOutput.close();
            this.zzafy = zzbm;
            return true;
        } catch (IOException e) {
            zze("Error creating hash file", e);
            return false;
        }
    }

    private final synchronized AdvertisingIdClient.Info zzkh() {
        if (this.zzafx.zzu(1000)) {
            this.zzafx.start();
            AdvertisingIdClient.Info zzki = zzki();
            if (zza(this.zzafw, zzki)) {
                this.zzafw = zzki;
            } else {
                zzbs("Failed to reset client id on adid change. Not using adid");
                this.zzafw = new AdvertisingIdClient.Info("", false);
            }
        }
        return this.zzafw;
    }

    private final AdvertisingIdClient.Info zzki() {
        try {
            return AdvertisingIdClient.getAdvertisingIdInfo(getContext());
        } catch (IllegalStateException e) {
            zzbr("IllegalStateException getting Ad Id Info. If you would like to see Audience reports, please ensure that you have added '<meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />' to your application manifest file. See http://goo.gl/naFqQk for details.");
            return null;
        } catch (Throwable th) {
            if (zzafv) {
                return null;
            }
            zzafv = true;
            zzd("Error getting advertiser id", th);
            return null;
        }
    }

    private final String zzkj() {
        IOException e;
        String str = null;
        try {
            FileInputStream openFileInput = getContext().openFileInput("gaClientIdData");
            byte[] bArr = new byte[128];
            int read = openFileInput.read(bArr, 0, 128);
            if (openFileInput.available() > 0) {
                zzbr("Hash file seems corrupted, deleting it.");
                openFileInput.close();
                getContext().deleteFile("gaClientIdData");
                return null;
            } else if (read <= 0) {
                zzbo("Hash file is empty.");
                openFileInput.close();
                return null;
            } else {
                String str2 = new String(bArr, 0, read);
                try {
                    openFileInput.close();
                    return str2;
                } catch (FileNotFoundException e2) {
                    return str2;
                } catch (IOException e3) {
                    e = e3;
                    str = str2;
                    zzd("Error reading Hash file, deleting it", e);
                    getContext().deleteFile("gaClientIdData");
                    return str;
                }
            }
        } catch (FileNotFoundException e4) {
            return null;
        } catch (IOException e5) {
            e = e5;
            zzd("Error reading Hash file, deleting it", e);
            getContext().deleteFile("gaClientIdData");
            return str;
        }
    }

    /* access modifiers changed from: protected */
    public final void zzjD() {
    }

    public final boolean zzjZ() {
        zzkD();
        AdvertisingIdClient.Info zzkh = zzkh();
        return zzkh != null && !zzkh.isLimitAdTrackingEnabled();
    }

    public final String zzkg() {
        zzkD();
        AdvertisingIdClient.Info zzkh = zzkh();
        String id = zzkh != null ? zzkh.getId() : null;
        if (TextUtils.isEmpty(id)) {
            return null;
        }
        return id;
    }
}

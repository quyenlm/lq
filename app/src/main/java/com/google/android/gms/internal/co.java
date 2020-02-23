package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.common.util.zzn;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class co {
    private final Context mContext;
    private final ct zzbKL;
    private final ExecutorService zzbrV;

    public co(Context context) {
        this(context, Executors.newSingleThreadExecutor(), new cp(context));
    }

    private co(Context context, ExecutorService executorService, ct ctVar) {
        this.mContext = context;
        this.zzbrV = executorService;
        this.zzbKL = ctVar;
    }

    private final File zzfS(String str) {
        return new File(this.mContext.getDir("google_tagmanager", 0), zzfT(str));
    }

    private static String zzfT(String str) {
        String valueOf = String.valueOf("resource_");
        String valueOf2 = String.valueOf(str);
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    private static byte[] zzj(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            zzn.zza(inputStream, byteArrayOutputStream, false);
            try {
                inputStream.close();
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                zzcvk.zzaT("Error closing stream for reading resource from disk");
                return null;
            }
        } catch (IOException e2) {
            zzcvk.zzaT("Failed to read the resource from disk");
            try {
                inputStream.close();
            } catch (IOException e3) {
                zzcvk.zzaT("Error closing stream for reading resource from disk");
                return null;
            }
        } catch (Throwable th) {
            try {
                inputStream.close();
                throw th;
            } catch (IOException e4) {
                zzcvk.zzaT("Error closing stream for reading resource from disk");
                return null;
            }
        }
    }

    public final void zza(String str, cc ccVar) {
        this.zzbrV.execute(new cq(this, str, ccVar));
    }

    public final void zza(String str, String str2, cc ccVar) {
        this.zzbrV.execute(new cr(this, str, str2, ccVar));
    }

    /* access modifiers changed from: package-private */
    public final void zzb(String str, cc ccVar) {
        zzcvk.v("Starting to load a saved resource file from Disk.");
        try {
            ccVar.zzv(zzj(new FileInputStream(zzfS(str))));
        } catch (FileNotFoundException e) {
            String valueOf = String.valueOf(zzfT(str));
            zzcvk.e(valueOf.length() != 0 ? "Saved resource not found: ".concat(valueOf) : new String("Saved resource not found: "));
            ccVar.zzk(0, 1);
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzb(String str, String str2, cc ccVar) {
        zzcvk.v("Starting to load a default asset file from Disk.");
        if (str2 == null) {
            zzcvk.v("Default asset file is not specified. Not proceeding with the loading");
            ccVar.zzk(0, 2);
            return;
        }
        try {
            InputStream open = this.zzbKL.open(str2);
            if (open != null) {
                ccVar.zzv(zzj(open));
            } else {
                ccVar.zzk(0, 2);
            }
        } catch (IOException e) {
            zzcvk.e(new StringBuilder(String.valueOf(str).length() + 42 + String.valueOf(str2).length()).append("Default asset file not found. ").append(str).append(". Filename: ").append(str2).toString());
            ccVar.zzk(0, 2);
        }
    }

    public final void zzd(String str, byte[] bArr) {
        this.zzbrV.execute(new cs(this, str, bArr));
    }

    /* access modifiers changed from: package-private */
    public final void zze(String str, byte[] bArr) {
        File zzfS = zzfS(str);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(zzfS);
            try {
                fileOutputStream.write(bArr);
                try {
                    fileOutputStream.close();
                    zzcvk.v(new StringBuilder(String.valueOf(str).length() + 24).append("Resource ").append(str).append(" saved on Disk.").toString());
                } catch (IOException e) {
                    zzcvk.e("Error closing stream for writing resource to disk");
                }
            } catch (IOException e2) {
                zzcvk.e("Error writing resource to disk. Removing resource from disk");
                zzfS.delete();
                try {
                    fileOutputStream.close();
                    zzcvk.v(new StringBuilder(String.valueOf(str).length() + 24).append("Resource ").append(str).append(" saved on Disk.").toString());
                } catch (IOException e3) {
                    zzcvk.e("Error closing stream for writing resource to disk");
                }
            } catch (Throwable th) {
                try {
                    fileOutputStream.close();
                    zzcvk.v(new StringBuilder(String.valueOf(str).length() + 24).append("Resource ").append(str).append(" saved on Disk.").toString());
                } catch (IOException e4) {
                    zzcvk.e("Error closing stream for writing resource to disk");
                }
                throw th;
            }
        } catch (FileNotFoundException e5) {
            zzcvk.e("Error opening resource file for writing");
        }
    }

    public final long zzfR(String str) {
        File zzfS = zzfS(str);
        if (zzfS.exists()) {
            return zzfS.lastModified();
        }
        return 0;
    }
}

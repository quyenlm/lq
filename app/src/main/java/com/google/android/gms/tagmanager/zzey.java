package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.gms.internal.ado;
import com.google.android.gms.internal.adp;
import com.google.android.gms.internal.ee;
import com.google.android.gms.internal.eg;
import com.google.android.gms.internal.ek;
import com.google.android.gms.internal.eo;
import com.google.android.gms.internal.zzbn;
import com.google.android.gms.tagmanager.zzei;
import com.tencent.tp.a.h;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONException;

final class zzey implements zzah {
    private final Context mContext;
    private final String zzbDw;
    private zzdi<ee> zzbFV;
    private final ExecutorService zzbrV = Executors.newSingleThreadExecutor();

    zzey(Context context, String str) {
        this.mContext = context;
        this.zzbDw = str;
    }

    private final File zzBJ() {
        String valueOf = String.valueOf("resource_");
        String valueOf2 = String.valueOf(this.zzbDw);
        return new File(this.mContext.getDir("google_tagmanager", 0), valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
    }

    private static ek zza(ByteArrayOutputStream byteArrayOutputStream) {
        try {
            return zzdb.zzfo(byteArrayOutputStream.toString("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            zzdj.zzaC("Failed to convert binary resource to string for JSON parsing; the file format is not UTF-8 format.");
            return null;
        } catch (JSONException e2) {
            zzdj.zzaT("Failed to extract the container from the resource file. Resource is a UTF-8 encoded string but doesn't contain a JSON container");
            return null;
        }
    }

    private static ek zzu(byte[] bArr) {
        try {
            ek zza = eg.zza((zzbn) adp.zza(new zzbn(), bArr));
            if (zza == null) {
                return zza;
            }
            zzdj.v("The container was successfully loaded from the resource (using binary file)");
            return zza;
        } catch (ado e) {
            zzdj.e("The resource file is corrupted. The container cannot be extracted from the binary file");
            return null;
        } catch (eo e2) {
            zzdj.zzaT("The resource file is invalid. The container from the binary file is invalid");
            return null;
        }
    }

    public final synchronized void release() {
        this.zzbrV.shutdown();
    }

    public final void zzAR() {
        this.zzbrV.execute(new zzez(this));
    }

    /* access modifiers changed from: package-private */
    public final void zzBI() {
        if (this.zzbFV == null) {
            throw new IllegalStateException("Callback must be set before execute");
        }
        zzdj.v("Attempting to load resource from disk");
        if ((zzei.zzBD().zzBE() == zzei.zza.CONTAINER || zzei.zzBD().zzBE() == zzei.zza.CONTAINER_DEBUG) && this.zzbDw.equals(zzei.zzBD().getContainerId())) {
            this.zzbFV.zzbw(zzda.zzbFh);
            return;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(zzBJ());
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                eg.zzb(fileInputStream, byteArrayOutputStream);
                ee eeVar = (ee) adp.zza(new ee(), byteArrayOutputStream.toByteArray());
                if (eeVar.zzlB == null && eeVar.zzbLH == null) {
                    throw new IllegalArgumentException("Resource and SupplementedResource are NULL.");
                }
                this.zzbFV.onSuccess(eeVar);
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    zzdj.zzaT("Error closing stream for reading resource from disk");
                }
                zzdj.v("The Disk resource was successfully read.");
            } catch (IOException e2) {
                this.zzbFV.zzbw(zzda.zzbFi);
                zzdj.zzaT("Failed to read the resource from disk");
                try {
                    fileInputStream.close();
                } catch (IOException e3) {
                    zzdj.zzaT("Error closing stream for reading resource from disk");
                }
            } catch (IllegalArgumentException e4) {
                this.zzbFV.zzbw(zzda.zzbFi);
                zzdj.zzaT("Failed to read the resource from disk. The resource is inconsistent");
                try {
                    fileInputStream.close();
                } catch (IOException e5) {
                    zzdj.zzaT("Error closing stream for reading resource from disk");
                }
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (IOException e6) {
                    zzdj.zzaT("Error closing stream for reading resource from disk");
                }
                throw th;
            }
        } catch (FileNotFoundException e7) {
            zzdj.zzaC("Failed to find the resource in the disk");
            this.zzbFV.zzbw(zzda.zzbFh);
        }
    }

    public final void zza(ee eeVar) {
        this.zzbrV.execute(new zzfa(this, eeVar));
    }

    public final void zza(zzdi<ee> zzdi) {
        this.zzbFV = zzdi;
    }

    /* access modifiers changed from: package-private */
    public final boolean zzb(ee eeVar) {
        File zzBJ = zzBJ();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(zzBJ);
            try {
                fileOutputStream.write(adp.zzc(eeVar));
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    zzdj.zzaT("error closing stream for writing resource to disk");
                }
                return true;
            } catch (IOException e2) {
                zzdj.zzaT("Error writing resource to disk. Removing resource from disk.");
                zzBJ.delete();
                try {
                    fileOutputStream.close();
                    return false;
                } catch (IOException e3) {
                    zzdj.zzaT("error closing stream for writing resource to disk");
                    return false;
                }
            } catch (Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (IOException e4) {
                    zzdj.zzaT("error closing stream for writing resource to disk");
                }
                throw th;
            }
        } catch (FileNotFoundException e5) {
            zzdj.e("Error opening resource file for writing");
            return false;
        }
    }

    public final ek zzbx(int i) {
        try {
            InputStream openRawResource = this.mContext.getResources().openRawResource(i);
            String valueOf = String.valueOf(this.mContext.getResources().getResourceName(i));
            zzdj.v(new StringBuilder(String.valueOf(valueOf).length() + 66).append("Attempting to load a container from the resource ID ").append(i).append(" (").append(valueOf).append(h.b).toString());
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                eg.zzb(openRawResource, byteArrayOutputStream);
                ek zza = zza(byteArrayOutputStream);
                if (zza == null) {
                    return zzu(byteArrayOutputStream.toByteArray());
                }
                zzdj.v("The container was successfully loaded from the resource (using JSON file format)");
                return zza;
            } catch (IOException e) {
                String valueOf2 = String.valueOf(this.mContext.getResources().getResourceName(i));
                zzdj.zzaT(new StringBuilder(String.valueOf(valueOf2).length() + 67).append("Error reading the default container with resource ID ").append(i).append(" (").append(valueOf2).append(h.b).toString());
                return null;
            }
        } catch (Resources.NotFoundException e2) {
            zzdj.zzaT(new StringBuilder(98).append("Failed to load the container. No default container resource found with the resource ID ").append(i).toString());
            return null;
        }
    }
}

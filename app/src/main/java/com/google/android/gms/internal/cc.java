package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzi;

public abstract class cc {
    private int zzBM;
    @Nullable
    protected final zzcuo zzbHO;
    protected final cl zzbKt;
    private ch zzbKu;
    private zze zzvw;

    public cc(int i, cl clVar, ch chVar, @Nullable zzcuo zzcuo) {
        this(i, clVar, chVar, zzcuo, zzi.zzrY());
    }

    private cc(int i, cl clVar, ch chVar, @Nullable zzcuo zzcuo, zze zze) {
        this.zzbKt = (cl) zzbo.zzu(clVar);
        zzbo.zzu(clVar.zzCP());
        this.zzBM = i;
        this.zzbKu = (ch) zzbo.zzu(chVar);
        this.zzvw = (zze) zzbo.zzu(zze);
        this.zzbHO = zzcuo;
    }

    private final cm zzw(byte[] bArr) {
        cm cmVar = null;
        try {
            cmVar = this.zzbKu.zzx(bArr);
            if (cmVar == null) {
                zzcvk.zzaS("Parsed resource from is null");
            }
        } catch (ca e) {
            zzcvk.zzaS("Resource data is corrupted");
        }
        return cmVar;
    }

    /* access modifiers changed from: protected */
    public abstract void zza(cm cmVar);

    public final void zzk(int i, int i2) {
        String str;
        if (this.zzbHO != null && i2 == 0 && i == 3) {
            this.zzbHO.zzAU();
        }
        String valueOf = String.valueOf(this.zzbKt.zzCP().getContainerId());
        switch (i) {
            case 0:
                str = "Resource not available";
                break;
            case 1:
                str = "IOError";
                break;
            case 2:
                str = "Server error";
                break;
            default:
                str = "Unknown reason";
                break;
        }
        String valueOf2 = String.valueOf(str);
        zzcvk.v(new StringBuilder(String.valueOf(valueOf).length() + 61 + String.valueOf(valueOf2).length()).append("Failed to fetch the container resource for the container \"").append(valueOf).append("\": ").append(valueOf2).toString());
        zza(new cm(Status.zzaBo, i2));
    }

    public final void zzv(byte[] bArr) {
        cm cmVar;
        cm zzw = zzw(bArr);
        if (this.zzbHO != null && this.zzBM == 0) {
            this.zzbHO.zzAV();
        }
        if (zzw == null || zzw.getStatus() != Status.zzaBm) {
            cmVar = new cm(Status.zzaBo, this.zzBM);
        } else {
            db zzCV = zzw.zzCQ().zzCV();
            cmVar = new cm(Status.zzaBm, this.zzBM, new cn(this.zzbKt.zzCP(), bArr, zzCV, this.zzvw.currentTimeMillis()), zzw.zzCR());
        }
        zza(cmVar);
    }
}

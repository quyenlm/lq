package com.google.android.gms.internal;

import com.tencent.smtt.sdk.TbsListener;
import java.io.IOException;

public final class zzav extends adj<zzav> {
    public Integer zzaS;

    public zzav() {
        this.zzcsx = -1;
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case TbsListener.ErrorCode.INCR_UPDATE_ERROR:
                    int position = adg.getPosition();
                    int zzLF = adg.zzLF();
                    switch (zzLF) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                            this.zzaS = Integer.valueOf(zzLF);
                            break;
                        default:
                            adg.zzcp(position);
                            zza(adg, zzLA);
                            continue;
                    }
                default:
                    if (!super.zza(adg, zzLA)) {
                        break;
                    } else {
                        continue;
                    }
            }
        }
        return this;
    }

    public final void zza(adh adh) throws IOException {
        if (this.zzaS != null) {
            adh.zzr(27, this.zzaS.intValue());
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        return this.zzaS != null ? zzn + adh.zzs(27, this.zzaS.intValue()) : zzn;
    }
}

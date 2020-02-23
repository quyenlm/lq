package com.google.android.gms.internal;

import com.tencent.smtt.sdk.TbsListener;
import java.io.IOException;

public final class zzay extends adj<zzay> {
    private static volatile zzay[] zzca;
    public Long zzbk = null;
    public Long zzbl = null;
    public Long zzcb = null;
    public Long zzcc = null;
    public Long zzcd = null;
    public Long zzce = null;
    public Integer zzcf;
    public Long zzcg = null;
    public Long zzch = null;
    public Long zzci = null;
    public Integer zzcj;
    public Long zzck = null;
    public Long zzcl = null;
    public Long zzcm = null;
    public Long zzcn = null;
    public Long zzco = null;
    public Long zzcp = null;
    public Long zzcq = null;
    public Long zzcr = null;
    public Long zzcs = null;
    public Long zzct = null;

    public zzay() {
        this.zzcsx = -1;
    }

    public static zzay[] zzo() {
        if (zzca == null) {
            synchronized (adn.zzcsw) {
                if (zzca == null) {
                    zzca = new zzay[0];
                }
            }
        }
        return zzca;
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 8:
                    this.zzbk = Long.valueOf(adg.zzLG());
                    continue;
                case 16:
                    this.zzbl = Long.valueOf(adg.zzLG());
                    continue;
                case 24:
                    this.zzcb = Long.valueOf(adg.zzLG());
                    continue;
                case 32:
                    this.zzcc = Long.valueOf(adg.zzLG());
                    continue;
                case 40:
                    this.zzcd = Long.valueOf(adg.zzLG());
                    continue;
                case 48:
                    this.zzce = Long.valueOf(adg.zzLG());
                    continue;
                case 56:
                    int position = adg.getPosition();
                    int zzLF = adg.zzLF();
                    switch (zzLF) {
                        case 0:
                        case 1:
                        case 2:
                        case 1000:
                            this.zzcf = Integer.valueOf(zzLF);
                            break;
                        default:
                            adg.zzcp(position);
                            zza(adg, zzLA);
                            continue;
                    }
                case 64:
                    this.zzcg = Long.valueOf(adg.zzLG());
                    continue;
                case 72:
                    this.zzch = Long.valueOf(adg.zzLG());
                    continue;
                case 80:
                    this.zzci = Long.valueOf(adg.zzLG());
                    continue;
                case 88:
                    int position2 = adg.getPosition();
                    int zzLF2 = adg.zzLF();
                    switch (zzLF2) {
                        case 0:
                        case 1:
                        case 2:
                        case 1000:
                            this.zzcj = Integer.valueOf(zzLF2);
                            break;
                        default:
                            adg.zzcp(position2);
                            zza(adg, zzLA);
                            continue;
                    }
                case 96:
                    this.zzck = Long.valueOf(adg.zzLG());
                    continue;
                case 104:
                    this.zzcl = Long.valueOf(adg.zzLG());
                    continue;
                case 112:
                    this.zzcm = Long.valueOf(adg.zzLG());
                    continue;
                case TbsListener.ErrorCode.DOWNLOAD_HAS_LOCAL_TBS_ERROR:
                    this.zzcn = Long.valueOf(adg.zzLG());
                    continue;
                case 128:
                    this.zzco = Long.valueOf(adg.zzLG());
                    continue;
                case 136:
                    this.zzcp = Long.valueOf(adg.zzLG());
                    continue;
                case 144:
                    this.zzcq = Long.valueOf(adg.zzLG());
                    continue;
                case 152:
                    this.zzcr = Long.valueOf(adg.zzLG());
                    continue;
                case 160:
                    this.zzcs = Long.valueOf(adg.zzLG());
                    continue;
                case 168:
                    this.zzct = Long.valueOf(adg.zzLG());
                    continue;
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
        if (this.zzbk != null) {
            adh.zzb(1, this.zzbk.longValue());
        }
        if (this.zzbl != null) {
            adh.zzb(2, this.zzbl.longValue());
        }
        if (this.zzcb != null) {
            adh.zzb(3, this.zzcb.longValue());
        }
        if (this.zzcc != null) {
            adh.zzb(4, this.zzcc.longValue());
        }
        if (this.zzcd != null) {
            adh.zzb(5, this.zzcd.longValue());
        }
        if (this.zzce != null) {
            adh.zzb(6, this.zzce.longValue());
        }
        if (this.zzcf != null) {
            adh.zzr(7, this.zzcf.intValue());
        }
        if (this.zzcg != null) {
            adh.zzb(8, this.zzcg.longValue());
        }
        if (this.zzch != null) {
            adh.zzb(9, this.zzch.longValue());
        }
        if (this.zzci != null) {
            adh.zzb(10, this.zzci.longValue());
        }
        if (this.zzcj != null) {
            adh.zzr(11, this.zzcj.intValue());
        }
        if (this.zzck != null) {
            adh.zzb(12, this.zzck.longValue());
        }
        if (this.zzcl != null) {
            adh.zzb(13, this.zzcl.longValue());
        }
        if (this.zzcm != null) {
            adh.zzb(14, this.zzcm.longValue());
        }
        if (this.zzcn != null) {
            adh.zzb(15, this.zzcn.longValue());
        }
        if (this.zzco != null) {
            adh.zzb(16, this.zzco.longValue());
        }
        if (this.zzcp != null) {
            adh.zzb(17, this.zzcp.longValue());
        }
        if (this.zzcq != null) {
            adh.zzb(18, this.zzcq.longValue());
        }
        if (this.zzcr != null) {
            adh.zzb(19, this.zzcr.longValue());
        }
        if (this.zzcs != null) {
            adh.zzb(20, this.zzcs.longValue());
        }
        if (this.zzct != null) {
            adh.zzb(21, this.zzct.longValue());
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzbk != null) {
            zzn += adh.zze(1, this.zzbk.longValue());
        }
        if (this.zzbl != null) {
            zzn += adh.zze(2, this.zzbl.longValue());
        }
        if (this.zzcb != null) {
            zzn += adh.zze(3, this.zzcb.longValue());
        }
        if (this.zzcc != null) {
            zzn += adh.zze(4, this.zzcc.longValue());
        }
        if (this.zzcd != null) {
            zzn += adh.zze(5, this.zzcd.longValue());
        }
        if (this.zzce != null) {
            zzn += adh.zze(6, this.zzce.longValue());
        }
        if (this.zzcf != null) {
            zzn += adh.zzs(7, this.zzcf.intValue());
        }
        if (this.zzcg != null) {
            zzn += adh.zze(8, this.zzcg.longValue());
        }
        if (this.zzch != null) {
            zzn += adh.zze(9, this.zzch.longValue());
        }
        if (this.zzci != null) {
            zzn += adh.zze(10, this.zzci.longValue());
        }
        if (this.zzcj != null) {
            zzn += adh.zzs(11, this.zzcj.intValue());
        }
        if (this.zzck != null) {
            zzn += adh.zze(12, this.zzck.longValue());
        }
        if (this.zzcl != null) {
            zzn += adh.zze(13, this.zzcl.longValue());
        }
        if (this.zzcm != null) {
            zzn += adh.zze(14, this.zzcm.longValue());
        }
        if (this.zzcn != null) {
            zzn += adh.zze(15, this.zzcn.longValue());
        }
        if (this.zzco != null) {
            zzn += adh.zze(16, this.zzco.longValue());
        }
        if (this.zzcp != null) {
            zzn += adh.zze(17, this.zzcp.longValue());
        }
        if (this.zzcq != null) {
            zzn += adh.zze(18, this.zzcq.longValue());
        }
        if (this.zzcr != null) {
            zzn += adh.zze(19, this.zzcr.longValue());
        }
        if (this.zzcs != null) {
            zzn += adh.zze(20, this.zzcs.longValue());
        }
        return this.zzct != null ? zzn + adh.zze(21, this.zzct.longValue()) : zzn;
    }
}

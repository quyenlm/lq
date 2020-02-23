package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;

public final class zzbio {
    private final aci zzaKP;

    private zzbio(aci aci) {
        this.zzaKP = (aci) zzbo.zzu(aci);
    }

    private static aci zza(int i, long j, int i2) {
        aci aci = new aci();
        aci.zzcqq = i;
        aci.zzcqr = j;
        switch (i) {
            case 1:
            case 2:
            case 3:
                aci.zzcqt = i2;
                break;
            case 4:
            case 5:
            case 6:
                aci.zzcqu = i2;
                break;
            case 7:
            case 8:
            case 9:
                aci.zzcqv = i2;
                break;
            case 10:
            case 11:
            case 12:
                aci.zzcqw = i2;
                break;
            case 13:
            case 14:
            case 15:
                aci.zzcqx = i2;
                break;
            case 16:
            case 17:
            case 18:
                aci.zzcqy = i2;
                break;
            default:
                zzeq.zza("AudioStateFenceStub", "Unknown trigger type=%s", (Object) Integer.valueOf(i));
                break;
        }
        return aci;
    }

    public static zzbio zzaJ(int i) {
        return new zzbio(zza(1, 0, i));
    }

    public static zzbio zzsC() {
        return new zzbio(zza(2, 3000, 0));
    }

    public static zzbio zzsD() {
        return new zzbio(zza(3, 3000, 0));
    }

    public final aci zzsE() {
        return this.zzaKP;
    }
}

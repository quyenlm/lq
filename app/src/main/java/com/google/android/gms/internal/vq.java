package com.google.android.gms.internal;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class vq {
    public static final vq zzcgZ = new vq();
    private xe zzcgV = xr.zzJl();
    private Integer zzcha;
    private int zzchb;
    private xm zzchc = null;
    private wp zzchd = null;
    private xm zzche = null;
    private wp zzchf = null;
    private String zzchg = null;

    public static vq zzF(Map<String, Object> map) {
        xe xqVar;
        vq vqVar = new vq();
        vqVar.zzcha = (Integer) map.get("l");
        if (map.containsKey("sp")) {
            vqVar.zzchc = zze(xp.zza(map.get("sp"), xd.zzJb()));
            String str = (String) map.get("sn");
            if (str != null) {
                vqVar.zzchd = wp.zzgT(str);
            }
        }
        if (map.containsKey("ep")) {
            vqVar.zzche = zze(xp.zza(map.get("ep"), xd.zzJb()));
            String str2 = (String) map.get("en");
            if (str2 != null) {
                vqVar.zzchf = wp.zzgT(str2);
            }
        }
        String str3 = (String) map.get("vf");
        if (str3 != null) {
            vqVar.zzchb = str3.equals("l") ? vs.zzchi : vs.zzchj;
        }
        String str4 = (String) map.get("i");
        if (str4 != null) {
            if (str4.equals(".value")) {
                xqVar = xw.zzJm();
            } else if (str4.equals(".key")) {
                xqVar = xg.zzJh();
            } else if (str4.equals(".priority")) {
                throw new IllegalStateException("queryDefinition shouldn't ever be .priority since it's the default");
            } else {
                xqVar = new xq(new qr(str4));
            }
            vqVar.zzcgV = xqVar;
        }
        return vqVar;
    }

    private final vq zzIn() {
        vq vqVar = new vq();
        vqVar.zzcha = this.zzcha;
        vqVar.zzchc = this.zzchc;
        vqVar.zzchd = this.zzchd;
        vqVar.zzche = this.zzche;
        vqVar.zzchf = this.zzchf;
        vqVar.zzchb = this.zzchb;
        vqVar.zzcgV = this.zzcgV;
        return vqVar;
    }

    private static xm zze(xm xmVar) {
        if ((xmVar instanceof xu) || (xmVar instanceof wo) || (xmVar instanceof xc) || (xmVar instanceof xd)) {
            return xmVar;
        }
        if (xmVar instanceof xk) {
            return new xc(Double.valueOf(((Long) xmVar.getValue()).doubleValue()), xd.zzJb());
        }
        String valueOf = String.valueOf(xmVar.getValue());
        throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 43).append("Unexpected value passed to normalizeValue: ").append(valueOf).toString());
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        vq vqVar = (vq) obj;
        if (this.zzcha == null ? vqVar.zzcha != null : !this.zzcha.equals(vqVar.zzcha)) {
            return false;
        }
        if (this.zzcgV == null ? vqVar.zzcgV != null : !this.zzcgV.equals(vqVar.zzcgV)) {
            return false;
        }
        if (this.zzchf == null ? vqVar.zzchf != null : !this.zzchf.equals(vqVar.zzchf)) {
            return false;
        }
        if (this.zzche == null ? vqVar.zzche != null : !this.zzche.equals(vqVar.zzche)) {
            return false;
        }
        if (this.zzchd == null ? vqVar.zzchd != null : !this.zzchd.equals(vqVar.zzchd)) {
            return false;
        }
        if (this.zzchc == null ? vqVar.zzchc != null : !this.zzchc.equals(vqVar.zzchc)) {
            return false;
        }
        return zzIo() == vqVar.zzIo();
    }

    public final int getLimit() {
        if (zzIk()) {
            return this.zzcha.intValue();
        }
        throw new IllegalArgumentException("Cannot get limit if limit has not been set");
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.zzchf != null ? this.zzchf.hashCode() : 0) + (((this.zzche != null ? this.zzche.hashCode() : 0) + (((this.zzchd != null ? this.zzchd.hashCode() : 0) + (((this.zzchc != null ? this.zzchc.hashCode() : 0) + (((zzIo() ? 1231 : 1237) + ((this.zzcha != null ? this.zzcha.intValue() : 0) * 31)) * 31)) * 31)) * 31)) * 31)) * 31;
        if (this.zzcgV != null) {
            i = this.zzcgV.hashCode();
        }
        return hashCode + i;
    }

    public final boolean isDefault() {
        return zzIq() && this.zzcgV.equals(xr.zzJl());
    }

    public final String toString() {
        return zzIp().toString();
    }

    public final boolean zzIe() {
        return this.zzchc != null;
    }

    public final xm zzIf() {
        if (zzIe()) {
            return this.zzchc;
        }
        throw new IllegalArgumentException("Cannot get index start value if start has not been set");
    }

    public final wp zzIg() {
        if (zzIe()) {
            return this.zzchd != null ? this.zzchd : wp.zzIJ();
        }
        throw new IllegalArgumentException("Cannot get index start name if start has not been set");
    }

    public final boolean zzIh() {
        return this.zzche != null;
    }

    public final xm zzIi() {
        if (zzIh()) {
            return this.zzche;
        }
        throw new IllegalArgumentException("Cannot get index end value if start has not been set");
    }

    public final wp zzIj() {
        if (zzIh()) {
            return this.zzchf != null ? this.zzchf : wp.zzIK();
        }
        throw new IllegalArgumentException("Cannot get index end name if start has not been set");
    }

    public final boolean zzIk() {
        return this.zzcha != null;
    }

    public final boolean zzIl() {
        return zzIk() && this.zzchb != 0;
    }

    public final xe zzIm() {
        return this.zzcgV;
    }

    public final boolean zzIo() {
        return this.zzchb != 0 ? this.zzchb == vs.zzchi : zzIe();
    }

    public final Map<String, Object> zzIp() {
        HashMap hashMap = new HashMap();
        if (zzIe()) {
            hashMap.put("sp", this.zzchc.getValue());
            if (this.zzchd != null) {
                hashMap.put("sn", this.zzchd.asString());
            }
        }
        if (zzIh()) {
            hashMap.put("ep", this.zzche.getValue());
            if (this.zzchf != null) {
                hashMap.put("en", this.zzchf.asString());
            }
        }
        if (this.zzcha != null) {
            hashMap.put("l", this.zzcha);
            int i = this.zzchb;
            if (i == 0) {
                i = zzIe() ? vs.zzchi : vs.zzchj;
            }
            switch (vr.zzchh[i - 1]) {
                case 1:
                    hashMap.put("vf", "l");
                    break;
                case 2:
                    hashMap.put("vf", "r");
                    break;
            }
        }
        if (!this.zzcgV.equals(xr.zzJl())) {
            hashMap.put("i", this.zzcgV.zzJd());
        }
        return hashMap;
    }

    public final boolean zzIq() {
        return !zzIe() && !zzIh() && !zzIk();
    }

    public final String zzIr() {
        if (this.zzchg == null) {
            try {
                this.zzchg = yr.zzak(zzIp());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return this.zzchg;
    }

    public final wf zzIs() {
        return zzIq() ? new wd(this.zzcgV) : zzIk() ? new we(this) : new wh(this);
    }

    public final vq zza(xe xeVar) {
        vq zzIn = zzIn();
        zzIn.zzcgV = xeVar;
        return zzIn;
    }

    public final vq zza(xm xmVar, wp wpVar) {
        zd.zzaF(!(xmVar instanceof xk));
        vq zzIn = zzIn();
        zzIn.zzchc = xmVar;
        zzIn.zzchd = wpVar;
        return zzIn;
    }

    public final vq zzb(xm xmVar, wp wpVar) {
        zd.zzaF(!(xmVar instanceof xk));
        vq zzIn = zzIn();
        zzIn.zzche = xmVar;
        zzIn.zzchf = wpVar;
        return zzIn;
    }

    public final vq zzbW(int i) {
        vq zzIn = zzIn();
        zzIn.zzcha = Integer.valueOf(i);
        zzIn.zzchb = vs.zzchi;
        return zzIn;
    }

    public final vq zzbX(int i) {
        vq zzIn = zzIn();
        zzIn.zzcha = Integer.valueOf(i);
        zzIn.zzchb = vs.zzchj;
        return zzIn;
    }
}

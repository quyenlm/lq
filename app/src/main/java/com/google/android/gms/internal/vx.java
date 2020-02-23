package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public final class vx {
    private static wg zzchu = new vy();
    private final wf zzcht;

    public vx(wf wfVar) {
        this.zzcht = wfVar;
    }

    private final vw zza(vw vwVar, qr qrVar, pz pzVar, ts tsVar, xm xmVar, boolean z, wc wcVar) {
        if (vwVar.zzIA().zzFn().isEmpty() && !vwVar.zzIA().zzHU()) {
            return vwVar;
        }
        if (!qrVar.isEmpty()) {
            pzVar = pz.zzGI().zzb(qrVar, pzVar);
        }
        xm zzFn = vwVar.zzIA().zzFn();
        Map<wp, pz> zzGL = pzVar.zzGL();
        vw vwVar2 = vwVar;
        for (Map.Entry next : zzGL.entrySet()) {
            wp wpVar = (wp) next.getKey();
            if (zzFn.zzk(wpVar)) {
                vwVar2 = zza(vwVar2, new qr(wpVar), ((pz) next.getValue()).zzb(zzFn.zzm(wpVar)), tsVar, xmVar, z, wcVar);
            }
        }
        for (Map.Entry next2 : zzGL.entrySet()) {
            wp wpVar2 = (wp) next2.getKey();
            boolean z2 = !vwVar.zzIA().zzf(wpVar2) && ((pz) next2.getValue()).zzGJ() == null;
            if (!zzFn.zzk(wpVar2) && !z2) {
                vwVar2 = zza(vwVar2, new qr(wpVar2), ((pz) next2.getValue()).zzb(zzFn.zzm(wpVar2)), tsVar, xmVar, z, wcVar);
            }
        }
        return vwVar2;
    }

    private final vw zza(vw vwVar, qr qrVar, ts tsVar, wg wgVar, wc wcVar) {
        xm zza;
        xf zza2;
        xm zzc;
        vg zzIy = vwVar.zzIy();
        if (tsVar.zzu(qrVar) != null) {
            return vwVar;
        }
        if (qrVar.isEmpty()) {
            if (vwVar.zzIA().zzHV()) {
                xm zzIB = vwVar.zzIB();
                if (!(zzIB instanceof wr)) {
                    zzIB = xd.zzJb();
                }
                zzc = tsVar.zzd(zzIB);
            } else {
                zzc = tsVar.zzc(vwVar.zzIB());
            }
            zza2 = this.zzcht.zza(vwVar.zzIy().zzHW(), xf.zza(zzc, this.zzcht.zzIm()), wcVar);
        } else {
            wp zzHc = qrVar.zzHc();
            if (zzHc.zzIN()) {
                xm zza3 = tsVar.zza(qrVar, zzIy.zzFn(), vwVar.zzIA().zzFn());
                zza2 = zza3 != null ? this.zzcht.zza(zzIy.zzHW(), zza3) : zzIy.zzHW();
            } else {
                qr zzHd = qrVar.zzHd();
                if (zzIy.zzf(zzHc)) {
                    xm zza4 = tsVar.zza(qrVar, zzIy.zzFn(), vwVar.zzIA().zzFn());
                    zza = zza4 != null ? zzIy.zzFn().zzm(zzHc).zzl(zzHd, zza4) : zzIy.zzFn().zzm(zzHc);
                } else {
                    zza = tsVar.zza(zzHc, vwVar.zzIA());
                }
                zza2 = zza != null ? this.zzcht.zza(zzIy.zzHW(), zzHc, zza, zzHd, wgVar, wcVar) : zzIy.zzHW();
            }
        }
        return vwVar.zza(zza2, zzIy.zzHU() || qrVar.isEmpty(), this.zzcht.zzIE());
    }

    private final vw zza(vw vwVar, qr qrVar, xm xmVar, ts tsVar, xm xmVar2, wc wcVar) {
        xm zzh;
        vg zzIy = vwVar.zzIy();
        wb wbVar = new wb(tsVar, vwVar, xmVar2);
        if (qrVar.isEmpty()) {
            return vwVar.zza(this.zzcht.zza(vwVar.zzIy().zzHW(), xf.zza(xmVar, this.zzcht.zzIm()), wcVar), true, this.zzcht.zzIE());
        }
        wp zzHc = qrVar.zzHc();
        if (zzHc.zzIN()) {
            return vwVar.zza(this.zzcht.zza(vwVar.zzIy().zzHW(), xmVar), zzIy.zzHU(), zzIy.zzHV());
        }
        qr zzHd = qrVar.zzHd();
        xm zzm = zzIy.zzFn().zzm(zzHc);
        if (zzHd.isEmpty()) {
            zzh = xmVar;
        } else {
            zzh = wbVar.zzh(zzHc);
            if (zzh == null) {
                zzh = xd.zzJb();
            } else if (!zzHd.zzHf().zzIN() || !zzh.zzN(zzHd.zzHe()).isEmpty()) {
                zzh = zzh.zzl(zzHd, xmVar);
            }
        }
        return !zzm.equals(zzh) ? vwVar.zza(this.zzcht.zza(zzIy.zzHW(), zzHc, zzh, zzHd, wbVar, wcVar), zzIy.zzHU(), this.zzcht.zzIE()) : vwVar;
    }

    private final vw zza(vw vwVar, qr qrVar, xm xmVar, ts tsVar, xm xmVar2, boolean z, wc wcVar) {
        xf zza;
        vg zzIA = vwVar.zzIA();
        wf zzID = z ? this.zzcht : this.zzcht.zzID();
        if (qrVar.isEmpty()) {
            zza = zzID.zza(zzIA.zzHW(), xf.zza(xmVar, zzID.zzIm()), (wc) null);
        } else if (!zzID.zzIE() || zzIA.zzHV()) {
            wp zzHc = qrVar.zzHc();
            if (!zzIA.zzL(qrVar) && qrVar.size() > 1) {
                return vwVar;
            }
            qr zzHd = qrVar.zzHd();
            xm zzl = zzIA.zzFn().zzm(zzHc).zzl(zzHd, xmVar);
            zza = zzHc.zzIN() ? zzID.zza(zzIA.zzHW(), zzl) : zzID.zza(zzIA.zzHW(), zzHc, zzl, zzHd, zzchu, (wc) null);
        } else {
            wp zzHc2 = qrVar.zzHc();
            zza = zzID.zza(zzIA.zzHW(), zzIA.zzHW().zzg(zzHc2, zzIA.zzFn().zzm(zzHc2).zzl(qrVar.zzHd(), xmVar)), (wc) null);
        }
        vw zzb = vwVar.zzb(zza, zzIA.zzHU() || qrVar.isEmpty(), zzID.zzIE());
        return zza(zzb, qrVar, tsVar, new wb(tsVar, zzb, xmVar2), wcVar);
    }

    private static boolean zza(vw vwVar, wp wpVar) {
        return vwVar.zzIy().zzf(wpVar);
    }

    public final wa zza(vw vwVar, tx txVar, ts tsVar, xm xmVar) {
        vw zza;
        boolean z;
        wc wcVar = new wc();
        switch (vz.zzchv[txVar.zzHF().ordinal()]) {
            case 1:
                ub ubVar = (ub) txVar;
                if (!ubVar.zzHE().zzHG()) {
                    zza = zza(vwVar, ubVar.zzFq(), ubVar.zzHJ(), tsVar, xmVar, ubVar.zzHE().zzHH() || (vwVar.zzIA().zzHV() && !ubVar.zzFq().isEmpty()), wcVar);
                    break;
                } else {
                    zza = zza(vwVar, ubVar.zzFq(), ubVar.zzHJ(), tsVar, xmVar, wcVar);
                    break;
                }
                break;
            case 2:
                tw twVar = (tw) txVar;
                if (!twVar.zzHE().zzHG()) {
                    zza = zza(vwVar, twVar.zzFq(), twVar.zzHD(), tsVar, xmVar, twVar.zzHE().zzHH() || vwVar.zzIA().zzHV(), wcVar);
                    break;
                } else {
                    qr zzFq = twVar.zzFq();
                    pz zzHD = twVar.zzHD();
                    Iterator<Map.Entry<qr, xm>> it = zzHD.iterator();
                    vw vwVar2 = vwVar;
                    while (it.hasNext()) {
                        Map.Entry next = it.next();
                        qr zzh = zzFq.zzh((qr) next.getKey());
                        if (zza(vwVar, zzh.zzHc())) {
                            vwVar2 = zza(vwVar2, zzh, (xm) next.getValue(), tsVar, xmVar, wcVar);
                        }
                    }
                    Iterator<Map.Entry<qr, xm>> it2 = zzHD.iterator();
                    while (it2.hasNext()) {
                        Map.Entry next2 = it2.next();
                        qr zzh2 = zzFq.zzh((qr) next2.getKey());
                        if (!zza(vwVar, zzh2.zzHc())) {
                            vwVar2 = zza(vwVar2, zzh2, (xm) next2.getValue(), tsVar, xmVar, wcVar);
                        }
                    }
                    zza = vwVar2;
                    break;
                }
                break;
            case 3:
                tu tuVar = (tu) txVar;
                if (!tuVar.zzHC()) {
                    qr zzFq2 = tuVar.zzFq();
                    uv<Boolean> zzHB = tuVar.zzHB();
                    if (tsVar.zzu(zzFq2) == null) {
                        boolean zzHV = vwVar.zzIA().zzHV();
                        vg zzIA = vwVar.zzIA();
                        if (zzHB.getValue() != null) {
                            if ((!zzFq2.isEmpty() || !zzIA.zzHU()) && !zzIA.zzL(zzFq2)) {
                                if (!zzFq2.isEmpty()) {
                                    zza = vwVar;
                                    break;
                                } else {
                                    pz zzGI = pz.zzGI();
                                    for (xl xlVar : zzIA.zzFn()) {
                                        zzGI = zzGI.zza(xlVar.zzJk(), xlVar.zzFn());
                                    }
                                    zza = zza(vwVar, zzFq2, zzGI, tsVar, xmVar, zzHV, wcVar);
                                    break;
                                }
                            } else {
                                zza = zza(vwVar, zzFq2, zzIA.zzFn().zzN(zzFq2), tsVar, xmVar, zzHV, wcVar);
                                break;
                            }
                        } else {
                            pz zzGI2 = pz.zzGI();
                            Iterator<Map.Entry<qr, Boolean>> it3 = zzHB.iterator();
                            while (it3.hasNext()) {
                                qr qrVar = (qr) it3.next().getKey();
                                qr zzh3 = zzFq2.zzh(qrVar);
                                if (zzIA.zzL(zzh3)) {
                                    zzGI2 = zzGI2.zze(qrVar, zzIA.zzFn().zzN(zzh3));
                                }
                            }
                            zza = zza(vwVar, zzFq2, zzGI2, tsVar, xmVar, zzHV, wcVar);
                            break;
                        }
                    } else {
                        zza = vwVar;
                        break;
                    }
                } else {
                    qr zzFq3 = tuVar.zzFq();
                    if (tsVar.zzu(zzFq3) != null) {
                        zza = vwVar;
                        break;
                    } else {
                        wb wbVar = new wb(tsVar, vwVar, xmVar);
                        xf zzHW = vwVar.zzIy().zzHW();
                        if (zzFq3.isEmpty() || zzFq3.zzHc().zzIN()) {
                            zzHW = this.zzcht.zza(zzHW, xf.zza(vwVar.zzIA().zzHU() ? tsVar.zzc(vwVar.zzIB()) : tsVar.zzd(vwVar.zzIA().zzFn()), this.zzcht.zzIm()), wcVar);
                        } else {
                            wp zzHc = zzFq3.zzHc();
                            xm zza2 = tsVar.zza(zzHc, vwVar.zzIA());
                            if (zza2 == null && vwVar.zzIA().zzf(zzHc)) {
                                zza2 = zzHW.zzFn().zzm(zzHc);
                            }
                            if (zza2 != null) {
                                zzHW = this.zzcht.zza(zzHW, zzHc, zza2, zzFq3.zzHd(), wbVar, wcVar);
                            } else if (zza2 == null && vwVar.zzIy().zzFn().zzk(zzHc)) {
                                zzHW = this.zzcht.zza(zzHW, zzHc, xd.zzJb(), zzFq3.zzHd(), wbVar, wcVar);
                            }
                            if (zzHW.zzFn().isEmpty() && vwVar.zzIA().zzHU()) {
                                xm zzc = tsVar.zzc(vwVar.zzIB());
                                if (zzc.zzIQ()) {
                                    zzHW = this.zzcht.zza(zzHW, xf.zza(zzc, this.zzcht.zzIm()), wcVar);
                                }
                            }
                        }
                        if (!vwVar.zzIA().zzHU()) {
                            if (tsVar.zzu(qr.zzGZ()) == null) {
                                z = false;
                                zza = vwVar.zza(zzHW, z, this.zzcht.zzIE());
                                break;
                            }
                        }
                        z = true;
                        zza = vwVar.zza(zzHW, z, this.zzcht.zzIE());
                    }
                }
                break;
            case 4:
                qr zzFq4 = txVar.zzFq();
                vg zzIA2 = vwVar.zzIA();
                zza = zza(vwVar.zzb(zzIA2.zzHW(), zzIA2.zzHU() || zzFq4.isEmpty(), zzIA2.zzHV()), zzFq4, tsVar, zzchu, wcVar);
                break;
            default:
                String valueOf = String.valueOf(txVar.zzHF());
                throw new AssertionError(new StringBuilder(String.valueOf(valueOf).length() + 19).append("Unknown operation: ").append(valueOf).toString());
        }
        ArrayList arrayList = new ArrayList(wcVar.zzIC());
        vg zzIy = zza.zzIy();
        if (zzIy.zzHU()) {
            boolean z2 = zzIy.zzFn().zzIQ() || zzIy.zzFn().isEmpty();
            if (!arrayList.isEmpty() || !vwVar.zzIy().zzHU() || ((z2 && !zzIy.zzFn().equals(vwVar.zzIz())) || !zzIy.zzFn().zzIR().equals(vwVar.zzIz().zzIR()))) {
                arrayList.add(vi.zza(zzIy.zzHW()));
            }
        }
        return new wa(zza, arrayList);
    }
}

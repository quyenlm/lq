package com.google.android.gms.internal;

import com.beetalk.sdk.ShareConstants;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.tencent.imsdk.expansion.downloader.impl.DownloaderService;
import com.tencent.smtt.sdk.TbsListener;
import java.io.IOException;
import org.apache.http.HttpStatus;

public final class zzax extends adj<zzax> {
    public String zzaP = null;
    public String zzaT = null;
    public String zzaV = null;
    public String zzaW = null;
    public String zzaX = null;
    public String zzaY = null;
    public Long zzaZ = null;
    private zzba zzbA = null;
    public Long zzbB = null;
    public Long zzbC = null;
    public Long zzbD = null;
    public Long zzbE = null;
    public Long zzbF = null;
    public Long zzbG = null;
    public Integer zzbH;
    public Integer zzbI;
    public Long zzbJ = null;
    public Long zzbK = null;
    private Long zzbL = null;
    private Long zzbM = null;
    private Long zzbN = null;
    public Integer zzbO;
    public zzay zzbP = null;
    public zzay[] zzbQ = zzay.zzo();
    public zzaz zzbR = null;
    private Long zzbS = null;
    private Long zzbT = null;
    public String zzbU = null;
    public Integer zzbV;
    public Boolean zzbW = null;
    private String zzbX = null;
    public Long zzbY = null;
    public zzbd zzbZ = null;
    private Long zzba = null;
    public Long zzbb = null;
    public Long zzbc = null;
    private Long zzbd = null;
    private Long zzbe = null;
    private Long zzbf = null;
    private Long zzbg = null;
    private Long zzbh = null;
    public Long zzbi = null;
    private String zzbj = null;
    public Long zzbk = null;
    public Long zzbl = null;
    public Long zzbm = null;
    public Long zzbn = null;
    private Long zzbo = null;
    private Long zzbp = null;
    public Long zzbq = null;
    public Long zzbr = null;
    public Long zzbs = null;
    public String zzbt = null;
    public Long zzbu = null;
    public Long zzbv = null;
    public Long zzbw = null;
    public Long zzbx = null;
    public Long zzby = null;
    public Long zzbz = null;

    public zzax() {
        this.zzcsx = -1;
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 10:
                    this.zzaY = adg.readString();
                    continue;
                case 18:
                    this.zzaP = adg.readString();
                    continue;
                case 24:
                    this.zzaZ = Long.valueOf(adg.zzLG());
                    continue;
                case 32:
                    this.zzba = Long.valueOf(adg.zzLG());
                    continue;
                case 40:
                    this.zzbb = Long.valueOf(adg.zzLG());
                    continue;
                case 48:
                    this.zzbc = Long.valueOf(adg.zzLG());
                    continue;
                case 56:
                    this.zzbd = Long.valueOf(adg.zzLG());
                    continue;
                case 64:
                    this.zzbe = Long.valueOf(adg.zzLG());
                    continue;
                case 72:
                    this.zzbf = Long.valueOf(adg.zzLG());
                    continue;
                case 80:
                    this.zzbg = Long.valueOf(adg.zzLG());
                    continue;
                case 88:
                    this.zzbh = Long.valueOf(adg.zzLG());
                    continue;
                case 96:
                    this.zzbi = Long.valueOf(adg.zzLG());
                    continue;
                case 106:
                    this.zzbj = adg.readString();
                    continue;
                case 112:
                    this.zzbk = Long.valueOf(adg.zzLG());
                    continue;
                case TbsListener.ErrorCode.DOWNLOAD_HAS_LOCAL_TBS_ERROR:
                    this.zzbl = Long.valueOf(adg.zzLG());
                    continue;
                case 128:
                    this.zzbm = Long.valueOf(adg.zzLG());
                    continue;
                case 136:
                    this.zzbn = Long.valueOf(adg.zzLG());
                    continue;
                case 144:
                    this.zzbo = Long.valueOf(adg.zzLG());
                    continue;
                case 152:
                    this.zzbp = Long.valueOf(adg.zzLG());
                    continue;
                case 160:
                    this.zzbq = Long.valueOf(adg.zzLG());
                    continue;
                case 168:
                    this.zzbT = Long.valueOf(adg.zzLG());
                    continue;
                case 176:
                    this.zzbr = Long.valueOf(adg.zzLG());
                    continue;
                case ShareConstants.ERROR_CODE.GG_RESULT_CANCELLED:
                    this.zzbs = Long.valueOf(adg.zzLG());
                    continue;
                case DownloaderService.STATUS_WAITING_TO_RETRY:
                    this.zzbU = adg.readString();
                    continue;
                case 200:
                    this.zzbY = Long.valueOf(adg.zzLG());
                    continue;
                case TbsListener.ErrorCode.EXCEED_DEXOPT_RETRY_NUM:
                    int position = adg.getPosition();
                    int zzLF = adg.zzLF();
                    switch (zzLF) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                            this.zzbV = Integer.valueOf(zzLF);
                            break;
                        default:
                            adg.zzcp(position);
                            zza(adg, zzLA);
                            continue;
                    }
                case TbsListener.ErrorCode.INCR_UPDATE_EXCEPTION:
                    this.zzaT = adg.readString();
                    continue;
                case TbsListener.ErrorCode.EXCEED_INCR_UPDATE:
                    this.zzbW = Boolean.valueOf(adg.zzLD());
                    continue;
                case 234:
                    this.zzbt = adg.readString();
                    continue;
                case 242:
                    this.zzbX = adg.readString();
                    continue;
                case 248:
                    this.zzbu = Long.valueOf(adg.zzLG());
                    continue;
                case 256:
                    this.zzbv = Long.valueOf(adg.zzLG());
                    continue;
                case 264:
                    this.zzbw = Long.valueOf(adg.zzLG());
                    continue;
                case 274:
                    this.zzaV = adg.readString();
                    continue;
                case 280:
                    this.zzbx = Long.valueOf(adg.zzLG());
                    continue;
                case 288:
                    this.zzby = Long.valueOf(adg.zzLG());
                    continue;
                case 296:
                    this.zzbz = Long.valueOf(adg.zzLG());
                    continue;
                case TbsListener.ErrorCode.THROWABLE_QBSDK_INIT:
                    if (this.zzbA == null) {
                        this.zzbA = new zzba();
                    }
                    adg.zza(this.zzbA);
                    continue;
                case TbsListener.ErrorCode.ERROR_TBSCORE_SHARE_DIR:
                    this.zzbB = Long.valueOf(adg.zzLG());
                    continue;
                case 320:
                    this.zzbC = Long.valueOf(adg.zzLG());
                    continue;
                case TbsListener.ErrorCode.THROWABLE_INITTESRUNTIMEENVIRONMENT:
                    this.zzbD = Long.valueOf(adg.zzLG());
                    continue;
                case 336:
                    this.zzbE = Long.valueOf(adg.zzLG());
                    continue;
                case 346:
                    int zzb = ads.zzb(adg, 346);
                    int length = this.zzbQ == null ? 0 : this.zzbQ.length;
                    zzay[] zzayArr = new zzay[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzbQ, 0, zzayArr, 0, length);
                    }
                    while (length < zzayArr.length - 1) {
                        zzayArr[length] = new zzay();
                        adg.zza(zzayArr[length]);
                        adg.zzLA();
                        length++;
                    }
                    zzayArr[length] = new zzay();
                    adg.zza(zzayArr[length]);
                    this.zzbQ = zzayArr;
                    continue;
                case 352:
                    this.zzbF = Long.valueOf(adg.zzLG());
                    continue;
                case 360:
                    this.zzbG = Long.valueOf(adg.zzLG());
                    continue;
                case 370:
                    this.zzaW = adg.readString();
                    continue;
                case 378:
                    this.zzaX = adg.readString();
                    continue;
                case 384:
                    int position2 = adg.getPosition();
                    int zzLF2 = adg.zzLF();
                    switch (zzLF2) {
                        case 0:
                        case 1:
                        case 2:
                        case 1000:
                            this.zzbH = Integer.valueOf(zzLF2);
                            break;
                        default:
                            adg.zzcp(position2);
                            zza(adg, zzLA);
                            continue;
                    }
                case 392:
                    int position3 = adg.getPosition();
                    int zzLF3 = adg.zzLF();
                    switch (zzLF3) {
                        case 0:
                        case 1:
                        case 2:
                        case 1000:
                            this.zzbI = Integer.valueOf(zzLF3);
                            break;
                        default:
                            adg.zzcp(position3);
                            zza(adg, zzLA);
                            continue;
                    }
                case 402:
                    if (this.zzbP == null) {
                        this.zzbP = new zzay();
                    }
                    adg.zza(this.zzbP);
                    continue;
                case 408:
                    this.zzbJ = Long.valueOf(adg.zzLG());
                    continue;
                case 416:
                    this.zzbK = Long.valueOf(adg.zzLG());
                    continue;
                case HttpStatus.SC_FAILED_DEPENDENCY /*424*/:
                    this.zzbL = Long.valueOf(adg.zzLG());
                    continue;
                case 432:
                    this.zzbM = Long.valueOf(adg.zzLG());
                    continue;
                case 440:
                    this.zzbN = Long.valueOf(adg.zzLG());
                    continue;
                case 448:
                    int position4 = adg.getPosition();
                    int zzLF4 = adg.zzLF();
                    switch (zzLF4) {
                        case 0:
                        case 1:
                        case 2:
                        case 1000:
                            this.zzbO = Integer.valueOf(zzLF4);
                            break;
                        default:
                            adg.zzcp(position4);
                            zza(adg, zzLA);
                            continue;
                    }
                case FacebookRequestErrorClassification.ESC_APP_NOT_INSTALLED:
                    if (this.zzbR == null) {
                        this.zzbR = new zzaz();
                    }
                    adg.zza(this.zzbR);
                    continue;
                case 464:
                    this.zzbS = Long.valueOf(adg.zzLG());
                    continue;
                case 1610:
                    if (this.zzbZ == null) {
                        this.zzbZ = new zzbd();
                    }
                    adg.zza(this.zzbZ);
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
        if (this.zzaY != null) {
            adh.zzl(1, this.zzaY);
        }
        if (this.zzaP != null) {
            adh.zzl(2, this.zzaP);
        }
        if (this.zzaZ != null) {
            adh.zzb(3, this.zzaZ.longValue());
        }
        if (this.zzba != null) {
            adh.zzb(4, this.zzba.longValue());
        }
        if (this.zzbb != null) {
            adh.zzb(5, this.zzbb.longValue());
        }
        if (this.zzbc != null) {
            adh.zzb(6, this.zzbc.longValue());
        }
        if (this.zzbd != null) {
            adh.zzb(7, this.zzbd.longValue());
        }
        if (this.zzbe != null) {
            adh.zzb(8, this.zzbe.longValue());
        }
        if (this.zzbf != null) {
            adh.zzb(9, this.zzbf.longValue());
        }
        if (this.zzbg != null) {
            adh.zzb(10, this.zzbg.longValue());
        }
        if (this.zzbh != null) {
            adh.zzb(11, this.zzbh.longValue());
        }
        if (this.zzbi != null) {
            adh.zzb(12, this.zzbi.longValue());
        }
        if (this.zzbj != null) {
            adh.zzl(13, this.zzbj);
        }
        if (this.zzbk != null) {
            adh.zzb(14, this.zzbk.longValue());
        }
        if (this.zzbl != null) {
            adh.zzb(15, this.zzbl.longValue());
        }
        if (this.zzbm != null) {
            adh.zzb(16, this.zzbm.longValue());
        }
        if (this.zzbn != null) {
            adh.zzb(17, this.zzbn.longValue());
        }
        if (this.zzbo != null) {
            adh.zzb(18, this.zzbo.longValue());
        }
        if (this.zzbp != null) {
            adh.zzb(19, this.zzbp.longValue());
        }
        if (this.zzbq != null) {
            adh.zzb(20, this.zzbq.longValue());
        }
        if (this.zzbT != null) {
            adh.zzb(21, this.zzbT.longValue());
        }
        if (this.zzbr != null) {
            adh.zzb(22, this.zzbr.longValue());
        }
        if (this.zzbs != null) {
            adh.zzb(23, this.zzbs.longValue());
        }
        if (this.zzbU != null) {
            adh.zzl(24, this.zzbU);
        }
        if (this.zzbY != null) {
            adh.zzb(25, this.zzbY.longValue());
        }
        if (this.zzbV != null) {
            adh.zzr(26, this.zzbV.intValue());
        }
        if (this.zzaT != null) {
            adh.zzl(27, this.zzaT);
        }
        if (this.zzbW != null) {
            adh.zzk(28, this.zzbW.booleanValue());
        }
        if (this.zzbt != null) {
            adh.zzl(29, this.zzbt);
        }
        if (this.zzbX != null) {
            adh.zzl(30, this.zzbX);
        }
        if (this.zzbu != null) {
            adh.zzb(31, this.zzbu.longValue());
        }
        if (this.zzbv != null) {
            adh.zzb(32, this.zzbv.longValue());
        }
        if (this.zzbw != null) {
            adh.zzb(33, this.zzbw.longValue());
        }
        if (this.zzaV != null) {
            adh.zzl(34, this.zzaV);
        }
        if (this.zzbx != null) {
            adh.zzb(35, this.zzbx.longValue());
        }
        if (this.zzby != null) {
            adh.zzb(36, this.zzby.longValue());
        }
        if (this.zzbz != null) {
            adh.zzb(37, this.zzbz.longValue());
        }
        if (this.zzbA != null) {
            adh.zza(38, (adp) this.zzbA);
        }
        if (this.zzbB != null) {
            adh.zzb(39, this.zzbB.longValue());
        }
        if (this.zzbC != null) {
            adh.zzb(40, this.zzbC.longValue());
        }
        if (this.zzbD != null) {
            adh.zzb(41, this.zzbD.longValue());
        }
        if (this.zzbE != null) {
            adh.zzb(42, this.zzbE.longValue());
        }
        if (this.zzbQ != null && this.zzbQ.length > 0) {
            for (zzay zzay : this.zzbQ) {
                if (zzay != null) {
                    adh.zza(43, (adp) zzay);
                }
            }
        }
        if (this.zzbF != null) {
            adh.zzb(44, this.zzbF.longValue());
        }
        if (this.zzbG != null) {
            adh.zzb(45, this.zzbG.longValue());
        }
        if (this.zzaW != null) {
            adh.zzl(46, this.zzaW);
        }
        if (this.zzaX != null) {
            adh.zzl(47, this.zzaX);
        }
        if (this.zzbH != null) {
            adh.zzr(48, this.zzbH.intValue());
        }
        if (this.zzbI != null) {
            adh.zzr(49, this.zzbI.intValue());
        }
        if (this.zzbP != null) {
            adh.zza(50, (adp) this.zzbP);
        }
        if (this.zzbJ != null) {
            adh.zzb(51, this.zzbJ.longValue());
        }
        if (this.zzbK != null) {
            adh.zzb(52, this.zzbK.longValue());
        }
        if (this.zzbL != null) {
            adh.zzb(53, this.zzbL.longValue());
        }
        if (this.zzbM != null) {
            adh.zzb(54, this.zzbM.longValue());
        }
        if (this.zzbN != null) {
            adh.zzb(55, this.zzbN.longValue());
        }
        if (this.zzbO != null) {
            adh.zzr(56, this.zzbO.intValue());
        }
        if (this.zzbR != null) {
            adh.zza(57, (adp) this.zzbR);
        }
        if (this.zzbS != null) {
            adh.zzb(58, this.zzbS.longValue());
        }
        if (this.zzbZ != null) {
            adh.zza(201, (adp) this.zzbZ);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzaY != null) {
            zzn += adh.zzm(1, this.zzaY);
        }
        if (this.zzaP != null) {
            zzn += adh.zzm(2, this.zzaP);
        }
        if (this.zzaZ != null) {
            zzn += adh.zze(3, this.zzaZ.longValue());
        }
        if (this.zzba != null) {
            zzn += adh.zze(4, this.zzba.longValue());
        }
        if (this.zzbb != null) {
            zzn += adh.zze(5, this.zzbb.longValue());
        }
        if (this.zzbc != null) {
            zzn += adh.zze(6, this.zzbc.longValue());
        }
        if (this.zzbd != null) {
            zzn += adh.zze(7, this.zzbd.longValue());
        }
        if (this.zzbe != null) {
            zzn += adh.zze(8, this.zzbe.longValue());
        }
        if (this.zzbf != null) {
            zzn += adh.zze(9, this.zzbf.longValue());
        }
        if (this.zzbg != null) {
            zzn += adh.zze(10, this.zzbg.longValue());
        }
        if (this.zzbh != null) {
            zzn += adh.zze(11, this.zzbh.longValue());
        }
        if (this.zzbi != null) {
            zzn += adh.zze(12, this.zzbi.longValue());
        }
        if (this.zzbj != null) {
            zzn += adh.zzm(13, this.zzbj);
        }
        if (this.zzbk != null) {
            zzn += adh.zze(14, this.zzbk.longValue());
        }
        if (this.zzbl != null) {
            zzn += adh.zze(15, this.zzbl.longValue());
        }
        if (this.zzbm != null) {
            zzn += adh.zze(16, this.zzbm.longValue());
        }
        if (this.zzbn != null) {
            zzn += adh.zze(17, this.zzbn.longValue());
        }
        if (this.zzbo != null) {
            zzn += adh.zze(18, this.zzbo.longValue());
        }
        if (this.zzbp != null) {
            zzn += adh.zze(19, this.zzbp.longValue());
        }
        if (this.zzbq != null) {
            zzn += adh.zze(20, this.zzbq.longValue());
        }
        if (this.zzbT != null) {
            zzn += adh.zze(21, this.zzbT.longValue());
        }
        if (this.zzbr != null) {
            zzn += adh.zze(22, this.zzbr.longValue());
        }
        if (this.zzbs != null) {
            zzn += adh.zze(23, this.zzbs.longValue());
        }
        if (this.zzbU != null) {
            zzn += adh.zzm(24, this.zzbU);
        }
        if (this.zzbY != null) {
            zzn += adh.zze(25, this.zzbY.longValue());
        }
        if (this.zzbV != null) {
            zzn += adh.zzs(26, this.zzbV.intValue());
        }
        if (this.zzaT != null) {
            zzn += adh.zzm(27, this.zzaT);
        }
        if (this.zzbW != null) {
            this.zzbW.booleanValue();
            zzn += adh.zzct(28) + 1;
        }
        if (this.zzbt != null) {
            zzn += adh.zzm(29, this.zzbt);
        }
        if (this.zzbX != null) {
            zzn += adh.zzm(30, this.zzbX);
        }
        if (this.zzbu != null) {
            zzn += adh.zze(31, this.zzbu.longValue());
        }
        if (this.zzbv != null) {
            zzn += adh.zze(32, this.zzbv.longValue());
        }
        if (this.zzbw != null) {
            zzn += adh.zze(33, this.zzbw.longValue());
        }
        if (this.zzaV != null) {
            zzn += adh.zzm(34, this.zzaV);
        }
        if (this.zzbx != null) {
            zzn += adh.zze(35, this.zzbx.longValue());
        }
        if (this.zzby != null) {
            zzn += adh.zze(36, this.zzby.longValue());
        }
        if (this.zzbz != null) {
            zzn += adh.zze(37, this.zzbz.longValue());
        }
        if (this.zzbA != null) {
            zzn += adh.zzb(38, (adp) this.zzbA);
        }
        if (this.zzbB != null) {
            zzn += adh.zze(39, this.zzbB.longValue());
        }
        if (this.zzbC != null) {
            zzn += adh.zze(40, this.zzbC.longValue());
        }
        if (this.zzbD != null) {
            zzn += adh.zze(41, this.zzbD.longValue());
        }
        if (this.zzbE != null) {
            zzn += adh.zze(42, this.zzbE.longValue());
        }
        if (this.zzbQ != null && this.zzbQ.length > 0) {
            int i = zzn;
            for (zzay zzay : this.zzbQ) {
                if (zzay != null) {
                    i += adh.zzb(43, (adp) zzay);
                }
            }
            zzn = i;
        }
        if (this.zzbF != null) {
            zzn += adh.zze(44, this.zzbF.longValue());
        }
        if (this.zzbG != null) {
            zzn += adh.zze(45, this.zzbG.longValue());
        }
        if (this.zzaW != null) {
            zzn += adh.zzm(46, this.zzaW);
        }
        if (this.zzaX != null) {
            zzn += adh.zzm(47, this.zzaX);
        }
        if (this.zzbH != null) {
            zzn += adh.zzs(48, this.zzbH.intValue());
        }
        if (this.zzbI != null) {
            zzn += adh.zzs(49, this.zzbI.intValue());
        }
        if (this.zzbP != null) {
            zzn += adh.zzb(50, (adp) this.zzbP);
        }
        if (this.zzbJ != null) {
            zzn += adh.zze(51, this.zzbJ.longValue());
        }
        if (this.zzbK != null) {
            zzn += adh.zze(52, this.zzbK.longValue());
        }
        if (this.zzbL != null) {
            zzn += adh.zze(53, this.zzbL.longValue());
        }
        if (this.zzbM != null) {
            zzn += adh.zze(54, this.zzbM.longValue());
        }
        if (this.zzbN != null) {
            zzn += adh.zze(55, this.zzbN.longValue());
        }
        if (this.zzbO != null) {
            zzn += adh.zzs(56, this.zzbO.intValue());
        }
        if (this.zzbR != null) {
            zzn += adh.zzb(57, (adp) this.zzbR);
        }
        if (this.zzbS != null) {
            zzn += adh.zze(58, this.zzbS.longValue());
        }
        return this.zzbZ != null ? zzn + adh.zzb(201, (adp) this.zzbZ) : zzn;
    }
}

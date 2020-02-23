package com.google.android.gms.internal;

import android.text.TextUtils;
import com.amazonaws.services.s3.internal.Constants;
import com.google.android.gms.common.internal.zzbo;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.tencent.component.debug.TraceFormat;
import com.tencent.midas.oversea.comm.APDataReportManager;
import java.util.ArrayList;

public final class zzcxp {
    public static double zza(dp<?> dpVar, dp<?> dpVar2) {
        boolean z = true;
        zzbo.zzaf(dpVar != null);
        if (dpVar2 == null) {
            z = false;
        }
        zzbo.zzaf(z);
        double zzb = zzb(dpVar);
        double zzb2 = zzb(dpVar2);
        if (Double.isNaN(zzb) || Double.isNaN(zzb2)) {
            return Double.NaN;
        }
        if ((zzb == Double.POSITIVE_INFINITY && zzb2 == Double.NEGATIVE_INFINITY) || (zzb == Double.NEGATIVE_INFINITY && zzb2 == Double.POSITIVE_INFINITY)) {
            return Double.NaN;
        }
        return (!Double.isInfinite(zzb) || Double.isInfinite(zzb2)) ? (Double.isInfinite(zzb) || !Double.isInfinite(zzb2)) ? zzb + zzb2 : zzb2 : zzb;
    }

    public static boolean zza(dp<?> dpVar) {
        zzbo.zzaf(dpVar != null);
        if (dpVar == dv.zzbLu || dpVar == dv.zzbLt) {
            return false;
        }
        if (dpVar instanceof ds) {
            return ((ds) dpVar).zzDn().booleanValue();
        }
        if (dpVar instanceof dt) {
            if (((dt) dpVar).zzDo().doubleValue() == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE || ((dt) dpVar).zzDo().doubleValue() == -0.0d || Double.isNaN(((dt) dpVar).zzDo().doubleValue())) {
                return false;
            }
        } else if (dpVar instanceof eb) {
            if (((eb) dpVar).value().isEmpty()) {
                return false;
            }
        } else if (zzf(dpVar)) {
            String valueOf = String.valueOf(dpVar.toString());
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 33).append("Illegal type given to isTruthy: ").append(valueOf).append(".").toString());
        }
        return true;
    }

    public static double zzb(dp<?> dpVar) {
        dp<?> dpVar2 = dpVar;
        while (true) {
            zzbo.zzaf(dpVar2 != null);
            if (dpVar2 == dv.zzbLu) {
                return Double.NaN;
            }
            if (dpVar2 == dv.zzbLt) {
                return FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
            }
            if (dpVar2 instanceof ds) {
                if (((ds) dpVar2).zzDn().booleanValue()) {
                    return 1.0d;
                }
                return FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
            } else if (dpVar2 instanceof dt) {
                return ((dt) dpVar2).zzDo().doubleValue();
            } else {
                if (dpVar2 instanceof dw) {
                    dw dwVar = (dw) dpVar2;
                    if (!dwVar.zzDs().isEmpty()) {
                        if (dwVar.zzDs().size() != 1) {
                            break;
                        }
                        dpVar2 = new eb(zzd(dwVar.zzbG(0)));
                    } else {
                        return FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
                    }
                } else if (dpVar2 instanceof eb) {
                    eb ebVar = (eb) dpVar2;
                    if (ebVar.value().isEmpty()) {
                        return FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
                    }
                    try {
                        return Double.parseDouble(ebVar.value());
                    } catch (NumberFormatException e) {
                        return Double.NaN;
                    }
                }
            }
        }
        if (!zzf(dpVar2)) {
            return Double.NaN;
        }
        String valueOf = String.valueOf(dpVar2.toString());
        throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 41).append("Illegal type given to numberEquivalent: ").append(valueOf).append(".").toString());
    }

    public static boolean zzb(dp<?> dpVar, dp<?> dpVar2) {
        zzbo.zzaf(dpVar != null);
        zzbo.zzaf(dpVar2 != null);
        if (zzf(dpVar)) {
            String valueOf = String.valueOf(dpVar.toString());
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 50).append("Illegal type given to abstractRelationalCompare: ").append(valueOf).append(".").toString());
        } else if (zzf(dpVar2)) {
            String valueOf2 = String.valueOf(dpVar2.toString());
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf2).length() + 50).append("Illegal type given to abstractRelationalCompare: ").append(valueOf2).append(".").toString());
        } else {
            eb ebVar = ((dpVar instanceof dz) || (dpVar instanceof dw) || (dpVar instanceof du)) ? new eb(zzd(dpVar)) : dpVar;
            eb ebVar2 = ((dpVar2 instanceof dz) || (dpVar2 instanceof dw) || (dpVar2 instanceof du)) ? new eb(zzd(dpVar2)) : dpVar2;
            if ((ebVar instanceof eb) && (ebVar2 instanceof eb)) {
                return ebVar.value().compareTo(ebVar2.value()) < 0;
            }
            double zzb = zzb(ebVar);
            double zzb2 = zzb(ebVar2);
            if (Double.isNaN(zzb) || Double.isNaN(zzb2)) {
                return false;
            }
            if (zzb == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE && zzb2 == -0.0d) {
                return false;
            }
            if ((zzb == -0.0d && zzb2 == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) || zzb == Double.POSITIVE_INFINITY) {
                return false;
            }
            if (zzb2 == Double.POSITIVE_INFINITY) {
                return true;
            }
            if (zzb2 == Double.NEGATIVE_INFINITY) {
                return false;
            }
            if (zzb == Double.NEGATIVE_INFINITY) {
                return true;
            }
            return Double.compare(zzb, zzb2) < 0;
        }
    }

    public static double zzc(dp<?> dpVar) {
        double zzb = zzb(dpVar);
        return Double.isNaN(zzb) ? FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE : (zzb == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE || zzb == -0.0d || Double.isInfinite(zzb)) ? zzb : Math.signum(zzb) * Math.floor(Math.abs(zzb));
    }

    public static boolean zzc(dp<?> dpVar, dp<?> dpVar2) {
        dt dtVar = dpVar2;
        dt dtVar2 = dpVar;
        while (true) {
            zzbo.zzaf(dtVar2 != null);
            zzbo.zzaf(dtVar != null);
            if (zzf(dtVar2)) {
                String valueOf = String.valueOf(dtVar2.toString());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 48).append("Illegal type given to abstractEqualityCompare: ").append(valueOf).append(".").toString());
            } else if (zzf(dtVar)) {
                String valueOf2 = String.valueOf(dtVar.toString());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf2).length() + 48).append("Illegal type given to abstractEqualityCompare: ").append(valueOf2).append(".").toString());
            } else {
                String zze = zze(dtVar2);
                String zze2 = zze(dtVar);
                if (zze.equals(zze2)) {
                    char c = 65535;
                    switch (zze.hashCode()) {
                        case -1950496919:
                            if (zze.equals("Number")) {
                                c = 2;
                                break;
                            }
                            break;
                        case -1939501217:
                            if (zze.equals("Object")) {
                                c = 5;
                                break;
                            }
                            break;
                        case -1808118735:
                            if (zze.equals("String")) {
                                c = 3;
                                break;
                            }
                            break;
                        case 2439591:
                            if (zze.equals("Null")) {
                                c = 1;
                                break;
                            }
                            break;
                        case 965837104:
                            if (zze.equals("Undefined")) {
                                c = 0;
                                break;
                            }
                            break;
                        case 1729365000:
                            if (zze.equals("Boolean")) {
                                c = 4;
                                break;
                            }
                            break;
                    }
                    switch (c) {
                        case 0:
                        case 1:
                            return true;
                        case 2:
                            double doubleValue = ((dt) dtVar2).zzDo().doubleValue();
                            double doubleValue2 = ((dt) dtVar).zzDo().doubleValue();
                            return !Double.isNaN(doubleValue) && !Double.isNaN(doubleValue2) && doubleValue == doubleValue2;
                        case 3:
                            return ((eb) dtVar2).value().equals(((eb) dtVar).value());
                        case 4:
                            return ((ds) dtVar2).zzDn() == ((ds) dtVar).zzDn();
                        case 5:
                            return dtVar2 == dtVar;
                        default:
                            return false;
                    }
                } else if ((dtVar2 != dv.zzbLu && dtVar2 != dv.zzbLt) || (dtVar != dv.zzbLu && dtVar != dv.zzbLt)) {
                    if (zze.equals("Number") && zze2.equals("String")) {
                        dtVar = new dt(Double.valueOf(zzb(dtVar)));
                    } else if (zze.equals("String") && zze2.equals("Number")) {
                        dtVar2 = new dt(Double.valueOf(zzb(dtVar2)));
                    } else if (zze.equals("Boolean")) {
                        dtVar2 = new dt(Double.valueOf(zzb(dtVar2)));
                    } else if (zze2.equals("Boolean")) {
                        dtVar = new dt(Double.valueOf(zzb(dtVar)));
                    } else if ((zze.equals("String") || zze.equals("Number")) && zze2.equals("Object")) {
                        dtVar = new eb(zzd(dtVar));
                    } else if (!zze.equals("Object")) {
                        return false;
                    } else {
                        if (!zze2.equals("String") && !zze2.equals("Number")) {
                            return false;
                        }
                        dtVar2 = new eb(zzd(dtVar2));
                    }
                }
            }
        }
        return true;
    }

    public static String zzd(dp<?> dpVar) {
        String str;
        int i = 1;
        zzbo.zzaf(dpVar != null);
        if (dpVar == dv.zzbLu) {
            return "undefined";
        }
        if (dpVar == dv.zzbLt) {
            return Constants.NULL_VERSION_ID;
        }
        if (dpVar instanceof ds) {
            return ((ds) dpVar).zzDn().booleanValue() ? "true" : "false";
        }
        if (dpVar instanceof dt) {
            String d = Double.toString(((dt) dpVar).zzDo().doubleValue());
            int indexOf = d.indexOf(TraceFormat.STR_ERROR);
            if (indexOf > 0) {
                int parseInt = Integer.parseInt(d.substring(indexOf + 1, d.length()));
                if (parseInt < 0) {
                    if (parseInt <= -7) {
                        return d.replace(TraceFormat.STR_ERROR, APDataReportManager.ACCOUNTINPUT_PRE);
                    }
                    String replace = d.substring(0, indexOf).replace(".", "");
                    StringBuilder sb = new StringBuilder();
                    sb.append("0.");
                    for (int i2 = parseInt; i2 + 1 < 0; i2++) {
                        sb.append("0");
                    }
                    sb.append(replace);
                    return sb.toString();
                } else if (parseInt >= 21) {
                    return d.replace(TraceFormat.STR_ERROR, "e+");
                } else {
                    String replace2 = d.substring(0, indexOf).replace(".", "");
                    int i3 = parseInt + 1;
                    int length = replace2.length();
                    if (!replace2.startsWith("-")) {
                        i = 0;
                    }
                    int i4 = i3 - (length - i);
                    StringBuilder sb2 = new StringBuilder();
                    if (i4 < 0) {
                        int length2 = i4 + replace2.length();
                        sb2.append(replace2.substring(0, length2));
                        sb2.append(".");
                        sb2.append(replace2.substring(length2, replace2.length()));
                    } else {
                        sb2.append(replace2);
                        while (i4 > 0) {
                            sb2.append("0");
                            i4--;
                        }
                    }
                    return sb2.toString();
                }
            } else if (!d.endsWith(".0")) {
                return d;
            } else {
                String substring = d.substring(0, d.length() - 2);
                return substring.equals("-0") ? "0" : substring;
            }
        } else {
            if (dpVar instanceof du) {
                zzcxo zzDp = ((du) dpVar).zzDp();
                if (zzDp instanceof zzcxn) {
                    return ((zzcxn) zzDp).getName();
                }
            } else if (dpVar instanceof dw) {
                ArrayList arrayList = new ArrayList();
                for (dp dpVar2 : ((dw) dpVar).zzDs()) {
                    if (dpVar2 == dv.zzbLt || dpVar2 == dv.zzbLu) {
                        arrayList.add("");
                    } else {
                        arrayList.add(zzd(dpVar2));
                    }
                }
                return TextUtils.join(",", arrayList);
            } else if (dpVar instanceof dz) {
                return "[object Object]";
            } else {
                if (dpVar instanceof eb) {
                    return ((eb) dpVar).value();
                }
            }
            if (zzf(dpVar)) {
                String valueOf = String.valueOf(dpVar.toString());
                str = new StringBuilder(String.valueOf(valueOf).length() + 41).append("Illegal type given to stringEquivalent: ").append(valueOf).append(".").toString();
            } else {
                str = "Unknown type in stringEquivalent.";
            }
            throw new IllegalArgumentException(str);
        }
    }

    public static boolean zzd(dp<?> dpVar, dp<?> dpVar2) {
        zzbo.zzaf(dpVar != null);
        zzbo.zzaf(dpVar2 != null);
        if (zzf(dpVar)) {
            String valueOf = String.valueOf(dpVar.toString());
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 46).append("Illegal type given to strictEqualityCompare: ").append(valueOf).append(".").toString());
        } else if (zzf(dpVar2)) {
            String valueOf2 = String.valueOf(dpVar2.toString());
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf2).length() + 46).append("Illegal type given to strictEqualityCompare: ").append(valueOf2).append(".").toString());
        } else {
            String zze = zze(dpVar);
            if (!zze.equals(zze(dpVar2))) {
                return false;
            }
            char c = 65535;
            switch (zze.hashCode()) {
                case -1950496919:
                    if (zze.equals("Number")) {
                        c = 2;
                        break;
                    }
                    break;
                case -1808118735:
                    if (zze.equals("String")) {
                        c = 3;
                        break;
                    }
                    break;
                case 2439591:
                    if (zze.equals("Null")) {
                        c = 1;
                        break;
                    }
                    break;
                case 965837104:
                    if (zze.equals("Undefined")) {
                        c = 0;
                        break;
                    }
                    break;
                case 1729365000:
                    if (zze.equals("Boolean")) {
                        c = 4;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                case 1:
                    return true;
                case 2:
                    double doubleValue = ((dt) dpVar).zzDo().doubleValue();
                    double doubleValue2 = ((dt) dpVar2).zzDo().doubleValue();
                    return !Double.isNaN(doubleValue) && !Double.isNaN(doubleValue2) && doubleValue == doubleValue2;
                case 3:
                    return ((eb) dpVar).value().equals(((eb) dpVar2).value());
                case 4:
                    return ((ds) dpVar).zzDn() == ((ds) dpVar2).zzDn();
                default:
                    return dpVar == dpVar2;
            }
        }
    }

    private static String zze(dp<?> dpVar) {
        return dpVar == dv.zzbLu ? "Undefined" : dpVar == dv.zzbLt ? "Null" : dpVar instanceof ds ? "Boolean" : dpVar instanceof dt ? "Number" : dpVar instanceof eb ? "String" : "Object";
    }

    private static boolean zzf(dp<?> dpVar) {
        return (dpVar instanceof ea) || !(!(dpVar instanceof dv) || dpVar == dv.zzbLu || dpVar == dv.zzbLt);
    }
}

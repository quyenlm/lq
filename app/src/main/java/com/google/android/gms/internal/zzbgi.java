package com.google.android.gms.internal;

import com.amazonaws.services.s3.internal.Constants;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.util.zzc;
import com.google.android.gms.common.util.zzo;
import com.google.android.gms.common.util.zzp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class zzbgi {
    protected static <O, I> I zza(zzbgj<I, O> zzbgj, Object obj) {
        return zzbgj.zzaIQ != null ? zzbgj.convertBack(obj) : obj;
    }

    private static void zza(StringBuilder sb, zzbgj zzbgj, Object obj) {
        if (zzbgj.zzaIH == 11) {
            sb.append(((zzbgi) zzbgj.zzaIN.cast(obj)).toString());
        } else if (zzbgj.zzaIH == 7) {
            sb.append("\"");
            sb.append(zzo.zzcK((String) obj));
            sb.append("\"");
        } else {
            sb.append(obj);
        }
    }

    private static void zza(StringBuilder sb, zzbgj zzbgj, ArrayList<Object> arrayList) {
        sb.append("[");
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(",");
            }
            Object obj = arrayList.get(i);
            if (obj != null) {
                zza(sb, zzbgj, obj);
            }
        }
        sb.append("]");
    }

    public String toString() {
        Map<String, zzbgj<?, ?>> zzrL = zzrL();
        StringBuilder sb = new StringBuilder(100);
        for (String next : zzrL.keySet()) {
            zzbgj zzbgj = zzrL.get(next);
            if (zza(zzbgj)) {
                Object zza = zza(zzbgj, zzb(zzbgj));
                if (sb.length() == 0) {
                    sb.append("{");
                } else {
                    sb.append(",");
                }
                sb.append("\"").append(next).append("\":");
                if (zza != null) {
                    switch (zzbgj.zzaIJ) {
                        case 8:
                            sb.append("\"").append(zzc.encode((byte[]) zza)).append("\"");
                            break;
                        case 9:
                            sb.append("\"").append(zzc.zzg((byte[]) zza)).append("\"");
                            break;
                        case 10:
                            zzp.zza(sb, (HashMap) zza);
                            break;
                        default:
                            if (!zzbgj.zzaII) {
                                zza(sb, zzbgj, zza);
                                break;
                            } else {
                                zza(sb, zzbgj, (ArrayList<Object>) (ArrayList) zza);
                                break;
                            }
                    }
                } else {
                    sb.append(Constants.NULL_VERSION_ID);
                }
            }
        }
        if (sb.length() > 0) {
            sb.append("}");
        } else {
            sb.append("{}");
        }
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public boolean zza(zzbgj zzbgj) {
        if (zzbgj.zzaIJ != 11) {
            return zzcI(zzbgj.zzaIL);
        }
        if (zzbgj.zzaIK) {
            String str = zzbgj.zzaIL;
            throw new UnsupportedOperationException("Concrete type arrays not supported");
        }
        String str2 = zzbgj.zzaIL;
        throw new UnsupportedOperationException("Concrete types not supported");
    }

    /* access modifiers changed from: protected */
    public Object zzb(zzbgj zzbgj) {
        String str = zzbgj.zzaIL;
        if (zzbgj.zzaIN == null) {
            return zzcH(zzbgj.zzaIL);
        }
        zzcH(zzbgj.zzaIL);
        zzbo.zza(true, "Concrete field shouldn't be value object: %s", zzbgj.zzaIL);
        boolean z = zzbgj.zzaIK;
        try {
            char upperCase = Character.toUpperCase(str.charAt(0));
            String valueOf = String.valueOf(str.substring(1));
            return getClass().getMethod(new StringBuilder(String.valueOf(valueOf).length() + 4).append("get").append(upperCase).append(valueOf).toString(), new Class[0]).invoke(this, new Object[0]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* access modifiers changed from: protected */
    public abstract Object zzcH(String str);

    /* access modifiers changed from: protected */
    public abstract boolean zzcI(String str);

    public abstract Map<String, zzbgj<?, ?>> zzrL();
}

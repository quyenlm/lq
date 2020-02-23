package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.util.zzc;
import com.google.android.gms.common.util.zzo;
import com.google.android.gms.common.util.zzp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class zzbgt extends zzbgl {
    public static final Parcelable.Creator<zzbgt> CREATOR = new zzbgu();
    private final String mClassName;
    private final zzbgo zzaIP;
    private final Parcel zzaIW;
    private final int zzaIX = 2;
    private int zzaIY;
    private int zzaIZ;
    private final int zzaku;

    zzbgt(int i, Parcel parcel, zzbgo zzbgo) {
        this.zzaku = i;
        this.zzaIW = (Parcel) zzbo.zzu(parcel);
        this.zzaIP = zzbgo;
        if (this.zzaIP == null) {
            this.mClassName = null;
        } else {
            this.mClassName = this.zzaIP.zzrR();
        }
        this.zzaIY = 2;
    }

    private static void zza(StringBuilder sb, int i, Object obj) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                sb.append(obj);
                return;
            case 7:
                sb.append("\"").append(zzo.zzcK(obj.toString())).append("\"");
                return;
            case 8:
                sb.append("\"").append(zzc.encode((byte[]) obj)).append("\"");
                return;
            case 9:
                sb.append("\"").append(zzc.zzg((byte[]) obj));
                sb.append("\"");
                return;
            case 10:
                zzp.zza(sb, (HashMap) obj);
                return;
            case 11:
                throw new IllegalArgumentException("Method does not accept concrete type.");
            default:
                throw new IllegalArgumentException(new StringBuilder(26).append("Unknown type = ").append(i).toString());
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v37, resolved type: double[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v42, resolved type: java.lang.Object[]} */
    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r0v45 */
    /* JADX WARNING: type inference failed for: r0v46 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zza(java.lang.StringBuilder r8, com.google.android.gms.internal.zzbgj<?, ?> r9, android.os.Parcel r10, int r11) {
        /*
            r7 = this;
            r0 = 0
            r2 = 0
            boolean r1 = r9.zzaIK
            if (r1 == 0) goto L_0x00c7
            java.lang.String r1 = "["
            r8.append(r1)
            int r1 = r9.zzaIJ
            switch(r1) {
                case 0: goto L_0x0018;
                case 1: goto L_0x0032;
                case 2: goto L_0x0060;
                case 3: goto L_0x0068;
                case 4: goto L_0x0070;
                case 5: goto L_0x0087;
                case 6: goto L_0x008f;
                case 7: goto L_0x0097;
                case 8: goto L_0x009f;
                case 9: goto L_0x009f;
                case 10: goto L_0x009f;
                case 11: goto L_0x00a7;
                default: goto L_0x0010;
            }
        L_0x0010:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Unknown field type out."
            r0.<init>(r1)
            throw r0
        L_0x0018:
            int[] r0 = com.google.android.gms.common.internal.safeparcel.zzb.zzw(r10, r11)
            int r1 = r0.length
        L_0x001d:
            if (r2 >= r1) goto L_0x003f
            if (r2 == 0) goto L_0x0026
            java.lang.String r3 = ","
            r8.append(r3)
        L_0x0026:
            r3 = r0[r2]
            java.lang.String r3 = java.lang.Integer.toString(r3)
            r8.append(r3)
            int r2 = r2 + 1
            goto L_0x001d
        L_0x0032:
            int r1 = com.google.android.gms.common.internal.safeparcel.zzb.zza(r10, r11)
            int r3 = r10.dataPosition()
            if (r1 != 0) goto L_0x0045
        L_0x003c:
            com.google.android.gms.common.util.zzb.zza((java.lang.StringBuilder) r8, (T[]) r0)
        L_0x003f:
            java.lang.String r0 = "]"
            r8.append(r0)
        L_0x0044:
            return
        L_0x0045:
            int r4 = r10.readInt()
            java.math.BigInteger[] r0 = new java.math.BigInteger[r4]
        L_0x004b:
            if (r2 >= r4) goto L_0x005b
            java.math.BigInteger r5 = new java.math.BigInteger
            byte[] r6 = r10.createByteArray()
            r5.<init>(r6)
            r0[r2] = r5
            int r2 = r2 + 1
            goto L_0x004b
        L_0x005b:
            int r1 = r1 + r3
            r10.setDataPosition(r1)
            goto L_0x003c
        L_0x0060:
            long[] r0 = com.google.android.gms.common.internal.safeparcel.zzb.zzx(r10, r11)
            com.google.android.gms.common.util.zzb.zza((java.lang.StringBuilder) r8, (long[]) r0)
            goto L_0x003f
        L_0x0068:
            float[] r0 = com.google.android.gms.common.internal.safeparcel.zzb.zzy(r10, r11)
            com.google.android.gms.common.util.zzb.zza((java.lang.StringBuilder) r8, (float[]) r0)
            goto L_0x003f
        L_0x0070:
            int r1 = com.google.android.gms.common.internal.safeparcel.zzb.zza(r10, r11)
            int r2 = r10.dataPosition()
            if (r1 != 0) goto L_0x007e
        L_0x007a:
            com.google.android.gms.common.util.zzb.zza((java.lang.StringBuilder) r8, (double[]) r0)
            goto L_0x003f
        L_0x007e:
            double[] r0 = r10.createDoubleArray()
            int r1 = r1 + r2
            r10.setDataPosition(r1)
            goto L_0x007a
        L_0x0087:
            java.math.BigDecimal[] r0 = com.google.android.gms.common.internal.safeparcel.zzb.zzz(r10, r11)
            com.google.android.gms.common.util.zzb.zza((java.lang.StringBuilder) r8, (T[]) r0)
            goto L_0x003f
        L_0x008f:
            boolean[] r0 = com.google.android.gms.common.internal.safeparcel.zzb.zzv(r10, r11)
            com.google.android.gms.common.util.zzb.zza((java.lang.StringBuilder) r8, (boolean[]) r0)
            goto L_0x003f
        L_0x0097:
            java.lang.String[] r0 = com.google.android.gms.common.internal.safeparcel.zzb.zzA(r10, r11)
            com.google.android.gms.common.util.zzb.zza((java.lang.StringBuilder) r8, (java.lang.String[]) r0)
            goto L_0x003f
        L_0x009f:
            java.lang.UnsupportedOperationException r0 = new java.lang.UnsupportedOperationException
            java.lang.String r1 = "List of type BASE64, BASE64_URL_SAFE, or STRING_MAP is not supported"
            r0.<init>(r1)
            throw r0
        L_0x00a7:
            android.os.Parcel[] r1 = com.google.android.gms.common.internal.safeparcel.zzb.zzE(r10, r11)
            int r3 = r1.length
            r0 = r2
        L_0x00ad:
            if (r0 >= r3) goto L_0x003f
            if (r0 <= 0) goto L_0x00b6
            java.lang.String r4 = ","
            r8.append(r4)
        L_0x00b6:
            r4 = r1[r0]
            r4.setDataPosition(r2)
            java.util.Map r4 = r9.zzrP()
            r5 = r1[r0]
            r7.zza((java.lang.StringBuilder) r8, (java.util.Map<java.lang.String, com.google.android.gms.internal.zzbgj<?, ?>>) r4, (android.os.Parcel) r5)
            int r0 = r0 + 1
            goto L_0x00ad
        L_0x00c7:
            int r0 = r9.zzaIJ
            switch(r0) {
                case 0: goto L_0x00d4;
                case 1: goto L_0x00dd;
                case 2: goto L_0x00e6;
                case 3: goto L_0x00ef;
                case 4: goto L_0x00f8;
                case 5: goto L_0x0101;
                case 6: goto L_0x010a;
                case 7: goto L_0x0113;
                case 8: goto L_0x012c;
                case 9: goto L_0x0145;
                case 10: goto L_0x015d;
                case 11: goto L_0x01ba;
                default: goto L_0x00cc;
            }
        L_0x00cc:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Unknown field type out"
            r0.<init>(r1)
            throw r0
        L_0x00d4:
            int r0 = com.google.android.gms.common.internal.safeparcel.zzb.zzg(r10, r11)
            r8.append(r0)
            goto L_0x0044
        L_0x00dd:
            java.math.BigInteger r0 = com.google.android.gms.common.internal.safeparcel.zzb.zzk(r10, r11)
            r8.append(r0)
            goto L_0x0044
        L_0x00e6:
            long r0 = com.google.android.gms.common.internal.safeparcel.zzb.zzi(r10, r11)
            r8.append(r0)
            goto L_0x0044
        L_0x00ef:
            float r0 = com.google.android.gms.common.internal.safeparcel.zzb.zzl(r10, r11)
            r8.append(r0)
            goto L_0x0044
        L_0x00f8:
            double r0 = com.google.android.gms.common.internal.safeparcel.zzb.zzn(r10, r11)
            r8.append(r0)
            goto L_0x0044
        L_0x0101:
            java.math.BigDecimal r0 = com.google.android.gms.common.internal.safeparcel.zzb.zzp(r10, r11)
            r8.append(r0)
            goto L_0x0044
        L_0x010a:
            boolean r0 = com.google.android.gms.common.internal.safeparcel.zzb.zzc(r10, r11)
            r8.append(r0)
            goto L_0x0044
        L_0x0113:
            java.lang.String r0 = com.google.android.gms.common.internal.safeparcel.zzb.zzq(r10, r11)
            java.lang.String r1 = "\""
            java.lang.StringBuilder r1 = r8.append(r1)
            java.lang.String r0 = com.google.android.gms.common.util.zzo.zzcK(r0)
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r1 = "\""
            r0.append(r1)
            goto L_0x0044
        L_0x012c:
            byte[] r0 = com.google.android.gms.common.internal.safeparcel.zzb.zzt(r10, r11)
            java.lang.String r1 = "\""
            java.lang.StringBuilder r1 = r8.append(r1)
            java.lang.String r0 = com.google.android.gms.common.util.zzc.encode(r0)
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r1 = "\""
            r0.append(r1)
            goto L_0x0044
        L_0x0145:
            byte[] r0 = com.google.android.gms.common.internal.safeparcel.zzb.zzt(r10, r11)
            java.lang.String r1 = "\""
            java.lang.StringBuilder r1 = r8.append(r1)
            java.lang.String r0 = com.google.android.gms.common.util.zzc.zzg(r0)
            r1.append(r0)
            java.lang.String r0 = "\""
            r8.append(r0)
            goto L_0x0044
        L_0x015d:
            android.os.Bundle r3 = com.google.android.gms.common.internal.safeparcel.zzb.zzs(r10, r11)
            java.util.Set r1 = r3.keySet()
            r1.size()
            java.lang.String r0 = "{"
            r8.append(r0)
            r0 = 1
            java.util.Iterator r4 = r1.iterator()
            r1 = r0
        L_0x0173:
            boolean r0 = r4.hasNext()
            if (r0 == 0) goto L_0x01b3
            java.lang.Object r0 = r4.next()
            java.lang.String r0 = (java.lang.String) r0
            if (r1 != 0) goto L_0x0186
            java.lang.String r1 = ","
            r8.append(r1)
        L_0x0186:
            java.lang.String r1 = "\""
            java.lang.StringBuilder r1 = r8.append(r1)
            java.lang.StringBuilder r1 = r1.append(r0)
            java.lang.String r5 = "\""
            r1.append(r5)
            java.lang.String r1 = ":"
            r8.append(r1)
            java.lang.String r1 = "\""
            java.lang.StringBuilder r1 = r8.append(r1)
            java.lang.String r0 = r3.getString(r0)
            java.lang.String r0 = com.google.android.gms.common.util.zzo.zzcK(r0)
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r1 = "\""
            r0.append(r1)
            r1 = r2
            goto L_0x0173
        L_0x01b3:
            java.lang.String r0 = "}"
            r8.append(r0)
            goto L_0x0044
        L_0x01ba:
            android.os.Parcel r0 = com.google.android.gms.common.internal.safeparcel.zzb.zzD(r10, r11)
            r0.setDataPosition(r2)
            java.util.Map r1 = r9.zzrP()
            r7.zza((java.lang.StringBuilder) r8, (java.util.Map<java.lang.String, com.google.android.gms.internal.zzbgj<?, ?>>) r1, (android.os.Parcel) r0)
            goto L_0x0044
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzbgt.zza(java.lang.StringBuilder, com.google.android.gms.internal.zzbgj, android.os.Parcel, int):void");
    }

    private final void zza(StringBuilder sb, Map<String, zzbgj<?, ?>> map, Parcel parcel) {
        SparseArray sparseArray = new SparseArray();
        for (Map.Entry next : map.entrySet()) {
            sparseArray.put(((zzbgj) next.getValue()).zzaIM, next);
        }
        sb.append('{');
        int zzd = zzb.zzd(parcel);
        boolean z = false;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            Map.Entry entry = (Map.Entry) sparseArray.get(65535 & readInt);
            if (entry != null) {
                if (z) {
                    sb.append(",");
                }
                zzbgj zzbgj = (zzbgj) entry.getValue();
                sb.append("\"").append((String) entry.getKey()).append("\":");
                if (zzbgj.zzrO()) {
                    switch (zzbgj.zzaIJ) {
                        case 0:
                            zzb(sb, zzbgj, zza(zzbgj, Integer.valueOf(zzb.zzg(parcel, readInt))));
                            break;
                        case 1:
                            zzb(sb, zzbgj, zza(zzbgj, zzb.zzk(parcel, readInt)));
                            break;
                        case 2:
                            zzb(sb, zzbgj, zza(zzbgj, Long.valueOf(zzb.zzi(parcel, readInt))));
                            break;
                        case 3:
                            zzb(sb, zzbgj, zza(zzbgj, Float.valueOf(zzb.zzl(parcel, readInt))));
                            break;
                        case 4:
                            zzb(sb, zzbgj, zza(zzbgj, Double.valueOf(zzb.zzn(parcel, readInt))));
                            break;
                        case 5:
                            zzb(sb, zzbgj, zza(zzbgj, zzb.zzp(parcel, readInt)));
                            break;
                        case 6:
                            zzb(sb, zzbgj, zza(zzbgj, Boolean.valueOf(zzb.zzc(parcel, readInt))));
                            break;
                        case 7:
                            zzb(sb, zzbgj, zza(zzbgj, zzb.zzq(parcel, readInt)));
                            break;
                        case 8:
                        case 9:
                            zzb(sb, zzbgj, zza(zzbgj, zzb.zzt(parcel, readInt)));
                            break;
                        case 10:
                            zzb(sb, zzbgj, zza(zzbgj, zzo(zzb.zzs(parcel, readInt))));
                            break;
                        case 11:
                            throw new IllegalArgumentException("Method does not accept concrete type.");
                        default:
                            throw new IllegalArgumentException(new StringBuilder(36).append("Unknown field out type = ").append(zzbgj.zzaIJ).toString());
                    }
                } else {
                    zza(sb, zzbgj, parcel, readInt);
                }
                z = true;
            }
        }
        if (parcel.dataPosition() != zzd) {
            throw new com.google.android.gms.common.internal.safeparcel.zzc(new StringBuilder(37).append("Overread allowed size end=").append(zzd).toString(), parcel);
        }
        sb.append('}');
    }

    private final void zzb(StringBuilder sb, zzbgj<?, ?> zzbgj, Object obj) {
        if (zzbgj.zzaII) {
            ArrayList arrayList = (ArrayList) obj;
            sb.append("[");
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (i != 0) {
                    sb.append(",");
                }
                zza(sb, zzbgj.zzaIH, arrayList.get(i));
            }
            sb.append("]");
            return;
        }
        zza(sb, zzbgj.zzaIH, obj);
    }

    private static HashMap<String, String> zzo(Bundle bundle) {
        HashMap<String, String> hashMap = new HashMap<>();
        for (String str : bundle.keySet()) {
            hashMap.put(str, bundle.getString(str));
        }
        return hashMap;
    }

    private Parcel zzrT() {
        switch (this.zzaIY) {
            case 0:
                this.zzaIZ = zzd.zze(this.zzaIW);
                break;
            case 1:
                break;
        }
        zzd.zzI(this.zzaIW, this.zzaIZ);
        this.zzaIY = 2;
        return this.zzaIW;
    }

    public String toString() {
        zzbo.zzb(this.zzaIP, (Object) "Cannot convert to JSON on client side.");
        Parcel zzrT = zzrT();
        zzrT.setDataPosition(0);
        StringBuilder sb = new StringBuilder(100);
        zza(sb, this.zzaIP.zzcJ(this.mClassName), zzrT);
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzbgo zzbgo;
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzaku);
        zzd.zza(parcel, 2, zzrT(), false);
        switch (this.zzaIX) {
            case 0:
                zzbgo = null;
                break;
            case 1:
                zzbgo = this.zzaIP;
                break;
            case 2:
                zzbgo = this.zzaIP;
                break;
            default:
                throw new IllegalStateException(new StringBuilder(34).append("Invalid creation type: ").append(this.zzaIX).toString());
        }
        zzd.zza(parcel, 3, (Parcelable) zzbgo, i, false);
        zzd.zzI(parcel, zze);
    }

    public final Object zzcH(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    public final boolean zzcI(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    public final Map<String, zzbgj<?, ?>> zzrL() {
        if (this.zzaIP == null) {
            return null;
        }
        return this.zzaIP.zzcJ(this.mClassName);
    }
}

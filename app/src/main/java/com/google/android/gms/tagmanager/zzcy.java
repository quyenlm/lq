package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbf;
import com.google.android.gms.internal.zzbg;
import com.google.android.gms.internal.zzbr;
import com.tencent.imsdk.framework.request.HttpRequest;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

final class zzcy extends zzbr {
    private static final String ID = zzbf.JOINER.toString();
    private static final String zzbEH = zzbg.ARG0.toString();
    private static final String zzbEZ = zzbg.ITEM_SEPARATOR.toString();
    private static final String zzbFa = zzbg.KEY_VALUE_SEPARATOR.toString();
    private static final String zzbFb = zzbg.ESCAPE.toString();

    public zzcy() {
        super(ID, zzbEH);
    }

    /* JADX WARNING: type inference failed for: r7v0, types: [int, java.lang.Integer] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String zza(java.lang.String r6, java.lang.Integer r7, java.util.Set<java.lang.Character> r8) {
        /*
            int[] r0 = com.google.android.gms.tagmanager.zzcz.zzbFc
            int r1 = r7 + -1
            r0 = r0[r1]
            switch(r0) {
                case 1: goto L_0x000a;
                case 2: goto L_0x0016;
                default: goto L_0x0009;
            }
        L_0x0009:
            return r6
        L_0x000a:
            java.lang.String r6 = com.google.android.gms.tagmanager.zzgo.zzfC(r6)     // Catch:{ UnsupportedEncodingException -> 0x000f }
            goto L_0x0009
        L_0x000f:
            r0 = move-exception
            java.lang.String r1 = "Joiner: unsupported encoding"
            com.google.android.gms.tagmanager.zzdj.zzb(r1, r0)
            goto L_0x0009
        L_0x0016:
            java.lang.String r0 = "\\"
            java.lang.String r1 = "\\\\"
            java.lang.String r0 = r6.replace(r0, r1)
            java.util.Iterator r2 = r8.iterator()
            r1 = r0
        L_0x0023:
            boolean r0 = r2.hasNext()
            if (r0 == 0) goto L_0x004f
            java.lang.Object r0 = r2.next()
            java.lang.Character r0 = (java.lang.Character) r0
            java.lang.String r3 = r0.toString()
            java.lang.String r4 = "\\"
            java.lang.String r0 = java.lang.String.valueOf(r3)
            int r5 = r0.length()
            if (r5 == 0) goto L_0x0049
            java.lang.String r0 = r4.concat(r0)
        L_0x0043:
            java.lang.String r0 = r1.replace(r3, r0)
            r1 = r0
            goto L_0x0023
        L_0x0049:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r4)
            goto L_0x0043
        L_0x004f:
            r6 = r1
            goto L_0x0009
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzcy.zza(java.lang.String, int, java.util.Set):java.lang.String");
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [int, java.lang.Integer] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void zza(java.lang.StringBuilder r1, java.lang.String r2, java.lang.Integer r3, java.util.Set<java.lang.Character> r4) {
        /*
            java.lang.String r0 = zza(r2, r3, r4)
            r1.append(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzcy.zza(java.lang.StringBuilder, java.lang.String, int, java.util.Set):void");
    }

    private static void zza(Set<Character> set, String str) {
        for (int i = 0; i < str.length(); i++) {
            set.add(Character.valueOf(str.charAt(i)));
        }
    }

    public final boolean zzAE() {
        return true;
    }

    public final zzbr zzo(Map<String, zzbr> map) {
        HashSet hashSet;
        zzbr zzbr = map.get(zzbEH);
        if (zzbr == null) {
            return zzgk.zzCh();
        }
        zzbr zzbr2 = map.get(zzbEZ);
        String zzb = zzbr2 != null ? zzgk.zzb(zzbr2) : "";
        zzbr zzbr3 = map.get(zzbFa);
        String zzb2 = zzbr3 != null ? zzgk.zzb(zzbr3) : HttpRequest.HTTP_REQ_ENTITY_MERGE;
        int i = zzda.zzbFd;
        zzbr zzbr4 = map.get(zzbFb);
        if (zzbr4 != null) {
            String zzb3 = zzgk.zzb(zzbr4);
            if ("url".equals(zzb3)) {
                i = zzda.zzbFe;
                hashSet = null;
            } else if ("backslash".equals(zzb3)) {
                int i2 = zzda.zzbFf;
                hashSet = new HashSet();
                zza(hashSet, zzb);
                zza(hashSet, zzb2);
                hashSet.remove('\\');
                i = i2;
            } else {
                String valueOf = String.valueOf(zzb3);
                zzdj.e(valueOf.length() != 0 ? "Joiner: unsupported escape type: ".concat(valueOf) : new String("Joiner: unsupported escape type: "));
                return zzgk.zzCh();
            }
        } else {
            hashSet = null;
        }
        StringBuilder sb = new StringBuilder();
        switch (zzbr.type) {
            case 2:
                boolean z = true;
                zzbr[] zzbrArr = zzbr.zzlE;
                int length = zzbrArr.length;
                int i3 = 0;
                while (i3 < length) {
                    zzbr zzbr5 = zzbrArr[i3];
                    if (!z) {
                        sb.append(zzb);
                    }
                    zza(sb, zzgk.zzb(zzbr5), i, hashSet);
                    i3++;
                    z = false;
                }
                break;
            case 3:
                for (int i4 = 0; i4 < zzbr.zzlF.length; i4++) {
                    if (i4 > 0) {
                        sb.append(zzb);
                    }
                    String zzb4 = zzgk.zzb(zzbr.zzlF[i4]);
                    String zzb5 = zzgk.zzb(zzbr.zzlG[i4]);
                    zza(sb, zzb4, i, hashSet);
                    sb.append(zzb2);
                    zza(sb, zzb5, i, hashSet);
                }
                break;
            default:
                zza(sb, zzgk.zzb(zzbr), i, hashSet);
                break;
        }
        return zzgk.zzI(sb.toString());
    }
}

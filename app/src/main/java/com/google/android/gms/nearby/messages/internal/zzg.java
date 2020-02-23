package com.google.android.gms.nearby.messages.internal;

import com.google.android.gms.common.internal.zzbo;

public final class zzg extends zzc {
    public zzg(String str) {
        this(zzeE(str));
    }

    public zzg(String str, String str2) {
        this(zzeE(str), zzeE(str2));
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public zzg(byte[] bArr) {
        super(bArr);
        zzbo.zzb(bArr.length == 10 || bArr.length == 16, (Object) "Bytes must be a namespace (10 bytes), or a namespace plus instance (16 bytes).");
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private zzg(byte[] r8, byte[] r9) {
        /*
            r7 = this;
            r1 = 1
            r2 = 0
            r0 = 2
            byte[][] r3 = new byte[r0][]
            int r0 = r8.length
            r4 = 10
            if (r0 != r4) goto L_0x005a
            r0 = r1
        L_0x000b:
            int r4 = r8.length
            r5 = 62
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>(r5)
            java.lang.String r5 = "Namespace length("
            java.lang.StringBuilder r5 = r6.append(r5)
            java.lang.StringBuilder r4 = r5.append(r4)
            java.lang.String r5 = " bytes) must be 10 bytes."
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            com.google.android.gms.common.internal.zzbo.zzb((boolean) r0, (java.lang.Object) r4)
            r3[r2] = r8
            int r0 = r9.length
            r4 = 6
            if (r0 != r4) goto L_0x0031
            r2 = r1
        L_0x0031:
            int r0 = r9.length
            r4 = 61
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r4)
            java.lang.String r4 = "Instance length("
            java.lang.StringBuilder r4 = r5.append(r4)
            java.lang.StringBuilder r0 = r4.append(r0)
            java.lang.String r4 = " bytes) must be 6 bytes."
            java.lang.StringBuilder r0 = r0.append(r4)
            java.lang.String r0 = r0.toString()
            com.google.android.gms.common.internal.zzbo.zzb((boolean) r2, (java.lang.Object) r0)
            r3[r1] = r9
            byte[] r0 = com.google.android.gms.common.util.zzb.zza((byte[][]) r3)
            r7.<init>((byte[]) r0)
            return
        L_0x005a:
            r0 = r2
            goto L_0x000b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.nearby.messages.internal.zzg.<init>(byte[], byte[]):void");
    }

    public final String toString() {
        String valueOf = String.valueOf(getHex());
        return new StringBuilder(String.valueOf(valueOf).length() + 26).append("EddystoneUidPrefix{bytes=").append(valueOf).append("}").toString();
    }
}

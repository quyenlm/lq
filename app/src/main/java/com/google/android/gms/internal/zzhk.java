package com.google.android.gms.internal;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;

@zzzn
public final class zzhk extends zzhf {
    private MessageDigest zzzd;

    public final byte[] zzy(String str) {
        byte[] bArr;
        byte[] bArr2;
        String[] split = str.split(" ");
        if (split.length == 1) {
            int zzA = zzhj.zzA(split[0]);
            ByteBuffer allocate = ByteBuffer.allocate(4);
            allocate.order(ByteOrder.LITTLE_ENDIAN);
            allocate.putInt(zzA);
            bArr = allocate.array();
        } else if (split.length < 5) {
            byte[] bArr3 = new byte[(split.length << 1)];
            for (int i = 0; i < split.length; i++) {
                int zzA2 = zzhj.zzA(split[i]);
                int i2 = (zzA2 >> 16) ^ (65535 & zzA2);
                byte[] bArr4 = {(byte) i2, (byte) (i2 >> 8)};
                bArr3[i << 1] = bArr4[0];
                bArr3[(i << 1) + 1] = bArr4[1];
            }
            bArr = bArr3;
        } else {
            bArr = new byte[split.length];
            for (int i3 = 0; i3 < split.length; i3++) {
                int zzA3 = zzhj.zzA(split[i3]);
                bArr[i3] = (byte) ((zzA3 >> 24) ^ (((zzA3 & 255) ^ ((zzA3 >> 8) & 255)) ^ ((zzA3 >> 16) & 255)));
            }
        }
        this.zzzd = zzcW();
        synchronized (this.mLock) {
            if (this.zzzd == null) {
                bArr2 = new byte[0];
            } else {
                this.zzzd.reset();
                this.zzzd.update(bArr);
                byte[] digest = this.zzzd.digest();
                bArr2 = new byte[(digest.length > 4 ? 4 : digest.length)];
                System.arraycopy(digest, 0, bArr2, 0, bArr2.length);
            }
        }
        return bArr2;
    }
}

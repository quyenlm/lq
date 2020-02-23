package com.google.android.gms.nearby.messages.internal;

import com.google.android.gms.common.internal.zzbo;
import java.nio.ByteBuffer;
import java.util.UUID;

public final class zzl extends zzc {
    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public zzl(java.util.UUID r5, @android.support.annotation.Nullable java.lang.Short r6, @android.support.annotation.Nullable java.lang.Short r7) {
        /*
            r4 = this;
            r1 = 2
            r0 = 0
            if (r6 != 0) goto L_0x0037
            r2 = r0
        L_0x0005:
            int r2 = r2 + 16
            if (r7 != 0) goto L_0x0039
        L_0x0009:
            int r0 = r0 + r2
            java.nio.ByteBuffer r0 = java.nio.ByteBuffer.allocate(r0)
            long r2 = r5.getMostSignificantBits()
            java.nio.ByteBuffer r1 = r0.putLong(r2)
            long r2 = r5.getLeastSignificantBits()
            r1.putLong(r2)
            if (r6 == 0) goto L_0x0026
            short r1 = r6.shortValue()
            r0.putShort(r1)
        L_0x0026:
            if (r7 == 0) goto L_0x002f
            short r1 = r7.shortValue()
            r0.putShort(r1)
        L_0x002f:
            byte[] r0 = r0.array()
            r4.<init>(r0)
            return
        L_0x0037:
            r2 = r1
            goto L_0x0005
        L_0x0039:
            r0 = r1
            goto L_0x0009
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.nearby.messages.internal.zzl.<init>(java.util.UUID, java.lang.Short, java.lang.Short):void");
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public zzl(byte[] bArr) {
        super(bArr);
        zzbo.zzb(bArr.length == 16 || bArr.length == 18 || bArr.length == 20, (Object) "Prefix must be a UUID, a UUID and a major, or a UUID, a major, and a minor.");
    }

    public final UUID getProximityUuid() {
        ByteBuffer wrap = ByteBuffer.wrap(getBytes());
        return new UUID(wrap.getLong(), wrap.getLong());
    }

    public final String toString() {
        String valueOf = String.valueOf(getProximityUuid());
        String valueOf2 = String.valueOf(zzzV());
        String valueOf3 = String.valueOf(zzzW());
        return new StringBuilder(String.valueOf(valueOf).length() + 47 + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length()).append("IBeaconIdPrefix{proximityUuid=").append(valueOf).append(", major=").append(valueOf2).append(", minor=").append(valueOf3).append("}").toString();
    }

    public final Short zzzV() {
        byte[] bytes = getBytes();
        if (bytes.length >= 18) {
            return Short.valueOf(ByteBuffer.wrap(bytes).getShort(16));
        }
        return null;
    }

    public final Short zzzW() {
        byte[] bytes = getBytes();
        if (bytes.length == 20) {
            return Short.valueOf(ByteBuffer.wrap(bytes).getShort(18));
        }
        return null;
    }
}

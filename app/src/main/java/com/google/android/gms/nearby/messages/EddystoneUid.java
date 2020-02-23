package com.google.android.gms.nearby.messages;

import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.nearby.messages.internal.zzc;
import com.google.android.gms.nearby.messages.internal.zzg;
import java.util.Arrays;

public class EddystoneUid {
    public static final int INSTANCE_LENGTH = 6;
    public static final int LENGTH = 16;
    public static final int NAMESPACE_LENGTH = 10;
    private final zzg zzbxR;

    public EddystoneUid(String str) {
        this(zzc.zzeE(str));
    }

    public EddystoneUid(String str, String str2) {
        this.zzbxR = new zzg(str, str2);
    }

    private EddystoneUid(byte[] bArr) {
        zzbo.zzb(bArr.length == 16, (Object) "Bytes must be a namespace plus instance (16 bytes).");
        this.zzbxR = new zzg(bArr);
    }

    public static EddystoneUid from(Message message) {
        boolean zzeD = message.zzeD(Message.MESSAGE_TYPE_EDDYSTONE_UID);
        String valueOf = String.valueOf(message.getType());
        zzbo.zzb(zzeD, (Object) new StringBuilder(String.valueOf(valueOf).length() + 58).append("Message type '").append(valueOf).append("' is not Message.MESSAGE_TYPE_EDDYSTONE_UID.").toString());
        return new EddystoneUid(message.getContent());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EddystoneUid)) {
            return false;
        }
        return zzbe.equal(this.zzbxR, ((EddystoneUid) obj).zzbxR);
    }

    public String getHex() {
        return this.zzbxR.getHex();
    }

    public String getInstance() {
        byte[] bytes = this.zzbxR.getBytes();
        if (bytes.length < 16) {
            return null;
        }
        return zzc.zzo(Arrays.copyOfRange(bytes, 10, 16));
    }

    public String getNamespace() {
        return zzc.zzo(Arrays.copyOfRange(this.zzbxR.getBytes(), 0, 10));
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzbxR});
    }

    public String toString() {
        String valueOf = String.valueOf(getHex());
        return new StringBuilder(String.valueOf(valueOf).length() + 17).append("EddystoneUid{id=").append(valueOf).append("}").toString();
    }
}

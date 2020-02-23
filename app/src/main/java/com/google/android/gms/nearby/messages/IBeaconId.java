package com.google.android.gms.nearby.messages;

import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.nearby.messages.internal.zzl;
import java.util.Arrays;
import java.util.UUID;

public class IBeaconId {
    public static final int LENGTH = 20;
    private final zzl zzbxS;

    public IBeaconId(UUID uuid, short s, short s2) {
        this.zzbxS = new zzl(uuid, Short.valueOf(s), Short.valueOf(s2));
    }

    private IBeaconId(byte[] bArr) {
        zzbo.zzb(bArr.length == 20, (Object) "iBeacon ID must be a UUID, a major, and a minor (20 total bytes).");
        this.zzbxS = new zzl(bArr);
    }

    public static IBeaconId from(Message message) {
        boolean zzeD = message.zzeD(Message.MESSAGE_TYPE_I_BEACON_ID);
        String valueOf = String.valueOf(message.getType());
        zzbo.zzb(zzeD, (Object) new StringBuilder(String.valueOf(valueOf).length() + 55).append("Message type '").append(valueOf).append("' is not Message.MESSAGE_TYPE_I_BEACON_ID").toString());
        return new IBeaconId(message.getContent());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IBeaconId)) {
            return false;
        }
        return zzbe.equal(this.zzbxS, ((IBeaconId) obj).zzbxS);
    }

    public short getMajor() {
        return this.zzbxS.zzzV().shortValue();
    }

    public short getMinor() {
        return this.zzbxS.zzzW().shortValue();
    }

    public UUID getProximityUuid() {
        return this.zzbxS.getProximityUuid();
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzbxS});
    }

    public String toString() {
        String valueOf = String.valueOf(getProximityUuid());
        short major = getMajor();
        return new StringBuilder(String.valueOf(valueOf).length() + 53).append("IBeaconId{proximityUuid=").append(valueOf).append(", major=").append(major).append(", minor=").append(getMinor()).append("}").toString();
    }
}

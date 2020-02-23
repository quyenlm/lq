package com.google.android.gms.nearby.messages;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.zzcph;
import com.google.android.gms.internal.zzcpl;
import com.google.android.gms.nearby.messages.internal.zzad;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MessageFilter extends zza {
    public static final Parcelable.Creator<MessageFilter> CREATOR = new zzc();
    public static final MessageFilter INCLUDE_ALL_MY_TYPES = new Builder().includeAllMyTypes().build();
    private int zzaku;
    private final List<zzad> zzbxX;
    private final List<zzcpl> zzbxY;
    private final boolean zzbxZ;
    private final List<zzcph> zzbya;
    private final int zzbyb;

    public static final class Builder {
        private final List<zzcpl> zzbxY = new ArrayList();
        private boolean zzbxZ;
        private int zzbyb = 0;
        private final Set<zzad> zzbyc = new HashSet();
        private final Set<zzcph> zzbyd = new HashSet();

        private final Builder zzS(String str, String str2) {
            this.zzbyc.add(new zzad(str, str2));
            return this;
        }

        public final MessageFilter build() {
            zzbo.zza(this.zzbxZ || !this.zzbyc.isEmpty(), (Object) "At least one of the include methods must be called.");
            return new MessageFilter((List) new ArrayList(this.zzbyc), (List) this.zzbxY, this.zzbxZ, (List) new ArrayList(this.zzbyd), this.zzbyb);
        }

        public final Builder includeAllMyTypes() {
            this.zzbxZ = true;
            return this;
        }

        public final Builder includeAudioBytes(int i) {
            boolean z = true;
            zzbo.zzb(this.zzbyb == 0, (Object) "includeAudioBytes() can only be called once per MessageFilter instance.");
            zzbo.zzb(i > 0, (Object) new StringBuilder(44).append("Invalid value for numAudioBytes: ").append(i).toString());
            if (i > 10) {
                z = false;
            }
            zzbo.zzb(z, (Object) "numAudioBytes is capped by AudioBytes.MAX_SIZE = 10");
            zzS(Message.MESSAGE_NAMESPACE_RESERVED, Message.MESSAGE_TYPE_AUDIO_BYTES);
            this.zzbyb = i;
            return this;
        }

        public final Builder includeEddystoneUids(String str, @Nullable String str2) {
            zzS(Message.MESSAGE_NAMESPACE_RESERVED, Message.MESSAGE_TYPE_EDDYSTONE_UID);
            this.zzbxY.add(zzcpl.zzT(str, str2));
            return this;
        }

        public final Builder includeFilter(MessageFilter messageFilter) {
            this.zzbyc.addAll(messageFilter.zzzQ());
            this.zzbxY.addAll(messageFilter.zzzS());
            this.zzbyd.addAll(messageFilter.zzzT());
            this.zzbxZ |= messageFilter.zzzR();
            return this;
        }

        public final Builder includeIBeaconIds(UUID uuid, @Nullable Short sh, @Nullable Short sh2) {
            zzS(Message.MESSAGE_NAMESPACE_RESERVED, Message.MESSAGE_TYPE_I_BEACON_ID);
            this.zzbxY.add(zzcpl.zza(uuid, sh, sh2));
            return this;
        }

        public final Builder includeNamespacedType(String str, String str2) {
            zzbo.zzb(str != null && !str.isEmpty() && !str.contains("*"), "namespace(%s) cannot be null, empty or contain (*).", str);
            zzbo.zzb(str2 != null && !str2.contains("*"), "type(%s) cannot be null or contain (*).", str2);
            return zzS(str, str2);
        }
    }

    MessageFilter(int i, List<zzad> list, List<zzcpl> list2, boolean z, List<zzcph> list3, int i2) {
        this.zzaku = i;
        this.zzbxX = Collections.unmodifiableList((List) zzbo.zzu(list));
        this.zzbxZ = z;
        this.zzbxY = Collections.unmodifiableList(list2 == null ? Collections.emptyList() : list2);
        this.zzbya = Collections.unmodifiableList(list3 == null ? Collections.emptyList() : list3);
        this.zzbyb = i2;
    }

    private MessageFilter(List<zzad> list, List<zzcpl> list2, boolean z, List<zzcph> list3, int i) {
        this(2, list, list2, z, list3, i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MessageFilter)) {
            return false;
        }
        MessageFilter messageFilter = (MessageFilter) obj;
        return this.zzbxZ == messageFilter.zzbxZ && zzbe.equal(this.zzbxX, messageFilter.zzbxX) && zzbe.equal(this.zzbxY, messageFilter.zzbxY) && zzbe.equal(this.zzbya, messageFilter.zzbya);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzbxX, this.zzbxY, Boolean.valueOf(this.zzbxZ), this.zzbya});
    }

    public String toString() {
        boolean z = this.zzbxZ;
        String valueOf = String.valueOf(this.zzbxX);
        return new StringBuilder(String.valueOf(valueOf).length() + 53).append("MessageFilter{includeAllMyTypes=").append(z).append(", messageTypes=").append(valueOf).append("}").toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzbxX, false);
        zzd.zzc(parcel, 2, this.zzbxY, false);
        zzd.zza(parcel, 3, this.zzbxZ);
        zzd.zzc(parcel, 4, this.zzbya, false);
        zzd.zzc(parcel, 5, this.zzbyb);
        zzd.zzc(parcel, 1000, this.zzaku);
        zzd.zzI(parcel, zze);
    }

    public final List<zzad> zzzQ() {
        return this.zzbxX;
    }

    public final boolean zzzR() {
        return this.zzbxZ;
    }

    /* access modifiers changed from: package-private */
    public final List<zzcpl> zzzS() {
        return this.zzbxY;
    }

    public final List<zzcph> zzzT() {
        return this.zzbya;
    }
}

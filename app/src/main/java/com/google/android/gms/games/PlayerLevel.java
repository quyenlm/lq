package com.google.android.gms.games;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.games.internal.zzc;
import java.util.Arrays;

public final class PlayerLevel extends zzc {
    public static final Parcelable.Creator<PlayerLevel> CREATOR = new zzh();
    private final int zzaYS;
    private final long zzaYT;
    private final long zzaYU;

    public PlayerLevel(int i, long j, long j2) {
        boolean z = true;
        zzbo.zza(j >= 0, (Object) "Min XP must be positive!");
        zzbo.zza(j2 <= j ? false : z, (Object) "Max XP must be more than min XP!");
        this.zzaYS = i;
        this.zzaYT = j;
        this.zzaYU = j2;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof PlayerLevel)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        PlayerLevel playerLevel = (PlayerLevel) obj;
        return zzbe.equal(Integer.valueOf(playerLevel.getLevelNumber()), Integer.valueOf(getLevelNumber())) && zzbe.equal(Long.valueOf(playerLevel.getMinXp()), Long.valueOf(getMinXp())) && zzbe.equal(Long.valueOf(playerLevel.getMaxXp()), Long.valueOf(getMaxXp()));
    }

    public final int getLevelNumber() {
        return this.zzaYS;
    }

    public final long getMaxXp() {
        return this.zzaYU;
    }

    public final long getMinXp() {
        return this.zzaYT;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.zzaYS), Long.valueOf(this.zzaYT), Long.valueOf(this.zzaYU)});
    }

    public final String toString() {
        return zzbe.zzt(this).zzg("LevelNumber", Integer.valueOf(getLevelNumber())).zzg("MinXp", Long.valueOf(getMinXp())).zzg("MaxXp", Long.valueOf(getMaxXp())).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, getLevelNumber());
        zzd.zza(parcel, 2, getMinXp());
        zzd.zza(parcel, 3, getMaxXp());
        zzd.zzI(parcel, zze);
    }
}

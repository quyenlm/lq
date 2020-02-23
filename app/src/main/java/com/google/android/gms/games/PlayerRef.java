package com.google.android.gms.games;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcel;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzc;
import com.google.android.gms.games.internal.player.zza;
import com.google.android.gms.games.internal.player.zzd;
import com.google.android.gms.games.internal.player.zze;

public final class PlayerRef extends zzc implements Player {
    private final PlayerLevelInfo zzaYI;
    private final zze zzaYZ;
    private final zzd zzaZa;

    public PlayerRef(DataHolder dataHolder, int i) {
        this(dataHolder, i, (String) null);
    }

    public PlayerRef(DataHolder dataHolder, int i, String str) {
        super(dataHolder, i);
        this.zzaYZ = new zze(str);
        this.zzaZa = new zzd(dataHolder, i, this.zzaYZ);
        if (!zzcx(this.zzaYZ.zzbcj) && getLong(this.zzaYZ.zzbcj) != -1) {
            int integer = getInteger(this.zzaYZ.zzbck);
            int integer2 = getInteger(this.zzaYZ.zzbcn);
            PlayerLevel playerLevel = new PlayerLevel(integer, getLong(this.zzaYZ.zzbcl), getLong(this.zzaYZ.zzbcm));
            this.zzaYI = new PlayerLevelInfo(getLong(this.zzaYZ.zzbcj), getLong(this.zzaYZ.zzbcp), playerLevel, integer != integer2 ? new PlayerLevel(integer2, getLong(this.zzaYZ.zzbcm), getLong(this.zzaYZ.zzbco)) : playerLevel);
            return;
        }
        this.zzaYI = null;
    }

    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        return PlayerEntity.zza(this, obj);
    }

    public final /* synthetic */ Object freeze() {
        return new PlayerEntity(this);
    }

    public final Uri getBannerImageLandscapeUri() {
        return zzcw(this.zzaYZ.zzbcA);
    }

    public final String getBannerImageLandscapeUrl() {
        return getString(this.zzaYZ.zzbcB);
    }

    public final Uri getBannerImagePortraitUri() {
        return zzcw(this.zzaYZ.zzbcC);
    }

    public final String getBannerImagePortraitUrl() {
        return getString(this.zzaYZ.zzbcD);
    }

    public final String getDisplayName() {
        return getString(this.zzaYZ.zzbcb);
    }

    public final void getDisplayName(CharArrayBuffer charArrayBuffer) {
        zza(this.zzaYZ.zzbcb, charArrayBuffer);
    }

    public final Uri getHiResImageUri() {
        return zzcw(this.zzaYZ.zzbce);
    }

    public final String getHiResImageUrl() {
        return getString(this.zzaYZ.zzbcf);
    }

    public final Uri getIconImageUri() {
        return zzcw(this.zzaYZ.zzbcc);
    }

    public final String getIconImageUrl() {
        return getString(this.zzaYZ.zzbcd);
    }

    public final long getLastPlayedWithTimestamp() {
        if (!zzcv(this.zzaYZ.zzbci) || zzcx(this.zzaYZ.zzbci)) {
            return -1;
        }
        return getLong(this.zzaYZ.zzbci);
    }

    public final PlayerLevelInfo getLevelInfo() {
        return this.zzaYI;
    }

    public final String getName() {
        return getString(this.zzaYZ.name);
    }

    public final String getPlayerId() {
        return getString(this.zzaYZ.zzbca);
    }

    public final long getRetrievedTimestamp() {
        return getLong(this.zzaYZ.zzbcg);
    }

    public final String getTitle() {
        return getString(this.zzaYZ.title);
    }

    public final void getTitle(CharArrayBuffer charArrayBuffer) {
        zza(this.zzaYZ.title, charArrayBuffer);
    }

    public final boolean hasHiResImage() {
        return getHiResImageUri() != null;
    }

    public final boolean hasIconImage() {
        return getIconImageUri() != null;
    }

    public final int hashCode() {
        return PlayerEntity.zza(this);
    }

    public final boolean isMuted() {
        return getBoolean(this.zzaYZ.zzbcG);
    }

    public final String toString() {
        return PlayerEntity.zzb(this);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        ((PlayerEntity) ((Player) freeze())).writeToParcel(parcel, i);
    }

    public final String zzuj() {
        return getString(this.zzaYZ.zzbcz);
    }

    public final boolean zzuk() {
        return getBoolean(this.zzaYZ.zzbcy);
    }

    public final int zzul() {
        return getInteger(this.zzaYZ.zzbch);
    }

    public final boolean zzum() {
        return getBoolean(this.zzaYZ.zzbcr);
    }

    public final zza zzun() {
        if (zzcx(this.zzaYZ.zzbcs)) {
            return null;
        }
        return this.zzaZa;
    }

    public final int zzuo() {
        return getInteger(this.zzaYZ.zzbcE);
    }

    public final long zzup() {
        return getLong(this.zzaYZ.zzbcF);
    }
}

package com.google.android.gms.games.snapshot;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.games.internal.zzc;
import java.util.Arrays;

public final class SnapshotEntity extends zzc implements Snapshot {
    public static final Parcelable.Creator<SnapshotEntity> CREATOR = new zzc();
    private final SnapshotMetadataEntity zzbex;
    private final zza zzbey;

    public SnapshotEntity(SnapshotMetadata snapshotMetadata, zza zza) {
        this.zzbex = new SnapshotMetadataEntity(snapshotMetadata);
        this.zzbey = zza;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof Snapshot) {
            if (this == obj) {
                return true;
            }
            Snapshot snapshot = (Snapshot) obj;
            return zzbe.equal(snapshot.getMetadata(), getMetadata()) && zzbe.equal(snapshot.getSnapshotContents(), getSnapshotContents());
        }
    }

    public final Snapshot freeze() {
        return this;
    }

    public final SnapshotMetadata getMetadata() {
        return this.zzbex;
    }

    public final SnapshotContents getSnapshotContents() {
        if (this.zzbey.isClosed()) {
            return null;
        }
        return this.zzbey;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{getMetadata(), getSnapshotContents()});
    }

    public final boolean isDataValid() {
        return true;
    }

    public final String toString() {
        return zzbe.zzt(this).zzg("Metadata", getMetadata()).zzg("HasContents", Boolean.valueOf(getSnapshotContents() != null)).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, (Parcelable) getMetadata(), i, false);
        zzd.zza(parcel, 3, (Parcelable) getSnapshotContents(), i, false);
        zzd.zzI(parcel, zze);
    }
}

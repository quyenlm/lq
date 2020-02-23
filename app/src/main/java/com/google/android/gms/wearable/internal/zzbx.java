package com.google.android.gms.wearable.internal;

import com.google.android.gms.wearable.DataItemAsset;

public final class zzbx implements DataItemAsset {
    private final String zzBN;
    private final String zzIi;

    public zzbx(DataItemAsset dataItemAsset) {
        this.zzIi = dataItemAsset.getId();
        this.zzBN = dataItemAsset.getDataItemKey();
    }

    public final /* bridge */ /* synthetic */ Object freeze() {
        return this;
    }

    public final String getDataItemKey() {
        return this.zzBN;
    }

    public final String getId() {
        return this.zzIi;
    }

    public final boolean isDataValid() {
        return true;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DataItemAssetEntity[");
        sb.append("@");
        sb.append(Integer.toHexString(hashCode()));
        if (this.zzIi == null) {
            sb.append(",noid");
        } else {
            sb.append(",");
            sb.append(this.zzIi);
        }
        sb.append(", key=");
        sb.append(this.zzBN);
        sb.append("]");
        return sb.toString();
    }
}

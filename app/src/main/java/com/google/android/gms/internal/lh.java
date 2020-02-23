package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class lh extends zza {
    public static final Parcelable.Creator<lh> CREATOR = new li();
    private int zzaku;
    private List<String> zzbXy;

    public lh() {
        this((List<String>) null);
    }

    lh(int i, List<String> list) {
        this.zzaku = i;
        if (list == null || list.isEmpty()) {
            this.zzbXy = Collections.emptyList();
            return;
        }
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 < list.size()) {
                String str = list.get(i3);
                if (TextUtils.isEmpty(str)) {
                    str = null;
                }
                list.set(i3, str);
                i2 = i3 + 1;
            } else {
                this.zzbXy = Collections.unmodifiableList(list);
                return;
            }
        }
    }

    private lh(@Nullable List<String> list) {
        this.zzaku = 1;
        this.zzbXy = new ArrayList();
        if (list != null && !list.isEmpty()) {
            this.zzbXy.addAll(list);
        }
    }

    public static lh zzEX() {
        return new lh((List<String>) null);
    }

    public static lh zza(lh lhVar) {
        return new lh(lhVar != null ? lhVar.zzbXy : null);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, this.zzaku);
        zzd.zzb(parcel, 2, this.zzbXy, false);
        zzd.zzI(parcel, zze);
    }

    public final List<String> zzEW() {
        return this.zzbXy;
    }
}

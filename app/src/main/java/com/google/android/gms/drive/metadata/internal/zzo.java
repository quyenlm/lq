package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.SearchableCollectionMetadataField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public final class zzo extends zzl<DriveId> implements SearchableCollectionMetadataField<DriveId> {
    public static final zzg zzaPP = new zzp();

    public zzo(int i) {
        super("parents", Collections.emptySet(), Arrays.asList(new String[]{"parentsExtra", "dbInstanceId", "parentsExtraHolder"}), 4100000);
    }

    /* access modifiers changed from: private */
    public static void zzd(DataHolder dataHolder) {
        Bundle zzqN = dataHolder.zzqN();
        if (zzqN != null) {
            synchronized (dataHolder) {
                DataHolder dataHolder2 = (DataHolder) zzqN.getParcelable("parentsExtraHolder");
                if (dataHolder2 != null) {
                    dataHolder2.close();
                    zzqN.remove("parentsExtraHolder");
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: zzd */
    public final Collection<DriveId> zzc(DataHolder dataHolder, int i, int i2) {
        String str;
        Bundle zzqN = dataHolder.zzqN();
        ArrayList parcelableArrayList = zzqN.getParcelableArrayList("parentsExtra");
        if (parcelableArrayList == null) {
            if (zzqN.getParcelable("parentsExtraHolder") != null) {
                synchronized (dataHolder) {
                    DataHolder dataHolder2 = (DataHolder) dataHolder.zzqN().getParcelable("parentsExtraHolder");
                    if (dataHolder2 != null) {
                        try {
                            int count = dataHolder.getCount();
                            ArrayList arrayList = new ArrayList(count);
                            HashMap hashMap = new HashMap(count);
                            for (int i3 = 0; i3 < count; i3++) {
                                int zzat = dataHolder.zzat(i3);
                                ParentDriveIdSet parentDriveIdSet = new ParentDriveIdSet();
                                arrayList.add(parentDriveIdSet);
                                hashMap.put(Long.valueOf(dataHolder.zzb("sqlId", i3, zzat)), parentDriveIdSet);
                            }
                            Bundle zzqN2 = dataHolder2.zzqN();
                            String string = zzqN2.getString("childSqlIdColumn");
                            String string2 = zzqN2.getString("parentSqlIdColumn");
                            String string3 = zzqN2.getString("parentResIdColumn");
                            int count2 = dataHolder2.getCount();
                            for (int i4 = 0; i4 < count2; i4++) {
                                int zzat2 = dataHolder2.zzat(i4);
                                ((ParentDriveIdSet) hashMap.get(Long.valueOf(dataHolder2.zzb(string, i4, zzat2)))).zzaPO.add(new zzq(dataHolder2.zzd(string3, i4, zzat2), dataHolder2.zzb(string2, i4, zzat2), 1));
                            }
                            dataHolder.zzqN().putParcelableArrayList("parentsExtra", arrayList);
                        } finally {
                            dataHolder2.close();
                            str = "parentsExtraHolder";
                            dataHolder.zzqN().remove(str);
                        }
                    }
                }
                parcelableArrayList = zzqN.getParcelableArrayList("parentsExtra");
            }
            if (parcelableArrayList == null) {
                return null;
            }
        }
        return ((ParentDriveIdSet) parcelableArrayList.get(i)).zzB(zzqN.getLong("dbInstanceId"));
    }

    /* access modifiers changed from: protected */
    /* renamed from: zzr */
    public final Collection<DriveId> zzq(Bundle bundle) {
        Collection zzr = super.zzq(bundle);
        if (zzr == null) {
            return null;
        }
        return new HashSet(zzr);
    }
}

package com.google.android.gms.internal;

import android.os.Bundle;
import android.support.v4.util.LongSparseArray;
import android.util.SparseArray;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.CustomPropertyKey;
import com.google.android.gms.drive.metadata.internal.AppVisibleCustomProperties;
import com.google.android.gms.drive.metadata.internal.zzc;
import com.google.android.gms.drive.metadata.internal.zzg;
import com.google.android.gms.drive.metadata.internal.zzm;
import java.util.Arrays;

public class zzbrn extends zzm<AppVisibleCustomProperties> {
    public static final zzg zzaQH = new zzbro();

    public zzbrn(int i) {
        super("customProperties", Arrays.asList(new String[]{"hasCustomProperties", "sqlId"}), Arrays.asList(new String[]{"customPropertiesExtra", "customPropertiesExtraHolder"}), 5000000);
    }

    /* access modifiers changed from: private */
    public static void zzd(DataHolder dataHolder) {
        Bundle zzqN = dataHolder.zzqN();
        if (zzqN != null) {
            synchronized (dataHolder) {
                DataHolder dataHolder2 = (DataHolder) zzqN.getParcelable("customPropertiesExtraHolder");
                if (dataHolder2 != null) {
                    dataHolder2.close();
                    zzqN.remove("customPropertiesExtraHolder");
                }
            }
        }
    }

    private static AppVisibleCustomProperties zzf(DataHolder dataHolder, int i, int i2) {
        String str;
        Bundle zzqN = dataHolder.zzqN();
        SparseArray sparseParcelableArray = zzqN.getSparseParcelableArray("customPropertiesExtra");
        if (sparseParcelableArray == null) {
            if (zzqN.getParcelable("customPropertiesExtraHolder") != null) {
                synchronized (dataHolder) {
                    DataHolder dataHolder2 = (DataHolder) dataHolder.zzqN().getParcelable("customPropertiesExtraHolder");
                    if (dataHolder2 != null) {
                        try {
                            Bundle zzqN2 = dataHolder2.zzqN();
                            String string = zzqN2.getString("entryIdColumn");
                            String string2 = zzqN2.getString("keyColumn");
                            String string3 = zzqN2.getString("visibilityColumn");
                            String string4 = zzqN2.getString("valueColumn");
                            LongSparseArray longSparseArray = new LongSparseArray();
                            for (int i3 = 0; i3 < dataHolder2.getCount(); i3++) {
                                int zzat = dataHolder2.zzat(i3);
                                long zzb = dataHolder2.zzb(string, i3, zzat);
                                zzc zzc = new zzc(new CustomPropertyKey(dataHolder2.zzd(string2, i3, zzat), dataHolder2.zzc(string3, i3, zzat)), dataHolder2.zzd(string4, i3, zzat));
                                AppVisibleCustomProperties.zza zza = (AppVisibleCustomProperties.zza) longSparseArray.get(zzb);
                                if (zza == null) {
                                    zza = new AppVisibleCustomProperties.zza();
                                    longSparseArray.put(zzb, zza);
                                }
                                zza.zza(zzc);
                            }
                            SparseArray sparseArray = new SparseArray();
                            for (int i4 = 0; i4 < dataHolder.getCount(); i4++) {
                                AppVisibleCustomProperties.zza zza2 = (AppVisibleCustomProperties.zza) longSparseArray.get(dataHolder.zzb("sqlId", i4, dataHolder.zzat(i4)));
                                if (zza2 != null) {
                                    sparseArray.append(i4, zza2.zztm());
                                }
                            }
                            dataHolder.zzqN().putSparseParcelableArray("customPropertiesExtra", sparseArray);
                        } finally {
                            dataHolder2.close();
                            str = "customPropertiesExtraHolder";
                            dataHolder.zzqN().remove(str);
                        }
                    }
                }
                sparseParcelableArray = zzqN.getSparseParcelableArray("customPropertiesExtra");
            }
            if (sparseParcelableArray == null) {
                return AppVisibleCustomProperties.zzaPG;
            }
        }
        return (AppVisibleCustomProperties) sparseParcelableArray.get(i, AppVisibleCustomProperties.zzaPG);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Object zzc(DataHolder dataHolder, int i, int i2) {
        return zzf(dataHolder, i, i2);
    }
}

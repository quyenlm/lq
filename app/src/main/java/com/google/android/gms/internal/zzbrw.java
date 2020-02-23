package com.google.android.gms.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.zzm;
import java.util.Arrays;

public final class zzbrw extends zzm<DriveId> {
    public static final zzbrw zzaQO = new zzbrw();

    private zzbrw() {
        super("driveId", Arrays.asList(new String[]{"sqlId", "resourceId", "mimeType"}), Arrays.asList(new String[]{"dbInstanceId"}), 4100000);
    }

    /* access modifiers changed from: protected */
    public final boolean zzb(DataHolder dataHolder, int i, int i2) {
        for (String zzcv : zztk()) {
            if (!dataHolder.zzcv(zzcv)) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Object zzc(DataHolder dataHolder, int i, int i2) {
        long j = dataHolder.zzqN().getLong("dbInstanceId");
        int i3 = DriveFolder.MIME_TYPE.equals(dataHolder.zzd(zzbrc.zzaQn.getName(), i, i2)) ? 1 : 0;
        String zzd = dataHolder.zzd("resourceId", i, i2);
        Long valueOf = Long.valueOf(dataHolder.zzb("sqlId", i, i2));
        if ("generated-android-null".equals(zzd)) {
            zzd = null;
        }
        return new DriveId(zzd, valueOf.longValue(), j, i3);
    }
}

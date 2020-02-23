package com.google.android.gms.drive;

import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.drive.metadata.internal.zzf;
import com.google.android.gms.internal.zzblj;
import com.google.android.gms.internal.zzbrc;

public final class MetadataBuffer extends AbstractDataBuffer<Metadata> {
    private zza zzaMx;

    static class zza extends Metadata {
        private final DataHolder zzaCX;
        private final int zzaFy;
        /* access modifiers changed from: private */
        public final int zzaMy;

        public zza(DataHolder dataHolder, int i) {
            this.zzaCX = dataHolder;
            this.zzaMy = i;
            this.zzaFy = dataHolder.zzat(i);
        }

        public final /* synthetic */ Object freeze() {
            MetadataBundle zztp = MetadataBundle.zztp();
            for (MetadataField<BitmapTeleporter> next : zzf.zztn()) {
                if (next != zzbrc.zzaQv) {
                    next.zza(this.zzaCX, zztp, this.zzaMy, this.zzaFy);
                }
            }
            return new zzblj(zztp);
        }

        public final boolean isDataValid() {
            return !this.zzaCX.isClosed();
        }

        public final <T> T zza(MetadataField<T> metadataField) {
            return metadataField.zza(this.zzaCX, this.zzaMy, this.zzaFy);
        }
    }

    public MetadataBuffer(DataHolder dataHolder) {
        super(dataHolder);
        dataHolder.zzqN().setClassLoader(MetadataBuffer.class.getClassLoader());
    }

    public final Metadata get(int i) {
        zza zza2 = this.zzaMx;
        if (zza2 != null && zza2.zzaMy == i) {
            return zza2;
        }
        zza zza3 = new zza(this.zzaCX, i);
        this.zzaMx = zza3;
        return zza3;
    }

    @Deprecated
    public final String getNextPageToken() {
        return null;
    }

    public final void release() {
        if (this.zzaCX != null) {
            zzf.zzb(this.zzaCX);
        }
        super.release();
    }
}

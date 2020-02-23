package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Looper;
import android.os.SystemClock;
import com.google.android.gms.common.util.zzq;
import java.io.InputStream;

final class zzyu implements zzaii<zznp> {
    private /* synthetic */ String zzNO;
    private /* synthetic */ zzyn zzRF;
    private /* synthetic */ boolean zzRT;
    private /* synthetic */ double zzRU;
    private /* synthetic */ boolean zzRV;

    zzyu(zzyn zzyn, boolean z, double d, boolean z2, String str) {
        this.zzRF = zzyn;
        this.zzRT = z;
        this.zzRU = d;
        this.zzRV = z2;
        this.zzNO = str;
    }

    /* access modifiers changed from: private */
    @TargetApi(19)
    /* renamed from: zzg */
    public final zznp zzh(InputStream inputStream) {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDensity = (int) (160.0d * this.zzRU);
        if (!this.zzRV) {
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        try {
            bitmap = BitmapFactory.decodeStream(inputStream, (Rect) null, options);
        } catch (Exception e) {
            zzafr.zzb("Error grabbing image.", e);
            bitmap = null;
        }
        if (bitmap == null) {
            this.zzRF.zzc(2, this.zzRT);
            return null;
        }
        long uptimeMillis2 = SystemClock.uptimeMillis();
        if (zzq.zzsc() && zzafr.zzhM()) {
            int width = bitmap.getWidth();
            zzafr.v(new StringBuilder(108).append("Decoded image w: ").append(width).append(" h:").append(bitmap.getHeight()).append(" bytes: ").append(bitmap.getAllocationByteCount()).append(" time: ").append(uptimeMillis2 - uptimeMillis).append(" on ui thread: ").append(Looper.getMainLooper().getThread() == Thread.currentThread()).toString());
        }
        return new zznp(new BitmapDrawable(Resources.getSystem(), bitmap), Uri.parse(this.zzNO), this.zzRU);
    }

    public final /* synthetic */ Object zzgy() {
        this.zzRF.zzc(2, this.zzRT);
        return null;
    }
}

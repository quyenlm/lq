package com.google.android.gms.internal;

import android.text.TextUtils;
import com.amazonaws.services.s3.util.Mimetypes;
import com.google.android.gms.ads.internal.js.zzai;
import java.util.Map;

final class zzoh implements zzrd {
    final /* synthetic */ zzai zzIj;
    final /* synthetic */ zzog zzIk;

    zzoh(zzog zzog, zzai zzai) {
        this.zzIk = zzog;
        this.zzIj = zzai;
    }

    public final void zza(zzaka zzaka, Map<String, String> map) {
        zzaka zzaka2 = (zzaka) this.zzIk.zzIh.get();
        if (zzaka2 == null) {
            this.zzIj.zzb("/loadHtml", (zzrd) this);
            return;
        }
        zzaka2.zziw().zza((zzakf) new zzoi(this, map));
        String str = map.get("overlayHtml");
        String str2 = map.get("baseUrl");
        if (TextUtils.isEmpty(str2)) {
            zzaka2.loadData(str, Mimetypes.MIMETYPE_HTML, "UTF-8");
        } else {
            zzaka2.loadDataWithBaseURL(str2, str, Mimetypes.MIMETYPE_HTML, "UTF-8", (String) null);
        }
    }
}

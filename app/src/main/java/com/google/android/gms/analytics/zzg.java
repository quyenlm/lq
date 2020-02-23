package com.google.android.gms.analytics;

import android.net.Uri;
import android.text.TextUtils;
import android.util.LogPrinter;
import com.facebook.share.internal.ShareConstants;
import java.util.ArrayList;
import java.util.Collections;

public final class zzg implements zzo {
    private static final Uri zzadO;
    private final LogPrinter zzadP = new LogPrinter(4, "GA/LogCatTransport");

    static {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(ShareConstants.MEDIA_URI);
        builder.authority("local");
        zzadO = builder.build();
    }

    public final void zzb(zzi zzi) {
        ArrayList arrayList = new ArrayList(zzi.zzjq());
        Collections.sort(arrayList, new zzh(this));
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            String obj2 = ((zzj) obj).toString();
            if (!TextUtils.isEmpty(obj2)) {
                if (sb.length() != 0) {
                    sb.append(", ");
                }
                sb.append(obj2);
            }
        }
        this.zzadP.println(sb.toString());
    }

    public final Uri zzjl() {
        return zzadO;
    }
}

package com.google.android.gms.internal;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.zzbs;
import java.math.BigInteger;
import java.util.Locale;

@zzzn
public final class zzafo {
    private static String zzYU;
    private static final Object zzuF = new Object();

    public static String zzb(Context context, String str, String str2) {
        String str3;
        synchronized (zzuF) {
            if (zzYU == null && !TextUtils.isEmpty(str)) {
                try {
                    ClassLoader classLoader = context.createPackageContext(str2, 3).getClassLoader();
                    Class<?> cls = Class.forName("com.google.ads.mediation.MediationAdapter", false, classLoader);
                    BigInteger bigInteger = new BigInteger(new byte[1]);
                    String[] split = str.split(",");
                    for (int i = 0; i < split.length; i++) {
                        zzbs.zzbz();
                        if (zzagz.zza(classLoader, cls, split[i])) {
                            bigInteger = bigInteger.setBit(i);
                        }
                    }
                    zzYU = String.format(Locale.US, "%X", new Object[]{bigInteger});
                } catch (Throwable th) {
                    zzYU = NotificationCompat.CATEGORY_ERROR;
                }
            }
            str3 = zzYU;
        }
        return str3;
    }

    public static String zzhK() {
        String str;
        synchronized (zzuF) {
            str = zzYU;
        }
        return str;
    }
}

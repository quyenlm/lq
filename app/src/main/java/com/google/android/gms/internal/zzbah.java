package com.google.android.gms.internal;

import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;
import com.appsflyer.share.Constants;
import com.google.android.gms.common.internal.zzbo;
import com.tencent.component.debug.FileTracerConfig;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public final class zzbah implements zzazq {
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    static Boolean zzazO = null;
    private zzbai zzazP;

    public zzbah(Context context) {
        this(new zzbai(context));
    }

    private zzbah(zzbai zzbai) {
        this.zzazP = (zzbai) zzbo.zzu(zzbai);
    }

    private static zzbaj zzcr(String str) {
        int i = 0;
        if (str == null) {
            return null;
        }
        String str2 = "";
        int indexOf = str.indexOf(44);
        if (indexOf >= 0) {
            str2 = str.substring(0, indexOf);
            i = indexOf + 1;
        }
        int indexOf2 = str.indexOf(47, i);
        if (indexOf2 <= 0) {
            String valueOf = String.valueOf(str);
            Log.e("LogSamplerImpl", valueOf.length() != 0 ? "Failed to parse the rule: ".concat(valueOf) : new String("Failed to parse the rule: "));
            return null;
        }
        try {
            long parseLong = Long.parseLong(str.substring(i, indexOf2));
            long parseLong2 = Long.parseLong(str.substring(indexOf2 + 1));
            if (parseLong >= 0 && parseLong2 >= 0) {
                return new zzbaj(str2, parseLong, parseLong2);
            }
            Log.e("LogSamplerImpl", new StringBuilder(72).append("negative values not supported: ").append(parseLong).append(Constants.URL_PATH_DELIMITER).append(parseLong2).toString());
            return null;
        } catch (NumberFormatException e) {
            NumberFormatException numberFormatException = e;
            String valueOf2 = String.valueOf(str);
            Log.e("LogSamplerImpl", valueOf2.length() != 0 ? "parseLong() failed while parsing: ".concat(valueOf2) : new String("parseLong() failed while parsing: "), numberFormatException);
            return null;
        }
    }

    public final boolean zzg(String str, int i) {
        String zza;
        long j;
        if (str == null || str.isEmpty()) {
            str = i >= 0 ? String.valueOf(i) : null;
        }
        if (str == null) {
            return true;
        }
        zzbai zzbai = this.zzazP;
        long j2 = zzbai.zzazQ == null ? 0 : hi.getLong(zzbai.zzazQ, "android_id", 0);
        zzbai zzbai2 = this.zzazP;
        if (zzbai2.zzazQ == null) {
            zza = null;
        } else {
            ContentResolver contentResolver = zzbai2.zzazQ;
            String valueOf = String.valueOf("gms:playlog:service:sampling_");
            String valueOf2 = String.valueOf(str);
            zza = hi.zza(contentResolver, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf), (String) null);
        }
        zzbaj zzcr = zzcr(zza);
        if (zzcr == null) {
            return true;
        }
        String str2 = zzcr.zzazR;
        if (str2 == null || str2.isEmpty()) {
            j = zzbac.zzf(ByteBuffer.allocate(8).putLong(j2).array());
        } else {
            byte[] bytes = str2.getBytes(UTF_8);
            ByteBuffer allocate = ByteBuffer.allocate(bytes.length + 8);
            allocate.put(bytes);
            allocate.putLong(j2);
            j = zzbac.zzf(allocate.array());
        }
        long j3 = zzcr.zzazS;
        long j4 = zzcr.zzazT;
        if (j3 < 0 || j4 < 0) {
            throw new IllegalArgumentException(new StringBuilder(72).append("negative values not supported: ").append(j3).append(Constants.URL_PATH_DELIMITER).append(j4).toString());
        }
        if (j4 > 0) {
            if ((j >= 0 ? j % j4 : (((j & FileTracerConfig.FOREVER) % j4) + ((FileTracerConfig.FOREVER % j4) + 1)) % j4) < j3) {
                return true;
            }
        }
        return false;
    }
}

package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbf;
import com.google.android.gms.internal.zzbg;
import com.google.android.gms.internal.zzbr;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

final class zzem extends zzga {
    private static final String ID = zzbf.REGEX.toString();
    private static final String zzbFR = zzbg.IGNORE_CASE.toString();

    public zzem() {
        super(ID);
    }

    /* access modifiers changed from: protected */
    public final boolean zza(String str, String str2, Map<String, zzbr> map) {
        try {
            return Pattern.compile(str2, zzgk.zzf(map.get(zzbFR)).booleanValue() ? 66 : 64).matcher(str).find();
        } catch (PatternSyntaxException e) {
            return false;
        }
    }
}

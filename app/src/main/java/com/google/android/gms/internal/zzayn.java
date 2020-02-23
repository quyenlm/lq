package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;

public final class zzayn {
    public static final Api.zzf<zzaxx> zzayp = new Api.zzf<>();
    public static final Api.zzf<zzazf> zzayq = new Api.zzf<>();
    private static Api.zzf<Object> zzayr = new Api.zzf<>();
    private static Api.zzf<Object> zzays = new Api.zzf<>();
    private static Charset zzayt;
    private static String zzayu = zzaye.zzcj("com.google.cast.multizone");

    static {
        Charset charset = null;
        try {
            charset = Charset.forName("UTF-8");
        } catch (IllegalCharsetNameException | UnsupportedCharsetException e) {
        }
        zzayt = charset;
    }
}

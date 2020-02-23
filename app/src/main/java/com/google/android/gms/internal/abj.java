package com.google.android.gms.internal;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

public final class abj implements FirebaseRemoteConfigValue {
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    public static final Pattern zzaKs = Pattern.compile("^(1|true|t|yes|y|on)$", 2);
    public static final Pattern zzaKt = Pattern.compile("^(0|false|f|no|n|off|)$", 2);
    private final int zzBM;
    private final byte[] zzcnL;

    public abj(byte[] bArr, int i) {
        this.zzcnL = bArr;
        this.zzBM = i;
    }

    public final boolean asBoolean() throws IllegalArgumentException {
        if (this.zzBM == 0) {
            return false;
        }
        String asString = asString();
        if (zzaKs.matcher(asString).matches()) {
            return true;
        }
        if (zzaKt.matcher(asString).matches()) {
            return false;
        }
        throw new IllegalArgumentException(new StringBuilder(String.valueOf(asString).length() + 45).append("[Value: ").append(asString).append("] cannot be interpreted as a boolean.").toString());
    }

    public final byte[] asByteArray() {
        return this.zzBM == 0 ? FirebaseRemoteConfig.DEFAULT_VALUE_FOR_BYTE_ARRAY : this.zzcnL;
    }

    public final double asDouble() {
        if (this.zzBM == 0) {
            return FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        }
        String asString = asString();
        try {
            return Double.valueOf(asString).doubleValue();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(asString).length() + 42).append("[Value: ").append(asString).append("] cannot be converted to a double.").toString(), e);
        }
    }

    public final long asLong() {
        if (this.zzBM == 0) {
            return 0;
        }
        String asString = asString();
        try {
            return Long.valueOf(asString).longValue();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(asString).length() + 40).append("[Value: ").append(asString).append("] cannot be converted to a long.").toString(), e);
        }
    }

    public final String asString() {
        if (this.zzBM == 0) {
            return "";
        }
        if (this.zzcnL != null) {
            return new String(this.zzcnL, UTF_8);
        }
        throw new IllegalArgumentException("Value is null, and cannot be converted to the desired type.");
    }

    public final int getSource() {
        return this.zzBM;
    }
}

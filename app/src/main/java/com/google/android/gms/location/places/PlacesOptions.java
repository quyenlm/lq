package com.google.android.gms.location.places;

import android.support.annotation.Nullable;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.zzbe;
import java.util.Arrays;
import java.util.Locale;

public final class PlacesOptions implements Api.ApiOptions.Optional {
    @Nullable
    public final String zzakh;
    @Nullable
    public final String zzbjS;
    @Nullable
    public final String zzbjT;
    public final int zzbjU;
    @Nullable
    public final Locale zzbjV;

    public static class Builder {
        private int zzbjU = 0;

        public PlacesOptions build() {
            return new PlacesOptions(this);
        }
    }

    private PlacesOptions(Builder builder) {
        this.zzbjS = null;
        this.zzbjT = null;
        this.zzbjU = 0;
        this.zzakh = null;
        this.zzbjV = null;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof PlacesOptions) && zzbe.equal((Object) null, (Object) null) && zzbe.equal((Object) null, (Object) null) && zzbe.equal(0, 0) && zzbe.equal((Object) null, (Object) null) && zzbe.equal((Object) null, (Object) null);
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{null, null, 0, null, null});
    }
}

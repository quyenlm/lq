package com.google.android.gms.nearby.messages;

import android.support.annotation.Nullable;
import com.google.android.gms.common.api.Api;

public class MessagesOptions implements Api.ApiOptions.Optional {
    @Nullable
    public final String zzbye;
    public final boolean zzbyf;
    public final int zzbyg;
    public final String zzbyh;

    public static class Builder {
        /* access modifiers changed from: private */
        public int zzbyi = -1;

        public MessagesOptions build() {
            return new MessagesOptions(this);
        }

        public Builder setPermissions(int i) {
            this.zzbyi = i;
            return this;
        }
    }

    private MessagesOptions(Builder builder) {
        this.zzbye = null;
        this.zzbyf = false;
        this.zzbyg = builder.zzbyi;
        this.zzbyh = null;
    }
}

package com.subao.common.j;

import android.support.annotation.NonNull;

/* compiled from: NetTypeDetector */
public interface j {
    @NonNull
    a a();

    /* compiled from: NetTypeDetector */
    public enum a {
        DISCONNECT(-1),
        UNKNOWN(0),
        WIFI(1),
        MOBILE_2G(2),
        MOBILE_3G(3),
        MOBILE_4G(4);
        
        public final int g;

        private a(int i) {
            this.g = i;
        }
    }
}

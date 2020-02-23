package com.subao.common.intf;

import android.support.annotation.NonNull;

public class SupportGameLabel {
    private final boolean exact;
    private final String label;

    public SupportGameLabel(@NonNull String str, boolean z) {
        this.label = str;
        this.exact = z;
    }

    @NonNull
    public String getLabel() {
        return this.label;
    }

    public boolean isExact() {
        return this.exact;
    }
}

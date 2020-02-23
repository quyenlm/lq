package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.List;

public final class zzaid {
    /* access modifiers changed from: private */
    public final List<String> zzaaa = new ArrayList();
    /* access modifiers changed from: private */
    public final List<Double> zzaab = new ArrayList();
    /* access modifiers changed from: private */
    public final List<Double> zzaac = new ArrayList();

    public final zzaid zza(String str, double d, double d2) {
        int i;
        int i2 = 0;
        while (true) {
            i = i2;
            if (i >= this.zzaaa.size()) {
                break;
            }
            double doubleValue = this.zzaac.get(i).doubleValue();
            double doubleValue2 = this.zzaab.get(i).doubleValue();
            if (d < doubleValue || (doubleValue == d && d2 < doubleValue2)) {
                break;
            }
            i2 = i + 1;
        }
        this.zzaaa.add(i, str);
        this.zzaac.add(i, Double.valueOf(d));
        this.zzaab.add(i, Double.valueOf(d2));
        return this;
    }

    public final zzaia zzid() {
        return new zzaia(this);
    }
}

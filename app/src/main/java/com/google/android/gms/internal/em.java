package com.google.android.gms.internal;

import java.util.Collections;
import java.util.List;

public final class em {
    private final List<ei> zzbLJ;
    private final List<ei> zzbLK;
    private final List<String> zzbLL;
    private final List<String> zzbLM;
    private final List<String> zzbLN;
    private final List<String> zzbLO;
    private final List<ei> zzbLb;
    private final List<ei> zzbLc;
    private final List<ei> zzbLd;
    private final List<ei> zzbLe;

    private em(List<ei> list, List<ei> list2, List<ei> list3, List<ei> list4, List<ei> list5, List<ei> list6, List<String> list7, List<String> list8, List<String> list9, List<String> list10) {
        this.zzbLb = Collections.unmodifiableList(list);
        this.zzbLc = Collections.unmodifiableList(list2);
        this.zzbLd = Collections.unmodifiableList(list3);
        this.zzbLe = Collections.unmodifiableList(list4);
        this.zzbLJ = Collections.unmodifiableList(list5);
        this.zzbLK = Collections.unmodifiableList(list6);
        this.zzbLL = Collections.unmodifiableList(list7);
        this.zzbLM = Collections.unmodifiableList(list8);
        this.zzbLN = Collections.unmodifiableList(list9);
        this.zzbLO = Collections.unmodifiableList(list10);
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zzbLb);
        String valueOf2 = String.valueOf(this.zzbLc);
        String valueOf3 = String.valueOf(this.zzbLd);
        String valueOf4 = String.valueOf(this.zzbLe);
        String valueOf5 = String.valueOf(this.zzbLJ);
        String valueOf6 = String.valueOf(this.zzbLK);
        return new StringBuilder(String.valueOf(valueOf).length() + 102 + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length() + String.valueOf(valueOf4).length() + String.valueOf(valueOf5).length() + String.valueOf(valueOf6).length()).append("Positive predicates: ").append(valueOf).append("  Negative predicates: ").append(valueOf2).append("  Add tags: ").append(valueOf3).append("  Remove tags: ").append(valueOf4).append("  Add macros: ").append(valueOf5).append("  Remove macros: ").append(valueOf6).toString();
    }

    public final List<ei> zzDC() {
        return this.zzbLJ;
    }

    public final List<ei> zzDD() {
        return this.zzbLK;
    }

    public final List<ei> zzDb() {
        return this.zzbLb;
    }

    public final List<ei> zzDc() {
        return this.zzbLc;
    }

    public final List<ei> zzDd() {
        return this.zzbLd;
    }

    public final List<ei> zzDe() {
        return this.zzbLe;
    }
}

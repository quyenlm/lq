package com.tencent.mna.base.c;

/* compiled from: ReportType */
public enum c {
    START("mna_start_p", 1),
    NORMAL("ino_newacc_p", 2),
    PREDICT("mna_predict_p", 3),
    END("mna_end_p", 4),
    DIAGNOSE("kartin_report", 5),
    BANDWIDTH("mna_brand_p", 6),
    WIFI("mna_wifisdk_p", 7),
    QUERY_NETWORK("mna_query_network_p", 8, false);
    
    boolean mIsRealTime;
    String mName;
    int mType;

    private c(String str, int i) {
        this(r7, r8, str, i, true);
    }

    private c(String str, int i, boolean z) {
        this.mName = str;
        this.mType = i;
        this.mIsRealTime = z;
    }

    public int getType() {
        return this.mType;
    }

    public String getName() {
        return this.mName;
    }

    public boolean isRealTime() {
        return this.mIsRealTime;
    }
}

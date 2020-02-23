package com.tencent.msdk.stat.a;

import com.tencent.midas.oversea.network.http.APErrorCode;

public enum e {
    PAGE_VIEW(1),
    SESSION_ENV(2),
    ERROR(3),
    BACKGROUND(4),
    CUSTOM(1000),
    ADDITION(1001),
    MONITOR_STAT(1002),
    MTA_GAME_USER(1003),
    NETWORK_MONITOR(1004),
    NETWORK_DETECTOR(1005),
    PERFORMANCE_EVALUATION(APErrorCode.ERROR_NETWORK_HTTPSTIMES);
    
    private int l;

    private e(int i) {
        this.l = i;
    }

    public int a() {
        return this.l;
    }
}

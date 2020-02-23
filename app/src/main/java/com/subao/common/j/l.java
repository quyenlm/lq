package com.subao.common.j;

/* compiled from: Protocol */
public enum l {
    UDP("UDP", 0),
    TCP("TCP", 1),
    BOTH("BOTH", 2);
    
    public final String d;
    public final int e;

    private l(String str, int i) {
        this.d = str;
        this.e = i;
    }
}

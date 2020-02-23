package com.tencent.qqgamemi.mgc.pb;

import com.squareup.wire.Wire;

public class WireHolder {
    private static Wire sWire = null;

    private WireHolder() {
    }

    public static synchronized Wire getWire() {
        Wire wire;
        synchronized (WireHolder.class) {
            if (sWire == null) {
                sWire = new Wire((Class<?>[]) new Class[0]);
            }
            wire = sWire;
        }
        return wire;
    }
}

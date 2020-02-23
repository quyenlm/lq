package com.google.android.gms.internal;

import java.io.IOException;

public final class adi extends IOException {
    adi(int i, int i2) {
        super(new StringBuilder(108).append("CodedOutputStream was writing to a flat byte array and ran out of space (pos ").append(i).append(" limit ").append(i2).append(").").toString());
    }
}

package com.google.android.gms.maps.model;

public final class Gap extends PatternItem {
    public final float length;

    public Gap(float f) {
        super(2, Float.valueOf(Math.max(f, 0.0f)));
        this.length = Math.max(f, 0.0f);
    }

    public final String toString() {
        return new StringBuilder(29).append("[Gap: length=").append(this.length).append("]").toString();
    }
}

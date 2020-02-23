package com.facebook.places.internal;

public class BluetoothScanResult {
    public String payload;
    public int rssi;
    public long timestampNanos;

    public BluetoothScanResult(String payload2, int rssi2, long timestampNanos2) {
        this.payload = payload2;
        this.rssi = rssi2;
        this.timestampNanos = timestampNanos2;
    }
}

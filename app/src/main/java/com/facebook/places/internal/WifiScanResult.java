package com.facebook.places.internal;

public class WifiScanResult {
    public String bssid;
    public int frequency;
    public int rssi;
    public String ssid;
    public long timestampMs;

    public WifiScanResult() {
    }

    public WifiScanResult(String ssid2, String bssid2, int rssi2, int frequency2, long timestampMs2) {
        this.ssid = ssid2;
        this.bssid = bssid2;
        this.rssi = rssi2;
        this.frequency = frequency2;
        this.timestampMs = timestampMs2;
    }
}

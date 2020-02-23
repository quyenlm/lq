package com.facebook.places.internal;

import com.google.android.gms.cast.framework.media.NotificationOptions;

public class LocationPackageRequestParams {
    private static final boolean DEFAULT_BLUETOOTH_ENABLED = true;
    private static final long DEFAULT_BLUETOOTH_FLUSH_RESULTS_TIMEOUT_MS = 300;
    private static final int DEFAULT_BLUETOOTH_MAX_SCAN_RESULTS = 25;
    private static final long DEFAULT_BLUETOOTH_SCAN_DURATION_MS = 500;
    private static final long DEFAULT_LAST_LOCATION_MAX_AGE_MS = 60000;
    private static final boolean DEFAULT_LOCATION_ENABLED = true;
    private static final float DEFAULT_LOCATION_MAX_ACCURACY_METERS = 100.0f;
    /* access modifiers changed from: private */
    public static final String[] DEFAULT_LOCATION_PROVIDERS = {"network", "gps"};
    private static final long DEFAULT_LOCATION_REQUEST_TIMEOUT_MS = 30000;
    private static final boolean DEFAULT_WIFI_ACTIVE_SCAN_ALLOWED = true;
    private static final boolean DEFAULT_WIFI_ACTIVE_SCAN_FORCED = false;
    private static final boolean DEFAULT_WIFI_ENABLED = true;
    private static final int DEFAULT_WIFI_MAX_SCAN_RESULTS = 25;
    private static final long DEFAULT_WIFI_SCAN_MAX_AGE_MS = 30000;
    private static final long DEFAULT_WIFI_SCAN_TIMEOUT_MS = 6000;
    private long bluetoothFlushResultsTimeoutMs;
    private int bluetoothMaxScanResults;
    private long bluetoothScanDurationMs;
    private boolean isBluetoothScanEnabled;
    private boolean isLocationScanEnabled;
    private boolean isWifiActiveScanAllowed;
    private boolean isWifiActiveScanForced;
    private boolean isWifiScanEnabled;
    private long lastLocationMaxAgeMs;
    private float locationMaxAccuracyMeters;
    private final String[] locationProviders;
    private long locationRequestTimeoutMs;
    private int wifiMaxScanResults;
    private long wifiScanMaxAgeMs;
    private long wifiScanTimeoutMs;

    private LocationPackageRequestParams(Builder b) {
        this.isLocationScanEnabled = b.isLocationScanEnabled;
        this.locationProviders = b.locationProviders;
        this.locationMaxAccuracyMeters = b.locationMaxAccuracyMeters;
        this.locationRequestTimeoutMs = b.locationRequestTimeoutMs;
        this.lastLocationMaxAgeMs = b.lastLocationMaxAgeMs;
        this.isWifiScanEnabled = b.isWifiScanEnabled;
        this.wifiScanMaxAgeMs = b.wifiScanMaxAgeMs;
        this.wifiMaxScanResults = b.wifiMaxScanResults;
        this.wifiScanTimeoutMs = b.wifiScanTimeoutMs;
        this.isWifiActiveScanAllowed = b.isWifiActiveScanAllowed;
        this.isWifiActiveScanForced = b.isWifiActiveScanForced;
        this.isBluetoothScanEnabled = b.isBluetoothScanEnabled;
        this.bluetoothScanDurationMs = b.bluetoothScanDurationMs;
        this.bluetoothMaxScanResults = b.bluetoothMaxScanResults;
        this.bluetoothFlushResultsTimeoutMs = b.bluetoothFlushResultsTimeoutMs;
    }

    public boolean isLocationScanEnabled() {
        return this.isLocationScanEnabled;
    }

    public String[] getLocationProviders() {
        return this.locationProviders;
    }

    public float getLocationMaxAccuracyMeters() {
        return this.locationMaxAccuracyMeters;
    }

    public long getLocationRequestTimeoutMs() {
        return this.locationRequestTimeoutMs;
    }

    public long getLastLocationMaxAgeMs() {
        return this.lastLocationMaxAgeMs;
    }

    public boolean isWifiScanEnabled() {
        return this.isWifiScanEnabled;
    }

    public long getWifiScanMaxAgeMs() {
        return this.wifiScanMaxAgeMs;
    }

    public int getWifiMaxScanResults() {
        return this.wifiMaxScanResults;
    }

    public long getWifiScanTimeoutMs() {
        return this.wifiScanTimeoutMs;
    }

    public boolean isWifiActiveScanAllowed() {
        return this.isWifiActiveScanAllowed;
    }

    public boolean isWifiActiveScanForced() {
        return this.isWifiActiveScanForced;
    }

    public boolean isBluetoothScanEnabled() {
        return this.isBluetoothScanEnabled;
    }

    public long getBluetoothScanDurationMs() {
        return this.bluetoothScanDurationMs;
    }

    public long getBluetoothFlushResultsTimeoutMs() {
        return this.bluetoothFlushResultsTimeoutMs;
    }

    public int getBluetoothMaxScanResults() {
        return this.bluetoothMaxScanResults;
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public long bluetoothFlushResultsTimeoutMs = LocationPackageRequestParams.DEFAULT_BLUETOOTH_FLUSH_RESULTS_TIMEOUT_MS;
        /* access modifiers changed from: private */
        public int bluetoothMaxScanResults = 25;
        /* access modifiers changed from: private */
        public long bluetoothScanDurationMs = LocationPackageRequestParams.DEFAULT_BLUETOOTH_SCAN_DURATION_MS;
        /* access modifiers changed from: private */
        public boolean isBluetoothScanEnabled = true;
        /* access modifiers changed from: private */
        public boolean isLocationScanEnabled = true;
        /* access modifiers changed from: private */
        public boolean isWifiActiveScanAllowed = true;
        /* access modifiers changed from: private */
        public boolean isWifiActiveScanForced = false;
        /* access modifiers changed from: private */
        public boolean isWifiScanEnabled = true;
        /* access modifiers changed from: private */
        public long lastLocationMaxAgeMs = 60000;
        /* access modifiers changed from: private */
        public float locationMaxAccuracyMeters = LocationPackageRequestParams.DEFAULT_LOCATION_MAX_ACCURACY_METERS;
        /* access modifiers changed from: private */
        public String[] locationProviders = LocationPackageRequestParams.DEFAULT_LOCATION_PROVIDERS;
        /* access modifiers changed from: private */
        public long locationRequestTimeoutMs = NotificationOptions.SKIP_STEP_THIRTY_SECONDS_IN_MS;
        /* access modifiers changed from: private */
        public int wifiMaxScanResults = 25;
        /* access modifiers changed from: private */
        public long wifiScanMaxAgeMs = NotificationOptions.SKIP_STEP_THIRTY_SECONDS_IN_MS;
        /* access modifiers changed from: private */
        public long wifiScanTimeoutMs = 6000;

        public LocationPackageRequestParams build() {
            return new LocationPackageRequestParams(this);
        }

        public Builder setLocationScanEnabled(boolean locationScanEnabled) {
            this.isLocationScanEnabled = locationScanEnabled;
            return this;
        }

        public Builder setLastLocationMaxAgeMs(long lastLocationMaxAgeMs2) {
            this.lastLocationMaxAgeMs = lastLocationMaxAgeMs2;
            return this;
        }

        public Builder setLocationProviders(String[] locationProviders2) {
            this.locationProviders = locationProviders2;
            return this;
        }

        public Builder setLocationMaxAccuracyMeters(float locationMaxAccuracyMeters2) {
            this.locationMaxAccuracyMeters = locationMaxAccuracyMeters2;
            return this;
        }

        public Builder setLocationRequestTimeoutMs(long locationRequestTimeoutMs2) {
            this.locationRequestTimeoutMs = locationRequestTimeoutMs2;
            return this;
        }

        public Builder setWifiScanEnabled(boolean wifiScanEnabled) {
            this.isWifiScanEnabled = wifiScanEnabled;
            return this;
        }

        public Builder setWifiScanMaxAgeMs(long wifiScanMaxAgeMs2) {
            this.wifiScanMaxAgeMs = wifiScanMaxAgeMs2;
            return this;
        }

        public Builder setWifiMaxScanResults(int wifiMaxScanResults2) {
            this.wifiMaxScanResults = wifiMaxScanResults2;
            return this;
        }

        public Builder setWifiScanTimeoutMs(long wifiScanTimeoutMs2) {
            this.wifiScanTimeoutMs = wifiScanTimeoutMs2;
            return this;
        }

        public Builder setWifiActiveScanAllowed(boolean wifiActiveScanAllowed) {
            this.isWifiActiveScanAllowed = wifiActiveScanAllowed;
            return this;
        }

        public Builder setWifiActiveScanForced(boolean wifiActiveScanForced) {
            this.isWifiActiveScanForced = wifiActiveScanForced;
            return this;
        }

        public Builder setBluetoothScanEnabled(boolean bluetoothScanEnabled) {
            this.isBluetoothScanEnabled = bluetoothScanEnabled;
            return this;
        }

        public Builder setBluetoothScanDurationMs(long bluetoothScanDurationMs2) {
            this.bluetoothScanDurationMs = bluetoothScanDurationMs2;
            return this;
        }

        public Builder setBluetoothMaxScanResults(int bluetoothMaxScanResults2) {
            this.bluetoothMaxScanResults = bluetoothMaxScanResults2;
            return this;
        }

        public Builder setBluetoothFlushResultsTimeoutMs(long bluetoothFlushResultsTimeoutMs2) {
            this.bluetoothFlushResultsTimeoutMs = bluetoothFlushResultsTimeoutMs2;
            return this;
        }
    }
}

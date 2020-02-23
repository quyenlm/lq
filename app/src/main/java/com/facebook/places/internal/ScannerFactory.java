package com.facebook.places.internal;

import android.content.Context;
import android.os.Build;

public class ScannerFactory {
    public static final int OS_VERSION_JELLY_BEAN_MR1 = 17;
    public static final int OS_VERSION_JELLY_BEAN_MR2 = 18;
    public static final int OS_VERSION_LOLLIPOP = 21;

    public static BleScanner newBleScanner(Context context, LocationPackageRequestParams params) {
        if (Build.VERSION.SDK_INT >= 21) {
            return new BleScannerImpl(context, params);
        }
        return new BleScannerLegacy();
    }

    public static WifiScanner newWifiScanner(Context context, LocationPackageRequestParams params) {
        return new WifiScannerImpl(context, params);
    }

    public static LocationScanner newLocationScanner(Context context, LocationPackageRequestParams params) {
        return new LocationScannerImpl(context, params);
    }
}

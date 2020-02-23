package com.facebook.places.internal;

import android.util.Log;
import com.facebook.FacebookSdk;
import com.facebook.internal.Utility;
import com.facebook.places.internal.ScannerException;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class LocationPackageManager {
    private static final String TAG = "LocationPackageManager";

    public interface Listener {
        void onLocationPackage(LocationPackage locationPackage);
    }

    public static void requestLocationPackage(final LocationPackageRequestParams requestParams, final Listener listener) {
        FacebookSdk.getExecutor().execute(new Runnable() {
            /* JADX WARNING: Code restructure failed: missing block: B:22:0x008e, code lost:
                r2 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
                com.facebook.places.internal.LocationPackageManager.access$300("Exception scanning for bluetooth beacons", r2);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:26:0x0095, code lost:
                r2 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:27:0x0096, code lost:
                com.facebook.places.internal.LocationPackageManager.access$300("Exception scanning for locations", r2);
                r3.locationError = r2.type;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:28:0x00a0, code lost:
                r2 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
                com.facebook.places.internal.LocationPackageManager.access$300("Exception scanning for wifi access points", r2);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:32:0x00a7, code lost:
                r2 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a8, code lost:
                com.facebook.places.internal.LocationPackageManager.access$300("Exception requesting a location package", r2);
             */
            /* JADX WARNING: Failed to process nested try/catch */
            /* JADX WARNING: Removed duplicated region for block: B:26:0x0095 A[ExcHandler: ScannerException (r2v1 'e' com.facebook.places.internal.ScannerException A[CUSTOM_DECLARE]), Splitter:B:1:0x0008] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r11 = this;
                    com.facebook.places.internal.LocationPackage r3 = new com.facebook.places.internal.LocationPackage
                    r3.<init>()
                    r4 = 0
                    r7 = 0
                    r0 = 0
                    com.facebook.places.internal.LocationPackageRequestParams r9 = r2     // Catch:{ ScannerException -> 0x0095, Exception -> 0x00a7 }
                    boolean r9 = r9.isLocationScanEnabled()     // Catch:{ ScannerException -> 0x0095, Exception -> 0x00a7 }
                    if (r9 == 0) goto L_0x002a
                    android.content.Context r9 = com.facebook.FacebookSdk.getApplicationContext()     // Catch:{ ScannerException -> 0x0095, Exception -> 0x00a7 }
                    com.facebook.places.internal.LocationPackageRequestParams r10 = r2     // Catch:{ ScannerException -> 0x0095, Exception -> 0x00a7 }
                    com.facebook.places.internal.LocationScanner r5 = com.facebook.places.internal.ScannerFactory.newLocationScanner(r9, r10)     // Catch:{ ScannerException -> 0x0095, Exception -> 0x00a7 }
                    r5.initAndCheckEligibility()     // Catch:{ ScannerException -> 0x0095, Exception -> 0x00a7 }
                    com.facebook.places.internal.LocationPackageRequestParams r9 = r2     // Catch:{ ScannerException -> 0x0095, Exception -> 0x00a7 }
                    java.util.concurrent.FutureTask r4 = com.facebook.places.internal.LocationPackageManager.newLocationScanFuture(r5, r9)     // Catch:{ ScannerException -> 0x0095, Exception -> 0x00a7 }
                    java.util.concurrent.Executor r9 = com.facebook.FacebookSdk.getExecutor()     // Catch:{ ScannerException -> 0x0095, Exception -> 0x00a7 }
                    r9.execute(r4)     // Catch:{ ScannerException -> 0x0095, Exception -> 0x00a7 }
                L_0x002a:
                    com.facebook.places.internal.LocationPackageRequestParams r9 = r2     // Catch:{ ScannerException -> 0x0095, Exception -> 0x00a7 }
                    boolean r9 = r9.isWifiScanEnabled()     // Catch:{ ScannerException -> 0x0095, Exception -> 0x00a7 }
                    if (r9 == 0) goto L_0x003f
                    com.facebook.places.internal.LocationPackageRequestParams r9 = r2     // Catch:{ ScannerException -> 0x0095, Exception -> 0x00a7 }
                    java.util.concurrent.FutureTask r7 = com.facebook.places.internal.LocationPackageManager.newWifiScanFuture(r9)     // Catch:{ ScannerException -> 0x0095, Exception -> 0x00a7 }
                    java.util.concurrent.Executor r9 = com.facebook.FacebookSdk.getExecutor()     // Catch:{ ScannerException -> 0x0095, Exception -> 0x00a7 }
                    r9.execute(r7)     // Catch:{ ScannerException -> 0x0095, Exception -> 0x00a7 }
                L_0x003f:
                    com.facebook.places.internal.LocationPackageRequestParams r9 = r2     // Catch:{ ScannerException -> 0x0095, Exception -> 0x00a7 }
                    boolean r9 = r9.isBluetoothScanEnabled()     // Catch:{ ScannerException -> 0x0095, Exception -> 0x00a7 }
                    if (r9 == 0) goto L_0x0054
                    com.facebook.places.internal.LocationPackageRequestParams r9 = r2     // Catch:{ ScannerException -> 0x0095, Exception -> 0x00a7 }
                    java.util.concurrent.FutureTask r0 = com.facebook.places.internal.LocationPackageManager.newBluetoothScanFuture(r9)     // Catch:{ ScannerException -> 0x0095, Exception -> 0x00a7 }
                    java.util.concurrent.Executor r9 = com.facebook.FacebookSdk.getExecutor()     // Catch:{ ScannerException -> 0x0095, Exception -> 0x00a7 }
                    r9.execute(r0)     // Catch:{ ScannerException -> 0x0095, Exception -> 0x00a7 }
                L_0x0054:
                    if (r0 == 0) goto L_0x0064
                    java.lang.Object r1 = r0.get()     // Catch:{ Exception -> 0x008e, ScannerException -> 0x0095 }
                    com.facebook.places.internal.LocationPackage r1 = (com.facebook.places.internal.LocationPackage) r1     // Catch:{ Exception -> 0x008e, ScannerException -> 0x0095 }
                    java.util.List<com.facebook.places.internal.BluetoothScanResult> r9 = r1.ambientBluetoothLe     // Catch:{ Exception -> 0x008e, ScannerException -> 0x0095 }
                    r3.ambientBluetoothLe = r9     // Catch:{ Exception -> 0x008e, ScannerException -> 0x0095 }
                    boolean r9 = r1.isBluetoothScanningEnabled     // Catch:{ Exception -> 0x008e, ScannerException -> 0x0095 }
                    r3.isBluetoothScanningEnabled = r9     // Catch:{ Exception -> 0x008e, ScannerException -> 0x0095 }
                L_0x0064:
                    if (r7 == 0) goto L_0x0078
                    java.lang.Object r8 = r7.get()     // Catch:{ Exception -> 0x00a0, ScannerException -> 0x0095 }
                    com.facebook.places.internal.LocationPackage r8 = (com.facebook.places.internal.LocationPackage) r8     // Catch:{ Exception -> 0x00a0, ScannerException -> 0x0095 }
                    boolean r9 = r8.isWifiScanningEnabled     // Catch:{ Exception -> 0x00a0, ScannerException -> 0x0095 }
                    r3.isWifiScanningEnabled = r9     // Catch:{ Exception -> 0x00a0, ScannerException -> 0x0095 }
                    com.facebook.places.internal.WifiScanResult r9 = r8.connectedWifi     // Catch:{ Exception -> 0x00a0, ScannerException -> 0x0095 }
                    r3.connectedWifi = r9     // Catch:{ Exception -> 0x00a0, ScannerException -> 0x0095 }
                    java.util.List<com.facebook.places.internal.WifiScanResult> r9 = r8.ambientWifi     // Catch:{ Exception -> 0x00a0, ScannerException -> 0x0095 }
                    r3.ambientWifi = r9     // Catch:{ Exception -> 0x00a0, ScannerException -> 0x0095 }
                L_0x0078:
                    if (r4 == 0) goto L_0x0088
                    java.lang.Object r6 = r4.get()     // Catch:{ Exception -> 0x00ae, ScannerException -> 0x0095 }
                    com.facebook.places.internal.LocationPackage r6 = (com.facebook.places.internal.LocationPackage) r6     // Catch:{ Exception -> 0x00ae, ScannerException -> 0x0095 }
                    com.facebook.places.internal.ScannerException$Type r9 = r6.locationError     // Catch:{ Exception -> 0x00ae, ScannerException -> 0x0095 }
                    r3.locationError = r9     // Catch:{ Exception -> 0x00ae, ScannerException -> 0x0095 }
                    android.location.Location r9 = r6.location     // Catch:{ Exception -> 0x00ae, ScannerException -> 0x0095 }
                    r3.location = r9     // Catch:{ Exception -> 0x00ae, ScannerException -> 0x0095 }
                L_0x0088:
                    com.facebook.places.internal.LocationPackageManager$Listener r9 = r3
                    r9.onLocationPackage(r3)
                    return
                L_0x008e:
                    r2 = move-exception
                    java.lang.String r9 = "Exception scanning for bluetooth beacons"
                    com.facebook.places.internal.LocationPackageManager.logException(r9, r2)     // Catch:{ ScannerException -> 0x0095, Exception -> 0x00a7 }
                    goto L_0x0064
                L_0x0095:
                    r2 = move-exception
                    java.lang.String r9 = "Exception scanning for locations"
                    com.facebook.places.internal.LocationPackageManager.logException(r9, r2)
                    com.facebook.places.internal.ScannerException$Type r9 = r2.type
                    r3.locationError = r9
                    goto L_0x0088
                L_0x00a0:
                    r2 = move-exception
                    java.lang.String r9 = "Exception scanning for wifi access points"
                    com.facebook.places.internal.LocationPackageManager.logException(r9, r2)     // Catch:{ ScannerException -> 0x0095, Exception -> 0x00a7 }
                    goto L_0x0078
                L_0x00a7:
                    r2 = move-exception
                    java.lang.String r9 = "Exception requesting a location package"
                    com.facebook.places.internal.LocationPackageManager.logException(r9, r2)
                    goto L_0x0088
                L_0x00ae:
                    r2 = move-exception
                    java.lang.String r9 = "Exception getting location"
                    com.facebook.places.internal.LocationPackageManager.logException(r9, r2)     // Catch:{ ScannerException -> 0x0095, Exception -> 0x00a7 }
                    goto L_0x0088
                */
                throw new UnsupportedOperationException("Method not decompiled: com.facebook.places.internal.LocationPackageManager.AnonymousClass1.run():void");
            }
        });
    }

    /* access modifiers changed from: private */
    public static FutureTask<LocationPackage> newLocationScanFuture(final LocationScanner locationScanner, LocationPackageRequestParams requestParams) {
        return new FutureTask<>(new Callable<LocationPackage>() {
            public LocationPackage call() throws Exception {
                LocationPackage locationPackage = new LocationPackage();
                try {
                    locationPackage.location = locationScanner.getLocation();
                } catch (ScannerException e) {
                    locationPackage.locationError = e.type;
                    LocationPackageManager.logException("Exception while getting location", e);
                } catch (Exception e2) {
                    locationPackage.locationError = ScannerException.Type.UNKNOWN_ERROR;
                }
                return locationPackage;
            }
        });
    }

    /* access modifiers changed from: private */
    public static FutureTask<LocationPackage> newBluetoothScanFuture(final LocationPackageRequestParams requestParams) {
        return new FutureTask<>(new Callable<LocationPackage>() {
            public LocationPackage call() throws Exception {
                BleScanner bleScanner;
                LocationPackage locationPackage = new LocationPackage();
                try {
                    bleScanner = ScannerFactory.newBleScanner(FacebookSdk.getApplicationContext(), requestParams);
                    bleScanner.initAndCheckEligibility();
                    bleScanner.startScanning();
                    try {
                        Thread.sleep(requestParams.getBluetoothScanDurationMs());
                    } catch (Exception e) {
                    }
                    bleScanner.stopScanning();
                    int errorCode = bleScanner.getErrorCode();
                    if (errorCode == 0) {
                        locationPackage.ambientBluetoothLe = bleScanner.getScanResults();
                        locationPackage.isBluetoothScanningEnabled = true;
                    } else {
                        if (FacebookSdk.isDebugEnabled()) {
                            Utility.logd(LocationPackageManager.TAG, String.format(Locale.getDefault(), "Bluetooth LE scan failed with error: %d", new Object[]{Integer.valueOf(errorCode)}));
                        }
                        locationPackage.isBluetoothScanningEnabled = false;
                    }
                } catch (Exception e2) {
                    LocationPackageManager.logException("Exception scanning for bluetooth beacons", e2);
                    locationPackage.isBluetoothScanningEnabled = false;
                } catch (Throwable th) {
                    bleScanner.stopScanning();
                    throw th;
                }
                return locationPackage;
            }
        });
    }

    /* access modifiers changed from: private */
    public static FutureTask<LocationPackage> newWifiScanFuture(final LocationPackageRequestParams requestParams) {
        return new FutureTask<>(new Callable<LocationPackage>() {
            public LocationPackage call() throws Exception {
                LocationPackage locationPackage = new LocationPackage();
                try {
                    WifiScanner wifiScanner = ScannerFactory.newWifiScanner(FacebookSdk.getApplicationContext(), requestParams);
                    wifiScanner.initAndCheckEligibility();
                    locationPackage.connectedWifi = wifiScanner.getConnectedWifi();
                    locationPackage.isWifiScanningEnabled = wifiScanner.isWifiScanningEnabled();
                    if (locationPackage.isWifiScanningEnabled) {
                        locationPackage.ambientWifi = wifiScanner.getWifiScans();
                    }
                } catch (Exception e) {
                    LocationPackageManager.logException("Exception scanning for wifi access points", e);
                    locationPackage.isWifiScanningEnabled = false;
                }
                return locationPackage;
            }
        });
    }

    /* access modifiers changed from: private */
    public static void logException(String message, Throwable throwable) {
        if (FacebookSdk.isDebugEnabled()) {
            Log.e(TAG, message, throwable);
        }
    }
}

package com.facebook.places.internal;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.facebook.FacebookSdk;
import com.facebook.internal.Validate;
import com.facebook.places.internal.ScannerException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@TargetApi(21)
public class BleScannerImpl implements BleScanner {
    private static final byte[] EDDYSTONE_PREFIX = fromHexString("16aafe");
    private static final byte[] GRAVITY_PREFIX = fromHexString("17ffab01");
    private static final byte[] IBEACON_PREFIX = fromHexString("ff4c000215");
    private static final String TAG = "BleScannerImpl";
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private Context context;
    /* access modifiers changed from: private */
    public int errorCode;
    private boolean isScanInProgress;
    private LocationPackageRequestParams params;
    private ScanCallBackImpl scanCallBack;
    /* access modifiers changed from: private */
    public final List<BluetoothScanResult> scanResults = new ArrayList();

    BleScannerImpl(Context context2, LocationPackageRequestParams params2) {
        this.context = context2;
        this.params = params2;
    }

    public synchronized void initAndCheckEligibility() throws ScannerException {
        if (Build.VERSION.SDK_INT < 21) {
            throw new ScannerException(ScannerException.Type.NOT_SUPPORTED);
        } else if (!Validate.hasBluetoothPermission(this.context)) {
            throw new ScannerException(ScannerException.Type.PERMISSION_DENIED);
        } else if (!Validate.hasLocationPermission(this.context)) {
            throw new ScannerException(ScannerException.Type.PERMISSION_DENIED);
        } else {
            this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (this.bluetoothAdapter == null || !this.bluetoothAdapter.isEnabled()) {
                throw new ScannerException(ScannerException.Type.DISABLED);
            }
            this.bluetoothLeScanner = this.bluetoothAdapter.getBluetoothLeScanner();
            if (this.bluetoothLeScanner == null) {
                throw new ScannerException(ScannerException.Type.UNKNOWN_ERROR);
            }
        }
    }

    public synchronized void startScanning() throws ScannerException {
        if (this.isScanInProgress) {
            throw new ScannerException(ScannerException.Type.SCAN_ALREADY_IN_PROGRESS);
        }
        this.scanCallBack = new ScanCallBackImpl();
        this.isScanInProgress = true;
        this.errorCode = 0;
        synchronized (this.scanResults) {
            this.scanResults.clear();
        }
        if (this.bluetoothLeScanner == null) {
            throw new ScannerException(ScannerException.Type.UNKNOWN_ERROR);
        }
        try {
            ScanSettings.Builder builder = new ScanSettings.Builder();
            builder.setScanMode(2);
            builder.setReportDelay(0);
            this.bluetoothLeScanner.startScan((List) null, builder.build(), this.scanCallBack);
            this.isScanInProgress = true;
        } catch (Exception e) {
            throw new ScannerException(ScannerException.Type.UNKNOWN_ERROR);
        }
    }

    public synchronized void stopScanning() {
        this.bluetoothLeScanner.flushPendingScanResults(this.scanCallBack);
        this.bluetoothLeScanner.stopScan(this.scanCallBack);
        waitForMainLooper(this.params.getBluetoothFlushResultsTimeoutMs());
        this.isScanInProgress = false;
    }

    private void waitForMainLooper(long maxWaitTimeoutMs) {
        try {
            final Object lock = new Object();
            synchronized (lock) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        try {
                            synchronized (lock) {
                                lock.notify();
                            }
                        } catch (Exception e) {
                            BleScannerImpl.logException("Exception waiting for main looper", e);
                        }
                    }
                });
                lock.wait(maxWaitTimeoutMs);
            }
        } catch (Exception e) {
            logException("Exception waiting for main looper", e);
        }
    }

    public synchronized int getErrorCode() {
        return this.errorCode;
    }

    public synchronized List<BluetoothScanResult> getScanResults() {
        List<BluetoothScanResult> output;
        synchronized (this.scanResults) {
            int maxSanResults = this.params.getBluetoothMaxScanResults();
            if (this.scanResults.size() > maxSanResults) {
                output = new ArrayList<>(maxSanResults);
                Collections.sort(this.scanResults, new Comparator<BluetoothScanResult>() {
                    public int compare(BluetoothScanResult lhs, BluetoothScanResult rhs) {
                        return rhs.rssi - lhs.rssi;
                    }
                });
                output.addAll(this.scanResults.subList(0, maxSanResults));
            } else {
                output = new ArrayList<>(this.scanResults.size());
                output.addAll(this.scanResults);
            }
        }
        return output;
    }

    private class ScanCallBackImpl extends ScanCallback {
        private ScanCallBackImpl() {
        }

        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            int unused = BleScannerImpl.this.errorCode = errorCode;
        }

        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
            try {
                synchronized (BleScannerImpl.this.scanResults) {
                    for (ScanResult result : results) {
                        BluetoothScanResult bluetoothScanResult = BleScannerImpl.newBluetoothScanResult(result);
                        if (bluetoothScanResult != null) {
                            BleScannerImpl.this.scanResults.add(bluetoothScanResult);
                        }
                    }
                }
            } catch (Exception e) {
                BleScannerImpl.logException("Exception in ble scan callback", e);
            }
        }

        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            try {
                synchronized (BleScannerImpl.this.scanResults) {
                    BluetoothScanResult bluetoothScanResult = BleScannerImpl.newBluetoothScanResult(result);
                    if (bluetoothScanResult != null) {
                        BleScannerImpl.this.scanResults.add(bluetoothScanResult);
                    }
                }
            } catch (Exception e) {
                BleScannerImpl.logException("Exception in ble scan callback", e);
            }
        }
    }

    /* access modifiers changed from: private */
    public static BluetoothScanResult newBluetoothScanResult(ScanResult scanResult) {
        ScanRecord scanRecord = scanResult.getScanRecord();
        if (isBeacon(scanRecord.getBytes())) {
            return new BluetoothScanResult(formatPayload(scanRecord.getBytes()), scanResult.getRssi(), scanResult.getTimestampNanos());
        }
        return null;
    }

    private static String formatPayload(byte[] payload) {
        if (payload == null || payload.length == 0) {
            return null;
        }
        return toHexString(payload, getPayloadLength(payload));
    }

    private static int getPayloadLength(byte[] payload) {
        int offset = 0;
        while (offset < payload.length) {
            byte length = payload[offset];
            if (length == 0) {
                return offset;
            }
            if (length < 0) {
                return payload.length;
            }
            offset += length + 1;
        }
        return payload.length;
    }

    private static String toHexString(byte[] bytes, int length) {
        StringBuffer sb = new StringBuffer();
        if (length < 0 || length > bytes.length) {
            length = bytes.length;
        }
        for (int i = 0; i < length; i++) {
            sb.append(String.format("%02x", new Object[]{Byte.valueOf(bytes[i])}));
        }
        return sb.toString();
    }

    /* access modifiers changed from: private */
    public static void logException(String message, Exception e) {
        if (FacebookSdk.isDebugEnabled()) {
            Log.e(TAG, message, e);
        }
    }

    private static boolean isBeacon(byte[] payload) {
        if (payload == null) {
            return false;
        }
        int startIndex = 0;
        int payloadLength = payload.length;
        while (startIndex < payloadLength) {
            byte advLengthField = payload[startIndex];
            if (advLengthField <= 0) {
                return false;
            }
            int advPacketLength = advLengthField + 1;
            if (startIndex + advPacketLength > payloadLength) {
                return false;
            }
            if (isAdvPacketBeacon(payload, startIndex)) {
                return true;
            }
            startIndex += advPacketLength;
        }
        return false;
    }

    private static boolean isAdvPacketBeacon(byte[] payload, int advStartIndex) {
        if (!isArrayContained(payload, advStartIndex + 1, IBEACON_PREFIX) && !isArrayContained(payload, advStartIndex + 1, EDDYSTONE_PREFIX) && !isArrayContained(payload, advStartIndex, GRAVITY_PREFIX) && !isAltBeacon(payload, advStartIndex)) {
            return false;
        }
        return true;
    }

    private static boolean isAltBeacon(byte[] payload, int startIndex) {
        if (startIndex + 5 >= payload.length) {
            return false;
        }
        byte length = payload[startIndex];
        byte packetType = payload[startIndex + 1];
        byte beaconCode1 = payload[startIndex + 4];
        byte beaconCode2 = payload[startIndex + 5];
        if (length == 27 && packetType == -1 && beaconCode1 == -66 && beaconCode2 == -84) {
            return true;
        }
        return false;
    }

    private static boolean isArrayContained(byte[] array1, int startIndex1, byte[] array2) {
        int length = array2.length;
        if (startIndex1 + length > array1.length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (array1[startIndex1 + i] != array2[i]) {
                return false;
            }
        }
        return true;
    }

    private static byte[] fromHexString(String hexString) {
        int len = hexString.length();
        byte[] bytes = new byte[(len / 2)];
        for (int i = 0; i < len; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return bytes;
    }
}

package com.facebook.devicerequests.internal;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Build;
import com.facebook.FacebookSdk;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.SmartLoginOption;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class DeviceRequestsHelper {
    static final String DEVICE_INFO_DEVICE = "device";
    static final String DEVICE_INFO_MODEL = "model";
    public static final String DEVICE_INFO_PARAM = "device_info";
    static final String SDK_FLAVOR = "android";
    static final String SDK_HEADER = "fbsdk";
    static final String SERVICE_TYPE = "_fb._tcp.";
    private static HashMap<String, NsdManager.RegistrationListener> deviceRequestsListeners = new HashMap<>();

    public static String getDeviceInfo() {
        JSONObject deviceInfo = new JSONObject();
        try {
            deviceInfo.put(DEVICE_INFO_DEVICE, Build.DEVICE);
            deviceInfo.put(DEVICE_INFO_MODEL, Build.MODEL);
        } catch (JSONException e) {
        }
        return deviceInfo.toString();
    }

    public static boolean startAdvertisementService(String userCode) {
        if (isAvailable()) {
            return startAdvertisementServiceImpl(userCode);
        }
        return false;
    }

    public static boolean isAvailable() {
        return Build.VERSION.SDK_INT >= 16 && FetchedAppSettingsManager.getAppSettingsWithoutQuery(FacebookSdk.getApplicationId()).getSmartLoginOptions().contains(SmartLoginOption.Enabled);
    }

    public static Bitmap generateQRCode(String url) {
        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.MARGIN, 2);
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 200, 200, hints);
            int h = matrix.getHeight();
            int w = matrix.getWidth();
            int[] pixels = new int[(h * w)];
            for (int i = 0; i < h; i++) {
                int offset = i * w;
                for (int j = 0; j < w; j++) {
                    pixels[offset + j] = matrix.get(j, i) ? -16777216 : -1;
                }
            }
            Bitmap qrCode = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            qrCode.setPixels(pixels, 0, w, 0, 0, w, h);
            return qrCode;
        } catch (WriterException e) {
            return null;
        }
    }

    public static void cleanUpAdvertisementService(String userCode) {
        cleanUpAdvertisementServiceImpl(userCode);
    }

    @TargetApi(16)
    private static boolean startAdvertisementServiceImpl(final String userCode) {
        if (!deviceRequestsListeners.containsKey(userCode)) {
            final String nsdServiceName = String.format("%s_%s_%s", new Object[]{SDK_HEADER, String.format("%s-%s", new Object[]{"android", FacebookSdk.getSdkVersion().replace('.', '|')}), userCode});
            NsdServiceInfo nsdLoginAdvertisementService = new NsdServiceInfo();
            nsdLoginAdvertisementService.setServiceType(SERVICE_TYPE);
            nsdLoginAdvertisementService.setServiceName(nsdServiceName);
            nsdLoginAdvertisementService.setPort(80);
            NsdManager.RegistrationListener nsdRegistrationListener = new NsdManager.RegistrationListener() {
                public void onServiceRegistered(NsdServiceInfo NsdServiceInfo) {
                    if (!nsdServiceName.equals(NsdServiceInfo.getServiceName())) {
                        DeviceRequestsHelper.cleanUpAdvertisementService(userCode);
                    }
                }

                public void onServiceUnregistered(NsdServiceInfo serviceInfo) {
                }

                public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                    DeviceRequestsHelper.cleanUpAdvertisementService(userCode);
                }

                public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                }
            };
            deviceRequestsListeners.put(userCode, nsdRegistrationListener);
            ((NsdManager) FacebookSdk.getApplicationContext().getSystemService("servicediscovery")).registerService(nsdLoginAdvertisementService, 1, nsdRegistrationListener);
        }
        return true;
    }

    @TargetApi(16)
    private static void cleanUpAdvertisementServiceImpl(String userCode) {
        NsdManager.RegistrationListener nsdRegistrationListener = deviceRequestsListeners.get(userCode);
        if (nsdRegistrationListener != null) {
            ((NsdManager) FacebookSdk.getApplicationContext().getSystemService("servicediscovery")).unregisterService(nsdRegistrationListener);
            deviceRequestsListeners.remove(userCode);
        }
    }
}

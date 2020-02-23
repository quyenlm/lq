package com.tencent.midas.oversea.safe;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APDataInterface;
import com.tencent.midas.oversea.comm.APDataStorage;
import com.tencent.midas.oversea.comm.APGlobalInfo;
import com.tencent.midas.oversea.comm.APToolAES;

public class APSecretKeyManager {
    private static volatile APSecretKeyManager b = null;
    private Context a;

    private APSecretKeyManager() {
    }

    private String a() {
        String substring = "caUdsBbJ1oOxMbPy".substring(0, 4);
        String substring2 = "caUdsBbJ1oOxMbPy".substring(4, 8);
        String substring3 = "caUdsBbJ1oOxMbPy".substring(8, 12);
        String substring4 = "caUdsBbJ1oOxMbPy".substring(12, 16);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(substring3);
        stringBuffer.append(substring2);
        stringBuffer.append(substring);
        stringBuffer.append(substring4);
        return stringBuffer.toString();
    }

    public static APSecretKeyManager getInstance(Context context) {
        if (b == null) {
            synchronized (APSecretKeyManager.class) {
                if (b == null) {
                    b = new APSecretKeyManager();
                    b.a = context;
                    String readSecretKey = b.readSecretKey(APDataInterface.singleton().getUserInfo().openId);
                    if (readSecretKey == null) {
                        readSecretKey = "";
                    }
                    APAppDataInterface.singleton().setSecretKey(readSecretKey);
                }
            }
        }
        return b;
    }

    public static void release() {
        b = null;
    }

    public String readCryptKey(String str) {
        String secretKey = APAppDataInterface.singleton().getSecretKey();
        String str2 = "";
        String data = new APDataStorage().getData(this.a, APGlobalInfo.SharedPreferencesTag, APGlobalInfo.SDK_VERSION + "_" + APAppDataInterface.singleton().getOfferid() + "_" + str + "_CryptEncodeKey");
        if (!data.equals("")) {
            str2 = APToolAES.doDecode(data, secretKey);
        }
        return str2 == null ? "" : str2;
    }

    public String readCryptKeyTime(String str) {
        return new APDataStorage().getData(this.a, APGlobalInfo.SharedPreferencesTag, APGlobalInfo.SDK_VERSION + "_" + APAppDataInterface.singleton().getOfferid() + "_" + str + "_CryptEncodeKeyTime");
    }

    public String readSecretKey(String str) {
        String a2 = a();
        String str2 = "";
        String data = new APDataStorage().getData(this.a, APGlobalInfo.SharedPreferencesTag, APGlobalInfo.SDK_VERSION + "_" + APAppDataInterface.singleton().getOfferid() + "_" + str + "_SecretEncodeKey");
        if (!data.equals("")) {
            str2 = APToolAES.doDecode(data, a2);
        }
        return str2 == null ? "" : str2;
    }

    public void saveCryptKey(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            String secretKey = APAppDataInterface.singleton().getSecretKey();
            new APDataStorage().storeData(this.a, APGlobalInfo.SharedPreferencesTag, APGlobalInfo.SDK_VERSION + "_" + APAppDataInterface.singleton().getOfferid() + "_" + str + "_CryptEncodeKey", APToolAES.doEncode(str2, secretKey));
        }
    }

    public void saveCryptKeyTime(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            new APDataStorage().storeData(this.a, APGlobalInfo.SharedPreferencesTag, APGlobalInfo.SDK_VERSION + "_" + APAppDataInterface.singleton().getOfferid() + "_" + str + "_CryptEncodeKeyTime", str2);
        }
    }

    public void saveSecretKey(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            String a2 = a();
            new APDataStorage().storeData(this.a, APGlobalInfo.SharedPreferencesTag, APGlobalInfo.SDK_VERSION + "_" + APAppDataInterface.singleton().getOfferid() + "_" + str + "_SecretEncodeKey", APToolAES.doEncode(str2, a2));
        }
    }
}

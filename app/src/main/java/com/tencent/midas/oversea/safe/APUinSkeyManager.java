package com.tencent.midas.oversea.safe;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APDataStorage;
import com.tencent.midas.oversea.comm.APToolAES;

public class APUinSkeyManager {
    private static volatile APUinSkeyManager c = null;
    private final String a = "caUdsBbJ1oOxMbPy";
    private Context b;

    private APUinSkeyManager() {
    }

    private String a() {
        c.getClass();
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

    public static APUinSkeyManager getInstance(Context context) {
        if (c == null) {
            synchronized (APUinSkeyManager.class) {
                if (c == null) {
                    c = new APUinSkeyManager();
                    c.b = context;
                }
            }
        }
        return c;
    }

    public String readAuthKey(String str) {
        APDataStorage aPDataStorage = new APDataStorage();
        String data = aPDataStorage.getData(this.b, "TencentUnipayPayIdKey", APAppDataInterface.singleton().getOfferid() + "_authKey_" + str);
        String data2 = aPDataStorage.getData(this.b, "TencentUnipayPayIdKey", APAppDataInterface.singleton().getOfferid() + "_authKeyLen_" + str);
        try {
            return (TextUtils.isEmpty(data) || TextUtils.isEmpty(data2)) ? "" : APToolAES.doDecode(data, a()).substring(0, Integer.parseInt(data2));
        } catch (Exception e) {
            return "";
        }
    }

    public String readPayId(String str) {
        return new APDataStorage().getData(this.b, "TencentUnipayPayIdKey", APAppDataInterface.singleton().getOfferid() + "_payId_" + str);
    }

    public void saveAuthKey(String str, String str2, int i) {
        if (!TextUtils.isEmpty(str2)) {
            String doEncode = APToolAES.doEncode(str2, a());
            APDataStorage aPDataStorage = new APDataStorage();
            aPDataStorage.storeData(this.b, "TencentUnipayPayIdKey", APAppDataInterface.singleton().getOfferid() + "_authKey_" + str, doEncode);
            aPDataStorage.storeData(this.b, "TencentUnipayPayIdKey", APAppDataInterface.singleton().getOfferid() + "_authKeyLen_" + str, String.valueOf(i));
        }
    }

    public void savePayId(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            new APDataStorage().storeData(this.b, "TencentUnipayPayIdKey", APAppDataInterface.singleton().getOfferid() + "_payId_" + str, str2);
        }
    }
}

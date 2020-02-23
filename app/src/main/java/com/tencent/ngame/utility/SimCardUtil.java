package com.tencent.ngame.utility;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class SimCardUtil {
    static final String CHIP_NAME_MTK = "MTK芯片";
    static final String CHIP_NAME_QCOM = "高通芯片";
    static final String CHIP_NAME_SINGLE = "单卡芯片";
    static final String CHIP_NAME_SPREADTRUM = "展讯芯片";
    private String carrierName_1 = "";
    private String carrierName_2 = "";
    private String imei_1 = "";
    private String imei_2 = "";
    private String imsi_1 = "";
    private String imsi_2 = "";
    private Context mContext;
    private Integer simId_1 = 0;
    private Integer simId_2 = 1;

    enum CHIP_TYPE {
        MTK,
        MTK2,
        SPREADTRUM,
        QCOM,
        SINGLE
    }

    public SimCardUtil(Context mContext2) {
        this.mContext = mContext2;
    }

    public SIMInfo getSimInfo() {
        SIMInfo imsInfo = initQualcommDoubleSim();
        if (imsInfo != null) {
            SIMInfo sIMInfo = imsInfo;
            return imsInfo;
        }
        SIMInfo imsInfo2 = initMtkDoubleSim();
        if (imsInfo2 != null) {
            SIMInfo sIMInfo2 = imsInfo2;
            return imsInfo2;
        }
        SIMInfo imsInfo3 = initMtkSecondDoubleSim();
        if (imsInfo3 != null) {
            SIMInfo sIMInfo3 = imsInfo3;
            return imsInfo3;
        }
        SIMInfo imsInfo4 = initSpreadDoubleSim();
        if (imsInfo4 != null) {
            SIMInfo sIMInfo4 = imsInfo4;
            return imsInfo4;
        }
        SIMInfo imsInfo5 = getIMSI();
        if (imsInfo5 == null) {
            return null;
        }
        SIMInfo sIMInfo5 = imsInfo5;
        return imsInfo5;
    }

    public SIMInfo initMtkDoubleSim() {
        try {
            TelephonyManager tm = (TelephonyManager) this.mContext.getSystemService("phone");
            Class<?> c = Class.forName("com.android.internal.telephony.Phone");
            Field fields1 = c.getField("GEMINI_SIM_1");
            fields1.setAccessible(true);
            this.simId_1 = (Integer) fields1.get((Object) null);
            Field fields2 = c.getField("GEMINI_SIM_2");
            fields2.setAccessible(true);
            this.simId_2 = (Integer) fields2.get((Object) null);
            Method m = TelephonyManager.class.getDeclaredMethod("getSubscriberIdGemini", new Class[]{Integer.TYPE});
            this.imsi_1 = (String) m.invoke(tm, new Object[]{this.simId_1});
            this.imsi_2 = (String) m.invoke(tm, new Object[]{this.simId_2});
            Method m1 = TelephonyManager.class.getDeclaredMethod("getDeviceIdGemini", new Class[]{Integer.TYPE});
            this.imei_1 = (String) m1.invoke(tm, new Object[]{this.simId_1});
            this.imei_2 = (String) m1.invoke(tm, new Object[]{this.simId_2});
            Method mc = TelephonyManager.class.getDeclaredMethod("getCarrierName", new Class[]{Integer.TYPE});
            this.carrierName_1 = (String) mc.invoke(tm, new Object[]{this.simId_1});
            this.carrierName_2 = (String) mc.invoke(tm, new Object[]{this.simId_2});
            SIMInfo imsInfo = new SIMInfo(CHIP_TYPE.MTK, CHIP_NAME_MTK);
            try {
                imsInfo.addSimInfo(this.imsi_1, this.imei_1, this.carrierName_1);
                imsInfo.addSimInfo(this.imsi_2, this.imei_2, this.carrierName_2);
                SIMInfo sIMInfo = imsInfo;
                return imsInfo;
            } catch (Exception e) {
                e = e;
                SIMInfo sIMInfo2 = imsInfo;
                e.printStackTrace();
                return null;
            }
        } catch (Exception e2) {
            e = e2;
            e.printStackTrace();
            return null;
        }
    }

    public SIMInfo initMtkSecondDoubleSim() {
        try {
            TelephonyManager tm = (TelephonyManager) this.mContext.getSystemService("phone");
            Class<?> c = Class.forName("com.android.internal.telephony.Phone");
            Field fields1 = c.getField("GEMINI_SIM_1");
            fields1.setAccessible(true);
            this.simId_1 = (Integer) fields1.get((Object) null);
            Field fields2 = c.getField("GEMINI_SIM_2");
            fields2.setAccessible(true);
            this.simId_2 = (Integer) fields2.get((Object) null);
            Method mx = TelephonyManager.class.getMethod("getDefault", new Class[]{Integer.TYPE});
            TelephonyManager tm1 = (TelephonyManager) mx.invoke(tm, new Object[]{this.simId_1});
            TelephonyManager tm2 = (TelephonyManager) mx.invoke(tm, new Object[]{this.simId_2});
            this.imsi_1 = tm1.getSubscriberId();
            this.imsi_2 = tm2.getSubscriberId();
            this.imei_1 = tm1.getDeviceId();
            this.imei_2 = tm2.getDeviceId();
            this.carrierName_1 = tm1.getSimOperatorName();
            this.carrierName_2 = tm.getSimOperatorName();
            SIMInfo imsInfo = new SIMInfo(CHIP_TYPE.MTK2, CHIP_NAME_MTK);
            try {
                imsInfo.addSimInfo(this.imsi_1, this.imei_1, this.carrierName_1);
                imsInfo.addSimInfo(this.imsi_2, this.imei_2, this.carrierName_2);
                SIMInfo sIMInfo = imsInfo;
                return imsInfo;
            } catch (Exception e) {
                e = e;
                SIMInfo sIMInfo2 = imsInfo;
                e.printStackTrace();
                return null;
            }
        } catch (Exception e2) {
            e = e2;
            e.printStackTrace();
            return null;
        }
    }

    public SIMInfo initSpreadDoubleSim() {
        try {
            Class<?> c = Class.forName("com.android.internal.telephony.PhoneFactory");
            TelephonyManager tm = (TelephonyManager) this.mContext.getSystemService("phone");
            this.imsi_1 = tm.getSubscriberId();
            this.imei_1 = tm.getDeviceId();
            this.carrierName_1 = tm.getSimOperatorName();
            Context context = this.mContext;
            TelephonyManager tm1 = (TelephonyManager) context.getSystemService((String) c.getMethod("getServiceName", new Class[]{String.class, Integer.TYPE}).invoke(c, new Object[]{"phone", 1}));
            this.imsi_2 = tm1.getSubscriberId();
            this.imei_2 = tm1.getDeviceId();
            this.carrierName_2 = tm1.getSimOperatorName();
            SIMInfo imsInfo = new SIMInfo(CHIP_TYPE.SPREADTRUM, CHIP_NAME_SPREADTRUM);
            try {
                imsInfo.addSimInfo(this.imsi_1, this.imei_1, this.carrierName_1);
                imsInfo.addSimInfo(this.imsi_2, this.imei_2, this.carrierName_2);
                SIMInfo sIMInfo = imsInfo;
                return imsInfo;
            } catch (Exception e) {
                e = e;
                SIMInfo sIMInfo2 = imsInfo;
                e.printStackTrace();
                return null;
            }
        } catch (Exception e2) {
            e = e2;
            e.printStackTrace();
            return null;
        }
    }

    public SIMInfo initQualcommDoubleSim() {
        try {
            Class<?> cx = Class.forName("android.telephony.MSimTelephonyManager");
            Object obj = this.mContext.getSystemService("phone_msim");
            Method md = cx.getMethod("getDeviceId", new Class[]{Integer.TYPE});
            Method ms = cx.getMethod("getSubscriberId", new Class[]{Integer.TYPE});
            Method mc = cx.getMethod("getSimOperatorName", new Class[]{Integer.TYPE});
            this.imei_1 = (String) md.invoke(obj, new Object[]{this.simId_1});
            this.imei_2 = (String) md.invoke(obj, new Object[]{this.simId_2});
            this.carrierName_1 = (String) mc.invoke(obj, new Object[]{this.simId_1});
            this.carrierName_2 = (String) mc.invoke(obj, new Object[]{this.simId_2});
            this.imsi_1 = (String) ms.invoke(obj, new Object[]{this.simId_1});
            this.imsi_2 = (String) ms.invoke(obj, new Object[]{this.simId_2});
            int statephoneType_2 = 0;
            boolean flag = false;
            try {
                Method mx = cx.getMethod("getPreferredDataSubscription", new Class[]{Integer.TYPE});
                Method is = cx.getMethod("isMultiSimEnabled", new Class[]{Integer.TYPE});
                statephoneType_2 = ((Integer) mx.invoke(obj, new Object[0])).intValue();
                flag = ((Boolean) is.invoke(obj, new Object[0])).booleanValue();
            } catch (Exception e) {
                Log.e("SimINfo", "高通芯片判断");
                e.printStackTrace();
            }
            SIMInfo imsInfo = new SIMInfo(CHIP_TYPE.QCOM, "高通芯片高通芯片-getPreferredDataSubscription:" + statephoneType_2 + ",flag:" + flag);
            try {
                imsInfo.addSimInfo(this.imsi_1, this.imei_1, this.carrierName_1);
                imsInfo.addSimInfo(this.imsi_2, this.imei_2, this.carrierName_2);
                SIMInfo sIMInfo = imsInfo;
                return imsInfo;
            } catch (Exception e2) {
                e = e2;
                SIMInfo sIMInfo2 = imsInfo;
                e.printStackTrace();
                return null;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    public SIMInfo getIMSI() {
        try {
            TelephonyManager tm = (TelephonyManager) this.mContext.getSystemService("phone");
            this.imsi_1 = tm.getSubscriberId();
            this.imei_1 = tm.getDeviceId();
            this.carrierName_1 = tm.getSimOperatorName();
            if (TextUtils.isEmpty(this.imsi_1) || this.imsi_1.length() < 10) {
                return null;
            }
            SIMInfo imsInfo = new SIMInfo(CHIP_TYPE.SINGLE, CHIP_NAME_SINGLE);
            imsInfo.addSimInfo(this.imsi_1, this.imei_1, this.carrierName_1);
            SIMInfo sIMInfo = imsInfo;
            return imsInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public class SingleSIMInfo {
        public String carrierName;
        public String imei;
        public String imsi;

        public SingleSIMInfo(String imsi2, String imei2, String carrierName2) {
            this.imsi = imsi2;
            this.imei = imei2;
            this.carrierName = carrierName2;
        }
    }

    public class SIMInfo {
        public String chipName;
        public CHIP_TYPE chipType;
        public ArrayList<SingleSIMInfo> l_ssim_info = new ArrayList<>();

        public SIMInfo(CHIP_TYPE type, String name) {
            this.chipType = type;
            this.chipName = name;
        }

        public void addSimInfo(String imsi, String imei, String carrierName) {
            this.l_ssim_info.add(new SingleSIMInfo(imsi, imei, carrierName));
        }
    }
}

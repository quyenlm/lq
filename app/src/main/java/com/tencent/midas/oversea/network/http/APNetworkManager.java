package com.tencent.midas.oversea.network.http;

import com.tencent.midas.oversea.comm.APDataReportManager;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.network.modle.APDataReportAns;
import com.tencent.midas.oversea.network.modle.APDataReportOnceAns;
import com.tencent.midas.oversea.network.modle.APDataReportReq;
import com.tencent.midas.oversea.network.modle.APGetIPListAns;
import com.tencent.midas.oversea.network.modle.APGetIPListReq;
import com.tencent.midas.oversea.network.modle.APGetKeyAns;
import com.tencent.midas.oversea.network.modle.APGetKeyReq;
import com.tencent.midas.oversea.network.modle.APOverSeaInfoAns;
import com.tencent.midas.oversea.network.modle.APOverSeaInfoReq;
import com.tencent.midas.oversea.network.modle.APOverSeaOrderAns;
import com.tencent.midas.oversea.network.modle.APOverSeaOrderReq;
import com.tencent.midas.oversea.network.modle.APOverSeaProvideAns;
import com.tencent.midas.oversea.network.modle.APOverSeaProvideReq;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class APNetworkManager {
    public static final String HTTP_KEY_DATAREPORT = "datareport";
    public static final String HTTP_KEY_GETIPLIST = "getIPlist";
    public static final String HTTP_KEY_GET_KEY = "getkey";
    public static final String HTTP_KEY_OVERSEAINFO = "overseainfo";
    public static final String HTTP_KEY_OVERSEAORDER = "overseaorder";
    public static final String HTTP_KEY_OVERSEAPROVIDE = "overseaprovide";
    private static APNetworkManager a = null;
    private HashMap<String, APBaseHttpReq> b = new HashMap<>();

    private APNetworkManager() {
    }

    public static void cancelRequest(String str) {
        APBaseHttpReq aPBaseHttpReq = a.b.get(str);
        if (aPBaseHttpReq != null) {
            aPBaseHttpReq.stopRequest();
            a.b.remove(str);
        }
    }

    public static APNetworkManager getInstance() {
        if (a == null) {
            synchronized (APNetworkManager.class) {
                if (a == null) {
                    a = new APNetworkManager();
                }
            }
        }
        return a;
    }

    public static void release() {
        a = null;
    }

    public void cancelPreRequest() {
        if (a.b != null) {
            ArrayList arrayList = new ArrayList();
            APLog.d("APNetworkManager", "gInstance.httpReqMap size:" + a.b.size());
            for (Map.Entry<String, APBaseHttpReq> value : a.b.entrySet()) {
                arrayList.add((APBaseHttpReq) value.getValue());
            }
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                ((APBaseHttpReq) arrayList.get(i)).stopRequest();
            }
            a.b.clear();
        }
    }

    public void dataReport(IAPHttpAnsObserver iAPHttpAnsObserver) {
        ArrayList arrayList = new ArrayList();
        int logRecord = APDataReportManager.getInstance().getLogRecord(arrayList);
        APLog.i("APDataReportManager", "num = " + logRecord);
        for (int i = 0; i < logRecord; i++) {
            String str = (String) arrayList.get(i);
            try {
                Class<?> cls = Class.forName("com.pay.test.APDataReportTest");
                if (cls != null) {
                    cls.getDeclaredMethod("setData", new Class[]{String.class}).invoke(cls, new Object[]{str});
                }
            } catch (Exception e) {
                APLog.w("APDataReportTest error", e.toString());
            }
            APDataReportReq aPDataReportReq = new APDataReportReq();
            aPDataReportReq.setHttpAns(new APDataReportAns(APHttpHandle.getIntanceHandel(), iAPHttpAnsObserver, this.b, "datareport"));
            aPDataReportReq.startService(str);
        }
        APDataReportManager.getInstance().clearData();
    }

    public void dataReport(String str, IAPHttpAnsObserver iAPHttpAnsObserver) {
        APDataReportReq aPDataReportReq = new APDataReportReq();
        aPDataReportReq.setHttpAns(new APDataReportAns(APHttpHandle.getIntanceHandel(), iAPHttpAnsObserver, this.b, "datareport"));
        aPDataReportReq.startService(str);
    }

    public void dataReport(String str, String str2, IAPHttpReportObserver iAPHttpReportObserver) {
        APDataReportReq aPDataReportReq = new APDataReportReq();
        aPDataReportReq.setHttpAns(new APDataReportOnceAns(iAPHttpReportObserver));
        aPDataReportReq.startService(str);
    }

    public void getIpList(IAPHttpAnsObserver iAPHttpAnsObserver) {
        APGetIPListReq aPGetIPListReq = new APGetIPListReq();
        aPGetIPListReq.setHttpAns(new APGetIPListAns(APHttpHandle.getIntanceHandel(), iAPHttpAnsObserver, this.b, HTTP_KEY_GETIPLIST));
        aPGetIPListReq.startService();
    }

    public void getKey(int i, int i2, IAPHttpAnsObserver iAPHttpAnsObserver) {
        APGetKeyReq aPGetKeyReq = new APGetKeyReq();
        aPGetKeyReq.setHttpAns(new APGetKeyAns(APHttpHandle.getIntanceHandel(), new APHttpInstrument(HTTP_KEY_GET_KEY, iAPHttpAnsObserver), this.b, HTTP_KEY_GET_KEY));
        aPGetKeyReq.starService(i, i2);
    }

    public APBaseHttpReq getNetWorkBykey(String str) {
        return this.b.get(str);
    }

    public void overSeanInfo(String str, String str2, String str3, String str4, String str5, String str6, IAPHttpAnsObserver iAPHttpAnsObserver) {
        APOverSeaInfoReq aPOverSeaInfoReq = new APOverSeaInfoReq(str, str2, str3, str4, str5, str6);
        aPOverSeaInfoReq.setHttpAns(new APOverSeaInfoAns(APHttpHandle.getIntanceHandel(), new APHttpInstrument(HTTP_KEY_OVERSEAINFO, iAPHttpAnsObserver), this.b, HTTP_KEY_OVERSEAINFO));
        aPOverSeaInfoReq.startService();
    }

    public void overSeanOrder(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, IAPHttpAnsObserver iAPHttpAnsObserver) {
        APOverSeaOrderReq aPOverSeaOrderReq = new APOverSeaOrderReq(str, str2, str3, str4, str5, str6, str7, str8, str10, str9);
        aPOverSeaOrderReq.setHttpAns(new APOverSeaOrderAns(APHttpHandle.getIntanceHandel(), new APHttpInstrument(HTTP_KEY_OVERSEAORDER, iAPHttpAnsObserver), this.b, HTTP_KEY_OVERSEAORDER));
        aPOverSeaOrderReq.startService();
    }

    public void overSeanProvider(boolean z, String str, String str2, String str3, String str4, String str5, String str6, String str7, IAPHttpAnsObserver iAPHttpAnsObserver) {
        APOverSeaProvideReq aPOverSeaProvideReq = new APOverSeaProvideReq(z, str, str2, str3, str4, str5, str6, str7);
        aPOverSeaProvideReq.setHttpAns(new APOverSeaProvideAns(APHttpHandle.getIntanceHandel(), new APHttpInstrument(HTTP_KEY_OVERSEAPROVIDE, iAPHttpAnsObserver), this.b, HTTP_KEY_OVERSEAPROVIDE));
        aPOverSeaProvideReq.startService();
    }

    public void stopNetWorkBykey(String str) {
        APBaseHttpReq aPBaseHttpReq = this.b.get(str);
        if (aPBaseHttpReq != null) {
            aPBaseHttpReq.stopRequest();
        }
    }
}

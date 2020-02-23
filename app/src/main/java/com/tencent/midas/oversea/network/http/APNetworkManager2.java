package com.tencent.midas.oversea.network.http;

import com.tencent.midas.oversea.comm.APDataReportManager;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.network.modle.APDataReportAns;
import com.tencent.midas.oversea.network.modle.APDataReportOnceAns;
import com.tencent.midas.oversea.network.modle.APDataReportReq;
import com.tencent.midas.oversea.network.modle.APOverSeaCommAns;
import com.tencent.midas.oversea.network.modle.APOverSeaCommReq;
import com.tencent.midas.oversea.network.modle.APOverSeaInitAns;
import com.tencent.midas.oversea.network.modle.APOverSeaInitReq;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class APNetworkManager2 {
    public static final String HTTP_KEY_DATAREPORT = "datareport";
    public static final String HTTP_KEY_GETIPLIST = "get_ip";
    public static final String HTTP_KEY_GET_KEY = "get_key";
    public static final String HTTP_KEY_OVERSEA = "oversea";
    public static final String HTTP_KEY_OVERSEAINFO = "info";
    public static final String HTTP_KEY_OVERSEAINIT = "overseainit";
    public static final String HTTP_KEY_OVERSEAORDER = "order";
    public static final String HTTP_KEY_OVERSEAPROVIDE = "provide";
    public static final String HTTP_KEY_OVERSEARESTORE = "restore";
    private static APNetworkManager2 a = null;
    private HashMap<String, APBaseHttpReq> b = new HashMap<>();

    private APNetworkManager2() {
    }

    public static void cancelRequest(String str) {
        APBaseHttpReq aPBaseHttpReq = a.b.get(str);
        if (aPBaseHttpReq != null) {
            aPBaseHttpReq.stopRequest();
            a.b.remove(str);
        }
    }

    public static APNetworkManager2 getInstance() {
        if (a == null) {
            synchronized (APNetworkManager2.class) {
                if (a == null) {
                    a = new APNetworkManager2();
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

    /* access modifiers changed from: protected */
    public Object clone() {
        return super.clone();
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

    public void getMall(String str, String str2, String str3, String str4, IAPHttpAnsObserver iAPHttpAnsObserver) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(HTTP_KEY_OVERSEAINFO);
        APOverSeaCommReq aPOverSeaCommReq = new APOverSeaCommReq(stringBuffer.toString(), str3, str4, str2, str, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, false, (String) null);
        aPOverSeaCommReq.setHttpAns(new APOverSeaCommAns(APHttpHandle.getIntanceHandel(), new APHttpInstrument(HTTP_KEY_OVERSEA, iAPHttpAnsObserver), this.b, HTTP_KEY_OVERSEA));
        aPOverSeaCommReq.startService();
    }

    public APBaseHttpReq getNetWorkBykey(String str) {
        return this.b.get(str);
    }

    public void getOrder(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, IAPHttpAnsObserver iAPHttpAnsObserver) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(HTTP_KEY_OVERSEAORDER);
        APOverSeaCommReq aPOverSeaCommReq = new APOverSeaCommReq(stringBuffer.toString(), str4, str5, str3, str, str2, str6, str7, str8, str9, str10, str11, (String) null, (String) null, (String) null, false, (String) null);
        aPOverSeaCommReq.setHttpAns(new APOverSeaCommAns(APHttpHandle.getIntanceHandel(), new APHttpInstrument(HTTP_KEY_OVERSEA, iAPHttpAnsObserver), this.b, HTTP_KEY_OVERSEA));
        aPOverSeaCommReq.startService();
    }

    public void init(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, IAPHttpAnsObserver iAPHttpAnsObserver) {
        APOverSeaInitReq aPOverSeaInitReq = new APOverSeaInitReq(str, str2, str3, str4, str5, str6, str7, str8, str9, str10);
        aPOverSeaInitReq.setHttpAns(new APOverSeaInitAns(APHttpHandle.getIntanceHandel(), new APHttpInstrument(HTTP_KEY_OVERSEAINIT, iAPHttpAnsObserver), this.b, HTTP_KEY_OVERSEAINIT));
        aPOverSeaInitReq.startService();
    }

    public void provide(boolean z, String str, String str2, String str3, String str4, String str5, String str6, String str7, IAPHttpAnsObserver iAPHttpAnsObserver) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(HTTP_KEY_OVERSEAPROVIDE);
        APOverSeaCommReq aPOverSeaCommReq = new APOverSeaCommReq(stringBuffer.toString(), (String) null, (String) null, str2, str, (String) null, (String) null, (String) null, str3, (String) null, (String) null, (String) null, str5, str6, str7, z, str4);
        aPOverSeaCommReq.setHttpAns(new APOverSeaCommAns(APHttpHandle.getIntanceHandel(), new APHttpInstrument(HTTP_KEY_OVERSEA, iAPHttpAnsObserver), this.b, HTTP_KEY_OVERSEA));
        aPOverSeaCommReq.startService();
    }

    public void restore(String str, String str2, String str3, String str4, String str5, String str6, String str7, IAPHttpAnsObserver iAPHttpAnsObserver) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(HTTP_KEY_OVERSEAPROVIDE);
        APOverSeaCommReq aPOverSeaCommReq = new APOverSeaCommReq(stringBuffer.toString(), (String) null, (String) null, str2, str, (String) null, (String) null, (String) null, str3, (String) null, (String) null, (String) null, str5, str6, str7, true, str4);
        aPOverSeaCommReq.setHttpAns(new APOverSeaCommAns(APHttpHandle.getIntanceHandel(), new APHttpInstrument(HTTP_KEY_OVERSEARESTORE, iAPHttpAnsObserver), this.b, HTTP_KEY_OVERSEARESTORE));
        aPOverSeaCommReq.startService();
    }

    public void stopNetWorkBykey(String str) {
        APBaseHttpReq aPBaseHttpReq = this.b.get(str);
        if (aPBaseHttpReq != null) {
            aPBaseHttpReq.stopRequest();
        }
    }
}

package com.tencent.midas.oversea.network.modle;

import android.text.TextUtils;
import android.util.Base64;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APDataInterface;
import com.tencent.midas.oversea.comm.APDataReportManager;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.network.http.APHttpReqPost;
import com.tencent.midas.oversea.network.http.APUrlConf;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.zip.GZIPOutputStream;

public class APDataReportReq extends APHttpReqPost {
    public APDataReportReq() {
        String offerid = APAppDataInterface.singleton().getOfferid();
        String format = String.format("/v1/r/%s/log_data", new Object[]{offerid});
        String format2 = String.format("/v1/r/%s/log_data", new Object[]{offerid});
        String format3 = String.format(APUrlConf.AP_LOGREPORT_FCG, new Object[]{offerid});
        String str = "";
        try {
            str = String.format(APUrlConf.AP_LOGREPORT_CUSTOM_FCG, new Object[]{APAppDataInterface.singleton().getCustomCgi(), offerid});
        } catch (Exception e) {
        }
        setUrl(str, format, format2, format3);
    }

    private String a(String str) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(str.getBytes());
            gZIPOutputStream.close();
            return URLEncoder.encode(new String(Base64.encode(byteArrayOutputStream.toByteArray(), 2)), "UTF-8");
        } catch (IOException e) {
            APLog.w("APDataReport", "encodeGzip error:" + e.toString());
            return "";
        }
    }

    private void a() {
        if (APDataInterface.singleton().getIsSendReport()) {
            ArrayList arrayList = new ArrayList();
            int logRecord = APDataReportManager.getInstance().getLogRecord(arrayList);
            for (int i = 0; i < logRecord; i++) {
                String str = (String) arrayList.get(i);
                if (!str.equals("")) {
                    if (!TextUtils.isEmpty(str)) {
                        this.httpParam.reqParam.clear();
                        String a = a(str);
                        if (TextUtils.isEmpty(a) || a.length() >= str.length()) {
                            this.httpParam.reqParam.put(str, "");
                        } else {
                            this.httpParam.reqParam.put("t", "g");
                            this.httpParam.reqParam.put("c", a);
                        }
                    }
                    startRequest();
                }
            }
            APDataReportManager.getInstance().clearData();
        }
    }

    public void startService() {
        a();
        startRequest();
    }

    public void startService(String str) {
        if (APDataInterface.singleton().getIsSendReport() && !str.equals("")) {
            if (!TextUtils.isEmpty(str)) {
                this.httpParam.reqParam.clear();
                String a = a(str);
                if (TextUtils.isEmpty(a) || a.length() >= str.length()) {
                    this.httpParam.reqParam.put(str, "");
                } else {
                    this.httpParam.reqParam.put("t", "g");
                    this.httpParam.reqParam.put("c", a);
                }
            }
            startRequest();
        }
    }
}

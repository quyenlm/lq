package com.tencent.midas.oversea.comm;

import android.text.TextUtils;
import com.appsflyer.share.Constants;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.midas.oversea.api.UnityPayHelper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

class f implements Runnable {
    final /* synthetic */ APDataReportCache a;

    f(APDataReportCache aPDataReportCache) {
        this.a = aPDataReportCache;
    }

    public void run() {
        File unused = this.a.c = new File(this.a.b, "MidasReport.ini");
        int size = this.a.a.size();
        StringBuffer stringBuffer = new StringBuffer();
        String str = "android_v" + APCommMethod.getVersion();
        for (int i = 0; i < size; i++) {
            HashMap hashMap = this.a.a.get(i);
            stringBuffer.append("3=" + APDataInterface.singleton().getUserInfo().openId);
            stringBuffer.append("|7=0");
            stringBuffer.append("|13=" + APDataReportManager.getInstance().getDataId());
            stringBuffer.append("|24=" + APAppDataInterface.singleton().getOfferid());
            if (!TextUtils.isEmpty((CharSequence) hashMap.get("payid"))) {
                stringBuffer.append("|4=" + ((String) hashMap.get("payid")));
            }
            if (!TextUtils.isEmpty((CharSequence) hashMap.get("isBindQQ"))) {
                stringBuffer.append("|55=" + ((String) hashMap.get("isBindQQ")));
            }
            stringBuffer.append("|21=" + ((String) hashMap.get("format")));
            stringBuffer.append("|26=" + ((String) hashMap.get(UnityPayHelper.PF)));
            String str2 = (String) hashMap.get("token");
            if (str2 != null) {
                stringBuffer.append("|56=" + str2);
            }
            if (hashMap.get("extend") != null) {
                stringBuffer.append("|8=" + APTools.urlEncode("reportAgain=true&" + ((String) hashMap.get("extend")), 3));
            } else {
                stringBuffer.append("|8=" + APTools.urlEncode("reportAgain=true", 3));
            }
            String str3 = (String) hashMap.get("from");
            if (str3 != null) {
                stringBuffer.append("|20=" + str3);
            }
            String str4 = (String) hashMap.get(Constants.URL_MEDIA_SOURCE);
            if (str4 != null) {
                stringBuffer.append("|44=" + str4);
            }
            String str5 = (String) hashMap.get("buytype");
            if (str5 != null) {
                stringBuffer.append("|47=" + str5);
            }
            stringBuffer.append("|29=" + ((String) hashMap.get("sessionToken")));
            stringBuffer.append("|31=" + str);
            stringBuffer.append("|38=" + ((String) hashMap.get("times")));
            stringBuffer.append("|34=" + ((String) hashMap.get("uinTypeFromSvr")));
            stringBuffer.append("|35=" + ((String) hashMap.get("uinFromSvr")));
            stringBuffer.append("|37=" + ((String) hashMap.get(UnityPayHelper.SESSIONID)));
            stringBuffer.append("|43=" + ((String) hashMap.get(UnityPayHelper.SESSIONTYPE)));
            stringBuffer.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
            stringBuffer.append("\r\n");
        }
        try {
            if (stringBuffer.length() > 0) {
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            }
            FileWriter fileWriter = new FileWriter(this.a.c, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(stringBuffer.toString());
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

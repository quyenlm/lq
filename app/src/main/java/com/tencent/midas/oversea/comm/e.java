package com.tencent.midas.oversea.comm;

import com.tencent.imsdk.framework.request.HttpRequest;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

class e implements Runnable {
    final /* synthetic */ APDataReportCache a;

    e(APDataReportCache aPDataReportCache) {
        this.a = aPDataReportCache;
    }

    public void run() {
        File unused = this.a.c = new File(this.a.b, "MidasReport.ini");
        StringBuffer stringBuffer = new StringBuffer();
        String substring = this.a.d.substring(14, this.a.d.length());
        if (substring.contains("|8=")) {
            stringBuffer.append(substring);
            stringBuffer.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
            stringBuffer.insert(stringBuffer.indexOf("|8=") + 3, "reportAgain=true&");
        } else {
            stringBuffer.append(substring);
            stringBuffer.append("|8=reportAgain=true");
            stringBuffer.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
        }
        try {
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

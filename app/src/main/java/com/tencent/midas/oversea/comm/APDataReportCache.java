package com.tencent.midas.oversea.comm;

import android.content.Context;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.midas.oversea.network.http.APNetworkManager;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class APDataReportCache {
    List<HashMap<String, String>> a = new ArrayList();
    /* access modifiers changed from: private */
    public File b = null;
    /* access modifiers changed from: private */
    public File c = null;
    /* access modifiers changed from: private */
    public String d = "";

    public APDataReportCache(Context context) {
        this.b = context.getDir("midaspluginreport", 0);
    }

    /* access modifiers changed from: private */
    public void a(ArrayList<String> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        StringBuffer stringBuffer = new StringBuffer();
        int i = size % 12 == 0 ? size / 12 : (size / 12) + 1;
        int i2 = 0;
        while (i2 < i) {
            int i3 = 0;
            int i4 = 0;
            while (i3 < 12 && (i2 * 12) + i3 <= size - 1) {
                i4++;
                stringBuffer.append("record" + i3 + HttpRequest.HTTP_REQ_ENTITY_MERGE);
                stringBuffer.append(arrayList.get((i2 * 12) + i3));
                i3++;
            }
            if (stringBuffer.length() > 0) {
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            }
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("num=");
            stringBuffer2.append(i4);
            stringBuffer2.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
            stringBuffer2.append(stringBuffer.toString());
            arrayList2.add(stringBuffer2.toString());
            stringBuffer.setLength(0);
            i2++;
        }
        for (int i5 = 0; i5 < arrayList2.size(); i5++) {
            APNetworkManager.getInstance().dataReport((String) arrayList2.get(i5), "", new h(this));
        }
    }

    public void dataNativeCacheList() {
        new Thread(new f(this)).start();
    }

    public void dataNativeOneCahe() {
        new Thread(new e(this)).start();
    }

    public void sendReportOnce() {
        this.c = new File(this.b, "MidasReport.ini");
        if (this.c.exists()) {
            new Thread(new g(this)).start();
        }
    }

    public void setDataList(List<HashMap<String, String>> list) {
        this.a = list;
    }

    public void setDataOneRecord(String str) {
        this.d = str;
    }
}

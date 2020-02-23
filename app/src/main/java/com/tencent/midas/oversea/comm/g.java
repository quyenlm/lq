package com.tencent.midas.oversea.comm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

class g implements Runnable {
    final /* synthetic */ APDataReportCache a;

    g(APDataReportCache aPDataReportCache) {
        this.a = aPDataReportCache;
    }

    public void run() {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        if (this.a.c.length() > 0) {
            try {
                FileReader fileReader = new FileReader(this.a.c);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                do {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    i++;
                    arrayList.add(readLine);
                } while (i <= 500);
                bufferedReader.close();
                fileReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.a.a((ArrayList<String>) arrayList);
        }
    }
}

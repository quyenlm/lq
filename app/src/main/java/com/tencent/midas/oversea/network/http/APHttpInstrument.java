package com.tencent.midas.oversea.network.http;

import com.tencent.midas.oversea.comm.APDataReportManager;

public class APHttpInstrument {
    private static final a[] f = {new a(APNetworkManager.HTTP_KEY_GET_KEY, APDataReportManager.GETKEY_SUCESS, APDataReportManager.GETKEY_FAILURE), new a(APNetworkManager.HTTP_KEY_OVERSEAINFO, APDataReportManager.OVERSEAINFO_SUCESS, APDataReportManager.OVERSEAINFO_FAILURE), new a(APNetworkManager.HTTP_KEY_OVERSEAORDER, APDataReportManager.OVERSEAORDER_SUCESS, APDataReportManager.OVERSEAORDER_FAILURE), new a(APNetworkManager.HTTP_KEY_OVERSEAPROVIDE, APDataReportManager.OVERSEAPROVIDE_SUCESS, APDataReportManager.OVERSEAPROVIDE_FAILURE), new a(APNetworkManager2.HTTP_KEY_OVERSEA, APDataReportManager.OVERSEA_SUCESS, APDataReportManager.OVERSEA_FAILURE), new a(APNetworkManager2.HTTP_KEY_OVERSEAINIT, APDataReportManager.OVERSEA_INIT_SUCESS, APDataReportManager.OVERSEA_INIT_FAILURE), new a(APNetworkManager2.HTTP_KEY_OVERSEARESTORE, APDataReportManager.OVERSEA_RESTORE_SUCESS, APDataReportManager.OVERSEA_RESTORE_FAILURE)};
    final long a;
    /* access modifiers changed from: private */
    public IAPHttpAnsObserver b;
    private String c;
    /* access modifiers changed from: private */
    public String d;
    /* access modifiers changed from: private */
    public String e;
    private IAPHttpAnsObserver g = new c(this);

    private static class a {
        public String a;
        public String b;
        public String c;

        public a(String str, String str2, String str3) {
            this.a = str;
            this.b = str2;
            this.c = str3;
        }
    }

    public APHttpInstrument(String str, IAPHttpAnsObserver iAPHttpAnsObserver) {
        this.b = iAPHttpAnsObserver;
        this.c = str;
        this.d = a(this.c);
        this.e = b(this.c);
        this.a = System.currentTimeMillis();
    }

    private String a(String str) {
        for (a aVar : f) {
            if (aVar.a.equalsIgnoreCase(str)) {
                return aVar.b;
            }
        }
        return "";
    }

    private String b(String str) {
        for (a aVar : f) {
            if (aVar.a.equalsIgnoreCase(str)) {
                return aVar.c;
            }
        }
        return "";
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0014, code lost:
        r0 = (com.tencent.midas.oversea.network.modle.APOverSeaInitReq) r3.httpClient;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getCmd(com.tencent.midas.oversea.network.http.APBaseHttpAns r3) {
        /*
            r2 = this;
            java.lang.String r1 = ""
            boolean r0 = r3 instanceof com.tencent.midas.oversea.network.modle.APOverSeaCommAns     // Catch:{ Exception -> 0x0024 }
            if (r0 == 0) goto L_0x0010
            com.tencent.midas.oversea.network.http.APBaseHttpReq r0 = r3.httpClient     // Catch:{ Exception -> 0x0024 }
            com.tencent.midas.oversea.network.modle.APOverSeaCommReq r0 = (com.tencent.midas.oversea.network.modle.APOverSeaCommReq) r0     // Catch:{ Exception -> 0x0024 }
            com.tencent.midas.oversea.network.modle.APOverSeaCommReq r0 = (com.tencent.midas.oversea.network.modle.APOverSeaCommReq) r0     // Catch:{ Exception -> 0x0024 }
            if (r0 == 0) goto L_0x0010
            java.lang.String r1 = r0.cmd     // Catch:{ Exception -> 0x0024 }
        L_0x0010:
            boolean r0 = r3 instanceof com.tencent.midas.oversea.network.modle.APOverSeaInitAns     // Catch:{ Exception -> 0x0024 }
            if (r0 == 0) goto L_0x0027
            com.tencent.midas.oversea.network.http.APBaseHttpReq r0 = r3.httpClient     // Catch:{ Exception -> 0x0024 }
            com.tencent.midas.oversea.network.modle.APOverSeaInitReq r0 = (com.tencent.midas.oversea.network.modle.APOverSeaInitReq) r0     // Catch:{ Exception -> 0x0024 }
            com.tencent.midas.oversea.network.modle.APOverSeaInitReq r0 = (com.tencent.midas.oversea.network.modle.APOverSeaInitReq) r0     // Catch:{ Exception -> 0x0024 }
            if (r0 == 0) goto L_0x0027
            java.lang.String r0 = r0.cmd     // Catch:{ Exception -> 0x0024 }
        L_0x001e:
            r1 = 3
            java.lang.String r0 = com.tencent.midas.oversea.comm.APTools.urlEncode(r0, r1)
            return r0
        L_0x0024:
            r0 = move-exception
            r0 = r1
            goto L_0x001e
        L_0x0027:
            r0 = r1
            goto L_0x001e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.midas.oversea.network.http.APHttpInstrument.getCmd(com.tencent.midas.oversea.network.http.APBaseHttpAns):java.lang.String");
    }

    public IAPHttpAnsObserver getObserver() {
        return this.g;
    }
}

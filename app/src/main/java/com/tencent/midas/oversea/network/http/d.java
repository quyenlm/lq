package com.tencent.midas.oversea.network.http;

import com.tencent.midas.oversea.comm.APLog;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

class d implements HostnameVerifier {
    final /* synthetic */ APHttpsReqPost a;

    d(APHttpsReqPost aPHttpsReqPost) {
        this.a = aPHttpsReqPost;
    }

    public boolean verify(String str, SSLSession sSLSession) {
        if (APIPList.getInstance().checkIpInList(str)) {
            APLog.i("APHttpsRePost", "hostName:" + str + " verify hostName true");
            return true;
        }
        APLog.i("APHttpsRePost", "hostName:" + str + " verify hostName false");
        return false;
    }
}

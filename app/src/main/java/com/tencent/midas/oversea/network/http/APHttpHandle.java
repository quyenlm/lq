package com.tencent.midas.oversea.network.http;

import android.os.Handler;
import android.os.Message;
import com.tencent.midas.oversea.comm.APLog;
import java.util.HashMap;

public class APHttpHandle extends Handler {
    private static APHttpHandle a;
    private static Object b = new Object();
    private HashMap<String, IAPHttpAnsObserver> c = new HashMap<>();

    private APHttpHandle() {
    }

    private void a(Message message) {
        int i = message.what;
        APBaseHttpAns aPBaseHttpAns = (APBaseHttpAns) message.obj;
        String httpReqKey = aPBaseHttpAns.getHttpReqKey();
        IAPHttpAnsObserver iAPHttpAnsObserver = this.c.get(httpReqKey);
        if (iAPHttpAnsObserver == null) {
            APLog.i("HttpHandler", "observer is null");
            return;
        }
        unregister(httpReqKey);
        switch (i) {
            case 3:
                iAPHttpAnsObserver.onFinish(aPBaseHttpAns);
                return;
            case 4:
                iAPHttpAnsObserver.onError(aPBaseHttpAns);
                return;
            case 5:
                iAPHttpAnsObserver.onStop(aPBaseHttpAns);
                return;
            default:
                return;
        }
    }

    public static APHttpHandle getIntanceHandel() {
        synchronized (b) {
            if (a == null) {
                a = new APHttpHandle();
            }
        }
        return a;
    }

    public void handleMessage(Message message) {
        a(message);
    }

    public void register(String str, IAPHttpAnsObserver iAPHttpAnsObserver) {
        this.c.put(str, iAPHttpAnsObserver);
    }

    public void unregister(String str) {
        this.c.remove(str);
    }
}

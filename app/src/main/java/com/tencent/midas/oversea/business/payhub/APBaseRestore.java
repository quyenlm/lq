package com.tencent.midas.oversea.business.payhub;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.midas.oversea.api.IAPPayUpdateCallBack;
import com.tencent.midas.oversea.api.ICallback;
import com.tencent.midas.oversea.api.IGetPurchaseCallback;
import com.tencent.midas.oversea.api.IPostProvideCallback;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.data.channel.APPayReceipt;
import com.tencent.midas.oversea.network.http.APNetworkManager;
import com.tencent.midas.oversea.network.http.APNetworkManager2;
import com.tencent.midas.oversea.network.http.IAPHttpAnsObserver;
import java.util.ArrayList;
import java.util.List;

public abstract class APBaseRestore {
    /* access modifiers changed from: private */
    public static final String a = APBaseRestore.class.getSimpleName();
    private Context b;
    /* access modifiers changed from: private */
    public List<APPayReceipt> c = null;
    /* access modifiers changed from: private */
    public List<String> d = new ArrayList();
    /* access modifiers changed from: private */
    public int e = 0;
    private String f;
    private IAPPayUpdateCallBack g;
    /* access modifiers changed from: private */
    public String h = null;
    private IAPHttpAnsObserver i = new h(this);

    /* access modifiers changed from: private */
    public void a(boolean z) {
        APLog.i(a, "curIndex:" + this.e);
        APLog.i(a, "provideList:" + this.c.size());
        if (z && this.e < this.c.size()) {
            APLog.i(a, "connect to server");
            APPayReceipt aPPayReceipt = this.c.get(this.e);
            if (APAppDataInterface.singleton().isNewCGI()) {
                APNetworkManager2.getInstance().restore("", "", this.f, "", "", aPPayReceipt.receipt, aPPayReceipt.receipt_sig, this.i);
            } else {
                APNetworkManager.getInstance().overSeanProvider(true, "", "", this.f, "", "", aPPayReceipt.receipt, aPPayReceipt.receipt_sig, this.i);
            }
        } else if (this.d.size() == 0) {
            APLog.i(a, "reprovider cousumeList is empty");
            dispose();
        } else {
            APLog.i(a, "reprovider start postProvide");
            postProvide(this.d, new i(this));
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        getPurchaseList(this.b, new f(this));
    }

    static /* synthetic */ int e(APBaseRestore aPBaseRestore) {
        int i2 = aPBaseRestore.e;
        aPBaseRestore.e = i2 + 1;
        return i2;
    }

    public void dispose() {
        if (this.g != null) {
            APLog.i(a, "mICallback");
            if (TextUtils.isEmpty(this.h)) {
                this.g.onUpdate(1, (String) null);
            } else {
                this.g.onUpdate(0, this.h);
            }
        }
    }

    public abstract void getPurchaseList(Context context, IGetPurchaseCallback iGetPurchaseCallback);

    public abstract void init(Context context, ICallback iCallback);

    public abstract void postProvide(List<String> list, IPostProvideCallback iPostProvideCallback);

    public void register() {
    }

    public void restore(Context context, String str, IAPPayUpdateCallBack iAPPayUpdateCallBack) {
        this.b = context;
        this.f = str;
        this.g = iAPPayUpdateCallBack;
        this.h = null;
        init(this.b, new g(this));
    }
}

package com.tencent.imsdk.pay.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.tencent.imsdk.pay.api.IMPayExtendListener;
import com.tencent.imsdk.pay.api.IMPayListener;
import com.tencent.imsdk.pay.api.IMPayListener2;
import com.tencent.imsdk.pay.entity.IMPayRequestInfo;
import com.tencent.imsdk.tool.etc.IMLogger;
import java.util.List;

public abstract class IMPayBase {
    public Context context;

    public abstract void dispose();

    public abstract void getMp(IMPayRequestInfo iMPayRequestInfo);

    public abstract void getSkuDetails(IMPayRequestInfo iMPayRequestInfo);

    public abstract boolean handleActivityResult(int i, int i2, Intent intent);

    public abstract void init(Activity activity, String str);

    public abstract void pay(IMPayRequestInfo iMPayRequestInfo);

    public abstract void restorePay(String str);

    public abstract void setDebugLog(boolean z);

    public abstract void setEnv(String str);

    public abstract void setPayListener(IMPayListener iMPayListener);

    public abstract void setReleaseIDC(String str);

    public abstract void setScreenType(boolean z);

    public void prepare(IMPayRequestInfo info) {
        IMLogger.d("not support prepare");
    }

    public String toString() {
        return "IMPayBase{context=" + this.context + '}';
    }

    public void setPayExtendListener(IMPayExtendListener payExtendListener) {
        IMLogger.d("not support setPayExtendListener");
    }

    public void payExtend(Object o) {
        IMLogger.d("not support payExtend");
    }

    public void realPayExtend(String content) {
        IMLogger.d("not support realPayExtend");
    }

    public void getProductExtend(List<Object> list) {
        IMLogger.d("not support getProductExtend");
    }

    public void realGetProductExtend(List<String> list) {
        IMLogger.d("not support realGetProductExtend");
    }

    public void prepareExtend(Object o) {
        IMLogger.d("not support prepareExtend");
    }

    public void realPrepareExtend(String content) {
        IMLogger.d("not support realPrepareExtend");
    }

    public void getMpExtend(Object o) {
        IMLogger.d("not support getMpExtend");
    }

    public void realGetMpExtend(String content) {
        IMLogger.d("not support realGetMpExtend");
    }

    public String getPF(String registerChannel, String setupChannel, String appCode, String openid) {
        IMLogger.d("not support getPF");
        return "not support";
    }

    public void prepare2(IMPayRequestInfo info, boolean needPayUpdate) {
        IMLogger.d("not support prepare2");
    }

    public void prepare2Extend(Object o, boolean needPayUpdate) {
        IMLogger.d("not support prepare2Extend");
    }

    public void realPrepare2Extend(String content, boolean needPayUpdate) {
        IMLogger.d("not support realPrepare2Extend");
    }

    public void deinit() {
        IMLogger.d("not support exit");
    }

    public void setPayListener2(IMPayListener2 payListener2) {
        IMLogger.d("not support setPayListener2");
    }

    protected IMPayBase() {
    }

    public void initialize(Context ctx) {
        this.context = ctx;
    }
}

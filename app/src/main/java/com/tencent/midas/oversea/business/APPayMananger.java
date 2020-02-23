package com.tencent.midas.oversea.business;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.tencent.midas.oversea.api.APMidasPayAPI;
import com.tencent.midas.oversea.api.APMidasResponse;
import com.tencent.midas.oversea.api.IAPMidasNetCallBack;
import com.tencent.midas.oversea.api.IAPMidasPayCallBack;
import com.tencent.midas.oversea.api.UnityPayHelper;
import com.tencent.midas.oversea.api.request.APMidasBaseRequest;
import com.tencent.midas.oversea.business.order.APOrder;
import com.tencent.midas.oversea.business.payhub.APBasePayChannel;
import com.tencent.midas.oversea.business.payhub.APPayHub;
import com.tencent.midas.oversea.comm.APActivityResult;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.comm.APDataReportManager;
import com.tencent.midas.oversea.comm.APGlobalInfo;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.comm.APTools;
import com.tencent.midas.oversea.comm.APUICommonMethod;
import com.tencent.midas.oversea.data.channel.APUserSelInfo;
import com.tencent.midas.oversea.data.channel.ChannelItem;
import com.tencent.midas.oversea.data.channel.GoodsItem;
import com.tencent.midas.oversea.network.http.APHttpHandle;
import com.tencent.midas.oversea.network.http.APIPList;
import com.tencent.midas.oversea.network.http.APNetworkManager;
import com.tencent.midas.oversea.network.http.IAPHttpAnsObserver;
import com.tencent.midas.oversea.network.modle.APMpAns;
import com.tencent.midas.oversea.network.modle.APMpReq;
import com.tencent.midas.oversea.safe.APGetKeyManager;
import com.tencent.midas.oversea.view.APUICommMethod;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import org.json.JSONObject;

public class APPayMananger {
    /* access modifiers changed from: private */
    public static final String a = APPayMananger.class.getSimpleName();
    private static APPayMananger b = null;
    /* access modifiers changed from: private */
    public APOrder c;
    /* access modifiers changed from: private */
    public APPayHub d;
    private Context e;
    /* access modifiers changed from: private */
    public Activity f;
    /* access modifiers changed from: private */
    public Activity g;
    /* access modifiers changed from: private */
    public a h;

    private class a extends Handler {
        WeakReference<Activity> a;

        public a(Activity activity) {
            this.a = new WeakReference<>(activity);
        }

        public void handleMessage(Message message) {
            APOrder a2 = APPayMananger.this.c;
            switch (message.what) {
                case 1:
                    APPayMananger.this.f();
                    return;
                case 2:
                    APPayMananger.this.a(3, "get key error!!");
                    return;
                case 4:
                    APPayMananger.this.a(3, (String) message.obj);
                    return;
                case 5:
                    APPayMananger.this.a(3, "same order error!!");
                    return;
                case 6:
                    if (!APAppDataInterface.singleton().isNewCGI()) {
                        APPayMananger.this.d.getChannelList();
                        return;
                    } else if (a2.needShowMall()) {
                        APPayMananger.this.d.getChannelListNew();
                        return;
                    } else {
                        APPayMananger.this.b(a2);
                        return;
                    }
                case 8:
                    APUICommonMethod.dismissWaitDialog();
                    if (a2.needShowMall()) {
                        APPayMananger.this.f.startActivity(new Intent(APPayMananger.this.f, APMallActivity.class));
                        return;
                    } else {
                        APPayMananger.this.a(a2);
                        return;
                    }
                case 9:
                    APUICommonMethod.dismissWaitDialog();
                    APPayMananger.this.b(message);
                    return;
                case 12:
                    if (!APAppDataInterface.singleton().isNewCGI()) {
                        APPayMananger.this.d.getChannelList();
                        return;
                    } else if (a2.needShowMall()) {
                        APPayMananger.this.d.getChannelListNew();
                        return;
                    } else {
                        APPayMananger.this.b(a2);
                        return;
                    }
                case 16:
                    APUICommonMethod.dismissWaitDialog();
                    String str = "";
                    String str2 = "";
                    try {
                        JSONObject jSONObject = new JSONObject((String) message.obj);
                        str = jSONObject.getString("channel");
                        str2 = jSONObject.getString("billno");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    APPayMananger.this.a(message.arg1, message.arg2, APPayHub.getPayChannelIndex(str), str, str2);
                    return;
                case 17:
                    APUICommonMethod.dismissWaitDialog();
                    APPayMananger.this.b(message);
                    return;
                case 18:
                    APUICommonMethod.dismissWaitDialog();
                    APPayMananger.this.c(message);
                    return;
                case 19:
                    APPayMananger.this.b();
                    return;
                case 21:
                    APUserSelInfo aPUserSelInfo = (APUserSelInfo) message.obj;
                    Activity unused = APPayMananger.this.g = aPUserSelInfo.srcActivity;
                    if (!APPayMananger.this.d.gotoPay(aPUserSelInfo.srcActivity, aPUserSelInfo.channel, aPUserSelInfo.goods, aPUserSelInfo.country, aPUserSelInfo.currency, a2.needShowMall())) {
                        APLog.e(APPayMananger.a, "select channel error!!");
                        return;
                    }
                    return;
                case 22:
                    if (APAppDataInterface.singleton().isNewCGI()) {
                        APPayMananger.this.b(a2, (Activity) message.obj);
                        return;
                    } else {
                        APPayMananger.this.a(a2, (Activity) message.obj);
                        return;
                    }
                case 23:
                    APUICommonMethod.dismissWaitDialog();
                    APPayMananger.this.e();
                    return;
                case 27:
                    APUICommonMethod.dismissWaitDialog();
                    APPayMananger.this.a(message);
                    return;
                case 57:
                    APActivityResult aPActivityResult = (APActivityResult) message.obj;
                    APPayMananger.this.d.handleActivityResult(aPActivityResult.requestCode, aPActivityResult.resultCode, aPActivityResult.data);
                    return;
                default:
                    return;
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(int i, int i2, int i3, String str, String str2) {
        releaseUI();
        APDataReportManager.getInstance().insertData(APDataReportManager.SDK_OVERSEA_EXIT, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, "0");
        d();
        APOrder gerCurOrder = gerCurOrder();
        APUICommMethod.successToast(this.f, !gerCurOrder.showSuccDetail() ? "" : String.valueOf(i), new f(this, gerCurOrder, i2, i3, str, i, str2));
    }

    /* access modifiers changed from: private */
    public void a(int i, String str) {
        a(i, str, -1, -1);
    }

    private void a(int i, String str, int i2, int i3) {
        releaseUI();
        APDataReportManager.getInstance().insertData(APDataReportManager.SDK_OVERSEA_EXIT, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, i + "");
        d();
        APOrder gerCurOrder = gerCurOrder();
        if (gerCurOrder != null) {
            APMidasResponse aPMidasResponse = gerCurOrder.getAPMidasResponse();
            aPMidasResponse.resultCode = i;
            aPMidasResponse.resultMsg = str;
            aPMidasResponse.payState = i2;
            aPMidasResponse.provideState = i3;
            gerCurOrder.callback(aPMidasResponse);
        }
        c();
    }

    private void a(Activity activity, APMidasBaseRequest aPMidasBaseRequest, IAPMidasPayCallBack iAPMidasPayCallBack) {
        this.f = activity;
        this.h = new a(this.f);
        APOrder createOrder = APOrder.createOrder(aPMidasBaseRequest);
        createOrder.setCallback(iAPMidasPayCallBack);
        if (!createOrder.checkOrderInfo()) {
            APMidasResponse aPMidasResponse = createOrder.getAPMidasResponse();
            aPMidasResponse.resultCode = 3;
            iAPMidasPayCallBack.MidasPayCallBack(aPMidasResponse);
            return;
        }
        this.c = createOrder;
        this.d = APPayHub.createInstance(createOrder);
        APMidasPayAPI.singleton().mBuyType = getCurBaseRequest().mOldType;
        APDataReportManager.getInstance().insertData(APDataReportManager.SDK_OVERSEA_ENTER, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, (String) null);
        j();
    }

    /* access modifiers changed from: private */
    public void a(Message message) {
        a(message.arg1, (String) message.obj, 0, -1);
    }

    /* access modifiers changed from: private */
    public void a(APOrder aPOrder) {
        String payChannel = aPOrder.getBaseRequest().getPayChannel();
        String assignProductid = aPOrder.getBaseRequest().getAssignProductid();
        String str = singleton().getCurBaseRequest().country;
        String str2 = singleton().getCurBaseRequest().currency_type;
        ChannelItem channelItem = this.d.getChannelItem(payChannel);
        GoodsItem goods = this.d.getGoods(payChannel, str, assignProductid);
        if (channelItem == null) {
            a(3, "wrong assign channel");
        } else if (channelItem.mAPBasePayChannel.hasGoodsList() && goods != null) {
            this.f.startActivity(new Intent(this.f, APProxyMallActivity.class));
        } else if (!channelItem.mAPBasePayChannel.hasGoodsList()) {
            this.f.startActivity(new Intent(this.f, APProxyMallActivity.class));
        } else {
            a(3, "check the productid,not in the config");
        }
    }

    /* access modifiers changed from: private */
    public void a(APOrder aPOrder, Activity activity) {
        String payChannel = aPOrder.getBaseRequest().getPayChannel();
        String assignProductid = aPOrder.getBaseRequest().getAssignProductid();
        String str = singleton().getCurBaseRequest().country;
        String str2 = singleton().getCurBaseRequest().currency_type;
        ChannelItem channelItem = this.d.getChannelItem(payChannel);
        GoodsItem goods = this.d.getGoods(payChannel, str, assignProductid);
        this.g = activity;
        if (channelItem == null) {
            a(3, "wrong assign channel");
        } else if (!channelItem.mAPBasePayChannel.hasGoodsList() || goods == null) {
            if (channelItem.mAPBasePayChannel.hasGoodsList()) {
                a(3, "check the productid,not in the config");
            } else if (!this.d.gotoPay(this.g, channelItem, goods, str, str2, aPOrder.needShowMall())) {
                APLog.e(a, "select channel error!!");
            }
        } else if (!this.d.gotoPay(this.g, channelItem, goods, str, str2, aPOrder.needShowMall())) {
            APLog.e(a, "select channel error!!");
        }
    }

    private void a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, IAPHttpAnsObserver iAPHttpAnsObserver) {
        APMpReq aPMpReq = new APMpReq(str, str2, str3, str4, str5, str6, str7, str9, str10, str11, str12, str13, str14);
        aPMpReq.setHttpAns(new APMpAns(APHttpHandle.getIntanceHandel(), iAPHttpAnsObserver, new HashMap(), str8));
        aPMpReq.starService();
    }

    /* access modifiers changed from: private */
    public void b() {
    }

    /* access modifiers changed from: private */
    public void b(Message message) {
        a(message.arg1, (String) message.obj, -1, -1);
    }

    /* access modifiers changed from: private */
    public void b(APOrder aPOrder) {
        if (!this.d.isChannelSupport(aPOrder.getBaseRequest().getPayChannel())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.f);
            builder.setTitle(APCommMethod.getStringId(this.f, "unipay_hints"));
            builder.setMessage("can not find the channel jar file or maybe anything wrong with the channel name!!");
            builder.setCancelable(false);
            builder.setNeutralButton(APCommMethod.getStringId(this.f, "unipay_sure"), new d(this));
            AlertDialog create = builder.create();
            if (create != null) {
                try {
                    create.show();
                } catch (Exception e2) {
                }
            }
        } else {
            this.f.startActivity(new Intent(this.f, APProxyMallActivity.class));
        }
    }

    /* access modifiers changed from: private */
    public void b(APOrder aPOrder, Activity activity) {
        String payChannel = aPOrder.getBaseRequest().getPayChannel();
        String assignProductid = aPOrder.getBaseRequest().getAssignProductid();
        String str = singleton().getCurBaseRequest().country;
        String str2 = singleton().getCurBaseRequest().currency_type;
        ChannelItem simpleChannelItem = this.d.getSimpleChannelItem(payChannel);
        GoodsItem goodsItem = new GoodsItem();
        goodsItem.productid = assignProductid;
        goodsItem.country = str;
        goodsItem.currency_type = str2;
        this.g = activity;
        if (simpleChannelItem == null) {
            a(3, "wrong assign channel");
        } else if (!simpleChannelItem.mAPBasePayChannel.hasGoodsList() || goodsItem == null) {
            if (simpleChannelItem.mAPBasePayChannel.hasGoodsList()) {
                a(3, "check the productid,not in the config");
            } else if (!this.d.gotoPay(this.g, simpleChannelItem, goodsItem, str, str2, aPOrder.needShowMall())) {
                APLog.e(a, "select channel error!!");
            }
        } else if (!this.d.gotoPay(this.g, simpleChannelItem, goodsItem, str, str2, aPOrder.needShowMall())) {
            APLog.e(a, "select channel error!!");
        }
    }

    /* access modifiers changed from: private */
    public void c() {
        if (this.d != null) {
            this.d.release();
            this.d = null;
        }
        APIPList.release();
    }

    /* access modifiers changed from: private */
    public void c(Message message) {
        a(-1, "unknow result,check later", 4, -1);
    }

    private void d() {
        try {
            APLog.i("getDeviceInfo:", APAppDataInterface.singleton().getDeviceInfo());
            APDataReportManager.getInstance().insertData(APDataReportManager.PHONE_DEVICE, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, APAppDataInterface.singleton().getDeviceInfo());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        APNetworkManager.getInstance().dataReport(new e(this));
    }

    /* access modifiers changed from: private */
    public void e() {
        releaseUI();
        APDataReportManager.getInstance().insertData(APDataReportManager.SDK_OVERSEA_EXIT, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
        d();
        APOrder gerCurOrder = gerCurOrder();
        if (gerCurOrder != null) {
            APMidasResponse aPMidasResponse = gerCurOrder.getAPMidasResponse();
            aPMidasResponse.resultCode = 2;
            gerCurOrder.getCallback().MidasPayCallBack(aPMidasResponse);
        }
        c();
    }

    /* access modifiers changed from: private */
    public void f() {
        releaseUI();
        APDataReportManager.getInstance().insertData(APDataReportManager.SDK_OVERSEA_EXIT, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, "-2");
        d();
        APOrder gerCurOrder = gerCurOrder();
        if (gerCurOrder != null) {
            gerCurOrder.getCallback().MidasPayNeedLogin();
        }
        c();
    }

    private void g() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.f);
        builder.setTitle(APCommMethod.getStringId(this.f, "unipay_hints"));
        builder.setMessage(APCommMethod.getStringId(this.f, "unipay_no_charge_hints"));
        builder.setNeutralButton(APCommMethod.getStringId(this.f, "unipay_sure"), new g(this));
        AlertDialog create = builder.create();
        if (create != null) {
            create.setOnKeyListener(new i(this));
            try {
                create.show();
            } catch (Exception e2) {
            }
        }
    }

    private void h() {
        new APGetKeyManager(this.f).changeKey(new j(this));
    }

    /* access modifiers changed from: private */
    public void i() {
        if (APAppDataInterface.singleton().getSecretKey().length() <= 0 || APAppDataInterface.singleton().getCryptoKey().length() <= 0) {
            h();
            return;
        }
        APOrder gerCurOrder = gerCurOrder();
        if (gerCurOrder != null) {
            gerCurOrder.pay(this.h);
        } else {
            APLog.i(a, "no order to pay");
        }
    }

    private void j() {
        if (APAppDataInterface.singleton().getEnv().equals(APGlobalInfo.TestEnv)) {
            g();
            return;
        }
        APUICommonMethod.showWaitDialog(this.f, APCommMethod.getStringId(this.f, "unipay_order_release_tip"), true, new k(this));
        if (APAppDataInterface.singleton().isNewCGI()) {
            APOrder gerCurOrder = gerCurOrder();
            if (gerCurOrder != null) {
                gerCurOrder.pay(this.h);
            } else {
                APLog.i(a, "no order to pay");
            }
        } else {
            i();
        }
    }

    public static APPayMananger singleton() {
        if (b == null) {
            synchronized (APPayMananger.class) {
                if (b == null) {
                    b = new APPayMananger();
                }
            }
        }
        return b;
    }

    public APOrder gerCurOrder() {
        return this.c;
    }

    public Context getApplicationContext() {
        return this.e;
    }

    public APMidasBaseRequest getCurBaseRequest() {
        APOrder gerCurOrder = gerCurOrder();
        if (gerCurOrder != null) {
            return gerCurOrder.getBaseRequest();
        }
        return null;
    }

    public APBasePayChannel getCurChannel() {
        return this.d.getCurChannel();
    }

    public Handler getCurHandler() {
        return this.h;
    }

    public APPayHub getCurPayHub() {
        return this.d;
    }

    public void init(Activity activity) {
        this.c = null;
        this.d = null;
        this.e = activity.getApplicationContext();
        Log.i(a, "APPayMananger init");
        APAppDataInterface.singleton().setDeviceInfo(APTools.collectDeviceInfo(activity));
    }

    public void init(Activity activity, APMidasBaseRequest aPMidasBaseRequest) {
        APOrder createOrder = APOrder.createOrder(aPMidasBaseRequest);
        if (createOrder.checkOrderInfo()) {
            this.c = createOrder;
        }
    }

    public void net(String str, APMidasBaseRequest aPMidasBaseRequest, IAPMidasNetCallBack iAPMidasNetCallBack) {
        APOrder createOrder = APOrder.createOrder(aPMidasBaseRequest);
        if (!createOrder.checkOrderInfo()) {
            createOrder.getAPMidasResponse().resultCode = 3;
            iAPMidasNetCallBack.MidasNetError(str, -1, "request params error");
            return;
        }
        this.c = createOrder;
        a(aPMidasBaseRequest.openId, aPMidasBaseRequest.openKey, aPMidasBaseRequest.sessionId, aPMidasBaseRequest.sessionType, aPMidasBaseRequest.zoneId, aPMidasBaseRequest.pf, aPMidasBaseRequest.pfKey, str, aPMidasBaseRequest.mpInfo.drmInfo, aPMidasBaseRequest.reserv, aPMidasBaseRequest.country, aPMidasBaseRequest.currency_type, aPMidasBaseRequest.mpInfo.payChannel, aPMidasBaseRequest.mType, new l(this, iAPMidasNetCallBack));
    }

    public void pay(Activity activity, APMidasBaseRequest aPMidasBaseRequest, IAPMidasPayCallBack iAPMidasPayCallBack) {
        a(activity, aPMidasBaseRequest, iAPMidasPayCallBack);
    }

    public void releaseUI() {
        if (this.g != null) {
            this.g.finish();
            this.g = null;
        }
        APUICommonMethod.dismissWaitDialog();
        APUICommonMethod.releaseLoadingMap();
        APUICommonMethod.releaseManageDlgMap();
    }
}

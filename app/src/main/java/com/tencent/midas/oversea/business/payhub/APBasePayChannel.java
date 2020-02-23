package com.tencent.midas.oversea.business.payhub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.tencent.midas.oversea.api.request.APMidasBaseRequest;
import com.tencent.midas.oversea.api.request.APMidasGoodsRequest;
import com.tencent.midas.oversea.api.request.APMidasMonthRequest;
import com.tencent.midas.oversea.business.APPayMananger;
import com.tencent.midas.oversea.comm.APAppDataInterface;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.comm.APDataInterface;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.comm.APUICommonMethod;
import com.tencent.midas.oversea.data.channel.APPayInfo;
import com.tencent.midas.oversea.data.channel.APPayReceipt;
import com.tencent.midas.oversea.data.channel.ChannelItem;
import com.tencent.midas.oversea.data.channel.GoodsItem;
import com.tencent.midas.oversea.network.http.APNetworkManager;
import com.tencent.midas.oversea.network.http.APNetworkManager2;
import com.tencent.midas.oversea.network.http.IAPHttpAnsObserver;
import com.tencent.midas.oversea.network.modle.APOverSeaCommAns;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public abstract class APBasePayChannel implements IAPBasePayChannel {
    public static final int FORCE_CONSUME = 5017;
    /* access modifiers changed from: private */
    public static final String a = APBasePayChannel.class.getSimpleName();
    protected Handler UIHandler = new Handler(new b(this));
    /* access modifiers changed from: private */
    public Handler b = null;
    /* access modifiers changed from: private */
    public Activity c;
    protected String currency_amt;
    /* access modifiers changed from: private */
    public boolean d;
    /* access modifiers changed from: private */
    public int e = 0;
    private final int f = 5;
    /* access modifiers changed from: private */
    public boolean g = false;
    /* access modifiers changed from: private */
    public String h = "";
    /* access modifiers changed from: private */
    public String i;
    /* access modifiers changed from: private */
    public APPayReceipt j;
    private IAPHttpAnsObserver k = new c(this);
    private IAPHttpAnsObserver l = new d(this);
    private IAPHttpAnsObserver m = new e(this);
    protected String mBillNo;
    protected ChannelItem mChannelItem = null;
    protected Context mContext;
    protected String mCountry;
    protected String mCurrency;
    protected GoodsItem mGoodsItem = null;
    protected JSONObject mInfo;
    protected String num;
    protected String offer_name;
    protected String product_name;

    /* access modifiers changed from: private */
    public void a(Message message) {
        if (this.d) {
            String str = (String) message.obj;
            if (TextUtils.isEmpty(str)) {
                str = APCommMethod.getStringId(APPayMananger.singleton().getApplicationContext(), "unipay_pay_error_tip");
            }
            APUICommonMethod.showMessageDialog(this.c, str);
            return;
        }
        String str2 = (String) message.obj;
        if (TextUtils.isEmpty(str2)) {
            Message obtainMessage = this.b.obtainMessage();
            obtainMessage.arg1 = -1;
            obtainMessage.obj = APCommMethod.getStringId(APPayMananger.singleton().getApplicationContext(), "unipay_pay_error_tip");
            obtainMessage.what = 17;
            this.b.sendMessage(obtainMessage);
            return;
        }
        APUICommonMethod.showErrorMsg(this.c, (String) message.obj, new a(this, str2));
    }

    /* access modifiers changed from: private */
    public void a(APOverSeaCommAns aPOverSeaCommAns) {
        APLog.i(a, "update local goods info");
        if (this.mGoodsItem != null) {
            if (!TextUtils.isEmpty(this.product_name)) {
                APLog.i(a, "update local goods info,product_name:" + this.product_name);
                this.mGoodsItem.producename = this.product_name;
            }
            if (!TextUtils.isEmpty(this.currency_amt)) {
                APLog.i(a, "update local goods info,currency_amt:" + this.currency_amt);
                this.mGoodsItem.price = this.currency_amt;
            }
            if (!TextUtils.isEmpty(this.num)) {
                APLog.i(a, "update local goods info,num:" + this.num);
                this.mGoodsItem.num = this.num;
            }
        }
        APPayMananger.singleton().getCurPayHub().offer_name = aPOverSeaCommAns.getOfferName();
    }

    /* access modifiers changed from: private */
    public String b() {
        return this.mChannelItem == null ? "" : this.mChannelItem.id;
    }

    public static List<PackageInfo> getAllApps(Context context) {
        int i2 = 0;
        ArrayList arrayList = new ArrayList();
        List<PackageInfo> installedPackages = context.getPackageManager().getInstalledPackages(0);
        while (true) {
            int i3 = i2;
            if (i3 >= installedPackages.size()) {
                return arrayList;
            }
            PackageInfo packageInfo = installedPackages.get(i3);
            int i4 = packageInfo.applicationInfo.flags;
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            if ((i4 & 1) <= 0) {
                arrayList.add(packageInfo);
            }
            i2 = i3 + 1;
        }
    }

    public void dispose() {
        APUICommonMethod.dismissWaitDialog();
    }

    public Handler getUIHandler() {
        return this.UIHandler;
    }

    public void gotoPay(Activity activity, Handler handler, ChannelItem channelItem, GoodsItem goodsItem, String str, String str2, boolean z) {
        this.c = activity;
        this.d = z;
        this.mContext = activity;
        this.mChannelItem = channelItem;
        this.mGoodsItem = goodsItem;
        this.mCountry = str;
        this.mCurrency = str2;
        this.b = handler;
        this.g = false;
        this.UIHandler.sendEmptyMessage(31);
    }

    public boolean handleActivityResult(int i2, int i3, Intent intent) {
        return false;
    }

    public boolean hasGoodsList() {
        return true;
    }

    public void init(Context context, ChannelItem channelItem, GoodsItem goodsItem, String str, String str2) {
        this.UIHandler.sendEmptyMessage(32);
    }

    /* access modifiers changed from: protected */
    public boolean isSDKProvide() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean needOrder() {
        return true;
    }

    /* access modifiers changed from: protected */
    public void onSaveReceipt(APPayReceipt aPPayReceipt) {
    }

    public void order(APPayInfo aPPayInfo) {
        String str = "";
        String str2 = "";
        if (APPayMananger.singleton().getCurBaseRequest().mType.equals(APMidasBaseRequest.OFFER_TYPE_BG)) {
            str2 = ((APMidasGoodsRequest) APPayMananger.singleton().getCurBaseRequest()).goodsTokenUrl;
        }
        if (APPayMananger.singleton().getCurBaseRequest().mType.equals(APMidasBaseRequest.OFFER_TYPE_UNIONMONTH)) {
            str = ((APMidasMonthRequest) APPayMananger.singleton().getCurBaseRequest()).serviceCode;
        }
        String str3 = aPPayInfo == null ? "" : aPPayInfo.amount;
        this.h = aPPayInfo == null ? "" : aPPayInfo.currency;
        this.i = aPPayInfo == null ? "" : aPPayInfo.ext;
        String str4 = "";
        if (!hasGoodsList()) {
            str4 = "1";
        }
        if (APAppDataInterface.singleton().isNewCGI()) {
            APNetworkManager2.getInstance().getOrder(this.mCurrency, this.h, this.mCountry, str, "", str3, this.mGoodsItem != null ? this.mGoodsItem.productid : "", this.mChannelItem.id, str2, this.i, str4, this.k);
        } else {
            APNetworkManager.getInstance().overSeanOrder(str3, this.i, str4, this.mCurrency, this.mCountry, this.mChannelItem.id, this.mGoodsItem != null ? this.mGoodsItem.productid : "", this.mGoodsItem != null ? this.mGoodsItem.productType : "", str, str2, this.k);
        }
    }

    public void pay(Activity activity, JSONObject jSONObject) {
    }

    public void postPay() {
        this.UIHandler.sendEmptyMessage(52);
    }

    public void prePay() {
        this.UIHandler.sendEmptyMessage(35);
    }

    public void provide(APPayReceipt aPPayReceipt) {
        APAppDataInterface.singleton().getOfferid();
        String str = APDataInterface.singleton().getUserInfo().openId;
        String str2 = APDataInterface.singleton().getUserInfo().openKey;
        String str3 = APDataInterface.singleton().getUserInfo().sessionId;
        String str4 = APDataInterface.singleton().getUserInfo().sessionType;
        String str5 = APDataInterface.singleton().getUserInfo().pf;
        String str6 = APDataInterface.singleton().getUserInfo().pfKey;
        String str7 = APDataInterface.singleton().getUserInfo().zoneId;
        String str8 = APDataInterface.singleton().getUserInfo().iChannel;
        if (APAppDataInterface.singleton().isNewCGI()) {
            APNetworkManager2.getInstance().provide(false, this.mCurrency, this.mCountry, this.mChannelItem.id, this.mGoodsItem.num, this.mBillNo, aPPayReceipt.receipt, aPPayReceipt.receipt_sig, this.l);
        } else {
            APNetworkManager.getInstance().overSeanProvider(false, this.mCurrency, this.mCountry, this.mChannelItem.id, this.mGoodsItem.num, this.mBillNo, aPPayReceipt.receipt, aPPayReceipt.receipt_sig, this.l);
        }
    }

    public void queryPayResult(Activity activity) {
        this.UIHandler.sendEmptyMessage(55);
    }

    public void reprovider(APPayReceipt aPPayReceipt) {
        APAppDataInterface.singleton().getOfferid();
        String str = APDataInterface.singleton().getUserInfo().openId;
        String str2 = APDataInterface.singleton().getUserInfo().openKey;
        String str3 = APDataInterface.singleton().getUserInfo().sessionId;
        String str4 = APDataInterface.singleton().getUserInfo().sessionType;
        String str5 = APDataInterface.singleton().getUserInfo().pf;
        String str6 = APDataInterface.singleton().getUserInfo().pfKey;
        String str7 = APDataInterface.singleton().getUserInfo().zoneId;
        String str8 = APDataInterface.singleton().getUserInfo().iChannel;
        if (APAppDataInterface.singleton().isNewCGI()) {
            APNetworkManager2.getInstance().provide(true, this.mCurrency, this.mCountry, this.mChannelItem.id, "", "", aPPayReceipt.receipt, aPPayReceipt.receipt_sig, this.m);
        } else {
            APNetworkManager.getInstance().overSeanProvider(true, this.mCurrency, this.mCountry, this.mChannelItem.id, "", "", aPPayReceipt.receipt, aPPayReceipt.receipt_sig, this.m);
        }
    }
}

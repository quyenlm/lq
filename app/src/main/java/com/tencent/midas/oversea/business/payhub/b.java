package com.tencent.midas.oversea.business.payhub;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import com.tencent.midas.oversea.api.APMidasPayAPI;
import com.tencent.midas.oversea.business.APPayMananger;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.comm.APDataReportManager;
import com.tencent.midas.oversea.comm.APLog;
import com.tencent.midas.oversea.comm.APTypeChange;
import com.tencent.midas.oversea.comm.APUICommonMethod;
import com.tencent.midas.oversea.data.channel.APPayInfo;
import com.tencent.midas.oversea.data.channel.APPayReceipt;
import org.json.JSONObject;

class b implements Handler.Callback {
    final /* synthetic */ APBasePayChannel a;

    b(APBasePayChannel aPBasePayChannel) {
        this.a = aPBasePayChannel;
    }

    public boolean handleMessage(Message message) {
        switch (message.what) {
            case 1:
                this.a.b.sendEmptyMessage(1);
                break;
            case 30:
                APLog.i(APBasePayChannel.a, "cancel");
                this.a.dispose();
                APUICommonMethod.dismissWaitDialog();
                if (!this.a.d) {
                    Message obtainMessage = this.a.b.obtainMessage();
                    obtainMessage.arg1 = -1;
                    obtainMessage.obj = message.obj;
                    obtainMessage.what = 23;
                    this.a.b.sendMessage(obtainMessage);
                }
                APDataReportManager.getInstance().insertData(APDataReportManager.SDK_OVERSEA_CHANNEL_PAYRESULT, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, this.a.b() + "_" + "cancel");
                break;
            case 31:
                APUICommonMethod.showWaitDialog(this.a.c, APCommMethod.getStringId(this.a.c, "unipay_waiting"), false, (DialogInterface.OnCancelListener) null);
                int unused = this.a.e = 0;
                this.a.init(this.a.c, this.a.mChannelItem, this.a.mGoodsItem, this.a.mCountry, this.a.mCurrency);
                break;
            case 32:
                APLog.i(APBasePayChannel.a, "init success");
                this.a.prePay();
                break;
            case 33:
                APUICommonMethod.dismissWaitDialog();
                this.a.a(message);
                APLog.i(APBasePayChannel.a, "init error");
                APDataReportManager.getInstance().insertData(APDataReportManager.SDK_OVERSEA_CHANNEL_PAYRESULT, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, this.a.b() + "_" + "initerr");
                break;
            case 35:
                APLog.i(APBasePayChannel.a, "get goods success");
                if (!this.a.needOrder()) {
                    this.a.pay(this.a.c, (JSONObject) null);
                    break;
                } else {
                    this.a.order(message.obj != null ? (APPayInfo) message.obj : null);
                    break;
                }
            case 36:
                this.a.dispose();
                APUICommonMethod.dismissWaitDialog();
                this.a.a(message);
                APLog.i(APBasePayChannel.a, "get goods error:" + message.obj);
                APDataReportManager.getInstance().insertData(APDataReportManager.SDK_OVERSEA_CHANNEL_PAYRESULT, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, this.a.b() + "_" + "prepayerr_" + message.obj);
                break;
            case 38:
                APLog.i(APBasePayChannel.a, "get order success");
                APUICommonMethod.dismissWaitDialog();
                this.a.pay(this.a.c, this.a.mInfo);
                break;
            case 39:
                this.a.dispose();
                APUICommonMethod.dismissWaitDialog();
                this.a.a(message);
                APLog.i(APBasePayChannel.a, "get order error" + message.obj);
                APDataReportManager.getInstance().insertData(APDataReportManager.SDK_OVERSEA_CHANNEL_PAYRESULT, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, this.a.b() + "_" + "ordererr_" + message.obj);
                break;
            case 40:
                APLog.i(APBasePayChannel.a, "reprovide");
                APUICommonMethod.showWaitDialog(this.a.c, APCommMethod.getStringId(this.a.c, "unipay_waiting"), false, (DialogInterface.OnCancelListener) null);
                this.a.reprovider((APPayReceipt) message.obj);
                break;
            case 43:
                APLog.i(APBasePayChannel.a, "recover");
                APUICommonMethod.dismissWaitDialog();
                this.a.pay(this.a.c, (JSONObject) message.obj);
                break;
            case 46:
                APLog.i(APBasePayChannel.a, "consume");
                APDataReportManager.getInstance().insertData(APDataReportManager.SDK_OVERSEA_CHANNEL_PAYRESULT, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, this.a.b() + "_" + "providesucc");
                this.a.postPay();
                break;
            case 47:
                APLog.i(APBasePayChannel.a, "provide error");
                this.a.dispose();
                this.a.a(message);
                APDataReportManager.getInstance().insertData(APDataReportManager.SDK_OVERSEA_CHANNEL_PAYRESULT, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, this.a.b() + "_" + "provideerr_" + message.obj);
                APLog.i(APBasePayChannel.a, "save receipt");
                this.a.onSaveReceipt(this.a.j);
                break;
            case 49:
                APLog.i(APBasePayChannel.a, "consume");
                boolean unused2 = this.a.g = true;
                APDataReportManager.getInstance().insertData(APDataReportManager.SDK_OVERSEA_CHANNEL_PAYRESULT, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, this.a.b() + "_" + "reprovidesucc");
                this.a.postPay();
                break;
            case 50:
                if (message.arg1 != 5017) {
                    APLog.i(APBasePayChannel.a, "reprovide error");
                    this.a.dispose();
                    this.a.a(message);
                    APDataReportManager.getInstance().insertData(APDataReportManager.SDK_OVERSEA_CHANNEL_PAYRESULT, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, this.a.b() + "_" + "reprovideerr_" + message.obj);
                    break;
                } else {
                    APLog.i(APBasePayChannel.a, "consume 5017");
                    boolean unused3 = this.a.g = true;
                    APDataReportManager.getInstance().insertData(APDataReportManager.SDK_OVERSEA_CHANNEL_PAYRESULT, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, this.a.b() + "_" + "reprovideerr:" + 5017);
                    this.a.postPay();
                    break;
                }
            case 52:
                APLog.i(APBasePayChannel.a, "consume success");
                APUICommonMethod.dismissWaitDialog();
                APDataReportManager.getInstance().insertData(APDataReportManager.SDK_OVERSEA_CHANNEL_PAYRESULT, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, this.a.b() + "_" + "postpaysucc");
                if (message.obj != null) {
                    APPayMananger.singleton().gerCurOrder().mReceipt = message.obj.toString();
                }
                if (!this.a.g) {
                    this.a.dispose();
                    Message obtainMessage2 = this.a.b.obtainMessage();
                    obtainMessage2.what = 16;
                    APLog.i(APBasePayChannel.a, "consume success");
                    if (message.arg1 == 0) {
                        try {
                            obtainMessage2.arg1 = Integer.parseInt(this.a.mGoodsItem.num);
                        } catch (Exception e) {
                            APLog.e(APBasePayChannel.a, e.toString());
                        }
                    } else {
                        obtainMessage2.arg1 = message.arg1;
                    }
                    obtainMessage2.arg2 = 0;
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("channel", this.a.mChannelItem.id);
                        jSONObject.put("billno", this.a.mBillNo);
                        obtainMessage2.obj = jSONObject.toString();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    this.a.b.sendMessage(obtainMessage2);
                    break;
                } else {
                    this.a.dispose();
                    int unused4 = this.a.e = 0;
                    boolean unused5 = this.a.g = false;
                    this.a.UIHandler.sendEmptyMessage(31);
                    break;
                }
            case 53:
                APLog.i(APBasePayChannel.a, "consume error");
                APUICommonMethod.dismissWaitDialog();
                APDataReportManager.getInstance().insertData(APDataReportManager.SDK_OVERSEA_CHANNEL_PAYRESULT, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, this.a.b() + "_" + "postpayerr_" + message.obj);
                if (!this.a.g) {
                    this.a.dispose();
                    Message obtainMessage3 = this.a.b.obtainMessage();
                    obtainMessage3.what = 16;
                    if (message.arg1 == 0) {
                        obtainMessage3.arg1 = Integer.parseInt(this.a.mGoodsItem.num);
                    } else {
                        obtainMessage3.arg1 = message.arg1;
                    }
                    obtainMessage3.arg2 = 0;
                    try {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("channel", this.a.mChannelItem.id);
                        jSONObject2.put("billno", this.a.mBillNo);
                        obtainMessage3.obj = jSONObject2.toString();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    this.a.b.sendMessage(obtainMessage3);
                    break;
                } else {
                    this.a.dispose();
                    int unused6 = this.a.e = 0;
                    boolean unused7 = this.a.g = false;
                    this.a.UIHandler.sendEmptyMessage(31);
                    break;
                }
            case 55:
                APLog.i(APBasePayChannel.a, "pay success sdk provide:" + this.a.isSDKProvide());
                if (!this.a.isSDKProvide()) {
                    APUICommonMethod.dismissWaitDialog();
                    this.a.dispose();
                    Message obtainMessage4 = this.a.b.obtainMessage();
                    obtainMessage4.what = 16;
                    if (message.arg1 != 0 || this.a.mGoodsItem == null) {
                        obtainMessage4.arg1 = message.arg1;
                    } else {
                        obtainMessage4.arg1 = APTypeChange.StringToInt(this.a.mGoodsItem.num);
                    }
                    obtainMessage4.arg2 = -1;
                    try {
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject3.put("channel", this.a.mChannelItem.id);
                        jSONObject3.put("billno", this.a.mBillNo);
                        obtainMessage4.obj = jSONObject3.toString();
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                    this.a.b.sendMessage(obtainMessage4);
                    APDataReportManager.getInstance().insertData(APDataReportManager.SDK_OVERSEA_CHANNEL_PAYRESULT, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, this.a.b() + "_" + "paysucc");
                    break;
                } else {
                    APUICommonMethod.showWaitDialog(this.a.c, APCommMethod.getStringId(this.a.c, "unipay_waiting"), false, (DialogInterface.OnCancelListener) null);
                    APPayReceipt unused8 = this.a.j = (APPayReceipt) message.obj;
                    this.a.provide(this.a.j);
                    break;
                }
            case 56:
                this.a.dispose();
                APUICommonMethod.dismissWaitDialog();
                this.a.a(message);
                APDataReportManager.getInstance().insertData(APDataReportManager.SDK_OVERSEA_CHANNEL_PAYRESULT, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, this.a.b() + "_" + "payerr_" + message.obj);
                break;
            case 58:
                APUICommonMethod.dismissWaitDialog();
                this.a.dispose();
                Message obtainMessage5 = this.a.b.obtainMessage();
                obtainMessage5.what = 18;
                this.a.b.sendMessage(obtainMessage5);
                APDataReportManager.getInstance().insertData(APDataReportManager.SDK_OVERSEA_CHANNEL_PAYRESULT, APMidasPayAPI.singleton().mBuyType, (String) null, (String) null, this.a.b() + "_" + "unknown");
                break;
        }
        return true;
    }
}

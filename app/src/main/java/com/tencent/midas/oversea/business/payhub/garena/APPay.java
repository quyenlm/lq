package com.tencent.midas.oversea.business.payhub.garena;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Message;
import android.text.TextUtils;
import com.beetalk.sdk.GGLoginSession;
import com.beetalk.sdk.GGPlatform;
import com.garena.pay.android.GGAndroidPaymentPlatform;
import com.garena.pay.android.GGPayResponseCallback;
import com.garena.pay.android.data.GGPayment;
import com.garena.pay.android.data.TransactionInfo;
import com.garena.pay.android.data.TransactionStatus;
import com.tencent.midas.oversea.business.APPayMananger;
import com.tencent.midas.oversea.business.payhub.APBasePayChannel;
import com.tencent.midas.oversea.comm.APCommMethod;
import com.tencent.midas.oversea.comm.APDataInterface;
import com.tencent.midas.oversea.comm.APLog;
import java.util.HashMap;
import java.util.Locale;
import org.json.JSONObject;

public class APPay extends APBasePayChannel {
    /* access modifiers changed from: private */
    public static final String LOGTAG = APPay.class.getSimpleName();
    public static final int PAYMENT_REQUEST_ID = 4658;
    private Activity mActivity;
    private GGPayResponseCallback mPayResponseCallback = new GGPayResponseCallback() {
        public void onPaymentProcessed(TransactionStatus status, Exception exception, TransactionInfo transactionInfo) {
            if (status.getValue().intValue() >= TransactionStatus.CLOSED_WITH_ERROR.getValue().intValue()) {
                APLog.e(APPay.LOGTAG, "Error: " + exception.getMessage());
                Message msg = APPay.this.UIHandler.obtainMessage();
                msg.obj = "";
                msg.what = 56;
                APPay.this.UIHandler.sendMessage(msg);
                return;
            }
            Message succMsg = Message.obtain();
            succMsg.what = 55;
            APPay.this.UIHandler.sendMessage(succMsg);
        }
    };

    public static HashMap<String, String> url2Map(String params) {
        HashMap<String, String> desHashMap = new HashMap<>();
        try {
            if (!TextUtils.isEmpty(params)) {
                String[] paramArray = params.split("\\&");
                String key = "";
                String value = "";
                for (int i = 0; i < paramArray.length; i++) {
                    try {
                        key = paramArray[i].split("\\=")[0];
                        value = paramArray[i].split("\\=")[1];
                    } catch (Exception e) {
                    }
                    if (!TextUtils.isEmpty(key)) {
                        desHashMap.put(key, value);
                    }
                }
            } else {
                APLog.i("url2Map", "url后参数为空");
            }
        } catch (Exception e2) {
            APLog.w("url2Map", e2.toString());
        }
        return desHashMap;
    }

    public void pay(Activity act, JSONObject obj) {
        String language;
        String country;
        this.mActivity = act;
        if (!GGLoginSession.checkSessionValidity()) {
            Message msg = this.UIHandler.obtainMessage();
            msg.obj = APCommMethod.getStringId(APPayMananger.singleton().getApplicationContext(), "unipay_pay_error_tip");
            msg.what = 1;
            this.UIHandler.sendMessage(msg);
            return;
        }
        try {
            ApplicationInfo appInfo = act.getPackageManager().getApplicationInfo(act.getPackageName(), 128);
            String appid = appInfo.metaData.getString("MIDAS.APP_SDK_ASSIGN_ID");
            APLog.i(LOGTAG, "appid:" + appid);
            GGPayment.GGPaymentBuilder builder = new GGPayment.GGPaymentBuilder();
            builder.setAppId(appid);
            builder.setBuyerId(GGLoginSession.getCurrentSession().getTokenValue().getOpenId());
            builder.setToken(GGLoginSession.getCurrentSession().getTokenValue().getAuthToken());
            String extras = APDataInterface.singleton().getUserInfo().extras;
            HashMap<String, String> map = url2Map(extras);
            APLog.i(LOGTAG, "extras:" + extras);
            builder.setServerId(Integer.valueOf(Integer.parseInt(map.get("serverId"))));
            APLog.i(LOGTAG, "serverId:" + Integer.parseInt(map.get("serverId")));
            builder.setRoleId(Integer.valueOf(Integer.parseInt(map.get("roleId"))));
            APLog.i(LOGTAG, "roleid:" + Integer.parseInt(map.get("roleId")));
            builder.setPlatform(GGLoginSession.getCurrentSession().getPlatform());
            String name = appInfo.metaData.getString("MIDAS.VIRTUAL_CURRENCY_NAME");
            APLog.i(LOGTAG, "virtual name:" + name);
            long rebateId = -1;
            try {
                if (!TextUtils.isEmpty(map.get("rebateId"))) {
                    rebateId = Long.parseLong(map.get("rebateId"));
                }
            } catch (Exception e) {
                APLog.e(LOGTAG, e.toString());
            }
            APLog.i(LOGTAG, "rebateId:" + rebateId);
            String local = map.get("local");
            if (TextUtils.isEmpty(local)) {
                language = "zh";
                country = "TW";
            } else {
                String[] lz = local.split("_");
                if (lz == null || lz.length != 2) {
                    APLog.e(LOGTAG, "local info is error");
                    language = "zh";
                    country = "TW";
                } else {
                    language = lz[0];
                    if (TextUtils.isEmpty(language)) {
                        language = "zh";
                    }
                    APLog.i(LOGTAG, "language:" + language);
                    country = lz[1];
                    if (TextUtils.isEmpty(country)) {
                        country = "TW";
                    }
                    APLog.i(LOGTAG, "country:" + country);
                }
            }
            builder.setLocale(new Locale(language, country));
            builder.setVirtualCurrencyName(name);
            if (rebateId != -1) {
                builder.setRebateId(Long.valueOf(rebateId));
            }
            GGAndroidPaymentPlatform.processPayment(act, builder.build(), this.mPayResponseCallback, PAYMENT_REQUEST_ID);
        } catch (Exception e2) {
            e2.printStackTrace();
            Message msg2 = this.UIHandler.obtainMessage();
            msg2.obj = APCommMethod.getStringId(APPayMananger.singleton().getApplicationContext(), "unipay_pay_error_tip");
            msg2.what = 56;
            this.UIHandler.sendMessage(msg2);
        }
    }

    public boolean handleActivityResult(int requestCode, int resultCode, Intent data) {
        APLog.d(LOGTAG, "google wallet payHelper handleActivityResult requestCode:" + requestCode + " resultCode:" + resultCode);
        if (requestCode == 4658) {
            GGAndroidPaymentPlatform.onActivityResult(data);
            return false;
        }
        GGPlatform.handleActivityResult(this.mActivity, requestCode, resultCode, data);
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean needOrder() {
        return false;
    }
}

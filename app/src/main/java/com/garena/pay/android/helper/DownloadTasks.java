package com.garena.pay.android.helper;

import android.text.TextUtils;
import com.beetalk.sdk.GGLoginSession;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.LocaleHelper;
import com.beetalk.sdk.networking.HttpRequestTask;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.garena.pay.android.GGPayRequest;
import com.garena.pay.android.inappbilling.IabHelper;
import com.garena.pay.android.inappbilling.Purchase;
import java.util.HashMap;
import java.util.Map;

public class DownloadTasks {
    public static void initGooglePayment(HttpRequestTask.StringCallback callback, Map<String, String> data) {
        new HttpRequestTask(HttpRequestTask.RequestType.POST, data, (HttpRequestTask.BaseCallback) callback).executeParallel(SDKConstants.getInitGooglePayUrl());
    }

    public static void commitGooglePayment(HttpRequestTask.StringCallback callback, Map<String, String> data) {
        new HttpRequestTask(HttpRequestTask.RequestType.POST, data, (HttpRequestTask.BaseCallback) callback).executeParallel(SDKConstants.getGoogleCommitPayUrl());
    }

    public static HttpRequestTask.StringResult commitGooglePaymentSync(Map<String, String> data) {
        return new HttpRequestTask(HttpRequestTask.RequestType.POST, data, (HttpRequestTask.BaseCallback) null).executeStringSync(SDKConstants.getGoogleCommitPayUrl());
    }

    public static void redeemRebateCard(HttpRequestTask.StringCallback callback, Map<String, String> data) {
        new HttpRequestTask(HttpRequestTask.RequestType.POST, data, (HttpRequestTask.BaseCallback) callback).executeParallel(SDKConstants.getRedeemURL());
    }

    public static void getPaymentChannels(HttpRequestTask.StringCallback callback, Map<String, String> data) {
        new HttpRequestTask(HttpRequestTask.RequestType.GET, data, (HttpRequestTask.BaseCallback) callback).executeParallel(SDKConstants.getChannelsGetUrl());
    }

    public static void getRebateOptions(HttpRequestTask.StringCallback callback, Map<String, String> data) {
        new HttpRequestTask(HttpRequestTask.RequestType.GET, data, (HttpRequestTask.BaseCallback) callback).executeParallel(SDKConstants.getRebateOptionsUrl());
    }

    public static Map<String, String> buildChannelGetParams(GGPayRequest request) {
        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", request.getClientPaymentRequest().getToken());
        if (request.getClientPaymentRequest().getLocale() != null) {
            map.put("locale", LocaleHelper.getLocaleStringForServer(request.getClientPaymentRequest().getLocale()));
        } else {
            map.put("locale", LocaleHelper.getLocaleStringForServer(LocaleHelper.getDefaultLocale(request.getActivity())));
        }
        map.put("app_id", request.getClientPaymentRequest().getAppId());
        map.put("open_id", request.getClientPaymentRequest().getBuyerId());
        map.put("source", GGLoginSession.getCurrentSession().getSourceId().toString());
        map.put("platform", String.valueOf(request.getClientPaymentRequest().getPlatform()));
        map.put(GGLiveConstants.PARAM.CLIENT_TYPE, String.valueOf(2));
        map.put("app_server_id", String.valueOf(request.getClientPaymentRequest().getAppServerId()));
        map.put("app_role_id", String.valueOf(request.getClientPaymentRequest().getRoleId()));
        BBLogger.d("Channel Get Request Data %s", map.toString());
        return map;
    }

    public static Map<String, String> buildGooglePurchaseCommitParams(Purchase purchase, String token, String appId, int serverId, int roleId, boolean check) {
        Map<String, String> map = new HashMap<>();
        map.put("purchase_data", purchase.getOriginalJson());
        map.put("data_signature", purchase.getSignature());
        map.put("check", String.valueOf(check ? 1 : 0));
        if (TextUtils.isEmpty(purchase.getSignature()) && purchase.getOriginalJson().contains(SDKConstants.ANDROID_TEST_PURCHASED)) {
            map.put("purchase_data", purchase.getOriginalJson().replace(SDKConstants.ANDROID_TEST_PURCHASED, SDKConstants.TEST_PURCHASE_ITEM_ID).replace("transactionId.9999", purchase.getDeveloperPayload()));
            map.put("data_signature", "test_signature");
        }
        if (TextUtils.isEmpty(purchase.getDeveloperPayload()) || TextUtils.equals(purchase.getItemType(), IabHelper.ITEM_TYPE_SUBS)) {
            map.put("access_token", token);
            map.put("app_id", appId);
            map.put(GGLiveConstants.PARAM.CLIENT_TYPE, String.valueOf(2));
            map.put("app_server_id", String.valueOf(serverId));
            map.put("app_role_id", String.valueOf(roleId));
        }
        return map;
    }
}

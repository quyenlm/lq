package com.garena.pay.android.helper;

import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.garena.pay.android.data.GGPayment;
import com.garena.pay.android.data.GGRebateItem;
import com.garena.pay.android.data.GGRedeemResponse;
import com.garena.pay.android.data.GGRedeemResultItem;
import com.garena.pay.android.ndk.RebateOptionItem;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tencent.component.net.download.multiplex.download.DownloadDBHelper;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NetworkUtils {
    public static List<GGPayment.PaymentChannel> parseNormalPaymentChannelList(String response) {
        try {
            List<GGPayment.PaymentChannel> allChannelList = parseAllPaymentChannelList(new JSONObject(response).getJSONArray("channels"));
            List<GGPayment.PaymentChannel> channelList = new ArrayList<>();
            for (GGPayment.PaymentChannel channel : allChannelList) {
                if (channel.isGooglePlay()) {
                    if (channel.isDirectPay()) {
                        channelList.add(channel);
                    } else {
                        List<GGPayment.Denomination> allItems = channel.getItems();
                        List<GGPayment.Denomination> normalItems = new ArrayList<>();
                        for (GGPayment.Denomination item : allItems) {
                            if (item.getRebateId() == 0) {
                                normalItems.add(item);
                            }
                        }
                        channel.setItems(normalItems);
                        channelList.add(channel);
                    }
                }
            }
            return channelList;
        } catch (JSONException e) {
            BBLogger.e(e);
            return null;
        }
    }

    private static List<GGPayment.PaymentChannel> parseAllPaymentChannelList(JSONArray channelJsonArray) throws JSONException {
        List<GGPayment.PaymentChannel> channelList = new ArrayList<>();
        for (int i = 0; i < channelJsonArray.length(); i++) {
            JSONObject channelObject = channelJsonArray.getJSONObject(i);
            int channelId = channelObject.getInt("channel");
            if (channelId == 201069) {
                String name = channelObject.getString("name");
                String iconUrl = channelObject.getString("icon");
                float discount = (float) channelObject.getDouble("discount");
                String description = channelObject.getString("description");
                String currencyIconUrl = channelObject.optString("currency_icon", "");
                int category = channelObject.optInt("category");
                int flag = channelObject.optInt(DownloadDBHelper.FLAG);
                List<GGPayment.Denomination> itemList = parseSKUItemList(channelObject);
                if (!TextUtils.isEmpty(currencyIconUrl) && itemList != null && itemList.size() > 0) {
                    for (GGPayment.Denomination item : itemList) {
                        item.setCurrencyIconUrl(currencyIconUrl);
                    }
                }
                GGPayment.PaymentChannel paymentChannel = new GGPayment.PaymentChannel();
                paymentChannel.setChannelId(String.valueOf(channelId));
                paymentChannel.setName(name);
                paymentChannel.setIconUrl(iconUrl);
                paymentChannel.setDiscount(discount);
                paymentChannel.setDescription(description);
                paymentChannel.setItems(itemList);
                paymentChannel.setCategory(category);
                paymentChannel.setFlag(flag);
                channelList.add(paymentChannel);
            }
        }
        return channelList;
    }

    public static List<GGPayment.PaymentChannel> parseRebatePaymentChannelList(String response, Long rebateId) {
        if (rebateId == null || rebateId.longValue() == 0) {
            return parseNormalPaymentChannelList(response);
        }
        if (rebateId.longValue() == -1) {
            try {
                return parseAllPaymentChannelList(new JSONObject(response).getJSONArray("channels"));
            } catch (JSONException e) {
                BBLogger.e(e);
                return null;
            }
        } else {
            try {
                JSONObject jsonObject = new JSONObject(response);
                List<GGPayment.PaymentChannel> allChannelList = parseAllPaymentChannelList(jsonObject.getJSONArray("channels"));
                List<GGRebateItem> allRebateItems = parseRebateItemList(jsonObject);
                List<GGPayment.PaymentChannel> rebateChannelList = new ArrayList<>();
                for (GGPayment.PaymentChannel channel : allChannelList) {
                    if (!channel.isDirectPay()) {
                        List<GGPayment.Denomination> allItems = channel.getItems();
                        List<GGPayment.Denomination> rebateItems = new ArrayList<>();
                        if (allItems != null) {
                            for (GGPayment.Denomination item : allItems) {
                                if (item.getRebateId() > 0 && item.getRebateId() == rebateId.longValue() && isRebateIdValid(item.getRebateId(), allRebateItems)) {
                                    rebateItems.add(item);
                                }
                            }
                        }
                        if (rebateItems.size() > 0) {
                            channel.setItems(rebateItems);
                            rebateChannelList.add(channel);
                        }
                    }
                }
                return rebateChannelList;
            } catch (JSONException e2) {
                BBLogger.e(e2);
                return null;
            }
        }
    }

    private static boolean isRebateIdValid(long rebateId, List<GGRebateItem> rebateItems) {
        if (rebateItems == null) {
            return false;
        }
        for (GGRebateItem rebateItem : rebateItems) {
            if (rebateItem.getId() == rebateId && rebateItem.isValid()) {
                return true;
            }
        }
        return false;
    }

    public static List<Long> parseRebateIdList(String response) {
        try {
            List<Long> rebateIdList = new ArrayList<>();
            List<GGRebateItem> rebateItems = parseRebateItemList(new JSONObject(response));
            if (rebateItems == null) {
                return rebateIdList;
            }
            for (GGRebateItem item : rebateItems) {
                if (item.isValid()) {
                    rebateIdList.add(Long.valueOf(item.getId()));
                }
            }
            return rebateIdList;
        } catch (JSONException e) {
            BBLogger.e(e);
            return null;
        }
    }

    private static List<GGRebateItem> parseRebateItemList(JSONObject jsonObject) {
        try {
            JSONArray rebateArray = jsonObject.getJSONArray("rebates");
            List<GGRebateItem> rebateItems = new ArrayList<>();
            for (int i = 0; i < rebateArray.length(); i++) {
                JSONObject rebateObject = rebateArray.getJSONObject(i);
                GGRebateItem rebateItem = new GGRebateItem();
                rebateItem.setId(rebateObject.getLong("id"));
                rebateItem.setValid(rebateObject.getBoolean("valid"));
                rebateItem.setDays(rebateObject.getInt("rebate_days"));
                rebateItem.setAmount(rebateObject.getDouble("rebate_amount"));
                rebateItem.setName(rebateObject.getString("name"));
                rebateItem.setDescription(rebateObject.getString("description"));
                rebateItems.add(rebateItem);
            }
            return rebateItems;
        } catch (JSONException e) {
            BBLogger.e(e);
            return null;
        }
    }

    public static List<RebateOptionItem> parseRebateOptions(String response) {
        try {
            JSONArray rebateArray = new JSONObject(response).getJSONArray("rebates");
            List<RebateOptionItem> rebateItems = new ArrayList<>();
            for (int i = 0; i < rebateArray.length(); i++) {
                JSONObject rebateObject = rebateArray.getJSONObject(i);
                RebateOptionItem rebateItem = new RebateOptionItem();
                rebateItem.rebateId = rebateObject.optLong("id");
                rebateItem.rebateDays = rebateObject.getInt("rebate_days");
                rebateItem.rebateAmount = rebateObject.optInt("rebate_amount");
                rebateItem.remainingDays = rebateObject.optInt(SDKConstants.WEB_PAY.EXTRA_REMAINING_DAYS);
                rebateItem.advanceDays = rebateObject.optInt("advance_days");
                rebateItem.validToPurchase = rebateObject.optBoolean("valid_to_purchase");
                rebateItem.validToRedeem = rebateObject.optBoolean("valid_to_redeem");
                rebateItem.owned = rebateObject.optBoolean("owned");
                rebateItem.name = rebateObject.optString("name");
                rebateItem.description = rebateObject.optString("description");
                rebateItems.add(rebateItem);
            }
            return rebateItems;
        } catch (JSONException e) {
            BBLogger.e(e);
            return null;
        }
    }

    public static GGRedeemResponse parseRedeemResult(String response) {
        try {
            GGRedeemResponse resp = new GGRedeemResponse();
            JSONObject jsonObject = new JSONObject(response);
            resp.result = jsonObject.getInt(GGLiveConstants.PARAM.RESULT);
            if (resp.result != 0) {
                return null;
            }
            resp.redeemList = new ArrayList();
            JSONArray array = jsonObject.getJSONArray("redeemed");
            for (int i = 0; i < array.length(); i++) {
                JSONObject redeemResult = array.getJSONObject(i);
                resp.redeemList.add(new GGRedeemResultItem(redeemResult.getLong(SDKConstants.WEB_PAY.EXTRA_REBATE_CARD_ID), redeemResult.getInt(SDKConstants.WEB_PAY.EXTRA_AMOUNT), redeemResult.getInt(SDKConstants.WEB_PAY.EXTRA_REMAINING_DAYS)));
            }
            resp.totalRedeemed = jsonObject.getInt("total_redeemed");
            return resp;
        } catch (JSONException e) {
            BBLogger.e(e);
            return null;
        }
    }

    public static List<GGPayment.Denomination> parseSKUItemList(JSONObject jsonObject) throws JSONException {
        ArrayList arrayList = new ArrayList();
        JSONArray array = null;
        try {
            array = jsonObject.getJSONArray("items");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String name = object.optString("name");
                String icon = object.optString("icon");
                String item_id = object.optString("id");
                Integer app_point_amount = Integer.valueOf(object.optInt(SDKConstants.WEB_PAY.EXTRA_AMOUNT));
                String price = object.optString(FirebaseAnalytics.Param.PRICE);
                boolean promo = object.optBoolean(NotificationCompat.CATEGORY_PROMO, false);
                long rebateId = object.optLong(SDKConstants.WEB_PAY.EXTRA_REBATE_CARD_ID, 0);
                GGPayment.Denomination denomination = new GGPayment.Denomination(name, item_id, app_point_amount, icon, price, promo, Integer.valueOf(object.optInt("promo_amount")));
                JSONObject subsObject = object.optJSONObject("subscription");
                if (subsObject != null) {
                    int period = subsObject.optInt("period", 0);
                    int status = subsObject.optInt("status", -1);
                    int lastRenewal = subsObject.optInt("last_payment_time", 0);
                    denomination.getClass();
                    denomination.setSubscription(new GGPayment.Denomination.Subscription(period, status, lastRenewal));
                }
                denomination.setRebateId(rebateId);
                arrayList.add(denomination);
            }
        }
        return arrayList;
    }
}

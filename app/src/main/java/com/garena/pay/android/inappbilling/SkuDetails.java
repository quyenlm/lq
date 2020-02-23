package com.garena.pay.android.inappbilling;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.tencent.midas.oversea.api.UnityPayHelper;
import org.json.JSONException;
import org.json.JSONObject;

public class SkuDetails {
    private final String mDescription;
    private final String mItemType;
    private final String mJson;
    private final String mPrice;
    private final long mPriceAmountMicros;
    private final String mPriceCurrencyCode;
    private final String mSku;
    private final String mTitle;
    private final String mType;

    public SkuDetails(String jsonSkuDetails) throws JSONException {
        this(IabHelper.ITEM_TYPE_INAPP, jsonSkuDetails);
    }

    public SkuDetails(String itemType, String jsonSkuDetails) throws JSONException {
        this.mItemType = itemType;
        this.mJson = jsonSkuDetails;
        JSONObject o = new JSONObject(this.mJson);
        this.mSku = o.optString(UnityPayHelper.PRODUCTID);
        this.mType = o.optString("type");
        this.mPrice = o.optString(FirebaseAnalytics.Param.PRICE);
        this.mPriceAmountMicros = o.optLong("price_amount_micros");
        this.mPriceCurrencyCode = o.optString("price_currency_code");
        this.mTitle = o.optString("title");
        this.mDescription = o.optString("description");
    }

    public String getSku() {
        return this.mSku;
    }

    public String getType() {
        return this.mType;
    }

    public String getPrice() {
        return this.mPrice;
    }

    public long getPriceAmountMicros() {
        return this.mPriceAmountMicros;
    }

    public String getPriceCurrencyCode() {
        return this.mPriceCurrencyCode;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public String toString() {
        return "SkuDetails:" + this.mJson;
    }
}

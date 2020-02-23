package com.tencent.imsdk.pay.entity;

import com.tencent.imsdk.tool.json.JsonProp;
import com.tencent.imsdk.tool.json.JsonSerializable;

public class IMSkuDetails extends JsonSerializable {
    @JsonProp(name = "description")
    public String mDescription;
    @JsonProp(name = "itemType")
    public String mItemType;
    String mJson = "{}";
    @JsonProp(name = "price")
    public String mPrice;
    @JsonProp(name = "sku")
    public String mSku;
    @JsonProp(name = "title")
    public String mTitle;
    @JsonProp(name = "type")
    public String mType;

    public String getSku() {
        return this.mSku;
    }

    public String getType() {
        return this.mType;
    }

    public String getPrice() {
        return this.mPrice;
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

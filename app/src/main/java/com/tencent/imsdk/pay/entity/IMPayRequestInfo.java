package com.tencent.imsdk.pay.entity;

import com.tencent.imsdk.tool.json.JsonProp;
import com.tencent.imsdk.tool.json.JsonSerializable;
import org.json.JSONException;
import org.json.JSONObject;

public class IMPayRequestInfo extends JsonSerializable {
    @JsonProp(name = "amt")
    public String amt;
    @JsonProp(name = "appId")
    public String appId;
    @JsonProp(name = "billno")
    public String billno;
    @JsonProp(name = "changeKey")
    public changkeyType changeKey;
    @JsonProp(name = "country")
    public String country;
    @JsonProp(name = "currencyType")
    public String currencyType;
    @JsonProp(name = "extend")
    public String extend;
    @JsonProp(name = "from")
    public String from;
    @JsonProp(name = "isChangeKey")
    public boolean isChangeKey;
    @JsonProp(name = "isDepositGameCoin")
    public boolean isDepostGameCoin;
    @JsonProp(name = "keyType")
    public keytype keyType;
    @JsonProp(name = "openId")
    public String openId;
    @JsonProp(name = "openKey")
    public String openKey;
    @JsonProp(name = "payItem")
    public String payItem;
    @JsonProp(name = "payProductType")
    public int payProductType;
    @JsonProp(name = "pf")
    public String pf;
    @JsonProp(name = "pfKey")
    public String pfKey;
    @JsonProp(name = "plat")
    public String plat;
    @JsonProp(name = "productID")
    public String productID;
    @JsonProp(name = "productType")
    public productType productType;
    @JsonProp(name = "quantity")
    public int quantity;
    @JsonProp(name = "resId")
    public String resId;
    @JsonProp(name = "sessionId")
    public String sessionId;
    @JsonProp(name = "sessionType")
    public String sessionType;
    @JsonProp(name = "varItem")
    public String varItem;
    @JsonProp(name = "zoneId")
    public String zoneId;

    public enum changkeyType {
        Nonmal,
        Resort
    }

    public enum keytype {
        BaseKey,
        Secretkey
    }

    public enum productType {
        Consumable,
        Non_Consumable,
        Subscription
    }

    public IMPayRequestInfo() {
    }

    public IMPayRequestInfo(JSONObject object) throws JSONException {
        super(object);
    }

    public IMPayRequestInfo(String json) throws JSONException {
        super(json);
    }

    public String toString() {
        return "IMPayRequestInfo{openId='" + this.openId + '\'' + ", openKey='" + this.openKey + '\'' + ", sessionId='" + this.sessionId + '\'' + ", sessionType='" + this.sessionType + '\'' + ", pf='" + this.pf + '\'' + ", pfKey='" + this.pfKey + '\'' + ", zoneId='" + this.zoneId + '\'' + ", appId='" + this.appId + '\'' + ", plat='" + this.plat + '\'' + ", productID='" + this.productID + '\'' + ", payItem='" + this.payItem + '\'' + ", payProductType=" + this.payProductType + ", productType=" + this.productType + ", billno='" + this.billno + '\'' + ", changeKey=" + this.changeKey + ", keyType=" + this.keyType + ", amt='" + this.amt + '\'' + ", quantity=" + this.quantity + ", from='" + this.from + '\'' + ", isChangeKey=" + this.isChangeKey + ", isDepostGameCoin=" + this.isDepostGameCoin + ", varItem='" + this.varItem + '\'' + ", resId='" + this.resId + '\'' + ", country='" + this.country + '\'' + ", currencyType='" + this.currencyType + '\'' + ", extend='" + this.extend + '\'' + '}';
    }
}

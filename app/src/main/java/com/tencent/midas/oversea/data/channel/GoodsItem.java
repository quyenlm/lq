package com.tencent.midas.oversea.data.channel;

public class GoodsItem {
    public String country;
    public String currency_type;
    public String num;
    public String price;
    public String producename;
    public String productType;
    public String productid;

    public String toString() {
        return "{ productid=" + this.productid + " | " + "producename=" + this.producename + " | " + "price=" + this.price + " | " + "num=" + this.num + " | " + "productType=" + this.productType + " | " + "country=" + this.country + " | " + "currency_type=" + this.currency_type + " }";
    }
}

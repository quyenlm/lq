package com.tencent.midas.oversea.api.request;

import java.io.Serializable;

public abstract class APMidasBaseRequest implements Serializable {
    public static final String DEFAULTPRODUCTID = "FFFFFFFF";
    public static final int MALL_TYPE_DEFAULT = 0;
    public static final int MALL_TYPE_GROUPBUY = 1;
    public static final int MALL_TYPE_VMALL = 2;
    public static final String OFFER_TYPE_BG = "bg";
    public static final String OFFER_TYPE_GAME = "save";
    public static final String OFFER_TYPE_UNIONMONTH = "unimonth";
    private static final long serialVersionUID = -9123623786877679280L;
    public String acctType;
    public String country;
    public String currency_type;
    public APMidasExtendInfo extendInfo;
    public String extras;
    public String h5Url;
    public boolean isCanChange;
    public int mOldType;
    public String mType;
    public int mallType;
    public APMidasMPInfo mpInfo;
    public String offerId;
    public String openId;
    public String openKey;
    public String pf;
    public String pfKey;
    public byte[] resData;
    public int resId;
    public String reserv;
    public String saveValue;
    public String sessionId;
    public String sessionType;
    public String zoneId;

    public class APMidasExtendInfo implements Serializable {
        private static final long serialVersionUID = -5387967973327966068L;
        public String iChannel;
        public boolean isShowListOtherNum;
        public boolean isShowNum;
        public String unit;
        public String userExtend;

        public APMidasExtendInfo() {
            this.userExtend = "";
            this.iChannel = "";
            this.unit = "";
            this.isShowNum = true;
            this.isShowListOtherNum = true;
            this.userExtend = "";
            this.iChannel = "";
        }
    }

    public class APMidasMPInfo implements Serializable {
        private static final long serialVersionUID = 631101753621041424L;
        public String discountType;
        public String discountUrl;
        public String discoutId;
        public String drmInfo;
        public String extras;
        public String payChannel;
        public String productid;

        public APMidasMPInfo() {
            this.drmInfo = "";
            this.discoutId = "";
            this.extras = "";
            this.payChannel = "";
            this.discountType = "";
            this.discountUrl = "";
            this.drmInfo = "";
            this.discoutId = "";
            this.extras = "";
        }
    }

    public APMidasBaseRequest() {
        this.mallType = 0;
        this.h5Url = "";
        this.currency_type = "";
        this.country = "";
        this.extras = "";
        this.offerId = "";
        this.openId = "";
        this.openKey = "";
        this.sessionId = "";
        this.sessionType = "";
        this.zoneId = "";
        this.pf = "";
        this.pfKey = "";
        this.resId = 0;
        this.acctType = "common";
        this.saveValue = "";
        this.isCanChange = true;
        this.mallType = 0;
        this.h5Url = "";
        this.mpInfo = new APMidasMPInfo();
        this.extendInfo = new APMidasExtendInfo();
    }

    public String getAcctType() {
        return this.acctType;
    }

    public String getAssignProductid() {
        return this.mpInfo.productid;
    }

    public String getDiscountType() {
        return this.mpInfo.discountType;
    }

    public String getDiscountUrl() {
        return this.mpInfo.discountUrl;
    }

    public String getDiscoutId() {
        return this.mpInfo.discoutId;
    }

    public String getDrmInfo() {
        return this.mpInfo.drmInfo;
    }

    public String getExtras() {
        return this.mpInfo.extras;
    }

    public String getH5Url() {
        return this.h5Url;
    }

    public boolean getIsCanChange() {
        return this.isCanChange;
    }

    public int getMallType() {
        return this.mallType;
    }

    public String getOfferId() {
        return this.offerId;
    }

    public String getOpenId() {
        return this.openId;
    }

    public String getOpenKey() {
        return this.openKey;
    }

    public String getPayChannel() {
        return this.mpInfo.payChannel;
    }

    public String getPf() {
        return this.pf;
    }

    public String getPfKey() {
        return this.pfKey;
    }

    public byte[] getResData() {
        return this.resData;
    }

    public int getResId() {
        return this.resId;
    }

    public String getReserv() {
        return this.reserv;
    }

    public String getSaveValue() {
        return this.saveValue;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public String getSessionType() {
        return this.sessionType;
    }

    public boolean getShowListOtherNum() {
        return this.extendInfo.isShowListOtherNum;
    }

    public boolean getShowNum() {
        return this.extendInfo.isShowNum;
    }

    public String getUnit() {
        return this.extendInfo.unit;
    }

    public String getZoneId() {
        return this.zoneId;
    }

    public void setAcctType(String str) {
        this.acctType = str;
    }

    public void setDiscountType(String str) {
        this.mpInfo.discountType = str;
    }

    public void setDiscountUrl(String str) {
        this.mpInfo.discountUrl = str;
    }

    public void setDiscoutId(String str) {
        this.mpInfo.discoutId = str;
    }

    public void setDrmInfo(String str) {
        this.mpInfo.drmInfo = str;
    }

    public void setExtras(String str) {
        this.mpInfo.extras = str;
    }

    public void setH5Url(String str) {
        this.h5Url = str;
    }

    public void setIsCanChange(boolean z) {
        this.isCanChange = z;
    }

    public void setMallType(int i) {
        this.mallType = i;
    }

    public void setOfferId(String str) {
        this.offerId = str;
    }

    public void setOpenId(String str) {
        this.openId = str;
    }

    public void setOpenKey(String str) {
        this.openKey = str;
    }

    public void setPayChannel(String str) {
        this.mpInfo.payChannel = str;
    }

    public void setPf(String str) {
        this.pf = str;
    }

    public void setPfKey(String str) {
        this.pfKey = str;
    }

    public void setResData(byte[] bArr) {
        this.resData = bArr;
    }

    public void setResId(int i) {
        this.resId = i;
    }

    public void setReserv(String str) {
        this.reserv = str;
    }

    public void setSaveValue(String str) {
        this.saveValue = str;
    }

    public void setSessionId(String str) {
        this.sessionId = str;
    }

    public void setSessionType(String str) {
        this.sessionType = str;
    }

    public void setShowListOtherNum(boolean z) {
        this.extendInfo.isShowListOtherNum = z;
    }

    public void setShowNum(boolean z) {
        this.extendInfo.isShowNum = z;
    }

    public void setUnit(String str) {
        this.extendInfo.unit = str;
    }

    public void setZoneId(String str) {
        this.zoneId = str;
    }
}

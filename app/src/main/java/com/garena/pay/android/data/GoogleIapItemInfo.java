package com.garena.pay.android.data;

public class GoogleIapItemInfo {
    private final int mAppPointAmount;
    private final boolean mIsPromotion;
    private final String mItemIconUrl;
    private final String mItemName;
    private final String mItemSku;

    private GoogleIapItemInfo(Builder b) {
        this.mItemSku = b.itemSku;
        this.mItemName = b.itemName;
        this.mItemIconUrl = b.itemIconUrl;
        this.mAppPointAmount = b.appPointAmount;
        this.mIsPromotion = b.isPromotion;
    }

    public String getItemSku() {
        return this.mItemSku;
    }

    public String getItemName() {
        return this.mItemName;
    }

    public String getItemIcon() {
        return this.mItemIconUrl;
    }

    public int getAmount() {
        return this.mAppPointAmount;
    }

    public boolean isPromotion() {
        return this.mIsPromotion;
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public int appPointAmount;
        /* access modifiers changed from: private */
        public boolean isPromotion;
        /* access modifiers changed from: private */
        public String itemIconUrl;
        /* access modifiers changed from: private */
        public String itemName;
        /* access modifiers changed from: private */
        public String itemSku;

        public Builder itemSku(String itemSku2) {
            this.itemSku = itemSku2;
            return this;
        }

        public Builder itemName(String itemName2) {
            this.itemName = itemName2;
            return this;
        }

        public Builder itemIconUrl(String itemIconUrl2) {
            this.itemIconUrl = itemIconUrl2;
            return this;
        }

        public Builder appPointAmount(int appPointAmount2) {
            this.appPointAmount = appPointAmount2;
            return this;
        }

        public Builder isPromotion(boolean isPromotion2) {
            this.isPromotion = isPromotion2;
            return this;
        }

        public GoogleIapItemInfo build() {
            return new GoogleIapItemInfo(this);
        }
    }
}

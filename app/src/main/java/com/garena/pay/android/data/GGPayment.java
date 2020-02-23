package com.garena.pay.android.data;

import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.helper.LocaleHelper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GGPayment implements Serializable {
    public static final long ALL_ITEMS = -1;
    public static final long NON_REBATE_ITEMS = 0;
    private static final long serialVersionUID = 1;
    private String appId;
    private Integer appServerId;
    private String buyerId;
    private Float conversionRatio;
    private boolean convertGooglePlayPrices;
    private List<Denomination> denominations;
    private String description;
    private Locale locale;
    private Integer platform;
    private Long rebateId;
    private Integer roleId;
    private String token;
    private String transactionId;
    private String virtualCurrencyName;

    public Long getRebateId() {
        return this.rebateId;
    }

    public void setRebateId(Long rebateId2) {
        this.rebateId = rebateId2;
    }

    public boolean isConvertGooglePlayPrices() {
        return this.convertGooglePlayPrices;
    }

    public void setConvertGooglePlayPrices(boolean convertGooglePlayPrices2) {
        this.convertGooglePlayPrices = convertGooglePlayPrices2;
    }

    public GGPayment(List<Denomination> denominations2, Locale locale2, Float conversionRatio2, String token2, String virtualCurrencyName2, String appId2, String buyerId2, String description2, String transactionId2, Integer appServerId2, Integer roleId2, Integer platform2) {
        this.locale = LocaleHelper.getDefaultLocale();
        this.appServerId = 0;
        this.roleId = 0;
        this.rebateId = 0L;
        this.convertGooglePlayPrices = false;
        this.denominations = denominations2;
        this.locale = locale2;
        this.conversionRatio = conversionRatio2;
        this.token = token2;
        this.virtualCurrencyName = virtualCurrencyName2;
        this.appId = appId2;
        this.buyerId = buyerId2;
        this.description = description2;
        this.transactionId = transactionId2;
        this.appServerId = appServerId2;
        this.roleId = roleId2;
        this.platform = platform2;
    }

    private GGPayment(List<Denomination> denominations2, Float conversionRatio2, String token2, String virtualCurrencyName2, String appId2, String buyerId2, String description2, String transactionId2, Integer serverId, Integer roleId2, Integer platform2) {
        this.locale = LocaleHelper.getDefaultLocale();
        this.appServerId = 0;
        this.roleId = 0;
        this.rebateId = 0L;
        this.convertGooglePlayPrices = false;
        this.denominations = denominations2;
        this.conversionRatio = conversionRatio2;
        this.token = token2;
        this.virtualCurrencyName = virtualCurrencyName2;
        this.appId = appId2;
        this.buyerId = buyerId2;
        this.description = description2;
        this.transactionId = transactionId2;
        this.appServerId = serverId;
        this.roleId = roleId2;
        this.platform = platform2;
    }

    public Integer getAppServerId() {
        return this.appServerId;
    }

    public void setAppServerId(Integer appServerId2) {
        this.appServerId = appServerId2;
    }

    public Integer getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Integer roleId2) {
        this.roleId = roleId2;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(String transactionId2) {
        this.transactionId = transactionId2;
    }

    public List<Denomination> getDenominations() {
        return this.denominations;
    }

    public void setDenominations(List<Denomination> denominations2) {
        this.denominations = denominations2;
    }

    public Float getConversionRatio() {
        return this.conversionRatio;
    }

    public String getToken() {
        return this.token;
    }

    public String getVirtualCurrencyName() {
        return this.virtualCurrencyName;
    }

    public String getAppId() {
        return this.appId;
    }

    public String getBuyerId() {
        return this.buyerId;
    }

    public String getDescription() {
        return this.description;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public void setLocale(Locale locale2) {
        this.locale = locale2;
    }

    public Integer getPlatform() {
        return this.platform;
    }

    public static class GGPaymentBuilder {
        private String appId;
        private String buyerId;
        private Float conversionRatio;
        private boolean convertGooglePlayPrices = false;
        private List<Denomination> denominations = new ArrayList();
        private String description;
        private Locale locale = LocaleHelper.getDefaultLocale();
        private Integer platform = 2;
        private Long rebateId = 0L;
        private Integer roleId = 0;
        private Integer serverId = 0;
        private String token;
        private String transactionId;
        private String virtualCurrencyName;

        public GGPaymentBuilder setServerId(Integer serverId2) {
            this.serverId = serverId2;
            return this;
        }

        public GGPaymentBuilder setRoleId(Integer roleId2) {
            this.roleId = roleId2;
            return this;
        }

        public GGPaymentBuilder setDenominations(List<Denomination> denominations2) {
            this.denominations = denominations2;
            return this;
        }

        public GGPaymentBuilder addDenomination(Denomination denomination) {
            this.denominations.add(denomination);
            return this;
        }

        public GGPaymentBuilder setConversionRatio(Float conversionRatio2) {
            this.conversionRatio = conversionRatio2;
            return this;
        }

        public GGPaymentBuilder setVirtualCurrencyName(String virtualCurrencyName2) {
            this.virtualCurrencyName = virtualCurrencyName2;
            return this;
        }

        public GGPaymentBuilder setAppId(String appId2) {
            this.appId = appId2;
            return this;
        }

        public GGPaymentBuilder setBuyerId(String buyerId2) {
            this.buyerId = buyerId2;
            return this;
        }

        public GGPaymentBuilder setDescription(String description2) {
            this.description = description2;
            return this;
        }

        public void setRebateId(Long rebateId2) {
            this.rebateId = rebateId2;
        }

        public GGPayment build() {
            GGPayment payment;
            if (this.locale == null) {
                payment = new GGPayment((List) this.denominations, this.conversionRatio, this.token, this.virtualCurrencyName, this.appId, this.buyerId, this.description, this.transactionId, this.serverId, this.roleId, this.platform);
            } else {
                payment = new GGPayment(this.denominations, this.locale, this.conversionRatio, this.token, this.virtualCurrencyName, this.appId, this.buyerId, this.description, this.transactionId, this.serverId, this.roleId, this.platform);
            }
            payment.setRebateId(this.rebateId);
            payment.setConvertGooglePlayPrices(this.convertGooglePlayPrices);
            return payment;
        }

        public GGPaymentBuilder setToken(String token2) {
            this.token = token2;
            return this;
        }

        public GGPaymentBuilder setTransactionId(String transactionId2) {
            this.transactionId = transactionId2;
            return this;
        }

        public GGPaymentBuilder setLocale(Locale locale2) {
            this.locale = locale2;
            return this;
        }

        public GGPaymentBuilder setPlatform(Integer platform2) {
            this.platform = platform2;
            return this;
        }

        public GGPaymentBuilder setConvertGooglePlayPrices(boolean convertGooglePlayPrices2) {
            this.convertGooglePlayPrices = convertGooglePlayPrices2;
            return this;
        }
    }

    public static class Denomination implements Serializable {
        private static final long serialVersionUID = 1;
        private Integer appPoints;
        private String iconUrl;
        private boolean isPromo;
        private String itemId;
        private String mCurrencyIconUrl = null;
        private String name;
        private String price;
        private Integer promoPoints = 0;
        private long rebateId = 0;
        private Subscription subscription = null;

        public Denomination(String description, String itemId2, Integer appPoints2, String iconUrl2, String price2, boolean promo, Integer promoPoints2) {
            this.name = description;
            this.itemId = itemId2;
            this.appPoints = appPoints2;
            this.iconUrl = iconUrl2;
            this.price = price2;
            this.isPromo = promo;
            this.promoPoints = promoPoints2;
        }

        public String getPrice() {
            return this.price;
        }

        public void setPrice(String price2) {
            this.price = price2;
        }

        public String getItemId() {
            return this.itemId;
        }

        public void setItemId(String itemId2) {
            this.itemId = itemId2;
        }

        public Integer getAppPoints() {
            return this.appPoints;
        }

        public String getName() {
            return this.name;
        }

        public String getIconUrl() {
            return this.iconUrl;
        }

        public boolean isInPromotion() {
            return this.isPromo;
        }

        public String getCurrencyIconUrl() {
            return this.mCurrencyIconUrl;
        }

        public void setCurrencyIconUrl(String currencyIconUrl) {
            this.mCurrencyIconUrl = currencyIconUrl;
        }

        public long getRebateId() {
            return this.rebateId;
        }

        public void setRebateId(long rebateId2) {
            this.rebateId = rebateId2;
        }

        public Integer getPromoPoints() {
            return this.promoPoints;
        }

        public Subscription getSubscription() {
            return this.subscription;
        }

        public void setSubscription(Subscription subscription2) {
            this.subscription = subscription2;
        }

        public boolean isSubscription() {
            return this.subscription != null;
        }

        public class Subscription implements Serializable {
            private int lastPaymentTime;
            private int period;
            private int status;

            public Subscription(int period2, int status2, int lastPaymentTime2) {
                this.period = period2;
                this.status = status2;
                this.lastPaymentTime = lastPaymentTime2;
            }

            public int getPeriod() {
                return this.period;
            }

            public int getStatus() {
                return this.status;
            }

            public int getLastPaymentTime() {
                return this.lastPaymentTime;
            }
        }
    }

    public static class PaymentChannel implements Serializable {
        private int category;
        private String channelId;
        private String description;
        private float discount;
        private int flag;
        private String iconUrl;
        private List<Denomination> items;
        private String name;

        public List<Denomination> getItems() {
            return this.items;
        }

        public boolean isDirectPay() {
            if (this.items == null || this.items.size() == 0) {
                return true;
            }
            return false;
        }

        public Denomination getDenomination(String itemId) {
            if (this.items != null) {
                for (Denomination denomination : this.items) {
                    if (denomination.getItemId().equalsIgnoreCase(itemId)) {
                        return denomination;
                    }
                }
            }
            return null;
        }

        public void setItems(List<Denomination> items2) {
            this.items = items2;
        }

        public String getChannelId() {
            return this.channelId;
        }

        public void setChannelId(String channelId2) {
            this.channelId = channelId2;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name2) {
            this.name = name2;
        }

        public String getIconUrl() {
            return this.iconUrl;
        }

        public void setIconUrl(String iconUrl2) {
            this.iconUrl = iconUrl2;
        }

        public float getDiscount() {
            return this.discount;
        }

        public void setDiscount(float discount2) {
            this.discount = discount2;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description2) {
            this.description = description2;
        }

        public int getCategory() {
            return this.category;
        }

        public void setCategory(int category2) {
            this.category = category2;
        }

        public int getFlag() {
            return this.flag;
        }

        public void setFlag(int flag2) {
            this.flag = flag2;
        }

        public boolean isGooglePlay() {
            return String.valueOf(SDKConstants.PaymentProvider.GOOGLE_PROVIDER_ID).equalsIgnoreCase(this.channelId);
        }
    }
}

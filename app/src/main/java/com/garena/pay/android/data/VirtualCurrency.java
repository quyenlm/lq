package com.garena.pay.android.data;

import java.io.Serializable;

public class VirtualCurrency implements Serializable {
    private static final long serialVersionUID = 1;
    private Float conversionRatio;
    private String currencyCode;
    private Double currencyRate;
    private String currencySymbolFormat;
    private String virtualCurrencyName;

    public Double getCurrencyRate() {
        return this.currencyRate;
    }

    public void setCurrencyRate(Double currencyRate2) {
        this.currencyRate = currencyRate2;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode2) {
        this.currencyCode = currencyCode2;
    }

    public String getCurrencySymbolFormat() {
        return this.currencySymbolFormat;
    }

    public void setCurrencySymbolFormat(String currencySymbolFormat2) {
        this.currencySymbolFormat = currencySymbolFormat2;
    }

    public String getVirtualCurrencyName() {
        return this.virtualCurrencyName;
    }

    public void setVirtualCurrencyName(String virtualCurrencyName2) {
        this.virtualCurrencyName = virtualCurrencyName2;
    }

    public Float getConversionRatio() {
        return this.conversionRatio;
    }

    public void setConversionRatio(Float conversionRatio2) {
        this.conversionRatio = conversionRatio2;
    }
}

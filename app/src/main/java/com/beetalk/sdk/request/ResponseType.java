package com.beetalk.sdk.request;

public enum ResponseType {
    CODE("code"),
    TOKEN("token");
    
    String mValue;

    private ResponseType(String value) {
        this.mValue = value;
    }

    public String getStringValue() {
        return this.mValue;
    }
}

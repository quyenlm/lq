package com.beetalk.sdk.data;

public enum TokenProvider {
    GARENA_NATIVE_ANDROID(3),
    GARENA_WEB_ANDROID(4),
    GARENA_OTHERS(5),
    FACEBOOK(6),
    OTHERS(7),
    GUEST(8),
    VK(9),
    LINE(10),
    GOOGLE(11);
    
    private int value;

    private TokenProvider(int value2) {
        this.value = value2;
    }

    public static TokenProvider valueOf(int value2) {
        for (TokenProvider TokenProvider : values()) {
            if (TokenProvider.getValue() == value2) {
                return TokenProvider;
            }
        }
        return OTHERS;
    }

    public int getValue() {
        return this.value;
    }
}

package com.facebook.login;

public enum LoginBehavior {
    NATIVE_WITH_FALLBACK(true, true, true, false, true, true),
    NATIVE_ONLY(true, true, false, false, false, true),
    KATANA_ONLY(false, true, false, false, false, false),
    WEB_ONLY(false, false, true, false, true, false),
    WEB_VIEW_ONLY(false, false, true, false, false, false),
    DEVICE_AUTH(false, false, false, true, false, false);
    
    private final boolean allowsCustomTabAuth;
    private final boolean allowsDeviceAuth;
    private final boolean allowsFacebookLiteAuth;
    private final boolean allowsGetTokenAuth;
    private final boolean allowsKatanaAuth;
    private final boolean allowsWebViewAuth;

    private LoginBehavior(boolean allowsGetTokenAuth2, boolean allowsKatanaAuth2, boolean allowsWebViewAuth2, boolean allowsDeviceAuth2, boolean allowsCustomTabAuth2, boolean allowsFacebookLiteAuth2) {
        this.allowsGetTokenAuth = allowsGetTokenAuth2;
        this.allowsKatanaAuth = allowsKatanaAuth2;
        this.allowsWebViewAuth = allowsWebViewAuth2;
        this.allowsDeviceAuth = allowsDeviceAuth2;
        this.allowsCustomTabAuth = allowsCustomTabAuth2;
        this.allowsFacebookLiteAuth = allowsFacebookLiteAuth2;
    }

    /* access modifiers changed from: package-private */
    public boolean allowsGetTokenAuth() {
        return this.allowsGetTokenAuth;
    }

    /* access modifiers changed from: package-private */
    public boolean allowsKatanaAuth() {
        return this.allowsKatanaAuth;
    }

    /* access modifiers changed from: package-private */
    public boolean allowsWebViewAuth() {
        return this.allowsWebViewAuth;
    }

    /* access modifiers changed from: package-private */
    public boolean allowsDeviceAuth() {
        return this.allowsDeviceAuth;
    }

    /* access modifiers changed from: package-private */
    public boolean allowsCustomTabAuth() {
        return this.allowsCustomTabAuth;
    }

    /* access modifiers changed from: package-private */
    public boolean allowsFacebookLiteAuth() {
        return this.allowsFacebookLiteAuth;
    }
}

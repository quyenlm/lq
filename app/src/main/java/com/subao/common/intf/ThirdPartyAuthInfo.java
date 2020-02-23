package com.subao.common.intf;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ThirdPartyAuthInfo {
    @NonNull
    private final String accessToken;
    private final long expiresIn;
    @Nullable
    private final String openId;
    @Nullable
    private final String refreshToken;

    public ThirdPartyAuthInfo(@NonNull String str, @Nullable String str2, @Nullable String str3, long j) {
        this.accessToken = str;
        this.refreshToken = str2;
        this.openId = str3;
        this.expiresIn = j;
    }

    @NonNull
    public String getAccessToken() {
        return this.accessToken;
    }

    @Nullable
    public String getRefreshToken() {
        return this.refreshToken;
    }

    @Nullable
    public String getOpenId() {
        return this.openId;
    }

    public long getExpiresIn() {
        return this.expiresIn;
    }
}

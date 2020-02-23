package com.beetalk.sdk.facebook;

public interface FBRequestUserInfoCallback {
    void onError();

    void onSuccess(FBUserInfo fBUserInfo);
}

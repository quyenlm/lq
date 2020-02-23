package com.subao.common.intf;

import android.support.annotation.Nullable;

public interface QueryThirdPartyAuthInfoCallback {
    void onThirdPartyAuthInfoResult(int i, @Nullable ThirdPartyAuthInfo thirdPartyAuthInfo);
}

package com.tencent.imsdk.sns.api;

import android.content.Intent;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.sns.base.IMLaunchResult;

public interface IMLaunchHandler {
    void handleIntent(Intent intent, IMCallback<IMLaunchResult> iMCallback);
}

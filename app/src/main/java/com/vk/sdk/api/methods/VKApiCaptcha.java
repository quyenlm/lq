package com.vk.sdk.api.methods;

import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;

public class VKApiCaptcha extends VKApiBase {
    public VKRequest force() {
        return prepareRequest("force", (VKParameters) null);
    }

    /* access modifiers changed from: protected */
    public String getMethodsGroup() {
        return "captcha";
    }
}

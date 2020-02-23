package com.vk.sdk;

import com.vk.sdk.api.VKError;

public interface VKCallback<RESULT> {
    void onError(VKError vKError);

    void onResult(RESULT result);
}

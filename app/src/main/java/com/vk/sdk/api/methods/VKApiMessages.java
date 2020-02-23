package com.vk.sdk.api.methods;

import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKParser;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.model.VKApiGetDialogResponse;
import com.vk.sdk.api.model.VKApiGetMessagesResponse;
import org.json.JSONObject;

public class VKApiMessages extends VKApiBase {
    public VKRequest get() {
        return get(VKParameters.from(VKApiConst.COUNT, "10"));
    }

    public VKRequest get(VKParameters params) {
        return prepareRequest("get", params, (VKParser) new VKParser() {
            public Object createModel(JSONObject object) {
                return new VKApiGetMessagesResponse(object);
            }
        });
    }

    public VKRequest getDialogs() {
        return getDialogs(VKParameters.from(VKApiConst.COUNT, "5"));
    }

    public VKRequest getDialogs(VKParameters params) {
        return prepareRequest("getDialogs", params, (VKParser) new VKParser() {
            public Object createModel(JSONObject object) {
                return new VKApiGetDialogResponse(object);
            }
        });
    }

    /* access modifiers changed from: protected */
    public String getMethodsGroup() {
        return "messages";
    }
}

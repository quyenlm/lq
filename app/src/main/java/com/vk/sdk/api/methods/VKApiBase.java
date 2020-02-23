package com.vk.sdk.api.methods;

import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKParser;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.model.VKApiModel;
import java.util.Locale;

public abstract class VKApiBase {
    /* access modifiers changed from: protected */
    public abstract String getMethodsGroup();

    /* access modifiers changed from: protected */
    public VKRequest prepareRequest(String methodName, VKParameters methodParameters) {
        return new VKRequest(String.format(Locale.US, "%s.%s", new Object[]{getMethodsGroup(), methodName}), methodParameters, (Class<? extends VKApiModel>) null);
    }

    /* access modifiers changed from: protected */
    public VKRequest prepareRequest(String methodName, VKParameters methodParameters, Class<? extends VKApiModel> modelClass) {
        return new VKRequest(String.format(Locale.US, "%s.%s", new Object[]{getMethodsGroup(), methodName}), methodParameters, modelClass);
    }

    /* access modifiers changed from: protected */
    public VKRequest prepareRequest(String methodName, VKParameters methodParameters, VKParser responseParser) {
        VKRequest result = new VKRequest(String.format(Locale.US, "%s.%s", new Object[]{getMethodsGroup(), methodName}), methodParameters);
        result.setResponseParser(responseParser);
        return result;
    }
}

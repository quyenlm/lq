package com.vk.sdk.api;

import com.vk.sdk.api.model.VKApiModel;
import org.json.JSONObject;

public class VKDefaultParser extends VKParser {
    private final Class<? extends VKApiModel> mModelClass;

    public VKDefaultParser(Class<? extends VKApiModel> objectModel) {
        this.mModelClass = objectModel;
    }

    public Object createModel(JSONObject object) {
        try {
            VKApiModel model = (VKApiModel) this.mModelClass.newInstance();
            model.parse(object);
            return model;
        } catch (Exception e) {
            return null;
        }
    }
}

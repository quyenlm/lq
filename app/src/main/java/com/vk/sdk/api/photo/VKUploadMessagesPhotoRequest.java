package com.vk.sdk.api.photo;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.util.VKJsonHelper;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

public class VKUploadMessagesPhotoRequest extends VKUploadPhotoBase {
    private static final long serialVersionUID = 1;

    public VKUploadMessagesPhotoRequest(File image) {
        this.mImages = new File[]{image};
    }

    public VKUploadMessagesPhotoRequest(VKUploadImage image) {
        this.mImages = new File[]{image.getTmpFile()};
    }

    /* access modifiers changed from: protected */
    public VKRequest getServerRequest() {
        return VKApi.photos().getMessagesUploadServer();
    }

    /* access modifiers changed from: protected */
    public VKRequest getSaveRequest(JSONObject response) {
        try {
            return VKApi.photos().saveMessagesPhoto(new VKParameters(VKJsonHelper.toMap(response)));
        } catch (JSONException e) {
            return null;
        }
    }
}

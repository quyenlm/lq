package com.vk.sdk.api.photo;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.util.VKJsonHelper;
import com.vk.sdk.util.VKUtil;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

public class VKUploadWallPhotoRequest extends VKUploadPhotoBase {
    private static final long serialVersionUID = 4732771149932923938L;

    public VKUploadWallPhotoRequest(File image, long userId, int groupId) {
        this.mUserId = userId;
        this.mGroupId = (long) groupId;
        this.mImages = new File[]{image};
    }

    public VKUploadWallPhotoRequest(VKUploadImage image, long userId, int groupId) {
        this.mUserId = userId;
        this.mGroupId = (long) groupId;
        this.mImages = new File[]{image.getTmpFile()};
    }

    public VKUploadWallPhotoRequest(VKUploadImage[] images, long userId, int groupId) {
        this.mUserId = userId;
        this.mGroupId = (long) groupId;
        this.mImages = new File[images.length];
        for (int i = 0; i < images.length; i++) {
            this.mImages[i] = images[i].getTmpFile();
        }
    }

    /* access modifiers changed from: protected */
    public VKRequest getServerRequest() {
        if (this.mGroupId != 0) {
            return VKApi.photos().getWallUploadServer(this.mGroupId);
        }
        return VKApi.photos().getWallUploadServer();
    }

    /* access modifiers changed from: protected */
    public VKRequest getSaveRequest(JSONObject response) {
        try {
            VKRequest saveRequest = VKApi.photos().saveWallPhoto(new VKParameters(VKJsonHelper.toMap(response)));
            if (this.mUserId != 0) {
                saveRequest.addExtraParameters(VKUtil.paramsFrom("user_id", Long.valueOf(this.mUserId)));
            }
            if (this.mGroupId == 0) {
                return saveRequest;
            }
            saveRequest.addExtraParameters(VKUtil.paramsFrom("group_id", Long.valueOf(this.mGroupId)));
            return saveRequest;
        } catch (JSONException e) {
            return null;
        }
    }
}

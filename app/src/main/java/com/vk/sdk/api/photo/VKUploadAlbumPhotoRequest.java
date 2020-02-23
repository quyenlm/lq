package com.vk.sdk.api.photo;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.util.VKJsonHelper;
import com.vk.sdk.util.VKUtil;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

public class VKUploadAlbumPhotoRequest extends VKUploadPhotoBase {
    private static final long serialVersionUID = 5439648671595840976L;

    public VKUploadAlbumPhotoRequest(File image, long albumId, long groupId) {
        this.mAlbumId = albumId;
        this.mGroupId = groupId;
        this.mImages = new File[]{image};
    }

    public VKUploadAlbumPhotoRequest(VKUploadImage image, long albumId, long groupId) {
        this.mAlbumId = albumId;
        this.mGroupId = groupId;
        this.mImages = new File[]{image.getTmpFile()};
    }

    /* access modifiers changed from: protected */
    public VKRequest getServerRequest() {
        if (this.mAlbumId == 0 || this.mGroupId == 0) {
            return VKApi.photos().getUploadServer(this.mAlbumId);
        }
        return VKApi.photos().getUploadServer(this.mAlbumId, this.mGroupId);
    }

    /* access modifiers changed from: protected */
    public VKRequest getSaveRequest(JSONObject response) {
        try {
            VKRequest saveRequest = VKApi.photos().save(new VKParameters(VKJsonHelper.toMap(response)));
            if (this.mAlbumId != 0) {
                saveRequest.addExtraParameters(VKUtil.paramsFrom(VKApiConst.ALBUM_ID, Long.valueOf(this.mAlbumId)));
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

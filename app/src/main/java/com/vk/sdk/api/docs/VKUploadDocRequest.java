package com.vk.sdk.api.docs;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.util.VKJsonHelper;
import com.vk.sdk.util.VKUtil;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

public class VKUploadDocRequest extends VKUploadDocBase {
    public VKUploadDocRequest(File doc) {
        this.mDoc = doc;
        this.mGroupId = 0;
    }

    public VKUploadDocRequest(File doc, long groupId) {
        this.mDoc = doc;
        this.mGroupId = groupId;
    }

    /* access modifiers changed from: protected */
    public VKRequest getServerRequest() {
        if (this.mGroupId != 0) {
            return VKApi.docs().getUploadServer(this.mGroupId);
        }
        return VKApi.docs().getUploadServer();
    }

    /* access modifiers changed from: protected */
    public VKRequest getSaveRequest(JSONObject response) {
        try {
            VKRequest saveRequest = VKApi.docs().save(new VKParameters(VKJsonHelper.toMap(response)));
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

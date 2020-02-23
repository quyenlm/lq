package com.facebook.share.internal;

import android.content.Context;
import android.os.Bundle;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.PlatformServiceClient;

@Deprecated
final class LikeStatusClient extends PlatformServiceClient {
    private String objectId;

    LikeStatusClient(Context context, String applicationId, String objectId2) {
        super(context, NativeProtocol.MESSAGE_GET_LIKE_STATUS_REQUEST, NativeProtocol.MESSAGE_GET_LIKE_STATUS_REPLY, NativeProtocol.PROTOCOL_VERSION_20141001, applicationId);
        this.objectId = objectId2;
    }

    /* access modifiers changed from: protected */
    public void populateRequestBundle(Bundle data) {
        data.putString(ShareConstants.EXTRA_OBJECT_ID, this.objectId);
    }
}

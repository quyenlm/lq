package com.facebook.login;

import android.content.Context;
import android.os.Bundle;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.PlatformServiceClient;

final class LoginStatusClient extends PlatformServiceClient {
    static final long DEFAULT_TOAST_DURATION_MS = 5000;
    private final String graphApiVersion;
    private final String loggerRef;
    private final long toastDurationMs;

    LoginStatusClient(Context context, String applicationId, String loggerRef2, String graphApiVersion2, long toastDurationMs2) {
        super(context, NativeProtocol.MESSAGE_GET_LOGIN_STATUS_REQUEST, NativeProtocol.MESSAGE_GET_LOGIN_STATUS_REPLY, NativeProtocol.PROTOCOL_VERSION_20170411, applicationId);
        this.loggerRef = loggerRef2;
        this.graphApiVersion = graphApiVersion2;
        this.toastDurationMs = toastDurationMs2;
    }

    /* access modifiers changed from: protected */
    public void populateRequestBundle(Bundle data) {
        data.putString(NativeProtocol.EXTRA_LOGGER_REF, this.loggerRef);
        data.putString(NativeProtocol.EXTRA_GRAPH_API_VERSION, this.graphApiVersion);
        data.putLong(NativeProtocol.EXTRA_TOAST_DURATION_MS, this.toastDurationMs);
    }
}

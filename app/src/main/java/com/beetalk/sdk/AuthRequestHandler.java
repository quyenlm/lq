package com.beetalk.sdk;

import android.content.Intent;
import com.beetalk.sdk.AuthClient;
import java.io.Serializable;

public abstract class AuthRequestHandler implements Serializable {
    private static final long serialVersionUID = 1;
    protected AuthClient client;

    /* access modifiers changed from: package-private */
    public abstract boolean startAuth(AuthClient.AuthClientRequest authClientRequest);

    protected AuthRequestHandler(AuthClient client2) {
        this.client = client2;
    }

    /* access modifiers changed from: package-private */
    public boolean onActivityResult(int requestCode, int resultCode, Intent data, AuthClient.AuthClientRequest pendingRequest) {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void cancel() {
    }
}

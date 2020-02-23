package com.facebook.internal;

import android.content.Intent;
import java.util.UUID;

public class AppCall {
    private static AppCall currentPendingCall;
    private UUID callId;
    private int requestCode;
    private Intent requestIntent;

    public static AppCall getCurrentPendingCall() {
        return currentPendingCall;
    }

    public static synchronized AppCall finishPendingCall(UUID callId2, int requestCode2) {
        AppCall pendingCall;
        synchronized (AppCall.class) {
            pendingCall = getCurrentPendingCall();
            if (pendingCall == null || !pendingCall.getCallId().equals(callId2) || pendingCall.getRequestCode() != requestCode2) {
                pendingCall = null;
            } else {
                setCurrentPendingCall((AppCall) null);
            }
        }
        return pendingCall;
    }

    private static synchronized boolean setCurrentPendingCall(AppCall appCall) {
        boolean z;
        synchronized (AppCall.class) {
            AppCall oldAppCall = getCurrentPendingCall();
            currentPendingCall = appCall;
            z = oldAppCall != null;
        }
        return z;
    }

    public AppCall(int requestCode2) {
        this(requestCode2, UUID.randomUUID());
    }

    public AppCall(int requestCode2, UUID callId2) {
        this.callId = callId2;
        this.requestCode = requestCode2;
    }

    public Intent getRequestIntent() {
        return this.requestIntent;
    }

    public UUID getCallId() {
        return this.callId;
    }

    public int getRequestCode() {
        return this.requestCode;
    }

    public void setRequestCode(int requestCode2) {
        this.requestCode = requestCode2;
    }

    public void setRequestIntent(Intent requestIntent2) {
        this.requestIntent = requestIntent2;
    }

    public boolean setPending() {
        return setCurrentPendingCall(this);
    }
}

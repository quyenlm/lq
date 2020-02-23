package com.garena.android;

public interface PushNotificationStateListener {
    void onRegistered(String str);

    void onRegisteredWithPlatform(String str);

    void onUnRegisteredWithPlatform();

    void onUnregistered();
}

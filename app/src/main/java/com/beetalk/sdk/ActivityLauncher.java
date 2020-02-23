package com.beetalk.sdk;

import android.app.Activity;
import android.content.Intent;

public interface ActivityLauncher {
    Activity getContext();

    void startActivityForResult(Intent intent, int i);
}

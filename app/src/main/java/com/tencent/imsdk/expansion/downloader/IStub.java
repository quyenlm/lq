package com.tencent.imsdk.expansion.downloader;

import android.content.Context;
import android.os.Messenger;

public interface IStub {
    void connect(Context context);

    void disconnect(Context context);

    Messenger getMessenger();
}

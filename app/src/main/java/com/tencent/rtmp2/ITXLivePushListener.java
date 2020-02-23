package com.tencent.rtmp2;

import android.os.Bundle;

public interface ITXLivePushListener {
    void onNetStatus(Bundle bundle);

    void onPushEvent(int i, Bundle bundle);
}

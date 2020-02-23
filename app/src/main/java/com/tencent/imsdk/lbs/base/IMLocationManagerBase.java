package com.tencent.imsdk.lbs.base;

import android.content.Context;
import com.tencent.imsdk.IMCallback;

public abstract class IMLocationManagerBase {
    public abstract void enableGPS(boolean z);

    public abstract boolean initialize(Context context);

    public abstract boolean requestLocationInfo(boolean z);

    public abstract void setLanguageTag(int i);

    public abstract void setLocationListener(IMCallback iMCallback);
}

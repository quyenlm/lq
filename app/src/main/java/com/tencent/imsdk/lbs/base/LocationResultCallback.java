package com.tencent.imsdk.lbs.base;

import android.location.Location;

public abstract class LocationResultCallback {
    public abstract void getLocation(Location location, String str);
}

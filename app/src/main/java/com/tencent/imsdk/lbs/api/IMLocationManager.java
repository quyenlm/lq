package com.tencent.imsdk.lbs.api;

import android.content.Context;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMModules;
import com.tencent.imsdk.lbs.base.IMLocationManagerBase;
import com.tencent.imsdk.tool.etc.IMLogger;

public class IMLocationManager extends IMLocationManagerBase {
    private static final String DEFAULT_CLASSNAME = "IMLocationManagerConcrete";
    private static final String LOCATION_MANAGER_PACKAGE_FORMAT = "com.tencent.imsdk.lbs.%s";
    private IMLocationManagerBase imLocationManagerBase;
    private String packageName = null;

    public boolean initialize(Context mContext) {
        if (this.packageName == null || this.packageName.length() <= 0) {
            IMLogger.w("You hadn't setChannel() yet, so default classname will be used");
            setChannel(DEFAULT_CLASSNAME);
        }
        this.imLocationManagerBase = (IMLocationManagerBase) IMModules.getInstance().getModule(this.packageName);
        if (this.imLocationManagerBase != null) {
            return this.imLocationManagerBase.initialize(mContext);
        }
        IMLogger.e("IMLocationManager initialize fail, check your class name again! packageName = " + this.packageName);
        return false;
    }

    public boolean requestLocationInfo(boolean immediate) {
        if (this.imLocationManagerBase != null) {
            return this.imLocationManagerBase.requestLocationInfo(immediate);
        }
        return false;
    }

    public void setChannel(String className) {
        this.packageName = String.format(LOCATION_MANAGER_PACKAGE_FORMAT, new Object[]{className});
    }

    public void setLocationListener(IMCallback imCallback) {
        if (this.imLocationManagerBase != null) {
            this.imLocationManagerBase.setLocationListener(imCallback);
        }
    }

    public void setLanguageTag(int languageTag) {
        if (this.imLocationManagerBase != null) {
            this.imLocationManagerBase.setLanguageTag(languageTag);
        }
    }

    public void enableGPS(boolean enableGPS) {
        if (this.imLocationManagerBase != null) {
            this.imLocationManagerBase.enableGPS(enableGPS);
        }
    }
}

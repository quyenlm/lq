package com.tencent.imsdk.ads.api;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.IMModules;
import com.tencent.imsdk.ads.base.IMAdsBase;
import com.tencent.imsdk.tool.etc.IMLogger;

public class IMAds {
    private static String currentChannel = "";
    private static Context currentContext = null;
    private static IMAdsBase nInstance = null;

    private static boolean initialize() {
        if (currentContext == null) {
            return false;
        }
        IMConfig.initialize(currentContext);
        return true;
    }

    public static void initialize(Context context) {
        try {
            currentContext = context;
            IMConfig.initialize(currentContext);
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static String getChanel() {
        return currentChannel;
    }

    public static boolean setChannel(String channel) {
        if (TextUtils.isEmpty(channel)) {
            IMLogger.e("channel is null,please check channel value");
            return false;
        } else if (currentContext == null) {
            IMLogger.e("initialize should be called before set channel !");
            return false;
        } else {
            currentChannel = channel;
            nInstance = getInstance(currentChannel);
            if (nInstance != null) {
                nInstance.init(currentContext);
                return true;
            }
            IMLogger.e("get channel  " + currentChannel + " instance failed !");
            return false;
        }
    }

    private static IMAdsBase getInstance(String channel) {
        currentChannel = channel;
        IMLogger.d("switch channel to : " + channel);
        String platformClass = "com.tencent.imsdk.ads." + currentChannel + "." + currentChannel.substring(0, 1).toUpperCase() + currentChannel.substring(1) + "NoticeHelper";
        IMLogger.d("try to get class : " + platformClass);
        IMAdsBase instance = (IMAdsBase) IMModules.getInstance().getModule(platformClass);
        if (instance != null) {
            instance.initialize(currentContext);
        } else {
            IMLogger.e("get class : " + platformClass + " failed !");
        }
        return instance;
    }

    public static void setListener(IMAdsListener listener) {
        try {
            if (nInstance != null) {
                nInstance.commonSetListener(listener);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void commonSetLocation(double latitude, double longitude, String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.commonSetLocation(latitude, longitude, extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void commonSetAge(int age, String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.commonSetAge(age, extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void commonSetGender(int gender, String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.commonSetGender(gender, extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void commonSetBirthday(String birthday, String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.commonSetBirthday(birthday, extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void commonSetAdUnitId(String unitId, String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.commonSetAdUnitId(unitId, extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void commonTagForChildDirectedTreatment(boolean tagForChild, String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.commonTagForChildDirectedTreatment(tagForChild, extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void commonSetRequestAgent(String agent, String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.commonSetRequestAgent(agent, extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void commonLoadEndingAd(String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.commonLoadEndingAd(extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void commonShowEndingAd(boolean hideEndingAdNormalPopup, String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.commonShowEndingAd(hideEndingAdNormalPopup, extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void adBannerCreate(int width, int height, int gravity, String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.adBannerCreate(width, height, gravity, extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void adBannerShow(String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.adBannerShow(extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void adBannerPause(String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.adBannerPause(extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void adBannerStop(String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.adBannerStop(extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void adBannerSetVisibility(boolean visible, String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.adBannerSetVisibility(visible, extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void adInterestCreate(String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.adInterestCreate(extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static boolean adInterestIsLoaded(String extraJson) {
        try {
            if (nInstance != null) {
                return nInstance.adInterestIsLoaded(extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
        return false;
    }

    public static void adInterestShow(String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.adInterestShow(extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void adPopupCreate(String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.adPopupCreate(extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void adPopupShow(String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.adPopupShow(extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void adNativeCreate(String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.adPopupShow(extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void adNativeShow(String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.adNativeShow(extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void adNativeClose(String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.adNativeClose(extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static String getNativeAdContents(String extraJson) {
        try {
            if (nInstance != null) {
                return nInstance.getNativeAdContents(extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
        return "";
    }

    public static void impressionAction(String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.impressionAction(extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static void clickAction(String extraJson) {
        try {
            if (nInstance != null) {
                nInstance.clickAction(extraJson);
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }
}

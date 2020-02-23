package com.tencent.imsdk.ads.base;

import android.content.Context;
import com.tencent.imsdk.ads.api.IMAdsListener;
import com.tencent.imsdk.tool.etc.IMLogger;

public abstract class IMAdsBase {
    public Context context;

    public abstract void commonSetListener(IMAdsListener iMAdsListener);

    public abstract void init(Context context2);

    public void commonSetLocation(double latitude, double longitude, String extraJson) {
        IMLogger.d("not support commonSetLocation");
    }

    public void commonSetAge(int age, String extraJson) {
        IMLogger.d("not support commonSetAge");
    }

    public void commonSetGender(int gender, String extraJson) {
        IMLogger.d("not support commonSetGender");
    }

    public void commonSetBirthday(String birthday, String extraJson) {
        IMLogger.d("not support commonSetBirthday");
    }

    public void commonSetAdUnitId(String unitId, String extraJson) {
        IMLogger.d("not support CommonSetAdUnitId");
    }

    public void commonTagForChildDirectedTreatment(boolean tagForChild, String extraJson) {
        IMLogger.d("not support commonTagForChildDirectedTreatment");
    }

    public void commonSetRequestAgent(String agent, String extraJson) {
        IMLogger.d("not support commonSetRequestAgent");
    }

    public void commonLoadEndingAd(String extraJson) {
        IMLogger.d("not support commonLoadEndingAd");
    }

    public void commonShowEndingAd(boolean hideEndingAdNormalPopup, String extraJson) {
        IMLogger.d("not support commonShowEndingAd");
    }

    public void adBannerCreate(int width, int height, int gravity, String extraJson) {
        IMLogger.d("not support adBannerCreate");
    }

    public void adBannerShow(String extraJson) {
        IMLogger.d("not support adBannerShow");
    }

    public void adBannerPause(String extraJson) {
        IMLogger.d("not support adBannerPause");
    }

    public void adBannerStop(String extraJson) {
        IMLogger.d("not support adBannerStop");
    }

    public void adBannerSetVisibility(boolean visible, String extraJson) {
        IMLogger.d("not support adBannerSetVisibility");
    }

    public void adInterestCreate(String extraJson) {
        IMLogger.d("not support adBannerStop");
    }

    public boolean adInterestIsLoaded(String extraJson) {
        return false;
    }

    public void adInterestShow(String extraJson) {
        IMLogger.d("not support adInterstShow");
    }

    public void adPopupCreate(String extraJson) {
        IMLogger.d("not support adPopupCreate");
    }

    public void adPopupShow(String extraJson) {
        IMLogger.d("not support adPopupShow");
    }

    public void adNativeCreate(String extraJson) {
        IMLogger.d("not support AdNativeCreate");
    }

    public void adNativeShow(String extraJson) {
        IMLogger.d("not support AdNativeShow");
    }

    public void adNativeClose(String extraJson) {
        IMLogger.d("not support AdNativeClose");
    }

    public String getNativeAdContents(String extraJson) {
        return "";
    }

    public void impressionAction(String extraJson) {
        IMLogger.d("not support impressionAction");
    }

    public void clickAction(String extraJson) {
        IMLogger.d("not support clickAction");
    }

    protected IMAdsBase() {
    }

    public void initialize(Context ctx) {
        this.context = ctx;
    }

    public String toString() {
        return "IMAdsBase{context=" + this.context + '}';
    }
}

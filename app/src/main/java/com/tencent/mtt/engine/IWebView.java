package com.tencent.mtt.engine;

import android.graphics.Picture;

public interface IWebView {
    public static final int FLAG_SNAPSHOT_WITHOUT_EMB_TITLE_FOR_NATIVE_PAGE = 1;
    public static final int FLAG_SNAPSHOT_WITH_EMB_TITLE_FOR_WEB_PAGE = 2;
    public static final int FLAG_X5_FAST_SNAPSHOT = 4;

    public enum RatioRespect {
        RESPECT_NONE,
        RESPECT_WIDTH,
        RESPECT_HEIGHT,
        RESPECT_BOTH
    }

    void active();

    void back(boolean z);

    boolean canGoBack();

    boolean canGoForward();

    void configFontSize(int i);

    void deactive();

    void destroy();

    void forward();

    String getTitle();

    String getUrl();

    void loadUrl(String str);

    void postUrl(String str, byte[] bArr);

    void reload();

    void setWebViewClient(IWebViewClient iWebViewClient);

    Picture snapshotWholePage(int i, int i2, RatioRespect ratioRespect, int i3);

    void stopLoading();
}

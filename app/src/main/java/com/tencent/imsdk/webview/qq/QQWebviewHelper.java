package com.tencent.imsdk.webview.qq;

import android.content.Context;
import com.tencent.imsdk.tool.etc.CommonUtil;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.MetaDataUtil;
import com.tencent.imsdk.webview.api.IMWebViewActionListener;
import com.tencent.imsdk.webview.base.IMWebviewBase;

public class QQWebviewHelper extends IMWebviewBase {
    private static final String DEF_URL = "com.tencent.imsdk.webview.DefUrl";
    private Context mContext;

    public void init(Context con) {
        if (con != null) {
            this.mContext = con;
            WebViewManager.getInstance().init(con);
        }
    }

    public void openURL(String url, String ticket, boolean toolBar) {
        if (CommonUtil.ckIsEmpty(url)) {
            String defUrl = getDefUrlMetaData(this.mContext);
            if (!CommonUtil.ckIsEmpty(defUrl)) {
                url = defUrl;
            } else {
                IMLogger.w("url cannot be null");
                return;
            }
        }
        WebViewManager.getInstance().openUrl(url, ticket, toolBar);
    }

    public void openURL(String url, String ticket, boolean toolBar, boolean browser) {
        if (CommonUtil.ckIsEmpty(url)) {
            String defUrl = getDefUrlMetaData(this.mContext);
            if (!CommonUtil.ckIsEmpty(defUrl)) {
                url = defUrl;
            } else {
                IMLogger.w("url cannot be null");
                return;
            }
        }
        WebViewManager.getInstance().openUrl(url, ticket, toolBar, browser);
    }

    private boolean checkArgs(int count, Object... args) {
        if (args == null || args.length != count) {
            return false;
        }
        return true;
    }

    public void optCmd(int cmdType, Object... args) {
        IMLogger.d("cmdType = " + cmdType);
        switch (cmdType) {
            case 1:
                if (checkArgs(4, args)) {
                    WebViewManager.getInstance().openUrlWithX5(args[0], args[1], args[2].booleanValue(), args[3].booleanValue());
                    return;
                }
                return;
            default:
                try {
                    IMLogger.e("not support cmdtype = " + cmdType);
                    return;
                } catch (Exception e) {
                    IMLogger.w(e.getMessage());
                    return;
                }
        }
    }

    public void setZoom(float zoomHeight, float zoomWidth) {
        WebViewManager.getInstance().setZoom(zoomHeight, zoomWidth);
    }

    public void setOrientation(boolean isLandscape) {
        WebViewManager.getInstance().isUserSetOrientation = true;
        WebViewManager.getInstance().setOrientation(isLandscape);
    }

    public void close() {
        WebViewManager.getInstance().close();
    }

    public void back() {
        WebViewManager.getInstance().back();
    }

    public void forward() {
        WebViewManager.getInstance().forward();
    }

    public void reload() {
        WebViewManager.getInstance().reload();
    }

    public void setPosition(int x, int y, int w, int h) {
        WebViewManager.getInstance().setPosition(x, y, w, h);
    }

    public void setBackgroundColor(int a, int r, int g, int b) {
        WebViewManager.getInstance().setBackgroundColor(a, r, g, b);
    }

    public boolean canGoBack() {
        return WebViewManager.getInstance().canGoBack();
    }

    public boolean canGoForward() {
        return WebViewManager.getInstance().canGoForward();
    }

    public void callJs(String parasJson) {
        WebViewManager.getInstance().callJs(parasJson);
    }

    public boolean isActivated() {
        return WebViewManager.getInstance().isActivated();
    }

    public void setWebViewActionListener(IMWebViewActionListener listener) {
        WebViewManager.getInstance().setWebViewActionListener(listener);
    }

    private String getDefUrlMetaData(Context context) {
        String defUrl = "";
        if (context == null) {
            return defUrl;
        }
        try {
            defUrl = MetaDataUtil.readMetaDataFromApplication(context, DEF_URL);
            IMLogger.d("defUrl:" + defUrl);
            return defUrl;
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
            return defUrl;
        }
    }
}

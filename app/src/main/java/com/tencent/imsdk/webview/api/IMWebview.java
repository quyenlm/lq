package com.tencent.imsdk.webview.api;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.IMModules;
import com.tencent.imsdk.api.IMSDKApi;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.webview.base.IMWebviewBase;
import org.json.JSONObject;

public class IMWebview {
    private static String KEY_JSON_TICKET = "isneedticket";
    private static String KEY_JSON_X5 = "isx5work";
    protected static String currentChannel = "";
    protected static Context currentContext = null;
    protected static IMWebviewBase wInstance = null;

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
            IMSDKApi.onCreate((Activity) currentContext);
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
            IMLogger.e("channel is null, please check channel value");
            return false;
        } else if (currentContext == null) {
            IMLogger.e("initialize should be called before set channel !");
            return false;
        } else {
            currentChannel = channel;
            wInstance = getInstance(currentChannel);
            if (wInstance != null) {
                wInstance.init(currentContext);
                IMWebViewManager.getInstance().init();
                return true;
            }
            IMLogger.e("get channel  " + currentChannel + " instance failed !");
            return false;
        }
    }

    private static IMWebviewBase getInstance(String channel) {
        currentChannel = channel;
        IMLogger.d("switch channel to : " + channel);
        String platformClass = "com.tencent.imsdk.webview." + channel.toLowerCase() + "." + currentChannel + "WebviewHelper";
        IMLogger.d("try to get class : " + platformClass);
        IMWebviewBase instance = (IMWebviewBase) IMModules.getInstance().getModule(platformClass);
        if (instance != null) {
            instance.initialize(currentContext);
        } else {
            IMLogger.e("get class : " + platformClass + " failed !");
        }
        return instance;
    }

    public static void setZoom(float zoomHeight, float zoomWidth) {
        if (wInstance != null) {
            wInstance.setZoom(zoomHeight, zoomWidth);
        } else {
            IMLogger.e("initialize should be called before setZoom");
        }
    }

    public static void setOrientation(boolean isLandscape) {
        if (wInstance != null) {
            wInstance.setOrientation(isLandscape);
        } else {
            IMLogger.e("initialize should be called before setOrientation");
        }
    }

    public static void setPosition(int x, int y, int w, int h) {
        if (wInstance != null) {
            wInstance.setPosition(x, y, w, h);
        } else {
            IMLogger.e("initialize should be called before close");
        }
    }

    public static void setBackgroundColor(int a, int r, int g, int b) {
        if (wInstance != null) {
            wInstance.setBackgroundColor(a, r, g, b);
        } else {
            IMLogger.e("initialize should be called before close");
        }
    }

    public static void back() {
        if (wInstance != null) {
            wInstance.back();
        } else {
            IMLogger.e("initialize should be called before close");
        }
    }

    public static void forward() {
        if (wInstance != null) {
            wInstance.forward();
        } else {
            IMLogger.e("initialize should be called before close");
        }
    }

    public static void reload() {
        if (wInstance != null) {
            wInstance.reload();
        } else {
            IMLogger.e("initialize should be called before close");
        }
    }

    public static void close() {
        if (wInstance != null) {
            wInstance.close();
        } else {
            IMLogger.e("initialize should be called before close");
        }
    }

    public static void openURL(String url, boolean toolBar, boolean browser) {
        openURLWithTicket(url, toolBar, browser, true);
    }

    public static void openURL(String url, boolean toolBar, boolean browser, String extraJson) {
        if (extraJson != null) {
            try {
                IMLogger.d("openURLWithExtra extraJson = " + extraJson);
                JSONObject json = new JSONObject(extraJson);
                if (json.has(KEY_JSON_TICKET)) {
                    boolean isTicket = json.getBoolean(KEY_JSON_TICKET);
                    if (json.has(KEY_JSON_X5)) {
                        openURLX5(url, toolBar, isTicket, json.getBoolean(KEY_JSON_X5));
                        return;
                    } else {
                        openURLWithTicket(url, toolBar, browser, isTicket);
                        return;
                    }
                } else if (json.has(KEY_JSON_X5)) {
                    openURLX5(url, toolBar, true, json.getBoolean(KEY_JSON_X5));
                    return;
                }
            } catch (Exception e) {
                IMLogger.e(e.getMessage());
            }
        } else {
            IMLogger.d("openURLWithExtra extraJson null,  exec default openURL!");
        }
        openURLWithTicket(url, toolBar, browser, true);
    }

    private static void openURLX5(final String url, final boolean toolBar, final boolean isTicket, final boolean isX5Work) {
        try {
            if (TextUtils.isEmpty(url)) {
                IMLogger.w("this IMCommon need a valid url start with http:// or https:// or file:// or ftp://");
            } else {
                IMWebViewManager.getInstance().getMainHandler().post(new Runnable() {
                    public void run() {
                        if (isTicket) {
                            IMWebViewManager.getInstance().getTicketRequest(new IMWebviewGetTickInterface() {
                                public void onGetTicketSuccess(String ticket) {
                                    IMLogger.d("openURLX5 getTicketRequest success ticket=" + ticket);
                                    if (IMWebview.wInstance != null) {
                                        IMWebview.wInstance.optCmd(1, url, ticket, Boolean.valueOf(toolBar), Boolean.valueOf(isX5Work));
                                    }
                                }

                                public void onGetTicketFail() {
                                    IMLogger.d("openURLX5 getTicketRequest fail");
                                    if (IMWebview.wInstance != null) {
                                        IMWebview.wInstance.optCmd(1, url, "", Boolean.valueOf(toolBar), Boolean.valueOf(isX5Work));
                                    }
                                }
                            });
                            return;
                        }
                        IMLogger.d("openURLX5 with no get ticket!");
                        if (IMWebview.wInstance != null) {
                            IMWebview.wInstance.optCmd(1, url, "", Boolean.valueOf(toolBar), Boolean.valueOf(isX5Work));
                        }
                    }
                });
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    private static void openURLWithTicket(final String url, final boolean toolBar, final boolean browser, final boolean isTicket) {
        try {
            if (TextUtils.isEmpty(url)) {
                IMLogger.w("this IMCommon need a valid url start with http:// or https:// or file:// or ftp://");
            } else {
                IMWebViewManager.getInstance().getMainHandler().post(new Runnable() {
                    public void run() {
                        if (isTicket) {
                            IMWebViewManager.getInstance().getTicketRequest(new IMWebviewGetTickInterface() {
                                public void onGetTicketSuccess(String ticket) {
                                    IMLogger.d("openURLWithTicket getTicketRequest success ticket=" + ticket);
                                    if (IMWebview.wInstance != null) {
                                        IMWebview.wInstance.openURL(url, ticket, toolBar, browser);
                                    }
                                }

                                public void onGetTicketFail() {
                                    IMLogger.d("openURLWithTicket getTicketRequest fail");
                                    if (IMWebview.wInstance != null) {
                                        IMWebview.wInstance.openURL(url, "", toolBar, browser);
                                    }
                                }
                            });
                            return;
                        }
                        IMLogger.d("openURLWithTicket with no get ticket!");
                        if (IMWebview.wInstance != null) {
                            IMWebview.wInstance.openURL(url, "", toolBar, browser);
                        }
                    }
                });
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
    }

    public static boolean isActivated() {
        if (wInstance != null) {
            return wInstance.isActivated();
        }
        return false;
    }

    public static boolean canGoBack() {
        if (wInstance != null) {
            return wInstance.canGoBack();
        }
        return false;
    }

    public static boolean canGoForward() {
        if (wInstance != null) {
            return wInstance.canGoForward();
        }
        return false;
    }

    public static void getIMSDKTicket(IMWebviewGetTickInterface callback) {
        if (wInstance != null) {
            IMWebViewManager.getInstance().getTicketRequest(callback);
        }
    }

    public static void setWebViewActionListener(IMWebViewActionListener listener) {
        if (wInstance != null) {
            wInstance.setWebViewActionListener(listener);
        }
    }

    public static void callJs(String parasJson) {
        if (wInstance != null) {
            wInstance.callJs(parasJson);
        } else {
            IMLogger.e("callJs should be called before close");
        }
    }
}

package com.tencent.mtt.spcialcall.sdk;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;
import com.tencent.mtt.spcialcall.WebViewProxyManager;
import com.tencent.smtt.sdk.TbsConfig;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class MttApi {
    public static final String BOOKMARK_ITEMS = "bookmarkItems";
    public static final String CHANNELID = "";
    public static final String CID_MQQ_PUB_ACCOUNT = "10299";
    public static final String DISPLAY_TYPE = "display_type";
    public static final String DOWNLOAD_ITEMS = "downloadItems";
    public static final String DOWNLOAD_RSP = "downloadRsp";
    public static final String FAV_ITEMS = "favItems";
    public static final int FROM_AIO = 1;
    public static final int FROM_LEBA = 3;
    public static final int FROM_PUB_ACCOUNT = 4;
    public static final int FROM_QZONE = 2;
    public static final String FROM_TYPE = "from";
    public static final String FUNC_BOOKMARK = "func_bookmark";
    public static final String FUNC_BROWSER = "func_browser";
    public static final String FUNC_COPYLINK = "func_copylink";
    public static final String FUNC_FAV = "func_fav";
    public static final String FUNC_MTT = "func_mtt";
    public static final String FUNC_READMODE = "func_readmode";
    public static final String FUNC_RESTORE = "func_restore";
    public static final String FUNC_SHARE = "func_share";
    public static final String FUNC_TITLE = "func_title";
    public static final String FUNC_TITLE_MUTABLE = "func_title_mutable";
    public static final String FUNC_WEBAPP = "func_hide_toolbar";
    public static final int MENU_ACTION_SHEET = 1;
    public static final int MENU_POP_UP = 0;
    public static final String MENU_STYLE = "menu_style";
    public static final String MORE_ITEMS = "moreItems";
    public static final String MORE_RSP = "moreRsp";
    public static final String MTT_ACTION_LIGHTAPPP = "com.tencent.QQBrowser.action.LIGHTAPP";
    public static final String MTT_ACTION_LITE = "com.tencent.QQBrowser.action.VIEWLITE";
    public static final String MTT_ACTION_SP = "com.tencent.QQBrowser.action.VIEWSP";
    private static final String MTT_SIG = "3082023f308201a8a00302010202044c46914a300d06092a864886f70d01010505003064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f301e170d3130303732313036313835305a170d3430303731333036313835305a3064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f30819f300d06092a864886f70d010101050003818d0030818902818100c209077044bd0d63ea00ede5b839914cabcc912a87f0f8b390877e0f7a2583f0d5933443c40431c35a4433bc4c965800141961adc44c9625b1d321385221fd097e5bdc2f44a1840d643ab59dc070cf6c4b4b4d98bed5cbb8046e0a7078ae134da107cdf2bfc9b440fe5cb2f7549b44b73202cc6f7c2c55b8cfb0d333a021f01f0203010001300d06092a864886f70d010105050003818100b007db9922774ef4ccfee81ba514a8d57c410257e7a2eba64bfa17c9e690da08106d32f637ac41fbc9f205176c71bde238c872c3ee2f8313502bee44c80288ea4ef377a6f2cdfe4d3653c145c4acfedbfbadea23b559d41980cc3cdd35d79a68240693739aabf5c5ed26148756cf88264226de394c8a24ac35b712b120d4d23a";
    public static final String ORIPKG = "oriPackage";
    public static final String PLUS_ITEMS = "plusItems";
    public static final String PLUS_RSP = "plusRsp";
    public static final String POST_DATA = "post_data";
    public static final int RESULT_LITECORE_STATE = 431;
    public static final int RESULT_SHARE_RSP = 4302;
    public static final int RESULT_X5CORE_FAIL = 4301;
    public static final int RESULT_X5CORE_STATE = 430;
    public static final String SHARE_ITEMS = "shareItems";
    public static final String SHARE_RSP = "shareRsp";
    public static final String THEME_ITEMS = "themeItems";
    public static final String TITLEBAR_HEIGHT = "titlebar_height";
    public static final String TITLE_ITEMS = "title_items";
    public static final String TOOLBAR_HEIGTH = "toolbar_heigth";
    public static final int TYPE_FULLSCREEN = 1;
    public static final int TYPE_NO_TITLEBAR = 0;
    public static final String WEBVIEW_ID = "webview_id";
    public static final String WHITELIST_PATTERN = "whitelist_pattern";
    static Intent intent = new Intent();

    public static WebViewProxy createWebViewProxy(Activity context) {
        return WebViewProxyManager.getInstance().create(context);
    }

    @Deprecated
    public static boolean loadUrlInMbWnd(Activity context, String url, byte[] postData) {
        intent.setAction("com.tencent.QQBrowser.action.VIEWSP");
        intent.setData(Uri.parse(url));
        Bundle extras = intent.getExtras();
        if (extras == null || (!extras.containsKey("sid") && !extras.containsKey("skey"))) {
            intent.setPackage((String) null);
        } else {
            try {
                if (!context.getPackageManager().getPackageInfo(TbsConfig.APP_QB, 64).signatures[0].toCharsString().equals(MTT_SIG)) {
                    return false;
                }
                intent.setPackage(TbsConfig.APP_QB);
            } catch (Exception e) {
                return false;
            }
        }
        intent.putExtra(POST_DATA, postData);
        intent.putExtra(ORIPKG, context.getPackageName());
        try {
            context.startActivityForResult(intent, RESULT_X5CORE_STATE);
            return true;
        } catch (Exception e2) {
            return false;
        }
    }

    @Deprecated
    public static boolean loadUrlInLiteMbWnd(Activity context, String url, byte[] postData) {
        intent.setAction(MTT_ACTION_LITE);
        intent.setData(Uri.parse(url));
        intent.putExtra(POST_DATA, postData);
        intent.setPackage(context.getPackageName());
        intent.putExtra(ORIPKG, context.getPackageName());
        try {
            context.startActivityForResult(intent, RESULT_LITECORE_STATE);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Deprecated
    public static boolean loadUrlInMbWnd(Activity context, String url) {
        return loadUrlInMbWnd(context, url, (byte[]) null);
    }

    @Deprecated
    public static boolean loadUrlInLiteMbWnd(Activity context, String url) {
        return loadUrlInLiteMbWnd(context, url, (byte[]) null);
    }

    @Deprecated
    public static int loadUrlInMttWnd(Activity context, String url, HashMap<String, String> args) {
        return MttLoader.loadUrl(context, url, args);
    }

    public static boolean isSpecialCallRsp(Intent intent2) {
        if (intent2 == null || (intent2.getSerializableExtra(SHARE_RSP) == null && intent2.getSerializableExtra(MORE_RSP) == null && intent2.getSerializableExtra(PLUS_RSP) == null && intent2.getSerializableExtra(DOWNLOAD_RSP) == null)) {
            return false;
        }
        return true;
    }

    public static RspContent parseResponse(Intent intent2) {
        if (isSpecialCallRsp(intent2)) {
            if (intent2.getSerializableExtra(SHARE_RSP) != null) {
                return (RspContent) intent2.getSerializableExtra(SHARE_RSP);
            }
            if (intent2.getSerializableExtra(MORE_RSP) != null) {
                return (RspContent) intent2.getSerializableExtra(MORE_RSP);
            }
            if (intent2.getSerializableExtra(PLUS_RSP) != null) {
                return (RspContent) intent2.getSerializableExtra(PLUS_RSP);
            }
            if (intent2.getSerializableExtra(DOWNLOAD_RSP) != null) {
                return (RspContent) intent2.getSerializableExtra(DOWNLOAD_RSP);
            }
        }
        return null;
    }

    public static void setDefaultFunc(boolean share, boolean copyLink, boolean mtt, boolean browser, boolean readmode, boolean bookmark, boolean fav, int menu_style) {
        intent.putExtra(FUNC_SHARE, share);
        intent.putExtra(FUNC_COPYLINK, copyLink);
        intent.putExtra(FUNC_MTT, mtt);
        intent.putExtra(FUNC_BROWSER, browser);
        intent.putExtra(FUNC_READMODE, readmode);
        intent.putExtra(FUNC_BOOKMARK, bookmark);
        intent.putExtra(FUNC_FAV, fav);
        intent.putExtra(MENU_STYLE, menu_style);
    }

    public static void setTile(String title, boolean enableChage) {
        intent.putExtra(FUNC_TITLE, title);
        intent.putExtra(FUNC_TITLE_MUTABLE, enableChage);
    }

    public static void enableRestore(boolean enable) {
        intent.putExtra(FUNC_RESTORE, enable);
    }

    public static void setDownloadItem(ExtendItem item) {
        intent.putExtra(DOWNLOAD_ITEMS, item);
    }

    @Deprecated
    public static void setWebAppMod(boolean isWebApp) {
        intent.putExtra(FUNC_WEBAPP, isWebApp);
    }

    public static void setDisplayMode(int mode) {
        intent.putExtra(DISPLAY_TYPE, mode);
    }

    public static void setTheme(List<ExtendItem> mThemeItems) {
        if (mThemeItems != null && mThemeItems.size() > 0) {
            intent.putExtra(THEME_ITEMS, (Serializable) mThemeItems);
        }
    }

    public static void setBarHeight(int titleBarHeight, int toolBarHeight) {
        if (titleBarHeight != -1) {
            intent.putExtra(TITLEBAR_HEIGHT, titleBarHeight);
        }
        if (titleBarHeight != -1) {
            intent.putExtra(TOOLBAR_HEIGTH, toolBarHeight);
        }
    }

    public static void setFromType(int fromType) {
        intent.putExtra("from", fromType);
    }

    public static int getFromType() {
        return intent.getIntExtra("from", -1);
    }

    public static String getStringExtra(String name) {
        return intent.getStringExtra(name);
    }

    public static void setExtraBundle(Bundle b) {
        intent.putExtras(b);
    }

    public static void setMoreItem(List<ExtendItem> moreItems) {
        if (moreItems != null && moreItems.size() > 0) {
            intent.putExtra(MORE_ITEMS, (Serializable) moreItems);
        }
    }

    public static void setShareItem(List<ExtendItem> shareItems) {
        if (shareItems != null && shareItems.size() > 0) {
            intent.putExtra(SHARE_ITEMS, (Serializable) shareItems);
        }
    }

    public static void setPlusItem(ExtendItem plusItems) {
        intent.putExtra(PLUS_ITEMS, plusItems);
    }

    public static void setFavItem(ExtendItem favItems) {
        intent.putExtra(FAV_ITEMS, favItems);
    }

    public static void setBookmarkItem(ExtendItem bookmarkItems) {
        intent.putExtra(BOOKMARK_ITEMS, bookmarkItems);
    }

    public static void setTitleItem(List<ExtendItem> titleItems) {
        if (titleItems != null && titleItems.size() > 0) {
            intent.putExtra(TITLE_ITEMS, (Serializable) titleItems);
        }
    }

    public static void insertRecommend(WebView webView, RecommendParams params) {
        params.put(RecommendParams.BROWSER_VER, new StringBuilder(String.valueOf(MttLoader.getBrowserInfo(webView.getContext()).ver)).toString());
        params.put("title", webView.getTitle());
        params.put("url", webView.getUrl());
        try {
            webView.loadUrl("javascript:var insertJsNode=document.getElementById(\"" + "x5insertadnode" + "\");if(null==insertJsNode){insertJsNode=document.createElement('script');insertJsNode.setAttribute('id',\"" + "x5insertadnode" + "\");insertJsNode.setAttribute('charset','" + "gbk" + "');insertJsNode.setAttribute('src',\"" + params.buildUpon("http://mqqad.html5.qq.com/adjs") + "\");document.body.appendChild(insertJsNode);}");
        } catch (Exception e) {
        }
    }

    public static void insertRecommend(WebView webView, String url) {
        String adJsUrl;
        if (!isinValidUrl(webView, url)) {
            if (MttLoader.isBrowserInstalled(webView.getContext())) {
                adJsUrl = "http://res.imtt.qq.com/mbwebview/open_mtt_hd.txt";
            } else if (!TextUtils.isEmpty("")) {
                adJsUrl = "http://res.imtt.qq.com/mbwebview/download_mtt_hd_.txt";
            } else {
                adJsUrl = "http://res.imtt.qq.com/mbwebview/download_mtt_hd.txt";
            }
            try {
                webView.loadUrl("javascript:var insertJsNode=document.getElementById(\"" + "x5insertadnode" + "\");if(null==insertJsNode){insertJsNode=document.createElement('script');insertJsNode.setAttribute('id',\"" + "x5insertadnode" + "\");insertJsNode.setAttribute('charset','" + "gbk" + "');insertJsNode.setAttribute('src',\"" + adJsUrl + "\");document.body.appendChild(insertJsNode);}");
            } catch (Exception e) {
            }
        }
    }

    public static boolean isOpenMttUrl(WebView webView, String url) {
        if (!(url == null || webView == null || !url.startsWith("mttbrowser://"))) {
            Intent i = new Intent();
            i.setAction("com.tencent.QQBrowser.action.VIEW");
            String originalUrl = webView.getUrl();
            if (originalUrl != null) {
                i.setData(Uri.parse(originalUrl));
                try {
                    webView.getContext().startActivity(i);
                    return true;
                } catch (Exception e) {
                    webView.loadUrl(MttLoader.QQBROWSER_DOWNLOAD_URL);
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isinValidUrl(WebView webView, String url) {
        return webView == null || TextUtils.isEmpty(url);
    }

    public static void clearAllData() {
        intent = new Intent();
    }

    public static void setJsWhiteList(String pattern) {
        intent.putExtra(WHITELIST_PATTERN, pattern);
    }
}

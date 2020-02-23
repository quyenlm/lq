package com.tencent.mtt.spcialcall;

import android.content.Intent;
import android.os.Bundle;
import com.tencent.mtt.spcialcall.sdk.ExtendItem;
import com.tencent.mtt.spcialcall.sdk.MttApi;
import com.tencent.smtt.sdk.TbsConfig;
import java.io.Serializable;
import java.util.ArrayList;

public class ExtraInfo {
    private static boolean CanCopyLink = true;
    private static boolean CanOpenBrowser = true;
    private static boolean CanOpenMtt = true;
    private static boolean CanShare = true;
    private static int DisplayType = -1;
    private static boolean EnableChangeTitle = true;
    private static ExtendItem ExtraBookmarkItem;
    private static ExtendItem ExtraDownloadItem;
    private static ExtendItem ExtraFavItem;
    private static ArrayList<ExtendItem> ExtraMoreItem;
    private static Bundle ExtraParams = null;
    private static ExtendItem ExtraPlusItem;
    private static ArrayList<ExtendItem> ExtraShareItem;
    private static ArrayList<ExtendItem> ExtraThemeItem;
    private static ArrayList<ExtendItem> ExtraTitleItem;
    private static boolean HasBookmark = false;
    private static boolean HasFav = false;
    private static boolean HasReadMode = false;
    private static int MenuStyle;
    private static boolean NeedRestore = true;
    private static byte[] PostData = null;
    private static String Title = null;
    private static int TitleBarHeight = -1;
    private static int ToolBarHeight = -1;
    private static boolean WebApp = false;
    private static String WhiteListPattern = null;
    private static String oriPkg = null;

    public static int getDisplayType() {
        return DisplayType;
    }

    public static void setDefaultFunc(Intent intent) {
        ExtraParams = intent.getExtras();
        CanShare = intent.getBooleanExtra(MttApi.FUNC_SHARE, true);
        CanCopyLink = intent.getBooleanExtra(MttApi.FUNC_COPYLINK, true);
        CanOpenMtt = intent.getBooleanExtra(MttApi.FUNC_MTT, true);
        CanOpenBrowser = intent.getBooleanExtra(MttApi.FUNC_BROWSER, true);
        HasReadMode = intent.getBooleanExtra(MttApi.FUNC_READMODE, false);
        HasBookmark = intent.getBooleanExtra(MttApi.FUNC_BOOKMARK, false);
        HasFav = intent.getBooleanExtra(MttApi.FUNC_FAV, false);
        NeedRestore = intent.getBooleanExtra(MttApi.FUNC_RESTORE, true);
        WebApp = intent.getBooleanExtra(MttApi.FUNC_WEBAPP, false);
        MenuStyle = intent.getIntExtra(MttApi.MENU_STYLE, 0);
        DisplayType = intent.getIntExtra(MttApi.DISPLAY_TYPE, -1);
        PostData = intent.getByteArrayExtra(MttApi.POST_DATA);
        TitleBarHeight = intent.getIntExtra(MttApi.TITLEBAR_HEIGHT, -1);
        ToolBarHeight = intent.getIntExtra(MttApi.TOOLBAR_HEIGTH, -1);
        EnableChangeTitle = intent.getBooleanExtra(MttApi.FUNC_TITLE_MUTABLE, true);
        Title = intent.getStringExtra(MttApi.FUNC_TITLE);
        WhiteListPattern = intent.getStringExtra(MttApi.WHITELIST_PATTERN);
        ExtraThemeItem = (ArrayList) intent.getSerializableExtra(MttApi.THEME_ITEMS);
        ExtraShareItem = (ArrayList) intent.getSerializableExtra(MttApi.SHARE_ITEMS);
        ExtraMoreItem = (ArrayList) intent.getSerializableExtra(MttApi.MORE_ITEMS);
        ExtraPlusItem = (ExtendItem) intent.getSerializableExtra(MttApi.PLUS_ITEMS);
        ExtraFavItem = (ExtendItem) intent.getSerializableExtra(MttApi.FAV_ITEMS);
        ExtraBookmarkItem = (ExtendItem) intent.getSerializableExtra(MttApi.BOOKMARK_ITEMS);
        ExtraDownloadItem = (ExtendItem) intent.getSerializableExtra(MttApi.DOWNLOAD_ITEMS);
        setExtraTitleItem(intent.getSerializableExtra(MttApi.TITLE_ITEMS));
        setOriPkg(intent.getStringExtra(MttApi.ORIPKG));
    }

    public static ExtendItem getExtraFavItem() {
        return ExtraFavItem;
    }

    public static ArrayList<ExtendItem> getExtraShareItem() {
        return ExtraShareItem;
    }

    public static ArrayList<ExtendItem> getExtraTitleItem() {
        return ExtraTitleItem;
    }

    public static void setExtraTitleItem(Serializable serializable) {
        ExtraTitleItem = (ArrayList) serializable;
    }

    public static ArrayList<ExtendItem> getExtraMoreItem() {
        return ExtraMoreItem;
    }

    public static ArrayList<ExtendItem> getExtraThemeItem() {
        return ExtraThemeItem;
    }

    public static ExtendItem getExtraPlusItem() {
        return ExtraPlusItem;
    }

    public static ExtendItem getDownloadItem() {
        return ExtraDownloadItem;
    }

    public static String getOriPkg() {
        return oriPkg;
    }

    public static void setOriPkg(String oriPkg2) {
        oriPkg = oriPkg2;
    }

    public static boolean isCanShare() {
        return CanShare;
    }

    public static boolean isCanCopyLink() {
        return CanCopyLink;
    }

    public static boolean isCanOpenMtt() {
        return CanOpenMtt;
    }

    public static boolean isCanOpenBrowser() {
        return CanOpenBrowser;
    }

    public static int getMenuStyle() {
        return MenuStyle;
    }

    public static int getAppID() {
        if (TbsConfig.APP_QQ.equals(oriPkg)) {
            return 2;
        }
        if (TbsConfig.APP_WX.equals(oriPkg)) {
            return 5;
        }
        if ("com.tencent.WBlog".equals(oriPkg)) {
            return 4;
        }
        if (TbsConfig.APP_QZONE.equals(oriPkg)) {
            return 3;
        }
        return -1;
    }

    public static boolean isNeedRestore() {
        return NeedRestore;
    }

    public static boolean isWebApp() {
        return WebApp;
    }

    public static byte[] getPostData() {
        return PostData;
    }

    public static boolean isHasReadMode() {
        return HasReadMode;
    }

    public static boolean isHasBookmark() {
        return HasBookmark;
    }

    public static boolean isHasFav() {
        return HasFav;
    }

    public static int getTitleBarHeight() {
        return TitleBarHeight;
    }

    public static int getToolBarHeight() {
        return ToolBarHeight;
    }

    public static Bundle getExtraParams() {
        return ExtraParams;
    }

    public static boolean isEnableChangeTitle() {
        return EnableChangeTitle;
    }

    public static String getTitle() {
        return Title;
    }

    public static ExtendItem getExtraBookmarkItem() {
        return ExtraBookmarkItem;
    }

    public static String getWhiteListPattern() {
        return WhiteListPattern;
    }
}

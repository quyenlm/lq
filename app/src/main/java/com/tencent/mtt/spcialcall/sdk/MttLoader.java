package com.tencent.mtt.spcialcall.sdk;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MttLoader {
    private static final int BROWSER_MTT = 2;
    private static final int BROWSER_NONE = -1;
    private static final int BROWSER_QBX = 0;
    private static final int BROWSER_QBX5 = 1;
    @Deprecated
    public static final String KEY_ACTIVITY_NAME = "KEY_ACT";
    @Deprecated
    public static final String KEY_APP_NAME = "KEY_APPNAME";
    public static final String KEY_EUSESTAT = "KEY_EUSESTAT";
    @Deprecated
    public static final String KEY_PACKAGE = "KEY_PKG";
    public static final String KEY_PID = "KEY_PID";
    public static final String MTT_ACTION = "com.tencent.QQBrowser.action.VIEW";
    public static final String MTT_ACTION_SP = "com.tencent.QQBrowser.action.VIEWSP";
    private static final String MTT_PACKAGE_MTT = "com.tencent.mtt";
    private static final String MTT_PACKAGE_MTT_X86 = "com.tencent.mtt.x86";
    private static final String MTT_PACKAGE_QBX = "com.tencent.qbx";
    private static final String MTT_PACKAGE_QBX5 = "com.tencent.qbx5";
    public static final String PID_MOBILE_QQ = "50079";
    public static final String PID_QQPIM = "50190";
    public static final String QQBROWSER_DOWNLOAD_URL = "http://mdc.html5.qq.com/mh?channel_id=50079&u=";
    public static final int RESULT_INVALID_CONTEXT = 3;
    public static final int RESULT_INVALID_URL = 2;
    public static final int RESULT_NOT_INSTALL_QQBROWSER = 4;
    public static final int RESULT_OK = 0;
    public static final int RESULT_QQBROWSER_LOW = 5;
    public static final int RESULT_UNKNOWN = 1;
    private static final int SUPPORT_3RD_PARTY_CALL_VERSION = 33;
    private static final int SUPPORT_QB_SCHEME_VERSION = 42;
    private static final int VERSION_420 = 420000;

    public static class BrowserInfo {
        public int browserType = -1;
        public String quahead = "";
        public int ver = -1;
    }

    private static class BrowserPackageInfo {
        public String classname;
        public String packagename;

        private BrowserPackageInfo() {
            this.classname = "";
            this.packagename = "";
        }

        /* synthetic */ BrowserPackageInfo(BrowserPackageInfo browserPackageInfo) {
            this();
        }
    }

    public static String getValidQBUrl(Context context, String url) {
        if (!url.toLowerCase().startsWith("qb://")) {
            return url;
        }
        boolean shouldFixUrl = false;
        BrowserInfo info = getBrowserInfo(context);
        if (info.browserType == -1) {
            shouldFixUrl = true;
        } else if (info.browserType == 2 && info.ver < 33) {
            shouldFixUrl = true;
        }
        if (shouldFixUrl) {
            return getDownloadUrlWithQb(url);
        }
        return url;
    }

    public static String getDownloadUrlWithQb(String qburl) {
        try {
            return QQBROWSER_DOWNLOAD_URL + URLEncoder.encode(qburl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return QQBROWSER_DOWNLOAD_URL;
        }
    }

    public static boolean isSupportQBScheme(Context context) {
        BrowserInfo info = getBrowserInfo(context);
        if (info.browserType == -1) {
            return false;
        }
        if (info.browserType != 2 || info.ver >= 42) {
            return true;
        }
        return false;
    }

    public static int loadUrl(Activity context, String url, HashMap<String, String> args) {
        Set<String> keyset;
        if (context == null) {
            return 3;
        }
        if (!hasValidProtocal(url)) {
            url = "http://" + url;
        }
        try {
            Uri uri = Uri.parse(url);
            if (uri == null) {
                return 2;
            }
            if (uri.getScheme().toLowerCase().equals("qb") && !isSupportQBScheme(context)) {
                uri = Uri.parse(QQBROWSER_DOWNLOAD_URL + URLEncoder.encode(url, "UTF-8"));
            }
            BrowserInfo info = getBrowserInfo(context);
            if (info.browserType == -1) {
                return 4;
            }
            if (info.browserType == 2 && info.ver < 33) {
                return 5;
            }
            Intent intent = new Intent("android.intent.action.VIEW");
            if (info.browserType == 2) {
                if (info.ver >= 33 && info.ver <= 39) {
                    intent.setClassName("com.tencent.mtt", "com.tencent.mtt.MainActivity");
                } else if (info.ver >= 40 && info.ver <= 45) {
                    intent.setClassName("com.tencent.mtt", "com.tencent.mtt.SplashActivity");
                } else if (info.ver >= 46) {
                    intent = new Intent("com.tencent.QQBrowser.action.VIEW");
                    BrowserPackageInfo brinfo = chooseClassName(context, uri);
                    if (brinfo != null && !TextUtils.isEmpty(brinfo.classname)) {
                        intent.setClassName(brinfo.packagename, brinfo.classname);
                    }
                }
            } else if (info.browserType == 1) {
                if (info.ver == 1) {
                    intent.setClassName(MTT_PACKAGE_QBX5, "com.tencent.qbx5.MainActivity");
                } else if (info.ver == 2) {
                    intent.setClassName(MTT_PACKAGE_QBX5, "com.tencent.qbx5.SplashActivity");
                }
            } else if (info.browserType != 0) {
                intent = new Intent("com.tencent.QQBrowser.action.VIEW");
                BrowserPackageInfo brinfo2 = chooseClassName(context, uri);
                if (brinfo2 != null && !TextUtils.isEmpty(brinfo2.classname)) {
                    intent.setClassName(brinfo2.packagename, brinfo2.classname);
                }
            } else if (info.ver >= 4 && info.ver <= 6) {
                intent.setClassName(MTT_PACKAGE_QBX, "com.tencent.qbx.SplashActivity");
            } else if (info.ver > 6) {
                intent = new Intent("com.tencent.QQBrowser.action.VIEW");
                BrowserPackageInfo brinfo3 = chooseClassName(context, uri);
                if (brinfo3 != null && !TextUtils.isEmpty(brinfo3.classname)) {
                    intent.setClassName(brinfo3.packagename, brinfo3.classname);
                }
            }
            intent.setData(uri);
            if (!(args == null || (keyset = args.keySet()) == null)) {
                for (String key : keyset) {
                    String value = args.get(key);
                    if (!TextUtils.isEmpty(value)) {
                        intent.putExtra(key, value);
                    }
                }
            }
            try {
                context.startActivity(intent);
                return 0;
            } catch (ActivityNotFoundException e) {
                return 4;
            }
        } catch (Exception e2) {
            return 2;
        }
    }

    private static BrowserPackageInfo chooseClassName(Context context, Uri uri) {
        Intent intent = new Intent("com.tencent.QQBrowser.action.VIEW");
        intent.setData(uri);
        List<ResolveInfo> apps = context.getPackageManager().queryIntentActivities(intent, 0);
        if (apps.size() <= 0) {
            return null;
        }
        BrowserPackageInfo info = new BrowserPackageInfo((BrowserPackageInfo) null);
        for (ResolveInfo app : apps) {
            String packagename = app.activityInfo.packageName;
            if (packagename.contains("com.tencent.mtt")) {
                info.classname = app.activityInfo.name;
                info.packagename = app.activityInfo.packageName;
                return info;
            } else if (packagename.contains(MTT_PACKAGE_QBX)) {
                info.classname = app.activityInfo.name;
                info.packagename = app.activityInfo.packageName;
            }
        }
        return info;
    }

    public static BrowserInfo getBrowserInfo(Context context) {
        BrowserInfo result = new BrowserInfo();
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = null;
            try {
                pi = pm.getPackageInfo("com.tencent.mtt", 0);
                result.browserType = 2;
                result.quahead = "ADRQB_";
                if (pi != null && pi.versionCode > VERSION_420) {
                    result.ver = pi.versionCode;
                    result.quahead = String.valueOf(result.quahead) + pi.versionName.replaceAll("\\.", "");
                    return result;
                }
            } catch (PackageManager.NameNotFoundException e) {
            }
            try {
                pi = pm.getPackageInfo(MTT_PACKAGE_QBX, 0);
                result.browserType = 0;
                result.quahead = "ADRQBX_";
            } catch (PackageManager.NameNotFoundException e2) {
                try {
                    pi = pm.getPackageInfo(MTT_PACKAGE_QBX5, 0);
                    result.browserType = 1;
                    result.quahead = "ADRQBX5_";
                } catch (PackageManager.NameNotFoundException e3) {
                    try {
                        pi = pm.getPackageInfo("com.tencent.mtt", 0);
                        result.browserType = 2;
                        result.quahead = "ADRQB_";
                    } catch (PackageManager.NameNotFoundException e4) {
                        try {
                            pi = pm.getPackageInfo(MTT_PACKAGE_MTT_X86, 0);
                            result.browserType = 2;
                            result.quahead = "ADRQB_";
                        } catch (Exception e5) {
                            try {
                                BrowserPackageInfo browserPackageInfo = chooseClassName(context, Uri.parse(QQBROWSER_DOWNLOAD_URL));
                                if (browserPackageInfo != null && !TextUtils.isEmpty(browserPackageInfo.packagename)) {
                                    pi = pm.getPackageInfo(browserPackageInfo.packagename, 0);
                                    result.browserType = 2;
                                    result.quahead = "ADRQB_";
                                }
                            } catch (Exception e6) {
                            }
                        }
                    }
                }
            }
            if (pi != null) {
                result.ver = pi.versionCode;
                result.quahead = String.valueOf(result.quahead) + pi.versionName.replaceAll("\\.", "");
            }
        } catch (Exception e7) {
        }
        return result;
    }

    private static boolean hasValidProtocal(String aUrl) {
        if (aUrl == null || aUrl.length() == 0) {
            return false;
        }
        String url = aUrl.trim();
        int pos1 = url.toLowerCase().indexOf("://");
        int pos2 = url.toLowerCase().indexOf(46);
        if (pos1 <= 0 || pos2 <= 0 || pos1 <= pos2) {
            return url.toLowerCase().contains("://");
        }
        return false;
    }

    public static boolean isBrowserInstalled(Context context) {
        if (getBrowserInfo(context).browserType == -1) {
            return false;
        }
        return true;
    }
}

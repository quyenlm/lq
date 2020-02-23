package com.tencent.component.plugin.server;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.text.TextUtils;
import com.appsflyer.share.Constants;
import com.tencent.component.annotation.PluginApi;
import com.tencent.component.plugin.PluginInfo;
import com.tencent.component.utils.FileUtil;
import com.tencent.component.utils.SecurityUtil;
import com.tencent.imsdk.tool.etc.APNUtil;
import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.regex.Pattern;

public class PluginConstant {
    public static final String ACTION_INITIALIZE_FINISH = "plugin_platform_initialize_finish";
    public static final String ACTION_INITIALIZE_START = "plugin_platform_initialize_start";
    private static final String BUILTIN_CONFIG_FILE_FOLDER = "plugins";
    private static final String BUILTIN_CONFIG_FILE_NAME = "config.xml";
    static final Uri BUILTIN_DEFAULT_URI = null;
    public static final int FRAGMENT_CONTAINER_ID = Integer.MAX_VALUE;
    public static final String INTENT_PLUGIN = "__intent_plugin";
    @PluginApi(since = 400)
    public static final String INTENT_PLUGIN_ARGS = "__plugin_data";
    public static final String INTENT_PLUGIN_FRAGMENT = "__plugin_fragment";
    public static final String INTENT_PLUGIN_INNER = "__plugin_inner";
    public static final String INTENT_PLUGIN_LEAFSERVICE = "__plugin_leafservice";
    public static final String INTENT_PLUGIN_PLATFORM_ID = "__plugin_platform_id";
    private static final String KEY_PENDING_INFO_CORE_PLUGIN = "p_info_core_plugin";
    private static final String KEY_PENDING_INFO_EXTRA_INFO = "p_info_extra_info";
    private static final String PENDING_INSTALL_INFO = "p_pending_install_info";
    static final String PLUGIN_DEX_OPT_DIR = "dex_opt";
    static final String PLUGIN_EXTRA_DIR = "plugins_extra";
    public static final String PLUGIN_INSTALL_DEX_OPT_SUFFIX = ".dex";
    static final String PLUGIN_INSTALL_DIR = "plugins_installed";
    static final String PLUGIN_INSTALL_PACKAGE_SUFFIX = ".zip";
    public static final String PLUGIN_INSTALL_PENDING_DIR = "plugins_pending";
    static final String PLUGIN_LIB_DIR = "lib";
    static final String PLUGIN_META = "plugin.xml";
    static final String PLUGIN_META_BOOT_LISTENER_CLASS = "qqgame.plugin.boot.receiver";
    static final String PLUGIN_META_CORE_PLUGIN = "qqgame.plugin.corePlugin";
    static final String PLUGIN_META_EXCLUSIVE = "qqgame.plugin.exclusive";
    static final String PLUGIN_META_EXTRA_INFO_AUTO_LOAD = "qqgame.plugin.extra.autoLoad";
    static final String PLUGIN_META_EXTRA_INFO_SINGLE_PROCESS = "qqgame.plugin.extra.singleProcess";
    static final String PLUGIN_META_EXTRA_INFO_SINGLE_TOP = "qqgame.plugin.extra.singleTop";
    static final String PLUGIN_META_LAUNCH_FRAGMENT = "qqgame.plugin.launch.fragment";
    static final String PLUGIN_META_MAX_ANDROID_VERSION = "qqgame.plugin.maxAndroidVersion";
    static final String PLUGIN_META_MAX_BASE_PLATFORM_VERSION = "qqgame.plugin.maxBasePlatformVersion";
    @Deprecated
    static final String PLUGIN_META_MAX_PLATFORM_VERSION = "qqgame.plugin.maxPlatformVersion";
    static final String PLUGIN_META_MIN_ANDROID_VERSION = "qqgame.plugin.minAndroidVersion";
    static final String PLUGIN_META_MIN_BASE_PLATFORM_VERSION = "qqgame.plugin.minBasePlatformVersion";
    @Deprecated
    static final String PLUGIN_META_MIN_PLATFORM_VERSION = "qqgame.plugin.minPlatformVersion";
    static final String PLUGIN_META_PLUGIN_CLASS = "qqgame.plugin.class";
    private static final String PLUGIN_META_PREFIX = "qqgame.plugin.";
    static final String PLUGIN_META_REQUIRES = "requires";
    static final String PLUGIN_META_SURVIVE_DETECTOR = "qqgame.plugin.survive.detector";
    public static final int PLUGIN_PLATFORM_BUILD_NUMBER = 0;
    public static final int PLUGIN_PLATFORM_VERSION = 600;
    public static final String PLUGIN_PLATFROM_VERSION_NAME = "1.7.0.0";
    static final boolean PLUGIN_SIGNATURE_IMPORTANT_ONLY = true;
    static final String PLUGIN_SUB_META_REQUIRES_ITEM_NAME = "requireInfo";
    static final String PLUGIN_SUB_META_REQUIRES_ITEM_PROPERTY_ID = "id";
    static final String PLUGIN_SUB_META_REQUIRES_ITEM_PROPERTY_MAX_VERSION = "maxVersion";
    static final String PLUGIN_SUB_META_REQUIRES_ITEM_PROPERTY_MIN_VERSION = "minVersion";
    private static final String PLUGIN_TMP_DIR_NAME = "pluginTmp";
    private static final boolean PLUGIN_VERIFY_PLATFORM_SIGNATURE = true;
    static final HashMap<String, Integer> SINGLE_TOP_CONFIG = new HashMap<String, Integer>() {
        {
            put(APNUtil.ANP_NAME_NONE, 0);
            put("standard", 1);
            put("exactly", 2);
        }
    };
    private static final ThreadLocal<StringBuilder> sStringBuilder = new ThreadLocal<StringBuilder>() {
        /* access modifiers changed from: protected */
        public StringBuilder initialValue() {
            return new StringBuilder();
        }
    };

    static String getBuiltinConfigFilePath(PlatformServerContext platformServerContext) {
        return "plugins/" + platformServerContext.getPlatformId() + Constants.URL_PATH_DELIMITER + BUILTIN_CONFIG_FILE_NAME;
    }

    static File getInstallDir(PlatformServerContext platformServerContext) {
        return platformServerContext.getContext().getDir("plugins_installed_" + platformServerContext.getPlatformId(), 0);
    }

    static File getInstallPendingDir(PlatformServerContext platformServerContext) {
        return platformServerContext.getContext().getDir("plugins_pending_" + platformServerContext.getPlatformId(), 0);
    }

    static File getExternalInstallPendingDir(PlatformServerContext platformServerContext) {
        String dir = FileUtil.getExternalCacheDirExt(platformServerContext.getContext(), "plugins_pending_" + platformServerContext.getPlatformId());
        if (!TextUtils.isEmpty(dir)) {
            return new File(dir);
        }
        return null;
    }

    static File getExtraDir(Context context) {
        return context.getDir(PLUGIN_EXTRA_DIR, 0);
    }

    static String getPluginInstallName(PluginInfo pluginInfo) {
        String pluginName = getPluginName(pluginInfo);
        if (isEmpty(pluginName)) {
            return null;
        }
        return pluginName + ".zip";
    }

    private static String getPluginName(PluginInfo pluginInfo) {
        String name = pluginInfo == null ? null : pluginInfo.pluginId;
        if (isEmpty(name)) {
            return null;
        }
        return getPluginName(name, pluginInfo.version);
    }

    private static String getPluginName(String pluginId, int pluginVersion) {
        if (isEmpty(pluginId) || pluginVersion < 0) {
            return null;
        }
        String encryptedName = getPluginEncryptedName(pluginId);
        if (isEmpty(encryptedName)) {
            return null;
        }
        return String.format("GR-%s-%d-%s", new Object[]{pluginId, Integer.valueOf(pluginVersion), encryptedName});
    }

    static FilenameFilter getPluginInstallNameFilter(String id) {
        final String regex = getPluginEncryptedName(id);
        return new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                return filename.contains(regex);
            }
        };
    }

    static String getPluginNativeLibDir(Context context, PluginInfo pluginInfo) {
        String name = getPluginEncryptedName(pluginInfo);
        if (!isEmpty(name)) {
            return getExtraDir(context).getAbsolutePath() + File.separator + PLUGIN_LIB_DIR + File.separator + name;
        }
        return null;
    }

    public static String getPluginNativeLibDir(Context context) {
        if (context == null) {
            return null;
        }
        return getExtraDir(context).getAbsolutePath() + File.separator + PLUGIN_LIB_DIR;
    }

    private static String getPendingInfoKey(String key, String location) {
        if (TextUtils.isEmpty(location) || TextUtils.isEmpty(key)) {
            return null;
        }
        return key + "_" + SecurityUtil.encrypt(location);
    }

    public static void setPendingInstallInfo(Context context, String installLocation, boolean corePlugin, String extraInfo) {
        if (!TextUtils.isEmpty(installLocation)) {
            if (TextUtils.isEmpty(extraInfo)) {
                extraInfo = "";
            }
            String corePluginKey = getPendingInfoKey(KEY_PENDING_INFO_CORE_PLUGIN, installLocation);
            String extraInfoKey = getPendingInfoKey(KEY_PENDING_INFO_EXTRA_INFO, installLocation);
            if (!TextUtils.isEmpty(corePluginKey) && !TextUtils.isEmpty(extraInfoKey)) {
                SharedPreferences.Editor editor = context.getSharedPreferences(PENDING_INSTALL_INFO, 4).edit();
                editor.putBoolean(corePluginKey, corePlugin);
                editor.putString(extraInfoKey, extraInfo);
                editor.commit();
            }
        }
    }

    public static void removePendingInstallInfo(Context context, String installLocation) {
        if (!TextUtils.isEmpty(installLocation)) {
            String corePluginKey = getPendingInfoKey(KEY_PENDING_INFO_CORE_PLUGIN, installLocation);
            String extraInfoKey = getPendingInfoKey(KEY_PENDING_INFO_EXTRA_INFO, installLocation);
            SharedPreferences.Editor editor = context.getSharedPreferences(PENDING_INSTALL_INFO, 4).edit();
            if (!TextUtils.isEmpty(corePluginKey)) {
                editor.remove(corePluginKey);
            }
            if (!TextUtils.isEmpty(extraInfoKey)) {
                editor.remove(extraInfoKey);
            }
            editor.commit();
        }
    }

    public static boolean getCorePluginPendingInstallInfo(Context context, String installLocation) {
        if (TextUtils.isEmpty(installLocation)) {
            return false;
        }
        String corePluginKey = getPendingInfoKey(KEY_PENDING_INFO_CORE_PLUGIN, installLocation);
        if (!TextUtils.isEmpty(corePluginKey)) {
            return context.getSharedPreferences(PENDING_INSTALL_INFO, 4).getBoolean(corePluginKey, false);
        }
        return false;
    }

    public static String getPendingInstallExtraInfo(Context context, String installLocation) {
        if (!TextUtils.isEmpty(installLocation)) {
            String extraInfoKey = getPendingInfoKey(KEY_PENDING_INFO_EXTRA_INFO, installLocation);
            if (!TextUtils.isEmpty(extraInfoKey)) {
                return context.getSharedPreferences(PENDING_INSTALL_INFO, 4).getString(extraInfoKey, "");
            }
        }
        return "";
    }

    public static String getPluginTmpDir(Context context, String fileName, boolean external, boolean persist) {
        String dir;
        if (isEmpty(fileName)) {
            return null;
        }
        if (external) {
            dir = FileUtil.getExternalCacheDir(context, PLUGIN_TMP_DIR_NAME, persist);
        } else {
            dir = FileUtil.getInternalCacheDir(context, PLUGIN_TMP_DIR_NAME, persist);
        }
        if (dir == null) {
            return null;
        }
        StringBuilder sb = sStringBuilder.get();
        sb.delete(0, sb.length());
        sb.append(dir).append(File.separatorChar).append(fileName);
        return sb.toString();
    }

    static String getPluginDexOptimizeDir(Context context) {
        return getExtraDir(context).getAbsolutePath() + File.separator + PLUGIN_DEX_OPT_DIR;
    }

    static String getPluginDexOptimizeName(PluginInfo pluginInfo) {
        String name = getPluginEncryptedName(pluginInfo);
        if (isEmpty(name)) {
            return null;
        }
        return name + PLUGIN_INSTALL_DEX_OPT_SUFFIX;
    }

    static boolean shouldCheckPlatformSignature(Context context) {
        return true;
    }

    private static String getPluginEncryptedName(PluginInfo pluginInfo) {
        return getPluginEncryptedName(pluginInfo == null ? null : pluginInfo.pluginId);
    }

    public static boolean isFormatedInstalledPluginName(String filepath) {
        if (TextUtils.isEmpty(filepath)) {
            return false;
        }
        return Pattern.compile("^GR-[\\.|\\w]+-\\d+-\\w+\\.\\w").matcher(filepath).find();
    }

    private static String getPluginEncryptedName(String name) {
        if (isEmpty(name)) {
            return null;
        }
        return SecurityUtil.encrypt(name);
    }

    private static boolean isDebuggable(Context context) {
        ApplicationInfo appInfo = context.getApplicationInfo();
        return (appInfo == null || (appInfo.flags & 2) == 0) ? false : true;
    }

    private static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}

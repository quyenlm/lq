package com.tencent.component.plugin.server;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.component.plugin.PluginInfo;
import com.tencent.component.utils.ApkUtil;
import com.tencent.component.utils.log.LogUtil;
import java.util.ArrayList;
import java.util.Map;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class PluginParser {
    public static final int GET_SIGNATURES = 1;
    private static final String TAG = "PluginParser";

    private PluginParser() {
    }

    static PluginInfo parse(PlatformServerContext platformServerContext, Context context, String pluginPath) {
        return parse(platformServerContext, context, pluginPath, 0);
    }

    static PluginInfo parse(PlatformServerContext platformServerContext, Context context, String pluginPath, int flags) {
        if (TextUtils.isEmpty(pluginPath)) {
            LogUtil.e(TAG, "parse plugin failed,pluginPath is empty.");
            return null;
        }
        PackageInfo packageInfo = generatePackageInfo(context, pluginPath, flags);
        ApplicationInfo applicationInfo = packageInfo == null ? null : packageInfo.applicationInfo;
        if (packageInfo == null || applicationInfo == null) {
            LogUtil.e(TAG, "parse plugin failed,packageInfo or applicationInfo is empty.");
            return null;
        }
        Bundle metaData = parseMetaData(context, pluginPath);
        if (metaData == null) {
            LogUtil.e(TAG, "parse plugin failed,metaData is empty.");
            return null;
        }
        PluginInfo pluginInfo = new PluginInfo();
        pluginInfo.pluginClass = getMetaDataString(metaData, "qqgame.plugin.class");
        pluginInfo.launchFragment = getMetaDataString(metaData, "qqgame.plugin.launch.fragment");
        pluginInfo.bootCompleteReceiver = getMetaDataString(metaData, "qqgame.plugin.boot.receiver");
        pluginInfo.surviveDetector = getMetaDataString(metaData, "qqgame.plugin.survive.detector");
        pluginInfo.exclusive = toBooleanPrimitive(getMetaDataString(metaData, "qqgame.plugin.exclusive"), false);
        pluginInfo.corePlugin = toBooleanPrimitive(getMetaDataString(metaData, "qqgame.plugin.corePlugin"), false);
        pluginInfo.pluginRequirement = new PluginInfo.PluginRequirement();
        pluginInfo.pluginRequirement.requirementInfos = metaData.getParcelableArrayList("requires");
        int minBasePlatformVersion = toIntegerPrimitive(getMetaDataString(metaData, "qqgame.plugin.minBasePlatformVersion"), 0);
        int maxBasePlatformVersion = toIntegerPrimitive(getMetaDataString(metaData, "qqgame.plugin.maxBasePlatformVersion"), 0);
        int minPlatformVersion = toIntegerPrimitive(getMetaDataString(metaData, "qqgame.plugin.minPlatformVersion"), 0);
        int maxPlatformVersion = toIntegerPrimitive(getMetaDataString(metaData, "qqgame.plugin.maxPlatformVersion"), 0);
        if (platformServerContext.getPlatformConfig().enbaleCorePlugin) {
            if (minBasePlatformVersion != 0) {
                pluginInfo.minBasePlatformVersion = minBasePlatformVersion;
            } else if (pluginInfo.corePlugin) {
                pluginInfo.minBasePlatformVersion = minPlatformVersion;
            } else {
                pluginInfo.minBasePlatformVersion = 0;
            }
            if (minPlatformVersion > 0 && !pluginInfo.corePlugin && (pluginInfo.pluginRequirement.requirementInfos == null || pluginInfo.pluginRequirement.requirementInfos.isEmpty())) {
                pluginInfo.pluginRequirement.minCorePluginVersion = minPlatformVersion;
            }
            if (maxBasePlatformVersion != 0) {
                pluginInfo.maxBasePlatformVersion = maxBasePlatformVersion;
            } else if (pluginInfo.corePlugin) {
                pluginInfo.maxBasePlatformVersion = maxPlatformVersion;
            } else {
                pluginInfo.maxBasePlatformVersion = 0;
            }
            if (maxPlatformVersion > 0 && !pluginInfo.corePlugin && (pluginInfo.pluginRequirement.requirementInfos == null || pluginInfo.pluginRequirement.requirementInfos.isEmpty())) {
                pluginInfo.pluginRequirement.maxCorePluginVersion = maxPlatformVersion;
            }
        } else {
            if (minBasePlatformVersion == 0) {
                pluginInfo.minBasePlatformVersion = minPlatformVersion;
            } else {
                pluginInfo.minBasePlatformVersion = minBasePlatformVersion;
            }
            if (maxBasePlatformVersion == 0) {
                pluginInfo.maxBasePlatformVersion = maxPlatformVersion;
            } else {
                pluginInfo.maxBasePlatformVersion = maxBasePlatformVersion;
            }
        }
        pluginInfo.minAndroidVersion = toIntegerPrimitive(getMetaDataString(metaData, "qqgame.plugin.minAndroidVersion"), 0);
        pluginInfo.maxAndroidVersion = toIntegerPrimitive(getMetaDataString(metaData, "qqgame.plugin.maxAndroidVersion"), 0);
        pluginInfo.pluginId = applicationInfo.packageName;
        pluginInfo.version = packageInfo.versionCode;
        pluginInfo.versionName = packageInfo.versionName;
        pluginInfo.pluginName = getName(context, applicationInfo, pluginPath);
        pluginInfo.pluginIcon = applicationInfo.icon;
        pluginInfo.theme = applicationInfo.theme;
        pluginInfo.signatures = packageInfo.signatures;
        Integer singleTop = (Integer) getMetaDataOptions(PluginConstant.SINGLE_TOP_CONFIG, getMetaDataString(metaData, "qqgame.plugin.extra.singleTop"), 0);
        pluginInfo.extraInfo.singleTop = singleTop != null ? singleTop.intValue() : 0;
        pluginInfo.extraInfo.singleProcess = toBooleanPrimitive(getMetaDataString(metaData, "qqgame.plugin.extra.singleProcess"), false);
        pluginInfo.extraInfo.autoLoad = toBooleanPrimitive(getMetaDataString(metaData, "qqgame.plugin.extra.autoLoad"), false);
        pluginInfo.installPath = pluginPath;
        pluginInfo.nativeLibraryDir = PluginConstant.getPluginNativeLibDir(context, pluginInfo);
        pluginInfo.dexOptimizeDir = PluginConstant.getPluginDexOptimizeDir(context);
        if (!isParsedPluginValid(pluginInfo)) {
            return null;
        }
        return pluginInfo;
    }

    private static String getName(Context context, ApplicationInfo appInfo, String pluginPath) {
        try {
            String pluginName = ApkUtil.getResources(context, pluginPath).getString(appInfo.labelRes);
            if (!TextUtils.isEmpty(pluginName)) {
                return pluginName;
            }
            CharSequence lable = context.getPackageManager().getApplicationLabel(appInfo);
            if (lable != null) {
                return lable.toString();
            }
            return null;
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
        }
    }

    private static boolean isParsedPluginValid(PluginInfo pluginInfo) {
        return pluginInfo != null && !TextUtils.isEmpty(pluginInfo.installPath) && !TextUtils.isEmpty(pluginInfo.pluginClass) && !TextUtils.isEmpty(pluginInfo.pluginId);
    }

    public static PackageInfo generatePackageInfo(Context context, String pluginPath, int flags) {
        if (TextUtils.isEmpty(pluginPath)) {
            return null;
        }
        if ((flags & 1) != 0) {
        }
        PackageInfo packageInfo = ApkUtil.getPackageInfo(context, pluginPath, 0);
        if (packageInfo == null || (flags & 1) == 0) {
            return packageInfo;
        }
        packageInfo.signatures = ApkUtil.Certificates.collectCertificates(pluginPath, true);
        return packageInfo;
    }

    private static Bundle parseMetaData(Context context, String pluginPath) {
        if (TextUtils.isEmpty(pluginPath)) {
            return null;
        }
        Resources resources = ApkUtil.getResources(context, pluginPath);
        if (resources == null) {
            LogUtil.e(TAG, "parse meta data failed,resources is empty.");
            return null;
        }
        try {
            MetaDataHandler contentHandler = new MetaDataHandler();
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            xmlReader.setContentHandler(contentHandler);
            xmlReader.parse(new InputSource(resources.getAssets().open("plugin.xml")));
            return contentHandler.getMetaData();
        } catch (Throwable e) {
            LogUtil.e(TAG, "fail to parse meta-data for " + pluginPath, e);
            return null;
        }
    }

    private static <K, V> V getMetaDataOptions(Map<K, V> config, K key, V defaultValue) {
        if (config == null || key == null) {
            return defaultValue;
        }
        V value = config.get(key);
        return value == null ? defaultValue : value;
    }

    private static String getMetaDataString(Bundle bundle, String key) {
        if (bundle == null || TextUtils.isEmpty(key)) {
            return null;
        }
        return bundle.getString(key);
    }

    private static String[] split(String str, String regularExpression) {
        if (str == null || regularExpression == null) {
            return null;
        }
        return str.split(regularExpression);
    }

    /* access modifiers changed from: private */
    public static int toIntegerPrimitive(String str, int defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private static boolean toBooleanPrimitive(String str, boolean defaultValue) {
        if (!isEmpty(str)) {
            return Boolean.parseBoolean(str);
        }
        return defaultValue;
    }

    private static Boolean toBoolean(String str, Boolean defaultValue) {
        if (!isEmpty(str)) {
            return Boolean.valueOf(str);
        }
        return defaultValue;
    }

    private static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    static final class MetaDataHandler extends DefaultHandler {
        private static final String ATTRIBUTE_NAME = "name";
        private static final String ATTRIBUTE_VALUE = "value";
        private static final String TAG_ITEM = "item";
        private static final String TAG_PLUGIN = "plugin";
        private boolean mInDomain = false;
        private final Bundle mMetaData = new Bundle();
        public ArrayList<PluginInfo.RequirementInfo> requirementInfos;

        MetaDataHandler() {
        }

        public Bundle getMetaData() {
            return this.mMetaData;
        }

        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (TAG_PLUGIN.equalsIgnoreCase(localName)) {
                this.mInDomain = true;
            }
            if (this.mInDomain) {
                if (!TAG_ITEM.equalsIgnoreCase(localName) && !"requires".equalsIgnoreCase(localName) && !"requireInfo".equalsIgnoreCase(localName)) {
                    return;
                }
                if (TAG_ITEM.equalsIgnoreCase(localName)) {
                    String name = attributes.getValue("name");
                    String value = attributes.getValue("value");
                    if (!isEmpty(name)) {
                        this.mMetaData.putString(name, value);
                    }
                } else if ("requires".equalsIgnoreCase(localName)) {
                    if (this.requirementInfos == null) {
                        this.requirementInfos = new ArrayList<>();
                    }
                } else if ("requireInfo".equalsIgnoreCase(localName)) {
                    String id = attributes.getValue("id");
                    if (!isEmpty(id)) {
                        PluginInfo.RequirementInfo requirementInfo = new PluginInfo.RequirementInfo();
                        requirementInfo.id = id;
                        requirementInfo.minVersion = PluginParser.toIntegerPrimitive(attributes.getValue("minVersion"), 0);
                        requirementInfo.maxVersion = PluginParser.toIntegerPrimitive(attributes.getValue("maxVersion"), 0);
                        this.requirementInfos.add(requirementInfo);
                    }
                }
            }
        }

        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (TAG_PLUGIN.equalsIgnoreCase(localName)) {
                this.mInDomain = false;
            }
            if ("requires".equalsIgnoreCase(localName)) {
                this.mMetaData.putParcelableArrayList("requires", this.requirementInfos);
            }
        }

        private static boolean isEmpty(String str) {
            return str == null || str.length() == 0;
        }
    }
}

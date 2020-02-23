package com.facebook.internal;

import android.net.Uri;
import java.util.EnumSet;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public final class FetchedAppSettings {
    private boolean IAPAutomaticLoggingEnabled;
    private boolean automaticLoggingEnabled;
    private boolean codelessEventsEnabled;
    private boolean customTabsEnabled;
    private Map<String, Map<String, DialogFeatureConfig>> dialogConfigMap;
    private FacebookRequestErrorClassification errorClassification;
    private JSONArray eventBindings;
    private String nuxContent;
    private boolean nuxEnabled;
    private String sdkUpdateMessage;
    private int sessionTimeoutInSeconds;
    private String smartLoginBookmarkIconURL;
    private String smartLoginMenuIconURL;
    private EnumSet<SmartLoginOption> smartLoginOptions;
    private boolean supportsImplicitLogging;

    public FetchedAppSettings(boolean supportsImplicitLogging2, String nuxContent2, boolean nuxEnabled2, boolean customTabsEnabled2, int sessionTimeoutInSeconds2, EnumSet<SmartLoginOption> smartLoginOptions2, Map<String, Map<String, DialogFeatureConfig>> dialogConfigMap2, boolean automaticLoggingEnabled2, FacebookRequestErrorClassification errorClassification2, String smartLoginBookmarkIconURL2, String smartLoginMenuIconURL2, boolean IAPAutomaticLoggingEnabled2, boolean codelessEventsEnabled2, JSONArray eventBindings2, String sdkUpdateMessage2) {
        this.supportsImplicitLogging = supportsImplicitLogging2;
        this.nuxContent = nuxContent2;
        this.nuxEnabled = nuxEnabled2;
        this.customTabsEnabled = customTabsEnabled2;
        this.dialogConfigMap = dialogConfigMap2;
        this.errorClassification = errorClassification2;
        this.sessionTimeoutInSeconds = sessionTimeoutInSeconds2;
        this.automaticLoggingEnabled = automaticLoggingEnabled2;
        this.smartLoginOptions = smartLoginOptions2;
        this.smartLoginBookmarkIconURL = smartLoginBookmarkIconURL2;
        this.smartLoginMenuIconURL = smartLoginMenuIconURL2;
        this.IAPAutomaticLoggingEnabled = IAPAutomaticLoggingEnabled2;
        this.codelessEventsEnabled = codelessEventsEnabled2;
        this.eventBindings = eventBindings2;
        this.sdkUpdateMessage = sdkUpdateMessage2;
    }

    public boolean supportsImplicitLogging() {
        return this.supportsImplicitLogging;
    }

    public String getNuxContent() {
        return this.nuxContent;
    }

    public boolean getNuxEnabled() {
        return this.nuxEnabled;
    }

    public boolean getCustomTabsEnabled() {
        return this.customTabsEnabled;
    }

    public int getSessionTimeoutInSeconds() {
        return this.sessionTimeoutInSeconds;
    }

    public boolean getAutomaticLoggingEnabled() {
        return this.automaticLoggingEnabled;
    }

    public EnumSet<SmartLoginOption> getSmartLoginOptions() {
        return this.smartLoginOptions;
    }

    public Map<String, Map<String, DialogFeatureConfig>> getDialogConfigurations() {
        return this.dialogConfigMap;
    }

    public FacebookRequestErrorClassification getErrorClassification() {
        return this.errorClassification;
    }

    public String getSmartLoginBookmarkIconURL() {
        return this.smartLoginBookmarkIconURL;
    }

    public String getSmartLoginMenuIconURL() {
        return this.smartLoginMenuIconURL;
    }

    public boolean getIAPAutomaticLoggingEnabled() {
        return this.IAPAutomaticLoggingEnabled;
    }

    public boolean getCodelessEventsEnabled() {
        return this.codelessEventsEnabled;
    }

    public JSONArray getEventBindings() {
        return this.eventBindings;
    }

    public String getSdkUpdateMessage() {
        return this.sdkUpdateMessage;
    }

    public static class DialogFeatureConfig {
        private static final String DIALOG_CONFIG_DIALOG_NAME_FEATURE_NAME_SEPARATOR = "\\|";
        private static final String DIALOG_CONFIG_NAME_KEY = "name";
        private static final String DIALOG_CONFIG_URL_KEY = "url";
        private static final String DIALOG_CONFIG_VERSIONS_KEY = "versions";
        private String dialogName;
        private Uri fallbackUrl;
        private String featureName;
        private int[] featureVersionSpec;

        public static DialogFeatureConfig parseDialogConfig(JSONObject dialogConfigJSON) {
            String dialogNameWithFeature = dialogConfigJSON.optString("name");
            if (Utility.isNullOrEmpty(dialogNameWithFeature)) {
                return null;
            }
            String[] components = dialogNameWithFeature.split(DIALOG_CONFIG_DIALOG_NAME_FEATURE_NAME_SEPARATOR);
            if (components.length != 2) {
                return null;
            }
            String dialogName2 = components[0];
            String featureName2 = components[1];
            if (Utility.isNullOrEmpty(dialogName2) || Utility.isNullOrEmpty(featureName2)) {
                return null;
            }
            String urlString = dialogConfigJSON.optString("url");
            Uri fallbackUri = null;
            if (!Utility.isNullOrEmpty(urlString)) {
                fallbackUri = Uri.parse(urlString);
            }
            return new DialogFeatureConfig(dialogName2, featureName2, fallbackUri, parseVersionSpec(dialogConfigJSON.optJSONArray(DIALOG_CONFIG_VERSIONS_KEY)));
        }

        private static int[] parseVersionSpec(JSONArray versionsJSON) {
            int[] versionSpec = null;
            if (versionsJSON != null) {
                int numVersions = versionsJSON.length();
                versionSpec = new int[numVersions];
                for (int i = 0; i < numVersions; i++) {
                    int version = versionsJSON.optInt(i, -1);
                    if (version == -1) {
                        String versionString = versionsJSON.optString(i);
                        if (!Utility.isNullOrEmpty(versionString)) {
                            try {
                                version = Integer.parseInt(versionString);
                            } catch (NumberFormatException nfe) {
                                Utility.logd("FacebookSDK", (Exception) nfe);
                                version = -1;
                            }
                        }
                    }
                    versionSpec[i] = version;
                }
            }
            return versionSpec;
        }

        private DialogFeatureConfig(String dialogName2, String featureName2, Uri fallbackUrl2, int[] featureVersionSpec2) {
            this.dialogName = dialogName2;
            this.featureName = featureName2;
            this.fallbackUrl = fallbackUrl2;
            this.featureVersionSpec = featureVersionSpec2;
        }

        public String getDialogName() {
            return this.dialogName;
        }

        public String getFeatureName() {
            return this.featureName;
        }

        public Uri getFallbackUrl() {
            return this.fallbackUrl;
        }

        public int[] getVersionSpec() {
            return this.featureVersionSpec;
        }
    }

    public static DialogFeatureConfig getDialogFeatureConfig(String applicationId, String actionName, String featureName) {
        FetchedAppSettings settings;
        Map<String, DialogFeatureConfig> featureMap;
        if (Utility.isNullOrEmpty(actionName) || Utility.isNullOrEmpty(featureName) || (settings = FetchedAppSettingsManager.getAppSettingsWithoutQuery(applicationId)) == null || (featureMap = settings.getDialogConfigurations().get(actionName)) == null) {
            return null;
        }
        return featureMap.get(featureName);
    }
}

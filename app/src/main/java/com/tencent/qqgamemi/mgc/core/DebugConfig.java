package com.tencent.qqgamemi.mgc.core;

import android.annotation.TargetApi;
import android.content.Context;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.imsdk.framework.request.HttpRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class DebugConfig {
    private static final String DEBUG_CONFIG_FILE_NAME = "debug.cfg";
    public static final String KEY_IS_DEBUG_ENV = "is_debug_env";
    public static final String MGC_CONFIG_9527_FILE = "mgc_config9527";
    private static final String TAG = "DebugConfig";
    private volatile boolean isInit;
    private Properties mAssetProperties;
    private Context mContext;
    private boolean mIsAssetPropertieEnabled;
    private Properties mSDProperties;

    public DebugConfig(Context context) {
        this.mContext = context;
    }

    private void init() {
        loadAssetProperties();
        loadSDProperties();
    }

    @TargetApi(9)
    private void loadAssetProperties() {
        this.mIsAssetPropertieEnabled = this.mContext.getSharedPreferences(MGC_CONFIG_9527_FILE, 0).getBoolean(KEY_IS_DEBUG_ENV, false);
        this.mAssetProperties = new Properties();
        LogUtil.i(TAG, "release version disabled debug config");
    }

    @TargetApi(9)
    private void loadSDProperties() {
        this.mSDProperties = new Properties();
        File dir = MGCEnvironment.getConfigExternalStorageDirectory(this.mContext);
        if (dir != null) {
            File file = new File(dir, DEBUG_CONFIG_FILE_NAME);
            if (file.exists()) {
                try {
                    this.mSDProperties.load(new InputStreamReader(new FileInputStream(file), "UTF-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void ensureInit() {
        if (!this.isInit) {
            synchronized (this) {
                if (!this.isInit) {
                    init();
                    this.isInit = true;
                }
            }
        }
    }

    public String getProperty(String key) {
        return getProperty(key, (String) null);
    }

    public String getProperty(String key, String defaultValue) {
        ensureInit();
        String property = this.mSDProperties.getProperty(key);
        if (property == null && this.mIsAssetPropertieEnabled) {
            property = this.mAssetProperties.getProperty(key);
            LogUtil.i(TAG, "try read from assets debug config: [" + key + HttpRequest.HTTP_REQ_ENTITY_MERGE + property + "]");
        }
        return property == null ? defaultValue : property;
    }
}

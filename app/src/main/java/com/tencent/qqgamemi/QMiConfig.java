package com.tencent.qqgamemi;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.qqgamemi.util.StringUtils;
import java.io.InputStreamReader;
import java.util.Properties;

public class QMiConfig {
    private static final int DEFAULTCLIENTTYPE = 43;
    private static final QMiConfig instance = new QMiConfig();
    private int COCOS_TYPE = 2;
    private final String RECORD_CONFIG_FILE_NAME = "record_config.cfg";
    private String TAG = "QMiConfig";
    private int UNITY_TYPE = 1;
    private int UNREAL_ENGINE_TYPE = 3;
    private final String accessHostAddr = "Access_Host_Addr";
    private final String accessHostPorts = "Access_Host_Ports";
    private final String clientType = "ClientType";
    private final String engineType = "EngineType";
    private volatile boolean isInit;
    private final String jugement = "Jugement";
    private String jugementTile;
    private Properties mAssetProperties;
    private Context mContext;
    private final String manual = "Manual";
    private String manualTitle;
    private String momentTitle;
    private final String moments = "Moments";

    public static QMiConfig getInstance() {
        return instance;
    }

    public void init(Context context) {
        if (context != null) {
            if (this.mContext == null) {
                this.mContext = context;
            }
            if (!this.isInit) {
                synchronized (this) {
                    if (!this.isInit) {
                        loadAssetProperties(context.getApplicationContext());
                        this.isInit = true;
                    }
                }
            }
        }
    }

    private void loadAssetProperties(Context mContext2) {
        this.mAssetProperties = new Properties();
        try {
            this.mAssetProperties.load(new InputStreamReader(mContext2.getAssets().open("record_config.cfg"), "UTF-8"));
        } catch (Exception e) {
            LogUtil.e(this.TAG, "load asset properties failed: " + e);
        }
    }

    public String getProperty(Context context, String key) {
        if (context == null) {
            return null;
        }
        init(context);
        return getProperty(key, (String) null);
    }

    public String getProperty(String key, String defaultValue) {
        if (this.mAssetProperties == null) {
            return null;
        }
        String property = this.mAssetProperties.getProperty(key);
        if (property == null) {
            property = this.mAssetProperties.getProperty(key);
            LogUtil.i(this.TAG, "try read from assets debug config: [" + key + HttpRequest.HTTP_REQ_ENTITY_MERGE + property + "]");
        }
        return property != null ? property : defaultValue;
    }

    public String getManualTitle(Context context) {
        if (this.manualTitle == null) {
            this.manualTitle = getProperty(context, "Manual");
        }
        if (TextUtils.isEmpty(this.manualTitle)) {
            this.manualTitle = "";
        }
        return this.manualTitle;
    }

    public String getMomentsTile(Context context) {
        if (this.momentTitle == null) {
            this.momentTitle = getProperty(context, "Moments");
        }
        if (TextUtils.isEmpty(this.momentTitle)) {
            this.momentTitle = "";
        }
        return this.momentTitle;
    }

    public String getJugementTitle(Context context) {
        if (this.jugementTile == null) {
            this.jugementTile = getProperty(context, "Jugement");
        }
        if (TextUtils.isEmpty(this.jugementTile)) {
            this.jugementTile = "";
        }
        return this.jugementTile;
    }

    public int getClientType(Context context) {
        String clientTypeStr = getProperty(context, "ClientType");
        if (clientTypeStr != null) {
            return Integer.valueOf(clientTypeStr).intValue();
        }
        return 43;
    }

    public int getEnginType(Context context) {
        return ((Integer) StringUtils.transStringToSafe(getProperty(context, "EngineType"), 0)).intValue();
    }

    public boolean isUnity(Context context) {
        return getEnginType(context) == this.UNITY_TYPE;
    }

    public boolean isUnrealEngine(Context context) {
        return getEnginType(context) == this.UNREAL_ENGINE_TYPE;
    }

    public boolean isCocos2d(Context context) {
        return getEnginType(context) == this.COCOS_TYPE;
    }

    public String getAccessHostAddr(Context context) {
        String accessHostAddStr = getProperty(context, "Access_Host_Addr");
        if (accessHostAddStr != null) {
            return accessHostAddStr;
        }
        return null;
    }

    public int[] getAccessHostPorts(Context context) {
        return StringUtils.transStringToIntArray(getProperty(context, "Access_Host_Ports"));
    }
}

package com.beetalk.sdk.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Build;
import android.text.TextUtils;
import android.view.WindowManager;
import bolts.Task;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.CacheHelper;
import com.beetalk.sdk.helper.Helper;
import com.beetalk.sdk.helper.Validate;
import com.beetalk.sdk.networking.SimpleNetworkClient;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.garena.overlay.FloatDotViewUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import org.json.JSONObject;

public class GGAppConfig {
    private static final int CLIENT_ANDROID_GARENA = 4353;
    private static final int CLIENT_ANDROID_GOOGLE_PLAY = 4352;
    private static final int CLIENT_ANDROID_INTERNAL = 4354;
    public static final String CLIENT_LOG = "client_log";
    private static final String DEFAULT_KEY = "com.garena.msdk.app_config";
    public static final String OVERLAY_CONFIG = "overlay_button_config";
    public static final String OVERLAY_CONFIG_URL = "overlay_config_url";
    private static final String VIDEO_SUPPORT = "video_support";
    private final SharedPreferences cache = this.mContext.getSharedPreferences(DEFAULT_KEY, 0);
    /* access modifiers changed from: private */
    public final Context mContext;

    public GGAppConfig(Context context) {
        Validate.notNull(context, "Application Context");
        this.mContext = context.getApplicationContext();
    }

    public void init() {
        BBLogger.d("Initializing Download of Application Config", new Object[0]);
        Task.callInBackground(new Callable<Void>() {
            public Void call() throws Exception {
                int client_type;
                JSONObject config;
                String appId = Helper.getMetaDataAppId(GGAppConfig.this.mContext);
                if (!TextUtils.isEmpty(appId)) {
                    if (Helper.getMetaDataSourceId(GGAppConfig.this.mContext).intValue() == SDKConstants.CHANNEL_SOURCE.GOOGLE_PLAY.intValue()) {
                        client_type = GGAppConfig.CLIENT_ANDROID_GOOGLE_PLAY;
                    } else if (SDKConstants.getEnvironment() == SDKConstants.GGEnvironment.PRODUCTION) {
                        client_type = GGAppConfig.CLIENT_ANDROID_GARENA;
                    } else {
                        client_type = GGAppConfig.CLIENT_ANDROID_INTERNAL;
                    }
                    Map<String, String> data = new HashMap<>();
                    data.put("app_id", appId);
                    data.put(GGLiveConstants.PARAM.CLIENT_TYPE, String.valueOf(client_type));
                    data.put("client_version", String.valueOf(Helper.getPackageVersionCode(GGAppConfig.this.mContext, GGAppConfig.this.mContext.getPackageName())));
                    JSONObject result = SimpleNetworkClient.getInstance().makeGetRequest(SDKConstants.getAppConfigURL(), data);
                    if (result != null) {
                        BBLogger.d("AppConfig:" + result.toString(), new Object[0]);
                        if (result.has("error")) {
                            BBLogger.e("AppConfig Error:" + result.optString("error"), new Object[0]);
                        } else {
                            if (result.has(GGAppConfig.CLIENT_LOG) && SDKConstants.getEnvironment().equals(SDKConstants.GGEnvironment.PRODUCTION)) {
                                GGAppConfig.this.save(GGAppConfig.CLIENT_LOG, Boolean.valueOf(result.getBoolean(GGAppConfig.CLIENT_LOG)));
                            } else if (!SDKConstants.RELEASE_VERSION) {
                                BBLogger.d("Debug Build Skipping config value", new Object[0]);
                                GGAppConfig.this.save(GGAppConfig.CLIENT_LOG, false);
                            } else {
                                GGAppConfig.this.save(GGAppConfig.CLIENT_LOG, true);
                            }
                            if (result.has(GGAppConfig.OVERLAY_CONFIG_URL)) {
                                String overLayConfig = result.optString(GGAppConfig.OVERLAY_CONFIG_URL);
                                GGAppConfig.this.save(GGAppConfig.OVERLAY_CONFIG_URL, overLayConfig);
                                CacheHelper cacheHelper = CacheHelper.get(GGAppConfig.this.mContext, GGAppConfig.OVERLAY_CONFIG);
                                if (cacheHelper.getAsJSONObject(GGAppConfig.OVERLAY_CONFIG) == null && (config = SimpleNetworkClient.getInstance().makeGetRequest(overLayConfig, new HashMap())) != null) {
                                    BBLogger.i("OverLay Config:" + config.toString(), new Object[0]);
                                    cacheHelper.put(GGAppConfig.OVERLAY_CONFIG, config, 3600);
                                }
                            }
                        }
                    }
                    if (GGAppConfig.this.getVideoCapability() == 0) {
                        GGAppConfig.this.checkVideoCapability();
                    }
                }
                return null;
            }
        });
    }

    /* access modifiers changed from: private */
    public void save(String key, Object value) {
        SharedPreferences.Editor editor = this.cache.edit();
        if (value instanceof Boolean) {
            editor.putBoolean(key, ((Boolean) value).booleanValue());
        } else if (value instanceof Integer) {
            editor.putInt(key, ((Integer) value).intValue());
        } else if (value instanceof Float) {
            editor.putFloat(key, ((Float) value).floatValue());
        } else if (value instanceof Long) {
            editor.putLong(key, ((Long) value).longValue());
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        }
        editor.apply();
    }

    public Boolean getAppLogConfig() {
        return Boolean.valueOf(this.cache.getBoolean(CLIENT_LOG, false));
    }

    public String getOverlayConfigUrl() {
        return this.cache.getString(OVERLAY_CONFIG_URL, "");
    }

    public int getVideoCapability() {
        return this.cache.getInt(VIDEO_SUPPORT, 0);
    }

    /* access modifiers changed from: private */
    public void checkVideoCapability() {
        int i = -1;
        if (Build.VERSION.SDK_INT < 21) {
            save(VIDEO_SUPPORT, -1);
            return;
        }
        Point size = new Point();
        ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getSize(size);
        if (FloatDotViewUtil.isSupportVideo(size.x, size.y)) {
            i = 1;
        }
        save(VIDEO_SUPPORT, Integer.valueOf(i));
    }
}

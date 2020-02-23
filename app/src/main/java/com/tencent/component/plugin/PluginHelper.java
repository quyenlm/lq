package com.tencent.component.plugin;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import com.google.android.gms.drive.DriveFile;
import com.tencent.component.annotation.PluginApi;
import com.tencent.component.plugin.PluginManager;
import com.tencent.component.plugin.server.PluginConstant;
import com.tencent.component.plugin.service.LeafServiceManager;
import java.util.concurrent.ConcurrentHashMap;

@PluginApi(since = 4)
public class PluginHelper {
    private static final String TAG = "PluginHelper";
    private static ConcurrentHashMap<String, PluginHelper> sInstanceMap = new ConcurrentHashMap<>();
    private String mPlatformId;
    private PluginManager mPluginManager;

    @PluginApi(since = 4)
    public static class VERSION_CODES {
        @PluginApi(since = 4)
        public static final int EUTERPE_1_0 = 3;
        @PluginApi(since = 4)
        public static final int EUTERPE_1_1 = 4;
        @PluginApi(since = 5)
        public static final int EUTERPE_1_2 = 5;
        @PluginApi(since = 6)
        public static final int EUTERPE_1_3 = 6;
        @PluginApi(since = 7)
        public static final int EUTERPE_2_0 = 7;
        @PluginApi(since = 8)
        public static final int EUTERPE_2_0_1 = 8;
        @PluginApi(since = 9)
        public static final int EUTERPE_2_1 = 9;
        @PluginApi(since = 10)
        public static final int EUTERPE_2_1_1 = 10;
        @PluginApi(since = 11)
        public static final int EUTERPE_2_1_2 = 11;
        @PluginApi(since = 12)
        public static final int EUTERPE_2_1_3 = 12;
        @PluginApi(since = 13)
        public static final int EUTERPE_2_1_4 = 13;
        @PluginApi(since = 14)
        public static final int EUTERPE_2_1_5 = 14;
        @PluginApi(since = 15)
        public static final int EUTERPE_2_1_6 = 15;
        @PluginApi(since = 16)
        public static final int EUTERPE_2_1_7 = 16;
        @PluginApi(since = 100)
        public static final int EUTERPE_2_2 = 100;
        @PluginApi(since = 150)
        public static final int EUTERPE_2_2_1 = 150;
        @PluginApi(since = 200)
        public static final int EUTERPE_2_3 = 200;
        @PluginApi(since = 300)
        public static final int EUTERPE_2_4 = 300;
        @PluginApi(since = 310)
        public static final int EUTERPE_2_4_1 = 310;
        @PluginApi(since = 350)
        public static final int EUTERPE_2_4_2 = 350;
        @PluginApi(since = 400)
        public static final int EUTERPE_2_5 = 400;
        @PluginApi(since = 401)
        public static final int EUTERPE_2_5_1 = 401;
        @PluginApi(since = 450)
        public static final int EUTERPE_2_5_2 = 450;
        @PluginApi(since = 455)
        public static final int EUTERPE_2_5_2_1 = 455;
        @PluginApi(since = 500)
        public static final int EUTERPE_2_6 = 500;
        @PluginApi(since = 501)
        public static final int EUTERPE_2_6_1 = 501;
        @PluginApi(since = 502)
        public static final int EUTERPE_2_6_2 = 502;
        @PluginApi(since = 600)
        public static final int EUTERPE_2_7 = 600;
    }

    private PluginHelper(String platformId, PluginManager pluginManager) {
        this.mPlatformId = platformId;
        this.mPluginManager = pluginManager;
    }

    public static PluginHelper getInstance(String platformId, PluginManager pluginManager) {
        PluginHelper pluginHelper = sInstanceMap.get(platformId);
        if (pluginHelper == null) {
            synchronized (PluginHelper.class) {
                pluginHelper = sInstanceMap.get(platformId);
                if (pluginHelper == null) {
                    pluginHelper = new PluginHelper(platformId, pluginManager);
                }
                sInstanceMap.put(platformId, pluginHelper);
            }
        }
        return pluginHelper;
    }

    public Intent generateIntent(Context startContext, Plugin plugin, Intent args) {
        return generateIntent(startContext, plugin, (String) null, args);
    }

    public Intent generateIntent(Context startContext, Plugin plugin, String pluginFragment, Intent args) {
        return generateIntent(startContext, plugin.getPluginInfo(), pluginFragment, args);
    }

    /* access modifiers changed from: package-private */
    public Intent generateIntent(Context startContext, PluginInfo pluginInfo, String pluginFragment, Intent args) {
        return generateIntent(startContext, pluginInfo, pluginFragment, args, false);
    }

    /* access modifiers changed from: package-private */
    public Intent generateInnerIntent(Context startContext, PluginInfo pluginInfo, String pluginFragment, Intent args) {
        return generateIntent(startContext, pluginInfo, pluginFragment, args, true);
    }

    private Intent generateIntent(Context startContext, PluginInfo pluginInfo, String pluginFragment, Intent args, boolean innerUsage) {
        if (pluginInfo == null || startContext == null) {
            return null;
        }
        if (TextUtils.isEmpty(pluginInfo.installPath)) {
            return this.mPluginManager.handlePluginUri(pluginInfo.pluginId, pluginInfo.uri);
        }
        Intent intent = new Intent();
        if (!TextUtils.isEmpty(pluginFragment)) {
            intent.putExtra(PluginConstant.INTENT_PLUGIN_FRAGMENT, pluginFragment);
        }
        if (args != null) {
            intent.putExtra(PluginConstant.INTENT_PLUGIN_ARGS, args.getExtras());
            intent.addFlags(args.getFlags());
            intent.setFlags(intent.getFlags() & -67108865);
            if (pluginInfo.extraInfo.singleTop == 0) {
                intent.setFlags(intent.getFlags() & -536870913);
            }
        }
        if (startContext == startContext.getApplicationContext() || !(startContext instanceof Activity)) {
            intent.addFlags(DriveFile.MODE_READ_ONLY);
        }
        intent.setClass(startContext, this.mPluginManager.pluginPlatformConfig.pluginShellActivityClass);
        intent.putExtra(PluginConstant.INTENT_PLUGIN, pluginInfo.pluginId);
        intent.putExtra(PluginConstant.INTENT_PLUGIN_PLATFORM_ID, this.mPlatformId);
        if (!innerUsage) {
            return intent;
        }
        intent.putExtra(PluginConstant.INTENT_PLUGIN_INNER, pluginInfo);
        return intent;
    }

    @PluginApi(since = 4)
    public void startActivity(Context context, Plugin plugin, Intent intent) {
        if (context == null) {
            throw new IllegalArgumentException("Context can't be null.");
        } else if (plugin == null) {
            throw new IllegalArgumentException("plugin can't be null.");
        } else {
            PluginInfo pluginInfo = plugin.getPluginInfo();
            if (pluginInfo == null || TextUtils.isEmpty(pluginInfo.pluginId)) {
                throw new IllegalArgumentException("pluginInfo or pluginId is empty.");
            }
            String pluginId = plugin.getPluginInfo().pluginId;
            Class<?> clazz = findIntentClass(intent, plugin.getClass().getClassLoader());
            if (clazz == null || !PluginFragment.class.isAssignableFrom(clazz)) {
                if (context == context.getApplicationContext() || !(context instanceof Activity)) {
                    intent.addFlags(DriveFile.MODE_READ_ONLY);
                }
                context.startActivity(intent);
                return;
            }
            startActivity(context, pluginId, (Class<? extends PluginFragment>) clazz, intent);
        }
    }

    @PluginApi(since = 4)
    public void startActivity(Context context, String pluginId, Class<? extends PluginFragment> fragmentClass, Intent args) {
        startActivity(context, pluginId, fragmentClass != null ? fragmentClass.getName() : null, args);
    }

    @PluginApi(since = 4)
    public void startActivity(Context context, String pluginId, String fragmentClassName, Intent args) {
        if (TextUtils.isEmpty(pluginId)) {
            throw new IllegalArgumentException("pluginId can't be empty");
        } else if (context == null) {
            throw new IllegalArgumentException("Context can't be null");
        } else {
            final String str = pluginId;
            final Context context2 = context;
            final String str2 = fragmentClassName;
            final Intent intent = args;
            this.mPluginManager.getPluginInfo(pluginId, new PluginManager.GetPluginInfoCallback() {
                public void onGetPluginInfo(PluginInfo pluginInfo) {
                    if (pluginInfo == null) {
                        throw new IllegalStateException("Plugin(pluginId:" + str + ") not prepared");
                    }
                    Intent intent = PluginHelper.this.generateInnerIntent(context2, pluginInfo, str2, intent);
                    if (intent != null) {
                        context2.startActivity(intent);
                    }
                }
            });
        }
    }

    @PluginApi(since = 310)
    public void bindLeafService(Context context, String pluginId, String leafServiceName, Bundle args, ServiceConnection sc, int flags) {
        if (TextUtils.isEmpty(pluginId)) {
            throw new IllegalArgumentException("pluginId can't be empty");
        }
        final Context context2 = context;
        final String str = leafServiceName;
        final Bundle bundle = args;
        final ServiceConnection serviceConnection = sc;
        final int i = flags;
        this.mPluginManager.getPluginInfo(pluginId, new PluginManager.GetPluginInfoCallback() {
            public void onGetPluginInfo(PluginInfo pluginInfo) {
                if (pluginInfo != null) {
                    PluginHelper.this.bindLeafService(context2, pluginInfo, str, bundle, serviceConnection, i);
                }
            }
        });
    }

    @PluginApi(since = 310)
    public void bindLeafService(Context context, PluginInfo pluginInfo, String leafServiceName, Bundle args, ServiceConnection sc, int flags) {
        if (pluginInfo == null) {
            throw new IllegalArgumentException("pluginInfo can't be empty");
        } else if (context == null) {
            throw new IllegalArgumentException("Context can't be null");
        } else if (TextUtils.isEmpty(leafServiceName)) {
            throw new IllegalArgumentException("leafServiceName can't be null");
        } else {
            LeafServiceManager.getInstance(context, this.mPlatformId).bindService(this.mPlatformId, pluginInfo.pluginId, leafServiceName, sc, args, (Looper) null);
        }
    }

    @PluginApi(since = 401)
    public void unbindLeafService(Context context, ServiceConnection serviceConnection) {
        if (context == null) {
            throw new IllegalArgumentException("Context can't be null");
        } else if (serviceConnection != null) {
            LeafServiceManager.getInstance(context, this.mPlatformId).unbindService(serviceConnection);
        }
    }

    @PluginApi(since = 310)
    public void startLeafService(final Context context, String pluginId, final String leafServiceName, final Bundle args) {
        if (TextUtils.isEmpty(pluginId)) {
            throw new IllegalArgumentException("pluginId can't be empty");
        }
        this.mPluginManager.getPluginInfo(pluginId, new PluginManager.GetPluginInfoCallback() {
            public void onGetPluginInfo(PluginInfo pluginInfo) {
                if (pluginInfo != null) {
                    PluginHelper.this.startLeafService(context, pluginInfo, leafServiceName, args);
                }
            }
        });
    }

    @PluginApi(since = 310)
    public void startLeafService(Context context, PluginInfo pluginInfo, String leafServiceName, Bundle args) {
        if (pluginInfo == null) {
            throw new IllegalArgumentException("pluginInfo can't be empty");
        } else if (context == null) {
            throw new IllegalArgumentException("Context can't be null");
        } else if (TextUtils.isEmpty(leafServiceName)) {
            throw new IllegalArgumentException("leafServiceName can't be null");
        } else {
            LeafServiceManager.getInstance(context, this.mPlatformId).startService(this.mPlatformId, pluginInfo.pluginId, leafServiceName, args);
        }
    }

    public void startActivity(Context context, PluginInfo pluginInfo, String fragmentClassName, Intent args) {
        if (context == null) {
            throw new IllegalArgumentException("Context can't be null");
        } else if (pluginInfo == null) {
            throw new IllegalStateException("pluginInfo can't be null");
        } else {
            Intent intent = generateInnerIntent(context, pluginInfo, fragmentClassName, args);
            if (intent != null) {
                context.startActivity(intent);
            }
        }
    }

    static Class<?> findIntentClass(Intent intent, ClassLoader classLoader) {
        ComponentName cmp;
        if (classLoader == null) {
            return null;
        }
        if (intent != null) {
            cmp = intent.getComponent();
        } else {
            cmp = null;
        }
        if (cmp == null) {
            return null;
        }
        String clazzName = cmp.getClassName();
        if (TextUtils.isEmpty(clazzName)) {
            return null;
        }
        try {
            return Class.forName(clazzName, false, classLoader);
        } catch (Throwable th) {
            return null;
        }
    }

    public static boolean checkPluginId(String id) {
        return !isEmpty(id);
    }

    public static boolean checkPluginInfo(PluginInfo pluginInfo) {
        return pluginInfo != null;
    }

    private static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    @PluginApi(since = 4)
    public int currentVersion() {
        return this.mPluginManager.getPlatformVersion();
    }
}

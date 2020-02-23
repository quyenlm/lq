package com.tencent.component.plugin;

import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.view.LayoutInflater;
import com.tencent.component.utils.log.LogUtil;
import java.util.Locale;

public final class PluginContextWrapper extends ContextWrapper {
    private static final String TAG = "PluginContextWrapper";
    private LayoutInflater mLayoutInflater;
    private Configuration mOverrideConfiguration;
    private Context mPlatformContext;
    private final Plugin mPlugin;
    private AssetManager mPluginAsset;
    private final ClassLoader mPluginClassLoader;
    private final PluginInfo mPluginInfo;
    private Resources mPluginResources;
    private Resources.Theme mPluginTheme;

    public PluginContextWrapper(Context base, PluginInfo pluginInfo, Plugin plugin) {
        super(base);
        this.mPlatformContext = base;
        this.mPluginInfo = pluginInfo;
        this.mPlugin = plugin;
        this.mLayoutInflater = new PluginLayoutInflater((Context) this, plugin);
        this.mPluginClassLoader = plugin.getClass().getClassLoader();
        this.mPluginResources = plugin.getPluginManager().getPluginResources(pluginInfo);
        if (this.mPluginResources != null) {
            this.mPluginAsset = this.mPluginResources.getAssets();
        }
        if (this.mPluginResources != null) {
            resolveTheme();
        } else {
            LogUtil.i(TAG, "fail to init plugin resources for " + pluginInfo);
        }
    }

    public void applyOverrideConfiguration(Configuration overrideConfiguration) {
        if (overrideConfiguration != null) {
            this.mOverrideConfiguration = new Configuration();
            this.mOverrideConfiguration.setTo(overrideConfiguration);
            if (this.mPluginResources != null) {
                this.mPluginResources.updateConfiguration(this.mOverrideConfiguration, this.mPluginResources.getDisplayMetrics());
            }
        }
    }

    private void resolveTheme() {
        this.mPluginTheme = this.mPluginResources.newTheme();
        int theme = this.mPluginInfo.theme;
        Resources.Theme baseTheme = getBaseContext().getTheme();
        if (baseTheme != null) {
            this.mPluginTheme.setTo(baseTheme);
        }
        if (theme != 0) {
            this.mPluginTheme.applyStyle(theme, true);
        }
    }

    public Context getPlatformContext() {
        return this.mPlatformContext;
    }

    public LayoutInflater getLayoutInflater() {
        return this.mLayoutInflater;
    }

    public Object getSystemService(String name) {
        if ("layout_inflater".equals(name)) {
            return getLayoutInflater();
        }
        return super.getSystemService(name);
    }

    public Resources getResources() {
        if (this.mPluginResources != null) {
            if (this.mOverrideConfiguration != null) {
                Locale overrideLocale = getLocale(this.mOverrideConfiguration);
                Locale currentLocale = getLocale(this.mPluginResources.getConfiguration());
                if (overrideLocale != null && !overrideLocale.equals(currentLocale)) {
                    this.mPluginResources.updateConfiguration(this.mOverrideConfiguration, this.mPluginResources.getDisplayMetrics());
                }
            }
            return this.mPluginResources;
        }
        Resources resources = this.mPlugin.getPluginManager().getPluginResources(this.mPluginInfo);
        return resources == null ? super.getResources() : resources;
    }

    private static Locale getLocale(Configuration configuration) {
        if (Build.VERSION.SDK_INT >= 24) {
            return configuration.getLocales().get(0);
        }
        return configuration.locale;
    }

    public AssetManager getAssets() {
        if (this.mPluginAsset != null) {
            return this.mPluginAsset;
        }
        Resources resources = getResources();
        AssetManager assets = resources != null ? resources.getAssets() : null;
        return assets == null ? super.getAssets() : assets;
    }

    public ClassLoader getClassLoader() {
        return this.mPluginClassLoader;
    }

    public Resources.Theme getTheme() {
        return this.mPluginTheme != null ? this.mPluginTheme : super.getTheme();
    }

    public void setTheme(int resid) {
        if (this.mPluginTheme != null) {
            this.mPluginTheme.applyStyle(resid, true);
        } else {
            super.setTheme(resid);
        }
    }

    private Intent fixedIntent(Intent intent) {
        ComponentName componentName = intent.getComponent();
        if (componentName != null) {
            intent.setComponent(new ComponentName(getBaseContext(), componentName.getClassName()));
        }
        return intent;
    }

    public void startActivity(Intent intent) {
        super.startActivity(fixedIntent(intent));
    }

    public ComponentName startService(Intent service) {
        return super.startService(fixedIntent(service));
    }

    public String getPackageName() {
        return this.mPlugin.getPluginInfo().pluginId;
    }

    public PluginInfo getPluginInfo() {
        return this.mPluginInfo;
    }

    public Plugin getPlugin() {
        return this.mPlugin;
    }

    public static Context getPlatfromContext(Context context) {
        if (context instanceof PluginContextWrapper) {
            return ((PluginContextWrapper) context).getPlatformContext();
        }
        if (context instanceof PluginShellActivity) {
            return ((PluginShellActivity) context).getPlatformContext();
        }
        return context;
    }
}

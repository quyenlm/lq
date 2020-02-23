package com.tencent.component.plugin;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import com.tencent.component.plugin.PluginManager;
import com.tencent.component.plugin.server.PluginConstant;
import com.tencent.component.utils.log.LogUtil;
import java.util.List;

public class PluginShellActivity extends FragmentActivity {
    private static final String STATE_PLUGIN_INFO = "state_plugin_info";
    private static final String STATE_PLUGIN_PLATFORM_ID = "state_plugin_platform_id";
    private static final String TAG = "PluginShellActivity";
    private boolean mCreated = false;
    private int mDefaultThemeResource;
    private String mPlatformId;
    private Plugin mPlugin;
    private ClassLoader mPluginClassLoader;
    /* access modifiers changed from: private */
    public PluginInfo mPluginInfo;
    private LayoutInflater mPluginLayoutInflater;
    private PluginManager.PluginListener mPluginListener;
    private boolean mPluginResourceInit = false;
    private Resources mPluginResources;
    private Resources.Theme mPluginTheme;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        boolean pluginInit = initPlugin(savedInstanceState);
        super.onCreate(savedInstanceState);
        if (!pluginInit) {
            finish();
        } else if (!initPluginResourcesIfNeeded()) {
            finish();
        } else if (!initPluginFragment(savedInstanceState)) {
            finish();
        } else {
            registerPluginListener();
            this.mCreated = true;
        }
    }

    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        unregisterPluginListener();
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_PLUGIN_PLATFORM_ID, this.mPlatformId);
        outState.putParcelable(STATE_PLUGIN_INFO, this.mPluginInfo);
    }

    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (!this.mCreated && this.mPluginInfo != null && this.mPlugin != null) {
            initPluginResourcesIfNeeded();
        }
    }

    /* access modifiers changed from: package-private */
    public void beforeAttachFragment(Fragment fragment) {
        if (!this.mCreated && this.mPluginInfo != null && this.mPlugin != null) {
            initPluginResourcesIfNeeded();
        }
    }

    private boolean initPlugin(Bundle savedInstanceState) {
        PluginInfo pluginInfo;
        if (savedInstanceState == null) {
            this.mPlatformId = getPlatformIdFromIntent(getIntent());
            pluginInfo = loadPluginInfoFromIntent(getIntent());
        } else {
            this.mPlatformId = savedInstanceState.getString(STATE_PLUGIN_PLATFORM_ID);
            pluginInfo = (PluginInfo) savedInstanceState.getParcelable(STATE_PLUGIN_INFO);
        }
        if (TextUtils.isEmpty(this.mPlatformId)) {
            return false;
        }
        Plugin plugin = pluginInfo == null ? null : PluginManager.getInstance((Context) this, this.mPlatformId).getPlugin(pluginInfo);
        if (pluginInfo == null || plugin == null) {
            StringBuilder append = new StringBuilder().append("fail to init plugin for ");
            Object obj = pluginInfo;
            if (pluginInfo == null) {
                obj = loadPluginIdFromIntent(getIntent());
            }
            LogUtil.i(TAG, append.append(obj).toString());
            return false;
        }
        if (isFrameworkResource(pluginInfo.theme) || (isFrameworkPlugin(pluginInfo) && pluginInfo.theme != 0)) {
            setTheme(pluginInfo.theme);
        }
        plugin.notifyStartIfNeeded();
        this.mPluginInfo = pluginInfo;
        this.mPlugin = plugin;
        this.mPluginClassLoader = plugin.getClass().getClassLoader();
        return true;
    }

    private boolean initPluginFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            return true;
        }
        Plugin plugin = this.mPlugin;
        Intent intent = getIntent();
        PluginFragment fragment = null;
        try {
            fragment = PluginFragment.instantiate(plugin, loadPluginFragmentFromIntent(intent), loadPluginFragmentArgsFromIntent(intent));
        } catch (Throwable th) {
        }
        if (fragment == null) {
            LogUtil.i(TAG, "fail to init plugin fragment for " + this.mPluginInfo);
            return false;
        }
        fragment.beforeAddActivity(this);
        getSupportFragmentManager().beginTransaction().replace(16908290, fragment).commit();
        getSupportFragmentManager().executePendingTransactions();
        return true;
    }

    private boolean initPluginResourcesIfNeeded() {
        if (this.mPluginResourceInit) {
            return true;
        }
        this.mPluginResourceInit = initPluginResources();
        return this.mPluginResourceInit;
    }

    private boolean initPluginResources() {
        PluginInfo pluginInfo = this.mPluginInfo;
        Plugin plugin = this.mPlugin;
        if (isFrameworkPlugin(pluginInfo)) {
            return true;
        }
        this.mPluginLayoutInflater = new PluginLayoutInflater((Context) this, plugin);
        this.mPluginResources = PluginManager.getInstance((Context) this, this.mPlatformId).getPluginResources(pluginInfo);
        if (this.mPluginResources == null) {
            LogUtil.i(TAG, "fail to init plugin resources for " + pluginInfo);
            return false;
        }
        this.mPluginTheme = this.mPluginResources.newTheme();
        ensureDefaultThemeResource();
        int theme = pluginInfo.theme != 0 ? pluginInfo.theme : this.mDefaultThemeResource;
        Resources.Theme baseTheme = getBaseContext().getTheme();
        if (baseTheme != null) {
            this.mPluginTheme.setTo(baseTheme);
        }
        if (theme == 0) {
            return true;
        }
        this.mPluginTheme.applyStyle(theme, true);
        return true;
    }

    public Context getPlatformContext() {
        return PluginManager.getInstance((Context) this, this.mPlatformId).getPlatformContext();
    }

    private PluginFragment getCurrPluginFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(16908290);
        if (fragment == null || !(fragment instanceof PluginFragment)) {
            return null;
        }
        return (PluginFragment) fragment;
    }

    public ClassLoader getClassLoader() {
        return this.mPluginClassLoader != null ? this.mPluginClassLoader : super.getClassLoader();
    }

    public LayoutInflater getLayoutInflater() {
        return this.mPluginLayoutInflater != null ? this.mPluginLayoutInflater : super.getLayoutInflater();
    }

    public Resources getResources() {
        return this.mPluginResources != null ? this.mPluginResources : super.getResources();
    }

    public AssetManager getAssets() {
        Resources resources = getResources();
        AssetManager assets = resources != null ? resources.getAssets() : null;
        return assets != null ? assets : super.getAssets();
    }

    public Resources.Theme getTheme() {
        return this.mPluginTheme != null ? this.mPluginTheme : super.getTheme();
    }

    public void setTheme(int resid) {
        if (this.mPluginTheme != null) {
            this.mPluginTheme.applyStyle(resid, true);
            return;
        }
        ensureDefaultThemeResource();
        super.setTheme(resid);
    }

    /* access modifiers changed from: protected */
    public void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        super.onApplyThemeResource(theme, resid, first);
        if (first && this.mDefaultThemeResource == 0) {
            this.mDefaultThemeResource = resid;
        }
    }

    private void ensureDefaultThemeResource() {
        if (this.mDefaultThemeResource == 0) {
            super.getTheme();
        }
    }

    private void registerPluginListener() {
        if (this.mPluginListener == null) {
            this.mPluginListener = new PluginManager.PluginListener() {
                public void onPluginChanged(String id, int changeFlags, int statusFlags) {
                    if (id != null && id.equals(PluginShellActivity.this.mPluginInfo.pluginId) && (changeFlags & 1) != 0 && (statusFlags & 1) == 0 && !PluginShellActivity.this.isFinishing()) {
                        PluginShellActivity.this.finish();
                    }
                }

                public void onStartCheckPluginSurvive(List<PluginInfo> list) {
                }

                public void onPlatformInitialStart() {
                }

                public void onPlatformInitialFinish() {
                }

                public void onPluginInstalled(String id, int lastVersion, int version) {
                }

                public void onPluginUninstall(String id) {
                }

                public void onPendingInstallFinish(boolean success, boolean corePlugin, String extraInfo, String errorMsg) {
                }
            };
            PluginManager.getInstance((Context) this, this.mPlatformId).addPluginListener(this.mPluginListener);
        }
    }

    private void unregisterPluginListener() {
        if (this.mPluginListener != null) {
            PluginManager.getInstance((Context) this, this.mPlatformId).removePluginListener(this.mPluginListener);
        }
    }

    /* access modifiers changed from: package-private */
    public final PluginInfo getPluginInfo() {
        return this.mPluginInfo;
    }

    private PluginInfo loadPluginInfoFromIntent(Intent intent) {
        if (intent == null) {
            return null;
        }
        PluginInfo pluginInfo = null;
        if (intent.hasExtra(PluginConstant.INTENT_PLUGIN_INNER)) {
            pluginInfo = (PluginInfo) intent.getParcelableExtra(PluginConstant.INTENT_PLUGIN_INNER);
        }
        if (pluginInfo == null) {
            String id = intent.getStringExtra(PluginConstant.INTENT_PLUGIN);
            String platformId = getPlatformIdFromIntent(intent);
            if (!TextUtils.isEmpty(platformId)) {
                pluginInfo = id == null ? null : PluginManager.getInstance((Context) this, platformId).loadPluginInfoSync(id);
            }
        }
        return pluginInfo;
    }

    private String getPlatformIdFromIntent(Intent intent) {
        if (intent == null) {
            return null;
        }
        return intent.getStringExtra(PluginConstant.INTENT_PLUGIN_PLATFORM_ID);
    }

    private String loadPluginIdFromIntent(Intent intent) {
        if (intent == null) {
            return null;
        }
        return intent.getStringExtra(PluginConstant.INTENT_PLUGIN);
    }

    private String loadPluginFragmentFromIntent(Intent intent) {
        if (intent == null) {
            return null;
        }
        return intent.getStringExtra(PluginConstant.INTENT_PLUGIN_FRAGMENT);
    }

    private Bundle loadPluginFragmentArgsFromIntent(Intent intent) {
        if (intent == null) {
            return null;
        }
        return intent.getBundleExtra(PluginConstant.INTENT_PLUGIN_ARGS);
    }

    private static boolean isFrameworkResource(int resid) {
        return (resid >>> 24) == 1;
    }

    private static boolean isFrameworkPlugin(PluginInfo pluginInfo) {
        return pluginInfo != null && pluginInfo.isInternal();
    }

    public void onBackPressed() {
        PluginFragment pluginFragment = getCurrPluginFragment();
        if (pluginFragment == null || !pluginFragment.onBackPressed()) {
            super.onBackPressed();
        }
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handlePluginNewIntent(intent);
    }

    public void onUserInteraction() {
        super.onUserInteraction();
        PluginFragment pluginFragment = getCurrPluginFragment();
        if (pluginFragment != null) {
            pluginFragment.onUserInteraction();
        }
    }

    /* access modifiers changed from: protected */
    public void onUserLeaveHint() {
        super.onUserLeaveHint();
        PluginFragment pluginFragment = getCurrPluginFragment();
        if (pluginFragment != null) {
            pluginFragment.onUserLeaveHint();
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        PluginFragment pluginFragment = getCurrPluginFragment();
        if (pluginFragment != null) {
            pluginFragment.onWindowFocusChanged(hasFocus);
        }
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        PluginFragment pluginFragment = getCurrPluginFragment();
        if (pluginFragment == null || !pluginFragment.dispatchTouchEvent(event)) {
            return super.dispatchTouchEvent(event);
        }
        return true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        PluginFragment pluginFragment = getCurrPluginFragment();
        if (pluginFragment == null || !pluginFragment.onTouchEvent(event)) {
            return super.onTouchEvent(event);
        }
        return true;
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        PluginFragment pluginFragment = getCurrPluginFragment();
        if (pluginFragment == null || !pluginFragment.dispatchKeyEvent(event)) {
            return super.dispatchKeyEvent(event);
        }
        return true;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        PluginFragment pluginFragment = getCurrPluginFragment();
        if (pluginFragment == null || !pluginFragment.onKeyUp(keyCode, event)) {
            return super.onKeyUp(keyCode, event);
        }
        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        PluginFragment pluginFragment = getCurrPluginFragment();
        if (pluginFragment == null || !pluginFragment.onKeyDown(keyCode, event)) {
            return super.onKeyDown(keyCode, event);
        }
        return true;
    }

    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        PluginFragment pluginFragment = getCurrPluginFragment();
        if (pluginFragment == null || !pluginFragment.onKeyLongPress(keyCode, event)) {
            return super.onKeyLongPress(keyCode, event);
        }
        return true;
    }

    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        PluginFragment pluginFragment = getCurrPluginFragment();
        if (pluginFragment == null || !pluginFragment.onKeyMultiple(keyCode, repeatCount, event)) {
            return super.onKeyMultiple(keyCode, repeatCount, event);
        }
        return true;
    }

    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        PluginFragment pluginFragment = getCurrPluginFragment();
        if (pluginFragment == null || !pluginFragment.onKeyShortcut(keyCode, event)) {
            return super.onKeyShortcut(keyCode, event);
        }
        return true;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v0, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v0, resolved type: com.tencent.component.plugin.PluginInfo} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v1, resolved type: com.tencent.component.plugin.PluginInfo} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: com.tencent.component.plugin.PluginInfo} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v3, resolved type: com.tencent.component.plugin.PluginInfo} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v4, resolved type: com.tencent.component.plugin.PluginInfo} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void handlePluginNewIntent(android.content.Intent r15) {
        /*
            r14 = this;
            r12 = 2
            r13 = -536870913(0xffffffffdfffffff, float:-3.6893486E19)
            r10 = 0
            r2 = 1
            if (r15 != 0) goto L_0x0010
            java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException
            java.lang.String r11 = "invalid intent null"
            r10.<init>(r11)
            throw r10
        L_0x0010:
            com.tencent.component.plugin.PluginInfo r1 = r14.mPluginInfo
            com.tencent.component.plugin.PluginFragment r0 = r14.getCurrPluginFragment()
            java.lang.String r4 = r14.loadPluginIdFromIntent(r15)
            java.lang.String r3 = r14.loadPluginFragmentFromIntent(r15)
            com.tencent.component.plugin.PluginInfo r6 = r14.loadPluginInfoFromIntent(r15)
            if (r6 != 0) goto L_0x002f
            if (r4 == 0) goto L_0x002f
            java.lang.String r11 = r1.pluginId
            boolean r11 = r4.equals(r11)
            if (r11 == 0) goto L_0x002f
            r6 = r1
        L_0x002f:
            if (r6 == 0) goto L_0x0033
            if (r3 != 0) goto L_0x005a
        L_0x0033:
            java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "invalid plugin "
            java.lang.StringBuilder r11 = r11.append(r12)
            if (r6 == 0) goto L_0x0058
        L_0x0042:
            java.lang.StringBuilder r11 = r11.append(r6)
            java.lang.String r12 = ", or fragment "
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.StringBuilder r11 = r11.append(r3)
            java.lang.String r11 = r11.toString()
            r10.<init>(r11)
            throw r10
        L_0x0058:
            r6 = r4
            goto L_0x0042
        L_0x005a:
            com.tencent.component.plugin.PluginInfo$ExtraInfo r11 = r6.extraInfo
            int r9 = r11.singleTop
            if (r9 == r2) goto L_0x0062
            if (r9 != r12) goto L_0x0088
        L_0x0062:
            r7 = r2
        L_0x0063:
            if (r9 != r2) goto L_0x008c
            java.lang.String r11 = r6.pluginId
            java.lang.String r12 = r1.pluginId
            boolean r11 = r11.equals(r12)
            if (r11 == 0) goto L_0x008a
            if (r0 == 0) goto L_0x008a
        L_0x0071:
            if (r7 == 0) goto L_0x00b9
            android.content.Intent r5 = new android.content.Intent
            r5.<init>(r15)
            if (r2 == 0) goto L_0x00ad
            android.os.Bundle r8 = r14.loadPluginFragmentArgsFromIntent(r5)
            if (r8 == 0) goto L_0x0084
            android.content.Intent r5 = r5.replaceExtras(r8)
        L_0x0084:
            r0.onNewIntent(r5)
        L_0x0087:
            return
        L_0x0088:
            r7 = r10
            goto L_0x0063
        L_0x008a:
            r2 = r10
            goto L_0x0071
        L_0x008c:
            if (r9 != r12) goto L_0x00ab
            java.lang.String r11 = r6.pluginId
            java.lang.String r12 = r1.pluginId
            boolean r11 = r11.equals(r12)
            if (r11 == 0) goto L_0x00a9
            if (r0 == 0) goto L_0x00a9
            java.lang.Class r11 = r0.getClass()
            java.lang.String r11 = r11.getName()
            boolean r11 = r3.equals(r11)
            if (r11 == 0) goto L_0x00a9
        L_0x00a8:
            goto L_0x0071
        L_0x00a9:
            r2 = r10
            goto L_0x00a8
        L_0x00ab:
            r2 = 0
            goto L_0x0071
        L_0x00ad:
            int r10 = r5.getFlags()
            r10 = r10 & r13
            r5.setFlags(r10)
            r14.startActivity(r5)
            goto L_0x0087
        L_0x00b9:
            android.content.Intent r5 = new android.content.Intent
            r5.<init>(r15)
            int r10 = r5.getFlags()
            r10 = r10 & r13
            r5.setFlags(r10)
            r14.startActivity(r5)
            goto L_0x0087
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.plugin.PluginShellActivity.handlePluginNewIntent(android.content.Intent):void");
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PluginFragment pluginFragment = getCurrPluginFragment();
        if (pluginFragment != null) {
            pluginFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}

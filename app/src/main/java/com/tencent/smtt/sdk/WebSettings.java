package com.tencent.smtt.sdk;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.sdk.TbsListener;
import com.tencent.smtt.utils.v;

public class WebSettings {
    public static final int LOAD_CACHE_ELSE_NETWORK = 1;
    public static final int LOAD_CACHE_ONLY = 3;
    public static final int LOAD_DEFAULT = -1;
    public static final int LOAD_NORMAL = 0;
    public static final int LOAD_NO_CACHE = 2;
    private IX5WebSettings a;
    private android.webkit.WebSettings b;
    private boolean c;

    public enum LayoutAlgorithm {
        NORMAL,
        SINGLE_COLUMN,
        NARROW_COLUMNS
    }

    public enum PluginState {
        ON,
        ON_DEMAND,
        OFF
    }

    public enum RenderPriority {
        NORMAL,
        HIGH,
        LOW
    }

    public enum TextSize {
        SMALLEST(50),
        SMALLER(75),
        NORMAL(100),
        LARGER(TbsListener.ErrorCode.DOWNLOAD_THROWABLE),
        LARGEST(150);
        
        int value;

        private TextSize(int i) {
            this.value = i;
        }
    }

    public enum ZoomDensity {
        FAR(150),
        MEDIUM(100),
        CLOSE(75);
        
        int value;

        private ZoomDensity(int i) {
            this.value = i;
        }
    }

    WebSettings(android.webkit.WebSettings webSettings) {
        this.a = null;
        this.b = null;
        this.c = false;
        this.a = null;
        this.b = webSettings;
        this.c = false;
    }

    WebSettings(IX5WebSettings iX5WebSettings) {
        this.a = null;
        this.b = null;
        this.c = false;
        this.a = iX5WebSettings;
        this.b = null;
        this.c = true;
    }

    @TargetApi(17)
    public static String getDefaultUserAgent(Context context) {
        if (br.a().b()) {
            return br.a().c().i(context);
        }
        if (Build.VERSION.SDK_INT < 17) {
            return null;
        }
        Object a2 = v.a((Class<?>) android.webkit.WebSettings.class, "getDefaultUserAgent", (Class<?>[]) new Class[]{Context.class}, context);
        return a2 == null ? null : (String) a2;
    }

    @Deprecated
    public boolean enableSmoothTransition() {
        if (this.c && this.a != null) {
            return this.a.enableSmoothTransition();
        }
        if (this.c || this.b == null || Build.VERSION.SDK_INT < 11) {
            return false;
        }
        Object a2 = v.a((Object) this.b, "enableSmoothTransition");
        return a2 == null ? false : ((Boolean) a2).booleanValue();
    }

    @TargetApi(11)
    public boolean getAllowContentAccess() {
        if (this.c && this.a != null) {
            return this.a.getAllowContentAccess();
        }
        if (this.c || this.b == null || Build.VERSION.SDK_INT < 11) {
            return false;
        }
        Object a2 = v.a((Object) this.b, "getAllowContentAccess");
        return a2 == null ? false : ((Boolean) a2).booleanValue();
    }

    @TargetApi(3)
    public boolean getAllowFileAccess() {
        if (this.c && this.a != null) {
            return this.a.getAllowFileAccess();
        }
        if (this.c || this.b == null) {
            return false;
        }
        return this.b.getAllowFileAccess();
    }

    public synchronized boolean getBlockNetworkImage() {
        return (!this.c || this.a == null) ? (this.c || this.b == null) ? false : this.b.getBlockNetworkImage() : this.a.getBlockNetworkImage();
    }

    @TargetApi(8)
    public synchronized boolean getBlockNetworkLoads() {
        boolean z = false;
        synchronized (this) {
            if (this.c && this.a != null) {
                z = this.a.getBlockNetworkLoads();
            } else if (!this.c && this.b != null && Build.VERSION.SDK_INT >= 8) {
                z = this.b.getBlockNetworkLoads();
            }
        }
        return z;
    }

    @TargetApi(3)
    public boolean getBuiltInZoomControls() {
        if (this.c && this.a != null) {
            return this.a.getBuiltInZoomControls();
        }
        if (this.c || this.b == null) {
            return false;
        }
        return this.b.getBuiltInZoomControls();
    }

    public int getCacheMode() {
        if (this.c && this.a != null) {
            return this.a.getCacheMode();
        }
        if (this.c || this.b == null) {
            return 0;
        }
        return this.b.getCacheMode();
    }

    public synchronized String getCursiveFontFamily() {
        return (!this.c || this.a == null) ? (this.c || this.b == null) ? "" : this.b.getCursiveFontFamily() : this.a.getCursiveFontFamily();
    }

    @TargetApi(5)
    public synchronized boolean getDatabaseEnabled() {
        return (!this.c || this.a == null) ? (this.c || this.b == null) ? false : this.b.getDatabaseEnabled() : this.a.getDatabaseEnabled();
    }

    @TargetApi(5)
    public synchronized String getDatabasePath() {
        return (!this.c || this.a == null) ? (this.c || this.b == null) ? "" : this.b.getDatabasePath() : this.a.getDatabasePath();
    }

    public synchronized int getDefaultFixedFontSize() {
        return (!this.c || this.a == null) ? (this.c || this.b == null) ? 0 : this.b.getDefaultFixedFontSize() : this.a.getDefaultFixedFontSize();
    }

    public synchronized int getDefaultFontSize() {
        return (!this.c || this.a == null) ? (this.c || this.b == null) ? 0 : this.b.getDefaultFontSize() : this.a.getDefaultFontSize();
    }

    public synchronized String getDefaultTextEncodingName() {
        return (!this.c || this.a == null) ? (this.c || this.b == null) ? "" : this.b.getDefaultTextEncodingName() : this.a.getDefaultTextEncodingName();
    }

    @TargetApi(7)
    public ZoomDensity getDefaultZoom() {
        if (this.c && this.a != null) {
            return ZoomDensity.valueOf(this.a.getDefaultZoom().name());
        }
        if (this.c || this.b == null) {
            return null;
        }
        return ZoomDensity.valueOf(this.b.getDefaultZoom().name());
    }

    @TargetApi(11)
    public boolean getDisplayZoomControls() {
        if (this.c && this.a != null) {
            return this.a.getDisplayZoomControls();
        }
        if (this.c || this.b == null || Build.VERSION.SDK_INT < 11) {
            return false;
        }
        Object a2 = v.a((Object) this.b, "getDisplayZoomControls");
        return a2 == null ? false : ((Boolean) a2).booleanValue();
    }

    @TargetApi(7)
    public synchronized boolean getDomStorageEnabled() {
        return (!this.c || this.a == null) ? (this.c || this.b == null) ? false : this.b.getDomStorageEnabled() : this.a.getDomStorageEnabled();
    }

    public synchronized String getFantasyFontFamily() {
        return (!this.c || this.a == null) ? (this.c || this.b == null) ? "" : this.b.getFantasyFontFamily() : this.a.getFantasyFontFamily();
    }

    public synchronized String getFixedFontFamily() {
        return (!this.c || this.a == null) ? (this.c || this.b == null) ? "" : this.b.getFixedFontFamily() : this.a.getFixedFontFamily();
    }

    public synchronized boolean getJavaScriptCanOpenWindowsAutomatically() {
        return (!this.c || this.a == null) ? (this.c || this.b == null) ? false : this.b.getJavaScriptCanOpenWindowsAutomatically() : this.a.getJavaScriptCanOpenWindowsAutomatically();
    }

    public synchronized boolean getJavaScriptEnabled() {
        return (!this.c || this.a == null) ? (this.c || this.b == null) ? false : this.b.getJavaScriptEnabled() : this.a.getJavaScriptEnabled();
    }

    public synchronized LayoutAlgorithm getLayoutAlgorithm() {
        return (!this.c || this.a == null) ? (this.c || this.b == null) ? null : LayoutAlgorithm.valueOf(this.b.getLayoutAlgorithm().name()) : LayoutAlgorithm.valueOf(this.a.getLayoutAlgorithm().name());
    }

    public boolean getLightTouchEnabled() {
        if (this.c && this.a != null) {
            return this.a.getLightTouchEnabled();
        }
        if (this.c || this.b == null) {
            return false;
        }
        return this.b.getLightTouchEnabled();
    }

    @TargetApi(7)
    public boolean getLoadWithOverviewMode() {
        if (this.c && this.a != null) {
            return this.a.getLoadWithOverviewMode();
        }
        if (this.c || this.b == null) {
            return false;
        }
        return this.b.getLoadWithOverviewMode();
    }

    public synchronized boolean getLoadsImagesAutomatically() {
        return (!this.c || this.a == null) ? (this.c || this.b == null) ? false : this.b.getLoadsImagesAutomatically() : this.a.getLoadsImagesAutomatically();
    }

    @TargetApi(17)
    public boolean getMediaPlaybackRequiresUserGesture() {
        if (this.c && this.a != null) {
            return this.a.getMediaPlaybackRequiresUserGesture();
        }
        if (this.c || this.b == null || Build.VERSION.SDK_INT < 17) {
            return false;
        }
        Object a2 = v.a((Object) this.b, "getMediaPlaybackRequiresUserGesture");
        return a2 == null ? false : ((Boolean) a2).booleanValue();
    }

    public synchronized int getMinimumFontSize() {
        return (!this.c || this.a == null) ? (this.c || this.b == null) ? 0 : this.b.getMinimumFontSize() : this.a.getMinimumFontSize();
    }

    public synchronized int getMinimumLogicalFontSize() {
        return (!this.c || this.a == null) ? (this.c || this.b == null) ? 0 : this.b.getMinimumLogicalFontSize() : this.a.getMinimumLogicalFontSize();
    }

    public synchronized int getMixedContentMode() {
        int i = -1;
        synchronized (this) {
            if (this.c && this.a != null) {
                try {
                    i = this.a.getMixedContentMode();
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            } else if (Build.VERSION.SDK_INT >= 21) {
                Object a2 = v.a((Object) this.b, "getMixedContentMode", (Class<?>[]) new Class[0], new Object[0]);
                i = a2 == null ? -1 : ((Integer) a2).intValue();
            }
        }
        return i;
    }

    public boolean getNavDump() {
        if (this.c && this.a != null) {
            return this.a.getNavDump();
        }
        if (this.c || this.b == null) {
            return false;
        }
        Object a2 = v.a((Object) this.b, "getNavDump");
        if (a2 == null) {
            return false;
        }
        return ((Boolean) a2).booleanValue();
    }

    @TargetApi(8)
    @Deprecated
    public synchronized PluginState getPluginState() {
        PluginState pluginState;
        if (this.c && this.a != null) {
            pluginState = PluginState.valueOf(this.a.getPluginState().name());
        } else if (this.c || this.b == null) {
            pluginState = null;
        } else if (Build.VERSION.SDK_INT >= 8) {
            Object a2 = v.a((Object) this.b, "getPluginState");
            pluginState = a2 == null ? null : PluginState.valueOf(((WebSettings.PluginState) a2).name());
        } else {
            pluginState = null;
        }
        return pluginState;
    }

    @TargetApi(8)
    @Deprecated
    public synchronized boolean getPluginsEnabled() {
        boolean z = false;
        synchronized (this) {
            if (this.c && this.a != null) {
                z = this.a.getPluginsEnabled();
            } else if (!this.c && this.b != null) {
                if (Build.VERSION.SDK_INT <= 17) {
                    Object a2 = v.a((Object) this.b, "getPluginsEnabled");
                    z = a2 == null ? false : ((Boolean) a2).booleanValue();
                } else if (Build.VERSION.SDK_INT == 18 && WebSettings.PluginState.ON == this.b.getPluginState()) {
                    z = true;
                }
            }
        }
        return z;
    }

    @Deprecated
    public synchronized String getPluginsPath() {
        String str;
        if (this.c && this.a != null) {
            str = this.a.getPluginsPath();
        } else if (this.c || this.b == null) {
            str = "";
        } else if (Build.VERSION.SDK_INT <= 17) {
            Object a2 = v.a((Object) this.b, "getPluginsPath");
            str = a2 == null ? null : (String) a2;
        } else {
            str = "";
        }
        return str;
    }

    public synchronized String getSansSerifFontFamily() {
        return (!this.c || this.a == null) ? (this.c || this.b == null) ? "" : this.b.getSansSerifFontFamily() : this.a.getSansSerifFontFamily();
    }

    public boolean getSaveFormData() {
        if (this.c && this.a != null) {
            return this.a.getSaveFormData();
        }
        if (this.c || this.b == null) {
            return false;
        }
        return this.b.getSaveFormData();
    }

    public boolean getSavePassword() {
        if (this.c && this.a != null) {
            return this.a.getSavePassword();
        }
        if (this.c || this.b == null) {
            return false;
        }
        return this.b.getSavePassword();
    }

    public synchronized String getSerifFontFamily() {
        return (!this.c || this.a == null) ? (this.c || this.b == null) ? "" : this.b.getSerifFontFamily() : this.a.getSerifFontFamily();
    }

    public synchronized String getStandardFontFamily() {
        return (!this.c || this.a == null) ? (this.c || this.b == null) ? "" : this.b.getStandardFontFamily() : this.a.getStandardFontFamily();
    }

    public TextSize getTextSize() {
        if (this.c && this.a != null) {
            return TextSize.valueOf(this.a.getTextSize().name());
        }
        if (this.c || this.b == null) {
            return null;
        }
        return TextSize.valueOf(this.b.getTextSize().name());
    }

    @TargetApi(14)
    public synchronized int getTextZoom() {
        int i = 0;
        synchronized (this) {
            if (this.c && this.a != null) {
                i = this.a.getTextZoom();
            } else if (!this.c && this.b != null && Build.VERSION.SDK_INT >= 14) {
                try {
                    i = this.b.getTextZoom();
                } catch (Exception e) {
                    Object a2 = v.a((Object) this.b, "getTextZoom");
                    i = a2 == null ? 0 : ((Integer) a2).intValue();
                }
            }
        }
        return i;
    }

    @Deprecated
    public boolean getUseWebViewBackgroundForOverscrollBackground() {
        if (this.c && this.a != null) {
            return this.a.getUseWebViewBackgroundForOverscrollBackground();
        }
        if (this.c || this.b == null) {
            return false;
        }
        Object a2 = v.a((Object) this.b, "getUseWebViewBackgroundForOverscrollBackground");
        if (a2 == null) {
            return false;
        }
        return ((Boolean) a2).booleanValue();
    }

    public synchronized boolean getUseWideViewPort() {
        return (!this.c || this.a == null) ? (this.c || this.b == null) ? false : this.b.getUseWideViewPort() : this.a.getUseWideViewPort();
    }

    @TargetApi(3)
    public String getUserAgentString() {
        return (!this.c || this.a == null) ? (this.c || this.b == null) ? "" : this.b.getUserAgentString() : this.a.getUserAgentString();
    }

    @TargetApi(11)
    public void setAllowContentAccess(boolean z) {
        if (this.c && this.a != null) {
            this.a.setAllowContentAccess(z);
        } else if (!this.c && this.b != null && Build.VERSION.SDK_INT >= 11) {
            v.a((Object) this.b, "setAllowContentAccess", (Class<?>[]) new Class[]{Boolean.TYPE}, Boolean.valueOf(z));
        }
    }

    @TargetApi(3)
    public void setAllowFileAccess(boolean z) {
        if (this.c && this.a != null) {
            this.a.setAllowFileAccess(z);
        } else if (!this.c && this.b != null) {
            this.b.setAllowFileAccess(z);
        }
    }

    @TargetApi(16)
    public void setAllowFileAccessFromFileURLs(boolean z) {
        if (this.c && this.a != null) {
            this.a.setAllowFileAccessFromFileURLs(z);
        } else if (!this.c && this.b != null) {
            v.a((Object) this.b, "setAllowFileAccessFromFileURLs", (Class<?>[]) new Class[]{Boolean.TYPE}, Boolean.valueOf(z));
        }
    }

    @TargetApi(16)
    public void setAllowUniversalAccessFromFileURLs(boolean z) {
        if (this.c && this.a != null) {
            this.a.setAllowUniversalAccessFromFileURLs(z);
        } else if (!this.c && this.b != null) {
            v.a((Object) this.b, "setAllowUniversalAccessFromFileURLs", (Class<?>[]) new Class[]{Boolean.TYPE}, Boolean.valueOf(z));
        }
    }

    @TargetApi(7)
    public void setAppCacheEnabled(boolean z) {
        if (this.c && this.a != null) {
            this.a.setAppCacheEnabled(z);
        } else if (!this.c && this.b != null) {
            this.b.setAppCacheEnabled(z);
        }
    }

    @TargetApi(7)
    public void setAppCacheMaxSize(long j) {
        if (this.c && this.a != null) {
            this.a.setAppCacheMaxSize(j);
        } else if (!this.c && this.b != null) {
            this.b.setAppCacheMaxSize(j);
        }
    }

    @TargetApi(7)
    public void setAppCachePath(String str) {
        if (this.c && this.a != null) {
            this.a.setAppCachePath(str);
        } else if (!this.c && this.b != null) {
            this.b.setAppCachePath(str);
        }
    }

    public void setBlockNetworkImage(boolean z) {
        if (this.c && this.a != null) {
            this.a.setBlockNetworkImage(z);
        } else if (!this.c && this.b != null) {
            this.b.setBlockNetworkImage(z);
        }
    }

    @TargetApi(8)
    public synchronized void setBlockNetworkLoads(boolean z) {
        if (this.c && this.a != null) {
            this.a.setBlockNetworkLoads(z);
        } else if (!this.c && this.b != null && Build.VERSION.SDK_INT >= 8) {
            this.b.setBlockNetworkLoads(z);
        }
    }

    @TargetApi(3)
    public void setBuiltInZoomControls(boolean z) {
        if (this.c && this.a != null) {
            this.a.setBuiltInZoomControls(z);
        } else if (!this.c && this.b != null) {
            this.b.setBuiltInZoomControls(z);
        }
    }

    public void setCacheMode(int i) {
        if (this.c && this.a != null) {
            this.a.setCacheMode(i);
        } else if (!this.c && this.b != null) {
            this.b.setCacheMode(i);
        }
    }

    public synchronized void setCursiveFontFamily(String str) {
        if (this.c && this.a != null) {
            this.a.setCursiveFontFamily(str);
        } else if (!this.c && this.b != null) {
            this.b.setCursiveFontFamily(str);
        }
    }

    @TargetApi(5)
    public void setDatabaseEnabled(boolean z) {
        if (this.c && this.a != null) {
            this.a.setDatabaseEnabled(z);
        } else if (!this.c && this.b != null) {
            this.b.setDatabaseEnabled(z);
        }
    }

    @TargetApi(5)
    @Deprecated
    public void setDatabasePath(String str) {
        if (this.c && this.a != null) {
            this.a.setDatabasePath(str);
        } else if (!this.c && this.b != null) {
            v.a((Object) this.b, "setDatabasePath", (Class<?>[]) new Class[]{String.class}, str);
        }
    }

    public synchronized void setDefaultFixedFontSize(int i) {
        if (this.c && this.a != null) {
            this.a.setDefaultFixedFontSize(i);
        } else if (!this.c && this.b != null) {
            this.b.setDefaultFixedFontSize(i);
        }
    }

    public synchronized void setDefaultFontSize(int i) {
        if (this.c && this.a != null) {
            this.a.setDefaultFontSize(i);
        } else if (!this.c && this.b != null) {
            this.b.setDefaultFontSize(i);
        }
    }

    public synchronized void setDefaultTextEncodingName(String str) {
        if (this.c && this.a != null) {
            this.a.setDefaultTextEncodingName(str);
        } else if (!this.c && this.b != null) {
            this.b.setDefaultTextEncodingName(str);
        }
    }

    @TargetApi(7)
    public void setDefaultZoom(ZoomDensity zoomDensity) {
        if (this.c && this.a != null) {
            this.a.setDefaultZoom(IX5WebSettings.ZoomDensity.valueOf(zoomDensity.name()));
        } else if (!this.c && this.b != null) {
            this.b.setDefaultZoom(WebSettings.ZoomDensity.valueOf(zoomDensity.name()));
        }
    }

    @TargetApi(11)
    public void setDisplayZoomControls(boolean z) {
        if (this.c && this.a != null) {
            this.a.setDisplayZoomControls(z);
        } else if (!this.c && this.b != null && Build.VERSION.SDK_INT >= 11) {
            v.a((Object) this.b, "setDisplayZoomControls", (Class<?>[]) new Class[]{Boolean.TYPE}, Boolean.valueOf(z));
        }
    }

    @TargetApi(7)
    public void setDomStorageEnabled(boolean z) {
        if (this.c && this.a != null) {
            this.a.setDomStorageEnabled(z);
        } else if (!this.c && this.b != null) {
            this.b.setDomStorageEnabled(z);
        }
    }

    @TargetApi(11)
    @Deprecated
    public void setEnableSmoothTransition(boolean z) {
        if (this.c && this.a != null) {
            this.a.setEnableSmoothTransition(z);
        } else if (!this.c && this.b != null && Build.VERSION.SDK_INT >= 11) {
            v.a((Object) this.b, "setEnableSmoothTransition", (Class<?>[]) new Class[]{Boolean.TYPE}, Boolean.valueOf(z));
        }
    }

    public synchronized void setFantasyFontFamily(String str) {
        if (this.c && this.a != null) {
            this.a.setFantasyFontFamily(str);
        } else if (!this.c && this.b != null) {
            this.b.setFantasyFontFamily(str);
        }
    }

    public synchronized void setFixedFontFamily(String str) {
        if (this.c && this.a != null) {
            this.a.setFixedFontFamily(str);
        } else if (!this.c && this.b != null) {
            this.b.setFixedFontFamily(str);
        }
    }

    @TargetApi(5)
    public void setGeolocationDatabasePath(String str) {
        if (this.c && this.a != null) {
            this.a.setGeolocationDatabasePath(str);
        } else if (!this.c && this.b != null) {
            this.b.setGeolocationDatabasePath(str);
        }
    }

    @TargetApi(5)
    public void setGeolocationEnabled(boolean z) {
        if (this.c && this.a != null) {
            this.a.setGeolocationEnabled(z);
        } else if (!this.c && this.b != null) {
            this.b.setGeolocationEnabled(z);
        }
    }

    public synchronized void setJavaScriptCanOpenWindowsAutomatically(boolean z) {
        if (this.c && this.a != null) {
            this.a.setJavaScriptCanOpenWindowsAutomatically(z);
        } else if (!this.c && this.b != null) {
            this.b.setJavaScriptCanOpenWindowsAutomatically(z);
        }
    }

    @Deprecated
    public void setJavaScriptEnabled(boolean z) {
        try {
            if (this.c && this.a != null) {
                this.a.setJavaScriptEnabled(z);
            } else if (!this.c && this.b != null) {
                this.b.setJavaScriptEnabled(z);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void setLayoutAlgorithm(LayoutAlgorithm layoutAlgorithm) {
        if (this.c && this.a != null) {
            this.a.setLayoutAlgorithm(IX5WebSettings.LayoutAlgorithm.valueOf(layoutAlgorithm.name()));
        } else if (!this.c && this.b != null) {
            this.b.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.valueOf(layoutAlgorithm.name()));
        }
    }

    public void setLightTouchEnabled(boolean z) {
        if (this.c && this.a != null) {
            this.a.setLightTouchEnabled(z);
        } else if (!this.c && this.b != null) {
            this.b.setLightTouchEnabled(z);
        }
    }

    @TargetApi(7)
    public void setLoadWithOverviewMode(boolean z) {
        if (this.c && this.a != null) {
            this.a.setLoadWithOverviewMode(z);
        } else if (!this.c && this.b != null) {
            this.b.setLoadWithOverviewMode(z);
        }
    }

    public void setLoadsImagesAutomatically(boolean z) {
        if (this.c && this.a != null) {
            this.a.setLoadsImagesAutomatically(z);
        } else if (!this.c && this.b != null) {
            this.b.setLoadsImagesAutomatically(z);
        }
    }

    @TargetApi(17)
    public void setMediaPlaybackRequiresUserGesture(boolean z) {
        if (this.c && this.a != null) {
            this.a.setMediaPlaybackRequiresUserGesture(z);
        } else if (!this.c && this.b != null && Build.VERSION.SDK_INT >= 17) {
            v.a((Object) this.b, "setMediaPlaybackRequiresUserGesture", (Class<?>[]) new Class[]{Boolean.TYPE}, Boolean.valueOf(z));
        }
    }

    public synchronized void setMinimumFontSize(int i) {
        if (this.c && this.a != null) {
            this.a.setMinimumFontSize(i);
        } else if (!this.c && this.b != null) {
            this.b.setMinimumFontSize(i);
        }
    }

    public synchronized void setMinimumLogicalFontSize(int i) {
        if (this.c && this.a != null) {
            this.a.setMinimumLogicalFontSize(i);
        } else if (!this.c && this.b != null) {
            this.b.setMinimumLogicalFontSize(i);
        }
    }

    @TargetApi(21)
    public void setMixedContentMode(int i) {
        if ((!this.c || this.a == null) && !this.c && this.b != null && Build.VERSION.SDK_INT >= 21) {
            v.a((Object) this.b, "setMixedContentMode", (Class<?>[]) new Class[]{Integer.TYPE}, Integer.valueOf(i));
        }
    }

    public void setNavDump(boolean z) {
        if (this.c && this.a != null) {
            this.a.setNavDump(z);
        } else if (!this.c && this.b != null) {
            v.a((Object) this.b, "setNavDump", (Class<?>[]) new Class[]{Boolean.TYPE}, Boolean.valueOf(z));
        }
    }

    public void setNeedInitialFocus(boolean z) {
        if (this.c && this.a != null) {
            this.a.setNeedInitialFocus(z);
        } else if (!this.c && this.b != null) {
            this.b.setNeedInitialFocus(z);
        }
    }

    @TargetApi(8)
    @Deprecated
    public synchronized void setPluginState(PluginState pluginState) {
        if (this.c && this.a != null) {
            this.a.setPluginState(IX5WebSettings.PluginState.valueOf(pluginState.name()));
        } else if (!this.c && this.b != null && Build.VERSION.SDK_INT >= 8) {
            WebSettings.PluginState valueOf = WebSettings.PluginState.valueOf(pluginState.name());
            v.a((Object) this.b, "setPluginState", (Class<?>[]) new Class[]{WebSettings.PluginState.class}, valueOf);
        }
    }

    @Deprecated
    public void setPluginsEnabled(boolean z) {
        if (this.c && this.a != null) {
            this.a.setPluginsEnabled(z);
        } else if (!this.c && this.b != null) {
            v.a((Object) this.b, "setPluginsEnabled", (Class<?>[]) new Class[]{Boolean.TYPE}, Boolean.valueOf(z));
        }
    }

    @Deprecated
    public synchronized void setPluginsPath(String str) {
        if (this.c && this.a != null) {
            this.a.setPluginsPath(str);
        } else if (!this.c && this.b != null) {
            v.a((Object) this.b, "setPluginsPath", (Class<?>[]) new Class[]{String.class}, str);
        }
    }

    public void setRenderPriority(RenderPriority renderPriority) {
        if (this.c && this.a != null) {
            this.a.setRenderPriority(IX5WebSettings.RenderPriority.valueOf(renderPriority.name()));
        } else if (!this.c && this.b != null) {
            this.b.setRenderPriority(WebSettings.RenderPriority.valueOf(renderPriority.name()));
        }
    }

    public synchronized void setSansSerifFontFamily(String str) {
        if (this.c && this.a != null) {
            this.a.setSansSerifFontFamily(str);
        } else if (!this.c && this.b != null) {
            this.b.setSansSerifFontFamily(str);
        }
    }

    public void setSaveFormData(boolean z) {
        if (this.c && this.a != null) {
            this.a.setSaveFormData(z);
        } else if (!this.c && this.b != null) {
            this.b.setSaveFormData(z);
        }
    }

    public void setSavePassword(boolean z) {
        if (this.c && this.a != null) {
            this.a.setSavePassword(z);
        } else if (!this.c && this.b != null) {
            this.b.setSavePassword(z);
        }
    }

    public synchronized void setSerifFontFamily(String str) {
        if (this.c && this.a != null) {
            this.a.setSerifFontFamily(str);
        } else if (!this.c && this.b != null) {
            this.b.setSerifFontFamily(str);
        }
    }

    public synchronized void setStandardFontFamily(String str) {
        if (this.c && this.a != null) {
            this.a.setStandardFontFamily(str);
        } else if (!this.c && this.b != null) {
            this.b.setStandardFontFamily(str);
        }
    }

    public void setSupportMultipleWindows(boolean z) {
        if (this.c && this.a != null) {
            this.a.setSupportMultipleWindows(z);
        } else if (!this.c && this.b != null) {
            this.b.setSupportMultipleWindows(z);
        }
    }

    public void setSupportZoom(boolean z) {
        if (this.c && this.a != null) {
            this.a.setSupportZoom(z);
        } else if (!this.c && this.b != null) {
            this.b.setSupportZoom(z);
        }
    }

    public void setTextSize(TextSize textSize) {
        if (this.c && this.a != null) {
            this.a.setTextSize(IX5WebSettings.TextSize.valueOf(textSize.name()));
        } else if (!this.c && this.b != null) {
            this.b.setTextSize(WebSettings.TextSize.valueOf(textSize.name()));
        }
    }

    @TargetApi(14)
    public synchronized void setTextZoom(int i) {
        if (this.c && this.a != null) {
            this.a.setTextZoom(i);
        } else if (!this.c && this.b != null && Build.VERSION.SDK_INT >= 14) {
            try {
                this.b.setTextZoom(i);
            } catch (Exception e) {
                v.a((Object) this.b, "setTextZoom", (Class<?>[]) new Class[]{Integer.TYPE}, Integer.valueOf(i));
            }
        }
        return;
    }

    @Deprecated
    public void setUseWebViewBackgroundForOverscrollBackground(boolean z) {
        if (this.c && this.a != null) {
            this.a.setUseWebViewBackgroundForOverscrollBackground(z);
        } else if (!this.c && this.b != null) {
            v.a((Object) this.b, "setUseWebViewBackgroundForOverscrollBackground", (Class<?>[]) new Class[]{Boolean.TYPE}, Boolean.valueOf(z));
        }
    }

    public void setUseWideViewPort(boolean z) {
        if (this.c && this.a != null) {
            this.a.setUseWideViewPort(z);
        } else if (!this.c && this.b != null) {
            this.b.setUseWideViewPort(z);
        }
    }

    public void setUserAgent(String str) {
        if (this.c && this.a != null) {
            this.a.setUserAgent(str);
        } else if (!this.c && this.b != null) {
            this.b.setUserAgentString(str);
        }
    }

    @TargetApi(3)
    public void setUserAgentString(String str) {
        if (this.c && this.a != null) {
            this.a.setUserAgentString(str);
        } else if (!this.c && this.b != null) {
            this.b.setUserAgentString(str);
        }
    }

    public synchronized boolean supportMultipleWindows() {
        return (!this.c || this.a == null) ? (this.c || this.b == null) ? false : this.b.supportMultipleWindows() : this.a.supportMultipleWindows();
    }

    public boolean supportZoom() {
        if (this.c && this.a != null) {
            return this.a.supportZoom();
        }
        if (this.c || this.b == null) {
            return false;
        }
        return this.b.supportZoom();
    }
}

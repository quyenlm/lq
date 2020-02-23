package com.tencent.component.plugin.server;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.component.debug.FileTracerReader;
import com.tencent.component.plugin.PluginFileLock;
import com.tencent.component.plugin.PluginInfo;
import com.tencent.component.plugin.PluginNativeHelper;
import com.tencent.component.plugin.PluginReporter;
import com.tencent.component.plugin.server.PluginValidator;
import com.tencent.component.utils.FileUtil;
import com.tencent.component.utils.UniqueLock;
import com.tencent.component.utils.log.LogUtil;
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;

class PluginLoader {
    private static final int LOAD_STRATEGY_ALL = 3;
    private static final int LOAD_STRATEGY_NON_CORE_PLUGIN = 2;
    private static final int LOAD_STRATEGY_ONLY_CORE_PLUGIN = 1;
    private static final String TAG = "PluginLoader";
    private final Context mContext;
    private final PlatformServerContext mPlatformServerContext;
    private final File mPluginDir;
    private final PluginManagerServer mPluginManagerServer;
    private final UniqueLock<String> mUniqueLock = new UniqueLock<>();

    public PluginLoader(PlatformServerContext platformServerContext) {
        this.mPlatformServerContext = platformServerContext;
        this.mContext = platformServerContext.getContext();
        this.mPluginDir = PluginConstant.getInstallDir(platformServerContext);
        this.mPluginManagerServer = platformServerContext.getPluginManagerServer();
    }

    /* access modifiers changed from: package-private */
    public final void load() {
        File[] files;
        File dir = this.mPluginDir;
        if (isDirValid(dir) && (files = dir.listFiles()) != null) {
            for (File file : files) {
                if (!isPluginNameValid(file)) {
                    String filename = file.getName();
                    LogUtil.d(TAG, "found old version plugin: " + filename);
                    String pluginName = null;
                    int dotIndex = filename != null ? filename.lastIndexOf(".") : -1;
                    if (dotIndex > 0 && filename != null) {
                        pluginName = filename.substring(0, dotIndex);
                    }
                    removeOldPlugin(pluginName);
                }
            }
            if (this.mPlatformServerContext.getPlatformConfig().enbaleCorePlugin) {
                HashMap<String, PluginInfo> pluginInfoTmpCache = new HashMap<>();
                LogUtil.i(TAG, "load core plugin first.");
                for (File file2 : files) {
                    if (isPluginNameValid(file2)) {
                        performLoad(file2, 1, pluginInfoTmpCache);
                    }
                }
                LogUtil.i(TAG, "load non core plugin.");
                for (File file3 : files) {
                    if (isPluginNameValid(file3)) {
                        performLoad(file3, 2, pluginInfoTmpCache);
                    }
                }
                return;
            }
            int length = files.length;
            for (int i = 0; i < length; i++) {
                File file4 = files[i];
                if (isPluginNameValid(file4)) {
                    LogUtil.d(TAG, "try load : " + (file4 != null ? file4.getAbsolutePath() : ""));
                    performLoad(file4, 3, (HashMap<String, PluginInfo>) null);
                }
            }
        }
    }

    private void removeOldPlugin(String pluginName) {
        File pluginNativeLibDir;
        if (!TextUtils.isEmpty(pluginName)) {
            File pluginZipFile = new File(pluginName + FileTracerReader.ZIP_FILE_EXT);
            removePlugin(pluginZipFile);
            LogUtil.d(TAG, "remove plugin: " + pluginZipFile.getName());
            File dexOptfile = new File(pluginName + PluginConstant.PLUGIN_INSTALL_DEX_OPT_SUFFIX);
            LogUtil.d(TAG, "remove plugin dex: " + dexOptfile.getName());
            FileUtil.delete(dexOptfile);
            String nativeLibDir = PluginConstant.getPluginNativeLibDir(this.mContext);
            if (nativeLibDir != null && (pluginNativeLibDir = new File(nativeLibDir, pluginName)) != null && pluginNativeLibDir.exists() && pluginNativeLibDir.isDirectory()) {
                LogUtil.d(TAG, "remove plugin native lib: " + pluginNativeLibDir.getName());
                FileUtil.delete(pluginNativeLibDir);
            }
        }
    }

    private boolean isPluginNameValid(File file) {
        if (file == null) {
            return false;
        }
        return PluginConstant.isFormatedInstalledPluginName(file.getName());
    }

    /* access modifiers changed from: package-private */
    public final void load(String id) {
        File[] files;
        File dir = this.mPluginDir;
        if (isDirValid(dir) && (files = dir.listFiles(PluginConstant.getPluginInstallNameFilter(id))) != null) {
            File file = selectLatestFile(files);
            if (isFileValid(file) && !this.mPluginManagerServer.isPluginRegistered(id)) {
                if (this.mPlatformServerContext.getPlatformConfig().enbaleCorePlugin) {
                    File[] allPluginFiles = dir.listFiles();
                    if (allPluginFiles != null) {
                        HashMap<String, PluginInfo> pluginInfoTmpCache = new HashMap<>();
                        for (File pluginFile : allPluginFiles) {
                            performLoad(pluginFile, 1, pluginInfoTmpCache);
                        }
                        performLoad(file, 3, pluginInfoTmpCache);
                        return;
                    }
                    return;
                }
                performLoad(file, 3, (HashMap<String, PluginInfo>) null);
            }
        }
    }

    private void performLoad(File file, int loadStrategy, HashMap<String, PluginInfo> pluginInfoCache) {
        if (!isFileValid(file)) {
            PluginReporter.report(PluginReporter.EVENT_LOAD, false, "invalid file", "file:" + file + (file != null ? ", exist:" + file.exists() + ", isFile:" + file.isFile() + ", length:" + file.length() : ""), (Throwable) null);
            return;
        }
        PluginInfo pluginInfo = null;
        String pluginPath = file.getAbsolutePath();
        Lock fileLock = PluginFileLock.readLock(pluginPath);
        fileLock.lock();
        if (pluginInfoCache != null) {
            try {
                pluginInfo = pluginInfoCache.get(pluginPath);
            } catch (Throwable th) {
                fileLock.unlock();
                throw th;
            }
        }
        if (pluginInfo == null) {
            pluginInfo = PluginParser.parse(this.mPlatformServerContext, this.mContext, file.getAbsolutePath(), 1);
            if (pluginInfoCache != null) {
                pluginInfoCache.put(pluginPath, pluginInfo);
            }
        }
        fileLock.unlock();
        if (pluginInfo == null || !pluginInfo.corePlugin) {
            if (loadStrategy == 1) {
                return;
            }
        } else if (loadStrategy == 2) {
            return;
        }
        try {
            PluginValidator.getInstance(this.mContext).validate(pluginInfo, this.mPlatformServerContext);
            Lock lock = this.mUniqueLock.obtain(pluginInfo.pluginId);
            lock.lock();
            try {
                if (!this.mPluginManagerServer.isPluginRegistered(pluginInfo.pluginId)) {
                    if (!verifyPluginFile(file, pluginInfo)) {
                        removePlugin(file);
                        PluginReporter.report(PluginReporter.EVENT_LOAD, false, "invalid plugin file", "file:" + file, (Throwable) null);
                        LogUtil.i(TAG, "plugin " + pluginInfo + " cannot pass the file verification");
                        lock.unlock();
                        return;
                    }
                    BuiltinPluginLoader builtinPluginLoader = this.mPlatformServerContext.getBuiltinPluginLoader();
                    PluginInstaller pluginInstaller = this.mPlatformServerContext.getPluginInstaller();
                    if (builtinPluginLoader.isNewer(pluginInfo)) {
                        pluginInstaller.uninstall(pluginInfo);
                        builtinPluginLoader.load(pluginInfo.pluginId);
                        lock.unlock();
                        return;
                    }
                    String nativeLibDir = pluginInfo.nativeLibraryDir;
                    if (nativeLibDir == null || PluginNativeHelper.copyNativeBinariesIfNeeded(file.getAbsolutePath(), nativeLibDir)) {
                        this.mPluginManagerServer.registerPlugin(pluginInfo.pluginId, pluginInfo);
                        PluginReporter.report(PluginReporter.EVENT_LOAD, true, "succeed", "plugin:" + pluginInfo, (Throwable) null);
                        LogUtil.i(TAG, "succeed to load plugin " + pluginInfo);
                        lock.unlock();
                        return;
                    }
                    PluginInfo fakePluginInfo = new PluginInfo();
                    fakePluginInfo.pluginId = pluginInfo.pluginId;
                    fakePluginInfo.uri = pluginInfo.uri != null ? pluginInfo.uri : PluginConstant.BUILTIN_DEFAULT_URI;
                    fakePluginInfo.version = pluginInfo.version;
                    this.mPluginManagerServer.registerPlugin(fakePluginInfo.pluginId, fakePluginInfo);
                    PluginReporter.report(PluginReporter.EVENT_LOAD, false, "fail to copy native libraries", "plugin:" + pluginInfo, (Throwable) null);
                    LogUtil.i(TAG, "cannot un-pack native libraries for plugin " + pluginInfo + ", file " + file);
                    lock.unlock();
                }
            } finally {
                lock.unlock();
            }
        } catch (PluginValidator.ValidateException e) {
            if (e instanceof PluginValidator.ValidateSignatureException) {
                removePlugin(file);
            }
            PluginReporter.report(PluginReporter.EVENT_LOAD, false, "verify error", "plugin:" + pluginInfo + ", file:" + file, e);
            LogUtil.i(TAG, e.getMessage());
        }
    }

    private static boolean verifyPluginFile(File file, PluginInfo pluginInfo) {
        String pluginName;
        if (file == null || pluginInfo == null || (pluginName = PluginConstant.getPluginInstallName(pluginInfo)) == null || !pluginName.equals(file.getName())) {
            return false;
        }
        return true;
    }

    private static File selectLatestFile(File[] files) {
        if (files == null) {
            return null;
        }
        File latestFile = null;
        for (File file : files) {
            if (file != null && (latestFile == null || latestFile.lastModified() < file.lastModified())) {
                latestFile = file;
            }
        }
        return latestFile;
    }

    private static void removePlugin(File file) {
        if (file != null) {
            FileUtil.delete(file);
        }
    }

    private static boolean isDirValid(File dir) {
        return dir != null && dir.isDirectory() && dir.exists();
    }

    private static boolean isFileValid(File file) {
        return file != null && file.isFile() && file.length() > 0;
    }
}

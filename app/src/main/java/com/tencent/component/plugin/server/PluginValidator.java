package com.tencent.component.plugin.server;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.os.Build;
import android.text.TextUtils;
import com.tencent.component.plugin.PluginInfo;
import com.tencent.component.utils.ApkUtil;
import com.tencent.component.utils.DebugUtil;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.tp.a.h;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class PluginValidator {
    private static final int SIGNATURE_FIRST_NOT_SIGNED = -1;
    private static final int SIGNATURE_MATCH = 0;
    private static final int SIGNATURE_NEITHER_SIGNED = -3;
    private static final int SIGNATURE_NOT_MATCH = 1;
    private static final int SIGNATURE_SECOND_NOT_SIGNED = -2;
    private static final String TAG = "PluginValidator";
    private static volatile PluginValidator sInstance;
    private final Context mContext;
    private volatile int[] mPlatformArchiveSignatureHash;
    private volatile PackageInfo mPlatformPackageInfo;

    private PluginValidator(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public void validate(PluginInfo pluginInfo, PlatformServerContext platformServerContext) throws ValidateException {
        if (pluginInfo == null) {
            throw new ValidateException("invalid parameter: null");
        } else if (TextUtils.isEmpty(pluginInfo.pluginClass)) {
            throw new ValidateException("plugin " + pluginInfo + " has invalid target plugin: " + pluginInfo.pluginClass);
        } else if (TextUtils.isEmpty(pluginInfo.pluginId)) {
            throw new ValidateException("plugin " + pluginInfo + " has invalid id: " + pluginInfo.pluginId);
        } else {
            if (!pluginInfo.isInternal() && !DebugUtil.isDebuggable()) {
                if (pluginInfo.signatures == null) {
                    throw new ValidateException("plugin " + pluginInfo + " has inconsistent signatures");
                } else if (compareSignatureHash(getSignatureHash(pluginInfo.signatures), getPlatformArchiveSignature(platformServerContext)) != 0) {
                    if (PluginConstant.shouldCheckPlatformSignature(this.mContext)) {
                        throw new ValidateSignatureException("plugin " + pluginInfo + " has mismatched signatures against platform, plugin(" + dumpSignature(getSignatureHash(pluginInfo.signatures)) + ") platformArchive(" + dumpSignature(getPlatformArchiveSignature(platformServerContext)) + h.b);
                    }
                    LogUtil.w(TAG, "plugin " + pluginInfo + " has mismatched signatures against platform");
                }
            }
            int minRequireVersion = pluginInfo.minBasePlatformVersion;
            int maxRequireVersion = pluginInfo.maxBasePlatformVersion;
            int currentVersion = pluginInfo.corePlugin ? 600 : getPlatformVersion(platformServerContext);
            if ((minRequireVersion <= 0 || minRequireVersion <= currentVersion) && (maxRequireVersion <= 0 || maxRequireVersion >= currentVersion)) {
                if (platformServerContext.getPlatformConfig().enbaleCorePlugin && !pluginInfo.corePlugin) {
                    PluginInfo.PluginRequirement pluginRequirement = pluginInfo.pluginRequirement;
                    int minRequireCorePluginVersion = pluginRequirement.minCorePluginVersion;
                    int maxRequireCorePluginVersion = pluginRequirement.maxCorePluginVersion;
                    if (pluginRequirement.requirementInfos == null || pluginRequirement.requirementInfos.isEmpty()) {
                        List<PluginInfo> corePluginList = platformServerContext.getPluginManagerServer().getAllCorePluginInfos();
                        if (corePluginList != null) {
                            for (PluginInfo corePluginInfo : corePluginList) {
                                if (corePluginInfo != null && ((minRequireCorePluginVersion > 0 && minRequireCorePluginVersion > corePluginInfo.version) || (maxRequireCorePluginVersion > 0 && maxRequireCorePluginVersion < corePluginInfo.version))) {
                                    throw new ValidateException("plugin " + pluginInfo + " require corePlugin[" + corePluginInfo.pluginId + "] version: (min:" + minRequireCorePluginVersion + ", max:" + maxRequireCorePluginVersion + "), current is " + corePluginInfo.version);
                                }
                            }
                        }
                    } else {
                        Iterator<PluginInfo.RequirementInfo> it = pluginRequirement.requirementInfos.iterator();
                        while (it.hasNext()) {
                            PluginInfo.RequirementInfo requirementInfo = it.next();
                            if (requirementInfo != null) {
                                LogUtil.i(TAG, pluginInfo + " requires " + requirementInfo.id);
                                PluginInfo requireCorePluginInfo = platformServerContext.getPluginManagerServer().getPluginInfo(requirementInfo.id);
                                int minRequireVersion2 = requirementInfo.minVersion;
                                if (minRequireVersion2 == 0 && minRequireCorePluginVersion != 0) {
                                    minRequireVersion2 = minRequireCorePluginVersion;
                                }
                                int maxRequireVersion2 = requirementInfo.maxVersion;
                                if (maxRequireVersion2 == 0 && maxRequireCorePluginVersion != 0) {
                                    maxRequireVersion2 = maxRequireCorePluginVersion;
                                }
                                if (requireCorePluginInfo == null) {
                                    throw new ValidateException("plugin " + pluginInfo + " require corePlugin[" + requirementInfo.id + "] not exist. ");
                                } else if ((minRequireVersion2 > 0 && minRequireVersion2 > requireCorePluginInfo.version) || (maxRequireVersion2 > 0 && maxRequireVersion2 < requireCorePluginInfo.maxAndroidVersion)) {
                                    throw new ValidateException("plugin " + pluginInfo + " require corePlugin[" + requireCorePluginInfo.pluginId + "] version: (min:" + minRequireVersion2 + ", max:" + maxRequireVersion2 + "), current is " + requireCorePluginInfo.version);
                                }
                            }
                        }
                    }
                }
                int minRequireAndroidVersion = pluginInfo.minAndroidVersion;
                int maxRequireAndroidVersion = pluginInfo.maxAndroidVersion;
                int currentAndroidVersion = Build.VERSION.SDK_INT;
                if ((minRequireAndroidVersion > 0 && minRequireAndroidVersion > currentAndroidVersion) || (maxRequireAndroidVersion > 0 && maxRequireAndroidVersion < currentAndroidVersion)) {
                    throw new ValidateException("plugin " + pluginInfo + " require android version: (min:" + minRequireAndroidVersion + ", max:" + maxRequireAndroidVersion + "), current is " + currentAndroidVersion);
                }
                return;
            }
            throw new ValidateException("plugin " + pluginInfo + " require pluginPlatform version: (min:" + minRequireVersion + ", max:" + maxRequireVersion + "), current is " + currentVersion);
        }
    }

    private int getPlatformVersion(PlatformServerContext platformServerContext) {
        return platformServerContext.getPlatformConfig().platformVersion;
    }

    @SuppressLint({"InlinedApi"})
    private int[] getPlatformArchiveSignature(PlatformServerContext platformServerContext) {
        String path;
        if (this.mPlatformArchiveSignatureHash != null) {
            return this.mPlatformArchiveSignatureHash;
        }
        synchronized (this) {
            if (this.mPlatformArchiveSignatureHash != null) {
                int[] iArr = this.mPlatformArchiveSignatureHash;
                return iArr;
            }
            try {
                this.mPlatformArchiveSignatureHash = platformServerContext.getPlatformConfig().platformSignatureHash;
                if (this.mPlatformArchiveSignatureHash == null) {
                    if (Build.VERSION.SDK_INT >= 8) {
                        path = this.mContext.getPackageCodePath();
                    } else {
                        PackageInfo pkgInfo = getPlatformPackageInfo();
                        path = (pkgInfo == null || pkgInfo.applicationInfo == null) ? null : pkgInfo.applicationInfo.sourceDir;
                    }
                    if (path != null) {
                        this.mPlatformArchiveSignatureHash = getSignatureHash(ApkUtil.Certificates.collectCertificates(path, ApkUtil.Certificates.MANIFEST_ENTRY));
                    }
                }
            } catch (Throwable th) {
            }
            int[] iArr2 = this.mPlatformArchiveSignatureHash;
            return iArr2;
        }
    }

    private static int[] getSignatureHash(Signature[] signatures) {
        if (signatures == null) {
            return null;
        }
        int[] signHashes = new int[signatures.length];
        for (int i = 0; i < signatures.length; i++) {
            if (signatures[i] != null) {
                signHashes[i] = signatures[i].hashCode();
            }
        }
        return signHashes;
    }

    private PackageInfo getPlatformPackageInfo() {
        if (this.mPlatformPackageInfo != null) {
            return this.mPlatformPackageInfo;
        }
        synchronized (this) {
            if (this.mPlatformPackageInfo != null) {
                PackageInfo packageInfo = this.mPlatformPackageInfo;
                return packageInfo;
            }
            try {
                this.mPlatformPackageInfo = this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0);
            } catch (Throwable th) {
            }
            PackageInfo packageInfo2 = this.mPlatformPackageInfo;
            return packageInfo2;
        }
    }

    private static int compareSignatureHash(int[] s1, int[] s2) {
        if (s1 == null) {
            if (s2 == null) {
                return -3;
            }
            return -1;
        } else if (s2 == null) {
            return -2;
        } else {
            HashSet<Integer> set1 = new HashSet<>();
            for (int sig : s1) {
                set1.add(Integer.valueOf(sig));
            }
            HashSet<Integer> set2 = new HashSet<>();
            for (int sig2 : s2) {
                set2.add(Integer.valueOf(sig2));
            }
            if (!set1.equals(set2)) {
                return 1;
            }
            return 0;
        }
    }

    private static String dumpSignature(int[] s) {
        if (s == null || s.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int ss : s) {
            sb.append(ss).append(' ');
        }
        return sb.toString();
    }

    public static class ValidateException extends Exception {
        public ValidateException(String msg) {
            super(msg);
        }
    }

    public static class ValidateSignatureException extends ValidateException {
        public ValidateSignatureException(String msg) {
            super(msg);
        }
    }

    public static PluginValidator getInstance(Context context) {
        if (sInstance != null) {
            return sInstance;
        }
        synchronized (PluginValidator.class) {
            if (sInstance != null) {
                PluginValidator pluginValidator = sInstance;
                return pluginValidator;
            }
            PluginValidator pluginValidator2 = new PluginValidator(context);
            sInstance = pluginValidator2;
            return pluginValidator2;
        }
    }
}

package com.tencent.component.plugin;

import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.tencent.component.annotation.PluginApi;
import com.tencent.component.plugin.annotation.CorePluginApi;
import com.tencent.component.utils.log.LogUtil;
import java.util.ArrayList;

@PluginApi(since = 6)
public class PluginInfo implements Parcelable {
    public static final Parcelable.Creator<PluginInfo> CREATOR = new Parcelable.Creator<PluginInfo>() {
        public PluginInfo createFromParcel(Parcel source) {
            boolean z;
            boolean z2;
            boolean z3 = true;
            PluginInfo pluginInfo = new PluginInfo();
            pluginInfo.pluginId = source.readString();
            pluginInfo.installPath = source.readString();
            pluginInfo.pluginClass = source.readString();
            pluginInfo.nativeLibraryDir = source.readString();
            pluginInfo.dexOptimizeDir = source.readString();
            pluginInfo.pluginRequirement = (PluginRequirement) source.readParcelable(getClass().getClassLoader());
            pluginInfo.minBasePlatformVersion = source.readInt();
            pluginInfo.maxBasePlatformVersion = source.readInt();
            pluginInfo.uri = (Uri) source.readParcelable(Uri.class.getClassLoader());
            pluginInfo.version = source.readInt();
            pluginInfo.versionName = source.readString();
            pluginInfo.pluginName = source.readString();
            pluginInfo.pluginIcon = source.readInt();
            pluginInfo.theme = source.readInt();
            pluginInfo.launchFragment = source.readString();
            pluginInfo.enabled = source.readInt() == 1;
            pluginInfo.signatures = (Signature[]) source.createTypedArray(Signature.CREATOR);
            pluginInfo.extraInfo.setTo((ExtraInfo) source.readParcelable(ExtraInfo.class.getClassLoader()));
            pluginInfo.minAndroidVersion = source.readInt();
            pluginInfo.maxAndroidVersion = source.readInt();
            pluginInfo.bootCompleteReceiver = source.readString();
            pluginInfo.surviveDetector = source.readString();
            if (source.readInt() == 1) {
                z = true;
            } else {
                z = false;
            }
            pluginInfo.surviveable = z;
            if (source.readInt() == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            pluginInfo.exclusive = z2;
            if (source.readInt() != 1) {
                z3 = false;
            }
            pluginInfo.corePlugin = z3;
            return pluginInfo;
        }

        public PluginInfo[] newArray(int size) {
            return new PluginInfo[size];
        }
    };
    public String bootCompleteReceiver;
    @CorePluginApi(since = 400)
    public boolean corePlugin = false;
    @PluginApi(since = 6)
    public String dexOptimizeDir;
    @CorePluginApi(since = 400)
    public boolean enabled;
    @CorePluginApi(since = 400)
    public boolean exclusive = false;
    public ExtraInfo extraInfo = new ExtraInfo();
    @PluginApi(since = 6)
    public String installPath;
    public String launchFragment;
    @PluginApi(since = 6)
    public int maxAndroidVersion;
    @PluginApi(since = 310)
    public int maxBasePlatformVersion;
    @PluginApi(since = 6)
    public int minAndroidVersion;
    @PluginApi(since = 310)
    public int minBasePlatformVersion;
    @PluginApi(since = 6)
    public String nativeLibraryDir;
    public String pluginClass;
    @CorePluginApi(since = 400)
    public int pluginIcon;
    @PluginApi(since = 6)
    public String pluginId;
    @CorePluginApi(since = 400)
    public String pluginName;
    public PluginRequirement pluginRequirement;
    public Signature[] signatures;
    public String surviveDetector;
    @CorePluginApi(since = 400)
    public boolean surviveable = true;
    @CorePluginApi(since = 400)
    public int theme;
    public Uri uri;
    @PluginApi(since = 6)
    public int version;
    @PluginApi(since = 6)
    public String versionName;

    public PluginInfo() {
    }

    public PluginInfo(PluginInfo orig) {
        this.installPath = orig.installPath;
        this.pluginClass = orig.pluginClass;
        this.bootCompleteReceiver = orig.bootCompleteReceiver;
        this.nativeLibraryDir = orig.nativeLibraryDir;
        this.dexOptimizeDir = orig.dexOptimizeDir;
        this.pluginRequirement = orig.pluginRequirement;
        this.minBasePlatformVersion = orig.minBasePlatformVersion;
        this.maxBasePlatformVersion = orig.maxBasePlatformVersion;
        this.minAndroidVersion = orig.minAndroidVersion;
        this.maxAndroidVersion = orig.maxAndroidVersion;
        this.pluginId = orig.pluginId;
        this.uri = orig.uri;
        this.version = orig.version;
        this.versionName = orig.versionName;
        this.pluginName = orig.pluginName;
        this.pluginIcon = orig.pluginIcon;
        this.theme = orig.theme;
        this.signatures = orig.signatures;
        this.launchFragment = orig.launchFragment;
        this.extraInfo = orig.extraInfo;
        this.enabled = orig.enabled;
        this.surviveDetector = orig.surviveDetector;
        this.surviveable = orig.surviveable;
        this.exclusive = orig.exclusive;
        this.corePlugin = orig.corePlugin;
    }

    @CorePluginApi(since = 400)
    public Drawable getIcon(PluginManager pluginManager) {
        Resources resources = pluginManager.getPluginResources(this);
        if (resources != null) {
            try {
                return resources.getDrawable(this.pluginIcon);
            } catch (Exception e) {
                LogUtil.e("PluginInfo", e.getMessage(), e);
            }
        }
        return null;
    }

    public boolean isInternal() {
        return TextUtils.isEmpty(this.installPath);
    }

    public String toString() {
        return "PluginInfo{" + this.pluginId + " " + this.version + " " + this.surviveable + "}";
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i;
        int i2;
        int i3 = 1;
        dest.writeString(this.pluginId);
        dest.writeString(this.installPath);
        dest.writeString(this.pluginClass);
        dest.writeString(this.nativeLibraryDir);
        dest.writeString(this.dexOptimizeDir);
        dest.writeParcelable(this.pluginRequirement, flags);
        dest.writeInt(this.minBasePlatformVersion);
        dest.writeInt(this.maxBasePlatformVersion);
        dest.writeParcelable(this.uri, flags);
        dest.writeInt(this.version);
        dest.writeString(this.versionName);
        dest.writeString(this.pluginName);
        dest.writeInt(this.pluginIcon);
        dest.writeInt(this.theme);
        dest.writeString(this.launchFragment);
        dest.writeInt(this.enabled ? 1 : 0);
        dest.writeTypedArray(this.signatures, flags);
        dest.writeParcelable(this.extraInfo, flags);
        dest.writeInt(this.minAndroidVersion);
        dest.writeInt(this.maxAndroidVersion);
        dest.writeString(this.bootCompleteReceiver);
        dest.writeString(this.surviveDetector);
        if (this.surviveable) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeInt(i);
        if (this.exclusive) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        dest.writeInt(i2);
        if (!this.corePlugin) {
            i3 = 0;
        }
        dest.writeInt(i3);
    }

    public static final class PluginRequirement implements Parcelable {
        public static final Parcelable.Creator<PluginRequirement> CREATOR = new Parcelable.Creator<PluginRequirement>() {
            public PluginRequirement createFromParcel(Parcel source) {
                return new PluginRequirement(source);
            }

            public PluginRequirement[] newArray(int size) {
                return new PluginRequirement[size];
            }
        };
        public int maxCorePluginVersion;
        public int minCorePluginVersion;
        public ArrayList<RequirementInfo> requirementInfos;

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeSerializable(this.requirementInfos);
            dest.writeInt(this.maxCorePluginVersion);
            dest.writeInt(this.minCorePluginVersion);
        }

        public PluginRequirement() {
        }

        private PluginRequirement(Parcel in) {
            this.requirementInfos = (ArrayList) in.readSerializable();
            this.maxCorePluginVersion = in.readInt();
            this.minCorePluginVersion = in.readInt();
        }
    }

    public static final class RequirementInfo implements Parcelable {
        public static final Parcelable.Creator<RequirementInfo> CREATOR = new Parcelable.Creator<RequirementInfo>() {
            public RequirementInfo createFromParcel(Parcel source) {
                return new RequirementInfo(source);
            }

            public RequirementInfo[] newArray(int size) {
                return new RequirementInfo[size];
            }
        };
        public String id;
        public int maxVersion;
        public int minVersion;

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeInt(this.minVersion);
            dest.writeInt(this.maxVersion);
        }

        public RequirementInfo() {
        }

        private RequirementInfo(Parcel in) {
            this.id = in.readString();
            this.minVersion = in.readInt();
            this.maxVersion = in.readInt();
        }
    }

    public static final class ExtraInfo implements Parcelable {
        public static final Parcelable.Creator<ExtraInfo> CREATOR = new Parcelable.Creator<ExtraInfo>() {
            public ExtraInfo createFromParcel(Parcel source) {
                return new ExtraInfo(source);
            }

            public ExtraInfo[] newArray(int size) {
                return new ExtraInfo[size];
            }
        };
        public static final int SINGLE_TOP_EXACTLY = 2;
        public static final int SINGLE_TOP_NONE = 0;
        public static final int SINGLE_TOP_STANDARD = 1;
        public boolean autoLoad;
        public Boolean liveUpdate;
        public boolean singleProcess;
        public int singleTop;

        public ExtraInfo() {
        }

        private ExtraInfo(Parcel parcel) {
            boolean z;
            Boolean valueOf;
            boolean z2 = true;
            this.singleTop = parcel.readInt();
            if (parcel.readInt() != 0) {
                z = true;
            } else {
                z = false;
            }
            this.singleProcess = z;
            int liveUpdateInt = parcel.readInt();
            if (liveUpdateInt == -1) {
                valueOf = null;
            } else {
                valueOf = Boolean.valueOf(liveUpdateInt != 0);
            }
            this.liveUpdate = valueOf;
            this.autoLoad = parcel.readInt() == 0 ? false : z2;
        }

        public void setTo(ExtraInfo src) {
            if (src != null) {
                this.singleTop = src.singleTop;
                this.singleProcess = src.singleProcess;
                this.liveUpdate = src.liveUpdate;
                this.autoLoad = src.autoLoad;
            }
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            int i;
            int i2 = 1;
            dest.writeInt(this.singleTop);
            if (this.singleProcess) {
                i = 1;
            } else {
                i = 0;
            }
            dest.writeInt(i);
            dest.writeInt(this.liveUpdate == null ? -1 : this.liveUpdate.booleanValue() ? 1 : 0);
            if (!this.autoLoad) {
                i2 = 0;
            }
            dest.writeInt(i2);
        }
    }
}

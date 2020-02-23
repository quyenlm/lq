package com.tencent.component.plugin;

import android.os.Parcel;
import android.os.Parcelable;
import com.tencent.component.plugin.annotation.CorePluginApi;

public class PluginPlatformConfig implements Parcelable {
    public static final Parcelable.Creator<PluginPlatformConfig> CREATOR = new Parcelable.Creator<PluginPlatformConfig>() {
        public PluginPlatformConfig createFromParcel(Parcel source) {
            PluginPlatformConfig config = new PluginPlatformConfig();
            config.platformId = source.readString();
            config.platformSignatureHash = source.createIntArray();
            config.platformVersion = source.readInt();
            config.pluginShellActivityClass = (Class) source.readSerializable();
            config.pluginTreeServiceClass = (Class) source.readSerializable();
            config.pluginProxyReceiver = (Class) source.readSerializable();
            config.enbaleCorePlugin = source.readInt() == 1;
            return config;
        }

        public PluginPlatformConfig[] newArray(int size) {
            return new PluginPlatformConfig[size];
        }
    };
    private static final int[] PLUGIN_PLATFROM_SIGNATURE_HASH = {2071990634};
    public boolean enbaleCorePlugin = true;
    @CorePluginApi(since = 400)
    public String platformId;
    public int[] platformSignatureHash = PLUGIN_PLATFROM_SIGNATURE_HASH;
    @CorePluginApi(since = 400)
    public int platformVersion = 600;
    @CorePluginApi(since = 400)
    public Class pluginProxyReceiver = PluginProxyReceiver.class;
    @CorePluginApi(since = 400)
    public Class pluginShellActivityClass = PluginShellActivity.class;
    @CorePluginApi(since = 400)
    public Class pluginTreeServiceClass = TreeService.class;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.platformId);
        dest.writeIntArray(this.platformSignatureHash);
        dest.writeInt(this.platformVersion);
        dest.writeSerializable(this.pluginShellActivityClass);
        dest.writeSerializable(this.pluginTreeServiceClass);
        dest.writeSerializable(this.pluginProxyReceiver);
        dest.writeInt(this.enbaleCorePlugin ? 1 : 0);
    }
}

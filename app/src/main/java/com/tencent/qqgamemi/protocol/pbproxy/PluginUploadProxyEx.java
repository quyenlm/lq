package com.tencent.qqgamemi.protocol.pbproxy;

import android.os.Build;
import com.qt.qq.SYRecordConf.GetUpgradeInfoReq;
import com.qt.qq.SYRecordConf.GetUpgradeInfoRsp;
import com.qt.qq.SYRecordConf.syrecordconf_cmd_types;
import com.qt.qq.SYRecordConf.syrecordconf_subcmd_types;
import com.squareup.wire.Wire;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.qqgamemi.BuildConfig;
import com.tencent.qqgamemi.mgc.core.MGCContext;
import com.tencent.qqgamemi.mgc.pb.ProtoUtils;
import com.tencent.qqgamemi.protocol.pbproxy.BaseProxy;
import okio.ByteString;

public class PluginUploadProxyEx extends BaseProxy<GetUpgradeInfoRsp> {
    private static final String TAG = "PluginUploadProxyEx";
    private int srpVersion;

    public PluginUploadProxyEx(BaseProxy.MessageListener messageListener, Object... object) {
        super(messageListener, GetUpgradeInfoRsp.class);
        this.srpVersion = object[0].intValue();
    }

    /* access modifiers changed from: protected */
    public int getCommand() {
        return syrecordconf_cmd_types.CMD_SYRECORDCONF.getValue();
    }

    /* access modifiers changed from: protected */
    public int getSubcmd() {
        return syrecordconf_subcmd_types.SUBCMD_GET_UPGRADE_INFO.getValue();
    }

    /* access modifiers changed from: protected */
    public byte[] getRequestContent() {
        GetUpgradeInfoReq.Builder builder = new GetUpgradeInfoReq.Builder();
        builder.pkg_name(ProtoUtils.safeEncodeUtf8(MGCContext.getConnectionManager().getContext().getPackageName()));
        builder.os_version(ProtoUtils.safeEncodeUtf8(Build.VERSION.RELEASE));
        builder.sdk_version(ProtoUtils.safeEncodeUtf8(String.valueOf(BuildConfig.VERSION_CODE)));
        builder.plugin_version(ProtoUtils.safeEncodeUtf8(String.valueOf(this.srpVersion)));
        builder.phone_type(ProtoUtils.safeEncodeUtf8(Build.MODEL));
        builder.source(2);
        return builder.build().toByteArray();
    }

    /* access modifiers changed from: protected */
    public void parseRspPB(GetUpgradeInfoRsp getUpgradeInfoRsp) {
        if (getUpgradeInfoRsp != null) {
            ProtoUtils.getWire();
            int status = ((Integer) Wire.get(getUpgradeInfoRsp.result, -1)).intValue();
            ProtoUtils.getWire();
            boolean isNeedUpload = ((Boolean) Wire.get(getUpgradeInfoRsp.need_upgrade, false)).booleanValue();
            String url = ProtoUtils.decodeString((ByteString) Wire.get(getUpgradeInfoRsp.url, null));
            LogUtil.i(TAG, "status :" + status + ",url:" + url + ",isNeedUpload:" + isNeedUpload);
            if (this.messageListener != null) {
                this.messageListener.onSuccess(url, Boolean.valueOf(isNeedUpload));
            }
        }
    }
}

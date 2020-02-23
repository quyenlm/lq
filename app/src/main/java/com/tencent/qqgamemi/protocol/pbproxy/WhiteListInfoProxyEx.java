package com.tencent.qqgamemi.protocol.pbproxy;

import android.os.Build;
import com.qt.qq.SYRecordConf.GetWhiteListInfoReq;
import com.qt.qq.SYRecordConf.GetWhiteListInfoRsp;
import com.qt.qq.SYRecordConf.syrecordconf_cmd_types;
import com.qt.qq.SYRecordConf.syrecordconf_subcmd_types;
import com.squareup.wire.Wire;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.qqgamemi.BuildConfig;
import com.tencent.qqgamemi.mgc.core.MGCContext;
import com.tencent.qqgamemi.mgc.pb.ProtoUtils;
import com.tencent.qqgamemi.protocol.pbproxy.BaseProxy;
import com.tencent.qqgamemi.util.DeviceDetectUtil;

public class WhiteListInfoProxyEx extends BaseProxy<GetWhiteListInfoRsp> {
    private static String TAG = "WhiteListInfoProxyEx";
    private int srp_verison;

    public WhiteListInfoProxyEx(BaseProxy.MessageListener messageListener, Object... object) {
        super(messageListener, GetWhiteListInfoRsp.class);
        if (object != null && object.length > 0) {
            try {
                this.srp_verison = object[0].intValue();
            } catch (Exception e) {
                LogUtil.e(TAG, "parse srpVersion fail : " + e.getMessage());
            }
        }
    }

    /* access modifiers changed from: protected */
    public int getCommand() {
        return syrecordconf_cmd_types.CMD_SYRECORDCONF.getValue();
    }

    /* access modifiers changed from: protected */
    public int getSubcmd() {
        return syrecordconf_subcmd_types.SUBCMD_GET_WHITELIST_INFO.getValue();
    }

    /* access modifiers changed from: protected */
    public byte[] getRequestContent() {
        GetWhiteListInfoReq.Builder builder = new GetWhiteListInfoReq.Builder();
        builder.pkg_name(ProtoUtils.safeEncodeUtf8(MGCContext.getConnectionManager().getContext().getPackageName()));
        builder.os_version(ProtoUtils.safeEncodeUtf8(Build.VERSION.RELEASE));
        builder.sdk_version(ProtoUtils.safeEncodeUtf8(String.valueOf(BuildConfig.VERSION_CODE)));
        builder.plugin_version(ProtoUtils.safeEncodeUtf8(String.valueOf(this.srp_verison)));
        builder.phone_type(ProtoUtils.safeEncodeUtf8(Build.MODEL));
        builder.source(2);
        builder.cpu_version(ProtoUtils.safeEncodeUtf8(DeviceDetectUtil.getCpuInfo()));
        builder.gpu_version(ProtoUtils.safeEncodeUtf8(DeviceDetectUtil.getGpuInfo()));
        return builder.build().toByteArray();
    }

    /* access modifiers changed from: protected */
    public void parseRspPB(GetWhiteListInfoRsp getWhiteListInfoRsp) {
        if (getWhiteListInfoRsp != null) {
            ProtoUtils.getWire();
            int status = ((Integer) Wire.get(getWhiteListInfoRsp.result, -1)).intValue();
            ProtoUtils.getWire();
            boolean isWhite = ((Boolean) Wire.get(getWhiteListInfoRsp.in_whitelist, false)).booleanValue();
            ProtoUtils.getWire();
            long switchs = ((Long) Wire.get(getWhiteListInfoRsp._switch, 0L)).longValue();
            ProtoUtils.getWire();
            int videoBusId = ((Integer) Wire.get(getWhiteListInfoRsp.bzid, 0)).intValue();
            int swithsInt = (int) (7 & switchs);
            LogUtil.i(TAG, "status :" + status + ",isWhite:" + isWhite + ",videoBusId:" + videoBusId + ",switchs:" + swithsInt);
            if (this.messageListener != null) {
                this.messageListener.onSuccess(Boolean.valueOf(isWhite), Integer.valueOf(videoBusId), Integer.valueOf(swithsInt));
            }
        }
    }
}

package com.tencent.tdm.system;

import android.content.Context;
import android.content.IntentFilter;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.tencent.tdm.database.TXDataBase;
import java.util.ArrayList;

public class TX {
    private static TX instance = new TX();
    private static final String tag = "TX";
    private Context mContext = null;
    private boolean mInitialized = false;
    private TXReceiver mNetworkReceiver = null;
    private String m_szAppChannel = null;
    private String m_szAppID = null;
    private String m_szAppVersion = null;
    private String m_szBrand = null;
    private String m_szBundleId = null;
    private String m_szCPUName = null;
    private int m_szCarrierType = 0;
    private String m_szDeviceID = null;
    private double m_szLatitude = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private double m_szLongitude = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private String m_szMacAddr = null;
    private String m_szModel = null;
    private String m_szNetProtocol = null;
    private String m_szRouterAddressFormal = null;
    private String m_szRouterAddressTest = null;
    private long m_szRouterTcpPortFormal = 0;
    private long m_szRouterTcpPortTest = 0;
    private long m_szRouterUdpPortFormal = 0;
    private long m_szRouterUdpPortTest = 0;
    private int m_szScreenHeight = 0;
    private int m_szScreenWidth = 0;
    private String m_szSysVersion = null;
    private boolean m_szTestMode = false;
    private long m_szTotalMemory = 0;
    private long m_szTotalSpace = 0;
    private String m_szUUID = null;

    private native void TXInit();

    private native void TXOnNetworkChanged(int i, String str);

    public native String TXEncryptField(String str, String str2);

    public static TX GetInstance() {
        return instance;
    }

    public void Initialize(Context context) {
        if (context == null) {
            TXLog.e(tag, "context is null. initialize failed!");
        } else if (!this.mInitialized) {
            TXLog.i(tag, "Initialize begin");
            this.mContext = context;
            TXDataBase.getInstance().initialize(this.mContext);
            SaveSystemInfo(this.mContext);
            TXInit();
            this.mInitialized = true;
            TXLog.i(tag, "Initialize end");
        }
    }

    public void RegisterReceiver() {
        if (this.mContext != null) {
            if (this.mNetworkReceiver == null) {
                this.mNetworkReceiver = new TXReceiver();
            }
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            try {
                this.mContext.registerReceiver(this.mNetworkReceiver, filter);
            } catch (Exception e) {
                TXLog.e(tag, "OnResume Exception");
                TXLog.i(tag, "Exception Track: " + e);
            }
        }
    }

    public void UnregisterReceiver() {
        if (this.mContext != null && this.mNetworkReceiver != null) {
            try {
                this.mContext.unregisterReceiver(this.mNetworkReceiver);
            } catch (Exception e) {
                TXLog.e(tag, "OnPause Exception");
                TXLog.i(tag, "Exception Track: " + e);
            }
        }
    }

    public void OnNetworkChanged(int netType, boolean refreshCarrier) {
        TXOnNetworkChanged(netType, refreshCarrier ? TXSystem.getInstance().GetSimOperator(this.mContext) : null);
    }

    public void SetLogLevel(int level) {
        TXLog.setLogLevel(level);
    }

    private void SaveSystemInfo(Context context) {
        this.m_szUUID = TXSystem.getInstance().GetUUID(context);
        this.m_szDeviceID = TXSystem.getInstance().GetDeviceID(context);
        this.m_szMacAddr = TXSystem.getInstance().GetMacAddress(context);
        this.m_szModel = TXSystem.getInstance().GetModel();
        this.m_szBrand = TXSystem.getInstance().GetBrand();
        this.m_szSysVersion = TXSystem.getInstance().GetSysVersion();
        this.m_szCPUName = TXSystem.getInstance().GetCPUName();
        this.m_szTotalMemory = TXSystem.getInstance().GetTotalMemory(context);
        this.m_szTotalSpace = TXSystem.getInstance().GetTotalSpace();
        this.m_szScreenHeight = TXSystem.getInstance().GetScreenHeight(context);
        this.m_szScreenWidth = TXSystem.getInstance().GetScreenWidth(context);
        this.m_szBundleId = TXSystem.getInstance().GetBundleId(context);
        this.m_szAppVersion = TXSystem.getInstance().GetAppVersion(context);
        this.m_szAppID = TXSystem.getInstance().GetMetaString(context, "GCloud.GCloud.GameId");
        if (this.m_szAppID == null || this.m_szAppID.isEmpty()) {
            this.m_szAppID = TXSystem.getInstance().GetMetaString(context, "GCloud.TDM.AppId");
        }
        this.m_szAppChannel = TXSystem.getInstance().GetMetaString(context, "GCloud.TDM.AppChannel");
        this.m_szNetProtocol = TXSystem.getInstance().GetMetaString(context, "GCloud.TDM.Protocol");
        this.m_szTestMode = TXSystem.getInstance().GetMetaBool(context, "GCloud.TDM.Test");
        this.m_szRouterAddressFormal = TXSystem.getInstance().GetMetaString(context, "GCloud.TDM.TGEMIT_ROUTER_ADDRESS_FORMAL");
        this.m_szRouterAddressTest = TXSystem.getInstance().GetMetaString(context, "GCloud.TDM.TGEMIT_ROUTER_ADDRESS_TEST");
        try {
            this.m_szRouterTcpPortFormal = Long.valueOf(TXSystem.getInstance().GetMetaString(context, "GCloud.TDM.TGEMIT_ROUTER_TCP_PORT_FORMAL")).longValue();
        } catch (Exception e) {
            this.m_szRouterTcpPortFormal = 0;
        }
        try {
            this.m_szRouterUdpPortFormal = Long.valueOf(TXSystem.getInstance().GetMetaString(context, "GCloud.TDM.TGEMIT_ROUTER_UDP_PORT_FORMAL")).longValue();
        } catch (Exception e2) {
            this.m_szRouterUdpPortFormal = 0;
        }
        try {
            this.m_szRouterTcpPortTest = Long.valueOf(TXSystem.getInstance().GetMetaString(context, "GCloud.TDM.TGEMIT_ROUTER_TCP_PORT_TEST")).longValue();
        } catch (Exception e3) {
            this.m_szRouterTcpPortTest = 0;
        }
        try {
            this.m_szRouterUdpPortTest = Long.valueOf(TXSystem.getInstance().GetMetaString(context, "GCloud.TDM.TGEMIT_ROUTER_UDP_PORT_TEST")).longValue();
        } catch (Exception e4) {
            this.m_szRouterUdpPortTest = 0;
        }
        TXLog.d(tag, "m_szRouterAddressFormal = " + this.m_szRouterAddressFormal);
        TXLog.d(tag, "m_szRouterAddressTest = " + this.m_szRouterAddressTest);
        TXLog.d(tag, "m_szRouterTcpPortFormal = " + this.m_szRouterTcpPortFormal + "");
        TXLog.d(tag, "m_szRouterUdpPortFormal = " + this.m_szRouterUdpPortFormal + "");
        TXLog.d(tag, "m_szRouterTcpPortTest = " + this.m_szRouterTcpPortTest + "");
        TXLog.d(tag, "m_szRouterUdpPortTest = " + this.m_szRouterUdpPortTest + "");
        OnNetworkChanged(TXSystem.getInstance().GetNetworkType(this.mContext).ordinal(), true);
        GetLocation();
    }

    private long GetAvailMemory() {
        return TXSystem.getInstance().GetAvailMemory(this.mContext);
    }

    private long GetAvailSpace() {
        return TXSystem.getInstance().GetAvailSpace();
    }

    private String GetAPKPath() {
        return TXSystem.getInstance().GetApkPath(this.mContext);
    }

    private String GetCommentInfo(String str) {
        int at;
        String info = TXSystem.getInstance().GetCommentInfo(str);
        if (!info.isEmpty() && (at = info.indexOf("c")) >= 0) {
            return info.substring(at);
        }
        return "";
    }

    private void GetLocation() {
        this.m_szLatitude = 404.0d;
        this.m_szLongitude = 404.0d;
    }

    private ArrayList<String> GetApps() {
        ArrayList<String> list = new ArrayList<>();
        TXSystem.getInstance().GetAppList(this.mContext, list);
        return list;
    }
}

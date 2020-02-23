package com.tencent.qqgamemi.mgc.connection;

import android.content.Context;
import android.os.Looper;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.qqgamemi.QMiConfig;
import com.tencent.qqgamemi.mgc.core.ChannelEventDispatcher;
import com.tencent.qqgamemi.mgc.core.MGCEnvironment;
import com.tencent.qqgamemi.protocol.pbproxy.PBProxyManager;
import com.tencent.qqgamemi.util.GlobalUtil;
import com.tencent.qqgamemi.util.StringUtils;
import com.tencent.qt.base.net.NetworkEngine;
import com.tencent.qt.base.net.PLog;
import java.io.File;

public class ConnectionManager {
    static final String LOG_TAG = "ConnectionMgr";
    private static final String QTX_DEFAULT_KEY = "19ai^R*p*-l#_,L<";
    private Context mContext;
    private ProtocolFlowMonitor mProtocolFlowMonitor;

    public ConnectionManager(Context context) {
        this.mContext = context;
    }

    public void init() {
        initNetworkEngine();
    }

    public Context getContext() {
        return this.mContext;
    }

    private void initNetworkEngine() {
        int logLevel = 0 != 0 ? 0 : 2;
        if (MGCEnvironment.getMgcExternalStorageDirectory(this.mContext) != null) {
            String logDirectory = new File(MGCEnvironment.getMgcExternalStorageDirectory(this.mContext), "log").getAbsolutePath();
            PLog.TraceMode traceMode = 0 != 0 ? PLog.TraceMode.all : PLog.TraceMode.offline;
            PLog.StoreMode storeMode = 0 != 0 ? PLog.StoreMode.fixed : PLog.StoreMode.fixed;
            NetworkEngine.enableLogging(true, logLevel);
            NetworkEngine.traceLogging(traceMode, storeMode, logDirectory);
        }
        int gameVersionCode = GlobalUtil.getCurGameNameVersionCode(this.mContext);
        int clientType = QMiConfig.getInstance().getClientType(this.mContext);
        LogUtil.i(LOG_TAG, "clientTypte:" + clientType);
        LogUtil.i(LOG_TAG, "networkEngine ret: " + NetworkEngine.init(this.mContext.getApplicationContext(), (Looper) null, clientType, gameVersionCode));
        NetworkEngine.shareEngine().addBroadcastHandler(new ChannelEventDispatcher());
        this.mProtocolFlowMonitor = new ProtocolFlowMonitor();
        NetworkEngine.shareEngine().setFlowController(this.mProtocolFlowMonitor);
        PBProxyManager.getInstance().connectionSuccessNotify();
    }

    public byte[] getDefaultKey() {
        return StringUtils.getUtf8(QTX_DEFAULT_KEY);
    }
}

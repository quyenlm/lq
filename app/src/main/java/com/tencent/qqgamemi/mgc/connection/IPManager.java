package com.tencent.qqgamemi.mgc.connection;

import android.content.Context;
import com.tencent.component.utils.UITools;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.qqgamemi.QMiConfig;
import com.tencent.qqgamemi.mgc.core.MGCContext;
import java.util.ArrayList;
import java.util.List;

public class IPManager {
    private static final String ACCESS_HOST_ADDR = "52.59.67.83";
    private static final int ACCESS_HOST_PORT1 = 80;
    private static final int ACCESS_HOST_PORT2 = 8000;
    private static final int ACCESS_HOST_PORT3 = 443;
    static String LOG_TAG = "IPManager";
    private static IPManager instance = null;
    private Context mContext;

    public static IPManager getInstance() {
        if (instance == null) {
            synchronized (LOG_TAG) {
                if (instance == null) {
                    instance = new IPManager();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        this.mContext = context;
    }

    public List<NetworkAddress> getAccessHosts() {
        String debugHost = MGCContext.getDebugConfig().getProperty("access_host");
        if (debugHost == null) {
            return buildAccessHosts();
        }
        LogUtil.w(LOG_TAG, "sdk use debug access host: " + debugHost);
        UITools.showDebugToast((CharSequence) "MGC: 接入QT测试服: " + debugHost);
        List<NetworkAddress> list = new ArrayList<>();
        list.add(new NetworkAddress(debugHost, 8000));
        return list;
    }

    private List<NetworkAddress> buildAccessHosts() {
        String ip = QMiConfig.getInstance().getAccessHostAddr(this.mContext);
        if (ip == null) {
            ip = ACCESS_HOST_ADDR;
        } else {
            LogUtil.i(LOG_TAG, "sdk_ip:" + ip);
        }
        int[] ports = QMiConfig.getInstance().getAccessHostPorts(this.mContext);
        if (ports == null || ports.length == 0) {
            ports = new int[]{80, 8000, ACCESS_HOST_PORT3};
        }
        int portsLength = ports.length;
        List<NetworkAddress> list = new ArrayList<>();
        for (int i = 0; i < portsLength; i++) {
            for (String networkAddress : new String[]{ip}) {
                list.add(new NetworkAddress(networkAddress, ports[i]));
            }
        }
        return list;
    }
}

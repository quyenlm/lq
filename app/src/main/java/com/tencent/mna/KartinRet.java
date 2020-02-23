package com.tencent.mna;

import com.tencent.tp.a.h;

public final class KartinRet {
    public static final int KARTIN_FLAG_2G_FAILED = -5;
    public static final int KARTIN_FLAG_FAILED = -6;
    public static final int KARTIN_FLAG_GET_DGN_SPEED_TESTER_FAILED = -501;
    public static final int KARTIN_FLAG_NETWORK_CHANGE_FAILED = -4;
    public static final int KARTIN_FLAG_NORMAL = 0;
    public static final int KARTIN_FLAG_NO_NET_FAILED = -1;
    public static final int KARTIN_FLAG_REQ_FAILED = -2;
    public static final String KARTIN_REASON_2G_FAILED = "2G网络下网速差";
    public static final String KARTIN_REASON_2G_FAILED_ENGLISH = "2G Network";
    public static final String KARTIN_REASON_CLOUD_REQ_FAILED = "请求云控失败";
    public static final String KARTIN_REASON_CLOUD_REQ_FAILED_ENGLISH = "Request Control Fail";
    public static final String KARTIN_REASON_FAILED = "失败";
    public static final String KARTIN_REASON_FAILED_ENGLISH = "Fail";
    public static final String KARTIN_REASON_GET_DGN_SPEED_TESTER_FAILED = "获取诊断协议失败";
    public static final String KARTIN_REASON_GET_DGN_SPEED_TESTER_FAILED_ENGLISH = "Get DgnSpeedTester Fail";
    public static final String KARTIN_REASON_MASTER_REQ_FAILED = "请求Master失败";
    public static final String KARTIN_REASON_MASTER_REQ_FAILED_ENGLISH = "Request Master Fail";
    public static final String KARTIN_REASON_NETWORK_CHANGE_FAILED = "查询过程网络类型切换";
    public static final String KARTIN_REASON_NETWORK_CHANGE_FAILED_ENGLISH = "Network Changed";
    public static final String KARTIN_REASON_NORMAL = "成功";
    public static final String KARTIN_REASON_NORMAL_ENGLISH = "Success";
    public static final String KARTIN_REASON_NO_NET_FAILED = "无网络";
    public static final String KARTIN_REASON_NO_NET_FAILED_ENGLISH = "No Network";
    public String desc = "解析错误";
    public String direct_desc = "";
    public int direct_status = -1;
    public String export_desc = "";
    public int export_status = -1;
    public int flag = -1;
    public int jump_direct = -1;
    public int jump_edge = -1;
    public int jump_export = -1;
    public int jump_network = -1;
    public int jump_proxy = -1;
    public int jump_router = -1;
    public int jump_signal = -1;
    public int jump_terminal = -1;
    public String netinfo_desc = "";
    public int netinfo_status = -1;
    public int network_star = 0;
    public String router_desc = "";
    public int router_status = -1;
    public String signal_desc = "";
    public int signal_status = -1;
    public String tag = "";
    public String terminal_desc = "";
    public int terminal_status = -1;
    public int wifi_num = 0;

    public KartinRet(String str) {
        this.tag = str;
    }

    public String toString() {
        return "tag:" + this.tag + ",flag:" + this.flag + ",desc:" + this.desc + ",detail(" + this.jump_network + "," + this.jump_signal + "," + this.signal_status + "," + this.signal_desc + "," + this.jump_router + "," + this.router_status + "," + this.router_desc + "," + this.jump_export + "," + this.export_status + "," + this.export_desc + "," + this.jump_terminal + "," + this.terminal_status + "," + this.terminal_desc + "," + this.jump_proxy + "," + this.jump_edge + "," + this.jump_direct + "," + this.direct_status + "," + this.direct_desc + h.b;
    }
}

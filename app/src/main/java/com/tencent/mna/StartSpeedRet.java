package com.tencent.mna;

public class StartSpeedRet {
    public static final int SPEED_AB_B = 10;
    public static final String SPEED_AB_B_DESC = "AB测试选择不加速";
    public static final int SPEED_CONTROLDNSFAILED = -8;
    public static final String SPEED_CONTROLDNSFAILED_DESC = "中控域名DNS请求失败";
    public static final int SPEED_CONTROLFAILED = -3;
    public static final String SPEED_CONTROLFAILED_DESC = "请求中控失败";
    public static final int SPEED_DNSFAILED = -6;
    public static final String SPEED_DNSFAILED_DESC = "GameServer DNS请求失败";
    public static final int SPEED_EXCEPTION = -402;
    public static final String SPEED_EXCEPTION_DESC = "加速异常";
    public static final int SPEED_FORCE_DIRECT = -16;
    public static final String SPEED_FORCE_DIRECT_DESC = "强制直连";
    public static final int SPEED_GETACCELERATORFAILED = -401;
    public static final String SPEED_GETACCELERATORFAILED_DESC = "获取加速协议失败";
    public static final int SPEED_HOOKFAILED = -5;
    public static final String SPEED_HOOKFAILED_DESC = "hook失败";
    public static final int SPEED_HOOKNOTSUPPORT = -13;
    public static final String SPEED_HOOKNOTSUPPORT_DESC = "hook不支持";
    public static final int SPEED_JVMFAILED = -1;
    public static final String SPEED_JVMFAILED_DESC = "初始化时获取JVM失败";
    public static final int SPEED_LOADFAILED = -7;
    public static final String SPEED_LOADFAILED_DESC = "没有成功加载so库";
    public static final int SPEED_MASTERFAILED = -11;
    public static final String SPEED_MASTERFAILED_DESC = "请求调度失败";
    public static final int SPEED_NEGFAILED = -12;
    public static final String SPEED_NEGFAILED_DESC = "请求协商失败";
    public static final int SPEED_NONEED = 2;
    public static final String SPEED_NONEED_DESC = "无需开启加速";
    public static final int SPEED_NONETOR2G = -2;
    public static final String SPEED_NONETOR2G_DESC = "Unknown/2G或者无网络";
    public static final int SPEED_NOTREACH = -4;
    public static final String SPEED_NOTREACH_DESC = "未达到加速条件";
    public static final int SPEED_STARTING = 1;
    public static final String SPEED_STARTING_DESC = "正在执行startSpeed";
    public static final int SPEED_STARTSPEEDTESTERTIMEOUT = -10;
    public static final String SPEED_STARTSPEEDTESTERTIMEOUT_DESC = "startSpeed测速阶段超时";
    public static final int SPEED_STARTSPEEDTREQTIMEOUT = -9;
    public static final String SPEED_STARTSPEEDTREQTIMEOUT_DESC = "startSpeed请求阶段超时";
    public static final int SPEED_SUCCEED = 0;
    public static final String SPEED_SUCCEED_DESC = "成功";
    public static final int SPEED_THROWABLE = -403;
    public static final String SPEED_THROWABLE_DESC = "加速错误";
    public String desc;
    public int flag;
    public int htype;
    public String vip;
    public int vport;

    public StartSpeedRet(String str, int i, int i2) {
        this(str, i, i2, -1, SPEED_JVMFAILED_DESC);
    }

    public StartSpeedRet(String str, int i, int i2, int i3, String str2) {
        this.flag = -1;
        this.desc = SPEED_JVMFAILED_DESC;
        this.vip = str;
        this.vport = i;
        this.htype = i2;
        this.flag = i3;
        this.desc = str2;
    }

    public static boolean isRequestControlSucceed(int i) {
        return (i == -402 || i == -403 || i == -7 || i == -2 || i == -6 || i == -3) ? false : true;
    }

    public static boolean isCanHook(int i) {
        return (!isRequestControlSucceed(i) || i == -401 || i == -5 || i == -13) ? false : true;
    }

    public static boolean isNegotiateForwardTunnelSucceed(int i) {
        return (!isRequestControlSucceed(i) || i == -401 || i == 2 || i == -11 || i == -12) ? false : true;
    }

    public static boolean isSpeedSucceed(int i) {
        return i == 0;
    }

    public static String getSpeedDesc(int i) {
        switch (i) {
            case SPEED_THROWABLE /*-403*/:
                return SPEED_THROWABLE_DESC;
            case SPEED_EXCEPTION /*-402*/:
                return SPEED_EXCEPTION_DESC;
            case SPEED_GETACCELERATORFAILED /*-401*/:
                return SPEED_GETACCELERATORFAILED_DESC;
            case -16:
                return SPEED_FORCE_DIRECT_DESC;
            case -13:
                return SPEED_HOOKNOTSUPPORT_DESC;
            case -12:
                return SPEED_NEGFAILED_DESC;
            case -11:
                return SPEED_MASTERFAILED_DESC;
            case -10:
                return SPEED_STARTSPEEDTESTERTIMEOUT_DESC;
            case -9:
                return SPEED_STARTSPEEDTREQTIMEOUT_DESC;
            case -8:
                return SPEED_CONTROLDNSFAILED_DESC;
            case -7:
                return SPEED_LOADFAILED_DESC;
            case -6:
                return SPEED_DNSFAILED_DESC;
            case -5:
                return SPEED_HOOKFAILED_DESC;
            case -4:
                return SPEED_NOTREACH_DESC;
            case -3:
                return SPEED_CONTROLFAILED_DESC;
            case -2:
                return SPEED_NONETOR2G_DESC;
            case -1:
                return SPEED_JVMFAILED_DESC;
            case 0:
                return "成功";
            case 1:
                return SPEED_STARTING_DESC;
            case 2:
                return SPEED_NONEED_DESC;
            case 10:
                return SPEED_AB_B_DESC;
            default:
                return "";
        }
    }
}

package com.tencent.tersafe2;

import com.tencent.tp.TssSdk;
import com.tencent.tp.TssSdkGameStatusInfo;
import com.tencent.tp.TssSdkInitInfo;
import com.tencent.tp.TssSdkUserInfoEx;
import com.tencent.tp.m;

public class TP2Sdk {
    public static int init(int i) {
        TssSdkInitInfo tssSdkInitInfo = new TssSdkInitInfo();
        tssSdkInitInfo.game_id = i;
        TssSdk.init(tssSdkInitInfo);
        return 0;
    }

    public static int initEx(int i, String str) {
        m.a("app_key:" + str);
        return init(i);
    }

    public static String ioctl(String str) {
        return TssSdk.a(str);
    }

    public static int onAppPause() {
        TssSdkGameStatusInfo tssSdkGameStatusInfo = new TssSdkGameStatusInfo();
        tssSdkGameStatusInfo.game_status = 2;
        TssSdk.setgamestatus(tssSdkGameStatusInfo);
        return 0;
    }

    public static int onAppResume() {
        TssSdkGameStatusInfo tssSdkGameStatusInfo = new TssSdkGameStatusInfo();
        tssSdkGameStatusInfo.game_status = 1;
        TssSdk.setgamestatus(tssSdkGameStatusInfo);
        return 0;
    }

    public static int onUserLogin(int i, int i2, String str, String str2) {
        TssSdkUserInfoEx tssSdkUserInfoEx = new TssSdkUserInfoEx();
        tssSdkUserInfoEx.entry_id = i;
        tssSdkUserInfoEx.uin_type = 2;
        tssSdkUserInfoEx.uin_int = 0;
        tssSdkUserInfoEx.uin_str = str;
        tssSdkUserInfoEx.app_id_type = 2;
        tssSdkUserInfoEx.app_id_int = 0;
        tssSdkUserInfoEx.app_id_str = "";
        tssSdkUserInfoEx.world_id = i2;
        tssSdkUserInfoEx.role_id = str2;
        TssSdk.setuserinfoex(tssSdkUserInfoEx);
        return 0;
    }

    public static int setPopupOptions(int i) {
        return -1;
    }
}

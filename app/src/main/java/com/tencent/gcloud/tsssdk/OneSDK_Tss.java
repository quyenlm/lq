package com.tencent.gcloud.tsssdk;

import android.content.Context;
import com.tencent.tp.TssIOCtlResult;
import com.tencent.tp.TssSdk;
import com.tencent.tp.TssSdkGameStatusInfo;
import com.tencent.tp.TssSdkInitInfo;
import com.tencent.tp.TssSdkUserInfo;

public class OneSDK_Tss {
    public static final int ENTRT_ID_FACEBOOK = 3;
    public static final int ENTRY_ID_GAMECENTER = 7;
    public static final int ENTRY_ID_GOOGLEPLAY = 8;
    public static final int ENTRY_ID_LINE = 5;
    public static final int ENTRY_ID_MM = 2;
    public static final int ENTRY_ID_OTHERS = 99;
    public static final int ENTRY_ID_QQ = 1;
    public static final int ENTRY_ID_TWITTER = 4;
    public static final int ENTRY_ID_VK = 9;
    public static final int ENTRY_ID_WHATSAPP = 6;

    public static byte[] getReportData() {
        TssIOCtlResult tssIOCtlResult = new TssIOCtlResult();
        tssIOCtlResult.cmd = "get_report_data";
        if (TssSdk.getsdkantidata(tssIOCtlResult) != 0) {
            return null;
        }
        return tssIOCtlResult.data;
    }

    public static void init(Context context, int i) {
        TssSdkInitInfo tssSdkInitInfo = new TssSdkInitInfo();
        tssSdkInitInfo.game_id = i;
        TssSdk.init(tssSdkInitInfo);
    }

    public static String ioctl(String str) {
        return TssSdk.a(str);
    }

    public static void onPause() {
        TssSdkGameStatusInfo tssSdkGameStatusInfo = new TssSdkGameStatusInfo();
        tssSdkGameStatusInfo.game_status = 2;
        TssSdk.setgamestatus(tssSdkGameStatusInfo);
    }

    public static void onRecvData(byte[] bArr) {
        TssSdk.senddatatosdk(bArr, bArr.length);
    }

    public static void onResume() {
        TssSdkGameStatusInfo tssSdkGameStatusInfo = new TssSdkGameStatusInfo();
        tssSdkGameStatusInfo.game_status = 1;
        TssSdk.setgamestatus(tssSdkGameStatusInfo);
    }

    public static void setUserInfo(int i, String str) {
        TssSdkUserInfo tssSdkUserInfo = new TssSdkUserInfo();
        tssSdkUserInfo.entry_id = i;
        tssSdkUserInfo.uin_type = 2;
        tssSdkUserInfo.uin_str = str;
        tssSdkUserInfo.app_id_type = 2;
        tssSdkUserInfo.app_id_str = "";
        TssSdk.setuserinfo(tssSdkUserInfo);
    }
}

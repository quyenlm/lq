package com.tencent.liteav.basic.module;

import MTT.EFvrECode;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class TXCStatus {
    private static native double nativeStatusGetDoubleValue(String str, int i);

    private static native long nativeStatusGetIntValue(String str, int i);

    private static native String nativeStatusGetStrValue(String str, int i);

    private static native boolean nativeStatusSetDoubleValue(String str, int i, double d);

    private static native boolean nativeStatusSetIntValue(String str, int i, long j);

    private static native boolean nativeStatusSetStrValue(String str, int i, String str2);

    private static native void nativeStatusStartRecord(String str);

    private static native void nativeStatusStopRecord(String str);

    public static void a(String str) {
        nativeStatusStartRecord(str);
    }

    public static void b(String str) {
        nativeStatusStopRecord(str);
    }

    public static boolean a(String str, int i, Object obj) {
        if (str == null || str.length() == 0 || obj == null) {
            return false;
        }
        switch (i) {
            case 1001:
            case 4001:
            case 5001:
            case GamesStatusCodes.STATUS_MULTIPLAYER_ERROR_INVALID_MULTIPLAYER_TYPE /*6002*/:
                if (obj instanceof Double) {
                    return nativeStatusSetDoubleValue(str, i, ((Double) obj).doubleValue());
                }
                return false;
            case 2001:
            case 2002:
            case 2003:
            case 2004:
            case 2005:
            case 2006:
            case 2007:
            case 2008:
            case 2010:
            case 2011:
            case 2012:
            case EFvrECode._ERR_FVR_NONSUPPORT /*2013*/:
            case EFvrECode._ERR_FVR_IMGCVT_EXCEPTION /*2014*/:
            case EFvrECode._ERR_FVR_IMGCVT_ERR /*2015*/:
            case 4002:
            case 4003:
            case 4004:
            case 5002:
            case GamesStatusCodes.STATUS_MULTIPLAYER_ERROR_NOT_TRUSTED_TESTER /*6001*/:
            case GamesStatusCodes.STATUS_MULTIPLAYER_DISABLED /*6003*/:
            case GamesStatusCodes.STATUS_MULTIPLAYER_ERROR_INVALID_OPERATION /*6004*/:
            case 6005:
            case 6006:
            case 6007:
            case 6008:
            case 6009:
            case 7112:
                if (obj instanceof Long) {
                    return nativeStatusSetIntValue(str, i, ((Long) obj).longValue());
                }
                return false;
            case GamesStatusCodes.STATUS_REAL_TIME_MESSAGE_SEND_FAILED /*7001*/:
            case GamesStatusCodes.STATUS_INVALID_REAL_TIME_ROOM_ID /*7002*/:
            case GamesStatusCodes.STATUS_PARTICIPANT_NOT_CONNECTED /*7003*/:
            case GamesStatusCodes.STATUS_REAL_TIME_ROOM_NOT_JOINED /*7004*/:
            case GamesStatusCodes.STATUS_REAL_TIME_INACTIVE_ROOM /*7005*/:
            case 7006:
            case GamesStatusCodes.STATUS_OPERATION_IN_FLIGHT /*7007*/:
            case 7008:
            case 7009:
            case 7010:
            case 7011:
            case 7013:
            case 7016:
            case 7017:
            case 7018:
            case 7101:
            case 7102:
            case 7103:
            case 7104:
            case 7105:
            case 7107:
            case 7108:
            case 7109:
            case 7111:
            case 7116:
            case 7117:
            case 7118:
                if (obj instanceof Long) {
                    return nativeStatusSetIntValue(str, i, ((Long) obj).longValue());
                }
                return false;
            case 7012:
            case 7014:
            case 7015:
            case 7106:
            case 7110:
            case 7113:
            case 7114:
            case 7115:
                if (obj instanceof String) {
                    return nativeStatusSetStrValue(str, i, (String) obj);
                }
                return false;
            default:
                return false;
        }
    }

    public static Object a(String str, int i) {
        if (str == null || str.length() == 0) {
            return null;
        }
        switch (i) {
            case 1001:
            case 4001:
            case 5001:
            case GamesStatusCodes.STATUS_MULTIPLAYER_ERROR_INVALID_MULTIPLAYER_TYPE /*6002*/:
                return Double.valueOf(nativeStatusGetDoubleValue(str, i));
            case 2001:
            case 2002:
            case 2003:
            case 2004:
            case 2005:
            case 2006:
            case 2007:
            case 2008:
            case 2010:
            case 2011:
            case 2012:
            case EFvrECode._ERR_FVR_NONSUPPORT /*2013*/:
            case EFvrECode._ERR_FVR_IMGCVT_EXCEPTION /*2014*/:
            case EFvrECode._ERR_FVR_IMGCVT_ERR /*2015*/:
            case 4002:
            case 4003:
            case 4004:
            case 5002:
            case GamesStatusCodes.STATUS_MULTIPLAYER_ERROR_NOT_TRUSTED_TESTER /*6001*/:
            case GamesStatusCodes.STATUS_MULTIPLAYER_DISABLED /*6003*/:
            case GamesStatusCodes.STATUS_MULTIPLAYER_ERROR_INVALID_OPERATION /*6004*/:
            case 6005:
            case 6006:
            case 6007:
            case 6008:
            case 6009:
            case 7112:
                return Long.valueOf(nativeStatusGetIntValue(str, i));
            case GamesStatusCodes.STATUS_REAL_TIME_MESSAGE_SEND_FAILED /*7001*/:
            case GamesStatusCodes.STATUS_INVALID_REAL_TIME_ROOM_ID /*7002*/:
            case GamesStatusCodes.STATUS_PARTICIPANT_NOT_CONNECTED /*7003*/:
            case GamesStatusCodes.STATUS_REAL_TIME_ROOM_NOT_JOINED /*7004*/:
            case GamesStatusCodes.STATUS_REAL_TIME_INACTIVE_ROOM /*7005*/:
            case 7006:
            case GamesStatusCodes.STATUS_OPERATION_IN_FLIGHT /*7007*/:
            case 7008:
            case 7009:
            case 7010:
            case 7011:
            case 7013:
            case 7016:
            case 7017:
            case 7018:
            case 7101:
            case 7102:
            case 7103:
            case 7104:
            case 7105:
            case 7107:
            case 7108:
            case 7109:
            case 7111:
            case 7116:
            case 7117:
            case 7118:
                return Long.valueOf(nativeStatusGetIntValue(str, i));
            case 7012:
            case 7014:
            case 7015:
            case 7106:
            case 7110:
            case 7113:
            case 7114:
            case 7115:
                return nativeStatusGetStrValue(str, i);
            default:
                return null;
        }
    }

    public static long b(String str, int i) {
        Object a = a(str, i);
        if (a == null || !(a instanceof Long)) {
            return 0;
        }
        return ((Long) a).longValue();
    }

    public static String c(String str, int i) {
        Object a = a(str, i);
        if (a == null || !(a instanceof String)) {
            return "";
        }
        return (String) a;
    }

    public static int d(String str, int i) {
        Object a = a(str, i);
        if (a == null || !(a instanceof Long)) {
            return 0;
        }
        return ((Long) a).intValue();
    }

    public static double e(String str, int i) {
        Object a = a(str, i);
        if (a == null || !(a instanceof Double)) {
            return FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        }
        return ((Double) a).doubleValue();
    }
}

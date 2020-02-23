package com.google.android.gms.drive;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.tencent.imsdk.IMErrorDef;

public final class DriveStatusCodes extends CommonStatusCodes {
    public static final int DRIVE_CONTENTS_TOO_LARGE = 1508;
    @Deprecated
    public static final int DRIVE_EXTERNAL_STORAGE_REQUIRED = 1500;
    public static final int DRIVE_RATE_LIMIT_EXCEEDED = 1507;
    public static final int DRIVE_RESOURCE_NOT_AVAILABLE = 1502;

    private DriveStatusCodes() {
    }

    public static String getStatusCodeString(int i) {
        switch (i) {
            case IMErrorDef.Viber_INTERNAL_FAILURE /*1501*/:
                return "DRIVE_RESOURCE_ALREADY_EXISTS";
            case 1502:
                return "DRIVE_RESOURCE_NOT_AVAILABLE";
            case IMErrorDef.Viber_NOT_PERMITTED /*1503*/:
                return "DRIVE_RESOURCE_FORBIDDEN";
            case IMErrorDef.Viber_NOT_REGISTERED /*1504*/:
                return "DRIVE_REALTIME_CONCURRENT_CREATION";
            case IMErrorDef.Viber_NOT_AUTHENTICATED /*1505*/:
                return "DRIVE_REALTIME_INVALID_COMPOUND_OP";
            case IMErrorDef.Viber_RECEIVER_NOT_REG /*1506*/:
                return "DRIVE_FULL_SYNC_REQUIRED";
            case 1507:
                return "DRIVE_RATE_LIMIT_EXCEEDED";
            case 1508:
                return "DRIVE_CONTENTS_TOO_LARGE";
            case IMErrorDef.Viber_INVALID_DESTINATION /*1509*/:
                return "DRIVE_RESOURCE_PERMISSION_FORBIDDEN";
            case IMErrorDef.Viber_NO_SUITABLE_DEVICE /*1510*/:
                return "DRIVE_INAPPLICABLE_OPERATION";
            case IMErrorDef.Viber_APPS_API_BAD_PARAMETERS /*1511*/:
                return "DRIVE_INSUFFICIENT_SCOPES";
            case 3004:
                return "DRIVE_REALTIME_TOKEN_REFRESH_REQUIRED";
            default:
                return CommonStatusCodes.getStatusCodeString(i);
        }
    }
}

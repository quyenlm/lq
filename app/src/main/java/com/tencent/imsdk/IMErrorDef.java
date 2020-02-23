package com.tencent.imsdk;

import com.facebook.internal.AnalyticsEvents;
import com.tencent.imsdk.tool.etc.IMLogger;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Hashtable;

public final class IMErrorDef {
    @IMErrorMessage("Bind target already logged in")
    public static final int ALREADY_LOGIN = 1005;
    @IMErrorMessage("User canceled")
    public static final int CANCELED = 2;
    @IMErrorMessage("Login permission error")
    public static final int EPERMISSION = 1001;
    @IMErrorMessage("Filesystem error")
    public static final int FILESYSTEM = 8;
    @IMErrorMessage("Need Facebook app")
    public static final int NEEDFACEBOOK = 1007;
    @IMErrorMessage("Need GMS")
    public static final int NEEDGMS = 1004;
    @IMErrorMessage("Bind need guid")
    public static final int NEEDGUID = 1003;
    @IMErrorMessage("Strict login need user data on server")
    public static final int NEEDUSERDATA = 1006;
    @IMErrorMessage("No support IMCommon")
    public static final int NEEDWECHATAAPI = 4001;
    @IMErrorMessage("Need install wechat app")
    public static final int NEEDWECHATAPP = 4000;
    @IMErrorMessage("Need Viber app")
    public static final int NEED_VIBER = 1008;
    @IMErrorMessage("Network error")
    public static final int NETWORK = 4;
    @IMErrorMessage("Not login yet")
    public static final int NOLOGIN = 10;
    @IMErrorMessage("No plugin package or no channel")
    public static final int NOPACKAGE = 9;
    @IMErrorMessage("No support")
    public static final int NOSUPPORT = 7;
    @IMErrorMessage("Initialize not executed")
    public static final int NOT_INITIALIZE = 11;
    @IMErrorMessage("No user found")
    public static final int NOUSERDATA = 1002;
    @IMErrorMessage("Quick Login data error")
    public static final int QUICKLOGINDATA = 1000;
    @IMErrorMessage("Server error")
    public static final int SERVER = 5;
    @IMErrorMessage("Success")
    public static final int SUCCESS = 1;
    @IMErrorMessage("System error")
    public static final int SYSTEM = 3;
    @IMErrorMessage("Timeout")
    public static final int TIMEOUT = 6;
    @IMErrorMessage("User denied")
    public static final int USER_DENIED = 11;
    public static final int VIBER_OFFSET = 1500;
    @IMErrorMessage("Receiver of message blocked app")
    public static final int Viber_APPS_API_APP_BLOCKED = 1512;
    @IMErrorMessage("Message parameters are not suitable for message type")
    public static final int Viber_APPS_API_BAD_PARAMETERS = 1511;
    @IMErrorMessage("Specific message type was blocked by the system.")
    public static final int Viber_APPS_API_MESSAGE_TYPE_BLOCKED = 1514;
    @IMErrorMessage("Time out (Viber client related)")
    public static final int Viber_APPS_API_TIMEOUT = 1513;
    @IMErrorMessage("Cannot complete operation because of a bad app ID.")
    public static final int Viber_BAD_APPID = 1502;
    @IMErrorMessage("Internal failure")
    public static final int Viber_INTERNAL_FAILURE = 1501;
    @IMErrorMessage("Tried to send invalid data (text is too long, text contain link, bad url scheme, bat image url etc.)")
    public static final int Viber_INVALID_DATA = 1507;
    @IMErrorMessage("Message destination does not match message type.")
    public static final int Viber_INVALID_DESTINATION = 1509;
    @IMErrorMessage("Message type does not exist.")
    public static final int Viber_INVALID_MESSAGE_TYPE = 1508;
    @IMErrorMessage("User is not authenticated to this app")
    public static final int Viber_NOT_AUTHENTICATED = 1505;
    @IMErrorMessage("Action is not included in the permissions granted.")
    public static final int Viber_NOT_PERMITTED = 1503;
    @IMErrorMessage("User is not registered to Viber.")
    public static final int Viber_NOT_REGISTERED = 1504;
    @IMErrorMessage("Message destination does not have a suitable device to use app.")
    public static final int Viber_NO_SUITABLE_DEVICE = 1510;
    @IMErrorMessage("Failed to send a message because receiver was not registered to Viber or receiver's data was missing.")
    public static final int Viber_RECEIVER_NOT_REG = 1506;
    @IMErrorMessage("Post data is missing")
    public static final int Viber_REST_STATUS_NO_POST_DATA = 1300;
    @IMErrorMessage("Success")
    public static final int Viber_SUCCESS = 1500;
    private static volatile Hashtable<Integer, String> errorDict = null;
    private static Object mLock = new Object();

    public static String getErrorString(int code) {
        return getErrorString(code, (String) null);
    }

    public static String getErrorString(int code, String defaultValue) {
        synchronized (mLock) {
            if (errorDict == null) {
                errorDict = new Hashtable<>();
                for (Field field : IMErrorDef.class.getDeclaredFields()) {
                    if (Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers()) && field.isAnnotationPresent(IMErrorMessage.class)) {
                        try {
                            errorDict.put(Integer.valueOf(field.getInt((Object) null)), ((IMErrorMessage) field.getAnnotation(IMErrorMessage.class)).value());
                        } catch (Exception e) {
                            IMLogger.e(e.getMessage());
                        }
                    }
                }
            }
        }
        if (errorDict.containsKey(Integer.valueOf(code))) {
            return errorDict.get(Integer.valueOf(code));
        }
        if (defaultValue == null || defaultValue.length() <= 0) {
            return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
        return defaultValue;
    }
}

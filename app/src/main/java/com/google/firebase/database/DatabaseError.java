package com.google.firebase.database;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class DatabaseError {
    public static final int DATA_STALE = -1;
    public static final int DISCONNECTED = -4;
    public static final int EXPIRED_TOKEN = -6;
    public static final int INVALID_TOKEN = -7;
    public static final int MAX_RETRIES = -8;
    public static final int NETWORK_ERROR = -24;
    public static final int OPERATION_FAILED = -2;
    public static final int OVERRIDDEN_BY_SET = -9;
    public static final int PERMISSION_DENIED = -3;
    public static final int UNAVAILABLE = -10;
    public static final int UNKNOWN_ERROR = -999;
    public static final int USER_CODE_EXCEPTION = -11;
    public static final int WRITE_CANCELED = -25;
    private static final Map<Integer, String> zzbYI;
    private static final Map<String, Integer> zzbYJ;
    private final int code;
    private final String message;
    private final String zzbYK;

    static {
        HashMap hashMap = new HashMap();
        zzbYI = hashMap;
        hashMap.put(-1, "The transaction needs to be run again with current data");
        zzbYI.put(-2, "The server indicated that this operation failed");
        zzbYI.put(-3, "This client does not have permission to perform this operation");
        zzbYI.put(-4, "The operation had to be aborted due to a network disconnect");
        zzbYI.put(-6, "The supplied auth token has expired");
        zzbYI.put(-7, "The supplied auth token was invalid");
        zzbYI.put(-8, "The transaction had too many retries");
        zzbYI.put(-9, "The transaction was overridden by a subsequent set");
        zzbYI.put(-10, "The service is unavailable");
        zzbYI.put(-11, "User code called from the Firebase Database runloop threw an exception:\n");
        zzbYI.put(-24, "The operation could not be performed due to a network error");
        zzbYI.put(-25, "The write was canceled by the user.");
        zzbYI.put(Integer.valueOf(UNKNOWN_ERROR), "An unknown error occurred");
        HashMap hashMap2 = new HashMap();
        zzbYJ = hashMap2;
        hashMap2.put("datastale", -1);
        zzbYJ.put("failure", -2);
        zzbYJ.put("permission_denied", -3);
        zzbYJ.put("disconnected", -4);
        zzbYJ.put("expired_token", -6);
        zzbYJ.put("invalid_token", -7);
        zzbYJ.put("maxretries", -8);
        zzbYJ.put("overriddenbyset", -9);
        zzbYJ.put("unavailable", -10);
        zzbYJ.put("network_error", -24);
        zzbYJ.put("write_canceled", -25);
    }

    private DatabaseError(int i, String str) {
        this(-11, str, (String) null);
    }

    private DatabaseError(int i, String str, String str2) {
        this.code = i;
        this.message = str;
        this.zzbYK = "";
    }

    public static DatabaseError fromException(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        String valueOf = String.valueOf(zzbYI.get(-11));
        String valueOf2 = String.valueOf(stringWriter.toString());
        return new DatabaseError(-11, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
    }

    public static DatabaseError zzZ(String str, String str2) {
        Integer num = zzbYJ.get(str.toLowerCase());
        Integer valueOf = num == null ? Integer.valueOf(UNKNOWN_ERROR) : num;
        return new DatabaseError(valueOf.intValue(), str2 == null ? zzbYI.get(valueOf) : str2, (String) null);
    }

    public static DatabaseError zzbU(int i) {
        if (zzbYI.containsKey(-25)) {
            return new DatabaseError(-25, zzbYI.get(-25), (String) null);
        }
        throw new IllegalArgumentException(new StringBuilder(49).append("Invalid Firebase Database error code: -25").toString());
    }

    public static DatabaseError zzgA(String str) {
        Integer num = zzbYJ.get(str.toLowerCase());
        Integer valueOf = num == null ? Integer.valueOf(UNKNOWN_ERROR) : num;
        return new DatabaseError(valueOf.intValue(), zzbYI.get(valueOf), (String) null);
    }

    public int getCode() {
        return this.code;
    }

    public String getDetails() {
        return this.zzbYK;
    }

    public String getMessage() {
        return this.message;
    }

    public DatabaseException toException() {
        String valueOf = String.valueOf(this.message);
        return new DatabaseException(valueOf.length() != 0 ? "Firebase Database error: ".concat(valueOf) : new String("Firebase Database error: "));
    }

    public String toString() {
        String valueOf = String.valueOf(this.message);
        return valueOf.length() != 0 ? "DatabaseError: ".concat(valueOf) : new String("DatabaseError: ");
    }
}

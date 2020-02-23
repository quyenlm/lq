package com.tencent.component.debug;

public interface TraceLevel {
    public static final int ASSERT = 32;
    public static final int DEBUG = 2;
    public static final int DEBUG_AND_BELOW = 62;
    public static final int ERROR = 16;
    public static final int ERROR_AND_BELOW = 48;
    public static final int INFO = 4;
    public static final int INFO_AND_BELOW = 60;
    public static final int VERBOSE = 1;
    public static final int VERBOSE_AND_BELOW = 63;
    public static final int WARN = 8;
    public static final int WARN_AND_BELOW = 56;
}

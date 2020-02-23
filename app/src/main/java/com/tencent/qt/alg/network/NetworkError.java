package com.tencent.qt.alg.network;

public interface NetworkError {
    public static final int CANCEL = -5;
    public static final int FAIL_CONNECT_TIMEOUT = -2;
    public static final int FAIL_IO_ERROR = -4;
    public static final int FAIL_NOT_FOUND = -3;
    public static final int FAIL_UNKNOWN = -1;
    public static final int NO_AVALIABLE_NETWORK = -6;
    public static final int SUCCESS = 0;
}

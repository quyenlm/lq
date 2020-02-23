package com.tencent.mna;

public interface NetworkBindingListener {
    public static final int NB_BIND_EXCEPTION = -12;
    public static final int NB_BIND_FAIL = -11;
    public static final int NB_BUFFER_SCORE_LIMIT = -2;
    public static final int NB_PLAYER_LIMIT = -1;
    public static final int NB_PREPARE_ACCPLAT_NOTSUPPORT = -101;
    public static final int NB_PREPARE_BIND_FD_FAIL = -107;
    public static final int NB_PREPARE_CONTROL_OR_HOOK_FAIL = -108;
    public static final int NB_PREPARE_EXCEPTION = -109;
    public static final int NB_PREPARE_GEN_FD_FAIL = -106;
    public static final int NB_PREPARE_GET_NET_ID_FAIL = -104;
    public static final int NB_PREPARE_MATCHMODE_NOTSUPPORT = -110;
    public static final int NB_PREPARE_NOT_EXECUTE = -100;
    public static final int NB_PREPARE_REFLECTION_FAIL = -105;
    public static final int NB_PREPARE_SYSTEM_VERSION_TOO_LOW = -103;
    public static final int NB_PREPARE_WIFI_DISABLED = -102;
    public static final int NB_SPEED_TEST_EXCEPTION = -3;
    public static final int NB_TO_4G_DELAY_LIMIT = -7;
    public static final int NB_TO_4G_FAIL_FOR_AB = 2;
    public static final int NB_TO_4G_SIGNAL_LIMIT = -6;
    public static final int NB_TO_4G_SUCCESS = 1;
    public static final int NB_TO_4G_TIMES_LIMIT = -5;
    public static final int NB_TO_WIFI_DELAY_LIMIT = -10;
    public static final int NB_TO_WIFI_SIGNAL_LIMIT = -9;
    public static final int NB_TO_WIFI_SUCCESS = 0;
    public static final int NB_TO_WIFI_TIMES_LIMIT = -8;
    public static final int NB_WIFI_DISABLED_OR_CONTROL = -4;

    void onBinding(int i, boolean z);
}

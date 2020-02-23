package com.tencent.kgvmp.a;

public enum c {
    OPEN_ID(0),
    MAIN_VERCODE(1),
    SUB_VERCODE(2),
    TIME_STAMP(3),
    SCENE(4),
    FPS(5),
    FRAME_MISS(6),
    FPS_TARGET(7),
    MODEL_LEVEL(8),
    EFFECT_LEVEL(9),
    HD_MODEL(10),
    USERS_COUNT(11),
    NET_LATENCY(12),
    RECORDING(13),
    URGENT_SIGNAL(14),
    SERVER_IP(15),
    ROLE_STATUS(16),
    CPU_RATE(17),
    USED_MEM(18),
    BATTERY_TEMP(19),
    FPS_AVG(20),
    TIME_RELATIVE(21),
    FRAME_MISS_AVG(22),
    NET_LATENCY_AVG(23),
    MAP_ID(24),
    THREADTID(51);
    
    private int key;

    private c(int i) {
        this.key = i;
    }

    public int getKey() {
        return this.key;
    }

    public String getKeyStr() {
        return String.valueOf(this.key);
    }
}

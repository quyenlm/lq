package com.tencent.kgvmp.a;

public enum b {
    SCENEID("1"),
    LEVEL("2"),
    FPS("3"),
    OBJECTCOUNT("4"),
    EFFECTCOUNT("5"),
    ROLESTATUS("6"),
    NETLATENCY("7"),
    LOADING("8"),
    SERVERIP("9"),
    TARGETFPS("10"),
    RESOLUTION("11"),
    PICQUALITY("12"),
    USERCOUNT("13"),
    THREADNAME("14"),
    THREADTID("15"),
    BATTLE("16");
    
    private String key;

    private b(String str) {
        this.key = str;
    }

    public String getKey() {
        return this.key;
    }

    public int getKeyID() {
        return Integer.parseInt(this.key);
    }
}

package com.tencent.kgvmp.d;

public enum d {
    UNKOWN("unknown"),
    HUAWEI("huawei"),
    HUAWEI2("huawei2"),
    SAMSUNG("samsung"),
    SAMSUNG2("samsung2"),
    VIVO("vivo"),
    VIVO2("vivo2"),
    XIAOMI("xiaomi"),
    OPPO("oppo");
    
    private String type;

    private d(String str) {
        this.type = str;
    }

    public String getName() {
        return this.type;
    }
}

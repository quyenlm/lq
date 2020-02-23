package com.tencent.kgvmp.report;

public enum a {
    VMP_INIT("VMP1"),
    VMP_DOWNLOAD_CONFIG("VMP2"),
    VMP_REGCALL("VMP3"),
    VMP_SOCKETINFO("VMP4"),
    VMP_HANDLE_EXCEPTION("VMP5"),
    VMP_CALLBACK("VMP6"),
    VMP_SCENETIME("VMP7"),
    VMP_EXCEPTION("VMP8"),
    VMP_DEVICE_CHECK("VMP9");
    
    private String value;

    private a(String str) {
        this.value = str;
    }

    public String getValue() {
        return this.value;
    }
}

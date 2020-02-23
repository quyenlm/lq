package com.tencent.imsdk.config;

public enum Channel {
    eChannel_None(""),
    eChannel_MTA("MTA"),
    eChannel_Beacon("Beacon"),
    eChannel_Bugly("Bugly"),
    eChannel_RQD("RQD"),
    eChannel_Adjust("Adjust"),
    eChannel_AppsFlyer("AppsFlyer"),
    eChannel_TNK("TNK"),
    eChannel_IGAW("IGAW"),
    eChannel_Appang("Appang"),
    eChannel_PartyTrack("PartyTrack"),
    eChannel_Google("Google"),
    eChannel_Midas("Midas"),
    eChannel_Zalo("Zalo"),
    eChannel_XG("XG"),
    eChannel_QQ("QQ"),
    eChannel_Facebook("Facebook"),
    eChannel_WeChat("WeChat");
    
    String value;

    private Channel(String val) {
        this.value = "";
        this.value = val;
    }

    public String val() {
        return this.value;
    }
}

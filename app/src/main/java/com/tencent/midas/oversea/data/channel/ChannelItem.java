package com.tencent.midas.oversea.data.channel;

import com.tencent.midas.oversea.business.payhub.APBasePayChannel;

public class ChannelItem {
    public String appcode;
    public String id;
    public boolean isHasProduct = false;
    public String key;
    public APBasePayChannel mAPBasePayChannel;
    public String name;

    public String toString() {
        return "{ id=" + this.id + " | " + "name=" + this.name + " | " + "key=" + this.key + " | " + "appcode=" + this.appcode + " }";
    }
}

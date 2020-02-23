package com.tencent.ieg.ntv.event;

public class EventShowHolderBonusTips extends Event {
    public String iconName;
    public String iconUrl;
    public String tipText;

    public EventShowHolderBonusTips(String tiptext, String iconurl, String itemname) {
        this.tipText = tiptext;
        this.iconUrl = iconurl;
        this.iconName = itemname;
    }
}

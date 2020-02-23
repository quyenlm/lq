package com.tencent.ieg.ntv.view;

public class HolderBonusItemInfo {
    public String iconUrl;
    public String itemDec;
    public String itemName;
    public short itemid;
    public short status;
    public short viewtime;

    public short getMinute() {
        return (short) (this.viewtime / 60);
    }
}

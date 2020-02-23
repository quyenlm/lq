package com.tencent.ieg.ntv.event;

public class HolderBonusItemClick extends Event {
    public short sItemId;
    public short sStatus;

    public HolderBonusItemClick(short itemid, short status) {
        this.sItemId = itemid;
        this.sStatus = status;
    }
}

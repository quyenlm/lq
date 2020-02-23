package com.tencent.ieg.ntv.event;

public class EventHolderItemUpdate extends Event {
    public String btnLable = "";
    public short mItemId;
    public short mStatus;

    public EventHolderItemUpdate(String label, short itemid, short status) {
        this.btnLable = label;
        this.mItemId = itemid;
        this.mStatus = status;
    }
}

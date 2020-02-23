package com.tencent.ieg.ntv.view;

public class HolderBonusCountInfo {
    public short mItemId;
    public long mTime;
    public short mType;

    public static class CountTargetType {
        public static short TYPE_COUNT_TO_TARGET = 1;
        public static short TYPE_SHOW_HALL_ICON = 0;
    }

    public HolderBonusCountInfo(short type, short itemid, long time) {
        this.mType = type;
        this.mItemId = itemid;
        this.mTime = time;
    }
}

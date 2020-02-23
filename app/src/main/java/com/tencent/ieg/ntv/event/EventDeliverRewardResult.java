package com.tencent.ieg.ntv.event;

public class EventDeliverRewardResult extends Event {
    public short mItemId;
    public short mResult;
    public short mRewardId;
    public short mStatus;

    public EventDeliverRewardResult(short result, short rewardId, short itemId, short status) {
        this.mResult = result;
        this.mRewardId = rewardId;
        this.mItemId = itemId;
        this.mStatus = status;
    }
}

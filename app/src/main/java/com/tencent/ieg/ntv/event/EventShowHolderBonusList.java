package com.tencent.ieg.ntv.event;

import com.tencent.ieg.ntv.network.RewardInfo;

public class EventShowHolderBonusList extends Event {
    public RewardInfo mRewardInfo;

    public EventShowHolderBonusList(RewardInfo rewardInfo) {
        this.mRewardInfo = rewardInfo;
    }
}

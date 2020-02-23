package com.garena.pay.android.data;

public class GGRedeemResultItem {
    private int appPoint;
    private long rebateId;
    private int remainingDays;

    public GGRedeemResultItem(long rebateId2, int appPoint2, int remainingDays2) {
        this.rebateId = rebateId2;
        this.appPoint = appPoint2;
        this.remainingDays = remainingDays2;
    }

    public long getRebateId() {
        return this.rebateId;
    }

    public int getAppPoint() {
        return this.appPoint;
    }

    public int getRemainingDays() {
        return this.remainingDays;
    }
}

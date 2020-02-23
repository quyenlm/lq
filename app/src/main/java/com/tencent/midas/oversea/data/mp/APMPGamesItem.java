package com.tencent.midas.oversea.data.mp;

public class APMPGamesItem {
    public static final String SENDTYPE_NUM = "2";
    public static final String SENDTYPE_RATE = "1";
    public int limitNum = 0;
    public int realSendNum = 0;
    public String sendExt;
    public int sendNum = 0;
    public int sendRate = 0;
    public String sendType;

    public boolean getIsHasSend() {
        if (!"1".equals(this.sendType) || this.sendRate <= 0) {
            return "2".equals(this.sendType) && this.sendNum > 0;
        }
        return true;
    }

    public int getRealSendGamesNum(int i) {
        if ("1".equals(this.sendType)) {
            if ((this.sendRate * i) % 100 != 0) {
                this.realSendNum = ((this.sendRate * i) / 100) + 1;
            } else {
                this.realSendNum = (this.sendRate * i) / 100;
            }
        } else if (!"2".equals(this.sendType)) {
            this.realSendNum = 0;
        } else if (i >= this.limitNum) {
            this.realSendNum = this.sendNum;
        } else {
            this.realSendNum = 0;
        }
        return this.realSendNum;
    }

    public int getSendGamesNum() {
        if ("1".equals(this.sendType)) {
            if ((this.sendRate * this.limitNum) % 100 != 0) {
                this.realSendNum = ((this.sendRate * this.limitNum) / 100) + 1;
            } else {
                this.realSendNum = (this.sendRate * this.limitNum) / 100;
            }
        } else if ("2".equals(this.sendType)) {
            this.realSendNum = this.sendNum;
        } else {
            this.realSendNum = 0;
        }
        return this.realSendNum;
    }
}

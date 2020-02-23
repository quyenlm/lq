package com.tencent.midas.oversea.api;

public class APMidasResponse {
    public static final int PAYCHANEL_BOKU = 101;
    public static final int PAYCHANEL_FORTUMO = 108;
    public static final int PAYCHANEL_GOOGLE_WALLET = 100;
    public static final int PAYCHANEL_MOL_EASYPAY = 103;
    public static final int PAYCHANEL_MOL_PIN = 102;
    public static final int PAYCHANEL_MOL_WALLET = 104;
    public static final int PAYCHANEL_MYCARD = 106;
    public static final int PAYCHANEL_PAYMENTWALL = 105;
    public static final int PAYCHANEL_UNKOWN = -1;
    public static final int PAYCHANEL_ZALO = 107;
    public static final int PAYPROVIDESTATE_SUCC = 0;
    public static final int PAYPROVIDESTATE_UNKOWN = -1;
    public static final int PAYRESULT_CANCEL = 2;
    public static final int PAYRESULT_ERROR = -1;
    public static final int PAYRESULT_PARAMERROR = 3;
    public static final int PAYRESULT_SUCC = 0;
    public static final int PAYRESULT_UNKOWN = 4;
    public static final int PAYSTATE_PAYCANCEL = 1;
    public static final int PAYSTATE_PAYERROR = 2;
    public static final int PAYSTATE_PAYSUCC = 0;
    public static final int PAYSTATE_PAYUNKOWN = -1;
    public static final int SAVETYPE_GAME = 0;
    public static final int SAVETYPE_GOODS = 1;
    public static final int SAVETYPE_MONTH = 4;
    public static final int SAVETYPE_QB = 3;
    public static final int SAVETYPE_QD = 2;
    public static final int SAVETYPE_SUBSCRIBE = 5;
    public int billno;
    public String extendInfo;
    public String newPayChannel;
    public String newbillno;
    public int payChannel;
    public String payReserve1;
    public String payReserve2;
    public String payReserve3;
    public int payState;
    public int provideState;
    public int realSaveNum;
    public int resultCode;
    public int resultInerCode;
    public String resultMsg;

    public APMidasResponse() {
        this.resultMsg = null;
        this.extendInfo = null;
        this.payReserve1 = null;
        this.payReserve2 = null;
        this.payReserve3 = null;
        this.realSaveNum = 0;
        this.resultCode = -1;
        this.resultInerCode = 0;
        this.payChannel = -1;
        this.newPayChannel = "";
        this.payState = -1;
        this.provideState = -1;
        this.resultMsg = "";
        this.extendInfo = "";
        this.payReserve1 = "";
        this.payReserve2 = "";
        this.payReserve3 = "";
    }

    public String getExtendInfo() {
        return this.extendInfo;
    }

    public int getPayChannel() {
        return this.payChannel;
    }

    public String getPayReserve1() {
        return this.payReserve1;
    }

    public String getPayReserve2() {
        return this.payReserve2;
    }

    public String getPayReserve3() {
        return this.payReserve3;
    }

    public int getPayState() {
        return this.payState;
    }

    public int getProvideState() {
        return this.provideState;
    }

    public int getRealSaveNum() {
        return this.realSaveNum;
    }

    public int getResultCode() {
        return this.resultCode;
    }

    public int getResultInerCode() {
        return this.resultInerCode;
    }

    public String getResultMsg() {
        return this.resultMsg;
    }

    public void reset() {
        this.realSaveNum = 0;
        this.resultCode = -1;
        this.resultInerCode = 0;
        this.payChannel = -1;
        this.newPayChannel = "";
        this.payState = -1;
        this.provideState = -1;
        this.resultMsg = "";
        this.extendInfo = "";
        this.payReserve1 = "";
        this.payReserve2 = "";
        this.payReserve3 = "";
    }

    public void setExtendInfo(String str) {
        this.extendInfo = str;
    }

    public void setPayChannel(int i) {
        this.payChannel = i;
    }

    public void setPayReserve1(String str) {
        this.payReserve1 = str;
    }

    public void setPayReserve2(String str) {
        this.payReserve2 = str;
    }

    public void setPayReserve3(String str) {
        this.payReserve3 = str;
    }

    public void setPayState(int i) {
        this.payState = i;
    }

    public void setProvideState(int i) {
        this.provideState = i;
    }

    public void setRealSaveNum(int i) {
        this.realSaveNum = i;
    }

    public void setResultCode(int i) {
        this.resultCode = i;
    }

    public void setResultInerCode(int i) {
        this.resultInerCode = i;
    }

    public void setResultMsg(String str) {
        this.resultMsg = str;
    }

    public String toString() {
        return "{ resultCode=" + this.resultCode + " | " + "realSaveNum=" + this.realSaveNum + " | " + "resultInerCode=" + this.resultInerCode + " | " + "payChannel=" + this.payChannel + " | " + "newPayChannel=" + this.newPayChannel + " | " + "payState=" + this.payState + " | " + "provideState=" + this.provideState + " | " + "resultMsg=" + this.resultMsg + " }";
    }
}

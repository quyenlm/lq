package com.tencent.qqgamemi.mgc.step;

import com.tencent.tp.a.h;

public abstract class Step<PARAM> {
    private long endTime;
    private String mExecutionInfo;
    private PARAM[] mPARAM;
    private long startTime;

    /* access modifiers changed from: protected */
    public abstract String getName();

    /* access modifiers changed from: protected */
    public abstract void run();

    public void execute(PARAM... param) {
        this.mPARAM = param;
        onPreExecute();
        run();
        onPostExecute();
    }

    /* access modifiers changed from: protected */
    public PARAM[] getPARAM() {
        return this.mPARAM;
    }

    public String getExecutionInfo() {
        return this.mExecutionInfo;
    }

    private void setupExecutionInfo() {
        this.mExecutionInfo = "step(name=" + getName() + ", duration=" + (this.endTime - this.startTime) + h.b;
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
        this.startTime = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public void onPostExecute() {
        this.endTime = System.currentTimeMillis();
        setupExecutionInfo();
    }
}

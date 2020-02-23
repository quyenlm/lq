package com.tencent.tp.a;

import java.util.TimerTask;

class ak extends TimerTask {
    private aj a;

    public ak(aj ajVar) {
        this.a = ajVar;
    }

    public void run() {
        this.a.sendMessage(this.a.obtainMessage());
    }
}

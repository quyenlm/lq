package com.tencent.tp.a;

import java.util.Timer;

public class ai {
    private Timer a = new Timer();
    private ak b;

    public interface a {
        void a(int i);
    }

    public ai(int i, int i2, boolean z, a aVar) {
        aj ajVar = new aj(i, aVar);
        this.b = new ak(ajVar);
        if (z) {
            this.a.schedule(this.b, (long) i2);
        } else {
            this.a.schedule(this.b, (long) i2, (long) i2);
        }
    }

    public void a() {
        if (this.a != null) {
            this.a.cancel();
            this.a = null;
        }
    }

    public void b() {
    }
}

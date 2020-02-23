package com.tencent.liteav.audio.impl.Record;

import android.content.Context;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCTimeUtil;

/* compiled from: TXCAudioCustomRecord */
public class d extends b implements Runnable {
    private boolean d = false;
    private Thread e = null;
    private byte[] f = new byte[20480];
    private int g = 0;
    private int h = 0;

    public void a(Context context, int i, int i2, int i3) {
        super.a(context, i, i2, i3);
        c();
        this.d = true;
        this.e = new Thread(this, "AudioCustomRecord Thread");
        this.e.start();
    }

    public void c() {
        this.d = false;
        long currentTimeMillis = System.currentTimeMillis();
        if (!(this.e == null || !this.e.isAlive() || Thread.currentThread().getId() == this.e.getId())) {
            try {
                this.e.join();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
                TXCLog.e("AudioCenter:TXCAudioCustomRecord", "custom record stop Exception: " + e2.getMessage());
            }
        }
        TXCLog.i("AudioCenter:TXCAudioCustomRecord", "stop record cost time(MS): " + (System.currentTimeMillis() - currentTimeMillis));
        this.e = null;
    }

    public boolean d() {
        return this.d;
    }

    public synchronized void a(byte[] bArr) {
        int length;
        if (bArr != null) {
            if (f() >= bArr.length) {
                if (this.g + bArr.length <= this.f.length) {
                    System.arraycopy(bArr, 0, this.f, this.g, bArr.length);
                    this.g += bArr.length;
                } else {
                    int length2 = this.f.length - this.g;
                    System.arraycopy(bArr, 0, this.f, this.g, length2);
                    this.g = bArr.length - length2;
                    System.arraycopy(bArr, length2, this.f, 0, this.g);
                }
            }
        }
        StringBuilder append = new StringBuilder().append("缓冲区不够. 自定义数据长度 = ");
        if (bArr == null) {
            length = -1;
        } else {
            length = bArr.length;
        }
        TXCLog.e("AudioCenter:TXCAudioCustomRecord", append.append(length).append(", 剩余缓冲区长度 = ").append(f()).toString());
    }

    private int e() {
        return ((this.g + this.f.length) - this.h) % this.f.length;
    }

    private int f() {
        return this.f.length - e();
    }

    public void run() {
        if (!this.d) {
            TXCLog.w("AudioCenter:TXCAudioCustomRecord", "audio custom record: abandom start audio sys record thread!");
            return;
        }
        a();
        int i = ((this.b * 1024) * this.c) / 8;
        byte[] bArr = new byte[i];
        while (this.d && !Thread.interrupted()) {
            if (i <= e()) {
                synchronized (this) {
                    if (this.h + i <= this.f.length) {
                        System.arraycopy(this.f, this.h, bArr, 0, i);
                        this.h += i;
                    } else {
                        int length = this.f.length - this.h;
                        System.arraycopy(this.f, this.h, bArr, 0, length);
                        this.h = i - length;
                        System.arraycopy(this.f, 0, bArr, length, this.h);
                    }
                }
                a(bArr, bArr.length, TXCTimeUtil.getTimeTick());
            } else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
        }
        b();
    }
}

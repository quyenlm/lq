package com.tencent.mna.b.c;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import com.tencent.mna.b.c.a;
import com.tencent.mna.base.utils.h;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/* compiled from: VivoLocalSocket */
public class b implements a {
    private volatile boolean a = false;
    /* access modifiers changed from: private */
    public volatile boolean b = false;
    private LocalSocket c;
    /* access modifiers changed from: private */
    public a.C0026a d;
    /* access modifiers changed from: private */
    public InputStream e;
    private OutputStream f;

    public boolean a(a.C0026a aVar) {
        if (this.a) {
            return true;
        }
        try {
            this.c = new LocalSocket();
            this.c.connect(new LocalSocketAddress("netopt", LocalSocketAddress.Namespace.ABSTRACT));
            this.d = aVar;
            this.a = true;
            this.e = this.c.getInputStream();
            this.f = this.c.getOutputStream();
            new Thread(new Runnable() {
                public void run() {
                    try {
                        boolean unused = b.this.b = false;
                        while (!b.this.b && b.this.e != null) {
                            byte[] bArr = new byte[1024];
                            String str = new String(Arrays.copyOfRange(bArr, 0, b.this.e.read(bArr)), "UTF-8");
                            if (b.this.d != null && str.length() > 0) {
                                b.this.d.a(str);
                            }
                        }
                    } catch (Throwable th) {
                        h.c("LocalSocket recv failed");
                    }
                }
            }).start();
            return true;
        } catch (Exception e2) {
            h.c("LocalSocket connect failed");
            return false;
        }
    }

    public boolean a(String str) {
        if (!this.a) {
            return false;
        }
        try {
            if (this.f != null) {
                byte[] bytes = str.getBytes("UTF-8");
                this.f.write(bytes, 0, bytes.length);
                this.f.flush();
            }
            return true;
        } catch (Exception e2) {
            h.c("LocalSocket send failed");
            return false;
        }
    }

    public boolean a() {
        try {
            this.b = true;
            this.a = false;
            if (this.c == null) {
                return true;
            }
            this.c.shutdownInput();
            this.c.shutdownOutput();
            this.c.close();
            this.c = null;
            return true;
        } catch (Exception e2) {
            return false;
        }
    }
}

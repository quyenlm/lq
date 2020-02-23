package com.tencent.liteav.audio.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.tencent.liteav.basic.log.TXCLog;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: TXCTelephonyMgr */
public class c {
    private static final c a = new c();
    private ConcurrentHashMap<Integer, WeakReference<e>> b = new ConcurrentHashMap<>();
    /* access modifiers changed from: private */
    public PhoneStateListener c = null;
    /* access modifiers changed from: private */
    public Context d;

    public static c a() {
        return a;
    }

    private c() {
    }

    public synchronized void a(e eVar) {
        if (eVar != null) {
            this.b.put(Integer.valueOf(eVar.hashCode()), new WeakReference(eVar));
        }
    }

    public synchronized void b(e eVar) {
        if (eVar != null) {
            if (this.b.containsKey(Integer.valueOf(eVar.hashCode()))) {
                this.b.remove(Integer.valueOf(eVar.hashCode()));
            }
        }
    }

    /* access modifiers changed from: private */
    public synchronized void a(int i) {
        Iterator<Map.Entry<Integer, WeakReference<e>>> it = this.b.entrySet().iterator();
        while (it.hasNext()) {
            e eVar = (e) ((WeakReference) it.next().getValue()).get();
            if (eVar != null) {
                eVar.a(i);
            } else {
                it.remove();
            }
        }
    }

    public void a(Context context) {
        if (this.c == null) {
            this.d = context.getApplicationContext();
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    if (c.this.c == null) {
                        PhoneStateListener unused = c.this.c = new PhoneStateListener() {
                            public void onCallStateChanged(int i, String str) {
                                super.onCallStateChanged(i, str);
                                TXCLog.i("AudioCenter:TXCTelephonyMgr", "onCallStateChanged:" + i);
                                c.this.a(i);
                            }
                        };
                        try {
                            ((TelephonyManager) c.this.d.getSystemService("phone")).listen(c.this.c, 32);
                        } catch (Exception e) {
                        }
                    }
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        super.finalize();
        if (this.c != null && this.d != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    try {
                        if (!(c.this.c == null || c.this.d == null)) {
                            ((TelephonyManager) c.this.d.getApplicationContext().getSystemService("phone")).listen(c.this.c, 0);
                        }
                    } catch (Exception e) {
                    }
                    PhoneStateListener unused = c.this.c = null;
                }
            });
        }
    }
}

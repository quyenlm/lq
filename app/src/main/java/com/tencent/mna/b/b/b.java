package com.tencent.mna.b.b;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import com.tencent.mna.NetworkBindingListener;
import com.tencent.mna.base.jni.e;
import com.tencent.mna.base.utils.h;
import com.tencent.mna.base.utils.k;
import java.io.FileDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: NetworkBinding */
public class b {
    private static Field b = null;
    private static Field c = null;
    private static Network g = null;
    public volatile int a = 0;
    private a d = null;
    private ConnectivityManager.NetworkCallback e = null;
    private Map<Integer, Integer> f = new ConcurrentHashMap();
    /* access modifiers changed from: private */
    public volatile Network h = null;

    public int a(Context context, boolean z, boolean z2, boolean z3) {
        if (z) {
            return NetworkBindingListener.NB_PREPARE_MATCHMODE_NOTSUPPORT;
        }
        if (z2) {
            return -101;
        }
        int a2 = a(context);
        return a2 == 0 ? a(context, z3) : a2;
    }

    public int a(Context context, boolean z) {
        int c2 = e.c(3000);
        if (c2 <= 0) {
            return NetworkBindingListener.NB_PREPARE_GEN_FD_FAIL;
        }
        if (a(c2) != 0) {
            b(c2);
            e.d(c2);
            return NetworkBindingListener.NB_PREPARE_BIND_FD_FAIL;
        }
        this.d = new a();
        int a2 = this.d.a(context, c2, this.a);
        if (z && a2 == 0 && this.d.m != null && this.d.m.size() > 0) {
            e.h(this.d.m.get(0));
        }
        b(c2);
        e.d(c2);
        return a2;
    }

    public int a(Context context) {
        if (!c(context)) {
            return -102;
        }
        if (Build.VERSION.SDK_INT < 23) {
            return -103;
        }
        if (!g()) {
            return -105;
        }
        if (!d(context)) {
            return -104;
        }
        return 0;
    }

    public void b(Context context) {
        this.a = 0;
        this.h = null;
        e(context);
    }

    public int a(int i) {
        int a2 = a(i, this.h);
        this.f.put(Integer.valueOf(i), Integer.valueOf(this.a));
        h.b("NetworkBinding bindFdToNetid fd:" + i + ", mNetId:" + this.a + ", res:" + a2);
        return a2;
    }

    public int b(int i) {
        int a2 = a(i, g);
        h.b("NetworkBinding unbindFd fd:" + i + ", res:" + a2);
        this.f.remove(Integer.valueOf(i));
        return a2;
    }

    /* access modifiers changed from: private */
    public synchronized int a(Network network) {
        boolean z;
        boolean z2;
        int i = 0;
        synchronized (this) {
            boolean z3 = true;
            for (Integer intValue : this.f.keySet()) {
                int intValue2 = intValue.intValue();
                if (a(intValue2, network) == 0) {
                    z = true;
                } else {
                    z = false;
                }
                h.b("NetworkBinding bindAllToNetwork fd:" + intValue2 + ", mNetId:" + this.a + ", res:" + z);
                if (!z3 || !z) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                z3 = z2;
            }
            if (!z3) {
                i = -1;
            }
        }
        return i;
    }

    private synchronized int f() {
        boolean z;
        boolean z2;
        int i = 0;
        synchronized (this) {
            boolean z3 = true;
            for (Integer intValue : this.f.keySet()) {
                int intValue2 = intValue.intValue();
                if (a(intValue2, g) == 0) {
                    z = true;
                } else {
                    z = false;
                }
                h.b("NetworkBinding unbindAll fd:" + intValue2 + ", res:" + z);
                if (!z3 || !z) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                z3 = z2;
            }
            this.f.clear();
            if (!z3) {
                i = -1;
            }
        }
        return i;
    }

    public String a() {
        return this.d.a;
    }

    public int b() {
        return this.d.g;
    }

    public String c() {
        return this.d.f;
    }

    public int d() {
        return this.d.k;
    }

    public List<String> e() {
        return this.d.m;
    }

    private boolean c(Context context) {
        return k.a(context) == 4;
    }

    private boolean g() {
        if (b != null && c != null && g != null) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                b = Network.class.getDeclaredField("netId");
                b.setAccessible(true);
                c = FileDescriptor.class.getDeclaredField("descriptor");
                c.setAccessible(true);
                Constructor<Network> declaredConstructor = Network.class.getDeclaredConstructor(new Class[]{Integer.TYPE});
                declaredConstructor.setAccessible(true);
                g = declaredConstructor.newInstance(new Object[]{0});
                return true;
            } catch (Exception e2) {
                h.d("NetworkBinding reflect exception:" + e2.getMessage());
            }
        }
        return false;
    }

    private boolean d(Context context) {
        if (context == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                b(context);
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                NetworkRequest build = new NetworkRequest.Builder().addCapability(12).addTransportType(0).build();
                this.e = new ConnectivityManager.NetworkCallback() {
                    public void onAvailable(Network network) {
                        super.onAvailable(network);
                        int i = b.this.a;
                        int a2 = b.this.a((Object) network);
                        if (b.this.a != a2) {
                            b.this.a = a2;
                            Network unused = b.this.h = network;
                        }
                        h.b("NetworkBinding preNetId:" + i + ", mNetId = " + b.this.a + ", transportType:" + 0);
                        if (i != a2 && a2 != 0) {
                            h.b("NetworkBinding rebindAll, preNetId:" + i + ", newNetId:" + a2);
                            int unused2 = b.this.a(network);
                        }
                    }
                };
                if (connectivityManager != null) {
                    h.b("NetworkBinding callback register");
                    connectivityManager.requestNetwork(build, this.e);
                    int i = 0;
                    while (true) {
                        int i2 = i + 1;
                        if (i >= 25) {
                            break;
                        }
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e2) {
                        }
                        if (this.a != 0) {
                            return true;
                        }
                        i = i2;
                    }
                }
            } catch (Exception e3) {
                h.d("NetworkBinding prepareNetwork exception:" + e3.getMessage());
            }
        }
        return false;
    }

    private void e(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            f();
            f(context);
        }
    }

    /* access modifiers changed from: private */
    public int a(Object obj) {
        if (b == null) {
            return 0;
        }
        try {
            return b.getInt(obj);
        } catch (Exception e2) {
            h.d("NetworkBinding getNetId exception:" + e2.getMessage());
            return 0;
        }
    }

    private int a(int i, Network network) {
        if (c == null) {
            return -105;
        }
        try {
            if (Build.VERSION.SDK_INT < 23 || network == null) {
                return -103;
            }
            FileDescriptor fileDescriptor = new FileDescriptor();
            c.setInt(fileDescriptor, i);
            network.bindSocket(fileDescriptor);
            return 0;
        } catch (Exception e2) {
            h.d("NetworkBinding bindSocketToNetwork exception:" + e2.getMessage());
            return -1;
        }
    }

    private void f(Context context) {
        if (context != null && Build.VERSION.SDK_INT >= 23) {
            try {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                if (!(this.e == null || connectivityManager == null)) {
                    connectivityManager.unregisterNetworkCallback(this.e);
                    h.a("NetworkBinding callback unregistered");
                }
            } catch (Exception e2) {
                h.d("NetworkBinding unregisterNetworkCallback exception:" + e2.getMessage());
            } finally {
                this.e = null;
            }
        }
    }
}

package com.subao.common.k;

import MTT.EFvrECode;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.NetworkInfo;
import android.os.ParcelFileDescriptor;
import com.subao.common.d;
import com.subao.common.h;
import com.subao.common.j.f;
import com.subao.common.j.i;
import com.subao.common.j.j;
import com.subao.common.k.b;
import java.io.IOException;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

/* compiled from: CellularOperator */
public class a implements com.subao.common.a {
    private final b a;
    private Object b;

    /* renamed from: com.subao.common.k.a$a  reason: collision with other inner class name */
    /* compiled from: CellularOperator */
    public interface C0017a {
        void a(boolean z);
    }

    private a(C0017a aVar) {
        this.a = new b(aVar);
    }

    public static a a(Context context, C0017a aVar) {
        b.a(context);
        a aVar2 = new a(aVar);
        aVar2.b = b.a(b.e.CELLULAR, aVar2.a);
        return aVar2;
    }

    public void a() {
        synchronized (this) {
            if (this.b != null) {
                b.a(this.b);
                this.b = null;
            }
        }
    }

    public int a(Context context) {
        return this.a.a(context);
    }

    /* compiled from: CellularOperator */
    static class b implements b.a {
        private final C0017a a;
        private final List<b.C0018b> b = new ArrayList(2);

        b(C0017a aVar) {
            this.a = aVar;
        }

        @TargetApi(14)
        static int a(b.C0018b bVar) {
            try {
                DatagramSocket datagramSocket = new DatagramSocket();
                try {
                    bVar.a(datagramSocket);
                    ParcelFileDescriptor fromDatagramSocket = ParcelFileDescriptor.fromDatagramSocket(datagramSocket);
                    if (fromDatagramSocket == null) {
                        throw new b.d(EFvrECode._ERR_FVR_IMGCVT_ERR);
                    }
                    int detachFd = fromDatagramSocket.detachFd();
                    datagramSocket.close();
                    return detachFd;
                } catch (RuntimeException e) {
                    throw new b.d(EFvrECode._ERR_FVR_IMGCVT_ERR);
                } catch (Throwable th) {
                    datagramSocket.close();
                    throw th;
                }
            } catch (IOException e2) {
                d.b("SubaoParallel", e2.getMessage());
                throw new b.d(2005);
            }
        }

        static int a(h hVar) {
            switch (hVar) {
                case OFF:
                    return 2003;
                case ON:
                    return 2007;
                default:
                    return 2008;
            }
        }

        static void a(Context context, b.C0018b bVar) {
            try {
                NetworkInfo a2 = bVar.a(context);
                if (a2 == null) {
                    return;
                }
                if (a2.getType() != 0) {
                    d.a("SubaoParallel", "The network type is not mobile, can not create FD by mobile");
                    throw new b.d(EFvrECode._ERR_FVR_IMGCVT_EXCEPTION);
                } else if (j.a.MOBILE_2G == f.a(a2.getSubtype())) {
                    d.a("SubaoParallel", "The network type is 2G, can not create FD by mobile");
                    throw new b.d(2004);
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

        private b.C0018b a() {
            b.C0018b bVar;
            synchronized (this) {
                if (this.b.isEmpty()) {
                    bVar = null;
                } else {
                    bVar = this.b.get(this.b.size() - 1);
                }
            }
            return bVar;
        }

        /* access modifiers changed from: package-private */
        public int a(Context context) {
            b.C0018b a2 = a();
            if (a2 == null) {
                d.a("SubaoParallel", "No available cellular network.");
                throw new b.d(a(i.a(context)));
            }
            a(context, a2);
            return a(a2);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0034, code lost:
            if (r3.a == null) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0036, code lost:
            if (r0 != 1) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0038, code lost:
            r3.a.a(true);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void b(com.subao.common.k.b.C0018b r4) {
            /*
                r3 = this;
                r2 = 1
                monitor-enter(r3)
                java.util.List<com.subao.common.k.b$b> r0 = r3.b     // Catch:{ all -> 0x003e }
                int r0 = r0.size()     // Catch:{ all -> 0x003e }
                int r0 = r0 + -1
                r1 = r0
            L_0x000b:
                if (r1 < 0) goto L_0x0026
                java.util.List<com.subao.common.k.b$b> r0 = r3.b     // Catch:{ all -> 0x003e }
                java.lang.Object r0 = r0.get(r1)     // Catch:{ all -> 0x003e }
                com.subao.common.k.b$b r0 = (com.subao.common.k.b.C0018b) r0     // Catch:{ all -> 0x003e }
                boolean r0 = r0.equals(r4)     // Catch:{ all -> 0x003e }
                if (r0 == 0) goto L_0x0022
                java.util.List<com.subao.common.k.b$b> r0 = r3.b     // Catch:{ all -> 0x003e }
                r0.set(r1, r4)     // Catch:{ all -> 0x003e }
                monitor-exit(r3)     // Catch:{ all -> 0x003e }
            L_0x0021:
                return
            L_0x0022:
                int r0 = r1 + -1
                r1 = r0
                goto L_0x000b
            L_0x0026:
                java.util.List<com.subao.common.k.b$b> r0 = r3.b     // Catch:{ all -> 0x003e }
                r0.add(r4)     // Catch:{ all -> 0x003e }
                java.util.List<com.subao.common.k.b$b> r0 = r3.b     // Catch:{ all -> 0x003e }
                int r0 = r0.size()     // Catch:{ all -> 0x003e }
                monitor-exit(r3)     // Catch:{ all -> 0x003e }
                com.subao.common.k.a$a r1 = r3.a
                if (r1 == 0) goto L_0x0021
                if (r0 != r2) goto L_0x0021
                com.subao.common.k.a$a r0 = r3.a
                r0.a(r2)
                goto L_0x0021
            L_0x003e:
                r0 = move-exception
                monitor-exit(r3)     // Catch:{ all -> 0x003e }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.subao.common.k.a.b.b(com.subao.common.k.b$b):void");
        }

        public void c(b.C0018b bVar) {
            boolean isEmpty;
            if (!this.b.isEmpty()) {
                synchronized (this) {
                    int size = this.b.size() - 1;
                    while (true) {
                        if (size < 0) {
                            break;
                        } else if (this.b.get(size).equals(bVar)) {
                            this.b.remove(size);
                            break;
                        } else {
                            size--;
                        }
                    }
                    isEmpty = this.b.isEmpty();
                }
                if (isEmpty && this.a != null) {
                    this.a.a(false);
                }
            }
        }
    }
}

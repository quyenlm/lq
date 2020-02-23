package com.tencent.mna.base.e;

import android.content.Context;
import android.net.wifi.WifiManager;
import com.tencent.mna.base.utils.f;
import com.tencent.mna.base.utils.h;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/* compiled from: UpnpDeviceScanner */
public class b {
    /* access modifiers changed from: private */
    public static String a = "UpnpDeviceScanner";
    private a b;

    public b(Context context) {
        this.b = a.a(context);
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
        com.tencent.mna.base.utils.h.a(a + ": getGateWayDevice found gateway device and stop receive");
     */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00a5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.tencent.mna.base.e.a a() {
        /*
            r6 = this;
            r1 = 0
            com.tencent.mna.base.e.b$a r0 = r6.b
            if (r0 != 0) goto L_0x001e
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = a
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r2 = ": getGateWayDevice socket is null"
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r0 = r0.toString()
            com.tencent.mna.base.utils.h.a((java.lang.String) r0)
        L_0x001d:
            return r1
        L_0x001e:
            com.tencent.mna.base.e.b$a r0 = r6.b     // Catch:{ Exception -> 0x0087 }
            java.lang.String r2 = "ST:urn:schemas-upnp-org:device:InternetGatewayDevice:1"
            r0.a((java.lang.String) r2)     // Catch:{ Exception -> 0x0087 }
            r0 = 2
        L_0x0026:
            int r2 = r0 + -1
            if (r0 <= 0) goto L_0x00b8
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0087 }
            r0.<init>()     // Catch:{ Exception -> 0x0087 }
            java.lang.String r3 = a     // Catch:{ Exception -> 0x0087 }
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch:{ Exception -> 0x0087 }
            java.lang.String r3 = ": getGateWayDevice wait for dev response"
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch:{ Exception -> 0x0087 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0087 }
            com.tencent.mna.base.utils.h.a((java.lang.String) r0)     // Catch:{ Exception -> 0x0087 }
            com.tencent.mna.base.e.b$a r0 = r6.b     // Catch:{ Exception -> 0x0087 }
            java.net.DatagramPacket r0 = r0.a()     // Catch:{ Exception -> 0x0087 }
            java.lang.String r3 = new java.lang.String     // Catch:{ Exception -> 0x0087 }
            byte[] r4 = r0.getData()     // Catch:{ Exception -> 0x0087 }
            java.lang.String r5 = "UTF-8"
            r3.<init>(r4, r5)     // Catch:{ Exception -> 0x0087 }
            r4 = 0
            int r0 = r0.getLength()     // Catch:{ Exception -> 0x0087 }
            java.lang.String r0 = r3.substring(r4, r0)     // Catch:{ Exception -> 0x0087 }
            com.tencent.mna.base.e.a r0 = com.tencent.mna.base.e.a.a((java.lang.String) r0)     // Catch:{ Exception -> 0x0087 }
            if (r0 == 0) goto L_0x0085
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b6 }
            r1.<init>()     // Catch:{ Exception -> 0x00b6 }
            java.lang.String r2 = a     // Catch:{ Exception -> 0x00b6 }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Exception -> 0x00b6 }
            java.lang.String r2 = ": getGateWayDevice found gateway device and stop receive"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Exception -> 0x00b6 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x00b6 }
            com.tencent.mna.base.utils.h.a((java.lang.String) r1)     // Catch:{ Exception -> 0x00b6 }
        L_0x007a:
            com.tencent.mna.base.e.b$a r1 = r6.b
            if (r1 == 0) goto L_0x0083
            com.tencent.mna.base.e.b$a r1 = r6.b
            r1.b()
        L_0x0083:
            r1 = r0
            goto L_0x001d
        L_0x0085:
            r0 = r2
            goto L_0x0026
        L_0x0087:
            r0 = move-exception
            r0 = r1
        L_0x0089:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ab }
            r1.<init>()     // Catch:{ all -> 0x00ab }
            java.lang.String r2 = a     // Catch:{ all -> 0x00ab }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ all -> 0x00ab }
            java.lang.String r2 = ": getGateWayDevice socket time out and stop receive"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ all -> 0x00ab }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00ab }
            com.tencent.mna.base.utils.h.a((java.lang.String) r1)     // Catch:{ all -> 0x00ab }
            com.tencent.mna.base.e.b$a r1 = r6.b
            if (r1 == 0) goto L_0x0083
            com.tencent.mna.base.e.b$a r1 = r6.b
            r1.b()
            goto L_0x0083
        L_0x00ab:
            r0 = move-exception
            com.tencent.mna.base.e.b$a r1 = r6.b
            if (r1 == 0) goto L_0x00b5
            com.tencent.mna.base.e.b$a r1 = r6.b
            r1.b()
        L_0x00b5:
            throw r0
        L_0x00b6:
            r1 = move-exception
            goto L_0x0089
        L_0x00b8:
            r0 = r1
            goto L_0x007a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.mna.base.e.b.a():com.tencent.mna.base.e.a");
    }

    /* compiled from: UpnpDeviceScanner */
    private static class a {
        InetAddress a;
        MulticastSocket b;
        WifiManager.MulticastLock c;

        a() {
        }

        static a a(Context context) {
            if (context == null) {
                return null;
            }
            a aVar = new a();
            try {
                WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
                if (wifiManager == null) {
                    return null;
                }
                aVar.b = new MulticastSocket(0);
                aVar.a = f.h("239.255.255.250");
                aVar.b.joinGroup(aVar.a);
                aVar.b.setSoTimeout(1500);
                aVar.a(wifiManager);
                return aVar;
            } catch (Exception e) {
                h.a(b.a + ": get uPnPSocket failed, exception:" + e.getMessage());
                aVar.b();
                return null;
            }
        }

        private void a(WifiManager wifiManager) {
            if (wifiManager != null) {
                this.c = wifiManager.createMulticastLock("UpnpDeviceScanner");
                this.c.acquire();
            }
        }

        private static String b(String str) {
            StringBuilder sb = new StringBuilder();
            sb.append("M-SEARCH * HTTP/1.1").append("\r\n");
            sb.append("Host:239.255.255.250:1900").append("\r\n");
            sb.append("Man:\"ssdp:discover\"").append("\r\n");
            sb.append("MX:1").append("\r\n");
            sb.append(str).append("\r\n");
            sb.append("\r\n");
            return sb.toString();
        }

        /* access modifiers changed from: package-private */
        public void a(String str) {
            String b2 = b(str);
            this.b.send(new DatagramPacket(b2.getBytes("UTF-8"), b2.length(), this.a, 1900));
        }

        /* access modifiers changed from: package-private */
        public DatagramPacket a() {
            byte[] bArr = new byte[2048];
            DatagramPacket datagramPacket = new DatagramPacket(bArr, bArr.length);
            this.b.receive(datagramPacket);
            return datagramPacket;
        }

        /* access modifiers changed from: package-private */
        public void b() {
            try {
                if (this.b != null) {
                    this.b.close();
                }
                if (this.c != null) {
                    this.c.release();
                }
            } catch (Exception e) {
            }
        }
    }
}

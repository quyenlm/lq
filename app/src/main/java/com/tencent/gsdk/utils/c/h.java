package com.tencent.gsdk.utils.c;

import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: UdpSpeedTester */
final class h extends a {
    private static AtomicInteger a = new AtomicInteger(0);

    h() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x00c6  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00cf  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public short a(java.net.InetAddress r17, int r18, int r19) {
        /*
            r16 = this;
            r0 = r19
            short r4 = (short) r0
            java.util.concurrent.atomic.AtomicInteger r2 = a
            int r5 = r2.getAndIncrement()
            r2 = 0
            java.net.DatagramSocket r3 = new java.net.DatagramSocket     // Catch:{ Throwable -> 0x00c1, all -> 0x00ca }
            r3.<init>()     // Catch:{ Throwable -> 0x00c1, all -> 0x00ca }
            r0 = r19
            r3.setSoTimeout(r0)     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            r2 = 65
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            r6.<init>()     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            java.lang.String r7 = "getUdpDelay seq:"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            java.lang.StringBuilder r6 = r6.append(r5)     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            java.lang.String r7 = ", sndContent:"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            java.lang.StringBuilder r6 = r6.append(r2)     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            java.lang.String r6 = r6.toString()     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            com.tencent.gsdk.utils.Logger.d(r6)     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            r6 = 5
            byte[] r6 = new byte[r6]     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            java.nio.ByteBuffer r6 = java.nio.ByteBuffer.wrap(r6)     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            r6.putInt(r5)     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            byte r2 = (byte) r2     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            r6.put(r2)     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            java.net.DatagramPacket r2 = new java.net.DatagramPacket     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            byte[] r7 = r6.array()     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            byte[] r6 = r6.array()     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            int r6 = r6.length     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            r0 = r17
            r1 = r18
            r2.<init>(r7, r6, r0, r1)     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            long r8 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            r3.send(r2)     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            r2 = 8
            byte[] r2 = new byte[r2]     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            java.net.DatagramPacket r10 = new java.net.DatagramPacket     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            int r6 = r2.length     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            r10.<init>(r2, r6)     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            long r12 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            r6 = 0
        L_0x006d:
            r0 = r19
            long r14 = (long) r0     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            int r2 = (r6 > r14 ? 1 : (r6 == r14 ? 0 : -1))
            if (r2 >= 0) goto L_0x00d9
            r3.receive(r10)     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            byte[] r2 = r10.getData()     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            java.nio.ByteBuffer r2 = java.nio.ByteBuffer.wrap(r2)     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            int r6 = r2.getInt()     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            byte r2 = r2.get()     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            char r2 = (char) r2     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            r7.<init>()     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            java.lang.String r11 = "getUdpDelay rcvSeq:"
            java.lang.StringBuilder r7 = r7.append(r11)     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            java.lang.StringBuilder r7 = r7.append(r6)     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            java.lang.String r11 = ", rcvContent:"
            java.lang.StringBuilder r7 = r7.append(r11)     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            java.lang.StringBuilder r7 = r7.append(r2)     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            com.tencent.gsdk.utils.Logger.d(r7)     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            if (r6 != r5) goto L_0x00bb
            r6 = 66
            if (r2 != r6) goto L_0x00bb
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            long r4 = r4 - r8
            int r2 = (int) r4
            short r2 = (short) r2
        L_0x00b5:
            if (r3 == 0) goto L_0x00ba
            r3.close()
        L_0x00ba:
            return r2
        L_0x00bb:
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x00d7, all -> 0x00d3 }
            long r6 = r6 - r12
            goto L_0x006d
        L_0x00c1:
            r3 = move-exception
            r3 = r2
        L_0x00c3:
            r2 = -1
            if (r3 == 0) goto L_0x00ba
            r3.close()
            goto L_0x00ba
        L_0x00ca:
            r3 = move-exception
            r4 = r3
            r5 = r2
        L_0x00cd:
            if (r5 == 0) goto L_0x00d2
            r5.close()
        L_0x00d2:
            throw r4
        L_0x00d3:
            r2 = move-exception
            r4 = r2
            r5 = r3
            goto L_0x00cd
        L_0x00d7:
            r2 = move-exception
            goto L_0x00c3
        L_0x00d9:
            r2 = r4
            goto L_0x00b5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.gsdk.utils.c.h.a(java.net.InetAddress, int, int):short");
    }
}

package com.tencent.mna.b.g;

import com.tencent.mna.base.utils.f;
import com.tencent.mna.base.utils.h;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/* compiled from: UdpHelper */
public class e {
    public static DatagramPacket a(InetAddress inetAddress, int i, byte[] bArr, int i2) {
        return new DatagramPacket(bArr, i2, inetAddress, i);
    }

    public static DatagramPacket a(String str, int i, byte[] bArr) {
        return a(str, i, bArr, bArr.length);
    }

    public static DatagramPacket a(String str, int i, byte[] bArr, int i2) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        try {
            InetAddress h = f.h(str);
            if (h != null) {
                return a(h, i, bArr, i2);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static DatagramSocket a(int i) {
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            datagramSocket.setSoTimeout(i);
            return datagramSocket;
        } catch (Exception e) {
            h.a("getSocket exception:" + e.getMessage());
            return null;
        }
    }
}

package com.tencent.mna.b.g;

import com.tencent.mna.base.utils.a;
import com.tencent.mna.base.utils.h;
import java.nio.ByteBuffer;

/* compiled from: RouterMsg */
public class b {
    public int a;
    public short b;
    public short c;
    public short d;
    public String e;
    public byte[] f;

    private b() {
    }

    public static b a(int i, short s, short s2, String str) {
        byte[] bytes;
        b bVar = new b();
        bVar.a = i;
        bVar.b = s;
        bVar.c = s2;
        bVar.e = str;
        if (bVar.c == 1001) {
            bytes = a.a;
        } else {
            try {
                bytes = d.b.getBytes("UTF-8");
            } catch (Exception e2) {
                return null;
            }
        }
        bVar.f = a.a(bytes, str);
        if (bVar.f != null) {
            bVar.d = (short) bVar.f.length;
        }
        return bVar;
    }

    public static b a(byte[] bArr) {
        byte[] bytes;
        if (bArr.length < 10) {
            return null;
        }
        try {
            b bVar = new b();
            ByteBuffer wrap = ByteBuffer.wrap(bArr);
            bVar.a = wrap.getInt();
            bVar.b = wrap.getShort();
            bVar.c = wrap.getShort();
            bVar.d = wrap.getShort();
            byte[] bArr2 = new byte[bVar.d];
            wrap.get(bArr2);
            bVar.f = bArr2;
            if (bVar.c == 1002) {
                bytes = a.a;
            } else {
                try {
                    bytes = d.b.getBytes("UTF-8");
                } catch (Exception e2) {
                    return null;
                }
            }
            bVar.e = new String(a.a(bytes, bVar.f), "UTF-8");
            return bVar;
        } catch (Exception e3) {
            h.a("convert bytearray to RouterMsg failed, exception:" + e3.getMessage());
            return null;
        }
    }

    public byte[] a() {
        byte[] bArr = new byte[b()];
        b(bArr);
        return bArr;
    }

    public void b(byte[] bArr) {
        if (bArr.length >= b()) {
            ByteBuffer wrap = ByteBuffer.wrap(bArr);
            wrap.putInt(this.a);
            wrap.putShort(this.b);
            wrap.putShort(this.c);
            wrap.putShort(this.d);
            wrap.put(this.f, 0, this.d);
        }
    }

    public int b() {
        return this.d + 10;
    }

    public String toString() {
        return "bussId:" + this.a + ", version:" + this.b + ", type:" + this.c + ", contentLen: " + this.d + ", json:" + this.e;
    }
}

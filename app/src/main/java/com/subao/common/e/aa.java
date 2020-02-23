package com.subao.common.e;

import android.support.annotation.NonNull;
import android.util.Log;
import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.s3.internal.Constants;
import com.subao.common.e;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.zip.CRC32;

/* compiled from: PortalDataEx */
public class aa {
    private static final byte[] e = new byte[0];
    public final String a;
    public final String b;
    public final byte[] c;
    public final boolean d;
    private long f;

    public aa(String str, long j, String str2, byte[] bArr) {
        this(str, j, str2, bArr, false);
    }

    public aa(String str, long j, String str2, byte[] bArr, boolean z) {
        this.a = str;
        this.f = j;
        this.b = str2;
        this.c = bArr;
        this.d = z;
    }

    private static void a(ByteBuffer byteBuffer, byte[] bArr) {
        if (bArr == null) {
            byteBuffer.putInt(-1);
            return;
        }
        byteBuffer.putInt(bArr.length);
        byteBuffer.put(bArr);
    }

    private static String a(ByteBuffer byteBuffer) {
        byte[] c2 = c(byteBuffer);
        if (c2 != null) {
            return new String(c2);
        }
        return null;
    }

    private static long b(ByteBuffer byteBuffer) {
        if (byteBuffer.remaining() >= 8) {
            return byteBuffer.getLong();
        }
        throw new EOFException();
    }

    private static byte[] c(ByteBuffer byteBuffer) {
        if (byteBuffer.remaining() >= 4) {
            int i = byteBuffer.getInt();
            if (i == 0) {
                return e;
            }
            if (i < 0) {
                return null;
            }
            if (byteBuffer.remaining() >= i) {
                byte[] bArr = new byte[i];
                System.arraycopy(byteBuffer.array(), byteBuffer.position(), bArr, 0, i);
                byteBuffer.position(i + byteBuffer.position());
                return bArr;
            }
        }
        throw new EOFException();
    }

    private static int a(byte[] bArr) {
        if (bArr == null) {
            return 0;
        }
        return bArr.length;
    }

    private static byte[] a(String str) {
        if (str == null) {
            return null;
        }
        return str.getBytes();
    }

    @NonNull
    public static aa a(InputStream inputStream) {
        ByteBuffer a2 = a(4);
        if (inputStream.read(a2.array()) != 4) {
            throw new EOFException();
        }
        int i = a2.getInt();
        if (i < 24 || i > 33554432) {
            throw new IOException("Invalid total size");
        }
        CRC32 crc32 = new CRC32();
        crc32.update(a2.array(), 0, a2.position());
        ByteBuffer a3 = a(i - 4);
        if (inputStream.read(a3.array()) != a3.limit()) {
            throw new EOFException();
        }
        String a4 = a(a3);
        long b2 = b(a3);
        String a5 = a(a3);
        byte[] c2 = c(a3);
        if (a3.limit() - a3.position() >= 8) {
            crc32.update(a3.array(), 0, a3.position());
            if (crc32.getValue() != a3.getLong()) {
                throw new IOException("CRC verify failed");
            }
        } else {
            Log.d("SubaoData", "PortalDataEx.deserialize from old version");
        }
        return new aa(a4, b2, a5, c2);
    }

    private static ByteBuffer a(int i) {
        ByteBuffer wrap = ByteBuffer.wrap(new byte[i]);
        wrap.order(ByteOrder.BIG_ENDIAN);
        return wrap;
    }

    private static StringBuffer a(StringBuffer stringBuffer, String str, String str2) {
        stringBuffer.append(str).append('=');
        if (str2 == null) {
            stringBuffer.append(Constants.NULL_VERSION_ID);
        } else {
            stringBuffer.append('\"').append(str2).append('\"');
        }
        return stringBuffer;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof aa)) {
            return false;
        }
        aa aaVar = (aa) obj;
        if (this.d != aaVar.d || this.f != aaVar.f || !e.a(this.a, aaVar.a) || !e.a(this.b, aaVar.b) || !Arrays.equals(this.c, aaVar.c)) {
            z = false;
        }
        return z;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(256);
        stringBuffer.append('[');
        a(stringBuffer, "CacheTag", this.a);
        stringBuffer.append(", Expire=").append(this.f);
        stringBuffer.append(", ");
        a(stringBuffer, JsonDocumentFields.VERSION, this.b);
        stringBuffer.append(", ");
        stringBuffer.append("Data=");
        if (this.c == null) {
            stringBuffer.append(Constants.NULL_VERSION_ID);
        } else {
            stringBuffer.append(this.c.length);
        }
        stringBuffer.append(", new-download=").append(this.d);
        stringBuffer.append(']');
        return stringBuffer.toString();
    }

    public byte[] a() {
        return this.c;
    }

    public int b() {
        if (this.c == null) {
            return 0;
        }
        return this.c.length;
    }

    public String c() {
        return this.a;
    }

    public String d() {
        return this.b;
    }

    public void a(@NonNull OutputStream outputStream) {
        byte[] a2 = a(this.a);
        byte[] a3 = a(this.b);
        int a4 = a(a2) + 8 + 8 + 4 + a(a3) + 4 + a(this.c) + 8;
        ByteBuffer a5 = a(a4);
        a5.putInt(a4);
        a(a5, a2);
        a5.putLong(this.f);
        a(a5, a3);
        a(a5, this.c);
        byte[] array = a5.array();
        CRC32 crc32 = new CRC32();
        crc32.update(array, 0, a5.position());
        a5.putLong(crc32.getValue());
        outputStream.write(array, 0, a5.position());
        e.a((Closeable) outputStream);
    }

    public void a(long j) {
        this.f = j;
    }

    public long e() {
        return this.f;
    }
}

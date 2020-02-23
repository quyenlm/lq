package com.tencent.beacon.cover;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.protocol.HTTP;

/* compiled from: ShareElfFile */
public final class f implements Closeable {
    private final FileInputStream a;
    private final Map<String, c> b = new HashMap();
    private a c = null;
    private b[] d = null;
    private c[] e = null;

    public f(File file) throws IOException {
        this.a = new FileInputStream(file);
        FileChannel channel = this.a.getChannel();
        this.c = new a(channel, (byte) 0);
        ByteBuffer allocate = ByteBuffer.allocate(128);
        allocate.limit(this.c.d);
        allocate.order(this.c.a[5] == 1 ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);
        channel.position(this.c.b);
        this.d = new b[this.c.e];
        for (int i = 0; i < this.d.length; i++) {
            a(channel, allocate, "failed to read phdr.");
            this.d[i] = new b(allocate, this.c.a[4], (byte) 0);
        }
        channel.position(this.c.c);
        allocate.limit(this.c.f);
        this.e = new c[this.c.g];
        for (int i2 = 0; i2 < this.e.length; i2++) {
            a(channel, allocate, "failed to read shdr.");
            this.e[i2] = new c(allocate, this.c.a[4], (byte) 0);
        }
        if (this.c.h > 0) {
            c cVar = this.e[this.c.h];
            ByteBuffer allocate2 = ByteBuffer.allocate((int) cVar.c);
            this.a.getChannel().position(cVar.b);
            a(this.a.getChannel(), allocate2, "failed to read section: " + cVar.d);
            for (c cVar2 : this.e) {
                allocate2.position(cVar2.a);
                cVar2.d = a(allocate2);
                this.b.put(cVar2.d, cVar2);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x0058 A[SYNTHETIC, Splitter:B:35:0x0058] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int a(java.io.File r6) throws java.io.IOException {
        /*
            r1 = 1
            r0 = 0
            r3 = 0
            r2 = 4
            byte[] r4 = new byte[r2]     // Catch:{ all -> 0x0054 }
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ all -> 0x0054 }
            r2.<init>(r6)     // Catch:{ all -> 0x0054 }
            r2.read(r4)     // Catch:{ all -> 0x0064 }
            r3 = 0
            byte r3 = r4[r3]     // Catch:{ all -> 0x0064 }
            r5 = 100
            if (r3 != r5) goto L_0x002e
            r3 = 1
            byte r3 = r4[r3]     // Catch:{ all -> 0x0064 }
            r5 = 101(0x65, float:1.42E-43)
            if (r3 != r5) goto L_0x002e
            r3 = 2
            byte r3 = r4[r3]     // Catch:{ all -> 0x0064 }
            r5 = 121(0x79, float:1.7E-43)
            if (r3 != r5) goto L_0x002e
            r3 = 3
            byte r3 = r4[r3]     // Catch:{ all -> 0x0064 }
            r5 = 10
            if (r3 != r5) goto L_0x002e
            r2.close()     // Catch:{ Throwable -> 0x005c }
        L_0x002d:
            return r0
        L_0x002e:
            r0 = 0
            byte r0 = r4[r0]     // Catch:{ all -> 0x0064 }
            r3 = 127(0x7f, float:1.78E-43)
            if (r0 != r3) goto L_0x004f
            r0 = 1
            byte r0 = r4[r0]     // Catch:{ all -> 0x0064 }
            r3 = 69
            if (r0 != r3) goto L_0x004f
            r0 = 2
            byte r0 = r4[r0]     // Catch:{ all -> 0x0064 }
            r3 = 76
            if (r0 != r3) goto L_0x004f
            r0 = 3
            byte r0 = r4[r0]     // Catch:{ all -> 0x0064 }
            r3 = 70
            if (r0 != r3) goto L_0x004f
            r2.close()     // Catch:{ Throwable -> 0x005e }
        L_0x004d:
            r0 = r1
            goto L_0x002d
        L_0x004f:
            r2.close()     // Catch:{ Throwable -> 0x0060 }
        L_0x0052:
            r0 = -1
            goto L_0x002d
        L_0x0054:
            r0 = move-exception
            r1 = r3
        L_0x0056:
            if (r1 == 0) goto L_0x005b
            r1.close()     // Catch:{ Throwable -> 0x0062 }
        L_0x005b:
            throw r0
        L_0x005c:
            r1 = move-exception
            goto L_0x002d
        L_0x005e:
            r0 = move-exception
            goto L_0x004d
        L_0x0060:
            r0 = move-exception
            goto L_0x0052
        L_0x0062:
            r1 = move-exception
            goto L_0x005b
        L_0x0064:
            r0 = move-exception
            r1 = r2
            goto L_0x0056
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.beacon.cover.f.a(java.io.File):int");
    }

    public static void a(FileChannel fileChannel, ByteBuffer byteBuffer, String str) throws IOException {
        byteBuffer.rewind();
        int read = fileChannel.read(byteBuffer);
        if (read != byteBuffer.limit()) {
            throw new IOException(str + " Rest bytes insufficient, expect to read " + byteBuffer.limit() + " bytes but only " + read + " bytes were read.");
        }
        byteBuffer.flip();
    }

    private static String a(ByteBuffer byteBuffer) {
        byte[] array = byteBuffer.array();
        int position = byteBuffer.position();
        while (byteBuffer.hasRemaining() && array[byteBuffer.position()] != 0) {
            byteBuffer.position(byteBuffer.position() + 1);
        }
        byteBuffer.position(byteBuffer.position() + 1);
        return new String(array, position, (byteBuffer.position() - position) - 1, Charset.forName(HTTP.ASCII));
    }

    public final void close() throws IOException {
        this.a.close();
        this.b.clear();
        this.d = null;
        this.e = null;
    }

    /* compiled from: ShareElfFile */
    public static class a {
        public final byte[] a;
        public final long b;
        public final long c;
        public final short d;
        public final short e;
        public final short f;
        public final short g;
        public final short h;
        private int i;

        /* synthetic */ a(FileChannel fileChannel, byte b2) throws IOException {
            this(fileChannel);
        }

        private a(FileChannel fileChannel) throws IOException {
            this.a = new byte[16];
            fileChannel.position(0);
            fileChannel.read(ByteBuffer.wrap(this.a));
            if (this.a[0] == Byte.MAX_VALUE && this.a[1] == 69 && this.a[2] == 76 && this.a[3] == 70) {
                f.a((int) this.a[4], 2, "bad elf class: " + this.a[4]);
                f.a((int) this.a[5], 2, "bad elf data encoding: " + this.a[5]);
                ByteBuffer allocate = ByteBuffer.allocate(this.a[4] == 1 ? 36 : 48);
                allocate.order(this.a[5] == 1 ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);
                f.a(fileChannel, allocate, "failed to read rest part of ehdr.");
                allocate.getShort();
                allocate.getShort();
                this.i = allocate.getInt();
                f.a(this.i, 1, "bad elf version: " + this.i);
                switch (this.a[4]) {
                    case 1:
                        allocate.getInt();
                        this.b = (long) allocate.getInt();
                        this.c = (long) allocate.getInt();
                        break;
                    case 2:
                        allocate.getLong();
                        this.b = allocate.getLong();
                        this.c = allocate.getLong();
                        break;
                    default:
                        throw new IOException("Unexpected elf class: " + this.a[4]);
                }
                allocate.getInt();
                allocate.getShort();
                this.d = allocate.getShort();
                this.e = allocate.getShort();
                this.f = allocate.getShort();
                this.g = allocate.getShort();
                this.h = allocate.getShort();
                return;
            }
            throw new IOException(String.format("bad elf magic: %x %x %x %x.", new Object[]{Byte.valueOf(this.a[0]), Byte.valueOf(this.a[1]), Byte.valueOf(this.a[2]), Byte.valueOf(this.a[3])}));
        }
    }

    /* compiled from: ShareElfFile */
    public static class b {
        /* synthetic */ b(ByteBuffer byteBuffer, int i, byte b) throws IOException {
            this(byteBuffer, i);
        }

        private b(ByteBuffer byteBuffer, int i) throws IOException {
            switch (i) {
                case 1:
                    byteBuffer.getInt();
                    byteBuffer.getInt();
                    byteBuffer.getInt();
                    byteBuffer.getInt();
                    byteBuffer.getInt();
                    byteBuffer.getInt();
                    byteBuffer.getInt();
                    byteBuffer.getInt();
                    return;
                case 2:
                    byteBuffer.getInt();
                    byteBuffer.getInt();
                    byteBuffer.getLong();
                    byteBuffer.getLong();
                    byteBuffer.getLong();
                    byteBuffer.getLong();
                    byteBuffer.getLong();
                    byteBuffer.getLong();
                    return;
                default:
                    throw new IOException("Unexpected elf class: " + i);
            }
        }
    }

    /* compiled from: ShareElfFile */
    public static class c {
        public final int a;
        public final long b;
        public final long c;
        public String d;

        /* synthetic */ c(ByteBuffer byteBuffer, int i, byte b2) throws IOException {
            this(byteBuffer, i);
        }

        private c(ByteBuffer byteBuffer, int i) throws IOException {
            switch (i) {
                case 1:
                    this.a = byteBuffer.getInt();
                    byteBuffer.getInt();
                    byteBuffer.getInt();
                    byteBuffer.getInt();
                    this.b = (long) byteBuffer.getInt();
                    this.c = (long) byteBuffer.getInt();
                    byteBuffer.getInt();
                    byteBuffer.getInt();
                    byteBuffer.getInt();
                    byteBuffer.getInt();
                    break;
                case 2:
                    this.a = byteBuffer.getInt();
                    byteBuffer.getInt();
                    byteBuffer.getLong();
                    byteBuffer.getLong();
                    this.b = byteBuffer.getLong();
                    this.c = byteBuffer.getLong();
                    byteBuffer.getInt();
                    byteBuffer.getInt();
                    byteBuffer.getLong();
                    byteBuffer.getLong();
                    break;
                default:
                    throw new IOException("Unexpected elf class: " + i);
            }
            this.d = null;
        }
    }

    static /* synthetic */ void a(int i, int i2, String str) throws IOException {
        if (i <= 0 || i > i2) {
            throw new IOException(str);
        }
    }
}

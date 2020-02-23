package com.tencent.imsdk.tool.etc;

import android.support.v4.view.MotionEventCompat;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.ProtocolException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Properties;
import java.util.zip.ZipException;

public final class ApkUtils {
    private static final int CFD_LOCATOR_OFFSET = 16;
    private static final String CHANNELID = "channelId";
    protected static final ZipLong EOCD_SIG = new ZipLong(101010256);
    private static final int MIN_EOCD_SIZE = 22;
    /* access modifiers changed from: private */
    public static ZipShort protoHead = new ZipShort(38650);

    public static final class ZipLong implements Cloneable {
        private long value;

        public ZipLong(byte[] bytes) {
            this(bytes, 0);
        }

        public ZipLong(byte[] bytes, int offset) {
            this.value = ((long) (bytes[offset + 3] << 24)) & 4278190080L;
            this.value += (long) ((bytes[offset + 2] << 16) & 16711680);
            this.value += (long) ((bytes[offset + 1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
            this.value += (long) (bytes[offset] & 255);
        }

        public ZipLong(long value2) {
            this.value = value2;
        }

        public boolean equals(Object o) {
            if (o == null || !(o instanceof ZipLong) || this.value != ((ZipLong) o).getValue()) {
                return false;
            }
            return true;
        }

        public byte[] getBytes() {
            return new byte[]{(byte) ((int) (this.value & 255)), (byte) ((int) ((this.value & 65280) >> 8)), (byte) ((int) ((this.value & 16711680) >> 16)), (byte) ((int) ((this.value & 4278190080L) >> 24))};
        }

        public long getValue() {
            return this.value;
        }

        public int hashCode() {
            return (int) this.value;
        }
    }

    public static final class ZipShort implements Cloneable {
        private int value;

        public ZipShort(byte[] bytes) {
            this(bytes, 0);
        }

        public ZipShort(byte[] bytes, int offset) {
            this.value = (bytes[offset + 1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK;
            this.value += bytes[offset] & 255;
        }

        public ZipShort(int value2) {
            this.value = value2;
        }

        public boolean equals(Object o) {
            if (o == null || !(o instanceof ZipShort) || this.value != ((ZipShort) o).getValue()) {
                return false;
            }
            return true;
        }

        public byte[] getBytes() {
            return new byte[]{(byte) (this.value & 255), (byte) ((this.value & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8)};
        }

        public int getValue() {
            return this.value;
        }

        public int hashCode() {
            return this.value;
        }
    }

    private static class ApkExternalInfo {
        byte[] otherData;
        Properties p;

        private ApkExternalInfo() {
            this.p = new Properties();
        }

        /* access modifiers changed from: package-private */
        public void decode(byte[] data) throws IOException {
            if (data != null) {
                ByteBuffer bb = ByteBuffer.wrap(data);
                int headLength = ApkUtils.protoHead.getBytes().length;
                byte[] d = new byte[headLength];
                bb.get(d);
                if (!ApkUtils.protoHead.equals(new ZipShort(d))) {
                    throw new ProtocolException("unknow protocl [" + Arrays.toString(data) + "]");
                } else if (data.length - headLength > 2) {
                    byte[] d2 = new byte[2];
                    bb.get(d2);
                    int len = new ZipShort(d2).getValue();
                    if ((data.length - headLength) - 2 >= len) {
                        byte[] d3 = new byte[len];
                        bb.get(d3);
                        this.p.load(new ByteArrayInputStream(d3));
                        int leftLen = ((data.length - headLength) - len) - 2;
                        if (leftLen > 0) {
                            this.otherData = new byte[leftLen];
                            bb.get(this.otherData);
                        }
                    }
                }
            }
        }

        public String toString() {
            return "ApkExternalInfo [p=" + this.p + ", otherData=" + Arrays.toString(this.otherData) + "]";
        }
    }

    public static String read(File apkFile, String key) throws IOException {
        String str = null;
        RandomAccessFile archive = null;
        try {
            RandomAccessFile archive2 = new RandomAccessFile(apkFile, "r");
            try {
                byte[] readComment = readComment(archive2);
                if (readComment != null) {
                    ApkExternalInfo apkExternalInfo = new ApkExternalInfo();
                    apkExternalInfo.decode(readComment);
                    str = apkExternalInfo.p.getProperty(key);
                    if (archive2 != null) {
                        try {
                            archive2.close();
                        } catch (Exception e) {
                            IMLogger.e(e.getMessage());
                        }
                    }
                } else if (archive2 != null) {
                    try {
                        archive2.close();
                    } catch (Exception e2) {
                        IMLogger.e(e2.getMessage());
                    }
                }
                return str;
            } catch (Throwable th) {
                th = th;
                archive = archive2;
            }
        } catch (Throwable th2) {
            th = th2;
            if (archive != null) {
                try {
                    archive.close();
                } catch (Exception e3) {
                    IMLogger.e(e3.getMessage());
                }
            }
            throw th;
        }
    }

    public static String readChannelId(File apkFile) throws IOException {
        return read(apkFile, CHANNELID);
    }

    private static byte[] readComment(RandomAccessFile archive) throws IOException {
        long off = archive.length() - 22;
        archive.seek(off);
        byte[] sig = EOCD_SIG.getBytes();
        int curr = archive.read();
        boolean found = false;
        while (true) {
            if (curr != -1) {
                if (curr == sig[0] && archive.read() == sig[1] && archive.read() == sig[2] && archive.read() == sig[3]) {
                    found = true;
                    break;
                }
                off--;
                archive.seek(off);
                curr = archive.read();
            } else {
                break;
            }
        }
        if (!found) {
            throw new ZipException("archive is not a ZIP archive");
        }
        archive.seek(16 + off + 4);
        byte[] data = new byte[2];
        archive.readFully(data);
        int length = new ZipShort(data).getValue();
        if (length == 0) {
            byte[] bArr = data;
            return null;
        }
        byte[] data2 = new byte[length];
        archive.read(data2);
        byte[] bArr2 = data2;
        return data2;
    }
}

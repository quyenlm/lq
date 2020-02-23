package com.tencent.imsdk.tool.etc;

import android.support.v4.view.MotionEventCompat;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.zip.ZipException;

public class ZipCommentReader {
    private static final int CFD_LOCATOR_OFFSET = 16;
    protected static final ZipLong EOCD_SIG = new ZipLong(101010256);
    private static final int MIN_EOCD_SIZE = 22;

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

        public Object clone() throws CloneNotSupportedException {
            return super.clone();
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

        public Object clone() throws CloneNotSupportedException {
            return super.clone();
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

    public static void main(String[] args) throws Throwable {
        positionAtCentralDirectory("D:\\lab.zip");
    }

    public static String positionAtCentralDirectory(String filePath) {
        if (filePath == null || "".equals(filePath)) {
            return "";
        }
        try {
            RandomAccessFile archive = new RandomAccessFile(new File(filePath), "r");
            try {
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
                    archive.close();
                    throw new ZipException("archive is not a ZIP archive");
                }
                archive.seek(16 + off + 4);
                byte[] cfdOffset = new byte[2];
                archive.readFully(cfdOffset);
                byte[] cfdOffset2 = new byte[new ZipShort(cfdOffset).getValue()];
                archive.readFully(cfdOffset2);
                if (archive != null) {
                    archive.close();
                }
                return new String(cfdOffset2, Charset.forName("UTF-8"));
            } catch (IOException e) {
                e = e;
                RandomAccessFile randomAccessFile = archive;
                IMLogger.d("ZipCommentReader read file error : " + e.getMessage());
                return "";
            }
        } catch (IOException e2) {
            e = e2;
            IMLogger.d("ZipCommentReader read file error : " + e.getMessage());
            return "";
        }
    }
}

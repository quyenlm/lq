package com.squareup.wire;

import java.io.IOException;

public final class WireOutput {
    private final byte[] buffer;
    private final int limit;
    private int position;

    public static int int32Size(int value) {
        if (value >= 0) {
            return varint32Size(value);
        }
        return 10;
    }

    public static int int64Size(long value) {
        if (value >= 0) {
            return varint64Size(value);
        }
        return 10;
    }

    public static int tagSize(int fieldNumber, WireType wireType) {
        return int32Size(makeTag(fieldNumber, wireType));
    }

    public static int messageSize(int fieldNumber, int messageLength) {
        return tagSize(fieldNumber, WireType.LENGTH_DELIMITED) + int32Size(messageLength) + messageLength;
    }

    public static int writeTag(int fieldNumber, WireType wireType, byte[] buffer2, int offset) {
        return writeVarint((long) makeTag(fieldNumber, wireType), buffer2, offset);
    }

    public static int writeVarint(long value, byte[] buffer2, int offset) {
        int start = offset;
        while (true) {
            int offset2 = offset;
            if ((-128 & value) == 0) {
                buffer2[offset2] = (byte) ((int) value);
                return (offset2 + 1) - start;
            }
            offset = offset2 + 1;
            buffer2[offset2] = (byte) ((int) ((127 & value) | 128));
            value >>>= 7;
        }
    }

    public static int messageHeaderSize(int fieldNumber, int byteCount) {
        return tagSize(fieldNumber, WireType.LENGTH_DELIMITED) + int32Size(byteCount);
    }

    public static int writeMessageHeader(int fieldNumber, byte[] buffer2, int bufferOffset, int byteCount) {
        int start = bufferOffset;
        int bufferOffset2 = bufferOffset + writeTag(fieldNumber, WireType.LENGTH_DELIMITED, buffer2, bufferOffset);
        return (bufferOffset2 + writeVarint((long) byteCount, buffer2, bufferOffset2)) - start;
    }

    public static int makeTag(int fieldNumber, WireType wireType) {
        return (fieldNumber << 3) | wireType.value();
    }

    private WireOutput(byte[] buffer2, int offset, int length) {
        this.buffer = buffer2;
        this.position = offset;
        this.limit = offset + length;
    }

    static WireOutput newInstance(byte[] flatArray) {
        return newInstance(flatArray, 0, flatArray.length);
    }

    static WireOutput newInstance(byte[] flatArray, int offset, int length) {
        return new WireOutput(flatArray, offset, length);
    }

    static int varintTagSize(int tag) {
        return varint32Size(makeTag(tag, WireType.VARINT));
    }

    static int varint32Size(int value) {
        if ((value & -128) == 0) {
            return 1;
        }
        if ((value & -16384) == 0) {
            return 2;
        }
        if ((-2097152 & value) == 0) {
            return 3;
        }
        if ((-268435456 & value) == 0) {
            return 4;
        }
        return 5;
    }

    static int varint64Size(long value) {
        if ((-128 & value) == 0) {
            return 1;
        }
        if ((-16384 & value) == 0) {
            return 2;
        }
        if ((-2097152 & value) == 0) {
            return 3;
        }
        if ((-268435456 & value) == 0) {
            return 4;
        }
        if ((-34359738368L & value) == 0) {
            return 5;
        }
        if ((-4398046511104L & value) == 0) {
            return 6;
        }
        if ((-562949953421312L & value) == 0) {
            return 7;
        }
        if ((-72057594037927936L & value) == 0) {
            return 8;
        }
        if ((Long.MIN_VALUE & value) == 0) {
            return 9;
        }
        return 10;
    }

    /* access modifiers changed from: package-private */
    public void writeRawByte(byte value) throws IOException {
        if (this.position == this.limit) {
            throw new IOException("Out of space: position=" + this.position + ", limit=" + this.limit);
        }
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        bArr[i] = value;
    }

    /* access modifiers changed from: package-private */
    public void writeRawByte(int value) throws IOException {
        writeRawByte((byte) value);
    }

    /* access modifiers changed from: package-private */
    public void writeRawBytes(byte[] value) throws IOException {
        writeRawBytes(value, 0, value.length);
    }

    /* access modifiers changed from: package-private */
    public void writeRawBytes(byte[] value, int offset, int length) throws IOException {
        if (this.limit - this.position >= length) {
            System.arraycopy(value, offset, this.buffer, this.position, length);
            this.position += length;
            return;
        }
        throw new IOException("Out of space: position=" + this.position + ", limit=" + this.limit);
    }

    /* access modifiers changed from: package-private */
    public void writeTag(int fieldNumber, WireType wireType) throws IOException {
        writeVarint32(makeTag(fieldNumber, wireType));
    }

    /* access modifiers changed from: package-private */
    public void writeSignedVarint32(int value) throws IOException {
        if (value >= 0) {
            writeVarint32(value);
        } else {
            writeVarint64((long) value);
        }
    }

    /* access modifiers changed from: package-private */
    public void writeVarint32(int value) throws IOException {
        while ((value & -128) != 0) {
            writeRawByte((value & 127) | 128);
            value >>>= 7;
        }
        writeRawByte(value);
    }

    /* access modifiers changed from: package-private */
    public void writeVarint64(long value) throws IOException {
        while ((-128 & value) != 0) {
            writeRawByte((((int) value) & 127) | 128);
            value >>>= 7;
        }
        writeRawByte((int) value);
    }

    /* access modifiers changed from: package-private */
    public void writeFixed32(int value) throws IOException {
        writeRawByte(value & 255);
        writeRawByte((value >> 8) & 255);
        writeRawByte((value >> 16) & 255);
        writeRawByte((value >> 24) & 255);
    }

    /* access modifiers changed from: package-private */
    public void writeFixed64(long value) throws IOException {
        writeRawByte(((int) value) & 255);
        writeRawByte(((int) (value >> 8)) & 255);
        writeRawByte(((int) (value >> 16)) & 255);
        writeRawByte(((int) (value >> 24)) & 255);
        writeRawByte(((int) (value >> 32)) & 255);
        writeRawByte(((int) (value >> 40)) & 255);
        writeRawByte(((int) (value >> 48)) & 255);
        writeRawByte(((int) (value >> 56)) & 255);
    }

    static int zigZag32(int n) {
        return (n << 1) ^ (n >> 31);
    }

    static long zigZag64(long n) {
        return (n << 1) ^ (n >> 63);
    }
}

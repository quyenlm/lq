package com.squareup.wire;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Source;

final class WireInput {
    private static final String ENCOUNTERED_A_MALFORMED_VARINT = "WireInput encountered a malformed varint.";
    private static final String ENCOUNTERED_A_NEGATIVE_SIZE = "Encountered a negative size";
    private static final String INPUT_ENDED_UNEXPECTEDLY = "The input ended unexpectedly in the middle of a field";
    private static final String PROTOCOL_MESSAGE_CONTAINED_AN_INVALID_TAG_ZERO = "Protocol message contained an invalid tag (zero).";
    private static final String PROTOCOL_MESSAGE_END_GROUP_TAG_DID_NOT_MATCH_EXPECTED_TAG = "Protocol message end-group tag did not match expected tag.";
    public static final int RECURSION_LIMIT = 64;
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private int currentLimit = Integer.MAX_VALUE;
    private int lastTag;
    private int pos = 0;
    public int recursionDepth;
    private final BufferedSource source;

    public static WireInput newInstance(byte[] buf) {
        return new WireInput(new Buffer().write(buf));
    }

    public static WireInput newInstance(byte[] buf, int offset, int count) {
        return new WireInput(new Buffer().write(buf, offset, count));
    }

    public static WireInput newInstance(InputStream source2) {
        return new WireInput(Okio.buffer(Okio.source(source2)));
    }

    public static WireInput newInstance(Source source2) {
        return new WireInput(Okio.buffer(source2));
    }

    public int readTag() throws IOException {
        if (isAtEnd()) {
            this.lastTag = 0;
            return 0;
        }
        this.lastTag = readVarint32();
        if (this.lastTag != 0) {
            return this.lastTag;
        }
        throw new IOException(PROTOCOL_MESSAGE_CONTAINED_AN_INVALID_TAG_ZERO);
    }

    public void checkLastTagWas(int value) throws IOException {
        if (this.lastTag != value) {
            throw new IOException(PROTOCOL_MESSAGE_END_GROUP_TAG_DID_NOT_MATCH_EXPECTED_TAG);
        }
    }

    public String readString() throws IOException {
        int count = readVarint32();
        this.pos += count;
        return this.source.readString((long) count, UTF_8);
    }

    public ByteString readBytes() throws IOException {
        return readBytes(readVarint32());
    }

    public ByteString readBytes(int count) throws IOException {
        this.pos += count;
        this.source.require((long) count);
        return this.source.readByteString((long) count);
    }

    public int readVarint32() throws IOException {
        int result;
        this.pos++;
        int tmp = this.source.readByte();
        if (tmp >= 0) {
            int i = tmp;
            return tmp;
        }
        int result2 = tmp & 127;
        this.pos++;
        byte tmp2 = this.source.readByte();
        if (tmp2 >= 0) {
            result = result2 | (tmp2 << 7);
        } else {
            int result3 = result2 | ((tmp2 & Byte.MAX_VALUE) << 7);
            this.pos++;
            tmp2 = this.source.readByte();
            if (tmp2 >= 0) {
                result = result3 | (tmp2 << 14);
            } else {
                int result4 = result3 | ((tmp2 & Byte.MAX_VALUE) << 14);
                this.pos++;
                tmp2 = this.source.readByte();
                if (tmp2 >= 0) {
                    result = result4 | (tmp2 << 21);
                } else {
                    int result5 = result4 | ((tmp2 & Byte.MAX_VALUE) << 21);
                    this.pos++;
                    tmp2 = this.source.readByte();
                    result = result5 | (tmp2 << 28);
                    if (tmp2 < 0) {
                        for (int i2 = 0; i2 < 5; i2++) {
                            this.pos++;
                            if (this.source.readByte() >= 0) {
                                byte b = tmp2;
                                return result;
                            }
                        }
                        throw new IOException(ENCOUNTERED_A_MALFORMED_VARINT);
                    }
                }
            }
        }
        byte b2 = tmp2;
        return result;
    }

    public long readVarint64() throws IOException {
        long result = 0;
        for (int shift = 0; shift < 64; shift += 7) {
            this.pos++;
            byte b = this.source.readByte();
            result |= ((long) (b & Byte.MAX_VALUE)) << shift;
            if ((b & 128) == 0) {
                return result;
            }
        }
        throw new IOException(ENCOUNTERED_A_MALFORMED_VARINT);
    }

    public int readFixed32() throws IOException {
        this.pos += 4;
        return this.source.readIntLe();
    }

    public long readFixed64() throws IOException {
        this.pos += 8;
        return this.source.readLongLe();
    }

    public static int decodeZigZag32(int n) {
        return (n >>> 1) ^ (-(n & 1));
    }

    public static long decodeZigZag64(long n) {
        return (n >>> 1) ^ (-(1 & n));
    }

    private WireInput(BufferedSource source2) {
        this.source = source2;
    }

    public int pushLimit(int byteLimit) throws IOException {
        if (byteLimit < 0) {
            throw new IOException(ENCOUNTERED_A_NEGATIVE_SIZE);
        }
        int byteLimit2 = byteLimit + this.pos;
        int oldLimit = this.currentLimit;
        if (byteLimit2 > oldLimit) {
            throw new EOFException(INPUT_ENDED_UNEXPECTEDLY);
        }
        this.currentLimit = byteLimit2;
        return oldLimit;
    }

    public void popLimit(int oldLimit) {
        this.currentLimit = oldLimit;
    }

    private boolean isAtEnd() throws IOException {
        if (getPosition() == ((long) this.currentLimit)) {
            return true;
        }
        return this.source.exhausted();
    }

    public long getPosition() {
        return (long) this.pos;
    }

    /*  JADX ERROR: StackOverflow in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    public void skipGroup() throws java.io.IOException {
        /*
            r2 = this;
        L_0x0000:
            int r0 = r2.readTag()
            if (r0 == 0) goto L_0x000c
            boolean r1 = r2.skipField(r0)
            if (r1 == 0) goto L_0x0000
        L_0x000c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.wire.WireInput.skipGroup():void");
    }

    private boolean skipField(int tag) throws IOException {
        switch (WireType.valueOf(tag)) {
            case VARINT:
                readVarint64();
                return false;
            case FIXED32:
                readFixed32();
                return false;
            case FIXED64:
                readFixed64();
                return false;
            case LENGTH_DELIMITED:
                skip((long) readVarint32());
                return false;
            case START_GROUP:
                skipGroup();
                checkLastTagWas((tag & -8) | WireType.END_GROUP.value());
                return false;
            case END_GROUP:
                return true;
            default:
                throw new AssertionError();
        }
    }

    private void skip(long count) throws IOException {
        this.pos = (int) (((long) this.pos) + count);
        this.source.skip(count);
    }
}

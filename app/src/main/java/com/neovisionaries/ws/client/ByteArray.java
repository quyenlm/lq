package com.neovisionaries.ws.client;

import java.nio.ByteBuffer;

class ByteArray {
    private static final int ADDITIONAL_BUFFER_SIZE = 1024;
    private ByteBuffer mBuffer;
    private int mLength;

    public ByteArray(int capacity) {
        this.mBuffer = ByteBuffer.allocate(capacity);
        this.mLength = 0;
    }

    public ByteArray(byte[] data) {
        this.mBuffer = ByteBuffer.wrap(data);
        this.mLength = data.length;
    }

    public int length() {
        return this.mLength;
    }

    public byte get(int index) throws IndexOutOfBoundsException {
        if (index >= 0 && this.mLength > index) {
            return this.mBuffer.get(index);
        }
        throw new IndexOutOfBoundsException(String.format("Bad index: index=%d, length=%d", new Object[]{Integer.valueOf(index), Integer.valueOf(this.mLength)}));
    }

    private void expandBuffer(int newBufferSize) {
        ByteBuffer newBuffer = ByteBuffer.allocate(newBufferSize);
        int oldPosition = this.mBuffer.position();
        this.mBuffer.position(0);
        newBuffer.put(this.mBuffer);
        newBuffer.position(oldPosition);
        this.mBuffer = newBuffer;
    }

    public void put(int data) {
        if (this.mBuffer.capacity() < this.mLength + 1) {
            expandBuffer(this.mLength + 1024);
        }
        this.mBuffer.put((byte) data);
        this.mLength++;
    }

    public void put(byte[] source) {
        if (this.mBuffer.capacity() < this.mLength + source.length) {
            expandBuffer(this.mLength + source.length + 1024);
        }
        this.mBuffer.put(source);
        this.mLength += source.length;
    }

    public void put(byte[] source, int index, int length) {
        if (this.mBuffer.capacity() < this.mLength + length) {
            expandBuffer(this.mLength + length + 1024);
        }
        this.mBuffer.put(source, index, length);
        this.mLength += length;
    }

    public void put(ByteArray source, int index, int length) {
        put(source.mBuffer.array(), index, length);
    }

    public byte[] toBytes() {
        return toBytes(0);
    }

    public byte[] toBytes(int beginIndex) {
        return toBytes(beginIndex, length());
    }

    public byte[] toBytes(int beginIndex, int endIndex) {
        int len = endIndex - beginIndex;
        if (len < 0 || beginIndex < 0 || this.mLength < endIndex) {
            throw new IllegalArgumentException(String.format("Bad range: beginIndex=%d, endIndex=%d, length=%d", new Object[]{Integer.valueOf(beginIndex), Integer.valueOf(endIndex), Integer.valueOf(this.mLength)}));
        }
        byte[] bytes = new byte[len];
        if (len != 0) {
            System.arraycopy(this.mBuffer.array(), beginIndex, bytes, 0, len);
        }
        return bytes;
    }

    public void clear() {
        this.mBuffer.clear();
        this.mBuffer.position(0);
        this.mLength = 0;
    }

    public void shrink(int size) {
        if (this.mBuffer.capacity() > size) {
            byte[] bytes = toBytes(this.mLength - size, this.mLength);
            this.mBuffer = ByteBuffer.wrap(bytes);
            this.mBuffer.position(bytes.length);
            this.mLength = bytes.length;
        }
    }

    public boolean getBit(int bitIndex) {
        if (((1 << (bitIndex % 8)) & get(bitIndex / 8)) != 0) {
            return true;
        }
        return false;
    }

    public int getBits(int bitIndex, int nBits) {
        int number = 0;
        int weight = 1;
        int i = 0;
        while (i < nBits) {
            if (getBit(bitIndex + i)) {
                number += weight;
            }
            i++;
            weight *= 2;
        }
        return number;
    }

    public int getHuffmanBits(int bitIndex, int nBits) {
        int number = 0;
        int weight = 1;
        int i = nBits - 1;
        while (i >= 0) {
            if (getBit(bitIndex + i)) {
                number += weight;
            }
            i--;
            weight *= 2;
        }
        return number;
    }

    public boolean readBit(int[] bitIndex) {
        boolean result = getBit(bitIndex[0]);
        bitIndex[0] = bitIndex[0] + 1;
        return result;
    }

    public int readBits(int[] bitIndex, int nBits) {
        int number = getBits(bitIndex[0], nBits);
        bitIndex[0] = bitIndex[0] + nBits;
        return number;
    }

    public void setBit(int bitIndex, boolean bit) {
        int value;
        int index = bitIndex / 8;
        int shift = bitIndex % 8;
        int value2 = get(index);
        if (bit) {
            value = value2 | (1 << shift);
        } else {
            value = value2 & ((1 << shift) ^ -1);
        }
        this.mBuffer.put(index, (byte) value);
    }

    public void clearBit(int bitIndex) {
        setBit(bitIndex, false);
    }
}

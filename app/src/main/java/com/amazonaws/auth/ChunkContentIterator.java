package com.amazonaws.auth;

class ChunkContentIterator {
    private int pos;
    private final byte[] signedChunk;

    public ChunkContentIterator(byte[] signedChunk2) {
        this.signedChunk = signedChunk2;
    }

    public boolean hasNext() {
        return this.pos < this.signedChunk.length;
    }

    public int read(byte[] output, int offset, int length) {
        if (length == 0) {
            return 0;
        }
        if (!hasNext()) {
            return -1;
        }
        int bytesToRead = Math.min(this.signedChunk.length - this.pos, length);
        System.arraycopy(this.signedChunk, this.pos, output, offset, bytesToRead);
        this.pos += bytesToRead;
        return bytesToRead;
    }
}

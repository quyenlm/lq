package com.neovisionaries.ws.client;

class CounterPayloadGenerator implements PayloadGenerator {
    private long mCount;

    CounterPayloadGenerator() {
    }

    public byte[] generate() {
        return Misc.getBytesUTF8(String.valueOf(increment()));
    }

    private long increment() {
        this.mCount = Math.max(this.mCount + 1, 1);
        return this.mCount;
    }
}

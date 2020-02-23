package com.garena.android.gpns.processor;

public abstract class AbstractProcessor {
    public abstract int getCommand();

    public abstract void process(byte[] bArr, int i, int i2) throws Exception;
}
